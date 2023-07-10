package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.util.worldgen.OverworldReplacer;
import net.minecraftforge.event.level.LevelEvent;

public class WorldLoadHandler {
    public static void updateFuelValues(LevelEvent.Load event) {
        VanillaIntegration.populateFuelMap();
        OverworldReplacer.init(event.getLevel());
    }
}
