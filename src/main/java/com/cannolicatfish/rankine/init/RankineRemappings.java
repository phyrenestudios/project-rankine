package com.cannolicatfish.rankine.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankineRemappings {

    public static Map<ResourceLocation, Item> itemRemappings = new HashMap<>();

    public static Map<ResourceLocation, Item> getItemRemappings() {
        itemRemappings.put(new ResourceLocation("rankine:power_cell_1"),RankineItems.MAGNESIUM_BATTERY.get());
        itemRemappings.put(new ResourceLocation("rankine:power_cell_2"),RankineItems.LEAD_ACID_BATTERY.get());
        itemRemappings.put(new ResourceLocation("rankine:power_cell_3"),RankineItems.VANADIUM_REDOX_BATTERY.get());
        itemRemappings.put(new ResourceLocation("rankine:power_cell_4"),RankineItems.ZINC_BROMINE_BATTERY.get());
        itemRemappings.put(new ResourceLocation("rankine:power_cell_5"),RankineItems.SODIUM_SULFUR_BATTERY.get());
        itemRemappings.put(new ResourceLocation("rankine:power_cell_6"),RankineItems.LITHIUM_ION_BATTERY.get());

        itemRemappings.put(new ResourceLocation("rankine:bronze_alloy"),RankineItems.BRONZE_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:titanium_alloy"),RankineItems.TITANIUM_ALLOY_INGOT.get());

        return itemRemappings;
    }

    public static Map<ResourceLocation, Block> blockRemappings = new HashMap<>();

    public static Map<ResourceLocation, Block> getBlockRemappings() {
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone"),RankineBlocks.ITACOLUMITE.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_tufa_limestone"),RankineBlocks.POLISHED_ITACOLUMITE.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_bricks"),RankineBlocks.ITACOLUMITE_BRICKS.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_slab"),RankineBlocks.ITACOLUMITE_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_tufa_limestone_slab"),RankineBlocks.POLISHED_ITACOLUMITE_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_bricks_slab"),RankineBlocks.ITACOLUMITE_BRICKS_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_stairs"),RankineBlocks.ITACOLUMITE_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_tufa_limestone_stairs"),RankineBlocks.POLISHED_ITACOLUMITE_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_bricks_stairs"),RankineBlocks.ITACOLUMITE_BRICKS_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_wall"),RankineBlocks.ITACOLUMITE_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_tufa_limestone_wall"),RankineBlocks.POLISHED_ITACOLUMITE_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_bricks_wall"),RankineBlocks.ITACOLUMITE_BRICKS_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_vertical_slab"),RankineBlocks.ITACOLUMITE_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_tufa_limestone_vertical_slab"),RankineBlocks.POLISHED_ITACOLUMITE_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_bricks_vertical_slab"),RankineBlocks.ITACOLUMITE_BRICKS_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_pressure_plate"),RankineBlocks.ITACOLUMITE_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_bricks_pressure_plate"),RankineBlocks.ITACOLUMITE_BRICKS_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_button"),RankineBlocks.ITACOLUMITE_BUTTON.get());


        blockRemappings.put(new ResourceLocation("rankine:carbonaceous_shale"),RankineBlocks.SHALE.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_carbonaceous_shale"),RankineBlocks.POLISHED_SHALE.get());
        blockRemappings.put(new ResourceLocation("rankine:carbonaceous_shale_bricks"),RankineBlocks.SHALE_BRICKS.get());
        blockRemappings.put(new ResourceLocation("rankine:carbonaceous_shale_slab"),RankineBlocks.SHALE_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_carbonaceous_shale_slab"),RankineBlocks.POLISHED_SHALE_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:carbonaceous_shale_bricks_slab"),RankineBlocks.SHALE_BRICKS_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:carbonaceous_shale_stairs"),RankineBlocks.SHALE_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_carbonaceous_shale_stairs"),RankineBlocks.POLISHED_SHALE_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:carbonaceous_shale_bricks_stairs"),RankineBlocks.SHALE_BRICKS_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:carbonaceous_shale_wall"),RankineBlocks.SHALE_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_carbonaceous_shale_wall"),RankineBlocks.POLISHED_SHALE_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:carbonaceous_shale_bricks_wall"),RankineBlocks.SHALE_BRICKS_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:carbonaceous_shale_vertical_slab"),RankineBlocks.SHALE_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_carbonaceous_shale_vertical_slab"),RankineBlocks.POLISHED_SHALE_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:carbonaceous_shale_bricks_vertical_slab"),RankineBlocks.SHALE_BRICKS_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:carbonaceous_shale_pressure_plate"),RankineBlocks.SHALE_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:carbonaceous_shale_bricks_pressure_plate"),RankineBlocks.SHALE_BRICKS_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:carbonaceous_shale_button"),RankineBlocks.SHALE_BUTTON.get());

        blockRemappings.put(new ResourceLocation("rankine:quartz_sandstone"),RankineBlocks.ITACOLUMITE.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_quartz_sandstone"),RankineBlocks.POLISHED_ITACOLUMITE.get());
        blockRemappings.put(new ResourceLocation("rankine:quartz_sandstone_bricks"),RankineBlocks.ITACOLUMITE_BRICKS.get());
        blockRemappings.put(new ResourceLocation("rankine:quartz_sandstone_slab"),RankineBlocks.ITACOLUMITE_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_quartz_sandstone_slab"),RankineBlocks.POLISHED_ITACOLUMITE_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:quartz_sandstone_bricks_slab"),RankineBlocks.ITACOLUMITE_BRICKS_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:quartz_sandstone_stairs"),RankineBlocks.ITACOLUMITE_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_quartz_sandstone_stairs"),RankineBlocks.POLISHED_ITACOLUMITE_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:quartz_sandstone_bricks_stairs"),RankineBlocks.ITACOLUMITE_BRICKS_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:quartz_sandstone_wall"),RankineBlocks.ITACOLUMITE_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_quartz_sandstone_wall"),RankineBlocks.POLISHED_ITACOLUMITE_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:quartz_sandstone_bricks_wall"),RankineBlocks.ITACOLUMITE_BRICKS_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:quartz_sandstone_vertical_slab"),RankineBlocks.ITACOLUMITE_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_quartz_sandstone_vertical_slab"),RankineBlocks.POLISHED_ITACOLUMITE_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:quartz_sandstone_bricks_vertical_slab"),RankineBlocks.ITACOLUMITE_BRICKS_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:quartz_sandstone_pressure_plate"),RankineBlocks.ITACOLUMITE_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:quartz_sandstone_bricks_pressure_plate"),RankineBlocks.ITACOLUMITE_BRICKS_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:quartz_sandstone_button"),RankineBlocks.ITACOLUMITE_BUTTON.get());

        blockRemappings.put(new ResourceLocation("rankine:arkose_sandstone"),RankineBlocks.ARKOSE.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_arkose_sandstone"),RankineBlocks.POLISHED_ARKOSE.get());
        blockRemappings.put(new ResourceLocation("rankine:arkose_sandstone_bricks"),RankineBlocks.ARKOSE_BRICKS.get());
        blockRemappings.put(new ResourceLocation("rankine:arkose_sandstone_slab"),RankineBlocks.ARKOSE_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_arkose_sandstone_slab"),RankineBlocks.POLISHED_ARKOSE_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:arkose_sandstone_bricks_slab"),RankineBlocks.ARKOSE_BRICKS_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:arkose_sandstone_stairs"),RankineBlocks.ARKOSE_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_arkose_sandstone_stairs"),RankineBlocks.POLISHED_ARKOSE_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:arkose_sandstone_bricks_stairs"),RankineBlocks.ARKOSE_BRICKS_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:arkose_sandstone_wall"),RankineBlocks.ARKOSE_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_arkose_sandstone_wall"),RankineBlocks.POLISHED_ARKOSE_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:arkose_sandstone_bricks_wall"),RankineBlocks.ARKOSE_BRICKS_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:arkose_sandstone_vertical_slab"),RankineBlocks.ARKOSE_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_arkose_sandstone_vertical_slab"),RankineBlocks.POLISHED_ARKOSE_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:arkose_sandstone_bricks_vertical_slab"),RankineBlocks.ARKOSE_BRICKS_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:arkose_sandstone_pressure_plate"),RankineBlocks.ARKOSE_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:arkose_sandstone_bricks_pressure_plate"),RankineBlocks.ARKOSE_BRICKS_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:arkose_sandstone_button"),RankineBlocks.ARKOSE_BUTTON.get());

        blockRemappings.put(new ResourceLocation("rankine:bridgmanite"),RankineBlocks.BRIDGMANHAM.get());
        /*blockRemappings.put(new ResourceLocation("rankine:polished_bridgmanite"),RankineBlocks.POLISHED_BRIDGMANHAM.get());
        blockRemappings.put(new ResourceLocation("rankine:bridgmanite_bricks"),RankineBlocks.BRIDGMANHAM_BRICKS.get());
        blockRemappings.put(new ResourceLocation("rankine:bridgmanite_slab"),RankineBlocks.BRIDGMANHAM_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_bridgmanite_slab"),RankineBlocks.POLISHED_BRIDGMANHAM_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:bridgmanite_bricks_slab"),RankineBlocks.BRIDGMANHAM_BRICKS_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:bridgmanite_stairs"),RankineBlocks.BRIDGMANHAM_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_bridgmanite_stairs"),RankineBlocks.POLISHED_BRIDGMANHAM_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:bridgmanite_bricks_stairs"),RankineBlocks.BRIDGMANHAM_BRICKS_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:bridgmanite_wall"),RankineBlocks.BRIDGMANHAM_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_bridgmanite_wall"),RankineBlocks.POLISHED_BRIDGMANHAM_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:bridgmanite_bricks_wall"),RankineBlocks.BRIDGMANHAM_BRICKS_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:bridgmanite_vertical_slab"),RankineBlocks.BRIDGMANHAM_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_bridgmanite_vertical_slab"),RankineBlocks.POLISHED_BRIDGMANHAM_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:bridgmanite_bricks_vertical_slab"),RankineBlocks.BRIDGMANHAM_BRICKS_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:bridgmanite_pressure_plate"),RankineBlocks.BRIDGMANHAM_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:bridgmanite_bricks_pressure_plate"),RankineBlocks.BRIDGMANHAM_BRICKS_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:bridgmanite_button"),RankineBlocks.BRIDGMANHAM_BUTTON.get());*/


        return blockRemappings;
    }
}
