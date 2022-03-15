package com.cannolicatfish.rankine.client.renders;

import com.cannolicatfish.rankine.client.models.entities.DiamondMantleGolemModel;
import com.cannolicatfish.rankine.entities.DiamondMantleGolemEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class DiamondMantleGolemRenderer extends LivingRenderer<DiamondMantleGolemEntity, DiamondMantleGolemModel<DiamondMantleGolemEntity>> {

    public static final RenderFactory instance = new RenderFactory();

    public DiamondMantleGolemRenderer(EntityRendererManager manager) {
        super(manager, new DiamondMantleGolemModel(), 0f);
    }

    @Override
    protected boolean shouldShowName(DiamondMantleGolemEntity entity) {
        return false;
    }

    @Override
    public ResourceLocation getTextureLocation(DiamondMantleGolemEntity entity) {
        return new ResourceLocation("rankine:textures/entity/diamond_mantle_golem.png");
    }

    public static class RenderFactory implements IRenderFactory<DiamondMantleGolemEntity> {
        @Override
        public EntityRenderer<? super DiamondMantleGolemEntity> createRenderFor(EntityRendererManager manager) {
            return new DiamondMantleGolemRenderer(manager);
        }

    }
}