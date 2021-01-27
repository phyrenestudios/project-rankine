package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.events.RankineEventHandler;
import net.minecraft.block.*;
import net.minecraft.util.IItemProvider;

public class VanillaIntegration {

    public static void init() {
        registerCompostable(0.3F, RankineBlocks.CEDAR_LEAVES.get());
        registerCompostable(0.3F, RankineBlocks.PINYON_PINE_LEAVES.get());
        registerCompostable(0.3F, RankineBlocks.COCONUT_PALM_LEAVES.get());
        registerCompostable(0.3F, RankineBlocks.JUNIPER_LEAVES.get());
        registerCompostable(0.3F, RankineBlocks.BALSAM_FIR_LEAVES.get());
        registerCompostable(0.3F, RankineBlocks.MAGNOLIA_LEAVES.get());
        registerCompostable(0.3F, RankineBlocks.EASTERN_HEMLOCK_LEAVES.get());
        registerCompostable(0.3F, RankineBlocks.BLACK_BIRCH_LEAVES.get());
        registerCompostable(0.3F, RankineBlocks.MAPLE_LEAVES.get());
        registerCompostable(0.3F, RankineBlocks.YELLOW_BIRCH_LEAVES.get());
        registerCompostable(0.3F, RankineBlocks.CEDAR_SAPLING.get());
        registerCompostable(0.3F, RankineBlocks.PINYON_PINE_SAPLING.get());
        registerCompostable(0.3F, RankineBlocks.COCONUT_PALM_SAPLING.get());
        registerCompostable(0.3F, RankineBlocks.JUNIPER_SAPLING.get());
        registerCompostable(0.3F, RankineBlocks.BALSAM_FIR_SAPLING.get());
        registerCompostable(0.3F, RankineBlocks.MAGNOLIA_SAPLING.get());
        registerCompostable(0.3F, RankineBlocks.EASTERN_HEMLOCK_SAPLING.get());
        registerCompostable(0.3F, RankineBlocks.YELLOW_BIRCH_SAPLING.get());
        registerCompostable(0.3F, RankineBlocks.BLACK_BIRCH_SAPLING.get());
        registerCompostable(0.3F, RankineBlocks.MAPLE_SAPLING.get());
        registerCompostable(0.5F, RankineItems.COMPOST.get());



        RankineEventHandler.stripping_map.put(RankineBlocks.CEDAR_LOG.get(), RankineBlocks.STRIPPED_CEDAR_LOG.get());
        RankineEventHandler.stripping_map.put(RankineBlocks.CEDAR_WOOD.get(), RankineBlocks.STRIPPED_CEDAR_WOOD.get());
        RankineEventHandler.stripping_map.put(RankineBlocks.PINYON_PINE_LOG.get(), RankineBlocks.STRIPPED_PINYON_PINE_LOG.get());
        RankineEventHandler.stripping_map.put(RankineBlocks.PINYON_PINE_WOOD.get(), RankineBlocks.STRIPPED_PINYON_PINE_WOOD.get());
        RankineEventHandler.stripping_map.put(RankineBlocks.BALSAM_FIR_LOG.get(), RankineBlocks.STRIPPED_BALSAM_FIR_LOG.get());
        RankineEventHandler.stripping_map.put(RankineBlocks.BALSAM_FIR_WOOD.get(), RankineBlocks.STRIPPED_BALSAM_FIR_WOOD.get());
        RankineEventHandler.stripping_map.put(RankineBlocks.COCONUT_PALM_LOG.get(), RankineBlocks.STRIPPED_COCONUT_PALM_LOG.get());
        RankineEventHandler.stripping_map.put(RankineBlocks.COCONUT_PALM_WOOD.get(), RankineBlocks.STRIPPED_COCONUT_PALM_WOOD.get());
        RankineEventHandler.stripping_map.put(RankineBlocks.MAGNOLIA_LOG.get(), RankineBlocks.STRIPPED_MAGNOLIA_LOG.get());
        RankineEventHandler.stripping_map.put(RankineBlocks.MAGNOLIA_WOOD.get(), RankineBlocks.STRIPPED_MAGNOLIA_WOOD.get());
        RankineEventHandler.stripping_map.put(RankineBlocks.JUNIPER_LOG.get(), RankineBlocks.STRIPPED_JUNIPER_LOG.get());
        RankineEventHandler.stripping_map.put(RankineBlocks.JUNIPER_WOOD.get(), RankineBlocks.STRIPPED_JUNIPER_WOOD.get());
        RankineEventHandler.stripping_map.put(RankineBlocks.EASTERN_HEMLOCK_LOG.get(), RankineBlocks.STRIPPED_EASTERN_HEMLOCK_LOG.get());
        RankineEventHandler.stripping_map.put(RankineBlocks.EASTERN_HEMLOCK_WOOD.get(), RankineBlocks.STRIPPED_EASTERN_HEMLOCK_WOOD.get());
        RankineEventHandler.stripping_map.put(RankineBlocks.YELLOW_BIRCH_LOG.get(), Blocks.STRIPPED_BIRCH_LOG);
        RankineEventHandler.stripping_map.put(RankineBlocks.YELLOW_BIRCH_WOOD.get(), Blocks.STRIPPED_BIRCH_WOOD);
        RankineEventHandler.stripping_map.put(RankineBlocks.BLACK_BIRCH_LOG.get(), Blocks.STRIPPED_BIRCH_LOG);
        RankineEventHandler.stripping_map.put(RankineBlocks.BLACK_BIRCH_WOOD.get(), Blocks.STRIPPED_BIRCH_WOOD);
        RankineEventHandler.stripping_map.put(RankineBlocks.MAPLE_LOG.get(), RankineBlocks.STRIPPED_MAPLE_LOG.get());
        RankineEventHandler.stripping_map.put(RankineBlocks.MAPLE_WOOD.get(), RankineBlocks.STRIPPED_MAPLE_WOOD.get());

        addFlowerPot(RankineBlocks.CEDAR_SAPLING.get(), RankineBlocks.POTTED_CEDAR_SAPLING.get());
        addFlowerPot(RankineBlocks.PINYON_PINE_SAPLING.get(), RankineBlocks.POTTED_PINYON_PINE_SAPLING.get());
        addFlowerPot(RankineBlocks.COCONUT_PALM_SAPLING.get(), RankineBlocks.POTTED_COCONUT_PALM_SAPLING.get());
        addFlowerPot(RankineBlocks.JUNIPER_SAPLING.get(), RankineBlocks.POTTED_JUNIPER_SAPLING.get());
        addFlowerPot(RankineBlocks.BALSAM_FIR_SAPLING.get(), RankineBlocks.POTTED_BALSAM_FIR_SAPLING.get());
        addFlowerPot(RankineBlocks.MAGNOLIA_SAPLING.get(), RankineBlocks.POTTED_MAGNOLIA_SAPLING.get());
        addFlowerPot(RankineBlocks.EASTERN_HEMLOCK_SAPLING.get(), RankineBlocks.POTTED_EASTERN_HEMLOCK_SAPLING.get());
        addFlowerPot(RankineBlocks.YELLOW_BIRCH_SAPLING.get(), RankineBlocks.POTTED_YELLOW_BIRCH_SAPLING.get());
        addFlowerPot(RankineBlocks.BLACK_BIRCH_SAPLING.get(), RankineBlocks.POTTED_BLACK_BIRCH_SAPLING.get());
        addFlowerPot(RankineBlocks.MAPLE_SAPLING.get(), RankineBlocks.POTTED_MAPLE_SAPLING.get());

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
