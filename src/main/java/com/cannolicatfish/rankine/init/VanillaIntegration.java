package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.blocks.states.TilledSoilTypes;
import com.cannolicatfish.rankine.blocks.states.TreeTapFluids;
import net.minecraft.block.*;
import net.minecraft.util.IItemProvider;

import java.util.HashMap;
import java.util.Map;

public class VanillaIntegration {
    public static Map<Block, Block> stripping_map = new HashMap<Block, Block>();
    public static Map<Block, Block> pathBlocks_map = new HashMap<Block, Block>();
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

        for (Block GRASS : RankineLists.GRASS_BLOCKS)  {
            pathBlocks_map.put(GRASS, RankineLists.PATH_BLOCKS.get(RankineLists.GRASS_BLOCKS.indexOf(GRASS)));
        }
        for (Block GRASS : RankineLists.PODZOL_BLOCKS)  {
            pathBlocks_map.put(GRASS, RankineLists.PATH_BLOCKS.get(RankineLists.PODZOL_BLOCKS.indexOf(GRASS)));
        }
        for (Block GRASS : RankineLists.SOIL_BLOCKS)  {
            pathBlocks_map.put(GRASS, RankineLists.PATH_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(GRASS)));
        }
        for (Block GRASS : RankineLists.MYCELIUM_BLOCKS)  {
            pathBlocks_map.put(GRASS, RankineBlocks.MYCELIUM_PATH.get());
        }


        hoeables_map.put(Blocks.DIRT, TilledSoilTypes.DIRT);
        hoeables_map.put(Blocks.GRASS_BLOCK, TilledSoilTypes.DIRT);
        hoeables_map.put(Blocks.MYCELIUM, TilledSoilTypes.DIRT);
        hoeables_map.put(Blocks.PODZOL, TilledSoilTypes.DIRT);
        hoeables_map.put(Blocks.COARSE_DIRT, TilledSoilTypes.COARSE_DIRT);
        hoeables_map.put(Blocks.SOUL_SOIL, TilledSoilTypes.SOUL_SOIL);
        hoeables_map.put(RankineBlocks.LOAM.get(), TilledSoilTypes.LOAM);
        hoeables_map.put(RankineBlocks.LOAM_GRASS_PATH.get(), TilledSoilTypes.LOAM);
        hoeables_map.put(RankineBlocks.LOAM_GRASS_BLOCK.get(), TilledSoilTypes.LOAM);
        hoeables_map.put(RankineBlocks.LOAM_PODZOL.get(), TilledSoilTypes.LOAM);
        hoeables_map.put(RankineBlocks.LOAM_MYCELIUM.get(), TilledSoilTypes.LOAM);
        hoeables_map.put(RankineBlocks.LOAM_MUD.get(), TilledSoilTypes.LOAM);
        hoeables_map.put(RankineBlocks.HUMUS.get(), TilledSoilTypes.HUMUS);
        hoeables_map.put(RankineBlocks.HUMUS_GRASS_PATH.get(), TilledSoilTypes.HUMUS);
        hoeables_map.put(RankineBlocks.HUMUS_GRASS_BLOCK.get(), TilledSoilTypes.HUMUS);
        hoeables_map.put(RankineBlocks.HUMUS_PODZOL.get(), TilledSoilTypes.HUMUS);
        hoeables_map.put(RankineBlocks.HUMUS_MYCELIUM.get(), TilledSoilTypes.HUMUS);
        hoeables_map.put(RankineBlocks.HUMUS_MUD.get(), TilledSoilTypes.HUMUS);
        hoeables_map.put(RankineBlocks.CLAY_LOAM.get(), TilledSoilTypes.CLAY_LOAM);
        hoeables_map.put(RankineBlocks.CLAY_LOAM_GRASS_PATH.get(), TilledSoilTypes.CLAY_LOAM);
        hoeables_map.put(RankineBlocks.CLAY_LOAM_GRASS_BLOCK.get(), TilledSoilTypes.CLAY_LOAM);
        hoeables_map.put(RankineBlocks.CLAY_LOAM_PODZOL.get(), TilledSoilTypes.CLAY_LOAM);
        hoeables_map.put(RankineBlocks.CLAY_LOAM_MYCELIUM.get(), TilledSoilTypes.CLAY_LOAM);
        hoeables_map.put(RankineBlocks.CLAY_LOAM_MUD.get(), TilledSoilTypes.CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_LOAM.get(), TilledSoilTypes.SANDY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_LOAM_GRASS_PATH.get(), TilledSoilTypes.SANDY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_LOAM_GRASS_BLOCK.get(), TilledSoilTypes.SANDY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_LOAM_PODZOL.get(), TilledSoilTypes.SANDY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_LOAM_MYCELIUM.get(), TilledSoilTypes.SANDY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_LOAM_MUD.get(), TilledSoilTypes.SANDY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_LOAM.get(), TilledSoilTypes.SILTY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_LOAM_GRASS_PATH.get(), TilledSoilTypes.SILTY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_LOAM_GRASS_BLOCK.get(), TilledSoilTypes.SILTY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_LOAM_PODZOL.get(), TilledSoilTypes.SILTY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_LOAM_MYCELIUM.get(), TilledSoilTypes.SILTY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_LOAM_MUD.get(), TilledSoilTypes.SILTY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_LOAM.get(), TilledSoilTypes.SILTY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_LOAM_GRASS_PATH.get(), TilledSoilTypes.SILTY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_LOAM_GRASS_BLOCK.get(), TilledSoilTypes.SILTY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_LOAM_PODZOL.get(), TilledSoilTypes.SILTY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_LOAM_MYCELIUM.get(), TilledSoilTypes.SILTY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_LOAM_MUD.get(), TilledSoilTypes.SILTY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_LOAM.get(), TilledSoilTypes.SANDY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_LOAM_GRASS_PATH.get(), TilledSoilTypes.SANDY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_LOAM_GRASS_BLOCK.get(), TilledSoilTypes.SANDY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_LOAM_PODZOL.get(), TilledSoilTypes.SANDY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_LOAM_MYCELIUM.get(), TilledSoilTypes.SANDY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_LOAM_MUD.get(), TilledSoilTypes.SANDY_LOAM);
        hoeables_map.put(RankineBlocks.LOAMY_SAND.get(), TilledSoilTypes.LOAMY_SAND);
        hoeables_map.put(RankineBlocks.LOAMY_SAND_GRASS_PATH.get(), TilledSoilTypes.LOAMY_SAND);
        hoeables_map.put(RankineBlocks.LOAMY_SAND_GRASS_BLOCK.get(), TilledSoilTypes.LOAMY_SAND);
        hoeables_map.put(RankineBlocks.LOAMY_SAND_PODZOL.get(), TilledSoilTypes.LOAMY_SAND);
        hoeables_map.put(RankineBlocks.LOAMY_SAND_MYCELIUM.get(), TilledSoilTypes.LOAMY_SAND);
        hoeables_map.put(RankineBlocks.LOAMY_SAND_MUD.get(), TilledSoilTypes.LOAMY_SAND);
        hoeables_map.put(RankineBlocks.SANDY_CLAY.get(), TilledSoilTypes.SANDY_CLAY);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_GRASS_PATH.get(), TilledSoilTypes.SANDY_CLAY);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_GRASS_BLOCK.get(), TilledSoilTypes.SANDY_CLAY);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_PODZOL.get(), TilledSoilTypes.SANDY_CLAY);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_MYCELIUM.get(), TilledSoilTypes.SANDY_CLAY);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_MUD.get(), TilledSoilTypes.SANDY_CLAY);
        hoeables_map.put(RankineBlocks.SILTY_CLAY.get(), TilledSoilTypes.SILTY_CLAY);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_GRASS_PATH.get(), TilledSoilTypes.SILTY_CLAY);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_GRASS_BLOCK.get(), TilledSoilTypes.SILTY_CLAY);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_PODZOL.get(), TilledSoilTypes.SILTY_CLAY);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_MUD.get(), TilledSoilTypes.SILTY_CLAY);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_MUD.get(), TilledSoilTypes.SILTY_CLAY);

        stripping_map.put(RankineBlocks.CEDAR_LOG.get(), RankineBlocks.STRIPPED_CEDAR_LOG.get());
        stripping_map.put(RankineBlocks.CEDAR_WOOD.get(), RankineBlocks.STRIPPED_CEDAR_WOOD.get());
        stripping_map.put(RankineBlocks.PETRIFIED_CHORUS_LOG.get(), RankineBlocks.STRIPPED_PETRIFIED_CHORUS_LOG.get());
        stripping_map.put(RankineBlocks.PETRIFIED_CHORUS_WOOD.get(), RankineBlocks.STRIPPED_PETRIFIED_CHORUS_WOOD.get());
        stripping_map.put(RankineBlocks.ERYTHRINA_LOG.get(), RankineBlocks.STRIPPED_ERYTHRINA_LOG.get());
        stripping_map.put(RankineBlocks.ERYTHRINA_WOOD.get(), RankineBlocks.STRIPPED_ERYTHRINA_WOOD.get());
        stripping_map.put(RankineBlocks.CHARRED_LOG.get(), RankineBlocks.STRIPPED_CHARRED_LOG.get());
        stripping_map.put(RankineBlocks.CHARRED_WOOD.get(), RankineBlocks.STRIPPED_CHARRED_WOOD.get());
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


    }



    public static void registerCompostable(float chance, IItemProvider itemIn) {
        ComposterBlock.CHANCES.put(itemIn.asItem(), chance);
    }

    public static void addFlowerPot(Block plant, Block plantPot) {
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(plant.getRegistryName(), () -> plantPot);
    }


}
