package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.blocks.block_groups.RankineWood;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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

    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 1;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(PERSISTENT);
    }

    @Override
    public void tick(BlockState p_54426_, ServerLevel levelIn, BlockPos pos, Random p_54429_) {
        levelIn.setBlockAndUpdate(pos, updateDistance(p_54426_, levelIn, pos));
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        BlockState bs = worldIn.getBlockState(pos.below());
        if (random.nextFloat() < Config.GENERAL.LEAF_LITTER_GEN.get() && (bs.is(Blocks.AIR) || bs.canBeReplaced(Fluids.WATER)) && !bs.is(RankineBlocks.WILLOW_BRANCHLET.get())) {
            for (RankineWood Wood : RankineLists.RANKINE_WOODS) {
                if (state.is(Wood.getLeaves())) {
                    worldIn.setBlockAndUpdate(pos.below(), Wood.getLeafLitter().defaultBlockState());
                    break;
                }
            }
        }
        if (worldIn.getBlockState(pos.above()).is(Blocks.SNOW)) {
            worldIn.setBlockAndUpdate(pos, state.setValue(AGE, 2));
        } else if (!state.getValue(AGE).equals(0)) {
            worldIn.setBlockAndUpdate(pos, state.setValue(AGE, 0));
        }
        super.randomTick(state, worldIn, pos, random);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        int i = getDistanceAt(facingState) + 1;
        if (i != 1 || stateIn.getValue(DISTANCE) != i) {
            worldIn.scheduleTick(currentPos, this, 1);
        }
        return worldIn.getBlockState(currentPos.above()).is(Blocks.SNOW) ? stateIn.setValue(AGE,2) : stateIn.setValue(AGE,0);
    }

    private static BlockState updateDistance(BlockState p_54436_, LevelAccessor p_54437_, BlockPos p_54438_) {
        int i = 7;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(Direction direction : Direction.values()) {
            blockpos$mutableblockpos.setWithOffset(p_54438_, direction);
            i = Math.min(i, getDistanceAt(p_54437_.getBlockState(blockpos$mutableblockpos)) + 1);
            if (i == 1) {
                break;
            }
        }

        return p_54436_.setValue(DISTANCE, i);
    }

    private static int getDistanceAt(BlockState p_54464_) {
        if (p_54464_.is(BlockTags.LOGS)) {
            return 0;
        } else {
            return p_54464_.getBlock() instanceof LeavesBlock ? p_54464_.getValue(DISTANCE) : 7;
        }
    }

    public void animateTick(BlockState p_54431_, Level p_54432_, BlockPos p_54433_, Random p_54434_) {
        if (p_54432_.isRainingAt(p_54433_.above())) {
            if (p_54434_.nextInt(15) == 1) {
                BlockPos blockpos = p_54433_.below();
                BlockState blockstate = p_54432_.getBlockState(blockpos);
                if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(p_54432_, blockpos, Direction.UP)) {
                    double d0 = (double)p_54433_.getX() + p_54434_.nextDouble();
                    double d1 = (double)p_54433_.getY() - 0.05D;
                    double d2 = (double)p_54433_.getZ() + p_54434_.nextDouble();
                    p_54432_.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            }
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
