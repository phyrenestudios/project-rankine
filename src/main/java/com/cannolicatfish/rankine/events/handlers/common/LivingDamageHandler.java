package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.items.alloys.AlloyArmorItem;
import com.cannolicatfish.rankine.items.alloys.AlloySwordItem;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.List;

public class LivingDamageHandler {
    public static void onLivingDamaged(LivingDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Level worldIn = player.getCommandSenderWorld();
            for (int i = 0; i < player.getInventory().armor.size(); ++i) {
                ItemStack s = player.getInventory().armor.get(i);
                int curSlot = i;
                if (s.getItem() instanceof AlloyArmorItem armor) {

                    if (worldIn.getRandom().nextFloat() > armor.getHeatResist(s) && (player.isInLava() || player.getRemainingFireTicks() > 0 || worldIn.dimension() == Level.NETHER)) {
                        s.hurtAndBreak(1,player,(entity) -> {
                            entity.broadcastBreakEvent(EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, curSlot));
                        });
                    } else if ((worldIn.getRandom().nextFloat() > armor.getCorrResist(s) && player.isInWaterOrRain())) {
                        s.hurtAndBreak(1 + EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOBIOTIC.get(),s)*3,player,(entity) -> {
                            entity.broadcastBreakEvent(EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, curSlot));
                        });
                    }
                }

            }
            if (!(event.getSource() == DamageSource.WITHER && event.getSource() == DamageSource.MAGIC)) {
                boolean wither = false;
                for(int i = 0; i < player.getInventory().getContainerSize(); ++i) {
                    ItemStack itemstack = player.getInventory().getItem(i);
                    if (!itemstack.isEmpty() && EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.WITHERING_CURSE.get(), itemstack) > 0) {
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

            if (!player.level.isClientSide) {
                LivingEntity receiver = event.getEntity();
                ItemStack mainHandStack = player.getItemInHand(InteractionHand.MAIN_HAND);

                if (mainHandStack.getItem() instanceof HammerItem) {
                    if ((receiver instanceof Blaze || receiver instanceof AbstractGolem || receiver instanceof AbstractSkeleton || receiver instanceof SkeletonHorse || receiver instanceof Guardian)) {
                        int endLevel = EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDEAVOR.get(),mainHandStack);
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
                if ((player.getMainHandItem().getItem().equals(RankineItems.TOTEM_OF_BLAZING.get()) || player.getOffhandItem().getItem().equals(RankineItems.TOTEM_OF_BLAZING.get()))) {
                    float damage = event.getAmount() + event.getAmount() * 0.5f;
                    event.setAmount(damage);
                }
                if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOTOXIN.get(),mainHandStack) >= 1) {
                    if ((receiver instanceof EnderMan || receiver instanceof Shulker || receiver instanceof Endermite || receiver.getCommandSenderWorld().dimension().equals(Level.END))) {
                        event.setAmount(event.getAmount() + 2.5f*EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDOTOXIN.get(),mainHandStack));
                    }
                }

                if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.CLEANSE.get(),mainHandStack) >= 1) {
                    float damage = EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.CLEANSE.get(),mainHandStack) * receiver.getActiveEffects().size();
                    event.setAmount(event.getAmount() + damage);
                    boolean flag = damage >= 1;
                    if (flag) {
                        receiver.removeAllEffects();
                        receiver.playSound(SoundEvents.GRINDSTONE_USE,1.0f, 1.0f);
                    }
                }

                if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.BACKSTAB.get(), mainHandStack) >= 1) {
                    if (receiver.getDirection().equals(player.getDirection())) {
                        receiver.playSound(SoundEvents.TRIDENT_HIT,1.0f, 1.0f);
                        float damage = event.getAmount() + event.getAmount() * EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.BACKSTAB.get(), mainHandStack);
                        event.setAmount(damage);
                    }
                }

                if (RankineEnchantmentHelper.getTorqueEnchantment(mainHandStack) > 0) {

                    float size = receiver.getDimensions(receiver.getPose()).height * receiver.getDimensions(receiver.getPose()).width;
                    int lvl = RankineEnchantmentHelper.getTorqueEnchantment(mainHandStack);
                    //System.out.println(size);
                    float mod = -1.5f + lvl;
                    float damage = event.getAmount() + Math.max(0,Math.min(size + mod, 1.5f *lvl));
                    //System.out.println("damageOut: " + damage);
                    event.setAmount(damage);
                }

                if (mainHandStack.getItem() instanceof AlloySwordItem swordItem) {
                    float effectChance = 1 - 1/swordItem.getAlloyMiningSpeed(mainHandStack);
                }
            }




            for (ItemStack armor : event.getEntity().getArmorSlots()) {
                if (armor.getItem() instanceof AlloyArmorItem) {
                    EquipmentSlot slot = armor.getEquipmentSlot() != null ? armor.getEquipmentSlot() : EquipmentSlot.HEAD;
                    int i = ((AlloyArmorItem) armor.getItem()).calcDurabilityLoss(armor,event.getEntity().getCommandSenderWorld(),event.getEntity(),true);
                    armor.hurtAndBreak(i,player, (p_220287_1_) -> {
                        p_220287_1_.broadcastBreakEvent(slot);
                    });
                }
            }
        }

    }
}
