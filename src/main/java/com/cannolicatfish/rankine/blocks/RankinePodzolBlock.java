package com.cannolicatfish.rankine.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class RankinePodzolBlock extends SnowyDirtBlock {
    public RankinePodzolBlock() {
        super(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.PODZOL).strength(0.5F).sound(SoundType.GRAVEL));
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter levelIn, BlockPos pos, Direction facing, IPlantable plantable) {
        if (plantable instanceof SugarCaneBlock) {
            for(Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState blockstate1 = levelIn.getBlockState(pos.relative(direction));
                FluidState fluidstate = levelIn.getFluidState(pos.relative(direction));
                if (fluidstate.is(FluidTags.WATER) || blockstate1.is(Blocks.FROSTED_ICE)) {
                    return true;
                }
            }
            return false;
        }
        return plantable.getPlantType(levelIn, pos.relative(facing)).equals(PlantType.PLAINS) || plantable.getPlantType(levelIn, pos.relative(facing)).equals(PlantType.BEACH);
    }
}
