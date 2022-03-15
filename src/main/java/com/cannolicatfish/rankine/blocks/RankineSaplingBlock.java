package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.Tags;

import net.minecraft.block.AbstractBlock.Properties;

public class RankineSaplingBlock extends SaplingBlock {
    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
    private final Tree tree;
    private int type;
    public RankineSaplingBlock(Tree treeIn, Properties properties, int type) {
        super(treeIn, properties);
        this.tree = treeIn;
        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, 0));
        this.type = type;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = state.getBlock();
        switch (type) {
            case 3:
                return block.is(Tags.Blocks.DIRT) || block.is(Tags.Blocks.SAND) || block.is(RankineTags.Blocks.COARSE_DIRT);
            case 2:
                return block.is(Tags.Blocks.DIRT) || block.is(RankineTags.Blocks.COARSE_DIRT);
            case 1:
            default:
                return block.is(Tags.Blocks.DIRT);
        }
    }

}