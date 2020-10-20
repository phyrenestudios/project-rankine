package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.ModEntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class DragonyteEntity extends SilverfishEntity {
    public DragonyteEntity(EntityType<? extends SilverfishEntity> typeIn, World worldIn) {
        super(typeIn, worldIn);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));;
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, new Class[0])).setCallsForHelp(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute getAttributes() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MAX_HEALTH, 8.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D);
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
                    ((LivingEntity)entityIn).setFire(i);
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
            return typeIn != ModEntityTypes.MANTLE_GOLEM && typeIn != ModEntityTypes.DIAMOND_MANTLE_GOLEM && typeIn != ModEntityTypes.PERIDOT_MANTLE_GOLEM && super.canAttack(typeIn);
        }
    }
}

