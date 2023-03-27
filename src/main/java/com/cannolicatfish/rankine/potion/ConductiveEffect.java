package com.cannolicatfish.rankine.potion;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.level.Level;

import java.util.Random;

public class ConductiveEffect extends MobEffect {
    public ConductiveEffect(MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        Random rand = entityLivingBaseIn.getRandom();
        Level worldIn = entityLivingBaseIn.getCommandSenderWorld();
        if (worldIn.isRainingAt(entityLivingBaseIn.blockPosition()) && rand.nextFloat() < 0.2f) {
            LightningBolt ent = new LightningBolt(EntityType.LIGHTNING_BOLT,worldIn);
            ent.setPos(entityLivingBaseIn.getX(),entityLivingBaseIn.getY(),entityLivingBaseIn.getZ());
            worldIn.addFreshEntity(ent);
            entityLivingBaseIn.removeEffect(this);
        }
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % Math.max(1, 20 - amplifier) == 0;
    }
}
