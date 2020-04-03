package com.cannolicatfish.rankine.client.renders;

import com.cannolicatfish.rankine.client.models.BeaverModel;
import com.cannolicatfish.rankine.entities.BeaverEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.WolfModel;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class BeaverRenderer extends MobRenderer<BeaverEntity, BeaverModel<BeaverEntity>> {

    public static final BeaverRenderer.RenderFactory instance = new BeaverRenderer.RenderFactory();

    @Override
    protected boolean canRenderName(BeaverEntity entity) {
        return false;
    }

    public void render(BeaverEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {

        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

    }

    public BeaverRenderer(EntityRendererManager manager) {
        super(manager, new BeaverModel(), 0.5f);
    }

    @Override
    public ResourceLocation getEntityTexture(BeaverEntity entity) {
        return new ResourceLocation("rankine:textures/entity/beaver.png");
    }

    public static class RenderFactory implements IRenderFactory<BeaverEntity> {
        @Override
        public EntityRenderer<? super BeaverEntity> createRenderFor(EntityRendererManager manager) {
            return new BeaverRenderer(manager);
        }

    }
}

