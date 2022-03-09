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
    private static final DataParameter<ItemStack> STACK = EntityDataManager.createKey(EnderballEntity.class, DataSerializers.ITEMSTACK);

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
        return itemstack.isEmpty() ? new ItemStack(RankineItems.ENDERBALL.get()) : itemstack;
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
            List<LivingEntity> list = world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(this.getPosition()).grow(5, 5, 5), (e) -> (e instanceof MobEntity || e instanceof PlayerEntity) && !(e instanceof EndermiteEntity));
            for (int i = 0; i < 3; i++) {
                EndermiteEntity endermiteEntity = new EndermiteEntity(EntityType.ENDERMITE,this.world);
                endermiteEntity.setPosition(this.getPosX(),this.getPosY(),this.getPosZ());
                if (list.size() > 1) {
                    endermiteEntity.setAttackTarget(list.get(world.getRandom().nextInt(list.size())));
                } else if (list.size() == 1) {
                    endermiteEntity.setAttackTarget(list.get(0));
                }
                if (list.size() >= 1) {
                    endermiteEntity.addPotionEffect(new EffectInstance(Effects.SPEED,80,2));
                    this.world.addEntity(endermiteEntity);
                }

            }
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
