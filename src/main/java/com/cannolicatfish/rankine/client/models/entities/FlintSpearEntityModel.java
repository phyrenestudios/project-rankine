package com.cannolicatfish.rankine.client.models.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.DataFixerBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.TridentModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FlintSpearEntityModel extends Model {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("rankine:textures/entity/flint_spear.png");
    private final ModelRenderer modelRenderer;

    public FlintSpearEntityModel() {
        super(RenderType::getEntitySolid);
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.modelRenderer = new ModelRenderer(this, 0, 0);
        this.modelRenderer.addBox(-0.5F, -4.0F, -0.5F, 1, 31, 1, 0.0F);
        ModelRenderer renderermodel = new ModelRenderer(this, 4, 0);
        renderermodel.addBox(-1.5F, -2.0F, -0.5F, 3, 2, 1);
        this.modelRenderer.addChild(renderermodel);
        /*
        ModelRenderer renderermodel1 = new ModelRenderer(this, 4, 3);
        renderermodel1.addBox(-2.5F, -3.0F, -0.5F, 1, 4, 1);
        this.modelRenderer.addChild(renderermodel1);
        ModelRenderer renderermodel2 = new ModelRenderer(this, 4, 3);
        renderermodel2.mirror = true;
        renderermodel2.addBox(1.5F, -3.0F, -0.5F, 1, 4, 1);
        this.modelRenderer.addChild(renderermodel2);

         */
    }

    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
