package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.potion.RankineEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;

public class LivingSetAttackTargetHandler {
    public static void onLivingSetAttackTarget(LivingSetAttackTargetEvent event) {
        if (event.getEntityLiving() instanceof Monster && event.getTarget() != null) {
            if (event.getTarget().getOffhandItem().getItem() == RankineItems.TOTEM_OF_REPULSING.get() || event.getEntityLiving().getEffect(RankineEffects.MERCURY_POISONING) != null) {
                ((Mob) event.getEntityLiving()).setTarget(null);
            }
        }
    }
}
