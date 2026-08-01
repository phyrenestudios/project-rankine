package com.cannolicatfish.rankine.world.modifier;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBiomeModifierTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeGenerationSettingsBuilder;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Removes the vanilla stone/ore features from UNDERGROUND_ORES when
 * {@code Config.WORLDGEN.DISABLE_VANILLA_FEATURES} is on.
 * <p>
 * PORT-NOTE: 1:1 port of {@code FeatureGeneration#disableGenerators} / {@code findOreConfig}.
 * {@code neoforge:remove_features} cannot express this: the old code matched features by inspecting
 * their {@code FeatureConfiguration} target block state rather than by placed-feature id, so it also
 * caught modded/datapack features that generate those same states.
 */
public record DisableVanillaOresBiomeModifier() implements BiomeModifier {

    public static final MapCodec<DisableVanillaOresBiomeModifier> CODEC = MapCodec.unit(DisableVanillaOresBiomeModifier::new);

    private static final List<BlockState> DISABLE = Arrays.asList(
            Blocks.ANDESITE.defaultBlockState(), Blocks.DIORITE.defaultBlockState(), Blocks.GRANITE.defaultBlockState(),
            Blocks.COPPER_ORE.defaultBlockState(), Blocks.IRON_ORE.defaultBlockState(), Blocks.COAL_ORE.defaultBlockState(),
            Blocks.GOLD_ORE.defaultBlockState(), Blocks.REDSTONE_ORE.defaultBlockState(), Blocks.LAPIS_ORE.defaultBlockState(),
            Blocks.DIAMOND_ORE.defaultBlockState(), Blocks.EMERALD_ORE.defaultBlockState(),
            Blocks.DEEPSLATE_COPPER_ORE.defaultBlockState(), Blocks.DEEPSLATE_IRON_ORE.defaultBlockState(),
            Blocks.DEEPSLATE_COAL_ORE.defaultBlockState(), Blocks.DEEPSLATE_GOLD_ORE.defaultBlockState(),
            Blocks.DEEPSLATE_REDSTONE_ORE.defaultBlockState(), Blocks.DEEPSLATE_LAPIS_ORE.defaultBlockState(),
            Blocks.DEEPSLATE_DIAMOND_ORE.defaultBlockState(), Blocks.DEEPSLATE_EMERALD_ORE.defaultBlockState());

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase != Phase.REMOVE) return;
        if (!Config.WORLDGEN.DISABLE_VANILLA_FEATURES.get()) return;

        BiomeGenerationSettingsBuilder generationSettings = builder.getGenerationSettings();
        List<Holder<PlacedFeature>> features = generationSettings.getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);
        final List<Holder<PlacedFeature>> drain = new ArrayList<>();
        features.forEach(feature -> findOreConfig(feature).ifPresent(ore -> {
            if (DISABLE.contains(ore)) {
                drain.add(feature);
            }
        }));
        features.removeAll(drain);
    }

    private static Optional<BlockState> findOreConfig(Holder<PlacedFeature> feature) {
        ConfiguredFeature<?, ?> te = feature.value().feature().value();
        final FeatureConfiguration config = te.config();
        if (config instanceof OreConfiguration) {
            return Optional.of(((OreConfiguration) config).targetStates.get(0).state);
        } else if (config instanceof SimpleBlockConfiguration) {
            return Optional.of(((SimpleBlockConfiguration) config).toPlace().getState(RandomSource.create(), BlockPos.ZERO));
        } else if (config instanceof ReplaceBlockConfiguration) {
            return Optional.of(((ReplaceBlockConfiguration) config).targetStates.get(0).state);
        }
        return Optional.empty();
    }

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return RankineBiomeModifierTypes.DISABLE_VANILLA_ORES.get();
    }
}
