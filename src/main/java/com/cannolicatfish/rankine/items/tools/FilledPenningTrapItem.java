package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.blocks.AntimatterBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class FilledPenningTrapItem extends Item {

    public FilledPenningTrapItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level worldIn = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player playerIn = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        if (playerIn != null) {
            if (worldIn.getBlockState(pos).is(Blocks.DRAGON_EGG) && worldIn.getBlockState(pos.above()).isAir() && !worldIn.isClientSide) {
                worldIn.setBlock(pos.above(), Blocks.DRAGON_EGG.defaultBlockState(), 3);
                playerIn.addItem(new ItemStack(RankineItems.FILLED_PENNING_TRAP.get()));
                stack.shrink(1);
                return InteractionResult.SUCCESS;
            } else if (worldIn.getBlockState(pos.offset(context.getClickedFace().getNormal())).isAir()) {
                worldIn.setBlock(pos.offset(context.getClickedFace().getNormal()), RankineBlocks.ANTIMATTER.get().defaultBlockState(), 3);
                playerIn.addItem(new ItemStack(RankineItems.PENNING_TRAP.get()));
                stack.shrink(1);
                return InteractionResult.SUCCESS;
            }

        }
        return super.useOn(context);
    }

}
