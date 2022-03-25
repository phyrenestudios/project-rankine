package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.world.gen.feature.ReplacerPlacement;
import com.cannolicatfish.rankine.world.gen.placers.IntrusionPlacement;
import net.minecraft.world.level.levelgen.placement.ChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RankinePlacements {
    public static final DeferredRegister<FeatureDecorator<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.DECORATORS, ProjectRankine.MODID);

    public static final RegistryObject<FeatureDecorator<NoneDecoratorConfiguration>> REPLACER_PLACEMENT = REGISTRY.register("replacer_placement", () -> new ReplacerPlacement(NoneDecoratorConfiguration.CODEC));
    public static final RegistryObject<FeatureDecorator<ChanceDecoratorConfiguration>> INTRUSION_PLACEMENT = REGISTRY.register("intrusion_placement", () -> new IntrusionPlacement(ChanceDecoratorConfiguration.CODEC));




}

