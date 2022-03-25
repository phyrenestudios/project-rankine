package com.cannolicatfish.rankine.potion;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import javax.annotation.Nullable;
import java.util.Random;

public class MercuryPoisonEffect extends MobEffect {
    public MercuryPoisonEffect(MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {

        if (entityLivingBaseIn instanceof Player)
        {
            Player player = ((Player)entityLivingBaseIn);

            if (player.isSprinting() || player.isSwimming() || player.swinging)
            {
                player.causeFoodExhaustion(0.05f * amplifier);
            }

        }
    }



    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}