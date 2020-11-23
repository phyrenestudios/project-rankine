package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.blocks.RankineBerryBushBlock;
import com.cannolicatfish.rankine.init.ModFeatures;
import com.cannolicatfish.rankine.world.biome.RankineBiomeFeatures;
import com.cannolicatfish.rankine.world.gen.feature.CustomScatteredPlantFeature;
import com.cannolicatfish.rankine.world.gen.feature.MeteoriteFeature;
import com.cannolicatfish.rankine.world.gen.feature.MeteoriteFeatureConfig;
import com.cannolicatfish.rankine.init.RankineFeatures;
import net.minecraft.block.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
@Mod.EventBusSubscriber
public class DecorationGen
{
    public static void setupDecoration()
    {
        /*
        addBerryBushes(ModBlocks.ELDERBERRY_BUSH, Collections.singletonList(Blocks.GRASS_BLOCK), getBiomesFromCategory(Arrays.asList(Biome.Category.FOREST, Biome.Category.PLAINS), true));
        addBerryBushes(ModBlocks.SNOWBERRY_BUSH, Collections.singletonList(Blocks.GRASS_BLOCK), getBiomesFromCategory(Arrays.asList(Biome.Category.EXTREME_HILLS, Biome.Category.ICY), true));
        addBerryBushes(ModBlocks.BLUEBERRY_BUSH, Collections.singletonList(Blocks.GRASS_BLOCK), getBiomesFromCategory(Arrays.asList(Biome.Category.RIVER, Biome.Category.PLAINS), true));
        addBerryBushes(ModBlocks.RASPBERRY_BUSH, Collections.singletonList(Blocks.GRASS_BLOCK), getBiomesFromCategory(Collections.singletonList(Biome.Category.FOREST), true));
        addBerryBushes(ModBlocks.BLACKBERRY_BUSH, Collections.singletonList(Blocks.GRASS_BLOCK), getBiomesFromCategory(Collections.singletonList(Biome.Category.FOREST), true));
        addBerryBushes(ModBlocks.CRANBERRY_BUSH, Collections.singletonList(Blocks.GRASS_BLOCK), getBiomesFromCategory(Collections.singletonList(Biome.Category.SWAMP), true));
        addBerryBushes(ModBlocks.STRAWBERRY_BUSH, Collections.singletonList(Blocks.GRASS_BLOCK), getBiomesFromCategory(Collections.singletonList(Biome.Category.PLAINS), true));
        addBerryBushes(ModBlocks.PINEAPPLE_BUSH, Arrays.asList(Blocks.GRASS_BLOCK, Blocks.SAND, Blocks.RED_SAND), getBiomesFromCategory(Collections.singletonList(Biome.Category.JUNGLE), true));
        addBerryBushes(ModBlocks.BANANA_YUCCA_BUSH, Arrays.asList(ModBlocks.SANDY_DIRT, Blocks.GRASS_BLOCK, Blocks.SAND, Blocks.RED_SAND), getBiomesFromCategory(Arrays.asList(Biome.Category.SAVANNA, Biome.Category.DESERT, Biome.Category.MESA),true));

        addErratics(RankineFeatures.METEORITE, ModBlocks.METEORITE.getDefaultState(), 1, 100, getBiomesFromCategory(Collections.emptyList(),false));


        addTreesChance(RankineBiomeFeatures.YELLOW_BIRCH_TREE_CONFIG, 2, Arrays.asList(Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS, Biomes.FOREST, Biomes.FLOWER_FOREST));
        addTreesChance(RankineBiomeFeatures.BLACK_BIRCH_TREE_CONFIG, 1, Arrays.asList(Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS, Biomes.FOREST, Biomes.FLOWER_FOREST));
        addTreesCount(RankineBiomeFeatures.EASTERN_HEMLOCK_TREE_CONFIG, 1, Arrays.asList(Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.TAIGA_MOUNTAINS, Biomes.SNOWY_TAIGA, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.SNOWY_TAIGA_HILLS, Biomes.GIANT_SPRUCE_TAIGA));
        addTreesCount(RankineBiomeFeatures.CEDAR_TREE_CONFIG, 1, Arrays.asList(Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.TAIGA_MOUNTAINS, Biomes.SNOWY_TAIGA, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.SNOWY_TAIGA_HILLS, Biomes.GIANT_SPRUCE_TAIGA));
        addTreesCount(RankineBiomeFeatures.COCNUT_PALM_TREE_CONFIG, 1, getBiomesFromCategory(Collections.singletonList(Biome.Category.JUNGLE), true));
        addTreesCount(RankineBiomeFeatures.PINYON_PINE_TREE_CONFIG, 1, Arrays.asList(Biomes.SAVANNA_PLATEAU, Biomes.SHATTERED_SAVANNA_PLATEAU, Biomes.SHATTERED_SAVANNA));
        addTreesCount(RankineBiomeFeatures.BALSAM_FIR_TREE_CONFIG, 1, Arrays.asList(Biomes.SWAMP, Biomes.SWAMP_HILLS));
        addTreesCount(RankineBiomeFeatures.DEAD_BALSAM_FIR_TREE_CONFIG, 1, Arrays.asList(Biomes.SWAMP, Biomes.SWAMP_HILLS));

        addFoxfire(RankineBiomeFeatures.BLUE_FOXFIRE_CONFIG, 2, Arrays.asList(ModBiomes.CRACKED_CRUST));
        addFoxfire(RankineBiomeFeatures.GREEN_FOXFIRE_CONFIG, 2, Arrays.asList(ModBiomes.CRACKED_CRUST));
        addFoxfire(RankineBiomeFeatures.PINK_FOXFIRE_CONFIG, 2, Arrays.asList(ModBiomes.CRACKED_CRUST));
        addFoxfire(RankineBiomeFeatures.YELLOW_FOXFIRE_CONFIG, 2, Arrays.asList(ModBiomes.CRACKED_CRUST));
         */

    }

    private static List<ResourceLocation> getBiomeNamesFromCategory(List<Biome.Category> biomeCats, boolean include) {
        List<ResourceLocation> b = new ArrayList<>();
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (!biomeCats.isEmpty()) {
                for (Biome.Category cat : biomeCats) {
                    if (biome.getCategory() == cat && include){
                        b.add(biome.getRegistryName());
                    }
                    if (!include && biome.getCategory() != cat && biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
                        b.add(biome.getRegistryName());
                    }
                }
            }
            else if (!include && biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
                b.add(biome.getRegistryName());
            }
        }
        return b;
    }
/*
    public static void addFoxfire(BlockClusterFeatureConfig FEATURE, int CHANCE, List<Biome> biomes)
    {
        for (Biome b: biomes) {
            b.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.withConfiguration(FEATURE).withPlacement(Placement.CHANCE_HEIGHTMAP.configure(new ChanceConfig(CHANCE))));
        }
    }

    public static void addBerryBushes(RankineBerryBushBlock bush, List<Block> groundBlocks, List<Biome> biomes)
    {
        for (Biome b: biomes) {
            b.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, new CustomScatteredPlantFeature(NoFeatureConfig.field_236558_a_, bush.getDefaultState().with(SweetBerryBushBlock.AGE, 3),groundBlocks)
                    .withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(5))));
        }
    }

    public static void addTreesChance(BaseTreeFeatureConfig Tree, int chance,  List<Biome> biomes)
    {
        for (Biome b: biomes) {
            b.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236291_c_.withConfiguration(Tree).withPlacement(Placement.CHANCE_HEIGHTMAP.configure(new ChanceConfig(chance))));
        }
    }

    public static void addTreesCount(BaseTreeFeatureConfig Tree, int count,  List<Biome> biomes)
    {
        for (Biome b: biomes) {
            b.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_236291_c_.withConfiguration(Tree).withPlacement(Placement.COUNT_HEIGHTMAP.configure(new FrequencyConfig(count))));
        }
    }

    private static void addErratics(Feature<MeteoriteFeatureConfig> feature, BlockState blockstate, int radius, int chance, List<Biome> biomes) {
        for (Biome b: biomes) {
            b.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, feature.withConfiguration(
                    new MeteoriteFeatureConfig(blockstate, radius)).withPlacement(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(chance))));
        }
    }
*/

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getLocalModificationFeatures() {
        return Collections.singletonList(
                new AbstractMap.SimpleEntry<>(ModFeatures.METEORITE, getBiomeNamesFromCategory(Collections.emptyList(),false))

        );
    }

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getVegetalDecorationFeatures() {
        return Arrays.asList(
                new AbstractMap.SimpleEntry<>(ModFeatures.ELDERBERRY_BUSH,getBiomeNamesFromCategory(Arrays.asList(Biome.Category.FOREST, Biome.Category.PLAINS),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.SNOWBERRY_BUSH,getBiomeNamesFromCategory(Arrays.asList(Biome.Category.EXTREME_HILLS, Biome.Category.ICY),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.BLUEBERRY_BUSH,getBiomeNamesFromCategory(Arrays.asList(Biome.Category.RIVER, Biome.Category.PLAINS),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.RASPBERRY_BUSH,getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.FOREST),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.BLACKBERRY_BUSH,getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.FOREST),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.CRANBERRY_BUSH,getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SWAMP),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.STRAWBERRY_BUSH,getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.PLAINS),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.PINEAPPLE_BUSH,getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.JUNGLE),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.BANANA_YUCCA_BUSH,getBiomeNamesFromCategory(Arrays.asList(Biome.Category.SAVANNA, Biome.Category.DESERT, Biome.Category.MESA),true)),

                new AbstractMap.SimpleEntry<>(ModFeatures.YELLOW_BIRCH_TREE, Arrays.asList(Biomes.BIRCH_FOREST.getRegistryName(), Biomes.BIRCH_FOREST_HILLS.getRegistryName(),
                        Biomes.TALL_BIRCH_FOREST.getRegistryName(), Biomes.TALL_BIRCH_HILLS.getRegistryName(), Biomes.FOREST.getRegistryName(), Biomes.FLOWER_FOREST.getRegistryName())),
                new AbstractMap.SimpleEntry<>(ModFeatures.BLACK_BIRCH_TREE, Arrays.asList(Biomes.BIRCH_FOREST.getRegistryName(), Biomes.BIRCH_FOREST_HILLS.getRegistryName(),
                        Biomes.TALL_BIRCH_FOREST.getRegistryName(), Biomes.TALL_BIRCH_HILLS.getRegistryName(), Biomes.FOREST.getRegistryName(), Biomes.FLOWER_FOREST.getRegistryName())),
                new AbstractMap.SimpleEntry<>(ModFeatures.EASTERN_HEMLOCK_TREE,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.TAIGA),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.CEDAR_TREE,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.TAIGA),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.COCONUT_PALM_TREE,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.JUNGLE),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.PINYON_PINE_TREE,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SAVANNA),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.BALSAM_FIR_TREE,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SWAMP),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.DEAD_BALSAM_FIR_TREE,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SWAMP),true))
                );
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void addBiomeFeatures(BiomeLoadingEvent event)
    {
        if (event.getName() != null)
        {
            List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> localModificationFeatures = getLocalModificationFeatures();
            for (AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : localModificationFeatures)
            {
                if (entry.getValue().contains(event.getName()))
                {
                    event.getGeneration().withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS.ordinal(),entry::getKey);
                }
            }

            List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> vegetalDecorationFeatures = getVegetalDecorationFeatures();
            for (AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : vegetalDecorationFeatures)
            {
                if (entry.getValue().contains(event.getName()))
                {
                    event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION.ordinal(), entry::getKey);
                }
            }
            //System.out.println("Console log deltas");
            //System.out.println(Features.ORE_GOLD_DELTAS);
        }
    }


}
