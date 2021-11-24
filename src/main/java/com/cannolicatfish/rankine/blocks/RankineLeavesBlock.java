package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class RankineLeavesBlock extends LeavesBlock {
    public RankineLeavesBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return !state.get(PERSISTENT);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (random.nextFloat() < Config.GENERAL.LEAF_LITTER_GEN.get() && (worldIn.getBlockState(pos.down()).matchesBlock(Blocks.AIR) || worldIn.getBlockState(pos.down()).isReplaceable(Fluids.WATER))) {
                worldIn.setBlockState(pos.down(), RankineLists.LEAF_LITTERS.get(RankineLists.LEAVES.indexOf(state.getBlock())).getDefaultState(),2);
        }
        super.randomTick(state, worldIn, pos, random);
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 30;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 60;
    }
}
