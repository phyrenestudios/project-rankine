package com.cannolicatfish.rankine.world.gen;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DecorationGen {



    @SubscribeEvent(priority = EventPriority.LOW)
    public static void addBiomeFeatures(BiomeLoadingEvent event) {

    }


}
