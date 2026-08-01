package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.VanillaIntegration;
import net.neoforged.neoforge.event.world.WorldEvent;

public class WorldLoadHandler {
    public static void updateFuelValues(WorldEvent.Load event) {
        VanillaIntegration.populateFuelMap();
    }
}
