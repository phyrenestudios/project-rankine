package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.RankineDamageSources;
import com.cannolicatfish.rankine.init.RankineEntityTypes;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
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

import java.util.List;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = IRendersAsItem.class
)

public class CarcassEntity extends AbstractFireballEntity implements IRendersAsItem {
    private static final DataParameter<ItemStack> STACK = EntityDataManager.createKey(CarcassEntity.class, DataSerializers.ITEMSTACK);

    public CarcassEntity(EntityType<? extends CarcassEntity> p_i50147_1_, World p_i50147_2_) {
        super(p_i50147_1_, p_i50147_2_);
    }


    public CarcassEntity(World worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
        super(RankineEntityTypes.CARCASS, shooter, accelX, accelY, accelZ, worldIn);
    }

    public CarcassEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(RankineEntityTypes.CARCASS, x, y, z, accelX, accelY, accelZ, worldIn);
    }

    @OnlyIn(Dist.CLIENT)
    public CarcassEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world, EntityType<CarcassEntity> e) {
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
        return true;
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
            if (!entity.isImmuneToFire()) {
                Entity entity1 = this.getShooter();
                int i = entity.getFireTimer();
                entity.setFire(5);
                boolean flag = entity.attackEntityFrom(DamageSource.causeOnFireDamage(this, entity1), 5.0F);
                if (!flag) {
                    entity.forceFireTicks(i);
                } else if (entity1 instanceof LivingEntity) {
                    this.applyEnchantments((LivingEntity)entity1, entity);
                }
            }
        }
    }

    protected void func_230299_a_(BlockRayTraceResult result) {
        super.func_230299_a_(result);
        if (!this.world.isRemote) {
            Explosion.Mode explosion$mode = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this.getShooter()) ? Explosion.Mode.DESTROY : Explosion.Mode.NONE;
            this.world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), 0.5F, false, explosion$mode);
            Entity entity = this.getShooter();
            if (entity == null || !(entity instanceof MobEntity) || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this.getEntity())) {
                BlockPos blockpos = result.getPos().offset(result.getFace());
                if (this.world.isAirBlock(blockpos)) {
                    this.world.setBlockState(blockpos, AbstractFireBlock.getFireForPlacement(this.world, blockpos));
                }
            }

        }
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
        Entity entity = this.getShooter();
        if (!this.world.isRemote) {
            List<LivingEntity> list = this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(4.0D, 2.0D, 4.0D));
            AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ());
            if (entity instanceof LivingEntity) {
                areaeffectcloudentity.setOwner((LivingEntity)entity);
            }

            areaeffectcloudentity.setParticleData(ParticleTypes.ENTITY_EFFECT);
            areaeffectcloudentity.setRadius(3.0F);
            areaeffectcloudentity.setDuration(300);
            areaeffectcloudentity.setRadiusPerTick((7.0F - areaeffectcloudentity.getRadius()) / (float)areaeffectcloudentity.getDuration());
            areaeffectcloudentity.addEffect(new EffectInstance(Effects.POISON, 80, 1));
            if (!list.isEmpty()) {
                for(LivingEntity livingentity : list) {
                    double d0 = this.getDistanceSq(livingentity);
                    if (d0 < 16.0D) {
                        areaeffectcloudentity.setPosition(livingentity.getPosX(), livingentity.getPosY(), livingentity.getPosZ());
                        break;
                    }
                }
            }
            this.world.addEntity(areaeffectcloudentity);
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
