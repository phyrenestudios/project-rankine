package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.*;
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

public class RopeBlock extends ScaffoldingBlock implements IWaterLoggable {
    VoxelShape voxelshape = Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    private static final VoxelShape field_220124_g = VoxelShapes.fullCube().withOffset(0.0D, -1.0D, 0.0D);

    public RopeBlock(Properties p_i49976_1_) {
        super(p_i49976_1_);
        this.setDefaultState(this.stateContainer.getBaseState().with(DISTANCE, 7).with(WATERLOGGED, Boolean.FALSE).with(BOTTOM, Boolean.FALSE));
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
        return !worldIn.getBlockState(pos.up()).isAir(worldIn, pos.up()) || !worldIn.getBlockState(pos.down()).isAir(worldIn, pos.down());
    }


    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        int i = func_220117_a(worldIn, pos);
        BlockState blockstate = state.with(DISTANCE, i).with(BOTTOM, this.func_220116_a(worldIn, pos, i));
        if (state != blockstate) {
            worldIn.setBlockState(pos, blockstate, 3);
        }

    }

    private boolean func_220116_a(IBlockReader p_220116_1_, BlockPos p_220116_2_, int p_220116_3_) {
        return p_220116_3_ > 0 && p_220116_1_.getBlockState(p_220116_2_.down()).getBlock() != this;
    }

    private boolean func_220116_b(IBlockReader p_220116_1_, BlockPos p_220116_2_, int p_220116_3_) {
        return p_220116_3_ > 0 && p_220116_1_.getBlockState(p_220116_2_.up()).getBlock() != this;
    }

    public static int func_220117_a(IBlockReader p_220117_0_, BlockPos p_220117_1_) {
        BlockPos.Mutable blockpos$mutable = p_220117_1_.toMutable().move(Direction.DOWN);
        BlockState blockstate = p_220117_0_.getBlockState(blockpos$mutable);
        int i = 7;
        if (blockstate.getBlock() == RankineBlocks.ROPE.get()) {
            i = blockstate.get(DISTANCE);
        } else if (blockstate.isSolidSide(p_220117_0_, blockpos$mutable, Direction.UP)) {
            return 0;
        }

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState blockstate1 = p_220117_0_.getBlockState(blockpos$mutable.setPos(p_220117_1_).move(direction));
            if (blockstate1.getBlock() == RankineBlocks.ROPE.get()) {
                i = Math.min(i, blockstate1.get(DISTANCE) + 1);
                if (i == 1) {
                    break;
                }
            }
        }

        return i;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        if (context.isSneaking()) {
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
