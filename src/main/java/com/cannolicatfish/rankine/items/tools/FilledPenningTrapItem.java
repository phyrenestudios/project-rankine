package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.blocks.AntimatterBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineSoundEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FilledPenningTrapItem extends Item {
    public FilledPenningTrapItem(Properties properties) {
        super(properties);
    }


    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World worldIn = context.getWorld();
        BlockPos pos = context.getPos();
        PlayerEntity playerIn = context.getPlayer();
        ItemStack stack = context.getItem();
        if (playerIn != null) {
            if (worldIn.getBlockState(pos).matchesBlock(Blocks.DRAGON_EGG) && worldIn.getBlockState(pos.up()).isAir() && !worldIn.isRemote) {
                worldIn.setBlockState(pos.up(),Blocks.DRAGON_EGG.getDefaultState(),3);
                playerIn.addItemStackToInventory(new ItemStack(RankineItems.PENNING_TRAP.get()));
                stack.shrink(1);
                return ActionResultType.func_233537_a_(worldIn.isRemote);
            }
            else if (worldIn.isAirBlock(pos.offset(context.getFace()))) {
                worldIn.setBlockState(pos.offset(context.getFace()),RankineBlocks.ANTIMATTER.get().getDefaultState(),3);
                playerIn.addItemStackToInventory(new ItemStack(RankineItems.PENNING_TRAP.get()));
                stack.shrink(1);
                return ActionResultType.func_233537_a_(worldIn.isRemote);
            }

        }
        return super.onItemUse(context);
    }

}
