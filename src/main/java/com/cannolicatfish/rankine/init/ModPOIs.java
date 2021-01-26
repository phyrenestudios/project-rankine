package com.cannolicatfish.rankine.init;


import com.cannolicatfish.rankine.ProjectRankine;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class ModPOIs {



    public static final PointOfInterestType TEMPLATE_TABLE_POI = new PointOfInterestType("template_table_poi", ImmutableSet.copyOf(getAllStates(ModBlocks.TEMPLATE_TABLE)), 1, 1);
    public static final PointOfInterestType PISTON_CRUSHER_POI = new PointOfInterestType("piston_crusher_poi", ImmutableSet.copyOf(getAllStates(ModBlocks.PISTON_CRUSHER)), 1, 1);
    public static final PointOfInterestType BOTANIST_STATION_POI = new PointOfInterestType("botanist_poi", ImmutableSet.copyOf(getAllStates(ModBlocks.BOTANIST_STATION)), 1, 1);

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
