package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;

public class EntityInteractHandler {
    public static void onBreedEvent( PlayerInteractEvent.EntityInteract event) {
        PlayerEntity player = event.getPlayer();
        Entity ent = event.getTarget();
        ItemStack itemStack = event.getItemStack();

        if (ent instanceof AnimalEntity) {
            AnimalEntity entA = (AnimalEntity) ent;
            EntityType<?> type = ent.getType();
            boolean flag = false;
            if (type.equals(EntityType.PIG) && itemStack.getItem().isIn(RankineTags.Items.BREEDABLES_PIG)) {
                flag = true;
            } else if ((type.equals(EntityType.COW) || type.equals(EntityType.MOOSHROOM)) && itemStack.getItem().isIn(RankineTags.Items.BREEDABLES_COW)) {
                flag = true;
            } else if (type.equals(EntityType.SHEEP) && itemStack.getItem().isIn(RankineTags.Items.BREEDABLES_SHEEP)) {
                flag = true;
            } else if (type.equals(EntityType.CHICKEN) && itemStack.getItem().isIn(RankineTags.Items.BREEDABLES_CHICKEN)) {
                flag = true;
            } else if (type.equals(EntityType.FOX) && itemStack.getItem().isIn(RankineTags.Items.BREEDABLES_FOX)) {
                flag = true;
            } else if (type.equals(EntityType.RABBIT) && itemStack.getItem().isIn(RankineTags.Items.BREEDABLES_RABBIT)) {
                flag = true;
            } else if (type.equals(EntityType.CAT) && itemStack.getItem().isIn(RankineTags.Items.BREEDABLES_CAT)) {
                flag = true;
            } else if ((type.equals(EntityType.HORSE) || type.equals(EntityType.DONKEY)) && itemStack.getItem().isIn(RankineTags.Items.BREEDABLES_HORSE)) {
                flag = true;
            }

            if (flag) {
                int i = entA.getGrowingAge();
                if (!entA.world.isRemote && i == 0 && entA.canFallInLove()) {
                    if (!player.abilities.isCreativeMode) {
                        itemStack.shrink(1);
                    }
                    entA.setInLove(player);
                    event.setResult(Event.Result.ALLOW);
                }

                if (entA.isChild()) {
                    if (!player.abilities.isCreativeMode) {
                        itemStack.shrink(1);
                    }
                    entA.ageUp((int) ((float) (-i / 20) * 0.1F), true);
                    event.setResult(Event.Result.ALLOW);
                }

                if (entA.world.isRemote) {
                    event.setResult(Event.Result.ALLOW);
                }
            } else {
                event.setResult(Event.Result.DENY);
            }

        }


    }
}
