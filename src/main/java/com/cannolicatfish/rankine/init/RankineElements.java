package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.element.Element;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;


public class RankineElements {
    public static final ResourceKey<Registry<Element>> ELEMENT_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ProjectRankine.MODID, "elements"));

}
