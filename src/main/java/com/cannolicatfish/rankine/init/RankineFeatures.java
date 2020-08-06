package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.world.gen.feature.MeteoriteFeature;
import com.cannolicatfish.rankine.world.gen.feature.MeteoriteFeatureConfig;
import com.cannolicatfish.rankine.world.gen.feature.SpikesFeature;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ProjectRankine.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RankineFeatures {

    public static final Feature<MeteoriteFeatureConfig> METEORITE = new MeteoriteFeature(MeteoriteFeatureConfig.AAAA);
    public static final Feature<NoFeatureConfig> SPIKE = new SpikesFeature(NoFeatureConfig.field_236558_a_);


    @SubscribeEvent
    public static void PRregisterFeatures(RegistryEvent.Register<Feature<?>> event) {
        ProjectRankine.LOGGER.debug("PR: Registering Features...");
        event.getRegistry().registerAll(
                METEORITE.setRegistryName("meteorite"),
                SPIKE.setRegistryName("spike")
        );

        ProjectRankine.LOGGER.info("PR: Features Registered!");

    }
}
