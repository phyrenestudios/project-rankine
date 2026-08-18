package com.cannolicatfish.rankine.world.modifier;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBiomeModifierTypes;
import com.cannolicatfish.rankine.init.RankineFeatures;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.cannolicatfish.rankine.world.gen.ores.RankineOreFeatureConfig;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.common.world.BiomeGenerationSettingsBuilder;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

import java.util.Arrays;
import java.util.List;

/**
 * Adds the fully config-driven Rankine ore veins ({@code Config.BIOME_GEN.ORE_SETTINGS}) to biomes.
 * <p>
 * PORT-NOTE: this replaces the "---Ore Settings---" loop at the bottom of the 1.18.2
 * {@code FeatureGeneration#addFeaturesToBiomes}. Because the entire vein definition (block, vein
 * shape, height band, size, density, count, chance) plus the biome selection come from the config
 * file, this cannot be expressed as static {@code neoforge:add_features} JSON — the modifier builds
 * inline {@code Holder.direct} placed features exactly as the old event handler did.
 * <p>
 * Biome selection semantics preserved from 1.18.2:
 * <ul>
 *   <li>empty first entry -> the whole row is skipped (and, as in 1.18.2, iteration stops);</li>
 *   <li>{@code "overworld"} -> every biome that is not Nether and not End;</li>
 *   <li>{@code "ns:path"} -> that exact biome;</li>
 *   <li>a bare word -> the old {@code BiomeCategory}, resolved by
 *       {@link WorldgenUtils#matchesCategory} (the same test {@code ensureBiomesResolved} uses for
 *       the biomeSettings rows, so the two cannot drift).</li>
 * </ul>
 */
public record RankineOreBiomeModifier() implements BiomeModifier {

    public static final MapCodec<RankineOreBiomeModifier> CODEC = MapCodec.unit(RankineOreBiomeModifier::new);

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase != Phase.ADD) return;
        BiomeGenerationSettingsBuilder generationSettings = builder.getGenerationSettings();

        for (List<Object> L : Config.BIOME_GEN.ORE_SETTINGS.get()) {
            Block oreBlock = BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse((String) L.get(0)));
            if (oreBlock == null) break;
            List<String> biomeList = (List<String>) L.get(1);
            if (biomeList.isEmpty() || biomeList.get(0).isEmpty()) {
                break;
            }
            if (!matches(biome, biomeList)) {
                continue;
            }

            String veinType = (String) L.get(2);
            String distributionType = (String) L.get(3);
            int minHeight = (int) L.get(4);
            int maxHeight = (int) L.get(5);
            int size = (int) L.get(6);
            float density = (float) (double) L.get(7);
            int count = (int) L.get(8);
            float spawnChance = (float) (double) L.get(9);
            float discardChance = (float) (double) L.get(10);

            Feature<RankineOreFeatureConfig> veinTypeFeature;
            if (veinType.equals("sphere")) {
                veinTypeFeature = RankineFeatures.SPHERICAL_ORE_VEIN.get();
            } else if (veinType.equals("disk")) {
                veinTypeFeature = RankineFeatures.DISK_ORE_VEIN.get();
            } else {
                veinTypeFeature = RankineFeatures.DEFAULT_ORE_VEIN.get();
            }

            PlacementModifier heightPlacement = distributionType.equals("triangle")
                    ? HeightRangePlacement.triangle(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight))
                    : HeightRangePlacement.uniform(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight));

            Holder<ConfiguredFeature<?, ?>> configured = Holder.direct(new ConfiguredFeature<>(veinTypeFeature,
                    new RankineOreFeatureConfig(new TagMatchTest(RankineTags.Blocks.RANKINE_ORE_REPLACEABLES), oreBlock.defaultBlockState(), size, discardChance, density, spawnChance)));
            Holder<PlacedFeature> oreFeature = Holder.direct(new PlacedFeature(configured,
                    List.of(CountPlacement.of(count), InSquarePlacement.spread(), heightPlacement, BiomeFilter.biome())));

            generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, oreFeature);
        }
    }

    private static boolean matches(Holder<Biome> biome, List<String> biomeList) {
        if (biomeList.get(0).equals("overworld")) {
            return WorldgenUtils.isOverworld(biome);
        }
        for (String b : biomeList) {
            if (Arrays.asList(b.split(":")).size() > 1) {
                ResourceLocation rl = ResourceLocation.tryParse(b);
                if (rl != null && rl.equals(WorldgenUtils.biomeLocation(biome))) return true;
            } else if (WorldgenUtils.matchesCategory(biome, b)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return RankineBiomeModifierTypes.ORE_SETTINGS.get();
    }
}
