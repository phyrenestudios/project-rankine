package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.blocks.AntimatterBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineSoundEvents;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item.Properties;

public class PenningTrapItem extends Item {

    public PenningTrapItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player playerIn = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        if (stack.getTag() == null && stack.getTag().getInt("filled") == 0) {
            if (worldIn.getBlockState(pos).is(RankineBlocks.ANTIMATTER.get())) {
                context.getItemInHand().getOrCreateTag().putInt("filled", 1);
                worldIn.removeBlock(pos, false);
                playerIn.playSound(RankineSoundEvents.PENNING_TRAP_ABSORB.get(), 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        } else if (stack.getTag() != null && stack.getTag().getInt("filled") != 0) {
            if (worldIn.getBlockState(pos).is(Blocks.DRAGON_EGG) && worldIn.getBlockState(pos.above()).isAir() && !worldIn.isClientSide) {
                worldIn.setBlock(pos.above(),Blocks.DRAGON_EGG.defaultBlockState(),3);
                stack.getTag().putInt("filled", 0);
                return InteractionResult.SUCCESS;
            }
            AntimatterBlock.explode(worldIn,pos);
            stack.getTag().putInt("filled", 0);
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }

}
