package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.RankineDamageSources;
import com.cannolicatfish.rankine.init.RankineEntityTypes;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;

public class CannonballEntity extends AbstractHurtingProjectile implements ItemSupplier {
    private static final EntityDataAccessor<ItemStack> STACK = SynchedEntityData.defineId(CannonballEntity.class, EntityDataSerializers.ITEM_STACK);

    public CannonballEntity(EntityType<? extends CannonballEntity> p_i50147_1_, Level p_i50147_2_) {
        super(p_i50147_1_, p_i50147_2_);
    }

    public CannonballEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
        // 1.21.1: AbstractHurtingProjectile takes the acceleration as a Vec3
        super(RankineEntityTypes.CANNONBALL.get(), shooter, new Vec3(accelX, accelY, accelZ), worldIn);
    }

    public CannonballEntity(Level worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(RankineEntityTypes.CANNONBALL.get(), x, y, z, new Vec3(accelX, accelY, accelZ), worldIn);
    }

    // 1.21.1: getAddEntityPacket/NetworkHooks override removed — vanilla handles spawn packets.

    public void setStack(ItemStack stack) {
        if (stack.getItem() != RankineItems.CANNONBALL.get() || !stack.isComponentsPatchEmpty()) {
            this.getEntityData().set(STACK, Util.make(stack.copy(), (p_213897_0_) -> {
                p_213897_0_.setCount(1);
            }));
        }

    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    protected ItemStack getStack() {
        return this.getEntityData().get(STACK);
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemstack = this.getStack();
        return itemstack.isEmpty() ? new ItemStack(RankineItems.CANNONBALL.get()) : itemstack;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STACK, ItemStack.EMPTY);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        ItemStack itemstack = this.getStack();
        if (!itemstack.isEmpty()) {
            compound.put("Item", itemstack.save(this.registryAccess()));
        }

    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        ItemStack itemstack = ItemStack.parseOptional(this.registryAccess(), compound.getCompound("Item"));
        this.setStack(itemstack);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            Entity entity = result.getEntity();
            Entity entity1 = this.getOwner();
            DamageSource damagesource = RankineDamageSources.source(this.level(), RankineDamageSources.CANNONBALL);
            boolean flag = entity.hurt(damagesource, 10.0F);
            if (flag && entity1 instanceof LivingEntity && this.level() instanceof ServerLevel serverlevel) {
            // PORT-NOTE: Entity#doEnchantDamageEffects is gone in 1.21.1; EnchantmentHelper.doPostAttackEffects
            // is the replacement. It derives the attacker (and its weapon) from the DamageSource instead of
            // taking it as an argument.
                // BEHAVIOUR DELTA: rankine:cannonball is an ownerless damage source (as it was in 1.18.2),
                // so only the victim-side post-attack effects fire; the attacker-weapon side is skipped
                // because 1.21.1 reads the attacker off the DamageSource.
                EnchantmentHelper.doPostAttackEffects(serverlevel, entity, damagesource);
            }
            Vec3 vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double)1.6D);
            if (vector3d.lengthSqr() > 0.0D) {
                entity.push(vector3d.x, 0.1D, vector3d.z);
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            // 1.18.2: mob griefing ? DESTROY : NONE — ExplosionInteraction.MOB applies the same gamerule check
            Level.ExplosionInteraction explosionInteraction = EventHooks.canEntityGrief(this.level(), this.getOwner()) ? Level.ExplosionInteraction.MOB : Level.ExplosionInteraction.NONE;
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 1.0F, false, explosionInteraction);

        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
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
