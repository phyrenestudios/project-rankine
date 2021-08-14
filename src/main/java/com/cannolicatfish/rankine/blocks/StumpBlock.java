package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class StumpBlock extends Block {
    public static final BooleanProperty ROT = BooleanProperty.create("rot");

    public StumpBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(ROT, false));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ROT);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return makeCuboidShape(1,0,1,15,16,15);
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (!state.get(ROT) && rand.nextFloat() < worldIn.getBiome(pos).getDownfall()/50.0f) {
            worldIn.setBlockState(pos,state.with(ROT, true), 2);
        } else if (state.get(ROT) && rand.nextFloat() < worldIn.getBiome(pos).getDownfall()/50.0f) {
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
        }
    }


}
