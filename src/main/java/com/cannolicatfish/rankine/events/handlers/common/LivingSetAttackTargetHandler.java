package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineMobEffects;
import net.minecraft.world.entity.monster.Monster;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;

public class LivingSetAttackTargetHandler {
    /**
     * 1.21.1: LivingSetAttackTargetEvent -> LivingChangeTargetEvent (fires BEFORE the target is set
     * and is cancelable), so the old "set the target back to null after the fact" workaround is
     * replaced by cancelling the target change. getEntityLiving() -> getEntity().
     */
    public static void onLivingSetAttackTarget(LivingChangeTargetEvent event) {
        if (event.getEntity() instanceof Monster && event.getNewAboutToBeSetTarget() != null) {
            if (event.getNewAboutToBeSetTarget().getOffhandItem().getItem() == RankineItems.TOTEM_OF_REPULSING.get()
                    || event.getEntity().getEffect(RankineMobEffects.MERCURY_POISONING) != null) {
                event.setCanceled(true);
            }
        }
    }

}
