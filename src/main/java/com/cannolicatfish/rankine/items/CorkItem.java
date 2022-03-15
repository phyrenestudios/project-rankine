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
    public ActionResultType useOn(ItemUseContext context) {
        ActionResultType actionresulttype = this.place(new BlockItemUseContext(context));
        return !actionresulttype.consumesAction() && this.isEdible() ? this.use(context.getLevel(), context.getPlayer(), context.getHand()).getResult() : actionresulttype;
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #onItemUse}.
     */
    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        BlockRayTraceResult blockraytraceresult = getPlayerPOVHitResult(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (!worldIn.getFluidState(blockraytraceresult.getBlockPos()).isEmpty()) {
            ActionResultType actionresulttype = super.useOn(new ItemUseContext(playerIn, handIn, blockraytraceresult));
            return new ActionResult<>(actionresulttype, playerIn.getItemInHand(handIn));
        } else {
            return super.use(worldIn,playerIn,handIn);
        }

    }

}
