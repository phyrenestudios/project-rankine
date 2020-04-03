package com.cannolicatfish.rankine.client.renders;

import com.cannolicatfish.rankine.client.models.DesmoxyteModel;
import com.cannolicatfish.rankine.entities.DesmoxyteEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class DesmoxyteRenderer extends LivingRenderer<DesmoxyteEntity, DesmoxyteModel<DesmoxyteEntity>> {

    public static final DesmoxyteRenderer.RenderFactory instance = new DesmoxyteRenderer.RenderFactory();

    @Override
    protected boolean canRenderName(DesmoxyteEntity entity) {
        return false;
    }

    public DesmoxyteRenderer(EntityRendererManager manager) {
        super(manager, new DesmoxyteModel(), 0f);
    }

    @Override
    public ResourceLocation getEntityTexture(DesmoxyteEntity entity) {
        return new ResourceLocation("rankine:textures/entity/desmoxyte.png");
    }

    public static class RenderFactory implements IRenderFactory<DesmoxyteEntity> {
        @Override
        public EntityRenderer<? super DesmoxyteEntity> createRenderFor(EntityRendererManager manager) {
            return new DesmoxyteRenderer(manager);
        }

    }
}
