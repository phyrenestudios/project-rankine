package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

public class GrassySoilPathBlock extends DirtPathBlock {

    public GrassySoilPathBlock() {
        super(Block.Properties.of(Material.DIRT).sound(SoundType.GRAVEL).strength(0.5F));
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, Random rand) {
        worldIn.setBlockAndUpdate(pos, pushEntitiesUp(state, RankineLists.SOIL_BLOCKS.get(RankineLists.PATH_BLOCKS.indexOf(state.getBlock())).defaultBlockState(), worldIn, pos));
    }
}
