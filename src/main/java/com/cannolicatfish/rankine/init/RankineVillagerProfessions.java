package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RankineVillagerProfessions {
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, ProjectRankine.MODID);

    public static final RegistryObject<VillagerProfession> METALLURGIST = VILLAGER_PROFESSIONS.register("metallurgist", () -> new VillagerProfession("metallurgist", (poiTypeHolder) -> poiTypeHolder == RankinePOIs.TEMPLATE_TABLE_POI.getHolder().get(), (poiTypeHolder) -> poiTypeHolder == RankinePOIs.TEMPLATE_TABLE_POI.getHolder().get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ANVIL_HIT));
    public static final RegistryObject<VillagerProfession> MINERALOGIST = VILLAGER_PROFESSIONS.register("mineralogist", () -> new VillagerProfession("mineralogist", (poiTypeHolder) -> poiTypeHolder == RankinePOIs.PISTON_CRUSHER_POI.getHolder().get(), (poiTypeHolder) -> poiTypeHolder == RankinePOIs.PISTON_CRUSHER_POI.getHolder().get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.STONE_BREAK));
    public static final RegistryObject<VillagerProfession> BOTANIST = VILLAGER_PROFESSIONS.register("botanist", () -> new VillagerProfession("botanist", (poiTypeHolder) -> poiTypeHolder == RankinePOIs.BOTANIST_STATION_POI.getHolder().get(), (poiTypeHolder) -> poiTypeHolder == RankinePOIs.BOTANIST_STATION_POI.getHolder().get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.WET_GRASS_STEP));
    public static final RegistryObject<VillagerProfession> GEM_CUTTER = VILLAGER_PROFESSIONS.register("gem_cutter", () -> new VillagerProfession("gem_cutter",(poiTypeHolder) -> poiTypeHolder == RankinePOIs.GEM_CUTTER_POI.getHolder().get(), (poiTypeHolder) -> poiTypeHolder == RankinePOIs.GEM_CUTTER_POI.getHolder().get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.GLASS_PLACE));
    public static final RegistryObject<VillagerProfession> ROCK_COLLECTOR = VILLAGER_PROFESSIONS.register("rock_collector", () -> new VillagerProfession("rock_collector", (poiTypeHolder) -> poiTypeHolder == RankinePOIs.ROCK_COLLECTOR_POI.getHolder().get(), (poiTypeHolder) -> poiTypeHolder == RankinePOIs.ROCK_COLLECTOR_POI.getHolder().get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.STONE_PLACE));

}
