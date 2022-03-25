package com.cannolicatfish.rankine.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class ReplacerFeatureConfig implements FeatureConfiguration {
    public static final Codec<ReplacerFeatureConfig> CODEC = RecordCodecBuilder.create((p_236451_0_) -> {
        return p_236451_0_.group(BlockState.CODEC.fieldOf("target").forGetter((p_236452_0_) -> {
            return p_236452_0_.target;
        }), BlockState.CODEC.fieldOf("state").forGetter((p_236569_0_) -> {
            return p_236569_0_.state;
        }), Codec.INT.fieldOf("bottom_bound").orElse(0).forGetter((p_236450_0_) -> {
            return p_236450_0_.bottomBound;
        }),Codec.INT.fieldOf("top_bound").orElse(0).forGetter((p_236450_0_) -> {
            return p_236450_0_.topBound;
        })).apply(p_236451_0_, ReplacerFeatureConfig::new);
    });
    public final BlockState target;
    public final BlockState state;
    public final int bottomBound;
    public final int topBound;

    public ReplacerFeatureConfig(BlockState target, BlockState state, int bottomBound, int topBound) {
        this.target = target;
        this.state = state;
        this.bottomBound = bottomBound;
        this.topBound = topBound;
    }

}
