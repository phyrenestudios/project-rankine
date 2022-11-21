package com.cannolicatfish.rankine.recipe.helper;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;

public class FluidHelper {
    private static final UUID SLOW_FALLING_ID = UUID.fromString("A5B6CF2A-2F7C-31EF-9022-7C3E7D5E6ABA");
    private static final AttributeModifier SLOW_FALLING = new AttributeModifier(SLOW_FALLING_ID, "Slow falling acceleration reduction", -0.07, AttributeModifier.Operation.ADDITION); // Add -0.07 to 0.08 so we get the vanilla default of 0.01

    public static Vec3 handleFluidAcceleration(Entity ent, double motionScale) {
        AABB axisalignedbb = ent.getBoundingBox().deflate(0.001D);
        int i = Mth.floor(axisalignedbb.minX);
        int j = Mth.ceil(axisalignedbb.maxX);
        int k = Mth.floor(axisalignedbb.minY);
        int l = Mth.ceil(axisalignedbb.maxY);
        int i1 = Mth.floor(axisalignedbb.minZ);
        int j1 = Mth.ceil(axisalignedbb.maxZ);
        if (!ent.level.hasChunksAt(i, k, i1, j, l, j1)) {
            return Vec3.ZERO;
        } else {
            double d0 = 0.0D;
            boolean flag = ent.isPushedByFluid();
            boolean flag1 = false;
            Vec3 vector3d = Vec3.ZERO;
            int k1 = 0;
            BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

            for(int l1 = i; l1 < j; ++l1) {
                for(int i2 = k; i2 < l; ++i2) {
                    for(int j2 = i1; j2 < j1; ++j2) {
                        blockpos$mutable.set(l1, i2, j2);
                        FluidState fluidstate = ent.level.getFluidState(blockpos$mutable);
                        double d1 = (double)((float)i2 + fluidstate.getHeight(ent.level, blockpos$mutable));
                        if (d1 >= axisalignedbb.minY) {
                            flag1 = true;
                            d0 = Math.max(d1 - axisalignedbb.minY, d0);
                            if (flag) {
                                Vec3 vector3d1 = fluidstate.getFlow(ent.level, blockpos$mutable);
                                if (d0 < 0.4D) {
                                    vector3d1 = vector3d1.scale(d0);
                                }

                                vector3d = vector3d.add(vector3d1);
                                ++k1;
                            }
                        }
                    }
                }
            }

            if (vector3d.length() > 0.0D) {
                if (k1 > 0) {
                    vector3d = vector3d.scale(1.0D / (double)k1);
                }

                if (!(ent instanceof Player)) {
                    vector3d = vector3d.normalize();
                }

                Vec3 vector3d2 = ent.getDeltaMovement();
                vector3d = vector3d.scale(motionScale);
                double d2 = 0.003D;
                if (Math.abs(vector3d2.x) < 0.003D && Math.abs(vector3d2.z) < 0.003D && vector3d.length() < 0.0045000000000000005D) {
                    vector3d = vector3d.normalize().scale(0.0045000000000000005D);
                }

                ent.setDeltaMovement(ent.getDeltaMovement().add(vector3d));
            }
            return vector3d;
        }
    }

    public static void travel(Vec3 travelVector, LivingEntity ent, boolean inLiquid) {
        if (ent.isEffectiveAi() || ent.isControlledByLocalInstance()) {
            double d0 = 0.08D;
            AttributeInstance gravity = ent.getAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
            boolean flag = ent.getDeltaMovement().y <= 0.0D;
            if (flag && ent.hasEffect(MobEffects.SLOW_FALLING)) {
                if (!gravity.hasModifier(SLOW_FALLING)) gravity.addTransientModifier(SLOW_FALLING);
                ent.fallDistance = 0.0F;
            } else if (gravity.hasModifier(SLOW_FALLING)) {
                gravity.removeModifier(SLOW_FALLING);
            }
            d0 = gravity.getValue();

            FluidState fluidstate = ent.level.getFluidState(ent.blockPosition());
            if (inLiquid && !ent.canStandOnFluid(fluidstate)) {
                double d7 = ent.getY();
                ent.moveRelative(0.02F, travelVector);
                ent.move(MoverType.SELF, ent.getDeltaMovement());
                if (ent.getFluidHeight(FluidTags.LAVA) <= ent.getFluidJumpThreshold()) {
                    ent.setDeltaMovement(ent.getDeltaMovement().multiply(0.5D, (double)0.8F, 0.5D));
                    Vec3 vector3d3 = ent.getFluidFallingAdjustedMovement(d0, flag, ent.getDeltaMovement());
                    ent.setDeltaMovement(vector3d3);
                } else {
                    ent.setDeltaMovement(ent.getDeltaMovement().scale(0.5D));
                }

                if (!ent.isNoGravity()) {
                    ent.setDeltaMovement(ent.getDeltaMovement().add(0.0D, -d0 / 4.0D, 0.0D));
                }

                Vec3 vector3d4 = ent.getDeltaMovement();
                if (ent.horizontalCollision && ent.isFree(vector3d4.x, vector3d4.y + (double)0.6F - ent.getY() + d7, vector3d4.z)) {
                    ent.setDeltaMovement(vector3d4.x, (double)0.3F, vector3d4.z);
                }
            }
        }

        ent.calculateEntityAnimation(ent, ent instanceof FlyingAnimal);
    }

    public static double horizontalMag(Vec3 vec) {
        return vec.x * vec.x + vec.z * vec.z;
    }

    protected static BlockPos getPositionUnderneath(Entity ent) {
        return new BlockPos(ent.position().x, ent.getBoundingBox().minY - 0.5000001D, ent.position().z);
    }

    protected static void setFlag(Entity ent, int flag, boolean set) {
        final EntityDataAccessor<Byte> FLAGS = SynchedEntityData.defineId(Entity.class, EntityDataSerializers.BYTE);
        byte b0 = ent.getEntityData().get(FLAGS);
        if (set) {
            ent.getEntityData().set(FLAGS, (byte)(b0 | 1 << flag));
        } else {
            ent.getEntityData().set(FLAGS, (byte)(b0 & ~(1 << flag)));
        }

    }

    public static int decreaseAirSupply(LivingEntity ent, Level world, int air, int amount) {
        int i = EnchantmentHelper.getRespiration(ent);
        return i > 0 && world.getRandom().nextInt(i + 1) > 0 ? air : air - amount;
    }


    public static FluidStack getFluidStack(JsonObject json) {
        String fluidName = GsonHelper.getAsString(json, "fluid");
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidName));
        if (fluid == null) {
            throw new JsonSyntaxException("Unknown fluid '" + fluidName + "'");
        }
        return new FluidStack(fluid, GsonHelper.getAsInt(json, "amount", 1000));
    }

}

