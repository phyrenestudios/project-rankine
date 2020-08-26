package com.cannolicatfish.rankine.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class RankineMultiOreFeatureConfig implements IFeatureConfig {
    public static final Codec<RankineMultiOreFeatureConfig> field_236566_a_ = RecordCodecBuilder.create((p_236568_0_) -> {
        return p_236568_0_.group(RankineOreFeatureConfig.RankineFillerBlockType.field_236571_d_.fieldOf("target").forGetter((p_236570_0_) -> {
            return p_236570_0_.target;
        }), BlockState.CODEC.fieldOf("state").forGetter((p_236569_0_) -> {
            return p_236569_0_.state;
        }), Codec.INT.fieldOf("size").withDefault(0).forGetter((p_236567_0_) -> {
            return p_236567_0_.size;
        }), Codec.FLOAT.fieldOf("chance").withDefault(0f).forGetter((p_236567_0_) -> {
            return p_236567_0_.chance;
        })).apply(p_236568_0_, RankineMultiOreFeatureConfig::new);
    });
    public final RankineOreFeatureConfig.RankineFillerBlockType target;
    public final int size;
    public final BlockState state;
    public final float chance;

    public RankineMultiOreFeatureConfig(RankineOreFeatureConfig.RankineFillerBlockType target, BlockState state, int size, float chance) {
        this.size = size;
        this.state = state;
        this.target = target;
        this.chance = chance;
    }

}