package com.cannolicatfish.rankine.enchantment;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

/**
 * 1.21 enchantments are registry entries; level lookups need a Holder resolved from the
 * level's registry access. Use these helpers everywhere instead of touching
 * {@link EnchantmentHelper} directly with raw keys.
 */
public class RankineEnchantmentHelper {

    /** Resolves the Holder for a Rankine (or vanilla) enchantment key. Throws if the registry entry is missing. */
    public static Holder<Enchantment> holder(Level level, ResourceKey<Enchantment> key) {
        return level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(key);
    }

    /** Level of the enchantment on the given stack, or 0 if the enchantment is not registered (e.g. datapack removed it). */
    public static int getLevel(Level level, ResourceKey<Enchantment> key, ItemStack stack) {
        try {
            return EnchantmentHelper.getItemEnchantmentLevel(holder(level, key), stack);
        } catch (Exception e) {
            return 0;
        }
    }

    /** Convenience overload resolving the registry through the entity's level. */
    public static int getLevel(LivingEntity entity, ResourceKey<Enchantment> key, ItemStack stack) {
        try {
            return EnchantmentHelper.getItemEnchantmentLevel(holder(entity.level(), key), stack);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Highest level of the enchantment across the entity's equipment
     * (replaces 1.18 {@code EnchantmentHelper.getEnchantmentLevel(ench, entity)}).
     */
    public static int getEquipmentLevel(LivingEntity entity, ResourceKey<Enchantment> key) {
        try {
            return EnchantmentHelper.getEnchantmentLevel(holder(entity.level(), key), entity);
        } catch (Exception e) {
            return 0;
        }
    }

    /** True if any equipped item has the enchantment (old hasSpeedSkater/hasDuneWalker/... helpers). */
    public static boolean hasEnchantment(LivingEntity entity, ResourceKey<Enchantment> key) {
        return getEquipmentLevel(entity, key) > 0;
    }
}
