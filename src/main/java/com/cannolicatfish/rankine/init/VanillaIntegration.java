package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.blocks.states.TilledSoilTypes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class VanillaIntegration {
    public static Map<Item, Integer> fuelValueMap = new HashMap<Item, Integer>();
    public static Map<Block, Item> oreNuggetMap = new HashMap<Block, Item>();
    public static Map<Block, Block> pathBlocks_map = new HashMap<Block, Block>();
    public static Map<Block, TilledSoilTypes> hoeables_map = new HashMap<Block, TilledSoilTypes>();
    public static final Map<Item, ForgeConfigSpec.BooleanValue> DISABLED_ITEMS = new HashMap<>();


    public static void init() {

        oreNuggetMap.put(RankineBlocks.MALACHITE_ORE.get(),RankineItems.COPPER_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.CHALCOCITE_ORE.get(),RankineItems.COPPER_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.HEMATITE_ORE.get(), Items.IRON_NUGGET);
        oreNuggetMap.put(RankineBlocks.MAGNETITE_ORE.get(), Items.IRON_NUGGET);
        oreNuggetMap.put(RankineBlocks.PETALITE_ORE.get(),RankineItems.LITHIUM_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.MAGNESITE_ORE.get(),RankineItems.MAGNESIUM_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.BAUXITE_ORE.get(),RankineItems.ALUMINUM_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.ILMENITE_ORE.get(),RankineItems.TITANIUM_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.CHROMITE_ORE.get(),RankineItems.CHROMIUM_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.PYROLUSITE_ORE.get(),RankineItems.MANGANESE_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.SPHALERITE_ORE.get(),RankineItems.ZINC_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.CASSITERITE_ORE.get(),RankineItems.TIN_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.GALENA_ORE.get(),RankineItems.LEAD_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.BISMUTHINITE_ORE.get(),RankineItems.BISMUTH_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.COBALTITE_ORE.get(),RankineItems.COBALT_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.PENTLANDITE_ORE.get(),RankineItems.NICKEL_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.INTERSPINIFEX_ORE.get(),RankineItems.NICKEL_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.CELESTINE_ORE.get(),RankineItems.STRONTIUM_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.XENOTIME_ORE.get(),RankineItems.YTTRIUM_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.MOLYBDENITE_ORE.get(),RankineItems.MOLYBDENUM_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.SPERRYLITE_ORE.get(),RankineItems.PLATINUM_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.COLTAN_ORE.get(),RankineItems.NIOBIUM_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.ACANTHITE_ORE.get(),RankineItems.SILVER_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.GREENOCKITE_ORE.get(),RankineItems.CADMIUM_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.WOLFRAMITE_ORE.get(),RankineItems.TUNGSTEN_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.RHENIITE_ORE.get(),RankineItems.RHENIUM_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.URANINITE_ORE.get(),RankineItems.URANIUM_NUGGET.get());
        oreNuggetMap.put(RankineBlocks.CRYOLITE_ORE.get(),RankineItems.SODIUM_NUGGET.get());





        for (Block blk : RankineLists.TALL_FLOWERS) {
            registerCompostable(0.65F, blk);
        }
        for (Block blk : RankineLists.LEAVES) {
            registerCompostable(0.3F, blk);
        }
        for (Block blk : RankineLists.LEAF_LITTERS) {
            registerCompostable(0.1F, blk);
        }
        for (Block blk : RankineLists.SAPLINGS) {
            registerCompostable(0.3F, blk);
        }
        for (Item blk : RankineLists.SEEDS) {
            registerCompostable(0.3F, blk);
        }
        registerCompostable(0.5F, RankineItems.COMPOST.get());
        registerCompostable(1.0F, RankineItems.PINEAPPLE_SLEEVES.get());
        registerCompostable(0.25F, RankineItems.SHORT_GRASS.get());
        registerCompostable(0.5F, RankineItems.STINGING_NETTLE.get());
        registerCompostable(0.5F, RankineItems.YELLOW_CLOVER.get());
        registerCompostable(0.5F, RankineItems.CRIMSON_CLOVER.get());
        registerCompostable(0.5F, RankineItems.RED_CLOVER.get());
        registerCompostable(0.5F, RankineItems.WHITE_CLOVER.get());

        registerCompostable(0.3F, RankineItems.ELDERBERRIES.get());
        registerCompostable(0.3F, RankineItems.BLUEBERRIES.get());
        registerCompostable(0.3F, RankineItems.STRAWBERRIES.get());
        registerCompostable(0.3F, RankineItems.CRANBERRIES.get());
        registerCompostable(0.3F, RankineItems.POKEBERRIES.get());
        registerCompostable(0.3F, RankineItems.RASPBERRIES.get());
        registerCompostable(0.3F, RankineItems.BLACKBERRIES.get());
        registerCompostable(0.3F, RankineItems.PINEAPPLE.get());
        registerCompostable(0.3F, RankineItems.ALOE.get());
        registerCompostable(0.3F, RankineItems.CAMPHOR_BASIL_LEAF.get());
        registerCompostable(0.3F, RankineItems.CAMPHOR_BASIL_SEEDS.get());
        registerCompostable(0.3F, RankineItems.BANANA_YUCCA.get());
        registerCompostable(0.3F, RankineItems.SNOWBERRIES.get());
        registerCompostable(0.3F, RankineItems.JUNIPER_BERRIES.get());
        registerCompostable(0.3F, RankineItems.BLACK_WALNUT.get());
        registerCompostable(0.3F, RankineItems.COCONUT.get());

        for (Block GRASS : RankineLists.GRASS_BLOCKS)  {
            pathBlocks_map.put(GRASS, RankineLists.PATH_BLOCKS.get(RankineLists.GRASS_BLOCKS.indexOf(GRASS)));
        }
        for (Block GRASS : RankineLists.PODZOL_BLOCKS)  {
            pathBlocks_map.put(GRASS, RankineLists.PATH_BLOCKS.get(RankineLists.PODZOL_BLOCKS.indexOf(GRASS)));
        }
        for (Block GRASS : RankineLists.SOIL_BLOCKS)  {
            pathBlocks_map.put(GRASS, RankineLists.PATH_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(GRASS)));
        }
        for (Block GRASS : RankineLists.MYCELIUM_BLOCKS)  {
            pathBlocks_map.put(GRASS, RankineBlocks.MYCELIUM_PATH.get());
        }

        /*
        hoeables_map.put(Blocks.DIRT, TilledSoilTypes.DIRT);
        hoeables_map.put(Blocks.COARSE_DIRT, TilledSoilTypes.DIRT);
        hoeables_map.put(Blocks.GRASS_BLOCK, TilledSoilTypes.DIRT);
        hoeables_map.put(Blocks.GRASS_PATH, TilledSoilTypes.DIRT);
        hoeables_map.put(Blocks.MYCELIUM, TilledSoilTypes.DIRT);
        hoeables_map.put(Blocks.PODZOL, TilledSoilTypes.DIRT);

         */

        hoeables_map.put(Blocks.SOUL_SOIL, TilledSoilTypes.SOUL_SOIL);
        hoeables_map.put(RankineBlocks.LOAM.get(), TilledSoilTypes.LOAM);
        hoeables_map.put(RankineBlocks.COARSE_LOAM.get(), TilledSoilTypes.LOAM);
        hoeables_map.put(RankineBlocks.LOAM_GRASS_PATH.get(), TilledSoilTypes.LOAM);
        hoeables_map.put(RankineBlocks.LOAM_GRASS_BLOCK.get(), TilledSoilTypes.LOAM);
        hoeables_map.put(RankineBlocks.LOAM_PODZOL.get(), TilledSoilTypes.LOAM);
        hoeables_map.put(RankineBlocks.LOAM_MYCELIUM.get(), TilledSoilTypes.LOAM);
        hoeables_map.put(RankineBlocks.LOAM_MUD.get(), TilledSoilTypes.LOAM);
        hoeables_map.put(RankineBlocks.HUMUS.get(), TilledSoilTypes.HUMUS);
        hoeables_map.put(RankineBlocks.COARSE_HUMUS.get(), TilledSoilTypes.HUMUS);
        hoeables_map.put(RankineBlocks.HUMUS_GRASS_PATH.get(), TilledSoilTypes.HUMUS);
        hoeables_map.put(RankineBlocks.HUMUS_GRASS_BLOCK.get(), TilledSoilTypes.HUMUS);
        hoeables_map.put(RankineBlocks.HUMUS_PODZOL.get(), TilledSoilTypes.HUMUS);
        hoeables_map.put(RankineBlocks.HUMUS_MYCELIUM.get(), TilledSoilTypes.HUMUS);
        hoeables_map.put(RankineBlocks.HUMUS_MUD.get(), TilledSoilTypes.HUMUS);
        hoeables_map.put(RankineBlocks.CLAY_LOAM.get(), TilledSoilTypes.CLAY_LOAM);
        hoeables_map.put(RankineBlocks.COARSE_CLAY_LOAM.get(), TilledSoilTypes.CLAY_LOAM);
        hoeables_map.put(RankineBlocks.CLAY_LOAM_GRASS_PATH.get(), TilledSoilTypes.CLAY_LOAM);
        hoeables_map.put(RankineBlocks.CLAY_LOAM_GRASS_BLOCK.get(), TilledSoilTypes.CLAY_LOAM);
        hoeables_map.put(RankineBlocks.CLAY_LOAM_PODZOL.get(), TilledSoilTypes.CLAY_LOAM);
        hoeables_map.put(RankineBlocks.CLAY_LOAM_MYCELIUM.get(), TilledSoilTypes.CLAY_LOAM);
        hoeables_map.put(RankineBlocks.CLAY_LOAM_MUD.get(), TilledSoilTypes.CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_LOAM.get(), TilledSoilTypes.SANDY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.COARSE_SANDY_CLAY_LOAM.get(), TilledSoilTypes.SANDY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_LOAM_GRASS_PATH.get(), TilledSoilTypes.SANDY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_LOAM_GRASS_BLOCK.get(), TilledSoilTypes.SANDY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_LOAM_PODZOL.get(), TilledSoilTypes.SANDY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_LOAM_MYCELIUM.get(), TilledSoilTypes.SANDY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_LOAM_MUD.get(), TilledSoilTypes.SANDY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_LOAM.get(), TilledSoilTypes.SILTY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.COARSE_SILTY_CLAY_LOAM.get(), TilledSoilTypes.SILTY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_LOAM_GRASS_PATH.get(), TilledSoilTypes.SILTY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_LOAM_GRASS_BLOCK.get(), TilledSoilTypes.SILTY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_LOAM_PODZOL.get(), TilledSoilTypes.SILTY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_LOAM_MYCELIUM.get(), TilledSoilTypes.SILTY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_LOAM_MUD.get(), TilledSoilTypes.SILTY_CLAY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_LOAM.get(), TilledSoilTypes.SILTY_LOAM);
        hoeables_map.put(RankineBlocks.COARSE_SILTY_LOAM.get(), TilledSoilTypes.SILTY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_LOAM_GRASS_PATH.get(), TilledSoilTypes.SILTY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_LOAM_GRASS_BLOCK.get(), TilledSoilTypes.SILTY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_LOAM_PODZOL.get(), TilledSoilTypes.SILTY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_LOAM_MYCELIUM.get(), TilledSoilTypes.SILTY_LOAM);
        hoeables_map.put(RankineBlocks.SILTY_LOAM_MUD.get(), TilledSoilTypes.SILTY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_LOAM.get(), TilledSoilTypes.SANDY_LOAM);
        hoeables_map.put(RankineBlocks.COARSE_SANDY_LOAM.get(), TilledSoilTypes.SANDY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_LOAM_GRASS_PATH.get(), TilledSoilTypes.SANDY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_LOAM_GRASS_BLOCK.get(), TilledSoilTypes.SANDY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_LOAM_PODZOL.get(), TilledSoilTypes.SANDY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_LOAM_MYCELIUM.get(), TilledSoilTypes.SANDY_LOAM);
        hoeables_map.put(RankineBlocks.SANDY_LOAM_MUD.get(), TilledSoilTypes.SANDY_LOAM);
        hoeables_map.put(RankineBlocks.LOAMY_SAND.get(), TilledSoilTypes.LOAMY_SAND);
        hoeables_map.put(RankineBlocks.COARSE_LOAMY_SAND.get(), TilledSoilTypes.LOAMY_SAND);
        hoeables_map.put(RankineBlocks.LOAMY_SAND_GRASS_PATH.get(), TilledSoilTypes.LOAMY_SAND);
        hoeables_map.put(RankineBlocks.LOAMY_SAND_GRASS_BLOCK.get(), TilledSoilTypes.LOAMY_SAND);
        hoeables_map.put(RankineBlocks.LOAMY_SAND_PODZOL.get(), TilledSoilTypes.LOAMY_SAND);
        hoeables_map.put(RankineBlocks.LOAMY_SAND_MYCELIUM.get(), TilledSoilTypes.LOAMY_SAND);
        hoeables_map.put(RankineBlocks.LOAMY_SAND_MUD.get(), TilledSoilTypes.LOAMY_SAND);
        hoeables_map.put(RankineBlocks.SANDY_CLAY.get(), TilledSoilTypes.SANDY_CLAY);
        hoeables_map.put(RankineBlocks.COARSE_SANDY_CLAY.get(), TilledSoilTypes.SANDY_CLAY);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_GRASS_PATH.get(), TilledSoilTypes.SANDY_CLAY);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_GRASS_BLOCK.get(), TilledSoilTypes.SANDY_CLAY);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_PODZOL.get(), TilledSoilTypes.SANDY_CLAY);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_MYCELIUM.get(), TilledSoilTypes.SANDY_CLAY);
        hoeables_map.put(RankineBlocks.SANDY_CLAY_MUD.get(), TilledSoilTypes.SANDY_CLAY);
        hoeables_map.put(RankineBlocks.SILTY_CLAY.get(), TilledSoilTypes.SILTY_CLAY);
        hoeables_map.put(RankineBlocks.COARSE_SILTY_CLAY.get(), TilledSoilTypes.SILTY_CLAY);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_GRASS_PATH.get(), TilledSoilTypes.SILTY_CLAY);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_GRASS_BLOCK.get(), TilledSoilTypes.SILTY_CLAY);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_PODZOL.get(), TilledSoilTypes.SILTY_CLAY);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_MYCELIUM.get(), TilledSoilTypes.SILTY_CLAY);
        hoeables_map.put(RankineBlocks.SILTY_CLAY_MUD.get(), TilledSoilTypes.SILTY_CLAY);

        


        
        addFlowerPot(RankineBlocks.CEDAR_SAPLING.get(), RankineBlocks.POTTED_CEDAR_SAPLING.get());
        addFlowerPot(RankineBlocks.PINYON_PINE_SAPLING.get(), RankineBlocks.POTTED_PINYON_PINE_SAPLING.get());
        addFlowerPot(RankineBlocks.COCONUT_PALM_SAPLING.get(), RankineBlocks.POTTED_COCONUT_PALM_SAPLING.get());
        addFlowerPot(RankineBlocks.JUNIPER_SAPLING.get(), RankineBlocks.POTTED_JUNIPER_SAPLING.get());
        addFlowerPot(RankineBlocks.BALSAM_FIR_SAPLING.get(), RankineBlocks.POTTED_BALSAM_FIR_SAPLING.get());
        addFlowerPot(RankineBlocks.MAGNOLIA_SAPLING.get(), RankineBlocks.POTTED_MAGNOLIA_SAPLING.get());
        addFlowerPot(RankineBlocks.EASTERN_HEMLOCK_SAPLING.get(), RankineBlocks.POTTED_EASTERN_HEMLOCK_SAPLING.get());
        addFlowerPot(RankineBlocks.WESTERN_HEMLOCK_SAPLING.get(), RankineBlocks.POTTED_WESTERN_HEMLOCK_SAPLING.get());
        addFlowerPot(RankineBlocks.RED_BIRCH_SAPLING.get(), RankineBlocks.POTTED_RED_BIRCH_SAPLING.get());
        addFlowerPot(RankineBlocks.YELLOW_BIRCH_SAPLING.get(), RankineBlocks.POTTED_YELLOW_BIRCH_SAPLING.get());
        addFlowerPot(RankineBlocks.BLACK_BIRCH_SAPLING.get(), RankineBlocks.POTTED_BLACK_BIRCH_SAPLING.get());
        addFlowerPot(RankineBlocks.MAPLE_SAPLING.get(), RankineBlocks.POTTED_MAPLE_SAPLING.get());
        addFlowerPot(RankineBlocks.SHARINGA_SAPLING.get(), RankineBlocks.POTTED_SHARINGA_SAPLING.get());
        addFlowerPot(RankineBlocks.BLACK_WALNUT_SAPLING.get(), RankineBlocks.POTTED_BLACK_WALNUT_SAPLING.get());
        addFlowerPot(RankineBlocks.CORK_OAK_SAPLING.get(), RankineBlocks.POTTED_CORK_OAK_SAPLING.get());
        addFlowerPot(RankineBlocks.CINNAMON_SAPLING.get(), RankineBlocks.POTTED_CINNAMON_SAPLING.get());
        addFlowerPot(RankineBlocks.WEEPING_WILLOW_SAPLING.get(), RankineBlocks.POTTED_WEEPING_WILLOW_SAPLING.get());
        addFlowerPot(RankineBlocks.HONEY_LOCUST_SAPLING.get(), RankineBlocks.POTTED_HONEY_LOCUST_SAPLING.get());

    }

    public static void populateFuelMap() {
        fuelValueMap.put(RankineItems.LIGNITE.get(),1200);
        fuelValueMap.put(RankineItems.LIGNITE_BLOCK.get(),1200*9);
        fuelValueMap.put(RankineItems.SUBBITUMINOUS_COAL.get(),1600);
        fuelValueMap.put(RankineItems.SUBBITUMINOUS_COAL_BLOCK.get(),1600*9);
        fuelValueMap.put(RankineItems.BITUMINOUS_COAL.get(),2400);
        fuelValueMap.put(RankineItems.BITUMINOUS_COAL_BLOCK.get(),2400*9);
        fuelValueMap.put(RankineItems.ANTHRACITE_COAL.get(),3200);
        fuelValueMap.put(RankineItems.ANTHRACITE_COAL_BLOCK.get(),3200*9);
        fuelValueMap.put(RankineItems.COKE.get(),3200);
        fuelValueMap.put(RankineItems.COKE_BLOCK.get(),3200*9);
        fuelValueMap.put(RankineItems.DRIED_BAMBOO.get(),50);
        fuelValueMap.put(RankineItems.BIOMASS.get(),25);
        fuelValueMap.put(RankineItems.COMPRESSED_BIOMASS.get(),200);
        fuelValueMap.put(RankineItems.STICK_BLOCK.get(),200);
        fuelValueMap.put(RankineItems.COMPRESSED_BIOMASS.get(),200);
        fuelValueMap.put(RankineItems.COMPRESSED_BIOMASS.get(),200);
        fuelValueMap.put(RankineItems.COMPRESSED_BIOMASS.get(),200);

        if (!Config.GENERAL.FUEL_VALUES_LIST.get().isEmpty()) {
            for (String s : Config.GENERAL.FUEL_VALUES_LIST.get()) {
                if (Arrays.asList(s.split("\\|")).size() == 2) {
                    String RS;
                    int burnTime;
                    try {
                        RS = s.split("\\|")[0];
                    }
                    catch(Exception e) {
                        System.out.println(e.getLocalizedMessage() + " " + s + " is an invalid entry");
                        continue;
                    }
                    try {
                        burnTime = Integer.parseInt(s.split("\\|")[1]);
                    }
                    catch(Exception e) {
                        System.out.println(e.getLocalizedMessage() + " " + s + " is an invalid entry");
                        continue;
                    }

                    if (RS.contains("#")) {
                        ResourceLocation newRS = ResourceLocation.tryParse(RS.replace("#",""));
                        TagKey<Item> tag = TagKey.create(Registry.ITEM_REGISTRY,newRS);
                        for (Item item : ForgeRegistries.ITEMS.tags().getTag(tag).stream().toList()) {
                            if (item != null && !fuelValueMap.containsKey(item)) {
                                fuelValueMap.put(item, burnTime);
                            }
                        }
                    } else {
                        Item item = ForgeRegistries.ITEMS.getValue(ResourceLocation.tryParse(RS));
                        if (item != null && !fuelValueMap.containsKey(item)) {
                            fuelValueMap.put(item,burnTime);
                        }
                    }
                }

            }

        }

        // Wood
        DISABLED_ITEMS.put(Items.WOODEN_SWORD, Config.TOOLS.DISABLE_WOODEN_SWORD);
        DISABLED_ITEMS.put(Items.WOODEN_AXE, Config.TOOLS.DISABLE_WOODEN_AXE);
        DISABLED_ITEMS.put(Items.WOODEN_SHOVEL, Config.TOOLS.DISABLE_WOODEN_SHOVEL);
        DISABLED_ITEMS.put(Items.WOODEN_PICKAXE, Config.TOOLS.DISABLE_WOODEN_PICKAXE);
        DISABLED_ITEMS.put(Items.WOODEN_HOE, Config.TOOLS.DISABLE_WOODEN_HOE);
        // Stone
        DISABLED_ITEMS.put(Items.STONE_SWORD, Config.TOOLS.DISABLE_STONE_SWORD);
        DISABLED_ITEMS.put(Items.STONE_AXE, Config.TOOLS.DISABLE_STONE_AXE);
        DISABLED_ITEMS.put(Items.STONE_SHOVEL, Config.TOOLS.DISABLE_STONE_SHOVEL);
        DISABLED_ITEMS.put(Items.STONE_PICKAXE, Config.TOOLS.DISABLE_STONE_PICKAXE);
        DISABLED_ITEMS.put(Items.STONE_HOE, Config.TOOLS.DISABLE_STONE_HOE);
        // Iron
        DISABLED_ITEMS.put(Items.IRON_SWORD, Config.TOOLS.DISABLE_IRON_SWORD);
        DISABLED_ITEMS.put(Items.IRON_AXE, Config.TOOLS.DISABLE_IRON_AXE);
        DISABLED_ITEMS.put(Items.IRON_SHOVEL, Config.TOOLS.DISABLE_IRON_SHOVEL);
        DISABLED_ITEMS.put(Items.IRON_PICKAXE, Config.TOOLS.DISABLE_IRON_PICKAXE);
        DISABLED_ITEMS.put(Items.IRON_HOE, Config.TOOLS.DISABLE_IRON_HOE);
        // Gold
        DISABLED_ITEMS.put(Items.GOLDEN_SWORD, Config.TOOLS.DISABLE_GOLDEN_SWORD);
        DISABLED_ITEMS.put(Items.GOLDEN_AXE, Config.TOOLS.DISABLE_GOLDEN_AXE);
        DISABLED_ITEMS.put(Items.GOLDEN_SHOVEL, Config.TOOLS.DISABLE_GOLDEN_SHOVEL);
        DISABLED_ITEMS.put(Items.GOLDEN_PICKAXE, Config.TOOLS.DISABLE_GOLDEN_PICKAXE);
        DISABLED_ITEMS.put(Items.GOLDEN_HOE, Config.TOOLS.DISABLE_GOLDEN_HOE);
        // Diamond
        DISABLED_ITEMS.put(Items.DIAMOND_SWORD, Config.TOOLS.DISABLE_DIAMOND_SWORD);
        DISABLED_ITEMS.put(Items.DIAMOND_AXE, Config.TOOLS.DISABLE_DIAMOND_AXE);
        DISABLED_ITEMS.put(Items.DIAMOND_SHOVEL, Config.TOOLS.DISABLE_DIAMOND_SHOVEL);
        DISABLED_ITEMS.put(Items.DIAMOND_PICKAXE, Config.TOOLS.DISABLE_DIAMOND_PICKAXE);
        DISABLED_ITEMS.put(Items.DIAMOND_HOE, Config.TOOLS.DISABLE_DIAMOND_HOE);
        // Netherite
        DISABLED_ITEMS.put(Items.NETHERITE_SWORD, Config.TOOLS.DISABLE_NETHERITE_SWORD);
        DISABLED_ITEMS.put(Items.NETHERITE_AXE, Config.TOOLS.DISABLE_NETHERITE_AXE);
        DISABLED_ITEMS.put(Items.NETHERITE_SHOVEL, Config.TOOLS.DISABLE_NETHERITE_SHOVEL);
        DISABLED_ITEMS.put(Items.NETHERITE_PICKAXE, Config.TOOLS.DISABLE_NETHERITE_PICKAXE);
        DISABLED_ITEMS.put(Items.NETHERITE_HOE, Config.TOOLS.DISABLE_NETHERITE_HOE);

        // Compass
        DISABLED_ITEMS.put(Items.COMPASS, Config.TOOLS.DISABLE_COMPASS);
        // Clock
        DISABLED_ITEMS.put(Items.CLOCK, Config.TOOLS.DISABLE_CLOCK);
        // Wooden Hammer
        DISABLED_ITEMS.put(RankineItems.WOODEN_HAMMER.get(), Config.TOOLS.DISABLE_WOODEN_HAMMER);
        DISABLED_ITEMS.put(RankineItems.STONE_HAMMER.get(), Config.TOOLS.DISABLE_STONE_HAMMER);
        // Altimeter
        DISABLED_ITEMS.put(RankineItems.ALTIMETER.get(), Config.TOOLS.DISABLE_ALTIMETER);
        // Thermometer
        DISABLED_ITEMS.put(RankineItems.THERMOMETER.get(), Config.TOOLS.DISABLE_THERMOMETER);
        // Photometer
        DISABLED_ITEMS.put(RankineItems.PHOTOMETER.get(), Config.TOOLS.DISABLE_PHOTOMETER);
        // Speedometer
        DISABLED_ITEMS.put(RankineItems.SPEEDOMETER.get(), Config.TOOLS.DISABLE_SPEEDOMETER);
        // Biometer
        DISABLED_ITEMS.put(RankineItems.BIOMETER.get(), Config.TOOLS.DISABLE_BIOMETER);
        // Magnetometer
        DISABLED_ITEMS.put(RankineItems.MAGNETOMETER.get(), Config.TOOLS.DISABLE_MAGNETOMETER);
    }

    public static void registerCompostable(float chance, ItemLike itemIn) {
        ComposterBlock.COMPOSTABLES.put(itemIn.asItem(), chance);
    }

    public static void addFlowerPot(Block plant, Block plantPot) {
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(plant.getRegistryName(), () -> plantPot);
    }


}
