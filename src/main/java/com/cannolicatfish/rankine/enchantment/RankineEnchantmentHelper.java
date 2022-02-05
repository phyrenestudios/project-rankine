package com.cannolicatfish.rankine.enchantment;

import com.cannolicatfish.rankine.init.RankineEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;

public class RankineEnchantmentHelper {

    public static boolean hasAquaAffinity(LivingEntity entityIn) {
        return EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.AQUA_AFFINITY, entityIn) > 0;
    }

    public static boolean hasSpeedSkater(LivingEntity entityIn) {
        return EnchantmentHelper.getMaxEnchantmentLevel(RankineEnchantments.SPEED_SKATER, entityIn) > 0;
    }

    public static boolean hasDuneWalker(LivingEntity entityIn) {
        return EnchantmentHelper.getMaxEnchantmentLevel(RankineEnchantments.DUNE_WALKER, entityIn) > 0;
    }

    public static boolean hasSnowDrifter(LivingEntity entityIn) {
        return EnchantmentHelper.getMaxEnchantmentLevel(RankineEnchantments.SNOW_DRIFTER, entityIn) > 0;
    }

    public static boolean hasFlippers(LivingEntity entityIn) {
        return EnchantmentHelper.getMaxEnchantmentLevel(RankineEnchantments.FLIPPERS, entityIn) > 0;
    }


}
