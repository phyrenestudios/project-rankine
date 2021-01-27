package com.cannolicatfish.rankine.init;

import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.SoundEvents;

public class RankineVillagerProfessions {

    public static final VillagerProfession METALLURGIST = new VillagerProfession("metallurgist", RankinePOIs.TEMPLATE_TABLE_POI, ImmutableSet.of(), ImmutableSet.of(), SoundEvents.BLOCK_ANVIL_HIT);
    public static final VillagerProfession MINERALOGIST = new VillagerProfession("mineralogist", RankinePOIs.PISTON_CRUSHER_POI, ImmutableSet.of(), ImmutableSet.of(), SoundEvents.BLOCK_STONE_BREAK);
    public static final VillagerProfession BOTANIST = new VillagerProfession("botanist", RankinePOIs.BOTANIST_STATION_POI, ImmutableSet.of(), ImmutableSet.of(), SoundEvents.BLOCK_WET_GRASS_STEP);
}
