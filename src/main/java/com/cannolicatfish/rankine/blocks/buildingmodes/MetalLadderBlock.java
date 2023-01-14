package com.cannolicatfish.rankine.blocks.buildingmodes;

import com.cannolicatfish.rankine.items.BuildingModeBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class MetalLadderBlock extends BuildingModeBlock implements SimpleWaterloggedBlock {
    protected static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 3.0D, 3.0D, 16.0D, 13.0D);
    protected static final VoxelShape WEST_AABB = Block.box(13.0D, 0.0D, 3.0D, 16.0D, 16.0D, 13.0D);
    protected static final VoxelShape SOUTH_AABB = Block.box(3.0D, 0.0D, 0.0D, 13.0D, 16.0D, 3.0D);
    protected static final VoxelShape NORTH_AABB = Block.box(3.0D, 0.0D, 13.0D, 13.0D, 16.0D, 16.0D);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    int alloyColor;

    public MetalLadderBlock(int color) {
        super(Block.Properties.of(Material.DECORATION).sound(SoundType.METAL).strength(1.0F).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.FALSE));
        this.alloyColor = color;
    }

    @Override
    public int getMaxStyles() {
        return 4;
    }
    
    @Override
    public VoxelShape getShape(BlockState p_54372_, BlockGetter p_54373_, BlockPos p_54374_, CollisionContext p_54375_) {
        switch(p_54372_.getValue(FACING)) {
            case NORTH:
                return NORTH_AABB;
            case SOUTH:
                return SOUTH_AABB;
            case WEST:
                return WEST_AABB;
            case EAST:
            default:
                return EAST_AABB;
        }
    }

    public int getColor() {
        return this.alloyColor;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader levelIn, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        if (canAttachTo(levelIn, pos.relative(direction.getOpposite()), direction)) return true;
        for (int n = 1; n <= 7; ++n) {
            if (!(levelIn.getBlockState(pos.below(n)).getBlock() instanceof MetalLadderBlock)) break;
            if (canAttachTo(levelIn, pos.below(n).relative(direction.getOpposite()), direction)) return true;
        }
        return false;
    }

    private boolean canAttachTo(BlockGetter blockReader, BlockPos pos, Direction direction) {
        BlockState blockstate = blockReader.getBlockState(pos);
        return blockstate.isFaceSturdy(blockReader, pos, direction);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction dirIn, BlockState facingState, LevelAccessor levelIn, BlockPos posIn, BlockPos facingPos) {
        if (!canSurvive(stateIn, levelIn, posIn)) return Blocks.AIR.defaultBlockState();
        if (stateIn.getValue(WATERLOGGED)) {
            levelIn.scheduleTick(posIn, Fluids.WATER, Fluids.WATER.getTickDelay(levelIn));
        }
        return super.updateShape(stateIn, dirIn, facingState, levelIn, posIn, facingPos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_54347_) {
        if (!p_54347_.replacingClickedOnBlock()) {
            BlockState blockstate = p_54347_.getLevel().getBlockState(p_54347_.getClickedPos().relative(p_54347_.getClickedFace().getOpposite()));
            if (blockstate.is(this) && blockstate.getValue(FACING) == p_54347_.getClickedFace()) {
                return null;
            }
        }

        BlockState blockstate1 = this.defaultBlockState();
        LevelReader levelreader = p_54347_.getLevel();
        BlockPos blockpos = p_54347_.getClickedPos();
        FluidState fluidstate = p_54347_.getLevel().getFluidState(p_54347_.getClickedPos());

        for (Direction direction : p_54347_.getNearestLookingDirections()) {
            if (direction.getAxis().isHorizontal()) {
                blockstate1 = blockstate1.setValue(FACING, direction.getOpposite());
                if (blockstate1.canSurvive(levelreader, blockpos)) {
                    return blockstate1.setValue(getProperty(), ((BuildingModeBlockItem) p_54347_.getItemInHand().getItem()).getBuildingMode(p_54347_.getItemInHand())).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
                }
            }
        }

        return null;
    }

    @Override
    public InteractionResult use(BlockState state, Level levelIn, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (!player.getItemInHand(hand).is(this.asItem())) return super.use(state, levelIn, pos, player, hand, result);
        int n = 0;
        while (!levelIn.isEmptyBlock(pos.above(n))) {
            n += 1;
        }
        if (n > 0 && canSurvive(state, levelIn, pos.above(n))) {
            levelIn.setBlockAndUpdate(pos.above(n), state.setValue(getProperty(), ((BuildingModeBlockItem) player.getItemInHand(hand).getItem()).getBuildingMode(player.getItemInHand(hand))));
            levelIn.playSound(null,pos.above(n), SoundEvents.METAL_PLACE, SoundSource.BLOCKS,1.0f,1.0f);
            if (!player.isCreative()) player.getItemInHand(hand).shrink(1);
            return InteractionResult.sidedSuccess(levelIn.isClientSide);
        }
        return super.use(state, levelIn, pos, player, hand, result);
    }

    public BlockState rotate(BlockState p_54360_, Rotation p_54361_) {
        return p_54360_.setValue(FACING, p_54361_.rotate(p_54360_.getValue(FACING)));
    }

    public BlockState mirror(BlockState p_54357_, Mirror p_54358_) {
        return p_54357_.rotate(p_54358_.getRotation(p_54357_.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54370_) {
        p_54370_.add(FACING, WATERLOGGED, getProperty());
    }

    public FluidState getFluidState(BlockState p_54377_) {
        return p_54377_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_54377_);
    }
}
