package com.cannolicatfish.rankine.enchantment;

import com.cannolicatfish.rankine.init.RankineEnchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class RankineEnchantmentHelper {
    public static int getLiftEnchantment(ItemStack stackIn) {
        return EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.FULCRUM.get(), stackIn);
    }
    public static int getTorqueEnchantment(ItemStack stackIn) {
        return EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.TORQUE.get(), stackIn);
    }
    public static int getLeverageEnchantment(ItemStack stackIn) {
        return EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.LEVERAGE.get(), stackIn);
    }
    public static int getLightningAspectEnchantment(ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.LIGHTNING_ASPECT.get(), stack);
    }
    public static int getDazeEnchantment(ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.DAZE.get(), stack);
    }
    public static int getExcavateEnchantment(ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.EXCAVATE.get(), stack);
    }
    public static int getAtomizeEnchantment(ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ATOMIZE.get(), stack);
    }
    public static boolean hasSpeedSkater(LivingEntity entityIn) {
        return EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.SPEED_SKATER.get(), entityIn) > 0;
    }
    public static boolean hasDuneWalker(LivingEntity entityIn) {
        return EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.DUNE_WALKER.get(), entityIn) > 0;
    }
    public static boolean hasSnowDrifter(LivingEntity entityIn) {
        return EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.SNOW_DRIFTER.get(), entityIn) > 0;
    }
    public static boolean hasSwiftSwimmer(LivingEntity entityIn) {
        return EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.SWIFT_SWIMMER.get(), entityIn) > 0;
    }
    public static boolean hasAquaLense(LivingEntity entityIn) {
        return EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.AQUA_LENSE.get(), entityIn) > 0;
    }


}
