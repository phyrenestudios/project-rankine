package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.items.alloys.AlloyArmorItem;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import com.cannolicatfish.rankine.items.tools.KnifeItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.List;

public class LivingDamageHandler {
    public static void onParryEvent(LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof Player) {
            Player player = (Player) event.getEntityLiving();
            ItemStack stack = player.getOffhandItem().getItem() instanceof KnifeItem ? player.getOffhandItem() : ItemStack.EMPTY;
            if (!stack.isEmpty()) {
                int i = stack.getItem().getUseDuration(stack) - player.getUseItemRemainingTicks();
                if (i < (10 + EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.PREPARATION,stack))) {


                    if (!event.getSource().isBypassArmor()) {
                        player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP,1.0f, 1.0f);
                        if (event.getSource().getEntity() instanceof LivingEntity && EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.RETALIATE,stack) >= 1) {
                            LivingEntity ent = (LivingEntity) event.getSource().getEntity();
                            ent.hurt(event.getSource(),event.getAmount());
                        } else if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.RETREAT,stack) >= 1) {
                            player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY,60));
                        }
                        if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDGAME,stack) > 0) {
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
    public static void onLivingDamaged(LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof Player) {
            Player player = (Player) event.getEntityLiving();
            Level worldIn = player.getCommandSenderWorld();
            for (int i = 0; i < player.getInventory().armor.size(); ++i) {
                ItemStack s = player.getInventory().armor.get(i);
                if (!event.getSource().isProjectile() || EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOBIOTIC,s) == 0) {
                    if (s.getItem() instanceof AlloyArmorItem) {
                        AlloyArmorItem armor = (AlloyArmorItem) s.getItem();
                        if (worldIn.getRandom().nextFloat() > armor.getHeatResist(s) && (player.isInLava() || player.getRemainingFireTicks() > 0 || worldIn.dimension() == Level.NETHER)) {
                            int finalI = i;
                            s.hurtAndBreak(1,player,(entity) -> {
                                entity.broadcastBreakEvent(EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, finalI));
                            });
                        } else if ((worldIn.getRandom().nextFloat() > armor.getCorrResist(s) && player.isInWaterOrRain())) {
                            int finalI1 = i;
                            s.hurtAndBreak(1 + EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOBIOTIC,s)*3,player,(entity) -> {
                                entity.broadcastBreakEvent(EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, finalI1));
                            });
                        }
                    }
                } else if (event.getSource().isProjectile() && EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOBIOTIC,s) > 0) {
                    if (!worldIn.isClientSide) {
                        double d0 = player.getX();
                        double d1 = player.getY();
                        double d2 = player.getZ();
                        if (s.getItem() instanceof AlloyArmorItem) {
                            AlloyArmorItem armor = (AlloyArmorItem) s.getItem();
                            if ((worldIn.getRandom().nextFloat() > armor.getCorrResist(s) && player.isInWaterOrRain())) {
                                int finalI1 = i;
                                s.hurtAndBreak(1 + EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOBIOTIC,s)*3,player,(entity) -> {
                                    entity.broadcastBreakEvent(EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, finalI1));
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
                        event.setAmount(0);
                    }
                }

            }
            if (!(event.getSource() == DamageSource.WITHER && event.getSource() == DamageSource.MAGIC)) {
                boolean wither = false;
                for(int i = 0; i < player.getInventory().getContainerSize(); ++i) {
                    ItemStack itemstack = player.getInventory().getItem(i);
                    if (!itemstack.isEmpty() && EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.WITHERING_CURSE, itemstack) > 0) {
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
    public static void onDamageEntity(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ForgeConfigSpec.BooleanValue configSpec = VanillaIntegration.DISABLED_ITEMS.get(player.getMainHandItem().getItem());
            if (configSpec != null && configSpec.get()) {
                event.setAmount(1.0f);
            }

            if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof HammerItem && !player.level.isClientSide) {
                LivingEntity receiver = event.getEntityLiving();
                if ((receiver instanceof Blaze || receiver instanceof AbstractGolem || receiver instanceof AbstractSkeleton || receiver instanceof Guardian)) {
                    int endLevel = EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDEAVOR,player.getItemInHand(InteractionHand.MAIN_HAND));
                    event.setAmount(event.getAmount() + event.getAmount()/2f + 1.5f*endLevel);
                    if (endLevel > 0 && player.level.getRandom().nextFloat() < (0.15f*endLevel) && receiver.level.getServer() != null && player.level instanceof ServerLevel) {
                        LootTable loot = receiver.level.getServer().getLootTables().get(receiver.getLootTable());
                        LootContext n = new LootContext.Builder((ServerLevel) player.level)
                                .withParameter(LootContextParams.THIS_ENTITY, receiver)
                                .withParameter(LootContextParams.ORIGIN, receiver.position())
                                .withParameter(LootContextParams.DAMAGE_SOURCE, DamageSource.playerAttack(player))
                                .withParameter(LootContextParams.LAST_DAMAGE_PLAYER,player).create(LootContextParamSets.ENTITY);
                        List<ItemStack> s = loot.getRandomItems(n);
                        if (s.size() > 1) {
                            receiver.spawnAtLocation(s.get(receiver.level.getRandom().nextInt(s.size())));
                        } else if (s.size() == 1) {
                            receiver.spawnAtLocation(s.get(0));
                        }
                    }
                }
            }

            if ((player.getMainHandItem().getItem().equals(RankineItems.TOTEM_OF_BLAZING.get()) || player.getOffhandItem().getItem().equals(RankineItems.TOTEM_OF_BLAZING.get())) && !player.level.isClientSide) {
                float damage = event.getAmount() + event.getAmount() * 0.5f;
                event.setAmount(damage);
            }
            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOTOXIN,player.getItemInHand(InteractionHand.MAIN_HAND)) >= 1 && !player.level.isClientSide) {
                LivingEntity receiver = event.getEntityLiving();
                if ((receiver instanceof EnderMan || receiver instanceof Shulker || receiver instanceof Endermite || receiver.getCommandSenderWorld().dimension().equals(Level.END))) {
                    event.setAmount(event.getAmount() + 2.5f*EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOTOXIN,player.getItemInHand(InteractionHand.MAIN_HAND)));
                }
            }

            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.CLEANSE,player.getItemInHand(InteractionHand.MAIN_HAND)) >= 1 && !player.level.isClientSide) {
                LivingEntity receiver = event.getEntityLiving();
                float damage = EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.CLEANSE,player.getItemInHand(InteractionHand.MAIN_HAND)) * receiver.getActiveEffects().size();
                event.setAmount(event.getAmount() + damage);
                boolean flag = damage >= 1;
                if (flag) {
                    receiver.removeAllEffects();
                    receiver.playSound(SoundEvents.GRINDSTONE_USE,1.0f, 1.0f);
                }
            }

            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.BACKSTAB,player.getItemInHand(InteractionHand.MAIN_HAND)) >= 1 && !player.level.isClientSide) {
                ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

                LivingEntity receiver = event.getEntityLiving();
                if (receiver.getDirection().equals(player.getDirection())) {
                    receiver.playSound(SoundEvents.TRIDENT_HIT,1.0f, 1.0f);
                    float damage = event.getAmount() + event.getAmount() * EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.BACKSTAB,stack);
                    event.setAmount(damage);
                }
            }

            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.LEVERAGE,player.getItemInHand(InteractionHand.MAIN_HAND)) >= 1 && !player.level.isClientSide) {
                ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

                LivingEntity receiver = event.getEntityLiving();
                float size = receiver.getDimensions(receiver.getPose()).height * receiver.getDimensions(receiver.getPose()).width;
                int lvl = EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.LEVERAGE,stack);
                //System.out.println(size);
                float mod = -2 + lvl;
                float damage = event.getAmount() + Math.max(0,Math.min(size + mod,1.5f*EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.LEVERAGE,stack)));
                //System.out.println("damageOut: " + damage);
                event.setAmount(damage);
            }

            for (ItemStack armor : event.getEntityLiving().getArmorSlots()) {
                if (armor.getItem() instanceof AlloyArmorItem) {
                    EquipmentSlot slot = armor.getEquipmentSlot() != null ? armor.getEquipmentSlot() : EquipmentSlot.HEAD;
                    int i = ((AlloyArmorItem) armor.getItem()).calcDurabilityLoss(armor,event.getEntity().getCommandSenderWorld(),event.getEntityLiving(),true);
                    armor.hurtAndBreak(i,player, (p_220287_1_) -> {
                        p_220287_1_.broadcastBreakEvent(slot);
                    });
                }
            }
        }

    }
}
