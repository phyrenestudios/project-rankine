package com.cannolicatfish.rankine.potion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.PhantomEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;

import javax.annotation.Nullable;
import java.util.Random;

public class MercuryPoisonEffect extends Effect {
    public MercuryPoisonEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        Random rand = new Random();
        if (entityLivingBaseIn instanceof CreeperEntity)
        {
            ((CreeperEntity)entityLivingBaseIn).ignite();
        }
        else if (entityLivingBaseIn instanceof PlayerEntity)
        {

            PlayerEntity player = ((PlayerEntity)entityLivingBaseIn);
            if (rand.nextFloat() < .25f)
            {
                player.swingArm(Hand.MAIN_HAND);
                player.addExhaustion(0.05f);

            } else if (rand.nextFloat() < .1f && player.isOnGround())
            {
                player.jump();
            }

        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}