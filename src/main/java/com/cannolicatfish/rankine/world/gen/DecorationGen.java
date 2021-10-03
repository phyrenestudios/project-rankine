package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.init.RankineFeatures;
import com.cannolicatfish.rankine.init.WGConfig;
import com.cannolicatfish.rankine.util.WorldgenUtils;
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
public class DecorationGen {

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getTopLayerFeatures() {
        List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> TopLayer = new ArrayList<>();
        new AbstractMap.SimpleEntry<>(RankineFeatures.SNOW_GEN, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.EXTREME_HILLS), true));

        return TopLayer;
    }

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getLocalModificationFeatures() {
        List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> LocalModifications = new ArrayList<>();
        if (WGConfig.MISC.METEORITE_GEN.get()) {
            LocalModifications.add(new AbstractMap.SimpleEntry<>(RankineFeatures.METEORITE, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false)));
        }
        //LocalModifications.add(new AbstractMap.SimpleEntry<>(RankineFeatures.METEORITE, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false)));

        return LocalModifications;
    }

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getVegetalDecorationFeatures() {
        if (WGConfig.MISC.RANKINE_FAUNA.get()) {
            return Arrays.asList(
                new AbstractMap.SimpleEntry<>(RankineFeatures.ELDERBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.FOREST, Biome.Category.PLAINS),true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.SNOWBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.EXTREME_HILLS, Biome.Category.ICY),true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.BLUEBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.RIVER, Biome.Category.PLAINS),true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.CAMPHOR_BASIL_PLANT,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.RIVER, Biome.Category.JUNGLE),true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.RASPBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.FOREST),true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.BLACKBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.FOREST),true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.CRANBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SWAMP),true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.STRAWBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.PLAINS),true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.PINEAPPLE_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.JUNGLE),true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.BANANA_YUCCA_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.SAVANNA, Biome.Category.DESERT, Biome.Category.MESA),true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.ALOE_PLANT,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.SAVANNA, Biome.Category.DESERT, Biome.Category.MESA),true)),

                new AbstractMap.SimpleEntry<>(RankineFeatures.YELLOW_BIRCH_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.FOREST), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.BLACK_BIRCH_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.FOREST), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.EASTERN_HEMLOCK_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.TAIGA), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.CEDAR_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.TAIGA), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.COCONUT_PALM_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.BEACH), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.PINYON_PINE_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SAVANNA), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.BALSAM_FIR_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SWAMP), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.DEAD_BALSAM_FIR_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SWAMP), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.MAGNOLIA_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.RIVER), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.JUNIPER_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SAVANNA), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.ERYTHRINA_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.PLAINS), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.MAPLE_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.PLAINS), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.BLACK_WALNUT_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.RIVER), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.CORK_OAK_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.MUSHROOM), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.SHARINGA_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.JUNGLE), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.CINNAMON_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.JUNGLE), true)),
                new AbstractMap.SimpleEntry<>(RankineFeatures.SOIL_GEN, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false))

            );
        } else {
            return Collections.emptyList();
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void addBiomeFeatures(BiomeLoadingEvent event) {
        if (event.getName() != null) {
            List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> topLayerFeatures = getTopLayerFeatures();
            for (AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : topLayerFeatures) {
                if (entry.getValue().contains(event.getName())) {
                    event.getGeneration().withFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION.ordinal(),entry::getKey);
                }
            }

            List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> localModificationFeatures = getLocalModificationFeatures();
            for (AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : localModificationFeatures) {
                if (entry.getValue().contains(event.getName())) {
                    event.getGeneration().withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS.ordinal(),entry::getKey);
                }
            }

            List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> vegetalDecorationFeatures = getVegetalDecorationFeatures();
            for (AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>> entry : vegetalDecorationFeatures) {
                if (entry.getValue().contains(event.getName())) {
                    event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION.ordinal(), entry::getKey);
                }
            }
        }
    }


}
