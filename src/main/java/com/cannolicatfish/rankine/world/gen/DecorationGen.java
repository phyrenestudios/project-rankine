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

                new AbstractMap.SimpleEntry<>(ModFeatures.YELLOW_BIRCH_TREE,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.FOREST),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.BLACK_BIRCH_TREE,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.FOREST),true)),
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
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SWAMP),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.MAGNOLIA_TREE,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.RIVER),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.JUNIPER_TREE,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SAVANNA),true)),
                new AbstractMap.SimpleEntry<>(ModFeatures.MAPLE_TREE,
                        getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.PLAINS),true))
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
