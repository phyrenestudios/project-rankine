package com.cannolicatfish.rankine.init;


import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.PointOfInterestType;

import java.util.*;

public class RankinePOIs {



    public static final PointOfInterestType TEMPLATE_TABLE_POI = new PointOfInterestType("template_table_poi", ImmutableSet.copyOf(getAllStates(RankineBlocks.TEMPLATE_TABLE.get())), 1, 1);
    public static final PointOfInterestType PISTON_CRUSHER_POI = new PointOfInterestType("piston_crusher_poi", ImmutableSet.copyOf(getAllStates(RankineBlocks.PISTON_CRUSHER.get())), 1, 1);
    public static final PointOfInterestType BOTANIST_STATION_POI = new PointOfInterestType("botanist_poi", ImmutableSet.copyOf(getAllStates(RankineBlocks.BOTANIST_STATION.get())), 1, 1);
    public static final PointOfInterestType GEM_CUTTER_POI = new PointOfInterestType("gem_cutter_poi", ImmutableSet.copyOf(getAllStates(RankineBlocks.DIAMOND_ANVIL_CELL.get())), 1, 1);
    public static final PointOfInterestType ROCK_COLLECTOR_POI = new PointOfInterestType("rock_collector_poi", ImmutableSet.copyOf(getAllStates(RankineBlocks.SEDIMENT_FAN.get())), 1, 1);

    public static Set<BlockState> getAllStates(Block blockIn) {
        return ImmutableSet.copyOf(blockIn.getStateContainer().getValidStates());
    }

    public static Set<BlockState> getAllStatesByTag(ResourceLocation tag) {
        ITag<Block> col = BlockTags.getCollection().get(tag);
        if (col != null)
        {
            List<BlockState> blockList = new ArrayList<>();
            for (Block block : col.getAllElements())
            {
                blockList.addAll(block.getStateContainer().getValidStates());
            }
            return ImmutableSet.copyOf(blockList);
        } else {
            return ImmutableSet.copyOf(Collections.emptyList());
        }

    }
}
