package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;

public class RankineDamageSources {

    public static final ResourceKey<DamageType> COLUMNS = key("columns");
    public static final ResourceKey<DamageType> CANNONBALL = key("cannonball");
    // PORT-TODO(datagen): uranium_shot bypassed armor in 1.18.2 — add it to the
    // minecraft:bypasses_armor damage_type tag via datagen.
    public static final ResourceKey<DamageType> URANIUM_SHOT = key("uranium_shot");
    // PORT-TODO(datagen): suffocating bypassed armor AND magic in 1.18.2 — add it to the
    // minecraft:bypasses_armor and minecraft:bypasses_effects damage_type tags via datagen.
    public static final ResourceKey<DamageType> SUFFOCATION = key("suffocating");

    private static ResourceKey<DamageType> key(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, name));
    }

    /**
     * Creates a DamageSource for one of the damage types above at the use-site.
     */
    public static DamageSource source(Level level, ResourceKey<DamageType> key) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key));
    }

    /**
     * Datagen bootstrap for the {@code rankine} damage types (wire into the
     * DatapackBuiltinEntriesProvider RegistrySetBuilder under Registries.DAMAGE_TYPE).
     */
    public static void bootstrap(BootstrapContext<DamageType> ctx) {
        ctx.register(COLUMNS, new DamageType("columns", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.1F));
        ctx.register(CANNONBALL, new DamageType("cannonball", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.1F));
        ctx.register(URANIUM_SHOT, new DamageType("uranium_shot", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.1F));
        ctx.register(SUFFOCATION, new DamageType("suffocating", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.1F));
    }
}
