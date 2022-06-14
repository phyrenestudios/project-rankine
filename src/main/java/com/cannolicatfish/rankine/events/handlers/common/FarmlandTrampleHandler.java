package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.items.totems.SofteningTotemItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.world.BlockEvent;

public class FarmlandTrampleHandler {
    public static void onCropTrample(BlockEvent.FarmlandTrampleEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof SofteningTotemItem || player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof SofteningTotemItem) {
                event.setCanceled(true);
            }
        }
    }
}
