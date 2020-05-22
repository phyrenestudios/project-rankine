package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.blocks.ModBlocks;
import com.cannolicatfish.rankine.blocks.RankineBerryBushBlock;
import com.cannolicatfish.rankine.world.biome.RankineBiomes;
import com.cannolicatfish.rankine.world.feature.CustomScatteredPlantFeature;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.*;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TreeGen
{
    //public static final AbstractTreeFeature<NoFeatureConfig> CEDAR_TREE = new CedarTreeFeature(null, true, false, ModBlocks.CEDAR_LOG, ModBlocks.CEDAR_LEAVES, ModBlocks.CEDAR_SAPLING);
    //public static final AbstractTreeFeature<NoFeatureConfig> PINYON_TREE = new PinyonPineTreeFeature(null, true, false, ModBlocks.PINYON_PINE_LOG, ModBlocks.PINYON_PINE_LEAVES, ModBlocks.PINYON_PINE_SAPLING);
    //public static final AbstractTreeFeature<NoFeatureConfig> COCONUT_TREE = new CoconutPalmTreeFeature(null, true, false, ModBlocks.COCONUT_PALM_LOG, ModBlocks.COCONUT_PALM_LEAVES, ModBlocks.COCONUT_PALM_SAPLING);
    public static void setupTreeGeneration()
    {
        addBerryBushes(ModBlocks.ELDERBERRY_BUSH, Arrays.asList(Biome.Category.FOREST, Biome.Category.PLAINS),Arrays.asList(0,0), Collections.singletonList(Blocks.GRASS_BLOCK));
        addBerryBushes(ModBlocks.SNOWBERRY_BUSH, Arrays.asList(Biome.Category.PLAINS, Biome.Category.EXTREME_HILLS, Biome.Category.TAIGA, Biome.Category.ICY),Arrays.asList(0,1,0,1), Arrays.asList(Blocks.GRASS_BLOCK));
        addBerryBushes(ModBlocks.BANANA_YUCCA_BUSH, Arrays.asList(Biome.Category.SAVANNA, Biome.Category.DESERT, Biome.Category.MESA),Arrays.asList(1,0,0), Arrays.asList(Blocks.GRASS_BLOCK, Blocks.SAND, Blocks.RED_SAND));
        addBerryBushes(ModBlocks.PINEAPPLE_BUSH, Collections.singletonList(Biome.Category.BEACH), Collections.singletonList(1), Arrays.asList(Blocks.GRASS_BLOCK, Blocks.SAND, Blocks.RED_SAND));



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

    public static void addBerryBushes(RankineBerryBushBlock bush, List<Biome.Category> biomes, List<Integer> type, List<Block> blocks)
    {
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (biomes.contains(biome.getCategory()) && biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND && biome != RankineBiomes.MANTLE) {
                int index = biomes.indexOf(biome.getCategory());
                int retrieve = type.get(index);
                if (retrieve == 0) {
                    biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, new CustomScatteredPlantFeature(NoFeatureConfig::deserialize, bush.getDefaultState().with(SweetBerryBushBlock.AGE, Integer.valueOf(3)),blocks)
                            .withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(12))));
                }
                if (retrieve == 1)
                {
                    biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, new CustomScatteredPlantFeature(NoFeatureConfig::deserialize, bush.getDefaultState().with(SweetBerryBushBlock.AGE, Integer.valueOf(3)),blocks)
                            .withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(1))));
                }

            }
        }
    }
}
