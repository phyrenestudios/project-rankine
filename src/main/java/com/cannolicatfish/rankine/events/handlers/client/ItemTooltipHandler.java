package com.cannolicatfish.rankine.events.handlers.client;

import com.cannolicatfish.rankine.init.VanillaIntegration;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ItemTooltipHandler {

    private static final IFormattableTextComponent ITEM_DISABLED = new StringTextComponent("\"This tool is disabled in the " + "config.\"")
            .mergeStyle(TextFormatting.RED);

    public static void onTooltipCheck( ItemTooltipEvent event ) {
        ForgeConfigSpec.BooleanValue configSpec = VanillaIntegration.DISABLED_ITEMS.get(event.getItemStack().getItem());
        if (configSpec != null && configSpec.get()) {
            event.getToolTip().add(ITEM_DISABLED);
        }
    }
}
