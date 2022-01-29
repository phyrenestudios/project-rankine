package com.cannolicatfish.rankine.blocks.plants;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class OatPlantBlock extends RankineCropsBlock {

    public OatPlantBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected IItemProvider getSeedsItem() {
        return RankineItems.OAT_SEEDS.get();
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.matchesBlock(Blocks.FARMLAND);
    }
}