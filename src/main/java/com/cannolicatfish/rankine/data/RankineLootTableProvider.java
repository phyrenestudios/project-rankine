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

    protected static final ILootCondition.IBuilder SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
    protected static final ILootCondition.IBuilder NO_SILK_TOUCH = SILK_TOUCH.invert();
    protected static final ILootCondition.IBuilder SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    protected static final ILootCondition.IBuilder SILK_TOUCH_OR_SHEARS = SHEARS.or(SILK_TOUCH);
    protected static final ILootCondition.IBuilder NOT_SILK_TOUCH_OR_SHEARS = SILK_TOUCH_OR_SHEARS.invert();
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
    public void run(DirectoryCache cache) {
        addTables();

        Map<ResourceLocation, LootTable> tables = new HashMap<>();
        for (Map.Entry<Block, LootTable.Builder> entry : lootTables.entrySet()) {
            tables.put(entry.getKey().getLootTable(), entry.getValue().setParamSet(LootParameterSets.BLOCK).build());
        }
        writeTables(cache, tables);
    }

    // Actually write out the tables in the output folder
    private void writeTables(DirectoryCache cache, Map<ResourceLocation, LootTable> tables) {
        Path outputFolder = this.generator.getOutputFolder();
        tables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                IDataProvider.save(GSON, cache, LootTableManager.serialize(lootTable), path);
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
        return (T)(!IMMUNE_TO_EXPLOSIONS.contains(item.asItem()) ? function.apply(ExplosionDecay.explosionDecay()) : function.unwrap());
    }

    protected static <T> T withSurvivesExplosion(IItemProvider item, ILootConditionConsumer<T> condition) {
        return (T)(!IMMUNE_TO_EXPLOSIONS.contains(item.asItem()) ? condition.when(SurvivesExplosion.survivesExplosion()) : condition.unwrap());
    }

    protected static LootTable.Builder dropping(IItemProvider item) {
        return LootTable.lootTable().withPool(withSurvivesExplosion(item, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(item))));
    }

    protected static LootTable.Builder dropping(Block block, ILootCondition.IBuilder conditionBuilder, LootEntry.Builder<?> p_218494_2_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(block).when(conditionBuilder).otherwise(p_218494_2_)));
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
        return droppingWithSilkTouch(block, withSurvivesExplosion(block, ItemLootEntry.lootTableItem(noSilkTouch)));
    }

    protected static LootTable.Builder droppingRandomly(IItemProvider item, IRandomRange range) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(withExplosionDecay(item, ItemLootEntry.lootTableItem(item).apply(SetCount.setCount(range)))));
    }

    protected static LootTable.Builder droppingWithSilkTouchOrRandomly(Block block, IItemProvider item, IRandomRange range) {
        return droppingWithSilkTouch(block, withExplosionDecay(block, ItemLootEntry.lootTableItem(item).apply(SetCount.setCount(range))));
    }

    protected static LootTable.Builder onlyWithSilkTouch(IItemProvider item) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(SILK_TOUCH).setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(item)));
    }

    protected static LootTable.Builder droppingAndFlowerPot(IItemProvider flower) {
        return LootTable.lootTable().withPool(withSurvivesExplosion(Blocks.FLOWER_POT, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(Blocks.FLOWER_POT)))).withPool(withSurvivesExplosion(flower, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(flower))));
    }

    protected static LootTable.Builder droppingSlab(Block slab) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(withExplosionDecay(slab, ItemLootEntry.lootTableItem(slab).apply(SetCount.setCount(ConstantRange.exactly(2)).when(BlockStateProperty.hasBlockStateProperties(slab).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))))));
    }

    protected static <T extends Comparable<T> & IStringSerializable> LootTable.Builder droppingWhen(Block block, Property<T> property, T value) {
        return LootTable.lootTable().withPool(withSurvivesExplosion(block, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(block).when(BlockStateProperty.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value))))));
    }

    protected static LootTable.Builder droppingWithName(Block block) {
        return LootTable.lootTable().withPool(withSurvivesExplosion(block, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(block).apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY)))));
    }

    protected static LootTable.Builder droppingWithContents(Block shulker) {
        return LootTable.lootTable().withPool(withSurvivesExplosion(shulker, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(shulker).apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY)).apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY).copy("Lock", "BlockEntityTag.Lock").copy("LootTable", "BlockEntityTag.LootTable").copy("LootTableSeed", "BlockEntityTag.LootTableSeed")).apply(SetContents.setContents().withEntry(DynamicLootEntry.dynamicEntry(ShulkerBoxBlock.CONTENTS))))));
    }

    protected static LootTable.Builder droppingWithPatterns(Block banner) {
        return LootTable.lootTable().withPool(withSurvivesExplosion(banner, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(banner).apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY)).apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY).copy("Patterns", "BlockEntityTag.Patterns")))));
    }

    private static LootTable.Builder droppingAndBees(Block nest) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(SILK_TOUCH).setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(nest).apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY).copy("Bees", "BlockEntityTag.Bees")).apply(CopyBlockState.copyState(nest).copy(BeehiveBlock.HONEY_LEVEL))));
    }

    private static LootTable.Builder droppingAndBeesWithAlternative(Block hive) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(hive).when(SILK_TOUCH).apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY).copy("Bees", "BlockEntityTag.Bees")).apply(CopyBlockState.copyState(hive).copy(BeehiveBlock.HONEY_LEVEL)).otherwise(ItemLootEntry.lootTableItem(hive))));
    }

    protected static LootTable.Builder droppingItemWithFortune(Block block, Item item) {
        return droppingWithSilkTouch(block, withExplosionDecay(block, ItemLootEntry.lootTableItem(item).apply(ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    /**
     * Creates a builder that drops the given IItemProvider in amounts between 0 and 2, most often 0. Only used in
     * vanilla for huge mushroom blocks.
     */
    protected static LootTable.Builder droppingItemRarely(Block block, IItemProvider item) {
        return droppingWithSilkTouch(block, withExplosionDecay(block, ItemLootEntry.lootTableItem(item).apply(SetCount.setCount(RandomValueRange.between(-6.0F, 2.0F))).apply(LimitCount.limitCount(IntClamper.lowerBound(0)))));
    }

    protected static LootTable.Builder droppingSeeds(Block block) {
        return droppingWithShears(block, withExplosionDecay(block, ItemLootEntry.lootTableItem(Items.WHEAT_SEEDS).when(RandomChance.randomChance(0.125F)).apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2))));
    }

    /**
     * Creates a builder that drops the given IItemProvider in amounts between 0 and 3, based on the AGE property. Only
     * used in vanilla for pumpkin and melon stems.
     */
    protected static LootTable.Builder droppingByAge(Block stemFruit, Item item) {
        return LootTable.lootTable().withPool(withExplosionDecay(stemFruit, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(item).apply(SetCount.setCount(BinomialRange.binomial(3, 0.06666667F)).when(BlockStateProperty.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 0)))).apply(SetCount.setCount(BinomialRange.binomial(3, 0.13333334F)).when(BlockStateProperty.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 1)))).apply(SetCount.setCount(BinomialRange.binomial(3, 0.2F)).when(BlockStateProperty.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 2)))).apply(SetCount.setCount(BinomialRange.binomial(3, 0.26666668F)).when(BlockStateProperty.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 3)))).apply(SetCount.setCount(BinomialRange.binomial(3, 0.33333334F)).when(BlockStateProperty.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 4)))).apply(SetCount.setCount(BinomialRange.binomial(3, 0.4F)).when(BlockStateProperty.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 5)))).apply(SetCount.setCount(BinomialRange.binomial(3, 0.46666667F)).when(BlockStateProperty.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 6)))).apply(SetCount.setCount(BinomialRange.binomial(3, 0.53333336F)).when(BlockStateProperty.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 7)))))));
    }

    private static LootTable.Builder dropSeedsForStem(Block stem, Item stemSeed) {
        return LootTable.lootTable().withPool(withExplosionDecay(stem, LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(stemSeed).apply(SetCount.setCount(BinomialRange.binomial(3, 0.53333336F))))));
    }

    protected static LootTable.Builder onlyWithShears(IItemProvider item) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).when(SHEARS).add(ItemLootEntry.lootTableItem(item)));
    }

    /**
     * Used for all leaves, drops self with silk touch, otherwise drops the second Block param with the passed chances
     * for fortune levels, adding in sticks.
     */
    protected static LootTable.Builder droppingWithChancesAndSticks(Block block, Block sapling, float... chances) {
        return droppingWithSilkTouchOrShears(block, withSurvivesExplosion(block, ItemLootEntry.lootTableItem(sapling)).when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, chances))).withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).when(NOT_SILK_TOUCH_OR_SHEARS).add(withExplosionDecay(block, ItemLootEntry.lootTableItem(Items.STICK).apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F)))).when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
    }

    /**
     * Used for oak and dark oak, same as droppingWithChancesAndSticks but adding in apples.
     */
    protected static LootTable.Builder droppingWithChancesSticksAndApples(Block block, Block sapling, float... chances) {
        return droppingWithChancesAndSticks(block, sapling, chances).withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).when(NOT_SILK_TOUCH_OR_SHEARS).add(withSurvivesExplosion(block, ItemLootEntry.lootTableItem(Items.APPLE)).when(TableBonus.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
    }

    /**
     * Drops the first item parameter always, and the second item parameter plus more of the first when the loot
     * condition is met, applying fortune to only the second argument.
     */
    protected static LootTable.Builder droppingAndBonusWhen(Block block, Item itemConditional, Item withBonus, ILootCondition.IBuilder conditionBuilder) {
        return withExplosionDecay(block, LootTable.lootTable().withPool(LootPool.lootPool().add(ItemLootEntry.lootTableItem(itemConditional).when(conditionBuilder).otherwise(ItemLootEntry.lootTableItem(withBonus)))).withPool(LootPool.lootPool().when(conditionBuilder).add(ItemLootEntry.lootTableItem(withBonus).apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3)))));
    }

    private static LootTable.Builder droppingSheared(Block sheared) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(SHEARS).add(ItemLootEntry.lootTableItem(sheared).apply(SetCount.setCount(ConstantRange.exactly(2)))));
    }

    private static LootTable.Builder droppingSeedsTall(Block block, Block sheared) {
        LootEntry.Builder<?> builder = ItemLootEntry.lootTableItem(sheared).apply(SetCount.setCount(ConstantRange.exactly(2))).when(SHEARS).otherwise(withSurvivesExplosion(block, ItemLootEntry.lootTableItem(Items.WHEAT_SEEDS)).when(RandomChance.randomChance(0.125F)));
        return LootTable.lootTable().withPool(LootPool.lootPool().add(builder).when(BlockStateProperty.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).build()).build()), new BlockPos(0, 1, 0)))).withPool(LootPool.lootPool().add(builder).when(BlockStateProperty.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).build()).build()), new BlockPos(0, -1, 0))));
    }

    public static LootTable.Builder blockNoDrop() {
        return LootTable.lootTable();
    }



    //BASE LOOTTABLES
    protected LootTable.Builder metalPole(Block BLK) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(AlternativesLootEntry.alternatives(
                                ItemLootEntry.lootTableItem(RankineItems.GARLAND.get())
                                        .when(BlockStateProperty.hasBlockStateProperties(BLK).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MetalPoleBlock.STYLE, 1))),
                                ItemLootEntry.lootTableItem(BLK)))
                        .when(SurvivesExplosion.survivesExplosion()));
    }
    protected LootTable.Builder singleCrop(Block CROP, Item DROP, Item SEED) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                    .setRolls(ConstantRange.exactly(1))
                        .add(AlternativesLootEntry.alternatives(
                            ItemLootEntry.lootTableItem(DROP)
                                .when(BlockStateProperty.hasBlockStateProperties(CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropsBlock.AGE, 7))),
                            ItemLootEntry.lootTableItem(SEED)))
                    .when(SurvivesExplosion.survivesExplosion()))
                .withPool(LootPool.lootPool()
                    .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(SEED)
                            .apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 2)))
                    .when(SurvivesExplosion.survivesExplosion()));
    }

    protected LootTable.Builder doubleCrop(Block CROP, Item DROP, Item SEED, int Count) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(AlternativesLootEntry.alternatives(
                                ItemLootEntry.lootTableItem(DROP)
                                        .apply(SetCount.setCount(ConstantRange.exactly(Count)))
                                        .when(BlockStateProperty.hasBlockStateProperties(CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropsBlock.AGE, 7).hasProperty(DoubleCropsBlock.SECTION, DoubleBlockHalf.LOWER))),
                                ItemLootEntry.lootTableItem(SEED)))
                        .when(BlockStateProperty.hasBlockStateProperties(CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoubleCropsBlock.SECTION, DoubleBlockHalf.LOWER)))
                        .when(SurvivesExplosion.survivesExplosion()))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(
                                ItemLootEntry.lootTableItem(SEED)
                                        .when(BlockStateProperty.hasBlockStateProperties(CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoubleCropsBlock.SECTION, DoubleBlockHalf.LOWER)))
                                        .apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 2)))
                        .when(SurvivesExplosion.survivesExplosion()));
    }

    protected LootTable.Builder tripleCrop(Block CROP, Item DROP, Item SEED, int Count) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                    .setRolls(ConstantRange.exactly(1))
                    .add(AlternativesLootEntry.alternatives(
                        ItemLootEntry.lootTableItem(DROP)
                            .apply(SetCount.setCount(ConstantRange.exactly(Count)))
                            .when(BlockStateProperty.hasBlockStateProperties(CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropsBlock.AGE, 7).hasProperty(TripleCropsBlock.SECTION, TripleBlockSection.BOTTOM))),
                        ItemLootEntry.lootTableItem(SEED)))
                            .when(BlockStateProperty.hasBlockStateProperties(CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TripleCropsBlock.SECTION, TripleBlockSection.BOTTOM)))
                    .when(SurvivesExplosion.survivesExplosion()))
                .withPool(LootPool.lootPool()
                    .setRolls(ConstantRange.exactly(1))
                    .add(
                        ItemLootEntry.lootTableItem(SEED)
                            .when(BlockStateProperty.hasBlockStateProperties(CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TripleCropsBlock.SECTION, TripleBlockSection.BOTTOM)))
                            .apply(ApplyBonus.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 2)))
                    .when(SurvivesExplosion.survivesExplosion()));
    }

    protected LootTable.Builder doubleBushOneDrop(Block BUSH, Item DROP) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantRange.exactly(1))
                .add(AlternativesLootEntry.alternatives(
                        ItemLootEntry.lootTableItem(DROP)
                                .when(BlockStateProperty.hasBlockStateProperties(BUSH).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankinePlantBlock.AGE, 3).hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)))
                                .apply(SetCount.setCount(RandomValueRange.between(2.0F, 3.0F)))
                                .apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)),
                        ItemLootEntry.lootTableItem(DROP)
                                .when(BlockStateProperty.hasBlockStateProperties(BUSH).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankinePlantBlock.AGE, 2).hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)))
                                .apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F)))
                                .apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))))
                .when(SurvivesExplosion.survivesExplosion());

        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder bushOneDrop(Block BUSH, Item DROP) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantRange.exactly(1))
                .add(AlternativesLootEntry.alternatives(
                    ItemLootEntry.lootTableItem(DROP)
                        .when(BlockStateProperty.hasBlockStateProperties(BUSH).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankinePlantBlock.AGE, 3)))
                        .apply(SetCount.setCount(RandomValueRange.between(2.0F, 3.0F)))
                        .apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)),
                    ItemLootEntry.lootTableItem(DROP)
                        .when(BlockStateProperty.hasBlockStateProperties(BUSH).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankinePlantBlock.AGE, 2)))
                        .apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F)))
                        .apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))))
        .when(SurvivesExplosion.survivesExplosion());

        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder bushTwoDrop(Block BUSH, Item DROP1, Item DROP2) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantRange.exactly(1))
                .add(AlternativesLootEntry.alternatives(
                        ItemLootEntry.lootTableItem(DROP1)
                                .when(BlockStateProperty.hasBlockStateProperties(BUSH).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankinePlantBlock.AGE, 3)))
                                .apply(SetCount.setCount(RandomValueRange.between(2.0F, 3.0F)))
                                .apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)),
                        ItemLootEntry.lootTableItem(DROP1)
                                .when(BlockStateProperty.hasBlockStateProperties(BUSH).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankinePlantBlock.AGE, 2)))
                                .apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F)))
                                .apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)),
                        ItemLootEntry.lootTableItem(DROP2)
                                .when(BlockStateProperty.hasBlockStateProperties(BUSH).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankinePlantBlock.AGE, 2)))
                                .apply(SetCount.setCount(ConstantRange.exactly(1)))
                                .apply(ApplyBonus.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))))
                .when(SurvivesExplosion.survivesExplosion());

        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder eightLayerBlock(Block BLK) {
        Item ITEM = BLK.asItem();
        if (BLK.is(RankineBlocks.CHARCOAL_BLOCK.get())) {
            ITEM = Items.CHARCOAL;
        }
        return LootTable.lootTable().withPool(LootPool.lootPool()
                //.acceptCondition(EntityHasProperty.builder(LootContext.EntityTarget.THIS))
                .add(AlternativesLootEntry.alternatives(
                    ItemLootEntry.lootTableItem(ITEM).when(BlockStateProperty.hasBlockStateProperties(BLK).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankineEightLayerBlock.LAYERS, 1))),
                    ItemLootEntry.lootTableItem(ITEM).apply(SetCount.setCount(ConstantRange.exactly(2))).when(BlockStateProperty.hasBlockStateProperties(BLK).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankineEightLayerBlock.LAYERS, 2))),
                    ItemLootEntry.lootTableItem(ITEM).apply(SetCount.setCount(ConstantRange.exactly(3))).when(BlockStateProperty.hasBlockStateProperties(BLK).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankineEightLayerBlock.LAYERS, 3))),
                    ItemLootEntry.lootTableItem(ITEM).apply(SetCount.setCount(ConstantRange.exactly(4))).when(BlockStateProperty.hasBlockStateProperties(BLK).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankineEightLayerBlock.LAYERS, 4))),
                    ItemLootEntry.lootTableItem(ITEM).apply(SetCount.setCount(ConstantRange.exactly(5))).when(BlockStateProperty.hasBlockStateProperties(BLK).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankineEightLayerBlock.LAYERS, 5))),
                    ItemLootEntry.lootTableItem(ITEM).apply(SetCount.setCount(ConstantRange.exactly(6))).when(BlockStateProperty.hasBlockStateProperties(BLK).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankineEightLayerBlock.LAYERS, 6))),
                    ItemLootEntry.lootTableItem(ITEM).apply(SetCount.setCount(ConstantRange.exactly(7))).when(BlockStateProperty.hasBlockStateProperties(BLK).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankineEightLayerBlock.LAYERS, 7))),
                    ItemLootEntry.lootTableItem(ITEM).apply(SetCount.setCount(ConstantRange.exactly(8))).when(BlockStateProperty.hasBlockStateProperties(BLK).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankineEightLayerBlock.LAYERS, 8)))))
        .when(SurvivesExplosion.survivesExplosion()));


    }

    protected LootTable.Builder withShears(Block BLK) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantRange.exactly(1))
                .add(ItemLootEntry.lootTableItem(BLK))
                .when(SHEARS)
                .when(SurvivesExplosion.survivesExplosion());
        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder createBlockLootTable(Block BLK) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantRange.exactly(1))
                .add(ItemLootEntry.lootTableItem(BLK))
                .when(SurvivesExplosion.survivesExplosion());
        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder slabBlockLootTable(Block SLAB) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantRange.exactly(1))
                .add(ItemLootEntry.lootTableItem(SLAB)
                        .apply(SetCount.setCount(ConstantRange.exactly(2))
                                .when(BlockStateProperty.hasBlockStateProperties(SLAB).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))))
                .when(SurvivesExplosion.survivesExplosion());
        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder verticalSlabBlockLootTable(Block SLAB) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantRange.exactly(1))
                .add(ItemLootEntry.lootTableItem(SLAB)
                        .apply(SetCount.setCount(ConstantRange.exactly(2))
                                .when(BlockStateProperty.hasBlockStateProperties(SLAB).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankineVerticalSlabBlock.TYPE, VerticalSlabStates.DOUBLE)))))
                .when(SurvivesExplosion.survivesExplosion());
        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder nativeOreBlockLootTable(Block ORE, Item NUGGET) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantRange.exactly(1))
                .add(ItemLootEntry.lootTableItem(ORE)
                    .when(SILK_TOUCH)
                    .when(SurvivesExplosion.survivesExplosion()))
                .add(ItemLootEntry.lootTableItem(NUGGET)
                    .apply(SetCount.setCount(RandomValueRange.between(3.0F, 8.0F)))
                    .apply(ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))
                    .when(SurvivesExplosion.survivesExplosion());
        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder fortunableOreOreBlockLootTable(Block ORE, Item GEM) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantRange.exactly(1))
                .add(ItemLootEntry.lootTableItem(ORE)
                        .when(SILK_TOUCH))
                .add(ItemLootEntry.lootTableItem(GEM)
                        .when(NO_SILK_TOUCH)
                        .apply(ApplyBonus.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))
                .when(SurvivesExplosion.survivesExplosion());
        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder silkTouchOnly(Block BLK) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantRange.exactly(1))
                .add(ItemLootEntry.lootTableItem(BLK)
                        .when(SILK_TOUCH)
                .when(SurvivesExplosion.survivesExplosion()));
        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder silkTouch(Block BLK, Item OTHER) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantRange.exactly(1))
                .add(ItemLootEntry.lootTableItem(BLK)
                        .when(SILK_TOUCH))
                .add(ItemLootEntry.lootTableItem(OTHER)
                        .when(NO_SILK_TOUCH))
                .when(SurvivesExplosion.survivesExplosion());
        return LootTable.lootTable().withPool(builder);
    }


}