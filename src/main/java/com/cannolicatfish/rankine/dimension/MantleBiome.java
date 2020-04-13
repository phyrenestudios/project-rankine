package com.cannolicatfish.rankine.dimension;

import com.cannolicatfish.rankine.blocks.ModBlocks;
import com.cannolicatfish.rankine.blocks.RankineOre;
import com.cannolicatfish.rankine.entities.ModEntityTypes;
import com.cannolicatfish.rankine.world.biome.ModBiomes;
import com.cannolicatfish.rankine.world.feature.*;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.UnderwaterCaveWorldCarver;
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
        this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(new MantleWorldCarver(ProbabilityConfig::deserialize, 256), new ProbabilityConfig(0.7285715F)));
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SPRING_FEATURE.withConfiguration( new LiquidsConfig(Fluids.LAVA.getDefaultState(), true, 4, 1, ImmutableSet.of(ModBlocks.BRIDGMANITE,ModBlocks.KIMBERLITE,ModBlocks.PEROVSKITE,ModBlocks.KOMATIITE,ModBlocks.PERIDOTITE,ModBlocks.WADSLEYITE,ModBlocks.FERROPERICLASE)))
                .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(200, 8, 16, 256))));


        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(ModEntityTypes.MANTLE_GOLEM, 5, 1, 1));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(ModEntityTypes.DIAMOND_MANTLE_GOLEM, 2, 1, 3));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(ModEntityTypes.PERIDOT_MANTLE_GOLEM, 2, 1, 1));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(ModEntityTypes.STEAMER, 1, 1, 1));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(ModEntityTypes.DESMOXYTE, 8, 1, 3));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(ModEntityTypes.DEMONYTE, 4, 1, 3));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(ModEntityTypes.DRAGONYTE, 6, 1, 3));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 1, 1, 4));


        intrusionGenDef(ModBlocks.KIMBERLITE,this,70,256,0.15f);

        rockGenDef(this, ModBlocks.GNEISS.getDefaultState(), ModBlocks.PERIDOTITE.getDefaultState(),40,0.2f,221,256);
        rockGenDef(this, ModBlocks.KOMATIITE.getDefaultState(),ModBlocks.PERIDOTITE.getDefaultState(),100,1,101,160);
        rockGenDef(this, ModBlocks.RINGWOODITE.getDefaultState(),ModBlocks.PERIDOTITE.getDefaultState(),50,0.5f,61,120);
        rockGenDef(this, ModBlocks.PEROVSKITE.getDefaultState(),ModBlocks.PERIDOTITE.getDefaultState(),50,0.5f,0,60);

        mantleReplaceGen(ModBlocks.MARBLE,this, 221, 256);
        mantleReplaceGen(ModBlocks.WADSLEYITE,this, 41, 120);
        mantleReplaceGen(ModBlocks.FERROPERICLASE,this, 0, 40);

        mantleGenDef(this, ModBlocks.PLUMBAGO_ORE,8,0.10f,221,256, RankineOreFeatureConfig.RankineFillerBlockType.MARBLE);
        mantleGenDef(this, ModBlocks.PENTLANDITE_ORE,30,0.30f,121,220, RankineOreFeatureConfig.RankineFillerBlockType.PERIDOTITE);
        mantleGenDef(this, ModBlocks.MAGNESITE_ORE,30,0.30f,121,220, RankineOreFeatureConfig.RankineFillerBlockType.PERIDOTITE);
        mantleGenDef(this, ModBlocks.GALENA_ORE,30,0.30f,121,220, RankineOreFeatureConfig.RankineFillerBlockType.PERIDOTITE);
        mantleGenDef(this, ModBlocks.ACANTHITE_ORE,30,0.30f,121,220, RankineOreFeatureConfig.RankineFillerBlockType.PERIDOTITE);
        mantleGenDef(this, ModBlocks.PYROLUSITE_ORE,30,0.30f,121,220, RankineOreFeatureConfig.RankineFillerBlockType.PERIDOTITE);
        mantleGenDef(this, ModBlocks.CHROMITE_ORE,15,1f,101,160, RankineOreFeatureConfig.RankineFillerBlockType.KOMATIITE);
        mantleGenDef(this, ModBlocks.ILMENITE_ORE,40,0.30f,41,160, RankineOreFeatureConfig.RankineFillerBlockType.PERIDOT_WADS);
        mantleGenDef(this, ModBlocks.WOLFRAMITE_ORE,40,0.30f,41,160, RankineOreFeatureConfig.RankineFillerBlockType.PERIDOT_WADS);
        mantleGenDef(this, ModBlocks.SPERRYLITE_ORE,30,0.30f,41,160, RankineOreFeatureConfig.RankineFillerBlockType.PERIDOT_WADS);
        mantleGenDef(this, ModBlocks.MOLYBDENITE_ORE,20,0.30f,41,160, RankineOreFeatureConfig.RankineFillerBlockType.PERIDOT_WADS);
        mantleGenDef(this, ModBlocks.COLUMBITE_ORE,20,0.30f,41,160, RankineOreFeatureConfig.RankineFillerBlockType.PERIDOT_WADS);
        mantleGenDef(this, ModBlocks.TANTALITE_ORE,20,0.30f,41,160, RankineOreFeatureConfig.RankineFillerBlockType.PERIDOT_WADS);
        mantleGenDef(this, ModBlocks.ANTHRACITE_ORE,30,10f,41,220, RankineOreFeatureConfig.RankineFillerBlockType.PERIDOT_WADS);
        mantleGenDef(this, ModBlocks.DIAMOND_ORE,30,1f,71,220, RankineOreFeatureConfig.RankineFillerBlockType.KIMBERLITE);
        mantleGenDef(this, ModBlocks.MOISSANITE_ORE,30,0.30f,71,220, RankineOreFeatureConfig.RankineFillerBlockType.KIMBERLITE);

        rockGenDef(this, ModBlocks.RINGWOODITE.getDefaultState(),ModBlocks.PERIDOTITE.getDefaultState(),50,1f,61,120);
        rockGenDef(this, ModBlocks.PEROVSKITE.getDefaultState(),ModBlocks.PERIDOTITE.getDefaultState(),50,1f,0,60);
        rockGenDef(this,ModBlocks.VANADINITE_ORE.getDefaultState().with(RankineOre.TYPE,10),ModBlocks.GALENA_ORE.getDefaultState().with(RankineOre.TYPE,10),8,1,121,220);
        rockGenDef(this,ModBlocks.BISMITE_ORE.getDefaultState().with(RankineOre.TYPE,10), ModBlocks.GALENA_ORE.getDefaultState().with(RankineOre.TYPE,10),8,1,121,220);
    }
    private static void mantleGenDef(Biome biome, RankineOre block, int veinSize, float chance, int minHeight, int maxHeight, RankineOreFeatureConfig.RankineFillerBlockType type) {
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, MANTLE_ORE.withConfiguration(
                new RankineOreFeatureConfig(type, block.getStateContainer().getBaseState(), veinSize)).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(chance, minHeight, 0, maxHeight))));

    }

    private static void mantleReplaceGen(Block block, Biome biome, int lowerBound, int upperBound) {

        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new ReplacerFeature(ReplacerFeatureConfig::deserialize).withConfiguration(
                            new ReplacerFeatureConfig(ModBlocks.PERIDOTITE.getDefaultState(), block.getDefaultState(), lowerBound, upperBound)).withPlacement(new ReplacerPlacement(NoPlacementConfig::deserialize).configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));


    }

    private static void intrusionGenDef(Block block, Biome biome, int lowerBound, int upperBound, float chance) {
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, new IntrusionReplacerFeature(ReplacerFeatureConfig::deserialize).withConfiguration(
                        new ReplacerFeatureConfig(ModBlocks.PERIDOTITE.getDefaultState(), block.getDefaultState(), lowerBound, upperBound)).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(chance, lowerBound, 0, upperBound))));

    }

    private static void rockGenDef(Biome biome, BlockState block, BlockState replace, int veinSize, int count, int minHeight, int maxHeight)
    {
        final Feature<OreFeatureConfig> MODULE = new ModularOreFeature(OreFeatureConfig::deserialize,replace);
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, MODULE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, block, veinSize))
                            .withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(count, minHeight, 0, maxHeight))));
    }
    private static void rockGenDef(Biome biome, BlockState block, BlockState replace, int veinSize, float chance, int minHeight, int maxHeight)
    {
        final Feature<OreFeatureConfig> MODULE = new ModularOreFeature(OreFeatureConfig::deserialize,replace);
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, MODULE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, block, veinSize))
                .withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(chance, minHeight, 0, maxHeight))));
    }
}

