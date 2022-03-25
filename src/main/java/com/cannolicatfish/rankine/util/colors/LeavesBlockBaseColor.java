package com.cannolicatfish.rankine.util.colors;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.client.renderer.BiomeColors;
import org.jetbrains.annotations.Nullable;

public class LeavesBlockBaseColor implements BlockColor {
    @Override
    public int getColor(BlockState state, @Nullable BlockAndTintGetter reader, @Nullable BlockPos pos, int tint) {
        if (state.is(RankineBlocks.SPRUCE_LEAF_LITTER.get())) {
            return FoliageColor.getEvergreenColor();
        } else if (state.is(RankineBlocks.BIRCH_LEAF_LITTER.get())) {
            return FoliageColor.getBirchColor();
        } else {
            return reader != null && pos != null ? BiomeColors.getAverageFoliageColor(reader, pos) : FoliageColor.getDefaultColor();
        }
    }
}