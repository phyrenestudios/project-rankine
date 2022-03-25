package com.cannolicatfish.rankine.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class RadiationPoisonEffect extends MobEffect {
    public RadiationPoisonEffect(MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }


    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }
}
