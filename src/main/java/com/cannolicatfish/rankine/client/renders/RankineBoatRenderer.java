package com.cannolicatfish.rankine.client.renders;

import com.cannolicatfish.rankine.entities.RankineBoatEntity;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Map;
import java.util.stream.Stream;

@OnlyIn(Dist.CLIENT)
public class RankineBoatRenderer extends EntityRenderer<RankineBoatEntity> {
    private static final ResourceLocation[] BOAT_TEXTURES = new ResourceLocation[]{
            ResourceLocation.parse("textures/entity/boat/oak.png"),
            ResourceLocation.parse("textures/entity/boat/spruce.png"),
            ResourceLocation.parse("textures/entity/boat/birch.png"),
            ResourceLocation.parse("textures/entity/boat/jungle.png"),
            ResourceLocation.parse("textures/entity/boat/acacia.png"),
            ResourceLocation.parse("textures/entity/boat/dark_oak.png"),
            ResourceLocation.parse("rankine:textures/entity/boat/cedar.png"),
            ResourceLocation.parse("rankine:textures/entity/boat/balsam_fir.png"),
            ResourceLocation.parse("rankine:textures/entity/boat/eastern_hemlock.png"),
            ResourceLocation.parse("rankine:textures/entity/boat/western_hemlock.png"),
            ResourceLocation.parse("rankine:textures/entity/boat/pinyon_pine.png"),
            ResourceLocation.parse("rankine:textures/entity/boat/juniper.png"),
            ResourceLocation.parse("rankine:textures/entity/boat/black_birch.png"),
            ResourceLocation.parse("rankine:textures/entity/boat/yellow_birch.png"),
            ResourceLocation.parse("rankine:textures/entity/boat/red_birch.png"),
            ResourceLocation.parse("rankine:textures/entity/boat/magnolia.png"),
            ResourceLocation.parse("rankine:textures/entity/boat/maple.png"),
            ResourceLocation.parse("rankine:textures/entity/boat/black_walnut.png"),
            ResourceLocation.parse("rankine:textures/entity/boat/coconut_palm.png"),
            ResourceLocation.parse("rankine:textures/entity/boat/cork_oak.png"),
            ResourceLocation.parse("rankine:textures/entity/boat/sharinga.png"),
            ResourceLocation.parse("rankine:textures/entity/boat/cinnamon.png"),
            ResourceLocation.parse("rankine:textures/entity/boat/honey_locust.png"),
            ResourceLocation.parse("rankine:textures/entity/boat/weeping_willow.png"),
            ResourceLocation.parse("rankine:textures/entity/boat/erythrina.png"),
            ResourceLocation.parse("rankine:textures/entity/boat/petrified_chorus.png"),
            ResourceLocation.parse("rankine:textures/entity/boat/charred.png"),
            ResourceLocation.parse("rankine:textures/entity/boat/bamboo.png"),
            ResourceLocation.parse("rankine:textures/entity/boat/bamboo_culms.png")

    };
    private final Map<Boat.Type, Pair<ResourceLocation, BoatModel>> boatResources;

    public static final RankineBoatRenderer.RenderFactory instance = new RankineBoatRenderer.RenderFactory();

    public RankineBoatRenderer(EntityRendererProvider.Context renderManagerIn)
    {
        super(renderManagerIn);
        this.shadowRadius = 0.8F;
        this.boatResources = Stream.of(Boat.Type.values()).collect(ImmutableMap.toImmutableMap((p_173938_) -> {
            return p_173938_;
        }, (p_173941_) -> {
            return Pair.of(new ResourceLocation("rankine:textures/entity/boat/" + p_173941_.getName() + ".png"), new BoatModel(renderManagerIn.bakeLayer(ModelLayers.createBoatModelName(p_173941_))));
        }));
    }

    public void render(RankineBoatEntity p_113929_, float p_113930_, float p_113931_, PoseStack p_113932_, MultiBufferSource p_113933_, int p_113934_) {
        p_113932_.pushPose();
        p_113932_.translate(0.0D, 0.375D, 0.0D);
        p_113932_.mulPose(Vector3f.YP.rotationDegrees(180.0F - p_113930_));
        float f = (float)p_113929_.getHurtTime() - p_113931_;
        float f1 = p_113929_.getDamage() - p_113931_;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f > 0.0F) {
            p_113932_.mulPose(Vector3f.XP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * (float)p_113929_.getHurtDir()));
        }

        float f2 = p_113929_.getBubbleAngle(p_113931_);
        if (!Mth.equal(f2, 0.0F)) {
            p_113932_.mulPose(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), p_113929_.getBubbleAngle(p_113931_), true));
        }

        Pair<ResourceLocation, BoatModel> pair = getModelWithLocation(p_113929_);
        ResourceLocation resourcelocation = pair.getFirst();
        BoatModel boatmodel = pair.getSecond();
        p_113932_.scale(-1.0F, -1.0F, 1.0F);
        p_113932_.mulPose(Vector3f.YP.rotationDegrees(90.0F));
        boatmodel.setupAnim(p_113929_, p_113931_, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = p_113933_.getBuffer(boatmodel.renderType(this.getTextureLocation(p_113929_)));
        boatmodel.renderToBuffer(p_113932_, vertexconsumer, p_113934_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        if (!p_113929_.isUnderWater()) {
            VertexConsumer vertexconsumer1 = p_113933_.getBuffer(RenderType.waterMask());
            boatmodel.waterPatch().render(p_113932_, vertexconsumer1, p_113934_, OverlayTexture.NO_OVERLAY);
        }

        p_113932_.popPose();
        super.render(p_113929_, p_113930_, p_113931_, p_113932_, p_113933_, p_113934_);
    }


    @Override
    public ResourceLocation getTextureLocation(RankineBoatEntity entity) {
        return BOAT_TEXTURES[entity.getRankineBoatType().ordinal()];
    }

    public Pair<ResourceLocation, BoatModel> getModelWithLocation(Boat boat) { return this.boatResources.get(boat.getBoatType()); }

    public static class RenderFactory implements EntityRendererProvider<RankineBoatEntity> {
        @Override
        public EntityRenderer<RankineBoatEntity> create(Context manager) {
            return new RankineBoatRenderer(manager);
        }

    }


}