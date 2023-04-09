package com.cannolicatfish.rankine.client.renders;

import com.cannolicatfish.rankine.client.models.entities.SpearEntityModel;
import com.cannolicatfish.rankine.entities.SpearEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class SpearEntityRenderer extends EntityRenderer<SpearEntity> {
    public static final SpearEntityRenderer.SpearRenderFactory instance = new SpearEntityRenderer.SpearRenderFactory();
    public static final ResourceLocation FLINT_SPEAR = new ResourceLocation("rankine:textures/entity/flint_spear.png");
    public static final ResourceLocation BRONZE_SPEAR = new ResourceLocation("rankine:textures/entity/bronze_spear.png");
    public static final ResourceLocation IRON_SPEAR = new ResourceLocation("rankine:textures/entity/iron_spear.png");
    public static final ResourceLocation STEEL_SPEAR = new ResourceLocation("rankine:textures/entity/steel_spear.png");
    public static final ResourceLocation ROSE_GOLD_SPEAR = new ResourceLocation("rankine:textures/entity/rose_gold_spear.png");
    public static final ResourceLocation WHITE_GOLD_SPEAR = new ResourceLocation("rankine:textures/entity/white_gold_spear.png");
    public static final ResourceLocation GREEN_GOLD_SPEAR = new ResourceLocation("rankine:textures/entity/green_gold_spear.png");
    public static final ResourceLocation BLUE_GOLD_SPEAR = new ResourceLocation("rankine:textures/entity/blue_gold_spear.png");
    public static final ResourceLocation PURPLE_GOLD_SPEAR = new ResourceLocation("rankine:textures/entity/purple_gold_spear.png");
    public static final ResourceLocation AMALGAM_SPEAR = new ResourceLocation("rankine:textures/entity/amalgam_spear.png");

    private final SpearEntityModel spearModel;


    public SpearEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager);
        this.spearModel = new SpearEntityModel(renderManager.bakeLayer(ModelLayers.TRIDENT));
    }

    @Override
    public void render(SpearEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90.0F));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot()) + 90.0F));
        VertexConsumer ivertexbuilder = net.minecraft.client.renderer.entity.ItemRenderer.getFoilBuffer(bufferIn, this.spearModel.renderType(this.getTextureLocation(entityIn)), false, false);
        this.spearModel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.popPose();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(SpearEntity entity) {
        return entity.type == null ? new ResourceLocation("rankine:textures/entity/flint_spear.png") : entity.type;

    }

    public static class SpearRenderFactory implements EntityRendererProvider<SpearEntity> {

        @Override
        public EntityRenderer<SpearEntity> create(Context manager) {
            return new SpearEntityRenderer(manager);
        }


    }
}
