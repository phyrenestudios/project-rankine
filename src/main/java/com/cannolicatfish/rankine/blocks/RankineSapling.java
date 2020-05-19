package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class RankineSapling extends SaplingBlock {
    public static final IntegerProperty STAGE = BlockStateProperties.STAGE_0_1;
    private final Tree tree;
    private int type;
    public RankineSapling(Tree treeIn, Properties properties, int type) {
        super(treeIn, properties);
        this.tree = treeIn;
        this.setDefaultState(this.stateContainer.getBaseState().with(STAGE, Integer.valueOf(0)));
        this.type = type;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = state.getBlock();
        if (type == 2)
        {
            return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.SAND || block == Blocks.RED_SAND ||  block == Blocks.COARSE_DIRT  || block == ModBlocks.SANDY_DIRT;
        }
        if (type == 3)
        {
            return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == ModBlocks.MUDDY_DIRT || block == ModBlocks.MUDDY_GRASS;
        }
        if (type == 4)
        {
            return block == Blocks.GRASS_BLOCK;
        }
        return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.SAND || block == Blocks.RED_SAND || block == ModBlocks.SANDY_DIRT || block == ModBlocks.MUDDY_DIRT || block == ModBlocks.MUDDY_GRASS;
    }

}