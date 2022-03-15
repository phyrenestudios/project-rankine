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

        texWidth = 32;
        texHeight = 32;

        head = new ModelRenderer(this);
        head.setPos(0.0F, 24.0F, 0.0F);
        head.texOffs(0, 0).addBox( -2.0F, -4.0F, -8.0F, 4, 4, 3, 0.0F);
        head.texOffs(0, 15).addBox( -1.5F, -3.0F, -5.0F, 3, 3, 1, 0.0F);

        antena_right = new ModelRenderer(this);
        antena_right.setPos(-2.0F, -4.0F, -6.5F);
        setRotationAngle(antena_right, 0.0F, 0.0F, -0.6981F);
        head.addChild(antena_right);
        antena_right.texOffs(10, 15).addBox( -0.5F, -1.0F, -0.5F, 1, 2, 1, 0.0F);

        antena_left = new ModelRenderer(this);
        antena_left.setPos(2.0F, -4.0F, -6.5F);
        setRotationAngle(antena_left, 0.0F, 0.0F, 0.6981F);
        head.addChild(antena_left);
        antena_left.texOffs(0, 7).addBox( -0.5F, -1.0F, -0.5F, 1, 2, 1, 0.0F);

        body = new ModelRenderer(this);
        body.setPos(0.0F, 24.0F, 0.0F);

        body_segments = new ModelRenderer(this);
        body_segments.setPos(0.0F, 0.0F, 0.0F);
        body.addChild(body_segments);
        body_segments.texOffs(10, 3).addBox( -1.0F, -2.0F, -4.0F, 2, 2, 4, 0.0F);
        body_segments.texOffs(8, 9).addBox( 0.0F, -2.0F, 0.0F, 2, 2, 4, 0.0F);
        body_segments.texOffs( 0, 7).addBox( -1.0F, -2.0F, 4.0F, 2, 2, 4, 0.0F);

        legs = new ModelRenderer(this);
        legs.setPos(0.0F, 0.0F, 0.0F);
        body.addChild(legs);
        legs.texOffs(14, 15).addBox( -1.0F, -1.0F, 2.0F, 1, 1, 1, 0.0F);
        legs.texOffs(14, 1).addBox( 1.0F, -1.0F, -2.0F, 1, 1, 1, 0.0F);
        legs.texOffs(4, 13).addBox( 2.0F, -1.0F, 1.0F, 1, 1, 1, 0.0F);
        legs.texOffs(0, 13).addBox( -2.0F, -1.0F, -3.0F, 1, 1, 1, 0.0F);
        legs.texOffs(11, 0).addBox( 1.0F, -1.0F, 6.0F, 1, 1, 1, 0.0F);
        legs.texOffs(8, 9).addBox(-2.0F, -1.0F, 5.0F, 1, 1, 1, 0.0F);
    }

    public void prepareMobModel(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {

    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    private void setRotationOffset(ModelRenderer renderer, float x, float y, float z) {
        renderer.xRot = x;
        renderer.yRot = y;
        renderer.zRot = z;
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * 0.017453292F;
        this.head.yRot = netHeadYaw * 0.017453292F;
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(this.body);
    }
}
