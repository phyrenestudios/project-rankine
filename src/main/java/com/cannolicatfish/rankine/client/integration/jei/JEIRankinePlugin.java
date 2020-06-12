package com.cannolicatfish.rankine.client.integration.jei;

import com.cannolicatfish.rankine.ProjectRankine;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class JEIRankinePlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ProjectRankine.MODID, "main");
    }
}
