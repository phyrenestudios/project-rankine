package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.blocks.block_groups.RankineWood;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;

import java.util.Random;

public class RankineLeavesBlock extends LeavesBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;

    public RankineLeavesBlock() {
        super(Block.Properties.of(Material.LEAVES).randomTicks().strength(0.2F).sound(SoundType.GRASS).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(DISTANCE, 7).setValue(PERSISTENT, Boolean.FALSE).setValue(AGE, 0));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE, PERSISTENT, AGE);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel levelIn, BlockPos pos, Random random) {
        BlockState bs = levelIn.getBlockState(pos.below());
        if (random.nextFloat() < Config.GENERAL.LEAF_LITTER_GEN.get() && (bs.is(Blocks.AIR) || bs.canBeReplaced(Fluids.WATER)) && !bs.is(RankineBlocks.WILLOW_BRANCHLET.get())) {
            for (RankineWood Wood : RankineLists.RANKINE_WOODS) {
                if (state.is(Wood.getLeaves())) {
                    levelIn.setBlockAndUpdate(pos.below(), Wood.getLeafLitter().defaultBlockState());
                    break;
                }
            }
        }
        levelIn.setBlockAndUpdate(pos, state.setValue(AGE,isSnowy(levelIn,pos) ? 2 : 0));
        super.randomTick(state, levelIn, pos, random);
    }

    private boolean isSnowy(LevelAccessor levelIn, BlockPos posIn) {
        return levelIn.getBlockState(posIn.above()).is(Blocks.SNOW) || levelIn.getBlockState(posIn.above()).is(Blocks.SNOW_BLOCK) || levelIn.getBlockState(posIn.above()).is(Blocks.POWDER_SNOW);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        int i = getDistanceAt(facingState) + 1;
        if (i != 1 || stateIn.getValue(DISTANCE) != i) {
            worldIn.scheduleTick(currentPos, this, 1);
        }
        return stateIn.setValue(AGE,isSnowy(worldIn, currentPos) ? 2 : 0);
    }

    private static int getDistanceAt(BlockState p_54464_) {
        if (p_54464_.is(BlockTags.LOGS)) {
            return 0;
        } else {
            return p_54464_.getBlock() instanceof LeavesBlock ? p_54464_.getValue(DISTANCE) : 7;
        }
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return state.getValue(AGE) == 2 ? 10 : 30;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return state.getValue(AGE) == 2 ? 20 : 60;
    }
}
