package com.cannolicatfish.rankine.blocks.plants;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.Tags;

import net.minecraft.block.AbstractBlock.Properties;

public class RankineCropsBlock extends CropsBlock {
    public RankineCropsBlock(Properties builder) {
        super(builder);
    }

    public void placeAt(IWorld worldIn, BlockPos pos, int flags) {
        worldIn.setBlock(pos, this.defaultBlockState().setValue(AGE, 7), flags);
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if (state.getValue(AGE) == 7) {
            return worldIn.getBlockState(pos.below()).is(Tags.Blocks.DIRT) || super.canSurvive(state, worldIn, pos);
        }
        return super.canSurvive(state, worldIn, pos);
    }
}
