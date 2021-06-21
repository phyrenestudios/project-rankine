package com.cannolicatfish.rankine.items;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CobblingTotemItem extends Item {
    public CobblingTotemItem(Properties properties) {
        super(properties);
    }


    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        stack.setDamage(stack.getMaxDamage()-1);
        super.onCreated(stack, worldIn, playerIn);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World worldIn = context.getWorld();
        BlockPos pos = context.getPos();
        Direction opp = context.getFace();
        if (context.getPlayer() != null && context.getItem().getDamage() < context.getItem().getMaxDamage() - 1) {
            worldIn.setBlockState(pos.offset(opp), Blocks.COBBLESTONE.getDefaultState());
            if (context.getItem().getDamage() < context.getItem().getMaxDamage()) {
                context.getItem().damageItem(1,context.getPlayer(),(p_220040_1_) -> {
                    p_220040_1_.sendBreakAnimation(context.getHand());
                });
            }
            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.PASS;
        }
    }
}
