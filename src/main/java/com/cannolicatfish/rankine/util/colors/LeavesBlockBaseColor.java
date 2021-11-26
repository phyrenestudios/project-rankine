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
        if (state.matchesBlock(RankineBlocks.SPRUCE_LEAF_LITTER.get())) {
            return FoliageColors.getSpruce();
        } else if (state.matchesBlock(RankineBlocks.BIRCH_LEAF_LITTER.get())) {
            return FoliageColors.getBirch();
        } else {
            return reader != null && pos != null ? BiomeColors.getFoliageColor(reader, pos) : FoliageColors.getDefault();
        }
    }
}