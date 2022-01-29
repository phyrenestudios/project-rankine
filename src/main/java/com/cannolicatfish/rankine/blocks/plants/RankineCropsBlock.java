package com.cannolicatfish.rankine.blocks.plants;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.Tags;

public class RankineCropsBlock extends CropsBlock {
    public RankineCropsBlock(Properties builder) {
        super(builder);
    }

    public void placeAt(IWorld worldIn, BlockPos pos, int flags) {
        worldIn.setBlockState(pos, this.getDefaultState().with(AGE, 7), flags);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        if (state.get(AGE) == 7) {
            return worldIn.getBlockState(pos.down()).isIn(Tags.Blocks.DIRT) || super.isValidPosition(state, worldIn, pos);
        }
        return super.isValidPosition(state, worldIn, pos);
    }
}
