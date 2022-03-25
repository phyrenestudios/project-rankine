package com.cannolicatfish.rankine.blocks;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class RankinePodzolBlock extends SnowyDirtBlock {
    public RankinePodzolBlock() {
        super(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.PODZOL).strength(0.5F).sound(SoundType.GRAVEL));
    }


    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return plantable.getPlantType(world, pos.relative(facing)).equals(PlantType.CAVE);
    }
}
