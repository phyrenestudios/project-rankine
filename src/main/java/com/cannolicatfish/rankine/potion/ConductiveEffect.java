package com.cannolicatfish.rankine.potion;

import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class ConductiveEffect extends MobEffect {
    public ConductiveEffect(MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        RandomSource rand = entityLivingBaseIn.getRandom();
        Level worldIn = entityLivingBaseIn.getCommandSenderWorld();
        if (worldIn.isRainingAt(entityLivingBaseIn.blockPosition()) && rand.nextFloat() < 0.2f) {
            LightningBolt ent = new LightningBolt(EntityType.LIGHTNING_BOLT, worldIn);
            ent.setPos(entityLivingBaseIn.getX(), entityLivingBaseIn.getY(), entityLivingBaseIn.getZ());
            worldIn.addFreshEntity(ent);
            // 1.18.2 called removeEffect(this); in 1.21.1 returning false removes the effect.
            return false;
        }
        return true;
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % Math.max(1, 20 - amplifier) == 0;
    }
}
