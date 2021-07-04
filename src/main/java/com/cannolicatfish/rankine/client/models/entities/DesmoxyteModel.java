package com.cannolicatfish.rankine.client.models.entities;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.entity.model.SilverfishModel;
import net.minecraft.client.renderer.entity.model.TintedAgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

import java.util.Arrays;

public class DesmoxyteModel <T extends Entity> extends TintedAgeableModel<T> {
    private final ModelRenderer head;
    private final ModelRenderer antena_right;
    private final ModelRenderer antena_left;
    private final ModelRenderer body;
    private final ModelRenderer body_segments;
    private final ModelRenderer legs;

    public DesmoxyteModel() {

        textureWidth = 32;
        textureHeight = 32;

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 24.0F, 0.0F);
        head.setTextureOffset(0, 0).addBox( -2.0F, -4.0F, -8.0F, 4, 4, 3, 0.0F);
        head.setTextureOffset(0, 15).addBox( -1.5F, -3.0F, -5.0F, 3, 3, 1, 0.0F);

        antena_right = new ModelRenderer(this);
        antena_right.setRotationPoint(-2.0F, -4.0F, -6.5F);
        setRotationAngle(antena_right, 0.0F, 0.0F, -0.6981F);
        head.addChild(antena_right);
        antena_right.setTextureOffset(10, 15).addBox( -0.5F, -1.0F, -0.5F, 1, 2, 1, 0.0F);

        antena_left = new ModelRenderer(this);
        antena_left.setRotationPoint(2.0F, -4.0F, -6.5F);
        setRotationAngle(antena_left, 0.0F, 0.0F, 0.6981F);
        head.addChild(antena_left);
        antena_left.setTextureOffset(0, 7).addBox( -0.5F, -1.0F, -0.5F, 1, 2, 1, 0.0F);

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 24.0F, 0.0F);

        body_segments = new ModelRenderer(this);
        body_segments.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(body_segments);
        body_segments.setTextureOffset(10, 3).addBox( -1.0F, -2.0F, -4.0F, 2, 2, 4, 0.0F);
        body_segments.setTextureOffset(8, 9).addBox( 0.0F, -2.0F, 0.0F, 2, 2, 4, 0.0F);
        body_segments.setTextureOffset( 0, 7).addBox( -1.0F, -2.0F, 4.0F, 2, 2, 4, 0.0F);

        legs = new ModelRenderer(this);
        legs.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(legs);
        legs.setTextureOffset(14, 15).addBox( -1.0F, -1.0F, 2.0F, 1, 1, 1, 0.0F);
        legs.setTextureOffset(14, 1).addBox( 1.0F, -1.0F, -2.0F, 1, 1, 1, 0.0F);
        legs.setTextureOffset(4, 13).addBox( 2.0F, -1.0F, 1.0F, 1, 1, 1, 0.0F);
        legs.setTextureOffset(0, 13).addBox( -2.0F, -1.0F, -3.0F, 1, 1, 1, 0.0F);
        legs.setTextureOffset(11, 0).addBox( 1.0F, -1.0F, 6.0F, 1, 1, 1, 0.0F);
        legs.setTextureOffset(8, 9).addBox(-2.0F, -1.0F, 5.0F, 1, 1, 1, 0.0F);
    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {

    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    private void setRotationOffset(ModelRenderer renderer, float x, float y, float z) {
        renderer.rotateAngleX = x;
        renderer.rotateAngleY = y;
        renderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelRenderer> getBodyParts() {
        return ImmutableList.of(this.body);
    }
}
