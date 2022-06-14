package com.cannolicatfish.rankine.events.handlers.client;

import com.cannolicatfish.rankine.init.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ItemTooltipHandler {
    public static void onTooltipCheck(ItemTooltipEvent event) {
        if (Config.TOOLS.DISABLE_WOODEN_SWORD.get() && event.getItemStack().getItem() == Items.WOODEN_SWORD) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_WOODEN_AXE.get() && event.getItemStack().getItem() == Items.WOODEN_AXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_WOODEN_SHOVEL.get() && event.getItemStack().getItem() == Items.WOODEN_SHOVEL) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_WOODEN_PICKAXE.get() && event.getItemStack().getItem() == Items.WOODEN_PICKAXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_WOODEN_HOE.get() && event.getItemStack().getItem() == Items.WOODEN_HOE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_STONE_SWORD.get() && event.getItemStack().getItem() == Items.STONE_SWORD) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_STONE_AXE.get() && event.getItemStack().getItem() == Items.STONE_AXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_STONE_SHOVEL.get() && event.getItemStack().getItem() == Items.STONE_SHOVEL) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_STONE_PICKAXE.get() && event.getItemStack().getItem() == Items.STONE_PICKAXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_STONE_HOE.get() && event.getItemStack().getItem() == Items.STONE_HOE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_IRON_SWORD.get() && event.getItemStack().getItem() == Items.IRON_SWORD) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_IRON_AXE.get() && event.getItemStack().getItem() == Items.IRON_AXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_IRON_SHOVEL.get() && event.getItemStack().getItem() == Items.IRON_SHOVEL) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_IRON_PICKAXE.get() && event.getItemStack().getItem() == Items.IRON_PICKAXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_IRON_HOE.get() && event.getItemStack().getItem() == Items.IRON_HOE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_GOLDEN_SWORD.get() && event.getItemStack().getItem() == Items.GOLDEN_SWORD) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_GOLDEN_AXE.get() && event.getItemStack().getItem() == Items.GOLDEN_AXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_GOLDEN_SHOVEL.get() && event.getItemStack().getItem() == Items.GOLDEN_SHOVEL) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_GOLDEN_PICKAXE.get() && event.getItemStack().getItem() == Items.GOLDEN_PICKAXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_GOLDEN_HOE.get() && event.getItemStack().getItem() == Items.GOLDEN_HOE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_DIAMOND_SWORD.get() && event.getItemStack().getItem() == Items.DIAMOND_SWORD) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_DIAMOND_AXE.get() && event.getItemStack().getItem() == Items.DIAMOND_AXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_DIAMOND_SHOVEL.get() && event.getItemStack().getItem() == Items.DIAMOND_SHOVEL) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_DIAMOND_PICKAXE.get() && event.getItemStack().getItem() == Items.DIAMOND_PICKAXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_DIAMOND_HOE.get() && event.getItemStack().getItem() == Items.DIAMOND_HOE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_NETHERITE_SWORD.get() && event.getItemStack().getItem() == Items.NETHERITE_SWORD) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_NETHERITE_AXE.get() && event.getItemStack().getItem() == Items.NETHERITE_AXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_NETHERITE_SHOVEL.get() && event.getItemStack().getItem() == Items.NETHERITE_SHOVEL) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_NETHERITE_PICKAXE.get() && event.getItemStack().getItem() == Items.NETHERITE_PICKAXE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
        if (Config.TOOLS.DISABLE_NETHERITE_HOE.get() && event.getItemStack().getItem() == Items.NETHERITE_HOE) { event.getToolTip().add(new TextComponent("This tool is disabled in the config.").withStyle(ChatFormatting.RED)); }
    }
}
