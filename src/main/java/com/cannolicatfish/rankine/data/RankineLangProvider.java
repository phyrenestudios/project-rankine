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
                RankineLists.STONE,
                RankineLists.POLISHED_STONE,
                RankineLists.STONE_BRICKS,
                RankineLists.STONE_SLAB,
                RankineLists.POLISHED_STONE_SLAB,
                RankineLists.STONE_BRICKS_SLAB,
                RankineLists.STONE_VERTICAL_SLAB,
                RankineLists.POLISHED_STONE_VERTICAL_SLAB,
                RankineLists.STONE_BRICKS_VERTICAL_SLAB,
                RankineLists.STONE_STAIRS,
                RankineLists.POLISHED_STONE_STAIRS,
                RankineLists.STONE_BRICKS_STAIRS,
                RankineLists.STONE_WALL,
                RankineLists.POLISHED_STONE_WALL,
                RankineLists.STONE_BRICKS_WALL,
                RankineLists.STONE_PRESSURE_PLATE,
                RankineLists.STONE_BRICKS_PRESSURE_PLATE,
                RankineLists.STONE_BUTTON,
                RankineLists.BRICKS,
                RankineLists.BRICKS_SLAB,
                RankineLists.BRICKS_STAIRS,
                RankineLists.BRICKS_VERTICAL_SLAB,
                RankineLists.BRICKS_WALL,
                RankineLists.SHEETMETALS,
                RankineLists.SHEETMETAL_VERTICAL_SLAB,
                RankineLists.GEODES,
                RankineLists.LEDS,
                RankineLists.MINERAL_WOOL,
                RankineLists.FIBER_BLOCK,
                RankineLists.FIBER_MAT,
                RankineLists.FLOWER_POTS,
                RankineLists.TALL_FLOWERS,
                RankineLists.SAPLINGS,
                RankineLists.LEAVES,
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
                RankineLists.MINERAL_STONES,
                RankineLists.METAL_TRAPDOORS,
                RankineLists.METAL_DOORS,
                RankineLists.METAL_LADDERS,
                RankineLists.ELEMENT_BLOCKS,
                RankineLists.ALLOY_BLOCKS,
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
                RankineLists.SOILS,
                RankineLists.GRASSY_SOILS,
                RankineLists.PATH_BLOCKS,
                RankineLists.NATIVE_ORES,
                RankineLists.CRUSHING_ORES,
                RankineLists.SPECIAL_ORES,
                RankineLists.MINERAL_BLOCKS).flatMap(Collection::stream).collect(Collectors.toList())) {

            if (blk.matchesBlock(RankineBlocks.SODIUM_CHLORIDE_BLOCK.get())) {
                add(blk, "Salt Block (NaCl)");
            } else if (blk.matchesBlock(RankineBlocks.CALCIUM_CHLORIDE_BLOCK.get())) {
                add(blk, "Salt Block (CaCl2)");
            } else {
                add(blk, parseLangName(blk.getRegistryName().getPath()));
            }
        }

        // Misc Blocks
        for (Block blk : Arrays.asList(
                RankineBlocks.SEDIMENT_FAN.get(),
                RankineBlocks.GAS_VENT.get(),
                RankineBlocks.SODIUM_VAPOR_LAMP.get(),
                RankineBlocks.ALLOY_FURNACE.get(),
                RankineBlocks.INDUCTION_FURNACE.get(),
                RankineBlocks.CRUCIBLE_BLOCK.get(),
                RankineBlocks.BEEHIVE_OVEN_PIT.get(),
                RankineBlocks.HIGH_BEEHIVE_OVEN_PIT.get(),
                RankineBlocks.ULTRA_HIGH_BEEHIVE_OVEN_PIT.get(),
                RankineBlocks.MATERIAL_TESTING_TABLE.get(),
                RankineBlocks.TEMPLATE_TABLE.get(),
                RankineBlocks.PISTON_CRUSHER.get(),
                RankineBlocks.BOTANIST_STATION.get(),
                RankineBlocks.DIAMOND_ANVIL_CELL.get(),
                RankineBlocks.GYRATORY_CRUSHER.get(),
                RankineBlocks.EVAPORATION_TOWER.get(),
                RankineBlocks.LASER_PYLON_BASE.get(),
                RankineBlocks.LASER_PYLON_TOP.get(),
                RankineBlocks.LASER_QUARRY.get(),
                RankineBlocks.REACTION_CHAMBER_CORE.get(),
                RankineBlocks.RARE_EARTH_ELECTROMAGNET.get(),
                RankineBlocks.ALNICO_ELECTROMAGNET.get(),
                RankineBlocks.TRAMPOLINE.get(),
                RankineBlocks.RANKINE_BOX.get(),
                RankineBlocks.CHARCOAL_PIT.get(),
                
                
                RankineBlocks.CAST_IRON_BARS.get(),
                RankineBlocks.SHORT_GRASS.get(),
                RankineBlocks.STINGING_NETTLE.get(),
                RankineBlocks.RED_CLOVER.get(),
                RankineBlocks.CRIMSON_CLOVER.get(),
                RankineBlocks.WHITE_CLOVER.get(),
                RankineBlocks.YELLOW_CLOVER.get(),

                RankineBlocks.SOD_BLOCK.get(),
                RankineBlocks.SOD_BLOCK_WALL.get(),
                RankineBlocks.SOD_BLOCK_STAIRS.get(),
                RankineBlocks.SOD_BLOCK_SLAB.get(),
                RankineBlocks.SOD_BLOCK_VERTICAL_SLAB.get(),
                RankineBlocks.ROMAN_CONCRETE_BRICKS.get(),
                RankineBlocks.ROMAN_CONCRETE_BRICKS_SLAB.get(),
                RankineBlocks.ROMAN_CONCRETE_BRICKS_STAIRS.get(),
                RankineBlocks.ROMAN_CONCRETE_BRICKS_WALL.get(),
                RankineBlocks.ROMAN_CONCRETE_BRICKS_VERTICAL_SLAB.get(),
                RankineBlocks.POLISHED_ROMAN_CONCRETE.get(),
                RankineBlocks.POLISHED_ROMAN_CONCRETE_SLAB.get(),
                RankineBlocks.POLISHED_ROMAN_CONCRETE_WALL.get(),
                RankineBlocks.POLISHED_ROMAN_CONCRETE_STAIRS.get(),
                RankineBlocks.POLISHED_ROMAN_CONCRETE_VERTICAL_SLAB.get(),
                RankineBlocks.ROMAN_CONCRETE.get(),
                RankineBlocks.ROMAN_CONCRETE_WALL.get(),
                RankineBlocks.ROMAN_CONCRETE_STAIRS.get(),
                RankineBlocks.ROMAN_CONCRETE_SLAB.get(),
                RankineBlocks.ROMAN_CONCRETE_VERTICAL_SLAB.get(),
                RankineBlocks.CHECKERED_MARBLE.get(),
                RankineBlocks.CHECKERED_MARBLE_WALL.get(),
                RankineBlocks.CHECKERED_MARBLE_STAIRS.get(),
                RankineBlocks.CHECKERED_MARBLE_SLAB.get(),
                RankineBlocks.CHECKERED_MARBLE_VERTICAL_SLAB.get(),
                RankineBlocks.SKARN.get(),
                RankineBlocks.SKARN_WALL.get(),
                RankineBlocks.SKARN_STAIRS.get(),
                RankineBlocks.SKARN_SLAB.get(),
                RankineBlocks.SKARN_VERTICAL_SLAB.get(),
                RankineBlocks.BRECCIA.get(),
                RankineBlocks.BRECCIA_WALL.get(),
                RankineBlocks.BRECCIA_STAIRS.get(),
                RankineBlocks.BRECCIA_SLAB.get(),
                RankineBlocks.BRECCIA_VERTICAL_SLAB.get(),
                RankineBlocks.FIBER_BLOCK_WALL.get(),
                RankineBlocks.FIBER_BLOCK_STAIRS.get(),
                RankineBlocks.FIBER_BLOCK_SLAB.get(),
                RankineBlocks.FIBER_BLOCK_VERTICAL_SLAB.get(),
                RankineBlocks.CAST_IRON_SUPPORT.get(),
                RankineBlocks.CAST_IRON_SUPPORT_STAIRS.get(),
                RankineBlocks.CAST_IRON_SUPPORT_SLAB.get(),
                RankineBlocks.CAST_IRON_SUPPORT_VERTICAL_SLAB.get(),
                RankineBlocks.CEMENT.get(),
                RankineBlocks.CEMENT_WALL.get(),
                RankineBlocks.CEMENT_STAIRS.get(),
                RankineBlocks.CEMENT_SLAB.get(),
                RankineBlocks.CEMENT_VERTICAL_SLAB.get(),
                RankineBlocks.CONCRETE.get(),
                RankineBlocks.CONCRETE_WALL.get(),
                RankineBlocks.CONCRETE_STAIRS.get(),
                RankineBlocks.CONCRETE_SLAB.get(),
                RankineBlocks.CONCRETE_VERTICAL_SLAB.get(),
                
                
                RankineBlocks.UNAMED_EXPLOSIVE.get(),
                RankineBlocks.ANTIMATTER.get(),
                RankineBlocks.LIGHT_GRAVEL.get(),
                RankineBlocks.DARK_GRAVEL.get(),
                RankineBlocks.FIRE_CLAY.get(),
                RankineBlocks.KAOLINITE_BLOCK.get(),
                RankineBlocks.PORPHYRY_COPPER.get()
                )) {
            add(blk, parseLangName(blk.getRegistryName().getPath()));
        }

        for (Item item : Stream.of(
                RankineLists.WOODEN_BOATS,
                RankineLists.ARROWS,
                RankineLists.WOODEN_TOOLS,
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
            RankineItems.CAMPHOR_BASIL_SEEDS.get(),
            RankineItems.RICE_SEEDS.get(),
            RankineItems.ASPARAGUS_SEEDS.get(),
            RankineItems.CORN_SEEDS.get(),
            RankineItems.COTTON_SEEDS.get(),
            RankineItems.JUTE_SEEDS.get(),
            RankineItems.FLOWER_SEEDS.get(),
            RankineItems.CORN_STALK.get(),
            RankineItems.VULCANIZED_RUBBER.get(),
            RankineItems.BITUMEN.get(),
            RankineItems.FIRE_CLAY_BALL.get(),
            RankineItems.KAOLINITE_BALL.get(),
            RankineItems.BONE_CHAR.get(),
            RankineItems.NETHERITE_NUGGET.get(),
            RankineItems.SLAG.get(),
            RankineItems.TRONA.get(),
            RankineItems.POTASH.get(),
            RankineItems.ASBESTOS.get(),
            RankineItems.THENARDITE.get(),
            RankineItems.BORAX.get(),
            RankineItems.SODIUM_SULFIDE.get(),
            RankineItems.SODIUM_HYDROXIDE.get(),
            RankineItems.SODIUM_CARBONATE.get(),
            RankineItems.SALTPETER.get(),
            RankineItems.CEMENT_MIX.get(),
            RankineItems.MORTAR.get(),
            RankineItems.METEORIC_IRON.get(),
            RankineItems.COMPOST.get(),
            RankineItems.BIOMASS.get(),
            RankineItems.COMPRESSED_BIOMASS.get(),
            RankineItems.DRIED_BAMBOO.get(),
            RankineItems.BEAVER_PELT.get(),
            RankineItems.ROPE.get(),
            RankineItems.HERBICIDE.get(),
            RankineItems.SYNTHETIC_LEATHER.get(),
            RankineItems.PULP.get(),
            RankineItems.DRY_RUBBER.get(),
            RankineItems.INDIUM_TIN_OXIDE.get(),
            RankineItems.CADMIUM_TELLURIDE_CELL.get(),
            RankineItems.YAG_ROD.get(),
            RankineItems.UNCUT_GEODE.get(),
            RankineItems.SODIUM_ARC_TUBE.get(),
            RankineItems.GRAPHITE_ELECTRODE.get(),
            RankineItems.HARD_CARBON_ELECTRODE.get(),
            RankineItems.SADDLE_TREE.get(),
            RankineItems.PUMICE_SOAP.get(),
            RankineItems.REFRACTORY_BRICK.get(),
            RankineItems.HIGH_REFRACTORY_BRICK.get(),
            RankineItems.ULTRA_HIGH_REFRACTORY_BRICK.get(),
            RankineItems.TAP_LINE.get(),
            RankineItems.TREE_TAP.get(),
            RankineItems.LODESTONE.get(),
            RankineItems.ELEMENT.get(),
            RankineItems.ELEMENT_TRANSMUTER.get(),
            RankineItems.LEFT_TRANSMUTER.get(),
            RankineItems.RIGHT_TRANSMUTER.get(),
            RankineItems.DOWN_TRANSMUTER.get(),
            RankineItems.UP_TRANSMUTER.get(),
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
            RankineItems.TOTEM_OF_COBBLING.get(),
            RankineItems.ELEMENT_INDEXER.get(),
            RankineItems.PIA.get(),
            RankineItems.SHULKER_GAS_VACUUM.get(),
            RankineItems.BANDAGE.get(),
            RankineItems.HASTE_PENDANT.get(),
            RankineItems.REPULSION_PENDANT.get(),
            RankineItems.LEVITATION_PENDANT.get(),
            RankineItems.SPEED_PENDANT.get(),
            RankineItems.HEALTH_PENDANT.get(),
            RankineItems.LUCK_PENDANT.get(),
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
            RankineItems.SULFURIC_ACID_BUCKET.get(),
            RankineItems.COIN.get(),
            RankineItems.MAGNESIUM_BATTERY.get(),
            RankineItems.LEAD_ACID_BATTERY.get(),
            RankineItems.VANADIUM_REDOX_BATTERY.get(),
            RankineItems.ZINC_BROMINE_BATTERY.get(),
            RankineItems.SODIUM_SULFUR_BATTERY.get(),
            RankineItems.LITHIUM_ION_BATTERY.get())) {
            add(item, parseLangName(item.getRegistryName().getPath()));
        }

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
        add("item.rankine.alloy_rod_steel", "Steel Rpd");
        add("item.rankine.alloy_rod_cast_iron", "Cast Iron Rpd");
        //Alloy Wires
        add("item.rankine.alloy_wire_cupronickel", "Cupronickel Wire");
        add("item.rankine.alloy_wire_steel", "Steel Wire");

        //Death Messages
        add("death.attack.suffocating", "%1$s suffocated in a gas cloud");
        add("death.attack.suffocating.player", "%1$s suffocated in a gas cloud whilst trying to escape %2$s");






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
        add("rankine.jei.evaporation", "Evaporation");
        add("rankine.jei.beeoven", "Beehive Oven");
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
        add("element.rankine,elements/copper.preview", "Commonly used in early alloys.");
        add("element.rankine,elements/copper.desc0", "Copper is a transition metal with an atomic number of 29.");
        add("element.rankine,elements/copper.desc1", "Commonly used in electrical components and early alloys.");
        add("element.rankine,elements/copper.desc2", "Commonly used in electrical components and early alloys.");

        add("rankine.advancements.story.root.title", "Project Rankine");
        add("rankine.advancements.story.root.description", "Begin delving into the changed world");
        add("rankine.advancements.story.make_hardness_tester.title", "Strength in Knowledge");
        add("rankine.advancements.story.make_hardness_tester.description", "Make a Hardness Tester to easily determine harvest level");
        add("rankine.advancements.story.make_gold_pan.title", "Forager");
        add("rankine.advancements.story.make_gold_pan.description", "Make a Gold Pan to sift through loose material");
        add("rankine.advancements.story.make_prospecting_stick.title", "Help Wanted");
        add("rankine.advancements.story.make_prospecting_stick.description", "Make a Prospecting Stick to point the way to ores");
        add("rankine.advancements.story.gather_rope.title", "Knot Likely");
        add("rankine.advancements.story.gather_rope.description", "Gather rope by obtaining a flint knife and harvesting grass");
        add("rankine.advancements.story.get_flint_axe.title", "Hard Wood");
        add("rankine.advancements.story.get_flint_axe.description", "Make a Flint Handaxe to harvest wood early and more easily");
        add("rankine.advancements.story.make_charcoal_pit.title", "Buried Logs");
        add("rankine.advancements.story.make_charcoal_pit.description", "Make a Charcoal Pit to help alleviate early fuel requirements and make large batches of charcoal");
        add("rankine.advancements.story.get_flint_tools.title", "Sharp Beginnings");
        add("rankine.advancements.story.get_flint_tools.description", "Construct a Flint Pickaxe to begin mining quickly");
        add("rankine.advancements.story.get_flint_spear.title", "Getting the Point");
        add("rankine.advancements.story.get_flint_spear.description", "Make a throwable Flint Spear to protect yourself");
        add("rankine.advancements.story.make_refractory_brick.title", "Another Brick in the Wall");
        add("rankine.advancements.story.make_refractory_brick.description", "Make Refractory Brick by finding clay and using mortar");
        add("rankine.advancements.story.make_alloy_furnace.title", "Combined Strength");
        add("rankine.advancements.story.make_alloy_furnace.description", "Make an Alloy Furnace by using sheetmetal and refractory bricks");
        add("rankine.advancements.story.make_material_testing_table.title", "Knowledge of Strength");
        add("rankine.advancements.story.make_material_testing_table.description", "Make a Material Testing Table to begin examining properties of elements and alloys");
        add("rankine.advancements.story.make_template_table.title", "The Plan");
        add("rankine.advancements.story.make_template_table.description", "Make an Alloy Template Table to automate the creation of alloys");
        add("rankine.advancements.story.make_evaporation_tower.title", "Water Extraction");
        add("rankine.advancements.story.make_evaporation_tower.description", "Construct an Evaporation Tower to generate resources from water in different biomes");
        add("rankine.advancements.story.make_coal_forge.title", "Automated Tools");
        add("rankine.advancements.story.make_coal_forge.description", "Make a Coal Forge to make alloy tools");
        add("rankine.advancements.story.get_bronze_tools.title", "Timing is Everything");
        add("rankine.advancements.story.get_bronze_tools.description", "Make a Pewter, Colored Gold, or Bronze Hammer to begin crushing ores and stones");
        add("rankine.advancements.story.get_crowbar.title", "Lever Action");
        add("rankine.advancements.story.get_crowbar.description", "Make a Crowbar to manipulate blocks");
        add("rankine.advancements.story.make_templates.title", "The Plan");
        add("rankine.advancements.story.make_templates.description", "Make templates to construct alloy tools at the coal forge");
        add("rankine.advancements.story.make_blast_furnace.title", "Blast from the Past");
        add("rankine.advancements.story.make_blast_furnace.description", "Make a Blast Furnace to begin smelting more complex ores");
        add("rankine.advancements.story.find_meteorite.title", "Iron from Space");
        add("rankine.advancements.story.find_meteorite.description", "Find a meteorite and crush the ore in it to get meteoric iron");
        add("rankine.advancements.story.find_ironstone.title", "Iron in the Rough");
        add("rankine.advancements.story.find_ironstone.description", "Find ironstone under the sands of a desert or mesa");
        add("rankine.advancements.story.make_cast_iron_alloy.title", "Cooking Time");
        add("rankine.advancements.story.make_cast_iron_alloy.description", "Make cast iron alloy by using a form of carbon and pig iron");
        add("rankine.advancements.story.make_tree_tap.title", "Flowing Phloem");
        add("rankine.advancements.story.make_tree_tap.description", "Make a tree tap to begin extracting liquids from trees");
        add("rankine.advancements.story.make_bandage.title", "Quick Healing");
        add("rankine.advancements.story.make_bandage.description", "Heal some minor damage with a bandage");
        add("rankine.advancements.story.make_trampoline.title", "Boing!");
        add("rankine.advancements.story.make_trampoline.description", "Make a trampoline to launch high into the air");
        add("rankine.advancements.story.make_herbicide.title", "Weedkiller");
        add("rankine.advancements.story.make_herbicide.description", "Produce herbicide from juglone to clear patches of grass and other plants");
        add("rankine.advancements.story.make_piston_crusher.title", "Automatic Labor");
        add("rankine.advancements.story.make_piston_crusher.description", "Make a piston crusher to automate crushing");
        add("rankine.advancements.story.make_pulp.title", "Crushed to a Pulp");
        add("rankine.advancements.story.make_pulp.description", "Make pulp from crushed logs");
        add("rankine.advancements.story.make_quartz.title", "Overworld Quartz");
        add("rankine.advancements.story.make_quartz.description", "Find or make quartz by crushing stones");
        add("rankine.advancements.story.make_glass_cutter.title", "Transparent Carving");
        add("rankine.advancements.story.make_glass_cutter.description", "Make a glass cutter to minimize losses");
        add("rankine.advancements.story.make_pig_iron.title", "No Pigs Harmed");
        add("rankine.advancements.story.make_pig_iron.description", "Make pig iron by smelting an iron ingot");
        add("rankine.advancements.story.make_beehive_oven_pit.title", "No Bees Harmed");
        add("rankine.advancements.story.make_beehive_oven_pit.description", "Build a beehive oven pit");
        add("rankine.advancements.story.make_quicklime.title", "Limecraft");
        add("rankine.advancements.story.make_quicklime.description", "Produce quicklime from combusting limestone with the beehive oven");
        add("rankine.advancements.story.make_roman_concrete.title", "Roaming like the Romans");
        add("rankine.advancements.story.make_roman_concrete.description", "Make roman concrete from pozzolanic mortar");
        add("rankine.advancements.story.make_magnesia.title", "Take the Heat");
        add("rankine.advancements.story.make_magnesia.description", "Make magnesia from cooking a magnesite block with the beehive oven");
        add("rankine.advancements.story.make_coke.title", "I cant believe it's not Coal!");
        add("rankine.advancements.story.make_coke.description", "Use a beehive oven to produce coke from bituminous and sub-bituminous coal blocks");
        add("rankine.advancements.story.make_wrought_iron.title", "Acquire Harderware");
        add("rankine.advancements.story.make_wrought_iron.description", "Make Wrought Iron by crushing bloom iron made from the Beehive Oven");
        add("rankine.advancements.story.make_crucible.title", "Refractory Factory");
        add("rankine.advancements.story.make_crucible.description", "Construct a crucible to make steel");
        add("rankine.advancements.story.make_steel_alloy.title", "Steel Yourself");
        add("rankine.advancements.story.make_steel_alloy.description", "Make Steel Alloy by using the Crucible or the Induction Furnace");
        add("rankine.advancements.story.make_brigandine_armor.title", "Plate Up");
        add("rankine.advancements.story.make_brigandine_armor.description", "Make a full set of Brigandine Armor");
        add("rankine.advancements.story.make_induction_furnace.title", "Heating Up");
        add("rankine.advancements.story.make_induction_furnace.description", "Make an Induction Furnace for more advanced alloys");
        add("rankine.advancements.story.make_steel_gold_pan.title", "Direct Deposit");
        add("rankine.advancements.story.make_steel_gold_pan.description", "Make a Steel Gold Pan to sieve alluvium and black sand");
        add("rankine.advancements.story.make_nickel_superalloy.title", "Diving Checklist");
        add("rankine.advancements.story.make_nickel_superalloy.description", "Make every available type of Nickel Superalloy tool");
        add("rankine.advancements.story.make_stainless_steel.title", "Always Prepared");
        add("rankine.advancements.story.make_stainless_steel.description", "Make every available type of Stainless Steel tool");
        add("rankine.advancements.story.make_tungsten_heavy_alloy.title", "Bound to the Fire");
        add("rankine.advancements.story.make_tungsten_heavy_alloy.description", "Make every available type of Tungsten Heavy Alloy tool");
        add("rankine.advancements.story.make_cobalt_superalloy.title", "Lasting Impression");
        add("rankine.advancements.story.make_cobalt_superalloy.description", "Make every available type of Cobalt Superalloy tool");
        add("rankine.advancements.story.make_element_transmuter.title", "Fishy Science");
        add("rankine.advancements.story.make_element_transmuter.description", "Create an Element Transmuter); a substance from otherworldly materials that can extract elements");
        add("rankine.advancements.story.make_power_cell.title", "Fully Charged");
        add("rankine.advancements.story.make_power_cell.description", "Make any power cell for use in advanced machines");
        add("rankine.advancements.story.make_electromagnet.title", "Moving Metals");
        add("rankine.advancements.story.make_electromagnet.description", "Make any type of Electromagnet Block");
        add("rankine.advancements.story.make_gyratory_crusher.title", "Pressing Issue");
        add("rankine.advancements.story.make_gyratory_crusher.description", "Make a Gyratory Crusher to automate crushing further");
        add("rankine.advancements.story.make_rankine_box.title", "Atomic Dissonance");
        add("rankine.advancements.story.make_rankine_box.description", "Make a Rankine Box to transmute elements");
        add("rankine.advancements.story.make_laser_quarry.title", "Excavation Invigoration");
        add("rankine.advancements.story.make_laser_quarry.description", "Construct a Laser Quarry to mine out large chunks of the world");
        add("rankine.advancements.story.make_thorium_arrow.title", "Sounds of Thunder");
        add("rankine.advancements.story.make_thorium_arrow.description", "Make Thorium Arrows to control the power of lightning with your bow");
        add("rankine.advancements.story.get_pendant_template_title", "Secret Plan");
        add("rankine.advancements.story.get_pendant_template_description", "Obtain a pendant template");
        add("rankine.advancements.story.make_pendants.title", "Hidden Power");
        add("rankine.advancements.story.make_pendants.description", "Make all of the 6 pendants");
        add("rankine.advancements.story.support.title", "We support Patchouli and JEI!");
        add("rankine.advancements.story.support.description", "Be sure to download these mods if you want more information!");
        add("rankine.advancements.challenges.root.title", "Project Rankine Challenges");
        add("rankine.advancements.challenges.root.description", "Complete your understanding of the changed world");
        add("rankine.advancements.challenges.rock_collector.title", "We Have the Rockiest Rocks");
        add("rankine.advancements.challenges.rock_collector.description", "Collect all the new types of rocks");
        add("rankine.advancements.challenges.element_collector.title", "Periodic Collector");
        add("rankine.advancements.challenges.element_collector.description", "Collect every element in the Periodic Table");
        add("rankine.advancements.challenges.make_all_power_cells.title", "Energized");
        add("rankine.advancements.challenges.make_all_power_cells.description", "Make every type of Power Cell");
        add("rankine.advancements.challenges.pendant_collector.title", "On the Other Hand");
        add("rankine.advancements.challenges.pendant_collector.description", "Make every type of Colored Gold Pendant");
        add("rankine.advancements.story.make_element_indexer.title", "Elementary");
        add("rankine.advancements.story.make_element_indexer.description", "Use a device to examine element properties");
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
