package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.world.biome.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;



public class RankineBiomes {
    public static final DeferredRegister<Biome> REGISTRY = DeferredRegister.create(ForgeRegistries.BIOMES, ProjectRankine.MODID);

    public static final Biome CEDAR_FOREST = add("cedar_forest", new CedarForestBiome());
    public static final Biome PINYON_JUNIPER_WOODLANDS = add("pinyon_juniper_woodlands", new PinyonJuniperWoodlandBiome());
    public static final Biome HIGHLAND_PLATEAU = add("highland_plateau", new HighlandPlateauBiome());
    public static final Biome FELSENMEER = add("felsenmeer", new FelsenmeerBiome());
    public static final Biome SHOAL = add("shoal", new ShoalBiome());
    public static final Biome DEAD_SWAMP = add("dead_swamp", new DeadSwampBiome());
    public static final Biome TROPICS = add("tropics", new TropicsBiome());
    public static final Biome FORESTED_LAGOON = add("forested_lagoon", new ForestedLagoonBiome());
    public static final Biome HEMLOCK_GROVE = add("hemlock_grove", new HemlockGroveBiome());
    public static final Biome SALT_PLAINS = add("salt_plains", new SaltPlainsBiome());
    public static final Biome SALT_SPIKES = add("salt_spikes", new SaltSpikesBiome());


    private static <T extends Biome> T add(String name, T biome) {
        System.out.print("Registering Biomes");
        REGISTRY.register(name, () -> biome);
        return biome;
    }

    public static void registerTypes(RegistryEvent.Register<?> event) {
        if (event.getRegistry().getRegistrySuperType() == Biome.class) {
            System.out.print("Registring Bipme Types");
            BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(RankineBiomes.CEDAR_FOREST, 10));
            BiomeManager.addSpawnBiome(RankineBiomes.CEDAR_FOREST);
            BiomeDictionary.addTypes(RankineBiomes.CEDAR_FOREST, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.OVERWORLD);

            BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(RankineBiomes.PINYON_JUNIPER_WOODLANDS, 10));
            BiomeManager.addSpawnBiome(RankineBiomes.PINYON_JUNIPER_WOODLANDS);
            BiomeDictionary.addTypes(RankineBiomes.PINYON_JUNIPER_WOODLANDS, BiomeDictionary.Type.SAVANNA, BiomeDictionary.Type.OVERWORLD);

            BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(RankineBiomes.HIGHLAND_PLATEAU, 10));
            BiomeManager.addSpawnBiome(RankineBiomes.HIGHLAND_PLATEAU);
            BiomeDictionary.addTypes(RankineBiomes.HIGHLAND_PLATEAU, BiomeDictionary.Type.PLATEAU, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.OVERWORLD);

            BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(RankineBiomes.FELSENMEER, 10));
            BiomeManager.addSpawnBiome(RankineBiomes.FELSENMEER);
            BiomeDictionary.addTypes(RankineBiomes.FELSENMEER, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.OVERWORLD);

            BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(RankineBiomes.SHOAL, 10));
            BiomeManager.addSpawnBiome(RankineBiomes.SHOAL);
            BiomeDictionary.addTypes(RankineBiomes.SHOAL, BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.OVERWORLD);

            BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(RankineBiomes.DEAD_SWAMP, 10));
            BiomeManager.addSpawnBiome(RankineBiomes.DEAD_SWAMP);
            BiomeDictionary.addTypes(RankineBiomes.DEAD_SWAMP, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.OVERWORLD);

            BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(RankineBiomes.TROPICS, 7));
            BiomeManager.addSpawnBiome(RankineBiomes.TROPICS);
            BiomeDictionary.addTypes(RankineBiomes.TROPICS, BiomeDictionary.Type.BEACH, BiomeDictionary.Type.OVERWORLD);

            BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(RankineBiomes.FORESTED_LAGOON, 5));
            BiomeManager.addSpawnBiome(RankineBiomes.FORESTED_LAGOON);
            BiomeDictionary.addTypes(RankineBiomes.FORESTED_LAGOON, BiomeDictionary.Type.BEACH, BiomeDictionary.Type.OVERWORLD);

            BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(RankineBiomes.HEMLOCK_GROVE, 10));
            BiomeManager.addSpawnBiome(RankineBiomes.HEMLOCK_GROVE);
            BiomeDictionary.addTypes(RankineBiomes.HEMLOCK_GROVE, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.OVERWORLD);

            BiomeManager.addBiome(BiomeManager.BiomeType.DESERT, new BiomeManager.BiomeEntry(RankineBiomes.SALT_PLAINS, 5));
            BiomeManager.addSpawnBiome(RankineBiomes.SALT_PLAINS);
            BiomeDictionary.addTypes(RankineBiomes.SALT_PLAINS, BiomeDictionary.Type.HOT, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.OVERWORLD);

            BiomeManager.addBiome(BiomeManager.BiomeType.DESERT, new BiomeManager.BiomeEntry(RankineBiomes.SALT_SPIKES, 1));
            BiomeManager.addSpawnBiome(RankineBiomes.SALT_SPIKES);
            BiomeDictionary.addTypes(RankineBiomes.SALT_SPIKES, BiomeDictionary.Type.HOT, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.OVERWORLD);

        }
    }


    public static final ResourceLocation BIOME_ID = new ResourceLocation(ProjectRankine.MODID, "mantle");
    @ObjectHolder("rankine:mantle")
    public static Biome MANTLE;


}