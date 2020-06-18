package com.cannolicatfish.rankine.init;

import net.minecraft.block.ComposterBlock;
import net.minecraft.util.IItemProvider;

public class RankineCompostables {
    public static void compostableThings() {

        registerCompostable(0.3F, ModBlocks.CEDAR_LEAVES);
        registerCompostable(0.3F, ModBlocks.PINYON_PINE_LEAVES);
        registerCompostable(0.3F, ModBlocks.COCONUT_PALM_LEAVES);
        registerCompostable(0.3F, ModBlocks.JUNIPER_LEAVES);
        registerCompostable(0.3F, ModBlocks.BALSAM_FIR_LEAVES);
        registerCompostable(0.3F, ModBlocks.MAGNOLIA_LEAVES);
        registerCompostable(0.3F, ModBlocks.CEDAR_SAPLING);
        registerCompostable(0.3F, ModBlocks.PINYON_PINE_SAPLING);
        registerCompostable(0.3F, ModBlocks.COCONUT_PALM_SAPLING);
        registerCompostable(0.3F, ModBlocks.JUNIPER_SAPLING);
        registerCompostable(0.3F, ModBlocks.BALSAM_FIR_SAPLING);
        registerCompostable(0.3F, ModBlocks.MAGNOLIA_SAPLING);
    }

    public static void registerCompostable(float chance, IItemProvider itemIn) {
        ComposterBlock.CHANCES.put(itemIn.asItem(), chance);
    }

    /*
            strippableBlock(ModBlocks.CEDAR_LOG, ModBlocks.STRIPPED_CEDAR_LOG);
        strippableBlock(ModBlocks.CEDAR_WOOD, ModBlocks.STRIPPED_CEDAR_WOOD);
        strippableBlock(ModBlocks.PINYON_PINE_LOG, ModBlocks.STRIPPED_PINYON_PINE_LOG);
        strippableBlock(ModBlocks.PINYON_PINE_WOOD, ModBlocks.STRIPPED_PINYON_PINE_WOOD);
        strippableBlock(ModBlocks.BALSAM_FIR_LOG, ModBlocks.STRIPPED_BALSAM_FIR_LOG);
        strippableBlock(ModBlocks.BALSAM_FIR_WOOD, ModBlocks.STRIPPED_BALSAM_FIR_WOOD);
        strippableBlock(ModBlocks.COCONUT_PALM_LOG, ModBlocks.STRIPPED_COCONUT_PALM_LOG);
        strippableBlock(ModBlocks.COCONUT_PALM_WOOD, ModBlocks.STRIPPED_COCONUT_PALM_WOOD);
        strippableBlock(ModBlocks.MAGNOLIA_LOG, ModBlocks.STRIPPED_MAGNOLIA_LOG);
        strippableBlock(ModBlocks.MAGNOLIA_WOOD, ModBlocks.STRIPPED_MAGNOLIA_WOOD);
        strippableBlock(ModBlocks.JUNIPER_LOG, ModBlocks.STRIPPED_JUNIPER_LOG);
        strippableBlock(ModBlocks.JUNIPER_WOOD, ModBlocks.STRIPPED_JUNIPER_WOOD);
     */

}
