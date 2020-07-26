package com.cannolicatfish.rankine.world.gen;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.registries.ForgeRegistries;
import java.util.ArrayList;
import java.util.List;

public class StructureGen {
    public static void setupStructureGen() {
        //structureGenDef(RankineFeatures.BEAVER_LODGE, Arrays.asList(RankineBiomes.DEAD_SWAMP, Biomes.SWAMP));
        //structureGenDef(RankineFeatures.TROPICS_HOUSE, Collections.singletonList(RankineBiomes.TROPICS));
        //structureGenDef(RankineFeatures.LAGOON_FOUNTAIN, Arrays.asList(RankineBiomes.FORESTED_LAGOON, Biomes.FLOWER_FOREST));
    }

    private static void structureGenDef(Structure<NoFeatureConfig> structure, List<Biome> biomes) {
        for (Biome b: biomes) {
            //b.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, structure.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
                    //.withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
            //b.addStructure(structure.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
        }
    }

    private static List<Biome> getBiomesFromCategory(Biome.Category cat, boolean include) {
        List<Biome> b = new ArrayList<>();
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (biome.getCategory() == cat && include) {
                b.add(biome);
            }
            if (!include && biome.getCategory() != cat && biome.getCategory() != Biome.Category.NETHER && biome.getCategory() != Biome.Category.THEEND) {
                b.add(biome);
            }
        }
        return b;
    }



}
