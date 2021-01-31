package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.init.RankineFeatures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
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
        List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> LocalModifications = new ArrayList<>();
        if (Config.METEORITE_GEN.get()) {
            LocalModifications.add(new AbstractMap.SimpleEntry<>(RankineFeatures.METEORITE, getBiomeNamesFromCategory(Collections.emptyList(), false)));
        }
        //LocalModifications.add(new AbstractMap.SimpleEntry<>(ModFeatures.VOLCANO, getBiomeNamesFromCategory(Collections.emptyList(),false)));
        LocalModifications.add(new AbstractMap.SimpleEntry<>(RankineFeatures.FIRE_CLAY, getBiomeNamesFromCategory(Collections.emptyList(),false)));

        return LocalModifications;
    }

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getVegetalDecorationFeatures() {
        if (Config.RANKINE_FAUNA.get()) {
            return Arrays.asList(
                new AbstractMap.SimpleEntry<>(RankineFeatures.ELDERBERRY_BUSH,getBiomeNamesFromCategory(Arrays.asList(Biome.Category.FOREST, Biome.Category.PLAINS),true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.SNOWBERRY_BUSH,getBiomeNamesFromCategory(Arrays.asList(Biome.Category.EXTREME_HILLS, Biome.Category.ICY),true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.BLUEBERRY_BUSH,getBiomeNamesFromCategory(Arrays.asList(Biome.Category.RIVER, Biome.Category.PLAINS),true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.RASPBERRY_BUSH,getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.FOREST),true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.BLACKBERRY_BUSH,getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.FOREST),true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.CRANBERRY_BUSH,getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SWAMP),true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.STRAWBERRY_BUSH,getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.PLAINS),true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.PINEAPPLE_BUSH,getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.JUNGLE),true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.BANANA_YUCCA_BUSH,getBiomeNamesFromCategory(Arrays.asList(Biome.Category.SAVANNA, Biome.Category.DESERT, Biome.Category.MESA),true)),

                new AbstractMap.SimpleEntry<>(RankineFeatures.YELLOW_BIRCH_TREE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.FOREST), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.BLACK_BIRCH_TREE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.FOREST), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.EASTERN_HEMLOCK_TREE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.TAIGA), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.CEDAR_TREE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.TAIGA), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.COCONUT_PALM_TREE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.JUNGLE), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.PINYON_PINE_TREE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SAVANNA), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.BALSAM_FIR_TREE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SWAMP), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.DEAD_BALSAM_FIR_TREE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SWAMP), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.MAGNOLIA_TREE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.RIVER), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.JUNIPER_TREE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SAVANNA), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.MAPLE_TREE, getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.PLAINS), true))
            );
        } else {
            return Collections.emptyList();
        }
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
