package com.cannolicatfish.rankine.items.tools;

import com.cannolicatfish.rankine.blocks.RankineOreBlock;
import com.cannolicatfish.rankine.client.integration.jei.IInventoryEmpty;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.CrushingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;

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
            if (x >= 61)
            {
                x = 0;
            }
            worldIn.setBlockState(context.getPos(),blockIn.getDefaultState().with(RankineOreBlock.TYPE, x));
            return ActionResultType.SUCCESS;
        }


        return ActionResultType.FAIL;
    }

}
