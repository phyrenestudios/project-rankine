package com.cannolicatfish.rankine.dimension;

import com.cannolicatfish.rankine.blocks.ModBlocks;
import com.cannolicatfish.rankine.blocks.RankineOre;
import com.cannolicatfish.rankine.entities.ModEntityTypes;
import com.cannolicatfish.rankine.world.biome.ModBiomes;
import com.cannolicatfish.rankine.world.feature.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.*;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MantleBiome extends Biome {
    private static final RankineOreFeature MANTLE_ORE = new RankineOreFeature(RankineOreFeatureConfig::deserialize);
    private static final ModifiedOreFeature MANTLE_ORELIKE = new ModifiedOreFeature(OreFeatureConfig::deserialize,ModBlocks.PERIDOTITE);

    public MantleBiome() {
        super((new Biome.Builder()).surfaceBuilder(new MantleSurfaceBuilder(SurfaceBuilderConfig::deserialize),new MantleSurfaceBuilderConfig(ModBlocks.PERIDOTITE.getDefaultState(),ModBlocks.PERIDOTITE.getDefaultState(),Blocks.MAGMA_BLOCK.getDefaultState())).precipitation(RainType.NONE).category(Category.NONE).depth(0.125F).scale(0.05F).temperature(0.8F).downfall(0.4F).waterColor(4159204).waterFogColor(329011).parent((String)null));
        this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(new MantleWorldCarver(ProbabilityConfig::deserialize, 256), new ProbabilityConfig(0.5285715F)));
        DefaultBiomeFeatures.addSprings(this);

        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(ModEntityTypes.MANTLE_GOLEM, 100, 1, 1));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(ModEntityTypes.DESMOXYTE, 100, 1, 3));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.MAGMA_CUBE, 5, 1, 1));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));

        //this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION,new DykeFeature(NoFeatureConfig::deserialize).withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).func_227228_a_(Placement.COUNT_HEIGHTMAP.func_227446_a_(new FrequencyConfig(2))));



        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, MANTLE_ORELIKE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                ModBlocks.GNEISS.getDefaultState(), 30)).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(0.4f, 221, 0, 255))));
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, MANTLE_ORELIKE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                ModBlocks.KIMBERLITE.getDefaultState(), 80)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 61, 0, 220))));
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, MANTLE_ORELIKE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                ModBlocks.KOMATIITE.getDefaultState(), 80)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(1, 61, 0, 220))));
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, MANTLE_ORELIKE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                ModBlocks.RINGWOODITE.getDefaultState(), 50)).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(0.5f, 21, 0, 60))));
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, MANTLE_ORELIKE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                ModBlocks.PEROVSKITE.getDefaultState(), 30)).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(0.4f, 0, 0, 20))));

        mantleReplaceGen(ModBlocks.MARBLE,this, 221, 256);
        mantleReplaceGen(ModBlocks.WADSLEYITE,this, 21, 60);
        mantleReplaceGen(ModBlocks.FERROPERICLASE,this, 0, 20);

        mantleGenDef(this, ModBlocks.CHROMITE_ORE, Arrays.asList(7, 8),20,0.15f,221,255, RankineOreFeatureConfig.RankineFillerBlockType.SEVENEIGHT);
        mantleGenDef(this, ModBlocks.ILMENITE_ORE, Arrays.asList(10, 12),50,0.2f,61,255, RankineOreFeatureConfig.RankineFillerBlockType.TENTWELVE);
        mantleGenDef(this,ModBlocks.WOLFRAMITE_ORE, Arrays.asList(10, 12),50,0.2f,61,255, RankineOreFeatureConfig.RankineFillerBlockType.TENTWELVE);
        mantleGenDef(this, ModBlocks.MOLYBDENITE_ORE, Arrays.asList(10, 12),50,0.15f,61,255, RankineOreFeatureConfig.RankineFillerBlockType.TENTWELVE);

        mantleGenDef(this, ModBlocks.COLUMBITE_ORE, Arrays.asList(10, 12),30,0.15f,61,255, RankineOreFeatureConfig.RankineFillerBlockType.TENTWELVE);
        mantleGenDef(this,ModBlocks.TANTALITE_ORE, Arrays.asList(10, 12),30,0.15f,61,255, RankineOreFeatureConfig.RankineFillerBlockType.TENTWELVE);
        mantleGenDef(this,ModBlocks.SPERRYLITE_ORE, Arrays.asList(10, 12),40,1f,61,220, RankineOreFeatureConfig.RankineFillerBlockType.TENTWELVE);
        mantleGenDef(this,ModBlocks.ANTHRACITE_ORE, Arrays.asList(10, 12),40,1f,61,255, RankineOreFeatureConfig.RankineFillerBlockType.TENTWELVE);
        mantleGenDef(this,ModBlocks.DIAMOND_ORE, Collections.singletonList(11),40,1f,61,220, RankineOreFeatureConfig.RankineFillerBlockType.ELEVEN);
        mantleGenDef(this,ModBlocks.PLUMBAGO_ORE, Collections.singletonList(11),40,1f,61,220, RankineOreFeatureConfig.RankineFillerBlockType.ELEVEN);
        mantleGenDef(this,ModBlocks.MOISSANITE_ORE, Collections.singletonList(11),40,1f,61,220, RankineOreFeatureConfig.RankineFillerBlockType.ELEVEN);
    }
    private static void mantleGenDef(Biome biome, RankineOre block, List<Integer> rocksSpawn, int veinSize, float chance, int minHeight, int maxHeight, RankineOreFeatureConfig.RankineFillerBlockType type) {
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, MANTLE_ORE.withConfiguration(
                new RankineOreFeatureConfig(type, block.getStateContainer().getBaseState(), veinSize)).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(chance, minHeight, 0, maxHeight))));

    }

    private static void mantleReplaceGen(Block block, Biome biome, int lowerBound, int upperBound) {

        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new ReplacerFeature(ReplacerFeatureConfig::deserialize).withConfiguration(
                            new ReplacerFeatureConfig(Blocks.STONE.getDefaultState(), block.getDefaultState(), lowerBound, upperBound)).withPlacement(new ReplacerPlacement(NoPlacementConfig::deserialize).configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));


    }


}
