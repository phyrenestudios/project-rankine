package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Items;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RankineBlockLootTables extends RankineLootTableProvider {

    public RankineBlockLootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        for (Block blk : Stream.of(
                RankineLists.STONE,
                RankineLists.POLISHED_STONE,
                RankineLists.STONE_BRICKS,
                RankineLists.POLISHED_STONE,
                RankineLists.STONE_STAIRS,
                RankineLists.POLISHED_STONE_STAIRS,
                RankineLists.STONE_BRICKS_STAIRS,
                RankineLists.STONE_WALL,
                RankineLists.POLISHED_STONE_WALL,
                RankineLists.STONE_BRICKS_WALL,
                RankineLists.STONE_PRESSURE_PLATE,
                RankineLists.STONE_BRICKS_PRESSURE_PLATE,
                RankineLists.STONE_BUTTON,
                RankineLists.STONE_PILLARS,
                RankineLists.BRICKS,
                RankineLists.BRICKS_STAIRS,
                RankineLists.BRICKS_WALL,
                RankineLists.SHEETMETALS,
                RankineLists.GEODES,
                RankineLists.LEDS,
                RankineLists.MINERAL_WOOL,
                RankineLists.FIBER_BLOCK,
                RankineLists.FIBER_MAT,
                RankineLists.PLANKS,
                RankineLists.LOGS,
                RankineLists.STRIPPED_LOGS,
                RankineLists.WOODS,
                RankineLists.STRIPPED_WOODS,
                RankineLists.WOODEN_STAIRS,
                RankineLists.WOODEN_TRAPDOORS,
                RankineLists.WOODEN_FENCES,
                RankineLists.WOODEN_FENCE_GATES,
                RankineLists.WOODEN_BUTTONS,
                RankineLists.WOODEN_PRESSURE_PLATES,
                RankineLists.MINERAL_STONES,
                RankineLists.METAL_TRAPDOORS,
                RankineLists.METAL_LADDERS,
                RankineLists.ALLOY_BLOCKS,
                RankineLists.ALLOY_PEDESTALS,
                RankineLists.ALLOY_POLES,
                RankineLists.STANDARD_BLOCKS,
                RankineLists.ROTATION_BLOCKS,
                RankineLists.ELEMENT_BLOCKS,
                RankineLists.SAPLINGS,

                RankineLists.CROPS_SINGLE,
                RankineLists.CROPS_DOUBLE,
                RankineLists.CROPS_TRIPLE,
                RankineLists.BUSH_PLANTS,
                RankineLists.DOUBLE_BUSH_PLANTS
                ).flatMap(Collection::stream).collect(Collectors.toList())) {
            lootTables.put(blk, createBlockLootTable(blk));
        }


        for (Block SOIL : Stream.of(RankineLists.SOILS).flatMap(Collection::stream).collect(Collectors.toList())) {
            Block GRASS = RankineLists.GRASSY_SOILS.get(RankineLists.SOILS.indexOf(SOIL));
            Block PATH = RankineLists.PATH_BLOCKS.get(RankineLists.SOILS.indexOf(SOIL));
            lootTables.put(SOIL, createBlockLootTable(SOIL));
            lootTables.put(PATH, createBlockLootTable(SOIL));
            lootTables.put(GRASS, droppingWithSilkTouch(GRASS,SOIL));
        }
        lootTables.put(RankineBlocks.ENDER_SHIRO.get(), droppingWithSilkTouch(RankineBlocks.ENDER_SHIRO.get(),Blocks.END_STONE));


        for (Block blk : Stream.of(
                RankineLists.STONE_SLAB,
                RankineLists.POLISHED_STONE_SLAB,
                RankineLists.STONE_BRICKS_SLAB,
                RankineLists.STONE_VERTICAL_SLAB,
                RankineLists.POLISHED_STONE_VERTICAL_SLAB,
                RankineLists.STONE_BRICKS_VERTICAL_SLAB,
                RankineLists.SHEETMETAL_VERTICAL_SLAB,
                RankineLists.BRICKS_SLAB,
                RankineLists.BRICKS_VERTICAL_SLAB,
                RankineLists.WOODEN_VERTICAL_SLABS,
                RankineLists.WOODEN_SLABS
        ).flatMap(Collection::stream).collect(Collectors.toList())) {
            lootTables.put(blk, slabBlockLootTable(blk));
        }

        
        lootTables.put(RankineBlocks.NATIVE_ARSENIC_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_ARSENIC_ORE.get(), RankineItems.ARSENIC_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_BISMUTH_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_BISMUTH_ORE.get(), RankineItems.BISMUTH_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_SILVER_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_SILVER_ORE.get(), RankineItems.SILVER_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_TELLURIUM_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_TELLURIUM_ORE.get(), RankineItems.TELLURIUM_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_SELENIUM_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_SELENIUM_ORE.get(), RankineItems.SELENIUM_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_GALLIUM_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_GALLIUM_ORE.get(), RankineItems.GALLIUM_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_INDIUM_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_INDIUM_ORE.get(), RankineItems.INDIUM_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_TIN_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_TIN_ORE.get(), RankineItems.TIN_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_LEAD_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_LEAD_ORE.get(), RankineItems.LEAD_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_GOLD_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_GOLD_ORE.get(), Items.GOLD_NUGGET));
        lootTables.put(RankineBlocks.NATIVE_SILVER_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_SILVER_ORE.get(), RankineItems.SILVER_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_SULFUR_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_SULFUR_ORE.get(), RankineItems.SULFUR_NUGGET.get()));

        lootTables.put(RankineBlocks.LIGNITE_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.LIGNITE_ORE.get(), RankineItems.LIGNITE.get()));
        lootTables.put(RankineBlocks.SUBBITUMINOUS_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.SUBBITUMINOUS_ORE.get(), RankineItems.SUBBITUMINOUS_COAL.get()));
        lootTables.put(RankineBlocks.BITUMINOUS_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.BITUMINOUS_ORE.get(), RankineItems.BITUMINOUS_COAL.get()));
        lootTables.put(RankineBlocks.ANTHRACITE_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.ANTHRACITE_ORE.get(), RankineItems.ANTHRACITE_COAL.get()));
        lootTables.put(RankineBlocks.LONSDALEITE_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.LONSDALEITE_ORE.get(), RankineItems.LONSDALEITE_DIAMOND.get()));
        lootTables.put(RankineBlocks.PLUMBAGO_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.PLUMBAGO_ORE.get(), RankineItems.GRAPHITE.get()));
        lootTables.put(RankineBlocks.HALITE_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.HALITE_ORE.get(), RankineItems.SODIUM_CHLORIDE.get()));
        lootTables.put(RankineBlocks.QUARTZ_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.QUARTZ_ORE.get(), Items.QUARTZ));
        lootTables.put(RankineBlocks.DIAMOND_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.DIAMOND_ORE.get(), Items.DIAMOND));
        lootTables.put(RankineBlocks.EMERALD_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.EMERALD_ORE.get(), Items.EMERALD));

        lootTables.put(RankineBlocks.TAENITE_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.TAENITE_ORE.get(), RankineItems.METEORIC_IRON.get()));
        lootTables.put(RankineBlocks.TETRATAENITE_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.TETRATAENITE_ORE.get(), RankineItems.METEORIC_IRON.get()));
        lootTables.put(RankineBlocks.ANTITAENITE_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.ANTITAENITE_ORE.get(), RankineItems.METEORIC_IRON.get()));
        lootTables.put(RankineBlocks.KAMACITE_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.KAMACITE_ORE.get(), RankineItems.METEORIC_IRON.get()));

        for (Block ORE : RankineLists.CRUSHING_ORES) {
            lootTables.put(ORE, fortunableOreOreBlockLootTable(ORE, ForgeRegistries.ITEMS.getValue(new ResourceLocation(ORE.getRegistryName().toString().replace("_ore","")))));
        }
        for (Block BLK : RankineLists.FLOWER_POTS) {
            lootTables.put(BLK, droppingAndFlowerPot(((FlowerPotBlock)BLK).getFlower()));
        }
        for (Block BLK : RankineLists.EIGHT_LAYER_BLOCKS) {
            lootTables.put(BLK, eightLayerBlock(BLK));
        }
        for (Block LEAF : RankineLists.LEAVES) {
            Block SAPLING = RankineLists.SAPLINGS.get(RankineLists.LEAVES.indexOf(LEAF));
            lootTables.put(LEAF, droppingWithChancesAndSticks(LEAF, SAPLING, DEFAULT_SAPLING_DROP_RATES));
        }
        for (Block BLK : Stream.of(
                RankineLists.TALL_FLOWERS,
                RankineLists.METAL_DOORS,
                RankineLists.WOODEN_DOORS
        ).flatMap(Collection::stream).collect(Collectors.toList())) {
            lootTables.put(BLK, droppingWhen(BLK, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        }

    }



}
