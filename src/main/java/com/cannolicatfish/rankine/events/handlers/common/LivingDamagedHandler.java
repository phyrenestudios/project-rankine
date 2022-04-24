package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.items.alloys.AlloyArmorItem;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import com.cannolicatfish.rankine.items.tools.KnifeItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.List;

public class LivingDamagedHandler {
    public static void onLivingDamaged( LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            World worldIn = player.getEntityWorld();
            for (int i = 0; i < player.inventory.armorInventory.size(); ++i) {
                ItemStack s = player.inventory.armorInventory.get(i);
                if (!event.getSource().isProjectile() || EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.ENDOBIOTIC, s) == 0) {
                    if (s.getItem() instanceof AlloyArmorItem) {
                        AlloyArmorItem armor = (AlloyArmorItem) s.getItem();
                        if (worldIn.getRandom().nextFloat() > armor.getHeatResist(s) && (player.isInLava() || player.getFireTimer() > 0 || worldIn.getDimensionKey() == World.THE_NETHER)) {
                            int finalI = i;
                            s.damageItem(1,player,(entity) -> {
                                entity.sendBreakAnimation(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, finalI));
                            });
                        } else if ((worldIn.getRandom().nextFloat() > armor.getCorrResist(s) && player.isWet())) {
                            int finalI1 = i;
                            s.damageItem(1 + EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.ENDOBIOTIC,s)*3,player,(entity) -> {
                                entity.sendBreakAnimation(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, finalI1));
                            });
                        }
                    }
                } else if (event.getSource().isProjectile() && EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.ENDOBIOTIC,s) > 0) {
                    if (!worldIn.isRemote) {
                        double d0 = player.getPosX();
                        double d1 = player.getPosY();
                        double d2 = player.getPosZ();
                        if (s.getItem() instanceof AlloyArmorItem) {
                            AlloyArmorItem armor = (AlloyArmorItem) s.getItem();
                            if ((worldIn.getRandom().nextFloat() > armor.getCorrResist(s) && player.isWet())) {
                                int finalI1 = i;
                                s.damageItem(1 + EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.ENDOBIOTIC,s)*3,player,(entity) -> {
                                    entity.sendBreakAnimation(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, finalI1));
                                });
                            }
                        }

                        for(int j = 0; j < 16; ++j) {
                            double d3 = player.getPosX() + (player.getRNG().nextDouble() - 0.5D) * 16.0D;
                            double d4 = MathHelper.clamp(player.getPosY() + (double)(player.getRNG().nextInt(16) - 8), 0.0D, (double)(worldIn.func_234938_ad_() - 1));
                            double d5 = player.getPosZ() + (player.getRNG().nextDouble() - 0.5D) * 16.0D;
                            if (player.isPassenger()) {
                                player.stopRiding();
                            }

                            if (player.attemptTeleport(d3, d4, d5, true)) {
                                SoundEvent soundevent = SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                                worldIn.playSound((PlayerEntity)null, d0, d1, d2, soundevent, SoundCategory.PLAYERS, 1.0F, 1.0F);
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
                for(int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                    ItemStack itemstack = player.inventory.getStackInSlot(i);
                    if (!itemstack.isEmpty() && EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.WITHERING_CURSE, itemstack) > 0) {
                        wither = true;
                        break;
                    }
                }
                if (wither) {
                    player.addPotionEffect(new EffectInstance(Effects.WITHER, 100));
                }
            }
        }
    }

    public static void onParryEvent(LivingDamageEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            ItemStack stack = player.getHeldItemOffhand().getItem() instanceof KnifeItem ? player.getHeldItemOffhand() : ItemStack.EMPTY;
            if (!stack.isEmpty()) {
                int i = stack.getItem().getUseDuration(stack) - player.getItemInUseCount();
                if (i < (10 + EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.PREPARATION,stack))) {


                    if (!event.getSource().isUnblockable()) {
                        player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP,1.0f, 1.0f);
                        if (event.getSource().getTrueSource() instanceof LivingEntity && EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.RETALIATE, stack) >= 1) {
                            LivingEntity ent = (LivingEntity) event.getSource().getTrueSource();
                            ent.attackEntityFrom(event.getSource(),event.getAmount());
                        } else if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.RETREAT,stack) >= 1) {
                            player.addPotionEffect(new EffectInstance(Effects.INVISIBILITY,60));
                        }
                        if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.ENDGAME,stack) > 0) {
                            List<LivingEntity> list = player.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(player.getPosition()).grow(5, 5, 5), ( e) -> (e instanceof MobEntity || e instanceof PlayerEntity) && !e.equals(player));
                            for (LivingEntity entity : list) {
                                ItemStack offhand = player.getHeldItemOffhand();
                                ItemStack mainhand = player.getHeldItemMainhand();
                                player.setItemStackToSlot(EquipmentSlotType.MAINHAND,offhand);
                                player.setItemStackToSlot(EquipmentSlotType.OFFHAND,mainhand);
                                player.attackTargetEntityWithCurrentItem(entity);
                            }
                            double d0 = player.getPosX();
                            double d1 = player.getPosY();
                            double d2 = player.getPosZ();

                            for(int j = 0; j < 16; ++j) {
                                double d3 = player.getPosX() + (player.getRNG().nextDouble() - 0.5D) * 16.0D;
                                double d4 = MathHelper.clamp(player.getPosY() + (double)(player.getRNG().nextInt(16) - 8), 0.0D, (double)(player.world.func_234938_ad_() - 1));
                                double d5 = player.getPosZ() + (player.getRNG().nextDouble() - 0.5D) * 16.0D;
                                if (player.isPassenger()) {
                                    player.stopRiding();
                                }

                                if (player.attemptTeleport(d3, d4, d5, true)) {
                                    SoundEvent soundevent = SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                                    player.world.playSound((PlayerEntity)null, d0, d1, d2, soundevent, SoundCategory.PLAYERS, 1.0F, 1.0F);
                                    player.playSound(soundevent, 1.0F, 1.0F);
                                    break;
                                }
                            }
                        }
                        stack.damageItem(1, player, (p_220045_0_) -> {
                            p_220045_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                        });
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    public static void onDamageEntity(LivingDamageEvent event) {
        if (event.getSource().getTrueSource() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
            ForgeConfigSpec.BooleanValue configSpec = VanillaIntegration.DISABLED_ITEMS.get(player.getHeldItem(Hand.MAIN_HAND).getItem());
            if (configSpec != null && configSpec.get()) {
                event.setAmount(1.0f);
            }
            if (player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof HammerItem && !player.world.isRemote) {
                LivingEntity receiver = event.getEntityLiving();
                if ((receiver instanceof BlazeEntity || receiver instanceof GolemEntity || receiver instanceof AbstractSkeletonEntity || receiver instanceof GuardianEntity )) {
                    int endLevel = EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.ENDEAVOR,player.getHeldItem(Hand.MAIN_HAND));
                    event.setAmount(event.getAmount() + event.getAmount()/2f + 1.5f*endLevel);
                    if (endLevel > 0 && player.world.getRandom().nextFloat() < (0.15f*endLevel) && receiver.world.getServer() != null && player.world instanceof ServerWorld) {
                        LootTable loot = receiver.world.getServer().getLootTableManager().getLootTableFromLocation(receiver.getLootTableResourceLocation());
                        LootContext n = new LootContext.Builder((ServerWorld) player.world)
                                .withParameter(LootParameters.THIS_ENTITY, receiver)
                                .withParameter(LootParameters.ORIGIN, receiver.getPositionVec())
                                .withParameter(LootParameters.DAMAGE_SOURCE, DamageSource.causePlayerDamage(player))
                                .withParameter(LootParameters.LAST_DAMAGE_PLAYER,player).build(LootParameterSets.ENTITY);
                        List<ItemStack> s = loot.generate(n);
                        if (s.size() > 1) {
                            receiver.entityDropItem(s.get(receiver.world.getRandom().nextInt(s.size())));
                        } else if (s.size() == 1) {
                            receiver.entityDropItem(s.get(0));
                        }
                    }
                }
            }

            if ((player.getHeldItemMainhand().getItem().equals(RankineItems.TOTEM_OF_BLAZING.get()) || player.getHeldItemOffhand().getItem().equals(RankineItems.TOTEM_OF_BLAZING.get())) && !player.world.isRemote) {
                float damage = event.getAmount() + event.getAmount() * 0.5f;
                event.setAmount(damage);
            }
            if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.ENDOTOXIN,player.getHeldItem(Hand.MAIN_HAND)) >= 1 && !player.world.isRemote) {
                LivingEntity receiver = event.getEntityLiving();
                if ((receiver instanceof EndermanEntity || receiver instanceof ShulkerEntity || receiver instanceof EndermiteEntity || receiver.getEntityWorld().getDimensionKey().equals(World.THE_END))) {
                    event.setAmount(event.getAmount() + 2.5f*EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.ENDOTOXIN,player.getHeldItem(Hand.MAIN_HAND)));
                }
            }

            if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.CLEANSE,player.getHeldItem(Hand.MAIN_HAND)) >= 1 && !player.world.isRemote) {
                LivingEntity receiver = event.getEntityLiving();
                float damage = EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.CLEANSE,player.getHeldItem(Hand.MAIN_HAND)) * receiver.getActivePotionEffects().size();
                event.setAmount(event.getAmount() + damage);
                boolean flag = damage >= 1;
                if (flag) {
                    receiver.clearActivePotions();
                    receiver.playSound(SoundEvents.BLOCK_GRINDSTONE_USE,1.0f, 1.0f);
                }
            }

            if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.BACKSTAB,player.getHeldItem(Hand.MAIN_HAND)) >= 1 && !player.world.isRemote) {
                ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);

                LivingEntity receiver = event.getEntityLiving();
                if (receiver.getHorizontalFacing().equals(player.getHorizontalFacing())) {
                    receiver.playSound(SoundEvents.ITEM_TRIDENT_HIT,1.0f, 1.0f);
                    float damage = event.getAmount() + event.getAmount() * EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.BACKSTAB,stack);
                    event.setAmount(damage);
                }
            }

            if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.LEVERAGE,player.getHeldItem(Hand.MAIN_HAND)) >= 1 && !player.world.isRemote) {
                ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);

                LivingEntity receiver = event.getEntityLiving();
                float size = receiver.getSize(receiver.getPose()).height * receiver.getSize(receiver.getPose()).width;
                int lvl = EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.LEVERAGE,stack);
                //System.out.println(size);
                float mod = -2 + lvl;
                float damage = event.getAmount() + Math.max(0,Math.min(size + mod,1.5f*EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.LEVERAGE,stack)));
                //System.out.println("damageOut: " + damage);
                event.setAmount(damage);
            }

            for (ItemStack armor : event.getEntityLiving().getArmorInventoryList()) {
                if (armor.getItem() instanceof AlloyArmorItem) {
                    EquipmentSlotType slot = armor.getEquipmentSlot() != null ? armor.getEquipmentSlot() : EquipmentSlotType.HEAD;
                    int i = ((AlloyArmorItem) armor.getItem()).calcDurabilityLoss(armor,event.getEntity().getEntityWorld(),event.getEntityLiving(),true);
                    armor.damageItem(i,player, (p_220287_1_) -> {
                        p_220287_1_.sendBreakAnimation(slot);
                    });
                }
            }
        }

    }
}
