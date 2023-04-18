package com.cannolicatfish.rankine.client.renders;

import com.cannolicatfish.rankine.entities.BalloonEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class BalloonRenderer extends EntityRenderer<BalloonEntity> {
    private final BlockRenderDispatcher dispatcher;
    public BalloonRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
        this.dispatcher = p_174008_.getBlockRenderDispatcher();
    }

    public void render(BalloonEntity p_114634_, float p_114635_, float p_114636_, PoseStack p_114637_, MultiBufferSource p_114638_, int p_114639_) {
        BlockState blockstate = p_114634_.getBlockState();
        if (blockstate.getRenderShape() == RenderShape.MODEL) {
            Level level = p_114634_.getLevel();
            if (blockstate != level.getBlockState(p_114634_.blockPosition()) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                p_114637_.pushPose();
                BlockPos blockpos = BlockPos.containing(p_114634_.getX(), p_114634_.getBoundingBox().maxY, p_114634_.getZ());
                p_114637_.translate(-0.5D, 0.0D, -0.5D);
                var model = this.dispatcher.getBlockModel(blockstate);
                for (var renderType : model.getRenderTypes(blockstate, RandomSource.create(blockstate.getSeed(p_114634_.getStartPos())), net.minecraftforge.client.model.data.ModelData.EMPTY))
                    this.dispatcher.getModelRenderer().tesselateBlock(level, model, blockstate, blockpos, p_114637_, p_114638_.getBuffer(renderType), false, RandomSource.create(), blockstate.getSeed(p_114634_.getStartPos()), OverlayTexture.NO_OVERLAY, net.minecraftforge.client.model.data.ModelData.EMPTY, renderType);
                p_114637_.popPose();
                super.render(p_114634_, p_114635_, p_114636_, p_114637_, p_114638_, p_114639_);
            }
        }
    }

    public ResourceLocation getTextureLocation(BalloonEntity p_114632_) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
