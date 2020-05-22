package com.cannolicatfish.rankine.world.biome;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;


@Mod.EventBusSubscriber(modid = "rankine", bus = Mod.EventBusSubscriber.Bus.MOD)
public class RankineBiomes {
    public static final DeferredRegister<Biome> BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES, ProjectRankine.MODID);
    public static final RegistryObject<Biome> CEDAR_FOREST = BIOMES.register("cedar_forest", CedarForestBiome::new);
    public static final RegistryObject<Biome> PINYON_JUNIPER_WOODLANDS = BIOMES.register("pinyon_juniper_woodlands", PinyonJuniperWoodlandBiome::new);
    public static final RegistryObject<Biome> HIGHLAND_PLATEAU = BIOMES.register("highland_plateau", HighlandPlateauBiome::new);
    public static final RegistryObject<Biome> FELSENMEER = BIOMES.register("felsenmeer", FelsenmeerBiome::new);
    public static final RegistryObject<Biome> SHOAL = BIOMES.register("shoal", ShoalBiome::new);
    public static final RegistryObject<Biome> DEAD_SWAMP_REG = BIOMES.register("dead_swamp", DeadSwampBiome::new);
    public static final RegistryObject<Biome> TROPICS_REG = BIOMES.register("tropics", TropicsBiome::new);


    @ObjectHolder("rankine:dead_swamp")
    public static DeadSwampBiome DEAD_SWAMP;

    @ObjectHolder("rankine:tropics")
    public static TropicsBiome TROPICS;






    public static final ResourceLocation BIOME_ID = new ResourceLocation(ProjectRankine.MODID, "mantle");
    @ObjectHolder("rankine:mantle")
    public static Biome MANTLE;




    public static void registerBiomesToDictionary() {

        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(CEDAR_FOREST.get(), 10));
   //     BiomeDictionary.addTypes(CEDAR_FOREST.get(),
   //             BiomeDictionary.Type.FOREST,
  //              BiomeDictionary.Type.CONIFEROUS,
   //             BiomeDictionary.Type.OVERWORLD);

        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(PINYON_JUNIPER_WOODLANDS.get(), 10));
    //    BiomeDictionary.addTypes(PINYON_JUNIPER_WOODLANDS.get(),
  //              BiomeDictionary.Type.SAVANNA,
   //             BiomeDictionary.Type.OVERWORLD);

        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(HIGHLAND_PLATEAU.get(), 10));
 //       BiomeDictionary.addTypes(HIGHLAND_PLATEAU.get(),
  //              BiomeDictionary.Type.PLATEAU,
  //              BiomeDictionary.Type.OVERWORLD);

        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(FELSENMEER.get(), 10));
    //    BiomeDictionary.addTypes(FELSENMEER.get(),
           //     BiomeDictionary.Type.SPARSE,
         //       BiomeDictionary.Type.FOREST,
       //         BiomeDictionary.Type.OVERWORLD);

        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(SHOAL.get(), 10));
     //   BiomeDictionary.addTypes(SHOAL.get(),
          //      BiomeDictionary.Type.BEACH,
        //        BiomeDictionary.Type.OVERWORLD);

        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(DEAD_SWAMP_REG.get(), 10));
      //  BiomeDictionary.addTypes(DEAD_SWAMP_REG.get(),
      //          BiomeDictionary.Type.SWAMP,
    //            BiomeDictionary.Type.OVERWORLD);

        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(TROPICS_REG.get(), 10));
     //   BiomeDictionary.addTypes(TROPICS_REG.get(),
   //             BiomeDictionary.Type.BEACH,
 //               BiomeDictionary.Type.OVERWORLD);

    }
}