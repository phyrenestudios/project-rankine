package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.init.RankineFeatures;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber
public class DecorationGen {

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getLocalModificationFeatures() {
        List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> LocalModifications = new ArrayList<>();
        if (Config.MISC_WORLDGEN.METEORITE_GEN.get()) {
            LocalModifications.add(new AbstractMap.SimpleEntry<>(RankineFeatures.METEORITE, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false)));
        }

        return LocalModifications;
    }

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getVegetalDecorationFeatures() {
        List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> VegetalDecor = new ArrayList<>();

        if (Config.MISC_WORLDGEN.RANKINE_FLORA.get()) {
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ELDERBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.FOREST, Biome.Category.PLAINS),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.POKEBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.FOREST, Biome.Category.TAIGA),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.SNOWBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.EXTREME_HILLS, Biome.Category.ICY),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.BLUEBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.RIVER, Biome.Category.PLAINS),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.RASPBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.FOREST),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.BLACKBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.FOREST),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.CRANBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SWAMP),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.STRAWBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.PLAINS),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.PINEAPPLE_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.JUNGLE),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.BANANA_YUCCA_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.SAVANNA, Biome.Category.DESERT, Biome.Category.MESA),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ALOE_PLANT,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.SAVANNA, Biome.Category.DESERT, Biome.Category.MESA),true)));

            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.GOLDENROD_PATCH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.SAVANNA, Biome.Category.DESERT, Biome.Category.MESA),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.RED_LILY_PATCH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.SAVANNA, Biome.Category.DESERT, Biome.Category.MESA),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.WHITE_LILY_PATCH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.EXTREME_HILLS),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ORANGE_LILY_PATCH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.PLAINS),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.BLACK_MORNING_GLORY_PATCH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.JUNGLE),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.BLUE_MORNING_GLORY_PATCH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.JUNGLE),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.PURPLE_MORNING_GLORY_PATCH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.TAIGA),true)));
        }
        if (Config.MISC_WORLDGEN.RANKINE_TREES.get()) {
            //VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.PETRIFIED_CHORUS_TREE, Arrays.asList(ResourceLocation.tryCreate("minecraft:end_barrens"),ResourceLocation.tryCreate("minecraft:end_highlands"),ResourceLocation.tryCreate("minecraft:end_midlands"),ResourceLocation.tryCreate("minecraft:small_end_islands"))));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.YELLOW_BIRCH_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.FOREST), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.BLACK_BIRCH_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.FOREST), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.EASTERN_HEMLOCK_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.TAIGA), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.CEDAR_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.TAIGA), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.COCONUT_PALM_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.BEACH), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.PINYON_PINE_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SAVANNA), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.BALSAM_FIR_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SWAMP), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.DEAD_BALSAM_FIR_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SWAMP), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.MAGNOLIA_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.RIVER), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.JUNIPER_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SAVANNA), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.ERYTHRINA_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.MESA), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.MAPLE_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.PLAINS), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.BLACK_WALNUT_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.RIVER), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.CORK_OAK_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.MUSHROOM), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.SHARINGA_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.JUNGLE), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.CINNAMON_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.JUNGLE), true)));
        }

        return VegetalDecor;
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void addBiomeFeatures(BiomeLoadingEvent event) {
        if (event.getName() != null) {
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
