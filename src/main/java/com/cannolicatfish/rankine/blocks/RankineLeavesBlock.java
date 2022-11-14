package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class RankineLeavesBlock extends LeavesBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_1;

    public RankineLeavesBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(DISTANCE, 7).with(PERSISTENT, Boolean.FALSE).with(AGE, 0));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE, PERSISTENT, AGE);
    }
    @Override
    public boolean ticksRandomly(BlockState state) {
        return !state.get(PERSISTENT);
    }
    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        BlockState bs = worldIn.getBlockState(pos.down());
        if (random.nextFloat() < Config.GENERAL.LEAF_LITTER_GEN.get() && (bs.matchesBlock(Blocks.AIR) || bs.isReplaceable(Fluids.WATER)) && (worldIn.getBlockState(pos.down(2)).matchesBlock(Blocks.AIR) || worldIn.getBlockState(pos.down(2)).isReplaceable(Fluids.WATER))) {
                worldIn.setBlockState(pos.down(), RankineLists.LEAF_LITTERS.get(RankineLists.LEAVES.indexOf(state.getBlock())).getDefaultState(),3);
        }
        super.randomTick(state, worldIn, pos, random);
    }
    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
       return super.updatePostPlacement(worldIn.getBlockState(currentPos.up()).matchesBlock(Blocks.SNOW) ? stateIn.with(AGE,1) : stateIn.with(AGE,0),facing,facingState,worldIn,currentPos,facingPos);
    }
    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return state.get(AGE) > 0 ? 10 : 30;
    }
    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return state.get(AGE) > 0 ? 20 : 60;
    }
}
