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

public class PenningTrapItem extends Item {

    public PenningTrapItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World worldIn = context.getWorld();
        BlockPos pos = context.getPos();
        PlayerEntity playerIn = context.getPlayer();
        ItemStack stack = context.getItem();

        if (playerIn != null && worldIn.getBlockState(pos).matchesBlock(RankineBlocks.ANTIMATTER.get())) {
            playerIn.addItemStackToInventory(new ItemStack(RankineItems.FILLED_PENNING_TRAP.get()));
            worldIn.removeBlock(pos, false);
            playerIn.playSound(RankineSoundEvents.PENNING_TRAP_ABSORB.get(), 1.0F, 1.0F);
            stack.shrink(1);
            return ActionResultType.func_233537_a_(worldIn.isRemote);
        }
        return super.onItemUse(context);
    }

}
