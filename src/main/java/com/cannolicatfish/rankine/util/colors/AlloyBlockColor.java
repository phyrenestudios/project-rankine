package com.cannolicatfish.rankine.util.colors;

import com.cannolicatfish.rankine.blocks.alloys.AlloyBlock;
import com.cannolicatfish.rankine.blocks.alloys.AlloyBlockTile;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import org.jetbrains.annotations.Nullable;

public class AlloyBlockColor implements IBlockColor {
    @Override
    public int getColor(BlockState p_getColor_1_, @Nullable IBlockDisplayReader p_getColor_2_, @Nullable BlockPos p_getColor_3_, int p_getColor_4_) {
        if (p_getColor_2_ != null && p_getColor_3_ != null) {
            TileEntity te = p_getColor_2_.getTileEntity(p_getColor_3_);
            if (te instanceof AlloyBlockTile) {
                return te.getTileData().getInt("color") != 0 ? te.getTileData().getInt("color") : 16777215;
            }
        }
        return 16777215;
    }
}
