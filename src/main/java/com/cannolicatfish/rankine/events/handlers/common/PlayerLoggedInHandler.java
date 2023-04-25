package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.compatibility.Patchouli;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PlayerLoggedInHandler {
    private static final String NBT_KEY = "rankine.firstjoin";

    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (Config.GENERAL.REFRESH_ALLOYS.get()) {
            for(int i = 0; i < event.getEntity().getInventory().getContainerSize(); ++i) {
                ItemStack itemstack = event.getEntity().getInventory().getItem(i);
                if (!itemstack.isEmpty() && itemstack.getItem() instanceof IAlloyItem) {
                    ((AlloyItem)itemstack.getItem()).setRefresh(itemstack);
                }
            }
        }

        if (Config.GENERAL.STARTING_BOOK.get() && !event.getEntity().getCommandSenderWorld().isClientSide && Patchouli.isInstalled()) {

            CompoundTag data = event.getEntity().getPersistentData();
            CompoundTag persistent;
            if (!data.contains(Player.PERSISTED_NBT_TAG)) {
                data.put(Player.PERSISTED_NBT_TAG, (persistent = new CompoundTag()));
            } else {
                persistent = data.getCompound(Player.PERSISTED_NBT_TAG);
            }

            if (!persistent.contains(NBT_KEY)) {
                persistent.putBoolean(NBT_KEY, true);
                //event.getEntity().getInventory().add(PatchouliAPI.get().getBookStack(new ResourceLocation("rankine:rankine_journal")));
            }
        }
    }
}
