package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.entities.*;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RankineEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, ProjectRankine.MODID);

    public static final RegistryObject<EntityType<SpearEntity>> ALLOY_SPEAR = ENTITY_TYPES.register("alloy_spear",
            () -> EntityType.Builder.<SpearEntity>of(SpearEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .build(new ResourceLocation(ProjectRankine.MODID, "alloy_spear").toString()));


    public static final RegistryObject<EntityType<RopeCoilArrowEntity>> ROPE_COIL_ARROW = ENTITY_TYPES.register("rope_coil_arrow",
            () -> EntityType.Builder.<RopeCoilArrowEntity>of(RopeCoilArrowEntity::new, MobCategory.MISC)
            .clientTrackingRange(4)
            .updateInterval(20)
            .sized(0.5F, 0.5F).build(new ResourceLocation(ProjectRankine.MODID, "rope_coil_arrow").toString()));

    public static final RegistryObject<EntityType<AlloyArrowEntity>> ALLOY_ARROW = ENTITY_TYPES.register("alloy_arrow",
            () -> EntityType.Builder.<AlloyArrowEntity>of(AlloyArrowEntity::new, MobCategory.MISC)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .sized(0.5F, 0.5F).build(new ResourceLocation(ProjectRankine.MODID, "alloy_arrow").toString()));

    public static final RegistryObject<EntityType<RankineBoatEntity>> RANKINE_BOAT = ENTITY_TYPES.register("rankine_boat",
            () -> EntityType.Builder.<RankineBoatEntity>of(RankineBoatEntity::new, MobCategory.MISC)
            .sized(1.375F, 0.5625F)
            .build(new ResourceLocation(ProjectRankine.MODID, "rankine_boat").toString()));

    public static final RegistryObject<EntityType<ReactiveItemEntity>> REACTIVE_ITEM = ENTITY_TYPES.register("reactive_item",
            () -> EntityType.Builder.<ReactiveItemEntity>of(ReactiveItemEntity::new, MobCategory.MISC)
            .sized(0.25F, 0.25F)
            .build(new ResourceLocation(ProjectRankine.MODID, "reactive_item").toString()));


    public static final RegistryObject<EntityType<CannonballEntity>> CANNONBALL =  ENTITY_TYPES.register("cannonball",
            () -> EntityType.Builder.<CannonballEntity>of(CannonballEntity::new, MobCategory.MISC)
            .sized(0.5F, 0.5F)
            .clientTrackingRange(4)
            .updateInterval(4)
            .build(new ResourceLocation(ProjectRankine.MODID, "cannonball").toString()));

    public static final RegistryObject<EntityType<EnderballEntity>> ENDERBALL =  ENTITY_TYPES.register("enderball",
            () -> EntityType.Builder.<EnderballEntity>of(EnderballEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(4)
                    .build(new ResourceLocation(ProjectRankine.MODID, "enderball").toString()));

    public static final RegistryObject<EntityType<CarcassEntity>> CARCASS =  ENTITY_TYPES.register("carcass",
            () -> EntityType.Builder.<CarcassEntity>of(CarcassEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(4)
                    .build(new ResourceLocation(ProjectRankine.MODID, "carcass").toString()));

    public static final RegistryObject<EntityType<BalloonEntity>> BALLOON =  ENTITY_TYPES.register("balloon",
            () -> EntityType.Builder.<BalloonEntity>of(BalloonEntity::new, MobCategory.MISC)
                    .sized(0.98F, 0.98F)
                    .clientTrackingRange(10)
                    .updateInterval(20)
                    .build(new ResourceLocation(ProjectRankine.MODID, "balloon").toString()));
}
