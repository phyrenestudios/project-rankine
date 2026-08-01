package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Data components replacing per-ItemStack NBT from the 1.18.2 version.
 * Registry names intentionally mirror the old NBT keys where possible.
 */
public class RankineDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ProjectRankine.MODID);

    /** Alloy composition string, e.g. "80Fe-20C" (was NBT "comp"). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> ALLOY_COMPOSITION =
            DATA_COMPONENTS.register("alloy_composition", () -> DataComponentType.<String>builder()
                    .persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());

    /** Alloy recipe id used to derive stats (was NBT "recipe"). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceLocation>> ALLOY_RECIPE =
            DATA_COMPONENTS.register("alloy_recipe", () -> DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC).build());

    /** Marks an alloy item whose stats need regeneration (was NBT "RegenerateAlloy"). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> REGENERATE_ALLOY =
            DATA_COMPONENTS.register("regenerate_alloy", () -> DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());

    /** Generic display-name override (was NBT "nameOverride"). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> NAME_OVERRIDE =
            DATA_COMPONENTS.register("name_override", () -> DataComponentType.<String>builder()
                    .persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());

    /** Stored gas id for the gas bottler / shulker gas vacuum (was NBT "gas"). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> GAS =
            DATA_COMPONENTS.register("gas", () -> DataComponentType.<String>builder()
                    .persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());

    /** Stored alloy string on alloy templates (was NBT "StoredAlloy"). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> STORED_ALLOY =
            DATA_COMPONENTS.register("stored_alloy", () -> DataComponentType.<String>builder()
                    .persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());

    /** Generic color (was NBT "color"). */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> COLOR =
            DATA_COMPONENTS.register("color", () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());

    // NOTE for porters: add further components here as NBT usages are migrated.
    // BlockEntity NBT (CookTime etc.) stays as CompoundTag — do NOT convert BE persistence.
}
