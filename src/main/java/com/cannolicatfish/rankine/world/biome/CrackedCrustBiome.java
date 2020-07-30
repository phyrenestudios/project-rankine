package com.cannolicatfish.rankine.world.biome;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class CrackedCrustBiome extends Biome {
    static final ConfiguredSurfaceBuilder SURFACE_BUILDER = new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, RankineSurfaceBuilder.VOLCANO);
    static final RainType PRECIPATATION = RainType.NONE;
    static final Category CATEGORY = Category.EXTREME_HILLS;
    static final float DEPTH = 0.2F;
    static final float SCALE = 0.8F;
    static final float TEMPERATURE = 3.0F;
    static final float DOWNFALL = 0.0F;
    static final int WATER_COLOR = 4159204;
    static final int WATER_FOG_COLOR = 329011;
    static final int FOG_COLOR = 16745110;
    static final String PARENT = null;

    public CrackedCrustBiome() {
        super((new Builder()).surfaceBuilder(SURFACE_BUILDER).precipitation(PRECIPATATION).category(CATEGORY).depth(DEPTH).scale(SCALE).temperature(TEMPERATURE).downfall(DOWNFALL).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(WATER_COLOR).func_235248_c_(WATER_FOG_COLOR).func_235239_a_(FOG_COLOR).func_235244_a_(new ParticleEffectAmbience(ParticleTypes.WHITE_ASH, 0.118093334F)).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).parent(PARENT));
        DefaultBiomeFeatures.func_235196_b_(this);
        RankineBiomeFeatures.addCrackedCrustStuff(this);
        this.func_235063_a_(DefaultBiomeFeatures.field_235133_E_);  //portal

        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.CREEPER, 200, 4, 10));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.WITCH, 5, 1, 1));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ZOMBIE, 19, 4, 4));
    }

    @Override
    public int getSkyColor() {
        return 16757940;
    }
}