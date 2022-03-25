package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MapleLeavesBlock extends LeavesBlock {
    public static final IntegerProperty COLOR = IntegerProperty.create("color",0,6);

    public MapleLeavesBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(DISTANCE, 7).setValue(PERSISTENT, Boolean.FALSE).setValue(COLOR,0));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (!state.getValue(PERSISTENT)) {
            if (random.nextFloat() < 0.005) {
                this.atumnize(state, worldIn.getLevel(), pos);
            }
            if (state.getValue(DISTANCE) == 7) {
                dropResources(state, worldIn, pos);
                worldIn.removeBlock(pos, false);
            }
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

    private static int getDistance(BlockState neighbor) {
        if (BlockTags.LOGS.contains(neighbor.getBlock())) {
            return 0;
        } else {
            return neighbor.getBlock() instanceof LeavesBlock ? neighbor.getValue(DISTANCE) : 7;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return updateDistance(this.defaultBlockState().setValue(PERSISTENT, Boolean.TRUE).setValue(COLOR,0), context.getLevel(), context.getClickedPos());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE, PERSISTENT, COLOR);
    }


    protected void atumnize(BlockState state, Level worldIn, BlockPos pos) {
        if (state.getValue(COLOR) != 6) {
            worldIn.setBlock(pos, RankineBlocks.MAPLE_LEAVES.get().defaultBlockState().setValue(DISTANCE, state.getValue(DISTANCE)).setValue(PERSISTENT, state.getValue(PERSISTENT)).setValue(COLOR, state.getValue(COLOR)+1),2);
        }
        worldIn.neighborChanged(pos, RankineBlocks.MAPLE_LEAVES.get(), pos);
    }




}
