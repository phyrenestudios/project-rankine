package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineAttributes;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

/**
 * 1.21.1: LivingEvent.LivingUpdateEvent -> EntityTickEvent.Pre (fired at the head of
 * LivingEntity#tick, same point as the old event). Callers must filter for LivingEntity.
 * AttributeInstance#hasModifier now takes the modifier's ResourceLocation id.
 */
public class LivingUpdateHandler {
    public static void onLivingUpdate(EntityTickEvent.Pre event) {
        if (!(event.getEntity() instanceof LivingEntity living)) return;
        if (living instanceof Monster && living.getLastHurtByMob() != null) {
            if (living.getLastHurtByMob().getOffhandItem().getItem() == RankineItems.TOTEM_OF_REPULSING.get() || living.getEffect(RankineMobEffects.MERCURY_POISONING) != null) {
                living.setLastHurtByMob(null);
            }
        }
    }
    public static void onEnvironmentEffect(EntityTickEvent.Pre event) {
        if (!(event.getEntity() instanceof LivingEntity ent)) return;
        if (Config.HARD_MODE.RADIOACTIVE.get()) {
            AttributeInstance maxHealth = ent.getAttribute(Attributes.MAX_HEALTH);
            MobEffectInstance rad = ent.getEffect(RankineMobEffects.RADIATION_POISONING);
            if (rad != null) {
                int duration = rad.getDuration();
                if (duration >= 400 && maxHealth != null && !maxHealth.hasModifier(RankineAttributes.MINOR_RADIATION_POISONING.id())) {
                    maxHealth.addPermanentModifier(RankineAttributes.MINOR_RADIATION_POISONING);
                }

                if (duration >= 1600 && maxHealth != null && !maxHealth.hasModifier(RankineAttributes.RADIATION_POISONING.id())) {
                    maxHealth.addPermanentModifier(RankineAttributes.RADIATION_POISONING);
                }

                if (duration >= 3200 && maxHealth != null && !maxHealth.hasModifier(RankineAttributes.EXTREME_RADIATION_POISONING.id())) {
                    maxHealth.addPermanentModifier(RankineAttributes.EXTREME_RADIATION_POISONING);
                }
            } else {
                if (maxHealth != null && maxHealth.hasModifier(RankineAttributes.MINOR_RADIATION_POISONING.id())) {
                    maxHealth.removeModifier(RankineAttributes.MINOR_RADIATION_POISONING);
                }
                if (maxHealth != null && maxHealth.hasModifier(RankineAttributes.RADIATION_POISONING.id())) {
                    maxHealth.removeModifier(RankineAttributes.RADIATION_POISONING);
                }
                if (maxHealth != null && maxHealth.hasModifier(RankineAttributes.EXTREME_RADIATION_POISONING.id())) {
                    maxHealth.removeModifier(RankineAttributes.EXTREME_RADIATION_POISONING);
                }
            }
        }


    }
}
