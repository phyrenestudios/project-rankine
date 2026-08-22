package com.cannolicatfish.rankine.util.colors;

import com.cannolicatfish.rankine.blocks.alloys.AlloyBlockTile;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import org.jetbrains.annotations.Nullable;

/**
 * Tints the generic Alloy Block with its own alloy's colour.
 *
 * <p>1.18.2 read {@code te.getTileData().getInt("color")} — Forge's per-block-entity persistent NBT,
 * which nothing in the mod ever wrote — so this returned 16777215 for every alloy block and placed
 * alloy blocks were never tinted, in 1.18.2 either. The port kept that verbatim
 * ({@code getPersistentData()} is the renamed {@code getTileData()}). The alloy now rides on the
 * block entity's data components, so ask the block entity for it.
 */
public class AlloyBlockColor implements BlockColor {
    @Override
    public int getColor(BlockState p_getColor_1_, @Nullable BlockAndTintGetter p_getColor_2_, @Nullable BlockPos p_getColor_3_, int p_getColor_4_) {
        if (p_getColor_2_ != null && p_getColor_3_ != null) {
            BlockEntity te = p_getColor_2_.getBlockEntity(p_getColor_3_);
            if (te instanceof AlloyBlockTile alloyBlockTile) {
                return alloyBlockTile.getAlloyColor();
            }
        }
        return 16777215;
    }
}
