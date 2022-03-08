package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.world.gen.feature.ReplacerPlacement;
import com.cannolicatfish.rankine.world.gen.placers.IntrusionPlacement;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RankinePlacements {
    public static final DeferredRegister<Placement<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.DECORATORS, ProjectRankine.MODID);

    public static final RegistryObject<Placement<NoPlacementConfig>> REPLACER_PLACEMENT = REGISTRY.register("replacer_placement", () -> new ReplacerPlacement(NoPlacementConfig.CODEC));
    public static final RegistryObject<Placement<ChanceConfig>> INTRUSION_PLACEMENT = REGISTRY.register("intrusion_placement", () -> new IntrusionPlacement(ChanceConfig.CODEC));




}

