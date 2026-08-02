package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.world.gen.ConfigRarityFilter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registry types for custom worldgen serializers (placement modifiers, and any future
 * trunk/foliage placer types — Rankine's trees are custom {@code Feature}s, so none are needed yet).
 * <p>
 * PORT-NOTE(main): {@code PLACEMENT_MODIFIER_TYPES.register(modBus)} is already wired in the
 * ProjectRankine constructor — keep it there, the placed features below will not load without it.
 */
public class RankineWorldgenTypes {
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIER_TYPES = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, ProjectRankine.MODID);

    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<ConfigRarityFilter>> CONFIG_RARITY_FILTER =
            PLACEMENT_MODIFIER_TYPES.register("config_rarity_filter", () -> (PlacementModifierType<ConfigRarityFilter>) () -> ConfigRarityFilter.CODEC);
}
