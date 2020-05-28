package com.cannolicatfish.rankine.world.biome;

import com.cannolicatfish.rankine.world.feature.RankineFeatures;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class TropicsBiome extends Biome {
    public TropicsBiome() {
       super((new Builder()).surfaceBuilder(SurfaceBuilder.SWAMP, SurfaceBuilder.SAND_SAND_GRAVEL_CONFIG)
               .precipitation(RainType.RAIN)
               .category(Category.BEACH)
               .depth(-0.1F).scale(0.05F)
               .temperature(0.9F)
               .downfall(0.2F)
               .waterColor(4445678)
               .waterFogColor(329011)
               .parent((String)null));

        this.addStructure(Feature.MINESHAFT.withConfiguration(new MineshaftConfig(0.004D, MineshaftStructure.Type.NORMAL)));
        this.addStructure(Feature.SHIPWRECK.withConfiguration(new ShipwreckConfig(true)));
        DefaultBiomeFeatures.addOceanCarvers(this);
        DefaultBiomeFeatures.addStructures(this);
        DefaultBiomeFeatures.addMonsterRooms(this);
        DefaultBiomeFeatures.addStoneVariants(this);
        DefaultBiomeFeatures.addOres(this);
        DefaultBiomeFeatures.addReedsAndPumpkins(this);
        DefaultBiomeFeatures.addSprings(this);
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SEA_PICKLE.withConfiguration(new CountConfig(20)).withPlacement(Placement.CHANCE_TOP_SOLID_HEIGHTMAP.configure(new ChanceConfig(16))));
        DefaultBiomeFeatures.func_222320_ai(this);
        DefaultBiomeFeatures.addFreezeTopLayer(this);
        RankineBiomeFeatures.addTropicsDecor(this);

        this.addSpawn(EntityClassification.WATER_CREATURE, new SpawnListEntry(EntityType.SQUID, 10, 4, 4));
        this.addSpawn(EntityClassification.WATER_CREATURE, new SpawnListEntry(EntityType.PUFFERFISH, 15, 1, 3));
        this.addSpawn(EntityClassification.WATER_CREATURE, new SpawnListEntry(EntityType.TROPICAL_FISH, 15, 1, 3));
        this.addSpawn(EntityClassification.WATER_CREATURE, new SpawnListEntry(EntityType.DOLPHIN, 2, 1, 2));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.TURTLE, 5, 2, 5));
        this.addSpawn(EntityClassification.AMBIENT, new SpawnListEntry(EntityType.BAT, 10, 8, 8));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.DROWNED, 5, 1, 1));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.WITCH, 5, 1, 1));
    }
}
