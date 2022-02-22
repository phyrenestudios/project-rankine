package com.cannolicatfish.rankine.init;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
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

        itemRemappings.put(new ResourceLocation("rankine:haste_pendant"),RankineItems.TOTEM_OF_HASTENING.get());
        itemRemappings.put(new ResourceLocation("rankine:luck_pendant"),RankineItems.TOTEM_OF_PROMISING.get());
        itemRemappings.put(new ResourceLocation("rankine:health_pendant"),RankineItems.TOTEM_OF_ENDURING.get());
        itemRemappings.put(new ResourceLocation("rankine:speed_pendant"),RankineItems.TOTEM_OF_TIMESAVING.get());
        itemRemappings.put(new ResourceLocation("rankine:levitation_pendant"),RankineItems.TOTEM_OF_LEVITATING.get());
        itemRemappings.put(new ResourceLocation("rankine:repulsion_pendant"),RankineItems.TOTEM_OF_REPULSING.get());

        itemRemappings.put(new ResourceLocation("rankine:feldspar"),RankineItems.ORTHOCLASE_FELDSPAR.get());
        itemRemappings.put(new ResourceLocation("rankine:salt"),RankineItems.SODIUM_CHLORIDE.get());

        itemRemappings.put(new ResourceLocation("rankine:alnico_alloy"),RankineItems.ALNICO_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:amalgam_alloy"),RankineItems.AMALGAM_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:black_gold_alloy"),RankineItems.BLACK_GOLD_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:blue_gold_alloy"),RankineItems.BLUE_GOLD_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:brass_alloy"),RankineItems.BRASS_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:bronze_alloy"),RankineItems.BRONZE_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:cast_iron_alloy"),RankineItems.CAST_IRON_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:cobalt_superalloy"),RankineItems.COBALT_SUPERALLOY_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:duralumin_alloy"),RankineItems.DURALUMIN_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:galinstan_alloy"),RankineItems.GALINSTAN_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:invar_alloy"),RankineItems.INVAR_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:magnesium_alloy"),RankineItems.MAGNESIUM_ALLOY_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:mischmetal_alloy"),RankineItems.MISCHMETAL_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:nickel_silver_alloy"),RankineItems.NICKEL_SILVER_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:nickel_superalloy"),RankineItems.NICKEL_SUPERALLOY_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:nitinol_alloy"),RankineItems.NITINOL_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:osmiridium_alloy"),RankineItems.OSMIRIDIUM_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:pewter_alloy"),RankineItems.PEWTER_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:purple_gold_alloy"),RankineItems.PURPLE_GOLD_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:rose_gold_alloy"),RankineItems.ROSE_GOLD_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:rose_metal_alloy"),RankineItems.ROSE_METAL_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:sodium_potassium_alloy"),RankineItems.SODIUM_POTASSIUM_ALLOY_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:solder_alloy"),RankineItems.SOLDER.get());
        itemRemappings.put(new ResourceLocation("rankine:steel_alloy"),RankineItems.STEEL_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:stainless_steel_alloy"),RankineItems.STAINLESS_STEEL_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:sterling_silver_alloy"),RankineItems.STERLING_SILVER_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:titanium_alloy"),RankineItems.TITANIUM_ALLOY_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:tungsten_heavy_alloy"),RankineItems.TUNGSTEN_HEAVY_ALLOY_INGOT.get());
        itemRemappings.put(new ResourceLocation("rankine:white_gold_alloy"),RankineItems.WHITE_GOLD_INGOT.get());

        itemRemappings.put(new ResourceLocation("rankine:corn_flour"),RankineItems.CORN_GRAIN.get());
        itemRemappings.put(new ResourceLocation("rankine:rice_flour"),RankineItems.RICE_GRAIN.get());

        itemRemappings.put(new ResourceLocation("rankine:dry_mortar"),RankineItems.CEMENT_MIX.get());
        itemRemappings.put(new ResourceLocation("rankine:kaolinite_ball"),RankineItems.KAOLINITE.get());

        itemRemappings.put(new ResourceLocation("rankine:element_transmuter"),RankineItems.MERCURY.get());
        itemRemappings.put(new ResourceLocation("rankine:up_transmuter"),RankineItems.MERCURY.get());
        itemRemappings.put(new ResourceLocation("rankine:down_transmuter"),RankineItems.MERCURY.get());
        itemRemappings.put(new ResourceLocation("rankine:left_transmuter"),RankineItems.MERCURY.get());
        itemRemappings.put(new ResourceLocation("rankine:right_transmuter"),RankineItems.MERCURY.get());

        itemRemappings.put(new ResourceLocation("rankine:antimony_ingot"),RankineItems.ANTIMONY.get());
        itemRemappings.put(new ResourceLocation("rankine:arsenic_ingot"),RankineItems.ARSENIC.get());
        itemRemappings.put(new ResourceLocation("rankine:boron_ingot"),RankineItems.BORON.get());
        itemRemappings.put(new ResourceLocation("rankine:bromine_ingot"),RankineItems.BROMINE.get());
        itemRemappings.put(new ResourceLocation("rankine:carbon_ingot"),RankineItems.CARBON.get());
        itemRemappings.put(new ResourceLocation("rankine:germanium_ingot"),RankineItems.GERMANIUM.get());
        itemRemappings.put(new ResourceLocation("rankine:iodine_ingot"),RankineItems.IODINE.get());
        itemRemappings.put(new ResourceLocation("rankine:mercury_ingot"),RankineItems.MERCURY.get());
        itemRemappings.put(new ResourceLocation("rankine:selenium_ingot"),RankineItems.SELENIUM.get());
        itemRemappings.put(new ResourceLocation("rankine:tellurium_ingot"),RankineItems.TELLURIUM.get());

        itemRemappings.put(new ResourceLocation("rankine:metal_knife"),RankineItems.INVAR_KNIFE.get());

        itemRemappings.put(new ResourceLocation("rankine:stone_hammer"),RankineItems.STONE_HAMMER.get());
        return itemRemappings;
    }

    public static Map<ResourceLocation, Block> blockRemappings = new HashMap<>();

    public static Map<ResourceLocation, Block> getBlockRemappings() {

        blockRemappings.put(new ResourceLocation("rankine:aluminum_ladder"),RankineBlocks.DURALUMIN_LADDER.get());

        blockRemappings.put(new ResourceLocation("rankine:aquamarine_ore"), RankineBlocks.BERYL_ORE.get());
        blockRemappings.put(new ResourceLocation("rankine:columbite_ore"), RankineBlocks.COLTAN_ORE.get());
        blockRemappings.put(new ResourceLocation("rankine:fluorite_ore"), Blocks.STONE);
        blockRemappings.put(new ResourceLocation("rankine:majorite_ore"), Blocks.STONE);
        blockRemappings.put(new ResourceLocation("rankine:moissanite_ore"), Blocks.STONE);
        blockRemappings.put(new ResourceLocation("rankine:native_aluminum_ore"), Blocks.STONE);
        blockRemappings.put(new ResourceLocation("rankine:native_copper_ore"), Blocks.STONE);
        blockRemappings.put(new ResourceLocation("rankine:opal_ore"), Blocks.STONE);
        blockRemappings.put(new ResourceLocation("rankine:pink_halite_ore"), RankineBlocks.SYLVINITE.get());
        blockRemappings.put(new ResourceLocation("rankine:halite_ore"), RankineBlocks.SYLVINITE.get());
        blockRemappings.put(new ResourceLocation("rankine:quartz_ore"), RankineBlocks.NETHER_QUARTZ_ORE.get());
        blockRemappings.put(new ResourceLocation("rankine:tantalite_ore"), RankineBlocks.COLTAN_ORE.get());
        blockRemappings.put(new ResourceLocation("rankine:vanadinite_ore"), RankineBlocks.GALENA_ORE.get());

        blockRemappings.put(new ResourceLocation("rankine:dacitic_tuff"), RankineBlocks.ANDESITIC_TUFF.get());

        blockRemappings.put(new ResourceLocation("rankine:enstatite"), RankineBlocks.ENSTATITE_CHONDRITE.get());
        blockRemappings.put(new ResourceLocation("rankine:enstatite_paver"), RankineBlocks.ENSTATITE_CHONDRITE_BRICKS.get());
        blockRemappings.put(new ResourceLocation("rankine:meteorite_paver"), RankineBlocks.METEORITE_BRICKS.get());

        blockRemappings.put(new ResourceLocation("rankine:etched_glass0"), Blocks.GLASS);
        blockRemappings.put(new ResourceLocation("rankine:etched_glass1"), Blocks.GLASS);
        blockRemappings.put(new ResourceLocation("rankine:etched_glass2"), Blocks.GLASS);

        blockRemappings.put(new ResourceLocation("rankine:feldspar_block"), RankineBlocks.ORTHOCLASE_FELDSPAR_BLOCK.get());

        blockRemappings.put(new ResourceLocation("rankine:gravel_concrete"), RankineBlocks.CONCRETE.get());
        blockRemappings.put(new ResourceLocation("rankine:uncolored_concrete"), RankineBlocks.CONCRETE.get());
        blockRemappings.put(new ResourceLocation("rankine:uncolored_concrete_slab"), RankineBlocks.CONCRETE_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:uncolored_concrete_stairs"), RankineBlocks.CONCRETE_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:uncolored_concrete_vertical_slab"), RankineBlocks.CONCRETE_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:uncolored_concrete_wall"), RankineBlocks.CONCRETE_WALL.get());

        blockRemappings.put(new ResourceLocation("rankine:high_beehive_oven_pit"), RankineBlocks.BEEHIVE_OVEN_PIT.get());
        blockRemappings.put(new ResourceLocation("rankine:ultra_high_beehive_oven_pit"), RankineBlocks.BEEHIVE_OVEN_PIT.get());

        blockRemappings.put(new ResourceLocation("rankine:kaolinite_block"), RankineBlocks.KAOLIN.get());

        blockRemappings.put(new ResourceLocation("rankine:laser_pylon_base"), RankineBlocks.STAINLESS_STEEL_SHEETMETAL.get());
        blockRemappings.put(new ResourceLocation("rankine:laser_pylon_top"), RankineBlocks.URANIUM_BLOCK.get());
        blockRemappings.put(new ResourceLocation("rankine:laser_pylon"), RankineBlocks.LONSDALEITE_DIAMOND_BLOCK.get());
        blockRemappings.put(new ResourceLocation("rankine:quarry_barrier"), Blocks.OBSIDIAN);

        blockRemappings.put(new ResourceLocation("rankine:limestone_nodule"), RankineBlocks.LIMESTONE.get());

        blockRemappings.put(new ResourceLocation("rankine:liquid_mercury_block"), RankineBlocks.LIQUID_MERCURY.get());

        blockRemappings.put(new ResourceLocation("rankine:mud"), RankineBlocks.SANDY_CLAY_LOAM_MUD.get());

        blockRemappings.put(new ResourceLocation("rankine:pozzolan_block"), RankineBlocks.POZZOLANA.get());

        blockRemappings.put(new ResourceLocation("rankine:salt_block"), RankineBlocks.SODIUM_CHLORIDE_BLOCK.get());

        blockRemappings.put(new ResourceLocation("rankine:checkered_dacite"),RankineBlocks.BLACK_DACITE.get());
        blockRemappings.put(new ResourceLocation("rankine:checkered_dacite_slab"), RankineBlocks.BLACK_DACITE_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:checkered_dacite_stairs"), RankineBlocks.BLACK_DACITE_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:checkered_dacite_vertical_slab"), RankineBlocks.BLACK_DACITE_VERTICAL_SLAB.get());

        blockRemappings.put(new ResourceLocation("rankine:checkered_porphyry"),RankineBlocks.RED_PORPHYRY.get());
        blockRemappings.put(new ResourceLocation("rankine:checkered_porphyry_slab"), RankineBlocks.RED_PORPHYRY_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:checkered_porphyry_stairs"), RankineBlocks.RED_PORPHYRY_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:checkered_porphyry_vertical_slab"), RankineBlocks.RED_PORPHYRY_VERTICAL_SLAB.get());

        blockRemappings.put(new ResourceLocation("rankine:polished_breccia"),RankineBlocks.BRECCIA.get());
        blockRemappings.put(new ResourceLocation("rankine:breccia_bricks"),RankineBlocks.BRECCIA.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_breccia_slab"),RankineBlocks.BRECCIA_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:breccia_bricks_slab"),RankineBlocks.BRECCIA_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_breccia_stairs"),RankineBlocks.BRECCIA_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:breccia_bricks_stairs"),RankineBlocks.BRECCIA_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_breccia_wall"),RankineBlocks.BRECCIA_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:breccia_bricks_wall"),RankineBlocks.BRECCIA_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:breccia_vertical_slab"),RankineBlocks.BRECCIA_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_breccia_vertical_slab"),RankineBlocks.BRECCIA_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:breccia_bricks_vertical_slab"),RankineBlocks.BRECCIA_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:breccia_pressure_plate"),Blocks.STONE_PRESSURE_PLATE);
        blockRemappings.put(new ResourceLocation("rankine:breccia_bricks_pressure_plate"),Blocks.STONE_PRESSURE_PLATE);
        blockRemappings.put(new ResourceLocation("rankine:breccia_button"),Blocks.STONE_BUTTON);

        blockRemappings.put(new ResourceLocation("rankine:polished_skarn"),RankineBlocks.SKARN.get());
        blockRemappings.put(new ResourceLocation("rankine:skarn_bricks"),RankineBlocks.SKARN.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_skarn_slab"),RankineBlocks.SKARN_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:skarn_bricks_slab"),RankineBlocks.SKARN_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_skarn_stairs"),RankineBlocks.SKARN_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:skarn_bricks_stairs"),RankineBlocks.SKARN_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_skarn_wall"),RankineBlocks.SKARN_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:skarn_bricks_wall"),RankineBlocks.SKARN_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:skarn_vertical_slab"),RankineBlocks.SKARN_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_skarn_vertical_slab"),RankineBlocks.SKARN_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:skarn_bricks_vertical_slab"),RankineBlocks.SKARN_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:skarn_pressure_plate"),Blocks.STONE_PRESSURE_PLATE);
        blockRemappings.put(new ResourceLocation("rankine:skarn_bricks_pressure_plate"),Blocks.STONE_PRESSURE_PLATE);
        blockRemappings.put(new ResourceLocation("rankine:skarn_button"),Blocks.STONE_BUTTON);

        blockRemappings.put(new ResourceLocation("rankine:polished_pumice"),RankineBlocks.PUMICE.get());
        blockRemappings.put(new ResourceLocation("rankine:pumice_bricks"),RankineBlocks.PUMICE.get());
        blockRemappings.put(new ResourceLocation("rankine:pumice_pressure_plate"),Blocks.STONE_PRESSURE_PLATE);
        blockRemappings.put(new ResourceLocation("rankine:pumice_bricks_pressure_plate"),Blocks.STONE_PRESSURE_PLATE);
        blockRemappings.put(new ResourceLocation("rankine:pumice_button"),Blocks.STONE_BUTTON);

        blockRemappings.put(new ResourceLocation("rankine:polished_scoria"),RankineBlocks.SCORIA.get());
        blockRemappings.put(new ResourceLocation("rankine:scoria_bricks"),RankineBlocks.SCORIA.get());
        blockRemappings.put(new ResourceLocation("rankine:scoria_pressure_plate"),Blocks.STONE_PRESSURE_PLATE);
        blockRemappings.put(new ResourceLocation("rankine:scoria_bricks_pressure_plate"),Blocks.STONE_PRESSURE_PLATE);
        blockRemappings.put(new ResourceLocation("rankine:scoria_button"),Blocks.STONE_BUTTON);

        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone"),RankineBlocks.LIMESTONE.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_tufa_limestone"),RankineBlocks.POLISHED_LIMESTONE.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_bricks"),RankineBlocks.LIMESTONE_BRICKS.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_slab"),RankineBlocks.LIMESTONE_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_tufa_limestone_slab"),RankineBlocks.POLISHED_LIMESTONE_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_bricks_slab"),RankineBlocks.LIMESTONE_BRICKS_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_stairs"),RankineBlocks.LIMESTONE_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_tufa_limestone_stairs"),RankineBlocks.POLISHED_LIMESTONE_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_bricks_stairs"),RankineBlocks.LIMESTONE_BRICKS_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_wall"),RankineBlocks.LIMESTONE_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_tufa_limestone_wall"),RankineBlocks.POLISHED_LIMESTONE_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_bricks_wall"),RankineBlocks.LIMESTONE_BRICKS_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_vertical_slab"),RankineBlocks.LIMESTONE_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_tufa_limestone_vertical_slab"),RankineBlocks.POLISHED_LIMESTONE_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_bricks_vertical_slab"),RankineBlocks.LIMESTONE_BRICKS_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_pressure_plate"),RankineBlocks.LIMESTONE_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_bricks_pressure_plate"),RankineBlocks.LIMESTONE_BRICKS_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:tufa_limestone_button"),RankineBlocks.LIMESTONE_BUTTON.get());


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

        blockRemappings.put(new ResourceLocation("rankine:pyroxene_gabbro"),RankineBlocks.GABBRO.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_pyroxene_gabbro"),RankineBlocks.POLISHED_GABBRO.get());
        blockRemappings.put(new ResourceLocation("rankine:pyroxene_gabbro_bricks"),RankineBlocks.GABBRO_BRICKS.get());
        blockRemappings.put(new ResourceLocation("rankine:pyroxene_gabbro_slab"),RankineBlocks.GABBRO_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_pyroxene_gabbro_slab"),RankineBlocks.POLISHED_GABBRO_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:pyroxene_gabbro_bricks_slab"),RankineBlocks.GABBRO_BRICKS_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:pyroxene_gabbro_stairs"),RankineBlocks.GABBRO_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_pyroxene_gabbro_stairs"),RankineBlocks.POLISHED_GABBRO_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:pyroxene_gabbro_bricks_stairs"),RankineBlocks.GABBRO_BRICKS_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:pyroxene_gabbro_wall"),RankineBlocks.GABBRO_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_pyroxene_gabbro_wall"),RankineBlocks.POLISHED_GABBRO_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:pyroxene_gabbro_bricks_wall"),RankineBlocks.GABBRO_BRICKS_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:pyroxene_gabbro_vertical_slab"),RankineBlocks.GABBRO_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_pyroxene_gabbro_vertical_slab"),RankineBlocks.POLISHED_GABBRO_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:pyroxene_gabbro_bricks_vertical_slab"),RankineBlocks.GABBRO_BRICKS_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:pyroxene_gabbro_pressure_plate"),RankineBlocks.GABBRO_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:pyroxene_gabbro_bricks_pressure_plate"),RankineBlocks.GABBRO_BRICKS_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:pyroxene_gabbro_button"),RankineBlocks.GABBRO_BUTTON.get());

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
        blockRemappings.put(new ResourceLocation("rankine:polished_bridgmanite"),RankineBlocks.POLISHED_BRIDGMANHAM.get());
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
        blockRemappings.put(new ResourceLocation("rankine:bridgmanite_button"),RankineBlocks.BRIDGMANHAM_BUTTON.get());

        blockRemappings.put(new ResourceLocation("rankine:ferropericlase"),RankineBlocks.SOMMANITE.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_ferropericlase"),RankineBlocks.POLISHED_SOMMANITE.get());
        blockRemappings.put(new ResourceLocation("rankine:ferropericlase_bricks"),RankineBlocks.SOMMANITE_BRICKS.get());
        blockRemappings.put(new ResourceLocation("rankine:ferropericlase_slab"),RankineBlocks.SOMMANITE_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_ferropericlase_slab"),RankineBlocks.POLISHED_SOMMANITE_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:ferropericlase_bricks_slab"),RankineBlocks.SOMMANITE_BRICKS_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:ferropericlase_stairs"),RankineBlocks.SOMMANITE_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_ferropericlase_stairs"),RankineBlocks.POLISHED_SOMMANITE_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:ferropericlase_bricks_stairs"),RankineBlocks.SOMMANITE_BRICKS_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:ferropericlase_wall"),RankineBlocks.SOMMANITE_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_ferropericlase_wall"),RankineBlocks.POLISHED_SOMMANITE_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:ferropericlase_bricks_wall"),RankineBlocks.SOMMANITE_BRICKS_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:ferropericlase_vertical_slab"),RankineBlocks.SOMMANITE_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_ferropericlase_vertical_slab"),RankineBlocks.POLISHED_SOMMANITE_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:ferropericlase_bricks_vertical_slab"),RankineBlocks.SOMMANITE_BRICKS_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:ferropericlase_pressure_plate"),RankineBlocks.SOMMANITE_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:ferropericlase_bricks_pressure_plate"),RankineBlocks.SOMMANITE_BRICKS_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:ferropericlase_button"),RankineBlocks.SOMMANITE_BUTTON.get());

        blockRemappings.put(new ResourceLocation("rankine:perovskite"),RankineBlocks.POST_PEROVSKITE.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_perovskite"),RankineBlocks.POLISHED_POST_PEROVSKITE.get());
        blockRemappings.put(new ResourceLocation("rankine:perovskite_bricks"),RankineBlocks.POST_PEROVSKITE_BRICKS.get());
        blockRemappings.put(new ResourceLocation("rankine:perovskite_slab"),RankineBlocks.POST_PEROVSKITE_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_perovskite_slab"),RankineBlocks.POLISHED_POST_PEROVSKITE_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:perovskite_bricks_slab"),RankineBlocks.POST_PEROVSKITE_BRICKS_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:perovskite_stairs"),RankineBlocks.POST_PEROVSKITE_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_perovskite_stairs"),RankineBlocks.POLISHED_POST_PEROVSKITE_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:perovskite_bricks_stairs"),RankineBlocks.POST_PEROVSKITE_BRICKS_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:perovskite_wall"),RankineBlocks.POST_PEROVSKITE_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_perovskite_wall"),RankineBlocks.POLISHED_POST_PEROVSKITE_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:perovskite_bricks_wall"),RankineBlocks.POST_PEROVSKITE_BRICKS_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:perovskite_vertical_slab"),RankineBlocks.POST_PEROVSKITE_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_perovskite_vertical_slab"),RankineBlocks.POLISHED_POST_PEROVSKITE_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:perovskite_bricks_vertical_slab"),RankineBlocks.POST_PEROVSKITE_BRICKS_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:perovskite_pressure_plate"),RankineBlocks.POST_PEROVSKITE_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:perovskite_bricks_pressure_plate"),RankineBlocks.POST_PEROVSKITE_BRICKS_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:perovskite_button"),RankineBlocks.POST_PEROVSKITE_BUTTON.get());

        blockRemappings.put(new ResourceLocation("rankine:ringwoodite"),RankineBlocks.RINGWOODINE.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_ringwoodite"),RankineBlocks.POLISHED_RINGWOODINE.get());
        blockRemappings.put(new ResourceLocation("rankine:ringwoodite_bricks"),RankineBlocks.RINGWOODINE_BRICKS.get());
        blockRemappings.put(new ResourceLocation("rankine:ringwoodite_slab"),RankineBlocks.RINGWOODINE_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_ringwoodite_slab"),RankineBlocks.POLISHED_RINGWOODINE_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:ringwoodite_bricks_slab"),RankineBlocks.RINGWOODINE_BRICKS_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:ringwoodite_stairs"),RankineBlocks.RINGWOODINE_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_ringwoodite_stairs"),RankineBlocks.POLISHED_RINGWOODINE_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:ringwoodite_bricks_stairs"),RankineBlocks.RINGWOODINE_BRICKS_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:ringwoodite_wall"),RankineBlocks.RINGWOODINE_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_ringwoodite_wall"),RankineBlocks.POLISHED_RINGWOODINE_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:ringwoodite_bricks_wall"),RankineBlocks.RINGWOODINE_BRICKS_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:ringwoodite_vertical_slab"),RankineBlocks.RINGWOODINE_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_ringwoodite_vertical_slab"),RankineBlocks.POLISHED_RINGWOODINE_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:ringwoodite_bricks_vertical_slab"),RankineBlocks.RINGWOODINE_BRICKS_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:ringwoodite_pressure_plate"),RankineBlocks.RINGWOODINE_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:ringwoodite_bricks_pressure_plate"),RankineBlocks.RINGWOODINE_BRICKS_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:ringwoodite_button"),RankineBlocks.RINGWOODINE_BUTTON.get());

        blockRemappings.put(new ResourceLocation("rankine:wadsleyite"),RankineBlocks.WADSLEYONE.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_wadsleyite"),RankineBlocks.POLISHED_WADSLEYONE.get());
        blockRemappings.put(new ResourceLocation("rankine:wadsleyite_bricks"),RankineBlocks.WADSLEYONE_BRICKS.get());
        blockRemappings.put(new ResourceLocation("rankine:wadsleyite_slab"),RankineBlocks.WADSLEYONE_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_wadsleyite_slab"),RankineBlocks.POLISHED_WADSLEYONE_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:wadsleyite_bricks_slab"),RankineBlocks.WADSLEYONE_BRICKS_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:wadsleyite_stairs"),RankineBlocks.WADSLEYONE_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_wadsleyite_stairs"),RankineBlocks.POLISHED_WADSLEYONE_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:wadsleyite_bricks_stairs"),RankineBlocks.WADSLEYONE_BRICKS_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:wadsleyite_wall"),RankineBlocks.WADSLEYONE_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_wadsleyite_wall"),RankineBlocks.POLISHED_WADSLEYONE_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:wadsleyite_bricks_wall"),RankineBlocks.WADSLEYONE_BRICKS_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:wadsleyite_vertical_slab"),RankineBlocks.WADSLEYONE_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_wadsleyite_vertical_slab"),RankineBlocks.POLISHED_WADSLEYONE_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:wadsleyite_bricks_vertical_slab"),RankineBlocks.WADSLEYONE_BRICKS_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:wadsleyite_pressure_plate"),RankineBlocks.WADSLEYONE_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:wadsleyite_bricks_pressure_plate"),RankineBlocks.WADSLEYONE_BRICKS_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:wadsleyite_button"),RankineBlocks.WADSLEYONE_BUTTON.get());


        blockRemappings.put(new ResourceLocation("rankine:peridodite"),RankineBlocks.DUNITE.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_peridodite"),RankineBlocks.POLISHED_DUNITE.get());
        blockRemappings.put(new ResourceLocation("rankine:peridodite_bricks"),RankineBlocks.DUNITE_BRICKS.get());
        blockRemappings.put(new ResourceLocation("rankine:peridodite_slab"),RankineBlocks.DUNITE_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_peridodite_slab"),RankineBlocks.POLISHED_DUNITE_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:peridodite_bricks_slab"),RankineBlocks.DUNITE_BRICKS_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:peridodite_stairs"),RankineBlocks.DUNITE_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_peridodite_stairs"),RankineBlocks.POLISHED_DUNITE_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:peridodite_bricks_stairs"),RankineBlocks.DUNITE_BRICKS_STAIRS.get());
        blockRemappings.put(new ResourceLocation("rankine:peridodite_wall"),RankineBlocks.DUNITE_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_peridodite_wall"),RankineBlocks.POLISHED_DUNITE_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:peridodite_bricks_wall"),RankineBlocks.DUNITE_BRICKS_WALL.get());
        blockRemappings.put(new ResourceLocation("rankine:peridodite_vertical_slab"),RankineBlocks.DUNITE_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:polished_peridodite_vertical_slab"),RankineBlocks.POLISHED_DUNITE_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:peridodite_bricks_vertical_slab"),RankineBlocks.DUNITE_BRICKS_VERTICAL_SLAB.get());
        blockRemappings.put(new ResourceLocation("rankine:peridodite_pressure_plate"),RankineBlocks.DUNITE_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:peridodite_bricks_pressure_plate"),RankineBlocks.DUNITE_BRICKS_PRESSURE_PLATE.get());
        blockRemappings.put(new ResourceLocation("rankine:peridodite_button"),RankineBlocks.DUNITE_BUTTON.get());

        return blockRemappings;
    }
}
