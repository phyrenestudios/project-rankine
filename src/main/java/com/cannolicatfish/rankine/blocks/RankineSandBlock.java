package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.SandBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class RankineSandBlock extends SandBlock {

    public RankineSandBlock(int dustColorIn, Properties properties) {
        super(dustColorIn, properties);
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        return plantable.getPlantType(world, pos.offset(facing)).equals(PlantType.DESERT) || plantable.getPlantType(world, pos.offset(facing)).equals(PlantType.BEACH);
    }
}
