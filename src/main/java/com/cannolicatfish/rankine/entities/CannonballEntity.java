package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.RankineDamageSources;
import com.cannolicatfish.rankine.init.RankineEntityTypes;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = IRendersAsItem.class
)

public class CannonballEntity extends DamagingProjectileEntity implements IRendersAsItem {
    private static final DataParameter<ItemStack> STACK = EntityDataManager.createKey(CannonballEntity.class, DataSerializers.ITEMSTACK);

    public CannonballEntity(EntityType<? extends CannonballEntity> p_i50147_1_, World p_i50147_2_) {
        super(p_i50147_1_, p_i50147_2_);
    }


    public CannonballEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
        super(RankineEntityTypes.CANNONBALL, shooter, accelX, accelY, accelZ, worldIn);
    }

    public CannonballEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(RankineEntityTypes.CANNONBALL, x, y, z, accelX, accelY, accelZ, worldIn);
    }

    @OnlyIn(Dist.CLIENT)
    public CannonballEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world, EntityType<CannonballEntity> e) {
        super(e, world);
    }

    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void setStack(ItemStack stack) {
        if (stack.getItem() != RankineItems.CANNONBALL.get() || stack.hasTag()) {
            this.getDataManager().set(STACK, Util.make(stack.copy(), (p_213897_0_) -> {
                p_213897_0_.setCount(1);
            }));
        }

    }

    @Override
    protected boolean isFireballFiery() {
        return false;
    }

    protected ItemStack getStack() {
        return this.getDataManager().get(STACK);
    }

    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        ItemStack itemstack = this.getStack();
        return itemstack.isEmpty() ? new ItemStack(RankineItems.CANNONBALL.get()) : itemstack;
    }

    protected void registerData() {
        this.getDataManager().register(STACK, ItemStack.EMPTY);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        ItemStack itemstack = this.getStack();
        if (!itemstack.isEmpty()) {
            compound.put("Item", itemstack.write(new CompoundNBT()));
        }

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        ItemStack itemstack = ItemStack.read(compound.getCompound("Item"));
        this.setStack(itemstack);
    }

    protected void onEntityHit(EntityRayTraceResult result) {
        super.onEntityHit(result);
        if (!this.world.isRemote) {
            Entity entity = result.getEntity();
            Entity entity1 = this.getShooter();
            boolean flag = entity.attackEntityFrom(RankineDamageSources.CANNONBALL, 10.0F);
            if (flag && entity1 instanceof LivingEntity) {
                this.applyEnchantments((LivingEntity)entity1, entity);
            }
            Vector3d vector3d = this.getMotion().mul(1.0D, 0.0D, 1.0D).normalize().scale((double)1.6D);
            if (vector3d.lengthSquared() > 0.0D) {
                entity.addVelocity(vector3d.x, 0.1D, vector3d.z);
            }
        }
    }

    protected void func_230299_a_(BlockRayTraceResult result) {
        super.func_230299_a_(result);
        if (!this.world.isRemote) {
            Explosion.Mode explosion$mode = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this.getShooter()) ? Explosion.Mode.DESTROY : Explosion.Mode.NONE;
            this.world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), 1.0F, false, explosion$mode);

        }
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
        if (!this.world.isRemote) {
            this.remove();
        }

    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith() {
        return true;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return false;
    }
}
