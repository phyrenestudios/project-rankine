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

public class RankineSaplingBlock extends SaplingBlock {
    public static final IntegerProperty STAGE = BlockStateProperties.STAGE_0_1;
    private final Tree tree;
    private int type;
    public RankineSaplingBlock(Tree treeIn, Properties properties, int type) {
        super(treeIn, properties);
        this.tree = treeIn;
        this.setDefaultState(this.stateContainer.getBaseState().with(STAGE, 0));
        this.type = type;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = state.getBlock();
        switch (type) {
            case 3:
                return block.isIn(Tags.Blocks.DIRT) || block.isIn(Tags.Blocks.SAND) || block.isIn(RankineTags.Blocks.COARSE_DIRT);
            case 2:
                return block.isIn(Tags.Blocks.DIRT) || block.isIn(RankineTags.Blocks.COARSE_DIRT);
            case 1:
            default:
                return block.isIn(Tags.Blocks.DIRT);
        }
    }

}