package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.world.biome.*;
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

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = ProjectRankine.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RankineBiomes {

    public static final Biome CEDAR_FOREST = new CedarForestBiome();
    public static final Biome PINYON_JUNIPER_WOODLANDS = new PinyonJuniperWoodlandBiome();
    public static final Biome HIGHLAND_PLATEAU = new HighlandPlateauBiome();
    public static final Biome FELSENMEER = new FelsenmeerBiome();
    public static final Biome SHOAL = new ShoalBiome();
    public static final Biome DEAD_SWAMP = new DeadSwampBiome();
    public static final Biome TROPICS = new TropicsBiome();
    public static final Biome FORESTED_LAGOON = new ForestedLagoonBiome();
    public static final Biome HEMLOCK_GROVE = new HemlockGroveBiome();
    public static final Biome SALT_PLAINS = new SaltPlainsBiome();
    public static final Biome SALT_SPIKES = new SaltSpikesBiome();

    @SubscribeEvent
    public static void registerRankineBiomes(RegistryEvent.Register<Biome> event) {
        ProjectRankine.LOGGER.debug("PR: Registering Biomes...");
        IForgeRegistry<Biome> registry = event.getRegistry();

        registerBiome(registry, CEDAR_FOREST, "cedar_forest", true, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.OVERWORLD);
        registerBiome(registry, PINYON_JUNIPER_WOODLANDS, "pinyon_juniper_woodlands", true, BiomeDictionary.Type.SAVANNA, BiomeDictionary.Type.OVERWORLD);
        registerBiome(registry, HIGHLAND_PLATEAU, "highland_plateau", true, BiomeDictionary.Type.PLATEAU, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.OVERWORLD);
        registerBiome(registry, FELSENMEER, "felsenmeer", true, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.OVERWORLD);
        registerBiome(registry, SHOAL, "shoal", true, BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.OVERWORLD);
        registerBiome(registry, DEAD_SWAMP, "dead_swamp", true, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.OVERWORLD);
        registerBiome(registry, TROPICS, "tropics", true, BiomeDictionary.Type.BEACH, BiomeDictionary.Type.OVERWORLD);
        registerBiome(registry, FORESTED_LAGOON, "forested_lagoon", true, BiomeDictionary.Type.BEACH, BiomeDictionary.Type.OVERWORLD);
        registerBiome(registry, HEMLOCK_GROVE, "hemlock_grove", true, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.OVERWORLD);
        registerBiome(registry, SALT_PLAINS, "salt_plains", true, BiomeDictionary.Type.HOT, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.OVERWORLD);
        registerBiome(registry, SALT_SPIKES, "salt_spikes", true, BiomeDictionary.Type.HOT, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.OVERWORLD);

        ProjectRankine.LOGGER.info("PR: Biomes Registered!");
    }

    public static void addRankineBiomes() {
        ProjectRankine.LOGGER.debug("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(CEDAR_FOREST, 10));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(PINYON_JUNIPER_WOODLANDS, 10));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(HIGHLAND_PLATEAU, 10));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(FELSENMEER, 10));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(SHOAL, 10));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(DEAD_SWAMP, 10));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(TROPICS, 10));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(FORESTED_LAGOON, 10));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(HEMLOCK_GROVE, 10));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(SALT_PLAINS, 10));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(SALT_SPIKES, 10));


        /*
        addBiomeEntry(CEDAR_FOREST, 10, BiomeManager.BiomeType.COOL);
        addBiomeEntry(PINYON_JUNIPER_WOODLANDS, 10, BiomeManager.BiomeType.WARM);
        addBiomeEntry(HIGHLAND_PLATEAU, 10, BiomeManager.BiomeType.COOL);
        addBiomeEntry(FELSENMEER, 10, BiomeManager.BiomeType.WARM);
        addBiomeEntry(SHOAL, 10, BiomeManager.BiomeType.WARM);
        addBiomeEntry(DEAD_SWAMP, 10, BiomeManager.BiomeType.WARM);
        addBiomeEntry(TROPICS, 10, BiomeManager.BiomeType.WARM);
        addBiomeEntry(FORESTED_LAGOON, 10, BiomeManager.BiomeType.WARM);
        addBiomeEntry(HEMLOCK_GROVE, 10, BiomeManager.BiomeType.COOL);
        addBiomeEntry(SALT_PLAINS, 10, BiomeManager.BiomeType.DESERT);
        addBiomeEntry(SALT_SPIKES, 10, BiomeManager.BiomeType.DESERT);

         */
    }

    private static void registerBiome(IForgeRegistry<Biome> registry, Biome biome, String name, boolean spawn, BiomeDictionary.Type... types) {
        registry.register(biome.setRegistryName(ProjectRankine.MODID, name));
        if (spawn) {
            BiomeManager.addSpawnBiome(biome);
        }
        BiomeDictionary.addTypes(biome, types);
    }

    /*
    public static List<Integer> HOT = new ArrayList<>();
    public static List<Integer> COOL = new ArrayList<>();
    public static List<Integer> WARM = new ArrayList<>();
    public static List<Integer> ICY = new ArrayList<>();

    public static void addBiomeEntry(Biome biome, int weight, BiomeManager.BiomeType type) {
        if (weight > 0) {
            BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(biome, weight));

            if (type == BiomeManager.BiomeType.WARM)
                WARM.add(Registry.BIOME.getId(biome));
            if (type == BiomeManager.BiomeType.COOL)
                COOL.add(Registry.BIOME.getId(biome));
            if (type == BiomeManager.BiomeType.DESERT)
                HOT.add(Registry.BIOME.getId(biome));
            if (type == BiomeManager.BiomeType.ICY)
                ICY.add(Registry.BIOME.getId(biome));
        }


    }
     */


}