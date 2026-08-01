package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.world.gen.ConfigRarityFilter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * PlacedFeature keys + datagen bootstrap.
 * PORT-NOTE: placed features are a datapack registry in 1.21 — no more DeferredRegister.
 * Registry path names are identical to 1.18.2 (including the historical "pos_world_replacer" typo).
 * The fumarole/meteorite rarity used to read the config at registration time; that is preserved
 * through the custom {@code rankine:config_rarity_filter} placement modifier.
 */
public class RankinePlacedFeatures {

    public static final ResourceKey<PlacedFeature> PLACED_FUMAROLE = createKey("fumarole");
    public static final ResourceKey<PlacedFeature> PLACED_INTRUSION = createKey("intrusion");
    public static final ResourceKey<PlacedFeature> PLACED_METEORITE = createKey("meteorite");
    public static final ResourceKey<PlacedFeature> PLACED_END_METEORITE = createKey("end_meteorite");
    public static final ResourceKey<PlacedFeature> PLACED_ANTIMATTER_BLOB = createKey("antimatter_blob");
    public static final ResourceKey<PlacedFeature> PLACED_WALL_MUSHROOMS = createKey("wall_mushrooms");
    public static final ResourceKey<PlacedFeature> PLACED_WORLD_REPLACER = createKey("world_replacer");
    public static final ResourceKey<PlacedFeature> PLACED_POST_WORLD_REPLACER = createKey("pos_world_replacer");
    public static final ResourceKey<PlacedFeature> PLACED_FLAT_BEDROCK = createKey("flat_bedrock");

    public static final ResourceKey<PlacedFeature> PLACED_PATCH_LILIES = createKey("patch_lilies");
    public static final ResourceKey<PlacedFeature> PLACED_PATCH_MORNING_GLORIES = createKey("patch_morning_glories");
    public static final ResourceKey<PlacedFeature> PLACED_PATCH_SAVANNA_FLOWERS = createKey("patch_savanna_flowers");
    public static final ResourceKey<PlacedFeature> PLACED_PATCH_SAVANNA_PLANTS = createKey("patch_savanna_plants");
    public static final ResourceKey<PlacedFeature> PLACED_PATCH_FOREST_PLANTS = createKey("patch_forest_plants");
    public static final ResourceKey<PlacedFeature> PLACED_PATCH_SWAMP_PLANTS = createKey("patch_swamp_plants");
    public static final ResourceKey<PlacedFeature> PLACED_PATCH_JUNGLE_PLANTS = createKey("patch_jungle_plants");
    public static final ResourceKey<PlacedFeature> PLACED_PATCH_PLAINS_PLANTS = createKey("patch_plains_plants");
    public static final ResourceKey<PlacedFeature> PLACED_PATCH_MOUNTAIN_PLANTS = createKey("patch_mountain_plants");
    public static final ResourceKey<PlacedFeature> PLACED_PATCH_TAIGA_PLANTS = createKey("patch_taiga_plants");
    public static final ResourceKey<PlacedFeature> PLACED_PATCH_COBBLES = createKey("patch_cobbles");

    public static final ResourceKey<PlacedFeature> PLACED_CEDAR_TREE = createKey("cedar_tree");
    public static final ResourceKey<PlacedFeature> PLACED_EASTERN_HEMLOCK_TREE = createKey("eastern_hemlock_tree");
    public static final ResourceKey<PlacedFeature> PLACED_WESTERN_HEMLOCK_TREE = createKey("western_hemlock_tree");
    public static final ResourceKey<PlacedFeature> PLACED_BALSAM_FIR_TREE = createKey("balsam_fir_tree");
    public static final ResourceKey<PlacedFeature> PLACED_SHORT_BALSAM_FIR_TREE = createKey("short_balsam_fir_tree");
    public static final ResourceKey<PlacedFeature> PLACED_SNOWY_SHORT_BALSAM_FIR_TREE = createKey("snowy_short_balsam_fir_tree");
    public static final ResourceKey<PlacedFeature> PLACED_COCONUT_PALM_TREE = createKey("coconut_palm_tree");
    public static final ResourceKey<PlacedFeature> PLACED_MODIFIED_BIRCH_TREE = createKey("modified_birch_tree");
    public static final ResourceKey<PlacedFeature> PLACED_BLACK_BIRCH_TREE = createKey("black_birch_tree");
    public static final ResourceKey<PlacedFeature> PLACED_YELLOW_BIRCH_TREE = createKey("yellow_birch_tree");
    public static final ResourceKey<PlacedFeature> PLACED_RED_BIRCH_TREE = createKey("red_birch_tree");
    public static final ResourceKey<PlacedFeature> PLACED_MAGNOLIA_TREE = createKey("magnolia_tree");
    public static final ResourceKey<PlacedFeature> PLACED_ERYTHRINA_TREE = createKey("erythrina_tree");
    public static final ResourceKey<PlacedFeature> PLACED_HONEY_LOCUST_TREE = createKey("honey_locust_tree");
    public static final ResourceKey<PlacedFeature> PLACED_WEEPING_WILLOW_TREE = createKey("weeping_willow_tree");
    public static final ResourceKey<PlacedFeature> PLACED_BLACK_WALNUT_TREE = createKey("black_walnut_tree");
    public static final ResourceKey<PlacedFeature> PLACED_PINYON_PINE_TREE = createKey("pinyon_pine_tree");
    public static final ResourceKey<PlacedFeature> PLACED_JUNIPER_TREE = createKey("juniper_tree");
    public static final ResourceKey<PlacedFeature> PLACED_MAPLE_TREE = createKey("maple_tree");
    public static final ResourceKey<PlacedFeature> PLACED_SHARINGA_TREE = createKey("sharinga_tree");
    public static final ResourceKey<PlacedFeature> PLACED_CORK_OAK_TREE = createKey("cork_oak_tree");
    public static final ResourceKey<PlacedFeature> PLACED_CINNAMON_TREE = createKey("cinnamon_tree");
    public static final ResourceKey<PlacedFeature> PLACED_PETRIFIED_CHORUS_TREE = createKey("petrified_chorus_tree");

    public static final ResourceKey<PlacedFeature> PLACED_CEDAR_FALLEN_LOG = createKey("cedar_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_EASTERN_HEMLOCK_FALLEN_LOG = createKey("eastern_hemlock_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_WESTERN_HEMLOCK_FALLEN_LOG = createKey("western_hemlock_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_BALSAM_FIR_FALLEN_LOG = createKey("balsam_fir_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_COCONUT_PALM_FALLEN_LOG = createKey("coconut_palm_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_BIRCH_FALLEN_LOG = createKey("birch_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_OAK_FALLEN_LOG = createKey("oak_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_DARK_OAK_FALLEN_LOG = createKey("dark_oak_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_ACACIA_FALLEN_LOG = createKey("acacia_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_SPRUCE_FALLEN_LOG = createKey("spruce_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_JUNGLE_FALLEN_LOG = createKey("jungle_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_BLACK_BIRCH_FALLEN_LOG = createKey("black_birch_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_YELLOW_BIRCH_FALLEN_LOG = createKey("yellow_birch_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_RED_BIRCH_FALLEN_LOG = createKey("red_birch_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_MAGNOLIA_FALLEN_LOG = createKey("magnolia_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_ERYTHRINA_FALLEN_LOG = createKey("erythrina_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_HONEY_LOCUST_FALLEN_LOG = createKey("honey_locust_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_WEEPING_WILLOW_FALLEN_LOG = createKey("weeping_willow_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_BLACK_WALNUT_FALLEN_LOG = createKey("black_walnut_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_PINYON_PINE_FALLEN_LOG = createKey("pinyon_pine_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_JUNIPER_FALLEN_LOG = createKey("juniper_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_MAPLE_FALLEN_LOG = createKey("maple_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_SHARINGA_FALLEN_LOG = createKey("sharinga_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_CORK_OAK_FALLEN_LOG = createKey("cork_oak_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_CINNAMON_FALLEN_LOG = createKey("cinnamon_fallen_log");
    public static final ResourceKey<PlacedFeature> PLACED_PETRIFIED_CHORUS_FALLEN_LOG = createKey("petrified_chorus_fallen_log");

    public static final BlockPredicate SNOW_TREE_PREDICATE = BlockPredicate.matchesBlocks(new BlockPos(0, -1, 0), Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW);
    public static final List<PlacementModifier> SNOW_TREE_FILTER_DECORATOR = List.of(
            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.POWDER_SNOW)), 8),
            BlockPredicateFilter.forPredicate(SNOW_TREE_PREDICATE));

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, name));
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?,?>> configured = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, PLACED_FUMAROLE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_FUMAROLE), Arrays.asList(new ConfigRarityFilter("fumarole"), BiomeFilter.biome()));
        register(context, PLACED_INTRUSION, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_INTRUSION), Arrays.asList(BiomeFilter.biome()));
        register(context, PLACED_METEORITE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_METEORITE), Arrays.asList(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, new ConfigRarityFilter("meteorite"), BiomeFilter.biome()));
        register(context, PLACED_END_METEORITE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_END_METEORITE), Collections.singletonList(BiomeFilter.biome()));
        register(context, PLACED_ANTIMATTER_BLOB, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_ANTIMATTER_BLOB), Collections.singletonList(BiomeFilter.biome()));
        register(context, PLACED_WALL_MUSHROOMS, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_WALL_MUSHROOMS), Arrays.asList(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, RarityFilter.onAverageOnceEvery(3), BiomeFilter.biome()));
        register(context, PLACED_WORLD_REPLACER, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_WORLD_REPLACER), Collections.emptyList());
        register(context, PLACED_POST_WORLD_REPLACER, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_POST_WORLD_REPLACER), Collections.emptyList());
        register(context, PLACED_FLAT_BEDROCK, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_FLAT_BEDROCK), Collections.emptyList());

        register(context, PLACED_PATCH_LILIES, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_PATCH_LILIES), Arrays.asList(RarityFilter.onAverageOnceEvery(9), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
        register(context, PLACED_PATCH_MORNING_GLORIES, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_PATCH_MORNING_GLORIES), Arrays.asList(RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
        register(context, PLACED_PATCH_SAVANNA_FLOWERS, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_PATCH_SAVANNA_FLOWERS), Arrays.asList(RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
        register(context, PLACED_PATCH_SAVANNA_PLANTS, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_PATCH_SAVANNA_PLANTS), Arrays.asList(RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
        register(context, PLACED_PATCH_FOREST_PLANTS, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_PATCH_FOREST_PLANTS), Arrays.asList(RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
        register(context, PLACED_PATCH_SWAMP_PLANTS, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_PATCH_SWAMP_PLANTS), Arrays.asList(RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
        register(context, PLACED_PATCH_JUNGLE_PLANTS, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_PATCH_JUNGLE_PLANTS), Arrays.asList(RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
        register(context, PLACED_PATCH_PLAINS_PLANTS, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_PATCH_PLAINS_PLANTS), Arrays.asList(RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
        register(context, PLACED_PATCH_MOUNTAIN_PLANTS, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_PATCH_MOUNTAIN_PLANTS), Arrays.asList(RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
        register(context, PLACED_PATCH_TAIGA_PLANTS, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_PATCH_TAIGA_PLANTS), Arrays.asList(RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
        register(context, PLACED_PATCH_COBBLES, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_PATCH_COBBLES), Arrays.asList(RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

        register(context, PLACED_CEDAR_TREE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_CEDAR_TREE), VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1F, 1)));
        register(context, PLACED_EASTERN_HEMLOCK_TREE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_EASTERN_HEMLOCK_TREE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(6)));
        register(context, PLACED_WESTERN_HEMLOCK_TREE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_WESTERN_HEMLOCK_TREE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(4)));
        register(context, PLACED_BALSAM_FIR_TREE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_BALSAM_FIR_TREE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(4)));
        register(context, PLACED_SHORT_BALSAM_FIR_TREE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_SHORT_BALSAM_FIR_TREE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(3)));
        register(context, PLACED_SNOWY_SHORT_BALSAM_FIR_TREE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_SHORT_BALSAM_FIR_TREE), Stream.concat(SNOW_TREE_FILTER_DECORATOR.stream(), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(2)).stream()).collect(Collectors.toList()));
        register(context, PLACED_COCONUT_PALM_TREE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_COCONUT_PALM_TREE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(2)));
        register(context, PLACED_MODIFIED_BIRCH_TREE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_MODIFIED_BIRCH_TREE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(2)));
        register(context, PLACED_BLACK_BIRCH_TREE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_BLACK_BIRCH_TREE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(4)));
        register(context, PLACED_YELLOW_BIRCH_TREE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_YELLOW_BIRCH_TREE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(2)));
        register(context, PLACED_RED_BIRCH_TREE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_RED_BIRCH_TREE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(4)));
        register(context, PLACED_MAGNOLIA_TREE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_MAGNOLIA_TREE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(6)));
        register(context, PLACED_ERYTHRINA_TREE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_ERYTHRINA_TREE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(6)));
        register(context, PLACED_HONEY_LOCUST_TREE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_HONEY_LOCUST_TREE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(6)));
        register(context, PLACED_WEEPING_WILLOW_TREE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_WEEPING_WILLOW_TREE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(6)));
        register(context, PLACED_BLACK_WALNUT_TREE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_BLACK_WALNUT_TREE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(3)));
        register(context, PLACED_PINYON_PINE_TREE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_PINYON_PINE_TREE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(3)));
        register(context, PLACED_JUNIPER_TREE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_JUNIPER_TREE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(2)));
        register(context, PLACED_MAPLE_TREE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_MAPLE_TREE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(3), RankineBlocks.MAPLE.getSapling()));
        register(context, PLACED_SHARINGA_TREE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_SHARINGA_TREE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(3), RankineBlocks.SHARINGA.getSapling()));
        register(context, PLACED_CORK_OAK_TREE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_CORK_OAK_TREE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(11), RankineBlocks.CORK_OAK.getSapling()));
        register(context, PLACED_CINNAMON_TREE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_CINNAMON_TREE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(3), RankineBlocks.CINNAMON.getSapling()));
        register(context, PLACED_PETRIFIED_CHORUS_TREE, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_PETRIFIED_CHORUS_TREE), VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(4)));

        register(context, PLACED_CEDAR_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_CEDAR_FALLEN_LOG), fallenLog(2));
        register(context, PLACED_EASTERN_HEMLOCK_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_EASTERN_HEMLOCK_FALLEN_LOG), fallenLog(3));
        register(context, PLACED_WESTERN_HEMLOCK_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_WESTERN_HEMLOCK_FALLEN_LOG), fallenLog(3));
        register(context, PLACED_BALSAM_FIR_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_BALSAM_FIR_FALLEN_LOG), fallenLog(3));
        register(context, PLACED_COCONUT_PALM_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_COCONUT_PALM_FALLEN_LOG), fallenLog(3));
        register(context, PLACED_BIRCH_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_BIRCH_FALLEN_LOG), fallenLog(3));
        register(context, PLACED_OAK_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_OAK_FALLEN_LOG), fallenLog(3));
        register(context, PLACED_DARK_OAK_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_DARK_OAK_FALLEN_LOG), fallenLog(3));
        register(context, PLACED_ACACIA_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_ACACIA_FALLEN_LOG), fallenLog(3));
        register(context, PLACED_SPRUCE_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_SPRUCE_FALLEN_LOG), fallenLog(3));
        register(context, PLACED_JUNGLE_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_JUNGLE_FALLEN_LOG), fallenLog(3));
        register(context, PLACED_BLACK_BIRCH_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_BLACK_BIRCH_FALLEN_LOG), fallenLog(3));
        register(context, PLACED_YELLOW_BIRCH_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_YELLOW_BIRCH_FALLEN_LOG), fallenLog(3));
        register(context, PLACED_RED_BIRCH_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_RED_BIRCH_FALLEN_LOG), fallenLog(3));
        register(context, PLACED_MAGNOLIA_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_MAGNOLIA_FALLEN_LOG), fallenLog(2));
        register(context, PLACED_ERYTHRINA_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_ERYTHRINA_FALLEN_LOG), fallenLog(4));
        register(context, PLACED_HONEY_LOCUST_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_HONEY_LOCUST_FALLEN_LOG), fallenLog(4));
        register(context, PLACED_WEEPING_WILLOW_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_WEEPING_WILLOW_FALLEN_LOG), fallenLog(3));
        register(context, PLACED_BLACK_WALNUT_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_BLACK_WALNUT_FALLEN_LOG), fallenLog(2));
        register(context, PLACED_PINYON_PINE_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_PINYON_PINE_FALLEN_LOG), fallenLog(3));
        register(context, PLACED_JUNIPER_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_JUNIPER_FALLEN_LOG), fallenLog(3));
        register(context, PLACED_MAPLE_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_MAPLE_FALLEN_LOG), fallenLog(2));
        register(context, PLACED_SHARINGA_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_SHARINGA_FALLEN_LOG), fallenLog(2));
        register(context, PLACED_CORK_OAK_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_CORK_OAK_FALLEN_LOG), fallenLog(3));
        register(context, PLACED_CINNAMON_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_CINNAMON_FALLEN_LOG), fallenLog(2));
        register(context, PLACED_PETRIFIED_CHORUS_FALLEN_LOG, configured.getOrThrow(RankineConfiguredFeatures.CONFIGURED_PETRIFIED_CHORUS_FALLEN_LOG), fallenLog(3));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?,?>> configured, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configured, modifiers));
    }

    private static List<PlacementModifier> fallenLog(int rarity) {
        return Arrays.asList(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, RarityFilter.onAverageOnceEvery(rarity), BiomeFilter.biome());
    }
}
