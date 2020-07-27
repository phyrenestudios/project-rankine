package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.ModEntityTypes;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Collection;
import java.util.EnumSet;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = IChargeableMob.class
)
public class SteamerEntity extends MonsterEntity implements IChargeableMob {
    private float heightOffset = 0.5F;
    private static final DataParameter<Integer> STATE = EntityDataManager.createKey(SteamerEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> POWERED = EntityDataManager.createKey(SteamerEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IGNITED = EntityDataManager.createKey(SteamerEntity.class, DataSerializers.BOOLEAN);
    private int lastActiveTime;
    private int timeSinceIgnited;
    private int fuseTime = 30;
    private int explosionRadius = 3;
    private int droppedSkulls;
    private int heightOffsetUpdateTime;
    public SteamerEntity(EntityType<? extends MonsterEntity> p_i50213_1_, World p_i50213_2_) {
        super(p_i50213_1_, p_i50213_2_);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new SteamerSwellGoal(this));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, OcelotEntity.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, CatEntity.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));

    }

    @Override
    public boolean canAttack(EntityType<?> typeIn) {
        if (typeIn == EntityType.PLAYER) {
            return true;
        } else {
            return typeIn != ModEntityTypes.MANTLE_GOLEM && typeIn != ModEntityTypes.DESMOXYTE && typeIn != ModEntityTypes.DIAMOND_MANTLE_GOLEM && typeIn != ModEntityTypes.PERIDOT_MANTLE_GOLEM && super.canAttack(typeIn);
        }
    }

    public static AttributeModifierMap.MutableAttribute getAttributes() {
        return MonsterEntity.func_234295_eP_().func_233815_a_(Attributes.MOVEMENT_SPEED, 0.25D);
    }


    protected void registerData() {
        super.registerData();
        this.dataManager.register(STATE, -1);
        this.dataManager.register(POWERED, false);
        this.dataManager.register(IGNITED, false);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        if (this.dataManager.get(POWERED)) {
            compound.putBoolean("powered", true);
        }

        compound.putShort("Fuse", (short)this.fuseTime);
        compound.putByte("ExplosionRadius", (byte)this.explosionRadius);
        compound.putBoolean("ignited", this.hasIgnited());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.dataManager.set(POWERED, compound.getBoolean("powered"));
        if (compound.contains("Fuse", 99)) {
            this.fuseTime = compound.getShort("Fuse");
        }

        if (compound.contains("ExplosionRadius", 99)) {
            this.explosionRadius = compound.getByte("ExplosionRadius");
        }

        if (compound.getBoolean("ignited")) {
            this.ignite();
        }

    }

    abstract class MoveGoal extends Goal {
        public MoveGoal() {
            this.setMutexFlags(EnumSet.of(Flag.MOVE));
        }

        protected boolean func_203146_f() {
            return SteamerEntity.this.getPositionVec().squareDistanceTo(SteamerEntity.this.getPosX(), SteamerEntity.this.getPosY(), SteamerEntity.this.getPosZ()) < 4.0D;
        }
    }



    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        if (this.isAlive()) {
            this.lastActiveTime = this.timeSinceIgnited;
            if (this.hasIgnited()) {
                this.setCreeperState(1);
            }

            int i = this.getCreeperState();
            if (i > 0 && this.timeSinceIgnited == 0) {
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
            }

            this.timeSinceIgnited += i;
            if (this.timeSinceIgnited < 0) {
                this.timeSinceIgnited = 0;
            }

            if (this.timeSinceIgnited >= this.fuseTime) {
                this.timeSinceIgnited = this.fuseTime;
                this.explode();
            }
        }

        super.tick();
    }

    public void livingTick() {
        if (!this.onGround && this.getMotion().y < 0.0D) {
            this.setMotion(this.getMotion().mul(1.0D, 1.0D, 1.0D));
        }

        if (this.world.isRemote) {
            if (this.rand.nextInt(24) == 0 && !this.isSilent()) {
                this.world.playSound(this.getPosX() + 0.5D, this.getPosY() + 0.5D, this.getPosZ() + 0.5D, SoundEvents.ENTITY_BLAZE_BURN, this.getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
            }

        }

        super.livingTick();
    }


    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_CREEPER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CREEPER_DEATH;
    }

    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropSpecialItems(source, looting, recentlyHitIn);
        Entity entity = source.getTrueSource();

    }

    public boolean attackEntityAsMob(Entity entityIn) {
        return true;
    }

    public boolean isCharged() {
        return this.dataManager.get(POWERED);
    }

    /**
     * Params: (Float)Render tick. Returns the intensity of the creeper's flash when it is ignited.
     */
    @OnlyIn(Dist.CLIENT)
    public float getCreeperFlashIntensity(float partialTicks) {
        return MathHelper.lerp(partialTicks, (float)this.lastActiveTime, (float)this.timeSinceIgnited) / (float)(this.fuseTime - 2);
    }

    /**
     * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
     */
    public int getCreeperState() {
        return this.dataManager.get(STATE);
    }

    /**
     * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
     */
    public void setCreeperState(int state) {
        this.dataManager.set(STATE, state);
    }

    /**
     * Called when a lightning bolt hits the entity.
     */
    public void onStruckByLightning(LightningBoltEntity lightningBolt) {
        super.onStruckByLightning(lightningBolt);
        this.dataManager.set(POWERED, true);
    }
/*
    protected boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
            this.world.playSound(player, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ITEM_FLINTANDSTEEL_USE, this.getSoundCategory(), 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
            if (!this.world.isRemote) {
                this.ignite();
                itemstack.damageItem(1, player, (p_213625_1_) -> {
                    p_213625_1_.sendBreakAnimation(hand);
                });
            }

            return true;
        } else {
            return super.processInteract(player, hand);
        }
    }
   
 */

    /**
     * Creates an explosion as determined by this creeper's power and explosion radius.
     */
    private void explode() {
        if (!this.world.isRemote) {
            Explosion.Mode explosion$mode = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this) ? Explosion.Mode.NONE : Explosion.Mode.NONE;
            float f = this.isCharged() ? 1.5F : 1.0F;
            this.addPotionEffect(new EffectInstance(Effects.LEVITATION,10*20,4));
            this.dead = true;
            this.world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), (float)this.explosionRadius * f, explosion$mode);
            this.remove();
            this.spawnLingeringCloud();
        }

    }

    private void spawnLingeringCloud() {
        Collection<EffectInstance> collection = this.getActivePotionEffects();
        if (!collection.isEmpty()) {
            AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ());
            areaeffectcloudentity.setRadius(2.5F);
            areaeffectcloudentity.setRadiusOnUse(-0.5F);
            areaeffectcloudentity.setWaitTime(10);
            areaeffectcloudentity.setDuration(areaeffectcloudentity.getDuration() / 2);
            areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());

            for(EffectInstance effectinstance : collection) {
                areaeffectcloudentity.addEffect(new EffectInstance(effectinstance));
            }

            this.world.addEntity(areaeffectcloudentity);
        }

    }

    public boolean hasIgnited() {
        return this.dataManager.get(IGNITED);
    }

    public void ignite() {
        this.dataManager.set(IGNITED, true);
    }

    /**
     * Returns true if an entity is able to drop its skull due to being blown up by this creeper.
     *
     * Does not test if this creeper is charged; the caller must do that. However, does test the doMobLoot gamerule.
     */
    public boolean ableToCauseSkullDrop() {
        return this.isCharged() && this.droppedSkulls < 1;
    }

    public void incrementDroppedSkulls() {
        ++this.droppedSkulls;
    }

    public class SteamerSwellGoal extends Goal {
        private final SteamerEntity swellingCreeper;
        private LivingEntity creeperAttackTarget;

        public SteamerSwellGoal(SteamerEntity p_i1655_1_) {
            this.swellingCreeper = p_i1655_1_;
            this.setMutexFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean shouldExecute() {
            LivingEntity lvt_1_1_ = this.swellingCreeper.getAttackTarget();
            return this.swellingCreeper.getCreeperState() > 0 || lvt_1_1_ != null && this.swellingCreeper.getDistanceSq(lvt_1_1_) < 9.0D;
        }

        public void startExecuting() {
            this.swellingCreeper.getNavigator().clearPath();
            this.creeperAttackTarget = this.swellingCreeper.getAttackTarget();
        }

        public void resetTask() {
            this.creeperAttackTarget = null;
        }

        public void tick() {
            if (this.creeperAttackTarget == null) {
                this.swellingCreeper.setCreeperState(-1);
            } else if (this.swellingCreeper.getDistanceSq(this.creeperAttackTarget) > 49.0D) {
                this.swellingCreeper.setCreeperState(-1);
            } else if (!this.swellingCreeper.getEntitySenses().canSee(this.creeperAttackTarget)) {
                this.swellingCreeper.setCreeperState(-1);
            } else {
                this.swellingCreeper.setCreeperState(1);
            }
        }
    }
}
