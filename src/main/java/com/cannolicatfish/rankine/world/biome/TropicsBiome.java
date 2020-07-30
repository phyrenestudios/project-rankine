package com.cannolicatfish.rankine.world.biome;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class TropicsBiome extends Biome {
 static final ConfiguredSurfaceBuilder SURFACE_BUILDER = new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.SAND_SAND_GRAVEL_CONFIG);
 static final RainType PRECIPATATION = RainType.RAIN;
 static final Category CATEGORY = Category.BEACH;
 static final float DEPTH = -0.1F;
 static final float SCALE = 0.05F;
 static final float TEMPERATURE = 0.9F;
 static final float DOWNFALL = 0.2F;
 static final int WATER_COLOR = 4445678;
 static final int WATER_FOG_COLOR = 329011;
 static final int FOG_COLOR = 16777180;
 static final String PARENT = null;

    public TropicsBiome() {
        super((new Builder()).surfaceBuilder(SURFACE_BUILDER).precipitation(PRECIPATATION).category(CATEGORY).depth(DEPTH).scale(SCALE).temperature(TEMPERATURE).downfall(DOWNFALL).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(WATER_COLOR).func_235248_c_(WATER_FOG_COLOR).func_235239_a_(FOG_COLOR).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).parent(PARENT));
     this.func_235063_a_(DefaultBiomeFeatures.field_235150_b_);
     this.func_235063_a_(DefaultBiomeFeatures.field_235180_r_);
     this.func_235063_a_(DefaultBiomeFeatures.field_235171_i_);
     this.func_235063_a_(DefaultBiomeFeatures.field_235187_y_);
        DefaultBiomeFeatures.addOceanCarvers(this);
        RankineBiomeFeatures.addModStructures(this);
        DefaultBiomeFeatures.addMonsterRooms(this);
        DefaultBiomeFeatures.addStoneVariants(this);
        DefaultBiomeFeatures.addOres(this);
        DefaultBiomeFeatures.addReedsAndPumpkins(this);
        DefaultBiomeFeatures.addSprings(this);
        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SEA_PICKLE.withConfiguration(new CountConfig(14)).withPlacement(Placement.CHANCE_TOP_SOLID_HEIGHTMAP.configure(new ChanceConfig(16))));
        DefaultBiomeFeatures.addSeagrass(this);
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
