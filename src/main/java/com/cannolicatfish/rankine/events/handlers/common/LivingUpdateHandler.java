package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineAttributes;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.potion.RankineEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.entity.living.LivingEvent;

public class LivingUpdateHandler {
    public static void onEnvironmentEffect( LivingEvent.LivingUpdateEvent event) {
        if (Config.HARD_MODE.RADIOACTIVE.get()) {
            LivingEntity ent = event.getEntityLiving();
            ModifiableAttributeInstance maxHealth = ent.getAttribute(Attributes.MAX_HEALTH);
            EffectInstance rad = ent.getActivePotionEffect(RankineEffects.RADIATION_POISONING);
            if (rad != null) {
                int duration = rad.getDuration();
                if (duration >= 400 && maxHealth != null && !maxHealth.hasModifier(RankineAttributes.MINOR_RADIATION_POISONING)) {
                    maxHealth.applyPersistentModifier(RankineAttributes.MINOR_RADIATION_POISONING);
                }

                if (duration >= 1600 && maxHealth != null && !maxHealth.hasModifier(RankineAttributes.RADIATION_POISONING)) {
                    maxHealth.applyPersistentModifier(RankineAttributes.RADIATION_POISONING);
                }

                if (duration >= 3200 && maxHealth != null && !maxHealth.hasModifier(RankineAttributes.EXTREME_RADIATION_POISONING)) {
                    maxHealth.applyPersistentModifier(RankineAttributes.EXTREME_RADIATION_POISONING);
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


    public static void onLivingUpdate( LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof MonsterEntity && event.getEntityLiving().getRevengeTarget() != null) {
            if (event.getEntityLiving().getRevengeTarget().getHeldItemOffhand().getItem() == RankineItems.TOTEM_OF_REPULSING.get() || event.getEntityLiving().getActivePotionEffect(RankineEffects.MERCURY_POISONING) != null) {
                event.getEntityLiving().setRevengeTarget(null);
            }
        }
    }
}
