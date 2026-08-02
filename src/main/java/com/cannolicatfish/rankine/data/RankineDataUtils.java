package com.cannolicatfish.rankine.data;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

/**
 * Datagen-only helpers. {@code IForgeRegistryEntry#getRegistryName()} was removed in 1.20+, so the
 * hundreds of datagen call sites that needed a block/item's registry id go through these instead.
 */
public final class RankineDataUtils {
    private RankineDataUtils() {}

    public static ResourceLocation rl(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    public static ResourceLocation rl(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }

    public static ResourceLocation rl(Fluid fluid) {
        return BuiltInRegistries.FLUID.getKey(fluid);
    }

    public static ResourceLocation rl(ItemLike itemLike) {
        return BuiltInRegistries.ITEM.getKey(itemLike.asItem());
    }

    /** Registry path only — the most common use ({@code RankineDataUtils.rl(x).getPath()}). */
    public static String path(Block block) {
        return rl(block).getPath();
    }

    public static String path(Item item) {
        return rl(item).getPath();
    }
}
