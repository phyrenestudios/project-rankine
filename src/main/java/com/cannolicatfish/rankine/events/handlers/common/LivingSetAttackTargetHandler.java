package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.potion.RankineEffects;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;

public class LivingSetAttackTargetHandler {
    public static void onLivingSetAttackTarget( LivingSetAttackTargetEvent event) {
        if (event.getEntityLiving() instanceof MonsterEntity && event.getTarget() != null) {
            if (event.getTarget().getHeldItemOffhand().getItem() == RankineItems.TOTEM_OF_REPULSING.get() || event.getEntityLiving().getActivePotionEffect(RankineEffects.MERCURY_POISONING) != null) {
                ((MobEntity) event.getEntityLiving()).setAttackTarget(null);
            }
        }
    }
}
