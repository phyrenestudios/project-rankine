package com.cannolicatfish.rankine.client.renders;

import com.cannolicatfish.rankine.client.models.entities.PeridotMantleGolemModel;
import com.cannolicatfish.rankine.entities.PeridotMantleGolemEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class PeridotMantleGolemRenderer extends LivingRenderer<PeridotMantleGolemEntity, PeridotMantleGolemModel<PeridotMantleGolemEntity>> {

    public static final RenderFactory instance = new RenderFactory();

    public PeridotMantleGolemRenderer(EntityRendererManager manager) {
        super(manager, new PeridotMantleGolemModel(), 0f);
    }

    @Override
    protected boolean shouldShowName(PeridotMantleGolemEntity entity) {
        return false;
    }

    @Override
    public ResourceLocation getTextureLocation(PeridotMantleGolemEntity entity) {
        return new ResourceLocation("rankine:textures/entity/peridot_mantle_golem.png");
    }

    public static class RenderFactory implements IRenderFactory<PeridotMantleGolemEntity> {
        @Override
        public EntityRenderer<? super PeridotMantleGolemEntity> createRenderFor(EntityRendererManager manager) {
            return new PeridotMantleGolemRenderer(manager);
        }

    }
}