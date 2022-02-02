package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class RankineEightLayerBlock extends FallingBlock {

    public static final IntegerProperty LAYERS = IntegerProperty.create("layers", 1, 8);
    protected static final VoxelShape[] SHAPES = new VoxelShape[]{VoxelShapes.empty(),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

    public RankineEightLayerBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(LAYERS, 1));
    }

    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        switch(type) {
            case LAND:
                return state.get(LAYERS) < 5;
            case WATER:
                return false;
            case AIR:
                return false;
            default:
                return false;
        }
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.get(LAYERS)];
    }

    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.get(LAYERS) - 1];
    }

    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos) {
        return SHAPES[state.get(LAYERS)];
    }

    public VoxelShape getRayTraceShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return SHAPES[state.get(LAYERS)];
    }

    public boolean isTransparent(BlockState state) {
        return true;
    }

    /*
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockState blockstate = worldIn.getBlockState(pos.down());
        if (!blockstate.matchesBlock(Blocks.HONEY_BLOCK) && !blockstate.matchesBlock(Blocks.SOUL_SAND)) {
            return Block.doesSideFillSquare(blockstate.getCollisionShapeUncached(worldIn, pos.down()), Direction.UP) || blockstate.getBlock() == this && blockstate.get(LAYERS) == 8;
        } else {
            return true;
        }
    }

     */

    /**
     * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific face passed in.
     */
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        int i = state.get(LAYERS);
        if (!player.getHeldItem(handIn).isEmpty() && player.getHeldItem(handIn).getItem() == state.getBlock().asItem() && i < 8 && !player.isSneaking()) {
            worldIn.setBlockState(pos, state.with(LAYERS, i+1));
            worldIn.playSound(null,pos,state.getSoundType().getPlaceSound(), SoundCategory.BLOCKS,1.0f,1.0f);
            return ActionResultType.CONSUME;
        }
        return ActionResultType.FAIL;
    }

    /*
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        int i = state.get(LAYERS);
        if (useContext.getItem().getItem() == state.getBlock().asItem() && i < 8) {
            if (useContext.replacingClickedOnBlock()) {
                return useContext.getFace() == Direction.UP;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

     */

/*
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = context.getWorld().getBlockState(context.getPos());
        if (blockstate.matchesBlock(this)) {
            int i = blockstate.get(LAYERS);
            return blockstate.with(LAYERS, Math.min(8, i + 1));
        } else {
            return super.getStateForPlacement(context);
        }
    }

 */

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LAYERS);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (worldIn.getBlockState(pos.down()).matchesBlock(state.getBlock())) {
            int i = worldIn.getBlockState(pos.down()).get(LAYERS);
            int j = state.get(LAYERS);
            if (i+j<=8) {
                worldIn.setBlockState(pos.down(), state.with(LAYERS,i+j));
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            } else {
                worldIn.setBlockState(pos.down(), state.with(LAYERS,8));
                worldIn.setBlockState(pos, state.with(LAYERS, (i+j)-8 ));
            }
        } else if (worldIn.isAirBlock(pos.down()) || canFallThrough(worldIn.getBlockState(pos.down())) && pos.getY() >= 0) {
            FallingBlockEntity fallingblockentity = new FallingBlockEntity(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, worldIn.getBlockState(pos));
            this.onStartFalling(fallingblockentity);
            worldIn.addEntity(fallingblockentity);
        }
    }

    @Override
    public void onEndFalling(World worldIn, BlockPos pos, BlockState fallingState, BlockState hitState, FallingBlockEntity fallingBlock) {
        if (hitState.getBlock().equals(fallingState.getBlock())) {
            int i = hitState.get(LAYERS);
            int j = fallingState.get(LAYERS);

            if (i+j<=8) {
                worldIn.setBlockState(pos.down(), fallingState.with(LAYERS,i+j));
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
            } else {
                worldIn.setBlockState(pos.down(), fallingState.with(LAYERS,8));
                worldIn.setBlockState(pos, fallingState.with(LAYERS, (i+j)-8 ));
            }
        }
        super.onEndFalling(worldIn, pos, fallingState, hitState, fallingBlock);
    }

    public void addLayers(World worldIn, BlockPos botPos, BlockPos topPos, BlockState bottom, BlockState top) {
        int i = top.get(LAYERS);
        int j = bottom.get(LAYERS);

        if (i+j<=8) {
            worldIn.setBlockState(botPos, bottom.with(LAYERS,i+j));
            worldIn.setBlockState(topPos, Blocks.AIR.getDefaultState());
        } else {
            worldIn.setBlockState(botPos, bottom.with(LAYERS,8));
            worldIn.setBlockState(botPos.up(), bottom.with(LAYERS, (i+j)-8 ));
        }
    }

}
