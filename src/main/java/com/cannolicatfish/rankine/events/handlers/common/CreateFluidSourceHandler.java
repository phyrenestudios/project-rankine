package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.neoforged.neoforge.event.level.block.CreateFluidSourceEvent;

public class CreateFluidSourceHandler {
    // 1.21.1: BlockEvent.CreateFluidSourceEvent -> level.block.CreateFluidSourceEvent;
    // Event.Result.DENY -> setCanConvert(false).
    public static void noWater(CreateFluidSourceEvent event) {
        if (Config.GENERAL.DISABLE_WATER.get() && event.getPos().getY() > WorldgenUtils.waterTableHeight(event.getLevel(), event.getPos())) {
            event.setCanConvert(false);
        }
    }
}
