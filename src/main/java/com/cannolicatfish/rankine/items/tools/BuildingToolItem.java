package com.cannolicatfish.rankine.items.tools;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class BuildingToolItem extends Item {
    private int maxModes = 8;
    public BuildingToolItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {

        if (playerIn.isShiftKeyDown()) {
            ItemStack heldItem = playerIn.getItemInHand(handIn);
            int mode = getBuildingMode(heldItem);
            heldItem.getOrCreateTag().putShort("buildingMode", (short) ((mode + 1) % maxModes));
            playerIn.displayClientMessage(new TranslationTextComponent("item.rankine.building_tool.message", (mode + 1) % maxModes).withStyle(TextFormatting.WHITE), true);
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
