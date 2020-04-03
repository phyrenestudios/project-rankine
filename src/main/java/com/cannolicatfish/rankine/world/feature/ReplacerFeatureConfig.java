package com.cannolicatfish.rankine.world.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;

public class ReplacerFeatureConfig implements IFeatureConfig {
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

    @Override
    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
        return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("target"), BlockState.serialize(ops, this.target).getValue(), ops.createString("state"), BlockState.serialize(ops, this.state).getValue(),ops.createString("bottom_bound"), ops.createInt(this.bottomBound), ops.createString("top_bound"), ops.createInt(this.topBound))));
    }

    public static ReplacerFeatureConfig deserialize(Dynamic<?> p_214733_0_) {
        BlockState blockstate = p_214733_0_.get("target").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
        BlockState blockstate1 = p_214733_0_.get("state").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
        int j = p_214733_0_.get("bottom_bound").asInt(0);
        int k = p_214733_0_.get("top_bound").asInt(0);
        return new ReplacerFeatureConfig(blockstate, blockstate1, j,k);
    }
}
