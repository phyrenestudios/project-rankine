package com.cannolicatfish.rankine.util.colors;

import com.cannolicatfish.rankine.blocks.MetalPoleBlock;
import com.cannolicatfish.rankine.blocks.buildingmodes.MetalBarsBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import org.jetbrains.annotations.Nullable;

public class NonAlloyBlockColor implements BlockColor {
    @Override
    public int getColor(BlockState state, @Nullable BlockAndTintGetter reader, @Nullable BlockPos pos, int tint) {
        Block blk = state.getBlock();
        if (blk instanceof MetalPoleBlock) {
            return ((MetalPoleBlock) blk).getColor();
        } else if (blk instanceof MetalBarsBlock) {
            return ((MetalBarsBlock) blk).getColor();
        }

        return 16777215;
    }

}