package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.blocks.RankineBerryBushBlock;
import com.cannolicatfish.rankine.world.biome.RankineBiomes;
import com.cannolicatfish.rankine.world.gen.feature.CustomScatteredPlantFeature;
import com.cannolicatfish.rankine.world.gen.feature.RankineFeatures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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

public class DecorationGen
{
    public static void setupDecoration()
    {
        addBerryBushes(ModBlocks.ELDERBERRY_BUSH, Collections.singletonList(Blocks.GRASS_BLOCK), getBiomesFromCategory(Arrays.asList(Biome.Category.FOREST, Biome.Category.PLAINS), true));
        addBerryBushes(ModBlocks.SNOWBERRY_BUSH, Collections.singletonList(Blocks.GRASS_BLOCK), getBiomesFromCategory(Arrays.asList(Biome.Category.PLAINS, Biome.Category.EXTREME_HILLS, Biome.Category.TAIGA, Biome.Category.ICY), true));
        addBerryBushes(ModBlocks.BANANA_YUCCA_BUSH, Arrays.asList(Blocks.GRASS_BLOCK, Blocks.SAND, Blocks.RED_SAND), getBiomesFromCategory(Arrays.asList(Biome.Category.SAVANNA, Biome.Category.DESERT, Biome.Category.MESA),true));
        addBerryBushes(ModBlocks.PINEAPPLE_BUSH, Arrays.asList(Blocks.GRASS_BLOCK, Blocks.SAND, Blocks.RED_SAND), Collections.singletonList(RankineBiomes.TROPICS));

        addErratics(RankineFeatures.METEOR, ModBlocks.METEORIC_IRON_ORE.getDefaultState(), 2, 200, getBiomesFromCategory(Collections.emptyList(),false));
        addErratics(RankineFeatures.LARGE_ERRATIC, ModBlocks.ANORTHOSITE.getDefaultState(), 6, 300, getBiomesFromCategory(Collections.singletonList(Biome.Category.EXTREME_HILLS),false));
        addErratics(RankineFeatures.LARGE_ERRATIC, ModBlocks.ANORTHOSITE.getDefaultState(), 1, 200, getBiomesFromCategory(Collections.singletonList(Biome.Category.EXTREME_HILLS),false));

    }

    private static List<Biome> getBiomesFromCategory(List<Biome.Category> biomeCats, boolean include) {
        List<Biome> b = new ArrayList<>();
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (!biomeCats.isEmpty()) {
                for (Biome.Category cat : biomeCats) {
                    if (biome.getCategory() == cat && include){
                        b.add(biome);
                    }
                    if (!include && biome.getCategory() != cat && biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND && biome != RankineBiomes.MANTLE) {
                        b.add(biome);
                    }
                }
            }
            else if (!include && biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND && biome != RankineBiomes.MANTLE) {
                b.add(biome);
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

    private static void addErratics(Feature<BlockBlobConfig> feature, BlockState blockstate, int radius, int chance, List<Biome> biomes) {
        for (Biome b: biomes) {
            b.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, feature.withConfiguration(
                    new BlockBlobConfig(blockstate, radius)).withPlacement(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(chance))));
        }
    }

}
