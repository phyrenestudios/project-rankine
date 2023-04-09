package com.cannolicatfish.rankine.blocks.buildingmodes;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class RankinePolishedStoneBlock extends BuildingModeBlock {

    public RankinePolishedStoneBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getMaxStyles() {
        return 4;
    }
}
