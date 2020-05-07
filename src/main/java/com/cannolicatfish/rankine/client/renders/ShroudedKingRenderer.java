package com.cannolicatfish.rankine.client.renders;

import com.cannolicatfish.rankine.client.models.ShroudedKingModel;
import com.cannolicatfish.rankine.entities.boss.ShroudedKingEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class ShroudedKingRenderer extends LivingRenderer<ShroudedKingEntity, ShroudedKingModel<ShroudedKingEntity>>
{

    public static final ShroudedKingRenderer.RenderFactory instance = new ShroudedKingRenderer.RenderFactory();
    public ShroudedKingRenderer(EntityRendererManager manager)
    {
        super(manager, new ShroudedKingModel(0.0F), 0f);
    }

    @Override
    protected boolean canRenderName(ShroudedKingEntity entity) {
        return false;
    }

    @Override
    public ResourceLocation getEntityTexture(ShroudedKingEntity entity)
    {
        return new ResourceLocation("rankine:textures/entity/king/shrouded_king.png");
    }

    public static class RenderFactory implements IRenderFactory<ShroudedKingEntity>
    {
        @Override
        public EntityRenderer<? super ShroudedKingEntity> createRenderFor(EntityRendererManager manager)
        {
            return new ShroudedKingRenderer(manager);
        }

    }
}