package com.cannolicatfish.rankine.world.biome;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

import javax.annotation.Nullable;

public class BlueberryMeadowsBiome extends Biome {
    static final ConfiguredSurfaceBuilder SURFACE_BUILDER = new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG);
    static final RainType PRECIPATATION = RainType.RAIN;
    static final Category CATEGORY = Category.PLAINS;
    static final float DEPTH = 0.2F;
    static final float SCALE = 0.1F;
    static final float TEMPERATURE = 0.8F;
    static final float DOWNFALL = 0.6F;
    static final int WATER_COLOR = 4159204;
    static final int WATER_FOG_COLOR = 329011;
    static final int FOG_COLOR = 12638463;
    static final String PARENT = null;

    public BlueberryMeadowsBiome() {
        super((new Builder()).surfaceBuilder(SURFACE_BUILDER).precipitation(PRECIPATATION).category(CATEGORY).depth(DEPTH).scale(SCALE).temperature(TEMPERATURE).downfall(DOWNFALL).func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(WATER_COLOR).func_235248_c_(WATER_FOG_COLOR).func_235239_a_(FOG_COLOR).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_()).parent(PARENT));
        this.func_235063_a_(DefaultBiomeFeatures.field_235182_t_);
        this.func_235063_a_(DefaultBiomeFeatures.field_235134_a_);
        DefaultBiomeFeatures.func_235196_b_(this);
        this.func_235063_a_(DefaultBiomeFeatures.field_235187_y_);
        DefaultBiomeFeatures.addCarvers(this);
        DefaultBiomeFeatures.addLakes(this);
        DefaultBiomeFeatures.addMonsterRooms(this);
        RankineBiomeFeatures.addBlueberryFieldsDecor(this);
        DefaultBiomeFeatures.addPlainsTallGrass(this);
        DefaultBiomeFeatures.addStoneVariants(this);
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

    @Nullable
    @Override
    public Biome getHill(INoiseRandom rand) {
        return Biomes.FOREST;
    }

    @Override
    public int getGrassColor(double posX, double posZ) {
        return 6604880;
    }
}
