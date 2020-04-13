package com.cannolicatfish.rankine.client.models;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SteamerModel<T extends Entity> extends SegmentedModel<T> {
    private final ModelRenderer head;
    private final ModelRenderer creeperArmor;
    private final ModelRenderer body;
    private final ModelRenderer leg1;
    private final ModelRenderer leg2;
    private final ModelRenderer leg3;
    private final ModelRenderer leg4;


    public SteamerModel() {
        this(0.0F);
    }

    public SteamerModel(float p_i46366_1_) {
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_i46366_1_);
        this.head.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.creeperArmor = new ModelRenderer(this, 32, 0);
        this.creeperArmor.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_i46366_1_ + 0.5F);
        this.creeperArmor.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.body = new ModelRenderer(this, 16, 16);
        this.body.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, p_i46366_1_);
        this.body.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.leg1 = new ModelRenderer(this, 0, 16);
        this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, p_i46366_1_);
        this.leg1.setRotationPoint(-2.0F, 18.0F, 4.0F);
        this.leg2 = new ModelRenderer(this, 0, 16);
        this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, p_i46366_1_);
        this.leg2.setRotationPoint(2.0F, 18.0F, 4.0F);
        this.leg3 = new ModelRenderer(this, 0, 16);
        this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, p_i46366_1_);
        this.leg3.setRotationPoint(-2.0F, 18.0F, -4.0F);
        this.leg4 = new ModelRenderer(this, 0, 16);
        this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, p_i46366_1_);
        this.leg4.setRotationPoint(2.0F, 18.0F, -4.0F);
    }

    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.head, this.body, this.leg1, this.leg2, this.leg3, this.leg4);
    }

    public void setRotationAngles(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
        this.head.rotateAngleY = p_225597_5_ * 0.017453292F;
        this.head.rotateAngleX = p_225597_6_ * 0.017453292F;
        this.leg1.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F) * 1.4F * p_225597_3_;
        this.leg2.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F + 3.1415927F) * 1.4F * p_225597_3_;
        this.leg3.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F + 3.1415927F) * 1.4F * p_225597_3_;
        this.leg4.rotateAngleX = MathHelper.cos(p_225597_2_ * 0.6662F) * 1.4F * p_225597_3_;
    }
}
