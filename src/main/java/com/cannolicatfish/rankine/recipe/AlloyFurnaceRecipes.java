package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.blocks.ModBlocks;
import com.cannolicatfish.rankine.items.ModItems;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import com.google.common.collect.Table;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Map;

public class AlloyFurnaceRecipes {
    private static final AlloyFurnaceRecipes INSTANCE = new AlloyFurnaceRecipes();
    private final Table<ItemStack, ItemStack, ItemStack> smeltingList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
    private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

    public static AlloyFurnaceRecipes getInstance() {
        return INSTANCE;
    }

    private AlloyFurnaceRecipes() {
        addAlloyRecipe(new ItemStack(ModItems.COPPER_INGOT, 8), new ItemStack(ModItems.TIN_INGOT, 2), new ItemStack(ModItems.BRONZE_ALLOY, 8), 5.0F);
        addAlloyRecipe(new ItemStack(ModItems.COPPER_INGOT, 7), new ItemStack(ModItems.ZINC_INGOT, 3), new ItemStack(ModItems.BRASS_ALLOY, 8), 5.0F);

    }

    public void addAlloyRecipe(ItemStack input1, ItemStack input2, ItemStack result, float experience) {
        if (getAlloyResult(input1, input2) != ItemStack.EMPTY) return;
        this.smeltingList.put(input1, input2, result);
        this.experienceList.put(result, Float.valueOf(experience));
    }

    public ItemStack getAlloyResult(ItemStack input1, ItemStack input2) {
/*
        for(Entry<ItemStack, Map<ItemStack, ItemStack>> entry : this.smeltingList.columnMap().entrySet())
        {
            if(this.compareItemStacks(input1, (ItemStack)entry.getKey()))
            {
                for(Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet())
                {
                    if(this.compareItemStacks(input2, (ItemStack)ent.getKey()))
                    {
                        //THIS IS THE PROBLEM SOMEHOW
                        System.out.println("AlloyResult Value");
                        System.out.println((ItemStack)ent.getValue());
                        return (ItemStack)ent.getValue();
                    }
                }
            }
        }
        */
        if ((input1.getItem() == ModItems.COPPER_INGOT && input1.getCount() >= 8 && input2.getItem() == ModItems.TIN_INGOT && input2.getCount() >= 2) || (input2.getItem() == ModItems.COPPER_INGOT && input2.getCount() >= 8 && input1.getItem() == ModItems.TIN_INGOT && input1.getCount() >= 2)) {
            return new ItemStack(ModItems.BRONZE_ALLOY, 8);
        }
        if ((input1.getItem() == ModItems.COPPER_INGOT && input1.getCount() >= 7 && input2.getItem() == ModItems.ZINC_INGOT && input2.getCount() >= 3) || (input2.getItem() == ModItems.COPPER_INGOT && input2.getCount() >= 7 && input1.getItem() == ModItems.ZINC_INGOT && input1.getCount() >= 3)) {
            return new ItemStack(ModItems.BRASS_ALLOY, 8);
        }
        if ((input1.getItem() == Items.SAND && input1.getCount() >= 6 && input2.getItem() == Items.QUARTZ && input2.getCount() >= 4) || (input2.getItem() == Items.SAND && input2.getCount() >= 6 && input1.getItem() == Items.QUARTZ && input1.getCount() >= 4)) {
            return new ItemStack(Items.GLASS,8);
        }

        if ((input1.getItem() == ModItems.PIG_IRON_INGOT && input1.getCount() >= 9 && input2.getItem() == ModItems.COKE) || (input2.getItem() == ModItems.PIG_IRON_INGOT && input2.getCount() >= 9 && input1.getItem() == ModItems.COKE)) {
            return new ItemStack(ModItems.CAST_IRON_INGOT,8);
        }
        return ItemStack.EMPTY;
    }

    public int[] returnStackAmount(ItemStack input1, ItemStack input2)
    {
        int ar[] = new int[3];
        if (input1.getItem() == ModItems.COPPER_INGOT && input2.getItem() == ModItems.TIN_INGOT)
        {
            ar[0] = 8;
            ar[1] = 2;
            ar[2] = 8;
            return ar;
        }
        if (input1.getItem() == ModItems.TIN_INGOT && input2.getItem() == ModItems.COPPER_INGOT)
        {
            ar[0] = 2;
            ar[1] = 8;
            ar[2] = 8;
            return ar;
        }
        if (input1.getItem() == ModItems.COPPER_INGOT && input2.getItem() == ModItems.ZINC_INGOT)
        {
            ar[0] = 7;
            ar[1] = 3;
            ar[2] = 8;
            return ar;
        }
        if (input1.getItem() == ModItems.ZINC_INGOT && input2.getItem() == ModItems.COPPER_INGOT)
        {
            ar[0] = 3;
            ar[1] = 7;
            ar[2] = 8;
            return ar;
        }
        if (input1.getItem() == Items.SAND && input2.getItem() == Items.QUARTZ)
        {
            ar[0] = 6;
            ar[1] = 4;
            ar[2] = 8;
            return ar;
        }
        if (input1.getItem() == Items.QUARTZ && input2.getItem() == Items.SAND)
        {
            ar[0] = 4;
            ar[1] = 6;
            ar[2] = 8;
            return ar;
        }
        if (input1.getItem() == ModItems.PIG_IRON_INGOT && input2.getItem() == ModItems.COKE)
        {
            ar[0] = 9;
            ar[1] = 1;
            ar[2] = 8;
            return ar;
        }
        if (input1.getItem() == ModItems.COKE && input2.getItem() == ModItems.PIG_IRON_INGOT)
        {
            ar[0] = 1;
            ar[1] = 9;
            ar[2] = 8;
            return ar;
        }
        ar[0] = 1;
        ar[1] = 1;
        ar[2] = 1;
        return ar;
    }

    public Table<ItemStack, ItemStack, ItemStack> getDualSmeltingList()
    {
        return this.smeltingList;
    }

    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
/*
        System.out.println(stack2.getItem());
        System.out.println(stack1.getItem());
        System.out.println("compare returns:");
        System.out.println(stack2.getItem() == stack1.getItem());
*/
        return stack2.getItem() == stack1.getItem();
    }
}
