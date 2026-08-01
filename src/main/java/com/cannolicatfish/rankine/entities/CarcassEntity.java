package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.RankineEntityTypes;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;

import java.util.List;

public class CarcassEntity extends Fireball implements ItemSupplier {
    // 1.21.1: Fireball itself keeps the item in entity data (setItem/getItem/save/load) —
    // the old custom STACK accessor and Item NBT handling are superseded by the superclass.

    public CarcassEntity(EntityType<? extends CarcassEntity> p_i50147_1_, Level p_i50147_2_) {
        super(p_i50147_1_, p_i50147_2_);
    }

    public CarcassEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
        // 1.21.1: Fireball takes the acceleration as a Vec3
        super(RankineEntityTypes.CARCASS.get(), shooter, new Vec3(accelX, accelY, accelZ), worldIn);
    }

    public CarcassEntity(Level worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(RankineEntityTypes.CARCASS.get(), x, y, z, new Vec3(accelX, accelY, accelZ), worldIn);
    }

    // 1.21.1: getAddEntityPacket/NetworkHooks override removed — vanilla handles spawn packets.

    @Override
    protected boolean shouldBurn() {
        return true;
    }

    @Override
    public ItemStack getItem() {
        // Fireball#getDefaultItem is private (fire charge); substitute the cannonball fallback of 1.18.2.
        ItemStack itemstack = super.getItem();
        return itemstack.isEmpty() || itemstack.is(net.minecraft.world.item.Items.FIRE_CHARGE) ? new ItemStack(RankineItems.CANNONBALL.get()) : itemstack;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            Entity entity = result.getEntity();
            if (!entity.fireImmune()) {
                Entity entity1 = this.getOwner();
                int i = entity.getRemainingFireTicks();
                entity.igniteForSeconds(5);
                boolean flag = entity.hurt(this.damageSources().fireball(this, entity1), 5.0F);
                if (!flag) {
                    entity.setRemainingFireTicks(i);
                } else if (entity1 instanceof LivingEntity) {
                    this.doEnchantDamageEffects((LivingEntity)entity1, entity);
                }
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            // 1.18.2: mob griefing ? DESTROY : NONE — ExplosionInteraction.MOB applies the same gamerule check
            Level.ExplosionInteraction explosionInteraction = EventHooks.canEntityGrief(this.level(), this.getOwner()) ? Level.ExplosionInteraction.MOB : Level.ExplosionInteraction.NONE;
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 0.5F, false, explosionInteraction);
            Entity entity = this.getOwner();
            if (entity == null || !(entity instanceof Mob) || EventHooks.canEntityGrief(this.level(), this)) {
                BlockPos blockpos = result.getBlockPos().relative(result.getDirection());
                if (this.level().isEmptyBlock(blockpos)) {
                    this.level().setBlockAndUpdate(blockpos, BaseFireBlock.getState(this.level(), blockpos));
                }
            }

        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        Entity entity = this.getOwner();
        if (!this.level().isClientSide) {
            List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D));
            AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
            if (entity instanceof LivingEntity) {
                areaeffectcloudentity.setOwner((LivingEntity)entity);
            }

            // 1.21.1: entity_effect particles are colored options now; the default AreaEffectCloud
            // particle is already entity_effect, so the old explicit setParticle call is dropped.
            areaeffectcloudentity.setRadius(3.0F);
            areaeffectcloudentity.setDuration(300);
            areaeffectcloudentity.setRadiusPerTick((7.0F - areaeffectcloudentity.getRadius()) / (float)areaeffectcloudentity.getDuration());
            areaeffectcloudentity.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 1));
            if (!list.isEmpty()) {
                for(LivingEntity livingentity : list) {
                    double d0 = this.distanceToSqr(livingentity);
                    if (d0 < 16.0D) {
                        areaeffectcloudentity.setPos(livingentity.getX(), livingentity.getY(), livingentity.getZ());
                        break;
                    }
                }
            }
            this.level().addFreshEntity(areaeffectcloudentity);
            this.remove(RemovalReason.DISCARDED);
        }

    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }
}
