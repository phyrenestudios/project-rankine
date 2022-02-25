package com.cannolicatfish.rankine.util.colors;

import com.cannolicatfish.rankine.blocks.MetalPoleBlock;
import com.cannolicatfish.rankine.blocks.buildingmodes.MetalBarsBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import org.jetbrains.annotations.Nullable;

public class NonAlloyBlockColor implements IBlockColor {
    @Override
    public int getColor(BlockState state, @Nullable IBlockDisplayReader reader, @Nullable BlockPos pos, int tint) {
        Block blk = state.getBlock();
        if (blk instanceof MetalPoleBlock) {
            return ((MetalPoleBlock) blk).getColor();
        } else if (blk instanceof MetalBarsBlock) {
            return ((MetalBarsBlock) blk).getColor();
        }

        return 16777215;
    }

}