package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.entities.*;
import com.cannolicatfish.rankine.entities.boss.ShroudedKingEntity;
import com.cannolicatfish.rankine.entities.projectiles.JarBlueFoxfireEntity;
import com.cannolicatfish.rankine.entities.projectiles.JarGreenFoxfireEntity;
import com.cannolicatfish.rankine.entities.boss.SolarFlareEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

public class ModEntityTypes {
    public static final EntityType<SpearEntity> FLINT_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world,ModEntityTypes.FLINT_SPEAR,new ResourceLocation("rankine:textures/entity/flint_spear.png"))).size(0.5F, 0.5F),"flint_spear");
    public static final EntityType<SpearEntity> BRONZE_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world,ModEntityTypes.BRONZE_SPEAR,new ResourceLocation("rankine:textures/entity/bronze_spear.png"))).size(0.5F, 0.5F),"bronze_spear");
    public static final EntityType<SpearEntity> IRON_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world,ModEntityTypes.IRON_SPEAR,new ResourceLocation("rankine:textures/entity/iron_spear.png"))).size(0.5F, 0.5F),"iron_spear");
    public static final EntityType<SpearEntity> METEORIC_IRON_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world,ModEntityTypes.METEORIC_IRON_SPEAR,new ResourceLocation("rankine:textures/entity/netherite_spear.png"))).size(0.5F, 0.5F),"meteoric_iron_spear");
    public static final EntityType<SpearEntity> STEEL_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world,ModEntityTypes.STEEL_SPEAR,new ResourceLocation("rankine:textures/entity/steel_spear.png"))).size(0.5F, 0.5F),"steel_spear");
    public static final EntityType<SpearEntity> ROSE_GOLD_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world,ModEntityTypes.ROSE_GOLD_SPEAR,new ResourceLocation("rankine:textures/entity/rose_gold_spear.png"))).size(0.5F, 0.5F),"rose_gold_spear");
    public static final EntityType<SpearEntity> WHITE_GOLD_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world,ModEntityTypes.WHITE_GOLD_SPEAR,new ResourceLocation("rankine:textures/entity/white_gold_spear.png"))).size(0.5F, 0.5F),"white_gold_spear");
    public static final EntityType<SpearEntity> GREEN_GOLD_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world,ModEntityTypes.GREEN_GOLD_SPEAR,new ResourceLocation("rankine:textures/entity/green_gold_spear.png"))).size(0.5F, 0.5F),"green_gold_spear");
    public static final EntityType<SpearEntity> BLUE_GOLD_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world,ModEntityTypes.BLUE_GOLD_SPEAR,new ResourceLocation("rankine:textures/entity/blue_gold_spear.png"))).size(0.5F, 0.5F),"blue_gold_spear");
    public static final EntityType<SpearEntity> PURPLE_GOLD_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world,ModEntityTypes.PURPLE_GOLD_SPEAR,new ResourceLocation("rankine:textures/entity/purple_gold_spear.png"))).size(0.5F, 0.5F),"purple_gold_spear");
    public static final EntityType<SpearEntity> BLACK_GOLD_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world,ModEntityTypes.BLACK_GOLD_SPEAR,new ResourceLocation("rankine:textures/entity/black_gold_spear.png"))).size(0.5F, 0.5F),"black_gold_spear");
    public static final EntityType<SpearEntity> AMALGAM_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world,ModEntityTypes.AMALGAM_SPEAR,new ResourceLocation("rankine:textures/entity/amalgam_spear.png"))).size(0.5F, 0.5F),"amalgam_spear");
    public static final EntityType<SpearEntity> DIAMOND_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world,ModEntityTypes.DIAMOND_SPEAR,new ResourceLocation("rankine:textures/entity/diamond_spear.png"))).size(0.5F, 0.5F),"diamond_spear");
    public static final EntityType<SpearEntity> NETHERITE_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world,ModEntityTypes.NETHERITE_SPEAR,new ResourceLocation("rankine:textures/entity/netherite_spear.png"))).size(0.5F, 0.5F),"netherite_spear");
    public static final EntityType<SpearEntity> NICKEL_SUPERALLOY_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world,ModEntityTypes.NICKEL_SUPERALLOY_SPEAR,new ResourceLocation("rankine:textures/entity/nickel_superalloy_spear.png"))).size(0.5F, 0.5F),"nickel_superalloy_spear");

    public static final EntityType<MantleGolemEntity> MANTLE_GOLEM = EntityType.Builder.create(MantleGolemEntity::new, EntityClassification.MONSTER).build(ProjectRankine.MODID + ":mantle_golem");
    public static final EntityType<DiamondMantleGolemEntity> DIAMOND_MANTLE_GOLEM = EntityType.Builder.create(DiamondMantleGolemEntity::new, EntityClassification.MONSTER).build(ProjectRankine.MODID + ":diamond_mantle_golem");
    public static final EntityType<PeridotMantleGolemEntity> PERIDOT_MANTLE_GOLEM = EntityType.Builder.create(PeridotMantleGolemEntity::new, EntityClassification.MONSTER).build(ProjectRankine.MODID + ":peridot_mantle_golem");
    public static final EntityType<DesmoxyteEntity> DESMOXYTE = EntityType.Builder.create(DesmoxyteEntity::new, EntityClassification.MONSTER).size(0.5F, 0.3F).build(ProjectRankine.MODID + ":desmoxyte");
    public static final EntityType<DemonyteEntity> DEMONYTE = EntityType.Builder.create(DemonyteEntity::new, EntityClassification.MONSTER).size(0.5F, 0.3F).build(ProjectRankine.MODID + ":demonyte");
    public static final EntityType<DragonyteEntity> DRAGONYTE = EntityType.Builder.create(DragonyteEntity::new, EntityClassification.MONSTER).size(0.5F, 0.3F).build(ProjectRankine.MODID + ":dragonyte");
    public static final EntityType<SteamerEntity> STEAMER = EntityType.Builder.create(SteamerEntity::new, EntityClassification.MONSTER).build(ProjectRankine.MODID + ":steamer");
    public static final EntityType<BeaverEntity> BEAVER = EntityType.Builder.create(BeaverEntity::new, EntityClassification.CREATURE).size(1F, 0.4F).build(ProjectRankine.MODID + ":beaver");
    public static final EntityType<ShroudedKingEntity> SHROUDED_KING = EntityType.Builder.create(ShroudedKingEntity::new, EntityClassification.MONSTER).build(ProjectRankine.MODID + ":shrouded_king");
    public static final EntityType<SolarFlareEntity> SOLAR_FLARE = EntityType.Builder.create(SolarFlareEntity::new, EntityClassification.MONSTER).build(ProjectRankine.MODID + ":solar_flare");
    public static final EntityType<RankineBoatEntity> RANKINE_BOAT = EntityType.Builder.<RankineBoatEntity>create(RankineBoatEntity::new, EntityClassification.MISC).size(1.375F, 0.5625F).build(ProjectRankine.MODID + ":rankine_boat");
    public static final EntityType<ReactiveItemEntity> REACTIVE_ITEM = EntityType.Builder.<ReactiveItemEntity>create(ReactiveItemEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).build(ProjectRankine.MODID + ":reactive_item");
    public static final EntityType<JarBlueFoxfireEntity> JAR_BLUE_FOXFIRE = EntityType.Builder.<JarBlueFoxfireEntity>create(JarBlueFoxfireEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).build(ProjectRankine.MODID + ":jar_blue_foxfire_item");
    public static final EntityType<JarGreenFoxfireEntity> JAR_GREEN_FOXFIRE = EntityType.Builder.<JarGreenFoxfireEntity>create(JarGreenFoxfireEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).build(ProjectRankine.MODID + ":jar_green_foxfire_item");




    public static <T extends Entity> EntityType<T> prepareEntity(EntityType.Builder builder, String name)
    {
        return (EntityType<T>) builder.build(ProjectRankine.MODID + '.' + name).setRegistryName(name);
    }
}
