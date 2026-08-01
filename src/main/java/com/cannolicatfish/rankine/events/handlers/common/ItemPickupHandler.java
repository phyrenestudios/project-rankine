package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;

public class ItemPickupHandler {
    // 1.21.1: PlayerEvent.ItemPickupEvent -> ItemEntityPickupEvent.Post (fires after the pickup,
    // same as the old event); getStack() -> getOriginalStack().
    // Tags: c:stone/c:cobblestone were renamed to c:stones/c:cobblestones.
    public static void onItemPickup(ItemEntityPickupEvent.Post event) {
        ItemStack picked = event.getOriginalStack();
        if ((picked.is(Tags.Items.STONES) || picked.is(Tags.Items.COBBLESTONES))
                && (event.getPlayer().getMainHandItem().getItem() == RankineItems.TOTEM_OF_COBBLING.get() || event.getPlayer().getOffhandItem().getItem() == RankineItems.TOTEM_OF_COBBLING.get())) {
            Player player = event.getPlayer();
            ItemStack totem = player.getMainHandItem().getItem() == RankineItems.TOTEM_OF_COBBLING.get() ? player.getMainHandItem() : player.getOffhandItem();
            if (totem.getDamageValue() != 0) {
                int x = totem.getDamageValue() - picked.copy().getCount();
                int slot = player.getInventory().findSlotMatchingItem(picked);
                if (slot != -1) {
                    player.getInventory().getItem(slot).shrink(totem.getDamageValue());
                }
                totem.setDamageValue(Math.max(x,0));

            }
        }
    }
}
