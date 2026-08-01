package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * RegistryEvent.MissingMappings / MissingMappingsEvent is GONE in NeoForge 1.21.1.
 * The replacement is registry aliases ({@code IRegistryExtension#addAlias}): any lookup of the old
 * name (including world save data) resolves to the new name when the old one is absent.
 */
@EventBusSubscriber(modid = ProjectRankine.MODID, bus = EventBusSubscriber.Bus.MOD)
public class RankineRemappings {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(RankineRemappings::registerAliases);
    }

    public static void registerAliases() {
        for (Map.Entry<ResourceLocation, Item> entry : getItemRemappings().entrySet()) {
            BuiltInRegistries.ITEM.addAlias(entry.getKey(), BuiltInRegistries.ITEM.getKey(entry.getValue()));
        }
        for (Map.Entry<ResourceLocation, Block> entry : getBlockRemappings().entrySet()) {
            BuiltInRegistries.BLOCK.addAlias(entry.getKey(), BuiltInRegistries.BLOCK.getKey(entry.getValue()));
            // The 1.18.2 handler also remapped missing ITEM entries through the block map (block items).
            Item blockItem = entry.getValue().asItem();
            if (blockItem != Items.AIR) {
                BuiltInRegistries.ITEM.addAlias(entry.getKey(), BuiltInRegistries.ITEM.getKey(blockItem));
            }
        }
    }

    public static Map<ResourceLocation, Item> itemRemappings = new HashMap<>();

    public static Map<ResourceLocation, Item> getItemRemappings() {

        itemRemappings.put(ResourceLocation.parse("rankine:piston_crusher"), Items.PISTON);
        itemRemappings.put(ResourceLocation.parse("rankine:gyratory_crusher"), Items.PISTON);

        itemRemappings.put(ResourceLocation.parse("rankine:power_cell_1"),RankineItems.MAGNESIUM_BATTERY.get());
        itemRemappings.put(ResourceLocation.parse("rankine:power_cell_2"),RankineItems.LEAD_ACID_BATTERY.get());
        itemRemappings.put(ResourceLocation.parse("rankine:power_cell_3"),RankineItems.VANADIUM_REDOX_BATTERY.get());
        itemRemappings.put(ResourceLocation.parse("rankine:power_cell_4"),RankineItems.ZINC_BROMINE_BATTERY.get());
        itemRemappings.put(ResourceLocation.parse("rankine:power_cell_5"),RankineItems.SODIUM_SULFUR_BATTERY.get());
        itemRemappings.put(ResourceLocation.parse("rankine:power_cell_6"),RankineItems.LITHIUM_ION_BATTERY.get());

        itemRemappings.put(ResourceLocation.parse("rankine:haste_pendant"),RankineItems.TOTEM_OF_HASTENING.get());
        itemRemappings.put(ResourceLocation.parse("rankine:luck_pendant"),RankineItems.TOTEM_OF_PROMISING.get());
        itemRemappings.put(ResourceLocation.parse("rankine:health_pendant"),RankineItems.TOTEM_OF_ENDURING.get());
        itemRemappings.put(ResourceLocation.parse("rankine:speed_pendant"),RankineItems.TOTEM_OF_TIMESAVING.get());
        itemRemappings.put(ResourceLocation.parse("rankine:levitation_pendant"),RankineItems.TOTEM_OF_LEVITATING.get());
        itemRemappings.put(ResourceLocation.parse("rankine:repulsion_pendant"),RankineItems.TOTEM_OF_REPULSING.get());

        itemRemappings.put(ResourceLocation.parse("rankine:feldspar"),RankineItems.ORTHOCLASE_FELDSPAR.get());
        itemRemappings.put(ResourceLocation.parse("rankine:salt"),RankineItems.SODIUM_CHLORIDE.get());
        itemRemappings.put(ResourceLocation.parse("rankine:mica"),RankineItems.BIOTITE.get());

        itemRemappings.put(ResourceLocation.parse("rankine:alnico_alloy"),RankineItems.ALNICO_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:amalgam_alloy"),RankineItems.AMALGAM_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:black_gold_alloy"),RankineItems.BLACK_GOLD_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:blue_gold_alloy"),RankineItems.BLUE_GOLD_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:brass_alloy"),RankineItems.BRASS_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:bronze_alloy"),RankineItems.BRONZE_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:cast_iron_alloy"),RankineItems.CAST_IRON_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:cobalt_superalloy"),RankineItems.COBALT_SUPERALLOY_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:duralumin_alloy"),RankineItems.DURALUMIN_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:galinstan_alloy"),RankineItems.GALINSTAN_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:invar_alloy"),RankineItems.INVAR_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:magnesium_alloy"),RankineItems.MAGNESIUM_ALLOY_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:mischmetal_alloy"),RankineItems.MISCHMETAL_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:nickel_silver_alloy"),RankineItems.NICKEL_SILVER_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:nickel_superalloy"),RankineItems.NICKEL_SUPERALLOY_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:nitinol_alloy"),RankineItems.NITINOL_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:osmiridium_alloy"),RankineItems.OSMIRIDIUM_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:pewter_alloy"),RankineItems.PEWTER_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:purple_gold_alloy"),RankineItems.PURPLE_GOLD_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:rose_gold_alloy"),RankineItems.ROSE_GOLD_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:rose_metal_alloy"),RankineItems.ROSE_METAL_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:sodium_potassium_alloy"),RankineItems.SODIUM_POTASSIUM_ALLOY_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:solder_alloy"),RankineItems.SOLDER.get());
        itemRemappings.put(ResourceLocation.parse("rankine:steel_alloy"),RankineItems.STEEL_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:stainless_steel_alloy"),RankineItems.STAINLESS_STEEL_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:sterling_silver_alloy"),RankineItems.STERLING_SILVER_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:titanium_alloy"),RankineItems.TITANIUM_ALLOY_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:tungsten_heavy_alloy"),RankineItems.TUNGSTEN_HEAVY_ALLOY_INGOT.get());
        itemRemappings.put(ResourceLocation.parse("rankine:white_gold_alloy"),RankineItems.WHITE_GOLD_INGOT.get());

        itemRemappings.put(ResourceLocation.parse("rankine:corn_flour"),RankineItems.CORN_GRAIN.get());
        itemRemappings.put(ResourceLocation.parse("rankine:rice_flour"),RankineItems.RICE_GRAIN.get());

        itemRemappings.put(ResourceLocation.parse("rankine:dry_mortar"),RankineItems.CEMENT_MIX.get());
        itemRemappings.put(ResourceLocation.parse("rankine:kaolinite_ball"),RankineItems.KAOLINITE.get());

        itemRemappings.put(ResourceLocation.parse("rankine:element_transmuter"),RankineItems.MERCURY.get());
        itemRemappings.put(ResourceLocation.parse("rankine:up_transmuter"),RankineItems.MERCURY.get());
        itemRemappings.put(ResourceLocation.parse("rankine:down_transmuter"),RankineItems.MERCURY.get());
        itemRemappings.put(ResourceLocation.parse("rankine:left_transmuter"),RankineItems.MERCURY.get());
        itemRemappings.put(ResourceLocation.parse("rankine:right_transmuter"),RankineItems.MERCURY.get());

        itemRemappings.put(ResourceLocation.parse("rankine:antimony_ingot"),RankineItems.ANTIMONY.get());
        itemRemappings.put(ResourceLocation.parse("rankine:arsenic_ingot"),RankineItems.ARSENIC.get());
        itemRemappings.put(ResourceLocation.parse("rankine:boron_ingot"),RankineItems.BORON.get());
        itemRemappings.put(ResourceLocation.parse("rankine:bromine_ingot"),RankineItems.BROMINE.get());
        itemRemappings.put(ResourceLocation.parse("rankine:carbon_ingot"),RankineItems.CARBON.get());
        itemRemappings.put(ResourceLocation.parse("rankine:germanium_ingot"),RankineItems.GERMANIUM.get());
        itemRemappings.put(ResourceLocation.parse("rankine:iodine_ingot"),RankineItems.IODINE.get());
        itemRemappings.put(ResourceLocation.parse("rankine:mercury_ingot"),RankineItems.MERCURY.get());
        itemRemappings.put(ResourceLocation.parse("rankine:selenium_ingot"),RankineItems.SELENIUM.get());
        itemRemappings.put(ResourceLocation.parse("rankine:tellurium_ingot"),RankineItems.TELLURIUM.get());

        itemRemappings.put(ResourceLocation.parse("rankine:metal_knife"),RankineItems.INVAR_KNIFE.get());

        itemRemappings.put(ResourceLocation.parse("rankine:stone_hammer"),RankineItems.STONE_HAMMER.get());
        return itemRemappings;
    }

    public static Map<ResourceLocation, Block> blockRemappings = new HashMap<>();

    public static Map<ResourceLocation, Block> getBlockRemappings() {

        blockRemappings.put(ResourceLocation.parse("rankine:aluminum_ladder"),RankineBlocks.DURALUMIN_LADDER.get());

        blockRemappings.put(ResourceLocation.parse("rankine:piston_crusher"), Blocks.PISTON);
        blockRemappings.put(ResourceLocation.parse("rankine:gyratory_crusher"), Blocks.PISTON);

        blockRemappings.put(ResourceLocation.parse("rankine:aquamarine_ore"), RankineBlocks.BERYL_ORE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:columbite_ore"), RankineBlocks.COLTAN_ORE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:fluorite_ore"), Blocks.STONE);
        blockRemappings.put(ResourceLocation.parse("rankine:majorite_ore"), Blocks.STONE);
        blockRemappings.put(ResourceLocation.parse("rankine:moissanite_ore"), Blocks.STONE);
        blockRemappings.put(ResourceLocation.parse("rankine:native_aluminum_ore"), Blocks.STONE);
        blockRemappings.put(ResourceLocation.parse("rankine:native_copper_ore"), Blocks.STONE);
        blockRemappings.put(ResourceLocation.parse("rankine:opal_ore"), Blocks.STONE);
        blockRemappings.put(ResourceLocation.parse("rankine:pink_halite_ore"), RankineBlocks.SYLVINITE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:halite_ore"), RankineBlocks.SYLVINITE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:quartz_ore"), RankineBlocks.NETHER_QUARTZ_ORE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:tantalite_ore"), RankineBlocks.COLTAN_ORE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:vanadinite_ore"), RankineBlocks.GALENA_ORE.get());

        blockRemappings.put(ResourceLocation.parse("rankine:dacitic_tuff"), RankineBlocks.ANDESITIC_TUFF.get());

        blockRemappings.put(ResourceLocation.parse("rankine:enstatite"), RankineBlocks.ENSTATITE_CHONDRITE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:enstatite_paver"), RankineBlocks.ENSTATITE_CHONDRITE_BRICKS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:meteorite_paver"), RankineBlocks.METEORITE_BRICKS.get());

        blockRemappings.put(ResourceLocation.parse("rankine:etched_glass0"), Blocks.GLASS);
        blockRemappings.put(ResourceLocation.parse("rankine:etched_glass1"), Blocks.GLASS);
        blockRemappings.put(ResourceLocation.parse("rankine:etched_glass2"), Blocks.GLASS);

        blockRemappings.put(ResourceLocation.parse("rankine:feldspar_block"), RankineBlocks.ORTHOCLASE_FELDSPAR_BLOCK.get());
        blockRemappings.put(ResourceLocation.parse("rankine:mica_block"), RankineBlocks.BIOTITE_BLOCK.get());

        blockRemappings.put(ResourceLocation.parse("rankine:gravel_concrete"), RankineBlocks.CONCRETE.getCementBlock());
        blockRemappings.put(ResourceLocation.parse("rankine:uncolored_concrete"), RankineBlocks.CONCRETE.getCementBlock());
        blockRemappings.put(ResourceLocation.parse("rankine:uncolored_concrete_slab"), RankineBlocks.CONCRETE.getCementSlab());
        blockRemappings.put(ResourceLocation.parse("rankine:uncolored_concrete_stairs"), RankineBlocks.CONCRETE.getCementStairs());
        blockRemappings.put(ResourceLocation.parse("rankine:uncolored_concrete_wall"), RankineBlocks.CONCRETE.getCementWall());

        blockRemappings.put(ResourceLocation.parse("rankine:high_beehive_oven_pit"), RankineBlocks.BEEHIVE_OVEN_PIT.get());
        blockRemappings.put(ResourceLocation.parse("rankine:ultra_high_beehive_oven_pit"), RankineBlocks.BEEHIVE_OVEN_PIT.get());

        blockRemappings.put(ResourceLocation.parse("rankine:kaolinite_block"), RankineBlocks.KAOLIN.get());

        blockRemappings.put(ResourceLocation.parse("rankine:laser_pylon_base"), RankineBlocks.STAINLESS_STEEL_SHEETMETAL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:laser_pylon_top"), RankineBlocks.URANIUM_BLOCK.get());
        blockRemappings.put(ResourceLocation.parse("rankine:laser_pylon"), RankineBlocks.LONSDALEITE_DIAMOND_BLOCK.get());
        blockRemappings.put(ResourceLocation.parse("rankine:quarry_barrier"), Blocks.OBSIDIAN);


        blockRemappings.put(ResourceLocation.parse("rankine:liquid_mercury_block"), RankineBlocks.LIQUID_MERCURY.get());

        blockRemappings.put(ResourceLocation.parse("rankine:mud"), RankineBlocks.SANDY_CLAY_LOAM_MUD.get());

        blockRemappings.put(ResourceLocation.parse("rankine:pozzolan_block"), RankineBlocks.POZZOLANA.get());

        blockRemappings.put(ResourceLocation.parse("rankine:salt_block"), RankineBlocks.SODIUM_CHLORIDE_BLOCK.get());


        blockRemappings.put(ResourceLocation.parse("rankine:polished_breccia"),RankineBlocks.BRECCIA.get());
        blockRemappings.put(ResourceLocation.parse("rankine:breccia_bricks"),RankineBlocks.BRECCIA.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_breccia_slab"),RankineBlocks.BRECCIA_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:breccia_bricks_slab"),RankineBlocks.BRECCIA_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_breccia_stairs"),RankineBlocks.BRECCIA_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:breccia_bricks_stairs"),RankineBlocks.BRECCIA_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_breccia_wall"),RankineBlocks.BRECCIA_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:breccia_bricks_wall"),RankineBlocks.BRECCIA_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:breccia_pressure_plate"),Blocks.STONE_PRESSURE_PLATE);
        blockRemappings.put(ResourceLocation.parse("rankine:breccia_bricks_pressure_plate"),Blocks.STONE_PRESSURE_PLATE);
        blockRemappings.put(ResourceLocation.parse("rankine:breccia_button"),Blocks.STONE_BUTTON);

        blockRemappings.put(ResourceLocation.parse("rankine:polished_skarn"),RankineBlocks.SKARN.get());
        blockRemappings.put(ResourceLocation.parse("rankine:skarn_bricks"),RankineBlocks.SKARN.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_skarn_slab"),RankineBlocks.SKARN_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:skarn_bricks_slab"),RankineBlocks.SKARN_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_skarn_stairs"),RankineBlocks.SKARN_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:skarn_bricks_stairs"),RankineBlocks.SKARN_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_skarn_wall"),RankineBlocks.SKARN_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:skarn_bricks_wall"),RankineBlocks.SKARN_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:skarn_pressure_plate"),Blocks.STONE_PRESSURE_PLATE);
        blockRemappings.put(ResourceLocation.parse("rankine:skarn_bricks_pressure_plate"),Blocks.STONE_PRESSURE_PLATE);
        blockRemappings.put(ResourceLocation.parse("rankine:skarn_button"),Blocks.STONE_BUTTON);

        blockRemappings.put(ResourceLocation.parse("rankine:polished_pumice"),RankineBlocks.PUMICE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:pumice_bricks"),RankineBlocks.PUMICE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:pumice_pressure_plate"),Blocks.STONE_PRESSURE_PLATE);
        blockRemappings.put(ResourceLocation.parse("rankine:pumice_bricks_pressure_plate"),Blocks.STONE_PRESSURE_PLATE);
        blockRemappings.put(ResourceLocation.parse("rankine:pumice_button"),Blocks.STONE_BUTTON);

        blockRemappings.put(ResourceLocation.parse("rankine:polished_scoria"),RankineBlocks.SCORIA.get());
        blockRemappings.put(ResourceLocation.parse("rankine:scoria_bricks"),RankineBlocks.SCORIA.get());
        blockRemappings.put(ResourceLocation.parse("rankine:scoria_pressure_plate"),Blocks.STONE_PRESSURE_PLATE);
        blockRemappings.put(ResourceLocation.parse("rankine:scoria_bricks_pressure_plate"),Blocks.STONE_PRESSURE_PLATE);
        blockRemappings.put(ResourceLocation.parse("rankine:scoria_button"),Blocks.STONE_BUTTON);
/*
        blockRemappings.put(ResourceLocation.parse("rankine:tufa_limestone"),RankineBlocks.LIMESTONE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_tufa_limestone"),RankineBlocks.POLISHED_LIMESTONE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:tufa_limestone_bricks"),RankineBlocks.LIMESTONE_BRICKS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:tufa_limestone_slab"),RankineBlocks.LIMESTONE_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_tufa_limestone_slab"),RankineBlocks.POLISHED_LIMESTONE_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:tufa_limestone_bricks_slab"),RankineBlocks.LIMESTONE_BRICKS_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:tufa_limestone_stairs"),RankineBlocks.LIMESTONE_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_tufa_limestone_stairs"),RankineBlocks.POLISHED_LIMESTONE_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:tufa_limestone_bricks_stairs"),RankineBlocks.LIMESTONE_BRICKS_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:tufa_limestone_wall"),RankineBlocks.LIMESTONE_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_tufa_limestone_wall"),RankineBlocks.POLISHED_LIMESTONE_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:tufa_limestone_bricks_wall"),RankineBlocks.LIMESTONE_BRICKS_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:tufa_limestone_pressure_plate"),RankineBlocks.LIMESTONE_PRESSURE_PLATE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:tufa_limestone_bricks_pressure_plate"),RankineBlocks.LIMESTONE_BRICKS_PRESSURE_PLATE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:tufa_limestone_button"),RankineBlocks.LIMESTONE_BUTTON.get());


        blockRemappings.put(ResourceLocation.parse("rankine:carbonaceous_shale"),RankineBlocks.SHALE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_carbonaceous_shale"),RankineBlocks.POLISHED_SHALE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:carbonaceous_shale_bricks"),RankineBlocks.SHALE_BRICKS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:carbonaceous_shale_slab"),RankineBlocks.SHALE_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_carbonaceous_shale_slab"),RankineBlocks.POLISHED_SHALE_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:carbonaceous_shale_bricks_slab"),RankineBlocks.SHALE_BRICKS_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:carbonaceous_shale_stairs"),RankineBlocks.SHALE_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_carbonaceous_shale_stairs"),RankineBlocks.POLISHED_SHALE_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:carbonaceous_shale_bricks_stairs"),RankineBlocks.SHALE_BRICKS_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:carbonaceous_shale_wall"),RankineBlocks.SHALE_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_carbonaceous_shale_wall"),RankineBlocks.POLISHED_SHALE_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:carbonaceous_shale_bricks_wall"),RankineBlocks.SHALE_BRICKS_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:carbonaceous_shale_pressure_plate"),RankineBlocks.SHALE_PRESSURE_PLATE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:carbonaceous_shale_bricks_pressure_plate"),RankineBlocks.SHALE_BRICKS_PRESSURE_PLATE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:carbonaceous_shale_button"),RankineBlocks.SHALE_BUTTON.get());

        blockRemappings.put(ResourceLocation.parse("rankine:pyroxene_gabbro"),RankineBlocks.GABBRO.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_pyroxene_gabbro"),RankineBlocks.POLISHED_GABBRO.get());
        blockRemappings.put(ResourceLocation.parse("rankine:pyroxene_gabbro_bricks"),RankineBlocks.GABBRO_BRICKS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:pyroxene_gabbro_slab"),RankineBlocks.GABBRO_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_pyroxene_gabbro_slab"),RankineBlocks.POLISHED_GABBRO_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:pyroxene_gabbro_bricks_slab"),RankineBlocks.GABBRO_BRICKS_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:pyroxene_gabbro_stairs"),RankineBlocks.GABBRO_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_pyroxene_gabbro_stairs"),RankineBlocks.POLISHED_GABBRO_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:pyroxene_gabbro_bricks_stairs"),RankineBlocks.GABBRO_BRICKS_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:pyroxene_gabbro_wall"),RankineBlocks.GABBRO_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_pyroxene_gabbro_wall"),RankineBlocks.POLISHED_GABBRO_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:pyroxene_gabbro_bricks_wall"),RankineBlocks.GABBRO_BRICKS_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:pyroxene_gabbro_pressure_plate"),RankineBlocks.GABBRO_PRESSURE_PLATE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:pyroxene_gabbro_bricks_pressure_plate"),RankineBlocks.GABBRO_BRICKS_PRESSURE_PLATE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:pyroxene_gabbro_button"),RankineBlocks.GABBRO_BUTTON.get());

        blockRemappings.put(ResourceLocation.parse("rankine:quartz_sandstone"),RankineBlocks.ITACOLUMITE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_quartz_sandstone"),RankineBlocks.POLISHED_ITACOLUMITE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:quartz_sandstone_bricks"),RankineBlocks.ITACOLUMITE_BRICKS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:quartz_sandstone_slab"),RankineBlocks.ITACOLUMITE_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_quartz_sandstone_slab"),RankineBlocks.POLISHED_ITACOLUMITE_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:quartz_sandstone_bricks_slab"),RankineBlocks.ITACOLUMITE_BRICKS_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:quartz_sandstone_stairs"),RankineBlocks.ITACOLUMITE_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_quartz_sandstone_stairs"),RankineBlocks.POLISHED_ITACOLUMITE_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:quartz_sandstone_bricks_stairs"),RankineBlocks.ITACOLUMITE_BRICKS_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:quartz_sandstone_wall"),RankineBlocks.ITACOLUMITE_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_quartz_sandstone_wall"),RankineBlocks.POLISHED_ITACOLUMITE_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:quartz_sandstone_bricks_wall"),RankineBlocks.ITACOLUMITE_BRICKS_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:quartz_sandstone_pressure_plate"),RankineBlocks.ITACOLUMITE_PRESSURE_PLATE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:quartz_sandstone_bricks_pressure_plate"),RankineBlocks.ITACOLUMITE_BRICKS_PRESSURE_PLATE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:quartz_sandstone_button"),RankineBlocks.ITACOLUMITE_BUTTON.get());

        blockRemappings.put(ResourceLocation.parse("rankine:arkose_sandstone"),RankineBlocks.ARKOSE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_arkose_sandstone"),RankineBlocks.POLISHED_ARKOSE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:arkose_sandstone_bricks"),RankineBlocks.ARKOSE_BRICKS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:arkose_sandstone_slab"),RankineBlocks.ARKOSE_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_arkose_sandstone_slab"),RankineBlocks.POLISHED_ARKOSE_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:arkose_sandstone_bricks_slab"),RankineBlocks.ARKOSE_BRICKS_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:arkose_sandstone_stairs"),RankineBlocks.ARKOSE_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_arkose_sandstone_stairs"),RankineBlocks.POLISHED_ARKOSE_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:arkose_sandstone_bricks_stairs"),RankineBlocks.ARKOSE_BRICKS_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:arkose_sandstone_wall"),RankineBlocks.ARKOSE_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_arkose_sandstone_wall"),RankineBlocks.POLISHED_ARKOSE_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:arkose_sandstone_bricks_wall"),RankineBlocks.ARKOSE_BRICKS_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:arkose_sandstone_pressure_plate"),RankineBlocks.ARKOSE_PRESSURE_PLATE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:arkose_sandstone_bricks_pressure_plate"),RankineBlocks.ARKOSE_BRICKS_PRESSURE_PLATE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:arkose_sandstone_button"),RankineBlocks.ARKOSE_BUTTON.get());

        blockRemappings.put(ResourceLocation.parse("rankine:bridgmanite"),RankineBlocks.BRIDGMANHAM.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_bridgmanite"),RankineBlocks.POLISHED_BRIDGMANHAM.get());
        blockRemappings.put(ResourceLocation.parse("rankine:bridgmanite_bricks"),RankineBlocks.BRIDGMANHAM_BRICKS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:bridgmanite_slab"),RankineBlocks.BRIDGMANHAM_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_bridgmanite_slab"),RankineBlocks.POLISHED_BRIDGMANHAM_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:bridgmanite_bricks_slab"),RankineBlocks.BRIDGMANHAM_BRICKS_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:bridgmanite_stairs"),RankineBlocks.BRIDGMANHAM_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_bridgmanite_stairs"),RankineBlocks.POLISHED_BRIDGMANHAM_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:bridgmanite_bricks_stairs"),RankineBlocks.BRIDGMANHAM_BRICKS_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:bridgmanite_wall"),RankineBlocks.BRIDGMANHAM_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_bridgmanite_wall"),RankineBlocks.POLISHED_BRIDGMANHAM_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:bridgmanite_bricks_wall"),RankineBlocks.BRIDGMANHAM_BRICKS_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:bridgmanite_pressure_plate"),RankineBlocks.BRIDGMANHAM_PRESSURE_PLATE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:bridgmanite_bricks_pressure_plate"),RankineBlocks.BRIDGMANHAM_BRICKS_PRESSURE_PLATE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:bridgmanite_button"),RankineBlocks.BRIDGMANHAM_BUTTON.get());

        blockRemappings.put(ResourceLocation.parse("rankine:ferropericlase"),RankineBlocks.SOMMANITE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_ferropericlase"),RankineBlocks.POLISHED_SOMMANITE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:ferropericlase_bricks"),RankineBlocks.SOMMANITE_BRICKS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:ferropericlase_slab"),RankineBlocks.SOMMANITE_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_ferropericlase_slab"),RankineBlocks.POLISHED_SOMMANITE_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:ferropericlase_bricks_slab"),RankineBlocks.SOMMANITE_BRICKS_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:ferropericlase_stairs"),RankineBlocks.SOMMANITE_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_ferropericlase_stairs"),RankineBlocks.POLISHED_SOMMANITE_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:ferropericlase_bricks_stairs"),RankineBlocks.SOMMANITE_BRICKS_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:ferropericlase_wall"),RankineBlocks.SOMMANITE_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_ferropericlase_wall"),RankineBlocks.POLISHED_SOMMANITE_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:ferropericlase_bricks_wall"),RankineBlocks.SOMMANITE_BRICKS_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:ferropericlase_pressure_plate"),RankineBlocks.SOMMANITE_PRESSURE_PLATE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:ferropericlase_bricks_pressure_plate"),RankineBlocks.SOMMANITE_BRICKS_PRESSURE_PLATE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:ferropericlase_button"),RankineBlocks.SOMMANITE_BUTTON.get());

        blockRemappings.put(ResourceLocation.parse("rankine:perovskite"),RankineBlocks.POST_PEROVSKITE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_perovskite"),RankineBlocks.POLISHED_POST_PEROVSKITE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:perovskite_bricks"),RankineBlocks.POST_PEROVSKITE_BRICKS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:perovskite_slab"),RankineBlocks.POST_PEROVSKITE_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_perovskite_slab"),RankineBlocks.POLISHED_POST_PEROVSKITE_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:perovskite_bricks_slab"),RankineBlocks.POST_PEROVSKITE_BRICKS_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:perovskite_stairs"),RankineBlocks.POST_PEROVSKITE_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_perovskite_stairs"),RankineBlocks.POLISHED_POST_PEROVSKITE_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:perovskite_bricks_stairs"),RankineBlocks.POST_PEROVSKITE_BRICKS_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:perovskite_wall"),RankineBlocks.POST_PEROVSKITE_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_perovskite_wall"),RankineBlocks.POLISHED_POST_PEROVSKITE_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:perovskite_bricks_wall"),RankineBlocks.POST_PEROVSKITE_BRICKS_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:perovskite_pressure_plate"),RankineBlocks.POST_PEROVSKITE_PRESSURE_PLATE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:perovskite_bricks_pressure_plate"),RankineBlocks.POST_PEROVSKITE_BRICKS_PRESSURE_PLATE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:perovskite_button"),RankineBlocks.POST_PEROVSKITE_BUTTON.get());

        blockRemappings.put(ResourceLocation.parse("rankine:ringwoodite"),RankineBlocks.RINGWOODINE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_ringwoodite"),RankineBlocks.POLISHED_RINGWOODINE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:ringwoodite_bricks"),RankineBlocks.RINGWOODINE_BRICKS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:ringwoodite_slab"),RankineBlocks.RINGWOODINE_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_ringwoodite_slab"),RankineBlocks.POLISHED_RINGWOODINE_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:ringwoodite_bricks_slab"),RankineBlocks.RINGWOODINE_BRICKS_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:ringwoodite_stairs"),RankineBlocks.RINGWOODINE_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_ringwoodite_stairs"),RankineBlocks.POLISHED_RINGWOODINE_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:ringwoodite_bricks_stairs"),RankineBlocks.RINGWOODINE_BRICKS_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:ringwoodite_wall"),RankineBlocks.RINGWOODINE_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_ringwoodite_wall"),RankineBlocks.POLISHED_RINGWOODINE_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:ringwoodite_bricks_wall"),RankineBlocks.RINGWOODINE_BRICKS_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:ringwoodite_pressure_plate"),RankineBlocks.RINGWOODINE_PRESSURE_PLATE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:ringwoodite_bricks_pressure_plate"),RankineBlocks.RINGWOODINE_BRICKS_PRESSURE_PLATE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:ringwoodite_button"),RankineBlocks.RINGWOODINE_BUTTON.get());

        blockRemappings.put(ResourceLocation.parse("rankine:wadsleyite"),RankineBlocks.WADSLEYONE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_wadsleyite"),RankineBlocks.POLISHED_WADSLEYONE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:wadsleyite_bricks"),RankineBlocks.WADSLEYONE_BRICKS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:wadsleyite_slab"),RankineBlocks.WADSLEYONE_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_wadsleyite_slab"),RankineBlocks.POLISHED_WADSLEYONE_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:wadsleyite_bricks_slab"),RankineBlocks.WADSLEYONE_BRICKS_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:wadsleyite_stairs"),RankineBlocks.WADSLEYONE_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_wadsleyite_stairs"),RankineBlocks.POLISHED_WADSLEYONE_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:wadsleyite_bricks_stairs"),RankineBlocks.WADSLEYONE_BRICKS_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:wadsleyite_wall"),RankineBlocks.WADSLEYONE_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_wadsleyite_wall"),RankineBlocks.POLISHED_WADSLEYONE_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:wadsleyite_bricks_wall"),RankineBlocks.WADSLEYONE_BRICKS_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:wadsleyite_pressure_plate"),RankineBlocks.WADSLEYONE_PRESSURE_PLATE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:wadsleyite_bricks_pressure_plate"),RankineBlocks.WADSLEYONE_BRICKS_PRESSURE_PLATE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:wadsleyite_button"),RankineBlocks.WADSLEYONE_BUTTON.get());


        blockRemappings.put(ResourceLocation.parse("rankine:peridodite"),RankineBlocks.DUNITE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_peridodite"),RankineBlocks.POLISHED_DUNITE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:peridodite_bricks"),RankineBlocks.DUNITE_BRICKS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:peridodite_slab"),RankineBlocks.DUNITE_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_peridodite_slab"),RankineBlocks.POLISHED_DUNITE_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:peridodite_bricks_slab"),RankineBlocks.DUNITE_BRICKS_SLAB.get());
        blockRemappings.put(ResourceLocation.parse("rankine:peridodite_stairs"),RankineBlocks.DUNITE_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_peridodite_stairs"),RankineBlocks.POLISHED_DUNITE_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:peridodite_bricks_stairs"),RankineBlocks.DUNITE_BRICKS_STAIRS.get());
        blockRemappings.put(ResourceLocation.parse("rankine:peridodite_wall"),RankineBlocks.DUNITE_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:polished_peridodite_wall"),RankineBlocks.POLISHED_DUNITE_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:peridodite_bricks_wall"),RankineBlocks.DUNITE_BRICKS_WALL.get());
        blockRemappings.put(ResourceLocation.parse("rankine:peridodite_pressure_plate"),RankineBlocks.DUNITE_PRESSURE_PLATE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:peridodite_bricks_pressure_plate"),RankineBlocks.DUNITE_BRICKS_PRESSURE_PLATE.get());
        blockRemappings.put(ResourceLocation.parse("rankine:peridodite_button"),RankineBlocks.DUNITE_BUTTON.get());
*/
        return blockRemappings;
    }
}
