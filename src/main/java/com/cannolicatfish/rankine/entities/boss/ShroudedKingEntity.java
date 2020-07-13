package com.cannolicatfish.rankine.entities.boss;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.function.Predicate;

public class ShroudedKingEntity extends WitherEntity {
    private static final Predicate<LivingEntity> NOT_UNDEAD = (p_213797_0_) -> {
        return p_213797_0_.getCreatureAttribute() != CreatureAttribute.UNDEAD && p_213797_0_.attackable();
    };

    public ShroudedKingEntity(EntityType<? extends WitherEntity> p_i50226_1_, World p_i50226_2_) {
        super(p_i50226_1_, p_i50226_2_);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ShroudedKingEntity.DoNothingGoal());
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0D, 40, 20.0F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, MobEntity.class, 0, false, false, NOT_UNDEAD));

    }

    class DoNothingGoal extends Goal {
        public DoNothingGoal() {
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return ShroudedKingEntity.this.getInvulTime() > 0;
        }
    }

    public static AttributeModifierMap.MutableAttribute func_234258_eI_() {
        return MonsterEntity.func_234295_eP_().func_233815_a_(Attributes.MAX_HEALTH, 300.0D).func_233815_a_(Attributes.MOVEMENT_SPEED, (double)0.6F).func_233815_a_(Attributes.FOLLOW_RANGE, 40.0D).func_233815_a_(Attributes.ARMOR, 4.0D);
    }
}
