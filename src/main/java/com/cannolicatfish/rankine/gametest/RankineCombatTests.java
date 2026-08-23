package com.cannolicatfish.rankine.gametest;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Combat regression tests for the two damage-event mappings and the projectile hand-off
 * (review P2 findings #24, #25, #31).
 *
 * <p>All three are pure event-ordering bugs: the port subscribed to the 1.21 event whose NAME
 * matched the 1.18.2 one rather than the one at the same point in the sequence, and dropped the
 * weapon argument 1.21 added to {@code ArrowItem#createArrow}. Nothing throws, nothing logs, and
 * every existing gate stays green — the only way to see them is to run the real
 * {@code LivingEntity#hurt} and the real {@code createArrow} and read the numbers back out.
 */
@GameTestHolder(ProjectRankine.MODID)
@PrefixGameTestTemplate(false)
public class RankineCombatTests {

    private static void assertEmpty(List<String> problems, String what) {
        if (!problems.isEmpty()) {
            int shown = Math.min(problems.size(), 20);
            throw new GameTestAssertException(problems.size() + " " + what + ": "
                    + String.join(", ", problems.subList(0, shown))
                    + (problems.size() > shown ? " (+" + (problems.size() - shown) + " more)" : ""));
        }
    }

    private static void near(List<String> problems, String what, float actual, float expected) {
        if (Math.abs(actual - expected) > 1.0E-4F) {
            problems.add(what + " " + actual + ", expected " + expected);
        }
    }

    /**
     * Absorption hearts, the way an effect or the mod's own GUARD enchantment would grant them.
     * {@code setAbsorptionAmount} clamps to {@code Attributes.MAX_ABSORPTION}, which defaults to 0
     * on every mob, so raising the cap first is not optional — without it the amount silently stays
     * at zero and every assertion below would be measuring an unabsorbed hit.
     */
    private static void giveAbsorption(LivingEntity entity, float hearts) {
        AttributeInstance cap = entity.getAttribute(Attributes.MAX_ABSORPTION);
        if (cap == null) {
            throw new GameTestAssertException(entity.getType() + " has no MAX_ABSORPTION attribute");
        }
        cap.setBaseValue(hearts);
        entity.setAbsorptionAmount(hearts);
        if (entity.getAbsorptionAmount() != hearts) {
            throw new GameTestAssertException("absorption did not stick on " + entity.getType()
                    + " (" + entity.getAbsorptionAmount() + " of " + hearts + ")");
        }
    }

    private static ItemStack enchanted(ServerLevel level, ItemStack stack, ResourceKey<Enchantment> key, int enchantLevel) {
        Holder<Enchantment> holder = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(key);
        stack.enchant(holder, enchantLevel);
        return stack;
    }

    // ----------------------------------------------------------------------------------------
    // F25 / finding #25 — damage buffs must not punch through absorption hearts
    // ----------------------------------------------------------------------------------------

    /**
     * Rankine's attacker-side damage buffs must scale the post-absorption figure, as they did at
     * {@code 859f87773}, and must consume exactly the absorption hearts the baseline consumed.
     *
     * <p>1.18.2's {@code LivingDamageEvent} fired at the END of {@code actuallyHurt}, after
     * {@code f2 = Math.max(damage - getAbsorptionAmount(), 0)} had already taken its share out of
     * the absorption hearts. The port mapped it onto {@code LivingDamageEvent.Pre}, which NeoForge
     * fires one line EARLIER (LivingEntity.java:1743 vs the absorption reduction at :1744), so every
     * buff multiplied the pre-absorption number and the surplus leaked onto health.
     *
     * <p>Three cases, chosen so that each is wrong in a different way if the shim is wrong:
     * <ol>
     *   <li><b>fully absorbed, multiplicative buff</b> — the headline case from the finding: 8
     *       absorption, a 6-damage hit, Totem of Blazing (x1.5). 1.18.2 gave 0 health lost and 2
     *       absorption left; the unfixed port took 1 health and left 0 absorption.</li>
     *   <li><b>partly absorbed, multiplicative buff</b> — 4 absorption, 6 damage: the buff applies
     *       to the 2 that got through, not to all 6.</li>
     *   <li><b>fully absorbed, FLAT adder</b> — Endotoxin's +2.5/level on an Enderman. 1.18.2
     *       applied flat adders after absorption, so they bypassed it entirely and hit health; a
     *       naive "re-add what absorption will eat" fix gets this one wrong in the other direction
     *       (absorption swallows the adder). This is the case that proves the ABSORPTION reduction
     *       is pinned, not just the damage rewritten.</li>
     * </ol>
     * Absorption left is asserted alongside health every time: getting health right by handing
     * absorption a different number to eat is exactly the failure mode here.
     */
    @GameTest(template = "empty")
    public void damageBuffsSitBehindAbsorptionLikeTheBaseline(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        List<String> problems = new ArrayList<>();

        Player attacker = helper.makeMockPlayer(GameType.SURVIVAL);
        DamageSource source = attacker.damageSources().playerAttack(attacker);

        // (1) 8 absorption vs a 6-damage hit, multiplicative buff. Pig: 10 max health, no armor
        // attribute, so nothing between the raw amount and the absorption step.
        attacker.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(RankineItems.TOTEM_OF_BLAZING.get()));
        Pig fullyAbsorbed = helper.spawnWithNoFreeWill(EntityType.PIG, new BlockPos(1, 1, 1));
        fullyAbsorbed.setHealth(fullyAbsorbed.getMaxHealth());
        giveAbsorption(fullyAbsorbed, 8.0F);
        fullyAbsorbed.hurt(source, 6.0F);
        near(problems, "blazing/absorbed health", fullyAbsorbed.getHealth(), fullyAbsorbed.getMaxHealth());
        near(problems, "blazing/absorbed absorption", fullyAbsorbed.getAbsorptionAmount(), 2.0F);

        // (2) 4 absorption vs the same hit: 2 damage gets past absorption, x1.5 makes it 3.
        Pig partlyAbsorbed = helper.spawnWithNoFreeWill(EntityType.PIG, new BlockPos(3, 1, 1));
        float maxHealth = partlyAbsorbed.getMaxHealth();
        partlyAbsorbed.setHealth(maxHealth);
        giveAbsorption(partlyAbsorbed, 4.0F);
        partlyAbsorbed.hurt(source, 6.0F);
        near(problems, "blazing/partial health", partlyAbsorbed.getHealth(), maxHealth - 3.0F);
        near(problems, "blazing/partial absorption", partlyAbsorbed.getAbsorptionAmount(), 0.0F);

        // (3) A flat adder on a fully absorbed hit. Endotoxin I is +2.5 and fires on Endermen;
        // 1.18.2 added it to the already-absorbed-down value, so all 2.5 reached health while
        // absorption still only lost the original 6.
        ItemStack endotoxinSword = enchanted(level, new ItemStack(Items.IRON_SWORD),
                RankineEnchantments.ENDOTOXIN, 1);
        if (RankineEnchantmentHelper.getLevel(level, RankineEnchantments.ENDOTOXIN, endotoxinSword) != 1) {
            throw new GameTestAssertException("Endotoxin did not apply to the test sword; the flat-adder case would be vacuous");
        }
        attacker.setItemSlot(EquipmentSlot.MAINHAND, endotoxinSword);
        EnderMan enderman = helper.spawnWithNoFreeWill(EntityType.ENDERMAN, new BlockPos(1, 1, 3));
        float endermanMax = enderman.getMaxHealth();
        enderman.setHealth(endermanMax);
        giveAbsorption(enderman, 8.0F);
        enderman.hurt(source, 6.0F);
        near(problems, "endotoxin/absorbed health", enderman.getHealth(), endermanMax - 2.5F);
        near(problems, "endotoxin/absorbed absorption", enderman.getAbsorptionAmount(), 2.0F);

        assertEmpty(problems, "absorption-ordering mismatches");
        helper.succeed();
    }

    // ----------------------------------------------------------------------------------------
    // F25 / finding #24 — a parry keeps its invulnerability window and eats one hit, not many
    // ----------------------------------------------------------------------------------------

    /**
     * A knife parry must leave the 1.18.2 aftermath: no damage, but a full 20-tick invulnerability
     * window, the hurt animation, and exactly ONE point of knife durability however many attackers
     * swing during that window.
     *
     * <p>1.18.2's {@code LivingHurtEvent} fired from inside {@code actuallyHurt}, so cancelling it
     * skipped only the damage — {@code hurt()} had already set {@code invulnerableTime = 20} and
     * {@code hurtTime = 10} and went on to knock back, play the hurt sound and return true. It also
     * meant the handler was never invoked for a hit the {@code invulnerableTime > 10} cooldown gate
     * had already discarded. The port cancels {@code LivingIncomingDamageEvent} instead, which
     * fires before all of that: {@code hurt()} returns false immediately, no window opens, and
     * every subsequent swing re-enters the parry branch and spends another durability point.
     *
     * <p>Both halves are asserted, because either one alone would pass with the other broken: the
     * first hit's aftermath (return value, health, invulnerableTime, hurtTime, one durability
     * point) and a second hit inside the window (rejected, still no damage, still one durability
     * point). {@code player_attack} from a player is used deliberately — it is the one common
     * melee source whose scaling function ignores world difficulty, so the numbers do not depend on
     * what difficulty the GameTest server happens to boot at.
     */
    @GameTest(template = "empty")
    public void parryKeepsItsInvulnerabilityWindow(GameTestHelper helper) {
        List<String> problems = new ArrayList<>();

        Player attacker = helper.makeMockPlayer(GameType.SURVIVAL);
        DamageSource source = attacker.damageSources().playerAttack(attacker);

        Player defender = helper.makeMockPlayer(GameType.SURVIVAL);
        ItemStack knife = new ItemStack(RankineItems.FLINT_KNIFE.get());
        defender.setItemSlot(EquipmentSlot.OFFHAND, knife);
        defender.startUsingItem(InteractionHand.OFF_HAND);
        if (!defender.isUsingItem()) {
            throw new GameTestAssertException("the defender is not using the knife; the parry window never opened");
        }
        defender.setHealth(defender.getMaxHealth());

        boolean firstDelivered = defender.hurt(source, 6.0F);
        if (!firstDelivered) {
            problems.add("hurt() returned false for the parried hit; 1.18.2 cancelled the damage but still returned true");
        }
        near(problems, "health after the parry", defender.getHealth(), defender.getMaxHealth());
        if (defender.invulnerableTime != 20) {
            problems.add("invulnerableTime " + defender.invulnerableTime + " after the parry, expected 20");
        }
        if (defender.hurtTime != 10) {
            problems.add("hurtTime " + defender.hurtTime + " after the parry, expected 10");
        }
        if (knife.getDamageValue() != 1) {
            problems.add("knife durability spent " + knife.getDamageValue() + " on one parry, expected 1");
        }

        // Second swing inside the window: 1.18.2 threw it away at the cooldown gate before the
        // handler ever ran, so it cost neither health nor durability.
        boolean secondDelivered = defender.hurt(source, 6.0F);
        if (secondDelivered) {
            problems.add("a second hit inside the invulnerability window was delivered");
        }
        near(problems, "health after the second hit", defender.getHealth(), defender.getMaxHealth());
        if (knife.getDamageValue() != 1) {
            problems.add("knife durability spent " + knife.getDamageValue() + " across two hits in one window, expected 1");
        }

        assertEmpty(problems, "parry semantics mismatches");
        helper.succeed();
    }

    // ----------------------------------------------------------------------------------------
    // F26 / finding #31 — alloy arrows keep the firing weapon's enchantments
    // ----------------------------------------------------------------------------------------

    /**
     * An alloy arrow must carry the bow or crossbow it was fired from, because in 1.21.1 every
     * projectile enchantment is gated on that reference.
     *
     * <p>1.18.2 had no such field: {@code BowItem#releaseUsing} applied Power, Punch and Flame to
     * whatever {@code ArrowItem#createArrow} returned, so alloy arrows behaved like vanilla ones.
     * 1.21.1 passes the weapon into {@code createArrow} and {@code AbstractArrow} stores it as
     * {@code firedFromWeapon}; the port's override dropped the argument and hard-coded null, which
     * silently disabled all four — Piercing at construction, Flame through
     * {@code EnchantmentHelper#onProjectileSpawned}, Power through the
     * {@code getWeaponItem() != null} guard in {@code onHitEntity} (line 347) and Punch through
     * {@code doKnockback}'s null check (line 437).
     *
     * <p>Asserted against a VANILLA arrow built from the same weapon rather than against constants:
     * the requirement is parity, the 1.21 numbers are vanilla's to change, and a vanilla-side
     * regression would otherwise silently weaken this test. The unenchanted control at the end is
     * what stops the comparison from passing on two equally broken arrows, and each metric is read
     * through the exact expression the vanilla call site uses.
     */
    @GameTest(template = "empty")
    public void alloyArrowsKeepTheirWeaponEnchantments(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        List<String> problems = new ArrayList<>();

        ArrowItem alloyArrowItem = (ArrowItem) RankineItems.ALLOY_ARROW.get();
        ArrowItem vanillaArrowItem = (ArrowItem) Items.ARROW;
        Player shooter = helper.makeMockPlayer(GameType.SURVIVAL);
        Pig target = helper.spawnWithNoFreeWill(EntityType.PIG, new BlockPos(1, 1, 1));

        ItemStack bow = new ItemStack(Items.BOW);
        enchanted(level, bow, Enchantments.POWER, 5);
        enchanted(level, bow, Enchantments.PUNCH, 2);
        enchanted(level, bow, Enchantments.FLAME, 1);
        ItemStack crossbow = enchanted(level, new ItemStack(Items.CROSSBOW), Enchantments.PIERCING, 3);

        record Shot(String name, ItemStack weapon) {}
        for (Shot shot : List.of(new Shot("bow", bow), new Shot("crossbow", crossbow))) {
            AbstractArrow alloy = alloyArrowItem.createArrow(level,
                    new ItemStack(RankineItems.ALLOY_ARROW.get()), shooter, shot.weapon());
            AbstractArrow vanilla = vanillaArrowItem.createArrow(level,
                    new ItemStack(Items.ARROW), shooter, shot.weapon());

            if (alloy.getWeaponItem() == null) {
                problems.add(shot.name() + ": alloy arrow stored no firing weapon, so every "
                        + "projectile enchantment is gated off");
                continue;
            }
            problems.addAll(compareArrows(level, shot.name(), alloy, vanilla, target, shooter));
        }

        // Positive control on vanilla itself: if the bow's enchantments do nothing even to a
        // vanilla arrow, the parity comparisons above prove nothing.
        AbstractArrow vanillaFromBow = vanillaArrowItem.createArrow(level, new ItemStack(Items.ARROW), shooter, bow);
        if (vanillaFromBow.getRemainingFireTicks() <= 0) {
            problems.add("a vanilla arrow from a Flame bow is not on fire; the control is broken");
        }
        if (EnchantmentHelper.modifyDamage(level, bow, target,
                level.damageSources().arrow(vanillaFromBow, shooter), 2.0F) <= 2.0F) {
            problems.add("a Power bow adds no damage even to a vanilla arrow; the control is broken");
        }
        if (EnchantmentHelper.modifyKnockback(level, bow, target,
                level.damageSources().arrow(vanillaFromBow, shooter), 0.0F) <= 0.0F) {
            problems.add("a Punch bow adds no knockback even to a vanilla arrow; the control is broken");
        }
        AbstractArrow vanillaFromCrossbow = vanillaArrowItem.createArrow(level, new ItemStack(Items.ARROW), shooter, crossbow);
        if (vanillaFromCrossbow.getPierceLevel() <= 0) {
            problems.add("a vanilla arrow from a Piercing crossbow has no pierce level; the control is broken");
        }

        // Negative control: no weapon means no weapon. This is what the port produced for every
        // alloy arrow, so it also documents exactly what was being asserted away.
        AbstractArrow loose = alloyArrowItem.createArrow(level,
                new ItemStack(RankineItems.ALLOY_ARROW.get()), shooter, null);
        if (loose.getWeaponItem() != null) {
            problems.add("an alloy arrow created with no weapon reported one");
        }
        if (loose.getRemainingFireTicks() > 0 || loose.getPierceLevel() > 0) {
            problems.add("an alloy arrow created with no weapon picked up fire/pierce from nowhere");
        }

        assertEmpty(problems, "alloy arrow enchantment mismatches");
        helper.succeed();
    }

    /**
     * Each metric read through the same expression its vanilla call site uses:
     * fire ticks and pierce level are set at construction, Power is
     * {@code AbstractArrow#onHitEntity} line 348 and Punch is {@code doKnockback} line 438.
     */
    private static List<String> compareArrows(ServerLevel level, String what, AbstractArrow alloy,
                                              AbstractArrow vanilla, LivingEntity target, Player shooter) {
        List<String> problems = new ArrayList<>();

        if (alloy.getRemainingFireTicks() != vanilla.getRemainingFireTicks()) {
            problems.add(what + ": alloy arrow fire ticks " + alloy.getRemainingFireTicks()
                    + ", vanilla arrow " + vanilla.getRemainingFireTicks());
        }
        if (alloy.getPierceLevel() != vanilla.getPierceLevel()) {
            problems.add(what + ": alloy arrow pierce level " + alloy.getPierceLevel()
                    + ", vanilla arrow " + vanilla.getPierceLevel());
        }

        float alloyDamage = EnchantmentHelper.modifyDamage(level, alloy.getWeaponItem(), target,
                level.damageSources().arrow(alloy, shooter), 2.0F);
        float vanillaDamage = EnchantmentHelper.modifyDamage(level, vanilla.getWeaponItem(), target,
                level.damageSources().arrow(vanilla, shooter), 2.0F);
        if (Math.abs(alloyDamage - vanillaDamage) > 1.0E-4F) {
            problems.add(what + ": alloy arrow Power damage " + alloyDamage + ", vanilla arrow " + vanillaDamage);
        }

        float alloyKnockback = EnchantmentHelper.modifyKnockback(level, alloy.getWeaponItem(), target,
                level.damageSources().arrow(alloy, shooter), 0.0F);
        float vanillaKnockback = EnchantmentHelper.modifyKnockback(level, vanilla.getWeaponItem(), target,
                level.damageSources().arrow(vanilla, shooter), 0.0F);
        if (Math.abs(alloyKnockback - vanillaKnockback) > 1.0E-4F) {
            problems.add(what + ": alloy arrow Punch knockback " + alloyKnockback
                    + ", vanilla arrow " + vanillaKnockback);
        }

        return problems;
    }
}
