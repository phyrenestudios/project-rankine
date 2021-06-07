package com.cannolicatfish.rankine.items;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockRayTraceResult;
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
        if (Minecraft.getInstance().objectMouseOver != null && context.getPlayer() != null) {
            ActionResultType actionresulttype = ((BlockItem) Items.COBBLESTONE).tryPlace(new BlockItemUseContext(
                    new ItemUseContext(context.getWorld(),context.getPlayer(),context.getHand(),new ItemStack(Items.COBBLESTONE),(BlockRayTraceResult) Minecraft.getInstance().objectMouseOver)));
            if (context.getItem().getDamage() < context.getItem().getMaxDamage()) {
                context.getItem().damageItem(1,context.getPlayer(),(p_220040_1_) -> {
                    p_220040_1_.sendBreakAnimation(context.getHand());
                });
            }
            return !actionresulttype.isSuccessOrConsume() && this.isFood() ? this.onItemRightClick(context.getWorld(), context.getPlayer(), context.getHand()).getType() : actionresulttype;
        } else {
            return ActionResultType.PASS;
        }
    }
}
