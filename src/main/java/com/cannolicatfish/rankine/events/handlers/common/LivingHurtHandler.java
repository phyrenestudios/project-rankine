package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.items.alloys.AlloyArmorItem;
import com.cannolicatfish.rankine.items.tools.KnifeItem;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;

public class LivingHurtHandler {
    public static void onParryEvent(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            ItemStack stack = player.getOffhandItem().getItem() instanceof KnifeItem ? player.getOffhandItem() : ItemStack.EMPTY;
            if (!stack.isEmpty()) {
                int i = stack.getItem().getUseDuration(stack) - player.getUseItemRemainingTicks();
                if (i < (10 + EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.PREPARATION.get(),stack))) {


                    if (!event.getSource().isBypassArmor()) {
                        player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP,1.0f, 1.0f);
                        if (event.getSource().getEntity() instanceof LivingEntity && EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.RETALIATE.get(),stack) >= 1) {
                            LivingEntity ent = (LivingEntity) event.getSource().getEntity();
                            ent.hurt(event.getSource(),event.getAmount());
                        } else if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.RETREAT.get(),stack) >= 1) {
                            player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY,60));
                        }
                        if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDGAME.get(),stack) > 0) {
                            List<LivingEntity> list = player.level.getEntitiesOfClass(LivingEntity.class, new AABB(player.blockPosition()).inflate(5, 5, 5), (e) -> (e instanceof Mob || e instanceof Player) && !e.equals(player));
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
                                double d4 = Mth.clamp(player.getY() + (double)(player.getRandom().nextInt(16) - 8), 0.0D, (double)(player.level.getHeight() - 1));
                                double d5 = player.getZ() + (player.getRandom().nextDouble() - 0.5D) * 16.0D;
                                if (player.isPassenger()) {
                                    player.stopRiding();
                                }

                                if (player.randomTeleport(d3, d4, d5, true)) {
                                    SoundEvent soundevent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                                    player.level.playSound((Player)null, d0, d1, d2, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                                    player.playSound(soundevent, 1.0F, 1.0F);
                                    break;
                                }
                            }
                        }
                        stack.hurtAndBreak(1, player, (p_220045_0_) -> {
                            p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                        });

                        event.setCanceled(true);
                    }
                }
            }
        }
    }
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Level worldIn = player.getCommandSenderWorld();
            for (int i = 0; i < player.getInventory().armor.size(); ++i) {
                ItemStack s = player.getInventory().armor.get(i);
                int curSlot = i;

                if (event.getSource().isProjectile() && EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOBIOTIC.get(),s) > 0) {
                    if (!worldIn.isClientSide) {
                        double d0 = player.getX();
                        double d1 = player.getY();
                        double d2 = player.getZ();
                        if (s.getItem() instanceof AlloyArmorItem armor) {
                            if ((worldIn.getRandom().nextFloat() > armor.getCorrResist(s) && player.isInWaterOrRain())) {

                                s.hurtAndBreak(1 + EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOBIOTIC.get(),s)*3,player,(entity) -> {
                                    entity.broadcastBreakEvent(EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, curSlot));
                                });
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
                    if (EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, curSlot).equals(EquipmentSlot.FEET)) {
                        if (event.getSource().equals(DamageSource.HOT_FLOOR) && armor.getHeatResist(s) >= 1) {
                            event.setCanceled(true);
                        } else if (event.getSource().equals(DamageSource.STALAGMITE) && armor.getToughness(s) >= 0.4f) {
                            event.setCanceled(true);
                        }
                    } else if (EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, curSlot).equals(EquipmentSlot.LEGS) && event.getSource().equals(DamageSource.SWEET_BERRY_BUSH) && armor.getToughness(s) >= 0.2f) {
                        event.setCanceled(true);
                    } else if (EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, curSlot).equals(EquipmentSlot.CHEST) && event.getSource().equals(DamageSource.CACTUS) && armor.getToughness(s) >= 0.2f) {
                        event.setCanceled(true);
                    } else if (EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, curSlot).equals(EquipmentSlot.HEAD) && (event.getSource().equals(DamageSource.FALLING_STALACTITE) || event.getSource().equals(DamageSource.FALLING_BLOCK)) && armor.getToughness(s) >= 0.4f) {
                        event.setCanceled(true);
                    }

                }
            }
        }
    }
}
