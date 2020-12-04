package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.blocks.RankineOre;
import com.cannolicatfish.rankine.blocks.RankineStone;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemOreCycler extends Item {
    public ItemOreCycler(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World worldIn = context.getWorld();
        if (worldIn.getBlockState(context.getPos()).getBlock() instanceof RankineOre)
        {
            RankineOre blockIn = (RankineOre) worldIn.getBlockState(context.getPos()).getBlock();
            int x = worldIn.getBlockState(context.getPos()).get(RankineOre.TYPE) + 1;
            if (x >= 35)
            {
                x = 0;
            }
            worldIn.setBlockState(context.getPos(),blockIn.getDefaultState().with(RankineOre.TYPE, x));
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }
}
