package com.cannolicatfish.rankine.util.colors;

import com.cannolicatfish.rankine.blocks.MetalPoleBlock;
import com.cannolicatfish.rankine.blocks.RankineLanternBlock;
import com.cannolicatfish.rankine.blocks.SheetmetalBlock;
import com.cannolicatfish.rankine.blocks.buildingmodes.GlazedPorcelainBlock;
import com.cannolicatfish.rankine.blocks.buildingmodes.MetalBarsBlock;
import com.cannolicatfish.rankine.blocks.buildingmodes.MetalLadderBlock;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class NonAlloyBlockColor implements BlockColor {
    @Override
    public int getColor(BlockState state, @Nullable BlockAndTintGetter reader, @Nullable BlockPos pos, int tint) {
        Block blk = state.getBlock();
        if (blk instanceof MetalPoleBlock) {
            return ((MetalPoleBlock) blk).getColor();
        } else if (blk instanceof MetalBarsBlock) {
            return ((MetalBarsBlock) blk).getColor();
        } else if (blk instanceof MetalLadderBlock) {
            return ((MetalLadderBlock) blk).getColor();
        } else if (blk instanceof SheetmetalBlock) {
            return ((SheetmetalBlock) blk).getColor();
        } else if (blk instanceof GlazedPorcelainBlock) {
            return ((GlazedPorcelainBlock) blk).getColor();
        } else if (blk instanceof RankineLanternBlock) {
            return ((RankineLanternBlock) blk).getColor();
        }

        return 16777215;
    }

}