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
import net.minecraftforge.event.entity.living.LivingEvent;

public class LivingUpdateHandler {
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof Monster && event.getEntityLiving().getLastHurtByMob() != null) {
            if (event.getEntityLiving().getLastHurtByMob().getOffhandItem().getItem() == RankineItems.TOTEM_OF_REPULSING.get() || event.getEntityLiving().getEffect(RankineMobEffects.MERCURY_POISONING.get()) != null) {
                event.getEntityLiving().setLastHurtByMob(null);
            }
        }
    }
    public static void onEnvironmentEffect(LivingEvent.LivingUpdateEvent event) {
        if (Config.HARD_MODE.RADIOACTIVE.get()) {
            LivingEntity ent = event.getEntityLiving();
            AttributeInstance maxHealth = ent.getAttribute(Attributes.MAX_HEALTH);
            MobEffectInstance rad = ent.getEffect(RankineMobEffects.RADIATION_POISONING.get());
            if (rad != null) {
                int duration = rad.getDuration();
                if (duration >= 400 && maxHealth != null && !maxHealth.hasModifier(RankineAttributes.MINOR_RADIATION_POISONING)) {
                    maxHealth.addPermanentModifier(RankineAttributes.MINOR_RADIATION_POISONING);
                }

                if (duration >= 1600 && maxHealth != null && !maxHealth.hasModifier(RankineAttributes.RADIATION_POISONING)) {
                    maxHealth.addPermanentModifier(RankineAttributes.RADIATION_POISONING);
                }

                if (duration >= 3200 && maxHealth != null && !maxHealth.hasModifier(RankineAttributes.EXTREME_RADIATION_POISONING)) {
                    maxHealth.addPermanentModifier(RankineAttributes.EXTREME_RADIATION_POISONING);
                }
            } else {
                if (maxHealth != null && maxHealth.hasModifier(RankineAttributes.MINOR_RADIATION_POISONING)) {
                    maxHealth.removeModifier(RankineAttributes.MINOR_RADIATION_POISONING);
                }
                if (maxHealth != null && maxHealth.hasModifier(RankineAttributes.RADIATION_POISONING)) {
                    maxHealth.removeModifier(RankineAttributes.RADIATION_POISONING);
                }
                if (maxHealth != null && maxHealth.hasModifier(RankineAttributes.EXTREME_RADIATION_POISONING)) {
                    maxHealth.removeModifier(RankineAttributes.EXTREME_RADIATION_POISONING);
                }
            }
        }


    }
}
