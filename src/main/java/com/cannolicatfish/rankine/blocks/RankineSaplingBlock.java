package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.common.Tags;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

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
        Block block = state.getBlock();
        switch (type) {
            case 3:
                return Tags.Blocks.DIRT.contains(block) || Tags.Blocks.SAND.contains(block) || RankineTags.Blocks.COARSE_DIRT.contains(block);
            case 2:
                return Tags.Blocks.DIRT.contains(block) || RankineTags.Blocks.COARSE_DIRT.contains(block);
            case 1:
            default:
                return Tags.Blocks.DIRT.contains(block);
        }
    }

}