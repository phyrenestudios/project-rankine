package com.cannolicatfish.rankine.blocks.buildingmodes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class RankinePlanksBlock extends BuildingModeBlock {

    public RankinePlanksBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getMaxStyles() {
        return 4;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 20;
    }

}
