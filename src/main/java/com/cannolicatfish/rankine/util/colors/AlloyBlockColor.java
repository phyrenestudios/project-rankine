package com.cannolicatfish.rankine.util.colors;

import com.cannolicatfish.rankine.blocks.alloys.AlloyBlock;
import com.cannolicatfish.rankine.blocks.alloys.AlloyBlockTile;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import org.jetbrains.annotations.Nullable;

public class AlloyBlockColor implements BlockColor {
    @Override
    public int getColor(BlockState p_getColor_1_, @Nullable BlockAndTintGetter p_getColor_2_, @Nullable BlockPos p_getColor_3_, int p_getColor_4_) {
        if (p_getColor_2_ != null && p_getColor_3_ != null) {
            BlockEntity te = p_getColor_2_.getBlockEntity(p_getColor_3_);
            if (te instanceof AlloyBlockTile) {
                return te.getPersistentData().getInt("color") != 0 ? te.getPersistentData().getInt("color") : 16777215;
            }
        }
        return 16777215;
    }
}
