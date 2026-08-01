package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.world.BlockEvent;
import net.neoforged.bus.api.Event;

public class CreateFluidSourceHandler {
    public static void noWater(BlockEvent.CreateFluidSourceEvent event) {
        if (Config.GENERAL.DISABLE_WATER.get() && event.getPos().getY() > WorldgenUtils.waterTableHeight((Level) event.getWorld(), event.getPos())) {
            event.setResult(Event.Result.DENY);
        }
    }
}
