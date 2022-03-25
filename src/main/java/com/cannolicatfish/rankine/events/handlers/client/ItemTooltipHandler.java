package com.cannolicatfish.rankine.events.handlers.client;

import com.cannolicatfish.rankine.init.Config;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.util.HashMap;
import java.util.Map;

public class ItemTooltipHandler {
    private static final Map<Item,ForgeConfigSpec.BooleanValue> DISABLED_ITEMS = new HashMap<>();

    static {
        // Wood
        DISABLED_ITEMS.put(Items.WOODEN_SWORD, Config.TOOLS.DISABLE_WOODEN_SWORD);
        DISABLED_ITEMS.put(Items.WOODEN_AXE, Config.TOOLS.DISABLE_WOODEN_AXE);
        DISABLED_ITEMS.put(Items.WOODEN_SHOVEL, Config.TOOLS.DISABLE_WOODEN_SHOVEL);
        DISABLED_ITEMS.put(Items.WOODEN_PICKAXE, Config.TOOLS.DISABLE_WOODEN_PICKAXE);
        DISABLED_ITEMS.put(Items.WOODEN_HOE, Config.TOOLS.DISABLE_WOODEN_HOE);

        // Stone
        DISABLED_ITEMS.put(Items.STONE_SWORD, Config.TOOLS.DISABLE_STONE_SWORD);
        DISABLED_ITEMS.put(Items.STONE_AXE, Config.TOOLS.DISABLE_STONE_AXE);
        DISABLED_ITEMS.put(Items.STONE_SHOVEL, Config.TOOLS.DISABLE_STONE_SHOVEL);
        DISABLED_ITEMS.put(Items.STONE_PICKAXE, Config.TOOLS.DISABLE_STONE_PICKAXE);
        DISABLED_ITEMS.put(Items.STONE_HOE, Config.TOOLS.DISABLE_STONE_HOE);

        // Iron
        DISABLED_ITEMS.put(Items.IRON_SWORD, Config.TOOLS.DISABLE_IRON_SWORD);
        DISABLED_ITEMS.put(Items.IRON_AXE, Config.TOOLS.DISABLE_IRON_AXE);
        DISABLED_ITEMS.put(Items.IRON_SHOVEL, Config.TOOLS.DISABLE_IRON_SHOVEL);
        DISABLED_ITEMS.put(Items.IRON_PICKAXE, Config.TOOLS.DISABLE_IRON_PICKAXE);
        DISABLED_ITEMS.put(Items.IRON_HOE, Config.TOOLS.DISABLE_IRON_HOE);

        // Gold
        DISABLED_ITEMS.put(Items.GOLDEN_SWORD, Config.TOOLS.DISABLE_GOLDEN_SWORD);
        DISABLED_ITEMS.put(Items.GOLDEN_AXE, Config.TOOLS.DISABLE_GOLDEN_AXE);
        DISABLED_ITEMS.put(Items.GOLDEN_SHOVEL, Config.TOOLS.DISABLE_GOLDEN_SHOVEL);
        DISABLED_ITEMS.put(Items.GOLDEN_PICKAXE, Config.TOOLS.DISABLE_GOLDEN_PICKAXE);
        DISABLED_ITEMS.put(Items.GOLDEN_HOE, Config.TOOLS.DISABLE_GOLDEN_HOE);

        // Diamond
        DISABLED_ITEMS.put(Items.DIAMOND_SWORD, Config.TOOLS.DISABLE_DIAMOND_SWORD);
        DISABLED_ITEMS.put(Items.DIAMOND_AXE, Config.TOOLS.DISABLE_DIAMOND_AXE);
        DISABLED_ITEMS.put(Items.DIAMOND_SHOVEL, Config.TOOLS.DISABLE_DIAMOND_SHOVEL);
        DISABLED_ITEMS.put(Items.DIAMOND_PICKAXE, Config.TOOLS.DISABLE_DIAMOND_PICKAXE);
        DISABLED_ITEMS.put(Items.DIAMOND_HOE, Config.TOOLS.DISABLE_DIAMOND_HOE);

        // Netherite
        DISABLED_ITEMS.put(Items.NETHERITE_SWORD, Config.TOOLS.DISABLE_NETHERITE_SWORD);
        DISABLED_ITEMS.put(Items.NETHERITE_AXE, Config.TOOLS.DISABLE_NETHERITE_AXE);
        DISABLED_ITEMS.put(Items.NETHERITE_SHOVEL, Config.TOOLS.DISABLE_NETHERITE_SHOVEL);
        DISABLED_ITEMS.put(Items.NETHERITE_PICKAXE, Config.TOOLS.DISABLE_NETHERITE_PICKAXE);
        DISABLED_ITEMS.put(Items.NETHERITE_HOE, Config.TOOLS.DISABLE_NETHERITE_HOE);
    }

    private static final IFormattableTextComponent ITEM_DISABLED = new StringTextComponent("\"This tool is disabled in the " + "config.\"")
            .mergeStyle(TextFormatting.RED);

    public static void onTooltipCheck( ItemTooltipEvent event ) {
        ForgeConfigSpec.BooleanValue configSpec = DISABLED_ITEMS.get(event.getItemStack().getItem());
        if( configSpec != null && configSpec.get() ) {
            event.getToolTip().add(ITEM_DISABLED);
        }
    }
}
