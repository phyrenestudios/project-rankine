package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.items.alloys.AlloyPickaxeItem;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class HarvestCheckHandler {
    public static void onBlockHarvest( PlayerEvent.HarvestCheck event) {
        Material mat = event.getTargetBlock().getMaterial();
        boolean flag = mat == Material.ROCK || mat == Material.IRON || mat == Material.ANVIL;
        if (flag && (event.getPlayer().getHeldItemMainhand().getItem() instanceof AlloyPickaxeItem || event.getPlayer().getHeldItemMainhand().getItem() instanceof AlloyPickaxeItem)) {
            ItemStack stack = event.getPlayer().getHeldItemMainhand();
            Item item = event.getPlayer().getHeldItemMainhand().getItem();
            boolean bool = item.canHarvestBlock(stack,event.getTargetBlock());
            event.setCanHarvest(bool);
        }
    }
}
