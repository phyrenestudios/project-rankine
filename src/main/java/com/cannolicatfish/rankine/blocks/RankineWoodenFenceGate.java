package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.properties.WoodType;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

// PORT-TODO(RankineWoodenFenceGate): FenceGateBlock no longer has a Properties-only constructor; it now requires
// a WoodType (which also supplies open/close sound events). Ported to WoodType.OAK, matching the original
// wooden fence gate's vanilla oak sounds 1:1.
public class RankineWoodenFenceGate extends FenceGateBlock {
    public static final MapCodec<RankineWoodenFenceGate> CODEC = simpleCodec(RankineWoodenFenceGate::new);

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec<FenceGateBlock> codec() {
        return (MapCodec<FenceGateBlock>) (MapCodec<?>) CODEC;
    }

    public RankineWoodenFenceGate(Properties properties) {
        super(WoodType.OAK, properties);
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 20;
    }
}

