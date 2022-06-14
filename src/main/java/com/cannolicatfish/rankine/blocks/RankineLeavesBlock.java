package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;

import java.util.Random;

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
    public void tick(BlockState p_54426_, ServerLevel levelIn, BlockPos pos, Random p_54429_) {
        levelIn.setBlock(pos, updateDistance(p_54426_, levelIn, pos), 3);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        BlockState bs = worldIn.getBlockState(pos.below());
        if (random.nextFloat() < Config.GENERAL.LEAF_LITTER_GEN.get() && (bs.is(Blocks.AIR) || bs.canBeReplaced(Fluids.WATER)) && !bs.is(RankineBlocks.WILLOW_BRANCHLET.get())) {
            worldIn.setBlock(pos.below(), RankineLists.LEAF_LITTERS.get(RankineLists.LEAVES.indexOf(state.getBlock())).defaultBlockState(),3);
        }
        if (worldIn.getBlockState(pos.above()).is(Blocks.SNOW) && !state.getValue(AGE).equals(5)) {
            worldIn.setBlock(pos, state.setValue(AGE, 5),3);
        } else if ((worldIn.getBiome(pos).value().getPrecipitation() == Biome.Precipitation.SNOW || worldIn.getBiome(pos).value().coldEnoughToSnow(pos)) && !state.getValue(AGE).equals(4)) {
            worldIn.setBlock(pos, state.setValue(AGE, 4),3);
        } else if (!state.getValue(AGE).equals(0)) {
            worldIn.setBlock(pos, state.setValue(AGE, 0),3);
        }
        super.randomTick(state, worldIn, pos, random);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        int i = getDistanceAt(facingState) + 1;
        if (i != 1 || stateIn.getValue(DISTANCE) != i) {
            worldIn.scheduleTick(currentPos, this, 1);
        }
        return worldIn.getBlockState(currentPos.above()).is(Blocks.SNOW) ? stateIn.setValue(AGE,5) : worldIn.getBiome(currentPos).value().getPrecipitation() == Biome.Precipitation.SNOW || worldIn.getBiome(currentPos).value().coldEnoughToSnow(currentPos) ? stateIn.setValue(AGE,4) : stateIn.setValue(AGE,0);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();
        return worldIn.getBiome(pos).value().getPrecipitation() == Biome.Precipitation.SNOW || worldIn.getBiome(pos).value().coldEnoughToSnow(pos) ? updateDistance(this.defaultBlockState().setValue(PERSISTENT, Boolean.TRUE).setValue(AGE,4), context.getLevel(), context.getClickedPos()) : updateDistance(this.defaultBlockState().setValue(PERSISTENT, Boolean.TRUE).setValue(AGE,0), context.getLevel(), context.getClickedPos());
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
        return state.getValue(AGE) > 3 ? 10 : 30;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return state.getValue(AGE) > 3 ? 20 : 60;
    }
}
