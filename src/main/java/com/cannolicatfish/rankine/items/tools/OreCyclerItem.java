package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.util.ElementEquation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.World;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class OreCyclerItem extends Item {
    public OreCyclerItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World worldIn = context.getWorld();

        if (worldIn.getBlockState(context.getPos()).getBlock() instanceof RankineOreBlock)
        {
            RankineOreBlock blockIn = (RankineOreBlock) worldIn.getBlockState(context.getPos()).getBlock();
            int x = worldIn.getBlockState(context.getPos()).get(RankineOreBlock.TYPE) + 1;
            if (x >= 88)
            {
                x = 0;
            }
            worldIn.setBlockState(context.getPos(),blockIn.getDefaultState().with(RankineOreBlock.TYPE, x));
            return ActionResultType.SUCCESS;
        }


        return ActionResultType.FAIL;
    }

}
