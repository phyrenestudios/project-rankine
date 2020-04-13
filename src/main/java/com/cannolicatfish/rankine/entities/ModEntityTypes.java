package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

public class ModEntityTypes {
    public static final EntityType<SpearEntity> FLINT_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world,ModEntityTypes.FLINT_SPEAR,0)).size(0.5F, 0.5F),"flint_spear");
    public static final EntityType<SpearEntity> BRONZE_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world,ModEntityTypes.BRONZE_SPEAR,1)).size(0.5F, 0.5F),"bronze_spear");
    public static final EntityType<SpearEntity> IRON_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world,ModEntityTypes.IRON_SPEAR,2)).size(0.5F, 0.5F),"iron_spear");
    public static final EntityType<SpearEntity> STEEL_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world,ModEntityTypes.STEEL_SPEAR,3)).size(0.5F, 0.5F),"steel_spear");
    public static final EntityType<MantleGolemEntity> MANTLE_GOLEM = EntityType.Builder.create(MantleGolemEntity::new, EntityClassification.MONSTER).build(ProjectRankine.MODID + ":mantle_golem");
    public static final EntityType<DiamondMantleGolemEntity> DIAMOND_MANTLE_GOLEM = EntityType.Builder.create(DiamondMantleGolemEntity::new, EntityClassification.MONSTER).build(ProjectRankine.MODID + ":diamond_mantle_golem");
    public static final EntityType<PeridotMantleGolemEntity> PERIDOT_MANTLE_GOLEM = EntityType.Builder.create(PeridotMantleGolemEntity::new, EntityClassification.MONSTER).build(ProjectRankine.MODID + ":peridot_mantle_golem");
    public static final EntityType<DesmoxyteEntity> DESMOXYTE = EntityType.Builder.create(DesmoxyteEntity::new, EntityClassification.MONSTER).size(0.5F, 0.3F).build(ProjectRankine.MODID + ":desmoxyte");
    public static final EntityType<DemonyteEntity> DEMONYTE = EntityType.Builder.create(DemonyteEntity::new, EntityClassification.MONSTER).size(0.5F, 0.3F).build(ProjectRankine.MODID + ":demonyte");
    public static final EntityType<DragonyteEntity> DRAGONYTE = EntityType.Builder.create(DragonyteEntity::new, EntityClassification.MONSTER).size(0.5F, 0.3F).build(ProjectRankine.MODID + ":dragonyte");
    public static final EntityType<SteamerEntity> STEAMER = EntityType.Builder.create(SteamerEntity::new, EntityClassification.MONSTER).build(ProjectRankine.MODID + ":steamer");
    public static final EntityType<BeaverEntity> BEAVER = EntityType.Builder.create(BeaverEntity::new, EntityClassification.CREATURE).build(ProjectRankine.MODID + ":beaver");

    public static <T extends Entity> EntityType<T> prepareEntity(EntityType.Builder builder, String name)
    {
        return (EntityType<T>) builder.build(ProjectRankine.MODID + '.' + name).setRegistryName(name);
    }
}
