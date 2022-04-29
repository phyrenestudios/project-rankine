package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.VanillaIntegration;
import net.minecraftforge.event.world.WorldEvent;

public class WorldLoadHandler {
    public static void fuelValues( WorldEvent.Load event) {
        VanillaIntegration.populateFuelMap();
    }
}
