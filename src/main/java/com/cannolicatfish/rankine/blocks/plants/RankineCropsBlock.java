package com.cannolicatfish.rankine.blocks.plants;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraftforge.common.Tags;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class RankineCropsBlock extends CropBlock {
    public RankineCropsBlock(Properties builder) {
        super(builder);
    }

    public void placeAt(LevelAccessor worldIn, BlockPos pos, int flags) {
        worldIn.setBlock(pos, this.defaultBlockState().setValue(AGE, 7), flags);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        if (state.getValue(AGE) == 7) {
            return worldIn.getBlockState(pos.below()).is(Tags.Blocks.DIRT) || super.canSurvive(state, worldIn, pos);
        }
        return super.canSurvive(state, worldIn, pos);
    }
}
