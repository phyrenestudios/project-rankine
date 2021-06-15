package com.cannolicatfish.rankine.util.colors;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CrucibleColor implements IBlockColor {
    @Override
    public int getColor(BlockState p_getColor_1_, @Nullable IBlockDisplayReader p_getColor_2_, @Nullable BlockPos p_getColor_3_, int p_getColor_4_) {
        if (p_getColor_4_ == 0) {
            World worldIn = Minecraft.getInstance().world;
            if (p_getColor_3_ != null && worldIn != null) {
                TileEntity t = worldIn.getTileEntity(p_getColor_3_);
                if (t != null) {
                    CompoundNBT data = t.getTileData();
                    return data.getInt("color");
                }

            }
        }

        return 16777215;
    }
}