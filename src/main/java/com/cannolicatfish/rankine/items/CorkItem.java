package com.cannolicatfish.rankine.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.World;

public class CorkItem extends BlockItem {

    public CorkItem(Block blockIn, Item.Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        ActionResultType actionresulttype = this.tryPlace(new BlockItemUseContext(context));
        return !actionresulttype.isSuccessOrConsume() && this.isFood() ? this.onItemRightClick(context.getWorld(), context.getPlayer(), context.getHand()).getType() : actionresulttype;
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #onItemUse}.
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        BlockRayTraceResult blockraytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (!worldIn.getFluidState(blockraytraceresult.getPos()).isEmpty()) {
            ActionResultType actionresulttype = super.onItemUse(new ItemUseContext(playerIn, handIn, blockraytraceresult));
            return new ActionResult<>(actionresulttype, playerIn.getHeldItem(handIn));
        } else {
            return super.onItemRightClick(worldIn,playerIn,handIn);
        }

    }

}
