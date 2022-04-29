package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.items.totems.SofteningTotemItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraftforge.event.world.BlockEvent;

public class CropTrampleHandler {
    public static void onCropTrample( BlockEvent.FarmlandTrampleEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            if (player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof SofteningTotemItem || player.getHeldItem(Hand.OFF_HAND).getItem() instanceof SofteningTotemItem) {
                event.setCanceled(true);
            }
        }
    }
}
