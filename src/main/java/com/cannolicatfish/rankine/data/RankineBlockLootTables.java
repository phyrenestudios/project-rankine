package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Items;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RankineBlockLootTables extends RankineLootTableProvider {

    public RankineBlockLootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        for (Block blk : Stream.of(
                RankineLists.STONE,
                RankineLists.POLISHED_STONE,
                RankineLists.STONE_BRICKS,
                RankineLists.POLISHED_STONE,
                RankineLists.STONE_STAIRS,
                RankineLists.POLISHED_STONE_STAIRS,
                RankineLists.STONE_BRICKS_STAIRS,
                RankineLists.STONE_WALL,
                RankineLists.POLISHED_STONE_WALL,
                RankineLists.STONE_BRICKS_WALL,
                RankineLists.STONE_PRESSURE_PLATE,
                RankineLists.STONE_BRICKS_PRESSURE_PLATE,
                RankineLists.STONE_BUTTON,
                RankineLists.STONE_PILLARS,
                RankineLists.BRICKS,
                RankineLists.BRICKS_STAIRS,
                RankineLists.BRICKS_WALL,
                RankineLists.SHEETMETALS,
                RankineLists.GEODES,
                RankineLists.LEDS,
                RankineLists.MINERAL_WOOL,
                RankineLists.FIBER_BLOCK,
                RankineLists.FIBER_MAT,
                RankineLists.PLANKS,
                RankineLists.LOGS,
                RankineLists.STRIPPED_LOGS,
                RankineLists.WOODS,
                RankineLists.STRIPPED_WOODS,
                RankineLists.WOODEN_STAIRS,
                RankineLists.WOODEN_TRAPDOORS,
                RankineLists.WOODEN_FENCES,
                RankineLists.WOODEN_FENCE_GATES,
                RankineLists.WOODEN_BUTTONS,
                RankineLists.WOODEN_PRESSURE_PLATES,
                RankineLists.MINERAL_STONES,
                RankineLists.METAL_TRAPDOORS,
                RankineLists.METAL_LADDERS,
                RankineLists.ALLOY_BLOCKS,
                RankineLists.ALLOY_PEDESTALS,
                RankineLists.ALLOY_POLES,
                RankineLists.STANDARD_BLOCKS,
                RankineLists.ROTATION_BLOCKS,
                RankineLists.ELEMENT_BLOCKS,
                RankineLists.SAPLINGS,

                RankineLists.CROPS_SINGLE,
                RankineLists.CROPS_DOUBLE,
                RankineLists.CROPS_TRIPLE,
                RankineLists.BUSH_PLANTS,
                RankineLists.DOUBLE_BUSH_PLANTS
                ).flatMap(Collection::stream).collect(Collectors.toList())) {
            lootTables.put(blk, createBlockLootTable(blk));
        }


        for (Block SOIL : Stream.of(RankineLists.SOILS).flatMap(Collection::stream).collect(Collectors.toList())) {
            Block GRASS = RankineLists.GRASSY_SOILS.get(RankineLists.SOILS.indexOf(SOIL));
            Block PATH = RankineLists.PATH_BLOCKS.get(RankineLists.SOILS.indexOf(SOIL));
            lootTables.put(SOIL, createBlockLootTable(SOIL));
            lootTables.put(PATH, createBlockLootTable(SOIL));
            lootTables.put(GRASS, droppingWithSilkTouch(GRASS,SOIL));
        }

        for (Block blk : Stream.of(
                RankineLists.STONE_SLAB,
                RankineLists.POLISHED_STONE_SLAB,
                RankineLists.STONE_BRICKS_SLAB,
                RankineLists.STONE_VERTICAL_SLAB,
                RankineLists.POLISHED_STONE_VERTICAL_SLAB,
                RankineLists.STONE_BRICKS_VERTICAL_SLAB,
                RankineLists.SHEETMETAL_VERTICAL_SLAB,
                RankineLists.BRICKS_SLAB,
                RankineLists.BRICKS_VERTICAL_SLAB,
                RankineLists.WOODEN_VERTICAL_SLABS,
                RankineLists.WOODEN_SLABS
        ).flatMap(Collection::stream).collect(Collectors.toList())) {
            lootTables.put(blk, slabBlockLootTable(blk));
        }

        
        lootTables.put(RankineBlocks.NATIVE_ARSENIC_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_ARSENIC_ORE.get(), RankineItems.ARSENIC_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_BISMUTH_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_BISMUTH_ORE.get(), RankineItems.BISMUTH_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_SILVER_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_SILVER_ORE.get(), RankineItems.SILVER_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_TELLURIUM_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_TELLURIUM_ORE.get(), RankineItems.TELLURIUM_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_SELENIUM_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_SELENIUM_ORE.get(), RankineItems.SELENIUM_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_GALLIUM_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_GALLIUM_ORE.get(), RankineItems.GALLIUM_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_INDIUM_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_INDIUM_ORE.get(), RankineItems.INDIUM_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_TIN_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_TIN_ORE.get(), RankineItems.TIN_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_LEAD_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_LEAD_ORE.get(), RankineItems.LEAD_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_GOLD_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_GOLD_ORE.get(), Items.GOLD_NUGGET));
        lootTables.put(RankineBlocks.NATIVE_SILVER_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_SILVER_ORE.get(), RankineItems.SILVER_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_SULFUR_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_SULFUR_ORE.get(), RankineItems.SULFUR_NUGGET.get()));

        lootTables.put(RankineBlocks.LIGNITE_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.LIGNITE_ORE.get(), RankineItems.LIGNITE.get()));
        lootTables.put(RankineBlocks.SUBBITUMINOUS_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.SUBBITUMINOUS_ORE.get(), RankineItems.SUBBITUMINOUS_COAL.get()));
        lootTables.put(RankineBlocks.BITUMINOUS_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.BITUMINOUS_ORE.get(), RankineItems.BITUMINOUS_COAL.get()));
        lootTables.put(RankineBlocks.ANTHRACITE_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.ANTHRACITE_ORE.get(), RankineItems.ANTHRACITE_COAL.get()));
        lootTables.put(RankineBlocks.LONSDALEITE_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.LONSDALEITE_ORE.get(), RankineItems.LONSDALEITE_DIAMOND.get()));
        lootTables.put(RankineBlocks.PLUMBAGO_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.PLUMBAGO_ORE.get(), RankineItems.GRAPHITE.get()));
        lootTables.put(RankineBlocks.HALITE_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.HALITE_ORE.get(), RankineItems.SODIUM_CHLORIDE.get()));
        lootTables.put(RankineBlocks.QUARTZ_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.QUARTZ_ORE.get(), Items.QUARTZ));
        lootTables.put(RankineBlocks.DIAMOND_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.DIAMOND_ORE.get(), Items.DIAMOND));
        lootTables.put(RankineBlocks.EMERALD_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.EMERALD_ORE.get(), Items.EMERALD));

        lootTables.put(RankineBlocks.TAENITE_ORE.get(), dropping(RankineBlocks.TAENITE_ORE.get()));
        lootTables.put(RankineBlocks.TETRATAENITE_ORE.get(), dropping(RankineBlocks.TAENITE_ORE.get()));
        lootTables.put(RankineBlocks.ANTITAENITE_ORE.get(), dropping(RankineBlocks.TAENITE_ORE.get()));
        lootTables.put(RankineBlocks.KAMACITE_ORE.get(), dropping(RankineBlocks.TAENITE_ORE.get()));

        for (Block ORE : RankineLists.CRUSHING_ORES) {
            lootTables.put(ORE, fortunableOreOreBlockLootTable(ORE, ForgeRegistries.ITEMS.getValue(new ResourceLocation(ORE.getRegistryName().toString().replace("_ore","")))));
        }
        for (Block BLK : RankineLists.FLOWER_POTS) {
            lootTables.put(BLK, droppingAndFlowerPot(((FlowerPotBlock)BLK).getFlower()));
        }
        for (Block BLK : RankineLists.EIGHT_LAYER_BLOCKS) {
            lootTables.put(BLK, eightLayerBlock(BLK));
        }
        for (Block LEAF : RankineLists.LEAVES) {
            Block SAPLING = RankineLists.SAPLINGS.get(RankineLists.LEAVES.indexOf(LEAF));
            lootTables.put(LEAF, droppingWithChancesAndSticks(LEAF, SAPLING, DEFAULT_SAPLING_DROP_RATES));
        }
        for (Block BLK : Stream.of(
                RankineLists.TALL_FLOWERS,
                RankineLists.METAL_DOORS,
                RankineLists.WOODEN_DOORS
        ).flatMap(Collection::stream).collect(Collectors.toList())) {
            lootTables.put(BLK, droppingWhen(BLK, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        }
                
        
        
/*

        //ALLOYS
        for (String s : RankineLists.ALLOYS_S) {
            if (!s.equals("solder")) {
                createBlockLootTable(getBlock(s+"_block"));
                createBlockLootTable(getBlock(s+"_pedestal"));
                createBlockLootTable(getBlock(s+"_pole"));
                //createBlockLootTable(getBlock(s+"_bars"));
            }
        }

        for (String s : RankineLists.SHEETMETALS_S) {
            createBlockLootTable(getBlock(s));
            //createBlockLootTable(getBlock(s+"_vertical_slab"), BlockLootTables::droppingSlab);
        }
        for (String s : RankineLists.GRASSY_BLOCKS) {
            createBlockLootTable(getBlock(s));
        }
        for (String s : RankineLists.ROTATION_BLOCKS) {
            createBlockLootTable(getBlock(s));
        }
        for (String s : RankineLists.SOIL_BLOCKS) {
            createBlockLootTable(getBlock(s));
        }
        for (String s : RankineLists.NORMAL_BLOCKS) {
            createBlockLootTable(getBlock(s));
        }
        for (String s : RankineLists.MINERAL_WOOL_S) {
            createBlockLootTable(getBlock(s));
        }
        for (String s : RankineLists.FIBER_BLOCKS_S) {
            createBlockLootTable(getBlock(s));
        }
        for (String s : RankineLists.FIBER_MATS_S) {
            createBlockLootTable(getBlock(s));
        }
        for (String s : RankineLists.LEDS_S) {
            createBlockLootTable(getBlock(s));
        }
        for (Block blk : RankineLists.GEODES) {
            createBlockLootTable(blk);
        }
        for (String s : RankineLists.MINERALS_AND_BLOCKS) {
            createBlockLootTable(getBlock(s+"_block"));
        }
        for (String s : RankineLists.BLOCK_MACHINES) {
            createBlockLootTable(getBlock(s));
        }

        /*
        //WOODS
        for (String s : RankineLists.WOODS) {
            if (s.equals("bamboo")) {
                registerDropSelfLootTable(getBlock(s+"_planks"));
                registerDropSelfLootTable(getBlock(s+"_wall"));
            } else if (s.equals("bamboo_culms")) {
                registerDropSelfLootTable(getBlock(s));
                registerDropSelfLootTable(getBlock(s+"_wall"));
            } else {
                registerDropSelfLootTable(getBlock(s+"_log"));
                registerDropSelfLootTable(getBlock(s+"_wood"));
                registerDropSelfLootTable(getBlock("stripped_"+s+"_log"));
                registerDropSelfLootTable(getBlock("stripped_"+s+"_wood"));
                registerDropSelfLootTable(getBlock(s+"_planks"));
                registerLootTable(getBlock(s+"_leaves"),
                        (leaves) -> droppingWithChancesAndSticks(leaves, getBlock(s+"_sapling"), DEFAULT_SAPLING_DROP_RATES));
                registerDropSelfLootTable(getBlock(s+"_sapling"));
                registerFlowerPot(getBlock("potted_"+s+"_sapling"));
            }
            registerLootTable(getBlock(s+"_slab"), BlockLootTables::droppingSlab);
            registerDropSelfLootTable(getBlock(s+"_stairs"));
            registerLootTable(getBlock(s+"_vertical_slab"), BlockLootTables::droppingSlab);
            registerDropSelfLootTable(getBlock(s+"_button"));
            registerDropSelfLootTable(getBlock(s+"_pressure_plate"));
            registerDropSelfLootTable(getBlock(s+"_fence"));
            registerDropSelfLootTable(getBlock(s+"_fence_gate"));
            registerDropSelfLootTable(getBlock(s+"_trapdoor"));
            registerLootTable(getBlock(s+"_door"), BlockLootTables::registerDoor);

        }


        //ORES
        for (String s : RankineLists.FORTUNE_ORES) {
            String oreID = Arrays.asList(s.split("_ore")).get(0);
            Block oreBlock = getBlock(s);
            Item oreItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation("rankine", oreID));

            switch (oreID) {
                case "diamond":
                    oreItem = Items.DIAMOND;
                    break;
                case "emerald":
                    oreItem = Items.EMERALD;
                    break;
                case "quartz":
                    oreItem = Items.EMERALD;
                    break;
                case "lazurite":
                    oreItem = Items.LAPIS_LAZULI;
                    break;
                case "majorite":
                    oreItem = RankineItems.GARNET.get();
                    break;
                case "plumbago":
                    oreItem = RankineItems.GRAPHITE.get();
                    break;
                case "moissanite":
                    oreItem = RankineItems.SILICON_CARBIDE.get();
                    break;
                case "lonsdaleite":
                    oreItem = RankineItems.LONSDALEITE_DIAMOND.get();
                    break;
                case "halite":
                    oreItem = RankineItems.SALT.get();
                    break;
                case "pink_halite":
                    oreItem = RankineItems.PINK_SALT.get();
                    break;
                case "subbituminous":
                    oreItem = RankineItems.SUBBITUMINOUS_COAL.get();
                    break;
                case "bituminous":
                    oreItem = RankineItems.BITUMINOUS_COAL.get();
                    break;
                case "anthracite":
                    oreItem = RankineItems.ANTHRACITE_COAL.get();
                    break;
            }
            Item idky = oreItem;
            registerLootTable(oreBlock, (ore) -> droppingItemWithFortune(ore, idky));
        }
        for (String s : RankineLists.SELF_ORES) {
            registerDropSelfLootTable(getBlock(s));
        }
        for (String s : RankineLists.NATIVE_ORES) {
            String ele = Arrays.asList(s.split("_")).get(1);
            if (ele.equals("gold")) {
                registerLootTable(getBlock(s), (ore) -> droppingWithSilkTouch(ore, withExplosionDecay(ore, ItemLootEntry.builder(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft",ele+"nuget"))).acceptFunction(SetCount.builder(RandomValueRange.of(3.0F, 8.0F))).acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE)))));
            } else {
                registerLootTable(getBlock(s), (ore) -> droppingWithSilkTouch(ore, withExplosionDecay(ore, ItemLootEntry.builder(ForgeRegistries.ITEMS.getValue(new ResourceLocation("rankine",ele+"nuget"))).acceptFunction(SetCount.builder(RandomValueRange.of(3.0F, 8.0F))).acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE)))));
            }
        }


        for (String s : RankineLists.MACHINES) {
            registerLootTable(getBlock(s), BlockLootTables::droppingWithName);
        }

        for (String s : Arrays.asList("cast_iron_support","fiber_block","uncolored_concrete","roman_concrete","polished_roman_concrete","roman_concrete_bricks","clay_bricks","refractory_bricks","high_refractory_bricks","ultra_high_refractory_bricks","checkered_marble","checkered_dacite","checkered_porphyry")) {
            if (!s.equals("cast_iron_support")) {
                registerDropSelfLootTable(getBlock(s+"_wall"));
            }
            registerDropSelfLootTable(getBlock(s));
            registerLootTable(getBlock(s+"_slab"), BlockLootTables::droppingSlab);
            registerDropSelfLootTable(getBlock(s+"_stairs"));
            registerLootTable(getBlock(s+"_vertical_slab"), BlockLootTables::droppingSlab);
        }


            for (String s : RankineLists.SIXTEEN) {
          //  Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("rankine",s));
          //  Block block = getBlock(s);
         //   registerLootTable(block, (blk) -> LootTable.builder().addLootPool(LootPool.builder().acceptCondition(EntityHasProperty.builder(LootContext.EntityTarget.THIS)).addEntry(AlternativesLootEntry.builder(AlternativesLootEntry.builder(ItemLootEntry.builder(item).acceptCondition(BlockStateProperty.builder(blk).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SnowBlock.LAYERS, 1))), ItemLootEntry.builder(item).acceptCondition(BlockStateProperty.builder(blk).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SnowBlock.LAYERS, 2))).acceptFunction(SetCount.builder(ConstantRange.of(2))), ItemLootEntry.builder(item).acceptCondition(BlockStateProperty.builder(blk).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SnowBlock.LAYERS, 3))).acceptFunction(SetCount.builder(ConstantRange.of(3))), ItemLootEntry.builder(item).acceptCondition(BlockStateProperty.builder(blk).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SnowBlock.LAYERS, 4))).acceptFunction(SetCount.builder(ConstantRange.of(4))), ItemLootEntry.builder(item).acceptCondition(BlockStateProperty.builder(blk).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SnowBlock.LAYERS, 5))).acceptFunction(SetCount.builder(ConstantRange.of(5))), ItemLootEntry.builder(item).acceptCondition(BlockStateProperty.builder(blk).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SnowBlock.LAYERS, 6))).acceptFunction(SetCount.builder(ConstantRange.of(6))), ItemLootEntry.builder(item).acceptCondition(BlockStateProperty.builder(blk).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SnowBlock.LAYERS, 7))).acceptFunction(SetCount.builder(ConstantRange.of(7))), ItemLootEntry.builder(item).acceptFunction(SetCount.builder(ConstantRange.of(8)))).acceptCondition(NO_SILK_TOUCH), AlternativesLootEntry.builder(ItemLootEntry.builder(Blocks.SNOW).acceptCondition(BlockStateProperty.builder(blk).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SnowBlock.LAYERS, 1))), ItemLootEntry.builder(Blocks.SNOW).acceptFunction(SetCount.builder(ConstantRange.of(2))).acceptCondition(BlockStateProperty.builder(blk).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SnowBlock.LAYERS, 2))), ItemLootEntry.builder(Blocks.SNOW).acceptFunction(SetCount.builder(ConstantRange.of(3))).acceptCondition(BlockStateProperty.builder(blk).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SnowBlock.LAYERS, 3))), ItemLootEntry.builder(Blocks.SNOW).acceptFunction(SetCount.builder(ConstantRange.of(4))).acceptCondition(BlockStateProperty.builder(blk).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SnowBlock.LAYERS, 4))), ItemLootEntry.builder(Blocks.SNOW).acceptFunction(SetCount.builder(ConstantRange.of(5))).acceptCondition(BlockStateProperty.builder(blk).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SnowBlock.LAYERS, 5))), ItemLootEntry.builder(Blocks.SNOW).acceptFunction(SetCount.builder(ConstantRange.of(6))).acceptCondition(BlockStateProperty.builder(blk).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SnowBlock.LAYERS, 6))), ItemLootEntry.builder(Blocks.SNOW).acceptFunction(SetCount.builder(ConstantRange.of(7))).acceptCondition(BlockStateProperty.builder(blk).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SnowBlock.LAYERS, 7))), ItemLootEntry.builder(Blocks.SNOW).acceptFunction(SetCount.builder(ConstantRange.of(8))).acceptCondition(BlockStateProperty.builder(blk).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SnowBlock.LAYERS, 8))), ItemLootEntry.builder(Blocks.SNOW).acceptFunction(SetCount.builder(ConstantRange.of(9))).acceptCondition(BlockStateProperty.builder(blk).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SnowBlock.LAYERS, 9))), ItemLootEntry.builder(Blocks.SNOW).acceptFunction(SetCount.builder(ConstantRange.of(10))).acceptCondition(BlockStateProperty.builder(blk).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SnowBlock.LAYERS, 10))), ItemLootEntry.builder(Blocks.SNOW).acceptFunction(SetCount.builder(ConstantRange.of(11))).acceptCondition(BlockStateProperty.builder(blk).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SnowBlock.LAYERS, 11))), ItemLootEntry.builder(Blocks.SNOW).acceptFunction(SetCount.builder(ConstantRange.of(12))).acceptCondition(BlockStateProperty.builder(blk).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SnowBlock.LAYERS, 12))), ItemLootEntry.builder(Blocks.SNOW).acceptFunction(SetCount.builder(ConstantRange.of(13))).acceptCondition(BlockStateProperty.builder(blk).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SnowBlock.LAYERS, 13))), ItemLootEntry.builder(Blocks.SNOW).acceptFunction(SetCount.builder(ConstantRange.of(14))).acceptCondition(BlockStateProperty.builder(blk).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SnowBlock.LAYERS, 14))), ItemLootEntry.builder(Blocks.SNOW).acceptFunction(SetCount.builder(ConstantRange.of(15))).acceptCondition(BlockStateProperty.builder(blk).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SnowBlock.LAYERS, 15))), ItemLootEntry.builder(Blocks.SNOW).acceptFunction(SetCount.builder(ConstantRange.of(16))).acceptCondition(BlockStateProperty.builder(blk).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SnowBlock.LAYERS, 16))))))));
            registerDropSelfLootTable(getBlock(s));

        }

        registerLootTable(RankineBlocks.ELDERBERRY_BUSH.get(), (berry) -> withExplosionDecay(berry, LootTable.builder().addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(RankineBlocks.SNOWBERRY_BUSH.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SweetBerryBushBlock.AGE, 3))).addEntry(ItemLootEntry.builder(RankineItems.SNOWBERRIES.get())).acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 3.0F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE))).addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(RankineBlocks.SNOWBERRY_BUSH.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SweetBerryBushBlock.AGE, 2))).addEntry(ItemLootEntry.builder(RankineItems.SNOWBERRIES.get())).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE)))));
        registerLootTable(RankineBlocks.BLUEBERRY_BUSH.get(), (berry) -> withExplosionDecay(berry, LootTable.builder().addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(RankineBlocks.SNOWBERRY_BUSH.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SweetBerryBushBlock.AGE, 3))).addEntry(ItemLootEntry.builder(RankineItems.SNOWBERRIES.get())).acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 3.0F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE))).addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(RankineBlocks.SNOWBERRY_BUSH.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SweetBerryBushBlock.AGE, 2))).addEntry(ItemLootEntry.builder(RankineItems.SNOWBERRIES.get())).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE)))));
        registerLootTable(RankineBlocks.CRANBERRY_BUSH.get(), (berry) -> withExplosionDecay(berry, LootTable.builder().addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(RankineBlocks.SNOWBERRY_BUSH.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SweetBerryBushBlock.AGE, 3))).addEntry(ItemLootEntry.builder(RankineItems.SNOWBERRIES.get())).acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 3.0F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE))).addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(RankineBlocks.SNOWBERRY_BUSH.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SweetBerryBushBlock.AGE, 2))).addEntry(ItemLootEntry.builder(RankineItems.SNOWBERRIES.get())).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE)))));

        registerLootTable(RankineBlocks.SNOWBERRY_BUSH.get(), (berry) -> withExplosionDecay(berry, LootTable.builder().addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(RankineBlocks.SNOWBERRY_BUSH.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SweetBerryBushBlock.AGE, 3))).addEntry(ItemLootEntry.builder(RankineItems.SNOWBERRIES.get())).acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 3.0F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE))).addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(RankineBlocks.SNOWBERRY_BUSH.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SweetBerryBushBlock.AGE, 2))).addEntry(ItemLootEntry.builder(RankineItems.SNOWBERRIES.get())).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE)))));
        registerLootTable(RankineBlocks.RASPBERRY_BUSH.get(), (berry) -> withExplosionDecay(berry, LootTable.builder().addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(RankineBlocks.SNOWBERRY_BUSH.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SweetBerryBushBlock.AGE, 3))).addEntry(ItemLootEntry.builder(RankineItems.SNOWBERRIES.get())).acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 3.0F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE))).addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(RankineBlocks.SNOWBERRY_BUSH.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SweetBerryBushBlock.AGE, 2))).addEntry(ItemLootEntry.builder(RankineItems.SNOWBERRIES.get())).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE)))));
        registerLootTable(RankineBlocks.BLACKBERRY_BUSH.get(), (berry) -> withExplosionDecay(berry, LootTable.builder().addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(RankineBlocks.SNOWBERRY_BUSH.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SweetBerryBushBlock.AGE, 3))).addEntry(ItemLootEntry.builder(RankineItems.SNOWBERRIES.get())).acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 3.0F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE))).addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(RankineBlocks.SNOWBERRY_BUSH.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SweetBerryBushBlock.AGE, 2))).addEntry(ItemLootEntry.builder(RankineItems.SNOWBERRIES.get())).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE)))));
        registerLootTable(RankineBlocks.STRAWBERRY_BUSH.get(), (berry) -> withExplosionDecay(berry, LootTable.builder().addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(RankineBlocks.SNOWBERRY_BUSH.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SweetBerryBushBlock.AGE, 3))).addEntry(ItemLootEntry.builder(RankineItems.SNOWBERRIES.get())).acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 3.0F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE))).addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(RankineBlocks.SNOWBERRY_BUSH.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SweetBerryBushBlock.AGE, 2))).addEntry(ItemLootEntry.builder(RankineItems.SNOWBERRIES.get())).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE)))));
        registerLootTable(RankineBlocks.PINEAPPLE_BUSH.get(), (berry) -> withExplosionDecay(berry, LootTable.builder().addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(RankineBlocks.SNOWBERRY_BUSH.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SweetBerryBushBlock.AGE, 3))).addEntry(ItemLootEntry.builder(RankineItems.SNOWBERRIES.get())).acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 3.0F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE))).addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(RankineBlocks.SNOWBERRY_BUSH.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SweetBerryBushBlock.AGE, 2))).addEntry(ItemLootEntry.builder(RankineItems.SNOWBERRIES.get())).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE)))));
        registerLootTable(RankineBlocks.BANANA_YUCCA_BUSH.get(), (berry) -> withExplosionDecay(berry, LootTable.builder().addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(RankineBlocks.SNOWBERRY_BUSH.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SweetBerryBushBlock.AGE, 3))).addEntry(ItemLootEntry.builder(RankineItems.SNOWBERRIES.get())).acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 3.0F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE))).addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(RankineBlocks.SNOWBERRY_BUSH.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SweetBerryBushBlock.AGE, 2))).addEntry(ItemLootEntry.builder(RankineItems.SNOWBERRIES.get())).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE)))));
        registerLootTable(RankineBlocks.ALOE_PLANT.get(), (berry) -> withExplosionDecay(berry, LootTable.builder().addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(RankineBlocks.SNOWBERRY_BUSH.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SweetBerryBushBlock.AGE, 3))).addEntry(ItemLootEntry.builder(RankineItems.SNOWBERRIES.get())).acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 3.0F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE))).addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(RankineBlocks.SNOWBERRY_BUSH.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SweetBerryBushBlock.AGE, 2))).addEntry(ItemLootEntry.builder(RankineItems.SNOWBERRIES.get())).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE)))));
        registerLootTable(RankineBlocks.CAMPHOR_BASIL_PLANT.get(), (berry) -> withExplosionDecay(berry, LootTable.builder().addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(RankineBlocks.SNOWBERRY_BUSH.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SweetBerryBushBlock.AGE, 3))).addEntry(ItemLootEntry.builder(RankineItems.SNOWBERRIES.get())).acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 3.0F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE))).addLootPool(LootPool.builder().acceptCondition(BlockStateProperty.builder(RankineBlocks.SNOWBERRY_BUSH.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(SweetBerryBushBlock.AGE, 2))).addEntry(ItemLootEntry.builder(RankineItems.SNOWBERRIES.get())).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F))).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE)))));

        registerLootTable(RankineBlocks.CORN_STALK.get(), (blk) -> droppingWhen(blk, CornStalkBlock.SECTION, TripleBlockSection.BOTTOM));
        registerLootTable(RankineBlocks.CORN_PLANT.get(), (blk) -> droppingWhen(blk, TripleCropsBlock.SECTION, TripleBlockSection.BOTTOM));
        registerLootTable(RankineBlocks.JUTE_PLANT.get(), (blk) -> droppingWhen(blk, TripleCropsBlock.SECTION, TripleBlockSection.BOTTOM));
        registerLootTable(RankineBlocks.ASPARAGUS_PLANT.get(), (blk) -> droppingWhen(blk, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        registerLootTable(RankineBlocks.COTTON_PLANT.get(), (blk) -> droppingWhen(blk, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        registerDropSelfLootTable(RankineBlocks.RICE_PLANT.get());

        for (String s : RankineLists.METAL_DECOR) {
            registerDropSelfLootTable(getBlock(s+"_trapdoor"));
            registerLootTable(getBlock(s+"_door"), BlockLootTables::registerDoor);
        }
        //SELF DROP
        for (String s : Arrays.asList("stick_block","charred_wood","tree_tap","rope","cast_iron_bars","cast_iron_ladder","brass_ladder","aluminum_ladder","mellite","mycelium_path","unaged_cheese","bone_char_block","trampoline","cork","quarry_barrier")) {
            registerDropSelfLootTable(getBlock(s));
        }
        //NO DROP
        for (Block blk : Arrays.asList(RankineBlocks.AGED_CHEESE.get(), RankineBlocks.ASPARAGUS_ROOT.get())) {
            registerLootTable(blk, blockNoDrop());
        }
        for (String s : RankineLists.GAS_BLOCKS) {
            registerLootTable(getBlock(s), blockNoDrop());
        }



    }

         */

    }



}
