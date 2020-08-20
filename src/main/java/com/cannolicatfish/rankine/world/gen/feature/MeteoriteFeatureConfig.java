package com.cannolicatfish.rankine.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class MeteoriteFeatureConfig implements IFeatureConfig {
    public static final Codec<MeteoriteFeatureConfig> AAAA = RecordCodecBuilder.create((p_236451_0_) -> {
        return p_236451_0_.group(BlockState.CODEC.fieldOf("state").forGetter((p_236452_0_) -> {
            return p_236452_0_.state;
        }), Codec.INT.fieldOf("start_radius").withDefault(0).forGetter((p_236450_0_) -> {
            return p_236450_0_.startRadius;
        })).apply(p_236451_0_, MeteoriteFeatureConfig::new);
    });
    public final BlockState state;
    public final int startRadius;

    public MeteoriteFeatureConfig(BlockState state, int startRadius) {
        this.state = state;
        this.startRadius = startRadius;
    }
}