package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.blocks.ModBlocks;
import com.cannolicatfish.rankine.blocks.RankineBerryBushBlock;
import com.cannolicatfish.rankine.world.biome.RankineBiomes;
import com.cannolicatfish.rankine.world.gen.feature.CustomScatteredPlantFeature;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.*;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TreeGen
{
    public static void setupTreeGeneration()
    {
        addBerryBushes(ModBlocks.ELDERBERRY_BUSH, Collections.singletonList(Blocks.GRASS_BLOCK), getBiomesFromCategory(Arrays.asList(Biome.Category.FOREST, Biome.Category.PLAINS), true));
        addBerryBushes(ModBlocks.SNOWBERRY_BUSH, Collections.singletonList(Blocks.GRASS_BLOCK), getBiomesFromCategory(Arrays.asList(Biome.Category.PLAINS, Biome.Category.EXTREME_HILLS, Biome.Category.TAIGA, Biome.Category.ICY), true));
        addBerryBushes(ModBlocks.BANANA_YUCCA_BUSH, Arrays.asList(Blocks.GRASS_BLOCK, Blocks.SAND, Blocks.RED_SAND), getBiomesFromCategory(Arrays.asList(Biome.Category.SAVANNA, Biome.Category.DESERT, Biome.Category.MESA),true));
        addBerryBushes(ModBlocks.PINEAPPLE_BUSH, Arrays.asList(Blocks.GRASS_BLOCK, Blocks.SAND, Blocks.RED_SAND), Collections.singletonList(RankineBiomes.TROPICS));


        /*for(Biome biome : ForgeRegistries.BIOMES)
        {
            if (biome.getCategory() == Biome.Category.RIVER || biome.getCategory() == Biome.Category.SWAMP) {
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(CEDAR_TREE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_EXTRA_HEIGHTMAP,
                        new AtSurfaceWithExtraConfig(2, 0.05F, 1)));
            }
            if (biome.getCategory() == Biome.Category.SAVANNA || biome.getCategory() == Biome.Category.DESERT || biome.getCategory() == Biome.Category.MESA) {
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(PINYON_TREE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_EXTRA_HEIGHTMAP,
                        new AtSurfaceWithExtraConfig(0, 0.05F, 1)));
            }
            if (biome.getCategory() == Biome.Category.BEACH || biome.getCategory() == Biome.Category.MUSHROOM) {
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(COCONUT_TREE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_EXTRA_HEIGHTMAP,
                        new AtSurfaceWithExtraConfig(1, 0.25F, 1)));
            }
        }*/
    }

    private static List<Biome> getBiomesFromCategory(List<Biome.Category> biomeCats, boolean include) {
        List<Biome> b = new ArrayList<>();
        for (Biome biome : ForgeRegistries.BIOMES) {
            for (Biome.Category cat : biomeCats) {
                if (biome.getCategory() == cat && include){
                    b.add(biome);
                }
                if (!include && biome.getCategory() != cat && biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND && biome != RankineBiomes.MANTLE) {
                    b.add(biome);
                }
            }
        }
        return b;
    }

    public static void addBerryBushes(RankineBerryBushBlock bush, List<Block> groundBlocks, List<Biome> biomes)
    {
        for (Biome b: biomes) {
     //           int index = biomes.indexOf(b);
    //            int retrieve = type.get(index);
 //               if (retrieve == 0) {
                    b.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, new CustomScatteredPlantFeature(NoFeatureConfig::deserialize, bush.getDefaultState().with(SweetBerryBushBlock.AGE, 3),groundBlocks)
                            .withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(5))));
   //             }
       //         if (retrieve == 1)
      //          {
     //               b.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, new CustomScatteredPlantFeature(NoFeatureConfig::deserialize, bush.getDefaultState().with(SweetBerryBushBlock.AGE, 3),groundBlocks)
    //                        .withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(1))));
   //             }

            }
    }



}
