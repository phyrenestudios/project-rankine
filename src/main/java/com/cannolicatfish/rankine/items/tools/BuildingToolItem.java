package com.cannolicatfish.rankine.items.tools;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item.Properties;

public class BuildingToolItem extends Item {
    private int maxModes = 8;
    public BuildingToolItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {

        if (playerIn.isShiftKeyDown()) {
            ItemStack heldItem = playerIn.getItemInHand(handIn);
            int mode = getBuildingMode(heldItem);
            heldItem.getOrCreateTag().putShort("buildingMode", (short) ((mode + 1) % maxModes));
            playerIn.displayClientMessage(new TranslatableComponent("item.rankine.building_tool.message", (mode + 1) % maxModes).withStyle(ChatFormatting.WHITE), true);
        }
        return super.use(worldIn, playerIn, handIn);
    }

    public static int getBuildingMode(ItemStack stack) {
        if (stack.hasTag()) {
            return stack.getTag().getShort("buildingMode");
        }
        return 0;
    }


}
