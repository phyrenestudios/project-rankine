package com.cannolicatfish.rankine.blocks.tilledsoil;

import com.cannolicatfish.rankine.init.RankineBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TilledSoilTile extends BlockEntity {

    public TilledSoilTile(BlockPos posIn, BlockState stateIn) {
        super(RankineBlockEntityTypes.TILLED_SOIL.get(), posIn, stateIn);
    }



}
