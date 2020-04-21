package com.cannolicatfish.rankine.world.feature;

import com.cannolicatfish.rankine.blocks.ModBlocks;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RankineMultiOreFeatureConfig implements IFeatureConfig {
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

    public <T> Dynamic<T> serialize(DynamicOps<T> ops) {
        return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("size"), ops.createInt(this.size), ops.createString("target"), ops.createString(this.target.returnName()), ops.createString("state"), BlockState.serialize(ops, this.state).getValue(), ops.createString("chance"), ops.createFloat(this.chance))));
    }

    public static RankineMultiOreFeatureConfig deserialize(Dynamic<?> p_214641_0_) {
        int i = p_214641_0_.get("size").asInt(0);
        RankineOreFeatureConfig.RankineFillerBlockType rankineorefeatureconfig$fillerblocktype = RankineOreFeatureConfig.RankineFillerBlockType.targetString(p_214641_0_.get("target").asString(""));
        BlockState blockstate = p_214641_0_.get("state").map(BlockState::deserialize).orElse(Blocks.AIR.getDefaultState());
        float f = p_214641_0_.get("chance").asFloat(0.0F);
        return new RankineMultiOreFeatureConfig(rankineorefeatureconfig$fillerblocktype, blockstate, i, f);
    }

}