package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RankineLangProvider extends LanguageProvider {
    private final String locale;
    public RankineLangProvider(DataGenerator gen, String locale) {
        super(gen, ProjectRankine.MODID, locale);
        this.locale = locale;
    }

    @Override
    public String getName() {
        return "Rankine Lang: " + locale;
    }

    @Override
    protected void addTranslations() {
        for (Block blk : Stream.of(
                RankineLists.MISC_SLABS,
                RankineLists.MISC_VERTICAL_SLABS,
                RankineLists.MISC_STAIRS,
                RankineLists.MISC_WALLS,

                RankineLists.MUSHROOM_BLOCKS,
                RankineLists.CONCRETE_VERTICAL_SLABS,
                RankineLists.CONCRETE_STAIRS,
                RankineLists.CONCRETE_WALLS,
                RankineLists.SHEETMETAL_SLABS,
                //RankineLists.GAS_TUBES,
                RankineLists.STONE_COLUMNS,
                RankineLists.HOLLOW_LOGS,
                RankineLists.LEAF_LITTERS,
                RankineLists.INFESTED_STONES,
                RankineLists.STONES,
                RankineLists.POLISHED_STONES,
                RankineLists.STONE_BRICKS,
                RankineLists.STONE_SLABS,
                RankineLists.POLISHED_STONE_SLABS,
                RankineLists.STONE_BRICKS_SLABS,
                RankineLists.STONE_VERTICAL_SLABS,
                RankineLists.POLISHED_STONE_VERTICAL_SLABS,
                RankineLists.STONE_BRICKS_VERTICAL_SLABS,
                RankineLists.STONE_STAIRS,
                RankineLists.POLISHED_STONE_STAIRS,
                RankineLists.STONE_BRICKS_STAIRS,
                RankineLists.STONE_WALLS,
                RankineLists.POLISHED_STONE_WALLS,
                RankineLists.STONE_BRICKS_WALLS,
                RankineLists.STONE_PRESSURE_PLATES,
                RankineLists.STONE_BRICKS_PRESSURE_PLATES,
                RankineLists.STONE_BUTTONS,
                RankineLists.VANILLA_BRICKS,
                RankineLists.VANILLA_BRICKS_SLABS,
                RankineLists.VANILLA_BRICKS_VERTICAL_SLABS,
                RankineLists.VANILLA_BRICKS_WALLS,
                RankineLists.VANILLA_BRICKS_STAIRS,
                RankineLists.VANILLA_BRICKS_PRESSURE_PLATES,
                RankineLists.BRICKS,
                RankineLists.BRICKS_SLAB,
                RankineLists.BRICKS_STAIRS,
                RankineLists.BRICKS_VERTICAL_SLAB,
                RankineLists.BRICKS_WALL,
                RankineLists.CUT_SANDSTONES,
                RankineLists.CUT_SANDSTONE_SLABS,
                RankineLists.CUT_SANDSTONE_VERTICAL_SLABS,
                RankineLists.CHISELED_SANDSTONES,
                RankineLists.SMOOTH_SANDSTONES,
                RankineLists.SMOOTH_SANDSTONE_SLABS,
                RankineLists.SMOOTH_SANDSTONE_STAIRS,
                RankineLists.SMOOTH_SANDSTONE_VERTICAL_SLABS,
                RankineLists.SMOOTH_SANDSTONE_WALLS,
                RankineLists.SANDSTONES,
                RankineLists.SANDSTONE_SLABS,
                RankineLists.SANDSTONE_STAIRS,
                RankineLists.SANDSTONE_VERTICAL_SLABS,
                RankineLists.SANDSTONE_WALLS,
                RankineLists.SHEETMETALS,
                RankineLists.SHEETMETAL_VERTICAL_SLABS,
                RankineLists.GEODES,
                RankineLists.LEDS,
                RankineLists.MINERAL_WOOL,
                RankineLists.FIBER_BLOCK,
                RankineLists.FIBER_MAT,
                RankineLists.FLOWER_POTS,
                RankineLists.TALL_FLOWERS,
                RankineLists.SAPLINGS,
                RankineLists.LEAVES,
                RankineLists.WOODEN_BOOKSHELVES,
                RankineLists.PLANKS,
                RankineLists.LOGS,
                RankineLists.STRIPPED_LOGS,
                RankineLists.WOODS,
                RankineLists.STRIPPED_WOODS,
                RankineLists.WOODEN_SLABS,
                RankineLists.WOODEN_STAIRS,
                RankineLists.WOODEN_VERTICAL_SLABS,
                RankineLists.WOODEN_DOORS,
                RankineLists.WOODEN_TRAPDOORS,
                RankineLists.WOODEN_FENCES,
                RankineLists.WOODEN_FENCE_GATES,
                RankineLists.WOODEN_BUTTONS,
                RankineLists.WOODEN_PRESSURE_PLATES,
                RankineLists.METAL_TRAPDOORS,
                RankineLists.METAL_DOORS,
                RankineLists.METAL_LADDERS,
                RankineLists.ALLOY_PEDESTALS,
                RankineLists.ALLOY_POLES,
                RankineLists.ALLOY_BARS,
                RankineLists.CROPS_SINGLE,
                RankineLists.CROPS_DOUBLE,
                RankineLists.CROPS_TRIPLE,
                RankineLists.WALL_MUSHROOMS,
                RankineLists.BUSH_PLANTS,
                RankineLists.DOUBLE_BUSH_PLANTS,
                RankineLists.EIGHT_LAYER_BLOCKS,
                RankineLists.FLUID_BLOCKS,
                RankineLists.GAS_BLOCKS,
                RankineLists.STONE_COBBLES,
                RankineLists.STANDARD_BLOCKS,
                RankineLists.ROTATION_BLOCKS,
                RankineLists.LIGHTNING_GLASSES,
                RankineLists.COARSE_SOIL_BLOCKS,
                RankineLists.SOIL_BLOCKS,
                RankineLists.MUD_BLOCKS,
                RankineLists.GRASS_BLOCKS,
                RankineLists.PODZOL_BLOCKS,
                RankineLists.MYCELIUM_BLOCKS,
                RankineLists.PATH_BLOCKS,
                RankineLists.ELECTROMAGNETS,
                RankineLists.LANTERNS,
                RankineLists.QUARTER_SLABS,
                RankineLists.ASPHALT_BLOCKS,
                RankineLists.RED_ASPHALT_BLOCKS,
                RankineLists.GRAY_ASPHALT_BLOCKS,
                RankineLists.BLUE_ASPHALT_BLOCKS,
                RankineLists.DARK_GRAY_ASPHALT_BLOCKS,
                RankineLists.GREEN_ASPHALT_BLOCKS,
                RankineLists.NATIVE_ORES,
                RankineLists.CRUSHING_ORES,
                RankineLists.SPECIAL_ORES).flatMap(Collection::stream).collect(Collectors.toList())) {
                add(blk, parseLangName(blk.getRegistryName().getPath()));
        }

        for (Block blk : Stream.of(
            RankineLists.ELEMENT_BLOCKS,
            RankineLists.MINERAL_BLOCKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            if (blk.matchesBlock(RankineBlocks.SODIUM_CHLORIDE_BLOCK.get())) {
                add(blk, "Block of Salt (NaCl)");
            } else if (blk.matchesBlock(RankineBlocks.CALCIUM_CHLORIDE_BLOCK.get())) {
                add(blk, "Block of Salt (CaCl2)");
            } else {
                add(blk, parseLangName("block_of_"+blk.getRegistryName().getPath().replace("_block","")));
            }
        }

        for (Block blk : RankineLists.ALLOY_BLOCKS) {
            if (blk != RankineBlocks.ALLOY_BLOCK.get()) {
                add(blk,parseLangNameCustomBlock(blk.getRegistryName().getPath()));
            }
        }

        // Misc Blocks
        for (Block blk : Arrays.asList(
                RankineBlocks.GAS_BOTTLER.get(),
                RankineBlocks.GAS_VENT.get(),
                RankineBlocks.TILLED_SOIL.get(),
                RankineBlocks.SEDIMENT_FAN.get(),
                RankineBlocks.ORNAMENT.get(),
                RankineBlocks.LOCUST_SPINE.get(),
                RankineBlocks.CARBON_DIOXIDE_FUMAROLE.get(),
                RankineBlocks.HYDROGEN_CHLORIDE_FUMAROLE.get(),
                RankineBlocks.HYDROGEN_SULFIDE_FUMAROLE.get(),
                RankineBlocks.SULFUR_DIOXIDE_FUMAROLE.get(),
                RankineBlocks.SODIUM_VAPOR_LAMP.get(),
                RankineBlocks.ALLOY_FURNACE.get(),
                RankineBlocks.INDUCTION_FURNACE.get(),
                RankineBlocks.FUSION_FURNACE.get(),
                RankineBlocks.MIXING_BARREL.get(),
                RankineBlocks.CRUCIBLE_BLOCK.get(),
                RankineBlocks.BEEHIVE_OVEN_PIT.get(),
                RankineBlocks.MATERIAL_TESTING_TABLE.get(),
                RankineBlocks.TEMPLATE_TABLE.get(),
                RankineBlocks.PISTON_CRUSHER.get(),
                RankineBlocks.BOTANIST_STATION.get(),
                RankineBlocks.DIAMOND_ANVIL_CELL.get(),
                RankineBlocks.PARTICLE_ACCELERATOR.get(),
                RankineBlocks.GYRATORY_CRUSHER.get(),
                RankineBlocks.EVAPORATION_TOWER.get(),
                //RankineBlocks.LASER_PYLON_BASE.get(),
                //RankineBlocks.LASER_PYLON_TOP.get(),
                //RankineBlocks.LASER_QUARRY.get(),
                RankineBlocks.DISTILLATION_TOWER.get(),
                RankineBlocks.AIR_DISTILLATION_PACKING.get(),
                RankineBlocks.REACTION_CHAMBER_CORE.get(),
                RankineBlocks.TRAMPOLINE.get(),
                RankineBlocks.CHARCOAL_PIT.get(),
                RankineBlocks.GWIHABAITE_CRYSTAL.get(),
                RankineBlocks.SHORT_GRASS.get(),
                RankineBlocks.STINGING_NETTLE.get(),
                RankineBlocks.WILLOW_BRANCHLET.get(),
                RankineBlocks.WILLOW_BRANCHLET_PLANT.get(),

                RankineBlocks.RED_CLOVER.get(),
                RankineBlocks.CRIMSON_CLOVER.get(),
                RankineBlocks.WHITE_CLOVER.get(),
                RankineBlocks.YELLOW_CLOVER.get(),

                RankineBlocks.COB.get(),
                RankineBlocks.SOD_BLOCK.get(),
                RankineBlocks.MYCELIUM_PATH.get(),
                RankineBlocks.BONE_CHAR_BLOCK.get(),
                RankineBlocks.STICK_BLOCK.get(),

                RankineBlocks.GROUND_TAP.get(),
                RankineBlocks.METAL_PIPE.get(),
                RankineBlocks.UNAMED_EXPLOSIVE.get(),
                RankineBlocks.GEODE.get(),
                RankineBlocks.ANTIMATTER.get(),
                RankineBlocks.LIGHT_GRAVEL.get(),
                RankineBlocks.DARK_GRAVEL.get(),
                RankineBlocks.FIRE_CLAY.get(),
                RankineBlocks.KAOLIN.get(),
                RankineBlocks.STUMP.get(),
                RankineBlocks.KIMBERLITIC_DIAMOND_ORE.get(),
                RankineBlocks.PORPHYRY_COPPER.get()
                )) {
            add(blk, parseLangName(blk.getRegistryName().getPath()));
        }

        for (Item item : Stream.of(
                RankineLists.WOODEN_BOATS,
                RankineLists.ARROWS,
                RankineLists.WOODEN_TOOLS,
                RankineLists.STONE_TOOLS,
                RankineLists.FLINT_TOOLS,
                RankineLists.ELEMENT_INGOTS,
                RankineLists.ELEMENT_NUGGETS,
                RankineLists.GAS_BOTTLES,
                RankineLists.MINERAL_ITEMS,
                RankineLists.SEEDS,
                RankineLists.RAW_FISH,
                RankineLists.COOKED_FISH,
                RankineLists.GRAINS).flatMap(Collection::stream).collect(Collectors.toList())) {

            if (item == RankineItems.SODIUM_CHLORIDE.get()) {
                add(item, "Salt (NaCl)");
            } else if (item == RankineItems.CALCIUM_CHLORIDE.get()) {
                add(item, "Salt (CaCl2)");
            } else {
                add(item, parseLangName(item.getRegistryName().getPath()));
            }
        }

        for (Item item : Stream.of(
                RankineLists.ALLOY_NUGGETS,
                RankineLists.ALLOY_INGOTS,
                RankineLists.ALLOY_TOOLS, RankineLists.BRONZE_TOOLS,
                RankineLists.PEWTER_TOOLS,
                RankineLists.INVAR_TOOLS,
                RankineLists.STEEL_TOOLS,
                RankineLists.STAINLESS_STEEL_TOOLS,
                RankineLists.COBALT_SUPERALLOY_TOOLS,
                RankineLists.NICKEL_SUPERALLOY_TOOLS,
                RankineLists.TUNGSTEN_HEAVY_ALLOY_TOOLS,
                RankineLists.ROSE_GOLD_TOOLS,
                RankineLists.WHITE_GOLD_TOOLS,
                RankineLists.BLUE_GOLD_TOOLS,
                RankineLists.GREEN_GOLD_TOOLS,
                RankineLists.BLACK_GOLD_TOOLS,
                RankineLists.PURPLE_GOLD_TOOLS,
                RankineLists.OSMIRIDIUM_TOOLS,
                RankineLists.AMALGAM_TOOLS,
                RankineLists.ENDER_AMALGAM_TOOLS,
                RankineLists.NIOBIUM_ALLOY_TOOLS,
                RankineLists.ZIRCONIUM_ALLOY_TOOLS,
                RankineLists.TITANIUM_ALLOY_TOOLS).flatMap(Collection::stream).collect(Collectors.toList())) {
            if (item != RankineItems.ALLOY_NUGGET.get() && item != RankineItems.ALLOY_INGOT.get()) {
                add(item, parseLangNameCustom(item.getRegistryName().getPath()));
            }
        }
        for (Item item : Arrays.asList(RankineItems.ALLOY_NUGGET.get(),RankineItems.ALLOY_INGOT.get())) {
            add(item, parseLangNameCustom(item.getRegistryName().getPath()));
        }
        add(RankineItems.ALLOY_BLOCK.get(), parseLangNameCustomBlock(RankineItems.ALLOY_BLOCK.get().getRegistryName().getPath()));

        for (Item item : Arrays.asList(
            RankineItems.SOLDER.get(),
            RankineItems.FRUIT_JAM.get(),
            RankineItems.BLACK_WALNUT.get(),
            RankineItems.COCONUT.get(),
            RankineItems.ALOE.get(),
            RankineItems.RICE.get(),
            RankineItems.BARLEY.get(),
            RankineItems.RYE.get(),
            RankineItems.SORGHUM.get(),
            RankineItems.MILLET.get(),
            RankineItems.OATS.get(),
            RankineItems.ASPARAGUS.get(),
            RankineItems.CORN_EAR.get(),
            RankineItems.COTTON.get(),
            RankineItems.JUTE.get(),
            RankineItems.SOYBEANS.get(),
            RankineItems.SOY_MILK.get(),
            RankineItems.TOFU.get(),
            RankineItems.BLUEBERRIES.get(),
            RankineItems.CRANBERRIES.get(),
            RankineItems.POKEBERRIES.get(),
            RankineItems.SNOWBERRIES.get(),
            RankineItems.ELDERBERRIES.get(),
            RankineItems.RASPBERRIES.get(),
            RankineItems.BLACKBERRIES.get(),
            RankineItems.STRAWBERRIES.get(),
            RankineItems.PINEAPPLE.get(),
            RankineItems.PINEAPPLE_SLEEVES.get(),
            RankineItems.BANANA_YUCCA.get(),
            RankineItems.JUNIPER_BERRIES.get(),
            RankineItems.ROASTED_ASPARAGUS.get(),
            RankineItems.ROASTED_WALNUT.get(),
            RankineItems.TOASTED_COCONUT.get(),
            RankineItems.POPCORN.get(),
            RankineItems.DOUGH.get(),
            RankineItems.TOAST.get(),
            RankineItems.CINNAMON.get(),
            RankineItems.CINNAMON_TOAST.get(),
            RankineItems.TRAIL_MIX.get(),
            RankineItems.CHEESE.get(),
            RankineItems.AGED_CHEESE.get(),
            RankineItems.UNAGED_CHEESE.get(),
            RankineItems.CAKE_SLICE.get(),
            RankineItems.PINA_COLADA.get(),
            RankineItems.MAPLE_SYRUP.get(),
            RankineItems.PANCAKE.get(),
            RankineItems.PANCAKE_BATTER.get(),
            RankineItems.PANCAKE_BREAKFAST.get(),
            RankineItems.PLANT_FIBER.get(),
            RankineItems.CAMPHOR_BASIL_LEAF.get(),
            RankineItems.CORN_STALK.get(),
            RankineItems.VULCANIZED_RUBBER.get(),
            RankineItems.BITUMEN.get(),
            RankineItems.GWIHABAITE.get(),
            RankineItems.FIRE_CLAY_BALL.get(),
            RankineItems.KAOLINITE.get(),
            RankineItems.BONE_CHAR.get(),
            RankineItems.NETHERITE_NUGGET.get(),
            RankineItems.SLAG.get(),
            RankineItems.TRONA.get(),
            RankineItems.POTASH.get(),
            RankineItems.BORON_TRIOXIDE.get(),
            RankineItems.VANADIUM_PENTOXIDE.get(),
            RankineItems.SODIUM_FLUOROSILICATE.get(),
            RankineItems.YELLOWCAKE.get(),
            RankineItems.ASBESTOS.get(),
            RankineItems.THENARDITE.get(),
            RankineItems.BORAX.get(),
            RankineItems.SODIUM_SULFIDE.get(),
            RankineItems.LITHIUM_HYDROXIDE.get(),
            RankineItems.SODIUM_HYDROXIDE.get(),
            RankineItems.POTASSIUM_HYDROXIDE.get(),
            RankineItems.RUBIDIUM_HYDROXIDE.get(),
            RankineItems.CESIUM_HYDROXIDE.get(),
            RankineItems.FRANCIUM_HYDROXIDE.get(),
            RankineItems.BETA_ALUMINA_SOLID_ELECTROLYTE.get(),
            RankineItems.LITHIUM_COBALT_OXIDE.get(),
            RankineItems.SODIUM_CARBONATE.get(),
            RankineItems.SALTPETER.get(),
            RankineItems.CEMENT_MIX.get(),
            RankineItems.MORTAR.get(),
            RankineItems.METEORIC_IRON.get(),
            RankineItems.OSMIRIDIUM.get(),
            RankineItems.COMPOST.get(),
            RankineItems.GARLAND.get(),
            RankineItems.BIOMASS.get(),
            RankineItems.COMPRESSED_BIOMASS.get(),
            RankineItems.DRIED_BAMBOO.get(),
            RankineItems.BEAVER_PELT.get(),
            RankineItems.ROPE.get(),
            RankineItems.BLEACH.get(),
            RankineItems.ICE_MELT.get(),
            RankineItems.HERBICIDE.get(),
            RankineItems.FERTILIZER.get(),
            RankineItems.SYNTHETIC_LEATHER.get(),
            RankineItems.PULP.get(),
            RankineItems.ALKALI_CELLULOSE.get(),
            RankineItems.DRY_RUBBER.get(),
            RankineItems.INDIUM_TIN_OXIDE.get(),
            RankineItems.CADMIUM_TELLURIDE_CELL.get(),
            RankineItems.SILICON_GERMANIUM_THERMOCOUPLE.get(),
            RankineItems.SODIUM_ARC_TUBE.get(),
            RankineItems.GRAPHITE_ELECTRODE.get(),
            RankineItems.HARD_CARBON_ELECTRODE.get(),
            RankineItems.SADDLE_TREE.get(),
            RankineItems.CANNONBALL.get(),
            RankineItems.CARCASS.get(),
            RankineItems.PENNING_TRAP.get(),
            RankineItems.PUMICE_SOAP.get(),
            RankineItems.REFRACTORY_BRICK.get(),
            RankineItems.HIGH_REFRACTORY_BRICK.get(),
            RankineItems.ULTRA_HIGH_REFRACTORY_BRICK.get(),
            RankineItems.TAP_LINE.get(),
            RankineItems.TREE_TAP.get(),
            RankineItems.LODESTONE.get(),
            RankineItems.EMERGENCY_FLOTATION_DEVICE.get(),
            RankineItems.ELEMENT.get(),
            RankineItems.ALLOY_TEMPLATE.get(),
            RankineItems.BUILDING_TOOL.get(),
            RankineItems.ORE_DETECTOR.get(),
            RankineItems.PROSPECTING_STICK.get(),
            RankineItems.DOWSING_ROD.get(),
            RankineItems.PACKAGED_TOOL.get(),
            RankineItems.SIMPLE_MAGNET.get(),
            RankineItems.ALNICO_MAGNET.get(),
            RankineItems.RARE_EARTH_MAGNET.get(),
            RankineItems.WOODEN_GOLD_PAN.get(),
            RankineItems.PEWTER_GOLD_PAN.get(),
            RankineItems.STEEL_GOLD_PAN.get(),
            RankineItems.ALTIMETER.get(),
            RankineItems.SPEEDOMETER.get(),
            RankineItems.BIOMETER.get(),
            RankineItems.PHOTOMETER.get(),
            RankineItems.GLASS_CUTTER.get(),
            RankineItems.FIRE_EXTINGUISHER.get(),
            RankineItems.ROCK_DRILL.get(),
            RankineItems.SPARK_LIGHTER.get(),
            RankineItems.MAGNETOMETER.get(),
            RankineItems.THERMOMETER.get(),
            RankineItems.HARDNESS_TESTER.get(),
            RankineItems.TOTEM_OF_BLAZING.get(),
            RankineItems.TOTEM_OF_COBBLING.get(),
            RankineItems.TOTEM_OF_ENDURING.get(),
            RankineItems.TOTEM_OF_HASTENING.get(),
            RankineItems.TOTEM_OF_INFUSING.get(),
            RankineItems.TOTEM_OF_INVIGORATING.get(),
            RankineItems.TOTEM_OF_LEVITATING.get(),
            RankineItems.TOTEM_OF_PROMISING.get(),
            RankineItems.TOTEM_OF_REPULSING.get(),
            RankineItems.TOTEM_OF_SOFTENING.get(),
            RankineItems.TOTEM_OF_TIMESAVING.get(),
            RankineItems.ELEMENT_INDEXER.get(),
            RankineItems.PIA.get(),
            RankineItems.SHULKER_GAS_VACUUM.get(),
            RankineItems.BANDAGE.get(),
            RankineItems.GAS_MASK.get(),
            RankineItems.SANDALS.get(),
            RankineItems.ICE_SKATES.get(),
            RankineItems.SNOWSHOES.get(),
            RankineItems.GOGGLES.get(),
            RankineItems.FINS.get(),
            RankineItems.BRIGADINE_CHESTPLATE.get(),
            RankineItems.BRIGADINE_LEGGINGS.get(),
            RankineItems.BRIGADINE_HELMET.get(),
            RankineItems.BRIGADINE_BOOTS.get(),
            RankineItems.CONDUIT_DIVING_CHESTPLATE.get(),
            RankineItems.CONDUIT_DIVING_HELMET.get(),
            RankineItems.CONDUIT_DIVING_LEGGINGS.get(),
            RankineItems.CONDUIT_DIVING_BOOTS.get(),
            RankineItems.DIVING_CHESTPLATE.get(),
            RankineItems.DIVING_LEGGINGS.get(),
            RankineItems.DIVING_HELMET.get(),
            RankineItems.DIVING_BOOTS.get(),
            RankineItems.LATEX_BUCKET.get(),
            RankineItems.SAP_BUCKET.get(),
            RankineItems.RESIN_BUCKET.get(),
            RankineItems.MAPLE_SAP_BUCKET.get(),
            RankineItems.JUGLONE_BUCKET.get(),
            RankineItems.LIQUID_MERCURY_BUCKET.get(),
            RankineItems.AQUA_REGIA_BUCKET.get(),
            RankineItems.CARBON_DISULFIDE_BUCKET.get(),
            RankineItems.HEXAFLUOROSILICIC_ACID_BUCKET.get(),
            RankineItems.HYDROBROMIC_ACID_BUCKET.get(),
            RankineItems.GRAY_MUD_BUCKET.get(),
            RankineItems.RED_MUD_BUCKET.get(),
            RankineItems.SULFURIC_ACID_BUCKET.get(),
            RankineItems.BLACK_LIQUOR_BUCKET.get(),
            RankineItems.GREEN_LIQUOR_BUCKET.get(),
            RankineItems.WHITE_LIQUOR_BUCKET.get(),
            RankineItems.COIN.get(),
            RankineItems.SILVER_ZINC_BATTERY.get(),
            RankineItems.MAGNESIUM_BATTERY.get(),
            RankineItems.LEAD_ACID_BATTERY.get(),
            RankineItems.VANADIUM_REDOX_BATTERY.get(),
            RankineItems.ZINC_BROMINE_BATTERY.get(),
            RankineItems.SODIUM_SULFUR_BATTERY.get(),
            RankineItems.LITHIUM_ION_BATTERY.get(),
            RankineItems.AMERICIUM_RTG.get(),
            RankineItems.CURIUM_RTG.get(),
            RankineItems.PLUTONIUM_RTG.get(),
            RankineItems.POLONIUM_RTG.get(),
            RankineItems.STRONTIUM_RTG.get(),
            RankineItems.CRUSHING_HEAD_HL0.get(),
            RankineItems.CRUSHING_HEAD_HL1.get(),
            RankineItems.CRUSHING_HEAD_HL2.get(),
            RankineItems.CRUSHING_HEAD_HL3.get(),
            RankineItems.CRUSHING_HEAD_HL4.get(),
            RankineItems.CRUSHING_HEAD_HL5.get())) {
            add(item, parseLangName(item.getRegistryName().getPath()));
        }






        //Alloy Lang
        add("item.rankine.custom_alloy_default","Alloy");
        add("item.rankine.alnico_alloying", "Alnico");
        add("item.rankine.aluminum_bronze_alloying", "Aluminum Bronze");
        add("item.rankine.amalgam_alloying", "Amalgam");
        add("item.rankine.bismanol_alloying", "Bismanol");
        add("item.rankine.black_gold_alloying", "Black Gold");
        add("item.rankine.black_gold_nr_alloying", "Black Netherite");
        add("item.rankine.blue_gold_alloying", "Blue Gold");
        add("item.rankine.blue_gold_nr_alloying", "Blue Netherite");
        add("item.rankine.brass_alloying", "Brass");
        add("item.rankine.bronze_alloying", "Bronze");
        add("item.rankine.cast_iron_alloying", "Cast Iron");
        add("item.rankine.cobalt_superalloy_alloying", "Cobalt Superalloy");
        add("item.rankine.crucible_steel_alloying", "Crucible Steel");
        add("item.rankine.cupronickel_alloying", "Cupronickel");
        add("item.rankine.duralumin_alloying", "Duralumin");
        add("item.rankine.ender_amalgam_alloying", "Ender Amalgam");
        add("item.rankine.ferrocerium_alloying", "Ferrocerium");
        add("item.rankine.galinstan_alloying", "Galinstan");
        add("item.rankine.green_gold_alloying", "Green Gold");
        add("item.rankine.green_gold_nr_alloying", "Green Netherite");
        add("item.rankine.invar_alloying", "Invar");
        add("item.rankine.permalloy_alloying", "Permalloy");
        add("item.rankine.magnesium_alloy_alloying", "Magnesium Alloy");
        add("item.rankine.maraging_steel_alloying", "Maraging Steel");
        add("item.rankine.mischmetal_alloying", "Mischmetal");
        add("item.rankine.nickel_silver_alloying", "Nickel Silver");
        add("item.rankine.nickel_superalloy_alloying", "Nickel Superalloy");
        add("item.rankine.niobium_alloy_alloying", "Niobium Alloy");
        add("item.rankine.nitinol_alloying", "Nitinol");
        add("item.rankine.osmiridium_alloying", "Osmiridium");
        add("item.rankine.pewter_alloying", "Pewter");
        add("item.rankine.purple_gold_alloying", "Purple Gold");
        add("item.rankine.purple_gold_nr_alloying", "Purple Netherite");
        add("item.rankine.rose_gold_alloying", "Rose Gold");
        add("item.rankine.rose_gold_nr_alloying", "Rose Netherite");
        add("item.rankine.rose_metal_alloying", "Rose Metal");
        add("item.rankine.sodium_potassium_alloy_alloying", "Sodium Potassium Alloy");
        add("item.rankine.solder_ag_alloying", "Solder");
        add("item.rankine.solder_pb_alloying", "Solder");
        add("item.rankine.stainless_steel_alloying", "Stainless Steel");
        add("item.rankine.steel_alloying", "Tool Steel");
        add("item.rankine.sterling_silver_alloying", "Sterling Silver");
        add("item.rankine.titanium_alloy_alloying", "Titanium Alloy");
        add("item.rankine.tungsten_heavy_alloy_alloying", "Tungsten Heavy Alloy");
        add("item.rankine.white_gold_alloying", "White Gold");
        add("item.rankine.white_gold_nr_alloying", "White Netherite");
        add("item.rankine.zirconium_alloy_alloying", "Zirconium Alloy");


        //Alloy Gears
        add("item.rankine.alloy_gear_cupronickel", "Cupronickel Gear");
        add("item.rankine.alloy_gear_bronze", "Bronze Gear");
        //Alloy Rods
        add("item.rankine.alloy_rod_steel", "Steel Rod");
        add("item.rankine.alloy_rod_cast_iron", "Cast Iron Rod");
        add("item.rankine.alloy_rod_cupronickel", "Cupronickel Rod");
        add("item.rankine.alloy_rod_ferrocerium", "Ferrocerium Rod");
        //Alloy Wires
        add("item.rankine.alloy_wire_cupronickel", "Cupronickel Wire");
        add("item.rankine.alloy_wire_steel", "Steel Wire");
        //Alloy Arrows
        add("item.rankine.alloy_arrow_steel", "Steel Arrow");
        add("item.rankine.alloy_arrow_stainless_steel", "Stainless Steel Arrow");





        add("item.rankine.building_tool.message", "Building Mode changed to %1$s");


        //Death Messages
        add("death.attack.suffocating", "%1$s suffocated in a gas cloud");
        add("death.attack.suffocating.player", "%1$s suffocated in a gas cloud whilst trying to escape %2$s");
        add("death.attack.cannonball", "%1$s was struck by a cannonball");
        add("death.attack.cannonball.player", "%1$s was struck by a cannonball whilst trying to escape %2$s");
        add("death.attack.columns", "%1$s was squished by a falling column");
        add("death.attack.columns.player", "%1$s was squished by a falling column whilst trying to escape %2$s");





        add("item.rankine.mantle_golem_egg", "Garnet Mantle Golem Spawn Egg");
        add("entity.rankine.mantle_golem", "Garnet Mantle Golem");
        add("item.rankine.diamond_mantle_golem_egg", "Diamond Mantle Golem Spawn Egg");
        add("entity.rankine.diamond_mantle_golem", "Diamond Mantle Golem");
        add("item.rankine.peridot_mantle_golem_egg", "Peridot Mantle Golem Spawn Egg");
        add("entity.rankine.peridot_mantle_golem", "Peridot Mantle Golem");
        add("item.rankine.desmoxyte_spawn_egg", "Desmoxyte Spawn Egg");
        add("entity.rankine.desmoxyte", "Desmoxyte");
        add("item.rankine.demonyte_spawn_egg", "Demonxyte Spawn Egg");
        add("entity.rankine.demonyte", "Demonxyte");
        add("item.rankine.dragonyte_spawn_egg", "Dragonxyte Spawn Egg");
        add("entity.rankine.dragonyte", "Dragonxyte");
        add("item.rankine.steamer_spawn_egg", "Steamer Spawn Egg");
        add("entity.rankine.steamer", "Steamer");
        add("item.rankine.beaver_spawn_egg", "Beaver Spawn Egg");
        add("entity.rankine.beaver", "Beaver");

        add("entity.minecraft.villager.rankine.metallurgist", "Metallurgist");
        add("entity.minecraft.villager.rankine.mineralogist", "Mineralogist");
        add("entity.minecraft.villager.rankine.botanist", "Botanist");
        add("entity.minecraft.villager.rankine.gem_cutter", "Gem Cutter");
        add("entity.minecraft.villager.rankine.rock_collector", "Rock Collector");

        add("enchantment.rankine.puncture", "Puncture");
        add("enchantment.rankine.puncture.desc", "Deals extra armor-piercing damage on direct attacks.");
        add("enchantment.rankine.blast", "Blast");
        add("enchantment.rankine.blast.desc", "Causes the hammer to explode blocks in a radius.");
        add("enchantment.rankine.atomize", "Atomize");
        add("enchantment.rankine.atomize.desc", "Rolls specific outputs again if they were missed on the first roll when crushing.");
        add("enchantment.rankine.lightning_aspect", "Lightning Aspect");
        add("enchantment.rankine.lightning_aspect.desc", "Causes lightning to strike when attacking targets.");
        add("enchantment.rankine.swing", "Swing");
        add("enchantment.rankine.swing.desc", "Decreases the cooldown of effects caused by swinging the hammer by 15% (additive).");
        add("enchantment.rankine.excavate", "Excavate");
        add("enchantment.rankine.excavate.desc", "Increases the number of blocks broken by swinging the hammer.");
        add("enchantment.rankine.daze", "Daze");
        add("enchantment.rankine.daze.desc", "Applies slow on targets. Effect enhanced on perfect swing.");
        add("enchantment.rankine.dune_walker", "Dune Walker");
        add("enchantment.rankine.dune_walker.desc", "Increases movement speed on sand.");
        add("enchantment.rankine.snow_drifter", "Snow Drifter");
        add("enchantment.rankine.snow_drifter.desc", "Increases movement speed on snow.");
        add("enchantment.rankine.speed_skater", "Speed Skater");
        add("enchantment.rankine.speed_skater.desc", "Increases movement speed on ice.");
        add("enchantment.rankine.flippers", "Swift Swimmer");
        add("enchantment.rankine.flippers.desc", "Increases swim speed.");
        add("enchantment.rankine.guard", "Guard");
        add("enchantment.rankine.guard.desc", "Generates passive protection hearts over time.");
        add("enchantment.rankine.gas_protection", "Gas Protection");
        add("enchantment.rankine.gas_protection.desc", "Protects from harmful effects of gases.");
        add("enchantment.rankine.quake", "Quake");
        add("enchantment.rankine.quake.desc", "Increases mining speed based on depth.");
        add("enchantment.rankine.foraging", "Foraging");
        add("enchantment.rankine.foraging.desc", "Adds chance to gain various items from digging in dirt.");
        add("enchantment.rankine.impact", "Impact");
        add("enchantment.rankine.impact.desc", "Increases knockback of thrown spears.");
        add("enchantment.rankine.antiquated", "Antiquated");
        add("enchantment.rankine.antiquated.desc", "Increases the luck stat while held in the main hand or off hand.");
        add("enchantment.rankine.cleanse", "Cleanse");
        add("enchantment.rankine.cleanse.desc", "Deals extra damage based on the number of potion effects the target has.");
        add("enchantment.rankine.endpoint", "Endpoint");
        add("enchantment.rankine.endpoint.desc", "Teleports the user to where the spear lands. Levels reduce the amount of damage taken.");
        add("enchantment.rankine.endobiotic", "Endobiotic");
        add("enchantment.rankine.endobiotic.desc", "Teleports the user when about to be hit by a projectile and negates damage. Uses extra durability.");
        add("enchantment.rankine.withering_curse", "Curse of Withering");
        add("enchantment.rankine.withering_curse.desc", "Taking damage (that isn't wither or magic related) causes you to receive the Wither effect for a short duration.");
        add("enchantment.rankine.ghast_regeneration", "Ghastly Regeneration");
        add("enchantment.rankine.ghast_regeneration.desc", "Regeneration potions restore durability.");
        add("enchantment.rankine.prying", "Prying");
        add("enchantment.rankine.prying.desc", "On hit); has a chance to cause the target to drop their held item. Chance is determined by rarity of the item.");
        add("enchantment.rankine.retrieval", "Retrieval");
        add("enchantment.rankine.retrieval.desc", "Can harvest any block provided its harvest level matches or is less than the crowbar's harvest level. Cannot harvest blocks in #rankine,crowbar_resistant.");
        add("enchantment.rankine.fulcrum", "Fulcrum");
        add("enchantment.rankine.fulcrum.desc", "When hitting a block that cannot be harvested); accelerate your current motion.");
        add("enchantment.rankine.leverage", "Leverage");
        add("enchantment.rankine.leverage.desc", "Deal increased damage to entities based on their size.");
        add("enchantment.rankine.lift", "Lift");
        add("enchantment.rankine.lift.desc", "When right-clicking a block in the air); bring yourself to the top of the block if there is no obstruction.");
        add("enchantment.rankine.preparation", "Preparation");
        add("enchantment.rankine.preparation.desc", "Increases parry time.");
        add("enchantment.rankine.retaliate", "Retaliate");
        add("enchantment.rankine.retaliate.desc", "On successful parry); deal the damage you would have taken back to the enemy.");
        add("enchantment.rankine.retreat", "Retreat");
        add("enchantment.rankine.retreat.desc", "On successful parry); turn invisible for a brief amount of time.");
        add("enchantment.rankine.backstab", "Backstab");
        add("enchantment.rankine.backstab.desc", "Deals a multiplier of your damage when hitting an enemy on their back.");
        add("enchantment.rankine.poison_aspect", "Poison Aspect");
        add("enchantment.rankine.poison_aspect.desc", "Applies a Poison or Weakness effect on enemies (depending on type) for a short duration.");
        add("enchantment.rankine.grafting", "Grafting");
        add("enchantment.rankine.grafting.desc", "Guarantees a sapling drop from leaves.");
        add("enchantment.rankine.shape_memory", "Shape Memory");
        add("enchantment.rankine.shape_memory.desc", "Restores durability in high-temperature environments.");
        add("enchantment.rankine.accuracy", "Accuracy");
        add("enchantment.rankine.accuracy.desc", "Reduces the inaccuracy penalty of the Blunderbuss and the spread of cannonballs that split.");
        add("enchantment.rankine.breath_of_the_dragon", "Breath of the Dragon");
        add("enchantment.rankine.breath_of_the_dragon.desc", "Carcass Cannonballs fired from the Blunderbuss now lay down a field of Dragon's Breath.");
        add("enchantment.rankine.burst", "Burst");
        add("enchantment.rankine.burst.desc", "Increases velocity of the cannonballs fired from the Blunderbuss and increases damage inversely with time in the air.");
        add("enchantment.rankine.explosion_aspect", "Explosion Aspect");
        add("enchantment.rankine.explosion_aspect.desc", "Increases radius and power of explosions of Cannonballs shot from the Blunderbuss.");
        add("enchantment.rankine.spread", "Spread");
        add("enchantment.rankine.spread.desc", "Increases the inaccuracy penalty of the Blunderbuss and the spread of cannonballs that split. An additional cannonball is also fired with the opposite inaccuracy penalty.");
        add("enchantment.rankine.shatter", "Shatter");
        add("enchantment.rankine.shatter.desc", "Enemies hit by cannonballs fired from the Blunderbuss take increased damage from other sources for a limited time.");
        add("enchantment.rankine.vacuum", "Vacuum Shot");
        add("enchantment.rankine.vacuum.desc", "Charging the Blunderbuss now also sucks enemies toward you. On firing, do minor damage and knockback to enemies in front of you.");
        add("jei.description.crushing_basic", "Can be obtained by crushing ores using a machine or a Bronze Hammer.");
        add("rankine.jei.crushing", "Crushing");
        add("rankine.jei.crucible", "Crucible");
        add("rankine.jei.alloying", "Alloying");
        add("rankine.jei.induction_alloying", "Advanced Alloying");
        add("rankine.jei.sluicing", "Gold Panning");
        add("rankine.jei.mixing", "Mixing");
        add("rankine.jei.evaporation", "Evaporation");
        add("rankine.jei.beeoven", "Beehive Oven");
        add("rankine.jei.fusion_furnace", "Fusion Furnace");
        add("rankine.jei.element", "Elements");
        add("rankine.jei.intrusive_igneous", "Intrusive Igneous Rock Generator");
        add("rankine.jei.extrusive_igneous", "Extrusive Igneous Rock Generator");
        add("rankine.jei.sedimentary", "Sedimentary Rock Generator");
        add("rankine.jei.metamorphic", "Metamorphic Rock Generator");
        add("rankine.jei.volcanic", "Volcanic Rock Generator");
        add("rankine.jei.air_distillation", "Air Distillation");
        add("rankine.jei.treetapping", "Treetap");
        add("itemGroup.rankine_world", "Project Rankine Building");
        add("itemGroup.rankine_metallurgy", "Project Rankine Metallurgy");
        add("itemGroup.rankine_elements", "Project Rankine Elements");
        add("itemGroup.rankine_components", "Project Rankine Components");
        add("itemGroup.rankine_misc", "Project Rankine Miscellaneous");
        add("itemGroup.rankine_plants", "Project Rankine Plants & Food");

        add("block.rankine.material_testing_bench", "Material Testing Table");
        add("block.rankine.material_testing_bench.durability.test", "Durability tests show the following,");
        add("block.rankine.material_testing_bench.durability", "Durability");
        add("block.rankine.material_testing_bench.durability.error", "Durability test inconclusive.");
        add("block.rankine.material_testing_bench.mining_speed.test", "Mining Speed tests show the following,");
        add("block.rankine.material_testing_bench.mining_speed", "Mining Speed");
        add("block.rankine.material_testing_bench.mining_speed.error", "Mining Speed test inconclusive.");
        add("block.rankine.material_testing_bench.harvest_level.test", "Harvest level tests show the following,");
        add("block.rankine.material_testing_bench.harvest_level", "Harvest Level");
        add("block.rankine.material_testing_bench.harvest_level.error", "Harvest level test inconclusive.");
        add("block.rankine.material_testing_bench.enchantability.test", "Enchantability tests show the following,");
        add("block.rankine.material_testing_bench.enchantability", "Enchantability");
        add("block.rankine.material_testing_bench.enchantability.error", "Enchantability test inconclusive.");
        add("block.rankine.material_testing_bench.attack_damage.test", "Attack damage tests show the following,");
        add("block.rankine.material_testing_bench.attack_damage", "Attack Damage");
        add("block.rankine.material_testing_bench.attack_damage.error", "Attack damage test inconclusive.");
        add("block.rankine.material_testing_bench.attack_speed.test", "Attack speed tests show the following,");
        add("block.rankine.material_testing_bench.attack_speed", "Attack Speed");
        add("block.rankine.material_testing_bench.attack_speed.error", "Attack speed test inconclusive.");
        add("block.rankine.material_testing_bench.corrosion_resistance.test", "Corrosion tests show the following,");
        add("block.rankine.material_testing_bench.corrosion_resistance", "Corrosion Resistance");
        add("block.rankine.material_testing_bench.corrosion_resistance.error", "Corrosion test inconclusive.");
        add("block.rankine.material_testing_bench.heat_resistance.test", "Heat tests show the following,");
        add("block.rankine.material_testing_bench.heat_resistance", "Heat Resistance");
        add("block.rankine.material_testing_bench.heat_resistance.error", "Heat test inconclusive.");
        add("block.rankine.material_testing_bench.knockback_resistance.test", "Knockback tests show the following,");
        add("block.rankine.material_testing_bench.knockback_resistance", "Knockback Resistance");
        add("block.rankine.material_testing_bench.knockback_resistance.error", "Knockback test inconclusive.");
        add("block.rankine.material_testing_bench.toughness.test", "Toughness tests show the following,");
        add("block.rankine.material_testing_bench.toughness", "Toughness");
        add("block.rankine.material_testing_bench.toughness.error", "Toughness test inconclusive.");
        add("block.rankine.material_testing_bench.enchantments.test", "Enchanting tests show the following,");
        add("block.rankine.material_testing_bench.enchantments", "Enchantments");
        add("block.rankine.material_testing_bench.enchantments.error", "Enchanting test inconclusive.");
        add("block.rankine.material_testing_bench.exam.test", "Examination tests show the following,");
        add("block.rankine.material_testing_bench.exam", "Examination");
        add("block.rankine.material_testing_bench.exam.error", "Examination test inconclusive.");
        add("element.rankine:elements/copper.preview", "Commonly used in early alloys.");
        add("element.rankine:elements/copper.desc0", "Copper is a transition metal with an atomic number of 29.");
        add("element.rankine:elements/copper.desc1", "Commonly used in electrical components and early alloys.");
        add("element.rankine:elements/copper.desc2", "Commonly used in electrical components and early alloys.");

        add("item.rankine.acanthite.tooltip0", "Ag2S");
        add("item.rankine.acanthite.tooltip1", "Used as a source of silver.");
        add("item.rankine.alumina.tooltip0", "Al2O3");
        add("item.rankine.alumina.tooltip1", "Used as a source of aluminum.");
        add("item.rankine.azurite.tooltip0", "Cu3(CO3)2(OH)2");
        add("item.rankine.azurite.tooltip1", "Used as a source of copper and light blue dye.");
        add("item.rankine.azurmalachite.tooltip0", "Cu2(CO3)(OH)2+Cu3(CO3)2(OH)2");
        add("item.rankine.azurmalachite.tooltip1", "Used as a source of copper and cyan dye.");
        add("item.rankine.baddeleyite.tooltip0", "ZrO2");
        add("item.rankine.baddeleyite.tooltip1", "Used as a source of zirconia.");
        add("item.rankine.barite.tooltip0", "BaSO4");
        add("item.rankine.barite.tooltip1", "Used as a source of barium.");
        add("item.rankine.bauxite.tooltip0", "Rock sample with high aluminum content.");
        add("item.rankine.bauxite.tooltip1", "Used as a precursor to alumina.");
        add("item.rankine.cerium_monazite.tooltip0", "CePO4");
        add("item.rankine.cerium_monazite.tooltip1", "Used as a source of cerium and other rare-earth elements.");

        add("item.rankine.totem_of_adhering.tooltip", "Stuck to the plan");
        add("item.rankine.totem_of_blazing.tooltip", "Burning up inside");
        add("item.rankine.totem_of_cobbling.tooltip", "Long term stone storage");
        add("item.rankine.totem_of_enduring.tooltip", "Built in body shield");
        add("item.rankine.totem_of_hastening.tooltip", "Efficiency, but in your hands");
        add("item.rankine.totem_of_infusing.tooltip", "Symbiotic enchanting");
        add("item.rankine.totem_of_invigorating.tooltip", "Regenerative equipment");
        add("item.rankine.totem_of_imitating.tooltip", "False promises");
        add("item.rankine.totem_of_levitating.tooltip", "For keeping your feet clean");
        add("item.rankine.totem_of_mending.tooltip", "Regenerate your losses");
        add("item.rankine.totem_of_promising.tooltip", "Fortune is on your side");
        add("item.rankine.totem_of_repulsing.tooltip", "Invisibility while visible");
        add("item.rankine.totem_of_softening.tooltip", "Soft fingers and toes");
        add("item.rankine.totem_of_timesaving.tooltip", "Handheld speed beacon");


        add("fluid.rankine.aqua_regia", "Aqua Regia");
        add("fluid.rankine.carbon_disulfide", "Carbon Disulfide");
        add("fluid.rankine.gray_mud", "Gray Mud");
        add("fluid.rankine.hexafluorosilicic_acid", "Hexafluorosilicic Acid");
        add("fluid.rankine.hydrobromic_acid", "Hydrobromic Acid");
        add("fluid.rankine.juglone", "Juglone");
        add("fluid.rankine.latex", "Latex");
        add("fluid.rankine.liquid_mercury", "Liquid Mercury");
        add("fluid.rankine.maple_sap", "Maple Sap");
        add("fluid.rankine.red_mud", "Red Mud");
        add("fluid.rankine.resin", "Resin");
        add("fluid.rankine.sap", "Sap");
        add("fluid.rankine.sulfuric_acid", "Sulfuric Acid");
        add("fluid.rankine.black_liquor", "Black Liquor");
        add("fluid.rankine.green_liquor", "Green Liquor");
        add("fluid.rankine.white_liquor", "White Liquor");

        //ADVANCEMENTS
        add("rankine.advancements.story.root.title", "Project Rankine");
        add("rankine.advancements.story.root.description", "The project starts here");
        add("rankine.advancements.story.support.title", "We support Patchouli and JEI!");
        add("rankine.advancements.story.support.description", "Be sure to download these mods if you want more information!");
        add("rankine.advancements.story.get_flint.title", "Flintstones");
        add("rankine.advancements.story.get_flint.description", "Obtain flint from any source, now including rocks when using lower-tier tools");
        add("rankine.advancements.story.craft_rope.title", "Knot Likely");
        add("rankine.advancements.story.craft_rope.description", "Gather rope by obtaining a flint knife and harvesting grass");
        add("rankine.advancements.story.craft_dowsing_rod.title", "Random Guess");
        add("rankine.advancements.story.craft_dowsing_rod.description", "Make a Dowsing Rod to detect the water level of the area");
        add("rankine.advancements.story.craft_wooden_mallet.title", "Time to Swing");
        add("rankine.advancements.story.craft_wooden_mallet.description", "Make a Wooden Mallet to start swinging in rhythm at rocks and other materials");
        add("rankine.advancements.story.craft_stone_mallet.title", "Stone to crush Stone");
        add("rankine.advancements.story.craft_stone_mallet.description", "Construct a stronger mallet");
        add("rankine.advancements.story.get_cobblestone.title", "Cobble Cobble");
        add("rankine.advancements.story.get_cobblestone.description", "Obtain cobblestone from crushing using a mallet or hammer");
        add("rankine.advancements.story.craft_mixing_barrel.title", "Mix it Up");
        add("rankine.advancements.story.craft_mixing_barrel.description", "Make a mixing barrel to start mixing materials with redstone signals");
        add("rankine.advancements.story.craft_fire_extinguisher.title", "Only You Can Prevent Fires");
        add("rankine.advancements.story.craft_fire_extinguisher.description", "Make a fire extinguisher to stop fires in their tracks");
        add("rankine.advancements.story.craft_rock_drill.title", "Rock Sample");
        add("rankine.advancements.story.craft_rock_drill.description", "Make a rock drill to detect rock layers");
        add("rankine.advancements.story.craft_prospecting_stick.title", "Help Wanted");
        add("rankine.advancements.story.craft_prospecting_stick.description", "Make a Prospecting Stick to point the way to ores");
        add("rankine.advancements.story.craft_ore_detector.title", "Help Granted");
        add("rankine.advancements.story.craft_ore_detector.description", "Make an Ore Detector to replace that x-ray mod");
        add("rankine.advancements.story.craft_hardness_tester.title", "Strength in Knowledge");
        add("rankine.advancements.story.craft_hardness_tester.description", "Make a Hardness Tester to easily determine harvest level");
        add("rankine.advancements.story.craft_wooden_gold_pan.title", "Forager");
        add("rankine.advancements.story.craft_wooden_gold_pan.description", "Make a Wooden Gold Pan to sift through loose material");
        add("rankine.advancements.story.craft_steel_gold_pan.title", "Direct Deposit");
        add("rankine.advancements.story.craft_steel_gold_pan.description", "Make a Steel Gold Pan to sieve alluvium and black sand");
        add("rankine.advancements.story.craft_flint_axe.title", "Hard Wood");
        add("rankine.advancements.story.craft_flint_axe.description", "Make a Flint Axe (or stone) to harvest wood without punching trees");
        add("rankine.advancements.story.craft_charcoal_pit.title", "Buried Logs");
        add("rankine.advancements.story.craft_charcoal_pit.description", "Make a Charcoal Pit to help alleviate early fuel requirements and craft large batches of charcoal");
        add("rankine.advancements.story.craft_flint_pickaxe.title", "Sharp Beginnings");
        add("rankine.advancements.story.craft_flint_pickaxe.description", "Construct a Flint Pickaxe (or stone) to begin mining quickly");
        add("rankine.advancements.story.craft_refractory_bricks.title", "Another Brick in the Wall");
        add("rankine.advancements.story.craft_refractory_bricks.description", "Make Refractory Brick by finding clay and using mortar");
        add("rankine.advancements.story.craft_high_refractory_bricks.title", "Another Other Brick in the Wall");
        add("rankine.advancements.story.craft_high_refractory_bricks.description", "Make High Refractory Bricks to upgrade");
        add("rankine.advancements.story.craft_alloy_furnace.title", "Combined Strength");
        add("rankine.advancements.story.craft_alloy_furnace.description", "Make an Alloy Furnace by using sheetmetal and refractory bricks");
        add("rankine.advancements.story.craft_material_testing_table.title", "Knowledge of Strength");
        add("rankine.advancements.story.craft_material_testing_table.description", "Make a Material Testing Table to begin examining properties of elements and alloys");
        add("rankine.advancements.story.craft_blast_furnace.title", "Blast from the Past");
        add("rankine.advancements.story.craft_blast_furnace.description", "Make a Blast Furnace to begin smelting more complex ores");
        add("rankine.advancements.story.craft_beehive_oven_pit.title", "No Bees Harmed");
        add("rankine.advancements.story.craft_beehive_oven_pit.description", "Build a beehive oven pit");
        add("rankine.advancements.story.craft_template_table.title", "The Plan");
        add("rankine.advancements.story.craft_template_table.description", "Make an Alloy Template Table to automate the creation of alloys");
        add("rankine.advancements.story.make_magnesia.title", "Take the Heat");
        add("rankine.advancements.story.make_magnesia.description", "Make magnesia from cooking a magnesite block with the beehive oven");
        add("rankine.advancements.story.make_coke.title", "I cant believe it's not Coal!");
        add("rankine.advancements.story.make_coke.description", "Use a beehive oven to produce coke from bituminous and sub-bituminous coal blocks");
        add("rankine.advancements.story.make_cast_iron.title", "Cast in Iron");
        add("rankine.advancements.story.make_cast_iron.description", "Alloy iron into Cast Iron");
        add("rankine.advancements.story.make_quicklime.title", "Limecraft");
        add("rankine.advancements.story.make_quicklime.description", "Produce quicklime from combusting limestone with the beehive oven");
        add("rankine.advancements.story.craft_crucible.title", "Refractory Factory");
        add("rankine.advancements.story.craft_crucible.description", "Construct a crucible to form steel");
        add("rankine.advancements.story.make_steel.title", "Steel Yourself");
        add("rankine.advancements.story.make_steel.description", "Make Steel Alloy by using the Crucible or the Induction Furnace");
        add("rankine.advancements.story.craft_brigandine_armor.title", "Plate Up");
        add("rankine.advancements.story.craft_brigandine_armor.description", "Make a full set of Brigandine Armor");
        add("rankine.advancements.story.craft_diving_armor.title", "Deeper Waters");
        add("rankine.advancements.story.craft_diving_armor.description", "Make a full set of Diving Armor");
        add("rankine.advancements.story.craft_conduit_diving_armor.title", "Into the Abyss");
        add("rankine.advancements.story.craft_conduit_diving_armor.description", "Make a full set of Conduit Diving Armor");
        add("rankine.advancements.story.craft_gas_mask.title", "Breathing In");
        add("rankine.advancements.story.craft_gas_mask.description", "Make a gas mask to protect against hazardous gases");
        add("rankine.advancements.story.craft_shulker_gas_vacuum.title", "Vacuum Sealed");
        add("rankine.advancements.story.craft_shulker_gas_vacuum.description", "Make a shulker gas vacuum to transport gases");
        add("rankine.advancements.story.craft_evaporation_tower.title", "Water Extraction");
        add("rankine.advancements.story.craft_evaporation_tower.description", "Construct an Evaporation Tower to generate resources from water in different biomes");
        add("rankine.advancements.story.craft_piston_crusher.title", "Automatic Labor");
        add("rankine.advancements.story.craft_piston_crusher.description", "Make a piston crusher to automate crushing");
        add("rankine.advancements.story.craft_power_cell.title", "Fully Charged");
        add("rankine.advancements.story.craft_power_cell.description", "Make any power cell for use in advanced machines");
        add("rankine.advancements.story.craft_electromagnet.title", "Moving Metals");
        add("rankine.advancements.story.craft_electromagnet.description", "Make any type of Electromagnet Block");
        add("rankine.advancements.story.craft_saddle_tree.title", "A Horse");
        add("rankine.advancements.story.craft_saddle_tree.description", "Craft a saddle tree to make horse-related equipment");
        add("rankine.advancements.story.craft_fusion_furnace.title", "Complex Systems");
        add("rankine.advancements.story.craft_fusion_furnace.description", "Make a Fusion Furnace to begin utilizing gases and liquids further");
        add("rankine.advancements.story.get_mercury.title", "Amalgamation");
        add("rankine.advancements.story.get_mercury.description", "Obtain mercury to make Amalgam alloys");
        add("rankine.advancements.story.craft_distillation_tower.title", "Atmospheric Composition");
        add("rankine.advancements.story.craft_distillation_tower.description", "Make a Distillation Tower to obtain gases from the air");
        add("rankine.advancements.story.craft_gyratory_crusher.title", "Pressing Issue");
        add("rankine.advancements.story.craft_gyratory_crusher.description", "Make a Gyratory Crusher to automate crushing further");
        add("rankine.advancements.story.craft_induction_furnace.title", "Heating Up");
        add("rankine.advancements.story.craft_induction_furnace.description", "Make an Induction Furnace for more advanced alloys");
        add("rankine.advancements.story.craft_sediment_fan.title", "Spin Cycle");
        add("rankine.advancements.story.craft_sediment_fan.description", "Make a Sediment Fan to generate Sedimentary Stones");
        add("rankine.advancements.story.craft_magnet.title", "Attraction");
        add("rankine.advancements.story.craft_magnet.description", "Make a handheld magnet to bring items closer to you");
        add("rankine.advancements.story.craft_tree_tap.title", "Flowing Phloem");
        add("rankine.advancements.story.craft_tree_tap.description", "Make a tree tap to begin extracting liquids from trees");
        add("rankine.advancements.story.craft_ground_tap.title", "Water Reservoir");
        add("rankine.advancements.story.craft_ground_tap.description", "Make a ground tap to bring water to the surface");
        add("rankine.advancements.story.craft_battery.title", "Fully Charged");
        add("rankine.advancements.story.craft_battery.description", "Make a battery to provide power to certain machines");
        add("rankine.advancements.story.craft_bandage.title", "Quick Healing");
        add("rankine.advancements.story.craft_bandage.description", "Heal some minor damage with a bandage");
        add("rankine.advancements.story.craft_alloy_hammer.title", "Crushed");
        add("rankine.advancements.story.craft_alloy_hammer.description", "Make a hammer with a harvest level of 2 or greater");
        add("rankine.advancements.story.craft_trampoline.title", "Boing!");
        add("rankine.advancements.story.craft_trampoline.description", "Make a trampoline to launch high into the air");
        add("rankine.advancements.story.craft_fertilizer.title", "Weedhelper");
        add("rankine.advancements.story.craft_fertilizer.description", "Produce fertilizer to restore patches of grass");
        add("rankine.advancements.story.craft_herbicide.title", "Weedkiller");
        add("rankine.advancements.story.craft_herbicide.description", "Produce herbicide from juglone to clear patches of grass and other plants");
        add("rankine.advancements.story.get_meteoric_iron.title", "Iron from Space");
        add("rankine.advancements.story.get_meteoric_iron.description", "Find a meteorite and crush the ore in it to get meteoric iron");
        add("rankine.advancements.story.get_ironstone.title", "Iron in the Rough");
        add("rankine.advancements.story.get_ironstone.description", "Find ironstone under the sands of a desert or mesa");
        add("rankine.advancements.story.get_bog_iron.title", "Iron in the Flood");
        add("rankine.advancements.story.get_bog_iron.description", "Find Bog Iron submerged in a swamp");
        add("rankine.advancements.story.craft_element_indexer.title", "Elementary");
        add("rankine.advancements.story.craft_element_indexer.description", "Use a device to examine element properties");
        add("rankine.advancements.story.info_movement.title", "A Primer on Movement");
        add("rankine.advancements.story.info_movement.description", "Movement (by default) changes depending on what material you are walking on");
        add("rankine.advancements.story.craft_sandals.title", "Sand Shifter");
        add("rankine.advancements.story.craft_sandals.description", "Make sandals to move more efficiently on sand");
        add("rankine.advancements.story.craft_ice_skates.title", "Ice Glider");
        add("rankine.advancements.story.craft_ice_skates.description", "Make ice skates to move more efficiently on ice");
        add("rankine.advancements.story.craft_snowshoes.title", "Snow Effort");
        add("rankine.advancements.story.craft_snowshoes.description", "Make snowshoes to move more efficiently on snow");




        add("rankine.advancements.story.get_bronze_tools.title", "Timing is Everything");
        add("rankine.advancements.story.get_bronze_tools.description", "Make a Pewter, Colored Gold, or Bronze Hammer to begin crushing ores and stones");
        add("rankine.advancements.story.get_crowbar.title", "Lever Action");
        add("rankine.advancements.story.get_crowbar.description", "Make a Crowbar to manipulate blocks");
        add("rankine.advancements.story.craft_pulp.title", "Crushed to a Pulp");
        add("rankine.advancements.story.craft_pulp.description", "Make pulp from crushed logs");
        add("rankine.advancements.story.craft_quartz.title", "Overworld Quartz");
        add("rankine.advancements.story.craft_quartz.description", "Find or craft quartz by crushing stones");
        add("rankine.advancements.story.craft_glass_cutter.title", "Transparent Carving");
        add("rankine.advancements.story.craft_glass_cutter.description", "Make a glass cutter to minimize losses");
        add("rankine.advancements.story.craft_roman_concrete.title", "Roaming like the Romans");
        add("rankine.advancements.story.craft_roman_concrete.description", "Make roman concrete from pozzolanic mortar");
        add("rankine.advancements.story.craft_nickel_superalloy.title", "Diving Checklist");
        add("rankine.advancements.story.craft_nickel_superalloy.description", "Make every available type of Nickel Superalloy tool");
        add("rankine.advancements.story.craft_stainless_steel.title", "Always Prepared");
        add("rankine.advancements.story.craft_stainless_steel.description", "Make every available type of Stainless Steel tool");
        add("rankine.advancements.story.craft_tungsten_heavy_alloy.title", "Bound to the Fire");
        add("rankine.advancements.story.craft_tungsten_heavy_alloy.description", "Make every available type of Tungsten Heavy Alloy tool");
        add("rankine.advancements.story.craft_cobalt_superalloy.title", "Lasting Impression");
        add("rankine.advancements.story.craft_cobalt_superalloy.description", "Make every available type of Cobalt Superalloy tool");
        add("rankine.advancements.story.craft_laser_quarry.title", "Excavation Invigoration");
        add("rankine.advancements.story.craft_laser_quarry.description", "Construct a Laser Quarry to mine out large chunks of the world");
        add("rankine.advancements.story.craft_thorium_arrow.title", "Sounds of Thunder");
        add("rankine.advancements.story.craft_thorium_arrow.description", "Make Thorium Arrows to control the power of lightning with your bow");



        add("rankine.advancements.challenges.root.title", "Project Rankine Challenges");
        add("rankine.advancements.challenges.root.description", "Complete your understanding of the changed world");
        add("rankine.advancements.challenges.rock_collector.title", "We Have the Rockiest Rocks");
        add("rankine.advancements.challenges.rock_collector.description", "Collect all the new types of rocks");
        add("rankine.advancements.challenges.dirt_collector.title", "We Have the Dirtiest Dirt");
        add("rankine.advancements.challenges.dirt_collector.description", "Collect all the new types of soil, not dirt");
        add("rankine.advancements.challenges.element_collector.title", "Periodic Collector");
        add("rankine.advancements.challenges.element_collector.description", "Collect every element in the Periodic Table");
        add("rankine.advancements.challenges.make_all_power_cells.title", "Energized");
        add("rankine.advancements.challenges.make_all_power_cells.description", "Make every type of Power Cell");
        add("rankine.advancements.challenges.craft_cannonball.title", "Cannonball");
        add("rankine.advancements.challenges.craft_cannonball.description", "Use a cannonball in a dispenser");
        add("rankine.advancements.challenges.totem_collector.title", "On the Other Hand");
        add("rankine.advancements.challenges.totem_collector.description", "Make every type of Totem");
        add("rankine.advancements.challenges.alloy_collector.title", "Worldmoulder");
        add("rankine.advancements.challenges.alloy_collector.description", "Create all of the different types of alloys");
        add("rankine.advancements.challenges.bronze_harvest.title", "Skipping Stones");
        add("rankine.advancements.challenges.bronze_harvest.description", "Make a Bronze Pickaxe with a Harvest Level of 2 (or Invar equivalent)");
        add("rankine.advancements.challenges.pewter_enchant.title", "Hidden Power");
        add("rankine.advancements.challenges.pewter_enchant.description", "Make a Pewter Tool with an Enchantability greater than or equal to 14");
        add("rankine.advancements.challenges.colored_gold_netherite.title", "Gold Standard");
        add("rankine.advancements.challenges.colored_gold_netherite.description", "Make a Colored Gold tool using Netherite instead of Gold");
        add("rankine.advancements.challenges.make_pancake_breakfast.title", "Fulfilling");
        add("rankine.advancements.challenges.make_pancake_breakfast.description", "Make a Pancake Breakfast");
        add("rankine.advancements.challenges.find_meteoric_materials.title", "Stellar Performance");
        add("rankine.advancements.challenges.find_meteoric_materials.description", "Find all the forms of meteoric iron");
        add("rankine.advancements.challenges.geode_collector.title", "Gifts from the Earth");
        add("rankine.advancements.challenges.geode_collector.description", "Find all of the different types of geodes");


        //SUBTITLES
        add("rankine.subtitle.totem_of_enduring_use", "totem use");
        add("rankine.subtitle.penning_trap_absorb", "Penning Trap absorb");
        add("rankine.subtitle.shulker_gas_vacuum_absorb", "Shulker Gas Vacuum absorb");
        add("rankine.subtitle.shulker_gas_vacuum_release", "Shulker Gas Vacuum release");
        add("rankine.subtitle.sediment_fan_gen", "sedimentary block generation");


        //JOURNAL==============================================================================
        add("rankine.journal.landing_text", "A mere collection of discoveries, awaiting application.");

        //Orientation
        add("rankine.journal.cat_orientation.name", "Orientation");
        add("rankine.journal.cat_orientation.desc", "Notes from very brief orientation to the Project. These are all I have to look over for now.");

        add("rankine.journal.cat_orientation.intro.name", "Greetings");
        add("rankine.journal.cat_orientation.intro.text1", "$(o)If you find this journal, then I welcome to a new world. You may find it familiar to the standards that you are used to. However, this is a Rankine world. In short that means I've changed some things to make it a little bit more interesting. Enjoy. $(br2)But seriously, if you want to know what's going on, read the foundation entries series.");
        add("rankine.journal.cat_orientation.intro.text2", "$(o)For more resources see the following pages.");

        add("rankine.journal.cat_orientation.foundation1.name", "Foundation I");
        add("rankine.journal.cat_orientation.foundation1.text1", "Lesson one, not all information can be shared via this journal. Some of it requires discovering it for yourself and utilizing JEI for many recipe related inquiries. The new changes are all about variety. There are more ground blocks, more ores, more processing mechanics, more ways to achieve. Utilize this variety to bolster creativity or just to have pretty colors. Regardless, let's see some changes.");
        add("rankine.journal.cat_orientation.foundation1.text2", "Very Early Game");
        add("rankine.journal.cat_orientation.foundation1.text3", "The beginning is very similar to normal, unless you aren't allowed to punch wood. In addition to wooden and stone tools, a flint toolset is available to start your journey beyond your fists. While from the beginning you could avoid all Rankine tool content, that seems unlikely and you may want the basic equipment to keep your journey running smoothly.");
        add("rankine.journal.cat_orientation.foundation1.text4", "Use a $(l:tools/standard_tools)flint knife$() to gather plant fiber and wool and use a wooden gold pan to $(l:mechanics/sluicing)sluice$() earthy blocks for useful resources. $(br2)One of the new tool types added is $(l:tools/standard_tools)hammers$(). If you are trying to get cobblestone, that's how. Hammers don't simply convert a block into a less shapely version, they refine blocks by extracting additional materials through $(l:mechanics/crushing)crushing$(). It is an important tool and concept for later requirements, so be sure to always have a hammer ready.");
        add("rankine.journal.cat_orientation.foundation1.text5", "Worldgen");
        add("rankine.journal.cat_orientation.foundation1.text6", "A few changes that you are sure to notice are the additions to the natural world. A collection of $(l:sediments/soils)soils$() and other sediments replace much of the original surface. Below the surface new $(l:stones)stones$() emerge from the depths of the mantle, creating vast layers across the biomes. A secondary form of stones is intrusions, large pillars of rock extending from bedrock to the surface.");
        add("rankine.journal.cat_orientation.foundation1.text7", "Stones are generally found in one of these categories and contain mineral themselves, but are also home to the more common ore veins. Ores are explored further in the next foundation. Other aspects to note about the world include new biota. Various specimens of $(l:biota/trees)trees$(), $(l:biota/crops)crops$(), $(l:biota/ground_flora)ground plants$(), $(l:biota/mushrooms)mushrooms$() and perhaps some animals can be discovered across the world. Although they aren't tools, they are still key to your survival.");
        add("rankine.journal.cat_orientation.foundation1.text8", "Even familiar blocks in this new environment have changed. Walking on certain earthen and constructed blocks affect movement speed. Using $(l:tools/equipment)equipment$() can combat these and other environmental forces. Another note about the environment is that breaking dirt blocks with an open hand or with crude tools will $(l:mechanics/foraging)forage$() for random items.");


        add("rankine.journal.cat_orientation.foundation2.name", "Foundation II");
        add("rankine.journal.cat_orientation.foundation2.text1", "This section details some of the nuances of the underground and how to get started with Rankine technology. Other important mechanics to note at this stage is that there is no $(l:mechanics/finite_water)infinite water$() at certain heights, fires can be created by using a flint in each hand, and stripping bark off trees can yield additional resources.");
        add("rankine.journal.cat_orientation.foundation2.text2", "Basic Machines");
        add("rankine.journal.cat_orientation.foundation2.text3", "You may be looking to fuel your furnace. Without immediate access to coal substances, a great alternative is charcoal. It is not suggested that you burn planks or logs directly as $(l:mechanics/fuel)fuel values$() have changed. A free fuel option is campfires, which have a few additional non-food related recipes. And to save time long-term, upgrade to blast furnaces (alternative recipe available) as soon as possible, there will be a lot of ore to smelt. ");
        add("rankine.journal.cat_orientation.foundation2.text4", "The two basic machines needed to produce early materials are the Mixing Barrel and the $(l:materials/beehive_oven)beehive oven$(). Learn how to use these well and even automate them as they produce required and niche recipes. If you don't like the more involved method of producing bricks, you can utilize the stone cutter later.");
        add("rankine.journal.cat_orientation.foundation2.text5", "Ultimately one of the most important machines you will make is the alloy furnace. $(l:mechanics/alloying)Alloying$() is a spectrum of simple metal ratios to intricate tool materials. Invest yourself as much as desired into experimenting with the process to produce suitable tools as well as crafting components and building materials.");
        add("rankine.journal.cat_orientation.foundation2.text6", "Oregen");
        add("rankine.journal.cat_orientation.foundation2.text7", "After making a set of stone-like tools, you may be able to skip the long trip to bedrock. Look along exposed surface stones for $(l:mineralogy/native_ores)native ores$(). These are the source of early harvestable metals that will let you make the next tiers of tools. Other forms of ores include a mixed variety of $(l:mineralogy/mineral_ores)mineral ores$() and $(l:mineralogy/gem_ores)gem ores$() which are found across the dimensions.");
        add("rankine.journal.cat_orientation.foundation2.text8", "Typical vein structure is as large clusters spaced out in the underground or as a dedicated deposit in a stone intrusion. If you need assistance finding ores, consider $(l:tools/ore_detection)prospecting equipment$().");

        add("rankine.journal.cat_orientation.foundation3.name", "Foundation III");
        add("rankine.journal.cat_orientation.foundation3.text1", "With alloys, a plethora of new capabilities are available. This section details some of the mid game advancements in technology and constructions.");
        add("rankine.journal.cat_orientation.foundation3.text2", "Progression");
        add("rankine.journal.cat_orientation.foundation3.text3", "Some common harvest level 2 tools depending on composition are bronze, pewter, invar, osmiridium, and iron. $(l:mineralogy/meteorites)Meteorites$() are a source of native invar and occur across the world surface. $(br2)The standard method of obtaining harvest level 3 tools is by producing steel. This, along with other interesting products, is made in the $(l:materials/crucible)crucible$().");
        add("rankine.journal.cat_orientation.foundation3.text4", "Steel allows for the creation of the $(l:materials/fusion_furnace)fusion furnace$(), a machine capable of reactions and recipes using three states of matter. Available recipes include advanced ore processing, extractions, and chemical conversions.");
        add("rankine.journal.cat_orientation.foundation3.text5", "Other Machines");
        add("rankine.journal.cat_orientation.foundation3.text6", "Two large scale machines can be constructed to passively produce resources. The $(l:materials/evaporation_tower)evaporation tower$() solidifies materials out of liquids. The smaller structure is an evaporation boiler which is used for more unique fluids that are product from tree tapping. $(l:materials/tree_tapping)Tree tapping$() is a process of extracting fluid from trees with more or less minimal damage. Use this combination to make whole new product lines.");
        add("rankine.journal.cat_orientation.foundation3.text7", "The other large structure is the air distillation tower. An uncommon block in their pure form, $(l:materials/gasses)gasses$() are useful in fusion furnace reactions and provide effects when breathed in instead of air.  $(br2)One final note is that you can build automated versions of hammers, the $(l:mechanics/crushing)piston crusher$() and the $(l:mechanics/crushing)gyratory crusher$().");


        //Biota
        add("rankine.journal.cat_biota.name", "Biota");
        add("rankine.journal.cat_biota.desc", "Plants be cool.");

        add("rankine.journal.cat_biota.trees.text1", "Trees be cool.");
        add("rankine.journal.cat_biota.trees.text2", "Cedar");
        add("rankine.journal.cat_biota.trees.text3", "Balsam Fir");


        add("rankine.journal.cat_biota.mushrooms.name", "Mushrooms");
        add("rankine.journal.cat_biota.mushrooms.text1", "New types of fungi populate the undergrowth of wooded ecosystems. Although they have a different growth pattern, some can be used similar to the common red and brown varieties.");
        add("rankine.journal.cat_biota.mushrooms.edible_mushrooms_header", "Edible Mushrooms");
        add("rankine.journal.cat_biota.mushrooms.edible_mushrooms", "");
        add("rankine.journal.cat_biota.mushrooms.inedible_mushrooms_header", "Inedible Mushrooms");
        add("rankine.journal.cat_biota.mushrooms.inedible_mushrooms", "");

        add("rankine.journal.cat_biota.ground_flora.name", "Ground Flora");
        add("rankine.journal.cat_biota.ground_flora.text1",  "");
        add("rankine.journal.cat_biota.ground_flora.morning_glories_header", "Morning Glories");
        add("rankine.journal.cat_biota.ground_flora.morning_glories", "");
        add("rankine.journal.cat_biota.ground_flora.goldenrod", "");
        add("rankine.journal.cat_biota.ground_flora.lilies_header", "Lilies");
        add("rankine.journal.cat_biota.ground_flora.lilies", "");
        add("rankine.journal.cat_biota.ground_flora.clovers_header", "Clovers");
        add("rankine.journal.cat_biota.ground_flora.clovers", "");
        add("rankine.journal.cat_biota.ground_flora.stinging_nettle", "");
        add("rankine.journal.cat_biota.ground_flora.short_grass", "");

        add("rankine.journal.cat_biota.trees.name", "Trees");
        add("rankine.journal.cat_biota.trees.cedar.title", "");
        add("rankine.journal.cat_biota.trees.cedar", "");
        add("rankine.journal.cat_biota.trees.balsam_fir.title", "");
        add("rankine.journal.cat_biota.trees.balsam_fir", "");
        add("rankine.journal.cat_biota.trees.eastern_hemlock.title", "");
        add("rankine.journal.cat_biota.trees.eastern_hemlock", "");
        add("rankine.journal.cat_biota.trees.western_hemlock.title", "");
        add("rankine.journal.cat_biota.trees.western_hemlock", "");
        add("rankine.journal.cat_biota.trees.pinyon_pine.title", "");
        add("rankine.journal.cat_biota.trees.pinyon_pine", "");
        add("rankine.journal.cat_biota.trees.juniper.title", "");
        add("rankine.journal.cat_biota.trees.juniper", "");
        add("rankine.journal.cat_biota.trees.black_birch.title", "");
        add("rankine.journal.cat_biota.trees.black_birch", "");
        add("rankine.journal.cat_biota.trees.yellow_birch.title", "");
        add("rankine.journal.cat_biota.trees.yellow_birch", "");
        add("rankine.journal.cat_biota.trees.red_birch.title", "");
        add("rankine.journal.cat_biota.trees.red_birch", "");
        add("rankine.journal.cat_biota.trees.maple.title", "");
        add("rankine.journal.cat_biota.trees.maple", "");
        add("rankine.journal.cat_biota.trees.black_walnut.title", "");
        add("rankine.journal.cat_biota.trees.black_walnut", "");
        add("rankine.journal.cat_biota.trees.coconut_palm.title", "");
        add("rankine.journal.cat_biota.trees.coconut_palm", "");
        add("rankine.journal.cat_biota.trees.cork_oak.title", "");
        add("rankine.journal.cat_biota.trees.cork_oak", "");
        add("rankine.journal.cat_biota.trees.sharinga.title", "");
        add("rankine.journal.cat_biota.trees.sharinga", "");
        add("rankine.journal.cat_biota.trees.cinnamon.title", "");
        add("rankine.journal.cat_biota.trees.cinnamon", "");
        add("rankine.journal.cat_biota.trees.honey_locust.title", "");
        add("rankine.journal.cat_biota.trees.honey_locust", "");
        add("rankine.journal.cat_biota.trees.weeping_willow.title", "");
        add("rankine.journal.cat_biota.trees.weeping_willow", "");

        add("rankine.journal.cat_biota.crops.name", "Crops");
        add("rankine.journal.cat_biota.crops.text1", "");
        add("rankine.journal.cat_biota.crops.barley", "");
        add("rankine.journal.cat_biota.crops.rice", "");
        add("rankine.journal.cat_biota.crops.millet", "");
        add("rankine.journal.cat_biota.crops.rye", "");
        add("rankine.journal.cat_biota.crops.oats", "");
        add("rankine.journal.cat_biota.crops.sorghum", "");
        add("rankine.journal.cat_biota.crops.corn_ear", "");
        add("rankine.journal.cat_biota.crops.soybeans", "");
        add("rankine.journal.cat_biota.crops.asparagus", "");
        add("rankine.journal.cat_biota.crops.jute", "");
        add("rankine.journal.cat_biota.crops.cotton", "");
        add("rankine.journal.cat_biota.crops.blueberries", "");
        add("rankine.journal.cat_biota.crops.cranberries", "");
        add("rankine.journal.cat_biota.crops.elderberries", "");
        add("rankine.journal.cat_biota.crops.pokeberries", "");
        add("rankine.journal.cat_biota.crops.strawberries", "");
        add("rankine.journal.cat_biota.crops.snowberries", "");
        add("rankine.journal.cat_biota.crops.raspberries", "");
        add("rankine.journal.cat_biota.crops.blackberries", "");
        add("rankine.journal.cat_biota.crops.pineapple", "");
        add("rankine.journal.cat_biota.crops.aloe", "");
        add("rankine.journal.cat_biota.crops.banana_yucca", "");
        add("rankine.journal.cat_biota.crops.camphor_basil_leaf", "");


        //Materials
        add("rankine.journal.cat_materials.name", "Materials");
        add("rankine.journal.cat_materials.desc", "Useable materials come from all sorts of places. Some are simply harvested raw from the world, while others require some manufacturing and refinement to obtain.");

        add("rankine.journal.cat_materials.mixing_barrel.name", "Mixing Barrel");
        add("rankine.journal.cat_materials.mixing_barrel.text1", "The mixing barrel combines together materials and fluid of various composition. Place items in any of the four items slots and their relative compositions will be displayed. Add fluid to the barrel by right clicking with a bucket. When an appropriate recipe is in the barrel, supply redstone pulses to the block to begin spinning. Each recipe has a variable number of spins needed to complete based off the redstone signal strength.");
        add("rankine.journal.cat_materials.mixing_barrel.text2", "A recipe is defined by ");


        add("rankine.journal.cat_materials.gasses.name", "Gas Blocks");
        add("rankine.journal.cat_materials.gasses.text1", "Gas blocks are similar to air in how you can't really interact wth them. When your head is inside a gas block, you become affected by various effects and may potentially suffocate. They rarely occur naturally except for from $(l:mineralogy/fumaroles)fumaroles$().");
        add("rankine.journal.cat_materials.gasses.text2", "The shulker gas vacuum can store gasses internally by right clicking inside a gas block. Gasses can be placed in a new location in a likewise manner. If glass bottles are present in the offhand, the vacuum will fill the bottles.");
        add("rankine.journal.cat_materials.gasses.text3", "The gas vent is an orientable block that moves gas blocks from one side to the opposite.");
        add("rankine.journal.cat_materials.gasses.text4", "The gas bottler intakes gas block from the front side and bottles them using glass bottles from its inventory.");
        add("rankine.journal.cat_materials.gasses.text5", "The air distillation tower intakes ambient air and separates out the gaseous components. The multiblock is shown on the next page. Pay careful attention to the internal column of the tower as there are air gaps. Gas vents can be substituted in place of sheetmetal to move gas from inside to out.");

        add("rankine.journal.cat_materials.fusion_furnace.name", "Fusion Furnace");
        add("rankine.journal.cat_materials.fusion_furnace.text1", "The Fusion Furnace utilizes solids, liquids, and gases to make new outputs. Many recipes which involve gases will require Bottled Gases and Empty Bottles. You can acquire Bottled Gases using either the $(l:materials/gasses)Gas Bottler$() or the $(l:materials/gasses)Shulker Gas Vacuum$(). In order for a recipe to go through, you must have the required ingredients, fluid in the input fluid tank, and bottled gas in the slot adjacent to the tank.");
        add("rankine.journal.cat_materials.fusion_furnace.text2", " This machine does not use coal, and must use electrical energy from items such as Batteries or RTGs.");
        add("rankine.journal.cat_materials.fusion_furnace.batteries_header", "Batteries");
        add("rankine.journal.cat_materials.fusion_furnace.batteries", "Batteries are energy storage devices that fuel late game machines. Currently they are single use.");
        add("rankine.journal.cat_materials.fusion_furnace.rtg_header", "RTGs");
        add("rankine.journal.cat_materials.fusion_furnace.rtg", "RTGs are radioactively powered batteries with a much higher capacity.");

        add("rankine.journal.cat_materials.beehive_oven.name", "Beehive Oven");
        add("rankine.journal.cat_materials.beehive_oven.text1", "The beehive oven is an in-world furnace that slowly converts blocks. It will convert blocks one at a time in a 3x3 ring for the two blocks above it (16 blocks total). The beehive oven pit must have access to the sky. Light the pit block with the conventional methods. The pit block will go off it doesn't detect a valid structure or no longer has blocks to process.");
        add("rankine.journal.cat_materials.beehive_oven.text2", "The bricks used in the structure can be upgraded one at a time to the high and ultra high tiers to increase processing speed.");
        add("rankine.journal.cat_materials.beehive_oven.text4", "The minimum requirements for an oven.");
        add("rankine.journal.cat_materials.beehive_oven.text5", "The full structure for the oven.");


        //Mechanics
        add("rankine.journal.cat_mechanics.name", "Mechanics");
        add("rankine.journal.cat_mechanics.desc", "Gameplay additions / changes.");
        add("rankine.journal.cat_mechanics.foraging.name", "Foraging");
        add("rankine.journal.cat_mechanics.foraging.text1", "There is a chance to find a variety of seeds and roots when breaking dirt and related blocks. This will only happen when using an open hand or crude tools. The foraging enchantment for hoes adds additional items that can be dropped.");



        add("rankine.journal.cat_mechanics.finite_water.name", "Finite Water");
        add("rankine.journal.cat_mechanics.finite_water.text1", "A config enabled by default, which makes water slightly less renewable. Infinite water sources can only be created below the local water table height. A mechanical way of bringing water to the surface is described in the latter pages.");
        add("rankine.journal.cat_mechanics.finite_water.text2", "The Dowsing Rod is used to detect the local water height. Right clicking the ground will display a message above the hotbar.");
        add("rankine.journal.cat_mechanics.finite_water.text3", "The Ground Tap will generate a source block of water when connected to an appropriate pipe network. That network consists of a Flood Gate placed below the water table height and connected through Metal Pipes. Multiple taps can be on the same pipe line.");
        add("rankine.journal.cat_mechanics.finite_water.text4", "");
        add("rankine.journal.cat_mechanics.finite_water.text5", "");
        add("rankine.journal.cat_mechanics.sluicing.name", "Sluicing");
        add("rankine.journal.cat_mechanics.sluicing.text1", "Sluicing is a general term for the act of sifting through material. Right click a block with the appropriate sluicing tool to receive an item.");
        add("rankine.journal.cat_mechanics.sluicing.text2", "Gold Pans");
        add("rankine.journal.cat_mechanics.sluicing.text3", "Other items can be used to perform the sluicing action. The required item is shown in JEI.");

        //Mineralogy
        add("rankine.journal.cat_mineralogy.name", "Mineralogy");
        add("rankine.journal.cat_mineralogy.desc", "Shiny things");

        add("rankine.journal.cat_mineralogy.meteorites.name", "Meteorites");
        add("rankine.journal.cat_mineralogy.meteorites.text1","");
        add("rankine.journal.cat_mineralogy.meteorites.text2","Overworld Meteorite");
        add("rankine.journal.cat_mineralogy.meteorites.text3","Meteorites spawn in the Overworld and End.");
        add("rankine.journal.cat_mineralogy.meteorites.meteorites_header","Meteorite Blocks");
        add("rankine.journal.cat_mineralogy.meteorites.meteorites","Core silica based blocks making up most of the meteorite.");
        add("rankine.journal.cat_mineralogy.meteorites.tektites_header","Tektites");
        add("rankine.journal.cat_mineralogy.meteorites.tektites","Tektite is a form of glass formed by meteorite impact with the surface.");
        add("rankine.journal.cat_mineralogy.meteorites.ores_header","Meteorite Ores");
        add("rankine.journal.cat_mineralogy.meteorites.ores","Meteoric ores have been found as a native source of Invar and accessible source of iron. Crushing the ores will yield meteoric iron chunks which can be used to make crude tools.");
        add("rankine.journal.cat_mineralogy.meteorites.meteoric_ice","A rare form of ice only found in frozen meteorites. Like normal ice blocks, it is slippery and requires silk touch to harvest.");

        add("rankine.journal.cat_mineralogy.mineral_stones.name", "Mineral Stones");
        add("rankine.journal.cat_mineralogy.mineral_stones.text1","");
        add("rankine.journal.cat_mineralogy.mineral_stones.phosphorite","");
        add("rankine.journal.cat_mineralogy.mineral_stones.sylvinite","");
        add("rankine.journal.cat_mineralogy.mineral_stones.evaporite","");


        add("rankine.journal.cat_mineralogy.fumaroles.name", "Fumaroles");
        add("rankine.journal.cat_mineralogy.fumaroles.text1","Fumaroles are natural vents for gasses trapped inside the crust. They continuously output gas blocks into nearby air space.");
        add("rankine.journal.cat_mineralogy.fumaroles.text2","Generate low in the Overworld and Nether.");
        add("rankine.journal.cat_mineralogy.fumaroles.text3","A mineral rich stone derived from the outgassing of deep-world materials. Important source of Vanadium Pentoxide and minerals for Beryllium production.");
        add("rankine.journal.cat_mineralogy.fumaroles.text4","A crystallized form of ammonia compounds. Used for ammonia production and complex metal reactions. Crystals form on the underside of fumarole deposit blocks that have water above them.");

        add("rankine.journal.cat_mineralogy.gem_ores.name", "Gem Ores");
        add("rankine.journal.cat_mineralogy.gem_ores.text1", "Gem ores refer to stones that directly drop their respective item when mined. Most can also be crushed for additional resources.");

        add("rankine.journal.cat_mineralogy.mineral_ores.name", "Mineral Ores");
        add("rankine.journal.cat_mineralogy.mineral_ores.text1", "Mineral ores are rocks containing more complex compounds that may not be directly usable. They are often of higher harvest level, drop their raw mineral when mined and additional resources when crushed.");


        add("rankine.journal.cat_mineralogy.native_ores.name", "Native Ores");
        add("rankine.journal.cat_mineralogy.native_ores.text1", "Native is a term used to describe elements that exist in their pure form in nature. The following blocks can be mined at a low harvest level to obtain pure metal samples.");
        add("rankine.journal.cat_mineralogy.native_ores.native_tin_ore", "Source of Tin. $(br2)Found near the surface in all regions of the world.");
        add("rankine.journal.cat_mineralogy.native_ores.native_lead_ore", "Source of Lead. $(br2)Found near the surface in all regions of the world.");
        add("rankine.journal.cat_mineralogy.native_ores.native_bismuth_ore", "Source of Bismuth. $(br2)Found near the surface in all regions of the world.");
        add("rankine.journal.cat_mineralogy.native_ores.native_silver_ore", "Source of Silver. $(br2)Found near the surface in all regions of the world.");
        add("rankine.journal.cat_mineralogy.native_ores.native_gold_ore", "Source of Gold. $(br2)Found scattered across the world.");
        add("rankine.journal.cat_mineralogy.native_ores.stibnite_ore", "Source of Antimony. $(br2)Found near the surface in all regions of the world.");
        add("rankine.journal.cat_mineralogy.native_ores.porphyry_copper", "Source of Copper. $(br2)Found in porphyry intrusions.");
        add("rankine.journal.cat_mineralogy.native_ores.native_sulfur_ore", "Source of Sulfur. $(br2)Found scattered across the Nether.");;
        add("rankine.journal.cat_mineralogy.native_ores.native_arsenic_ore", "Source of Arsenic. $(br2)Found scattered across the Nether.");
        add("rankine.journal.cat_mineralogy.native_ores.native_indium_ore", "Source of Indium. $(br2)Found scattered across the End.");
        add("rankine.journal.cat_mineralogy.native_ores.native_gallium_ore", "Source of Gallium. $(br2)Found scattered across the End.");
        add("rankine.journal.cat_mineralogy.native_ores.native_tellurium_ore", "Source of Tellurium. $(br2)Found scattered across the End.");
        add("rankine.journal.cat_mineralogy.native_ores.native_selenium_ore", "Source of Selenium. $(br2)Found scattered across the End.");



        //Stones
        add("rankine.journal.cat_stones.name", "Stones");
        add("rankine.journal.cat_stones.desc", "");

        add("rankine.journal.cat_stones.otherworldly.name", "Otherworldly Stones");
        add("rankine.journal.cat_stones.otherworldly.text1", "Some stones can not form under standard environmental conditions. This class of stone is simply named Otherworldly, as they naturally occur in atypical locations.");
        add("rankine.journal.cat_stones.otherworldly.text2", "Otherworldly Generator");
        add("rankine.journal.cat_stones.otherworldly.text3", "Otherworldly Generator");
        add("rankine.journal.cat_stones.otherworldly.end_stone", "");
        add("rankine.journal.cat_stones.otherworldly.sommanite", "");
        add("rankine.journal.cat_stones.otherworldly.wadsleyone", "");
        add("rankine.journal.cat_stones.otherworldly.bridgmanham", "");
        add("rankine.journal.cat_stones.otherworldly.ringwoodine", "");
        add("rankine.journal.cat_stones.otherworldly.post_perovskite", "");
        add("rankine.journal.cat_stones.otherworldly.whiteschist", "");

        add("rankine.journal.cat_stones.volcanic.name", "Volcanic Stones");
        add("rankine.journal.cat_stones.volcanic.text1", "Some stones can not form under standard environmental conditions. This class of stone is simply named Otherworldly, as they naturally occur in atypical locations.");
        add("rankine.journal.cat_stones.volcanic.text3", "Volcanic stones are generated using the vanilla obsidian generator, where water flows onto lava source blocks.");
        add("rankine.journal.cat_stones.volcanic.text2", "Volcanic Generator");
        add("rankine.journal.cat_stones.volcanic.obsidian", "");
        add("rankine.journal.cat_stones.volcanic.crying_obsidian", "");
        add("rankine.journal.cat_stones.volcanic.snowflake_obsidian", "");
        add("rankine.journal.cat_stones.volcanic.blood_obsidian", "");
        add("rankine.journal.cat_stones.volcanic.pumice", "");
        add("rankine.journal.cat_stones.volcanic.scoria", "");
        add("rankine.journal.cat_stones.volcanic.andesitic_tuff", "");
        add("rankine.journal.cat_stones.volcanic.basaltic_tuff", "");
        add("rankine.journal.cat_stones.volcanic.rhyolitic_tuff", "");
        add("rankine.journal.cat_stones.volcanic.kimberlitic_tuff", "");
        add("rankine.journal.cat_stones.volcanic.komatiitic_tuff", "");

        add("rankine.journal.cat_stones.sedimentary.name", "Sedimentary Stones");
        add("rankine.journal.cat_stones.sedimentary.text1", "");
        add("rankine.journal.cat_stones.sedimentary.text2", "Sedimentary Generator");
        add("rankine.journal.cat_stones.sedimentary.sandstone", "");
        add("rankine.journal.cat_stones.sedimentary.red_sandstone", "");
        add("rankine.journal.cat_stones.sedimentary.soul_sandstone", "");
        add("rankine.journal.cat_stones.sedimentary.desert_sandstone", "");
        add("rankine.journal.cat_stones.sedimentary.white_sandstone", "");
        add("rankine.journal.cat_stones.sedimentary.black_sandstone", "");
        add("rankine.journal.cat_stones.sedimentary.limestone", "");
        add("rankine.journal.cat_stones.sedimentary.dolostone", "");
        add("rankine.journal.cat_stones.sedimentary.chalk", "");
        add("rankine.journal.cat_stones.sedimentary.marlstone", "");
        add("rankine.journal.cat_stones.sedimentary.shale", "");
        add("rankine.journal.cat_stones.sedimentary.mudstone", "");
        add("rankine.journal.cat_stones.sedimentary.siltstone", "");
        add("rankine.journal.cat_stones.sedimentary.itacolumite", "");
        add("rankine.journal.cat_stones.sedimentary.arkose", "");
        add("rankine.journal.cat_stones.sedimentary.graywacke", "");
        add("rankine.journal.cat_stones.sedimentary.honeystone", "");



        add("rankine.journal.cat_stones.metamorphic.name", "Metamorphic Stones");
        add("rankine.journal.cat_stones.metamorphic.text1", "");
        add("rankine.journal.cat_stones.metamorphic.text2", "Metamorphic Generator");
        add("rankine.journal.cat_stones.metamorphic.text3", "");
        add("rankine.journal.cat_stones.metamorphic.black_marble", "");
        add("rankine.journal.cat_stones.metamorphic.gray_marble", "");
        add("rankine.journal.cat_stones.metamorphic.white_marble", "");
        add("rankine.journal.cat_stones.metamorphic.rose_marble", "");
        add("rankine.journal.cat_stones.metamorphic.slate", "");
        add("rankine.journal.cat_stones.metamorphic.phyllite", "");
        add("rankine.journal.cat_stones.metamorphic.mica_schist", "");
        add("rankine.journal.cat_stones.metamorphic.blueschist", "");
        add("rankine.journal.cat_stones.metamorphic.greenschist", "");
        add("rankine.journal.cat_stones.metamorphic.gneiss", "");
        add("rankine.journal.cat_stones.metamorphic.quartzite", "");
        add("rankine.journal.cat_stones.metamorphic.soapstone", "");
        add("rankine.journal.cat_stones.metamorphic.serpentinite", "");
        add("rankine.journal.cat_stones.metamorphic.mariposite", "");
        add("rankine.journal.cat_stones.metamorphic.eclogite", "");




        add("rankine.journal.cat_stones.igneous.name", "Igneous Stones");
        add("rankine.journal.cat_stones.igneous.text1", "");
        add("rankine.journal.cat_stones.igneous.text2", "Intrusive Stones");
        add("rankine.journal.cat_stones.igneous.text3", "");
        add("rankine.journal.cat_stones.igneous.text4", "Extrusive Stones");
        add("rankine.journal.cat_stones.igneous.text5", "");
        add("rankine.journal.cat_stones.igneous.stone", "Stone in this world is generally found solely as an intrusion. Depending on the biome, it will contain a variety of the vanilla ores.");
        add("rankine.journal.cat_stones.igneous.granite", "An intrusive stone found in plains and forest biomes. Associated ores include Malachite, and Cassiterite.");
        add("rankine.journal.cat_stones.igneous.gray_granite", "An intrusive stone found in mountain, swamp, taiga, and icy biomes. Associated ores include Malachite, and Cassiterite.");
        add("rankine.journal.cat_stones.igneous.diorite", "An intrusive stone found in mountain, icy, and taiga biomes. Associated ores include Plumbago, Magnetite, and Ilmenite.");
        add("rankine.journal.cat_stones.igneous.granodiorite", "An intrusive stone found in savanna, forest and plains biomes. Associated ores include Magnetite, and Wolframite.");
        add("rankine.journal.cat_stones.igneous.pegmatite", "An intrusive stone found in many parts of the Overworld. Associated ores include Beryl, Petalite, Baddeleyite, Coltan, and Uraninite.");
        add("rankine.journal.cat_stones.igneous.norite", "An intrusive stone found in savanna and jungle biomes. Associated ores include Magnetite and Chromite.");
        add("rankine.journal.cat_stones.igneous.diabase", "An intrusive stone found in ocean and beach biomes. Associated ores include Baddeleyite.");
        add("rankine.journal.cat_stones.igneous.red_porphyry", "An intrusive stone found in desert, mesa, and ocean biomes. Associated ores include Porphyry Copper, Gold, and Molybdenite.");
        add("rankine.journal.cat_stones.igneous.kimberite", "An intrusive stone found in many parts of the Overworld. Associated ores include Kimberlitic Diamond.");
        add("rankine.journal.cat_stones.igneous.shonkinite", "An intrusive stone found in mountain and swamp biomes. Associated ores include Beryl, Plumbago, and Magnetite.");




        //Sediments
        add("rankine.journal.cat_sediments.name", "Sediments");
        add("rankine.journal.cat_sediments.desc", "Dirt is too simple of a term to describe the complex matrix of organic material and pulverized rocks. A better term is soil, but there are more small particles than just that.");

        add("rankine.journal.cat_sediments.cobblestones.name", "Cobblestones");
        add("rankine.journal.cat_sediments.cobblestones.text1", "Cobbles are very large particles that can come from any parent stone when $(l:mechanics/crushing)crushed$().");
        add("rankine.journal.cat_sediments.cobblestones.cobblestone",  "Derived from $(l:stones/igneous)Igneous Stones$().");
        add("rankine.journal.cat_sediments.cobblestones.blackstone",  "Derived from $(l:stones/igneous)Igneous Stones$(), specifically mafic ones.");
        add("rankine.journal.cat_sediments.cobblestones.skarn",  "Derived from $(l:stones/metamorphic)Metamorphic Stones$().");
        add("rankine.journal.cat_sediments.cobblestones.breccia", "Derived from $(l:stones/sedimentary)Sedimentary Stones$().");

        add("rankine.journal.cat_sediments.gravels.name", "Gravels");
        add("rankine.journal.cat_sediments.gravels.text1", "");
        add("rankine.journal.cat_sediments.gravels.gravel",  "");
        add("rankine.journal.cat_sediments.gravels.light_gravel",  "");
        add("rankine.journal.cat_sediments.gravels.dark_gravel",  "");

        add("rankine.journal.cat_sediments.sands.name", "Sands");
        add("rankine.journal.cat_sediments.sands.text1", "");
        add("rankine.journal.cat_sediments.sands.sand",  "");
        add("rankine.journal.cat_sediments.sands.red_sand",  "");
        add("rankine.journal.cat_sediments.sands.soul_sand",  "");
        add("rankine.journal.cat_sediments.sands.white_sand",  "");
        add("rankine.journal.cat_sediments.sands.black_sand",  "");
        add("rankine.journal.cat_sediments.sands.desert_sand",  "");

        add("rankine.journal.cat_sediments.silts.name", "Silt");
        add("rankine.journal.cat_sediments.silts.text1", "");
        add("rankine.journal.cat_sediments.silts.silt",  "A smaller particle size than sand. It is used in many similar applications. Found along rivers.");

        add("rankine.journal.cat_sediments.clays.name", "Clays");
        add("rankine.journal.cat_sediments.clays.text1", "");
        add("rankine.journal.cat_sediments.clays.clay",  "");
        add("rankine.journal.cat_sediments.clays.fire_clay",  "");
        add("rankine.journal.cat_sediments.clays.kaolin",  "");

        add("rankine.journal.cat_sediments.soils.name", "Soils");
        add("rankine.journal.cat_sediments.soils.text1", "");
        add("rankine.journal.cat_sediments.soils.humus",  "");
        add("rankine.journal.cat_sediments.soils.loam",  "");
        add("rankine.journal.cat_sediments.soils.sandy_loam",  "");
        add("rankine.journal.cat_sediments.soils.silty_loam",  "");
        add("rankine.journal.cat_sediments.soils.sandy_clay_loam",  "");
        add("rankine.journal.cat_sediments.soils.silty_clay_loam",  "");
        add("rankine.journal.cat_sediments.soils.loamy_sand",  "");
        add("rankine.journal.cat_sediments.soils.sandy_clay",  "");
        add("rankine.journal.cat_sediments.soils.silty_clay",  "");
        add("rankine.journal.cat_sediments.soils.laterite",  "");
        add("rankine.journal.cat_sediments.soils.permafrost",  "");


        //Tools
        add("rankine.journal.cat_tools.name", "Tools");
        add("rankine.journal.cat_tools.desc", "Various implements use to manipulate the world.");


        add("rankine.journal.cat_tools.totems.name", "Totems");
        add("rankine.journal.cat_tools.totems.text1", "Totems are relics derived from the natural magics of the world. While in the offhand, they provide various effects.");
        add("rankine.journal.cat_tools.totems.totem_of_blazing", "");
        add("rankine.journal.cat_tools.totems.totem_of_cobbling", "Internally stores all types of stones up to 8 stacks. Can be used to place cobblestone.");
        add("rankine.journal.cat_tools.totems.totem_of_enduring", "Increases the wearer's health capacity. Can be used every so often to apply regeneration.");
        add("rankine.journal.cat_tools.totems.totem_of_hastening", "Applies efficiency to your arms, allowing you to mine all blocks quicker.");
        add("rankine.journal.cat_tools.totems.totem_of_infusing", "");
        add("rankine.journal.cat_tools.totems.totem_of_levitating", "While not sneaking, the wearer will not be able to fall.");
        add("rankine.journal.cat_tools.totems.totem_of_promising", "Mining brings you fortune for many materials. There is a chance to receive an extra block.");
        add("rankine.journal.cat_tools.totems.totem_of_repulsing", "Hostile mobs can no longer detect the wearer.");
        add("rankine.journal.cat_tools.totems.totem_of_softening", "The delicacy of an open hand allows you to collect blocks that naturally require silk touch.");
        add("rankine.journal.cat_tools.totems.totem_of_timesaving", "Provides additional movement speed across all solid terrain.");

        add("rankine.journal.cat_tools.ore_detection.name", "Ore Detection");
        add("rankine.journal.cat_tools.ore_detection.text1", "");
        add("rankine.journal.cat_tools.ore_detection.text2", "Determines the harvest level of a block. $(br2)Use by right clicking a block to see its mining level.Testable material only include those affected by a pickaxe/hammer.");
        add("rankine.journal.cat_tools.ore_detection.text3", "Detects ores in a straight line behind the face that was clicked. Displays the harvest level of the ore found.");
        add("rankine.journal.cat_tools.ore_detection.text4", "");
        add("rankine.journal.cat_tools.ore_detection.text5", "A device capable of sensing ores, even non-metallic ones. $(br)Use by right clicking a block. The device searches in a straight line behind the face that was clicked (range adjustable in config). Displays which ore is found and dings.");


        add("rankine.journal.cat_tools.equipment.name", "Equipment");
        add("rankine.journal.cat_tools.equipment.text1", "");
        add("rankine.journal.cat_tools.equipment.text2", "Sandals are worn on the feet and provide increased movemet speed on sand. Combine boots of any type with sandals in an anvil to apply the $(l:mechanics/enchantments#dune_walker)Dune Walker$() enchantment.");
        add("rankine.journal.cat_tools.equipment.text3", "Snowshoes are worn on the feet and provide increased movemet speed on snow. Combine boots of any type with snowshoes in an anvil to apply the $(l:mechanics/enchantments#snow_drifter)Snow Drifter$() enchantment.");
        add("rankine.journal.cat_tools.equipment.text4", "Ice Skates are worn on the feet and provide increased movemet speed on ice. Combine boots of any type with ice skates in an anvil to apply the $(l:mechanics/enchantments#speed_skater)Speed Skater$() enchantment.");
        add("rankine.journal.cat_tools.equipment.text5", "Fins are worn on the feet and provide increased movemet speed in water. Combine boots of any type with fins in an anvil to apply the $(l:mechanics/enchantments#flippers)Flippers$() enchantment.");
        add("rankine.journal.cat_tools.equipment.text6", "Goggles are worn on the head and provide better vision and movemet speed in water. Combine a helmet of any type with goggles in an anvil to apply the TBD");
        add("rankine.journal.cat_tools.equipment.text7", "A Gas Mask are worn on the head and protects against suffocation in gas blocks. Combine helmet of any type with a gas mask in an anvil to apply the $(l:mechanics/enchantments#gas_protection)Gas Protection$() enchantment.");

        add("rankine.journal.cat_tools.utility_tools.name", "Utility Tools");

        add("rankine.journal.cat_tools.standard_tools.name", "Standard Tools");
        add("rankine.journal.cat_tools.standard_tools.text1", "");
        add("rankine.journal.cat_tools.standard_tools.text2", "Swords");
        add("rankine.journal.cat_tools.standard_tools.text3", "");
        add("rankine.journal.cat_tools.standard_tools.text4", "Shovels");
        add("rankine.journal.cat_tools.standard_tools.text5", "");
        add("rankine.journal.cat_tools.standard_tools.text6", "Pixaxes");
        add("rankine.journal.cat_tools.standard_tools.text7", "");
        add("rankine.journal.cat_tools.standard_tools.text8", "Axes");
        add("rankine.journal.cat_tools.standard_tools.text9", "");
        add("rankine.journal.cat_tools.standard_tools.text10", "Hoes");
        add("rankine.journal.cat_tools.standard_tools.text11", "");
        add("rankine.journal.cat_tools.standard_tools.text12", "Hammers");
        add("rankine.journal.cat_tools.standard_tools.text13", "");
        add("rankine.journal.cat_tools.standard_tools.text14", "Knives");
        add("rankine.journal.cat_tools.standard_tools.text15", "");
        add("rankine.journal.cat_tools.standard_tools.text16", "Crowbars");
        add("rankine.journal.cat_tools.standard_tools.text17", "");
        add("rankine.journal.cat_tools.standard_tools.text18", "Surf Rods");
        add("rankine.journal.cat_tools.standard_tools.text19", "");



    }

    private String parseLangName(String registryName) {
        String LangName = "";
        for (String s : registryName.split("_")) {
            if (LangName.equals("")) {
                LangName = s.substring(0,1).toUpperCase() + s.substring(1);
            } else {
                LangName = LangName + " " + s.substring(0,1).toUpperCase() + s.substring(1);
            }
        }
        return LangName;
    }
/*
    private String parseLangNameCustom(String registryName) {
        StringBuilder LangName = new StringBuilder();
        for (String s : registryName.split("_")) {
            if (LangName.toString().equals("")) {
                String base = s.substring(0, 1).toUpperCase() + s.substring(1);
                if (base.equals("Alloy")) {
                    LangName = new StringBuilder("%1$s");
                } else {
                    LangName = new StringBuilder(base);
                }
            } else {
                LangName.append(" ").append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
            }
        }
        return LangName.toString();
    }*/

    private String parseLangNameCustom(String registryName) {
        String[] list = registryName.split("_");
        if (registryName.contains("surf_rod")) {
            return "%1$s " + "Surf Rod";
        } else {
            return "%1$s " + list[list.length-1].substring(0, 1).toUpperCase() + list[list.length-1].substring(1);
        }

    }

    private String parseLangNameCustomBlock(String registryName) {
        return "Block of %1$s";
    }


}
