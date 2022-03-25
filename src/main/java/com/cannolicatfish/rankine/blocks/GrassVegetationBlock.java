package com.cannolicatfish.rankine.blocks;


import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;

public class GrassVegetationBlock extends BushBlock {
    double height;

    public GrassVegetationBlock(double height, Properties properties) {
        super(properties);
        this.height = height;
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Block.box(2.0D, 0.0D, 2.0D, 14.0D, height, 14.0D);
    }

    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }
}
