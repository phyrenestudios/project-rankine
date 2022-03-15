package com.cannolicatfish.rankine.client.renders;

import com.cannolicatfish.rankine.client.models.entities.DemonyteModel;
import com.cannolicatfish.rankine.entities.DemonyteEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class DemonyteRenderer extends LivingRenderer<DemonyteEntity, DemonyteModel<DemonyteEntity>> {

    public static final DemonyteRenderer.RenderFactory instance = new DemonyteRenderer.RenderFactory();

    @Override
    protected boolean shouldShowName(DemonyteEntity entity) {
        return false;
    }

    public DemonyteRenderer(EntityRendererManager manager) {
        super(manager, new DemonyteModel(), 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(DemonyteEntity entity) {
        return new ResourceLocation("rankine:textures/entity/demonyte.png");
    }

    public static class RenderFactory implements IRenderFactory<DemonyteEntity> {
        @Override
        public EntityRenderer<? super DemonyteEntity> createRenderFor(EntityRendererManager manager) {
            return new DemonyteRenderer(manager);
        }

    }
}

