package com.cannolicatfish.rankine.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class RankineSetup {

    public CreativeModeTab rankineWorld = new CreativeModeTab("rankine_world") {
        @Override
        public ItemStack makeIcon() { return new ItemStack(RankineBlocks.REFRACTORY_BRICKS.get()); }
    };
    public CreativeModeTab rankinePlants = new CreativeModeTab("rankine_plants") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RankineItems.ELDERBERRIES.get());
        }
    };
    public CreativeModeTab rankineMetals = new CreativeModeTab("rankine_metallurgy") {
        @Override
        public ItemStack makeIcon() { return new ItemStack(RankineItems.CINNABAR_ORE.get()); }
    };
    public CreativeModeTab rankineTools = new CreativeModeTab("rankine_misc") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RankineItems.STEEL_SPEAR.get());
        }
    };
    public CreativeModeTab rankineElements = new CreativeModeTab("rankine_elements") {
        @Override
        public ItemStack makeIcon() { return new ItemStack(RankineItems.NIOBIUM_INGOT.get()); }
    };
    public CreativeModeTab rankineComponents = new CreativeModeTab("rankine_components") {
        @Override
        public ItemStack makeIcon() { return new ItemStack(RankineItems.ALLOY_GEAR.get()); }
    };
}
