package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.RankineEntityTypes;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

import java.util.List;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = ItemSupplier.class
)

public class EnderballEntity extends AbstractHurtingProjectile implements ItemSupplier {
    private static final EntityDataAccessor<ItemStack> STACK = SynchedEntityData.defineId(EnderballEntity.class, EntityDataSerializers.ITEM_STACK);

    public EnderballEntity(EntityType<? extends EnderballEntity> p_i50147_1_, Level p_i50147_2_) {
        super(p_i50147_1_, p_i50147_2_);
    }


    public EnderballEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
        super(RankineEntityTypes.ENDERBALL.get(), shooter, accelX, accelY, accelZ, worldIn);
    }

    public EnderballEntity(Level worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(RankineEntityTypes.ENDERBALL.get(), x, y, z, accelX, accelY, accelZ, worldIn);
    }

    @OnlyIn(Dist.CLIENT)
    public EnderballEntity(PlayMessages.SpawnEntity spawnEntity, Level world, EntityType<EnderballEntity> e) {
        super(e, world);
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void setStack(ItemStack stack) {
        if (stack.getItem() != RankineItems.CANNONBALL.get() || stack.hasTag()) {
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

    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        ItemStack itemstack = this.getStack();
        return itemstack.isEmpty() ? new ItemStack(RankineItems.ENDERBALL.get()) : itemstack;
    }

    protected void defineSynchedData() {
        this.getEntityData().define(STACK, ItemStack.EMPTY);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        ItemStack itemstack = this.getStack();
        if (!itemstack.isEmpty()) {
            compound.put("Item", itemstack.save(new CompoundTag()));
        }

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        ItemStack itemstack = ItemStack.of(compound.getCompound("Item"));
        this.setStack(itemstack);
    }

    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level.isClientSide) {
            Entity entity = result.getEntity();
            Entity entity1 = this.getOwner();
            boolean flag = entity.hurt(this.level.damageSources().generic(), 10.0F);
            if (flag && entity1 instanceof LivingEntity) {
                this.doEnchantDamageEffects((LivingEntity)entity1, entity);
            }
            Vec3 vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double)1.6D);
            if (vector3d.lengthSqr() > 0.0D) {
                entity.push(vector3d.x, 0.1D, vector3d.z);
            }
        }
    }

    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level.isClientSide) {
            Level.ExplosionInteraction explosion$mode = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner()) ? Level.ExplosionInteraction.BLOCK : Level.ExplosionInteraction.NONE;
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 1.0F, false, explosion$mode);


        }
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level.isClientSide) {
            List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, new AABB(this.blockPosition()).inflate(5, 5, 5), (e) -> (e instanceof Mob || e instanceof Player) && !(e instanceof Endermite));
            for (int i = 0; i < 3; i++) {
                Endermite endermiteEntity = new Endermite(EntityType.ENDERMITE,this.level);
                endermiteEntity.setPos(this.getX(),this.getY(),this.getZ());
                if (list.size() > 1) {
                    endermiteEntity.setTarget(list.get(level.getRandom().nextInt(list.size())));
                } else if (list.size() == 1) {
                    endermiteEntity.setTarget(list.get(0));
                }
                if (list.size() >= 1) {
                    endermiteEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,80,2));
                    this.level.addFreshEntity(endermiteEntity);
                }

            }
            this.remove(RemovalReason.DISCARDED);
        }

    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean isPickable() {
        return true;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }
}
