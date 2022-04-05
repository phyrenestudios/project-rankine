package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.Config;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraftforge.event.world.BlockEvent;

public class BlockToolInteractHandler {
    public static void onToolUse( BlockEvent.BlockToolInteractEvent event) {
        if (Config.TOOLS.DISABLE_WOODEN_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_SWORD) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_WOODEN_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_AXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_WOODEN_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_SHOVEL) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_WOODEN_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_PICKAXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_WOODEN_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.WOODEN_HOE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_STONE_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_SWORD) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_STONE_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_AXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_STONE_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_SHOVEL) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_STONE_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_PICKAXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_STONE_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.STONE_HOE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_IRON_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_SWORD) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_IRON_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_AXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_IRON_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_SHOVEL) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_IRON_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_PICKAXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_IRON_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_HOE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_GOLDEN_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_SWORD) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_GOLDEN_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_AXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_GOLDEN_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_SHOVEL) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_GOLDEN_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_PICKAXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_GOLDEN_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.GOLDEN_HOE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_DIAMOND_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_SWORD) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_DIAMOND_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_AXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_DIAMOND_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_SHOVEL) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_DIAMOND_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_PICKAXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_DIAMOND_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.DIAMOND_HOE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_NETHERITE_SWORD.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_SWORD) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_NETHERITE_AXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_AXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_NETHERITE_SHOVEL.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_SHOVEL) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_NETHERITE_PICKAXE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_PICKAXE) { event.setCanceled(true); }
        if (Config.TOOLS.DISABLE_NETHERITE_HOE.get() && event.getPlayer().getHeldItem(Hand.MAIN_HAND).getItem() == Items.NETHERITE_HOE) { event.setCanceled(true); }
    }
}
