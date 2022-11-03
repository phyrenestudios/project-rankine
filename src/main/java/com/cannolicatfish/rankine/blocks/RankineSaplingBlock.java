package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class RankineSaplingBlock extends SaplingBlock {
    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
    private final AbstractTreeGrower tree;
    private int type;
    public RankineSaplingBlock(AbstractTreeGrower treeIn, Properties properties, int type) {
        super(treeIn, properties);
        this.tree = treeIn;
        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, 0));
        this.type = type;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        switch (type) {
            case 3:
                return state.is(BlockTags.DIRT) || state.is(BlockTags.SAND) || state.is(RankineTags.Blocks.COARSE_DIRT);
            case 2:
                return state.is(BlockTags.DIRT) || state.is(RankineTags.Blocks.COARSE_DIRT);
            case 1:
            default:
                return state.is(BlockTags.DIRT);
        }
    }

}