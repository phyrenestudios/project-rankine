package com.cannolicatfish.rankine.blocks.plants;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class OatPlantBlock extends RankineCropsBlock {

    // Temperate, Moderate Humidity, Clay/Sandy Soils
    public OatPlantBlock(Properties properties) {
        super(properties,new float[]{0.5f,2,3,2,0.5f},new float[]{1,3,1},new float[]{2,1,3});
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return RankineItems.OAT_SEEDS.get();
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return state.is(Blocks.FARMLAND);
    }
}