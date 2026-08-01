package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.common.util.TriState;

public class RankineSandBlock extends ColoredFallingBlock {
    // ColoredFallingBlock.codec() is not wildcarded (returns the concrete MapCodec<ColoredFallingBlock>), so a
    // subclass with its own CODEC must match that exact signature; the cast is safe since generics are erased.
    public static final MapCodec<RankineSandBlock> CODEC = com.mojang.serialization.codecs.RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    ColorRGBA.CODEC.fieldOf("falling_dust_color").forGetter(b -> new ColorRGBA(b.dustColorIn)),
                    propertiesCodec()
            ).apply(instance, (color, properties) -> new RankineSandBlock(color.rgba(), properties))
    );

    private final int dustColorIn;

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec<ColoredFallingBlock> codec() {
        return (MapCodec<ColoredFallingBlock>) (MapCodec<?>) CODEC;
    }

    public RankineSandBlock(int dustColorIn, Properties properties) {
        super(new ColorRGBA(dustColorIn), properties);
        this.dustColorIn = dustColorIn;
    }

    // PORT-TODO(RankineSandBlock): Forge's IPlantable/PlantType system is gone. canSustainPlant now takes the
    // plant's BlockState directly instead of an IPlantable + PlantType query, so the old DESERT/BEACH PlantType
    // cross-mod check can't be reproduced 1:1. The sugar-cane-adjacent-to-water special case (real vanilla
    // mechanic) is preserved; the generic PlantType allowance falls back to TriState.DEFAULT (let the plant
    // decide via its own canSurvive), which matches vanilla sand behavior for all vanilla plants.
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
