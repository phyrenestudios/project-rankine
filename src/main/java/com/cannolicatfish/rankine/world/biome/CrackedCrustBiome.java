package com.cannolicatfish.rankine.world.biome;

import com.cannolicatfish.rankine.init.ModEntityTypes;
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
        super((new Builder()).surfaceBuilder(SURFACE_BUILDER).precipitation(PRECIPATATION).category(CATEGORY).depth(DEPTH).scale(SCALE).temperature(TEMPERATURE).downfall(DOWNFALL).func_235097_a_((new BiomeAmbience.Builder()).setWaterColor(WATER_COLOR).setWaterFogColor(WATER_FOG_COLOR).setFogColor(FOG_COLOR).setParticle(new ParticleEffectAmbience(ParticleTypes.WHITE_ASH, 0.118093334F)).setMoodSound(MoodSoundAmbience.field_235027_b_).build()).parent(PARENT));
        DefaultBiomeFeatures.func_235196_b_(this);
        RankineBiomeFeatures.addCrackedCrustStuff(this);
        this.func_235063_a_(DefaultBiomeFeatures.RUINED_PORTAL_MOUNTAIN);  //portal

        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(ModEntityTypes.MANTLE_GOLEM, 50, 2, 4));
    }

    @Override
    public int getSkyColor() {
        return 16757940;
    }
}