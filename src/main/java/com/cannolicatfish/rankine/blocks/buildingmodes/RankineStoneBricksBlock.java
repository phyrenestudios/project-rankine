package com.cannolicatfish.rankine.blocks.buildingmodes;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class RankineStoneBricksBlock extends BuildingModeBlock {

    public RankineStoneBricksBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getMaxStyles() {
        return 2;
    }

}
