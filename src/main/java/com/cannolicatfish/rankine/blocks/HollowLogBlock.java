package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class HollowLogBlock extends RotatedPillarBlock {
    public HollowLogBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(AXIS)) {
            case X:
                return VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(),makeCuboidShape(0,2,2,16,14,14), IBooleanFunction.ONLY_FIRST);
            case Z:
                return VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(),makeCuboidShape(2,2,0,14,14,16), IBooleanFunction.ONLY_FIRST);
            case Y:
            default:
                return VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(),makeCuboidShape(2,0,2,14,16,14), IBooleanFunction.ONLY_FIRST);
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos) {
        switch (state.get(AXIS)) {
            case X:
                return VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(),makeCuboidShape(0,2,2,16,14,14), IBooleanFunction.ONLY_FIRST);
            case Z:
                return VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(),makeCuboidShape(2,2,0,14,14,16), IBooleanFunction.ONLY_FIRST);
            case Y:
            default:
                return VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(),makeCuboidShape(2,0,2,14,16,14), IBooleanFunction.ONLY_FIRST);
        }
    }

    @Override
    public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
        switch (state.get(AXIS)) {
            case X:
                return VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(),makeCuboidShape(0,2,2,16,14,14), IBooleanFunction.ONLY_FIRST);
            case Z:
                return VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(),makeCuboidShape(2,2,0,14,14,16), IBooleanFunction.ONLY_FIRST);
            case Y:
            default:
                return VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(),makeCuboidShape(2,0,2,14,16,14), IBooleanFunction.ONLY_FIRST);
        }
    }


    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        if (fallDistance > 1.0f && worldIn.getRandom().nextFloat() < 0.2) {
            worldIn.destroyBlock(pos,true);
        }
        super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
    }
}
