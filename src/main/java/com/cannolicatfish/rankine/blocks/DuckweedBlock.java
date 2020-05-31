package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;


public class DuckweedBlock extends LilyPadBlock{
    public DuckweedBlock(Properties builder) {
        super(builder);
    }

    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.5D, 16.0D);

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(20) == 1 ) {
            BlockPos blockpos1 = pos.add(rand.nextInt(2) - 1, 0, rand.nextInt(2) - 1);
            if (worldIn.isAirBlock(blockpos1) && state.isValidPosition(worldIn, blockpos1)) {
                worldIn.setBlockState(blockpos1, state, 2);
            }
        }
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        IFluidState placedOn = worldIn.getFluidState(pos.down());
        IFluidState under = worldIn.getFluidState(pos.down(2));
        return placedOn.getFluid() == Fluids.WATER && under.getFluid() != Fluids.WATER;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        IFluidState placedOn = worldIn.getFluidState(pos.down());
        IFluidState under = worldIn.getFluidState(pos.down(2));
        return placedOn.getFluid() == Fluids.WATER && under.getFluid() != Fluids.WATER;
    }
}
