package com.cannolicatfish.rankine.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;

public class MercuryPoisonEffect extends Effect {
    public MercuryPoisonEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        entityLivingBaseIn.setFire(5);

    }
}
