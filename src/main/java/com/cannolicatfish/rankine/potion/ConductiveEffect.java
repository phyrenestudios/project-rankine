package com.cannolicatfish.rankine.potion;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.world.World;

import java.util.Random;

public class ConductiveEffect extends Effect {
    protected ConductiveEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        Random rand = entityLivingBaseIn.getRNG();
        World worldIn = entityLivingBaseIn.getEntityWorld();
        if (worldIn.isRainingAt(entityLivingBaseIn.getPosition()) && rand.nextFloat() < 0.2f) {
            LightningBoltEntity ent = new LightningBoltEntity(EntityType.LIGHTNING_BOLT,worldIn);
            ent.setPosition(entityLivingBaseIn.getPosX(),entityLivingBaseIn.getPosY(),entityLivingBaseIn.getPosZ());
            worldIn.addEntity(ent);
            entityLivingBaseIn.removePotionEffect(this.getEffect());
        }
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % Math.max(1, 20 - amplifier) == 0;
    }
}
