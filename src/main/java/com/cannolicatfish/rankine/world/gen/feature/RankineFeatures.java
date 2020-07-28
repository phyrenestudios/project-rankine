package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ProjectRankine.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RankineFeatures {

    public static final Feature<MeteoriteFeatureConfig> METEORITE = new MeteoriteFeature(MeteoriteFeatureConfig.AAAA);

    @SubscribeEvent
    public static void PRregisterFeatures(RegistryEvent.Register<Feature<?>> event) {
        ProjectRankine.LOGGER.debug("PR: Registering Features...");
        event.getRegistry().register(
                METEORITE.setRegistryName("meteorite")
        );

        ProjectRankine.LOGGER.info("PR: Features Registered!");

    }
}
