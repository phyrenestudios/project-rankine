package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.block.*;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class RopeBlock extends ScaffoldingBlock {
    VoxelShape voxelshape = Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    private static final VoxelShape field_220124_g = VoxelShapes.fullCube().withOffset(0.0D, -1.0D, 0.0D);
    public RopeBlock(Properties p_i49976_1_) {
        super(p_i49976_1_);
        this.setDefaultState(this.stateContainer.getBaseState().with(field_220118_a, Integer.valueOf(7)).with(WATERLOGGED, Boolean.valueOf(false)).with(field_220120_c, Boolean.valueOf(false)));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (!context.hasItem(state.getBlock().asItem())) {
            return Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
        } else {

            return voxelshape;
        }

    }
    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return true;
    }


    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        int i = func_220117_a(worldIn, pos);
        BlockState blockstate = state.with(field_220118_a, Integer.valueOf(i)).with(field_220120_c, Boolean.valueOf(this.func_220116_a(worldIn, pos, i)));
        if (blockstate.get(field_220118_a) == 7) {
            if (state.get(field_220118_a) == 7) {
                worldIn.addEntity(new FallingBlockEntity(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, blockstate.with(WATERLOGGED, Boolean.valueOf(false))));
            } /*else {
                worldIn.destroyBlock(pos, true);
            }*/
        } else if (state != blockstate) {
            worldIn.setBlockState(pos, blockstate, 3);
        }

    }

    private boolean func_220116_a(IBlockReader p_220116_1_, BlockPos p_220116_2_, int p_220116_3_) {
        return p_220116_3_ > 0 && p_220116_1_.getBlockState(p_220116_2_.down()).getBlock() != this;
    }

    public static int func_220117_a(IBlockReader p_220117_0_, BlockPos p_220117_1_) {
        BlockPos.Mutable blockpos$mutable = (new BlockPos.Mutable(p_220117_1_)).move(Direction.DOWN);
        BlockState blockstate = p_220117_0_.getBlockState(blockpos$mutable);
        int i = 7;
        if (blockstate.getBlock() == ModBlocks.ROPE) {
            i = blockstate.get(field_220118_a);
        } else if (blockstate.isSolidSide(p_220117_0_, blockpos$mutable, Direction.UP)) {
            return 0;
        }

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState blockstate1 = p_220117_0_.getBlockState(blockpos$mutable.setPos(p_220117_1_).move(direction));
            if (blockstate1.getBlock() == ModBlocks.ROPE) {
                i = Math.min(i, blockstate1.get(field_220118_a) + 1);
                if (i == 1) {
                    break;
                }
            }
        }

        return i;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (context.func_225581_b_()) {
            return voxelshape;
        } else {
            return VoxelShapes.empty();
        }
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return false;
    }
}
