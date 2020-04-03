package com.cannolicatfish.rankine.setup;

import com.cannolicatfish.rankine.dimension.ModDimensions;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public class ForgeEventHandlers {

    @SubscribeEvent
    public void serverLoad(FMLServerStartingEvent event) {

    }

    @SubscribeEvent
    public void onDimensionRegistry(RegisterDimensionsEvent event) {
        ModDimensions.MANTLE_DIMENSION = DimensionManager.registerOrGetDimension(ModDimensions.DIMENSION_ID, ModDimensions.MANTLE, null, true);
    }
}
