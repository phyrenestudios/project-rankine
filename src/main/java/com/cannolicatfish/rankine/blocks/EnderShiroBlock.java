package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ShovelItem;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.NetherVegetationFeature;
import net.minecraft.world.gen.feature.TwistingVineFeature;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class EnderShiroBlock extends Block {
    public EnderShiroBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (enderShiroGrowth(worldIn, pos)) {
            for(int i = 0; i < 4; ++i) {
                BlockPos blockpos = pos.add(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);
                if (worldIn.getBlockState(blockpos).getBlock().matchesBlock(Blocks.END_STONE) && enderShiroGrowth(worldIn, blockpos)) {
                    worldIn.setBlockState(blockpos, RankineBlocks.ENDER_SHIRO.get().getDefaultState());
                }
            }
        } else {
            worldIn.setBlockState(pos, Blocks.END_STONE.getDefaultState());
        }

    }

    private boolean enderShiroGrowth(ServerWorld worldIn, BlockPos pos) {
        return !worldIn.getBlockState(pos.up()).matchesBlock(this) && !worldIn.getBlockState(pos.up()).matchesBlock(Blocks.END_STONE);
    }

}
