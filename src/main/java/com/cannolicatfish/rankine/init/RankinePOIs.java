package com.cannolicatfish.rankine.init;


import com.google.common.collect.ImmutableSet;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;

import java.util.*;

public class RankinePOIs {



    public static final PoiType TEMPLATE_TABLE_POI = new PoiType("template_table_poi", ImmutableSet.copyOf(getAllStates(RankineBlocks.TEMPLATE_TABLE.get())), 1, 1);
    public static final PoiType PISTON_CRUSHER_POI = new PoiType("piston_crusher_poi", ImmutableSet.copyOf(getAllStates(RankineBlocks.PISTON_CRUSHER.get())), 1, 1);
    public static final PoiType BOTANIST_STATION_POI = new PoiType("botanist_poi", ImmutableSet.copyOf(getAllStates(RankineBlocks.BOTANIST_STATION.get())), 1, 1);
    public static final PoiType GEM_CUTTER_POI = new PoiType("gem_cutter_poi", ImmutableSet.copyOf(getAllStates(RankineBlocks.DIAMOND_ANVIL_CELL.get())), 1, 1);
    public static final PoiType ROCK_COLLECTOR_POI = new PoiType("rock_collector_poi", ImmutableSet.copyOf(getAllStates(RankineBlocks.SEDIMENT_FAN.get())), 1, 1);

    public static Set<BlockState> getAllStates(Block blockIn) {
        return ImmutableSet.copyOf(blockIn.getStateDefinition().getPossibleStates());
    }
}
