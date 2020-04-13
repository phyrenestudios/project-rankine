package com.cannolicatfish.rankine.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class DemonyteEntity extends SilverfishEntity {
    public DemonyteEntity(EntityType<? extends SilverfishEntity> typeIn, World worldIn) {
        super(typeIn, worldIn);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));;
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, new Class[0])).setCallsForHelp(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, PlayerEntity.class, true));
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
        this.getAttributes().registerAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        if (super.attackEntityAsMob(entityIn)) {
            if (entityIn instanceof LivingEntity) {
                int i = 0;
                if (this.world.getDifficulty() == Difficulty.NORMAL) {
                    i = 5;
                } else if (this.world.getDifficulty() == Difficulty.HARD) {
                    i = 10;
                }

                if (i > 0) {
                    ((LivingEntity)entityIn).addPotionEffect(new EffectInstance(Effects.WITHER, i * 20, 0));
                }
            }

            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean canAttack(EntityType<?> typeIn) {
        if (typeIn == EntityType.PLAYER) {
            return true;
        } else {
            return typeIn != ModEntityTypes.MANTLE_GOLEM && typeIn != ModEntityTypes.STEAMER && typeIn != ModEntityTypes.DIAMOND_MANTLE_GOLEM && typeIn != ModEntityTypes.PERIDOT_MANTLE_GOLEM && super.canAttack(typeIn);
        }
    }
}

