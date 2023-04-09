package com.cannolicatfish.rankine.fluids;

import com.cannolicatfish.rankine.blocks.gases.GasBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class CarbonDisulfideFlowingFluidBlock extends RankineFlowingFluidBlock {
    public CarbonDisulfideFlowingFluidBlock(Supplier<? extends FlowingFluid> supplier, boolean evaporates, Properties properties) {
        super(supplier, evaporates,  properties);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 300;
    }

    @Override
    public void onCaughtFire(BlockState state, Level world, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
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

    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (worldIn.getBlockState(pos).getBlock() instanceof CarbonDisulfideFlowingFluidBlock && worldIn.getBlockState(pos).isBurning(worldIn,pos)) {
            onCaughtFire(state, worldIn, pos, null, null);
        }
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }


}
