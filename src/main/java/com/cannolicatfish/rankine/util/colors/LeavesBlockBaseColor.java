package com.cannolicatfish.rankine.util.colors;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;
import org.jetbrains.annotations.Nullable;

public class LeavesBlockBaseColor implements IBlockColor {
    @Override
    public int getColor(BlockState state, @Nullable IBlockDisplayReader reader, @Nullable BlockPos pos, int tint) {
        if (state.is(RankineBlocks.SPRUCE_LEAF_LITTER.get())) {
            return FoliageColors.getEvergreenColor();
        } else if (state.is(RankineBlocks.BIRCH_LEAF_LITTER.get())) {
            return FoliageColors.getBirchColor();
        } else {
            return reader != null && pos != null ? BiomeColors.getAverageFoliageColor(reader, pos) : FoliageColors.getDefaultColor();
        }
    }
}