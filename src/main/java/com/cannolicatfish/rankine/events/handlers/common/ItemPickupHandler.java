package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class ItemPickupHandler {
    public static void onItemPickup( PlayerEvent.ItemPickupEvent event) {

        // Totem of Cobbling
        if ((event.getStack().getItem().getTags().contains(new ResourceLocation("forge:stone")) || event.getStack().getItem() == Items.COBBLESTONE) && (event.getPlayer().getHeldItemMainhand().getItem() == RankineItems.TOTEM_OF_COBBLING.get() || event.getPlayer().getHeldItemOffhand().getItem() == RankineItems.TOTEM_OF_COBBLING.get())) {
            PlayerEntity player = event.getPlayer();
            ItemStack totem = player.getHeldItemMainhand().getItem() == RankineItems.TOTEM_OF_COBBLING.get() ? player.getHeldItemMainhand() : player.getHeldItemOffhand();
            if (totem.getDamage() != 0) {
                int x = totem.getDamage() - event.getStack().copy().getCount();
                player.inventory.getStackInSlot(event.getPlayer().inventory.getSlotFor(event.getStack())).shrink(totem.getDamage());
                totem.setDamage(Math.max(x,0));

            }
        }
    }
}
