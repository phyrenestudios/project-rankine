package com.cannolicatfish.rankine.client.renders;

import com.cannolicatfish.rankine.entities.RankineBoatEntity;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Quaternionf;

import java.util.Map;
import java.util.stream.Stream;

@OnlyIn(Dist.CLIENT)
public class RankineBoatRenderer extends EntityRenderer<RankineBoatEntity> {
    private static final ResourceLocation[] BOAT_TEXTURES = new ResourceLocation[]{
            new ResourceLocation("textures/entity/boat/oak.png"),
            new ResourceLocation("textures/entity/boat/spruce.png"),
            new ResourceLocation("textures/entity/boat/birch.png"),
            new ResourceLocation("textures/entity/boat/jungle.png"),
            new ResourceLocation("textures/entity/boat/acacia.png"),
            new ResourceLocation("textures/entity/boat/dark_oak.png"),
            new ResourceLocation("rankine:textures/entity/boat/cedar.png"),
            new ResourceLocation("rankine:textures/entity/boat/balsam_fir.png"),
            new ResourceLocation("rankine:textures/entity/boat/eastern_hemlock.png"),
            new ResourceLocation("rankine:textures/entity/boat/western_hemlock.png"),
            new ResourceLocation("rankine:textures/entity/boat/pinyon_pine.png"),
            new ResourceLocation("rankine:textures/entity/boat/juniper.png"),
            new ResourceLocation("rankine:textures/entity/boat/black_birch.png"),
            new ResourceLocation("rankine:textures/entity/boat/yellow_birch.png"),
            new ResourceLocation("rankine:textures/entity/boat/red_birch.png"),
            new ResourceLocation("rankine:textures/entity/boat/magnolia.png"),
            new ResourceLocation("rankine:textures/entity/boat/maple.png"),
            new ResourceLocation("rankine:textures/entity/boat/black_walnut.png"),
            new ResourceLocation("rankine:textures/entity/boat/coconut_palm.png"),
            new ResourceLocation("rankine:textures/entity/boat/cork_oak.png"),
            new ResourceLocation("rankine:textures/entity/boat/sharinga.png"),
            new ResourceLocation("rankine:textures/entity/boat/cinnamon.png"),
            new ResourceLocation("rankine:textures/entity/boat/honey_locust.png"),
            new ResourceLocation("rankine:textures/entity/boat/weeping_willow.png"),
            new ResourceLocation("rankine:textures/entity/boat/erythrina.png"),
            new ResourceLocation("rankine:textures/entity/boat/petrified_chorus.png"),
            new ResourceLocation("rankine:textures/entity/boat/charred.png"),
            new ResourceLocation("rankine:textures/entity/boat/bamboo.png"),
            new ResourceLocation("rankine:textures/entity/boat/bamboo_culms.png")

    };

    private final Map<Boat.Type, Pair<ResourceLocation, ListModel<Boat>>> boatResources;

    public RankineBoatRenderer(EntityRendererProvider.Context p_234563_, boolean p_234564_) {
        super(p_234563_);
        this.shadowRadius = 0.8F;
        this.boatResources = Stream.of(Boat.Type.values()).collect(ImmutableMap.toImmutableMap((p_173938_) -> {
            return p_173938_;
        }, (p_247941_) -> {
            return Pair.of(new ResourceLocation(getTextureLocation(p_247941_, p_234564_)), this.createBoatModel(p_234563_, p_247941_, p_234564_));
        }));
    }

    private ListModel<Boat> createBoatModel(EntityRendererProvider.Context p_248834_, Boat.Type p_249317_, boolean p_250093_) {
        ModelLayerLocation modellayerlocation = p_250093_ ? ModelLayers.createChestBoatModelName(p_249317_) : ModelLayers.createBoatModelName(p_249317_);
        ModelPart modelpart = p_248834_.bakeLayer(modellayerlocation);
        if (p_249317_ == Boat.Type.BAMBOO) {
            return (ListModel<Boat>)(p_250093_ ? new ChestRaftModel(modelpart) : new RaftModel(modelpart));
        } else {
            return (ListModel<Boat>)(p_250093_ ? new ChestBoatModel(modelpart) : new BoatModel(modelpart));
        }
    }

    private static String getTextureLocation(Boat.Type p_234566_, boolean p_234567_) {
        return p_234567_ ? "textures/entity/chest_boat/" + p_234566_.getName() + ".png" : "textures/entity/boat/" + p_234566_.getName() + ".png";
    }

    public void render(RankineBoatEntity p_113929_, float p_113930_, float p_113931_, PoseStack p_113932_, MultiBufferSource p_113933_, int p_113934_) {
        p_113932_.pushPose();
        p_113932_.translate(0.0F, 0.375F, 0.0F);
        p_113932_.mulPose(Axis.YP.rotationDegrees(180.0F - p_113930_));
        float f = (float)p_113929_.getHurtTime() - p_113931_;
        float f1 = p_113929_.getDamage() - p_113931_;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f > 0.0F) {
            p_113932_.mulPose(Axis.XP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * (float)p_113929_.getHurtDir()));
        }

        float f2 = p_113929_.getBubbleAngle(p_113931_);
        if (!Mth.equal(f2, 0.0F)) {
            p_113932_.mulPose((new Quaternionf()).setAngleAxis(p_113929_.getBubbleAngle(p_113931_) * ((float)Math.PI / 180F), 1.0F, 0.0F, 1.0F));
        }

        Pair<ResourceLocation, ListModel<Boat>> pair = getModelWithLocation(p_113929_);
        ResourceLocation resourcelocation = pair.getFirst();
        ListModel<Boat> listmodel = pair.getSecond();
        p_113932_.scale(-1.0F, -1.0F, 1.0F);
        p_113932_.mulPose(Axis.YP.rotationDegrees(90.0F));
        listmodel.setupAnim(p_113929_, p_113931_, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = p_113933_.getBuffer(listmodel.renderType(resourcelocation));
        listmodel.renderToBuffer(p_113932_, vertexconsumer, p_113934_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        if (!p_113929_.isUnderWater()) {
            VertexConsumer vertexconsumer1 = p_113933_.getBuffer(RenderType.waterMask());
            if (listmodel instanceof WaterPatchModel) {
                WaterPatchModel waterpatchmodel = (WaterPatchModel)listmodel;
                waterpatchmodel.waterPatch().render(p_113932_, vertexconsumer1, p_113934_, OverlayTexture.NO_OVERLAY);
            }
        }

        p_113932_.popPose();
        super.render(p_113929_, p_113930_, p_113931_, p_113932_, p_113933_, p_113934_);
    }

    @Deprecated // forge: override getModelWithLocation to change the texture / model
    public ResourceLocation getTextureLocation(Boat p_113927_) {
        return getModelWithLocation(p_113927_).getFirst();
    }

    public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat) { return this.boatResources.get(boat.getVariant()); }

    @Override
    public ResourceLocation getTextureLocation(RankineBoatEntity entity) {
        return BOAT_TEXTURES[entity.getRankineBoatType().ordinal()];
    }


}