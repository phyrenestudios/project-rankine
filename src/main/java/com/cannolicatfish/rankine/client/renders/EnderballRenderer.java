package com.cannolicatfish.rankine.client.renders;

import com.cannolicatfish.rankine.entities.EnderballEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnderballRenderer extends EntityRenderer<EnderballEntity> {
    private final net.minecraft.client.renderer.ItemRenderer itemRenderer;
    private final float scale;
    private final boolean field_229126_f_;

    public EnderballRenderer(EntityRendererManager renderManager, net.minecraft.client.renderer.ItemRenderer p_i226035_2_, float p_i226035_3_, boolean p_i226035_4_) {
        super(renderManager);
        this.itemRenderer = p_i226035_2_;
        this.scale = p_i226035_3_;
        this.field_229126_f_ = p_i226035_4_;
    }

    public EnderballRenderer(EntityRendererManager renderManagerIn) {
        this(renderManagerIn, Minecraft.getInstance().getItemRenderer(), 1.0F, false);
    }

    protected int getBlockLight(EnderballEntity entityIn, BlockPos pos) {
        return this.field_229126_f_ ? 15 : super.getBlockLight(entityIn, pos);
    }

    public void render(EnderballEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (entityIn.ticksExisted >= 2 || !(this.renderManager.info.getRenderViewEntity().getDistanceSq(entityIn) < 12.25D)) {
            matrixStackIn.push();
            matrixStackIn.scale(this.scale, this.scale, this.scale);
            matrixStackIn.rotate(this.renderManager.getCameraOrientation());
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F));
            this.itemRenderer.renderItem(entityIn.getItem(), ItemCameraTransforms.TransformType.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
            matrixStackIn.pop();
            super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
    }
    public ResourceLocation getEntityTexture(EnderballEntity entity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }

}
