package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.world.gen.feature.MeteoriteFeature;
import com.cannolicatfish.rankine.world.gen.feature.MeteoriteFeatureConfig;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
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
        event.getRegistry().registerAll(
                METEORITE.setRegistryName("meteorite")
        );

        ProjectRankine.LOGGER.info("PR: Features Registered!");

    }

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }
}
