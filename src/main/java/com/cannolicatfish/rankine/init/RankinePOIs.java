package com.cannolicatfish.rankine.init;


import com.cannolicatfish.rankine.ProjectRankine;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class RankinePOIs {

    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, ProjectRankine.MODID);

    public static final RegistryObject<PoiType> TEMPLATE_TABLE_POI = POI_TYPES.register("template_table_poi", () -> new PoiType("template_table_poi", ImmutableSet.copyOf(getAllStates(RankineBlocks.TEMPLATE_TABLE.get())), 1, 1));
    public static final RegistryObject<PoiType> MATERIAL_TESTING_TABLE_POI = POI_TYPES.register("material_testing_table_poi", () -> new PoiType("material_testing_table_poi", ImmutableSet.copyOf(getAllStates(RankineBlocks.MATERIAL_TESTING_TABLE.get())), 1, 1));
    public static final RegistryObject<PoiType> BOTANIST_STATION_POI = POI_TYPES.register("botanist_poi", () -> new PoiType("botanist_poi", ImmutableSet.copyOf(getAllStates(RankineBlocks.BOTANIST_STATION.get())), 1, 1));
    public static final RegistryObject<PoiType> GEM_CUTTER_POI = POI_TYPES.register("gem_cutter_poi", () -> new PoiType("gem_cutter_poi", ImmutableSet.copyOf(getAllStates(RankineBlocks.FUSION_FURNACE.get())), 1, 1));
    public static final RegistryObject<PoiType> ROCK_COLLECTOR_POI = POI_TYPES.register("rock_collector_poi", () -> new PoiType("rock_collector_poi", ImmutableSet.copyOf(getAllStates(RankineBlocks.SEDIMENT_FAN.get())), 1, 1));

    public static Set<BlockState> getAllStates(Block blockIn) {
        return ImmutableSet.copyOf(blockIn.getStateDefinition().getPossibleStates());
    }
}
