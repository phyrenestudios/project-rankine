package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class LocustSpineBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    protected static final VoxelShape[] UP_SHAPES = new VoxelShape[] {Block.box(5.0D, 0.0D, 5.0D, 11.0D, 3.0D, 11.0D)};
    protected static final VoxelShape[] DOWN_SHAPES = new VoxelShape[] {Block.box(5.0D, 13.0D, 5.0D, 11.0D, 16.0D, 11.0D)};
    protected static final VoxelShape[] NORTH_SHAPES = new VoxelShape[] {Block.box(5.0D, 5.0D, 13.0D, 11.0D, 11.0D, 16.0D)};
    protected static final VoxelShape[] SOUTH_SHAPES = new VoxelShape[] {Block.box(5.0D, 5.0D, 0.0D, 11.0D, 11.0D, 3.0D)};
    protected static final VoxelShape[] WEST_SHAPES = new VoxelShape[] {Block.box(13.0D, 5.0D, 5.0D, 16.0D, 11.0D, 11.0D)};
    protected static final VoxelShape[] EAST_SHAPES = new VoxelShape[] {Block.box(0.0D, 5.0D, 5.0D, 3.0D, 11.0D, 11.0D)};

    public LocustSpineBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP));
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter p_230322_2_, BlockPos p_230322_3_, CollisionContext p_230322_4_) {
        switch(state.getValue(FACING)) {
            case UP:
                return UP_SHAPES[0];
            case DOWN:
                return DOWN_SHAPES[0];
            case EAST:
                return EAST_SHAPES[0];
            case WEST:
                return WEST_SHAPES[0];
            case NORTH:
                return NORTH_SHAPES[0];
            default:
                return SOUTH_SHAPES[0];
        }
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState state, BlockGetter worldIn, BlockPos pos) {
        switch(state.getValue(FACING)) {
            case UP:
                return UP_SHAPES[0];
            case DOWN:
                return DOWN_SHAPES[0];
            case EAST:
                return EAST_SHAPES[0];
            case WEST:
                return WEST_SHAPES[0];
            case NORTH:
                return NORTH_SHAPES[0];
            default:
                return SOUTH_SHAPES[0];
        }
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch(state.getValue(FACING)) {
            case UP:
                return UP_SHAPES[0];
            case DOWN:
                return DOWN_SHAPES[0];
            case EAST:
                return EAST_SHAPES[0];
            case WEST:
                return WEST_SHAPES[0];
            case NORTH:
                return NORTH_SHAPES[0];
            default:
                return SOUTH_SHAPES[0];
        }
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getClickedFace();
        return this.defaultBlockState().setValue(FACING, direction);
    }

    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.relative(state.getValue(FACING).getOpposite())).is(RankineTags.Blocks.HONEY_LOCUST_LOGS);
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return !this.canSurvive(stateIn, worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    //public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
    //    return true;
    //}

    @OnlyIn(Dist.CLIENT)
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
        return adjacentBlockState.getBlock() == this || super.skipRendering(state, adjacentBlockState, side);
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity) {
            entityIn.makeStuckInBlock(state, new Vec3((double)0.98F, 0.98D, (double)0.98F));
            if (!worldIn.isClientSide && (entityIn.xOld != entityIn.getX() || entityIn.zOld != entityIn.getZ())) {
                double d0 = Math.abs(entityIn.getX() - entityIn.xOld);
                double d1 = Math.abs(entityIn.getZ() - entityIn.zOld);
                if (d0 >= (double)0.01F || d1 >= (double)0.01F) {
                    entityIn.hurt(DamageSource.CACTUS, 0.5F);
                }
            }
        }
    }


    @Override
    public boolean canBeReplaced(BlockState p_196253_1_, BlockPlaceContext p_196253_2_) {
        return true;
    }
}
