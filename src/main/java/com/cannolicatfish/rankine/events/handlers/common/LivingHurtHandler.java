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
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

/**
 * 1.21.1 event mapping: LivingHurtEvent -> LivingIncomingDamageEvent.
 * Both fire pre-armor and both are cancelable, so parry/immunity semantics are preserved exactly.
 * getEntityLiving() -> getEntity(); DamageSource identity comparisons -> DamageSource#is(ResourceKey);
 * isBypassArmor()/isProjectile() -> DamageTypeTags checks;
 * hurtAndBreak + broadcastBreakEvent collapsed into hurtAndBreak(amount, entity, slot).
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

    public static void onParryEvent(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            Level level = player.level();
            ItemStack stack = player.getOffhandItem().getItem() instanceof KnifeItem ? player.getOffhandItem() : ItemStack.EMPTY;
            if (!stack.isEmpty()) {
                int i = stack.getUseDuration(player) - player.getUseItemRemainingTicks();
                if (i < (10 + RankineEnchantmentHelper.getLevel(level, RankineEnchantments.PREPARATION, stack))) {


                    if (!event.getSource().is(DamageTypeTags.BYPASSES_ARMOR)) {
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

                        event.setCanceled(true);
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
                        event.setCanceled(true);
                        return;
                    }
                }

                if (s.getItem() instanceof AlloyArmorItem armor) {
                    EquipmentSlot slot = armorSlot(curSlot);
                    if (slot.equals(EquipmentSlot.FEET)) {
                        if (event.getSource().is(DamageTypes.HOT_FLOOR) && armor.getHeatResist(s) >= 1) {
                            event.setCanceled(true);
                        } else if (event.getSource().is(DamageTypes.STALAGMITE) && armor.getToughness(s) >= 0.4f) {
                            event.setCanceled(true);
                        }
                    } else if (slot.equals(EquipmentSlot.LEGS) && event.getSource().is(DamageTypes.SWEET_BERRY_BUSH) && armor.getToughness(s) >= 0.2f) {
                        event.setCanceled(true);
                    } else if (slot.equals(EquipmentSlot.CHEST) && event.getSource().is(DamageTypes.CACTUS) && armor.getToughness(s) >= 0.2f) {
                        event.setCanceled(true);
                    } else if (slot.equals(EquipmentSlot.HEAD) && (event.getSource().is(DamageTypes.FALLING_STALACTITE) || event.getSource().is(DamageTypes.FALLING_BLOCK)) && armor.getToughness(s) >= 0.4f) {
                        event.setCanceled(true);
                    }

                }
            }
        }
    }
}
