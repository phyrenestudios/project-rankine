package com.cannolicatfish.rankine.items.pendants;


import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class HastePendantItem extends Item{
    public HastePendantItem(Properties properties) {
        super(properties);
        MinecraftForge.EVENT_BUS.addListener(this::onBreakSpeed);
    }

    public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (event.getPlayer().getHeldItemOffhand().getItem() == this) {
            event.setNewSpeed(event.getNewSpeed() + 3);
        }
    }

}
