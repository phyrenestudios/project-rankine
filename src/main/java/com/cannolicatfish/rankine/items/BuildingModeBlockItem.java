package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.blocks.buildingmodes.BuildingModeBlock;
import com.cannolicatfish.rankine.init.RankineDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class BuildingModeBlockItem extends BlockItem {

    public BuildingModeBlockItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    public int getBuildingMode(ItemStack stack) {
        // Old NBT behavior: absent tag -> 1 (was stack.getTag().getShort("building_mode")).
        return stack.getOrDefault(RankineDataComponents.BUILDING_MODE.get(), 1);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level levelIn, Player playerIn, InteractionHand handIn) {

        if (playerIn.isShiftKeyDown()) {
            ItemStack heldItem = playerIn.getItemInHand(handIn);
            int mode = getBuildingMode(heldItem);
            int maxModes = ((BuildingModeBlock) ((BlockItem) playerIn.getMainHandItem().getItem()).getBlock()).getMaxStyles();
            heldItem.set(RankineDataComponents.BUILDING_MODE.get(), (mode + 1) % maxModes == 0 ? maxModes : (mode + 1) % maxModes);
            playerIn.displayClientMessage(Component.translatable("item.rankine.building_tool.message", (mode + 1) % maxModes == 0 ? maxModes : ((mode + 1) % maxModes)).withStyle(ChatFormatting.WHITE), true);
        }
        return super.use(levelIn, playerIn, handIn);
    }

}
