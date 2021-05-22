package com.cannolicatfish.rankine.blocks.signs;

import com.cannolicatfish.rankine.init.RankineTileEntityTypes;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class RankineSignTileEntity extends SignTileEntity {

    @Override
    public TileEntityType<?> getType() {
        return RankineTileEntityTypes.SIGN.get();
    }

}
