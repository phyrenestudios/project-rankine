package com.cannolicatfish.rankine.init;


import com.cannolicatfish.rankine.ProjectRankine;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Set;

public class ModPOIs {



    public static final PointOfInterestType TEMPLATE_TABLE_POI = new PointOfInterestType("template_table_poi", ImmutableSet.copyOf(getAllStates(ModBlocks.TEMPLATE_TABLE)), 1, 1);

    public static Set<BlockState> getAllStates(Block blockIn) {
        return ImmutableSet.copyOf(blockIn.getStateContainer().getValidStates());
    }
}
