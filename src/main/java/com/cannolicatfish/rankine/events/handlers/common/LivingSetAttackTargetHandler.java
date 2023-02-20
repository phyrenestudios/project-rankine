package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.potion.RankineEffects;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;

public class LivingSetAttackTargetHandler {
    public static void onLivingSetAttackTarget(LivingChangeTargetEvent event) {
        if (event.getEntityLiving() instanceof Monster && event.getOriginalTarget() != null) {
            if (event.getOriginalTarget().getOffhandItem().getItem() == RankineItems.TOTEM_OF_REPULSING.get() || event.getEntityLiving().getEffect(RankineEffects.MERCURY_POISONING) != null) {
                event.setNewTarget(null);
            }
        }
    }
}
