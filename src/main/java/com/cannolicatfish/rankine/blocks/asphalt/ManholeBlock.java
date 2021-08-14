package com.cannolicatfish.rankine.blocks.asphalt;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class ManholeBlock extends BaseAsphaltBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public ManholeBlock(Properties properties) {
        super(properties);
    }

}
