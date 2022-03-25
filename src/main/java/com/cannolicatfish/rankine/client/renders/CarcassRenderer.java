package com.cannolicatfish.rankine.client.renders;

import com.cannolicatfish.rankine.entities.CarcassEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import com.mojang.math.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CarcassRenderer extends EntityRenderer<CarcassEntity> {
    private final net.minecraft.client.renderer.entity.ItemRenderer itemRenderer;
    private final float scale;
    private final boolean fullBright;

    public CarcassRenderer(EntityRendererProvider.Context renderManager, net.minecraft.client.renderer.entity.ItemRenderer p_i226035_2_, float p_i226035_3_, boolean p_i226035_4_) {
        super(renderManager);
        this.itemRenderer = p_i226035_2_;
        this.scale = p_i226035_3_;
        this.fullBright = p_i226035_4_;
    }

    public CarcassRenderer(EntityRendererProvider.Context renderManagerIn) {
        this(renderManagerIn, Minecraft.getInstance().getItemRenderer(), 1.0F, false);
    }

    protected int getBlockLightLevel(CarcassEntity entityIn, BlockPos pos) {
        return this.fullBright ? 15 : super.getBlockLightLevel(entityIn, pos);
    }

    public void render(CarcassEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        if (entityIn.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(entityIn) < 12.25D)) {
            matrixStackIn.pushPose();
            matrixStackIn.scale(this.scale, this.scale, this.scale);
            matrixStackIn.mulPose(this.entityRenderDispatcher.cameraOrientation());
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            this.itemRenderer.renderStatic(entityIn.getItem(), ItemTransforms.TransformType.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn, entityIn.getId());
            matrixStackIn.popPose();
            super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
    }
    public ResourceLocation getTextureLocation(CarcassEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

}
