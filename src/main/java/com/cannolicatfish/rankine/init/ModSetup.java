package com.cannolicatfish.rankine.init;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class ModSetup {

    public ItemGroup rankineBlocks = new ItemGroup("rankine_blocks") {
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

    public void init() {
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandlers());
    }

}
