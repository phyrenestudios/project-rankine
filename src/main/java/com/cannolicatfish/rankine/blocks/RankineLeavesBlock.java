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

import net.minecraft.block.AbstractBlock.Properties;

public class RankineLeavesBlock extends LeavesBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_5;

    public RankineLeavesBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(DISTANCE, 7).setValue(PERSISTENT, Boolean.FALSE).setValue(AGE, 0));
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE, PERSISTENT, AGE);
    }

    public int getLightBlock(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.getValue(AGE) == 4 || state.getValue(AGE) == 5 ? 3 : 1;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(PERSISTENT);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        BlockState bs = worldIn.getBlockState(pos.below());
        if (random.nextFloat() < Config.GENERAL.LEAF_LITTER_GEN.get() && (bs.is(Blocks.AIR) || bs.canBeReplaced(Fluids.WATER)) && !bs.is(RankineBlocks.WILLOW_BRANCHLET.get())) {
                worldIn.setBlock(pos.below(), RankineLists.LEAF_LITTERS.get(RankineLists.LEAVES.indexOf(state.getBlock())).defaultBlockState(),3);
        }
        if (worldIn.getBlockState(pos.above()).is(Blocks.SNOW) && !state.getValue(AGE).equals(5)) {
            worldIn.setBlock(pos, state.setValue(AGE, 5),2);
        } else if ((worldIn.getBiome(pos).getPrecipitation() == Biome.RainType.SNOW || worldIn.getBiome(pos).getTemperature(pos) < 0.15) && !state.getValue(AGE).equals(4)) {
            worldIn.setBlock(pos, state.setValue(AGE, 4),2);
        } else if (!state.getValue(AGE).equals(0)) {
            worldIn.setBlock(pos, state.setValue(AGE, 0),2);
        }
        super.randomTick(state, worldIn, pos, random);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        int i = getDistance(facingState) + 1;
        if (i != 1 || stateIn.getValue(DISTANCE) != i) {
            worldIn.getBlockTicks().scheduleTick(currentPos, this, 1);
        }
        return worldIn.getBlockState(currentPos.above()).is(Blocks.SNOW) ? stateIn.setValue(AGE,5) : worldIn.getBiome(currentPos).getPrecipitation() == Biome.RainType.SNOW || worldIn.getBiome(currentPos).getTemperature(currentPos) < 0.15 ? stateIn.setValue(AGE,4) : stateIn.setValue(AGE,0);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();
        return worldIn.getBiome(pos).getPrecipitation() == Biome.RainType.SNOW || worldIn.getBiome(pos).getTemperature(pos) < 0.15 ? updateDistance(this.defaultBlockState().setValue(PERSISTENT, Boolean.TRUE).setValue(AGE,4), context.getLevel(), context.getClickedPos()) : updateDistance(this.defaultBlockState().setValue(PERSISTENT, Boolean.TRUE).setValue(AGE,0), context.getLevel(), context.getClickedPos());
    }

    private static int getDistance(BlockState neighbor) {
        if (BlockTags.LOGS.contains(neighbor.getBlock())) {
            return 0;
        } else {
            return neighbor.getBlock() instanceof LeavesBlock ? neighbor.getValue(DISTANCE) : 7;
        }
    }

    private static BlockState updateDistance(BlockState state, IWorld worldIn, BlockPos pos) {
        int i = 7;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

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
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return state.getValue(AGE) > 3 ? 10 : 30;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return state.getValue(AGE) > 3 ? 20 : 60;
    }
}
