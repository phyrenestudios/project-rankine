package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class AlloyTemplateItem extends Item {
    public AlloyTemplateItem(Properties properties) {
        super(properties);
    }

    public static String assembleTemplateData(World worldIn, IInventory inv, int startIndex, int endIndex)
    {
        List<ElementRecipe> elementRecipes = new ArrayList<>();
        List<Integer> amount = new ArrayList<>();
        for (int i = startIndex; i < endIndex; i++)
        {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack.isEmpty())
            {
                continue;
            }

            Inventory temp = new Inventory(stack);
            ElementRecipe elem = worldIn.getRecipeManager().getRecipe(RankineRecipeTypes.ELEMENT, temp, worldIn).orElse(null);
            if (elem != null) {
                if (elementRecipes.contains(elem)) {
                    int index = elementRecipes.indexOf(elem);
                    amount.set(index,amount.get(index) + elem.getMaterialCount(stack.getItem()) * stack.getCount());
                } else {
                    elementRecipes.add(elem);
                    amount.add(elem.getMaterialCount(stack.getItem()) * stack.getCount());
                }
            }
        }
        return "";
    }
}
