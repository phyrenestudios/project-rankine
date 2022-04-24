package com.cannolicatfish.rankine.client.renders;

import com.cannolicatfish.rankine.blocks.pedestal.PedestalTile;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class PedestalRenderer extends TileEntityRenderer<PedestalTile> {

    public PedestalRenderer(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(PedestalTile tileEntityIn, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        double x = tileEntityIn.getPos().getX();
        double y = tileEntityIn.getPos().getY();
        double z = tileEntityIn.getPos().getZ();

        if(tileEntityIn.isEmpty()) return;

        if (tileEntityIn.entity == null || !ItemStack.areItemsEqual(tileEntityIn.entity.getItem(), tileEntityIn.getStackInSlot(0))) {
            tileEntityIn.entity = new ItemEntity(tileEntityIn.getWorld(), x, y, z, tileEntityIn.getStackInSlot(0));
        }

        float angle = (tileEntityIn.getWorld().getGameTime()) % 360;
        Quaternion rotation = Vector3f.YP.rotationDegrees(angle);


        matrixStack.push();
        matrixStack.translate(0.5D, 1.25D, 0.5D);
        matrixStack.rotate(rotation);
        matrixStack.scale(0.5f, 0.5f, 0.5f);

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = tileEntityIn.entity.getItem();
        IBakedModel ibakedmodel = itemRenderer.getItemModelWithOverrides(stack, tileEntityIn.getWorld(), null);
        itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED, true, matrixStack, buffer, combinedLight, combinedOverlay, ibakedmodel);
        matrixStack.pop();
    }
}
