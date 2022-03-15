package com.cannolicatfish.rankine.fluids;

import com.cannolicatfish.rankine.blocks.GasBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

import net.minecraft.block.AbstractBlock.Properties;

public class CarbonDisulfideFlowingFluidBlock extends RankineFlowingFluidBlock {
    public CarbonDisulfideFlowingFluidBlock(Supplier<? extends FlowingFluid> supplier, Properties properties) {
        super(supplier, properties);
    }

    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 300;
    }

    @Override
    public void catchFire(BlockState state, World world, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        if (!world.isClientSide && world.getRandom().nextFloat() < 0.002f) {
            for (int i = 0; i < 2; i++) {
                BlockPos close = BlockPos.findClosestMatch(pos,3,3,B -> world.isEmptyBlock(B) && !(world.getBlockState(B).getBlock() instanceof GasBlock)).orElse(null);
                if (close == null) {
                    break;
                } else {
                    world.setBlock(close, RankineBlocks.CARBON_DIOXIDE_GAS_BLOCK.get().defaultBlockState(),2);
                }
            }
            for (int i = 0; i < 1; i++) {
                BlockPos close = BlockPos.findClosestMatch(pos,3,3,B -> world.isEmptyBlock(B) && !(world.getBlockState(B).getBlock() instanceof GasBlock)).orElse(null);
                if (close == null) {
                    break;
                } else {
                    world.setBlock(pos,RankineBlocks.SULFUR_DIOXIDE_GAS_BLOCK.get().defaultBlockState(),3);
                }
            }
        }
        world.removeBlock(pos, false);
    }

    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (worldIn.getBlockState(pos).getBlock() instanceof CarbonDisulfideFlowingFluidBlock && worldIn.getBlockState(pos).isBurning(worldIn,pos)) {
            catchFire(state, worldIn, pos, null, null);
        }
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }


}
