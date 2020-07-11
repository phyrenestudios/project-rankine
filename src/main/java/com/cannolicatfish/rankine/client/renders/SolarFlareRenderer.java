package com.cannolicatfish.rankine.client.renders;

import com.cannolicatfish.rankine.client.models.SolarFlareModel;
import com.cannolicatfish.rankine.entities.boss.SolarFlareEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class SolarFlareRenderer extends LivingRenderer<SolarFlareEntity, SolarFlareModel<SolarFlareEntity>>
{

    public static final SolarFlareRenderer.RenderFactory instance = new SolarFlareRenderer.RenderFactory();
    public SolarFlareRenderer(EntityRendererManager manager)
            {
            super(manager, new SolarFlareModel(0), 0f);
            }

    @Override
    protected boolean canRenderName(SolarFlareEntity entity) {
            return false;
            }

    @Override
    public ResourceLocation getEntityTexture(SolarFlareEntity entity)
            {
            return new ResourceLocation("rankine:textures/entity/solar_flare.png");
            }

    public static class RenderFactory implements IRenderFactory<SolarFlareEntity>
    {
        @Override
        public EntityRenderer<? super SolarFlareEntity> createRenderFor(EntityRendererManager manager)
        {
            return new SolarFlareRenderer(manager);
        }

    }
}