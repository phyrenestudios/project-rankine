package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.world.biome.*;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DesertBiome;
import net.minecraft.world.biome.ForestBiome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = ProjectRankine.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBiomes {

    @SubscribeEvent
    public static void onRegisterBiome(RegistryEvent.Register<Biome> event) {
        ProjectRankine.LOGGER.debug("PR: Registering Biomes...");
        IForgeRegistry<Biome> registry = event.getRegistry();

        registerBiome(event, new CedarForestBiome(),"cedar_forest", 8, BiomeManager.BiomeType.COOL, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.OVERWORLD);
        registerBiome(event, new PinyonJuniperWoodlandBiome(),"pinyon_juniper_woodlands", 7, BiomeManager.BiomeType.WARM, BiomeDictionary.Type.SAVANNA, BiomeDictionary.Type.OVERWORLD);
        registerBiome(event, new HighlandPlateauBiome(),"highland_plateau", 8, BiomeManager.BiomeType.COOL, BiomeDictionary.Type.PLATEAU, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.OVERWORLD);
        registerBiome(event, new FelsenmeerBiome(),"felsenmeer", 8, BiomeManager.BiomeType.COOL, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.OVERWORLD);
        registerBiome(event, new ShoalBiome(),"shoal", 6, BiomeManager.BiomeType.WARM, BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.OVERWORLD);
        registerBiome(event, new DeadSwampBiome(),"dead_swamp", 8, BiomeManager.BiomeType.WARM, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.OVERWORLD);
        registerBiome(event, new TropicsBiome(),"tropics", 0, BiomeManager.BiomeType.WARM, BiomeDictionary.Type.BEACH, BiomeDictionary.Type.OVERWORLD);
        registerBiome(event, new ForestedLagoonBiome(),"forested_lagoon", 5, BiomeManager.BiomeType.WARM, BiomeDictionary.Type.BEACH, BiomeDictionary.Type.OVERWORLD);
        registerBiome(event, new HemlockGroveBiome(),"hemlock_grove", 8, BiomeManager.BiomeType.COOL, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.OVERWORLD);
        registerBiome(event, new HemlockHillsBiome(),"hemlock_hills", 0, BiomeManager.BiomeType.COOL, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.OVERWORLD);
        registerBiome(event, new SaltPlainsBiome(),"salt_plains", 2, BiomeManager.BiomeType.DESERT, BiomeDictionary.Type.HOT, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.OVERWORLD);
        registerBiome(event, new SaltSpikesBiome(),"salt_spikes", 1, BiomeManager.BiomeType.DESERT, BiomeDictionary.Type.HOT, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.OVERWORLD);
        registerBiome(event, new CrackedCrustBiome(),"cracked_crust", 1, BiomeManager.BiomeType.WARM, BiomeDictionary.Type.HOT, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.OVERWORLD);


        ProjectRankine.LOGGER.info("PR: Biomes Registered!");
    }

    private static void registerBiome(RegistryEvent.Register<Biome> event, Biome biome, String registryName, int spawnWeight, BiomeManager.BiomeType spawnType, BiomeDictionary.Type... types)
    {
        event.getRegistry().register(biome.setRegistryName(new ResourceLocation(ProjectRankine.MODID, registryName)));
        BiomeDictionary.addTypes(biome, types);
        BiomeManager.addBiome(spawnType, new BiomeManager.BiomeEntry(biome, spawnWeight));
    }


    @ObjectHolder("rankine:cedar_forest")
    public static Biome CEDAR_FOREST;

    @ObjectHolder("rankine:pinyon_juniper_woodlands")
    public static Biome PINYON_JUNIPER_WOODLANDS;

    @ObjectHolder("rankine:highland_plateau")
    public static Biome HIGHLAND_PLATEAU;

    @ObjectHolder("rankine:felsenmeer")
    public static Biome FELSENMEER;

    @ObjectHolder("rankine:shoal")
    public static Biome SHOAL;

    @ObjectHolder("rankine:dead_swamp")
    public static Biome DEAD_SWAMP;

    @ObjectHolder("rankine:tropics")
    public static Biome TROPICS;

    @ObjectHolder("rankine:forested_lagoon")
    public static Biome FORESTED_LAGOON;

    @ObjectHolder("rankine:hemlock_grove")
    public static Biome HEMLOCK_GROVE;

    @ObjectHolder("rankine:hemlock_hills")
    public static Biome HEMLOCK_HILLS;

    @ObjectHolder("rankine:salt_plains")
    public static Biome SALT_PLAINS;

    @ObjectHolder("rankine:salt_spikes")
    public static Biome SALT_SPIKES;

    @ObjectHolder("rankine:cracked_crust")
    public static Biome CRACKED_CRUST;
}

