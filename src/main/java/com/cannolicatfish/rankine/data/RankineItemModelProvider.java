package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.block_groups.RankineStone;
import com.cannolicatfish.rankine.blocks.block_groups.RankineWood;
import com.cannolicatfish.rankine.blocks.buildingmodes.BuildingModeBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.items.alloys.AlloyCrowbarItem;
import com.cannolicatfish.rankine.items.tools.SpearItem;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
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

        for (RankineStone Stone : RankineLists.RANKINE_STONES) {
            withExistingParent(Stone.getStone());
            buildingModeItem(Stone.getPolished().asItem(), ((BuildingModeBlock) Stone.getPolished()).getMaxStyles());
            buildingModeItem(Stone.getBricks().asItem(), ((BuildingModeBlock) Stone.getBricks()).getMaxStyles());
            buildingModeItem(Stone.getMossyBricks().asItem(), ((BuildingModeBlock) Stone.getMossyBricks()).getMaxStyles());
            slabParent(Stone.getSlab());
            slabParent(Stone.getPolishedSlab());
            slabParent(Stone.getBricksSlab());
            slabParent(Stone.getMossyBricksSlab());
            stairsParent(Stone.getStairs());
            stairsParent(Stone.getPolishedStairs());
            stairsParent(Stone.getBricksStairs());
            stairsParent(Stone.getMossyBricksStairs());
            wallParent(Stone.getWall());
            wallParent(Stone.getPolishedWall());
            wallParent(Stone.getBricksWall());
            wallParent(Stone.getMossyBricksWall());
            withExistingParent(Stone.getPressurePlate().getRegistryName().getPath(), modLoc("block/" + Stone.getPressurePlate().getRegistryName().getPath() + "_up"));
            withExistingParent(Stone.getBricksPressurePlate().getRegistryName().getPath(), modLoc("block/" + Stone.getBricksPressurePlate().getRegistryName().getPath() + "_up"));
            withExistingParent(Stone.getButton().getRegistryName().getPath(), modLoc("block/" + Stone.getButton().getRegistryName().getPath() + "_inventory"));
            withExistingParent(Stone.getCobble().getRegistryName().getPath(), RankineBlockStateProvider.getBlockRSL(Stone.getCobble().getRegistryName().getPath()+"1"));
            withExistingParent(Stone.getColumn().getRegistryName().getPath(), RankineBlockStateProvider.getBlockRSL(Stone.getColumn().getRegistryName().getPath()+"1"));
            withExistingParent(Stone.getInfested());
        }

        for (RankineWood Wood : RankineLists.RANKINE_WOODS) {
            if (Wood.hasLogs()) {
                withExistingParent(Wood.getLog());
                withExistingParent(Wood.getStrippedLog());
                withExistingParent(Wood.getWood());
                withExistingParent(Wood.getStrippedWood());
                withExistingParent(Wood.getHollowLog());
            }
            buildingModeItem(Wood.getPlanks().asItem(), ((BuildingModeBlock) Wood.getPlanks()).getMaxStyles());
            slabParent(Wood.getSlab());
            stairsParent(Wood.getStairs());
            withExistingParent(Wood.getFence().getRegistryName().getPath(), modLoc("block/" + Wood.getFence().getRegistryName().getPath() + "_inventory"));
            withExistingParent(Wood.getFenceGate());
            basicItem(Wood.getDoor().asItem());
            withExistingParent(Wood.getTrapdoor().getRegistryName().getPath(), modLoc("block/"+Wood.getTrapdoor().getRegistryName().getPath()+"_bottom"));
            withExistingParent(Wood.getPressurePlate().getRegistryName().getPath(), modLoc("block/" + Wood.getPressurePlate().getRegistryName().getPath() + "_up"));
            withExistingParent(Wood.getButton().getRegistryName().getPath(), modLoc("block/" + Wood.getButton().getRegistryName().getPath() + "_inventory"));
            basicItem(Wood.getSignItem());
            buildingModeItem(Wood.getBookshelf().asItem(), ((BuildingModeBlock) Wood.getBookshelf()).getMaxStyles());
            basicItem(Wood.getBoat());
            if (Wood.isTree()) {
                withExistingParent(Wood.getLeaves().getRegistryName().getPath(), new ResourceLocation("rankine","block/"+Wood.getLeaves().getRegistryName().getPath()+"_age0"));
                basicItemAltTexture(Wood.getSapling().asItem(), modLoc("block/" + Wood.getSapling().getRegistryName().getPath()));
                withExistingParent(Wood.getLeafLitter());
            }
        }

        //food items
        for (Item item : Stream.of(RankineLists.SEEDS,RankineLists.GRAINS,RankineLists.RAW_FISH,RankineLists.COOKED_FISH,RankineLists.GAS_BOTTLES,RankineLists.MINERAL_ITEMS).flatMap(Collection::stream).collect(Collectors.toList())) {
            basicItem(item);
        }
        for (Block BLOCK : Stream.of(RankineLists.VANILLA_BRICKS_SLABS,RankineLists.BRICKS_SLAB,RankineLists.CUT_SANDSTONE_SLABS,RankineLists.SANDSTONE_SLABS,RankineLists.SMOOTH_SANDSTONE_SLABS,RankineLists.MISC_SLABS).flatMap(Collection::stream).collect(Collectors.toList())) {
            slabParent(BLOCK);
        }
        for (Block BLOCK : Stream.of(RankineLists.VANILLA_BRICKS_STAIRS,RankineLists.BRICKS_STAIRS,RankineLists.SANDSTONE_STAIRS,RankineLists.SMOOTH_SANDSTONE_STAIRS,RankineLists.MISC_STAIRS,RankineLists.CONCRETE_STAIRS).flatMap(Collection::stream).collect(Collectors.toList())) {
            stairsParent(BLOCK);
        }
        for (Block BLOCK : Stream.of(RankineLists.VANILLA_BRICKS_WALLS,RankineLists.BRICKS_WALL,RankineLists.SANDSTONE_WALLS,RankineLists.SMOOTH_SANDSTONE_WALLS,RankineLists.MISC_WALLS,RankineLists.CONCRETE_WALLS).flatMap(Collection::stream).collect(Collectors.toList())) {
            wallParent(BLOCK);
        }

        for (Block BLOCK : Stream.of(
                RankineLists.DRIPSTONES,
                RankineLists.SANDSTONES,
                RankineLists.SMOOTH_SANDSTONES,
                RankineLists.CUT_SANDSTONES,
                RankineLists.CHISELED_SANDSTONES,
                RankineLists.ALLOY_PEDESTALS,
                RankineLists.ALLOY_SHEETMETALS,
                RankineLists.ALLOY_BLOCKS,
                RankineLists.HOLLOW_LOGS,
                RankineLists.LEAF_LITTERS,
                RankineLists.FIBER_BLOCK,
                RankineLists.FIBER_MAT,
                RankineLists.CRUSHING_HEADS,
                RankineLists.SOIL_BLOCKS,
                RankineLists.MUD_BLOCKS,
                RankineLists.COARSE_SOIL_BLOCKS,
                RankineLists.GRASS_BLOCKS,
                RankineLists.PODZOL_BLOCKS,
                RankineLists.MYCELIUM_BLOCKS,
                RankineLists.PATH_BLOCKS,
                RankineLists.GAS_BLOCKS,
                RankineLists.MINERAL_WOOL,
                RankineLists.LEDS,
                RankineLists.MINERAL_BLOCKS,
                RankineLists.ELEMENT_BLOCKS,
                RankineLists.STANDARD_BLOCKS,
                RankineLists.MUSHROOM_BLOCKS,
                RankineLists.BALES,
                RankineLists.ROTATION_BLOCKS,
                RankineLists.LIGHTNING_GLASSES,
                RankineLists.ELECTROMAGNETS,
                RankineLists.SHEETMETALS
                ).flatMap(Collection::stream).collect(Collectors.toList())) {
            withExistingParent(BLOCK);
        }

        for (Block blk : Arrays.asList(

                RankineBlocks.ANTIMATTER.get(),
                RankineBlocks.FLOOD_GATE.get(),
                RankineBlocks.ALLUVIUM.get(),
                RankineBlocks.SOD_BLOCK.get(),
                RankineBlocks.UNAMED_EXPLOSIVE.get(),
                RankineBlocks.ENDER_SHIRO.get(),
                RankineBlocks.LIGHT_GRAVEL.get(),
                RankineBlocks.DARK_GRAVEL.get(),
                RankineBlocks.FIRE_CLAY.get(),
                RankineBlocks.PORCELAIN_CLAY.get(),
                RankineBlocks.KAOLIN.get(),
                RankineBlocks.COB.get(),
                RankineBlocks.STICK_BLOCK.get(),
                RankineBlocks.MYCELIUM_PATH.get(),
                RankineBlocks.KIMBERLITIC_DIAMOND_ORE.get(),
                RankineBlocks.PORPHYRY_COPPER.get()
        )) {
            withExistingParent(blk);
        }



        for (Item item : Stream.of(RankineLists.ELEMENT_INGOTS,RankineLists.ELEMENT_NUGGETS).flatMap(Collection::stream).collect(Collectors.toList())) {
            basicItem(item);
        }
        basicItem(RankineItems.SOLDER.get());
        basicItem(RankineItems.LEATHER_CANTEEN.get());
        basicItem(RankineItems.STAINLESS_STEEL_CANTEEN.get());
        basicItem(RankineItems.BLACK_WALNUT.get());
        basicItem(RankineItems.COCONUT.get());
        basicItem(RankineItems.ALOE.get());
        basicItem(RankineItems.RICE.get());
        basicItem(RankineItems.OATS.get());
        basicItem(RankineItems.RYE.get());
        basicItem(RankineItems.SORGHUM.get());
        basicItem(RankineItems.BARLEY.get());
        basicItem(RankineItems.MILLET.get());
        basicItem(RankineItems.ASPARAGUS.get());
        basicItem(RankineItems.CORN_EAR.get());
        basicItem(RankineItems.COTTON.get());
        basicItem(RankineItems.JUTE.get());
        basicItem(RankineItems.TOFU.get());
        basicItem(RankineItems.COOKED_TOFU.get());
        basicItem(RankineItems.TOFU_CURRY.get());
        basicItem(RankineItems.SOYBEANS.get());
        basicItem(RankineItems.SOY_MILK.get());
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
        basicItem(RankineItems.INNER_BARK.get());
        basicItem(RankineItems.SMOULDERING_TINDER_CONK.get());
        basicItem(RankineItems.ROASTED_ASPARAGUS.get());
        basicItem(RankineItems.ROASTED_WALNUT.get());
        basicItem(RankineItems.TOASTED_COCONUT.get());
        basicItem(RankineItems.GARLAND.get());
        basicItem(RankineItems.FOUR_LEAF_CLOVER.get());
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
        basicItem(RankineItems.CORN_STALK.get());
        basicItemAltTexture(RankineItems.AGED_CHEESE.get(), modLoc("item/aged_cheese_wheel"));
        basicItemAltTexture(RankineItems.UNAGED_CHEESE.get(), modLoc("item/unaged_cheese_wheel"));
        basicItemAltTexture(RankineItems.GEODE.get(), modLoc("item/geode"));
        basicItemAltTexture(RankineItems.WILLOW_BRANCHLET.get(), modLoc("block/willow_branchlet_plant"));
        basicItemAltTexture(RankineItems.LOCUST_SPINE.get(), modLoc("block/locust_spine"));
        basicItemAltTexture(RankineItems.SLATE_STEPPING_STONES.get(), modLoc("item/slate_stepping_stones"));
        basicItemHandheld(RankineItems.DOWSING_ROD.get());

        basicItem(RankineItems.VULCANIZED_RUBBER.get());
        basicItem(RankineItems.BITUMEN.get());
        basicItem(RankineItems.GWIHABAITE.get());
        basicItem(RankineItems.FIRE_CLAY_BALL.get());
        basicItem(RankineItems.PORCELAIN_CLAY_BALL.get());
        basicItem(RankineItems.KAOLINITE.get());
        basicItem(RankineItems.BONE_CHAR.get());
        basicItem(RankineItems.COPPER_NUGGET.get());
        basicItem(RankineItems.NETHERITE_NUGGET.get());
        basicItem(RankineItems.SLAG.get());
        basicItem(RankineItems.TRONA.get());
        basicItem(RankineItems.POTASH.get());
        basicItem(RankineItems.BORON_TRIOXIDE.get());
        basicItem(RankineItems.TUNGSTEN_TRIOXIDE.get());
        basicItem(RankineItems.VANADIUM_PENTOXIDE.get());
        basicItem(RankineItems.SODIUM_FLUOROSILICATE.get());
        basicItem(RankineItems.YELLOWCAKE.get());
        basicItem(RankineItems.ASBESTOS.get());
        basicItem(RankineItems.THENARDITE.get());
        basicItem(RankineItems.BORAX.get());
        basicItem(RankineItems.SODIUM_SULFIDE.get());
        basicItem(RankineItems.LITHIUM_HYDROXIDE.get());
        basicItem(RankineItems.SODIUM_HYDROXIDE.get());
        basicItem(RankineItems.POTASSIUM_HYDROXIDE.get());
        basicItem(RankineItems.RUBIDIUM_HYDROXIDE.get());
        basicItem(RankineItems.CESIUM_HYDROXIDE.get());
        basicItem(RankineItems.FRANCIUM_HYDROXIDE.get());
        basicItem(RankineItems.BETA_ALUMINA_SOLID_ELECTROLYTE.get());
        basicItem(RankineItems.TUNGSTEN_CARBIDE.get());
        basicItem(RankineItems.LITHIUM_COBALT_OXIDE.get());
        basicItem(RankineItems.SODIUM_CARBONATE.get());
        basicItem(RankineItems.SALTPETER.get());
        basicItem(RankineItems.CEMENT_MIX.get());
        basicItem(RankineItems.MORTAR.get());
        basicItem(RankineItems.SAWDUST.get());
        basicItem(RankineItems.ASH.get());
        basicItem(RankineItems.BONE_ASH.get());
        basicItem(RankineItems.POZZOLANA.get());
        basicItem(RankineItems.METEORIC_IRON.get());
        basicItem(RankineItems.OSMIRIDIUM.get());
        basicItem(RankineItems.COMPOST.get());
        basicItem(RankineItems.BIOMASS.get());
        basicItem(RankineItems.COMPRESSED_BIOMASS.get());
        basicItem(RankineItems.DRIED_BAMBOO.get());
        basicItem(RankineItems.BEAVER_PELT.get());
        basicItem(RankineItems.ROPE.get());
        basicItem(RankineItems.BLEACH.get());
        basicItem(RankineItems.ICE_MELT.get());
        basicItem(RankineItems.HERBICIDE.get());
        basicItem(RankineItems.FERTILIZER.get());
        basicItem(RankineItems.SYNTHETIC_LEATHER.get());
        basicItem(RankineItems.CALCITE.get());
        basicItem(RankineItems.PULP.get());
        basicItem(RankineItems.ALKALI_CELLULOSE.get());
        basicItem(RankineItems.DRY_RUBBER.get());
        basicItem(RankineItems.INDIUM_TIN_OXIDE.get());
        basicItem(RankineItems.CADMIUM_TELLURIDE_CELL.get());
        basicItem(RankineItems.SILICON_GERMANIUM_THERMOCOUPLE.get());
        basicItem(RankineItems.SODIUM_ARC_TUBE.get());
        basicItem(RankineItems.GRAPHITE_ELECTRODE.get());
        basicItem(RankineItems.HARD_CARBON_ELECTRODE.get());
        basicItem(RankineItems.SADDLE_TREE.get());
        basicItem(RankineItems.CANNONBALL.get());
        basicItem(RankineItems.CARCASS.get());
        basicItem(RankineItems.ENDERBALL.get());
        basicItem(RankineItems.PUMICE_SOAP.get());
        basicItem(RankineItems.REFRACTORY_BRICK.get());
        basicItem(RankineItems.HIGH_REFRACTORY_BRICK.get());
        basicItem(RankineItems.ULTRA_HIGH_REFRACTORY_BRICK.get());
        basicItem(RankineItems.TAP_LINE.get());
        basicItem(RankineItems.GROUND_TAP.get());
        basicItem(RankineItems.METAL_PIPE.get());
        basicItem(RankineItems.TREE_TAP.get());
        basicItem(RankineItems.LODESTONE.get());
        basicItem(RankineItems.EMERGENCY_FLOTATION_DEVICE.get());
        basicItem(RankineItems.ELEMENT.get());
        basicItem(RankineItems.BUILDING_TOOL.get());
        basicItem(RankineItems.ORE_DETECTOR.get());
        basicItem(RankineItems.PROSPECTING_STICK.get());
        basicItem(RankineItems.PACKAGED_TOOL.get());
        basicItem(RankineItems.PACKAGED_ARMOR.get());
        basicItem(RankineItems.SIMPLE_MAGNET.get());
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
        basicItem(RankineItems.MAGNETOMETER.get());
        basicItem(RankineItems.THERMOMETER.get());
        basicItem(RankineItems.HARDNESS_TESTER.get());
        basicItem(RankineItems.TOTEM_OF_BLAZING.get());
        basicItem(RankineItems.TOTEM_OF_COBBLING.get());
        basicItem(RankineItems.TOTEM_OF_ENDURING.get());
        basicItem(RankineItems.TOTEM_OF_HASTENING.get());
        basicItem(RankineItems.TOTEM_OF_INFUSING.get());
        basicItem(RankineItems.TOTEM_OF_INVIGORATING.get());
        basicItem(RankineItems.TOTEM_OF_LEVITATING.get());
        basicItem(RankineItems.TOTEM_OF_PROMISING.get());
        basicItem(RankineItems.TOTEM_OF_REPULSING.get());
        basicItem(RankineItems.TOTEM_OF_SOFTENING.get());
        basicItem(RankineItems.TOTEM_OF_TIMESAVING.get());
        basicItem(RankineItems.ELEMENT_INDEXER.get());
        basicItem(RankineItems.PIA.get());
        basicItem(RankineItems.BANDAGE.get());
        basicItem(RankineItems.GAS_MASK.get());
        basicItem(RankineItems.SANDALS.get());
        basicItem(RankineItems.ICE_SKATES.get());
        basicItem(RankineItems.SNOWSHOES.get());
        basicItem(RankineItems.GOGGLES.get());
        basicItem(RankineItems.FINS.get());
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
        basicItem(RankineItems.AQUA_REGIA_BUCKET.get());
        basicItem(RankineItems.CARBON_DISULFIDE_BUCKET.get());
        basicItem(RankineItems.HEXAFLUOROSILICIC_ACID_BUCKET.get());
        basicItem(RankineItems.HYDROBROMIC_ACID_BUCKET.get());
        basicItem(RankineItems.GRAY_MUD_BUCKET.get());
        basicItem(RankineItems.RED_MUD_BUCKET.get());
        basicItem(RankineItems.SULFURIC_ACID_BUCKET.get());
        basicItem(RankineItems.BLACK_LIQUOR_BUCKET.get());
        basicItem(RankineItems.GREEN_LIQUOR_BUCKET.get());
        basicItem(RankineItems.WHITE_LIQUOR_BUCKET.get());
        basicItem(RankineItems.COIN.get());
        basicItem(RankineItems.SILVER_ZINC_BATTERY.get());
        basicItem(RankineItems.MAGNESIUM_BATTERY.get());
        basicItem(RankineItems.LEAD_ACID_BATTERY.get());
        basicItem(RankineItems.VANADIUM_REDOX_BATTERY.get());
        basicItem(RankineItems.ZINC_BROMINE_BATTERY.get());
        basicItem(RankineItems.SODIUM_SULFUR_BATTERY.get());
        basicItem(RankineItems.LITHIUM_ION_BATTERY.get());
        basicItem(RankineItems.AMERICIUM_RTG.get());
        basicItem(RankineItems.CURIUM_RTG.get());
        basicItem(RankineItems.POLONIUM_RTG.get());
        basicItem(RankineItems.PLUTONIUM_RTG.get());
        basicItem(RankineItems.STRONTIUM_RTG.get());
        basicItem(RankineItems.ORNAMENT.get());

        basicItem("rankine_journal");

        basicItem(RankineItems.BIOME_INDICATOR_GENERIC.get());
        basicItem(RankineItems.BIOME_INDICATOR_OCEAN.get());
        basicItem(RankineItems.BIOME_INDICATOR_RIVER.get());

        for (Block BLK : Arrays.asList(
                RankineBlocks.BONE_CHAR_BLOCK.get(),
                RankineBlocks.SEDIMENT_FAN.get(),
                RankineBlocks.HEATING_ELEMENT_1.get(),
                RankineBlocks.GAS_BOTTLER.get(),
                RankineBlocks.GAS_VENT.get(),
                RankineBlocks.PCF.get(),
                RankineBlocks.DIAMOND_ANVIL_CELL.get(),
                RankineBlocks.PARTICLE_ACCELERATOR.get(),
                RankineBlocks.TEMPLATE_TABLE.get(),
                RankineBlocks.MATERIAL_TESTING_TABLE.get(),
                RankineBlocks.BOTANIST_STATION.get(),
                RankineBlocks.EVAPORATION_TOWER.get(),
                RankineBlocks.DISTILLATION_TOWER.get(),
                RankineBlocks.AIR_DISTILLATION_PACKING.get(),
                RankineBlocks.REACTION_CHAMBER_CELL.get(),
                RankineBlocks.REACTION_CHAMBER_CORE.get(),
                RankineBlocks.FUSION_FURNACE.get(),
                RankineBlocks.BEEHIVE_OVEN_PIT.get(),
                RankineBlocks.ALLOY_FURNACE.get(),
                RankineBlocks.CHARCOAL_PIT.get(),
                RankineBlocks.GYRATORY_CRUSHER.get(),
                RankineBlocks.PISTON_CRUSHER.get(),
                RankineBlocks.INDUCTION_FURNACE.get(),
                RankineBlocks.TRAMPOLINE.get(),
                RankineBlocks.SODIUM_VAPOR_LAMP.get(),
                RankineBlocks.CARBON_DIOXIDE_FUMAROLE.get(),
                RankineBlocks.HYDROGEN_CHLORIDE_FUMAROLE.get(),
                RankineBlocks.HYDROGEN_SULFIDE_FUMAROLE.get(),
                RankineBlocks.SULFUR_DIOXIDE_FUMAROLE.get()
                )) {
            withExistingParent(BLK);
        }

        basicItem(RankineItems.CRUCIBLE.get());

        /*
        for (Block BLK : RankineLists.STONE_PILLARS) {
            withExistingParent(BLK.getRegistryName().getPath(), RankineBlockStateProvider.getBlockRSL(BLK.getRegistryName().getPath()+"8"));
        }

         */
        for (Block BLK : Stream.of(RankineLists.GLAZED_PORCELAIN_BLOCKS,RankineLists.VANILLA_BRICKS,RankineLists.BRICKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            buildingModeItem(BLK.asItem(), ((BuildingModeBlock) BLK).getMaxStyles());
        }
        withExistingParent(RankineBlocks.TILLED_SOIL.get().getRegistryName().getPath(), new ResourceLocation("rankine","block/tilled_soil_loam"));
        withExistingParent(RankineBlocks.STUMP.get().getRegistryName().getPath(), new ResourceLocation("rankine","block/stump0"));
        withExistingParent(RankineBlocks.MIXING_BARREL.get().getRegistryName().getPath(), new ResourceLocation("rankine","block/mixing_barrel0"));

        for (Block BLK : RankineLists.QUARTER_SLABS) {
            withExistingParent(BLK.getRegistryName().getPath(), new ResourceLocation("rankine:block/"+BLK.getRegistryName().getPath()+"_size2"));
        }


        withExistingParent(RankineBlocks.ASPHALT.get().getRegistryName().getPath(), new ResourceLocation("rankine","block/asphalt_size4"));
        withExistingParent(RankineBlocks.RED_ASPHALT.get().getRegistryName().getPath(), new ResourceLocation("rankine","block/red_asphalt_size4"));
        withExistingParent(RankineBlocks.GRAY_ASPHALT.get().getRegistryName().getPath(), new ResourceLocation("rankine","block/gray_asphalt_size4"));
        withExistingParent(RankineBlocks.DARK_GRAY_ASPHALT.get().getRegistryName().getPath(), new ResourceLocation("rankine","block/dark_gray_asphalt_size4"));
        withExistingParent(RankineBlocks.BLUE_ASPHALT.get().getRegistryName().getPath(), new ResourceLocation("rankine","block/blue_asphalt_size4"));
        withExistingParent(RankineBlocks.GREEN_ASPHALT.get().getRegistryName().getPath(), new ResourceLocation("rankine","block/green_asphalt_size4"));

        for (Block blk : RankineLists.WALL_MUSHROOMS) {
            basicItemAltTexture(blk.asItem(), modLoc("item/" + blk.getRegistryName().getPath()));
        }
        for (Block blk : RankineLists.ALLOY_POLES) {
            withExistingParent(blk.getRegistryName().getPath(), new ResourceLocation("rankine","block/" + blk.getRegistryName().getPath() + "0"));
        }
        for (Block blk : RankineLists.ALLOY_BARS) {
            basicItemAltTexture(blk.asItem(), modLoc("block/alloy_bars1"));
        }
        basicItemAltTexture(RankineItems.CRIMSON_CLOVER.get(), modLoc("block/" + "crimson_clover"));
        basicItemAltTexture(RankineItems.RED_CLOVER.get(), modLoc("block/" + "red_clover"));
        basicItemAltTexture(RankineItems.WHITE_CLOVER.get(), modLoc("block/" + "white_clover"));
        basicItemAltTexture(RankineItems.YELLOW_CLOVER.get(), modLoc("block/" + "yellow_clover"));
        basicItemAltTexture(RankineItems.STINGING_NETTLE.get(), modLoc("block/" + "stinging_nettle"));
        basicItemAltTexture(RankineItems.SHORT_GRASS.get(), modLoc("block/" + "short_grass0"));
        for (Block blk : RankineLists.POINTED_DRIPSTONES) {
            basicItemAltTexture(blk.asItem(), modLoc("block/" + blk.getRegistryName().getPath() + "_down_tip"));
        }
        for (Block blk : RankineLists.TALL_FLOWERS) {
            basicItemAltTexture(blk.asItem(), modLoc("block/" + blk.getRegistryName().getPath() + "_top"));
        }

        for (Item TOOL : Stream.of(RankineLists.WOODEN_TOOLS,RankineLists.STONE_TOOLS, RankineLists.FLINT_TOOLS, RankineLists.BRONZE_TOOLS, RankineLists.ALLOY_TOOLS, RankineLists.PEWTER_TOOLS, RankineLists.INVAR_TOOLS, RankineLists.TITANIUM_ALLOY_TOOLS, RankineLists.ZIRCONIUM_ALLOY_TOOLS, RankineLists.NIOBIUM_ALLOY_TOOLS, RankineLists.STEEL_TOOLS, RankineLists.STAINLESS_STEEL_TOOLS, RankineLists.COBALT_SUPERALLOY_TOOLS, RankineLists.NICKEL_SUPERALLOY_TOOLS, RankineLists.TUNGSTEN_HEAVY_ALLOY_TOOLS, RankineLists.BLACK_GOLD_TOOLS, RankineLists.BLUE_GOLD_TOOLS, RankineLists.GREEN_GOLD_TOOLS, RankineLists.ROSE_GOLD_TOOLS, RankineLists.PURPLE_GOLD_TOOLS, RankineLists.WHITE_GOLD_TOOLS, RankineLists.OSMIRIDIUM_TOOLS, RankineLists.AMALGAM_TOOLS, RankineLists.ENDER_AMALGAM_TOOLS).flatMap(Collection::stream).collect(Collectors.toList())) {
            if (TOOL instanceof AlloyCrowbarItem || TOOL.equals(RankineItems.ALLOY_SURF_ROD.get())) {
                basicItemHandheldRod(TOOL);
            } else if (TOOL instanceof SpearItem) {
                basicItemHandheld(TOOL);
            } else {
                basicItemHandheld(TOOL);
            }
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


        //ORES
        for (Block blk : Stream.of(RankineLists.NATIVE_ORES, RankineLists.CRUSHING_ORES, RankineLists.SPECIAL_ORES).flatMap(Collection::stream).collect(Collectors.toList())) {
            String regName = blk.getRegistryName().getPath();
            withExistingParent(regName, modLoc("block/"+regName+"0"));
        }



        //GEODESw
        for (Block blk : RankineLists.GEODES) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/"+name));
        }
        for (Block blk : Stream.of(RankineLists.VANILLA_BRICKS_PRESSURE_PLATES).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name + "_up"));
        }
        for (Block blk : Stream.of(RankineLists.METAL_DOORS).flatMap(Collection::stream).collect(Collectors.toList())) {
            basicItem(blk.asItem());
        }
        for (Block blk : Stream.of(RankineLists.METAL_TRAPDOORS).flatMap(Collection::stream).collect(Collectors.toList())) {
            withExistingParent(blk.getRegistryName().getPath(), modLoc("block/"+blk.getRegistryName().getPath()+"_bottom"));
        }

        for (Block blk : RankineLists.LANTERNS) {
            basicItem(blk.asItem());
        }

        basicItem(RankineItems.PENNING_TRAP.get());
        basicItem(RankineItems.FILLED_PENNING_TRAP.get());
    }

    private ItemModelBuilder withExistingParent(Block blk) {
        return withExistingParent(blk.getRegistryName().getPath(), modLoc("block/" + blk.getRegistryName().getPath()));
    }
    private ItemModelBuilder basicItem(String name) {
        return getBuilder(name).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/" + name);
    }
    public ItemModelBuilder basicItem(Item name) {
        return getBuilder(name.getRegistryName().getPath()).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/" + name.getRegistryName().getPath());
    }
    private ItemModelBuilder basicItemHandheld(Item name) {
        return getBuilder(name.getRegistryName().getPath()).parent(getExistingFile(mcLoc("item/handheld"))).texture("layer0", "item/" + name.getRegistryName().getPath());
    }
    private ItemModelBuilder basicItemHandheldRod(Item name) {
        return getBuilder(name.getRegistryName().getPath()).parent(getExistingFile(mcLoc("item/handheld_rod"))).texture("layer0", "item/" + name.getRegistryName().getPath());
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


    private void buildingModeItem(Item item, int maxModes) {
        String Path = item.getRegistryName().getPath();
        ItemModelBuilder thing = withExistingParent(Path, RankineBlockStateProvider.getBlockRSL(Path+"1"));

        for (int i = 2; i <= maxModes; i++) {
            thing.override().predicate(new ResourceLocation("rankine:building_mode"), (float) i).model(getExistingFile(RankineBlockStateProvider.getBlockRSL(Path+i))).end();
        }
    }

    private void spearItem(Item item) {
        String Path = item.getRegistryName().getPath();
        getBuilder(Path).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/" + Path);
        getBuilder(Path+"_throwing").parent(getExistingFile(modLoc("item/spear_throwing"))).texture("layer0", "item/" + Path);
        getBuilder(Path).parent(getExistingFile(modLoc("item/spear_in_hand"))).texture("layer0", "item/" + Path).override().predicate(new ResourceLocation("throwing"), 1.0f).model(getExistingFile(modLoc("item/"+Path+"_throwing")));
    }

    private void filledTagItem(Item item) {
        String Path = item.getRegistryName().getPath();
        getBuilder(Path+"_filled").parent(getExistingFile(mcLoc("item/handheld"))).texture("layer0", "item/" + Path+"_filled");
        getBuilder(Path).parent(getExistingFile(mcLoc("item/handheld"))).texture("layer0", "item/" + Path).override().predicate(new ResourceLocation("rankine:filled"), 1).model(getExistingFile(modLoc("item/"+Path+"_filled")));
    }
    
}
