package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.blocks.AntimatterBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
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

        if (stack.getTag() == null && stack.getTag().getInt("filled") == 0) {
            if (worldIn.getBlockState(pos).matchesBlock(RankineBlocks.ANTIMATTER.get())) {
                context.getItem().getOrCreateTag().putInt("filled", 1);
                worldIn.removeBlock(pos, false);
                playerIn.playSound(RankineSoundEvents.PENNING_TRAP_ABSORB.get(), 1.0F, 1.0F);
                return ActionResultType.SUCCESS;
            }
        } else if (stack.getTag() != null && stack.getTag().getInt("filled") != 0) {
            if (worldIn.getBlockState(pos).matchesBlock(Blocks.DRAGON_EGG) && worldIn.getBlockState(pos.up()).isAir() && !worldIn.isRemote) {
                worldIn.setBlockState(pos.up(),Blocks.DRAGON_EGG.getDefaultState(),3);
                stack.getTag().putInt("filled", 0);
                return ActionResultType.SUCCESS;
            }
            AntimatterBlock.explode(worldIn,pos);
            stack.getTag().putInt("filled", 0);
            return ActionResultType.SUCCESS;
        }
        return super.onItemUse(context);
    }

}
