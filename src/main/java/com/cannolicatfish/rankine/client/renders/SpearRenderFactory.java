package com.cannolicatfish.rankine.client.renders;

import com.cannolicatfish.rankine.entities.SpearEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class SpearRenderFactory implements IRenderFactory<SpearEntity> {
    public static final SpearRenderFactory instance = new SpearRenderFactory();

    @Override
    public EntityRenderer<? super SpearEntity> createRenderFor(EntityRendererManager manager) {
        return new SpearEntityRenderer(manager);
    }


}
