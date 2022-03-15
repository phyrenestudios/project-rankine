package com.cannolicatfish.rankine.client.models.entities;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.TintedAgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class DragonyteModel <T extends Entity> extends TintedAgeableModel<T> {
    private final ModelRenderer head;
    private final ModelRenderer antena_right;
    private final ModelRenderer antena_left;
    private final ModelRenderer body;
    private final ModelRenderer body_segments;
    private final ModelRenderer legs;
    private final ModelRenderer spines;

    public DragonyteModel() {
        texWidth = 32;
        texHeight = 32;

        head = new ModelRenderer(this);
        head.setPos(0.0F, 24.0F, 0.0F);
        head.texOffs(0, 0).addBox( -2.0F, -3.0F, -8.0F, 4, 3, 3, 0.0F);
        head.texOffs(11, 0).addBox(-1.5F, -2.0F, -5.0F, 3, 2, 1, 0.0F);

        antena_right = new ModelRenderer(this);
        antena_right.setPos(-2.0F, -4.0F, -6.5F);
        setRotationAngle(antena_right, 0.0F, 0.0F, -0.6981F);
        head.addChild(antena_right);
        antena_right.texOffs(15, 17).addBox( -1.0F, 0.5F, -0.5F, 1, 1, 1, 0.0F);

        antena_left = new ModelRenderer(this);
        antena_left.setPos(2.0F, -4.0F, -6.5F);
        setRotationAngle(antena_left, 0.0F, 0.0F, 0.6981F);
        head.addChild(antena_left);
        antena_left.texOffs(11, 17).addBox( 0.0F, 0.5F, -0.5F, 1, 1, 1, 0.0F);

        body = new ModelRenderer(this);
        body.setPos(0.0F, 24.0F, 0.0F);

        body_segments = new ModelRenderer(this);
        body_segments.setPos(0.0F, 0.0F, 0.0F);
        body.addChild(body_segments);
        body_segments.texOffs(0, 11).addBox( -1.0F, -2.0F, -4.0F, 2, 2, 3, 0.0F);
        body_segments.texOffs(7, 8).addBox(-2.0F, -2.0F, -1.0F, 2, 2, 3, 0.0F);
        body_segments.texOffs(0, 6).addBox( -1.0F, -2.0F, 2.0F, 2, 2, 3, 0.0F);
        body_segments.texOffs(12, 4).addBox(-0.5F, -1.0F, 5.0F, 1, 1, 2, 0.0F);

        legs = new ModelRenderer(this);
        legs.setPos(0.0F, 0.0F, 0.0F);
        body.addChild(legs);
        legs.texOffs(16, 12).addBox(-3.0F, -1.0F, 0.0F, 1, 1, 1, 0.0F);
        legs.texOffs(16, 3).addBox(1.0F, -1.0F, -3.0F, 1, 1, 1, 0.0F);
        legs.texOffs(8, 16).addBox(0.0F, -1.0F, 0.0F, 1, 1, 1, 0.0F);
        legs.texOffs(4, 16).addBox(-2.0F, -1.0F, -3.0F, 1, 1, 1, 0.0F);
        legs.texOffs(0, 16).addBox( 1.0F, -1.0F, 3.0F, 1, 1, 1, 0.0F);
        legs.texOffs(7, 6).addBox( -2.0F, -1.0F, 3.0F, 1, 1, 1, 0.0F);

        spines = new ModelRenderer(this);
        spines.setPos(0.0F, 0.0F, 0.0F);
        body.addChild(spines);
        spines.texOffs(14, 7).addBox(-0.5F, -2.5F, 2.5F, 1, 1, 2, 0.0F);
        spines.texOffs(14, 14).addBox( -1.5F, -2.5F, -0.5F, 1, 1, 2, 0.0F);
        spines.texOffs(10, 13).addBox( -0.5F, -2.5F, -3.5F, 1, 1, 2, 0.0F);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * 0.017453292F;
        this.head.yRot = netHeadYaw * 0.017453292F;
    }


    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
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