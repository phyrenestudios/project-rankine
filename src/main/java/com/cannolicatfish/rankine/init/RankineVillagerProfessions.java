package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RankineVillagerProfessions {
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, ProjectRankine.MODID);

    public static final RegistryObject<VillagerProfession> METALLURGIST = VILLAGER_PROFESSIONS.register("metallurgist", () -> new VillagerProfession("metallurgist", RankinePOIs.TEMPLATE_TABLE_POI.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ANVIL_HIT));
    public static final RegistryObject<VillagerProfession> MINERALOGIST = VILLAGER_PROFESSIONS.register("mineralogist", () -> new VillagerProfession("mineralogist", RankinePOIs.MATERIAL_TESTING_TABLE_POI.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.STONE_BREAK));
    public static final RegistryObject<VillagerProfession> BOTANIST = VILLAGER_PROFESSIONS.register("botanist", () -> new VillagerProfession("botanist", RankinePOIs.BOTANIST_STATION_POI.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.WET_GRASS_STEP));
    public static final RegistryObject<VillagerProfession> GEM_CUTTER = VILLAGER_PROFESSIONS.register("gem_cutter", () -> new VillagerProfession("gem_cutter", RankinePOIs.GEM_CUTTER_POI.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.GLASS_PLACE));
    public static final RegistryObject<VillagerProfession> ROCK_COLLECTOR = VILLAGER_PROFESSIONS.register("rock_collector", () -> new VillagerProfession("rock_collector", RankinePOIs.ROCK_COLLECTOR_POI.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.STONE_PLACE));

}
