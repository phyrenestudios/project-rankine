package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

// PORT-NOTE: the old `IForgeBlock` import is gone. NeoForge's replacement,
// net.neoforged.neoforge.common.extensions.IBlockExtension, is already implemented by BlockBehaviour, so both
// overrides below still resolve without any explicit `implements` clause.
public class RankineLogBlock extends RotatedPillarBlock {
    // RotatedPillarBlock#codec() is concrete but returns RotatedPillarBlock's own CODEC; without this override a
    // codec-driven deserialization would rebuild a plain RotatedPillarBlock instead of a RankineLogBlock.
    public static final MapCodec<RankineLogBlock> CODEC = simpleCodec(RankineLogBlock::new);

    @Override
    public MapCodec<? extends RankineLogBlock> codec() {
        return CODEC;
    }

    public RankineLogBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 5;
    }
}
