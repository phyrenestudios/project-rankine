package com.cannolicatfish.rankine.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class DiamondMantleGolemEntity extends IronGolemEntity {
    public DiamondMantleGolemEntity(EntityType<? extends IronGolemEntity> type, World worldIn) {
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
            return typeIn != ModEntityTypes.DESMOXYTE && typeIn != ModEntityTypes.MANTLE_GOLEM && typeIn != ModEntityTypes.PERIDOT_MANTLE_GOLEM && typeIn != ModEntityTypes.STEAMER && super.canAttack(typeIn);
        }
    }

    public static AttributeModifierMap.MutableAttribute func_234200_m_() {
        return MobEntity.func_233666_p_().func_233815_a_(Attributes.MAX_HEALTH, 100.0D).func_233815_a_(Attributes.MOVEMENT_SPEED, 0.25D).func_233815_a_(Attributes.KNOCKBACK_RESISTANCE, 1.0D).func_233815_a_(Attributes.ATTACK_DAMAGE, 15.0D);
    }
}

