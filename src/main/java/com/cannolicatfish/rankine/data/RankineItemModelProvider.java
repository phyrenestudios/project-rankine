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
        for (Item item : Stream.of(RankineLists.JAMS,RankineLists.GRAINS,RankineLists.GAS_BOTTLES,RankineLists.MINERAL_ITEMS).flatMap(Collection::stream).collect(Collectors.toList())) {
            basicItem(item);
        }

        for (Block BLOCK : Stream.of(RankineLists.STONE_SLABS,RankineLists.POLISHED_STONE_SLABS,RankineLists.STONE_BRICKS_SLABS,RankineLists.SHEETMETAL_SLABS,RankineLists.WOODEN_SLABS,RankineLists.BRICKS_SLAB,RankineLists.MISC_SLABS).flatMap(Collection::stream).collect(Collectors.toList())) {
            slabParent(BLOCK);
        }
        for (Block BLOCK : Stream.of(RankineLists.STONE_VERTICAL_SLABS,RankineLists.POLISHED_STONE_VERTICAL_SLABS,RankineLists.STONE_BRICKS_VERTICAL_SLABS,RankineLists.SHEETMETAL_VERTICAL_SLABS,RankineLists.WOODEN_VERTICAL_SLABS,RankineLists.BRICKS_VERTICAL_SLAB,RankineLists.MISC_VERTICAL_SLABS).flatMap(Collection::stream).collect(Collectors.toList())) {
            verticalSlabParent(BLOCK);
        }
        for (Block BLOCK : Stream.of(RankineLists.STONE_STAIRS,RankineLists.POLISHED_STONE_STAIRS,RankineLists.STONE_BRICKS_STAIRS,RankineLists.WOODEN_STAIRS,RankineLists.BRICKS_STAIRS,RankineLists.MISC_STAIRS).flatMap(Collection::stream).collect(Collectors.toList())) {
            stairsParent(BLOCK);
        }
        for (Block BLOCK : Stream.of(RankineLists.STONE_WALLS,RankineLists.POLISHED_STONE_WALLS,RankineLists.STONE_BRICKS_WALLS,RankineLists.BRICKS_WALL,RankineLists.MISC_WALLS).flatMap(Collection::stream).collect(Collectors.toList())) {
            wallParent(BLOCK);
        }

        for (Block blk : Stream.of(RankineLists.LEAVES, RankineLists.PLANKS, RankineLists.LOGS, RankineLists.STRIPPED_LOGS, RankineLists.WOODS, RankineLists.STRIPPED_WOODS, RankineLists.WOODEN_FENCE_GATES, RankineLists.WOODEN_SLABS, RankineLists.WOODEN_STAIRS).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name));
        }

        for (Block BLOCK : Stream.of(
                RankineLists.STONES,
                RankineLists.INFESTED_STONES,
                RankineLists.STONE_BRICKS,
                RankineLists.POLISHED_STONES,
                RankineLists.BRICKS,
                RankineLists.ALLOY_PEDESTALS,
                RankineLists.ALLOY_POLES,
                RankineLists.ALLOY_BLOCKS,
                RankineLists.LOGS,
                RankineLists.LEAVES,
                RankineLists.PLANKS,
                RankineLists.STRIPPED_WOODS,
                RankineLists.STRIPPED_LOGS,
                RankineLists.WOODEN_FENCE_GATES,
                RankineLists.FIBER_BLOCK,
                RankineLists.FIBER_MAT,
                RankineLists.SOILS,
                RankineLists.GRASSY_SOILS,
                RankineLists.PATH_BLOCKS,
                RankineLists.GAS_BLOCKS,
                RankineLists.MINERAL_WOOL,
                RankineLists.LEDS,
                RankineLists.MINERAL_BLOCKS,
                RankineLists.ELEMENT_BLOCKS,
                RankineLists.STANDARD_BLOCKS,
                RankineLists.ROTATION_BLOCKS,
                RankineLists.SHEETMETALS
                ).flatMap(Collection::stream).collect(Collectors.toList())) {
            withExistingParent(BLOCK);
        }

        for (Block blk : Arrays.asList(
                RankineBlocks.ANTIMATTER.get(),
                RankineBlocks.SOD_BLOCK.get(),
                RankineBlocks.UNAMED_EXPLOSIVE.get(),
                RankineBlocks.ENDER_SHIRO.get(),
                RankineBlocks.LIGHT_GRAVEL.get(),
                RankineBlocks.DARK_GRAVEL.get(),
                RankineBlocks.FIRE_CLAY.get(),
                RankineBlocks.KAOLIN.get(),
                RankineBlocks.STICK_BLOCK.get(),
                RankineBlocks.MYCELIUM_PATH.get(),
                RankineBlocks.KIMBERLITIC_DIAMOND_ORE.get(),
                RankineBlocks.PORPHYRY_COPPER.get()
        )) {
            withExistingParent(blk);
        }


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
        basicItem(RankineItems.POKEBERRIES.get());
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
        basicItemAltTexture(RankineItems.AGED_CHEESE.get(), modLoc("item/aged_cheese_wheel"));
        basicItemAltTexture(RankineItems.UNAGED_CHEESE.get(), modLoc("item/unaged_cheese_wheel"));

        basicItem(RankineItems.VULCANIZED_RUBBER.get());
        basicItem(RankineItems.BITUMEN.get());
        basicItem(RankineItems.GWIHABAITE.get());
        basicItem(RankineItems.FIRE_CLAY_BALL.get());
        basicItem(RankineItems.KAOLINITE.get());
        basicItem(RankineItems.BONE_CHAR.get());
        basicItem(RankineItems.NETHERITE_NUGGET.get());
        basicItem(RankineItems.SLAG.get());
        basicItem(RankineItems.TRONA.get());
        basicItem(RankineItems.POTASH.get());
        basicItem(RankineItems.VANADIUM_PENTOXIDE.get());
        basicItem(RankineItems.ASBESTOS.get());
        basicItem(RankineItems.THENARDITE.get());
        basicItem(RankineItems.BORAX.get());
        basicItem(RankineItems.SODIUM_SULFIDE.get());
        basicItem(RankineItems.SODIUM_HYDROXIDE.get());
        basicItem(RankineItems.SODIUM_CARBONATE.get());
        basicItem(RankineItems.SALTPETER.get());
        basicItem(RankineItems.CEMENT_MIX.get());
        basicItem(RankineItems.MORTAR.get());
        basicItem(RankineItems.SAWDUST.get());
        basicItem(RankineItems.ASH.get());
        basicItem(RankineItems.BONE_ASH.get());
        basicItem(RankineItems.POZZOLANA.get());
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
        //basicItem(RankineItems.YAG_ROD.get());
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
        basicItem(RankineItems.GROUND_TAP.get());
        basicItem(RankineItems.METAL_PIPE.get());
        basicItem(RankineItems.TREE_TAP.get());
        basicItem(RankineItems.LODESTONE.get());
        basicItem(RankineItems.ELEMENT.get());
        basicItem(RankineItems.BUILDING_TOOL.get());
        basicItem(RankineItems.ORE_DETECTOR.get());
        basicItem(RankineItems.PROSPECTING_STICK.get());
        basicItem(RankineItems.DOWSING_ROD.get());
        basicItem(RankineItems.PACKAGED_TOOL.get());
        basicItem(RankineItems.ALNICO_MAGNET.get());
        basicItem(RankineItems.RARE_EARTH_MAGNET.get());
        basicItem(RankineItems.WOODEN_GOLD_PAN.get());
        basicItem(RankineItems.PEWTER_GOLD_PAN.get());
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
        basicItem(RankineItems.SULFURIC_ACID_BUCKET.get());
        basicItem(RankineItems.COIN.get());
        basicItem(RankineItems.MAGNESIUM_BATTERY.get());
        basicItem(RankineItems.LEAD_ACID_BATTERY.get());
        basicItem(RankineItems.VANADIUM_REDOX_BATTERY.get());
        basicItem(RankineItems.ZINC_BROMINE_BATTERY.get());
        basicItem(RankineItems.SODIUM_SULFUR_BATTERY.get());
        basicItem(RankineItems.LITHIUM_ION_BATTERY.get());



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





        //basicItem(RankineBlocks.FLUID_DRAIN.get());
        withExistingParent(RankineBlocks.STUMP.get());
        withExistingParent(RankineBlocks.BONE_CHAR_BLOCK.get());
        withExistingParent(RankineBlocks.SEDIMENT_FAN.get());
        withExistingParent(RankineBlocks.PCF.get());
        withExistingParent(RankineBlocks.RANKINE_BOX.get());
        withExistingParent(RankineBlocks.DIAMOND_ANVIL_CELL.get());
        //withExistingParent(RankineBlocks.LASER_QUARRY.get());
        withExistingParent(RankineBlocks.TEMPLATE_TABLE.get());
        withExistingParent(RankineBlocks.MATERIAL_TESTING_TABLE.get());
        withExistingParent(RankineBlocks.BOTANIST_STATION.get());
        withExistingParent(RankineBlocks.EVAPORATION_TOWER.get());
        //withExistingParent(RankineBlocks.LASER_PYLON_TOP.get());
        //withExistingParent(RankineBlocks.LASER_PYLON_BASE.get());
        withExistingParent(RankineBlocks.REACTION_CHAMBER_CORE.get());
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
        withExistingParent(RankineBlocks.CEMENT_POLE.get());
        withExistingParent(RankineBlocks.SODIUM_VAPOR_LAMP.get());
        //withExistingParent(RankineBlocks.TAP_BARREL.get().getRegistryName().getPath(), modLoc("block/tap_barrel/" + RankineBlocks.TAP_BARREL.get().getRegistryName().getPath()));
        withExistingParent(RankineBlocks.CARBON_DIOXIDE_FUMAROLE.get());
        withExistingParent(RankineBlocks.HYDROGEN_CHLORIDE_FUMAROLE.get());
        withExistingParent(RankineBlocks.HYDROGEN_SULFIDE_FUMAROLE.get());
        withExistingParent(RankineBlocks.SULFUR_DIOXIDE_FUMAROLE.get());
        basicItem(RankineItems.CRUCIBLE.get());


        withExistingParent(RankineBlocks.TILLED_SOIL.get().getRegistryName().getPath(), new ResourceLocation("rankine","block/tilled_soil_loam"));

        withExistingParent(RankineBlocks.ASPHALT_0.get().getRegistryName().getPath(), new ResourceLocation("rankine","block/asphalt/asphalt0_age0_none"));
        withExistingParent(RankineBlocks.ASPHALT_1.get().getRegistryName().getPath(), new ResourceLocation("rankine","block/asphalt/asphalt1_age0_none"));
        withExistingParent(RankineBlocks.ASPHALT_2.get().getRegistryName().getPath(), new ResourceLocation("rankine","block/asphalt/asphalt2_age0_none"));
        withExistingParent(RankineBlocks.ASPHALT_3.get().getRegistryName().getPath(), new ResourceLocation("rankine","block/asphalt/asphalt3_age0_none"));
        withExistingParent(RankineBlocks.POTHOLE.get().getRegistryName().getPath(), new ResourceLocation("rankine","block/pothole"));


        basicItemAltTexture(RankineItems.CRIMSON_CLOVER.get(), modLoc("block/" + "crimson_clover"));
        basicItemAltTexture(RankineItems.RED_CLOVER.get(), modLoc("block/" + "red_clover"));
        basicItemAltTexture(RankineItems.WHITE_CLOVER.get(), modLoc("block/" + "white_clover"));
        basicItemAltTexture(RankineItems.YELLOW_CLOVER.get(), modLoc("block/" + "yellow_clover"));
        basicItemAltTexture(RankineItems.STINGING_NETTLE.get(), modLoc("block/" + "stinging_nettle"));
        basicItemAltTexture(RankineItems.SHORT_GRASS.get(), modLoc("block/" + "short_grass0"));
        basicItemAltTexture(RankineItems.ORANGE_LILY.get(), modLoc("block/" + "orange_lily_top"));
        basicItemAltTexture(RankineItems.RED_LILY.get(), modLoc("block/" + "red_lily_top"));
        basicItemAltTexture(RankineItems.WHITE_LILY.get(), modLoc("block/" + "white_lily_top"));
        basicItemAltTexture(RankineItems.GOLDENROD.get(), modLoc("block/" + "goldenrod_top"));
        basicItemAltTexture(RankineItems.BLUE_MORNING_GLORY.get(), modLoc("block/" + "blue_morning_glory_top"));
        basicItemAltTexture(RankineItems.PURPLE_MORNING_GLORY.get(), modLoc("block/" + "purple_morning_glory_top"));
        basicItemAltTexture(RankineItems.BLACK_MORNING_GLORY.get(), modLoc("block/" + "black_morning_glory_top"));



        
        slabParent(RankineBlocks.SOD_BLOCK_SLAB.get());
        verticalSlabParent(RankineBlocks.SOD_BLOCK_VERTICAL_SLAB.get());
        stairsParent(RankineBlocks.SOD_BLOCK_STAIRS.get());
        wallParent(RankineBlocks.SOD_BLOCK_WALL.get());
        
        for (Item TOOL : Stream.of(RankineLists.WOODEN_TOOLS, RankineLists.FLINT_TOOLS, RankineLists.BRONZE_TOOLS, RankineLists.ALLOY_TOOLS, RankineLists.PEWTER_TOOLS, RankineLists.INVAR_TOOLS, RankineLists.TITANIUM_ALLOY_TOOLS, RankineLists.STEEL_TOOLS, RankineLists.STAINLESS_STEEL_TOOLS, RankineLists.COBALT_SUPERALLOY_TOOLS, RankineLists.NICKEL_SUPERALLOY_TOOLS, RankineLists.TUNGSTEN_HEAVY_ALLOY_TOOLS, RankineLists.BLACK_GOLD_TOOLS, RankineLists.BLUE_GOLD_TOOLS, RankineLists.GREEN_GOLD_TOOLS, RankineLists.ROSE_GOLD_TOOLS, RankineLists.PURPLE_GOLD_TOOLS, RankineLists.WHITE_GOLD_TOOLS, RankineLists.AMALGAM_TOOLS, RankineLists.ENDER_AMALGAM_TOOLS).flatMap(Collection::stream).collect(Collectors.toList())) {
            basicItemHandheld(TOOL);
        }
        for (Item ARROW : RankineLists.ARROWS) {
            basicItem(ARROW);
        }

        for (Block blk : RankineLists.METAL_LADDERS) {
            basicItemAltTexture(blk.asItem(), modLoc("block/"+blk.getRegistryName().getPath()));
        }


        //ALLOYS
        for (Item ALLOY : RankineLists.ALLOY_NUGGETS) {
            String name = ALLOY.getRegistryName().getPath();
            basicItem(name);
        }
        for (Item ALLOY : RankineLists.ALLOY_INGOTS) {
            String name = ALLOY.getRegistryName().getPath();
            basicItem(name);
        }
        getBuilder("cast_iron" + "_bars").parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "block/" + "cast_iron" + "_bars");





        //ORES
        for (Block blk : Stream.of(RankineLists.NATIVE_ORES, RankineLists.CRUSHING_ORES, RankineLists.SPECIAL_ORES).flatMap(Collection::stream).collect(Collectors.toList())) {
            String regName = blk.getRegistryName().getPath();
            withExistingParent(regName, modLoc("block/"+regName+"0"));
        }



        //GEODES
        basicItem(RankineItems.UNCUT_GEODE.get().getRegistryName().getPath());
        for (Block blk : RankineLists.GEODES) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/"+name));
        }


        for (Item item : Stream.of(RankineLists.WOODEN_BOATS,RankineLists.ELEMENT_INGOTS,RankineLists.ELEMENT_NUGGETS).flatMap(Collection::stream).collect(Collectors.toList())) {
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

        for (Block blk : Stream.of(RankineLists.STONE_PRESSURE_PLATES, RankineLists.STONE_BRICKS_PRESSURE_PLATES).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name + "_up"));
        }
        for (Block blk : Stream.of(RankineLists.STONE_BUTTONS, RankineLists.WOODEN_BUTTONS).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name + "_inventory"));
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
    private ItemModelBuilder basicItemHandheld(Item name) {
        return getBuilder(name.getRegistryName().getPath()).parent(getExistingFile(mcLoc("item/handheld"))).texture("layer0", "item/" + name.getRegistryName().getPath());
    }
    private ItemModelBuilder basicItem(Block name) {
        return getBuilder(name.getRegistryName().getPath()).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/" + name.getRegistryName().getPath());
    }
    private ItemModelBuilder basicItemAltTexture(Item item, ResourceLocation texture) {
        return getBuilder(item.getRegistryName().getPath()).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", texture);
    }
    
    private ItemModelBuilder slabParent(Block BLK) {
        return withExistingParent(BLK.getRegistryName().getPath(), modLoc("block/" + BLK.getRegistryName().getPath()));
    }    
    private ItemModelBuilder verticalSlabParent(Block BLK) {
        return withExistingParent(BLK.getRegistryName().getPath(), modLoc("block/" + BLK.getRegistryName().getPath()+"_straight"));
    }    
    private ItemModelBuilder stairsParent(Block BLK) {
        return withExistingParent(BLK.getRegistryName().getPath(), modLoc("block/" + BLK.getRegistryName().getPath()));
    }    
    private ItemModelBuilder wallParent(Block BLK) {
        return withExistingParent(BLK.getRegistryName().getPath(), modLoc("block/" + BLK.getRegistryName().getPath()+"_inventory"));
    }
    
}
