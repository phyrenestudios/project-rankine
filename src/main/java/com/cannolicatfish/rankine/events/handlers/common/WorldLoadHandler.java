package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.util.worldgen.OverworldReplacer;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.level.LevelEvent;

public class WorldLoadHandler {
    public static void updateFuelValues(LevelEvent.Load event) {
        System.out.println(event.getPhase());
        System.out.println(event.getLevel());
        if (event.getLevel() instanceof ServerLevel) VanillaIntegration.populateFuelMap();
        if (event.getLevel() instanceof ServerLevel) OverworldReplacer.init(event.getLevel());
    }
}
