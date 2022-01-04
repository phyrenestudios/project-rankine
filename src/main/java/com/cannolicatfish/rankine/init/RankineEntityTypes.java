package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.entities.*;
import com.mojang.datafixers.TypeRewriteRule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

public class RankineEntityTypes {
    public static final EntityType<SpearEntity> FLINT_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world, RankineEntityTypes.FLINT_SPEAR,new ResourceLocation("rankine:textures/entity/flint_spear.png"))).size(0.5F, 0.5F),"flint_spear");
    public static final EntityType<SpearEntity> BRONZE_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world, RankineEntityTypes.BRONZE_SPEAR,new ResourceLocation("rankine:textures/entity/bronze_spear.png"))).size(0.5F, 0.5F),"bronze_spear");
    public static final EntityType<SpearEntity> PEWTER_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world, RankineEntityTypes.PEWTER_SPEAR,new ResourceLocation("rankine:textures/entity/pewter_spear.png"))).size(0.5F, 0.5F),"pewter_spear");
    public static final EntityType<SpearEntity> IRON_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world, RankineEntityTypes.IRON_SPEAR,new ResourceLocation("rankine:textures/entity/iron_spear.png"))).size(0.5F, 0.5F),"iron_spear");
    public static final EntityType<SpearEntity> INVAR_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world, RankineEntityTypes.INVAR_SPEAR,new ResourceLocation("rankine:textures/entity/invar_spear.png"))).size(0.5F, 0.5F),"meteoric_iron_spear");
    public static final EntityType<SpearEntity> STEEL_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world, RankineEntityTypes.STEEL_SPEAR,new ResourceLocation("rankine:textures/entity/steel_spear.png"))).size(0.5F, 0.5F),"steel_spear");
    public static final EntityType<SpearEntity> ROSE_GOLD_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world, RankineEntityTypes.ROSE_GOLD_SPEAR,new ResourceLocation("rankine:textures/entity/rose_gold_spear.png"))).size(0.5F, 0.5F),"rose_gold_spear");
    public static final EntityType<SpearEntity> WHITE_GOLD_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world, RankineEntityTypes.WHITE_GOLD_SPEAR,new ResourceLocation("rankine:textures/entity/white_gold_spear.png"))).size(0.5F, 0.5F),"white_gold_spear");
    public static final EntityType<SpearEntity> GREEN_GOLD_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world, RankineEntityTypes.GREEN_GOLD_SPEAR,new ResourceLocation("rankine:textures/entity/green_gold_spear.png"))).size(0.5F, 0.5F),"green_gold_spear");
    public static final EntityType<SpearEntity> BLUE_GOLD_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world, RankineEntityTypes.BLUE_GOLD_SPEAR,new ResourceLocation("rankine:textures/entity/blue_gold_spear.png"))).size(0.5F, 0.5F),"blue_gold_spear");
    public static final EntityType<SpearEntity> PURPLE_GOLD_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world, RankineEntityTypes.PURPLE_GOLD_SPEAR,new ResourceLocation("rankine:textures/entity/purple_gold_spear.png"))).size(0.5F, 0.5F),"purple_gold_spear");
    public static final EntityType<SpearEntity> BLACK_GOLD_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world, RankineEntityTypes.BLACK_GOLD_SPEAR,new ResourceLocation("rankine:textures/entity/black_gold_spear.png"))).size(0.5F, 0.5F),"black_gold_spear");
    public static final EntityType<SpearEntity> AMALGAM_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world, RankineEntityTypes.AMALGAM_SPEAR,new ResourceLocation("rankine:textures/entity/amalgam_spear.png"))).size(0.5F, 0.5F),"amalgam_spear");
    public static final EntityType<SpearEntity> DIAMOND_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world, RankineEntityTypes.DIAMOND_SPEAR,new ResourceLocation("rankine:textures/entity/diamond_spear.png"))).size(0.5F, 0.5F),"diamond_spear");
    public static final EntityType<SpearEntity> NETHERITE_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world, RankineEntityTypes.NETHERITE_SPEAR,new ResourceLocation("rankine:textures/entity/netherite_spear.png"))).size(0.5F, 0.5F),"netherite_spear");
    public static final EntityType<SpearEntity> STAINLESS_STEEL_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world, RankineEntityTypes.STAINLESS_STEEL_SPEAR,new ResourceLocation("rankine:textures/entity/stainless_steel_spear.png"))).size(0.5F, 0.5F),"stainless_steel_spear");
    public static final EntityType<SpearEntity> NICKEL_SUPERALLOY_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world, RankineEntityTypes.NICKEL_SUPERALLOY_SPEAR,new ResourceLocation("rankine:textures/entity/nickel_superalloy_spear.png"))).size(0.5F, 0.5F),"nickel_superalloy_spear");
    public static final EntityType<SpearEntity> COBALT_SUPERALLOY_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world, RankineEntityTypes.COBALT_SUPERALLOY_SPEAR,new ResourceLocation("rankine:textures/entity/cobalt_superalloy_spear.png"))).size(0.5F, 0.5F),"cobalt_superalloy_spear");
    public static final EntityType<SpearEntity> TUNGSTEN_HEAVY_ALLOY_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world, RankineEntityTypes.TUNGSTEN_HEAVY_ALLOY_SPEAR,new ResourceLocation("rankine:textures/entity/tungsten_heavy_alloy_spear.png"))).size(0.5F, 0.5F),"tungsten_heavy_alloy_spear");
    public static final EntityType<SpearEntity> TITANIUM_ALLOY_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world, RankineEntityTypes.TITANIUM_ALLOY_SPEAR,new ResourceLocation("rankine:textures/entity/titanium_alloy_spear.png"))).size(0.5F, 0.5F),"titanium_alloy_spear");
    public static final EntityType<SpearEntity> ALLOY_SPEAR = prepareEntity(EntityType.Builder.<SpearEntity>create(EntityClassification.MISC).setCustomClientFactory((spawnEntity, world) -> new SpearEntity(spawnEntity,world, RankineEntityTypes.ALLOY_SPEAR,new ResourceLocation("rankine:textures/entity/iron_spear.png"))).size(0.5F, 0.5F),"alloy_spear");
    public static final EntityType<RopeCoilArrowEntity> ROPE_COIL_ARROW = prepareEntity(EntityType.Builder.<RopeCoilArrowEntity>create(EntityClassification.MISC).size(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> new RopeCoilArrowEntity(spawnEntity,world, RankineEntityTypes.ROPE_COIL_ARROW)).trackingRange(4).updateInterval(20).size(0.5F, 0.5F),"rope_coil_arrow");
    public static final EntityType<ThoriumArrowEntity> THORIUM_ARROW = prepareEntity(EntityType.Builder.<ThoriumArrowEntity>create(EntityClassification.MISC).size(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> new ThoriumArrowEntity(spawnEntity,world, RankineEntityTypes.THORIUM_ARROW)).trackingRange(4).updateInterval(20).size(0.5F, 0.5F),"thorium_arrow");
    public static final EntityType<MagnesiumArrowEntity> MAGNESIUM_ARROW = prepareEntity(EntityType.Builder.<MagnesiumArrowEntity>create(EntityClassification.MISC).size(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> new MagnesiumArrowEntity(spawnEntity,world, RankineEntityTypes.MAGNESIUM_ARROW)).trackingRange(4).updateInterval(20).size(0.5F, 0.5F),"magnesium_arrow");
    public static final EntityType<AlloyArrowEntity> ALLOY_ARROW = prepareEntity(EntityType.Builder.<AlloyArrowEntity>create(EntityClassification.MISC).size(0.5F, 0.5F).setCustomClientFactory((spawnEntity, world) -> new AlloyArrowEntity(spawnEntity,world, RankineEntityTypes.ALLOY_ARROW)).trackingRange(4).updateInterval(20).size(0.5F, 0.5F),"alloy_arrow");
    public static final EntityType<MantleGolemEntity> MANTLE_GOLEM = EntityType.Builder.create(MantleGolemEntity::new, EntityClassification.MONSTER).build(ProjectRankine.MODID + ":mantle_golem");
    public static final EntityType<DiamondMantleGolemEntity> DIAMOND_MANTLE_GOLEM = EntityType.Builder.create(DiamondMantleGolemEntity::new, EntityClassification.MONSTER).build(ProjectRankine.MODID + ":diamond_mantle_golem");
    public static final EntityType<PeridotMantleGolemEntity> PERIDOT_MANTLE_GOLEM = EntityType.Builder.create(PeridotMantleGolemEntity::new, EntityClassification.MONSTER).build(ProjectRankine.MODID + ":peridot_mantle_golem");
    public static final EntityType<DesmoxyteEntity> DESMOXYTE = EntityType.Builder.create(DesmoxyteEntity::new, EntityClassification.MONSTER).size(0.5F, 0.3F).build(ProjectRankine.MODID + ":desmoxyte");
    public static final EntityType<DemonyteEntity> DEMONYTE = EntityType.Builder.create(DemonyteEntity::new, EntityClassification.MONSTER).size(0.5F, 0.3F).build(ProjectRankine.MODID + ":demonyte");
    public static final EntityType<DragonyteEntity> DRAGONYTE = EntityType.Builder.create(DragonyteEntity::new, EntityClassification.MONSTER).size(0.5F, 0.3F).build(ProjectRankine.MODID + ":dragonyte");
    public static final EntityType<BeaverEntity> BEAVER = EntityType.Builder.create(BeaverEntity::new, EntityClassification.CREATURE).size(1F, 0.4F).build(ProjectRankine.MODID + ":beaver");
    public static final EntityType<RankineBoatEntity> RANKINE_BOAT = EntityType.Builder.<RankineBoatEntity>create(RankineBoatEntity::new, EntityClassification.MISC).size(1.375F, 0.5625F).build(ProjectRankine.MODID + ":rankine_boat");
    public static final EntityType<ReactiveItemEntity> REACTIVE_ITEM = EntityType.Builder.<ReactiveItemEntity>create(ReactiveItemEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).build(ProjectRankine.MODID + ":reactive_item");
    public static final EntityType<CannonballEntity> CANNONBALL =  EntityType.Builder.<CannonballEntity>create(CannonballEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).trackingRange(4).updateInterval(4).build(ProjectRankine.MODID + ":cannonball");
    public static final EntityType<CarcassEntity> CARCASS =  EntityType.Builder.<CarcassEntity>create(CarcassEntity::new, EntityClassification.MISC).size(0.5F, 0.5F).trackingRange(4).updateInterval(4).build(ProjectRankine.MODID + ":carcass");



    public static <T extends Entity> EntityType<T> prepareEntity(EntityType.Builder builder, String name)
    {
        return (EntityType<T>) builder.build(ProjectRankine.MODID + '.' + name).setRegistryName(name);
    }
}
