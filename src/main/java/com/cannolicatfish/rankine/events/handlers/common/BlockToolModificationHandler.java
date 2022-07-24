package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.VanillaIntegration;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.world.BlockEvent;

public class BlockToolModificationHandler {
    public static void onToolUse( BlockEvent.BlockToolModificationEvent event) {
        if (event.getContext() != null) {
            ForgeConfigSpec.BooleanValue configSpec = VanillaIntegration.DISABLED_ITEMS.get(event.getContext().getItemInHand().getItem());
            if (configSpec != null && configSpec.get()) {
                event.setCanceled(true);
            }
        }

    }
}
