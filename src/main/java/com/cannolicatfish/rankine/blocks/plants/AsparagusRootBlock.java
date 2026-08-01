package com.cannolicatfish.rankine.blocks.plants;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class AsparagusRootBlock extends Block implements BonemealableBlock {
    public AsparagusRootBlock(Properties properties) {
        super(properties);
    }



    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        if (worldIn.isEmptyBlock(pos.above())) {
            if (random.nextFloat() < 0.04) {
                worldIn.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
            } else if (worldIn.getRawBrightness(pos.above(), 0) >= 9) {
                this.performBonemeal(worldIn, random, pos, state);
                net.neoforged.neoforge.common.CommonHooks.fireCropGrowPost(worldIn, pos, state);
            }
        }
    }


    @Override
    public boolean isValidBonemealTarget(LevelReader worldIn, BlockPos pos, BlockState state) {
        return worldIn.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        worldIn.setBlockAndUpdate(pos.above(), RankineBlocks.ASPARAGUS_PLANT.get().defaultBlockState());
    }
}
