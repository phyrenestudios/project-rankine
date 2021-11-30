package com.cannolicatfish.rankine.items.tools;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class BuildingToolItem extends Item {

    public BuildingToolItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        if (playerIn.isSneaking()) {
            ItemStack heldItem = playerIn.getHeldItem(handIn);
            int mode = getBuildingMode(heldItem);
            heldItem.getOrCreateTag().putShort("buildingMode", (short) ((mode + 1) % 4));
            playerIn.sendStatusMessage(new TranslationTextComponent("item.rankine.building_tool.message", (mode + 1) % 4).mergeStyle(TextFormatting.GRAY), true);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public static int getBuildingMode(ItemStack stack) {
        if (stack.hasTag()) {
            return stack.getTag().getShort("buildingMode");
        }
        return 0;
    }


}
