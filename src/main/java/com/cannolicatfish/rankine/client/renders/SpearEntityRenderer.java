package com.cannolicatfish.rankine.client.renders;

import com.cannolicatfish.rankine.client.models.BronzeSpearEntityModel;
import com.cannolicatfish.rankine.client.models.FlintSpearEntityModel;
import com.cannolicatfish.rankine.client.models.SpearEntityModel;
import com.cannolicatfish.rankine.client.models.SteelSpearEntityModel;
import com.cannolicatfish.rankine.entities.SpearEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpearEntityRenderer extends EntityRenderer<SpearEntity> {
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

    private final SpearEntityModel spearModel = new SpearEntityModel();


    public SpearEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(SpearEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationYaw, entityIn.rotationYaw) - 90.0F));
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch) + 90.0F));
        IVertexBuilder ivertexbuilder = net.minecraft.client.renderer.ItemRenderer.getBuffer(bufferIn, this.spearModel.getRenderType(this.getEntityTexture(entityIn)), false, false);
        this.spearModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(SpearEntity entity) {
        switch (entity.type)
        {
            case 0:
                return FLINT_SPEAR;
            case 1:
                return BRONZE_SPEAR;
            case 2:
                return IRON_SPEAR;
            case 3:
                return STEEL_SPEAR;
            case 4:
                return ROSE_GOLD_SPEAR;
            case 5:
                return WHITE_GOLD_SPEAR;
            case 6:
                return GREEN_GOLD_SPEAR;
            case 7:
                return BLUE_GOLD_SPEAR;
            case 8:
                return PURPLE_GOLD_SPEAR;
            case 9:
                return AMALGAM_SPEAR;
            default:
                return IRON_SPEAR;
        }

    }
}
