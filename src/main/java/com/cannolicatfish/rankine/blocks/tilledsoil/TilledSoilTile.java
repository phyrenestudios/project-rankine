package com.cannolicatfish.rankine.blocks.tilledsoil;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;


import static com.cannolicatfish.rankine.init.RankineBlocks.TILLED_SOIL_TILE;

public class TilledSoilTile extends BlockEntity {

    public TilledSoilTile(BlockPos posIn, BlockState stateIn) {
        super(TILLED_SOIL_TILE, posIn, stateIn);
    }



}
