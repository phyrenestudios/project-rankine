package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.dimension.ModDimensions;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ModDimension;


public class PipeBlock extends RotatedPillarBlock {
    public PipeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
        return true;
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

}
