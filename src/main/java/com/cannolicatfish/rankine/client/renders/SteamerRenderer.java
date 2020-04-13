package com.cannolicatfish.rankine.client.renders;

import com.cannolicatfish.rankine.client.models.SteamerModel;
import com.cannolicatfish.rankine.entities.PeridotMantleGolemEntity;
import com.cannolicatfish.rankine.entities.SteamerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class SteamerRenderer extends MobRenderer<SteamerEntity, SteamerModel<SteamerEntity>> {
    public static final SteamerRenderer.RenderFactory instance = new SteamerRenderer.RenderFactory();
    private static final ResourceLocation STEAMER_TEXTURES = new ResourceLocation("rankine:textures/entity/steamer.png");

    public SteamerRenderer(EntityRendererManager p_i46186_1_) {
        super(p_i46186_1_, new SteamerModel(), 0.5F);
    }

    @Override
    protected boolean canRenderName(SteamerEntity entity) {
        return false;
    }

    protected void preRenderCallback(SteamerEntity p_225620_1_, MatrixStack p_225620_2_, float p_225620_3_) {
        float lvt_4_1_ = p_225620_1_.getCreeperFlashIntensity(p_225620_3_);
        float lvt_5_1_ = 1.0F + MathHelper.sin(lvt_4_1_ * 100.0F) * lvt_4_1_ * 0.01F;
        lvt_4_1_ = MathHelper.clamp(lvt_4_1_, 0.0F, 1.0F);
        lvt_4_1_ *= lvt_4_1_;
        lvt_4_1_ *= lvt_4_1_;
        float lvt_6_1_ = (1.0F + lvt_4_1_ * 0.4F) * lvt_5_1_;
        float lvt_7_1_ = (1.0F + lvt_4_1_ * 0.1F) / lvt_5_1_;
        p_225620_2_.scale(lvt_6_1_, lvt_7_1_, lvt_6_1_);
    }

    protected float getOverlayProgress(SteamerEntity p_225625_1_, float p_225625_2_) {
        float lvt_3_1_ = p_225625_1_.getCreeperFlashIntensity(p_225625_2_);
        return (int)(lvt_3_1_ * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(lvt_3_1_, 0.5F, 1.0F);
    }

    public ResourceLocation getEntityTexture(SteamerEntity p_110775_1_) {
        return STEAMER_TEXTURES;
    }

    public static class RenderFactory implements IRenderFactory<SteamerEntity> {
        @Override
        public EntityRenderer<? super SteamerEntity> createRenderFor(EntityRendererManager manager) {
            return new SteamerRenderer(manager);
        }

    }
}

