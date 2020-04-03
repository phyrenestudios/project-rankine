package com.cannolicatfish.rankine.world.biome;

import com.cannolicatfish.rankine.blocks.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.ForestBiome;
import net.minecraft.world.biome.PlainsBiome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.BlockBlobConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.WoodedBadlandsSurfaceBuilder;

import java.util.Random;

public class FelsenmeerBiome extends Biome {
    public FelsenmeerBiome() {
        super((new Biome.Builder()).surfaceBuilder(new FelsenmeerSurfaceBuilder(SurfaceBuilderConfig::deserialize), new SurfaceBuilderConfig(ModBlocks.RHYOLITE.getDefaultState(), Blocks.STONE.getDefaultState(), Blocks.GRAVEL.getDefaultState())).precipitation(Biome.RainType.RAIN).category(Category.FOREST).depth(0.15F).scale(0.075F).temperature(0.8F).downfall(0.4F).waterColor(4159204).waterFogColor(329011).parent((String)null));
      this.addStructure(Feature.MINESHAFT.withConfiguration(new MineshaftConfig(0.004D, MineshaftStructure.Type.NORMAL)));
      this.addStructure(Feature.STRONGHOLD.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
      this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS,Feature.FOREST_ROCK.withConfiguration(new BlockBlobConfig(ModBlocks.ANDESITE.getDefaultState(),new Random().nextInt(2)))
              .withPlacement(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(2))));
      this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS,Feature.FOREST_ROCK.withConfiguration(new BlockBlobConfig(ModBlocks.DIORITE.getDefaultState(),new Random().nextInt(2)))
              .withPlacement(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(2))));
      DefaultBiomeFeatures.addCarvers(this);
      DefaultBiomeFeatures.addStructures(this);
      DefaultBiomeFeatures.addLakes(this);
      DefaultBiomeFeatures.addMonsterRooms(this);
      DefaultBiomeFeatures.addForestTrees(this);
      DefaultBiomeFeatures.func_222283_Y(this);
      DefaultBiomeFeatures.addStoneVariants(this);
      DefaultBiomeFeatures.addOres(this);
      DefaultBiomeFeatures.addSedimentDisks(this);
      DefaultBiomeFeatures.addOakTreesFlowersGrass(this);
      DefaultBiomeFeatures.addMushrooms(this);
      DefaultBiomeFeatures.addReedsAndPumpkins(this);
      DefaultBiomeFeatures.addSprings(this);
      DefaultBiomeFeatures.addFreezeTopLayer(this);
      this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.SHEEP, 12, 4, 4));
      this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.PIG, 10, 4, 4));
      this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.CHICKEN, 10, 4, 4));
      this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.COW, 8, 4, 4));
      this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.HORSE, 5, 2, 6));
      this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.DONKEY, 1, 1, 3));
      this.addSpawn(EntityClassification.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 10, 8, 8));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 100, 4, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.WITCH, 5, 1, 1));
}
}
