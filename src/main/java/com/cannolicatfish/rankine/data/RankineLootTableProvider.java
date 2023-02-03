package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.blocks.MetalPoleBlock;
import com.cannolicatfish.rankine.blocks.RankineEightLayerBlock;
import com.cannolicatfish.rankine.blocks.RankineVerticalSlabBlock;
import com.cannolicatfish.rankine.blocks.plants.DoubleCropsBlock;
import com.cannolicatfish.rankine.blocks.plants.RankinePlantBlock;
import com.cannolicatfish.rankine.blocks.plants.TripleCropsBlock;
import com.cannolicatfish.rankine.blocks.states.TripleBlockSection;
import com.cannolicatfish.rankine.blocks.states.VerticalSlabStates;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.advancements.criterion.*;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.*;
import net.minecraft.loot.functions.*;
import net.minecraft.state.Property;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public abstract class RankineLootTableProvider extends LootTableProvider {

    protected static final ILootCondition.IBuilder SILK_TOUCH = MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
    protected static final ILootCondition.IBuilder NO_SILK_TOUCH = SILK_TOUCH.inverted();
    protected static final ILootCondition.IBuilder SHEARS = MatchTool.builder(ItemPredicate.Builder.create().item(Items.SHEARS));
    protected static final ILootCondition.IBuilder SILK_TOUCH_OR_SHEARS = SHEARS.alternative(SILK_TOUCH);
    protected static final ILootCondition.IBuilder NOT_SILK_TOUCH_OR_SHEARS = SILK_TOUCH_OR_SHEARS.inverted();
    protected static final Set<Item> IMMUNE_TO_EXPLOSIONS = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(IItemProvider::asItem).collect(ImmutableSet.toImmutableSet());
    protected static final float[] DEFAULT_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
    protected static final float[] DOUBLE_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
    protected static final float[] RARE_SAPLING_DROP_RATES = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    // Filled by subclasses
    protected final Map<Block, LootTable.Builder> lootTables = new HashMap<>();

    private final DataGenerator generator;

    public RankineLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
        this.generator = dataGeneratorIn;
    }

    @Override
    // Entry point
    public void act(DirectoryCache cache) {
        addTables();

        Map<ResourceLocation, LootTable> tables = new HashMap<>();
        for (Map.Entry<Block, LootTable.Builder> entry : lootTables.entrySet()) {
            tables.put(entry.getKey().getLootTable(), entry.getValue().setParameterSet(LootParameterSets.BLOCK).build());
        }
        writeTables(cache, tables);
    }

    // Actually write out the tables in the output folder
    private void writeTables(DirectoryCache cache, Map<ResourceLocation, LootTable> tables) {
        Path outputFolder = this.generator.getOutputFolder();
        tables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                IDataProvider.save(GSON, cache, LootTableManager.toJson(lootTable), path);
            } catch (IOException e) {
                LOGGER.error("Couldn't write loot table {}", path, e);
            }
        });
    }

    @Override
    public String getName() {
        return "Project Rankine - LootTables";
    }

    protected abstract void addTables();

    protected static <T> T withExplosionDecay(IItemProvider item, ILootFunctionConsumer<T> function) {
        return (T)(!IMMUNE_TO_EXPLOSIONS.contains(item.asItem()) ? function.acceptFunction(ExplosionDecay.builder()) : function.cast());
    }

    protected static <T> T withSurvivesExplosion(IItemProvider item, ILootConditionConsumer<T> condition) {
        return (T)(!IMMUNE_TO_EXPLOSIONS.contains(item.asItem()) ? condition.acceptCondition(SurvivesExplosion.builder()) : condition.cast());
    }

    protected static LootTable.Builder dropping(IItemProvider item) {
        return LootTable.builder().addLootPool(withSurvivesExplosion(item, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(item))));
    }

    protected static LootTable.Builder dropping(Block block, ILootCondition.IBuilder conditionBuilder, LootEntry.Builder<?> p_218494_2_) {
        return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(block).acceptCondition(conditionBuilder).alternatively(p_218494_2_)));
    }

    protected static LootTable.Builder droppingWithSilkTouch(Block block, LootEntry.Builder<?> builder) {
        return dropping(block, SILK_TOUCH, builder);
    }

    protected static LootTable.Builder droppingWithShears(Block block, LootEntry.Builder<?> noShearAlternativeEntry) {
        return dropping(block, SHEARS, noShearAlternativeEntry);
    }

    protected static LootTable.Builder droppingWithSilkTouchOrShears(Block block, LootEntry.Builder<?> alternativeLootEntry) {
        return dropping(block, SILK_TOUCH_OR_SHEARS, alternativeLootEntry);
    }

    protected static LootTable.Builder droppingWithSilkTouch(Block block, IItemProvider noSilkTouch) {
        return droppingWithSilkTouch(block, withSurvivesExplosion(block, ItemLootEntry.builder(noSilkTouch)));
    }

    protected static LootTable.Builder droppingRandomly(IItemProvider item, IRandomRange range) {
        return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(withExplosionDecay(item, ItemLootEntry.builder(item).acceptFunction(SetCount.builder(range)))));
    }

    protected static LootTable.Builder droppingWithSilkTouchOrRandomly(Block block, IItemProvider item, IRandomRange range) {
        return droppingWithSilkTouch(block, withExplosionDecay(block, ItemLootEntry.builder(item).acceptFunction(SetCount.builder(range))));
    }

    protected static LootTable.Builder onlyWithSilkTouch(IItemProvider item) {
        return LootTable.builder().addLootPool(LootPool.builder().acceptCondition(SILK_TOUCH).rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(item)));
    }

    protected static LootTable.Builder droppingAndFlowerPot(IItemProvider flower) {
        return LootTable.builder().addLootPool(withSurvivesExplosion(Blocks.FLOWER_POT, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Blocks.FLOWER_POT)))).addLootPool(withSurvivesExplosion(flower, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(flower))));
    }

    protected static LootTable.Builder droppingSlab(Block slab) {
        return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(withExplosionDecay(slab, ItemLootEntry.builder(slab).acceptFunction(SetCount.builder(ConstantRange.of(2)).acceptCondition(BlockStateProperty.builder(slab).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(SlabBlock.TYPE, SlabType.DOUBLE)))))));
    }

    protected static <T extends Comparable<T> & IStringSerializable> LootTable.Builder droppingWhen(Block block, Property<T> property, T value) {
        return LootTable.builder().addLootPool(withSurvivesExplosion(block, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(block).acceptCondition(BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(property, value))))));
    }

    protected static LootTable.Builder droppingWithName(Block block) {
        return LootTable.builder().addLootPool(withSurvivesExplosion(block, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(block).acceptFunction(CopyName.builder(CopyName.Source.BLOCK_ENTITY)))));
    }

    protected static LootTable.Builder droppingWithContents(Block shulker) {
        return LootTable.builder().addLootPool(withSurvivesExplosion(shulker, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(shulker).acceptFunction(CopyName.builder(CopyName.Source.BLOCK_ENTITY)).acceptFunction(CopyNbt.builder(CopyNbt.Source.BLOCK_ENTITY).replaceOperation("Lock", "BlockEntityTag.Lock").replaceOperation("LootTable", "BlockEntityTag.LootTable").replaceOperation("LootTableSeed", "BlockEntityTag.LootTableSeed")).acceptFunction(SetContents.builderIn().addLootEntry(DynamicLootEntry.func_216162_a(ShulkerBoxBlock.CONTENTS))))));
    }

    protected static LootTable.Builder droppingWithPatterns(Block banner) {
        return LootTable.builder().addLootPool(withSurvivesExplosion(banner, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(banner).acceptFunction(CopyName.builder(CopyName.Source.BLOCK_ENTITY)).acceptFunction(CopyNbt.builder(CopyNbt.Source.BLOCK_ENTITY).replaceOperation("Patterns", "BlockEntityTag.Patterns")))));
    }

    private static LootTable.Builder droppingAndBees(Block nest) {
        return LootTable.builder().addLootPool(LootPool.builder().acceptCondition(SILK_TOUCH).rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(nest).acceptFunction(CopyNbt.builder(CopyNbt.Source.BLOCK_ENTITY).replaceOperation("Bees", "BlockEntityTag.Bees")).acceptFunction(CopyBlockState.func_227545_a_(nest).func_227552_a_(BeehiveBlock.HONEY_LEVEL))));
    }

    private static LootTable.Builder droppingAndBeesWithAlternative(Block hive) {
        return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(hive).acceptCondition(SILK_TOUCH).acceptFunction(CopyNbt.builder(CopyNbt.Source.BLOCK_ENTITY).replaceOperation("Bees", "BlockEntityTag.Bees")).acceptFunction(CopyBlockState.func_227545_a_(hive).func_227552_a_(BeehiveBlock.HONEY_LEVEL)).alternatively(ItemLootEntry.builder(hive))));
    }

    protected static LootTable.Builder droppingItemWithFortune(Block block, Item item) {
        return droppingWithSilkTouch(block, withExplosionDecay(block, ItemLootEntry.builder(item).acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE))));
    }

    /**
     * Creates a builder that drops the given IItemProvider in amounts between 0 and 2, most often 0. Only used in
     * vanilla for huge mushroom blocks.
     */
    protected static LootTable.Builder droppingItemRarely(Block block, IItemProvider item) {
        return droppingWithSilkTouch(block, withExplosionDecay(block, ItemLootEntry.builder(item).acceptFunction(SetCount.builder(RandomValueRange.of(-6.0F, 2.0F))).acceptFunction(LimitCount.func_215911_a(IntClamper.func_215848_a(0)))));
    }

    protected static LootTable.Builder droppingSeeds(Block block) {
        return droppingWithShears(block, withExplosionDecay(block, ItemLootEntry.builder(Items.WHEAT_SEEDS).acceptCondition(RandomChance.builder(0.125F)).acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE, 2))));
    }

    /**
     * Creates a builder that drops the given IItemProvider in amounts between 0 and 3, based on the AGE property. Only
     * used in vanilla for pumpkin and melon stems.
     */
    protected static LootTable.Builder droppingByAge(Block stemFruit, Item item) {
        return LootTable.builder().addLootPool(withExplosionDecay(stemFruit, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(item).acceptFunction(SetCount.builder(BinomialRange.of(3, 0.06666667F)).acceptCondition(BlockStateProperty.builder(stemFruit).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(StemBlock.AGE, 0)))).acceptFunction(SetCount.builder(BinomialRange.of(3, 0.13333334F)).acceptCondition(BlockStateProperty.builder(stemFruit).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(StemBlock.AGE, 1)))).acceptFunction(SetCount.builder(BinomialRange.of(3, 0.2F)).acceptCondition(BlockStateProperty.builder(stemFruit).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(StemBlock.AGE, 2)))).acceptFunction(SetCount.builder(BinomialRange.of(3, 0.26666668F)).acceptCondition(BlockStateProperty.builder(stemFruit).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(StemBlock.AGE, 3)))).acceptFunction(SetCount.builder(BinomialRange.of(3, 0.33333334F)).acceptCondition(BlockStateProperty.builder(stemFruit).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(StemBlock.AGE, 4)))).acceptFunction(SetCount.builder(BinomialRange.of(3, 0.4F)).acceptCondition(BlockStateProperty.builder(stemFruit).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(StemBlock.AGE, 5)))).acceptFunction(SetCount.builder(BinomialRange.of(3, 0.46666667F)).acceptCondition(BlockStateProperty.builder(stemFruit).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(StemBlock.AGE, 6)))).acceptFunction(SetCount.builder(BinomialRange.of(3, 0.53333336F)).acceptCondition(BlockStateProperty.builder(stemFruit).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(StemBlock.AGE, 7)))))));
    }

    private static LootTable.Builder dropSeedsForStem(Block stem, Item stemSeed) {
        return LootTable.builder().addLootPool(withExplosionDecay(stem, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(stemSeed).acceptFunction(SetCount.builder(BinomialRange.of(3, 0.53333336F))))));
    }

    protected static LootTable.Builder onlyWithShears(IItemProvider item) {
        return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).acceptCondition(SHEARS).addEntry(ItemLootEntry.builder(item)));
    }

    /**
     * Used for all leaves, drops self with silk touch, otherwise drops the second Block param with the passed chances
     * for fortune levels, adding in sticks.
     */
    protected static LootTable.Builder droppingWithChancesAndSticks(Block block, Block sapling, float... chances) {
        return droppingWithSilkTouchOrShears(block, withSurvivesExplosion(block, ItemLootEntry.builder(sapling)).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, chances))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).acceptCondition(NOT_SILK_TOUCH_OR_SHEARS).addEntry(withExplosionDecay(block, ItemLootEntry.builder(Items.STICK).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)))).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
    }

    /**
     * Used for oak and dark oak, same as droppingWithChancesAndSticks but adding in apples.
     */
    protected static LootTable.Builder droppingWithChancesSticksAndApples(Block block, Block sapling, float... chances) {
        return droppingWithChancesAndSticks(block, sapling, chances).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).acceptCondition(NOT_SILK_TOUCH_OR_SHEARS).addEntry(withSurvivesExplosion(block, ItemLootEntry.builder(Items.APPLE)).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
    }
    protected static LootTable.Builder droppingWithChancesSticksAndExtra(Block block, Block sapling, Item item, float... chances) {
        return droppingWithChancesAndSticks(block, sapling, chances).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).acceptCondition(NOT_SILK_TOUCH_OR_SHEARS).addEntry(withSurvivesExplosion(block, ItemLootEntry.builder(item)).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
    }

    /**
     * Drops the first item parameter always, and the second item parameter plus more of the first acceptCondition the loot
     * condition is met, acceptFunctioning fortune to only the second argument.
     */
    protected static LootTable.Builder droppingAndBonusWhen(Block block, Item itemConditional, Item withBonus, ILootCondition.IBuilder conditionBuilder) {
        return withExplosionDecay(block, LootTable.builder().addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(itemConditional).acceptCondition(conditionBuilder).alternatively(ItemLootEntry.builder(withBonus)))).addLootPool(LootPool.builder().acceptCondition(conditionBuilder).addEntry(ItemLootEntry.builder(withBonus).acceptFunction(ApplyBonus.binomialWithBonusCount(Enchantments.FORTUNE, 0.5714286F, 3)))));
    }

    private static LootTable.Builder droppingSheared(Block sheared) {
        return LootTable.builder().addLootPool(LootPool.builder().acceptCondition(SHEARS).addEntry(ItemLootEntry.builder(sheared).acceptFunction(SetCount.builder(ConstantRange.of(2)))));
    }

    private static LootTable.Builder droppingSeedsTall(Block block, Block sheared) {
        LootEntry.Builder<?> builder = ItemLootEntry.builder(sheared).acceptFunction(SetCount.builder(ConstantRange.of(2))).acceptCondition(SHEARS).alternatively(withSurvivesExplosion(block, ItemLootEntry.builder(Items.WHEAT_SEEDS)).acceptCondition(RandomChance.builder(0.125F)));
        return LootTable.builder().addLootPool(LootPool.builder().addEntry(builder).acceptCondition(BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))).acceptCondition(LocationCheck.func_241547_a_(LocationPredicate.Builder.builder().block(BlockPredicate.Builder.createBuilder().setBlock(block).setStatePredicate(StatePropertiesPredicate.Builder.newBuilder().withProp(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).build()).build()), new BlockPos(0, 1, 0)))).addLootPool(LootPool.builder().addEntry(builder).acceptCondition(BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))).acceptCondition(LocationCheck.func_241547_a_(LocationPredicate.Builder.builder().block(BlockPredicate.Builder.createBuilder().setBlock(block).setStatePredicate(StatePropertiesPredicate.Builder.newBuilder().withProp(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).build()).build()), new BlockPos(0, -1, 0))));
    }

    public static LootTable.Builder blockNoDrop() {
        return LootTable.builder();
    }



    //BASE LOOTTABLES
    protected LootTable.Builder metalPole(Block BLK) {
        return LootTable.builder()
                .addLootPool(LootPool.builder()
                        .rolls(ConstantRange.of(1))
                        .addEntry(AlternativesLootEntry.builder(
                                ItemLootEntry.builder(RankineItems.GARLAND.get())
                                        .acceptCondition(BlockStateProperty.builder(BLK).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(MetalPoleBlock.STYLE, 1))),
                                ItemLootEntry.builder(BLK)))
                        .acceptCondition(SurvivesExplosion.builder()));
    }

    protected LootTable.Builder singleCrop(Block CROP, Item DROP, Item SEED) {
        return LootTable.builder()
                .addLootPool(
                    LootPool.builder()
                        .rolls(ConstantRange.of(1))
                        .addEntry(AlternativesLootEntry.builder(
                            ItemLootEntry.builder(DROP)
                                .acceptCondition(BlockStateProperty.builder(CROP).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(CropsBlock.AGE, 7))),
                            ItemLootEntry.builder(SEED))
                        )
                        .acceptCondition(SurvivesExplosion.builder())
                )
                .addLootPool(
                    LootPool.builder()
                        .rolls(ConstantRange.of(1))
                        .addEntry(
                            ItemLootEntry.builder(SEED)
                                .acceptFunction(ApplyBonus.binomialWithBonusCount(Enchantments.FORTUNE, 0.5714286F, 2))
                        )
                        .acceptCondition(BlockStateProperty.builder(CROP).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(CropsBlock.AGE, 7)))
                        .acceptCondition(SurvivesExplosion.builder())
                );
    }

    protected LootTable.Builder doubleCrop(Block CROP, Item DROP, Item SEED, int Count) {
        return LootTable.builder()
                .addLootPool(
                    LootPool.builder().rolls(ConstantRange.of(1))
                        .addEntry(AlternativesLootEntry.builder(
                            ItemLootEntry.builder(DROP)
                                .acceptFunction(SetCount.builder(ConstantRange.of(Count)))
                                .acceptCondition(BlockStateProperty.builder(CROP).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(CropsBlock.AGE, 7).withProp(DoubleCropsBlock.SECTION, DoubleBlockHalf.LOWER))),
                            ItemLootEntry.builder(SEED)
                                .acceptCondition(BlockStateProperty.builder(CROP).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(DoubleCropsBlock.SECTION, DoubleBlockHalf.LOWER)))
                        )).acceptCondition(SurvivesExplosion.builder())
                )
                .addLootPool(
                    LootPool.builder()
                        .rolls(ConstantRange.of(1))
                        .addEntry(
                            ItemLootEntry.builder(SEED)
                            .acceptFunction(ApplyBonus.binomialWithBonusCount(Enchantments.FORTUNE, 0.5714286F, 2))
                            .acceptCondition(BlockStateProperty.builder(CROP).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(CropsBlock.AGE, 7).withProp(DoubleCropsBlock.SECTION, DoubleBlockHalf.LOWER)))
                        )
                        .acceptCondition(SurvivesExplosion.builder())
                );
    }

    protected LootTable.Builder tripleCrop(Block CROP, Item DROP, Item SEED, int Count) {
        return LootTable.builder()
                .addLootPool(LootPool.builder()
                    .rolls(ConstantRange.of(1))
                    .addEntry(AlternativesLootEntry.builder(
                            ItemLootEntry.builder(DROP)
                                .acceptFunction(SetCount.builder(ConstantRange.of(Count)))
                                .acceptCondition(BlockStateProperty.builder(CROP).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(CropsBlock.AGE, 7).withProp(TripleCropsBlock.SECTION, TripleBlockSection.BOTTOM))),
                            ItemLootEntry.builder(SEED)
                                .acceptCondition(BlockStateProperty.builder(CROP).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(TripleCropsBlock.SECTION, TripleBlockSection.BOTTOM)))
                        )
                    )
                    .acceptCondition(SurvivesExplosion.builder())
                )
                .addLootPool(LootPool.builder()
                    .rolls(ConstantRange.of(1))
                    .addEntry(
                        ItemLootEntry.builder(SEED)
                            .acceptFunction(ApplyBonus.binomialWithBonusCount(Enchantments.FORTUNE, 0.5714286F, 2))
                            .acceptCondition(BlockStateProperty.builder(CROP).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(CropsBlock.AGE, 7).withProp(TripleCropsBlock.SECTION, TripleBlockSection.BOTTOM)))
                    )
                    .acceptCondition(SurvivesExplosion.builder())
                );
    }
    

    protected LootTable.Builder doubleBushOneDrop(Block BUSH, Item DROP) {
        LootPool.Builder builder = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(AlternativesLootEntry.builder(
                        ItemLootEntry.builder(DROP)
                                .acceptCondition(BlockStateProperty.builder(BUSH).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(RankinePlantBlock.AGE, 3).withProp(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)))
                                .acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 3.0F)))
                                .acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE)),
                        ItemLootEntry.builder(DROP)
                                .acceptCondition(BlockStateProperty.builder(BUSH).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(RankinePlantBlock.AGE, 2).withProp(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)))
                                .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)))
                                .acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE))))
                .acceptCondition(SurvivesExplosion.builder());

        return LootTable.builder().addLootPool(builder);
    }

    protected LootTable.Builder bushOneDrop(Block BUSH, Item DROP) {
        LootPool.Builder builder = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(AlternativesLootEntry.builder(
                    ItemLootEntry.builder(DROP)
                        .acceptCondition(BlockStateProperty.builder(BUSH).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(RankinePlantBlock.AGE, 3)))
                        .acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 3.0F)))
                        .acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE)),
                    ItemLootEntry.builder(DROP)
                        .acceptCondition(BlockStateProperty.builder(BUSH).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(RankinePlantBlock.AGE, 2)))
                        .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)))
                        .acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE))))
        .acceptCondition(SurvivesExplosion.builder());

        return LootTable.builder().addLootPool(builder);
    }

    protected LootTable.Builder bushTwoDrop(Block BUSH, Item DROP1, Item DROP2) {
        LootPool.Builder builder = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(AlternativesLootEntry.builder(
                        ItemLootEntry.builder(DROP1)
                                .acceptCondition(BlockStateProperty.builder(BUSH).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(RankinePlantBlock.AGE, 3)))
                                .acceptFunction(SetCount.builder(RandomValueRange.of(2.0F, 3.0F)))
                                .acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE)),
                        ItemLootEntry.builder(DROP1)
                                .acceptCondition(BlockStateProperty.builder(BUSH).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(RankinePlantBlock.AGE, 2)))
                                .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F)))
                                .acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE)),
                        ItemLootEntry.builder(DROP2)
                                .acceptCondition(BlockStateProperty.builder(BUSH).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(RankinePlantBlock.AGE, 2)))
                                .acceptFunction(SetCount.builder(ConstantRange.of(1)))
                                .acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE))))
                .acceptCondition(SurvivesExplosion.builder());

        return LootTable.builder().addLootPool(builder);
    }

    protected LootTable.Builder eightLayerBlock(Block BLK) {
        Item ITEM = BLK.asItem();
        if (BLK.matchesBlock(RankineBlocks.CHARCOAL_BLOCK.get())) {
            ITEM = Items.CHARCOAL;
        }
        return LootTable.builder().addLootPool(LootPool.builder()
                //.acceptCondition(EntityHasProperty.builder(LootContext.EntityTarget.THIS))
                .addEntry(AlternativesLootEntry.builder(
                    ItemLootEntry.builder(ITEM).acceptCondition(BlockStateProperty.builder(BLK).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(RankineEightLayerBlock.LAYERS, 1))),
                    ItemLootEntry.builder(ITEM).acceptFunction(SetCount.builder(ConstantRange.of(2))).acceptCondition(BlockStateProperty.builder(BLK).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(RankineEightLayerBlock.LAYERS, 2))),
                    ItemLootEntry.builder(ITEM).acceptFunction(SetCount.builder(ConstantRange.of(3))).acceptCondition(BlockStateProperty.builder(BLK).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(RankineEightLayerBlock.LAYERS, 3))),
                    ItemLootEntry.builder(ITEM).acceptFunction(SetCount.builder(ConstantRange.of(4))).acceptCondition(BlockStateProperty.builder(BLK).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(RankineEightLayerBlock.LAYERS, 4))),
                    ItemLootEntry.builder(ITEM).acceptFunction(SetCount.builder(ConstantRange.of(5))).acceptCondition(BlockStateProperty.builder(BLK).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(RankineEightLayerBlock.LAYERS, 5))),
                    ItemLootEntry.builder(ITEM).acceptFunction(SetCount.builder(ConstantRange.of(6))).acceptCondition(BlockStateProperty.builder(BLK).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(RankineEightLayerBlock.LAYERS, 6))),
                    ItemLootEntry.builder(ITEM).acceptFunction(SetCount.builder(ConstantRange.of(7))).acceptCondition(BlockStateProperty.builder(BLK).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(RankineEightLayerBlock.LAYERS, 7))),
                    ItemLootEntry.builder(ITEM).acceptFunction(SetCount.builder(ConstantRange.of(8))).acceptCondition(BlockStateProperty.builder(BLK).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(RankineEightLayerBlock.LAYERS, 8)))))
        .acceptCondition(SurvivesExplosion.builder()));


    }

    protected LootTable.Builder withShears(Block BLK) {
        LootPool.Builder builder = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(BLK))
                .acceptCondition(SHEARS)
                .acceptCondition(SurvivesExplosion.builder());
        return LootTable.builder().addLootPool(builder);
    }

    protected LootTable.Builder createBlockLootTable(Block BLK) {
        LootPool.Builder builder = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(BLK))
                .acceptCondition(SurvivesExplosion.builder());
        return LootTable.builder().addLootPool(builder);
    }

    protected LootTable.Builder slabBlockLootTable(Block SLAB) {
        LootPool.Builder builder = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(SLAB)
                        .acceptFunction(SetCount.builder(ConstantRange.of(2))
                                .acceptCondition(BlockStateProperty.builder(SLAB).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(SlabBlock.TYPE, SlabType.DOUBLE)))))
                .acceptCondition(SurvivesExplosion.builder());
        return LootTable.builder().addLootPool(builder);
    }

    protected LootTable.Builder verticalSlabBlockLootTable(Block SLAB) {
        LootPool.Builder builder = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(SLAB)
                        .acceptFunction(SetCount.builder(ConstantRange.of(2))
                                .acceptCondition(BlockStateProperty.builder(SLAB).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(RankineVerticalSlabBlock.TYPE, VerticalSlabStates.DOUBLE)))))
                .acceptCondition(SurvivesExplosion.builder());
        return LootTable.builder().addLootPool(builder);
    }

    protected LootTable.Builder nativeOreBlockLootTable(Block ORE, Item NUGGET) {
        LootPool.Builder builder = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(ORE)
                    .acceptCondition(SILK_TOUCH)
                    .acceptCondition(SurvivesExplosion.builder()))
                .addEntry(ItemLootEntry.builder(NUGGET)
                    .acceptFunction(SetCount.builder(RandomValueRange.of(3.0F, 8.0F)))
                    .acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE)))
                    .acceptCondition(SurvivesExplosion.builder());
        return LootTable.builder().addLootPool(builder);
    }

    protected LootTable.Builder fortunableOreOreBlockLootTable(Block ORE, Item GEM) {
        LootPool.Builder builder = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(ORE)
                        .acceptCondition(SILK_TOUCH))
                .addEntry(ItemLootEntry.builder(GEM)
                        .acceptCondition(NO_SILK_TOUCH)
                        .acceptFunction(ApplyBonus.oreDrops(Enchantments.FORTUNE)))
                .acceptCondition(SurvivesExplosion.builder());
        return LootTable.builder().addLootPool(builder);
    }

    protected LootTable.Builder silkTouchOnly(Block BLK) {
        LootPool.Builder builder = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(BLK)
                        .acceptCondition(SILK_TOUCH)
                .acceptCondition(SurvivesExplosion.builder()));
        return LootTable.builder().addLootPool(builder);
    }

    protected LootTable.Builder silkTouch(Block BLK, Item OTHER) {
        LootPool.Builder builder = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(BLK)
                        .acceptCondition(SILK_TOUCH))
                .addEntry(ItemLootEntry.builder(OTHER)
                        .acceptCondition(NO_SILK_TOUCH))
                .acceptCondition(SurvivesExplosion.builder());
        return LootTable.builder().addLootPool(builder);
    }


}