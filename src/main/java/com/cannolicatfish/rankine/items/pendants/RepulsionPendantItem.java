package com.cannolicatfish.rankine.items.pendants;


import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.EntityPredicates;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;

import java.util.Objects;

public class RepulsionPendantItem extends Item{
    public RepulsionPendantItem(Properties properties) {
        super(properties);
        MinecraftForge.EVENT_BUS.addListener(this::onLivingSetAttackTarget);
        MinecraftForge.EVENT_BUS.addListener(this::onLivingUpdate);
        MinecraftForge.EVENT_BUS.addListener(this::onEntityJoinWorld);
    }

    public void onLivingSetAttackTarget(LivingSetAttackTargetEvent event) {
        if (event.getEntityLiving() instanceof MonsterEntity && event.getTarget() != null) {
            if (event.getTarget().getHeldItemOffhand().getItem() == this) {
                ((MobEntity) event.getEntityLiving()).setAttackTarget(null);
            }
        }
    }

    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof MonsterEntity && event.getEntityLiving().getRevengeTarget() != null) {
            if (event.getEntityLiving().getRevengeTarget().getHeldItemOffhand().getItem() == this) {
                event.getEntityLiving().setRevengeTarget(null);
            }
        }
    }

    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof MonsterEntity) {
                ((MonsterEntity) event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>((MonsterEntity) event.getEntity(), PlayerEntity.class, Objects::nonNull, 6, 1, 1.3, EntityPredicates.CAN_AI_TARGET::test));
        }
    }


}
