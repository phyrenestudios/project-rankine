package com.cannolicatfish.rankine.client.renders;

import com.cannolicatfish.rankine.entities.RankineBoatEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BoatModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class RankineBoatRenderer extends EntityRenderer<RankineBoatEntity> {
    private static final ResourceLocation[] BOAT_TEXTURES = new ResourceLocation[]{
            new ResourceLocation("textures/entity/boat/oak.png"),
            new ResourceLocation("textures/entity/boat/spruce.png"),
            new ResourceLocation("textures/entity/boat/birch.png"),
            new ResourceLocation("textures/entity/boat/jungle.png"),
            new ResourceLocation("textures/entity/boat/acacia.png"),
            new ResourceLocation("textures/entity/boat/dark_oak.png"),
            new ResourceLocation("rankine:textures/entity/boat/cedar.png"),
            new ResourceLocation("rankine:textures/entity/boat/coconut_palm.png"),
            new ResourceLocation("rankine:textures/entity/boat/pinyon_pine.png"),
            new ResourceLocation("rankine:textures/entity/boat/juniper.png"),
            new ResourceLocation("rankine:textures/entity/boat/balsam_fir.png"),
            new ResourceLocation("rankine:textures/entity/boat/magnolia.png"),
            new ResourceLocation("rankine:textures/entity/boat/eastern_hemlock.png"),
            new ResourceLocation("rankine:textures/entity/boat/maple.png"),
            new ResourceLocation("rankine:textures/entity/boat/black_walnut.png"),
            new ResourceLocation("rankine:textures/entity/boat/sharinga.png"),
            new ResourceLocation("rankine:textures/entity/boat/cork_oak.png"),
            new ResourceLocation("rankine:textures/entity/boat/cinnamon.png"),
            new ResourceLocation("rankine:textures/entity/boat/bamboo.png")
    };
    protected final BoatModel modelBoat = new BoatModel();
    public static final RankineBoatRenderer.RenderFactory instance = new RankineBoatRenderer.RenderFactory();

    public RankineBoatRenderer(EntityRendererManager renderManagerIn)
    {
        super(renderManagerIn);
        this.shadowSize = 0.8F;
    }

    public void render(RankineBoatEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, 0.375D, 0.0D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F - entityYaw));
        float f = (float)entityIn.getTimeSinceHit() - partialTicks;
        float f1 = entityIn.getDamageTaken() - partialTicks;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f > 0.0F) {
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(MathHelper.sin(f) * f * f1 / 10.0F * (float)entityIn.getForwardDirection()));
        }

        float f2 = entityIn.getRockingAngle(partialTicks);
        if (!MathHelper.epsilonEquals(f2, 0.0F)) {
            matrixStackIn.rotate(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), entityIn.getRockingAngle(partialTicks), true));
        }

        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90.0F));
        this.modelBoat.setRotationAngles(entityIn, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.modelBoat.getRenderType(this.getEntityTexture(entityIn)));
        this.modelBoat.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        IVertexBuilder ivertexbuilder1 = bufferIn.getBuffer(RenderType.getWaterMask());
        this.modelBoat.func_228245_c_().render(matrixStackIn, ivertexbuilder1, packedLightIn, OverlayTexture.NO_OVERLAY);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(RankineBoatEntity entity) {
        return BOAT_TEXTURES[entity.getRankineBoatType().ordinal()];
    }

    public static class RenderFactory implements IRenderFactory<RankineBoatEntity> {
        @Override
        public EntityRenderer<? super RankineBoatEntity> createRenderFor(EntityRendererManager manager) {
            return new RankineBoatRenderer(manager);
        }

    }


}