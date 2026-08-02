package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.HollowLogBlock;
import com.cannolicatfish.rankine.blocks.LeafLitterBlock;
import com.cannolicatfish.rankine.blocks.LightningGlassBlock;
import com.cannolicatfish.rankine.blocks.MetalPoleBlock;
import com.cannolicatfish.rankine.blocks.RankineEightLayerBlock;
import com.cannolicatfish.rankine.blocks.RankineLogBlock;
import com.cannolicatfish.rankine.blocks.RankineVerticalSlabBlock;
import com.cannolicatfish.rankine.blocks.block_groups.*;
import com.cannolicatfish.rankine.blocks.buildingmodes.RankineBookshelvesBlock;
import com.cannolicatfish.rankine.blocks.plants.DoubleCropsBlock;
import com.cannolicatfish.rankine.blocks.plants.RankinePlantBlock;
import com.cannolicatfish.rankine.blocks.plants.TripleCropsBlock;
import com.cannolicatfish.rankine.blocks.states.TripleBlockSection;
import com.cannolicatfish.rankine.blocks.states.VerticalSlabStates;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import com.google.common.collect.ImmutableSet;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Every Rankine block loot table. In 1.21.1 block loot lives in a {@link net.minecraft.data.loot.BlockLootSubProvider}
 * registered from {@link RankineLootTableProvider}; loot tables are keyed by {@code ResourceKey<LootTable>}.
 */
public class RankineBlockLootTables extends net.minecraft.data.loot.BlockLootSubProvider {

    protected static final Set<Item> IMMUNE_TO_EXPLOSIONS = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(ItemLike::asItem).collect(ImmutableSet.toImmutableSet());
    protected static final float[] DEFAULT_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
    protected static final float[] DOUBLE_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
    protected static final float[] RARE_SAPLING_DROP_RATES = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};

    /** Filled by {@link #addTables()}, drained into the vanilla map by {@link #generate()}. */
    protected final Map<Block, LootTable.Builder> lootTables = new LinkedHashMap<>();

    private final Holder<Enchantment> fortuneHolder;

    public RankineBlockLootTables(HolderLookup.Provider registries) {
        super(IMMUNE_TO_EXPLOSIONS, FeatureFlags.REGISTRY.allFlags(), registries);
        this.fortuneHolder = registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE);
    }

    /** 1.21: enchantment-based loot conditions take a {@code Holder<Enchantment>}. */
    protected Holder<Enchantment> fortune() {
        return this.fortuneHolder;
    }

    protected LootItemCondition.Builder silkTouchOrShears() {
        return HAS_SHEARS.or(this.hasSilkTouch());
    }

    protected LootItemCondition.Builder notSilkTouchOrShears() {
        return this.silkTouchOrShears().invert();
    }

    /**
     * The 1.18.2 provider only ever wrote the tables it explicitly built, so bypass the vanilla
     * "every known block must have a table" validation and emit exactly this map.
     */
    @Override
    public void generate(java.util.function.BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
        addTables();
        lootTables.forEach((block, builder) -> consumer.accept(block.getLootTable(), builder));
    }

    @Override
    protected void generate() {
    }

    protected void addTables() {
        for (RankineStone Stone : RankineLists.RANKINE_STONES) {
            for (Block blk : Stone.getStoneBlocks()) {
                if (blk instanceof SlabBlock) {
                    lootTables.put(blk, slabBlockLootTable(blk));
                } else if (blk instanceof InfestedBlock) {
                    lootTables.put(blk, droppingWithSilkTouch(blk, Stone.getStone()));
                } else {
                    lootTables.put(blk, createBlockLootTable(blk));
                }
            }
        }

        for (RankineWood Wood : RankineLists.RANKINE_WOODS) {
            for (Block blk : Wood.getWoodBlocks()) {
                if (!Wood.hasLogs() && blk instanceof RankineLogBlock) continue;
                if (blk instanceof SlabBlock) {
                    lootTables.put(blk, slabBlockLootTable(blk));
                } else if (blk instanceof RankineBookshelvesBlock) {
                    lootTables.put(blk, droppingWithSilkTouchOrRandomly(blk, Items.BOOK, ConstantValue.exactly(3)));
                } else if (blk instanceof DoorBlock) {
                    lootTables.put(blk, droppingWhen(blk, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
                } else if (blk instanceof LeavesBlock) {
                    if (Wood == RankineBlocks.BALSAM_FIR) {
                        lootTables.put(blk, droppingWithChancesAndSticks(blk, Wood.getSapling(), DOUBLE_SAPLING_DROP_RATES));
                    } else if (Wood == RankineBlocks.COCONUT_PALM) {
                        lootTables.put(blk, droppingWithChancesSticksAndExtra(blk, Wood.getSapling(), RankineItems.COCONUT.get(), DOUBLE_SAPLING_DROP_RATES));
                    } else if (Wood == RankineBlocks.BLACK_WALNUT) {
                        lootTables.put(blk, droppingWithChancesSticksAndExtra(blk, Wood.getSapling(),  RankineItems.BLACK_WALNUT.get(), DEFAULT_SAPLING_DROP_RATES));
                    } else {
                        lootTables.put(blk, droppingWithChancesAndSticks(blk, Wood.getSapling(), DEFAULT_SAPLING_DROP_RATES));
                    }
                } else if (blk instanceof LeafLitterBlock) {
                    lootTables.put(blk, withShears(blk));
                } else if (blk instanceof FlowerPotBlock) {
                    lootTables.put(blk, droppingAndFlowerPot(((FlowerPotBlock)blk).getPotted()));
                } else if (blk instanceof HollowLogBlock) {
                    lootTables.put(blk, droppingWithSilkTouchOrRandomly(blk, Items.STICK, UniformGenerator.between(2.0F, 6.0F)));
                } else if (blk != null) {
                    lootTables.put(blk, createBlockLootTable(blk));
                }
            }
        }

        for (RankineSandstone Sandstone : RankineLists.RANKINE_SANDSTONES) {
            for (Block blk : Sandstone.getSandstoneBlocks()) {
                if (blk instanceof SlabBlock) {
                    lootTables.put(blk, slabBlockLootTable(blk));
                } else {
                    lootTables.put(blk, createBlockLootTable(blk));
                }
            }
        }
        for (RankineCement Cement : RankineLists.RANKINE_CEMENTS) {
            for (Block blk : Cement.getCementBlocks()) {
                lootTables.put(blk, createBlockLootTable(blk));
            }
        }
        for (RankineDripstone Dripstone : RankineLists.RANKINE_DRIPSTONES) {
            for (Block blk : Dripstone.getBlocks()) {
                lootTables.put(blk, createBlockLootTable(blk));
            }
        }
        for (RankineBricks Bricks : RankineLists.RANKINE_BRICKS) {
            for (Block blk : Bricks.getBricksBlocks()) {
                if (blk instanceof SlabBlock) {
                    lootTables.put(blk, slabBlockLootTable(blk));
                } else {
                    lootTables.put(blk, createBlockLootTable(blk));
                }
            }
        }

        for (Block blk : Stream.of(
                RankineLists.VANILLA_BRICKS,
                RankineLists.VANILLA_BRICKS_PRESSURE_PLATES,
                RankineLists.VANILLA_BRICKS_WALLS,
                RankineLists.VANILLA_BRICKS_STAIRS,
                RankineLists.SHEETMETALS,
                RankineLists.GEODES,
                RankineLists.LEDS,
                RankineLists.GLAZED_PORCELAIN_BLOCKS,
                RankineLists.COARSE_SOIL_BLOCKS,
                RankineLists.MUD_BLOCKS,
                RankineLists.MINERAL_WOOL,
                RankineLists.FIBER_BLOCK,
                RankineLists.FIBER_MAT,
                RankineLists.METAL_TRAPDOORS,
                RankineLists.ALLOY_LADDERS,
                RankineLists.ALLOY_BLOCKS,
                RankineLists.ALLOY_PEDESTALS,
                RankineLists.MINERAL_BLOCKS,
                RankineLists.STANDARD_BLOCKS,
                RankineLists.ROTATION_BLOCKS,
                RankineLists.ELEMENT_BLOCKS,
                RankineLists.ELECTROMAGNETS,
                RankineLists.ALLOY_BARS,
                RankineLists.ALLOY_SHEETMETALS,
                RankineLists.CRUSHING_HEADS,
                RankineLists.MINING_HEADS,
                RankineLists.WALL_MUSHROOMS,
                RankineLists.BALES
                ).flatMap(Collection::stream).collect(Collectors.toList())) {
            lootTables.put(blk, createBlockLootTable(blk));
        }

        for (Block blk : Arrays.asList(
                RankineBlocks.FLOOD_GATE.get(),
                RankineBlocks.SLATE_STEPPING_STONES.get(),
                RankineBlocks.ORNAMENT.get(),
                RankineBlocks.SOD_BLOCK.get(),
                RankineBlocks.ALLUVIUM.get(),
                RankineBlocks.COB.get(),
                RankineBlocks.KAOLIN.get(),
                RankineBlocks.REFINED_COB.get(),
                RankineBlocks.STUMP.get(),
                RankineBlocks.REACTION_CHAMBER_CELL.get(),
                RankineBlocks.REACTION_CHAMBER_CORE.get(),
                RankineBlocks.CHARCOAL_PIT.get(),
                RankineBlocks.TREE_TAP.get(),
                RankineBlocks.BOTANIST_STATION.get(),
                RankineBlocks.MATERIAL_TESTING_TABLE.get(),
                RankineBlocks.DIAMOND_ANVIL_CELL.get(),
                RankineBlocks.PARTICLE_ACCELERATOR.get(),
                RankineBlocks.BEEHIVE_OVEN_PIT.get(),
                RankineBlocks.SEDIMENT_FAN.get(),
                RankineBlocks.GAS_VENT.get(),
                RankineBlocks.GEODE.get(),
                RankineBlocks.TRAMPOLINE.get(),
                RankineBlocks.METAL_PIPE.get(),
                RankineBlocks.GROUND_TAP.get(),
                RankineBlocks.HEATING_ELEMENT_1.get(),
                RankineBlocks.AIR_DISTILLATION_PACKING.get()
        )) {
            lootTables.put(blk, createBlockLootTable(blk));
        }
        for (Block blk : RankineLists.LANTERNS) {
            lootTables.put(blk, createBlockLootTable(Blocks.LANTERN, BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, RankineDataUtils.rl(blk).getPath().replace("lantern","nugget")))));
        }
        for (Block blk : RankineLists.ASPHALT_BLOCKS) {
            lootTables.put(blk, createBlockLootTable(RankineBlocks.ASPHALT.get()));
        }
        for (Block blk : RankineLists.RED_ASPHALT_BLOCKS) {
            lootTables.put(blk, createBlockLootTable(RankineBlocks.RED_ASPHALT.get()));
        }
        for (Block blk : RankineLists.GRAY_ASPHALT_BLOCKS) {
            lootTables.put(blk, createBlockLootTable(RankineBlocks.GRAY_ASPHALT.get()));
        }
        for (Block blk : RankineLists.DARK_GRAY_ASPHALT_BLOCKS) {
            lootTables.put(blk, createBlockLootTable(RankineBlocks.DARK_GRAY_ASPHALT.get()));
        }
        for (Block blk : RankineLists.BLUE_ASPHALT_BLOCKS) {
            lootTables.put(blk, createBlockLootTable(RankineBlocks.BLUE_ASPHALT.get()));
        }
        for (Block blk : RankineLists.GREEN_ASPHALT_BLOCKS) {
            lootTables.put(blk, createBlockLootTable(RankineBlocks.GREEN_ASPHALT.get()));
        }

        for (Block SOIL : Stream.of(RankineLists.SOIL_BLOCKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            Block GRASS = RankineLists.GRASS_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(SOIL));
            Block PODZOL = RankineLists.PODZOL_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(SOIL));
            Block MYCELIUM = RankineLists.MYCELIUM_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(SOIL));
            Block PATH = RankineLists.PATH_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(SOIL));
            lootTables.put(SOIL, createBlockLootTable(SOIL));
            lootTables.put(PATH, createBlockLootTable(SOIL));
            lootTables.put(GRASS, droppingWithSilkTouch(GRASS,SOIL));
            lootTables.put(PODZOL, droppingWithSilkTouch(PODZOL,SOIL));
            lootTables.put(MYCELIUM, droppingWithSilkTouch(MYCELIUM,SOIL));
        }
        lootTables.put(RankineBlocks.ENDER_SHIRO.get(), droppingWithSilkTouch(RankineBlocks.ENDER_SHIRO.get(),Blocks.END_STONE));
        lootTables.put(RankineBlocks.BONE_CHAR_BLOCK.get(), createBlockLootTable(RankineBlocks.BONE_CHAR_BLOCK.get()));
        lootTables.put(RankineBlocks.FIRE_CLAY.get(), droppingWithSilkTouchOrRandomly(RankineBlocks.FIRE_CLAY.get(), RankineItems.FIRE_CLAY_BALL.get(), ConstantValue.exactly(4)));
        lootTables.put(RankineBlocks.PORCELAIN_CLAY.get(), droppingWithSilkTouchOrRandomly(RankineBlocks.PORCELAIN_CLAY.get(), RankineItems.PORCELAIN_CLAY_BALL.get(), ConstantValue.exactly(4)));
        lootTables.put(RankineBlocks.KAOLINITE_BLOCK.get(), droppingWithSilkTouchOrRandomly(RankineBlocks.KAOLINITE_BLOCK.get(), RankineItems.KAOLINITE.get(), ConstantValue.exactly(4)));
        lootTables.put(RankineBlocks.HALLOYSITE_BLOCK.get(), droppingWithSilkTouchOrRandomly(RankineBlocks.HALLOYSITE_BLOCK.get(), RankineItems.HALLOYSITE.get(), ConstantValue.exactly(4)));
        lootTables.put(RankineBlocks.DARK_GRAVEL.get(), droppingWithSilkTouch(RankineBlocks.DARK_GRAVEL.get(), withSurvivesExplosion(RankineBlocks.DARK_GRAVEL.get(), LootItem.lootTableItem(Items.FLINT).when(BonusLevelTableCondition.bonusLevelFlatChance(fortune(), 0.2F, 0.5F, 1.0F, 1.0F)).otherwise(LootItem.lootTableItem(RankineBlocks.DARK_GRAVEL.get())))));
        lootTables.put(RankineBlocks.LIGHT_GRAVEL.get(), droppingWithSilkTouch(RankineBlocks.LIGHT_GRAVEL.get(), withSurvivesExplosion(RankineBlocks.LIGHT_GRAVEL.get(), LootItem.lootTableItem(Items.FLINT).when(BonusLevelTableCondition.bonusLevelFlatChance(fortune(), 0.05F, 0.1F, 0.2F, 0.5F)).otherwise(LootItem.lootTableItem(RankineBlocks.LIGHT_GRAVEL.get())))));


        for (Block blk : Stream.of(
                RankineLists.VANILLA_BRICKS_SLABS,
                RankineLists.MISC_SLABS
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
        lootTables.put(RankineBlocks.STIBNITE_ORE.get(), nativeOreBlockLootTable(RankineBlocks.STIBNITE_ORE.get(), RankineItems.ANTIMONY_NUGGET.get()));
        lootTables.put(RankineBlocks.PORPHYRY_COPPER.get(), nativeOreBlockLootTable(RankineBlocks.PORPHYRY_COPPER.get(), RankineItems.COPPER_NUGGET.get()));

        lootTables.put(RankineBlocks.LIGNITE_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.LIGNITE_ORE.get(), RankineItems.LIGNITE.get()));
        lootTables.put(RankineBlocks.SUBBITUMINOUS_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.SUBBITUMINOUS_ORE.get(), RankineItems.SUBBITUMINOUS_COAL.get()));
        lootTables.put(RankineBlocks.BITUMINOUS_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.BITUMINOUS_ORE.get(), RankineItems.BITUMINOUS_COAL.get()));
        lootTables.put(RankineBlocks.ANTHRACITE_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.ANTHRACITE_ORE.get(), RankineItems.ANTHRACITE_COAL.get()));
        lootTables.put(RankineBlocks.LONSDALEITE_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.LONSDALEITE_ORE.get(), RankineItems.LONSDALEITE_DIAMOND.get()));
        lootTables.put(RankineBlocks.PLUMBAGO_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.PLUMBAGO_ORE.get(), RankineItems.GRAPHITE.get()));
        lootTables.put(RankineBlocks.KIMBERLITIC_DIAMOND_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.KIMBERLITIC_DIAMOND_ORE.get(), Items.DIAMOND));
        lootTables.put(RankineBlocks.BERYL_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.BERYL_ORE.get(), Items.EMERALD));
        lootTables.put(RankineBlocks.GWIHABAITE_CRYSTAL.get(), fortunableOreOreBlockLootTable(RankineBlocks.GWIHABAITE_CRYSTAL.get(), RankineItems.GWIHABAITE.get()));
        lootTables.put(RankineBlocks.LAZURITE_ORE.get(), droppingWithSilkTouch(RankineBlocks.LAZURITE_ORE.get(), withExplosionDecay(RankineBlocks.LAZURITE_ORE.get(), LootItem.lootTableItem(Items.LAPIS_LAZULI).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 9.0F))).apply(ApplyBonusCount.addOreBonusCount(fortune())))));

        lootTables.put(RankineBlocks.EMERALD_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.EMERALD_ORE.get(), Items.EMERALD));
        lootTables.put(RankineBlocks.DIAMOND_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.DIAMOND_ORE.get(), Items.DIAMOND));
        lootTables.put(RankineBlocks.NETHER_QUARTZ_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.NETHER_QUARTZ_ORE.get(), Items.QUARTZ));
        lootTables.put(RankineBlocks.COAL_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.COAL_ORE.get(), Items.COAL));
        lootTables.put(RankineBlocks.IRON_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.IRON_ORE.get(), Items.RAW_IRON));
        lootTables.put(RankineBlocks.COPPER_ORE.get(), droppingWithSilkTouch(RankineBlocks.COPPER_ORE.get(), withExplosionDecay(RankineBlocks.COPPER_ORE.get(), LootItem.lootTableItem(Items.RAW_COPPER).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))).apply(ApplyBonusCount.addOreBonusCount(fortune())))));
        lootTables.put(RankineBlocks.GOLD_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.GOLD_ORE.get(), Items.RAW_GOLD));
        lootTables.put(RankineBlocks.LAPIS_ORE.get(), droppingWithSilkTouch(RankineBlocks.LAPIS_ORE.get(), withExplosionDecay(RankineBlocks.LAPIS_ORE.get(), LootItem.lootTableItem(Items.LAPIS_LAZULI).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 9.0F))).apply(ApplyBonusCount.addOreBonusCount(fortune())))));
        lootTables.put(RankineBlocks.REDSTONE_ORE.get(), droppingWithSilkTouch(RankineBlocks.REDSTONE_ORE.get(), withExplosionDecay(RankineBlocks.REDSTONE_ORE.get(), LootItem.lootTableItem(Items.REDSTONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 5.0F))).apply(ApplyBonusCount.addUniformBonusCount(fortune())))));
        lootTables.put(RankineBlocks.NETHER_GOLD_ORE.get(), droppingWithSilkTouch(RankineBlocks.NETHER_GOLD_ORE.get(), withExplosionDecay(RankineBlocks.NETHER_GOLD_ORE.get(), LootItem.lootTableItem(Items.GOLD_NUGGET).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))).apply(ApplyBonusCount.addOreBonusCount(fortune())))));

        lootTables.put(RankineBlocks.TAENITE_ORE.get(), createBlockLootTable(RankineBlocks.TAENITE_ORE.get()));
        lootTables.put(RankineBlocks.TETRATAENITE_ORE.get(), createBlockLootTable(RankineBlocks.TETRATAENITE_ORE.get()));
        lootTables.put(RankineBlocks.ANTITAENITE_ORE.get(), createBlockLootTable(RankineBlocks.ANTITAENITE_ORE.get()));
        lootTables.put(RankineBlocks.KAMACITE_ORE.get(), createBlockLootTable(RankineBlocks.KAMACITE_ORE.get()));
        lootTables.put(RankineBlocks.BATTERY_CHARGER.get(), droppingWithName(RankineBlocks.BATTERY_CHARGER.get()));
        lootTables.put(RankineBlocks.MIXING_BARREL.get(), droppingWithName(RankineBlocks.MIXING_BARREL.get()));
        lootTables.put(RankineBlocks.FUSION_FURNACE.get(), droppingWithName(RankineBlocks.FUSION_FURNACE.get()));
        lootTables.put(RankineBlocks.ALLOY_FURNACE.get(), droppingWithName(RankineBlocks.ALLOY_FURNACE.get()));
        lootTables.put(RankineBlocks.PISTON_CRUSHER.get(), droppingWithName(RankineBlocks.PISTON_CRUSHER.get()));
        lootTables.put(RankineBlocks.GYRATORY_CRUSHER.get(), droppingWithName(RankineBlocks.GYRATORY_CRUSHER.get()));
        lootTables.put(RankineBlocks.CRUCIBLE_BLOCK.get(), droppingWithName(RankineBlocks.CRUCIBLE_BLOCK.get()));
        lootTables.put(RankineBlocks.EVAPORATION_TOWER.get(), droppingWithName(RankineBlocks.EVAPORATION_TOWER.get()));
        lootTables.put(RankineBlocks.TEMPLATE_TABLE.get(), droppingWithName(RankineBlocks.TEMPLATE_TABLE.get()));
        lootTables.put(RankineBlocks.INDUCTION_FURNACE.get(), droppingWithName(RankineBlocks.INDUCTION_FURNACE.get()));
        lootTables.put(RankineBlocks.GAS_BOTTLER.get(), droppingWithName(RankineBlocks.GAS_BOTTLER.get()));

        for (Block ORE : RankineLists.CRUSHING_ORES) {
            lootTables.put(ORE, fortunableOreOreBlockLootTable(ORE, BuiltInRegistries.ITEM.get(ResourceLocation.parse(RankineDataUtils.rl(ORE).toString().replace("_ore","")))));
        }
        for (Block BLK : RankineLists.EIGHT_LAYER_BLOCKS) {
            lootTables.put(BLK, eightLayerBlock(BLK));
        }
        for (Block BLK : RankineLists.LIGHTNING_GLASSES) {
            lootTables.put(BLK, droppingWithSilkTouchPlus(BLK, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.GLOWSTONE_DUST)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BLK).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LightningGlassBlock.GLOWING, true)))));
        }
        for (Block BLK : RankineLists.HOLLOW_LOGS) {
            lootTables.put(BLK, droppingWithSilkTouchOrRandomly(BLK, Items.STICK, UniformGenerator.between(2.0F, 6.0F)));
        }
        for (Block BLK : RankineLists.MUSHROOM_BLOCKS) {
            lootTables.put(BLK, droppingItemRarely(BLK, RankineLists.WALL_MUSHROOMS.get(RankineLists.MUSHROOM_BLOCKS.indexOf(BLK)).asItem()));
        }
        for (Block BLK : RankineLists.LEAF_LITTERS) {
            lootTables.put(BLK, withShears(BLK));
        }
        for (Block BLK : Stream.of(
                RankineLists.TALL_FLOWERS,
                RankineLists.METAL_DOORS
        ).flatMap(Collection::stream).collect(Collectors.toList())) {
            lootTables.put(BLK, droppingWhen(BLK, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        }

        for (Block BLK : RankineLists.ALLOY_POLES) {
            lootTables.put(BLK, metalPole(BLK));
        }

        lootTables.put(RankineBlocks.RICE_PLANT.get(), singleCrop(RankineBlocks.RICE_PLANT.get(), RankineItems.RICE.get(), RankineItems.RICE_SEEDS.get()));
        lootTables.put(RankineBlocks.OAT_PLANT.get(), singleCrop(RankineBlocks.OAT_PLANT.get(), RankineItems.OATS.get(), RankineItems.OAT_SEEDS.get()));
        lootTables.put(RankineBlocks.MILLET_PLANT.get(), singleCrop(RankineBlocks.MILLET_PLANT.get(), RankineItems.MILLET.get(), RankineItems.MILLET_SEEDS.get()));
        lootTables.put(RankineBlocks.CAMPHOR_BASIL_PLANT.get(), singleCrop(RankineBlocks.CAMPHOR_BASIL_PLANT.get(), RankineItems.CAMPHOR_BASIL_SEEDS.get(), RankineItems.CAMPHOR_BASIL_LEAF.get()));
        lootTables.put(RankineBlocks.SOYBEAN_PLANT.get(), singleCrop(RankineBlocks.SOYBEAN_PLANT.get(), RankineItems.SOYBEANS.get(), RankineItems.SOYBEANS.get()));
        lootTables.put(RankineBlocks.COTTON_PLANT.get(), doubleCrop(RankineBlocks.COTTON_PLANT.get(), RankineItems.COTTON.get(), RankineItems.COTTON_SEEDS.get(), 2));
        lootTables.put(RankineBlocks.RYE_PLANT.get(), doubleCrop(RankineBlocks.RYE_PLANT.get(), RankineItems.RYE.get(), RankineItems.RYE_SEEDS.get(), 1));
        lootTables.put(RankineBlocks.BARLEY_PLANT.get(), doubleCrop(RankineBlocks.BARLEY_PLANT.get(), RankineItems.BARLEY.get(), RankineItems.BARLEY_SEEDS.get(), 1));
        lootTables.put(RankineBlocks.CORN_PLANT.get(), tripleCrop(RankineBlocks.CORN_PLANT.get(), RankineItems.CORN_EAR.get(), RankineItems.CORN_SEEDS.get(), 2));
        lootTables.put(RankineBlocks.JUTE_PLANT.get(), tripleCrop(RankineBlocks.JUTE_PLANT.get(), RankineItems.JUTE.get(), RankineItems.JUTE_SEEDS.get(), 3));
        lootTables.put(RankineBlocks.SORGHUM_PLANT.get(), tripleCrop(RankineBlocks.SORGHUM_PLANT.get(), RankineItems.SORGHUM.get(), RankineItems.SORGHUM_SEEDS.get(), 2));
        lootTables.put(RankineBlocks.ASPARAGUS_PLANT.get(), doubleCrop(RankineBlocks.ASPARAGUS_PLANT.get(), RankineItems.ASPARAGUS.get(), RankineItems.ASPARAGUS_SEEDS.get(), 1));

        lootTables.put(RankineBlocks.BLACKBERRY_BUSH.get(), bushOneDrop(RankineBlocks.BLACKBERRY_BUSH.get(), RankineItems.BLACKBERRIES.get()));
        lootTables.put(RankineBlocks.RASPBERRY_BUSH.get(), bushOneDrop(RankineBlocks.RASPBERRY_BUSH.get(), RankineItems.RASPBERRIES.get()));
        lootTables.put(RankineBlocks.BANANA_YUCCA_BUSH.get(), bushOneDrop(RankineBlocks.BANANA_YUCCA_BUSH.get(), RankineItems.BANANA_YUCCA.get()));
        lootTables.put(RankineBlocks.SNOWBERRY_BUSH.get(), bushOneDrop(RankineBlocks.SNOWBERRY_BUSH.get(), RankineItems.SNOWBERRIES.get()));
        lootTables.put(RankineBlocks.STRAWBERRY_BUSH.get(), bushOneDrop(RankineBlocks.STRAWBERRY_BUSH.get(), RankineItems.STRAWBERRIES.get()));
        lootTables.put(RankineBlocks.POKEBERRY_BUSH.get(), doubleBushOneDrop(RankineBlocks.POKEBERRY_BUSH.get(), RankineItems.POKEBERRIES.get()));
        lootTables.put(RankineBlocks.BLUEBERRY_BUSH.get(), doubleBushOneDrop(RankineBlocks.BLUEBERRY_BUSH.get(), RankineItems.BLUEBERRIES.get()));
        lootTables.put(RankineBlocks.ELDERBERRY_BUSH.get(), doubleBushOneDrop(RankineBlocks.ELDERBERRY_BUSH.get(), RankineItems.ELDERBERRIES.get()));
        lootTables.put(RankineBlocks.CRANBERRY_BUSH.get(), doubleBushOneDrop(RankineBlocks.CRANBERRY_BUSH.get(), RankineItems.CRANBERRIES.get()));

        lootTables.put(RankineBlocks.SHORT_GRASS.get(), droppingSeeds(RankineBlocks.SHORT_GRASS.get()));
        lootTables.put(RankineBlocks.STINGING_NETTLE.get(), withShears(RankineBlocks.STINGING_NETTLE.get()));
        lootTables.put(RankineBlocks.YELLOW_CLOVER.get(), withShears(RankineBlocks.YELLOW_CLOVER.get()));
        lootTables.put(RankineBlocks.RED_CLOVER.get(), withShears(RankineBlocks.RED_CLOVER.get()));
        lootTables.put(RankineBlocks.WHITE_CLOVER.get(), withShears(RankineBlocks.WHITE_CLOVER.get()));
        lootTables.put(RankineBlocks.CRIMSON_CLOVER.get(), withShears(RankineBlocks.CRIMSON_CLOVER.get()));


    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected LootPoolSingletonContainer.Builder<?> withExplosionDecay(ItemLike item, LootPoolSingletonContainer.Builder<?> entry) {
        return IMMUNE_TO_EXPLOSIONS.contains(item.asItem())
                ? entry
                : (LootPoolSingletonContainer.Builder<?>) ((LootPoolSingletonContainer.Builder) entry).apply(ApplyExplosionDecay.explosionDecay());
    }

    protected LootPool.Builder withExplosionDecay(ItemLike item, LootPool.Builder pool) {
        return IMMUNE_TO_EXPLOSIONS.contains(item.asItem()) ? pool : pool.apply(ApplyExplosionDecay.explosionDecay());
    }

    protected LootTable.Builder withExplosionDecay(ItemLike item, LootTable.Builder table) {
        return IMMUNE_TO_EXPLOSIONS.contains(item.asItem()) ? table : table.apply(ApplyExplosionDecay.explosionDecay());
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected LootPoolSingletonContainer.Builder<?> withSurvivesExplosion(ItemLike item, LootPoolSingletonContainer.Builder<?> entry) {
        return IMMUNE_TO_EXPLOSIONS.contains(item.asItem())
                ? entry
                : (LootPoolSingletonContainer.Builder<?>) ((LootPoolSingletonContainer.Builder) entry).when(ExplosionCondition.survivesExplosion());
    }

    protected LootPool.Builder withSurvivesExplosion(ItemLike item, LootPool.Builder pool) {
        return IMMUNE_TO_EXPLOSIONS.contains(item.asItem()) ? pool : pool.when(ExplosionCondition.survivesExplosion());
    }

    protected AlternativesEntry.Builder withSurvivesExplosion(ItemLike item, AlternativesEntry.Builder entry) {
        return IMMUNE_TO_EXPLOSIONS.contains(item.asItem()) ? entry : entry.when(ExplosionCondition.survivesExplosion());
    }

    protected LootTable.Builder dropping(ItemLike item) {
        return LootTable.lootTable().withPool(withSurvivesExplosion(item, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(item))));
    }

    protected LootTable.Builder dropping(Block block, LootItemCondition.Builder conditionBuilder, LootPoolEntryContainer.Builder<?> p_218494_2_) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(block).when(conditionBuilder).otherwise(p_218494_2_)));
    }
    protected LootTable.Builder droppingWithSilkTouchPlus(Block block, LootPool.Builder alwaysDropBuilder) {
        return LootTable.lootTable().withPool(alwaysDropBuilder).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(block).when(hasSilkTouch())));
    }

    protected LootTable.Builder droppingWithSilkTouch(Block block, LootPoolEntryContainer.Builder<?> builder) {
        return dropping(block, hasSilkTouch(), builder);
    }

    protected LootTable.Builder droppingWithShears(Block block, LootPoolEntryContainer.Builder<?> noShearAlternativeEntry) {
        return dropping(block, HAS_SHEARS, noShearAlternativeEntry);
    }

    protected LootTable.Builder droppingWithSilkTouchOrShears(Block block, LootPoolEntryContainer.Builder<?> alternativeLootEntry) {
        return dropping(block, silkTouchOrShears(), alternativeLootEntry);
    }

    protected LootTable.Builder droppingWithSilkTouch(Block block, ItemLike noSilkTouch) {
        return droppingWithSilkTouch(block, withSurvivesExplosion(block, LootItem.lootTableItem(noSilkTouch)));
    }

    protected LootTable.Builder droppingRandomly(ItemLike item, NumberProvider range) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(withExplosionDecay(item, LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(range)))));
    }

    protected LootTable.Builder droppingWithSilkTouchOrRandomly(Block block, ItemLike item, NumberProvider range) {
        return droppingWithSilkTouch(block, withExplosionDecay(block, LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(range))));
    }

    protected LootTable.Builder onlyWithSilkTouch(ItemLike item) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(hasSilkTouch()).setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(item)));
    }

    protected LootTable.Builder droppingAndFlowerPot(ItemLike flower) {
        return LootTable.lootTable().withPool(withSurvivesExplosion(Blocks.FLOWER_POT, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Blocks.FLOWER_POT)))).withPool(withSurvivesExplosion(flower, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(flower))));
    }

    protected LootTable.Builder droppingSlab(Block slab) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(withExplosionDecay(slab, LootItem.lootTableItem(slab).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(slab).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))))));
    }

    protected <T extends Comparable<T> & StringRepresentable> LootTable.Builder droppingWhen(Block block, Property<T> property, T value) {
        return LootTable.lootTable().withPool(withSurvivesExplosion(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(block).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value))))));
    }

    protected LootTable.Builder droppingWithName(Block block) {
        return LootTable.lootTable().withPool(withSurvivesExplosion(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(block).apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)))));
    }





    protected LootTable.Builder droppingItemWithFortune(Block block, Item item) {
        return droppingWithSilkTouch(block, withExplosionDecay(block, LootItem.lootTableItem(item).apply(ApplyBonusCount.addOreBonusCount(fortune()))));
    }

    /**
     * Creates a builder that drops the given IItemProvider in amounts between 0 and 2, most often 0. Only used in
     * vanilla for huge mushroom blocks.
     */
    protected LootTable.Builder droppingItemRarely(Block block, ItemLike item) {
        return droppingWithSilkTouch(block, withExplosionDecay(block, LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(UniformGenerator.between(-6.0F, 2.0F))).apply(LimitCount.limitCount(IntRange.lowerBound(0)))));
    }

    protected LootTable.Builder droppingSeeds(Block block) {
        return droppingWithShears(block, withExplosionDecay(block, LootItem.lootTableItem(Items.WHEAT_SEEDS).when(LootItemRandomChanceCondition.randomChance(0.125F)).apply(ApplyBonusCount.addUniformBonusCount(fortune(), 2))));
    }

    /**
     * Creates a builder that drops the given IItemProvider in amounts between 0 and 3, based on the AGE property. Only
     * used in vanilla for pumpkin and melon stems.
     */
    protected LootTable.Builder droppingByAge(Block stemFruit, Item item) {
        return LootTable.lootTable().withPool(withExplosionDecay(stemFruit, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.06666667F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 0)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.13333334F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 1)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.2F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 2)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.26666668F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 3)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.33333334F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 4)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.4F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 5)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.46666667F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 6)))).apply(SetItemCountFunction.setCount(BinomialDistributionGenerator.binomial(3, 0.53333336F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(stemFruit).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StemBlock.AGE, 7)))))));
    }


    protected LootTable.Builder onlyWithShears(ItemLike item) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(HAS_SHEARS).add(LootItem.lootTableItem(item)));
    }

    /**
     * Used for all leaves, drops self with silk touch, otherwise drops the second Block param with the passed chances
     * for fortune levels, adding in sticks.
     */
    protected LootTable.Builder droppingWithChancesAndSticks(Block block, Block sapling, float... chances) {
        return droppingWithSilkTouchOrShears(block, withSurvivesExplosion(block, LootItem.lootTableItem(sapling)).when(BonusLevelTableCondition.bonusLevelFlatChance(fortune(), chances))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(notSilkTouchOrShears()).add(withExplosionDecay(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).when(BonusLevelTableCondition.bonusLevelFlatChance(fortune(), 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
    }

    /**
     * Used for oak and dark oak, same as droppingWithChancesAndSticks but adding in apples.
     */
    protected LootTable.Builder droppingWithChancesSticksAndApples(Block block, Block sapling, float... chances) {
        return droppingWithChancesAndSticks(block, sapling, chances).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(notSilkTouchOrShears()).add(withSurvivesExplosion(block, LootItem.lootTableItem(Items.APPLE)).when(BonusLevelTableCondition.bonusLevelFlatChance(fortune(), 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
    }
    protected LootTable.Builder droppingWithChancesSticksAndExtra(Block block, Block sapling, Item Item, float... chances) {
        return droppingWithChancesAndSticks(block, sapling, chances).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(notSilkTouchOrShears()).add(withSurvivesExplosion(block, LootItem.lootTableItem(Item)).when(BonusLevelTableCondition.bonusLevelFlatChance(fortune(), 0.01F, 0.012F, 0.015F, 0.02F, 0.05F))));
    }

    /**
     * Drops the first item parameter always, and the second item parameter plus more of the first when the loot
     * condition is met, applying fortune to only the second argument.
     */
    protected LootTable.Builder droppingAndBonusWhen(Block block, Item itemConditional, Item withBonus, LootItemCondition.Builder conditionBuilder) {
        return withExplosionDecay(block, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(itemConditional).when(conditionBuilder).otherwise(LootItem.lootTableItem(withBonus)))).withPool(LootPool.lootPool().when(conditionBuilder).add(LootItem.lootTableItem(withBonus).apply(ApplyBonusCount.addBonusBinomialDistributionCount(fortune(), 0.5714286F, 3)))));
    }



    public LootTable.Builder blockNoDrop() {
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
                .withPool(
                    LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(
                        AlternativesEntry.alternatives(
                        LootItem.lootTableItem(DROP)
                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7))),
                        LootItem.lootTableItem(SEED))
                    )
                )
                .withPool(
                    LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(
                        LootItem.lootTableItem(SEED)
                        .apply(ApplyBonusCount.addBonusBinomialDistributionCount(fortune(), 0.5714286F, 2))
                    )
                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7)))
                )
                .apply(ApplyExplosionDecay.explosionDecay());
    }

    protected LootTable.Builder doubleCrop(Block CROP, Item DROP, Item SEED, int Count) {
        return LootTable.lootTable()
                .withPool(
                    LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(
                        AlternativesEntry.alternatives(
                            LootItem.lootTableItem(DROP)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(Count)))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7).hasProperty(DoubleCropsBlock.SECTION, DoubleBlockHalf.LOWER))),
                            LootItem.lootTableItem(SEED)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoubleCropsBlock.SECTION, DoubleBlockHalf.LOWER)))
                        )
                    )
                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoubleCropsBlock.SECTION, DoubleBlockHalf.LOWER))))
                .withPool(
                    LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(
                        LootItem.lootTableItem(SEED)
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7).hasProperty(DoubleCropsBlock.SECTION, DoubleBlockHalf.LOWER)))
                        .apply(ApplyBonusCount.addBonusBinomialDistributionCount(fortune(), 0.5714286F, 2)))
                )
                .apply(ApplyExplosionDecay.explosionDecay());
    }

    protected LootTable.Builder tripleCrop(Block CROP, Item DROP, Item SEED, int Count) {
        return LootTable.lootTable()
                .withPool(
                    LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(
                        AlternativesEntry.alternatives(
                            LootItem.lootTableItem(DROP)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(Count)))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7).hasProperty(TripleCropsBlock.SECTION, TripleBlockSection.BOTTOM))),
                            LootItem.lootTableItem(SEED)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TripleCropsBlock.SECTION, TripleBlockSection.BOTTOM)))
                        )
                    )
                )
                .withPool(
                    LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(
                        LootItem.lootTableItem(SEED)
                        .apply(ApplyBonusCount.addBonusBinomialDistributionCount(fortune(), 0.5714286F, 2))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(CROP).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7).hasProperty(TripleCropsBlock.SECTION, TripleBlockSection.BOTTOM)))
                    )
                )
                .apply(ApplyExplosionDecay.explosionDecay());
    }

    protected LootTable.Builder doubleBushOneDrop(Block BUSH, Item DROP) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(AlternativesEntry.alternatives(
                        LootItem.lootTableItem(DROP)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BUSH).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankinePlantBlock.AGE, 3).hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(fortune())),
                        LootItem.lootTableItem(DROP)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BUSH).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankinePlantBlock.AGE, 2).hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(fortune()))))
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
                        .apply(ApplyBonusCount.addUniformBonusCount(fortune())),
                    LootItem.lootTableItem(DROP)
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BUSH).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankinePlantBlock.AGE, 2)))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(fortune()))))
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
                                .apply(ApplyBonusCount.addUniformBonusCount(fortune())),
                        LootItem.lootTableItem(DROP1)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BUSH).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankinePlantBlock.AGE, 2)))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(fortune())),
                        LootItem.lootTableItem(DROP2)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BUSH).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RankinePlantBlock.AGE, 2)))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .apply(ApplyBonusCount.addUniformBonusCount(fortune()))))
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
                .when(HAS_SHEARS)
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
    protected LootTable.Builder createBlockLootTable(Block BLK, ItemLike itemLike) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(BLK)).when(ExplosionCondition.survivesExplosion()))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(itemLike)).when(ExplosionCondition.survivesExplosion()));
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
                    .when(hasSilkTouch())
                    .when(ExplosionCondition.survivesExplosion()))
                .add(LootItem.lootTableItem(NUGGET)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 8.0F)))
                    .apply(ApplyBonusCount.addOreBonusCount(fortune())))
                    .when(ExplosionCondition.survivesExplosion());
        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder fortunableOreOreBlockLootTable(Block ORE, Item GEM) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(ORE)
                        .when(hasSilkTouch()))
                .add(LootItem.lootTableItem(GEM)
                        .when(doesNotHaveSilkTouch())
                        .apply(ApplyBonusCount.addOreBonusCount(fortune())))
                .when(ExplosionCondition.survivesExplosion());
        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder silkTouchOnly(Block BLK) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(BLK)
                        .when(hasSilkTouch())
                .when(ExplosionCondition.survivesExplosion()));
        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder silkTouch(Block BLK, Item OTHER) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(BLK)
                        .when(hasSilkTouch()))
                .add(LootItem.lootTableItem(OTHER)
                        .when(doesNotHaveSilkTouch()))
                .when(ExplosionCondition.survivesExplosion());
        return LootTable.lootTable().withPool(builder);
    }


}
