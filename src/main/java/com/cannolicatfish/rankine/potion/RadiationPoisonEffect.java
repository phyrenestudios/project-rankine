package com.cannolicatfish.rankine.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class RadiationPoisonEffect extends Effect {
    public RadiationPoisonEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }


    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }
}
