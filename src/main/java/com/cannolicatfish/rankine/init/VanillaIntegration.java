package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.blocks.states.TilledSoilTypes;
import com.cannolicatfish.rankine.blocks.states.TreeTapFluids;
import com.cannolicatfish.rankine.events.RankineEventHandler;
import net.minecraft.block.*;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.IItemProvider;

import java.util.HashMap;
import java.util.Map;

public class VanillaIntegration {

    public static Map<Block, Block> stripping_map = new HashMap<Block, Block>();
    public static Map<Block, Block> pathBlocks_map = new HashMap<Block, Block>();
    public static Map<Block, Block> grass_dirt_map = new HashMap<Block, Block>();
    public static Map<Block, Block> dirt_grass_map = new HashMap<Block, Block>();
    public static Map<Block, TilledSoilTypes> hoeables_map = new HashMap<Block, TilledSoilTypes>();
    public static Map<TreeTapFluids, BlockState> tapFluids_map = new HashMap<TreeTapFluids, BlockState>();

    public static void init() {
        for (Block blk : RankineLists.TALL_FLOWERS) {
            registerCompostable(0.65F, blk);
        }
        for (Block blk : RankineLists.LEAVES) {
            registerCompostable(0.3F, blk);
        }
        for (Block blk : RankineLists.SAPLINGS) {
            registerCompostable(0.3F, blk);
        }
        registerCompostable(0.5F, RankineItems.COMPOST.get());
        registerCompostable(1.0F, RankineItems.PINEAPPLE_SLEEVES.get());
        registerCompostable(0.25F, RankineItems.SHORT_GRASS.get());
        registerCompostable(0.5F, RankineItems.STINGING_NETTLE.get());
        registerCompostable(0.5F, RankineItems.YELLOW_CLOVER.get());
        registerCompostable(0.5F, RankineItems.CRIMSON_CLOVER.get());
        registerCompostable(0.5F, RankineItems.RED_CLOVER.get());
        registerCompostable(0.5F, RankineItems.WHITE_CLOVER.get());

        registerCompostable(0.3F, RankineItems.ELDERBERRIES.get());
        registerCompostable(0.3F, RankineItems.BLUEBERRIES.get());
        registerCompostable(0.3F, RankineItems.STRAWBERRIES.get());
        registerCompostable(0.3F, RankineItems.CRANBERRIES.get());
        registerCompostable(0.3F, RankineItems.POKEBERRIES.get());
        registerCompostable(0.3F, RankineItems.RASPBERRIES.get());
        registerCompostable(0.3F, RankineItems.BLACKBERRIES.get());
        registerCompostable(0.3F, RankineItems.PINEAPPLE.get());
        registerCompostable(0.3F, RankineItems.ALOE.get());
        registerCompostable(0.3F, RankineItems.CAMPHOR_BASIL_LEAF.get());
        registerCompostable(0.3F, RankineItems.CAMPHOR_BASIL_SEEDS.get());
        registerCompostable(0.3F, RankineItems.BANANA_YUCCA.get());
        registerCompostable(0.3F, RankineItems.SNOWBERRIES.get());
        registerCompostable(0.3F, RankineItems.JUNIPER_BERRIES.get());
        registerCompostable(0.3F, RankineItems.BLACK_WALNUT.get());
        registerCompostable(0.3F, RankineItems.COCONUT.get());
        registerCompostable(0.3F, RankineItems.FLOWER_SEEDS.get());

        hoeables_map.put(Blocks.DIRT, TilledSoilTypes.DIRT);
        hoeables_map.put(Blocks.GRASS_BLOCK, TilledSoilTypes.DIRT);
        hoeables_map.put(Blocks.MYCELIUM, TilledSoilTypes.DIRT);
        hoeables_map.put(Blocks.PODZOL, TilledSoilTypes.DIRT);
        hoeables_map.put(Blocks.COARSE_DIRT, TilledSoilTypes.COARSE_DIRT);
        hoeables_map.put(Blocks.SOUL_SOIL, TilledSoilTypes.SOUL_SOIL);
        hoeables_map.put(RankineBlocks.LOAM.get(), TilledSoilTypes.LOAM);
        hoeables_map.put(RankineBlocks.LOAM_PATH.get(), TilledSoilTypes.LOAM);
        hoeables_map.put(RankineBlocks.GRASSY_LOAM.get(), TilledSoilTypes.LOAM);
        hoeables_map.put(RankineBlocks.HUMUS.get(), TilledSoilTypes.HUMUS);
        hoeables_map.put(RankineBlocks.HUMUS_PATH.get(), TilledSoilTypes.HUMUS);
        hoeables_map.put(RankineBlocks.GRASSY_HUMUS.get(), TilledSoilTypes.HUMUS);
        hoeables_map.put(RankineBlocks.CLAY_LOAM.get(), TilledSoilTypes.CLAY_LOAM);
        hoeables_map.put(RankineBlocks.CLAY_LOAM_PATH.get(), TilledSoilTypes.CLAY_LOAM);
        hoeables_map.put(RankineBlocks.GRASSY_CLAY_LOAM.get(), TilledSoilTypes.CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_LOAM.get(), TilledSoilTypes.SANDY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_LOAM_PATH.get(), TilledSoilTypes.SANDY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.GRASSY_SANDY_CLAY_LOAM.get(), TilledSoilTypes.SANDY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_LOAM.get(), TilledSoilTypes.SILTY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_LOAM_PATH.get(), TilledSoilTypes.SILTY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.GRASSY_SILTY_CLAY_LOAM.get(), TilledSoilTypes.SILTY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_LOAM.get(), TilledSoilTypes.SILTY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_LOAM_PATH.get(), TilledSoilTypes.SILTY_LOAM);
        hoeables_map.put(RankineBlocks.GRASSY_SILTY_LOAM.get(), TilledSoilTypes.SILTY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_LOAM.get(), TilledSoilTypes.SANDY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_LOAM_PATH.get(), TilledSoilTypes.SANDY_LOAM);
        hoeables_map.put(RankineBlocks.GRASSY_SANDY_LOAM.get(), TilledSoilTypes.SANDY_LOAM);
        hoeables_map.put(RankineBlocks.LOAMY_SAND.get(), TilledSoilTypes.LOAMY_SAND);
        hoeables_map.put(RankineBlocks.LOAMY_SAND_PATH.get(), TilledSoilTypes.LOAMY_SAND);
        hoeables_map.put(RankineBlocks.GRASSY_LOAMY_SAND.get(), TilledSoilTypes.LOAMY_SAND);
        hoeables_map.put(RankineBlocks.SANDY_CLAY.get(), TilledSoilTypes.SANDY_CLAY);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_PATH.get(), TilledSoilTypes.SANDY_CLAY);
        hoeables_map.put(RankineBlocks.GRASSY_SANDY_CLAY.get(), TilledSoilTypes.SANDY_CLAY);
        hoeables_map.put(RankineBlocks.SILTY_CLAY.get(), TilledSoilTypes.SILTY_CLAY);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_PATH.get(), TilledSoilTypes.SILTY_CLAY);
        hoeables_map.put(RankineBlocks.GRASSY_SILTY_CLAY.get(), TilledSoilTypes.SILTY_CLAY);

        pathBlocks_map.put(Blocks.DIRT, Blocks.GRASS_PATH);
        pathBlocks_map.put(Blocks.GRASS_BLOCK, Blocks.GRASS_PATH);
        pathBlocks_map.put(Blocks.PODZOL, Blocks.GRASS_PATH);
        pathBlocks_map.put(Blocks.COARSE_DIRT, Blocks.GRASS_PATH);
        pathBlocks_map.put(Blocks.MYCELIUM, RankineBlocks.MYCELIUM_PATH.get());
        pathBlocks_map.put(RankineBlocks.HUMUS.get(), RankineBlocks.HUMUS_PATH.get());
        pathBlocks_map.put(RankineBlocks.SILTY_CLAY.get(), RankineBlocks.SILTY_CLAY_PATH.get());
        pathBlocks_map.put(RankineBlocks.SANDY_CLAY.get(), RankineBlocks.SANDY_CLAY_PATH.get());
        pathBlocks_map.put(RankineBlocks.SANDY_LOAM.get(), RankineBlocks.SANDY_LOAM_PATH.get());
        pathBlocks_map.put(RankineBlocks.SILTY_LOAM.get(), RankineBlocks.SILTY_LOAM_PATH.get());
        pathBlocks_map.put(RankineBlocks.SILTY_CLAY_LOAM.get(), RankineBlocks.SILTY_CLAY_LOAM_PATH.get());
        pathBlocks_map.put(RankineBlocks.SANDY_CLAY_LOAM.get(), RankineBlocks.SANDY_CLAY_LOAM_PATH.get());
        pathBlocks_map.put(RankineBlocks.CLAY_LOAM.get(), RankineBlocks.CLAY_LOAM_PATH.get());
        pathBlocks_map.put(RankineBlocks.LOAM.get(), RankineBlocks.LOAM_PATH.get());
        pathBlocks_map.put(RankineBlocks.LOAMY_SAND.get(), RankineBlocks.LOAMY_SAND_PATH.get());
        pathBlocks_map.put(RankineBlocks.GRASSY_HUMUS.get(), RankineBlocks.HUMUS_PATH.get());
        pathBlocks_map.put(RankineBlocks.GRASSY_SILTY_CLAY.get(), RankineBlocks.SILTY_CLAY_PATH.get());
        pathBlocks_map.put(RankineBlocks.GRASSY_SANDY_CLAY.get(), RankineBlocks.SANDY_CLAY_PATH.get());
        pathBlocks_map.put(RankineBlocks.GRASSY_SANDY_LOAM.get(), RankineBlocks.SANDY_LOAM_PATH.get());
        pathBlocks_map.put(RankineBlocks.GRASSY_SILTY_LOAM.get(), RankineBlocks.SILTY_LOAM_PATH.get());
        pathBlocks_map.put(RankineBlocks.GRASSY_SILTY_CLAY_LOAM.get(), RankineBlocks.SILTY_CLAY_LOAM_PATH.get());
        pathBlocks_map.put(RankineBlocks.GRASSY_SANDY_CLAY_LOAM.get(), RankineBlocks.SANDY_CLAY_LOAM_PATH.get());
        pathBlocks_map.put(RankineBlocks.GRASSY_CLAY_LOAM.get(), RankineBlocks.CLAY_LOAM_PATH.get());
        pathBlocks_map.put(RankineBlocks.GRASSY_LOAM.get(), RankineBlocks.LOAM_PATH.get());
        pathBlocks_map.put(RankineBlocks.GRASSY_LOAMY_SAND.get(), RankineBlocks.LOAMY_SAND_PATH.get());

        grass_dirt_map.put(RankineBlocks.GRASSY_LOAM.get(), RankineBlocks.LOAM.get());
        grass_dirt_map.put(RankineBlocks.GRASSY_HUMUS.get(), RankineBlocks.HUMUS.get());
        grass_dirt_map.put(RankineBlocks.GRASSY_CLAY_LOAM.get(), RankineBlocks.CLAY_LOAM.get());
        grass_dirt_map.put(RankineBlocks.GRASSY_SANDY_CLAY_LOAM.get(), RankineBlocks.SANDY_CLAY_LOAM.get());
        grass_dirt_map.put(RankineBlocks.GRASSY_SILTY_CLAY_LOAM.get(), RankineBlocks.SILTY_CLAY_LOAM.get());
        grass_dirt_map.put(RankineBlocks.GRASSY_SILTY_LOAM.get(), RankineBlocks.SILTY_LOAM.get());
        grass_dirt_map.put(RankineBlocks.GRASSY_SANDY_LOAM.get(), RankineBlocks.SANDY_LOAM.get());
        grass_dirt_map.put(RankineBlocks.GRASSY_LOAMY_SAND.get(), RankineBlocks.LOAMY_SAND.get());
        grass_dirt_map.put(RankineBlocks.GRASSY_SANDY_CLAY.get(), RankineBlocks.SANDY_CLAY.get());
        grass_dirt_map.put(RankineBlocks.GRASSY_SILTY_CLAY.get(), RankineBlocks.SILTY_CLAY.get());

        dirt_grass_map.put(Blocks.DIRT, Blocks.GRASS_BLOCK);
        dirt_grass_map.put(RankineBlocks.LOAM.get(), RankineBlocks.GRASSY_LOAM.get());
        dirt_grass_map.put(RankineBlocks.HUMUS.get(), RankineBlocks.GRASSY_HUMUS.get());
        dirt_grass_map.put(RankineBlocks.CLAY_LOAM.get(), RankineBlocks.GRASSY_CLAY_LOAM.get());
        dirt_grass_map.put(RankineBlocks.SANDY_CLAY_LOAM.get(), RankineBlocks.GRASSY_SANDY_CLAY_LOAM.get());
        dirt_grass_map.put(RankineBlocks.SILTY_CLAY_LOAM.get(), RankineBlocks.GRASSY_SILTY_CLAY_LOAM.get());
        dirt_grass_map.put(RankineBlocks.SILTY_LOAM.get(), RankineBlocks.GRASSY_SILTY_LOAM.get());
        dirt_grass_map.put(RankineBlocks.SANDY_LOAM.get(), RankineBlocks.GRASSY_SANDY_LOAM.get());
        dirt_grass_map.put(RankineBlocks.LOAMY_SAND.get(), RankineBlocks.GRASSY_LOAMY_SAND.get());
        dirt_grass_map.put(RankineBlocks.SANDY_CLAY.get(), RankineBlocks.GRASSY_SANDY_CLAY.get());
        dirt_grass_map.put(RankineBlocks.SILTY_CLAY.get(), RankineBlocks.GRASSY_SILTY_CLAY.get());

        stripping_map.put(RankineBlocks.CEDAR_LOG.get(), RankineBlocks.STRIPPED_CEDAR_LOG.get());
        stripping_map.put(RankineBlocks.CEDAR_WOOD.get(), RankineBlocks.STRIPPED_CEDAR_WOOD.get());
        stripping_map.put(RankineBlocks.PINYON_PINE_LOG.get(), RankineBlocks.STRIPPED_PINYON_PINE_LOG.get());
        stripping_map.put(RankineBlocks.PINYON_PINE_WOOD.get(), RankineBlocks.STRIPPED_PINYON_PINE_WOOD.get());
        stripping_map.put(RankineBlocks.BALSAM_FIR_LOG.get(), RankineBlocks.STRIPPED_BALSAM_FIR_LOG.get());
        stripping_map.put(RankineBlocks.BALSAM_FIR_WOOD.get(), RankineBlocks.STRIPPED_BALSAM_FIR_WOOD.get());
        stripping_map.put(RankineBlocks.COCONUT_PALM_LOG.get(), RankineBlocks.STRIPPED_COCONUT_PALM_LOG.get());
        stripping_map.put(RankineBlocks.COCONUT_PALM_WOOD.get(), RankineBlocks.STRIPPED_COCONUT_PALM_WOOD.get());
        stripping_map.put(RankineBlocks.MAGNOLIA_LOG.get(), RankineBlocks.STRIPPED_MAGNOLIA_LOG.get());
        stripping_map.put(RankineBlocks.MAGNOLIA_WOOD.get(), RankineBlocks.STRIPPED_MAGNOLIA_WOOD.get());
        stripping_map.put(RankineBlocks.JUNIPER_LOG.get(), RankineBlocks.STRIPPED_JUNIPER_LOG.get());
        stripping_map.put(RankineBlocks.JUNIPER_WOOD.get(), RankineBlocks.STRIPPED_JUNIPER_WOOD.get());
        stripping_map.put(RankineBlocks.EASTERN_HEMLOCK_LOG.get(), RankineBlocks.STRIPPED_EASTERN_HEMLOCK_LOG.get());
        stripping_map.put(RankineBlocks.EASTERN_HEMLOCK_WOOD.get(), RankineBlocks.STRIPPED_EASTERN_HEMLOCK_WOOD.get());
        stripping_map.put(RankineBlocks.YELLOW_BIRCH_LOG.get(), RankineBlocks.STRIPPED_YELLOW_BIRCH_LOG.get());
        stripping_map.put(RankineBlocks.YELLOW_BIRCH_WOOD.get(), RankineBlocks.STRIPPED_YELLOW_BIRCH_WOOD.get());
        stripping_map.put(RankineBlocks.BLACK_BIRCH_LOG.get(), RankineBlocks.STRIPPED_BLACK_BIRCH_LOG.get());
        stripping_map.put(RankineBlocks.BLACK_BIRCH_WOOD.get(), RankineBlocks.STRIPPED_BLACK_BIRCH_WOOD.get());
        stripping_map.put(RankineBlocks.MAPLE_LOG.get(), RankineBlocks.STRIPPED_MAPLE_LOG.get());
        stripping_map.put(RankineBlocks.MAPLE_WOOD.get(), RankineBlocks.STRIPPED_MAPLE_WOOD.get());
        stripping_map.put(RankineBlocks.SHARINGA_LOG.get(), RankineBlocks.STRIPPED_SHARINGA_LOG.get());
        stripping_map.put(RankineBlocks.SHARINGA_WOOD.get(), RankineBlocks.STRIPPED_SHARINGA_WOOD.get());
        stripping_map.put(RankineBlocks.BLACK_WALNUT_LOG.get(), RankineBlocks.STRIPPED_BLACK_WALNUT_LOG.get());
        stripping_map.put(RankineBlocks.BLACK_WALNUT_WOOD.get(), RankineBlocks.STRIPPED_BLACK_WALNUT_WOOD.get());
        stripping_map.put(RankineBlocks.CORK_OAK_LOG.get(), RankineBlocks.STRIPPED_CORK_OAK_LOG.get());
        stripping_map.put(RankineBlocks.CORK_OAK_WOOD.get(), RankineBlocks.STRIPPED_CORK_OAK_WOOD.get());
        stripping_map.put(RankineBlocks.CINNAMON_LOG.get(), RankineBlocks.STRIPPED_CINNAMON_LOG.get());
        stripping_map.put(RankineBlocks.CINNAMON_WOOD.get(), RankineBlocks.STRIPPED_CINNAMON_WOOD.get());

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
        addFlowerPot(RankineBlocks.SHARINGA_SAPLING.get(), RankineBlocks.POTTED_SHARINGA_SAPLING.get());
        addFlowerPot(RankineBlocks.BLACK_WALNUT_SAPLING.get(), RankineBlocks.POTTED_BLACK_WALNUT_SAPLING.get());
        addFlowerPot(RankineBlocks.CORK_OAK_SAPLING.get(), RankineBlocks.POTTED_CORK_OAK_SAPLING.get());
        addFlowerPot(RankineBlocks.CINNAMON_SAPLING.get(), RankineBlocks.POTTED_CINNAMON_SAPLING.get());

        tapFluids_map.put(TreeTapFluids.LAVA, Blocks.LAVA.getDefaultState());
        tapFluids_map.put(TreeTapFluids.WATER, Blocks.WATER.getDefaultState());
        tapFluids_map.put(TreeTapFluids.JUGLONE, RankineBlocks.JUGLONE.get().getDefaultState());
        tapFluids_map.put(TreeTapFluids.SAP, RankineBlocks.SAP.get().getDefaultState());
        tapFluids_map.put(TreeTapFluids.MAPLE_SAP, RankineBlocks.MAPLE_SAP.get().getDefaultState());
        tapFluids_map.put(TreeTapFluids.RESIN, RankineBlocks.RESIN.get().getDefaultState());
        tapFluids_map.put(TreeTapFluids.LATEX, RankineBlocks.LATEX.get().getDefaultState());



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


}
