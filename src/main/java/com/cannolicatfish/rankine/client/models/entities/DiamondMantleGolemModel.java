package com.cannolicatfish.rankine.client.models.entities;

import com.cannolicatfish.rankine.entities.DiamondMantleGolemEntity;
import com.cannolicatfish.rankine.entities.MantleGolemEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DiamondMantleGolemModel<T extends DiamondMantleGolemEntity> extends SegmentedModel<T> {
    private final ModelRenderer GolemBody;
    private final ModelRenderer GolemRightArm;
    private final ModelRenderer GolemLeftArm;
    private final ModelRenderer GolemLeftLeg;
    private final ModelRenderer GolemRightLeg;

    public DiamondMantleGolemModel() {
        int i = 128;
        int j = 128;
        this.GolemBody = (new ModelRenderer(this)).setTexSize(128, 128);
        this.GolemBody.setPos(0.0F, -7.0F, 0.0F);
        this.GolemBody.texOffs(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18.0F, 12.0F, 11.0F, 0.0F);
        this.GolemBody.texOffs(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9.0F, 5.0F, 6.0F, 0.5F);
        this.GolemRightArm = (new ModelRenderer(this)).setTexSize(128, 128);
        this.GolemRightArm.setPos(0.0F, -7.0F, 0.0F);
        this.GolemRightArm.texOffs(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, 0.0F);
        this.GolemLeftArm = (new ModelRenderer(this)).setTexSize(128, 128);
        this.GolemLeftArm.setPos(0.0F, -7.0F, 0.0F);
        this.GolemLeftArm.texOffs(60, 58).addBox(9.0F, -2.5F, -3.0F, 4.0F, 30.0F, 6.0F, 0.0F);
        this.GolemLeftLeg = (new ModelRenderer(this, 0, 22)).setTexSize(128, 128);
        this.GolemLeftLeg.setPos(-4.0F, 11.0F, 0.0F);
        this.GolemLeftLeg.texOffs(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, 0.0F);
        this.GolemRightLeg = (new ModelRenderer(this, 0, 22)).setTexSize(128, 128);
        this.GolemRightLeg.mirror = true;
        this.GolemRightLeg.texOffs(60, 0).setPos(5.0F, 11.0F, 0.0F);
        this.GolemRightLeg.addBox(-3.5F, -3.0F, -3.0F, 6.0F, 16.0F, 5.0F, 0.0F);
    }

    public Iterable<ModelRenderer> parts() {
        return ImmutableList.of(this.GolemBody, this.GolemLeftLeg, this.GolemRightLeg, this.GolemRightArm, this.GolemLeftArm);
    }

    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        /*
        this.GolemHead.rotateAngleY = netHeadYaw * ((float)Math.PI / 180F);
        this.GolemHead.rotateAngleX = headPitch * ((float)Math.PI / 180F);
         */
        this.GolemLeftLeg.xRot = -1.5F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
        this.GolemRightLeg.xRot = 1.5F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
        this.GolemLeftLeg.yRot = 0.0F;
        this.GolemRightLeg.yRot = 0.0F;
    }

    public void prepareMobModel(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        int i = entityIn.getAttackAnimationTick();
        if (i > 0) {
            this.GolemRightArm.xRot = -2.0F + 1.5F * this.triangleWave((float)i - partialTick, 10.0F);
            this.GolemLeftArm.xRot = -2.0F + 1.5F * this.triangleWave((float)i - partialTick, 10.0F);
        } else {
            int j = entityIn.getOfferFlowerTick();
            if (j > 0) {
                this.GolemRightArm.xRot = -0.8F + 0.025F * this.triangleWave((float)j, 70.0F);
                this.GolemLeftArm.xRot = 0.0F;
            } else {
                this.GolemRightArm.xRot = (-0.2F + 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
                this.GolemLeftArm.xRot = (-0.2F - 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
            }
        }

    }

    private float triangleWave(float p_78172_1_, float p_78172_2_) {
        return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
    }

    public ModelRenderer getArmHoldingRose() {
        return this.GolemRightArm;
    }
}

