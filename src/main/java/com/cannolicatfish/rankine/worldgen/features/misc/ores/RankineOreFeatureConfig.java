package com.cannolicatfish.rankine.worldgen.features.misc.ores;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.List;

public class RankineOreFeatureConfig implements FeatureConfiguration {
    public static final Codec<RankineOreFeatureConfig> CODEC = RecordCodecBuilder.create((p_67849_) -> {
        return p_67849_.group(
            Codec.list(RankineOreFeatureConfig.TargetBlockState.CODEC).fieldOf("targets").forGetter((p_161027_) -> {
            return p_161027_.targetStates;
        }), Codec.intRange(0, 64).fieldOf("size").forGetter((p_161025_) -> {
            return p_161025_.size;
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("discard_chance_on_air_exposure").forGetter((p_161020_) -> {
            return p_161020_.discardChanceOnAirExposure;
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("density").orElse(0.0F).forGetter((p_236567_0_) -> {
            return p_236567_0_.density;
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("chance").orElse(0.0F).forGetter((p_236567_0_) -> {
            return p_236567_0_.chance;
        })).apply(p_67849_, RankineOreFeatureConfig::new);
    });

    public final List<RankineOreFeatureConfig.TargetBlockState> targetStates;
    public final int size;
    public final float discardChanceOnAirExposure;
    public final float density;
    public final float chance;

    public RankineOreFeatureConfig(List<RankineOreFeatureConfig.TargetBlockState> targets, int size, float discardChance, float density, float spawnChance) {
        this.size = size;
        this.density = density;
        this.chance = spawnChance;
        this.targetStates = targets;
        this.discardChanceOnAirExposure = discardChance;
    }

    public RankineOreFeatureConfig(RuleTest test, BlockState state, int size, float discardChance, float density, float spawnChance) {
        this(ImmutableList.of(new RankineOreFeatureConfig.TargetBlockState(test, state)), size, discardChance, density, spawnChance);
    }

    public static RankineOreFeatureConfig.TargetBlockState target(RuleTest p_161022_, BlockState p_161023_) {
        return new RankineOreFeatureConfig.TargetBlockState(p_161022_, p_161023_);
    }

    public static class TargetBlockState {
        public static final Codec<RankineOreFeatureConfig.TargetBlockState> CODEC = RecordCodecBuilder.create((p_161039_) -> {
            return p_161039_.group(RuleTest.CODEC.fieldOf("target").forGetter((p_161043_) -> {
                return p_161043_.target;
            }), BlockState.CODEC.fieldOf("state").forGetter((p_161041_) -> {
                return p_161041_.state;
            })).apply(p_161039_, RankineOreFeatureConfig.TargetBlockState::new);
        });
        public final RuleTest target;
        public final BlockState state;

        TargetBlockState(RuleTest p_161036_, BlockState p_161037_) {
            this.target = p_161036_;
            this.state = p_161037_;
        }
    }



}