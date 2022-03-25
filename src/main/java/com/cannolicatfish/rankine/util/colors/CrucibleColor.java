package com.cannolicatfish.rankine.util.colors;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class CrucibleColor implements BlockColor {
    @Override
    public int getColor(BlockState p_getColor_1_, @Nullable BlockAndTintGetter p_getColor_2_, @Nullable BlockPos p_getColor_3_, int p_getColor_4_) {
        if (p_getColor_4_ == 0) {
            Level worldIn = Minecraft.getInstance().level;
            if (p_getColor_3_ != null && worldIn != null) {
                BlockEntity t = worldIn.getBlockEntity(p_getColor_3_);
                if (t != null) {
                    CompoundTag data = t.getTileData();
                    return data.getInt("color");
                }

            }
        }

        return 16777215;
    }
}