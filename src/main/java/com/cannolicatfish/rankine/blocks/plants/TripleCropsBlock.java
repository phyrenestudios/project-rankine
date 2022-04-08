package com.cannolicatfish.rankine.blocks.plants;

import com.cannolicatfish.rankine.blocks.states.TripleBlockSection;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class TripleCropsBlock extends CropBlock {
    public static final EnumProperty<TripleBlockSection> SECTION = EnumProperty.create("section", TripleBlockSection.class);
    private static final VoxelShape[] BOTTOM_SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
    private static final VoxelShape[] MIDDLE_SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D), Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
    private static final VoxelShape[] TOP_SHAPE_BY_AGE = new VoxelShape[]{Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D), Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D), Block.box(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

    public TripleCropsBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SECTION, TripleBlockSection.BOTTOM).setValue(AGE, 0));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, SECTION);
    }

    @Override
    public BlockBehaviour.OffsetType getOffsetType() {
        return BlockBehaviour.OffsetType.XZ;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(SECTION)) {
            case BOTTOM:
                return BOTTOM_SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
            case MIDDLE:
                return MIDDLE_SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
            case TOP:
                return TOP_SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
        }
        return super.getShape(state, worldIn, pos, context);
    }

    protected TripleBlockSection getSection(BlockState state) {
        return state.getValue(SECTION);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return state.is(Blocks.FARMLAND);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        switch (state.getValue(SECTION)) {
            default:
            case BOTTOM:
                if (state.getValue(AGE) == 7) {
                    return worldIn.getBlockState(pos.below()).is(BlockTags.DIRT) || super.canSurvive(state, worldIn, pos);
                }
                return super.canSurvive(state, worldIn, pos);
            case MIDDLE:
                BlockState blockstate1 = worldIn.getBlockState(pos.below());
                if (state.getBlock() != this) return super.canSurvive(state, worldIn, pos); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
                return blockstate1.is(this) && blockstate1.getValue(SECTION) == TripleBlockSection.BOTTOM;
            case TOP:
                BlockState blockstate2 = worldIn.getBlockState(pos.below());
                if (state.getBlock() != this) return super.canSurvive(state, worldIn, pos); //Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
                return blockstate2.is(this) && blockstate2.getValue(SECTION) == TripleBlockSection.MIDDLE;
        }
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        TripleBlockSection tripleBlockSection = stateIn.getValue(SECTION);
        switch (tripleBlockSection) {
            case BOTTOM:
            case MIDDLE:
                if (facing.getAxis() != Direction.Axis.Y || !(facing == Direction.UP) || facingState.is(this) && facingState.getValue(SECTION) != tripleBlockSection) {
                    return facing == Direction.DOWN && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
                } else {
                    return Blocks.AIR.defaultBlockState();
                }
            case TOP:
                break;
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !this.isMaxAge(state) && getSection(state).equals(TripleBlockSection.BOTTOM);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (worldIn.getRawBrightness(pos, 0) >= 9) {
            if ((worldIn.getBlockState(pos.above(1)).is(Blocks.AIR) || worldIn.getBlockState(pos.above(1)).is(this)) && (worldIn.getBlockState(pos.above(2)).is(Blocks.AIR) || worldIn.getBlockState(pos.above(2)).is(this))) {
                int i = this.getAge(state);
                if (i < this.getMaxAge()) {
                    float f = getGrowthSpeed(this, worldIn, pos);
                    if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0)) {
                        worldIn.setBlock(pos, this.getStateForAge(i + 1).setValue(SECTION, TripleBlockSection.BOTTOM), 2);
                        worldIn.setBlock(pos.above(1), this.getStateForAge(i + 1).setValue(SECTION, TripleBlockSection.MIDDLE), 2);
                        worldIn.setBlock(pos.above(2), this.getStateForAge(i + 1).setValue(SECTION, TripleBlockSection.TOP), 2);
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
            case BOTTOM:
                worldIn.setBlock(pos, this.getStateForAge(i).setValue(SECTION, TripleBlockSection.BOTTOM), 2);
                worldIn.setBlock(pos.above(1), this.getStateForAge(i).setValue(SECTION, TripleBlockSection.MIDDLE), 2);
                worldIn.setBlock(pos.above(2), this.getStateForAge(i).setValue(SECTION, TripleBlockSection.TOP), 2);
                break;
            case MIDDLE:
                worldIn.setBlock(pos.below(), this.getStateForAge(i).setValue(SECTION, TripleBlockSection.BOTTOM), 2);
                worldIn.setBlock(pos, this.getStateForAge(i).setValue(SECTION, TripleBlockSection.MIDDLE), 2);
                worldIn.setBlock(pos.above(), this.getStateForAge(i).setValue(SECTION, TripleBlockSection.TOP), 2);
                break;
            case TOP:
                worldIn.setBlock(pos.below(2), this.getStateForAge(i).setValue(SECTION, TripleBlockSection.BOTTOM), 2);
                worldIn.setBlock(pos.below(1), this.getStateForAge(i).setValue(SECTION, TripleBlockSection.MIDDLE), 2);
                worldIn.setBlock(pos, this.getStateForAge(i).setValue(SECTION, TripleBlockSection.TOP), 2);
                break;
        }
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        switch (state.getValue(SECTION)) {
            case BOTTOM:
            case MIDDLE:
                return !this.isMaxAge(state) && (worldIn.getBlockState(pos.above()).is(Blocks.AIR) || worldIn.getBlockState(pos.above()).is(this));
            case TOP:
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
        if (state.getValue(SECTION).equals(TripleBlockSection.TOP)) {
            BlockPos blockpos = pos.below(2);
            BlockState blockstate = world.getBlockState(blockpos);
            if (blockstate.getBlock() == state.getBlock() && blockstate.getValue(SECTION) == TripleBlockSection.BOTTOM) {
                world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                world.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
            }
        } else if (state.getValue(SECTION).equals(TripleBlockSection.MIDDLE)) {
            BlockPos blockpos = pos.below(1);
            BlockState blockstate = world.getBlockState(blockpos);
            if (blockstate.getBlock() == state.getBlock() && blockstate.getValue(SECTION) == TripleBlockSection.BOTTOM) {
                world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                world.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
            }
        }

    }

    @OnlyIn(Dist.CLIENT)
    public long getSeed(BlockState state, BlockPos pos) {
        return Mth.getSeed(pos.getX(), pos.below(state.getValue(SECTION) == TripleBlockSection.BOTTOM ? 0 : 1).getY(), pos.getZ());
    }

    public void placeAt(LevelAccessor worldIn, BlockPos pos, int flags) {
        worldIn.setBlock(pos, this.defaultBlockState().setValue(AGE, 7).setValue(SECTION, TripleBlockSection.BOTTOM), flags);
        worldIn.setBlock(pos.above(), this.defaultBlockState().setValue(AGE, 7).setValue(SECTION, TripleBlockSection.MIDDLE), flags);
        worldIn.setBlock(pos.above(2), this.defaultBlockState().setValue(AGE, 7).setValue(SECTION, TripleBlockSection.TOP), flags);
    }

}