package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.Config;
import net.neoforged.neoforge.event.level.BlockGrowFeatureEvent;

public class SaplingGrowTreeHandler {
    // 1.21.1: SaplingGrowTreeEvent -> BlockGrowFeatureEvent; Event.Result.DENY -> setCanceled(true).
    public static void onSaplingGrow(BlockGrowFeatureEvent event) {
        if (event.getRandom().nextFloat() < 1 - Config.GENERAL.SAPLING_GROW.get()) {
            event.setCanceled(true);
        }
        // 1.18.2 had an empty spruce-sapling branch here; nothing to port.
    }
}
