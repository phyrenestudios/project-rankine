package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class LocustSpineBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    protected static final VoxelShape[] UP_SHAPES = new VoxelShape[] {Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 5.0D, 13.0D)};
    protected static final VoxelShape[] DOWN_SHAPES = new VoxelShape[] {Block.makeCuboidShape(3.0D, 11.0D, 3.0D, 13.0D, 16.0D, 13.0D)};
    protected static final VoxelShape[] NORTH_SHAPES = new VoxelShape[] {Block.makeCuboidShape(3.0D, 3.0D, 11.0D, 13.0D, 13.0D, 16.0D)};
    protected static final VoxelShape[] SOUTH_SHAPES = new VoxelShape[] {Block.makeCuboidShape(3.0D, 3.0D, 0.0D, 13.0D, 13.0D, 5.0D)};
    protected static final VoxelShape[] WEST_SHAPES = new VoxelShape[] {Block.makeCuboidShape(11.0D, 3.0D, 3.0D, 16.0D, 13.0D, 13.0D)};
    protected static final VoxelShape[] EAST_SHAPES = new VoxelShape[] {Block.makeCuboidShape(0.0D, 3.0D, 3.0D, 5.0D, 13.0D, 13.0D)};

    public LocustSpineBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.UP));
    }


    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        switch(state.get(FACING)) {
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
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch(state.get(FACING)) {
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

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction direction = context.getFace();
        return this.getDefaultState().with(FACING, direction);
    }

    @SuppressWarnings("deprecation")
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.offset(state.get(FACING).getOpposite())).isIn(RankineTags.Blocks.HONEY_LOCUST_LOGS);
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return !this.isValidPosition(stateIn, worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    //public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
    //    return true;
    //}

    @OnlyIn(Dist.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
        return adjacentBlockState.getBlock() == this || super.isSideInvisible(state, adjacentBlockState, side);
    }

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        entityIn.attackEntityFrom(DamageSource.CACTUS, 0.1F);
    }

    @Override
    public boolean canBeReplacedByLeaves(BlockState state, IWorldReader world, BlockPos pos) {
        return true;
    }
    @Override
    public boolean canBeReplacedByLogs(BlockState state, IWorldReader world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isReplaceable(BlockState p_196253_1_, BlockItemUseContext p_196253_2_) {
        return true;
    }

    @Override
    public PathNodeType getAiPathNodeType(BlockState state, IBlockReader world, BlockPos pos, @Nullable MobEntity entity) {
        return PathNodeType.DAMAGE_CACTUS;
    }
}
