package com.cannolicatfish.rankine.client.renders;

import com.cannolicatfish.rankine.client.models.DragonyteModel;
import com.cannolicatfish.rankine.entities.DragonyteEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class DragonyteRenderer extends LivingRenderer<DragonyteEntity, DragonyteModel<DragonyteEntity>> {

    public static final DragonyteRenderer.RenderFactory instance = new DragonyteRenderer.RenderFactory();

    @Override
    protected boolean canRenderName(DragonyteEntity entity) {
        return false;
    }

    public DragonyteRenderer(EntityRendererManager manager) {
        super(manager, new DragonyteModel(), 0f);
    }

    @Override
    public ResourceLocation getEntityTexture(DragonyteEntity entity) {
        return new ResourceLocation("rankine:textures/entity/dragonyte.png");
    }

    public static class RenderFactory implements IRenderFactory<DragonyteEntity> {
        @Override
        public EntityRenderer<? super DragonyteEntity> createRenderFor(EntityRendererManager manager) {
            return new DragonyteRenderer(manager);
        }

    }
}


