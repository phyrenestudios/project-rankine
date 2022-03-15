package com.cannolicatfish.rankine.client.models.entities;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.TintedAgeableModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class DemonyteModel <T extends Entity> extends TintedAgeableModel<T> {
    private final ModelRenderer head;
    private final ModelRenderer antena_right;
    private final ModelRenderer antena_left;
    private final ModelRenderer antena_back;
    private final ModelRenderer antena_back2;
    private final ModelRenderer body;
    private final ModelRenderer body_segments;
    private final ModelRenderer tail1;
    private final ModelRenderer tail2;
    private final ModelRenderer legs;

    public DemonyteModel() {
        texWidth = 32;
        texHeight = 32;

        head = new ModelRenderer(this);
        head.setPos(0.0F, 24.0F, 0.0F);
        head.texOffs(0, 0).addBox(-2.0F, -4.0F, -8.0F, 4, 4, 3, 0.0F);
        head.texOffs(0, 13).addBox( -1.5F, -3.0F, -5.0F, 3, 3, 1, 0.0F);

        antena_right = new ModelRenderer(this);
        antena_right.setPos(-2.0F, -4.0F, -6.5F);
        setRotationAngle(antena_right, 0.0F, 0.0F, -0.6981F);
        head.addChild(antena_right);
        antena_right.texOffs(4, 17).addBox( -0.5F, -1.0F, -0.5F, 1, 2, 1, 0.0F);

        antena_left = new ModelRenderer(this);
        antena_left.setPos(2.0F, -4.0F, -6.5F);
        setRotationAngle(antena_left, 0.0F, 0.0F, 0.6981F);
        head.addChild(antena_left);
        antena_left.texOffs(0, 17).addBox(-0.5F, -1.0F, -0.5F, 1, 2, 1, 0.0F);

        antena_back = new ModelRenderer(this);
        antena_back.setPos(0.0F, -3.5F, -4.75F);
        setRotationAngle(antena_back, -0.7854F, 0.0F, 0.0F);
        head.addChild(antena_back);
        antena_back.texOffs(14, 8).addBox(-0.5F, -1.3232F, -0.3232F, 1, 2, 1, 0.0F);

        antena_back2 = new ModelRenderer(this);
        antena_back2.setPos(0.0F, -2.25F, -2.75F);
        setRotationAngle(antena_back2, -0.7854F, 0.0F, 0.0F);
        head.addChild(antena_back2);
        antena_back2.texOffs(14, 14).addBox(-0.5F, -1.3232F, -0.3232F, 1, 2, 1, 0.0F);

        body = new ModelRenderer(this);
        body.setPos(0.0F, 24.0F, 0.0F);

        body_segments = new ModelRenderer(this);
        body_segments.setPos(0.0F, 0.0F, 0.0F);
        body.addChild(body_segments);
        body_segments.texOffs(7, 9).addBox(-1.0F, -2.0F, -4.0F, 2, 2, 3, 0.0F);
        body_segments.texOffs(0, 7).addBox( 0.0F, -2.0F, -1.0F, 2, 2, 3, 0.0F);
        body_segments.texOffs(11, 4).addBox(-1.0F, -1.0F, 2.0F, 2, 1, 3, 0.0F);

        tail1 = new ModelRenderer(this);
        tail1.setPos(0.0F, -2.0F, 5.25F);
        setRotationAngle(tail1, 0.6981F, 0.0F, 0.0F);
        body.addChild(tail1);
        tail1.texOffs(8, 14).addBox(-0.5F, 0.266F, -1.6428F, 1, 1, 2, 0.0F);

        tail2 = new ModelRenderer(this);
        tail2.setPos(0.0F, -3.0F, 5.25F);
        setRotationAngle(tail2, -1.2217F, 0.0F, 0.0F);
        body.addChild(tail2);
        tail2.texOffs(11, 0).addBox( -0.5F, -0.5639F, -0.4446F, 1, 1, 2, 0.0F);

        legs = new ModelRenderer(this);
        legs.setPos(0.0F, 0.0F, 0.0F);
        body.addChild(legs);
        legs.texOffs(16, 17).addBox(-1.0F, -1.0F, 0.0F, 1, 1, 1, 0.0F);
        legs.texOffs(12, 17).addBox(1.0F, -1.0F, -3.0F, 1, 1, 1, 0.0F);
        legs.texOffs(8, 17).addBox( 2.0F, -1.0F, 0.0F, 1, 1, 1, 0.0F);
        legs.texOffs(16, 2).addBox( -2.0F, -1.0F, -3.0F, 1, 1, 1, 0.0F);
        legs.texOffs(15, 0).addBox( 1.0F, -1.0F, 3.0F, 1, 1, 1, 0.0F);
        legs.texOffs(7, 7).addBox(-2.0F, -1.0F, 3.0F, 1, 1, 1, 0.0F);
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
