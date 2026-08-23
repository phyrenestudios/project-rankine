package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.items.alloys.AlloyArmorItem;
import com.cannolicatfish.rankine.items.tools.KnifeItem;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * 1.21.1 event mapping: LivingHurtEvent -> LivingIncomingDamageEvent.
 * getEntityLiving() -> getEntity(); DamageSource identity comparisons -> DamageSource#is(ResourceKey);
 * isBypassArmor()/isProjectile() -> DamageTypeTags checks;
 * hurtAndBreak + broadcastBreakEvent collapsed into hurtAndBreak(amount, entity, slot).
 *
 * <p><b>The two events are NOT at the same point in the sequence.</b> Forge fired LivingHurtEvent
 * from inside {@code LivingEntity#actuallyHurt}, i.e. AFTER {@code hurt()} had run the shield
 * block, the freeze/helmet multipliers, the {@code invulnerableTime > 10 && amount <= lastHurt}
 * cooldown gate, and after it had set {@code invulnerableTime = 20} and {@code hurtTime = 10}.
 * NeoForge fires LivingIncomingDamageEvent from {@code hurt()} itself
 * (LivingEntity.java:1125, {@code CommonHooks#onEntityIncomingDamage}) — before all of it. So the
 * naive mapping of {@code setCanceled(true)} onto this event is not the 1.18.2 behaviour: it
 * returns {@code false} out of {@code hurt()} immediately, which costs the parried player the
 * 20-tick invulnerability window, the hurt animation, the knockback and the hurt sound that 1.18.2
 * still gave them, and — because the cooldown gate has not run yet — re-enters the parry branch for
 * every hit 1.18.2 threw away before the handler was ever invoked (a crowd of mobs then eats knife
 * durability once per mob per tick).
 *
 * <p>1.18.2 is reproduced instead by {@link #nullifyDamage}: set the amount to zero and let
 * {@code hurt()} run to completion, which is exactly what cancelling used to leave behind. Two
 * consequences of the old ordering are restored explicitly:
 * <ul>
 *   <li>{@link #insideDamageCooldown} gates the parry's own side effects (sound, RETALIATE,
 *       RETREAT, ENDGAME, knife durability), because 1.18.2 never reached them for a hit the
 *       cooldown gate discarded;</li>
 *   <li>the container is marked, because cancelling in 1.18.2 also returned out of
 *       {@code actuallyHurt} before {@code LivingDamageEvent} — so {@link LivingDamageHandler}'s
 *       two {@code Pre} listeners must skip a nullified hit (see {@link #wasNullified}).</li>
 * </ul>
 * {@code LivingDamageEvent.Post} deliberately still fires: 1.18.2's {@code hurt()} returned true
 * for a cancelled hit, so {@code Player#attack} went on to run the post-attack enchantment
 * behaviours that now live in {@code LivingDamageHandler#onPostDamage}.
 *
 * <p>Known accepted delta: RETALIATE reflects {@code event.getAmount()}, which on this event is the
 * pre-shield, pre-invulnerability figure; 1.18.2 saw the post-shield, post-{@code lastHurt} one.
 * A knife in the offhand rules out a shield, so the two differ only for a follow-up hit taken
 * inside an existing invulnerability window.
 */
public class LivingHurtHandler {

    /**
     * 1.21.1 removed EquipmentSlot.byTypeAndIndex — this reproduces the old
     * {@code byTypeAndIndex(Type.ARMOR, i)} mapping over Inventory#armor (0=feet .. 3=head).
     */
    static EquipmentSlot armorSlot(int index) {
        return switch (index) {
            case 1 -> EquipmentSlot.LEGS;
            case 2 -> EquipmentSlot.CHEST;
            case 3 -> EquipmentSlot.HEAD;
            default -> EquipmentSlot.FEET;
        };
    }

    /**
     * Damage sequences a parry or an alloy-armor immunity has zeroed.
     *
     * <p>Identity-keyed and weak: a {@link DamageContainer} lives for exactly one
     * {@code LivingEntity#hurt} call and is only ever created on the server thread
     * (LivingEntity.java:1117 returns before the push on the client), but nested hurts do happen —
     * RETALIATE reflects damage from inside this handler — so a flag field would be clobbered
     * where a set is not.
     */
    private static final Set<DamageContainer> NULLIFIED =
            Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap<>()));

    /**
     * 1.18.2's {@code event.setCanceled(true)}: no damage, but the rest of {@code hurt()} still
     * runs, so the entity keeps its i-frames, its hurt animation, its knockback and its hurt sound.
     */
    static void nullifyDamage(LivingIncomingDamageEvent event) {
        event.setAmount(0.0F);
        NULLIFIED.add(event.getContainer());
    }

    /** Whether this damage sequence was zeroed by a parry or an immunity. */
    public static boolean wasNullified(DamageContainer container) {
        return NULLIFIED.contains(container);
    }

    /**
     * Whether {@code hurt()} is about to throw this hit away at the damage-cooldown gate
     * (LivingEntity.java:1162). 1.18.2's handler ran downstream of that gate and so was never
     * invoked for such a hit.
     *
     * <p>Only the {@code invulnerableTime} half is checked: the gate's second condition is
     * {@code amount <= this.lastHurt}, and {@code lastHurt} is protected with no accessor. The
     * residual difference is a follow-up hit BIGGER than the one that opened the window, for which
     * 1.18.2 would still have run the parry's side effects.
     */
    private static boolean insideDamageCooldown(LivingEntity entity, DamageSource source) {
        return entity.invulnerableTime > 10 && !source.is(DamageTypeTags.BYPASSES_COOLDOWN);
    }

    public static void onParryEvent(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            Level level = player.level();
            ItemStack stack = player.getOffhandItem().getItem() instanceof KnifeItem ? player.getOffhandItem() : ItemStack.EMPTY;
            if (!stack.isEmpty()) {
                int i = stack.getUseDuration(player) - player.getUseItemRemainingTicks();
                if (i < (10 + RankineEnchantmentHelper.getLevel(level, RankineEnchantments.PREPARATION, stack))) {


                    if (!event.getSource().is(DamageTypeTags.BYPASSES_ARMOR)) {
                        // 1.18.2 ran this handler from inside actuallyHurt, so a hit the damage
                        // cooldown discarded never reached the sound, the reflect or the durability
                        // hit. The zeroing below is NOT gated: skipping it would let the follow-up
                        // hit through at full damage, since a parried hit leaves lastHurt at 0.
                        if (!insideDamageCooldown(player, event.getSource())) {
                            player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP,1.0f, 1.0f);
                            if (event.getSource().getEntity() instanceof LivingEntity ent && RankineEnchantmentHelper.getLevel(level, RankineEnchantments.RETALIATE, stack) >= 1) {
                                ent.hurt(event.getSource(),event.getAmount());
                            } else if (RankineEnchantmentHelper.getLevel(level, RankineEnchantments.RETREAT, stack) >= 1) {
                                player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY,60));
                            }
                            if (RankineEnchantmentHelper.getLevel(level, RankineEnchantments.ENDGAME, stack) > 0) {
                                List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, new AABB(player.blockPosition()).inflate(5, 5, 5), (e) -> (e instanceof Mob || e instanceof Player) && !e.equals(player));
                                for (LivingEntity entity : list) {
                                    ItemStack offhand = player.getOffhandItem();
                                    ItemStack mainhand = player.getMainHandItem();
                                    player.setItemSlot(EquipmentSlot.MAINHAND,offhand);
                                    player.setItemSlot(EquipmentSlot.OFFHAND,mainhand);
                                    player.attack(entity);
                                }
                                double d0 = player.getX();
                                double d1 = player.getY();
                                double d2 = player.getZ();

                                for(int j = 0; j < 16; ++j) {
                                    double d3 = player.getX() + (player.getRandom().nextDouble() - 0.5D) * 16.0D;
                                    double d4 = Mth.clamp(player.getY() + (double)(player.getRandom().nextInt(16) - 8), 0.0D, (double)(level.getHeight() - 1));
                                    double d5 = player.getZ() + (player.getRandom().nextDouble() - 0.5D) * 16.0D;
                                    if (player.isPassenger()) {
                                        player.stopRiding();
                                    }

                                    if (player.randomTeleport(d3, d4, d5, true)) {
                                        SoundEvent soundevent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                                        level.playSound((Player)null, d0, d1, d2, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                                        player.playSound(soundevent, 1.0F, 1.0F);
                                        break;
                                    }
                                }
                            }
                            stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                        }

                        nullifyDamage(event);
                    }
                }
            }
        }
    }
    public static void onLivingHurt(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            Level worldIn = player.getCommandSenderWorld();
            for (int i = 0; i < player.getInventory().armor.size(); ++i) {
                ItemStack s = player.getInventory().armor.get(i);
                int curSlot = i;

                if (event.getSource().is(DamageTypeTags.IS_PROJECTILE) && RankineEnchantmentHelper.getLevel(worldIn, RankineEnchantments.ENDOBIOTIC, s) > 0) {
                    if (!worldIn.isClientSide) {
                        double d0 = player.getX();
                        double d1 = player.getY();
                        double d2 = player.getZ();
                        if (s.getItem() instanceof AlloyArmorItem armor) {
                            if ((worldIn.getRandom().nextFloat() > armor.getCorrResist(s) && player.isInWaterOrRain())) {

                                s.hurtAndBreak(1 + RankineEnchantmentHelper.getLevel(worldIn, RankineEnchantments.ENDOBIOTIC, s)*3, player,
                                        armorSlot(curSlot));
                            }
                        }

                        for(int j = 0; j < 16; ++j) {
                            double d3 = player.getX() + (player.getRandom().nextDouble() - 0.5D) * 16.0D;
                            double d4 = Mth.clamp(player.getY() + (double)(player.getRandom().nextInt(16) - 8), 0.0D, (double)(worldIn.getHeight() - 1));
                            double d5 = player.getZ() + (player.getRandom().nextDouble() - 0.5D) * 16.0D;
                            if (player.isPassenger()) {
                                player.stopRiding();
                            }

                            if (player.randomTeleport(d3, d4, d5, true)) {
                                SoundEvent soundevent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                                worldIn.playSound((Player)null, d0, d1, d2, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                                player.playSound(soundevent, 1.0F, 1.0F);
                                break;
                            }
                        }
                        nullifyDamage(event);
                        return;
                    }
                }

                if (s.getItem() instanceof AlloyArmorItem armor) {
                    EquipmentSlot slot = armorSlot(curSlot);
                    if (slot.equals(EquipmentSlot.FEET)) {
                        if (event.getSource().is(DamageTypes.HOT_FLOOR) && armor.getHeatResist(s) >= 1) {
                            nullifyDamage(event);
                        } else if (event.getSource().is(DamageTypes.STALAGMITE) && armor.getToughness(s) >= 0.4f) {
                            nullifyDamage(event);
                        }
                    } else if (slot.equals(EquipmentSlot.LEGS) && event.getSource().is(DamageTypes.SWEET_BERRY_BUSH) && armor.getToughness(s) >= 0.2f) {
                        nullifyDamage(event);
                    } else if (slot.equals(EquipmentSlot.CHEST) && event.getSource().is(DamageTypes.CACTUS) && armor.getToughness(s) >= 0.2f) {
                        nullifyDamage(event);
                    } else if (slot.equals(EquipmentSlot.HEAD) && (event.getSource().is(DamageTypes.FALLING_STALACTITE) || event.getSource().is(DamageTypes.FALLING_BLOCK)) && armor.getToughness(s) >= 0.4f) {
                        nullifyDamage(event);
                    }

                }
            }
        }
    }
}
