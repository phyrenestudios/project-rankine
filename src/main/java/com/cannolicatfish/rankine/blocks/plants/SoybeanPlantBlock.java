package com.cannolicatfish.rankine.blocks.plants;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SoybeanPlantBlock extends RankineCropsBlock {

    public SoybeanPlantBlock(Properties properties) {
        super(properties,new float[]{0.25f,1.5f,3,2,0.75f},new float[]{1,3,2},new float[]{0.5f,3.75f,1.75f});
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return RankineItems.SOYBEANS.get();
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return state.is(Blocks.FARMLAND);
    }
}