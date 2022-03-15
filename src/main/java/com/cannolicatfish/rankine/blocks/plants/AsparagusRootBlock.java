package com.cannolicatfish.rankine.blocks.plants;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;

import java.util.Random;

import net.minecraft.block.AbstractBlock.Properties;

public class AsparagusRootBlock extends Block implements IGrowable {
    public AsparagusRootBlock(Properties properties) {
        super(properties);
    }



    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
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
    public boolean isValidBonemealTarget(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return worldIn.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        worldIn.setBlockAndUpdate(pos.above(), RankineBlocks.ASPARAGUS_PLANT.get().defaultBlockState());
    }
}
