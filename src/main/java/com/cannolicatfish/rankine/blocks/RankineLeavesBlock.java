package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class RankineLeavesBlock extends LeavesBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_5;

    public RankineLeavesBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(DISTANCE, 7).with(PERSISTENT, Boolean.FALSE).with(AGE, 0));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE, PERSISTENT, AGE);
    }

    public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.get(AGE) == 4 || state.get(AGE) == 5 ? 3 : 1;
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return !state.get(PERSISTENT);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        BlockState bs = worldIn.getBlockState(pos.down());
        if (random.nextFloat() < Config.GENERAL.LEAF_LITTER_GEN.get() && (bs.matchesBlock(Blocks.AIR) || bs.isReplaceable(Fluids.WATER)) && !bs.matchesBlock(RankineBlocks.WILLOW_BRANCHLET.get())) {
                worldIn.setBlockState(pos.down(), RankineLists.LEAF_LITTERS.get(RankineLists.LEAVES.indexOf(state.getBlock())).getDefaultState(),3);
        }
        if (worldIn.getBlockState(pos.up()).matchesBlock(Blocks.SNOW) && !state.get(AGE).equals(5)) {
            worldIn.setBlockState(pos, state.with(AGE, 5),2);
        } else if ((worldIn.getBiome(pos).getPrecipitation() == Biome.RainType.SNOW || worldIn.getBiome(pos).getTemperature(pos) < 0.15) && !state.get(AGE).equals(4)) {
            worldIn.setBlockState(pos, state.with(AGE, 4),2);
        } else if (!state.get(AGE).equals(0)) {
            worldIn.setBlockState(pos, state.with(AGE, 0),2);
        }
        super.randomTick(state, worldIn, pos, random);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        int i = getDistance(facingState) + 1;
        if (i != 1 || stateIn.get(DISTANCE) != i) {
            worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
        }
        return worldIn.getBlockState(currentPos.up()).matchesBlock(Blocks.SNOW) ? stateIn.with(AGE,5) : worldIn.getBiome(currentPos).getPrecipitation() == Biome.RainType.SNOW || worldIn.getBiome(currentPos).getTemperature(currentPos) < 0.15 ? stateIn.with(AGE,4) : stateIn.with(AGE,0);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World worldIn = context.getWorld();
        BlockPos pos = context.getPos();
        return worldIn.getBiome(pos).getPrecipitation() == Biome.RainType.SNOW || worldIn.getBiome(pos).getTemperature(pos) < 0.15 ? updateDistance(this.getDefaultState().with(PERSISTENT, Boolean.TRUE).with(AGE,4), context.getWorld(), context.getPos()) : updateDistance(this.getDefaultState().with(PERSISTENT, Boolean.TRUE).with(AGE,0), context.getWorld(), context.getPos());
    }

    private static int getDistance(BlockState neighbor) {
        if (BlockTags.LOGS.contains(neighbor.getBlock())) {
            return 0;
        } else {
            return neighbor.getBlock() instanceof LeavesBlock ? neighbor.get(DISTANCE) : 7;
        }
    }

    private static BlockState updateDistance(BlockState state, IWorld worldIn, BlockPos pos) {
        int i = 7;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for(Direction direction : Direction.values()) {
            blockpos$mutable.setAndMove(pos, direction);
            i = Math.min(i, getDistance(worldIn.getBlockState(blockpos$mutable)) + 1);
            if (i == 1) {
                break;
            }
        }

        return state.with(DISTANCE, i);
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return state.get(AGE) > 3 ? 10 : 30;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return state.get(AGE) > 3 ? 20 : 60;
    }
}
