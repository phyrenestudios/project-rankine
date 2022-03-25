package com.cannolicatfish.rankine.items;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;

public class CorkItem extends BlockItem {

    public CorkItem(Block blockIn, Item.Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult actionresulttype = this.place(new BlockPlaceContext(context));
        return !actionresulttype.consumesAction() && this.isEdible() ? this.use(context.getLevel(), context.getPlayer(), context.getHand()).getResult() : actionresulttype;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        BlockHitResult blockraytraceresult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.SOURCE_ONLY);
        if (!worldIn.getFluidState(blockraytraceresult.getBlockPos()).isEmpty()) {
            InteractionResult actionresulttype = super.useOn(new UseOnContext(playerIn, handIn, blockraytraceresult));
            return new InteractionResultHolder<>(actionresulttype, playerIn.getItemInHand(handIn));
        } else {
            return super.use(worldIn,playerIn,handIn);
        }

    }

}
