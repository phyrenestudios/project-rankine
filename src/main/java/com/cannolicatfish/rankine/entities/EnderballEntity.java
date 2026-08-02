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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class EnderballEntity extends AbstractHurtingProjectile implements ItemSupplier {
    private static final EntityDataAccessor<ItemStack> STACK = SynchedEntityData.defineId(EnderballEntity.class, EntityDataSerializers.ITEM_STACK);

    public EnderballEntity(EntityType<? extends EnderballEntity> p_i50147_1_, Level p_i50147_2_) {
        super(p_i50147_1_, p_i50147_2_);
    }

    public EnderballEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
        // 1.21.1: AbstractHurtingProjectile takes the acceleration as a Vec3
        super(RankineEntityTypes.ENDERBALL.get(), shooter, new Vec3(accelX, accelY, accelZ), worldIn);
    }

    public EnderballEntity(Level worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(RankineEntityTypes.ENDERBALL.get(), x, y, z, new Vec3(accelX, accelY, accelZ), worldIn);
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
        return itemstack.isEmpty() ? new ItemStack(RankineItems.ENDERBALL.get()) : itemstack;
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
    protected void onHitBlock(net.minecraft.world.phys.BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            // 1.18.2: mob griefing ? DESTROY : NONE — ExplosionInteraction.MOB applies the same gamerule check
            Level.ExplosionInteraction explosionInteraction = net.neoforged.neoforge.event.EventHooks.canEntityGrief(this.level(), this.getOwner()) ? Level.ExplosionInteraction.MOB : Level.ExplosionInteraction.NONE;
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 1.0F, false, explosionInteraction);

        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            List<LivingEntity> list = level().getEntitiesOfClass(LivingEntity.class, new AABB(this.blockPosition()).inflate(5, 5, 5), (e) -> (e instanceof Mob || e instanceof Player) && !(e instanceof Endermite));
            for (int i = 0; i < 3; i++) {
                Endermite endermiteEntity = new Endermite(EntityType.ENDERMITE,this.level());
                endermiteEntity.setPos(this.getX(),this.getY(),this.getZ());
                if (list.size() > 1) {
                    endermiteEntity.setTarget(list.get(level().getRandom().nextInt(list.size())));
                } else if (list.size() == 1) {
                    endermiteEntity.setTarget(list.get(0));
                }
                if (list.size() >= 1) {
                    endermiteEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,80,2));
                    this.level().addFreshEntity(endermiteEntity);
                }

            }
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
