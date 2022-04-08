package com.cannolicatfish.rankine.blocks.plants;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.Tags;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class DoubleCropsBlock extends CropBlock {
    public static final EnumProperty<DoubleBlockHalf> SECTION = BlockStateProperties.DOUBLE_BLOCK_HALF;
    private static final VoxelShape[] LOWER_SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
    private static final VoxelShape[] UPPER_SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D), Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

    public DoubleCropsBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SECTION, DoubleBlockHalf.LOWER).setValue(AGE, 0));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, SECTION);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(SECTION)) {
            case LOWER:
                return LOWER_SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
            case UPPER:
                return UPPER_SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
        }
        return super.getShape(state, worldIn, pos, context);
    }

    protected DoubleBlockHalf getSection(BlockState state) {
        return state.getValue(SECTION);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return state.is(Blocks.FARMLAND);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        if (state.getValue(SECTION) != DoubleBlockHalf.UPPER) {
            if (state.getValue(AGE) == 7) {
                return worldIn.getBlockState(pos.below()).is(BlockTags.DIRT) || super.canSurvive(state, worldIn, pos);
            }
            return super.canSurvive(state, worldIn, pos);
        } else {
            BlockState blockstate = worldIn.getBlockState(pos.below());
            if (state.getBlock() != this) return super.canSurvive(state, worldIn, pos); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
            return blockstate.is(this) && blockstate.getValue(SECTION) == DoubleBlockHalf.LOWER;
        }
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        DoubleBlockHalf doubleblockhalf = stateIn.getValue(SECTION);
        if (facing.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (facing == Direction.UP) || facingState.is(this) && facingState.getValue(SECTION) != doubleblockhalf) {
            return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !this.isMaxAge(state) && getSection(state).equals(DoubleBlockHalf.LOWER);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (worldIn.getRawBrightness(pos, 0) >= 9) {
            if (worldIn.getBlockState(pos.above()).is(Blocks.AIR) || worldIn.getBlockState(pos.above()).is(this)) {
                int i = this.getAge(state);
                if (i < this.getMaxAge()) {
                    float f = getGrowthSpeed(this, worldIn, pos);
                    if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0)) {
                        worldIn.setBlock(pos, this.getStateForAge(i + 1).setValue(SECTION, DoubleBlockHalf.LOWER), 2);
                        worldIn.setBlock(pos.above(), this.getStateForAge(i + 1).setValue(SECTION, DoubleBlockHalf.UPPER), 2);
                        net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                    }
                }
            }
        }
    }

    @Override
    public void growCrops(Level worldIn, BlockPos pos, BlockState state) {
        int i = this.getAge(state) + this.getBonemealAgeIncrease(worldIn);
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }
        switch (state.getValue(SECTION)) {
            case LOWER:
                worldIn.setBlock(pos, this.getStateForAge(i).setValue(SECTION, DoubleBlockHalf.LOWER), 2);
                worldIn.setBlock(pos.above(), this.getStateForAge(i).setValue(SECTION, DoubleBlockHalf.UPPER), 2);
                break;
            case UPPER:
                worldIn.setBlock(pos.below(), this.getStateForAge(i).setValue(SECTION, DoubleBlockHalf.LOWER), 2);
                worldIn.setBlock(pos, this.getStateForAge(i).setValue(SECTION, DoubleBlockHalf.UPPER), 2);
                break;
        }
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        switch (state.getValue(SECTION)) {
            case LOWER:
                return !this.isMaxAge(state) && (worldIn.getBlockState(pos.above()).is(Blocks.AIR) || worldIn.getBlockState(pos.above()).is(this));
            case UPPER:
                return !this.isMaxAge(state);
        }
        return !this.isMaxAge(state);
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        if (!worldIn.isClientSide) {
            if (player.isCreative()) {
                removeLowerSections(worldIn, pos, state, player);
            } else {
                dropResources(state, worldIn, pos, (BlockEntity)null, player, player.getMainHandItem());
            }
        }
        super.playerWillDestroy(worldIn, pos, state, player);
    }

    protected static void removeLowerSections(Level world, BlockPos pos, BlockState state, Player player) {
        if (state.getValue(SECTION).equals(DoubleBlockHalf.UPPER)) {
            BlockPos blockpos = pos.below(1);
            BlockState blockstate = world.getBlockState(blockpos);
            if (blockstate.getBlock() == state.getBlock() && blockstate.getValue(SECTION) == DoubleBlockHalf.LOWER) {
                world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                world.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
            }
        }
    }

    public void placeAt(LevelAccessor worldIn, BlockPos pos, int flags) {
        worldIn.setBlock(pos, this.defaultBlockState().setValue(AGE, 7).setValue(SECTION, DoubleBlockHalf.LOWER), flags);
        worldIn.setBlock(pos.above(), this.defaultBlockState().setValue(AGE, 7).setValue(SECTION, DoubleBlockHalf.UPPER), flags);
    }

}