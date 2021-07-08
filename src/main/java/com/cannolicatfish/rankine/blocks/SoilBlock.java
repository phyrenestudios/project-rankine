package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CoralBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Arrays;
import java.util.Random;

public class SoilBlock extends Block {
    public static final BooleanProperty MUD = BooleanProperty.create("mud");

    public SoilBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(MUD, false));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(MUD);
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (state.get(MUD) && !isWet(worldIn, pos)) {
            worldIn.setBlockState(pos,state.with(MUD, true), 2);
        } else if (!state.get(MUD) && isWet(worldIn, pos)) {
            worldIn.setBlockState(pos,state.with(MUD, false), 2);
        }
    }

    public boolean isWet(World world, BlockPos pos) {
        for(Direction direction : Direction.values()) {
            FluidState fluidstate = world.getFluidState(pos.offset(direction));
            if (fluidstate.isTagged(FluidTags.WATER)) {
                return true;
            }
        }
        return false;
    }
}
