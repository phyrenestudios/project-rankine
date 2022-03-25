package com.cannolicatfish.rankine.blocks.plants;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class AsparagusPlantBlock extends DoubleCropsBlock {

    public AsparagusPlantBlock(Properties properties) {
        super(properties);
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return Tags.Blocks.DIRT.contains(block);
    }

    protected ItemLike getBaseSeedId() {
        return RankineItems.ASPARAGUS_SEEDS.get();
    }

    public BlockBehaviour.OffsetType getOffsetType() {
        return BlockBehaviour.OffsetType.XZ;
    }


}
