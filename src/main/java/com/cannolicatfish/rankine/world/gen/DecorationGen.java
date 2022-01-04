package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineFeatures;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.cannolicatfish.rankine.world.RankineBiomeFeatures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
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
            LocalModifications.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.METEORITE, WorldgenUtils.getBiomeNamesFromCategory(Collections.emptyList(), false)));
        }

        return LocalModifications;
    }

    private static List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> getVegetalDecorationFeatures() {
        List<AbstractMap.SimpleEntry<ConfiguredFeature<?,?>,List<ResourceLocation>>> VegetalDecor = new ArrayList<>();

        if (Config.MISC_WORLDGEN.RANKINE_FLORA.get()) {
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.ELDERBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.FOREST, Biome.Category.PLAINS),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.POKEBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.FOREST, Biome.Category.TAIGA),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.SNOWBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.EXTREME_HILLS, Biome.Category.ICY),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.BLUEBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.RIVER, Biome.Category.PLAINS),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.RASPBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.FOREST),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.BLACKBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.FOREST),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.CRANBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SWAMP),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.STRAWBERRY_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.PLAINS),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.PINEAPPLE_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.JUNGLE),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.BANANA_YUCCA_BUSH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.SAVANNA, Biome.Category.DESERT, Biome.Category.MESA),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.ALOE_PLANT,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.SAVANNA, Biome.Category.DESERT, Biome.Category.MESA),true)));

            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.GOLDENROD_PATCH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.SAVANNA, Biome.Category.DESERT, Biome.Category.MESA),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.RED_LILY_PATCH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.SAVANNA, Biome.Category.DESERT, Biome.Category.MESA),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.WHITE_LILY_PATCH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.EXTREME_HILLS),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.ORANGE_LILY_PATCH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.PLAINS),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.BLACK_MORNING_GLORY_PATCH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.JUNGLE),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.BLUE_MORNING_GLORY_PATCH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.JUNGLE),true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.PURPLE_MORNING_GLORY_PATCH,WorldgenUtils.getBiomeNamesFromCategory(Arrays.asList(Biome.Category.TAIGA),true)));
        }
        if (Config.MISC_WORLDGEN.RANKINE_TREES.get()) {
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.PETRIFIED_CHORUS_TREE, Arrays.asList(ResourceLocation.tryCreate("minecraft:end_barrens"),ResourceLocation.tryCreate("minecraft:end_highlands"),ResourceLocation.tryCreate("minecraft:end_midlands"),ResourceLocation.tryCreate("minecraft:small_end_islands"))));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.YELLOW_BIRCH_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.FOREST), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.BLACK_BIRCH_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.FOREST), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.EASTERN_HEMLOCK_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.TAIGA), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.CEDAR_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.TAIGA), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.PINYON_PINE_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SAVANNA), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.BALSAM_FIR_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SWAMP), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.DEAD_BALSAM_FIR_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SWAMP), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.MAGNOLIA_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.RIVER), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.JUNIPER_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.SAVANNA), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.ERYTHRINA_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.MESA), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.MAPLE_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.PLAINS), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.BLACK_WALNUT_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.RIVER), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.CORK_OAK_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.MUSHROOM), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.SHARINGA_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.JUNGLE), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineBiomeFeatures.CINNAMON_TREE, WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.JUNGLE), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.TEST_TREE.get().withConfiguration(RankineBiomeFeatures .TEST_TREE_CONFIG).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(1))),
                    WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.PLAINS), true)));
            VegetalDecor.add(new AbstractMap.SimpleEntry<>(RankineFeatures.COCONUT_PALM_TREE.get().withConfiguration(RankineBiomeFeatures .COCONUT_PALM_TREE_CONFIG).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.CHANCE.configure(new ChanceConfig(1))),
                    WorldgenUtils.getBiomeNamesFromCategory(Collections.singletonList(Biome.Category.BEACH), true)));
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
