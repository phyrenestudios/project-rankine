package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.blocks.beehiveoven.BeehiveOvenTile;
import com.cannolicatfish.rankine.blocks.buildingmodes.BuildingModeBlock;
import com.cannolicatfish.rankine.blocks.evaporationtower.EvaporationTowerTile;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item.Properties;

public class BuildingToolItem extends Item {
    public BuildingToolItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level levelIn, Player playerIn, InteractionHand handIn) {

        if (playerIn.isShiftKeyDown()) {
            ItemStack heldItem = playerIn.getItemInHand(handIn);
            int mode = getBuildingMode(heldItem);
            int maxModes = 1;
            if (playerIn.getMainHandItem().getItem() instanceof BlockItem && ((BlockItem) playerIn.getMainHandItem().getItem()).getBlock() instanceof BuildingModeBlock) {
                maxModes = ((BuildingModeBlock) ((BlockItem) playerIn.getMainHandItem().getItem()).getBlock()).getMaxStyles();
            }
            heldItem.getOrCreateTag().putShort("buildingMode", (short) (mode + 1) % maxModes == 0 ? (short) maxModes : (short) ((mode + 1) % maxModes));
            playerIn.displayClientMessage(new TranslatableComponent("item.rankine.building_tool.message", (mode + 1) % maxModes == 0 ? maxModes : ((mode + 1) % maxModes)).withStyle(ChatFormatting.WHITE), true);
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
                if (offhandItem.is(RankineBlocks.REFRACTORY_BRICKS.getBricksBlock().asItem()) || offhandItem.is(RankineBlocks.HIGH_REFRACTORY_BRICKS.getBricksBlock().asItem()) || offhandItem.is(RankineBlocks.ULTRA_HIGH_REFRACTORY_BRICKS.getBricksBlock().asItem())) {
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
        } else if (levelIn.getBlockState(posIn).is(RankineBlocks.EVAPORATION_TOWER.get())) {
            Player playerIn = context.getPlayer();
            if (playerIn != null) {
                ItemStack offhandItem = playerIn.getOffhandItem();
                if (offhandItem.is(RankineTags.Items.SHEETMETAL)) {
                    for (BlockPos b : ((EvaporationTowerTile) levelIn.getBlockEntity(posIn)).wallStructure(posIn)) {
                        if (!levelIn.getBlockState(b).is(RankineTags.Blocks.SHEETMETAL)) {
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
