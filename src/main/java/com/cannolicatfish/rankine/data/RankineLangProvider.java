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

                RankineLists.CONCRETE_VERTICAL_SLABS,
                RankineLists.CONCRETE_STAIRS,
                RankineLists.CONCRETE_WALLS,
                RankineLists.SHEETMETAL_SLABS,
                //RankineLists.GAS_TUBES,
                RankineLists.STONE_COLUMNS,
                RankineLists.MINERAL_COLUMNS,
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
                RankineLists.CROPS_SINGLE,
                RankineLists.CROPS_DOUBLE,
                RankineLists.CROPS_TRIPLE,
                RankineLists.BUSH_PLANTS,
                RankineLists.DOUBLE_BUSH_PLANTS,
                RankineLists.EIGHT_LAYER_BLOCKS,
                RankineLists.FLUID_BLOCKS,
                RankineLists.GAS_BLOCKS,
                RankineLists.STANDARD_BLOCKS,
                RankineLists.ROTATION_BLOCKS,
                RankineLists.LIGHTNING_GLASSES,
                RankineLists.SOIL_BLOCKS,
                RankineLists.MUD_BLOCKS,
                RankineLists.GRASS_BLOCKS,
                RankineLists.PODZOL_BLOCKS,
                RankineLists.MYCELIUM_BLOCKS,
                RankineLists.PATH_BLOCKS,
                RankineLists.LANTERNS,
                RankineLists.QUARTER_SLABS,
                RankineLists.NATIVE_ORES,
                RankineLists.CRUSHING_ORES,
                RankineLists.SPECIAL_ORES).flatMap(Collection::stream).collect(Collectors.toList())) {
                add(blk, parseLangName(blk.getRegistryName().getPath()));
        }

        for (Block blk : Stream.of(
            RankineLists.ALLOY_BLOCKS,
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

        // Misc Blocks
        for (Block blk : Arrays.asList(
                RankineBlocks.GAS_BOTTLER.get(),
                RankineBlocks.GAS_VENT.get(),
                RankineBlocks.SEDIMENT_FAN.get(),
                RankineBlocks.ORNAMENT.get(),
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
                RankineBlocks.GYRATORY_CRUSHER.get(),
                RankineBlocks.EVAPORATION_TOWER.get(),
                //RankineBlocks.LASER_PYLON_BASE.get(),
                //RankineBlocks.LASER_PYLON_TOP.get(),
                //RankineBlocks.LASER_QUARRY.get(),
                RankineBlocks.DISTILLATION_TOWER.get(),
                RankineBlocks.AIR_DISTILLATION_PACKING.get(),
                RankineBlocks.REACTION_CHAMBER_CORE.get(),
                RankineBlocks.RARE_EARTH_ELECTROMAGNET.get(),
                RankineBlocks.ALNICO_ELECTROMAGNET.get(),
                RankineBlocks.TRAMPOLINE.get(),
                RankineBlocks.RANKINE_BOX.get(),
                RankineBlocks.CHARCOAL_PIT.get(),
                RankineBlocks.GWIHABAITE_CRYSTAL.get(),
                
                RankineBlocks.CAST_IRON_BARS.get(),
                RankineBlocks.SHORT_GRASS.get(),
                RankineBlocks.STINGING_NETTLE.get(),

                RankineBlocks.RED_CLOVER.get(),
                RankineBlocks.CRIMSON_CLOVER.get(),
                RankineBlocks.WHITE_CLOVER.get(),
                RankineBlocks.YELLOW_CLOVER.get(),

                RankineBlocks.COB.get(),
                RankineBlocks.SOD_BLOCK.get(),
                RankineBlocks.SOD_BLOCK_WALL.get(),
                RankineBlocks.SOD_BLOCK_STAIRS.get(),
                RankineBlocks.SOD_BLOCK_SLAB.get(),
                RankineBlocks.SOD_BLOCK_VERTICAL_SLAB.get(),
                
                
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
                RankineLists.ALLOY_TOOLS,
                RankineLists.FLINT_TOOLS,
                RankineLists.BRONZE_TOOLS,
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
                RankineLists.TITANIUM_ALLOY_TOOLS,
                RankineLists.ELEMENT_INGOTS,
                RankineLists.ELEMENT_NUGGETS,
                RankineLists.ALLOY_NUGGETS,
                RankineLists.ALLOY_INGOTS,
                RankineLists.GAS_BOTTLES,
                RankineLists.MINERAL_ITEMS,
                RankineLists.JAMS,
                RankineLists.SEEDS,
                RankineLists.GRAINS).flatMap(Collection::stream).collect(Collectors.toList())) {

            if (item == RankineItems.SODIUM_CHLORIDE.get()) {
                add(item, "Salt (NaCl)");
            } else if (item == RankineItems.CALCIUM_CHLORIDE.get()) {
                add(item, "Salt (CaCl2)");
            } else {
                add(item, parseLangName(item.getRegistryName().getPath()));
            }
        }

        for (Item item : Arrays.asList(
            RankineItems.BLACK_WALNUT.get(),
            RankineItems.COCONUT.get(),
            RankineItems.ALOE.get(),
            RankineItems.RICE.get(),
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
            RankineItems.ASBESTOS.get(),
            RankineItems.THENARDITE.get(),
            RankineItems.BORAX.get(),
            RankineItems.SODIUM_SULFIDE.get(),
            RankineItems.SODIUM_HYDROXIDE.get(),
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
            RankineItems.PUMICE_SOAP.get(),
            RankineItems.REFRACTORY_BRICK.get(),
            RankineItems.HIGH_REFRACTORY_BRICK.get(),
            RankineItems.ULTRA_HIGH_REFRACTORY_BRICK.get(),
            RankineItems.TAP_LINE.get(),
            RankineItems.TREE_TAP.get(),
            RankineItems.LODESTONE.get(),
            RankineItems.ELEMENT.get(),
            RankineItems.ALLOY_TEMPLATE.get(),
            RankineItems.BUILDING_TOOL.get(),
            RankineItems.ORE_DETECTOR.get(),
            RankineItems.PROSPECTING_STICK.get(),
            RankineItems.DOWSING_ROD.get(),
            RankineItems.PACKAGED_TOOL.get(),
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
            RankineItems.THERMOMETER.get(),
            RankineItems.HARDNESS_TESTER.get(),
            RankineItems.TOTEM_OF_BLAZING.get(),
            RankineItems.TOTEM_OF_COBBLING.get(),
            RankineItems.TOTEM_OF_ENDURING.get(),
            RankineItems.TOTEM_OF_HASTENING.get(),
            RankineItems.TOTEM_OF_INFUSING.get(),
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
        add(RankineItems.GF_BREAD.get(),"Bread (Gluten Free)");

        //Alloy Ingots
        add("item.rankine.crucible_steel_alloying", "Crucible Steel Ingot");
        add("item.rankine.steel_alloying", "Tool Steel Ingot");
        add("item.rankine.maraging_steel_alloying", "Maraging Steel Ingot");
        //Alloy Plates
        add("item.rankine.alloy_plate_bronze", "Bronze Plate");
        add("item.rankine.alloy_plate_brass", "Brass Plate");
        add("item.rankine.alloy_plate_invar", "Invar Plate");
        add("item.rankine.alloy_plate_steel", "Steel Plate");
        add("item.rankine.alloy_plate_stainless_steel", "Stainless Steel Plate");
        add("item.rankine.alloy_plate_cupronickel", "Cupronickel Plate");
        add("item.rankine.alloy_plate_aluminum", "Aluminum Plate");
        add("item.rankine.alloy_plate_copper", "Copper Plate");
        add("item.rankine.alloy_plate_bismuth", "Bismuth Plate");
        add("item.rankine.alloy_plate_lead", "Lead Plate");
        add("item.rankine.alloy_plate_iron", "Iron Plate");
        add("item.rankine.alloy_plate_silver", "Silver Plate");
        add("item.rankine.alloy_plate_gold", "Gold Plate");
        add("item.rankine.alloy_plate_platinum", "Platinum Plate");
        add("item.rankine.alloy_plate_nickel", "Nickel Plate");
        add("item.rankine.alloy_plate_titanium", "Titanium Plate");
        add("item.rankine.alloy_plate_tin", "Tin Plate");
        add("item.rankine.alloy_plate_tungsten", "Tungsten Plate");
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
        add("rankine.subtitle.shulker_gas_vacuum_absorb", "Shulker Gas Vacuum absorb");
        add("rankine.subtitle.shulker_gas_vacuum_release", "Shulker Gas Vacuum release");
        add("rankine.subtitle.sediment_fan_gen", "sedimentary block generation");


        //JOURNAL
        add("rankine.journal.landing_text", "A mere collection of discoveries, awaiting application.");

        add("rankine.journal.cat_tools.name", "Tools");
        add("rankine.journal.cat_tools.desc", "Various implements use to manipulate the world.");

        add("rankine.journal.cat_elements.name", "Elements");
        add("rankine.journal.cat_elements.desc", "The foundation of the universe");
        add("rankine.journal.cat_elements.gasses.name", "Gas Blocks");
        add("rankine.journal.cat_elements.gasses.text1", "");
        add("rankine.journal.cat_elements.gasses.text2", "");
        add("rankine.journal.cat_elements.gasses.text3", "");
        add("rankine.journal.cat_elements.gasses.text4", "");
        add("rankine.journal.cat_elements.gasses.text5", "");



        add("rankine.journal.cat_sediments.name", "Sediments");
        add("rankine.journal.cat_sediments.desc", "Dirt is too simple of a term to describe the complex matrix of organic material and pulverized rocks. A better term is soil, but there are more small particles than just that.");


        add("rankine.journal.cat_mechanics.name", "Mechanics");
        add("rankine.journal.cat_mechanics.desc", "");
        add("rankine.journal.cat_mechanics.finite_water.name", "Finite Water");
        add("rankine.journal.cat_mechanics.finite_water.text1", "A config enabled by default, which makes water slightly less renewable. Infinite water sources can only be created below the local water table height. A mechanical way of bringing water to the surface is described in the latter pages.");
        add("rankine.journal.cat_mechanics.finite_water.text2", "The Dowsing Rod is used to detect the local water height. Right clicking the ground will display a message above the hotbar.");
        add("rankine.journal.cat_mechanics.finite_water.text3", "The Ground Tap will generate a source block of water when connected to an appropriate pipe network. That network consists of a Flood Gate placed below the water table height and connected through Metal Pipes. Multiple taps can be on the same pipe line.");
        add("rankine.journal.cat_mechanics.finite_water.text4", "");
        add("rankine.journal.cat_mechanics.finite_water.text5", "");
        add("rankine.journal.cat_mechanics.sluicing.name", "Sluicing");
        add("rankine.journal.cat_mechanics.sluicing.text1", "Sluicing is a general term for the act of sifting through material under a stream of water. Components can be separated from parent material in a more manual way too.");
        add("rankine.journal.cat_mechanics.sluicing.text2", "");
        add("rankine.journal.cat_mechanics.sluicing.text3", "");
        add("rankine.journal.cat_mechanics.sluicing.text4", "");




        add("rankine.journal.cat_orientation.name", "Orientation");
        add("rankine.journal.cat_orientation.desc", "Notes from very brief orientation to the Project. These are all I have to look over for now.");

        add("rankine.journal.cat_stones.name", "Stones");
        add("rankine.journal.cat_stones.desc", "");
        add("rankine.journal.cat_stones.igneous.name", "Igneous Stones");
        add("rankine.journal.cat_stones.igneous.text1", "");
        add("rankine.journal.cat_stones.igneous.text2", "");
        add("rankine.journal.cat_stones.igneous.text3", "");
        add("rankine.journal.cat_stones.igneous.text4", "");
        add("rankine.journal.cat_stones.igneous.text5", "");



        add("rankine.journal.cat_machines.name", "Machines");
        add("rankine.journal.cat_machines.desc", "");



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


}
