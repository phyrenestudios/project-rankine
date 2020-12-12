package com.cannolicatfish.rankine.init;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class ModSetup {

    public ItemGroup rankineWorld = new ItemGroup("rankine_world") {
        @Override
        public ItemStack createIcon() { return new ItemStack(ModBlocks.REFRACTORY_BRICKS); }
    };

    public ItemGroup rankineMetals = new ItemGroup("rankine_metallurgy") {
        @Override
        public ItemStack createIcon() { return new ItemStack(ModItems.NIOBIUM_INGOT); }
    };

    public ItemGroup rankineTools = new ItemGroup("rankine_misc") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.STEEL_SPEAR);
        }
    };
}
