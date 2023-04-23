package com.cannolicatfish.rankine.data.lang;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.block_groups.*;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SignBlock;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RankineEnUsLangProvider extends LanguageProvider {
    private final String locale;
    public RankineEnUsLangProvider(PackOutput packOutput, String locale) {
        super(packOutput, ProjectRankine.MODID, locale);
        this.locale = locale;
    }

    @Override
    public String getName() {
        return "Rankine Lang: " + locale;
    }

    @Override
    protected void addTranslations() {


        for (RankineStone Stone : RankineLists.RANKINE_STONES) {
            for (Block blk : Stone.getStoneBlocks()) {
                add(blk, parseLangName(blk.getDescriptionId()));
            }
        }
        for (RankineWood Wood : RankineLists.RANKINE_WOODS) {
            for (Block blk : Wood.getWoodBlocks()) {
                if (blk != null && !(blk instanceof SignBlock)) {
                    add(blk, parseLangName(blk.getDescriptionId()));
                }
            }
            add(Wood.getSignItem(), parseLangName(Wood.getSignItem().getDescriptionId()));
            add(Wood.getBoat(), parseLangName(Wood.getBoat().getDescriptionId()));
        }
        for (RankineSandstone Sandstone : RankineLists.RANKINE_SANDSTONES) {
            for (Block blk : Sandstone.getSandstoneBlocks()) {
                add(blk, parseLangName(blk.getDescriptionId()));
            }
        }
        for (RankineCement block : RankineLists.RANKINE_CEMENTS) {
            for (Block blk : block.getCementBlocks()) {
                add(blk, parseLangName(blk.getDescriptionId()));
            }
        }
        for (RankineBricks Bricks : RankineLists.RANKINE_BRICKS) {
            for (Block blk : Bricks.getBricksBlocks()) {
                add(blk, parseLangName(blk.getDescriptionId()));
            }
        }
        for (RankineDripstone Dripstone : RankineLists.RANKINE_DRIPSTONES) {
            for (Block blk : Dripstone.getBlocks()) {
                add(blk, parseLangName(blk.getDescriptionId()));
            }
        }

        for (Block blk : Stream.of(
                RankineLists.MISC_SLABS,
                RankineLists.MISC_STAIRS,
                RankineLists.MISC_WALLS,
                RankineLists.BALES,
                RankineLists.MUSHROOM_BLOCKS,
                RankineLists.HOLLOW_LOGS,
                RankineLists.LEAF_LITTERS,
                RankineLists.VANILLA_BRICKS,
                RankineLists.VANILLA_BRICKS_SLABS,
                RankineLists.VANILLA_BRICKS_WALLS,
                RankineLists.VANILLA_BRICKS_STAIRS,
                RankineLists.VANILLA_BRICKS_PRESSURE_PLATES,
                RankineLists.SHEETMETALS,
                RankineLists.GEODES,
                RankineLists.GLAZED_PORCELAIN_BLOCKS,
                RankineLists.MINERAL_WOOL,
                RankineLists.FIBER_BLOCK,
                RankineLists.FIBER_MAT,
                RankineLists.TALL_FLOWERS,
                RankineLists.METAL_TRAPDOORS,
                RankineLists.METAL_DOORS,
                RankineLists.ALLOY_LADDERS,
                RankineLists.ALLOY_PEDESTALS,
                RankineLists.ALLOY_POLES,
                RankineLists.ALLOY_SHEETMETALS,
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
                RankineLists.CRUSHING_HEADS,
                RankineLists.MINING_HEADS,
                RankineLists.LANTERNS,
                RankineLists.ASPHALT_BLOCKS,
                RankineLists.RED_ASPHALT_BLOCKS,
                RankineLists.GRAY_ASPHALT_BLOCKS,
                RankineLists.BLUE_ASPHALT_BLOCKS,
                RankineLists.DARK_GRAY_ASPHALT_BLOCKS,
                RankineLists.GREEN_ASPHALT_BLOCKS,
                RankineLists.NATIVE_ORES,
                RankineLists.CRUSHING_ORES,
                RankineLists.SPECIAL_ORES).flatMap(Collection::stream).collect(Collectors.toList())) {
            if (blk == RankineBlocks.SIMPLE_ELECTROMAGNET.get()) {
                add(blk, "Electromagnet (Tier 1)");
            } else if (blk == RankineBlocks.ALNICO_ELECTROMAGNET.get()) {
                add(blk, "Electromagnet (Tier 2)");
            } else if (blk == RankineBlocks.RARE_EARTH_ELECTROMAGNET.get()) {
                add(blk, "Electromagnet (Tier 3)");
            } else {
                add(blk, parseLangName(blk.getDescriptionId()));
            }
        }

        for (Block blk : Stream.of(
            RankineLists.ELEMENT_BLOCKS,
            RankineLists.MINERAL_BLOCKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            if (blk.equals(RankineBlocks.SODIUM_CHLORIDE_BLOCK.get())) {
                add(blk, "Block of Salt (NaCl)");
            } else if (blk.equals(RankineBlocks.CALCIUM_CHLORIDE_BLOCK.get())) {
                add(blk, "Block of Salt (CaCl2)");
            } else {
                add(blk, parseLangName("block_of_"+blk.getDescriptionId().replace("_block","")));
            }
        }
        add(RankineBlocks.KAOLINITE_BLOCK.get(),"Block of Kaolinite");
        add(RankineBlocks.HALLOYSITE_BLOCK.get(),"Block of Halloysite");

        for (Block blk : RankineLists.ALLOY_BLOCKS) {
            if (blk != RankineBlocks.ALLOY_BLOCK.get()) {
                add(blk,parseLangNameCustomBlock(blk.getDescriptionId()));
            }
        }

        // Misc Blocks
        for (Block blk : Arrays.asList(
                RankineBlocks.FLOOD_GATE.get(),
                RankineBlocks.SLATE_STEPPING_STONES.get(),
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
                RankineBlocks.BATTERY_CHARGER.get(),
                RankineBlocks.FUSION_FURNACE.get(),
                RankineBlocks.MIXING_BARREL.get(),
                RankineBlocks.CRUCIBLE_BLOCK.get(),
                RankineBlocks.LATEX_CAULDRON.get(),
                RankineBlocks.RESIN_CAULDRON.get(),
                RankineBlocks.JUGLONE_CAULDRON.get(),
                RankineBlocks.SAP_CAULDRON.get(),
                RankineBlocks.MAPLE_SAP_CAULDRON.get(),
                RankineBlocks.MAPLE_SYRUP_CAULDRON.get(),
                RankineBlocks.BEEHIVE_OVEN_PIT.get(),
                RankineBlocks.MATERIAL_TESTING_TABLE.get(),
                RankineBlocks.TEMPLATE_TABLE.get(),
                RankineBlocks.BOTANIST_STATION.get(),
                RankineBlocks.DIAMOND_ANVIL_CELL.get(),
                RankineBlocks.PARTICLE_ACCELERATOR.get(),
                RankineBlocks.EVAPORATION_TOWER.get(),
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

                RankineBlocks.ALLUVIUM.get(),
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
                RankineBlocks.PORCELAIN_CLAY.get(),
                RankineBlocks.KAOLIN.get(),
                RankineBlocks.STUMP.get(),
                RankineBlocks.KIMBERLITIC_DIAMOND_ORE.get(),
                RankineBlocks.PORPHYRY_COPPER.get()
                )) {
            add(blk, parseLangName(blk.getDescriptionId()));
        }

        for (Item item : Stream.of(
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
                add(item, parseLangName(item.getDescriptionId()));
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
                add(item, parseLangNameCustom(item.getDescriptionId()));
            }
        }
        for (Item item : Arrays.asList(RankineItems.ALLOY_NUGGET.get(),RankineItems.ALLOY_INGOT.get())) {
            add(item, parseLangNameCustom(item.getDescriptionId()));
        }
        add(RankineItems.ALLOY_BLOCK.get(), parseLangNameCustomBlock(RankineItems.ALLOY_BLOCK.get().getDescriptionId()));

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
            RankineItems.COOKED_TOFU.get(),
            RankineItems.TOFU_CURRY.get(),
            RankineItems.SMOULDERING_TINDER_CONK.get(),
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
            RankineItems.INNER_BARK.get(),
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
            RankineItems.PORCELAIN_CLAY_BALL.get(),
            RankineItems.KAOLINITE.get(),
            RankineItems.HALLOYSITE.get(),
            RankineItems.BONE_CHAR.get(),
            RankineItems.COPPER_NUGGET.get(),
            RankineItems.NETHERITE_NUGGET.get(),
            RankineItems.SLAG.get(),
            RankineItems.TRONA.get(),
            RankineItems.POTASH.get(),
            RankineItems.BORON_TRIOXIDE.get(),
            RankineItems.TUNGSTEN_TRIOXIDE.get(),
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
            RankineItems.TUNGSTEN_CARBIDE.get(),
            RankineItems.SODIUM_CARBONATE.get(),
            RankineItems.SALTPETER.get(),
            RankineItems.CEMENT_MIX.get(),
            RankineItems.MORTAR.get(),
            RankineItems.METEORIC_IRON.get(),
            RankineItems.OSMIRIDIUM.get(),
            RankineItems.COMPOST.get(),
            RankineItems.GARLAND.get(),
            RankineItems.FOUR_LEAF_CLOVER.get(),
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
            RankineItems.CALCITE.get(),
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
            RankineItems.ENDERBALL.get(),
            RankineItems.PENNING_TRAP.get(),
            RankineItems.FILLED_PENNING_TRAP.get(),
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
            RankineItems.GAS_DETECTOR.get(),
            RankineItems.ORE_DETECTOR.get(),
            RankineItems.PROSPECTING_STICK.get(),
            RankineItems.DOWSING_ROD.get(),
            RankineItems.PACKAGED_TOOL.get(),
            RankineItems.PACKAGED_ARMOR.get(),
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
            RankineItems.TOTEM_OF_POWERING.get(),
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
            RankineItems.LITHIUM_ION_BATTERY.get())) {
            add(item, parseLangName(item.getDescriptionId()));
        }

        add(RankineItems.AMERICIUM_RTG.get(), "Americium RTG");
        add(RankineItems.POLONIUM_RTG.get(), "Polonium RTG");
        add(RankineItems.CURIUM_RTG.get(), "Curium RTG");
        add(RankineItems.PLUTONIUM_RTG.get(), "Plutonium RTG");
        add(RankineItems.STRONTIUM_RTG.get(), "Strontium RTG");
        add(RankineBlocks.HEATING_ELEMENT_1.get(), "Low Grade Heating Element");
        add(RankineBlocks.WHITE_LED.get(), "White LED");
        add(RankineBlocks.GRAY_LED.get(), "Gray LED");
        add(RankineBlocks.LIGHT_GRAY_LED.get(), "Light Gray LED");
        add(RankineBlocks.BLACK_LED.get(), "Black LED");
        add(RankineBlocks.RED_LED.get(), "Red LED");
        add(RankineBlocks.ORANGE_LED.get(), "Orange LED");
        add(RankineBlocks.YELLOW_LED.get(), "Yellow LED");
        add(RankineBlocks.LIME_LED.get(), "Lime LED");
        add(RankineBlocks.GREEN_LED.get(), "Green LED");
        add(RankineBlocks.CYAN_LED.get(), "Cyan LED");
        add(RankineBlocks.BLUE_LED.get(), "Blue LED");
        add(RankineBlocks.LIGHT_BLUE_LED.get(), "Light Blue LED");
        add(RankineBlocks.MAGENTA_LED.get(), "Magenta LED");
        add(RankineBlocks.PURPLE_LED.get(), "Purple LED");
        add(RankineBlocks.PINK_LED.get(), "Pink LED");
        add(RankineBlocks.BROWN_LED.get(), "Brown LED");


        add("item.rankine.packaged_tool_desc","Contains a completely random tool. Results may vary.");
        add("item.rankine.packaged_armor_desc","Contains a completely random armor item. Results may vary.");
        add("rankine.battery.charge","Charge: %1$s/%2$s");
        //Alloy Lang
        add("rankine.alloys","Alloys");
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
        add("item.rankine.damascus_steel_alloying", "Damascus Steel");
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
        add("item.rankine.neptunium_alloy_alloying", "Neptunium Alloy");
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
        add("item.rankine.prospecting_stick_cobbles.message", "Trace amounts of %1$s were detected");
        add("item.rankine.prospecting_stick.message", "%1$s (HL:%2$s) was detected nearby");
        add("item.rankine.ore_detector.message", "%1$s (HL:%2$s) was detected at x:%3$s y:%4$s z:%5$s");
        add("item.rankine.gas_detector.message", "%1$s was detected at x:%2$s y:%3$s z:%4$s");
        add("item.rankine.magnetometer.message1", "Field Strength: %s");
        add("item.rankine.dowsing_rod.message", "Water table height is y=%s");


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
        add("item.rankine.demonyte_spawn_egg", "Modonomiconnxyte Spawn Egg");
        add("entity.rankine.demonyte", "Modonomiconnxyte");
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

        add("effect.rankine.radiation_poisoning", "Radiation Poisoning");
        add("effect.rankine.mercury_poisoning", "Mercury Poisoning");
        add("item.minecraft.potion.effect.mercury_poisoning_potion", "Potion of Mercury Poisoning");
        add("item.minecraft.splash_potion.effect.mercury_poisoning_potion", "Splash Potion of Mercury Poisoning");
        add("item.minecraft.lingering_potion.effect.mercury_poisoning_potion", "Lingering Potion of Mercury Poisoning");
        add("item.minecraft.tipped_arrow.effect.mercury_poisoning_potion", "Arrow of Mercury Poisoning");
        add("effect.rankine.conductivity", "Conductive");
        add("item.minecraft.potion.effect.conductivity_potion", "Potion of Conductivity");
        add("item.minecraft.splash_potion.effect.conductivity_potion", "Splash Potion of Conductivity");
        add("item.minecraft.lingering_potion.effect.conductivity_potion", "Lingering Potion of Conductivity");
        add("item.minecraft.tipped_arrow.effect.conductivity_potion", "Arrow of Conductivity");

        add("enchantment.rankine.puncture", "Puncture");
        add("enchantment.rankine.puncture.desc", "Deals extra armor-piercing damage on direct attacks.");
        add("enchantment.rankine.blast", "Blast");
        add("enchantment.rankine.blast.desc", "Causes the hammer to explode blocks in a radius.");
        add("enchantment.rankine.atomize", "Atomize");
        add("enchantment.rankine.atomize.desc", "All additional outputs are turned into experience.");
        add("enchantment.rankine.lightning_aspect", "Lightning Aspect");
        add("enchantment.rankine.lightning_aspect.desc", "Causes lightning to strike when attacking targets.");
        add("enchantment.rankine.swing", "Swing");
        add("enchantment.rankine.swing.desc", "Decreases the cooldown of effects caused by swinging the hammer or crowbar by 15% (additive).");
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
        add("enchantment.rankine.swift_swimmer", "Swift Swimmer");
        add("enchantment.rankine.swift_swimmer.desc", "Increases swim speed.");
        add("enchantment.rankine.aqua_lense", "Aqua Lense");
        add("enchantment.rankine.aqua_lense.desc", "Improves visibility under water.");
        add("enchantment.rankine.guard", "Guard");
        add("enchantment.rankine.guard.desc", "Generates passive protection hearts over time.");
        add("enchantment.rankine.gas_protection", "Gas Protection");
        add("enchantment.rankine.gas_protection.desc", "Protects from harmful effects of gases.");
        add("enchantment.rankine.quake", "Quake");
        add("enchantment.rankine.quake.desc", "Increases mining speed based on depth.");
        add("enchantment.rankine.foraging", "Foraging");
        add("enchantment.rankine.foraging.desc", "Adds a chance to obtain new items from various blocks.");
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
        add("enchantment.rankine.endospore", "Endospore");
        add("enchantment.rankine.endospore.desc", "On right-click harvesting a crop, has a chance of making a mature crop on a nearby empty farmland block. Chance influenced by luck.");
        add("enchantment.rankine.endotoxin", "Endotoxin");
        add("enchantment.rankine.endotoxin.desc", "Deals extra damage to mobs in the End and that come from the End.");
        add("enchantment.rankine.endure", "Endure");
        add("enchantment.rankine.endure.desc", "Nearby End Crystals heal the user while holding a sword with this enchantment.");
        add("enchantment.rankine.endgame", "Endgame");
        add("enchantment.rankine.endgame.desc", "On parry, teleport away and attack all enemies in an area around where you teleported away from.");
        add("enchantment.rankine.endolithic", "Endolithic");
        add("enchantment.rankine.endolithic.desc", "On right-click of a stone block, teleports you to the nearest empty space in the direction of the block you are facing. Will not teleport if there is no solid ground.");
        add("enchantment.rankine.endless", "Endless");
        add("enchantment.rankine.endless.desc", "Mining ores gives you a random beneficial potion effect or extends a currently applied effect.");
        add("enchantment.rankine.endeavor", "Endeavor");
        add("enchantment.rankine.endeavor.desc", "Further increases the damage dealt to elemental enemies. Has a chance to cause them to drop items on hit.");
        add("enchantment.rankine.endothermic", "Endothermic");
        add("enchantment.rankine.endothermic.desc", "Harvested logs turn directly into charcoal.");
        add("enchantment.rankine.endplay", "Endplay");
        add("enchantment.rankine.endplay.desc", "Regular cannonballs are converted into Enderballs, which has a higher projectile speed and can spawn endermites that attack nearby mobs.");
        add("enchantment.rankine.withering_curse", "Curse of Withering");
        add("enchantment.rankine.withering_curse.desc", "Taking damage (that isn't wither or magic related) causes you to receive the Wither effect for a short duration.");
        add("enchantment.rankine.ghastly_regeneration", "Ghastly Regeneration");
        add("enchantment.rankine.ghastly_regeneration.desc", "Regeneration potion effect restore durability.");
        add("enchantment.rankine.prying", "Prying");
        add("enchantment.rankine.prying.desc", "On hit has a chance to cause the target to drop their held item. Chance is determined by rarity of the item.");
        add("enchantment.rankine.retrieval", "Retrieval");
        add("enchantment.rankine.retrieval.desc", "Can harvest any block provided its harvest level matches or is less than the crowbar's harvest level. Cannot harvest blocks in #rankine,crowbar_resistant.");
        add("enchantment.rankine.fulcrum", "Fulcrum");
        add("enchantment.rankine.fulcrum.desc", "Right click a block while above the ground to launch yourself forward.");
        add("enchantment.rankine.torque", "Torque");
        add("enchantment.rankine.torque.desc", "Deal increased damage to entities based on their size.");
        add("enchantment.rankine.leverage", "Leverage");
        add("enchantment.rankine.leverage.desc", "Increases the range of a crowbar by half a block per level.");
        add("enchantment.rankine.preparation", "Preparation");
        add("enchantment.rankine.preparation.desc", "Increases parry time.");
        add("enchantment.rankine.retaliate", "Retaliate");
        add("enchantment.rankine.retaliate.desc", "On successful parry, deal the damage you would have taken back to the enemy.");
        add("enchantment.rankine.retreat", "Retreat");
        add("enchantment.rankine.retreat.desc", "On successful parry, turn invisible for a brief amount of time.");
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
        add("rankine.jei.info_sap", "Can be placed in a cauldron. If a heat source is placed adjacent to the cauldron, the fluid is turned into Sugar.");
        add("rankine.jei.info_maple_sap", "Can be placed in a cauldron. If a heat source is placed adjacent to the cauldron, the fluid is turned into Maple Syrup, which can be bottled out.");
        add("rankine.jei.info_resin", "Can be placed in a cauldron. If a heat source is placed adjacent to the cauldron, the fluid is turned into Amber.");
        add("rankine.jei.info_juglone", "Can be placed in a cauldron. If a heat source is placed adjacent to the cauldron, the fluid is turned into Brown Dye.");
        add("rankine.jei.info_latex", "Can be placed in a cauldron. If a heat source is placed adjacent to the cauldron, the fluid is turned into Dry Rubber.");
        add("rankine.jei.info_amber", "Resin can be placed in a cauldron. If a heat source is placed adjacent to the cauldron, Resin is turned into Amber.");
        add("rankine.jei.info_dry_rubber", "Latex can be placed in a cauldron. If a heat source is placed adjacent to the cauldron, Latex is turned into Dry Rubber.");
        add("rankine.jei.info_battery_energy", "Total Charge: %1$s [?]");
        add("rankine.jei.info_battery_fusion", "%1$s Fusion Furnace processes");
        add("rankine.jei.info_battery_induction", "%1$s Induction Furnace processes");
        add("rankine.jei.info_battery_rechargeable", "Rechargeable");
        add("rankine.jei.info_battery_rtg", "Single-use");
        add("rankine.jei.tooltip_chance", "Chance: ");
        add("rankine.jei.tooltip_failure_chance", "Failure Chance: ");
        add("rankine.jei.tooltip_guaranteed", "Guaranteed");
        add("rankine.jei.tooltip_limited", "Can only drop once per block");
        add("rankine.jei.tooltip_max_rolls", "Max outputs: ");
        add("rankine.jei.tooltip_nonlimited", "Can drop multiple times per block");
        add("rankine.jei.tooltip_nothing", "Skip Output");
        add("rankine.jei.tooltip_additional", "Additional");
        add("rankine.jei.crucible_tooltip_additional", "Additional (Pick %s Unique Ingredients)");
        add("rankine.jei.tooltip_required", "Required");
        add("rankine.jei.tooltip_enchantment_required", "Requires Foraging enchantment");
        add("rankine.jei.tooltip_starting_enchantability", "Obtained at %s enchantability");
        add("rankine.jei.tooltip_enchantability_interval","Gains a level every %s enchantability");
        add("rankine.jei.tooltip_max_level", "Max Level: ");
        add("rankine.jei.tooltip_material_info", "Material Count: ");
        add("rankine.jei.tooltip_biomes_info", "Biomes: ");
        add("rankine.jei.tooltip_biomes_tags_info", "Biome Tags: ");
        add("rankine.jei.tooltip_dimension_info", "Dimension: ");
        add("rankine.jei.tooltip_mixing_time","Base Mixing Time: %s redstone power per ingredient total");
        add("rankine.jei.tooltip_total_mixing_time","Total Mixing Time: %s redstone power");
        add("rankine.jei.tooltip_total_button_lever","(%s button presses or lever flicks)");
        add("rankine.jei.tooltip_total_ingredients","%s total ingredients");
        add("rankine.jei.tooltip_total_output","%s total output");
        add("rankine.jei.tooltip_none", "None");
        add("rankine.jei.tooltip_any", "Any");
        add("rankine.jei.tooltip_tier", "Tier: ");
        add("rankine.jei.alloying_bonus_stats", "Bonus Stats");
        add("rankine.jei.alloying_enchantments", "Bonus Enchantments");
        add("rankine.jei.battery", "Battery");
        add("rankine.jei.crushing", "Crushing");
        add("rankine.jei.crucible", "Crucible");
        add("rankine.jei.alloying", "Alloying");
        add("rankine.jei.foraging", "Foraging");
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
        add("rankine.jei.treetapping", "Tree Tapping");
        add("rankine.jei.stripping", "Axe Stripping");
        add("rankine.jei.cauldron_drying", "Cauldron Drying");
        add("itemGroup.rankine.blocks", "Project Rankine Building");
        add("itemGroup.rankine.rankine_metallurgy", "Project Rankine Metallurgy");
        add("itemGroup.rankine.rankine_elements", "Project Rankine: Elements");
        add("itemGroup.rankine.rankine_components", "Project Rankine: Components");
        add("itemGroup.rankine.rankine_misc", "Project Rankine: Miscellaneous");
        add("itemGroup.rankine.rankine_biota", "Project Rankine: Biota");

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
        add("item.rankine.totem_of_infusing.tooltip", "Potion Rationing");
        add("item.rankine.totem_of_invigorating.tooltip", "Regenerative equipment");
        add("item.rankine.totem_of_imitating.tooltip", "False promises");
        add("item.rankine.totem_of_levitating.tooltip", "For keeping your feet clean");
        add("item.rankine.totem_of_mending.tooltip", "Regenerate your losses");
        add("item.rankine.totem_of_powering.tooltip", "The sea heart calls and infuses");
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
        add("rankine.advancements.story.get_flint.description", "Obtain flint from any source. Including from stones when mined using lower-tier tools");
        add("rankine.advancements.story.craft_rope.title", "Knot Likely");
        add("rankine.advancements.story.craft_rope.description", "Gather rope by obtaining a flint knife and harvesting grass");
        add("rankine.advancements.story.craft_dowsing_rod.title", "Random Guess");
        add("rankine.advancements.story.craft_dowsing_rod.description", "Use a Dowsing Rod to detect the water level of the area. Infinite water sources only work below this height");
        add("rankine.advancements.story.craft_wooden_hammer.title", "Time to Swing");
        add("rankine.advancements.story.craft_wooden_hammer.description", "Make a Wooden Mallet to start swinging in rhythm at rocks and other materials");
        add("rankine.advancements.story.craft_stone_hammer.title", "Stone Smash Stone");
        add("rankine.advancements.story.craft_stone_hammer.description", "Constructing a stronger mallet drops more resources from crushed blocks");
        add("rankine.advancements.story.craft_alloy_hammer.title", "Dust to Dust Again");
        add("rankine.advancements.story.craft_alloy_hammer.description", "Make a hammer with a harvest level of 2 or greater (alloys required) to crack open those tougher materials");
        add("rankine.advancements.story.crushing_heads.title", "Pokey Pokey");
        add("rankine.advancements.story.crushing_heads.description", "Automatically crush blocks by pushing Crushing Heads with pistons");
        add("rankine.advancements.story.get_cobblestone.title", "Cobble Cobble");
        add("rankine.advancements.story.get_cobblestone.description", "Obtain cobblestone from crushing stones using a hammer");
        add("rankine.advancements.story.craft_mixing_barrel.title", "Mix it Up");
        add("rankine.advancements.story.craft_mixing_barrel.description", "Make a mixing barrel to start mixing materials with redstone signals");
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
        add("rankine.advancements.story.craft_refractory_bricks.description", "Make Refractory Brick by finding/crafting fire clay and mortar");
        add("rankine.advancements.story.craft_high_refractory_bricks.title", "Another Other Brick in the Wall");
        add("rankine.advancements.story.craft_high_refractory_bricks.description", "Make High Refractory Bricks for crafting purposes or to upgrade the beehive oven");
        add("rankine.advancements.story.craft_ultra_high_refractory_bricks.title", "Another Another Another");
        add("rankine.advancements.story.craft_ultra_high_refractory_bricks.description", "Make Ultra High Refractory Bricks for crafting purposes or to upgrade the beehive oven");
        add("rankine.advancements.story.craft_alloy_furnace.title", "Combined Strength");
        add("rankine.advancements.story.craft_alloy_furnace.description", "Make an Alloy Furnace to begin forging new metals");
        add("rankine.advancements.story.craft_material_testing_table.title", "Knowledge of Strength");
        add("rankine.advancements.story.craft_material_testing_table.description", "Make a Material Testing Table to begin examining properties of elements and alloys");
        add("rankine.advancements.story.craft_blast_furnace.title", "Blast from the Past");
        add("rankine.advancements.story.craft_blast_furnace.description", "Make a Blast Furnace to begin smelting more complex ores");
        add("rankine.advancements.story.craft_beehive_oven_pit.title", "No Bees Harmed");
        add("rankine.advancements.story.craft_beehive_oven_pit.description", "Build a beehive oven pit");
        add("rankine.advancements.story.craft_template_table.title", "The Plan");
        add("rankine.advancements.story.craft_template_table.description", "Make an Alloy Template Table to automate the creation of alloys");
        add("rankine.advancements.story.make_coke.title", "I cant believe it's not Coal!");
        add("rankine.advancements.story.make_coke.description", "Produce coke from bituminous and sub-bituminous coal blocks");
        add("rankine.advancements.story.craft_crucible.title", "Refractory Factory");
        add("rankine.advancements.story.craft_crucible.description", "Construct a crucible to form steel");
        add("rankine.advancements.story.make_steel.title", "Steel Yourself");
        add("rankine.advancements.story.make_steel.description", "Make Steel Alloy by using the Crucible");
        add("rankine.advancements.story.make_glowstone.title", "Unnatural Glow");
        add("rankine.advancements.story.make_glowstone.description", "Make additional Glowstone from components in the crucible");
        add("rankine.advancements.story.make_redstone.title", "Redstoned");
        add("rankine.advancements.story.make_redstone.description", "Make additional Redstone from cinnabar and components in the crucible");
        add("rankine.advancements.story.craft_brigandine_armor.title", "Plate Up");
        add("rankine.advancements.story.craft_brigandine_armor.description", "Smith together a full set of Brigandine Armor");
        add("rankine.advancements.story.craft_diving_armor.title", "Deeper Waters");
        add("rankine.advancements.story.craft_diving_armor.description", "Make a full set of Diving Armor");
        add("rankine.advancements.story.craft_conduit_diving_armor.title", "Into the Abyss");
        add("rankine.advancements.story.craft_conduit_diving_armor.description", "Make a full set of Conduit Diving Armor");
        add("rankine.advancements.story.craft_gas_mask.title", "Breathing in the Chemicals");
        add("rankine.advancements.story.craft_gas_mask.description", "Make a gas mask to protect against hazardous gases");
        add("rankine.advancements.story.craft_shulker_gas_vacuum.title", "Vacuum Sealed");
        add("rankine.advancements.story.craft_shulker_gas_vacuum.description", "Make a shulker gas vacuum to transport gases");
        add("rankine.advancements.story.craft_evaporation_tower.title", "Water Extraction");
        add("rankine.advancements.story.craft_evaporation_tower.description", "Construct an Evaporation Tower to generate resources from water in different biomes");
        add("rankine.advancements.story.craft_electromagnet.title", "The Magnetic Force");
        add("rankine.advancements.story.craft_electromagnet.description", "Make any tier of Electromagnet to push and pull blocks with the magnetic force");
        add("rankine.advancements.story.craft_saddle_tree.title", "A Horse");
        add("rankine.advancements.story.craft_saddle_tree.description", "Craft a saddle tree to make horse-related equipment");
        add("rankine.advancements.story.craft_fusion_furnace.title", "Complex Systems");
        add("rankine.advancements.story.craft_fusion_furnace.description", "Make a Fusion Furnace to begin utilizing gases and liquids further");
        add("rankine.advancements.story.get_mercury.title", "Amalgamation");
        add("rankine.advancements.story.get_mercury.description", "Obtain mercury to make Amalgam alloys");
        add("rankine.advancements.story.craft_distillation_tower.title", "Atmospheric Composition");
        add("rankine.advancements.story.craft_distillation_tower.description", "Construct a Distillation Tower to obtain gases from the air");
        add("rankine.advancements.story.craft_induction_furnace.title", "Heating Up");
        add("rankine.advancements.story.craft_induction_furnace.description", "Make an Induction Furnace for more advanced alloys");
        add("rankine.advancements.story.craft_magnet.title", "Attraction");
        add("rankine.advancements.story.craft_magnet.description", "Make a handheld magnet to bring items closer to you");
        add("rankine.advancements.story.craft_tree_tap.title", "Flowing Phloem");
        add("rankine.advancements.story.craft_tree_tap.description", "Make a tree tap to begin extracting liquids from trees");
        add("rankine.advancements.story.craft_ground_tap.title", "Water Reservoir");
        add("rankine.advancements.story.craft_ground_tap.description", "Create a ground tap network to suck water out of the ground");
        add("rankine.advancements.story.craft_flood_gate.title", "Oceans Arise");
        add("rankine.advancements.story.craft_flood_gate.description", "Flood Gates fill the space beneath them with fluids placed directy above them. Use an infinite source to create lakes quickly");
        add("rankine.advancements.story.craft_battery.title", "Fully Charged");
        add("rankine.advancements.story.craft_battery.description", "Make a battery to provide power to certain machines");
        add("rankine.advancements.story.stone_collector.title", "We Have the Rockiest Rocks");
        add("rankine.advancements.story.craft_bandage.title", "Quick Healing");
        add("rankine.advancements.story.craft_bandage.description", "Heal some minor damage with a bandage");
        add("rankine.advancements.story.craft_trampoline.title", "Boing!");
        add("rankine.advancements.story.craft_trampoline.description", "Make a trampoline to launch high into the air");
        add("rankine.advancements.story.get_hematite.title", "Hema-hema-he ma tite");
        add("rankine.advancements.story.get_hematite.description", "Use tier iron or higher hammers to make use of the most abundant iron ore");
        add("rankine.advancements.story.get_meteoric_iron.title", "Iron from Space");
        add("rankine.advancements.story.get_meteoric_iron.description", "Find a meteorite and crush the ore in it to get meteoric iron, a native alloy of invar");
        add("rankine.advancements.story.get_ironstone.title", "Iron in the Rough");
        add("rankine.advancements.story.get_ironstone.description", "Find ironstone under the sands of a desert or mesa");
        add("rankine.advancements.story.get_bog_iron.title", "Soggy Iron");
        add("rankine.advancements.story.get_bog_iron.description", "Find Bog Iron submerged in a swamp");
        add("rankine.advancements.story.get_banded_iron_formation.title", "Iron Sandwhich");
        add("rankine.advancements.story.get_banded_iron_formation.description", "Find Banded Iron Formations near the world surface");
        add("rankine.advancements.story.craft_element_indexer.title", "Elementary");
        add("rankine.advancements.story.craft_element_indexer.description", "Use a device to examine element properties");
        add("rankine.advancements.story.craft_penning_trap.title", "Tenuous Trap");
        add("rankine.advancements.story.craft_penning_trap.description", "Use a Penning Trap to 'safely' collect antimatter");
        add("rankine.advancements.story.craft_glass_cutter.title", "Transparent");
        add("rankine.advancements.story.craft_glass_cutter.description", "Use a Glass Cutter to recycle that broken glass");
        add("rankine.advancements.story.info_movement.title", "A Primer on Movement");
        add("rankine.advancements.story.info_movement.description", "Movement speed changes depending on what material you are walking on. Try crafting basic equipment for your feet to improve movement in various terrains");
        add("rankine.advancements.story.treads.title", "Don't Tread On Me");
        add("rankine.advancements.story.treads.description", "Aquire boots with the four enchantments from feet equipment");
        add("rankine.advancements.challenges.speed_runner.title", "Literal Speed Runner");
        add("rankine.advancements.challenges.speed_runner.description", "Have a pair of boots with every movement speed enchantment");

        //add("rankine.advancements.story.craft_thorium_arrow.title", "Sounds of Thunder");
        //add("rankine.advancements.story.craft_thorium_arrow.description", "Make Thorium Arrows to control the power of lightning with your bow");

        add("rankine.advancements.challenges.root.title", "Project Rankine Challenges");
        add("rankine.advancements.challenges.root.description", "Complete your understanding of the changed world");
        add("rankine.advancements.challenges.stone_collector.title", "We Have the Rockiest Rocks");
        add("rankine.advancements.challenges.stone_collector.description", "Collect all the new types of rocks");
        add("rankine.advancements.challenges.soil_collector.title", "We Have the Dirtiest Dirt");
        add("rankine.advancements.challenges.soil_collector.description", "Collect all the new types of soil, not dirt");
        add("rankine.advancements.challenges.gas_collector.title", "Frobscottle");
        add("rankine.advancements.challenges.gas_collector.description", "Bottle all types of gasses, consumption optional");
        add("rankine.advancements.challenges.element_collector.title", "Periodic Collector");
        add("rankine.advancements.challenges.element_collector.description", "Collect every element ingot in the extended Periodic Table");
        add("rankine.advancements.challenges.totem_collector.title", "On the Other Hand");
        add("rankine.advancements.challenges.totem_collector.description", "Make every type of Totem");
        add("rankine.advancements.challenges.alloy_collector.title", "Worldmoulder");
        add("rankine.advancements.challenges.alloy_collector.description", "Create all of the different types of alloys");
        add("rankine.advancements.challenges.bronze_harvest.title", "Skipping Stones");
        add("rankine.advancements.challenges.bronze_harvest.description", "Make a Bronze Pickaxe with a Harvest Level of 3 (or Steel equivalent)");
        add("rankine.advancements.challenges.pewter_enchant.title", "Hidden Power");
        add("rankine.advancements.challenges.pewter_enchant.description", "Make a Pewter Tool with an Enchantability greater than 13");
        add("rankine.advancements.challenges.colored_gold_netherite.title", "Gold Standard");
        add("rankine.advancements.challenges.colored_gold_netherite.description", "Make a Colored Gold tool using Netherite instead of Gold");
        add("rankine.advancements.challenges.craft_pancake_breakfast.title", "Fulfilling Feast");
        add("rankine.advancements.challenges.craft_pancake_breakfast.description", "Make a stack of Pancake Breakfast");
        add("rankine.advancements.challenges.meteoric_materials.title", "Stellar Performance");
        add("rankine.advancements.challenges.meteoric_materials.description", "Find all the forms of raw meteoric ores");
        add("rankine.advancements.challenges.geode_collector.title", "Gifts of the Earth");
        add("rankine.advancements.challenges.geode_collector.description", "Find all of the different types of geodes");
        add("rankine.advancements.challenges.radioactive.title", "Radioactive");
        add("rankine.advancements.challenges.radioactive.description", "Craft all of the RTGs");


        //SUBTITLE
        add("rankine.subtitle.totem_of_enduring_use", "totem use");
        add("rankine.subtitle.penning_trap_absorb", "Penning Trap absorb");
        add("rankine.subtitle.shulker_gas_vacuum_absorb", "Shulker Gas Vacuum absorb");
        add("rankine.subtitle.shulker_gas_vacuum_release", "Shulker Gas Vacuum release");
        add("rankine.subtitle.sediment_fan_gen", "sedimentary block generation");


        //JOURNAL==============================================================================
        add("rankine.journal.landing_text", "Project Rankine is about material acquisition and usage. Explore the variety of items and blocks and the new methods to obtain them. Follow the advancements for general progression.");

        //Orientation
        add("rankine.journal.cat_orientation.name", "Orientation");
        add("rankine.journal.cat_orientation.desc", "A general guide for working with Project Rankine systems.");

        add("rankine.journal.cat_orientation.intro.name", "Resources");
        add("rankine.journal.cat_orientation.intro.text1", "$(o)For more resources see the following pages.");

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
        add("rankine.journal.cat_biota.desc", "From decoration to edibles, a new ecosystem emerges across all biomes. Biota encompasses all the living aspects of Project Rankine.");

        add("rankine.journal.cat_biota.crops.name", "Crops");
        add("rankine.journal.cat_biota.crops.text1", "");
        add("rankine.journal.cat_biota.crops.grains_heading", "Grains");
        add("rankine.journal.cat_biota.crops.grains", "A variety of grains have rooted across the plains and savannas. They are grown and used similar to wheat.");
        add("rankine.journal.cat_biota.crops.corn_ear", "");
        add("rankine.journal.cat_biota.crops.soybeans", "");
        add("rankine.journal.cat_biota.crops.asparagus", "");
        add("rankine.journal.cat_biota.crops.jute", "");
        add("rankine.journal.cat_biota.crops.cotton", "");
        add("rankine.journal.cat_biota.crops.berries_heading", "Berries");
        add("rankine.journal.cat_biota.crops.berries", "Berries are a raw source of food that grow on bushes throughout the world. They can be used to craft Fruit Jam.");
        add("rankine.journal.cat_biota.crops.pineapple", "");
        add("rankine.journal.cat_biota.crops.aloe", "");
        add("rankine.journal.cat_biota.crops.banana_yucca", "");
        add("rankine.journal.cat_biota.crops.camphor_basil_leaf", "");

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

        add("rankine.journal.cat_biota.mushrooms.name", "Mushrooms");
        add("rankine.journal.cat_biota.mushrooms.text1", "New types of fungi populate the undergrowth of wooded ecosystems. All new varieties grow on woody material. They can be grown into large mushrooms as well.");
        add("rankine.journal.cat_biota.mushrooms.edible_mushrooms_header", "Edible Mushrooms");
        add("rankine.journal.cat_biota.mushrooms.edible_mushrooms", "Can be made into stew.");
        add("rankine.journal.cat_biota.mushrooms.inedible_mushrooms_header", "Inedible Mushrooms");
        add("rankine.journal.cat_biota.mushrooms.inedible_mushrooms", "");
        add("rankine.journal.cat_biota.mushrooms.text2", "Tinder Conk Mushrooms can be used to transport fire. After burning a Tinder Conk in a fire, campfire, furnace or lava, it will begin to smoulder. A Smouldering Tinder Conk can be used as furnace fuel or like a flint and steel.");

        add("rankine.journal.cat_biota.trees.name", "Trees");
        add("rankine.journal.cat_biota.trees.text1", "Large wooded plants, much like vanilla trees. Each tree can be found naturally in certain biomes of the world.");
        add("rankine.journal.cat_biota.trees.cedar.title", "Cedar");
        add("rankine.journal.cat_biota.trees.cedar", "Native to taiga biomes.");
        add("rankine.journal.cat_biota.trees.balsam_fir.title", "Balsam Fir");
        add("rankine.journal.cat_biota.trees.balsam_fir", "Native to mountain biomes.");
        add("rankine.journal.cat_biota.trees.eastern_hemlock.title", "Eastern Hemlock");
        add("rankine.journal.cat_biota.trees.eastern_hemlock", "Native to giant taiga biomes.");
        add("rankine.journal.cat_biota.trees.western_hemlock.title", "Western Hemlock");
        add("rankine.journal.cat_biota.trees.western_hemlock", "Native to giant taiga biomes.");
        add("rankine.journal.cat_biota.trees.pinyon_pine.title", "Pinyon Pine");
        add("rankine.journal.cat_biota.trees.pinyon_pine", "Native to savanna biomes.");
        add("rankine.journal.cat_biota.trees.juniper.title", "Juniper");
        add("rankine.journal.cat_biota.trees.juniper", "Native to savanna biomes.");
        add("rankine.journal.cat_biota.trees.black_birch.title", "Black Birch");
        add("rankine.journal.cat_biota.trees.black_birch", "Native to river biomes.");
        add("rankine.journal.cat_biota.trees.yellow_birch.title", "Yellow Birch");
        add("rankine.journal.cat_biota.trees.yellow_birch", "Native to forest biomes.");
        add("rankine.journal.cat_biota.trees.red_birch.title", "Red Birch");
        add("rankine.journal.cat_biota.trees.red_birch", "Native to mountain biomes.");
        add("rankine.journal.cat_biota.trees.maple.title", "Maple");
        add("rankine.journal.cat_biota.trees.maple", "Native to forest biomes.");
        add("rankine.journal.cat_biota.trees.magnolia.title", "Magnolia");
        add("rankine.journal.cat_biota.trees.magnolia", "Native to river biomes.");
        add("rankine.journal.cat_biota.trees.black_walnut.title", "Black Walnut");
        add("rankine.journal.cat_biota.trees.black_walnut", "Native to dark oak forests..");
        add("rankine.journal.cat_biota.trees.coconut_palm.title", "Coconut Palm");
        add("rankine.journal.cat_biota.trees.coconut_palm", "Native to beaches");
        add("rankine.journal.cat_biota.trees.cork_oak.title", "Cork Oak");
        add("rankine.journal.cat_biota.trees.cork_oak", "Native to savanna biomes.");
        add("rankine.journal.cat_biota.trees.sharinga.title", "Sharinga");
        add("rankine.journal.cat_biota.trees.sharinga", "Native to jungle biomes.");
        add("rankine.journal.cat_biota.trees.cinnamon.title", "Cinnamon");
        add("rankine.journal.cat_biota.trees.cinnamon", "Native to jungle biomes.");
        add("rankine.journal.cat_biota.trees.erythrina.title", "Erythrina");
        add("rankine.journal.cat_biota.trees.erythrina", "Native to badlands biomes.");
        add("rankine.journal.cat_biota.trees.honey_locust.title", "Honey Locust");
        add("rankine.journal.cat_biota.trees.honey_locust", "Native to plains biomes.");
        add("rankine.journal.cat_biota.trees.weeping_willow.title", "Weeping Willow");
        add("rankine.journal.cat_biota.trees.weeping_willow", "Native to swamp biomes.");

        //Constructs
        add("rankine.journal.cat_constructs.name", "Constructs");
        add("rankine.journal.cat_constructs.desc", "Not all blocks and items are of natural origins.");

        add("rankine.journal.cat_constructs.heating_element.name", "Heating Elements");
        add("rankine.journal.cat_constructs.heating_element.text1", "Heating elements may replace natural blocks as a source of heat (such as for the crucible). On a redstone pulse, blocks in a 13 radius cube will be melted.");

        add("rankine.journal.cat_constructs.metal_poles.name", "Metal Poles");
        add("rankine.journal.cat_constructs.metal_poles.text1", "Metal poles are crafted from any alloy. The variety of alloy colors make them an excellent decoration block. They can also be right-clicked with leaves or torches to add garland, string lights, or both! Holding shift while clicking will teleport the player to the bottom of pole column.");
        add("rankine.journal.cat_constructs.metal_poles.text2", "All pole recipes follow this pattern.");

        add("rankine.journal.cat_constructs.light_sources.name", "Light Sources");
        add("rankine.journal.cat_constructs.light_sources.text1", "A collection of the major light emitting blocks.");
        add("rankine.journal.cat_constructs.light_sources.text2", "Lanterns");
        add("rankine.journal.cat_constructs.light_sources.text3", "Most metals burn with a distinctive color. Right click a lantern or soul lantern with a metal nugget to change the flame color. Magnesium, Netherite, and Endositum lanterns produce particles.");

        add("rankine.journal.cat_constructs.metal_ladders.name", "Metal Ladders");
        add("rankine.journal.cat_constructs.metal_ladders.text1", "Metal Ladders can be crafted from any alloy. Due to their sturdy nature, metal ladders can be placed up to 8 blocks above the last one attached to a wall. Right click with a ladder on a ladder to autoplace ladders vertically. These blocks have $(l:mechanics/building_modes)Building Modes$().");
        add("rankine.journal.cat_constructs.metal_ladders.text2", "All metal ladder recipes follow this pattern.");

        add("rankine.journal.cat_constructs.pedestals.name", "Pedestals");
        add("rankine.journal.cat_constructs.pedestals.text1", "Pedestals, while having a unique shape, are primarily designed to display items. Right click a pedestal with an item to place it atop. A comparator will detect a signal strength based off the rarity of the item; 3,7,11 and 15 for common, uncommon, rare and epic respectively.");
        add("rankine.journal.cat_constructs.pedestals.text2", "All pedestal recipes follow this pattern.");

        add("rankine.journal.cat_constructs.electromagnets.name", "Electromagnets");
        add("rankine.journal.cat_constructs.electromagnets.text1", "Electromagnets are able to move blocks when powered with redstone. Shift right-click to switch the magnet between attraction/repulsion mode. All applicable blocks in the range will either be pulled or pushed away from the magnet. Some config options exist for magnet ranges and allowed materials. By default, it works with any blocks of the metal material.");
        add("rankine.journal.cat_constructs.electromagnets.alnico_electromagnet", "Default Range: 10 blocks");
        add("rankine.journal.cat_constructs.electromagnets.simple_electromagnet", "Default Range: 5 blocks");
        add("rankine.journal.cat_constructs.electromagnets.rare_earth_electromagnet", "Default Range: 15 blocks");

        add("rankine.journal.cat_constructs.mixing_barrel.name", "Mixing Barrel");
        add("rankine.journal.cat_constructs.mixing_barrel.text1", "The mixing barrel combines together materials and fluid of various composition. Place items in any of the four items slots and their relative compositions will be displayed. Add fluid to the barrel by right clicking with a bucket. When an appropriate recipe is in the barrel, supply redstone pulses to the block to begin spinning. Each recipe has a variable number of spins needed to complete based off the redstone signal strength.");
        add("rankine.journal.cat_constructs.mixing_barrel.text2", "A recipe is defined by composition of ingredients needed to make the output. The actual quantity of input material can vary and this will determine the quantity of the output and the number of spins required. The material scale of the recipe (shown by the #:# in JEI) means some recipes are not 1 to 1, make sure your mixing batch doesn't go over 64.");
        add("rankine.journal.cat_constructs.mixing_barrel.asphalt_heading", "Asphalt");
        add("rankine.journal.cat_constructs.mixing_barrel.asphalt", "Asphalts are decorative blocks for more urban environments. Right click on asphalt blocks with any dye to change the rotation and pattern of the block.");
        add("rankine.journal.cat_constructs.mixing_barrel.cement_mix", "Cement mix is an early game component used to create a variety of reinforced blocks suck as bricks, cements, and concretes. The simplest recipe is equal parts sand, clay balls, and limestone with water.");

        add("rankine.journal.cat_constructs.gas_collection.name", "Gas Collection");
        add("rankine.journal.cat_constructs.gas_collection.text1", "Gas blocks, much like air blocks, are difficult to interact with. They also cannot be simply destroyed by placing a block into their space.  When your head is inside a gas block, you become affected by various effects and may potentially suffocate. They rarely occur naturally except for from $(l:mineralogy/fumaroles)fumaroles$().");
        add("rankine.journal.cat_constructs.gas_collection.text2", "The shulker gas vacuum can store gasses internally by right clicking inside a gas block. Gasses can be placed in a new location in a likewise manner. If glass bottles are present in the offhand, the vacuum will fill the bottles.");
        add("rankine.journal.cat_constructs.gas_collection.text3", "The gas vent is an orientable block that moves gas blocks from one side to the opposite.");
        add("rankine.journal.cat_constructs.gas_collection.text4", "The gas bottler intakes gas block from the front side and bottles them using glass bottles from its inventory.");
        add("rankine.journal.cat_constructs.gas_collection.text5", "The air distillation tower intakes ambient air and separates out the gaseous components. The multiblock is shown on the next page. Pay careful attention to the internal column of the tower as there are air gaps. Gas vents can be substituted in place of sheetmetal to move gas from the inside spaces to out.");
        add("rankine.journal.cat_constructs.gas_collection.text6", "The multiblock on the previous page demonstrates 2 levels of construction. A single layer consists of packing - air space - packing with sheetmetal around the entire outside. The top layer use sheetmetal instead of packing. A full size tower is 10 layers, or 33 blocks tall including the base. $(br2)Each layer of the tower will produce a different gas output. By default, the outputs are also affected by the dimmension.");

        add("rankine.journal.cat_constructs.fusion_furnace.name", "Fusion Furnace");
        add("rankine.journal.cat_constructs.fusion_furnace.text1", "The Fusion Furnace utilizes solids, liquids, and gases to make new outputs. Many recipes which involve gases will require Bottled Gases and Empty Bottles. You can acquire Bottled Gases using either the $(l:materials/gasses)Gas Bottler$() or the $(l:materials/gasses)Shulker Gas Vacuum$(). In order for a recipe to go through, you must have the required ingredients, fluid in the input fluid tank, and bottled gas in the slot adjacent to the tank.");
        add("rankine.journal.cat_constructs.fusion_furnace.text2", " This machine does not use coal, and must use electrical energy from items such as Batteries or RTGs.");

        add("rankine.journal.cat_constructs.batteries.name", "Batteries");
        add("rankine.journal.cat_constructs.batteries.text1", "Batteries are energy storage devices that fuel electric machines.");
        add("rankine.journal.cat_constructs.batteries.text2", "Batteries");
        add("rankine.journal.cat_constructs.batteries.text3", "");
        add("rankine.journal.cat_constructs.batteries.text4", "The battery charger increases the charge of batteries within its inventory every ~20 seconds. The amount of charge returned depends on the capacity of the battery charger, shown by the red bar in the gui. To increase the charge capacity, place a daylight sensor on top of the charger. Additional charge can be added when lightning strikes a lightning rod adjacent to the charger.");
        add("rankine.journal.cat_constructs.batteries.text5", "RTGs");
        add("rankine.journal.cat_constructs.batteries.text6", "RTGs are radioactively powered batteries with a much higher capacity. They cannot be recharged.");

        add("rankine.journal.cat_constructs.beehive_oven.name", "Beehive Oven");
        add("rankine.journal.cat_constructs.beehive_oven.text1", "The beehive oven is an in-world furnace that slowly smelts blocks placed inside it. While holding bricks in the offhand, right click with Building Tool on the Oven Pit to place blocks. Or you can place the blocks manually by matching the structure on the next page.");
        add("rankine.journal.cat_constructs.beehive_oven.text2", "It will smelt blocks in a 3x2x3 area above it, minus the center column (16 blocks total). Once the structure is built, place your blocks in any of those smelting spaces and light the pit block with the conventional fire starting methods. The oven pit will un-light if an invalid structure is detected, or once the process is complete. Carbon Dioxide gas will be generated in any empty spaces inside the oven.");
        add("rankine.journal.cat_constructs.beehive_oven.text4", "The full structure for the oven. The Oven Pit requires 6 empty blocks above it for the smoke to escape and to function properly.");
        add("rankine.journal.cat_constructs.beehive_oven.text5", "Replacing the bricks for High Refractory Bricks will decrease the cooking speed to 50% the default time. Can be upgraded using the Building Tool");
        add("rankine.journal.cat_constructs.beehive_oven.text6", "Replacing the bricks for Ultra High Refractory Bricks will decrease the cooking speed to 25% the default time. Can be upgraded using the Building Tool");

        add("rankine.journal.cat_constructs.tree_tapping.name", "Tree Tapping");
        add("rankine.journal.cat_constructs.tree_tapping.text1", "The Tree Tap is a device that extracts various fluids from logs. Tree taps will not work if there is another one in any adjacent block. Also, the tree needs to be alive, meaning the tree must contained naturally spawned leaves. Taps only operate during the middle section of the day.");
        add("rankine.journal.cat_constructs.tree_tapping.tree_tap", "A tree tap will eventually fill with liquid and can be retrieved by right clicking with a bucket. This fluid can be placed in a cauldron to be boiled into various resources. Place a heat source (magma block, heating element, ect.) under or next to the cauldron to heat it.");
        add("rankine.journal.cat_constructs.tree_tapping.tap_line", "Tap lines are essentially a pipe network for tree taps to output to a cauldron. Fluids can not flow upwards in the lines. Tap lines will connect to a tree tap from below and to any side of a cauldron.");
        add("rankine.journal.cat_constructs.tree_tapping.water", "Water can be extracted from the variety of birch trees.");
        add("rankine.journal.cat_constructs.tree_tapping.lava", "Lava can be extracted from the variety of nether trees.");
        add("rankine.journal.cat_constructs.tree_tapping.juglone", "Juglone can be extracted from black walnut trees. Boil in a cauldron to produce brown dye. Also used as a crafting ingredient.");
        add("rankine.journal.cat_constructs.tree_tapping.resin", "Resin can be extracted from a variety of coniferous trees. Boil in a cauldron to produce amber. Also used as a crafting ingredient.");
        add("rankine.journal.cat_constructs.tree_tapping.latex", "Latex can be extracted from sharinga trees. Boil in a cauldron to produce dry rubber.");
        add("rankine.journal.cat_constructs.tree_tapping.sap", "Sap can be extracted from a variety of deciduous trees. Boil in a cauldron to produce sugar.");
        add("rankine.journal.cat_constructs.tree_tapping.maple_sap", "Maple Sap can be extracted from maple trees. Boil in a cauldron to produce maple syrup. Use a glass bottle to pickup the syrup.");

        add("rankine.journal.cat_constructs.crucible.name", "Crucible");
        add("rankine.journal.cat_constructs.crucible.text1", "The crucible is a device capable of melting the materials required to forge steel and other molten products. Place the crucible above a heat source. The top four slots of the Crucible are your inputs. Each item used as an input must be unique (cannot be used in more than one slot) and some inputs cannot be mixed together.");
        add("rankine.journal.cat_constructs.crucible.text2", "Valid heat sources include: $(li)Fire$() $(li)Lava$() $(li)Magma Block$() $(li)Campfires");

        add("rankine.journal.cat_constructs.evaporation_tower.name", "Evaporation Tower");
        add("rankine.journal.cat_constructs.evaporation_tower.text1", "A method to remove heavy materials from water. When constructed, the tower will slowly and passively generate various resources (can only run when outpute is empty, use a hopper!!). The resource lootable depends on the location. By default, water can be evaporated in the following different locations: $(li)Oceans $(li)Deserts $(li)Rivers and Swamps $(li)Caves $(li)Elsewhere");
        add("rankine.journal.cat_constructs.evaporation_tower.text2", "Construction notes: $(li)The inside is filled with the desired fluid. $(li)The walls are made from any #forge:sheetmetals. $(li)More layers of sheet metal and fluid can be added to decrease processing time by 4% each layer (max of 20). $(br2)By default, the sheetmetal in the tower will occasionally break, which will limit the max height to that broken layer. Configurable.");

        add("rankine.journal.cat_constructs.mining_heads.name", "Mining Heads");
        add("rankine.journal.cat_constructs.mining_heads.text1", "To automate the mining process, pistons can be used to push a mining head into a block. Place the mining head on the face of the piston and then a one block gap before the desired block to be crushed. Works in any direction.");
        add("rankine.journal.cat_constructs.mining_heads.text2", "The tier of the mining head determines which tier of block can be broken.");


        //Materials
        add("rankine.journal.cat_materials.name", "Base Materials");
        add("rankine.journal.cat_materials.desc", "The fundamental components of the world. Some are found naturally occurring. Others are gathered, processed, and extracted from various sources.");

        add("rankine.journal.cat_materials.gasses.name", "Gas Blocks");
        add("rankine.journal.cat_materials.gasses.text1", "Gas Blocks");
        add("rankine.journal.cat_materials.gasses.hydrogen", "Obtainable through fusion furnace processes and the Distillation Tower.");
        add("rankine.journal.cat_materials.gasses.helium", "Obtainable through fusion furnace processes and the Distillation Tower.");
        add("rankine.journal.cat_materials.gasses.nitrogen", "Obtainable through fusion furnace processes and the Distillation Tower.");
        add("rankine.journal.cat_materials.gasses.oxygen", "Obtainable through fusion furnace processes and the Distillation Tower.");
        add("rankine.journal.cat_materials.gasses.fluorine", "Obtainable through fusion furnace processes and the Distillation Tower.");
        add("rankine.journal.cat_materials.gasses.neon", "Obtainable through fusion furnace processes and the Distillation Tower.");
        add("rankine.journal.cat_materials.gasses.chlorine", "Obtainable through fusion furnace processes and the Distillation Tower.");
        add("rankine.journal.cat_materials.gasses.argon", "Obtainable through fusion furnace processes and the Distillation Tower.");
        add("rankine.journal.cat_materials.gasses.krypton", "Obtainable through fusion furnace processes and the Distillation Tower.");
        add("rankine.journal.cat_materials.gasses.xenon", "Obtainable through fusion furnace processes and the Distillation Tower.");
        add("rankine.journal.cat_materials.gasses.radon", "Obtainable through fusion furnace processes and the Distillation Tower.");
        add("rankine.journal.cat_materials.gasses.oganesson", "Obtainable through fusion furnace processes and the Distillation Tower.");
        add("rankine.journal.cat_materials.gasses.ammonia", "Obtainable through fusion furnace processes and the Distillation Tower.");
        add("rankine.journal.cat_materials.gasses.carbon_dioxide", "Obtainable through fusion furnace processes and the Distillation Tower.");
        add("rankine.journal.cat_materials.gasses.hydrogen_chloride", "$(liObtained from a $(l:mineralogy/fumaroles)fumarole$().");
        add("rankine.journal.cat_materials.gasses.hydrogen_fluoride", "$(liObtained from a $(l:mineralogy/fumaroles)fumarole$().");
        add("rankine.journal.cat_materials.gasses.hydrogen_sulfide", "$(liObtained from a $(l:mineralogy/fumaroles)fumarole$().");
        add("rankine.journal.cat_materials.gasses.sulfur_dioxide", "$(liObtained from a $(l:mineralogy/fumaroles)fumarole$().");
        add("rankine.journal.cat_materials.gasses.tungsten_hexafluoride", "$(liObtained through and used for fusion furnace processing.");

        add("rankine.journal.cat_materials.elements.name", "Elements");
        add("rankine.journal.cat_materials.elements.text1", "To be discovered.");

        //Mechanics
        add("rankine.journal.cat_mechanics.name", "Mechanics");
        add("rankine.journal.cat_mechanics.desc", "Gameplay mechanics. Some are changes to existing systems, while others are additional.");
        add("rankine.journal.cat_mechanics.foraging.name", "Foraging");
        add("rankine.journal.cat_mechanics.foraging.text1", "There is a chance to find a variety of seeds and roots when breaking dirt and related blocks. This will only happen when using an open hand or crude tools. The foraging enchantment for hoes adds additional items that can be dropped.");

        add("rankine.journal.cat_mechanics.crushing.name", "Crushing");
        add("rankine.journal.cat_mechanics.crushing.text1", "Crushing is a process that involves breaking blocks and items into smaller components. This is typically achieved by using a new tool, the $(l:tools/hammers)hammer$(). Crushing, and by association the hammer, work differently than the typical mining process.");
        add("rankine.journal.cat_mechanics.crushing.text2", "To crush a block in-world, you must swing your hammer at the right time. When the attack speed indicator (the sword that appears underneath your crosshair) is filled, you may swing your hammer to crush the block. Trying to swing before the attack speed indicator is filled will result in nothing happening. This means that a hammer with a faster attack speed stat will be able to break blocks at a quicker rate.");
        add("rankine.journal.cat_mechanics.crushing.text3", "The main thing that you need to consider when crushing a block is the crushing/harvest level of your tool. Similar to pickaxes, hammers cannot break blocks greater than its harvest level. However, the hammer's harvest level also influences how many rolls of the drops you will receive. JEI is the best source of information on the potential drops of any block when crushing it.");
        add("rankine.journal.cat_mechanics.crushing.text4", "Hovering over each item in JEI will also let you know if you can obtain multiple of an item or only obtain it once. Besides showing the drops you can potentially get, there can also be an output that looks like a barrier. When this output is rolled, it acts as a skip that reduces the total number of outputs by 1. The cyan text tells you the max number of outputs (besides guaranteed outputs) that you can obtain from crushing.");
        add("rankine.journal.cat_mechanics.crushing.text5", "To automate the crushing process, pistons can be used to push a crushing head into a block. Place the crushing head on the face of the piston and then a one block gap before the desired block to be crushed. Works in any direction.");
        add("rankine.journal.cat_mechanics.crushing.text6", "The tier of the Crushing Head affects the drops much like the Hammers.");

        add("rankine.journal.cat_mechanics.building_modes.name", "Building Modes");
        add("rankine.journal.cat_mechanics.building_modes.text1", "Some blocks have multiple model/texture variants but are not necessarily separate blocks. These variations are called Building Modes. Shift right click with a stack of blocks in your hand to change the variant. A message will appear above the hotbar to indicate the current mode.");
        add("rankine.journal.cat_mechanics.building_modes.text2", "Blocks that have modes include: $(li)Planks $(li)Stone Bricks $(li)Polished Stones $(li)Bricks $(li)Glazed Porcelains $(li)Metal Ladders");



        add("rankine.journal.cat_mechanics.finite_water.name", "Water Table");
        add("rankine.journal.cat_mechanics.finite_water.text1", "A config enabled by default, which limits infinite water sources to below the Water Table height. ");
        add("rankine.journal.cat_mechanics.finite_water.text2", "Right click the ground to display the Water Table height at that coordinate.");
        add("rankine.journal.cat_mechanics.finite_water.text3", "Moving Water");
        add("rankine.journal.cat_mechanics.finite_water.text4", "A passive method to create water sources above the Water Table ca be constructed. The Ground Tap will generate a source block of water every 30 seconds (configurable) when connected to a waterlogged Flood Gate and connected through Metal Pipes. The Flood Gate can become waterlogged by filling it manually with water or by placing it in an infinite water source. Multiple taps can be on the same pipe line.");
        add("rankine.journal.cat_mechanics.finite_water.text5", "The Flood Gate can transfer fluids from above it and fill the space below it. It must not be waterlogged to do this. Useful for filling in lakes or the Evaporation Tower.");


        add("rankine.journal.cat_mechanics.sluicing.name", "Sluicing");
        add("rankine.journal.cat_mechanics.sluicing.text1", "Sluicing is a general term for the act of sifting through material. Right click a block with the appropriate sluicing tool to receive an item.");
        add("rankine.journal.cat_mechanics.sluicing.text2", "Gold Pans");
        add("rankine.journal.cat_mechanics.sluicing.text3", "Other items can be used to perform the sluicing action. The required item is shown in JEI.");

        //Mineralogy
        add("rankine.journal.cat_mineralogy.name", "Mineralogy");
        add("rankine.journal.cat_mineralogy.desc", "Deposits of valuable materials are scattered throughout the surface and layers of the underground. Mineralogy encompasses the ores and other non-earthy components of the ground.");

        add("rankine.journal.cat_mineralogy.meteorites.name", "Meteorites");
        add("rankine.journal.cat_mineralogy.meteorites.text1", "Meteorites are formations of rock originating from another world/space. They are formed of compounds similar to terestrial stones, but also are concentrated sources of unique minearls.");
        add("rankine.journal.cat_mineralogy.meteorites.text2", "Overworld Meteorite");
        add("rankine.journal.cat_mineralogy.meteorites.meteorites_header", "Meteorite Blocks");
        add("rankine.journal.cat_mineralogy.meteorites.meteorites", "Core silica based blocks making up most of the meteorite.");
        add("rankine.journal.cat_mineralogy.meteorites.tektites_header", "Tektites");
        add("rankine.journal.cat_mineralogy.meteorites.tektites", "Tektite is a form of glass formed by meteorite impact with the surface.");
        add("rankine.journal.cat_mineralogy.meteorites.ores_header", "Meteorite Ores");
        add("rankine.journal.cat_mineralogy.meteorites.ores", "Meteoric ores have been found as a native source of Invar and accessible source of iron. Crushing the ores will yield meteoric iron chunks which can be used to make crude tools.");
        add("rankine.journal.cat_mineralogy.meteorites.meteoric_ice", "A rare form of ice only found in frozen meteorites. Like normal ice blocks, it is slippery and requires silk touch to harvest.");

        add("rankine.journal.cat_mineralogy.mineral_stones.name", "Mineral Stones");
        add("rankine.journal.cat_mineralogy.mineral_stones.text1","Some stones are more concentrated in specific minerals than the surrounding parent stone. The crystal formation is not as complete as a standard ore, and such these deposits are referred to as mineral stones.");
        add("rankine.journal.cat_mineralogy.mineral_stones.phosphorite","A major source of potassium. Found in ocean, beach, desert and mountain biomes.");
        add("rankine.journal.cat_mineralogy.mineral_stones.sylvinite","A major source of salt. Found in ocean, beach and desert biomes.");
        add("rankine.journal.cat_mineralogy.mineral_stones.banded_iron_formation","A source of iron. Found scarcely thorughout Overworld biomes.");
        add("rankine.journal.cat_mineralogy.mineral_stones.ironstone","A source of iron. Found in savanna, mesa and desert biomes.");
        add("rankine.journal.cat_mineralogy.mineral_stones.bog_iron","A source of iron. Found in swamp and jungle biomes.");

        add("rankine.journal.cat_mineralogy.vitrified.name", "Vitrified Blocks");
        add("rankine.journal.cat_mineralogy.vitrified.text1","When lightning strikes the ground, it releases large amounts of energy very quickly. This energy can change certain blocks, such as oxidized coppers back into normal. It also can convert some natural blocks.");
        add("rankine.journal.cat_mineralogy.vitrified.fulgurite","Made from earthy blocks like stones, grasses and soils.");
        add("rankine.journal.cat_mineralogy.vitrified.lightning_glass","Mad from sand, silt and desert sand.");
        add("rankine.journal.cat_mineralogy.vitrified.red_lightning_glass","Made from red sand.");
        add("rankine.journal.cat_mineralogy.vitrified.soul_lightning_glass","Made from soul sand.");
        add("rankine.journal.cat_mineralogy.vitrified.black_lightning_glass","Made from black sand.");
        add("rankine.journal.cat_mineralogy.vitrified.white_lightning_glass","Made from white sand.");

        add("rankine.journal.cat_mineralogy.fumaroles.name", "Fumaroles");
        add("rankine.journal.cat_mineralogy.fumaroles.text1", "Fumaroles are natural vents for gasses trapped inside the crust. They continuously output gas blocks into nearby air space.");
        add("rankine.journal.cat_mineralogy.fumaroles.text2", "Fumarole Types");
        add("rankine.journal.cat_mineralogy.fumaroles.text3", "Generate low in the Overworld and Nether, primarily in cave openings.");
        add("rankine.journal.cat_mineralogy.fumaroles.text4", "A mineral rich stone derived from the outgassing of deep-world materials. Important source of Vanadium Pentoxide and minerals for Beryllium production.");
        add("rankine.journal.cat_mineralogy.fumaroles.text5", "A crystallized form of ammonia compounds. Used for ammonia production and complex metal reactions. Crystals form on the underside of fumarole deposit blocks that have water above them.");

        add("rankine.journal.cat_mineralogy.gem_ores.name", "Gem Ores");
        add("rankine.journal.cat_mineralogy.gem_ores.text1", "Gem ores refer to stones that directly drop their respective item when mined. Most can also be crushed for additional resources.");
        add("rankine.journal.cat_mineralogy.gem_ores.lignite_ore", "Source of fuel. $(br2)Found near the surface in all regions of the world.");
        add("rankine.journal.cat_mineralogy.gem_ores.subbituminous_ore", "Source of fuel. $(br2)Found underground in all regions of the Overworld.");
        add("rankine.journal.cat_mineralogy.gem_ores.bituminous_ore", "Source of fuel. $(br2)Found in the depths of the Overworld.");
        add("rankine.journal.cat_mineralogy.gem_ores.anthracite_ore", "Source of fuel. $(br2)Found scattered across the Nether.");
        add("rankine.journal.cat_mineralogy.gem_ores.plumbago_ore", "Source of Graphite. $(br2)Found in the depths of the Overworld.");
        add("rankine.journal.cat_mineralogy.gem_ores.lazurite_ore", "Source of Lapis. $(br2)Found in the depths of the Overworld.");
        add("rankine.journal.cat_mineralogy.gem_ores.cinnabar_ore", "Source of Redstone and Mercury. $(br2)Found in all types of intrusions");
        add("rankine.journal.cat_mineralogy.gem_ores.beryl_ore", "Source of Emeralds. $(br2)Found in some intrusions");
        add("rankine.journal.cat_mineralogy.gem_ores.kimberlitic_diamond_ore", "Source of Diamonds. $(br2)Found solely in kimberlite intrusions.");

        add("rankine.journal.cat_mineralogy.mineral_ores.name", "Mineral Ores");
        add("rankine.journal.cat_mineralogy.mineral_ores.text1", "Mineral ores are rocks containing more complex compounds that may not be directly usable. They are often of higher harvest level, drop their raw mineral when mined and additional resources when crushed.");
        add("rankine.journal.cat_mineralogy.mineral_ores.acanthite_ore", "Source of Silver. $(br2)Found in the depths of the Overworld.");
        add("rankine.journal.cat_mineralogy.mineral_ores.baddeleyite_ore", "Source of Zirconium. $(br2)Found in diabase intrusions");
        add("rankine.journal.cat_mineralogy.mineral_ores.bauxite_ore", "Source of Aluminum. $(br2)Found in the surface of Overworld biomes except for Beaches andOceans.");
        add("rankine.journal.cat_mineralogy.mineral_ores.bismuthinite_ore", "Source of Bismuth. $(br2)Found in the depths of the Overworld.");
        add("rankine.journal.cat_mineralogy.mineral_ores.cassiterite_ore", "Source of Tin. $(br2)Found in some intrusions");
        add("rankine.journal.cat_mineralogy.mineral_ores.celestine_ore", "Source of Strontium. $(br2)Found in the depths of Jungle, Swamp, Plains, Forest and Taiga biomes.");
        add("rankine.journal.cat_mineralogy.mineral_ores.chalcocite_ore", "Source of Copper. $(br2)Found scattered across the Overworld.");
        add("rankine.journal.cat_mineralogy.mineral_ores.chromite_ore", "Source of Chromium. $(br2)Found in the depths of the Overworld.");
        add("rankine.journal.cat_mineralogy.mineral_ores.cobaltite_ore", "Source of Cobalt. $(br2)Found scattered across the Nether.");
        add("rankine.journal.cat_mineralogy.mineral_ores.coltan_ore", "Source of Niobium and Tantalum. $(br2)Found scattered across the Nether.");
        add("rankine.journal.cat_mineralogy.mineral_ores.cryolite_ore", "Source of Sodium. $(br2)Found in the depths of Taiga, Mountain and Icy biomes.");
        add("rankine.journal.cat_mineralogy.mineral_ores.galena_ore", "Source of Lead. $(br2)Found in the depths of the Overworld.");
        add("rankine.journal.cat_mineralogy.mineral_ores.greenockite_ore", "Source of Cadmium. $(br2)Found scattered across the End.");
        add("rankine.journal.cat_mineralogy.mineral_ores.interspinifex_ore", "Source of Copper an Nickel. $(br2)Found in komatiite intrusions");
        add("rankine.journal.cat_mineralogy.mineral_ores.ilmenite_ore", "Source of Titanium. $(br2)Found scattered across the Nether.");
        add("rankine.journal.cat_mineralogy.mineral_ores.magnesite_ore", "Source of Magnesium. $(br2)Found in the depths of the Overworld.");
        add("rankine.journal.cat_mineralogy.mineral_ores.magnetite_ore", "Source of Iron. $(br2)Found in some intrusions");
        add("rankine.journal.cat_mineralogy.mineral_ores.hematite_ore", "Source of Iron. $(br2)Found scattered across the Overworld.");
        add("rankine.journal.cat_mineralogy.mineral_ores.malachite_ore", "Source of Copper. $(br2)Found in some intrusions");
        add("rankine.journal.cat_mineralogy.mineral_ores.molybdenite_ore", "Source of Molybdenum. $(br2)Found scattered across the End.");
        add("rankine.journal.cat_mineralogy.mineral_ores.monazite_ore", "Source of rare elements. $(br2)Found scattered across the Nether.");
        add("rankine.journal.cat_mineralogy.mineral_ores.pentlandite_ore", "Source of Nickel. $(br2)Found in the depths of Ocean, Beach, Desert and Mesa biomes.");
        add("rankine.journal.cat_mineralogy.mineral_ores.petalite_ore", "Source of Lithium. $(br2)Found in pegmatite intrusions");
        add("rankine.journal.cat_mineralogy.mineral_ores.pyrolusite_ore", "Source of Manganese. $(br2)Found in the depths of the Overworld.");
        add("rankine.journal.cat_mineralogy.mineral_ores.pyrite_ore", "Fool's gold. Source of iron. $(br2)Found scattered across the Overworld.");
        add("rankine.journal.cat_mineralogy.mineral_ores.rheniite_ore", "Source of Rhenium. $(br2)Found scattered across the Nether.");
        add("rankine.journal.cat_mineralogy.mineral_ores.sperrylite_ore", "Source of Osmium and Iridium. $(br2)Found scattered across the Nether.");
        add("rankine.journal.cat_mineralogy.mineral_ores.sphalerite_ore", "Source of Zinc. $(br2)Found in the depths of Plains, Savannah, Desert and Mesa biomes.");
        add("rankine.journal.cat_mineralogy.mineral_ores.uraninite_ore", "Source of Uranium. $(br2)Found scattered across the End.");
        add("rankine.journal.cat_mineralogy.mineral_ores.wolframite_ore", "Source of Tungsten. $(br2)Found scattered across the Nether.");
        add("rankine.journal.cat_mineralogy.mineral_ores.xenotime_ore", "Source of Xenotime. $(br2)Found scattered across the End.");

        add("rankine.journal.cat_mineralogy.native_ores.name", "Native Ores");
        add("rankine.journal.cat_mineralogy.native_ores.text1", "Native is a term used to describe elements that exist in their pure form in nature. The following blocks can be mined at a low harvest level to obtain pure metal samples.");
        add("rankine.journal.cat_mineralogy.native_ores.native_tin_ore", "Source of Tin. $(br2)Found near the surface in all regions of the Overworld.");
        add("rankine.journal.cat_mineralogy.native_ores.native_lead_ore", "Source of Lead. $(br2)Found near the surface in all regions of the Overworld.");
        add("rankine.journal.cat_mineralogy.native_ores.native_bismuth_ore", "Source of Bismuth. $(br2)Found near the surface in all regions of the Overworld.");
        add("rankine.journal.cat_mineralogy.native_ores.native_silver_ore", "Source of Silver. $(br2)Found near the surface in all regions of the Overworld.");
        add("rankine.journal.cat_mineralogy.native_ores.native_gold_ore", "Source of Gold. $(br2)Found scattered across the Overworld.");
        add("rankine.journal.cat_mineralogy.native_ores.stibnite_ore", "Source of Antimony. $(br2)Found near the surface in all regions of the Overworld.");
        add("rankine.journal.cat_mineralogy.native_ores.porphyry_copper", "Source of Copper. $(br2)Found in porphyry intrusions.");
        add("rankine.journal.cat_mineralogy.native_ores.native_sulfur_ore", "Source of Sulfur. $(br2)Found scattered across the Nether.");
        add("rankine.journal.cat_mineralogy.native_ores.native_arsenic_ore", "Source of Arsenic. $(br2)Found scattered across the Nether.");
        add("rankine.journal.cat_mineralogy.native_ores.native_indium_ore", "Source of Indium. $(br2)Found scattered across the End.");
        add("rankine.journal.cat_mineralogy.native_ores.native_gallium_ore", "Source of Gallium. $(br2)Found scattered across the End.");
        add("rankine.journal.cat_mineralogy.native_ores.native_tellurium_ore", "Source of Tellurium. $(br2)Found scattered across the End.");
        add("rankine.journal.cat_mineralogy.native_ores.native_selenium_ore", "Source of Selenium. $(br2)Found scattered across the End.");


        //Sediments
        add("rankine.journal.cat_sediments.name", "Aggregates");
        add("rankine.journal.cat_sediments.desc", "Aggregate is a general term for the physical foundations of the world. They come in varieties of particle sizes, compositions, and formations.");

        add("rankine.journal.cat_sediments.otherworldly.name", "Otherworldly Stones");
        add("rankine.journal.cat_sediments.otherworldly.text1", "Some stones form under unique environmental conditions. They resemble igneous stones but this class of stone is simply named Otherworldly, as they naturally occur in atypical locations.");
        add("rankine.journal.cat_sediments.otherworldly.end_stone", "An intrusive stone found in The End.");
        add("rankine.journal.cat_sediments.otherworldly.sommanite", "An intrusive stone found deep in taiga biomes.");
        add("rankine.journal.cat_sediments.otherworldly.ringwoodine", "An intrusive stone found deep in mountain biomes.");
        add("rankine.journal.cat_sediments.otherworldly.wadsleyone", "An intrusive stone found deep in mushroom, beach and ocean biomes.");
        add("rankine.journal.cat_sediments.otherworldly.bridgmanham", "An intrusive stone found deep in savanna, mesa, desert and plains biomes.");
        add("rankine.journal.cat_sediments.otherworldly.post_perovskite", "An intrusive stone found deep in river and swamp biomes.");
        add("rankine.journal.cat_sediments.otherworldly.whiteschist", "Found deep in icy biomes");

        add("rankine.journal.cat_sediments.volcanic.name", "Volcanic Stones");
        add("rankine.journal.cat_sediments.volcanic.text1", "Volcanic stones are formed from quickly cooling lava, often during eruption events. ");
        add("rankine.journal.cat_sediments.volcanic.text2", "Generation");
        add("rankine.journal.cat_sediments.volcanic.text3", "Volcanic stones are found in lava rich locations. They can be generated using the vanilla obsidian generator, where water flows onto lava source blocks. A block adjacent to the generation site determines the generated stone. See JEI for generator recipes.");
        add("rankine.journal.cat_sediments.volcanic.obsidian", "Found deep in the overworld.");
        add("rankine.journal.cat_sediments.volcanic.crying_obsidian", "Found as usual.");
        add("rankine.journal.cat_sediments.volcanic.snowflake_obsidian", "Found deep in the overworld.");
        add("rankine.journal.cat_sediments.volcanic.blood_obsidian", "Found deep in the overworld.");
        add("rankine.journal.cat_sediments.volcanic.pumice", "Found as intrusions in the Nether.");
        add("rankine.journal.cat_sediments.volcanic.scoria", "Found as intrusions in the Nether.");
        add("rankine.journal.cat_sediments.volcanic.andesitic_tuff", "Found in biomes containing hornblende andesite.");
        add("rankine.journal.cat_sediments.volcanic.basaltic_tuff", "Found in biomes containing tholeiitic basalt.");
        add("rankine.journal.cat_sediments.volcanic.rhyolitic_tuff", "Found in biomes containing rhyolite.");
        add("rankine.journal.cat_sediments.volcanic.kimberlitic_tuff", "Found deep in the overworld.");
        add("rankine.journal.cat_sediments.volcanic.komatiitic_tuff", "Found in biomes containing komatiite.");

        add("rankine.journal.cat_sediments.sedimentary.name", "Sedimentary Stones");
        add("rankine.journal.cat_sediments.sedimentary.text1", "Sedimentary stones are formed from compactions and cementing of sediments.");
        add("rankine.journal.cat_sediments.sedimentary.text2", "Generation");
        add("rankine.journal.cat_sediments.sedimentary.text3", "Sediment blocks that despawn in water will sink to the bottom and become their stone counterpart. A more automatic method of generation involves the Sediment Fan.. See JEI for generator recipes.");
        add("rankine.journal.cat_sediments.sedimentary.text4", "The fan encourages particles of adjacent blocks to be pushed into water streams, where they will be compacted and converted into stones. Place a sediment block adjacent to the fan and a water source one block further out in a line. The stone will be generated on the third block out if it is also water.");
        add("rankine.journal.cat_sediments.sedimentary.sandstone", "Found in river, ocean and beach biomes");
        add("rankine.journal.cat_sediments.sedimentary.red_sandstone", "Not found in nature.");
        add("rankine.journal.cat_sediments.sedimentary.soul_sandstone", "Found in soul sand valley biomes");
        add("rankine.journal.cat_sediments.sedimentary.desert_sandstone", "Found in desert biomes");
        add("rankine.journal.cat_sediments.sedimentary.white_sandstone", "Found scarcely in sandy areas.");
        add("rankine.journal.cat_sediments.sedimentary.black_sandstone", "Not found in nature.");
        add("rankine.journal.cat_sediments.sedimentary.limestone", "Found in forest and plains biomes");
        add("rankine.journal.cat_sediments.sedimentary.dolostone", "Found in mountain and taiga biomes");
        add("rankine.journal.cat_sediments.sedimentary.chalk", "Found in icy, river and beach biomes");
        add("rankine.journal.cat_sediments.sedimentary.marlstone", "Found in mushroom and beach biomes");
        add("rankine.journal.cat_sediments.sedimentary.shale", "Found in river and beach biomes");
        add("rankine.journal.cat_sediments.sedimentary.mudstone", "Found in jungle and swamp biomes");
        add("rankine.journal.cat_sediments.sedimentary.siltstone", "Found in savanna, mesa, desert and plains biomes.");
        add("rankine.journal.cat_sediments.sedimentary.itacolumite", "Found in desert biomes.");
        add("rankine.journal.cat_sediments.sedimentary.arkose", "Found in mesa biomes.");
        add("rankine.journal.cat_sediments.sedimentary.graywacke", "Found in savanna biomes.");
        add("rankine.journal.cat_sediments.sedimentary.honeystone", "Found in soul sand valley biomes");

        add("rankine.journal.cat_sediments.metamorphic.name", "Metamorphic Stones");
        add("rankine.journal.cat_sediments.metamorphic.text1", "Metamorphic stones are formed by the conversion of existing stone under heat and pressure.");
        add("rankine.journal.cat_sediments.metamorphic.text2", "Generation");
        add("rankine.journal.cat_sediments.metamorphic.text3", "Metamorphic stones are found at all sites. They can be generated using the vanilla stone generator, where lava falls onto water source blocks.  A block adjacent to the generation site determines the generated block. See JEI for generator recipes.");
        add("rankine.journal.cat_sediments.metamorphic.deepslate", "Found deep in forest and jungle biomes");
        add("rankine.journal.cat_sediments.metamorphic.black_marble", "Found in forest biomes");
        add("rankine.journal.cat_sediments.metamorphic.gray_marble", "Found in plains and mountain biomes");
        add("rankine.journal.cat_sediments.metamorphic.white_marble", "Found in icy and taiga biomes");
        add("rankine.journal.cat_sediments.metamorphic.rose_marble", "Found in savanna and desert biomes");
        add("rankine.journal.cat_sediments.metamorphic.slate", "Found in swamp and jungle biomes");
        add("rankine.journal.cat_sediments.metamorphic.phyllite", "Found in icy, swamp and jungle biomes");
        add("rankine.journal.cat_sediments.metamorphic.mica_schist", "Found in icy, swamp and jungle biomes");
        add("rankine.journal.cat_sediments.metamorphic.blueschist", "Found in soul_sand_valley biomes");
        add("rankine.journal.cat_sediments.metamorphic.greenschist", "Found in mountain biomes");
        add("rankine.journal.cat_sediments.metamorphic.gneiss", "Found deep in jungle, swamp and mountain biomes");
        add("rankine.journal.cat_sediments.metamorphic.quartzite", "Found deep in mesa, savanna and desert biomes");
        add("rankine.journal.cat_sediments.metamorphic.soapstone", "Found deep in forest and taiga biomes");
        add("rankine.journal.cat_sediments.metamorphic.serpentinite", "Found in mountain biomes");
        add("rankine.journal.cat_sediments.metamorphic.mariposite", "Found in mountain biomes");
        add("rankine.journal.cat_sediments.metamorphic.eclogite", "Found in swamp and jungle biomes");

        add("rankine.journal.cat_sediments.igneous.name", "Igneous Stones");
        add("rankine.journal.cat_sediments.igneous.text1", "Igneous stones are formed from the cooling of magma/lava. The molten composition and the method in which they cool determine the rock texture and composition.");
        add("rankine.journal.cat_sediments.igneous.text2", "Intrusive Generation");
        add("rankine.journal.cat_sediments.igneous.text3", "Intrusive igneous stones are generally formed as underground structures such as intrusions or veins. Their magma cools slowly which allows large mineral crystals to form and often have a rough texture. They can also be generated in the standard cobblestone generator where lava meets flowing water. Two mineral blocks can be placed adjacent to the generation site to determine the generated stone. See JEI for generator recipes.");
        add("rankine.journal.cat_sediments.igneous.text4", "Extrusive Generation");
        add("rankine.journal.cat_sediments.igneous.text5", "Extrusive igneous stones are generally formed as large layers. Their lava cools rapidly which prevents large crystals from forming and often have a smoother texture. They can also be generated in the standard basalt generator where lava meets blue ice over soul soil. A block can be placed adjacent to the generation site to determine the generated stone.");
        add("rankine.journal.cat_sediments.igneous.stone", "Stone in this world is generally found solely as an intrusion. Depending on the biome, it will contain a variety of the vanilla ores.");
        add("rankine.journal.cat_sediments.igneous.granite", "An intrusive stone found in plains and forest biomes. Associated ores include Malachite, and Cassiterite.");
        add("rankine.journal.cat_sediments.igneous.diorite", "An intrusive stone found in mountain, icy, and taiga biomes. Associated ores include Plumbago, Magnetite, and Ilmenite.");
        add("rankine.journal.cat_sediments.igneous.andesite", "An extrusive stone found in icy biomes.");
        add("rankine.journal.cat_sediments.igneous.basalt", "An extrusive stone found in basalt deltas biomes.");
        add("rankine.journal.cat_sediments.igneous.netherrack", "An extrusive stone found in nether waste biomes.");
        add("rankine.journal.cat_sediments.igneous.pegmatite", "An intrusive stone found in many parts of the Overworld. Associated ores include Beryl, Petalite, Baddeleyite, Coltan, and Uraninite.");
        add("rankine.journal.cat_sediments.igneous.gray_granite", "An intrusive stone found in mountain, swamp, taiga, and icy biomes. Associated ores include Malachite, and Cassiterite.");
        add("rankine.journal.cat_sediments.igneous.rhyolite", "An extrusive stone found in savanna, mesa, desert and plains biomes.");
        add("rankine.journal.cat_sediments.igneous.comendite", "An extrusive stone found in icy, forest and taiga biomes.");
        add("rankine.journal.cat_sediments.igneous.granodiorite", "An intrusive stone found in savanna, forest and plains biomes. Associated ores include Magnetite, and Wolframite.");
        add("rankine.journal.cat_sediments.igneous.red_porphyry", "An intrusive stone found in desert, mesa, and ocean biomes. Associated ores include Porphyry Copper, Gold, and Molybdenite.");
        add("rankine.journal.cat_sediments.igneous.purple_porphyry", "An extrusive stone found in crimson forest biomes.");
        add("rankine.journal.cat_sediments.igneous.black_dacite", "An extrusive stone found in forest and taiga biomes.");
        add("rankine.journal.cat_sediments.igneous.red_dacite", "An extrusive stone found in savanna, mesa, desert and plains biomes.");
        add("rankine.journal.cat_sediments.igneous.hornblende_andesite", "An extrusive stone found in mountain biomes.");
        add("rankine.journal.cat_sediments.igneous.shonkinite", "An intrusive stone found in mountain and swamp biomes. Associated ores include Beryl, Plumbago, and Magnetite.");
        add("rankine.journal.cat_sediments.igneous.anorthosite", "An extrusive stone found in mountain, river, forest, taiga and plains biomes.");
        add("rankine.journal.cat_sediments.igneous.norite", "An intrusive stone found in savanna and jungle biomes. Associated ores include Magnetite and Chromite.");
        add("rankine.journal.cat_sediments.igneous.troctolite", "An extrusive stone found in mushroom, river, beach and ocean biomes.");
        add("rankine.journal.cat_sediments.igneous.gabbro", "An extrusive stone found in mushroom, river, beach and ocean biomes.");
        add("rankine.journal.cat_sediments.igneous.diabase", "An intrusive stone found in ocean and beach biomes. Associated ores include Baddeleyite.");
        add("rankine.journal.cat_sediments.igneous.tholeiitic_basalt", "An extrusive stone found in mushroom, river, beach and ocean biomes.");
        add("rankine.journal.cat_sediments.igneous.pyroxenite", "An intrusive stone found in warped forest biomes.");
        add("rankine.journal.cat_sediments.igneous.dunite", "An intrusive stone found in basalt deltas biomes.");
        add("rankine.journal.cat_sediments.igneous.harzburgite", "An intrusive stone found in warped forest biomes.");
        add("rankine.journal.cat_sediments.igneous.lherzolite", "An intrusive stone found in warped forest biomes.");
        add("rankine.journal.cat_sediments.igneous.wehrlite", "An intrusive stone found in soul sand valley biomes.");
        add("rankine.journal.cat_sediments.igneous.komatiite", "An extrusive stone found in crimson forest biomes.");
        add("rankine.journal.cat_sediments.igneous.kimberite", "An intrusive stone found in many parts of the Overworld. Associated ores include Kimberlitic Diamond.");

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
        add("rankine.journal.cat_sediments.clays.text1", "Clay minerals are very small sediments. They are one of the foundations of soil and are used in many construction applications.");
        add("rankine.journal.cat_sediments.clays.clay", "Found in the usual locations. Mainly in River and Swamp beds and in Lush Caves.");
        add("rankine.journal.cat_sediments.clays.kaolinite", "A near white clay. Obtained from crushing Kaolin and a few other sources.");
        add("rankine.journal.cat_sediments.clays.halloysite", "Identical composition to Kaolinite, but with a different structure that makes it appear yellowish. Obtained from crushing Kaolin and a few other sources.");
        add("rankine.journal.cat_sediments.clays.fire_clay", "A special type of clay with high fusion temperature. Used for refractory applications. Naturally occurs in association with coal veins. Can be crafted in the Mixing Barrel.");
        add("rankine.journal.cat_sediments.clays.porcelain_clay", "A manufactured white clay mixture. Used to create very white porcelain blocks. Crafted in the Mixing Barrel.");

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
        add("rankine.journal.cat_tools.totems.totem_of_blazing", "When on fire, deal 1.5x damage and lay fire at your feet. When health is less than 25% or you fall in lava, this totem disappears and provides Fire Resistance and a small amount of Regeneration.");
        add("rankine.journal.cat_tools.totems.totem_of_cobbling", "Internally stores all types of stones up to 8 stacks. Can be used to place cobblestone.");
        add("rankine.journal.cat_tools.totems.totem_of_enduring", "Increases the wearer's health capacity. Can be used every so often to apply regeneration.");
        add("rankine.journal.cat_tools.totems.totem_of_hastening", "Applies efficiency to your arms, allowing you to mine all blocks quicker.");
        add("rankine.journal.cat_tools.totems.totem_of_infusing", "Stores a potion effect and applies it to entities that you hit.");
        add("rankine.journal.cat_tools.totems.totem_of_levitating", "While not sneaking, the wearer will not be able to fall.");
        add("rankine.journal.cat_tools.totems.totem_of_promising", "Mining brings you fortune for many materials. There is a chance to receive an extra block.");
        add("rankine.journal.cat_tools.totems.totem_of_repulsing", "Hostile mobs can no longer detect the wearer.");
        add("rankine.journal.cat_tools.totems.totem_of_softening", "The delicacy of an open hand allows you to collect blocks that naturally require silk touch.");
        add("rankine.journal.cat_tools.totems.totem_of_timesaving", "Provides additional movement speed across all solid terrain.");
        add("rankine.journal.cat_tools.totems.totem_of_powering", "Provides bonuses when affected by conduit power. Applies the Dolphin's Grace effect and slowly recharges any batteries in the inventory.");

        add("rankine.journal.cat_tools.common_tools.name", "Other Tools");
        add("rankine.journal.cat_tools.common_tools.text1", "");
        add("rankine.journal.cat_tools.common_tools.glass_cutter", "A tool effective for breaking glass materials. Comes with silk touch by default. Right click glass blocks to convert them into glass panes.");
        add("rankine.journal.cat_tools.common_tools.rock_drill", "Right click the ground to receive a sample report of all the stones located below.");
        add("rankine.journal.cat_tools.common_tools.fire_extinguisher", "Right click to extinguish fires in a radius in front of you.");

        add("rankine.journal.cat_tools.armor.name", "Armor");
        add("rankine.journal.cat_tools.armor.text1", "Various armor sets exist in the game.");
        add("rankine.journal.cat_tools.armor.brigandine_title", "Brigandine Armor");
        add("rankine.journal.cat_tools.armor.brigandine", "Steel plated leather armor. Made by combining leather armor and a steel ingot in the smithing table.");
        add("rankine.journal.cat_tools.armor.diving_title", "Diving Armor");
        add("rankine.journal.cat_tools.armor.diving", "Armor made from brass. Allows the wearer to hold their breath for a longer period of time.");
        add("rankine.journal.cat_tools.armor.conduit_diving_title", "Conduit Diving Armor");
        add("rankine.journal.cat_tools.armor.conduit_diving", "Upgraded diving armor. Provides the wearer with conduit power when in water.");
        add("rankine.journal.cat_tools.armor.alloy_title", "Alloy Armor");
        add("rankine.journal.cat_tools.armor.alloy", "Like tools, alloy armor gets it's stats (armor, toughness, durability) depending on the composition of the alloy. Recipes for alloy armor can be added external using datapacks or mods. By default there are a few different recipes.");

        add("rankine.journal.cat_tools.arrows.name", "Arrows");
        add("rankine.journal.cat_tools.arrows.text1", "Alloy arrows gets their stats (damage) depending on the composition of the alloy. Recipes for alloy armor can be added external using datapacks or mods.");
        add("rankine.journal.cat_tools.arrows.steel_arrow", "An upgraded standard arrow. Deals more damage.\"");
        add("rankine.journal.cat_tools.arrows.thorium_arrow", "Summons lightning on impact.");
        add("rankine.journal.cat_tools.arrows.magnesium_arrow", "Blinds nearby enemies when on ground. Hit mobs will trigger nearby undead mobs to attack. Creepers explode on impact.");

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
        add("rankine.journal.cat_tools.equipment.text5", "Fins are worn on the feet and provide increased movemet speed in water. Combine boots of any type with fins in an anvil to apply the $(l:mechanics/enchantments#swift_swimmer)Swift Swimmer$() enchantment.");
        add("rankine.journal.cat_tools.equipment.text6", "Goggles are worn on the head and provide better vision and movemet speed in water. Combine a helmet of any type with goggles in an anvil to apply the $(l:mechanics/enchantments#aqua_lense)Aqua Lense$() enchantment.");
        add("rankine.journal.cat_tools.equipment.text7", "A Gas Mask are worn on the head and protects against suffocation in gas blocks. Combine helmet of any type with a gas mask in an anvil to apply the $(l:mechanics/enchantments#gas_protection)Gas Protection$() enchantment.");

        add("rankine.journal.cat_tools.element_indexer.name", "Element Indexer");
        add("rankine.journal.cat_tools.element_indexer.text1", "Upon inspection most materials used in the alloying process have some inherent properties to them. For the end goal of making ingots, this doesn't matter much, but for tools there can be profound effects.");
        add("rankine.journal.cat_tools.element_indexer.text2", "To see the stats of an element, right click the element indexer and place the ingot in the top left slot. The stats will show below for each of the various categories. Additionally, when in the GUI, scrolling will change the percentage of the material you are looking at. See $(l:alloying/alloying)Notes About Alloying$() for details.");

        add("rankine.journal.cat_tools.utility_tools.name", "Utility Tools");
        add("rankine.journal.cat_tools.utility_tools.text1", "Various utility tools have been added to display information. Tools will display on the status bar while in your main hand or off hand. Default is head position, sneak for position at feet.");
        add("rankine.journal.cat_tools.utility_tools.altimeter", "Displays the current Y value.");
        add("rankine.journal.cat_tools.utility_tools.photometer", "Displays the current light level.");
        add("rankine.journal.cat_tools.utility_tools.compass", "Displays the current X and Z coordinates.");
        add("rankine.journal.cat_tools.utility_tools.speedometer", "Displays the current speed in blocks per second.");
        add("rankine.journal.cat_tools.utility_tools.thermometer", "Displays the current air temperature.");
        add("rankine.journal.cat_tools.utility_tools.biometer", "Displays the current biome.");
        add("rankine.journal.cat_tools.utility_tools.magnetometer", "Displays the current magnetic field strength. Field strength is dependent on nearby blocks.");

        add("rankine.journal.cat_tools.magnets.name", "Magnets");
        add("rankine.journal.cat_tools.magnets.text1", "Magnets can pull in surrounding items when right-clicked in hand. Config options exist for magnet ranges.");
        add("rankine.journal.cat_tools.magnets.lodestone", "A weakly powered natural magnet. Lodestones will actively pull in items with a 2 block range. Obtained by crushing $(world/ores#magnetite_ore)Magnetite Ore$().");
        add("rankine.journal.cat_tools.magnets.simple_magnet", "Default Range: 4 blocks");
        add("rankine.journal.cat_tools.magnets.alnico_magnet", "Default Range: 8 blocks");
        add("rankine.journal.cat_tools.magnets.rare_earth_magnet", "Default Range: 12 blocks");

        add("rankine.journal.cat_tools.standard_tools.name", "Standard Tools");
        add("rankine.journal.cat_tools.standard_tools.text1", "");
        add("rankine.journal.cat_tools.standard_tools.text2", "Swords");
        add("rankine.journal.cat_tools.standard_tools.text3", "A familiar weapon used to damage mobs.");
        add("rankine.journal.cat_tools.standard_tools.text4", "Shovels");
        add("rankine.journal.cat_tools.standard_tools.text5", "A familiar tool used to break earthy blocks.");
        add("rankine.journal.cat_tools.standard_tools.text6", "Pixaxes");
        add("rankine.journal.cat_tools.standard_tools.text7", "A familiar tool used to break rocky blocks.");
        add("rankine.journal.cat_tools.standard_tools.text8", "Axes");
        add("rankine.journal.cat_tools.standard_tools.text9", "A familiar tool used to break wooden blocks. Additional items can be obtained from stripping logs. $(br2)Alloy axes have the bonus capability of chopping down entire trees.");
        add("rankine.journal.cat_tools.standard_tools.text10", "Hoes");
        add("rankine.journal.cat_tools.standard_tools.text11", "A familiar tool used to create farmland and break certain blocks. $(br2)Alloy hoes have the bonus capability of replanting crops when right clicked on them.");
        add("rankine.journal.cat_tools.standard_tools.text12", "Hammers");
        add("rankine.journal.cat_tools.standard_tools.text13", "A new tool for $(l:mechanics/crushing)crushing$() blocks. Left click a block when the attack indicator is off cooldown. Also functions as an effective weapon.");
        add("rankine.journal.cat_tools.standard_tools.text14", "Knives");
        add("rankine.journal.cat_tools.standard_tools.text15", "");
        add("rankine.journal.cat_tools.standard_tools.text16", "Crowbars");
        add("rankine.journal.cat_tools.standard_tools.text17", "");
        add("rankine.journal.cat_tools.standard_tools.text18", "Spears");
        add("rankine.journal.cat_tools.standard_tools.text19", "A new weapon similar to the trident.");
        add("rankine.journal.cat_tools.standard_tools.text20", "Surf Rods");
        add("rankine.journal.cat_tools.standard_tools.text21", "");

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
