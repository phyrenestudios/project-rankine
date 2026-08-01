package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Predicate;

public class RankineVillagerProfessions {
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(Registries.VILLAGER_PROFESSION, ProjectRankine.MODID);

    public static final DeferredHolder<VillagerProfession, VillagerProfession> METALLURGIST = VILLAGER_PROFESSIONS.register("metallurgist", () -> new VillagerProfession("metallurgist", poi(RankinePOIs.TEMPLATE_TABLE_POI), poi(RankinePOIs.TEMPLATE_TABLE_POI), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ANVIL_HIT));
    public static final DeferredHolder<VillagerProfession, VillagerProfession> MINERALOGIST = VILLAGER_PROFESSIONS.register("mineralogist", () -> new VillagerProfession("mineralogist", poi(RankinePOIs.MATERIAL_TESTING_TABLE_POI), poi(RankinePOIs.MATERIAL_TESTING_TABLE_POI), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.STONE_BREAK));
    public static final DeferredHolder<VillagerProfession, VillagerProfession> BOTANIST = VILLAGER_PROFESSIONS.register("botanist", () -> new VillagerProfession("botanist", poi(RankinePOIs.BOTANIST_STATION_POI), poi(RankinePOIs.BOTANIST_STATION_POI), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.WET_GRASS_STEP));
    public static final DeferredHolder<VillagerProfession, VillagerProfession> GEM_CUTTER = VILLAGER_PROFESSIONS.register("gem_cutter", () -> new VillagerProfession("gem_cutter", poi(RankinePOIs.GEM_CUTTER_POI), poi(RankinePOIs.GEM_CUTTER_POI), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.GLASS_PLACE));
    public static final DeferredHolder<VillagerProfession, VillagerProfession> ROCK_COLLECTOR = VILLAGER_PROFESSIONS.register("rock_collector", () -> new VillagerProfession("rock_collector", poi(RankinePOIs.ROCK_COLLECTOR_POI), poi(RankinePOIs.ROCK_COLLECTOR_POI), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.STONE_PLACE));

    private static Predicate<Holder<PoiType>> poi(DeferredHolder<PoiType, PoiType> poiType) {
        return holder -> holder.is(poiType.getKey());
    }

}
