package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.events.RankineEventHandler;
import net.minecraft.block.*;
import net.minecraft.util.IItemProvider;

public class VanillaIntegration {

    public static void init() {
        registerCompostable(0.3F, ModBlocks.CEDAR_LEAVES);
        registerCompostable(0.3F, ModBlocks.PINYON_PINE_LEAVES);
        registerCompostable(0.3F, ModBlocks.COCONUT_PALM_LEAVES);
        registerCompostable(0.3F, ModBlocks.JUNIPER_LEAVES);
        registerCompostable(0.3F, ModBlocks.BALSAM_FIR_LEAVES);
        registerCompostable(0.3F, ModBlocks.MAGNOLIA_LEAVES);
        registerCompostable(0.3F, ModBlocks.EASTERN_HEMLOCK_LEAVES);
        registerCompostable(0.3F, ModBlocks.CEDAR_SAPLING);
        registerCompostable(0.3F, ModBlocks.PINYON_PINE_SAPLING);
        registerCompostable(0.3F, ModBlocks.COCONUT_PALM_SAPLING);
        registerCompostable(0.3F, ModBlocks.JUNIPER_SAPLING);
        registerCompostable(0.3F, ModBlocks.BALSAM_FIR_SAPLING);
        registerCompostable(0.3F, ModBlocks.MAGNOLIA_SAPLING);
        registerCompostable(0.3F, ModBlocks.EASTERN_HEMLOCK_SAPLING);
        registerCompostable(0.3F, ModBlocks.YELLOW_BIRCH_SAPLING);
        registerCompostable(0.3F, ModBlocks.DUCKWEED);


        RankineEventHandler.stripping_map.put(ModBlocks.CEDAR_LOG, ModBlocks.STRIPPED_CEDAR_LOG);
        RankineEventHandler.stripping_map.put(ModBlocks.CEDAR_WOOD, ModBlocks.STRIPPED_CEDAR_WOOD);
        RankineEventHandler.stripping_map.put(ModBlocks.PINYON_PINE_LOG, ModBlocks.STRIPPED_PINYON_PINE_LOG);
        RankineEventHandler.stripping_map.put(ModBlocks.PINYON_PINE_WOOD, ModBlocks.STRIPPED_PINYON_PINE_WOOD);
        RankineEventHandler.stripping_map.put(ModBlocks.BALSAM_FIR_LOG, ModBlocks.STRIPPED_BALSAM_FIR_LOG);
        RankineEventHandler.stripping_map.put(ModBlocks.BALSAM_FIR_WOOD, ModBlocks.STRIPPED_BALSAM_FIR_WOOD);
        RankineEventHandler.stripping_map.put(ModBlocks.COCONUT_PALM_LOG, ModBlocks.STRIPPED_COCONUT_PALM_LOG);
        RankineEventHandler.stripping_map.put(ModBlocks.COCONUT_PALM_WOOD, ModBlocks.STRIPPED_COCONUT_PALM_WOOD);
        RankineEventHandler.stripping_map.put(ModBlocks.MAGNOLIA_LOG, ModBlocks.STRIPPED_MAGNOLIA_LOG);
        RankineEventHandler.stripping_map.put(ModBlocks.MAGNOLIA_WOOD, ModBlocks.STRIPPED_MAGNOLIA_WOOD);
        RankineEventHandler.stripping_map.put(ModBlocks.JUNIPER_LOG, ModBlocks.STRIPPED_JUNIPER_LOG);
        RankineEventHandler.stripping_map.put(ModBlocks.JUNIPER_WOOD, ModBlocks.STRIPPED_JUNIPER_WOOD);
        RankineEventHandler.stripping_map.put(ModBlocks.YELLOW_BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
        RankineEventHandler.stripping_map.put(ModBlocks.YELLOW_BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD);
        RankineEventHandler.stripping_map.put(ModBlocks.EASTERN_HEMLOCK_LOG, ModBlocks.STRIPPED_EASTERN_HEMLOCK_LOG);
        RankineEventHandler.stripping_map.put(ModBlocks.EASTERN_HEMLOCK_WOOD, ModBlocks.STRIPPED_EASTERN_HEMLOCK_WOOD);


        addFlowerPot(ModBlocks.CEDAR_SAPLING, ModBlocks.POTTED_CEDAR_SAPLING);
        addFlowerPot(ModBlocks.PINYON_PINE_SAPLING, ModBlocks.POTTED_PINYON_PINE_SAPLING);
        addFlowerPot(ModBlocks.COCONUT_PALM_SAPLING, ModBlocks.POTTED_COCONUT_PALM_SAPLING);
        addFlowerPot(ModBlocks.JUNIPER_SAPLING, ModBlocks.POTTED_JUNIPER_SAPLING);
        addFlowerPot(ModBlocks.BALSAM_FIR_SAPLING, ModBlocks.POTTED_BALSAM_FIR_SAPLING);
        addFlowerPot(ModBlocks.MAGNOLIA_SAPLING, ModBlocks.POTTED_MAGNOLIA_SAPLING);
        addFlowerPot(ModBlocks.EASTERN_HEMLOCK_SAPLING, ModBlocks.POTTED_EASTERN_HEMLOCK_SAPLING);
        addFlowerPot(ModBlocks.YELLOW_BIRCH_SAPLING, ModBlocks.POTTED_YELLOW_BIRCH_SAPLING);

/*
        registerFlamables(ModBlocks.CEDAR_LEAVES,30,60);
        registerFlamables(ModBlocks.EASTERN_HEMLOCK_LEAVES,30,60);
        registerFlamables(ModBlocks.BALSAM_FIR_LEAVES,30,60);
        registerFlamables(ModBlocks.COCONUT_PALM_LEAVES,30,60);
        registerFlamables(ModBlocks.MAGNOLIA_LEAVES,30,60);
        registerFlamables(ModBlocks.JUNIPER_LEAVES,30,60);
        registerFlamables(ModBlocks.PINYON_PINE_LEAVES,30,60);

        
        registerFlamables(ModBlocks.CEDAR_BUTTON,5,20);
        registerFlamables(ModBlocks.CEDAR_DOOR,5,20);
        registerFlamables(ModBlocks.CEDAR_FENCE,5,20);
        registerFlamables(ModBlocks.CEDAR_FENCE_GATE,5,20);
        registerFlamables(ModBlocks.CEDAR_LOG,5,20);
        registerFlamables(ModBlocks.CEDAR_PLANKS,5,20);
        registerFlamables(ModBlocks.CEDAR_PRESSURE_PLATE,5,20);
        registerFlamables(ModBlocks.CEDAR_SLAB,5,20);
        registerFlamables(ModBlocks.CEDAR_STAIRS,5,20);
        registerFlamables(ModBlocks.CEDAR_TRAPDOOR,5,20);
        registerFlamables(ModBlocks.CEDAR_WOOD,5,20);
        registerFlamables(ModBlocks.STRIPPED_CEDAR_LOG,5,20);
        registerFlamables(ModBlocks.STRIPPED_CEDAR_WOOD,5,20);

        registerFlamables(ModBlocks.BALSAM_FIR_BUTTON,5,20);
        registerFlamables(ModBlocks.BALSAM_FIR_DOOR,5,20);
        registerFlamables(ModBlocks.BALSAM_FIR_FENCE,5,20);
        registerFlamables(ModBlocks.BALSAM_FIR_FENCE_GATE,5,20);
        registerFlamables(ModBlocks.BALSAM_FIR_LOG,5,20);
        registerFlamables(ModBlocks.BALSAM_FIR_PLANKS,5,20);
        registerFlamables(ModBlocks.BALSAM_FIR_PRESSURE_PLATE,5,20);
        registerFlamables(ModBlocks.BALSAM_FIR_SLAB,5,20);
        registerFlamables(ModBlocks.BALSAM_FIR_STAIRS,5,20);
        registerFlamables(ModBlocks.BALSAM_FIR_TRAPDOOR,5,20);
        registerFlamables(ModBlocks.BALSAM_FIR_WOOD,5,20);
        registerFlamables(ModBlocks.STRIPPED_BALSAM_FIR_LOG,5,20);
        registerFlamables(ModBlocks.STRIPPED_BALSAM_FIR_WOOD,5,20);

        registerFlamables(ModBlocks.COCONUT_PALM_BUTTON,5,20);
        registerFlamables(ModBlocks.COCONUT_PALM_DOOR,5,20);
        registerFlamables(ModBlocks.COCONUT_PALM_FENCE,5,20);
        registerFlamables(ModBlocks.COCONUT_PALM_FENCE_GATE,5,20);
        registerFlamables(ModBlocks.COCONUT_PALM_LOG,5,20);
        registerFlamables(ModBlocks.COCONUT_PALM_PLANKS,5,20);
        registerFlamables(ModBlocks.COCONUT_PALM_PRESSURE_PLATE,5,20);
        registerFlamables(ModBlocks.COCONUT_PALM_SLAB,5,20);
        registerFlamables(ModBlocks.COCONUT_PALM_STAIRS,5,20);
        registerFlamables(ModBlocks.COCONUT_PALM_TRAPDOOR,5,20);
        registerFlamables(ModBlocks.COCONUT_PALM_WOOD,5,20);
        registerFlamables(ModBlocks.STRIPPED_COCONUT_PALM_LOG,5,20);
        registerFlamables(ModBlocks.STRIPPED_COCONUT_PALM_WOOD,5,20);

        registerFlamables(ModBlocks.JUNIPER_BUTTON,5,20);
        registerFlamables(ModBlocks.JUNIPER_DOOR,5,20);
        registerFlamables(ModBlocks.JUNIPER_FENCE,5,20);
        registerFlamables(ModBlocks.JUNIPER_FENCE_GATE,5,20);
        registerFlamables(ModBlocks.JUNIPER_LOG,5,20);
        registerFlamables(ModBlocks.JUNIPER_PLANKS,5,20);
        registerFlamables(ModBlocks.JUNIPER_PRESSURE_PLATE,5,20);
        registerFlamables(ModBlocks.JUNIPER_SLAB,5,20);
        registerFlamables(ModBlocks.JUNIPER_STAIRS,5,20);
        registerFlamables(ModBlocks.JUNIPER_TRAPDOOR,5,20);
        registerFlamables(ModBlocks.JUNIPER_WOOD,5,20);
        registerFlamables(ModBlocks.STRIPPED_JUNIPER_LOG,5,20);
        registerFlamables(ModBlocks.STRIPPED_JUNIPER_WOOD,5,20);

        registerFlamables(ModBlocks.PINYON_PINE_BUTTON,5,20);
        registerFlamables(ModBlocks.PINYON_PINE_DOOR,5,20);
        registerFlamables(ModBlocks.PINYON_PINE_FENCE,5,20);
        registerFlamables(ModBlocks.PINYON_PINE_FENCE_GATE,5,20);
        registerFlamables(ModBlocks.PINYON_PINE_LOG,5,20);
        registerFlamables(ModBlocks.PINYON_PINE_PLANKS,5,20);
        registerFlamables(ModBlocks.PINYON_PINE_PRESSURE_PLATE,5,20);
        registerFlamables(ModBlocks.PINYON_PINE_SLAB,5,20);
        registerFlamables(ModBlocks.PINYON_PINE_STAIRS,5,20);
        registerFlamables(ModBlocks.PINYON_PINE_TRAPDOOR,5,20);
        registerFlamables(ModBlocks.PINYON_PINE_WOOD,5,20);
        registerFlamables(ModBlocks.STRIPPED_PINYON_PINE_LOG,5,20);
        registerFlamables(ModBlocks.STRIPPED_PINYON_PINE_WOOD,5,20);

        registerFlamables(ModBlocks.MAGNOLIA_BUTTON,5,20);
        registerFlamables(ModBlocks.MAGNOLIA_DOOR,5,20);
        registerFlamables(ModBlocks.MAGNOLIA_FENCE,5,20);
        registerFlamables(ModBlocks.MAGNOLIA_FENCE_GATE,5,20);
        registerFlamables(ModBlocks.MAGNOLIA_LOG,5,20);
        registerFlamables(ModBlocks.MAGNOLIA_PLANKS,5,20);
        registerFlamables(ModBlocks.MAGNOLIA_PRESSURE_PLATE,5,20);
        registerFlamables(ModBlocks.MAGNOLIA_SLAB,5,20);
        registerFlamables(ModBlocks.MAGNOLIA_STAIRS,5,20);
        registerFlamables(ModBlocks.MAGNOLIA_TRAPDOOR,5,20);
        registerFlamables(ModBlocks.MAGNOLIA_WOOD,5,20);
        registerFlamables(ModBlocks.STRIPPED_MAGNOLIA_LOG,5,20);
        registerFlamables(ModBlocks.STRIPPED_MAGNOLIA_WOOD,5,20);

        registerFlamables(ModBlocks.EASTERN_HEMLOCK_BUTTON,5,20);
        registerFlamables(ModBlocks.EASTERN_HEMLOCK_DOOR,5,20);
        registerFlamables(ModBlocks.EASTERN_HEMLOCK_FENCE,5,20);
        registerFlamables(ModBlocks.EASTERN_HEMLOCK_FENCE_GATE,5,20);
        registerFlamables(ModBlocks.EASTERN_HEMLOCK_LOG,5,20);
        registerFlamables(ModBlocks.EASTERN_HEMLOCK_PLANKS,5,20);
        registerFlamables(ModBlocks.EASTERN_HEMLOCK_PRESSURE_PLATE,5,20);
        registerFlamables(ModBlocks.EASTERN_HEMLOCK_SLAB,5,20);
        registerFlamables(ModBlocks.EASTERN_HEMLOCK_STAIRS,5,20);
        registerFlamables(ModBlocks.EASTERN_HEMLOCK_TRAPDOOR,5,20);
        registerFlamables(ModBlocks.EASTERN_HEMLOCK_WOOD,5,20);
        registerFlamables(ModBlocks.STRIPPED_EASTERN_HEMLOCK_LOG,5,20);
        registerFlamables(ModBlocks.STRIPPED_EASTERN_HEMLOCK_WOOD,5,20);

        registerFlamables(ModBlocks.YELLOW_BIRCH_LOG,5,20);
        registerFlamables(ModBlocks.YELLOW_BIRCH_WOOD,5,20);

        registerFlamables(ModBlocks.BAMBOO_BUTTON,5,20);
        registerFlamables(ModBlocks.BAMBOO_DOOR,5,20);
        registerFlamables(ModBlocks.BAMBOO_FENCE,5,20);
        registerFlamables(ModBlocks.BAMBOO_FENCE_GATE,5,20);
        registerFlamables(ModBlocks.BAMBOO_PLANKS,5,20);
        registerFlamables(ModBlocks.BAMBOO_PRESSURE_PLATE,5,20);
        registerFlamables(ModBlocks.BAMBOO_SLAB,5,20);
        registerFlamables(ModBlocks.BAMBOO_STAIRS,5,20);
        registerFlamables(ModBlocks.BAMBOO_WALL,5,20);

        registerFlamables(ModBlocks.BAMBOO_CULMS_BUTTON,5,20);
        registerFlamables(ModBlocks.BAMBOO_CULMS_FENCE,5,20);
        registerFlamables(ModBlocks.BAMBOO_CULMS_FENCE_GATE,5,20);
        registerFlamables(ModBlocks.BAMBOO_CULMS_PRESSURE_PLATE,5,20);
        registerFlamables(ModBlocks.BAMBOO_CULMS_SLAB,5,20);
        registerFlamables(ModBlocks.BAMBOO_CULMS_STAIRS,5,20);
        registerFlamables(ModBlocks.BAMBOO_CULMS_WALL,5,20);

*/





    }









    public static void registerCompostable(float chance, IItemProvider itemIn) {
        ComposterBlock.CHANCES.put(itemIn.asItem(), chance);
    }

    public static void addFlowerPot(Block plant, Block plantPot) {
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(plant.getRegistryName(), () -> plantPot);
    }

    public static void registerFlamables(Block block, int encouragement, int flammability) {
       // ((FireBlock)Blocks.FIRE).setFireInfo(block, encouragement, flammability);
    }

}
