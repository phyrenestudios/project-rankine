package com.cannolicatfish.rankine.blocks.plants;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MilletPlantBlock extends RankineCropsBlock {

    // Warm, Dry Preference, Sandy-Silt soils
    public MilletPlantBlock(Properties properties) {
        super(properties,new float[]{0.25f,1,2,3,2},new float[]{3,2,1},new float[]{3,2,1});
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return RankineItems.MILLET_SEEDS.get();
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return state.is(Blocks.FARMLAND);
    }
}