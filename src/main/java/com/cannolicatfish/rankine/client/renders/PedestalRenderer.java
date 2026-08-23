package com.cannolicatfish.rankine.client.renders;

import com.cannolicatfish.rankine.blocks.pedestal.PedestalTile;
import com.cannolicatfish.rankine.util.RankineMathHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
public class PedestalRenderer implements BlockEntityRenderer<PedestalTile> {

    public PedestalRenderer(BlockEntityRendererProvider.Context context) {}

    // BlockEntity#getRenderBoundingBox was removed in NeoForge 1.21 (see PedestalTile PORT-TODO);
    // the same 1.18.2 bounding box now lives on the renderer via IBlockEntityRendererExtension.
    @Override
    public AABB getRenderBoundingBox(PedestalTile blockEntity) {
        // F29: AABB.encapsulatingFullBlocks is NOT the 1.21.1 equivalent of the removed
        // AABB(BlockPos, BlockPos) constructor — it adds +1 to the max corner. 1.18.2's
        // PedestalTile#getRenderBoundingBox built `new AABB(pos, pos.above(2))`, a degenerate
        // (zero-width in X/Z) 2-block-tall line, not a solid 1x3x1 box. Cosmetic (render culling
        // bounds only), but restore the exact 1.18.2 box via the shared helper for parity.
        return RankineMathHelper.boxBetween(blockEntity.getBlockPos(), blockEntity.getBlockPos().above(2));
    }

    @Override
    public void render(PedestalTile blockEntityIn, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        double x = blockEntityIn.getBlockPos().getX();
        double y = blockEntityIn.getBlockPos().getY();
        double z = blockEntityIn.getBlockPos().getZ();

        matrixStack.pushPose();
        //if (!blockEntityIn.isEmpty()) {
            if (blockEntityIn.entity == null || !ItemStack.isSameItemSameComponents(blockEntityIn.entity.getItem(), blockEntityIn.getItem(0))) {
                blockEntityIn.entity = new ItemEntity(blockEntityIn.getLevel(), x, y, z, blockEntityIn.getItem(0));
            }

            float angle = (blockEntityIn.getLevel().getGameTime()) % 360;
            Quaternionf rotation = Axis.YP.rotationDegrees(angle);

            matrixStack.translate(0.5D, 1.25D, 0.5D);
            matrixStack.mulPose(rotation);
            matrixStack.scale(0.5f, 0.5f, 0.5f);

            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            ItemStack stack = blockEntityIn.entity.getItem();
            BakedModel bakedmodel = itemRenderer.getModel(stack, blockEntityIn.getLevel(), (LivingEntity) null, blockEntityIn.entity.getId());
            itemRenderer.render(stack, ItemDisplayContext.FIXED, true, matrixStack, buffer, combinedLight, combinedOverlay, bakedmodel);

        //}
        matrixStack.popPose();
    }

}
