package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.compatibility.Patchouli;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class PlayerLoggedInHandler {
    private static final String NBT_KEY = "rankine.firstjoin";

    // 1.21.1: getPlayer() -> getEntity(); the Patchouli API dependency is gone, so the journal stack
    // is built from components by compatibility/Patchouli.
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (Config.GENERAL.REFRESH_ALLOYS.get()) {
            for(int i = 0; i < player.getInventory().getContainerSize(); ++i) {
                ItemStack itemstack = player.getInventory().getItem(i);
                if (!itemstack.isEmpty() && itemstack.getItem() instanceof IAlloyItem alloyItem) {
                    alloyItem.setRefresh(itemstack);
                }
            }
        }

        if (Config.GENERAL.STARTING_BOOK.get() && !player.getCommandSenderWorld().isClientSide && Patchouli.isInstalled()) {

            CompoundTag data = player.getPersistentData();
            CompoundTag persistent;
            if (!data.contains(Player.PERSISTED_NBT_TAG)) {
                data.put(Player.PERSISTED_NBT_TAG, (persistent = new CompoundTag()));
            } else {
                persistent = data.getCompound(Player.PERSISTED_NBT_TAG);
            }

            if (!persistent.contains(NBT_KEY)) {
                persistent.putBoolean(NBT_KEY, true);
                ItemStack journal = Patchouli.getJournalBookStack();
                if (!journal.isEmpty()) {
                    player.getInventory().add(journal);
                }
            }
        }
    }
}
