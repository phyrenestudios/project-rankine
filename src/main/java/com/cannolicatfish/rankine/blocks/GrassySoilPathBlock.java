package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class GrassySoilPathBlock extends GrassPathBlock {

    public GrassySoilPathBlock() {
        super(Block.Properties.of(Material.DIRT).sound(SoundType.GRAVEL).harvestTool(ToolType.SHOVEL).strength(0.5F).harvestLevel(0));
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        worldIn.setBlockAndUpdate(pos, pushEntitiesUp(state, RankineLists.SOIL_BLOCKS.get(RankineLists.PATH_BLOCKS.indexOf(state.getBlock())).defaultBlockState(), worldIn, pos));
    }
}
