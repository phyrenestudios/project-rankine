package com.cannolicatfish.rankine.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class StoneReplacerFeatureConfig implements IFeatureConfig {
    public static final Codec<StoneReplacerFeatureConfig> field_236449_a_ = RecordCodecBuilder.create((p_236451_0_) -> {
        return p_236451_0_.group(BlockState.field_235877_b_.fieldOf("target").forGetter((p_236452_0_) -> {
            return p_236452_0_.target;
        }), BlockState.field_235877_b_.fieldOf("state").forGetter((p_236569_0_) -> {
            return p_236569_0_.state;
        }), Codec.INT.fieldOf("bottom_bound").withDefault(0).forGetter((p_236450_0_) -> {
            return p_236450_0_.bottomBound;
        }),Codec.INT.fieldOf("biome_type").withDefault(0).forGetter((p_236450_0_) -> {
            return p_236450_0_.biomeType;
        })).apply(p_236451_0_, StoneReplacerFeatureConfig::new);
    });
    public final BlockState target;
    public final BlockState state;
    public final int bottomBound;
    public final int biomeType;

    public StoneReplacerFeatureConfig(BlockState target, BlockState state, int bottomBound, int topBound) {
        this.target = target;
        this.state = state;
        this.bottomBound = bottomBound;
        this.biomeType = topBound;
    }

}
