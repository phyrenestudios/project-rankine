package com.cannolicatfish.rankine.init;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class RankineRemappings {

    public static Map<ResourceLocation, Item> itemRemappings = new HashMap<>();

    public static Map<ResourceLocation, Item> getItemRemappings() {

        itemRemappings.put(new ResourceLocation("rankine:piston_crusher"), Items.PISTON);
        itemRemappings.put(new ResourceLocation("rankine:gyratory_crusher"), Items.PISTON);

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
        itemRemappings.put(new ResourceLocation("rankine:mica"),RankineItems.BIOTITE.get());

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

        blockRemappings.put(new ResourceLocation("rankine:piston_crusher"), Blocks.PISTON);
        blockRemappings.put(new ResourceLocation("rankine:gyratory_crusher"), Blocks.PISTON);

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

        blockRemappings.put(new ResourceLocation("rankine:etched_glass0"), Blocks.GLASS);
        blockRemappings.put(new ResourceLocation("rankine:etched_glass1"), Blocks.GLASS);
        blockRemappings.put(new ResourceLocation("rankine:etched_glass2"), Blocks.GLASS);

        blockRemappings.put(new ResourceLocation("rankine:feldspar_block"), RankineBlocks.ORTHOCLASE_FELDSPAR_BLOCK.get());
        blockRemappings.put(new ResourceLocation("rankine:mica_block"), RankineBlocks.BIOTITE_BLOCK.get());

        blockRemappings.put(new ResourceLocation("rankine:high_beehive_oven_pit"), RankineBlocks.BEEHIVE_OVEN_PIT.get());
        blockRemappings.put(new ResourceLocation("rankine:ultra_high_beehive_oven_pit"), RankineBlocks.BEEHIVE_OVEN_PIT.get());

        blockRemappings.put(new ResourceLocation("rankine:kaolinite_block"), RankineBlocks.KAOLIN.get());

        blockRemappings.put(new ResourceLocation("rankine:laser_pylon_base"), RankineBlocks.STAINLESS_STEEL_SHEETMETAL.get());
        blockRemappings.put(new ResourceLocation("rankine:laser_pylon_top"), RankineBlocks.URANIUM_BLOCK.get());
        blockRemappings.put(new ResourceLocation("rankine:laser_pylon"), RankineBlocks.LONSDALEITE_DIAMOND_BLOCK.get());
        blockRemappings.put(new ResourceLocation("rankine:quarry_barrier"), Blocks.OBSIDIAN);


        blockRemappings.put(new ResourceLocation("rankine:liquid_mercury_block"), RankineBlocks.LIQUID_MERCURY.get());

        blockRemappings.put(new ResourceLocation("rankine:pozzolan_block"), RankineBlocks.POZZOLANA.get());

        blockRemappings.put(new ResourceLocation("rankine:salt_block"), RankineBlocks.SODIUM_CHLORIDE_BLOCK.get());


        return blockRemappings;
    }
}
