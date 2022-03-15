package com.cannolicatfish.rankine.client.renders;

import com.cannolicatfish.rankine.client.models.entities.MantleGolemModel;
import com.cannolicatfish.rankine.entities.MantleGolemEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class MantleGolemRenderer extends LivingRenderer<MantleGolemEntity, MantleGolemModel<MantleGolemEntity>>
{

    public static final RenderFactory instance = new RenderFactory();
    public MantleGolemRenderer(EntityRendererManager manager)
    {
        super(manager, new MantleGolemModel(), 0f);
    }

    @Override
    protected boolean shouldShowName(MantleGolemEntity entity) {
        return false;
    }

    @Override
    public ResourceLocation getTextureLocation(MantleGolemEntity entity)
    {
        return new ResourceLocation("rankine:textures/entity/mantle_golem.png");
    }

    public static class RenderFactory implements IRenderFactory<MantleGolemEntity>
    {
        @Override
        public EntityRenderer<? super MantleGolemEntity> createRenderFor(EntityRendererManager manager)
        {
            return new MantleGolemRenderer(manager);
        }

    }

}
