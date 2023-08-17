package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.stone_features.Intrusion;
import com.cannolicatfish.rankine.stone_features.IntrusionShell;
import com.cannolicatfish.rankine.stone_features.SoilLayer;
import com.cannolicatfish.rankine.stone_features.StoneLayer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;


public class RankineWorldgen {
    public static final ResourceKey<Registry<Intrusion>> INTRUSION_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ProjectRankine.MODID, "intrusion_blocks"));
    public static final ResourceKey<Registry<IntrusionShell>> INTRUSION_SHELL_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ProjectRankine.MODID, "intrusion_shells"));
    public static final ResourceKey<Registry<StoneLayer>> STONE_LAYER_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ProjectRankine.MODID, "stone_layers"));
    public static final ResourceKey<Registry<SoilLayer>> SOIL_LAYER_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ProjectRankine.MODID, "soil_layers"));

}
