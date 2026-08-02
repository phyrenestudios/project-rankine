package com.cannolicatfish.rankine.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * Common ("c") tags that Rankine's datagen references but that NeoForge's {@code Tags} class no
 * longer declares in 1.21.x. Paths follow the current common-tag convention.
 */
public final class RankineDataTags {
    private RankineDataTags() {}

    public static final TagKey<Item> STORAGE_BLOCKS_QUARTZ = itemTag("storage_blocks/quartz");

    private static TagKey<Item> itemTag(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", path));
    }
}
