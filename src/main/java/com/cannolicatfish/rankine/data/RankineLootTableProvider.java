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
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.DataProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.util.StringRepresentable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

public abstract class RankineLootTableProvider extends LootTableProvider {

    protected static final LootItemCondition.Builder SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    protected static final LootItemCondition.Builder NO_SILK_TOUCH = SILK_TOUCH.invert();
    protected static final LootItemCondition.Builder SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    protected static final LootItemCondition.Builder SILK_TOUCH_OR_SHEARS = SHEARS.or(SILK_TOUCH);
    protected static final LootItemCondition.Builder NOT_SILK_TOUCH_OR_SHEARS = SILK_TOUCH_OR_SHEARS.invert();
    protected static final Set<Item> IMMUNE_TO_EXPLOSIONS = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(ItemLike::asItem).collect(ImmutableSet.toImmutableSet());
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
    public void run(HashCache cache) {
        addTables();

        Map<ResourceLocation, LootTable> tables = new HashMap<>();
        for (Map.Entry<Block, LootTable.Builder> entry : lootTables.entrySet()) {
            tables.put(entry.getKey().getLootTable(), entry.getValue().setParamSet(LootContextParamSets.BLOCK).build());
        }
        writeTables(cache, tables);
    }

    // Actually write out the tables in the output folder
    private void writeTables(HashCache cache, Map<ResourceLocation, LootTable> tables) {
        Path outputFolder = this.generator.getOutputFolder();
        tables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                DataProvider.save(GSON, cache, LootTables.serialize(lootTable), path);
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

    protected static <T> T withExplosionDecay(ItemLike item, FunctionUserBuilder<T> function) {
        return (T)(!IMMUNE_TO_EXPLOSIONS.contains(item.asItem()) ? function.apply(ApplyExplosionDecay.explosionDecay()) : function.unwrap());
    }

    protected static <T> T withSurvivesExplosion(ItemLike item, ConditionUserBuilder<T> condition) {
        return (T)(!IMMUNE_TO_EXPLOSIONS.contains(item.asItem()) ? condition.when(ExplosionCondition.survivesExplosion()) : condition.unwrap());
    }

    protected static LootTable.Builder dropping(ItemLike item) {
        return LootTable.lootTable().withPool(withSurvivesExplosion(item, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(item))));
    }

    protected static LootTable.Builder dropping(Block block, LootItemCondition.Builder conditionBuilder, LootPoolEntryContainer.Builder<?> p_218494_2_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(block).when(conditionBuilder).otherwise(p_218494_2_)));
    }

    protected static LootTable.Builder droppingWithSilkTouch(Block block, LootPoolEntryContainer.Builder<?> builder) {
        return dropping(block, SILK_TOUCH, builder);
    }

    protected static LootTable.Builder droppingWithShears(Block block, LootPoolEntryContainer.Builder<?> noShearAlternativeEntry) {
        return dropping(block, SHEARS, noShearAlternativeEntry);
    }

    protected static LootTable.Builder droppingWithSilkTouchOrShears(Block block, LootPoolEntryContainer.Builder<?> alternativeLootEntry) {
        return dropping(block, SILK_TOUCH_OR_SHEARS, alternativeLootEntry);
    }

    protected static LootTable.Builder droppingWithSilkTouch(Block block, ItemLike noSilkTouch) {
        return droppingWithSilkTouch(block, withSurvivesExplosion(block, LootItem.lootTableItem(noSilkTouch)));
    }

    protected static LootTable.Builder droppingRandomly(ItemLike item, NumberProvider range) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(withExplosionDecay(item, LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(range)))));
    }

    protected static LootTable.Builder droppingWithSilkTouchOrRandomly(Block block, ItemLike item, NumberProvider range) {
        return droppingWithSilkTouch(block, withExplosionDecay(block, LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(range))));
    }

    protected static LootTable.Builder onlyWithSilkTouch(ItemLike item) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(SILK_TOUCH).setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(item)));
    }

    protected static LootTable.Builder droppingAndFlowerPot(ItemLike flower) {
        return LootTable.lootTable().withPool(withSurvivesExplosion(Blocks.FLOWER_POT, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Blocks.FLOWER_POT)))).withPool(withSurvivesExplosion(flower, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(flower))));
    }

    protected static LootTable.Builder droppingSlab(Block slab) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(withExplosionDecay(slab, LootItem.lootTableItem(slab).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(slab).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))))));
    }

    protected static <T extends Comparable<T> & StringRepresentable> LootTable.Builder droppingWhen(Block block, Property<T> property, T value) {
        return LootTable.lootTable().withPool(withSurvivesExplosion(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(block).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value))))));
    }

    protected static LootTable.Builder droppingWithName(Block block) {
        return LootTable.lootTable().withPool(withSurvivesExplosion(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(block).apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)))));
    }


    protected static LootTable.Builder droppingWithPatterns(Block banner) {
        return LootTable.lootTable().withPool(withSurvivesExplosion(banner, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(banner).apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)).apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("Patterns", "BlockEntityTag.Patterns")))));
    }

    private static LootTable.Builder droppingAndBees(Block nest) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(SILK_TOUCH).setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(nest).apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("Bees", "BlockEntityTag.Bees")).apply(CopyBlockState.copyState(nest).copy(BeehiveBlock.HONEY_LEVEL))));
    }

    private static LootTable.Builder droppingAndBeesWithAlternative(Block hive) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(hive).when(SILK_TOUCH).apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("Bees", "BlockEntityTag.Bees")).apply(CopyBlockState.copyState(hive).copy(BeehiveBlock.HONEY_LEVEL)).otherwise(LootItem.lootTableItem(hive))));
    }

    protected static LootTable.Builder droppingItemWithFortune(Block block, Item item) {
        return droppingWithSilkTouch(block, withExplosionDecay(block, LootItem.lootTableItem(item).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    /**
     * Creates a builder that drops the given IItemProvider in amounts between 0 and 2, most often 0. Only used in
     * vanilla for huge mushroom blocks.
     */
    protected static LootTable.Builder droppingItemRarely(Block block, ItemLike item) {
        return droppingWithSilkTouch(block, withExplosionDecay(block, LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(UniformGenerator.between(-6.0F, 2.0F))).apply(LimitCount.limitCount(IntRange.lowerBound(0)))));
    }

    protected static LootTable.Builder droppingSeeds(Block block) {
        return droppingWithShears(block, withExplosionDecay(block, LootItem.lootTableItem(Items.WHEAT_SEEDS).when(LootItemRandomChanceCondition.randomChance(0.125F)).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2))));
    }

    /**
     * Creates a builder that drops the given IItemProvider in amounts between 0 and 3, based on the AGE property. Only
     * used in vanilla for pumpkin and melon stems.
     */
    protected static LootTable.Builder droppingByAge(Block stemFruit, Item item) {
        return LootTable.lootTable().withPool(withExplosionDecay(stemFruit, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.06666667F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 0)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.13333334F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 1)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.2F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 2)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.26666668F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 3)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.33333334F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 4)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.4F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 5)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.46666667F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 6)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.53333336F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 7)))))));
    }

    private static LootTable.Builder dropSeedsForStem(Block stem, Item stemSeed) {
        return LootTable.lootTable().withPool(withExplosionDecay(stem, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(stemSeed).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.53333336F))))));
    }

    protected static LootTable.Builder onlyWithShears(ItemLike item) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(SHEARS).add(LootItem.lootTableItem(item)));
    }

    /**
     * Used for all leaves, drops self with silk touch, otherwise drops the second Block param with the passed chances
     * for fortune levels, adding in sticks.
     */
    protected static LootTable.Builder droppingWithChancesAndSticks(Block block, Block sapling, float... chances) {
        return droppingWithSilkTouchOrShears(block, withSurvivesExplosion(block, LootItem.lootTableItem(sapling)).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, chances))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(NOT_SILK_TOUCH_OR_SHEARS).add(withExplosionDecay(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
    }

    /**
     * Used for oak and dark oak, same as droppingWithChancesAndSticks but adding in apples.
     */
    protected static LootTable.Builder droppingWithChancesSticksAndApples(Block block, Block sapling, float... chances) {
        return droppingWithChancesAndSticks(block, sapling, chances).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(NOT_SILK_TOUCH_OR_SHEARS).add(withSurvivesExplosion(block, LootItem.lootTableItem(Items.APPLE)).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
    }

    /**
     * Drops the first item parameter always, and the second item parameter plus more of the first when the loot
     * condition is met, applying fortune to only the second argument.
     */
    protected static LootTable.Builder droppingAndBonusWhen(Block block, Item itemConditional, Item withBonus, LootItemCondition.Builder conditionBuilder) {
        return withExplosionDecay(block, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(itemConditional).when(conditionBuilder).otherwise(LootItem.lootTableItem(withBonus)))).withPool(LootPool.lootPool().when(conditionBuilder).add(LootItem.lootTableItem(withBonus).apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3)))));
    }

    private static LootTable.Builder droppingSheared(Block sheared) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(SHEARS).add(LootItem.lootTableItem(sheared).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2)))));
    }

    private static LootTable.Builder droppingSeedsTall(Block block, Block sheared) {
        LootPoolEntryContainer.Builder<?> builder = LootItem.lootTableItem(sheared).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))).when(SHEARS).otherwise(withSurvivesExplosion(block, LootItem.lootTableItem(Items.WHEAT_SEEDS)).when(LootItemRandomChanceCondition.randomChance(0.125F)));
        return LootTable.lootTable().withPool(LootPool.lootPool().add(builder).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).build()).build()), new BlockPos(0, 1, 0)))).withPool(LootPool.lootPool().add(builder).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).build()).build()), new BlockPos(0, -1, 0))));
    }

    public static LootTable.Builder blockNoDrop() {
        return LootTable.lootTable();
    }



    //BASE LOOTTABLES
    protected LootTable.Builder metalPole(Block BLK) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(AlternativesEntry.alternatives(
                                LootItem.lootTableItem(RankineItems.GARLAND.get())
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BLK).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MetalPoleBlock.STYLE, 1))),
                                LootItem.lootTableItem(BLK)))
                        .when(ExplosionCondition.survivesExplosion()));
    }
    protected LootTable.Builder singleCrop(Block CROP, Item DROP, Item SEED) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                        .add(AlternativesEntry.alternatives(
                            LootItem.lootTableItem(DROP)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7))),
                            LootItem.lootTableItem(SEED)))
                    .when(ExplosionCondition.survivesExplosion()))
                .withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(SEED)
                            .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 2)))
                    .when(ExplosionCondition.survivesExplosion()));
    }

    protected LootTable.Builder doubleCrop(Block CROP, Item DROP, Item SEED, int Count) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(AlternativesEntry.alternatives(
                                LootItem.lootTableItem(DROP)
                                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(Count)))
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7).hasProperty(DoubleCropsBlock.SECTION, DoubleBlockHalf.LOWER))),
                                LootItem.lootTableItem(SEED)))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoubleCropsBlock.SECTION, DoubleBlockHalf.LOWER)))
                        .when(ExplosionCondition.survivesExplosion()))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(
                                LootItem.lootTableItem(SEED)
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoubleCropsBlock.SECTION, DoubleBlockHalf.LOWER)))
                                        .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 2)))
                        .when(ExplosionCondition.survivesExplosion()));
    }

    protected LootTable.Builder tripleCrop(Block CROP, Item DROP, Item SEED, int Count) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(AlternativesEntry.alternatives(
                        LootItem.lootTableItem(DROP)
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(Count)))
                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7).hasProperty(TripleCropsBlock.SECTION, TripleBlockSection.BOTTOM))),
                        LootItem.lootTableItem(SEED)))
                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TripleCropsBlock.SECTION, TripleBlockSection.BOTTOM)))
                    .when(ExplosionCondition.survivesExplosion()))
                .withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(
                        LootItem.lootTableItem(SEED)
                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TripleCropsBlock.SECTION, TripleBlockSection.BOTTOM)))
                            .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 2)))
                    .when(ExplosionCondition.survivesExplosion()));
    }

    protected LootTable.Builder doubleBushOneDrop(Block BUSH, Item DROP) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(AlternativesEntry.alternatives(
                        LootItem.lootTableItem(DROP)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BUSH).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankinePlantBlock.AGE, 3).hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)),
                        LootItem.lootTableItem(DROP)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BUSH).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankinePlantBlock.AGE, 2).hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))))
                .when(ExplosionCondition.survivesExplosion());

        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder bushOneDrop(Block BUSH, Item DROP) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(AlternativesEntry.alternatives(
                    LootItem.lootTableItem(DROP)
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BUSH).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankinePlantBlock.AGE, 3)))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)),
                    LootItem.lootTableItem(DROP)
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BUSH).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankinePlantBlock.AGE, 2)))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))))
        .when(ExplosionCondition.survivesExplosion());

        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder bushTwoDrop(Block BUSH, Item DROP1, Item DROP2) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(AlternativesEntry.alternatives(
                        LootItem.lootTableItem(DROP1)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BUSH).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankinePlantBlock.AGE, 3)))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)),
                        LootItem.lootTableItem(DROP1)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BUSH).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankinePlantBlock.AGE, 2)))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)),
                        LootItem.lootTableItem(DROP2)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BUSH).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankinePlantBlock.AGE, 2)))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))))
                .when(ExplosionCondition.survivesExplosion());

        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder eightLayerBlock(Block BLK) {
        Item ITEM = BLK.asItem();
        if (BLK.equals(RankineBlocks.CHARCOAL_BLOCK.get())) {
            ITEM = Items.CHARCOAL;
        }
        return LootTable.lootTable().withPool(LootPool.lootPool()
                //.acceptCondition(EntityHasProperty.builder(LootContext.EntityTarget.THIS))
                .add(AlternativesEntry.alternatives(
                    LootItem.lootTableItem(ITEM).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BLK).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankineEightLayerBlock.LAYERS, 1))),
                    LootItem.lootTableItem(ITEM).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BLK).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankineEightLayerBlock.LAYERS, 2))),
                    LootItem.lootTableItem(ITEM).apply(SetItemCountFunction.setCount(ConstantValue.exactly(3))).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BLK).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankineEightLayerBlock.LAYERS, 3))),
                    LootItem.lootTableItem(ITEM).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BLK).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankineEightLayerBlock.LAYERS, 4))),
                    LootItem.lootTableItem(ITEM).apply(SetItemCountFunction.setCount(ConstantValue.exactly(5))).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BLK).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankineEightLayerBlock.LAYERS, 5))),
                    LootItem.lootTableItem(ITEM).apply(SetItemCountFunction.setCount(ConstantValue.exactly(6))).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BLK).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankineEightLayerBlock.LAYERS, 6))),
                    LootItem.lootTableItem(ITEM).apply(SetItemCountFunction.setCount(ConstantValue.exactly(7))).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BLK).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankineEightLayerBlock.LAYERS, 7))),
                    LootItem.lootTableItem(ITEM).apply(SetItemCountFunction.setCount(ConstantValue.exactly(8))).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BLK).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankineEightLayerBlock.LAYERS, 8)))))
        .when(ExplosionCondition.survivesExplosion()));


    }

    protected LootTable.Builder withShears(Block BLK) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(BLK))
                .when(SHEARS)
                .when(ExplosionCondition.survivesExplosion());
        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder createBlockLootTable(Block BLK) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(BLK))
                .when(ExplosionCondition.survivesExplosion());
        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder slabBlockLootTable(Block SLAB) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(SLAB)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(SLAB).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))))
                .when(ExplosionCondition.survivesExplosion());
        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder verticalSlabBlockLootTable(Block SLAB) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(SLAB)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(SLAB).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankineVerticalSlabBlock.TYPE, VerticalSlabStates.DOUBLE)))))
                .when(ExplosionCondition.survivesExplosion());
        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder nativeOreBlockLootTable(Block ORE, Item NUGGET) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(ORE)
                    .when(SILK_TOUCH)
                    .when(ExplosionCondition.survivesExplosion()))
                .add(LootItem.lootTableItem(NUGGET)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 8.0F)))
                    .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))
                    .when(ExplosionCondition.survivesExplosion());
        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder fortunableOreOreBlockLootTable(Block ORE, Item GEM) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(ORE)
                        .when(SILK_TOUCH))
                .add(LootItem.lootTableItem(GEM)
                        .when(NO_SILK_TOUCH)
                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))
                .when(ExplosionCondition.survivesExplosion());
        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder silkTouchOnly(Block BLK) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(BLK)
                        .when(SILK_TOUCH)
                .when(ExplosionCondition.survivesExplosion()));
        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder silkTouch(Block BLK, Item OTHER) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(BLK)
                        .when(SILK_TOUCH))
                .add(LootItem.lootTableItem(OTHER)
                        .when(NO_SILK_TOUCH))
                .when(ExplosionCondition.survivesExplosion());
        return LootTable.lootTable().withPool(builder);
    }


}