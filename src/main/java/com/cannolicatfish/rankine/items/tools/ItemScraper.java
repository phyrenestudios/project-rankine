package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.items.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class ItemScraper extends Item {
    public ItemScraper(Properties builder) {
        super(builder);
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity playerentity = context.getPlayer();
        IWorld iworld = context.getWorld();
        BlockPos blockpos = context.getPos();
        BlockState blockstate = iworld.getBlockState(blockpos);
        if (isClayBlock(blockstate)) {
            iworld.playSound(playerentity, blockpos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
            iworld.removeBlock(blockpos,false);
            if (playerentity != null) {
                playerentity.addItemStackToInventory(new ItemStack(ModItems.CLAY_BRICKS,4));
                context.getItem().damageItem(1, playerentity, (p_219998_1_) -> {
                    p_219998_1_.sendBreakAnimation(context.getHand());
                });
            }
            return ActionResultType.SUCCESS;
        }
        else
        {
            return ActionResultType.FAIL;
        }
    }

    public static boolean isClayBlock(BlockState state) {
        return state.getBlock() == Blocks.CLAY;
    }


}
