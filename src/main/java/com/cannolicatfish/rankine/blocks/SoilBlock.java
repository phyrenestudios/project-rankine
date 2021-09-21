package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class SoilBlock extends Block {
    public static final BooleanProperty WET = BooleanProperty.create("wet");

    public SoilBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(WET, false));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WET);
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (state.get(WET) && !WorldgenUtils.isWet((ISeedReader) worldIn, pos)) {
            worldIn.setBlockState(pos,state.with(WET, false), 2);
        } else if (!state.get(WET) && WorldgenUtils.isWet((ISeedReader) worldIn, pos)) {
            worldIn.setBlockState(pos,state.with(WET, true), 2);
        }
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        if (worldIn.getBlockState(pos).get(WET)) {
            entityIn.setMotionMultiplier(worldIn.getBlockState(pos), new Vector3d((double)0.98F, 0.8D, (double)0.98F));
        } else {
            super.onEntityWalk(worldIn, pos, entityIn);
        }
    }
}
