package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class RankineLeavesBlock extends LeavesBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_5;

    public RankineLeavesBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(DISTANCE, 7).setValue(PERSISTENT, Boolean.FALSE).setValue(AGE, 0));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE, PERSISTENT, AGE);
    }

    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return state.getValue(AGE) == 4 || state.getValue(AGE) == 5 ? 3 : 1;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(PERSISTENT);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        BlockState bs = worldIn.getBlockState(pos.below());
        if (random.nextFloat() < Config.GENERAL.LEAF_LITTER_GEN.get() && (bs.is(Blocks.AIR) || bs.canBeReplaced(Fluids.WATER)) && !bs.is(RankineBlocks.WILLOW_BRANCHLET.get())) {
                worldIn.setBlock(pos.below(), RankineLists.LEAF_LITTERS.get(RankineLists.LEAVES.indexOf(state.getBlock())).defaultBlockState(),3);
        }
        if (worldIn.getBlockState(pos.above()).is(Blocks.SNOW) && !state.getValue(AGE).equals(5)) {
            worldIn.setBlock(pos, state.setValue(AGE, 5),2);
        } else if ((worldIn.getBiome(pos).value().getPrecipitation() == Biome.Precipitation.SNOW || worldIn.getBiome(pos).value().coldEnoughToSnow(pos)) && !state.getValue(AGE).equals(4)) {
            worldIn.setBlock(pos, state.setValue(AGE, 4),2);
        } else if (!state.getValue(AGE).equals(0)) {
            worldIn.setBlock(pos, state.setValue(AGE, 0),2);
        }
        super.randomTick(state, worldIn, pos, random);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        int i = getDistance(facingState) + 1;
        if (i != 1 || stateIn.getValue(DISTANCE) != i) {
            worldIn.getBlockTicks().willTickThisTick(currentPos, this);
        }
        return worldIn.getBlockState(currentPos.above()).is(Blocks.SNOW) ? stateIn.setValue(AGE,5) : worldIn.getBiome(currentPos).value().getPrecipitation() == Biome.Precipitation.SNOW || worldIn.getBiome(currentPos).value().coldEnoughToSnow(currentPos) ? stateIn.setValue(AGE,4) : stateIn.setValue(AGE,0);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();
        return worldIn.getBiome(pos).value().getPrecipitation() == Biome.Precipitation.SNOW || worldIn.getBiome(pos).value().coldEnoughToSnow(pos) ? updateDistance(this.defaultBlockState().setValue(PERSISTENT, Boolean.TRUE).setValue(AGE,4), context.getLevel(), context.getClickedPos()) : updateDistance(this.defaultBlockState().setValue(PERSISTENT, Boolean.TRUE).setValue(AGE,0), context.getLevel(), context.getClickedPos());
    }

    private static int getDistance(BlockState neighbor) {
        if (neighbor.is(BlockTags.LOGS)) {
            return 0;
        } else {
            return neighbor.getBlock() instanceof LeavesBlock ? neighbor.getValue(DISTANCE) : 7;
        }
    }

    private static BlockState updateDistance(BlockState state, LevelAccessor worldIn, BlockPos pos) {
        int i = 7;
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

        for(Direction direction : Direction.values()) {
            blockpos$mutable.setWithOffset(pos, direction);
            i = Math.min(i, getDistance(worldIn.getBlockState(blockpos$mutable)) + 1);
            if (i == 1) {
                break;
            }
        }

        return state.setValue(DISTANCE, i);
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return state.getValue(AGE) > 3 ? 10 : 30;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return state.getValue(AGE) > 3 ? 20 : 60;
    }
}
