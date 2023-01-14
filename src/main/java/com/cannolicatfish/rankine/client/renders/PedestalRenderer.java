package com.cannolicatfish.rankine.client.renders;

import com.cannolicatfish.rankine.blocks.pedestal.PedestalTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PedestalRenderer implements BlockEntityRenderer<PedestalTile> {

    public PedestalRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(PedestalTile blockEntityIn, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        double x = blockEntityIn.getBlockPos().getX();
        double y = blockEntityIn.getBlockPos().getY();
        double z = blockEntityIn.getBlockPos().getZ();

        matrixStack.pushPose();
        //if (!blockEntityIn.isEmpty()) {
            if (blockEntityIn.entity == null || !ItemStack.isSame(blockEntityIn.entity.getItem(), blockEntityIn.getItem(0))) {
                blockEntityIn.entity = new ItemEntity(blockEntityIn.getLevel(), x, y, z, blockEntityIn.getItem(0));
            }

            float angle = (blockEntityIn.getLevel().getGameTime()) % 360;
            Quaternion rotation = Vector3f.YP.rotationDegrees(angle);

            matrixStack.translate(0.5D, 1.25D, 0.5D);
            matrixStack.mulPose(rotation);
            matrixStack.scale(0.5f, 0.5f, 0.5f);

            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            ItemStack stack = blockEntityIn.entity.getItem();
            BakedModel bakedmodel = itemRenderer.getModel(stack, blockEntityIn.getLevel(), (LivingEntity) null, blockEntityIn.entity.getId());
            itemRenderer.render(stack, ItemTransforms.TransformType.FIXED, true, matrixStack, buffer, combinedLight, combinedOverlay, bakedmodel);

        //}
        matrixStack.popPose();
    }

}
