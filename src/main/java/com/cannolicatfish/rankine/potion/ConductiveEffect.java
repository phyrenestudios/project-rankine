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
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        Random rand = entityLivingBaseIn.getRandom();
        World worldIn = entityLivingBaseIn.getCommandSenderWorld();
        if (worldIn.isRainingAt(entityLivingBaseIn.blockPosition()) && rand.nextFloat() < 0.2f) {
            LightningBoltEntity ent = new LightningBoltEntity(EntityType.LIGHTNING_BOLT,worldIn);
            ent.setPos(entityLivingBaseIn.getX(),entityLivingBaseIn.getY(),entityLivingBaseIn.getZ());
            worldIn.addFreshEntity(ent);
            entityLivingBaseIn.removeEffect(this.getEffect());
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
