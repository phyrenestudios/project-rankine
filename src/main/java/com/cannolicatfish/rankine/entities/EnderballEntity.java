package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.RankineDamageSources;
import com.cannolicatfish.rankine.init.RankineEntityTypes;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Util;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = IRendersAsItem.class
)

public class EnderballEntity extends DamagingProjectileEntity implements IRendersAsItem {
    private static final DataParameter<ItemStack> STACK = EntityDataManager.defineId(EnderballEntity.class, DataSerializers.ITEM_STACK);

    public EnderballEntity(EntityType<? extends EnderballEntity> p_i50147_1_, World p_i50147_2_) {
        super(p_i50147_1_, p_i50147_2_);
    }


    public EnderballEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
        super(RankineEntityTypes.ENDERBALL, shooter, accelX, accelY, accelZ, worldIn);
    }

    public EnderballEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(RankineEntityTypes.ENDERBALL, x, y, z, accelX, accelY, accelZ, worldIn);
    }

    @OnlyIn(Dist.CLIENT)
    public EnderballEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world, EntityType<EnderballEntity> e) {
        super(e, world);
    }

    public IPacket<?> getAddEntityPacket() {
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

    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        ItemStack itemstack = this.getStack();
        if (!itemstack.isEmpty()) {
            compound.put("Item", itemstack.save(new CompoundNBT()));
        }

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        ItemStack itemstack = ItemStack.of(compound.getCompound("Item"));
        this.setStack(itemstack);
    }

    protected void onHitEntity(EntityRayTraceResult result) {
        super.onHitEntity(result);
        if (!this.level.isClientSide) {
            Entity entity = result.getEntity();
            Entity entity1 = this.getOwner();
            boolean flag = entity.hurt(RankineDamageSources.CANNONBALL, 10.0F);
            if (flag && entity1 instanceof LivingEntity) {
                this.doEnchantDamageEffects((LivingEntity)entity1, entity);
            }
            Vector3d vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double)1.6D);
            if (vector3d.lengthSqr() > 0.0D) {
                entity.push(vector3d.x, 0.1D, vector3d.z);
            }
        }
    }

    protected void onHitBlock(BlockRayTraceResult result) {
        super.onHitBlock(result);
        if (!this.level.isClientSide) {
            Explosion.Mode explosion$mode = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner()) ? Explosion.Mode.DESTROY : Explosion.Mode.NONE;
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 1.0F, false, explosion$mode);

        }
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onHit(RayTraceResult result) {
        super.onHit(result);
        if (!this.level.isClientSide) {
            List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(this.blockPosition()).inflate(5, 5, 5), (e) -> (e instanceof MobEntity || e instanceof PlayerEntity) && !(e instanceof EndermiteEntity));
            for (int i = 0; i < 3; i++) {
                EndermiteEntity endermiteEntity = new EndermiteEntity(EntityType.ENDERMITE,this.level);
                endermiteEntity.setPos(this.getX(),this.getY(),this.getZ());
                if (list.size() > 1) {
                    endermiteEntity.setTarget(list.get(level.getRandom().nextInt(list.size())));
                } else if (list.size() == 1) {
                    endermiteEntity.setTarget(list.get(0));
                }
                if (list.size() >= 1) {
                    endermiteEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED,80,2));
                    this.level.addFreshEntity(endermiteEntity);
                }

            }
            this.remove();
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
