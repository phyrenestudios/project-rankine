package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemPickupHandler {
    public static void onItemPickup(PlayerEvent.ItemPickupEvent event) {
        if ((ForgeRegistries.ITEMS.tags().getTag(Tags.Items.STONE).contains(event.getStack().getItem()) || ForgeRegistries.ITEMS.tags().getTag(Tags.Items.COBBLESTONE).contains(event.getStack().getItem())) && (event.getPlayer().getMainHandItem().getItem() == RankineItems.TOTEM_OF_COBBLING.get() || event.getPlayer().getOffhandItem().getItem() == RankineItems.TOTEM_OF_COBBLING.get())) {
            Player player = event.getPlayer();
            ItemStack totem = player.getMainHandItem().getItem() == RankineItems.TOTEM_OF_COBBLING.get() ? player.getMainHandItem() : player.getOffhandItem();
            if (totem.getDamageValue() != 0) {
                int x = totem.getDamageValue() - event.getStack().copy().getCount();
                player.getInventory().getItem(event.getPlayer().getInventory().findSlotMatchingItem(event.getStack())).shrink(totem.getDamageValue());
                totem.setDamageValue(Math.max(x,0));

            }
        }
    }
}
