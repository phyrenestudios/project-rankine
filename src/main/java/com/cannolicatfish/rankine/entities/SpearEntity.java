package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

/**
 * Trident-style thrown spear. 1.21.1 port modeled on vanilla ThrownTrident:
 * the thrown stack now lives in AbstractArrow's pickup item stack, loyalty is read via
 * EnchantmentHelper.getTridentReturnToOwnerAcceleration, damage/post-attack effects via
 * EnchantmentHelper.modifyDamage / doPostAttackEffectsWithItemSource.
 */
public class SpearEntity extends AbstractArrow {
    private static final EntityDataAccessor<Byte> LOYALTY_LEVEL = SynchedEntityData.defineId(SpearEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Byte> TELEPORT_LEVEL = SynchedEntityData.defineId(SpearEntity.class, EntityDataSerializers.BYTE);
    public ResourceLocation type;
    private boolean dealtDamage;
    private float attackDamage;
    public int returningTicks;
    private int knockbackStrength;


    public SpearEntity(EntityType<? extends SpearEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public SpearEntity(Level worldIn, LivingEntity thrower, ItemStack thrownStackIn, EntityType<SpearEntity> e, ResourceLocation type, float damage) {
        super(e, thrower, worldIn, thrownStackIn.copy(), null);
        this.entityData.set(TELEPORT_LEVEL, (byte) RankineEnchantmentHelper.getLevel(worldIn, RankineEnchantments.ENDPOINT, thrownStackIn));
        this.entityData.set(LOYALTY_LEVEL, this.getLoyaltyFromItem(thrownStackIn));
        this.type = type;
        this.attackDamage = damage;

    }

    public SpearEntity(Level worldIn, double x, double y, double z, EntityType<SpearEntity> e) {
        super(e, worldIn);
        this.setPos(x, y, z);
    }

    // 1.21.1: getAddEntityPacket/NetworkHooks + PlayMessages ctor removed — vanilla handles spawn packets.

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TELEPORT_LEVEL, (byte)0);
        builder.define(LOYALTY_LEVEL, (byte)0);
    }

    @Override
    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }
        int r = RankineEnchantmentHelper.getLevel(this.level(), RankineEnchantments.IMPACT, this.getPickupItemStackOrigin());

        if (r > 0) {
            this.setKnockback(r * 2);
        }

        Entity entity = this.getOwner();
        if (this.inGroundTime == 4 && entity instanceof Player && this.entityData.get(TELEPORT_LEVEL) > 0) {
            double x = this.getX();
            double y = this.getY();
            double z = this.getZ();
            if (level().isClientSide) {
                level().addParticle(ParticleTypes.PORTAL, entity.getX(), entity.getY() + 0.5D, entity.getZ(), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
                level().addParticle(ParticleTypes.PORTAL, x, y + 0.5D, z, (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
            }
            level().playLocalSound(x,y,z,SoundEvents.ENDERMAN_TELEPORT,SoundSource.PLAYERS,1f,1f,false);
            entity.teleportTo(x,y,z);
            if (!((Player) entity).isCreative()) {
                entity.hurt(this.damageSources().fall(), Math.max(5.0F - 2.5F*(this.entityData.get(TELEPORT_LEVEL) - 1),0));
            }
        }

        if ((this.dealtDamage || this.isNoPhysics()) && entity != null) {
            int i = this.entityData.get(LOYALTY_LEVEL);
            if (i > 0 && !this.shouldReturnToThrower()) {
                if (!this.level().isClientSide && this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }

                this.remove(RemovalReason.DISCARDED);
            } else if (i > 0) {
                this.setNoPhysics(true);
                Vec3 vec3d = new Vec3(entity.getX() - this.getX(), entity.getY() + (double)entity.getEyeHeight() - this.getY(), entity.getZ() - this.getZ());
                this.setPosRaw(this.getX(), this.getY() + vec3d.y * 0.015D * (double)i, this.getZ());
                if (this.level().isClientSide) {
                    this.yOld = this.getY();
                }

                double d0 = 0.05D * (double)i;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vec3d.normalize().scale(d0)));
                if (this.returningTicks == 0) {
                    this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
                }

                ++this.returningTicks;
            }
        }
        super.tick();
    }

    private boolean shouldReturnToThrower() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayer) || !entity.isSpectator();
        } else {
            return false;
        }
    }

    public void setKnockback(int knockbackStrengthIn) {
        this.knockbackStrength = knockbackStrengthIn;
    }

    private byte getLoyaltyFromItem(ItemStack stack) {
        return this.level() instanceof ServerLevel serverlevel
                ? (byte) Mth.clamp(EnchantmentHelper.getTridentReturnToOwnerAcceleration(serverlevel, stack, this), 0, 127)
                : 0;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(RankineItems.BRONZE_SPEAR.get());
    }

    @Override
    public ItemStack getWeaponItem() {
        return this.getPickupItemStackOrigin();
    }

    /**
     * Gets the EntityRayTraceResult representing the entity hit
     */
    @Nullable
    @Override
    protected EntityHitResult findHitEntity(Vec3 startVec, Vec3 endVec) {
        return this.dealtDamage ? null : super.findHitEntity(startVec, endVec);
    }

    /**
     * Called when the arrow hits an entity
     */
    @Override
    protected void onHitEntity(EntityHitResult p_213868_1_) {
        Entity entity = p_213868_1_.getEntity();
        float f = this.attackDamage;
        if (f == 0)
        {
            f = 4;
        }

        Entity entity1 = this.getOwner();
        DamageSource damagesource = this.damageSources().trident(this, (Entity)(entity1 == null ? this : entity1));
        if (this.level() instanceof ServerLevel serverlevel) {
            // 1.21.1: replaces EnchantmentHelper.getDamageBonus(stack, mobType)
            f = EnchantmentHelper.modifyDamage(serverlevel, this.getWeaponItem(), entity, damagesource, f);
        }

        if (entity instanceof LivingEntity livingentity && this.knockbackStrength > 0) {
            Vec3 vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockbackStrength * 0.6D);
            if (vector3d.lengthSqr() > 0.0D) {
                livingentity.push(vector3d.x, 0.1D, vector3d.z);
            }
        }

        this.dealtDamage = true;
        SoundEvent soundevent = SoundEvents.TRIDENT_HIT;
        if (entity.hurt(damagesource, f)) {
            if (this.level() instanceof ServerLevel serverlevel1) {
                // 1.21.1: replaces EnchantmentHelper.doPostHurtEffects/doPostDamageEffects
                EnchantmentHelper.doPostAttackEffectsWithItemSource(serverlevel1, entity, damagesource, this.getWeaponItem());
            }
            if (entity instanceof LivingEntity livingentity1) {
                this.doPostHurtEffects(livingentity1);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        float f1 = 1.0F;

        this.playSound(soundevent, f1, 1.0F);
    }

    /**
     * The sound made when an entity is hit by this projectile
     */
    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    @Override
    public void playerTouch(Player entityIn) {
        Entity entity = this.getOwner();
        if (entity == null || entity.getUUID() == entityIn.getUUID()) {
            super.playerTouch(entityIn);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Spear", 10)) {
            // legacy 1.18.2 save key — the stack otherwise round-trips via AbstractArrow's "item" tag
            this.setPickupItemStack(ItemStack.parseOptional(this.registryAccess(), compound.getCompound("Spear")));
        }

        this.dealtDamage = compound.getBoolean("DealtDamage");
        this.entityData.set(LOYALTY_LEVEL, this.getLoyaltyFromItem(this.getPickupItemStackOrigin()));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.put("Spear", this.getPickupItemStackOrigin().save(this.registryAccess(), new CompoundTag()));
        compound.putBoolean("DealtDamage", this.dealtDamage);
    }

    @Override
    protected void tickDespawn() {
        int i = this.entityData.get(LOYALTY_LEVEL);
        if (this.pickup != AbstractArrow.Pickup.ALLOWED || i <= 0) {
            super.tickDespawn();
        }

    }


    @Override
    protected float getWaterInertia() {
        return 0.8F;
    }

    @Override
    public boolean shouldRender(double x, double y, double z) {
        return true;
    }
}
