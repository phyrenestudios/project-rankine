package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.enchantment.RankineEnchantmentBehaviors;
import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.items.alloys.AlloyArmorItem;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import com.cannolicatfish.rankine.items.tools.SpearItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.List;

/**
 * 1.21.1 event mapping: LivingDamageEvent -> LivingDamageEvent.Pre.
 * getAmount/setAmount -> getNewDamage/setNewDamage. The new LivingDamageEvent.Post hook carries the
 * old Enchantment#doPostAttack behaviors, which vanilla no longer invokes for datapack enchantments.
 *
 * <p><b>Pre is post-armor but PRE-absorption; the 1.18.2 event was post-absorption.</b> Forge fired
 * LivingDamageEvent on {@code f2} — the value left after
 * {@code f2 = Math.max(damage - getAbsorptionAmount(), 0)} had already been taken out of the
 * absorption hearts — so {@code getAmount()} was exactly what health was about to lose. NeoForge
 * fires {@code Pre} at LivingEntity.java:1743, one line BEFORE the absorption reduction at :1744.
 * Reading {@code getNewDamage()} there and multiplying it hands absorption a bigger number to eat
 * and leaks the surplus onto health: an 8-absorption target taking a 6-damage hit from a
 * TOTEM_OF_BLAZING holder lost 0 health in 1.18.2 and 1 health here. The flat adders go wrong in
 * the opposite direction — 1.18.2 applied them after absorption, so they bypassed it entirely,
 * while pre-absorption they are simply eaten.
 *
 * <p>{@link LegacyDamage} restores the old view: it exposes the post-absorption figure, all of the
 * existing math runs on that, and the commit re-adds the absorbed part and pins the ABSORPTION
 * reduction to it so NeoForge still consumes exactly the hearts 1.18.2 consumed. Pinning is what
 * makes the flat-adder case exact as well; without it a flat adder on a fully absorbed hit would be
 * swallowed instead of reaching health. (A modifier registered here is still in time: the
 * {@code DamageContainer} runs its ABSORPTION functions at :1744, after this event.)
 */
public class LivingDamageHandler {
    public static void onLivingDamaged(LivingDamageEvent.Pre event) {
        if (LivingHurtHandler.wasNullified(event.getContainer())) {
            // 1.18.2: a cancelled LivingHurtEvent returned out of actuallyHurt before
            // LivingDamageEvent, so a parried or immune hit cost no armor durability and applied no
            // withering curse.
            return;
        }
        if (event.getEntity() instanceof Player player) {
            Level worldIn = player.getCommandSenderWorld();
            for (int i = 0; i < player.getInventory().armor.size(); ++i) {
                ItemStack s = player.getInventory().armor.get(i);
                int curSlot = i;
                if (s.getItem() instanceof AlloyArmorItem armor) {

                    if (worldIn.getRandom().nextFloat() > armor.getHeatResist(s) && (player.isInLava() || player.getRemainingFireTicks() > 0 || worldIn.dimension() == Level.NETHER)) {
                        s.hurtAndBreak(1, player, LivingHurtHandler.armorSlot(curSlot));
                    } else if ((worldIn.getRandom().nextFloat() > armor.getCorrResist(s) && player.isInWaterOrRain())) {
                        s.hurtAndBreak(1 + RankineEnchantmentHelper.getLevel(worldIn, RankineEnchantments.ENDOBIOTIC, s)*3, player, LivingHurtHandler.armorSlot(curSlot));
                    }
                }

            }
            // NOTE: this condition is always true (a source cannot be both WITHER and MAGIC) —
            // preserved verbatim from 1.18.2.
            if (!(event.getSource().is(DamageTypes.WITHER) && event.getSource().is(DamageTypes.MAGIC))) {
                boolean wither = false;
                for(int i = 0; i < player.getInventory().getContainerSize(); ++i) {
                    ItemStack itemstack = player.getInventory().getItem(i);
                    if (!itemstack.isEmpty() && RankineEnchantmentHelper.getLevel(worldIn, RankineEnchantments.WITHERING_CURSE, itemstack) > 0) {
                        wither = true;
                        break;
                    }
                }
                if (wither) {
                    player.addEffect(new MobEffectInstance(MobEffects.WITHER,100));
                }
            }
        }
    }

    public static void onDamageEntity(LivingDamageEvent.Pre event) {
        if (LivingHurtHandler.wasNullified(event.getContainer())) {
            // See onLivingDamaged: 1.18.2 never reached this handler for a parried or immune hit,
            // so no attacker buff, no Cleanse purge and no armor durability loss on one.
            return;
        }
        if (event.getSource().getEntity() instanceof Player player) {
            LegacyDamage damage = new LegacyDamage(event);
            ModConfigSpec.BooleanValue configSpec = VanillaIntegration.DISABLED_ITEMS.get(player.getMainHandItem().getItem());
            if (configSpec != null && configSpec.get()) {
                damage.set(1.0f);
            }

            Level playerLevel = player.level();
            if (!playerLevel.isClientSide) {
                LivingEntity receiver = event.getEntity();
                ItemStack mainHandStack = player.getItemInHand(InteractionHand.MAIN_HAND);

                if (mainHandStack.getItem() instanceof HammerItem) {
                    if ((receiver instanceof Blaze || receiver instanceof AbstractGolem || receiver instanceof AbstractSkeleton || receiver instanceof SkeletonHorse || receiver instanceof Guardian)) {
                        int endLevel = RankineEnchantmentHelper.getLevel(playerLevel, RankineEnchantments.ENDEAVOR, mainHandStack);
                        damage.set(damage.get() + damage.get()/2f + 1.5f*endLevel);
                        if (endLevel > 0 && playerLevel.getRandom().nextFloat() < (0.15f*endLevel) && playerLevel instanceof ServerLevel serverLevel) {
                            // 1.21.1: LootTables moved to the reloadable registries; LootContext.Builder -> LootParams.Builder
                            LootTable loot = serverLevel.getServer().reloadableRegistries().getLootTable(receiver.getLootTable());
                            LootParams params = new LootParams.Builder(serverLevel)
                                    .withParameter(LootContextParams.THIS_ENTITY, receiver)
                                    .withParameter(LootContextParams.ORIGIN, receiver.position())
                                    .withParameter(LootContextParams.DAMAGE_SOURCE, player.damageSources().playerAttack(player))
                                    .withParameter(LootContextParams.LAST_DAMAGE_PLAYER, player)
                                    .create(LootContextParamSets.ENTITY);
                            List<ItemStack> s = loot.getRandomItems(params);
                            if (s.size() > 1) {
                                receiver.spawnAtLocation(s.get(receiver.level().getRandom().nextInt(s.size())));
                            } else if (s.size() == 1) {
                                receiver.spawnAtLocation(s.get(0));
                            }
                        }
                    }
                }
                if ((player.getMainHandItem().getItem().equals(RankineItems.TOTEM_OF_BLAZING.get()) || player.getOffhandItem().getItem().equals(RankineItems.TOTEM_OF_BLAZING.get()))) {
                    damage.set(damage.get() + damage.get() * 0.5f);
                }
                int endotoxin = RankineEnchantmentHelper.getLevel(playerLevel, RankineEnchantments.ENDOTOXIN, mainHandStack);
                if (endotoxin >= 1) {
                    if ((receiver instanceof EnderMan || receiver instanceof Shulker || receiver instanceof Endermite || receiver.getCommandSenderWorld().dimension().equals(Level.END))) {
                        damage.set(damage.get() + 2.5f*endotoxin);
                    }
                }

                int cleanse = RankineEnchantmentHelper.getLevel(playerLevel, RankineEnchantments.CLEANSE, mainHandStack);
                if (cleanse >= 1) {
                    float bonus = cleanse * receiver.getActiveEffects().size();
                    damage.set(damage.get() + bonus);
                    boolean flag = bonus >= 1;
                    if (flag) {
                        receiver.removeAllEffects();
                        receiver.playSound(SoundEvents.GRINDSTONE_USE,1.0f, 1.0f);
                    }
                }

                int backstab = RankineEnchantmentHelper.getLevel(playerLevel, RankineEnchantments.BACKSTAB, mainHandStack);
                if (backstab >= 1) {
                    if (receiver.getDirection().equals(player.getDirection())) {
                        receiver.playSound(SoundEvents.TRIDENT_HIT,1.0f, 1.0f);
                        damage.set(damage.get() + damage.get() * backstab);
                    }
                }

                int torque = RankineEnchantmentHelper.getLevel(playerLevel, RankineEnchantments.TORQUE, mainHandStack);
                if (torque > 0) {

                    // 1.21.1: EntityDimensions is a record — height/width are accessor methods.
                    float size = receiver.getDimensions(receiver.getPose()).height() * receiver.getDimensions(receiver.getPose()).width();
                    float mod = -1.5f + torque;
                    damage.set(damage.get() + Math.max(0,Math.min(size + mod, 1.5f *torque)));
                }

                // 1.18.2 computed an unused effectChance for AlloySwordItem here; dead code, dropped.
            }

            damage.commit();

            for (ItemStack armor : event.getEntity().getArmorSlots()) {
                if (armor.getItem() instanceof AlloyArmorItem alloyArmor) {
                    // 1.21.1: ItemStack#getEquipmentSlot is nullable, resolve via the wearer.
                    EquipmentSlot slot = event.getEntity().getEquipmentSlotForItem(armor);
                    int i = alloyArmor.calcDurabilityLoss(armor, event.getEntity().getCommandSenderWorld(), event.getEntity(), true);
                    armor.hurtAndBreak(i, player, slot);
                }
            }
        }

    }

    /**
     * 1.21.1: datapack enchantments have no doPostAttack hook, so the old
     * PryingEnchantment/PunctureEnchantment behavior runs here instead.
     * Post fires after the damage was actually applied, matching the old post-attack ordering.
     */
    public static void onPostDamage(LivingDamageEvent.Post event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        Level level = attacker.level();
        if (level.isClientSide) return;
        ItemStack weapon = attacker.getMainHandItem();

        int prying = RankineEnchantmentHelper.getLevel(level, RankineEnchantments.PRYING, weapon);
        if (prying > 0) {
            RankineEnchantmentBehaviors.applyPrying(attacker, event.getEntity(), prying);
        }

        if (weapon.getItem() instanceof SpearItem) {
            int puncture = RankineEnchantmentHelper.getLevel(level, RankineEnchantments.PUNCTURE, weapon);
            if (puncture > 0) {
                RankineEnchantmentBehaviors.applyPuncture(attacker, event.getEntity(), puncture);
            }
        }
    }

    /**
     * The post-absorption damage figure 1.18.2's LivingDamageEvent carried, expressed on top of
     * 1.21's pre-absorption {@code LivingDamageEvent.Pre}.
     *
     * <p>Construction reproduces 1.18.2 {@code actuallyHurt} verbatim —
     * {@code f2 = Math.max(damage - getAbsorptionAmount(), 0)}, with {@code absorbed} the part the
     * absorption hearts are about to take. Every buff then runs on {@code f2}, exactly as it did at
     * {@code 859f87773}.
     *
     * <p>{@link #commit()} hands the sequence back: the new damage is the buffed value plus the
     * absorbed part, and an ABSORPTION reduction modifier pins the reduction to {@code absorbed} so
     * NeoForge (LivingEntity.java:1744-1746) removes exactly the hearts 1.18.2 removed and health
     * loses exactly the buffed value. Without the pin, a hit smaller than the target's absorption
     * would have its flat adders eaten too, where 1.18.2 put them straight onto health.
     *
     * <p>Nothing is written when no buff fired, so a stack with no Rankine bonus leaves the damage
     * sequence — and any other mod's reduction modifiers — untouched.
     */
    private static final class LegacyDamage {
        private final LivingDamageEvent.Pre event;
        /** What absorption is about to consume, computed the 1.18.2 way. */
        private final float absorbed;
        private final float original;
        private float amount;

        LegacyDamage(LivingDamageEvent.Pre event) {
            this.event = event;
            float preAbsorption = event.getNewDamage();
            this.amount = Math.max(preAbsorption - event.getEntity().getAbsorptionAmount(), 0.0F);
            this.absorbed = preAbsorption - this.amount;
            this.original = this.amount;
        }

        float get() {
            return this.amount;
        }

        void set(float value) {
            this.amount = value;
        }

        void commit() {
            if (this.amount == this.original) {
                return;
            }
            this.event.setNewDamage(this.amount + this.absorbed);
            this.event.getContainer().addModifier(DamageContainer.Reduction.ABSORPTION,
                    (container, reduction) -> this.absorbed);
        }
    }
}
