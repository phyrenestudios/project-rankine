package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.VanillaIntegration;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;

public class FurnaceFuelBurnTimeHandler {
    public static void updateFuelValues(FurnaceFuelBurnTimeEvent event) {
        Item Fuel = event.getItemStack().getItem();
        if (VanillaIntegration.fuelValueMap.containsKey(Fuel)) {
            event.setBurnTime(VanillaIntegration.fuelValueMap.get(Fuel));
        }
    }
}
