package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.VanillaIntegration;
import net.minecraft.util.Hand;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.world.BlockEvent;

public class BlockToolInteractHandler {
    public static void onToolUse( BlockEvent.BlockToolInteractEvent event) {
        ForgeConfigSpec.BooleanValue configSpec = VanillaIntegration.DISABLED_ITEMS.get(event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem());
        if (configSpec != null && configSpec.get()) {
            event.setCanceled(true);
        }
    }
}
