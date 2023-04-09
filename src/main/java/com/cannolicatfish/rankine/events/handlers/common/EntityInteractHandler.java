package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;

public class EntityInteractHandler {
    public static void breedables(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Entity ent = event.getTarget();
        ItemStack itemStack = event.getItemStack();

        if (ent instanceof Animal entA) {
            EntityType<?> type = ent.getType();
            boolean flag = false;
            if (type.equals(EntityType.PIG) && itemStack.is(RankineTags.Items.BREEDABLES_PIG)) {
                flag = true;
            } else if ((type.equals(EntityType.COW) || type.equals(EntityType.MOOSHROOM)) && itemStack.is(RankineTags.Items.BREEDABLES_COW)) {
                flag = true;
            } else if (type.equals(EntityType.SHEEP) && itemStack.is(RankineTags.Items.BREEDABLES_SHEEP)) {
                flag = true;
            } else if (type.equals(EntityType.CHICKEN) && itemStack.is(RankineTags.Items.BREEDABLES_CHICKEN)) {
                flag = true;
            } else if (type.equals(EntityType.FOX) && itemStack.is(RankineTags.Items.BREEDABLES_FOX)) {
                flag = true;
            } else if (type.equals(EntityType.RABBIT) && itemStack.is(RankineTags.Items.BREEDABLES_RABBIT)) {
                flag = true;
            } else if (type.equals(EntityType.CAT) && itemStack.is(RankineTags.Items.BREEDABLES_CAT)) {
                flag = true;
            } else if ((type.equals(EntityType.HORSE) || type.equals(EntityType.DONKEY)) && itemStack.is(RankineTags.Items.BREEDABLES_HORSE)) {
                flag = true;
            }

            if (flag) {
                int i = entA.getAge();
                if (!entA.level.isClientSide && i == 0 && entA.canFallInLove()) {
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                    entA.setInLove(player);
                    event.setResult(Event.Result.ALLOW);
                }

                if (entA.isBaby()) {
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                    entA.ageUp((int) ((float) (-i / 20) * 0.1F), true);
                    event.setResult(Event.Result.ALLOW);
                }

                if (entA.level.isClientSide) {
                    event.setResult(Event.Result.ALLOW);
                }
            } else {
                event.setResult(Event.Result.DENY);
            }

        }


    }
}
