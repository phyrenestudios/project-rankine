package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.VanillaIntegration;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.world.BlockEvent;

public class BlockToolModificationHandler {
    public static void onToolUse( BlockEvent.BlockToolModificationEvent event) {
        ForgeConfigSpec.BooleanValue configSpec = VanillaIntegration.DISABLED_ITEMS.get(event.getPlayer().getMainHandItem().getItem());
        if (configSpec != null && configSpec.get()) {
            event.setCanceled(true);
        }
    }
}
