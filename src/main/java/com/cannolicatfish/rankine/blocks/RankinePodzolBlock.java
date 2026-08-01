package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.MapCodec;
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
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.util.TriState;

public class RankinePodzolBlock extends SnowyDirtBlock {
    public static final MapCodec<RankinePodzolBlock> CODEC = MapCodec.unit(RankinePodzolBlock::new);

    @Override
    public MapCodec<? extends RankinePodzolBlock> codec() {
        return CODEC;
    }

    public RankinePodzolBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.PODZOL).strength(0.5F).sound(SoundType.GRAVEL));
    }

    // PORT-TODO(RankinePodzolBlock): Forge's IPlantable/PlantType system is gone. canSustainPlant now takes the
    // plant's BlockState directly instead of an IPlantable + PlantType query, so the old PLAINS/BEACH PlantType
    // cross-mod check can't be reproduced 1:1. The sugar-cane-adjacent-to-water special case (real vanilla
    // mechanic) is preserved; the generic PlantType allowance falls back to TriState.DEFAULT (let the plant
    // decide via its own canSurvive), which matches vanilla dirt/podzol behavior for all vanilla plants.
    @Override
    public TriState canSustainPlant(BlockState state, BlockGetter levelIn, BlockPos pos, Direction facing, BlockState plant) {
        if (plant.getBlock() instanceof SugarCaneBlock) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState blockstate1 = levelIn.getBlockState(pos.relative(direction));
                FluidState fluidstate = levelIn.getFluidState(pos.relative(direction));
                if (fluidstate.is(FluidTags.WATER) || blockstate1.is(Blocks.FROSTED_ICE)) {
                    return TriState.TRUE;
                }
            }
            return TriState.FALSE;
        }
        return TriState.DEFAULT;
    }
}
