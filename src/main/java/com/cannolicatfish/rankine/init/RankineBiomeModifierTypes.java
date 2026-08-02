package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.world.modifier.ConfigGatedFeaturesBiomeModifier;
import com.cannolicatfish.rankine.world.modifier.DisableVanillaOresBiomeModifier;
import com.cannolicatfish.rankine.world.modifier.RankineOreBiomeModifier;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * Codec types for Rankine's custom biome modifiers. Needed because the 1.18.2 BiomeLoadingEvent
 * logic was config-conditional, which static {@code neoforge:add_features} JSON cannot express.
 * <p>
 * PORT-NOTE(main): {@code BIOME_MODIFIER_SERIALIZERS.register(modBus)} is already wired in the
 * ProjectRankine constructor — keep it there, the modifiers below will not deserialize without it.
 */
public class RankineBiomeModifierTypes {
    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, ProjectRankine.MODID);

    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<ConfigGatedFeaturesBiomeModifier>> CONFIG_GATED_ADD_FEATURES =
            BIOME_MODIFIER_SERIALIZERS.register("config_gated_add_features", () -> ConfigGatedFeaturesBiomeModifier.CODEC);

    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<RankineOreBiomeModifier>> ORE_SETTINGS =
            BIOME_MODIFIER_SERIALIZERS.register("ore_settings", () -> RankineOreBiomeModifier.CODEC);

    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<DisableVanillaOresBiomeModifier>> DISABLE_VANILLA_ORES =
            BIOME_MODIFIER_SERIALIZERS.register("disable_vanilla_ores", () -> DisableVanillaOresBiomeModifier.CODEC);
}
