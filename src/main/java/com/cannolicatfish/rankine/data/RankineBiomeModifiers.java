package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankinePlacedFeatures;
import com.cannolicatfish.rankine.world.modifier.ConfigGatedFeaturesBiomeModifier;
import com.cannolicatfish.rankine.world.modifier.DisableVanillaOresBiomeModifier;
import com.cannolicatfish.rankine.world.modifier.RankineOreBiomeModifier;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;

import java.util.Arrays;
import java.util.List;

/**
 * Biome modifier keys + datagen bootstrap. This is the 1.21 replacement for the whole of
 * {@code world/features/FeatureGeneration#addFeaturesToBiomes} (which used the removed
 * {@code BiomeLoadingEvent}).
 * <p>
 * Two structural changes were forced by the API:
 * <ol>
 *   <li>{@code Biome.BiomeCategory} is gone, so every category test becomes a biome tag
 *       (see {@code WorldgenUtils.CATEGORY_TAGS} for the same mapping used by the config code).</li>
 *   <li>Biome modifier JSON is static, so each config-gated addition uses the custom
 *       {@code rankine:config_gated_add_features} modifier, which re-checks the config gate at
 *       biome-load time. The fully dynamic ore rows and the vanilla-ore stripping get their own
 *       custom modifiers.</li>
 * </ol>
 */
public class RankineBiomeModifiers {

    // --- global / dimension-wide ---
    public static final ResourceKey<BiomeModifier> FLAT_BEDROCK_OVERWORLD = createKey("flat_bedrock_overworld");
    public static final ResourceKey<BiomeModifier> FLAT_BEDROCK_NETHER = createKey("flat_bedrock_nether");
    public static final ResourceKey<BiomeModifier> FLAT_BEDROCK_END = createKey("flat_bedrock_end");
    public static final ResourceKey<BiomeModifier> WORLD_REPLACER_OVERWORLD = createKey("world_replacer_overworld");
    public static final ResourceKey<BiomeModifier> WORLD_REPLACER_NETHER = createKey("world_replacer_nether");
    public static final ResourceKey<BiomeModifier> WORLD_REPLACER_END = createKey("world_replacer_end");
    public static final ResourceKey<BiomeModifier> INTRUSION_OVERWORLD = createKey("intrusion_overworld");
    public static final ResourceKey<BiomeModifier> INTRUSION_NETHER = createKey("intrusion_nether");
    public static final ResourceKey<BiomeModifier> INTRUSION_END = createKey("intrusion_end");

    public static final ResourceKey<BiomeModifier> METEORITE = createKey("meteorite");
    public static final ResourceKey<BiomeModifier> FUMAROLE_OVERWORLD = createKey("fumarole_overworld");
    public static final ResourceKey<BiomeModifier> FUMAROLE_NETHER = createKey("fumarole_nether");
    public static final ResourceKey<BiomeModifier> WALL_MUSHROOMS = createKey("wall_mushrooms");
    public static final ResourceKey<BiomeModifier> PATCH_COBBLES = createKey("patch_cobbles");

    // --- flora (by former BiomeCategory) ---
    public static final ResourceKey<BiomeModifier> FLORA_SAVANNA = createKey("flora_savanna");
    public static final ResourceKey<BiomeModifier> FLORA_FOREST = createKey("flora_forest");
    public static final ResourceKey<BiomeModifier> FLORA_JUNGLE = createKey("flora_jungle");
    public static final ResourceKey<BiomeModifier> FLORA_PLAINS = createKey("flora_plains");
    public static final ResourceKey<BiomeModifier> FLORA_SWAMP = createKey("flora_swamp");
    public static final ResourceKey<BiomeModifier> FLORA_TAIGA = createKey("flora_taiga");
    public static final ResourceKey<BiomeModifier> FLORA_MOUNTAIN = createKey("flora_mountain");
    public static final ResourceKey<BiomeModifier> FLORA_HILL = createKey("flora_hill");

    // --- trees, exact-biome rules ---
    public static final ResourceKey<BiomeModifier> TREES_FOREST_BIOMES = createKey("trees_forest_biomes");
    public static final ResourceKey<BiomeModifier> LOGS_FOREST_BIOMES = createKey("logs_forest_biomes");
    public static final ResourceKey<BiomeModifier> TREES_DARK_FOREST = createKey("trees_dark_forest");
    public static final ResourceKey<BiomeModifier> LOGS_DARK_FOREST = createKey("logs_dark_forest");
    public static final ResourceKey<BiomeModifier> TREES_BEACH = createKey("trees_beach");
    public static final ResourceKey<BiomeModifier> LOGS_BEACH = createKey("logs_beach");
    public static final ResourceKey<BiomeModifier> TREES_GROVE = createKey("trees_grove");
    public static final ResourceKey<BiomeModifier> LOGS_GROVE = createKey("logs_grove");
    public static final ResourceKey<BiomeModifier> TREES_WINDSWEPT_FOREST = createKey("trees_windswept_forest");
    public static final ResourceKey<BiomeModifier> LOGS_WINDSWEPT_FOREST = createKey("logs_windswept_forest");
    public static final ResourceKey<BiomeModifier> TREES_BIRCH_FOREST = createKey("trees_birch_forest");
    public static final ResourceKey<BiomeModifier> LOGS_BIRCH_FOREST = createKey("logs_birch_forest");
    public static final ResourceKey<BiomeModifier> TREES_TAIGA_BIOMES = createKey("trees_taiga_biomes");
    public static final ResourceKey<BiomeModifier> LOGS_TAIGA_BIOMES = createKey("logs_taiga_biomes");
    public static final ResourceKey<BiomeModifier> TREES_OLD_GROWTH_TAIGA = createKey("trees_old_growth_taiga");
    public static final ResourceKey<BiomeModifier> LOGS_OLD_GROWTH_TAIGA = createKey("logs_old_growth_taiga");

    // --- trees, by former BiomeCategory ---
    public static final ResourceKey<BiomeModifier> TREES_CAT_TAIGA = createKey("trees_cat_taiga");
    public static final ResourceKey<BiomeModifier> LOGS_CAT_TAIGA = createKey("logs_cat_taiga");
    public static final ResourceKey<BiomeModifier> TREES_CAT_PLAINS = createKey("trees_cat_plains");
    public static final ResourceKey<BiomeModifier> LOGS_CAT_PLAINS = createKey("logs_cat_plains");
    public static final ResourceKey<BiomeModifier> TREES_CAT_SAVANNA = createKey("trees_cat_savanna");
    public static final ResourceKey<BiomeModifier> LOGS_CAT_SAVANNA = createKey("logs_cat_savanna");
    public static final ResourceKey<BiomeModifier> TREES_CAT_MESA = createKey("trees_cat_mesa");
    public static final ResourceKey<BiomeModifier> LOGS_CAT_MESA = createKey("logs_cat_mesa");
    public static final ResourceKey<BiomeModifier> TREES_CAT_SWAMP = createKey("trees_cat_swamp");
    public static final ResourceKey<BiomeModifier> LOGS_CAT_SWAMP = createKey("logs_cat_swamp");
    public static final ResourceKey<BiomeModifier> TREES_CAT_RIVER = createKey("trees_cat_river");
    public static final ResourceKey<BiomeModifier> LOGS_CAT_RIVER = createKey("logs_cat_river");
    public static final ResourceKey<BiomeModifier> TREES_CAT_JUNGLE = createKey("trees_cat_jungle");
    public static final ResourceKey<BiomeModifier> LOGS_CAT_JUNGLE = createKey("logs_cat_jungle");

    // --- end ---
    public static final ResourceKey<BiomeModifier> END_METEORITE = createKey("end_meteorite");
    public static final ResourceKey<BiomeModifier> END_PETRIFIED_CHORUS = createKey("end_petrified_chorus");
    public static final ResourceKey<BiomeModifier> END_ANTIMATTER = createKey("end_antimatter");

    // --- custom-logic modifiers ---
    public static final ResourceKey<BiomeModifier> ORE_SETTINGS = createKey("ore_settings");
    public static final ResourceKey<BiomeModifier> DISABLE_VANILLA_ORES = createKey("disable_vanilla_ores");

    private static ResourceKey<BiomeModifier> createKey(String name) {
        return ResourceKey.create(net.neoforged.neoforge.registries.NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, name));
    }

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placed = context.lookup(Registries.PLACED_FEATURE);

        HolderSet<Biome> overworld = biomes.getOrThrow(Tags.Biomes.IS_OVERWORLD);
        HolderSet<Biome> nether = biomes.getOrThrow(BiomeTags.IS_NETHER);
        HolderSet<Biome> end = biomes.getOrThrow(BiomeTags.IS_END);
        // 1.18.2 checked these four End biomes by name, not the whole End.
        HolderSet<Biome> outerEnd = HolderSet.direct(
                biomes.getOrThrow(Biomes.END_HIGHLANDS), biomes.getOrThrow(Biomes.END_MIDLANDS),
                biomes.getOrThrow(Biomes.END_BARRENS), biomes.getOrThrow(Biomes.SMALL_END_ISLANDS));

        // --- unconditional-biome, config-gated global features ---
        // (1.18.2 added these to every biome whose name was non-null, i.e. all three dimensions)
        add(context, FLAT_BEDROCK_OVERWORLD, overworld, GenerationStep.Decoration.UNDERGROUND_DECORATION, "bedrock_layers", placed, RankinePlacedFeatures.PLACED_FLAT_BEDROCK);
        add(context, FLAT_BEDROCK_NETHER, nether, GenerationStep.Decoration.UNDERGROUND_DECORATION, "bedrock_layers", placed, RankinePlacedFeatures.PLACED_FLAT_BEDROCK);
        add(context, FLAT_BEDROCK_END, end, GenerationStep.Decoration.UNDERGROUND_DECORATION, "bedrock_layers", placed, RankinePlacedFeatures.PLACED_FLAT_BEDROCK);
        add(context, WORLD_REPLACER_OVERWORLD, overworld, GenerationStep.Decoration.UNDERGROUND_DECORATION, "layer_gen", placed, RankinePlacedFeatures.PLACED_WORLD_REPLACER);
        add(context, WORLD_REPLACER_NETHER, nether, GenerationStep.Decoration.UNDERGROUND_DECORATION, "layer_gen", placed, RankinePlacedFeatures.PLACED_WORLD_REPLACER);
        add(context, WORLD_REPLACER_END, end, GenerationStep.Decoration.UNDERGROUND_DECORATION, "layer_gen", placed, RankinePlacedFeatures.PLACED_WORLD_REPLACER);
        add(context, INTRUSION_OVERWORLD, overworld, GenerationStep.Decoration.UNDERGROUND_DECORATION, "intrusion", placed, RankinePlacedFeatures.PLACED_INTRUSION);
        add(context, INTRUSION_NETHER, nether, GenerationStep.Decoration.UNDERGROUND_DECORATION, "intrusion", placed, RankinePlacedFeatures.PLACED_INTRUSION);
        add(context, INTRUSION_END, end, GenerationStep.Decoration.UNDERGROUND_DECORATION, "intrusion", placed, RankinePlacedFeatures.PLACED_INTRUSION);

        add(context, METEORITE, overworld, GenerationStep.Decoration.LOCAL_MODIFICATIONS, "meteorite", placed, RankinePlacedFeatures.PLACED_METEORITE);
        add(context, FUMAROLE_OVERWORLD, overworld, GenerationStep.Decoration.LOCAL_MODIFICATIONS, "fumarole", placed, RankinePlacedFeatures.PLACED_FUMAROLE);
        add(context, FUMAROLE_NETHER, nether, GenerationStep.Decoration.LOCAL_MODIFICATIONS, "fumarole", placed, RankinePlacedFeatures.PLACED_FUMAROLE);
        add(context, WALL_MUSHROOMS, overworld, GenerationStep.Decoration.VEGETAL_DECORATION, "mushrooms", placed, RankinePlacedFeatures.PLACED_WALL_MUSHROOMS);
        add(context, PATCH_COBBLES, overworld, GenerationStep.Decoration.LOCAL_MODIFICATIONS, "cobbles", placed, RankinePlacedFeatures.PLACED_PATCH_COBBLES);

        // --- flora ---
        addTag(context, FLORA_SAVANNA, biomes, BiomeTags.IS_SAVANNA, GenerationStep.Decoration.VEGETAL_DECORATION, "flora", placed,
                RankinePlacedFeatures.PLACED_PATCH_SAVANNA_FLOWERS, RankinePlacedFeatures.PLACED_PATCH_SAVANNA_PLANTS);
        addTag(context, FLORA_FOREST, biomes, BiomeTags.IS_FOREST, GenerationStep.Decoration.VEGETAL_DECORATION, "flora", placed,
                RankinePlacedFeatures.PLACED_PATCH_LILIES, RankinePlacedFeatures.PLACED_PATCH_FOREST_PLANTS);
        addTag(context, FLORA_JUNGLE, biomes, BiomeTags.IS_JUNGLE, GenerationStep.Decoration.VEGETAL_DECORATION, "flora", placed,
                RankinePlacedFeatures.PLACED_PATCH_MORNING_GLORIES, RankinePlacedFeatures.PLACED_PATCH_JUNGLE_PLANTS);
        addTag(context, FLORA_PLAINS, biomes, Tags.Biomes.IS_PLAINS, GenerationStep.Decoration.VEGETAL_DECORATION, "flora", placed,
                RankinePlacedFeatures.PLACED_PATCH_PLAINS_PLANTS);
        addTag(context, FLORA_SWAMP, biomes, Tags.Biomes.IS_SWAMP, GenerationStep.Decoration.VEGETAL_DECORATION, "flora", placed,
                RankinePlacedFeatures.PLACED_PATCH_SWAMP_PLANTS);
        addTag(context, FLORA_TAIGA, biomes, BiomeTags.IS_TAIGA, GenerationStep.Decoration.VEGETAL_DECORATION, "flora", placed,
                RankinePlacedFeatures.PLACED_PATCH_TAIGA_PLANTS);
        addTag(context, FLORA_MOUNTAIN, biomes, BiomeTags.IS_MOUNTAIN, GenerationStep.Decoration.VEGETAL_DECORATION, "flora", placed,
                RankinePlacedFeatures.PLACED_PATCH_MOUNTAIN_PLANTS);
        addTag(context, FLORA_HILL, biomes, BiomeTags.IS_HILL, GenerationStep.Decoration.VEGETAL_DECORATION, "flora", placed,
                RankinePlacedFeatures.PLACED_PATCH_MOUNTAIN_PLANTS);

        // --- trees keyed on exact biomes ---
        HolderSet<Biome> forestBiomes = HolderSet.direct(biomes.getOrThrow(Biomes.FOREST), biomes.getOrThrow(Biomes.FLOWER_FOREST));
        add(context, TREES_FOREST_BIOMES, forestBiomes, GenerationStep.Decoration.VEGETAL_DECORATION, "trees", placed, RankinePlacedFeatures.PLACED_MAPLE_TREE);
        add(context, LOGS_FOREST_BIOMES, forestBiomes, GenerationStep.Decoration.VEGETAL_DECORATION, "fallen_logs", placed,
                RankinePlacedFeatures.PLACED_MAPLE_FALLEN_LOG, RankinePlacedFeatures.PLACED_OAK_FALLEN_LOG);

        HolderSet<Biome> darkForest = HolderSet.direct(biomes.getOrThrow(Biomes.DARK_FOREST));
        add(context, TREES_DARK_FOREST, darkForest, GenerationStep.Decoration.VEGETAL_DECORATION, "trees", placed, RankinePlacedFeatures.PLACED_BLACK_WALNUT_TREE);
        add(context, LOGS_DARK_FOREST, darkForest, GenerationStep.Decoration.VEGETAL_DECORATION, "fallen_logs", placed,
                RankinePlacedFeatures.PLACED_BLACK_WALNUT_FALLEN_LOG, RankinePlacedFeatures.PLACED_DARK_OAK_FALLEN_LOG);

        HolderSet<Biome> beach = HolderSet.direct(biomes.getOrThrow(Biomes.BEACH));
        add(context, TREES_BEACH, beach, GenerationStep.Decoration.VEGETAL_DECORATION, "trees", placed, RankinePlacedFeatures.PLACED_COCONUT_PALM_TREE);
        add(context, LOGS_BEACH, beach, GenerationStep.Decoration.VEGETAL_DECORATION, "fallen_logs", placed, RankinePlacedFeatures.PLACED_COCONUT_PALM_FALLEN_LOG);

        HolderSet<Biome> grove = HolderSet.direct(biomes.getOrThrow(Biomes.GROVE));
        add(context, TREES_GROVE, grove, GenerationStep.Decoration.VEGETAL_DECORATION, "trees", placed, RankinePlacedFeatures.PLACED_SNOWY_SHORT_BALSAM_FIR_TREE);
        add(context, LOGS_GROVE, grove, GenerationStep.Decoration.VEGETAL_DECORATION, "fallen_logs", placed, RankinePlacedFeatures.PLACED_BALSAM_FIR_FALLEN_LOG);

        HolderSet<Biome> windsweptForest = HolderSet.direct(biomes.getOrThrow(Biomes.WINDSWEPT_FOREST));
        add(context, TREES_WINDSWEPT_FOREST, windsweptForest, GenerationStep.Decoration.VEGETAL_DECORATION, "trees", placed,
                RankinePlacedFeatures.PLACED_BALSAM_FIR_TREE, RankinePlacedFeatures.PLACED_SHORT_BALSAM_FIR_TREE, RankinePlacedFeatures.PLACED_RED_BIRCH_TREE);
        add(context, LOGS_WINDSWEPT_FOREST, windsweptForest, GenerationStep.Decoration.VEGETAL_DECORATION, "fallen_logs", placed,
                RankinePlacedFeatures.PLACED_BALSAM_FIR_FALLEN_LOG, RankinePlacedFeatures.PLACED_RED_BIRCH_FALLEN_LOG);

        HolderSet<Biome> birchForest = HolderSet.direct(biomes.getOrThrow(Biomes.BIRCH_FOREST), biomes.getOrThrow(Biomes.OLD_GROWTH_BIRCH_FOREST));
        add(context, TREES_BIRCH_FOREST, birchForest, GenerationStep.Decoration.VEGETAL_DECORATION, "trees", placed,
                RankinePlacedFeatures.PLACED_YELLOW_BIRCH_TREE, RankinePlacedFeatures.PLACED_MODIFIED_BIRCH_TREE, RankinePlacedFeatures.PLACED_BLACK_BIRCH_TREE);
        add(context, LOGS_BIRCH_FOREST, birchForest, GenerationStep.Decoration.VEGETAL_DECORATION, "fallen_logs", placed,
                RankinePlacedFeatures.PLACED_YELLOW_BIRCH_FALLEN_LOG, RankinePlacedFeatures.PLACED_BLACK_BIRCH_FALLEN_LOG, RankinePlacedFeatures.PLACED_BIRCH_FALLEN_LOG);

        HolderSet<Biome> taigaBiomes = HolderSet.direct(biomes.getOrThrow(Biomes.TAIGA), biomes.getOrThrow(Biomes.SNOWY_TAIGA));
        add(context, TREES_TAIGA_BIOMES, taigaBiomes, GenerationStep.Decoration.VEGETAL_DECORATION, "trees", placed, RankinePlacedFeatures.PLACED_EASTERN_HEMLOCK_TREE);
        add(context, LOGS_TAIGA_BIOMES, taigaBiomes, GenerationStep.Decoration.VEGETAL_DECORATION, "fallen_logs", placed, RankinePlacedFeatures.PLACED_EASTERN_HEMLOCK_FALLEN_LOG);

        HolderSet<Biome> oldGrowthTaiga = HolderSet.direct(biomes.getOrThrow(Biomes.OLD_GROWTH_SPRUCE_TAIGA), biomes.getOrThrow(Biomes.OLD_GROWTH_PINE_TAIGA));
        add(context, TREES_OLD_GROWTH_TAIGA, oldGrowthTaiga, GenerationStep.Decoration.VEGETAL_DECORATION, "trees", placed, RankinePlacedFeatures.PLACED_WESTERN_HEMLOCK_TREE);
        add(context, LOGS_OLD_GROWTH_TAIGA, oldGrowthTaiga, GenerationStep.Decoration.VEGETAL_DECORATION, "fallen_logs", placed, RankinePlacedFeatures.PLACED_WESTERN_HEMLOCK_FALLEN_LOG);

        // --- trees keyed on former BiomeCategory ---
        addTag(context, TREES_CAT_TAIGA, biomes, BiomeTags.IS_TAIGA, GenerationStep.Decoration.VEGETAL_DECORATION, "trees", placed, RankinePlacedFeatures.PLACED_CEDAR_TREE);
        addTag(context, LOGS_CAT_TAIGA, biomes, BiomeTags.IS_TAIGA, GenerationStep.Decoration.VEGETAL_DECORATION, "fallen_logs", placed,
                RankinePlacedFeatures.PLACED_CEDAR_FALLEN_LOG, RankinePlacedFeatures.PLACED_SPRUCE_FALLEN_LOG);
        addTag(context, TREES_CAT_PLAINS, biomes, Tags.Biomes.IS_PLAINS, GenerationStep.Decoration.VEGETAL_DECORATION, "trees", placed, RankinePlacedFeatures.PLACED_HONEY_LOCUST_TREE);
        addTag(context, LOGS_CAT_PLAINS, biomes, Tags.Biomes.IS_PLAINS, GenerationStep.Decoration.VEGETAL_DECORATION, "fallen_logs", placed, RankinePlacedFeatures.PLACED_HONEY_LOCUST_FALLEN_LOG);
        addTag(context, TREES_CAT_SAVANNA, biomes, BiomeTags.IS_SAVANNA, GenerationStep.Decoration.VEGETAL_DECORATION, "trees", placed,
                RankinePlacedFeatures.PLACED_JUNIPER_TREE, RankinePlacedFeatures.PLACED_PINYON_PINE_TREE, RankinePlacedFeatures.PLACED_CORK_OAK_TREE);
        addTag(context, LOGS_CAT_SAVANNA, biomes, BiomeTags.IS_SAVANNA, GenerationStep.Decoration.VEGETAL_DECORATION, "fallen_logs", placed,
                RankinePlacedFeatures.PLACED_CORK_OAK_FALLEN_LOG, RankinePlacedFeatures.PLACED_JUNIPER_FALLEN_LOG,
                RankinePlacedFeatures.PLACED_PINYON_PINE_FALLEN_LOG, RankinePlacedFeatures.PLACED_ACACIA_FALLEN_LOG);
        addTag(context, TREES_CAT_MESA, biomes, BiomeTags.IS_BADLANDS, GenerationStep.Decoration.VEGETAL_DECORATION, "trees", placed, RankinePlacedFeatures.PLACED_ERYTHRINA_TREE);
        addTag(context, LOGS_CAT_MESA, biomes, BiomeTags.IS_BADLANDS, GenerationStep.Decoration.VEGETAL_DECORATION, "fallen_logs", placed, RankinePlacedFeatures.PLACED_ERYTHRINA_FALLEN_LOG);
        addTag(context, TREES_CAT_SWAMP, biomes, Tags.Biomes.IS_SWAMP, GenerationStep.Decoration.VEGETAL_DECORATION, "trees", placed, RankinePlacedFeatures.PLACED_WEEPING_WILLOW_TREE);
        addTag(context, LOGS_CAT_SWAMP, biomes, Tags.Biomes.IS_SWAMP, GenerationStep.Decoration.VEGETAL_DECORATION, "fallen_logs", placed, RankinePlacedFeatures.PLACED_WEEPING_WILLOW_FALLEN_LOG);
        addTag(context, TREES_CAT_RIVER, biomes, BiomeTags.IS_RIVER, GenerationStep.Decoration.VEGETAL_DECORATION, "trees", placed,
                RankinePlacedFeatures.PLACED_BLACK_BIRCH_TREE, RankinePlacedFeatures.PLACED_MAGNOLIA_TREE);
        addTag(context, LOGS_CAT_RIVER, biomes, BiomeTags.IS_RIVER, GenerationStep.Decoration.VEGETAL_DECORATION, "fallen_logs", placed, RankinePlacedFeatures.PLACED_MAGNOLIA_FALLEN_LOG);
        addTag(context, TREES_CAT_JUNGLE, biomes, BiomeTags.IS_JUNGLE, GenerationStep.Decoration.VEGETAL_DECORATION, "trees", placed,
                RankinePlacedFeatures.PLACED_SHARINGA_TREE, RankinePlacedFeatures.PLACED_CINNAMON_TREE);
        addTag(context, LOGS_CAT_JUNGLE, biomes, BiomeTags.IS_JUNGLE, GenerationStep.Decoration.VEGETAL_DECORATION, "fallen_logs", placed,
                RankinePlacedFeatures.PLACED_SHARINGA_FALLEN_LOG, RankinePlacedFeatures.PLACED_CINNAMON_FALLEN_LOG, RankinePlacedFeatures.PLACED_JUNGLE_FALLEN_LOG);

        // --- End ---
        add(context, END_METEORITE, outerEnd, GenerationStep.Decoration.UNDERGROUND_DECORATION, "end_meteorite", placed, RankinePlacedFeatures.PLACED_END_METEORITE);
        add(context, END_PETRIFIED_CHORUS, outerEnd, GenerationStep.Decoration.VEGETAL_DECORATION, "end_meteorite_trees", placed, RankinePlacedFeatures.PLACED_PETRIFIED_CHORUS_TREE);
        add(context, END_ANTIMATTER, outerEnd, GenerationStep.Decoration.UNDERGROUND_DECORATION, "antimatter", placed, RankinePlacedFeatures.PLACED_ANTIMATTER_BLOB);

        // --- fully dynamic / inspection-based ---
        context.register(ORE_SETTINGS, new RankineOreBiomeModifier());
        context.register(DISABLE_VANILLA_ORES, new DisableVanillaOresBiomeModifier());
    }

    @SafeVarargs
    private static void add(BootstrapContext<BiomeModifier> context, ResourceKey<BiomeModifier> key, HolderSet<Biome> biomes,
                            GenerationStep.Decoration step, String gate, HolderGetter<PlacedFeature> placed,
                            ResourceKey<PlacedFeature>... features) {
        context.register(key, new ConfigGatedFeaturesBiomeModifier(biomes, features(placed, features), step, gate));
    }

    @SafeVarargs
    private static void addTag(BootstrapContext<BiomeModifier> context, ResourceKey<BiomeModifier> key, HolderGetter<Biome> biomes,
                               TagKey<Biome> tag, GenerationStep.Decoration step, String gate, HolderGetter<PlacedFeature> placed,
                               ResourceKey<PlacedFeature>... features) {
        add(context, key, biomes.getOrThrow(tag), step, gate, placed, features);
    }

    @SafeVarargs
    private static HolderSet<PlacedFeature> features(HolderGetter<PlacedFeature> placed, ResourceKey<PlacedFeature>... keys) {
        List<Holder<PlacedFeature>> holders = Arrays.stream(keys).<Holder<PlacedFeature>>map(placed::getOrThrow).toList();
        return HolderSet.direct(holders);
    }
}
