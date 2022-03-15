package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class SpearEntity extends AbstractArrowEntity {
    private static final DataParameter<Byte> LOYALTY_LEVEL = EntityDataManager.defineId(SpearEntity.class, DataSerializers.BYTE);
    private static final DataParameter<Byte> TELEPORT_LEVEL = EntityDataManager.defineId(SpearEntity.class, DataSerializers.BYTE);
    private ItemStack thrownStack = new ItemStack(RankineItems.BRONZE_SPEAR.get());
    public ResourceLocation type;
    private boolean dealtDamage;
    private float attackDamage;
    public int returningTicks;
    private int knockbackStrength;


    public SpearEntity(EntityType<? extends SpearEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public SpearEntity(World worldIn, LivingEntity thrower, ItemStack thrownStackIn, EntityType<SpearEntity> e, ResourceLocation type, float damage) {
        super(e, thrower, worldIn);
        this.thrownStack = thrownStackIn.copy();
        this.entityData.set(TELEPORT_LEVEL, (byte) EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDPOINT,thrownStackIn));
        this.entityData.set(LOYALTY_LEVEL, (byte) EnchantmentHelper.getLoyalty(thrownStackIn));
        this.type = type;
        this.attackDamage = damage;

    }


    @OnlyIn(Dist.CLIENT)
    public SpearEntity(World worldIn, double x, double y, double z, EntityType<SpearEntity> e) {
        super(e, x, y, z, worldIn);
    }
    @OnlyIn(Dist.CLIENT)
    public SpearEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world, EntityType<SpearEntity> e, ResourceLocation type) {
        super(e, world);
        this.type = type;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TELEPORT_LEVEL, (byte)0);
        this.entityData.define(LOYALTY_LEVEL, (byte)0);
    }

    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }
        int r = EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.IMPACT, this.thrownStack);

        if (r > 0) {
            this.setKnockback(r * 2);
        }

        Entity entity = this.getOwner();
        if (this.inGroundTime == 4 && entity instanceof PlayerEntity && this.entityData.get(TELEPORT_LEVEL) > 0) {
            double x = this.getX();
            double y = this.getY();
            double z = this.getZ();
            if (level.isClientSide) {
                level.addParticle(ParticleTypes.PORTAL, entity.getX(), entity.getY() + 0.5D, entity.getZ(), (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
                level.addParticle(ParticleTypes.PORTAL, x, y + 0.5D, z, (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
            }
            level.playLocalSound(x,y,z,SoundEvents.ENDERMAN_TELEPORT,SoundCategory.PLAYERS,1f,1f,false);
            entity.teleportTo(x,y,z);
            if (!((PlayerEntity) entity).isCreative()) {
                entity.hurt(DamageSource.FALL, Math.max(5.0F - 2.5F*(this.entityData.get(TELEPORT_LEVEL) - 1),0));
            }
        }

        if ((this.dealtDamage || this.isNoPhysics()) && entity != null) {
            int i = this.entityData.get(LOYALTY_LEVEL);
            if (i > 0 && !this.shouldReturnToThrower()) {
                if (!this.level.isClientSide && this.pickup == AbstractArrowEntity.PickupStatus.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }

                this.remove();
            } else if (i > 0) {
                this.setNoPhysics(true);
                Vector3d vec3d = new Vector3d(entity.getX() - this.getX(), entity.getY() + (double)entity.getEyeHeight() - this.getY(), entity.getZ() - this.getZ());
                this.setPosRaw(this.getX(), this.getY() + vec3d.y * 0.015D * (double)i, this.getZ());
                if (this.level.isClientSide) {
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
            return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
        } else {
            return false;
        }
    }

    public void setKnockback(int knockbackStrengthIn) {
        this.knockbackStrength = knockbackStrengthIn;
    }

    protected ItemStack getPickupItem() {
        return this.thrownStack.copy();
    }

    /**
     * Gets the EntityRayTraceResult representing the entity hit
     */
    @Nullable
    protected EntityRayTraceResult findHitEntity(Vector3d startVec, Vector3d endVec) {
        return this.dealtDamage ? null : super.findHitEntity(startVec, endVec);
    }

    /**
     * Called when the arrow hits an entity
     */
    protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
        Entity entity = p_213868_1_.getEntity();
        float f = this.attackDamage;
        if (f == 0)
        {
            f = 4;
        }

        if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entity;
            f += EnchantmentHelper.getDamageBonus(this.thrownStack, livingentity.getMobType());
            if (this.knockbackStrength > 0) {
                Vector3d vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockbackStrength * 0.6D);
                if (vector3d.lengthSqr() > 0.0D) {
                    livingentity.push(vector3d.x, 0.1D, vector3d.z);
                }
            }
        }

        Entity entity1 = this.getOwner();
        DamageSource damagesource = DamageSource.trident(this, (Entity)(entity1 == null ? this : entity1));
        this.dealtDamage = true;
        SoundEvent soundevent = SoundEvents.TRIDENT_HIT;
        if (entity.hurt(damagesource, f) && entity instanceof LivingEntity) {
            LivingEntity livingentity1 = (LivingEntity)entity;
            if (entity1 instanceof LivingEntity) {
                EnchantmentHelper.doPostHurtEffects(livingentity1, entity1);
                EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingentity1);
            }

            this.doPostHurtEffects(livingentity1);
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        float f1 = 1.0F;
        /*
        if (this.world instanceof ServerWorld && this.world.isThundering() && EnchantmentHelper.hasChanneling(this.thrownStack)) {
            BlockPos blockpos = entity.blockPosition();
            if (this.world.canSeeSky(blockpos)) {
                LightningBoltEntity lightningboltentity = new LightningBoltEntity(this.world, (double)blockpos.getX() + 0.5D, (double)blockpos.getY(), (double)blockpos.getZ() + 0.5D, false);
                lightningboltentity.setCaster(entity1 instanceof ServerPlayerEntity ? (ServerPlayerEntity)entity1 : null);
                ((ServerWorld)this.world).addLightningBolt(lightningboltentity);
                soundevent = SoundEvents.ITEM_TRIDENT_THUNDER;
                f1 = 5.0F;
            }
        }*/

        this.playSound(soundevent, f1, 1.0F);
    }

    /**
     * The sound made when an entity is hit by this projectile
     */
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void playerTouch(PlayerEntity entityIn) {
        Entity entity = this.getOwner();
        if (entity == null || entity.getUUID() == entityIn.getUUID()) {
            super.playerTouch(entityIn);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Spear", 10)) {
            this.thrownStack = ItemStack.of(compound.getCompound("Spear"));
        }

        this.dealtDamage = compound.getBoolean("DealtDamage");
        this.entityData.set(LOYALTY_LEVEL, (byte)EnchantmentHelper.getLoyalty(this.thrownStack));
    }

    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.put("Spear", this.thrownStack.save(new CompoundNBT()));
        compound.putBoolean("DealtDamage", this.dealtDamage);
    }

    protected void tickDespawn() {
        int i = this.entityData.get(LOYALTY_LEVEL);
        if (this.pickup != AbstractArrowEntity.PickupStatus.ALLOWED || i <= 0) {
            super.tickDespawn();
        }

    }


    protected float getWaterInertia() {
        return 0.8F;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldRender(double x, double y, double z) {
        return true;
    }
}
