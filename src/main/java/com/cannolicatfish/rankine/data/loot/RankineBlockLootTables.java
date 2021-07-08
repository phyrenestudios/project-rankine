package com.cannolicatfish.rankine.data.loot;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.plants.CornStalkBlock;
import com.cannolicatfish.rankine.blocks.plants.TripleCropsBlock;
import com.cannolicatfish.rankine.blocks.states.TripleBlockSection;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import com.google.common.collect.ImmutableSet;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.*;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RankineBlockLootTables extends BlockLootTables {
    private static final ILootCondition.IBuilder SILK_TOUCH = MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
    private static final ILootCondition.IBuilder NO_SILK_TOUCH = SILK_TOUCH.inverted();
    private static final ILootCondition.IBuilder SHEARS = MatchTool.builder(ItemPredicate.Builder.create().item(Items.SHEARS));
    private static final ILootCondition.IBuilder SILK_TOUCH_OR_SHEARS = SHEARS.alternative(SILK_TOUCH);
    private static final ILootCondition.IBuilder NOT_SILK_TOUCH_OR_SHEARS = SILK_TOUCH_OR_SHEARS.inverted();
    private static final Set<Item> IMMUNE_TO_EXPLOSIONS = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(IItemProvider::asItem).collect(ImmutableSet.toImmutableSet());
    private static final float[] DEFAULT_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
    private static final float[] DOUBLE_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
    private static final float[] RARE_SAPLING_DROP_RATES = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};

    @Override
    protected Iterable<Block> getKnownBlocks() {
        Iterable<Block> getKnownBlocks =
        ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> ProjectRankine.MODID.equals(block.getRegistryName().getNamespace()))
                .collect(Collectors.toSet());
        System.out.println(getKnownBlocks);
        return getKnownBlocks;
    }



    @Override
    protected void addTables() {

        //STONES
        for (String x : RankineLists.STONES) {
            for (String s : Arrays.asList(x, "polished_" + x, x + "_bricks")) {
                registerDropSelfLootTable(getBlock(s));
                registerLootTable(getBlock(s+"_slab"), BlockLootTables::droppingSlab);
                registerDropSelfLootTable(getBlock(s+"_stairs"));
                registerDropSelfLootTable(getBlock(s+"_wall"));
                registerLootTable(getBlock(s+"_vertical_slab"), BlockLootTables::droppingSlab);
                registerDropSelfLootTable(getBlock(s+"_button"));
                registerDropSelfLootTable(getBlock(s+"_pressure_plate"));
            }
        }

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





        //ELEMENT BLOCKS
        for (String s : RankineLists.ELEMENTS) {
            registerDropSelfLootTable(getBlock(s+"_block"));
        }
        //ALLOYS
        for (String s : RankineLists.ALLOYS) {
            if (!s.equals("solder")) {
                registerDropSelfLootTable(getBlock(s+"_block"));
                registerDropSelfLootTable(getBlock(s+"_pedestal"));
                registerDropSelfLootTable(getBlock(s+"_pole"));
                //registerDropSelfLootTable(getBlock(s+"_bars"));
            }
        }

        for (String s : RankineLists.SHEETMETALS) {
            registerDropSelfLootTable(getBlock(s));
            registerLootTable(getBlock(s+"_vertical_slab"), BlockLootTables::droppingSlab);
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

        for (String s : RankineLists.GRASSY_BLOCKS) {
            registerDropSelfLootTable(getBlock(s));
        }
        for (String s : RankineLists.ROTATION_BLOCKS) {
            registerDropSelfLootTable(getBlock(s));
        }
        for (String s : RankineLists.NORMAL_BLOCKS) {
            registerDropSelfLootTable(getBlock(s));
        }
        for (String s : RankineLists.MINERAL_WOOL) {
            registerDropSelfLootTable(getBlock(s));
        }
        for (String s : RankineLists.FIBER_BLOCKS) {
            registerDropSelfLootTable(getBlock(s));
        }
        for (String s : RankineLists.FIBER_MATS) {
            registerDropSelfLootTable(getBlock(s));
        }
        for (String s : RankineLists.LEDS) {
            registerDropSelfLootTable(getBlock(s));
        }
        for (String s : RankineLists.GEODES) {
            registerDropSelfLootTable(getBlock(s));
        }
        for (String s : RankineLists.MINERALS_AND_BLOCKS) {
            registerDropSelfLootTable(getBlock(s+"_block"));
        }
        for (String s : RankineLists.BLOCK_MACHINES) {
            registerDropSelfLootTable(getBlock(s));
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


    private Block getBlock(String s) {
        Block blk = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine",s));
        if (blk != null) {
            //System.out.println("rankine:"+s);
            return blk;
        } else {
            System.out.println("rankine:"+s+ " does not exist");
            return blk;
        }
    }


}
