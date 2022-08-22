package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.blocks.beehiveoven.BeehiveOvenTile;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class BuildingToolItem extends Item {
    private int maxModes = 8;
    public BuildingToolItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level levelIn, Player playerIn, InteractionHand handIn) {

        if (playerIn.isShiftKeyDown()) {
            ItemStack heldItem = playerIn.getItemInHand(handIn);
            int mode = getBuildingMode(heldItem);
            heldItem.getOrCreateTag().putShort("buildingMode", (short) ((mode + 1) % maxModes));
            playerIn.displayClientMessage(new TranslatableComponent("item.rankine.building_tool.message", (mode + 1) % maxModes).withStyle(ChatFormatting.WHITE), true);
        }
        return super.use(levelIn, playerIn, handIn);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level levelIn = context.getLevel();
        BlockPos posIn = context.getClickedPos();

        if (levelIn.getBlockState(posIn).is(RankineBlocks.BEEHIVE_OVEN_PIT.get())) {
            Player playerIn = context.getPlayer();
            if (playerIn != null) {
                ItemStack offhandItem = playerIn.getOffhandItem();
                if (offhandItem.is(RankineItems.REFRACTORY_BRICKS.get()) || offhandItem.is(RankineItems.HIGH_REFRACTORY_BRICKS.get()) || offhandItem.is(RankineItems.ULTRA_HIGH_REFRACTORY_BRICKS.get())) {
                    for (BlockPos b : BeehiveOvenTile.ovenStructure(posIn)) {
                        if (!levelIn.getBlockState(b).is(((BlockItem) offhandItem.getItem()).getBlock())) {
                            levelIn.destroyBlock(b, true);
                            levelIn.setBlockAndUpdate(b, ((BlockItem) offhandItem.getItem()).getBlock().defaultBlockState());
                            offhandItem.shrink(1);
                            return InteractionResult.sidedSuccess(levelIn.isClientSide);
                        }
                    }
                }
            }
        }


        return super.useOn(context);
    }

    public static int getBuildingMode(ItemStack stack) {
        if (stack.hasTag()) {
            return stack.getTag().getShort("buildingMode");
        }
        return 0;
    }


}
