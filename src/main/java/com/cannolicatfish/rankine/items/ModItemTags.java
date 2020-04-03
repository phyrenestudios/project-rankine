package com.cannolicatfish.rankine.items;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class ModItemTags {
    public static final Tag<Item> BULLETS = makeWrapperTag("rankine:bullets");

    private static Tag<Item> makeWrapperTag(String p_199901_0_) {
        return new ItemTags.Wrapper(new ResourceLocation(p_199901_0_));
    }
}
