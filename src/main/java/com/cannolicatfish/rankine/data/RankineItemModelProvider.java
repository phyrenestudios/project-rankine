package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RankineItemModelProvider extends ItemModelProvider {

    public RankineItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ProjectRankine.MODID, existingFileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Project Rankine - Item Models";
    }

    @Override
    protected void registerModels() {
        //food items

        basicItem(RankineItems.BLACK_WALNUT.get());
        basicItem(RankineItems.COCONUT.get());
        basicItem(RankineItems.ALOE.get());
        basicItem(RankineItems.RICE.get());
        basicItem(RankineItems.ASPARAGUS.get());
        basicItem(RankineItems.CORN_EAR.get());
        basicItem(RankineItems.COTTON.get());
        basicItem(RankineItems.JUTE.get());
        basicItem(RankineItems.BLUEBERRIES.get());
        basicItem(RankineItems.CRANBERRIES.get());
        basicItem(RankineItems.SNOWBERRIES.get());
        basicItem(RankineItems.ELDERBERRIES.get());
        basicItem(RankineItems.RASPBERRIES.get());
        basicItem(RankineItems.BLACKBERRIES.get());
        basicItem(RankineItems.STRAWBERRIES.get());
        basicItem(RankineItems.PINEAPPLE.get());
        basicItem(RankineItems.PINEAPPLE_SLEEVES.get());
        basicItem(RankineItems.BANANA_YUCCA.get());
        basicItem(RankineItems.JUNIPER_BERRIES.get());
        basicItem(RankineItems.ROASTED_ASPARAGUS.get());
        basicItem(RankineItems.ROASTED_WALNUT.get());
        basicItem(RankineItems.TOASTED_COCONUT.get());
        basicItem(RankineItems.POPCORN.get());
        basicItem(RankineItems.DOUGH.get());
        basicItem(RankineItems.TOAST.get());
        basicItem(RankineItems.CINNAMON.get());
        basicItem(RankineItems.CINNAMON_TOAST.get());
        basicItem(RankineItems.TRAIL_MIX.get());
        basicItem(RankineItems.CHEESE.get());
        basicItem(RankineItems.CAKE_SLICE.get());
        basicItem(RankineItems.PINA_COLADA.get());
        basicItem(RankineItems.MAPLE_SYRUP.get());
        basicItem(RankineItems.PANCAKE.get());
        basicItem(RankineItems.PANCAKE_BATTER.get());
        basicItem(RankineItems.PANCAKE_BREAKFAST.get());
        basicItem(RankineItems.PLANT_FIBER.get());
        basicItem(RankineItems.CAMPHOR_BASIL_LEAF.get());
        basicItem(RankineItems.CAMPHOR_BASIL_SEEDS.get());
        basicItem(RankineItems.RICE_SEEDS.get());
        basicItem(RankineItems.ASPARAGUS_SEEDS.get());
        basicItem(RankineItems.CORN_SEEDS.get());
        basicItem(RankineItems.COTTON_SEEDS.get());
        basicItem(RankineItems.JUTE_SEEDS.get());
        basicItem(RankineItems.FLOWER_SEEDS.get());
        basicItem(RankineItems.CORN_STALK.get());
        basicItem(RankineItems.CORN_FLOUR.get());
        basicItem(RankineItems.WHEAT_FLOUR.get());
        basicItem(RankineItems.RICE_FLOUR.get());
        basicItemAltTexture(RankineItems.AGED_CHEESE.get(), modLoc("item/aged_cheese_wheel"));
        basicItemAltTexture(RankineItems.UNAGED_CHEESE.get(), modLoc("item/unaged_cheese_wheel"));



        basicItem(RankineItems.BITUMEN.get());
        basicItem(RankineItems.FIRE_CLAY_BALL.get());
        basicItem(RankineItems.BONE_CHAR.get());
        basicItem(RankineItems.LEPIDOLITE.get());
        basicItem(RankineItems.NETHERITE_NUGGET.get());
        basicItem(RankineItems.ALUMINA.get());
        basicItem(RankineItems.ASH.get());
        basicItem(RankineItems.BONE_ASH.get());
        basicItem(RankineItems.SAWDUST.get());
        basicItem(RankineItems.SLAG.get());
        basicItem(RankineItems.TRONA.get());
        basicItem(RankineItems.CALCIUM_CHLORIDE.get());
        basicItem(RankineItems.THENARDITE.get());
        basicItem(RankineItems.BORAX.get());
        basicItem(RankineItems.SODIUM_SULFIDE.get());
        basicItem(RankineItems.SODIUM_HYDROXIDE.get());
        basicItem(RankineItems.SODIUM_CARBONATE.get());
        basicItem(RankineItems.SALTPETER.get());
        basicItem(RankineItems.DRY_MORTAR.get());
        basicItem(RankineItems.MORTAR.get());
        basicItem(RankineItems.POZZOLANIC_MORTAR.get());
        basicItem(RankineItems.METEORIC_IRON.get());
        basicItem(RankineItems.COMPOST.get());
        basicItem(RankineItems.BIOMASS.get());
        basicItem(RankineItems.COMPRESSED_BIOMASS.get());
        basicItem(RankineItems.DRIED_BAMBOO.get());
        basicItem(RankineItems.BEAVER_PELT.get());
        basicItem(RankineItems.ROPE.get());
        basicItem(RankineItems.HERBICIDE.get());
        basicItem(RankineItems.SYNTHETIC_LEATHER.get());
        basicItem(RankineItems.PULP.get());
        basicItem(RankineItems.DRY_RUBBER.get());
        basicItem(RankineItems.INDIUM_TIN_OXIDE.get());
        basicItem(RankineItems.CADMIUM_TELLURIDE_CELL.get());
        basicItem(RankineItems.YAG_ROD.get());
        basicItem(RankineItems.UNCUT_GEODE.get());
        basicItem(RankineItems.SODIUM_ARC_TUBE.get());
        basicItem(RankineItems.GRAPHITE_ELECTRODE.get());
        basicItem(RankineItems.HARD_CARBON_ELECTRODE.get());
        basicItem(RankineItems.SADDLE_TREE.get());
        basicItem(RankineItems.PUMICE_SOAP.get());
        basicItem(RankineItems.REFRACTORY_BRICK.get());
        basicItem(RankineItems.HIGH_REFRACTORY_BRICK.get());
        basicItem(RankineItems.ULTRA_HIGH_REFRACTORY_BRICK.get());
        basicItem(RankineItems.TAP_LINE.get());
        basicItem(RankineItems.TREE_TAP.get());
        basicItem(RankineItems.LODESTONE.get());
        basicItem(RankineItems.ELEMENT.get());
        basicItem(RankineItems.ELEMENT_TRANSMUTER.get());
        basicItem(RankineItems.LEFT_TRANSMUTER.get());
        basicItem(RankineItems.RIGHT_TRANSMUTER.get());
        basicItem(RankineItems.DOWN_TRANSMUTER.get());
        basicItem(RankineItems.UP_TRANSMUTER.get());
        basicItem(RankineItems.ORE_DETECTOR.get());
        basicItem(RankineItems.PROSPECTING_STICK.get());
        basicItem(RankineItems.PACKAGED_TOOL.get());
        basicItem(RankineItems.ALNICO_MAGNET.get());
        basicItem(RankineItems.RARE_EARTH_MAGNET.get());
        basicItem(RankineItems.STEEL_GOLD_PAN.get());
        basicItem(RankineItems.ALTIMETER.get());
        basicItem(RankineItems.SPEEDOMETER.get());
        basicItem(RankineItems.BIOMETER.get());
        basicItem(RankineItems.PHOTOMETER.get());
        basicItem(RankineItems.GLASS_CUTTER.get());
        basicItem(RankineItems.FIRE_EXTINGUISHER.get());
        basicItem(RankineItems.ROCK_DRILL.get());
        basicItem(RankineItems.SPARK_LIGHTER.get());
        basicItem(RankineItems.THERMOMETER.get());
        basicItem(RankineItems.HARDNESS_TESTER.get());
        basicItem(RankineItems.ORE_CYCLER.get());
        basicItem(RankineItems.TOTEM_OF_COBBLING.get());
        basicItem(RankineItems.ELEMENT_INDEXER.get());
        basicItem(RankineItems.PIA.get());
        basicItem(RankineItems.SHULKER_GAS_VACUUM.get());
        basicItem(RankineItems.BANDAGE.get());
        basicItem(RankineItems.HASTE_PENDANT.get());
        basicItem(RankineItems.REPULSION_PENDANT.get());
        basicItem(RankineItems.LEVITATION_PENDANT.get());
        basicItem(RankineItems.SPEED_PENDANT.get());
        basicItem(RankineItems.HEALTH_PENDANT.get());
        basicItem(RankineItems.LUCK_PENDANT.get());
        basicItem(RankineItems.GAS_MASK.get());
        basicItem(RankineItems.SANDALS.get());
        basicItem(RankineItems.ICE_SKATES.get());
        basicItem(RankineItems.SNOWSHOES.get());
        basicItem(RankineItems.BRIGADINE_CHESTPLATE.get());
        basicItem(RankineItems.BRIGADINE_LEGGINGS.get());
        basicItem(RankineItems.BRIGADINE_HELMET.get());
        basicItem(RankineItems.BRIGADINE_BOOTS.get());
        basicItem(RankineItems.CONDUIT_DIVING_CHESTPLATE.get());
        basicItem(RankineItems.CONDUIT_DIVING_HELMET.get());
        basicItem(RankineItems.CONDUIT_DIVING_LEGGINGS.get());
        basicItem(RankineItems.CONDUIT_DIVING_BOOTS.get());
        basicItem(RankineItems.DIVING_CHESTPLATE.get());
        basicItem(RankineItems.DIVING_LEGGINGS.get());
        basicItem(RankineItems.DIVING_HELMET.get());
        basicItem(RankineItems.DIVING_BOOTS.get());
        basicItem(RankineItems.LATEX_BUCKET.get());
        basicItem(RankineItems.SAP_BUCKET.get());
        basicItem(RankineItems.RESIN_BUCKET.get());
        basicItem(RankineItems.MAPLE_SAP_BUCKET.get());
        basicItem(RankineItems.JUGLONE_BUCKET.get());
        basicItem(RankineItems.LIQUID_MERCURY_BUCKET.get());
        basicItem(RankineItems.COIN.get());
        basicItem(RankineItems.POWER_CELL_1.get());
        basicItem(RankineItems.POWER_CELL_2.get());
        basicItem(RankineItems.POWER_CELL_3.get());
        basicItem(RankineItems.POWER_CELL_4.get());
        basicItem(RankineItems.POWER_CELL_5.get());
        basicItem(RankineItems.POWER_CELL_6.get());
        basicItem("rankine_journal");

        withExistingParent(RankineItems.BEAVER.get().getRegistryName().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(RankineItems.DESMOXYTE.get().getRegistryName().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(RankineItems.DEMONYTE.get().getRegistryName().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(RankineItems.DRAGONYTE.get().getRegistryName().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(RankineItems.MANTLE_GOLEM.get().getRegistryName().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(RankineItems.DIAMOND_MANTLE_GOLEM.get().getRegistryName().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(RankineItems.PERIDOT_MANTLE_GOLEM.get().getRegistryName().getPath(), mcLoc("item/template_spawn_egg"));

        basicItem(RankineItems.BIOME_INDICATOR_GENERIC.get());
        basicItem(RankineItems.BIOME_INDICATOR_OCEAN.get());
        basicItem(RankineItems.BIOME_INDICATOR_RIVER.get());





        basicItem(RankineBlocks.FLUID_DRAIN.get());
        withExistingParent(RankineBlocks.STUMP.get());
        withExistingParent(RankineBlocks.BONE_CHAR_BLOCK.get());
        withExistingParent(RankineBlocks.SEDIMENT_FAN.get());
        withExistingParent(RankineBlocks.PCF.get());
        withExistingParent(RankineBlocks.RANKINE_BOX.get());
        withExistingParent(RankineBlocks.DIAMOND_ANVIL_CELL.get());
        withExistingParent(RankineBlocks.LASER_QUARRY.get());
        withExistingParent(RankineBlocks.TEMPLATE_TABLE.get());
        withExistingParent(RankineBlocks.BOTANIST_STATION.get());
        withExistingParent(RankineBlocks.EVAPORATION_TOWER.get());
        withExistingParent(RankineBlocks.LASER_PYLON_TOP.get());
        withExistingParent(RankineBlocks.LASER_PYLON_BASE.get());
        withExistingParent(RankineBlocks.BEEHIVE_OVEN_PIT.get());
        withExistingParent(RankineBlocks.HIGH_BEEHIVE_OVEN_PIT.get());
        withExistingParent(RankineBlocks.ALLOY_FURNACE.get());
        withExistingParent(RankineBlocks.ULTRA_HIGH_BEEHIVE_OVEN_PIT.get());
        withExistingParent(RankineBlocks.CHARCOAL_PIT.get());
        withExistingParent(RankineBlocks.GYRATORY_CRUSHER.get());
        withExistingParent(RankineBlocks.PISTON_CRUSHER.get());
        withExistingParent(RankineBlocks.INDUCTION_FURNACE.get());
        withExistingParent(RankineBlocks.ALNICO_ELECTROMAGNET.get());
        withExistingParent(RankineBlocks.RARE_EARTH_ELECTROMAGNET.get());
        withExistingParent(RankineBlocks.TRAMPOLINE.get());
        withExistingParent(RankineBlocks.SODIUM_VAPOR_LAMP.get());
        withExistingParent(RankineBlocks.TAP_BARREL.get().getRegistryName().getPath(), modLoc("block/tap_barrel/" + RankineBlocks.TAP_BARREL.get().getRegistryName().getPath()));
        withExistingParent(RankineBlocks.GAS_VENT.get());
        basicItem(RankineItems.CRUCIBLE.get());


        withExistingParent(RankineBlocks.TILLED_SOIL.get().getRegistryName().getPath(), new ResourceLocation("rankine","block/tilled_soil_loam"));

        withExistingParent(RankineBlocks.ASPHALT.get().getRegistryName().getPath(), new ResourceLocation("rankine","block/asphalt_none"));
        withExistingParent(RankineBlocks.WORN_ASPHALT.get().getRegistryName().getPath(), new ResourceLocation("rankine","block/worn_asphalt_none"));
        withExistingParent(RankineBlocks.WEATHERED_ASPHALT.get().getRegistryName().getPath(), new ResourceLocation("rankine","block/weathered_asphalt_none"));
        withExistingParent(RankineBlocks.CRACKED_ASPHALT.get().getRegistryName().getPath(), new ResourceLocation("rankine","block/cracked_asphalt_none"));
        withExistingParent(RankineBlocks.POTHOLE.get().getRegistryName().getPath(), new ResourceLocation("rankine","block/pothole"));


        basicItemAltTexture(RankineItems.ORANGE_LILY.get(), modLoc("block/" + "orange_lily_top"));
        basicItemAltTexture(RankineItems.RED_LILY.get(), modLoc("block/" + "red_lily_top"));
        basicItemAltTexture(RankineItems.WHITE_LILY.get(), modLoc("block/" + "white_lily_top"));
        basicItemAltTexture(RankineItems.GOLDENROD.get(), modLoc("block/" + "goldenrod_top"));
        basicItemAltTexture(RankineItems.BLUE_MORNING_GLORY.get(), modLoc("block/" + "blue_morning_glory_top"));
        basicItemAltTexture(RankineItems.PURPLE_MORNING_GLORY.get(), modLoc("block/" + "purple_morning_glory_top"));
        basicItemAltTexture(RankineItems.BLACK_MORNING_GLORY.get(), modLoc("block/" + "black_morning_glory_top"));



        for (Item item : Stream.of(RankineLists.MINERAL_ITEMS).flatMap(Collection::stream).collect(Collectors.toList())) {
            basicItem(item);
        }
        for (Block blk : Stream.of(RankineLists.MINERAL_STONES).flatMap(Collection::stream).collect(Collectors.toList())) {
            withExistingParent(blk);
        }

        for (String s : Arrays.asList("cast_iron_support","fiber_block","uncolored_concrete","roman_concrete","polished_roman_concrete","roman_concrete_bricks","checkered_marble","clay_bricks","refractory_bricks","high_refractory_bricks","ultra_high_refractory_bricks")) {
            if (!s.equals("cast_iron_support")) {
                withExistingParent(s+"_wall", modLoc("block/"+s+"_wall_inventory"));
            }
            withExistingParent(s, modLoc("block/"+s));
            withExistingParent(s+"_slab", modLoc("block/"+s+"_slab"));
            withExistingParent(s+"_stairs", modLoc("block/"+s+"_stairs"));
            withExistingParent(s+"_vertical_slab", modLoc("block/"+s+"_vertical_slab"));
        }

        for (Item TOOL : Stream.of(RankineLists.FLINT_TOOLS, RankineLists.BRONZE_TOOLS, RankineLists.ALLOY_TOOLS, RankineLists.PEWTER_TOOLS, RankineLists.INVAR_TOOLS, RankineLists.TITANIUM_ALLOY_TOOLS, RankineLists.STEEL_TOOLS, RankineLists.STAINLESS_STEEL_TOOLS, RankineLists.COBALT_SUPERALLOY_TOOLS, RankineLists.NICKEL_SUPERALLOY_TOOLS, RankineLists.TUNGSTEN_HEAVY_ALLOY_TOOLS, RankineLists.BLACK_GOLD_TOOLS, RankineLists.BLUE_GOLD_TOOLS, RankineLists.GREEN_GOLD_TOOLS, RankineLists.ROSE_GOLD_TOOLS, RankineLists.PURPLE_GOLD_TOOLS, RankineLists.WHITE_GOLD_TOOLS, RankineLists.AMALGAM_TOOLS).flatMap(Collection::stream).collect(Collectors.toList())) {
            basicItem(TOOL);
        }
        for (Item ARROW : RankineLists.ARROWS) {
            basicItem(ARROW);
        }

        for (Block blk : RankineLists.METAL_LADDERS) {
            basicItemAltTexture(blk.asItem(), modLoc("block/"+blk.getRegistryName().getPath()));
        }

        //EARTHY BLOCKS
        for (String s : RankineLists.NORMAL_BLOCKS) {
            withExistingParent(s, modLoc("block/"+s));
        }
        for (String s : RankineLists.ROTATION_BLOCKS) {
            withExistingParent(s, modLoc("block/"+s));
        }
        //SHEETMETALS
        for (String s : RankineLists.SHEETMETALS_S) {
            withExistingParent(s, modLoc("block/"+s));
            withExistingParent(s+"_vertical_slab", modLoc("block/"+s+"_vertical_slab"));
        }
        //MINERALS
        for (String s : RankineLists.MINERALS_AND_BLOCKS) {
            basicItem(s);
            withExistingParent(s+"_block", modLoc("block/"+s+"_block"));
        }
        //ALLOYS
        for (Item ALLOY : RankineLists.ALLOYS) {
            String name = ALLOY.getRegistryName().getPath();
            basicItem(name);
        }
        for (Block ALLOY : RankineLists.ALLOY_BLOCKS) {
            String name = ALLOY.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/"+name));
        }
        for (Block ALLOY : RankineLists.ALLOY_PEDESTALS) {
            String name = ALLOY.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/"+name));
        }
        for (Block ALLOY : RankineLists.ALLOY_POLES) {
            String name = ALLOY.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/"+name));
        }
        getBuilder("cast_iron" + "_bars").parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "block/" + "cast_iron" + "_bars");

        for (Block SOIL : RankineLists.SOILS) {
            String name = SOIL.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/"+name));
        }
        for (Block GRASSY_SOIL : RankineLists.GRASSY_SOILS) {
            String name = GRASSY_SOIL.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/"+name));
        }
        for (Block GRASSY_SOIL : RankineLists.GRASSY_PATH_BLOCKS) {
            String name = GRASSY_SOIL.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/"+name));
        }
        withExistingParent(RankineBlocks.MYCELIUM_PATH.get());
        withExistingParent(RankineBlocks.END_GRASS_BLOCK.get());
        withExistingParent(RankineBlocks.END_SOIL.get());
        withExistingParent(RankineBlocks.STICK_BLOCK.get());




        //ORES
        for (String s : RankineLists.ORES) {
            withExistingParent(s, modLoc("block/"+s+"0"));
        }

        //ELEMENTS
        for (String s : RankineLists.ELEMENTS) {
            basicItem(s+"_nugget");
            if (s.equals("silicon") || s.equals("phosphorus") || s.equals("astatine") || s.equals("sulfur")) {
                basicItem(s);
            } else {
                basicItem(s+"_ingot");
            }
            withExistingParent(s+"_block", modLoc("block/"+s+"_block"));
        }

        //GEODES
        basicItem(RankineItems.UNCUT_GEODE.get().getRegistryName().getPath());
        for (Block blk : RankineLists.GEODES) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/"+name));
        }

        for (Block blk : Stream.of(RankineLists.LEAVES, RankineLists.PLANKS, RankineLists.LOGS, RankineLists.STRIPPED_LOGS, RankineLists.WOODS, RankineLists.STRIPPED_WOODS, RankineLists.WOODEN_FENCE_GATES, RankineLists.WOODEN_SLABS, RankineLists.WOODEN_STAIRS, RankineLists.WOODEN_VERTICAL_SLABS).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name));
        }
        for (Item item : Stream.of(RankineLists.WOODEN_BOATS).flatMap(Collection::stream).collect(Collectors.toList())) {
            basicItem(item);
        }
        for (Block blk : Stream.of(RankineLists.WOODEN_FENCES, RankineLists.WOODEN_BUTTONS).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name + "_inventory"));
        }
        for (Block blk : Stream.of(RankineLists.WOODEN_PRESSURE_PLATES).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name + "_up"));
        }
        for (Block blk : Stream.of(RankineLists.SAPLINGS).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            basicItemAltTexture(blk.asItem(), modLoc("block/" + name));
        }
        for (Block blk : Stream.of(RankineLists.WOODEN_DOORS,RankineLists.METAL_DOORS).flatMap(Collection::stream).collect(Collectors.toList())) {
            basicItem(blk.asItem());
        }
        for (Block blk : Stream.of(RankineLists.WOODEN_TRAPDOORS,RankineLists.METAL_TRAPDOORS).flatMap(Collection::stream).collect(Collectors.toList())) {
            withExistingParent(blk.getRegistryName().getPath(), modLoc("block/"+blk.getRegistryName().getPath()+"_bottom"));
        }
        //STONES
        for (Block blk : Stream.of(RankineLists.STONE, RankineLists.POLISHED_STONE, RankineLists.STONE_BRICKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name));
        }
        for (Block blk : Stream.of(RankineLists.STONE_SLAB, RankineLists.POLISHED_STONE_SLAB, RankineLists.STONE_BRICKS_SLAB).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name));
        }
        for (Block blk : Stream.of(RankineLists.STONE_STAIRS, RankineLists.POLISHED_STONE_STAIRS, RankineLists.STONE_BRICKS_STAIRS).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name));
        }
        for (Block blk : Stream.of(RankineLists.STONE_WALL, RankineLists.POLISHED_STONE_WALL, RankineLists.STONE_BRICKS_WALL).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name + "_inventory"));
        }
        for (Block blk : Stream.of(RankineLists.STONE_VERTICAL_SLAB, RankineLists.POLISHED_STONE_VERTICAL_SLAB, RankineLists.STONE_BRICKS_VERTICAL_SLAB).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name));
        }
        for (Block blk : Stream.of(RankineLists.STONE_PRESSURE_PLATE, RankineLists.STONE_BRICKS_PRESSURE_PLATE).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name + "_up"));
        }
        for (Block blk : Stream.of(RankineLists.STONE_BUTTON).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name + "_inventory"));
        }
        for (Block blk : Stream.of(RankineLists.STONE_PILLARS).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name));
        }


        for (Block blk : RankineLists.MINERAL_WOOL) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/"+name));
        }
        for (Block blk : RankineLists.FIBER_BLOCK) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/"+name));
        }
        for (Block blk : RankineLists.FIBER_MAT) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/"+name));
        }
        for (Block blk : RankineLists.LEDS) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name));
        }
        for (Block blk : RankineLists.GAS_BLOCKS) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name));
        }

    }

    private ItemModelBuilder withExistingParent(Block blk) {
        return withExistingParent(blk.getRegistryName().getPath(), modLoc("block/" + blk.getRegistryName().getPath()));
    }
    private ItemModelBuilder basicItem(String name) {
        return getBuilder(name).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/" + name);
    }
    private ItemModelBuilder basicItem(Item name) {
        return getBuilder(name.getRegistryName().getPath()).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/" + name.getRegistryName().getPath());
    }
    private ItemModelBuilder basicItem(Block name) {
        return getBuilder(name.getRegistryName().getPath()).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/" + name.getRegistryName().getPath());
    }
    private ItemModelBuilder basicItemAltTexture(Item item, ResourceLocation texture) {
        return getBuilder(item.getRegistryName().getPath()).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", texture);
    }

}
