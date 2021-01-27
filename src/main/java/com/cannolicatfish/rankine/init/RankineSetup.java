package com.cannolicatfish.rankine.init;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class RankineSetup {

    public ItemGroup rankineWorld = new ItemGroup("rankine_world") {
        @Override
        public ItemStack createIcon() { return new ItemStack(RankineBlocks.REFRACTORY_BRICKS.get()); }
    };

    public ItemGroup rankineMetals = new ItemGroup("rankine_metallurgy") {
        @Override
        public ItemStack createIcon() { return new ItemStack(RankineItems.NIOBIUM_INGOT.get()); }
    };

    public ItemGroup rankineTools = new ItemGroup("rankine_misc") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(RankineItems.STEEL_SPEAR.get());
        }
    };
}
