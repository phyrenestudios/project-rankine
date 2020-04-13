package com.cannolicatfish.rankine.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class PeridotMantleGolemEntity extends IronGolemEntity {
    public PeridotMantleGolemEntity(EntityType<? extends IronGolemEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected boolean isDespawnPeaceful() {
        return true;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, MobEntity.class, 5, false, false, (p_213619_0_) -> {
            return p_213619_0_ instanceof IMob && !(p_213619_0_ instanceof CreeperEntity)  && !(p_213619_0_ instanceof DesmoxyteEntity);
        }));
    }
    @Override
    public boolean canAttack(EntityType<?> typeIn) {
        if (this.isPlayerCreated() && typeIn == EntityType.PLAYER) {
            return true;
        } else {
            return typeIn != ModEntityTypes.DESMOXYTE && typeIn != ModEntityTypes.MANTLE_GOLEM && typeIn != ModEntityTypes.DIAMOND_MANTLE_GOLEM && typeIn != ModEntityTypes.STEAMER && super.canAttack(typeIn);
        }
    }

    @Override
    protected void registerAttributes() {
        this.getAttributes().registerAttribute(SharedMonsterAttributes.MAX_HEALTH);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ARMOR);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS);
        this.getAttributes().registerAttribute(SWIM_SPEED);
        this.getAttributes().registerAttribute(NAMETAG_DISTANCE);
        this.getAttributes().registerAttribute(ENTITY_GRAVITY);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(10.0D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.6D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
    }
}
