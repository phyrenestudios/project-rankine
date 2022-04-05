package com.cannolicatfish.rankine.blocks.signs;

import com.cannolicatfish.rankine.init.RankineTileEntities;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class RankineSignTile extends SignTileEntity {

    @Override
    public TileEntityType<?> getType() {
        return RankineTileEntities.RANKINE_SIGN.get();
    }
}
