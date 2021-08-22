package com.cannolicatfish.rankine.client.models.entities;

import com.cannolicatfish.rankine.entities.BeaverEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.TintedAgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;

@OnlyIn(Dist.CLIENT)
public class BeaverModel<T extends BeaverEntity> extends TintedAgeableModel<T> {
	private final ModelRenderer tail;
	private final ModelRenderer face;
	private final ModelRenderer body;
	private final ModelRenderer backleft;
	private final ModelRenderer backright;
	private final ModelRenderer frontleft;
	private final ModelRenderer frontright;

	public BeaverModel() {

		textureWidth = 64;
		textureHeight = 32;

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 21.0F, 4.5F);
		tail.setTextureOffset(12, 22).addBox(-2.0F, -2.0F, -2.5F, 4, 2, 2, 0.0F);
		tail.setTextureOffset(8, 16).addBox(-2.0F, 0.0F, -0.5F, 4, 2, 4, 0.0F);

		face = new ModelRenderer(this);
		face.setRotationPoint(0.0F, 20.25F, -6.5F);
		face.setTextureOffset(0,0).addBox(-2.0F, -3.25F, -0.5F,4,4,3,0.0F);
		face.setTextureOffset(25, 27).addBox( -1.0F, 0.75F, -0.5F, 1, 1, 1, 0.0F);
		face.setTextureOffset(31, 27).addBox( 0.0F, 0.75F, -0.5F, 1, 1, 1, 0.0F);
		face.setTextureOffset(13,23).addBox(-1.0F, -2.25F, -1.5F,2,2,1,0.0f);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 18.8333F, -1.0F);
		body.setTextureOffset(32, 13).addBox(-3.0F, -2.8333F, -3.0F, 6, 5, 2, 0.0f);
		body.setTextureOffset(32, 8).addBox( -3.0F, -2.8333F, -1.0F, 6, 6, 2, 0.0F);
		body.setTextureOffset(32,14).addBox( -3.0F, -1.8333F, 1.0F, 6,4,2,0.0F);


		backleft = new ModelRenderer(this);
		backleft.setRotationPoint(2.0F, 22.5F, 1.0F);
		backleft.setTextureOffset(53,15).addBox(-1.0F, -1.5F, -1.0F,2,3,2,0.0F);

		backright = new ModelRenderer(this);
		backright.setRotationPoint(-2.0F, 22.5F, -3.0F);
		backright.setTextureOffset(49,13).addBox(-1.0F, -1.5F, -1.0F,2,3,2,0.0F);

		frontleft = new ModelRenderer(this);
		frontleft.setRotationPoint(2.0F, 22.5F, -3.0F);
		frontleft.setTextureOffset(51,11).addBox(-1.0F, -1.5F, -1.0F,2,3,2,0.0F);

		frontright = new ModelRenderer(this);
		frontright.setRotationPoint(-2.0F, 22.5F, 1.0F);
		frontright.setTextureOffset(55,13).addBox(-1.0F, -1.5F, -1.0F,2,3,2,0.0F);
	}

	public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {

	}

	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.face.rotateAngleX = headPitch * 0.017453292F;
		this.face.rotateAngleY = netHeadYaw * 0.017453292F;
	}

	@Override
	protected Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of(this.face);
	}

	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(this.body,this.backleft,this.backright,this.frontleft,this.frontright,this.tail);
	}
}