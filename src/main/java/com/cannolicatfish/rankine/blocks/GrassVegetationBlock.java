package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

import net.minecraft.block.AbstractBlock.OffsetType;
import net.minecraft.block.AbstractBlock.Properties;

public class GrassVegetationBlock extends BushBlock {
    double height;

    public GrassVegetationBlock(double height, Properties properties) {
        super(properties);
        this.height = height;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Block.box(2.0D, 0.0D, 2.0D, 14.0D, height, 14.0D);
    }

    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }
}
