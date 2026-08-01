package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.VanillaIntegration;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.world.BlockEvent;

public class BlockToolModificationHandler {
    public static void onToolUse( BlockEvent.BlockToolModificationEvent event) {
        if (event.getContext() != null) {
            ModConfigSpec.BooleanValue configSpec = VanillaIntegration.DISABLED_ITEMS.get(event.getContext().getItemInHand().getItem());
            if (configSpec != null && configSpec.get()) {
                event.setCanceled(true);
            }
        }

    }
}
