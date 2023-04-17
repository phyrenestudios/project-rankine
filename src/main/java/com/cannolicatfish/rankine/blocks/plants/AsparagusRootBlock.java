package com.cannolicatfish.rankine.blocks.plants;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class AsparagusRootBlock extends Block implements BonemealableBlock {
    public AsparagusRootBlock(Properties properties) {
        super(properties);
    }



    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        if (worldIn.isEmptyBlock(pos.above())) {
            if (random.nextFloat() < 0.04) {
                worldIn.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
            } else if (worldIn.getRawBrightness(pos.above(), 0) >= 9) {
                this.performBonemeal(worldIn, random, pos, state);
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelIn, BlockPos pos, BlockState state, boolean isClient) {
        return levelIn.getBlockState(pos.above()).isAir();
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
