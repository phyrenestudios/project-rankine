package com.cannolicatfish.rankine.client.models;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.entity.model.SilverfishModel;
import net.minecraft.client.renderer.entity.model.TintedAgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

import java.util.Arrays;

public class DesmoxyteModel <T extends Entity> extends TintedAgeableModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer body1;
    private final ModelRenderer body2;
    private final ModelRenderer body3;
    private final ModelRenderer head;
    private final ModelRenderer headmain;
    private final ModelRenderer antena;
    private final ModelRenderer antenaright;
    private final ModelRenderer antenaleft;

    public DesmoxyteModel() {

        textureWidth = 32;
        textureHeight = 32;

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 24.0F, 0.0F);

        body1 = new ModelRenderer(this);
        body1.setRotationPoint(-1.0F, -1.0F, -2.5F);
        body.addChild(body1);
        body1.setTextureOffset(9,3).addBox(-1.0F, -1.0F, -1.5F, 2, 2, 3, 0.0F);


        body2 = new ModelRenderer(this);
        body2.setRotationPoint(0.0F, -1.0F, 0.5F);
        body.addChild(body2);
        body2.setTextureOffset(7,8).addBox(-1.0F, -1.0F, -1.5F, 2, 2, 3, 0.0F);

        body3 = new ModelRenderer(this);
        body3.setRotationPoint(-1.0F, -1.0F, 3.5F);
        body.addChild(body3);
        body3.setTextureOffset(0,6).addBox(-1.0F, -1.0F, -1.5F, 2, 2, 3, 0.0F);

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 24.0F, 0.0F);

        headmain = new ModelRenderer(this);
        headmain.setRotationPoint(-0.5F, -1.5F, -5.5F);
        head.addChild(headmain);
        headmain.setTextureOffset(0,0).addBox(-1.5F, -1.5F, -1.5F, 3, 3, 3, 0.0F);

        antena = new ModelRenderer(this);
        antena.setRotationPoint(0.0F, 0.0F, 0.0F);
        head.addChild(antena);

        antenaright = new ModelRenderer(this);
        antenaright.setRotationPoint(-2.0F, -3.0F, -6.0F);
        this.setRotationOffset(antenaright, 0.0F, 0.0F, -1.0472F);
        antena.addChild(antenaright);
        antenaright.setTextureOffset(0,11).addBox(-0.7165F, -1.0F, 0.0F, 1, 2, 1, 0.0F);

        antenaleft = new ModelRenderer(this);
        antenaleft.setRotationPoint(1.0F, -3.0F, -6.0F);
        this.setRotationOffset(antenaleft, 0.0F, 0.0F, 1.0472F);
        antena.addChild(antenaleft);
        antenaleft.setTextureOffset(9,0).addBox(-0.2835F, -1.0F, 0.0F, 1, 2, 1, 0.0F);
    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {

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
