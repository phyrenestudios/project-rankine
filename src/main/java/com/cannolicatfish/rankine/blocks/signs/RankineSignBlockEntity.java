package com.cannolicatfish.rankine.blocks.signs;

import com.cannolicatfish.rankine.init.RankineBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RankineSignBlockEntity extends SignBlockEntity {

    public RankineSignBlockEntity(BlockPos p_155700_, BlockState p_155701_) {
        super(RankineBlockEntityTypes.RANKINE_SIGN.get(), p_155700_, p_155701_);
    }
}
