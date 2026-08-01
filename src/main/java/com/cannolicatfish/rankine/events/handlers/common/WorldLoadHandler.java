package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.VanillaIntegration;
import net.neoforged.neoforge.event.level.LevelEvent;

public class WorldLoadHandler {
    // 1.21.1: WorldEvent.Load -> LevelEvent.Load
    public static void updateFuelValues(LevelEvent.Load event) {
        VanillaIntegration.populateFuelMap();
    }
}
