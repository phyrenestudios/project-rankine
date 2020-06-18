package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.ModItems;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import javafx.util.Pair;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AlloyingRecipes {
    private static final AlloyingRecipes INSTANCE = new AlloyingRecipes();
    private final Table<ItemStack, ItemStack, ItemStack> smeltingList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
    private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

    public static AlloyingRecipes getInstance() {
        return INSTANCE;
    }

    private AlloyingRecipes() {

    }

    public ItemStack getFurnaceResult(ItemStack input1)
    {
        return ItemStack.EMPTY;
    }


    public Pair<ItemStack,int[]> getAlloyResult(ItemStack input1, ItemStack input2, ItemStack input3) {
        if (input3.isEmpty())
        {
            return new Pair<>(AlloyFurnaceRecipes.getInstance().getAlloyResult(input1,input2),AlloyFurnaceRecipes.getInstance().returnStackAmount(input1,input2));
        }
        List<Item> inputList = Arrays.asList(input1.getItem(),input2.getItem(),input3.getItem());
        if (inputList.stream().distinct().count() < 3)
        {
            int[] ar = new int[3];
            ar[0] = 1;
            ar[1] = 1;
            ar[2] = 1;
            return new Pair<>(ItemStack.EMPTY, ar);
        }
        List<Integer> inputValList = Arrays.asList(input1.getCount(),input2.getCount(),input3.getCount());
        if (inputList.contains(ModItems.COPPER_INGOT) && inputList.contains(ModItems.TIN_INGOT)) {
            int x1 = inputList.indexOf(ModItems.COPPER_INGOT);
            int x2 = inputList.indexOf(ModItems.TIN_INGOT);
            int x3;
            if (x1 == 0 && x2 == 1 || x2 == 0 && x1 == 1)
            {
                x3 = 2;
            }
            else if (x1 == 2 && x2 == 0 || x2 == 2 && x1 == 0)
            {
                x3 = 1;
            }
            else
            {
                x3 = 0;
            }
            if (inputList.get(x3) == ModItems.ALUMINUM_INGOT || inputList.get(x3) == ModItems.MANGANESE_INGOT || inputList.get(x3) == ModItems.NICKEL_INGOT || inputList.get(x3) == ModItems.ZINC_INGOT) {
                if (inputValList.get(x1) >= 6 && inputValList.get(x2) <= 2 && inputValList.get(x1) + inputValList.get(x2) + inputValList.get(x3) == 10) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new Pair<>(new ItemStack(ModItems.BRONZE_ALLOY, 8),ar);
                }
            }
        }
        else if (inputList.contains(ModItems.COPPER_INGOT) && inputList.contains(ModItems.ZINC_INGOT)) {
            int x1 = inputList.indexOf(ModItems.COPPER_INGOT);
            int x2 = inputList.indexOf(ModItems.ZINC_INGOT);
            int x3;
            if (x1 == 0 && x2 == 1 || x2 == 0 && x1 == 1)
            {
                x3 = 2;
            }
            else if (x1 == 2 && x2 == 0 || x2 == 2 && x1 == 0)
            {
                x3 = 1;
            }
            else
            {
                x3 = 0;
            }
            if (inputList.get(x3) == ModItems.TIN_INGOT || inputList.get(x3) == ModItems.ALUMINUM_INGOT || inputList.get(x3) == ModItems.NICKEL_INGOT) {
                if (inputValList.get(x1) >= 5 && inputValList.get(x2) >= 3 && inputValList.get(x3) <= 1 && inputValList.get(x1) + inputValList.get(x2) + inputValList.get(x3) == 10) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new Pair<>(new ItemStack(ModItems.BRASS_ALLOY, 8),ar);
                }
            }
        }
        else if (inputList.contains(ModItems.PIG_IRON_INGOT) && (inputList.contains(ModItems.COKE))) {
            int x1 = inputList.indexOf(ModItems.PIG_IRON_INGOT);
            int x2 = inputList.indexOf(ModItems.COKE);
            int x3;
            if (x1 == 0 && x2 == 1 || x2 == 0 && x1 == 1)
            {
                x3 = 2;
            }
            else if (x1 == 2 && x2 == 0 || x2 == 2 && x1 == 0)
            {
                x3 = 1;
            }
            else
            {
                x3 = 0;
            }
            if (inputList.get(x3) == ModItems.SILICON || inputList.get(x3) == ModItems.MANGANESE_INGOT || inputList.get(x3) == ModItems.NICKEL_INGOT) {
                if (inputValList.get(x1) >= 7 && inputValList.get(x2) <= 1 && inputValList.get(x3) <= 2 && inputValList.get(x1) + inputValList.get(x2) + inputValList.get(x3) == 10) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new Pair<>(new ItemStack(ModItems.CAST_IRON_INGOT, 8),ar);
                }
            }
        }
        else if (inputList.contains(ModItems.COPPER_INGOT) && inputList.contains(ModItems.NICKEL_INGOT)) {
            int x1 = inputList.indexOf(ModItems.COPPER_INGOT);
            int x2 = inputList.indexOf(ModItems.NICKEL_INGOT);
            int x3;
            if (x1 == 0 && x2 == 1 || x2 == 0 && x1 == 1)
            {
                x3 = 2;
            }
            else if (x1 == 2 && x2 == 0 || x2 == 2 && x1 == 0)
            {
                x3 = 1;
            }
            else
            {
                x3 = 0;
            }
            if (inputList.get(x3) == Items.IRON_INGOT || inputList.get(x3) == ModItems.MANGANESE_INGOT) {
                if (inputValList.get(x1) >= 6 && inputValList.get(x2) <= 3 && inputValList.get(x3) <= 1 && inputValList.get(x1) + inputValList.get(x2) + inputValList.get(x3) == 10) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new Pair<>(new ItemStack(ModItems.CUPRONICKEL_ALLOY, 8),ar);
                }
            }
        }
        else if (inputList.contains(ModItems.ALUMINUM_INGOT) && inputList.contains(ModItems.COPPER_INGOT)) {
            int x1 = inputList.indexOf(ModItems.ALUMINUM_INGOT);
            int x2 = inputList.indexOf(ModItems.COPPER_INGOT);
            int x3;
            if (x1 == 0 && x2 == 1 || x2 == 0 && x1 == 1)
            {
                x3 = 2;
            }
            else if (x1 == 2 && x2 == 0 || x2 == 2 && x1 == 0)
            {
                x3 = 1;
            }
            else
            {
                x3 = 0;
            }
            if (inputList.get(x3) == Items.IRON_INGOT || inputList.get(x3) == ModItems.MANGANESE_INGOT || inputList.get(x3) == ModItems.MAGNESIUM_INGOT || inputList.get(x3) == ModItems.SILICON) {
                if (inputValList.get(x1) >= 8 && inputValList.get(x2) <= 1 && inputValList.get(x3) <= 1 && inputValList.get(x1) + inputValList.get(x2) + inputValList.get(x3) == 10) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new Pair<>(new ItemStack(ModItems.DURALUMIN_ALLOY, 8),ar);
                }
            }
        }
        else if (inputList.contains(Items.GOLD_INGOT) && inputList.contains(ModItems.COPPER_INGOT)) {
            int x1 = inputList.indexOf(Items.GOLD_INGOT);
            int x2 = inputList.indexOf(ModItems.COPPER_INGOT);
            int x3;
            if (x1 == 0 && x2 == 1 || x2 == 0 && x1 == 1)
            {
                x3 = 2;
            }
            else if (x1 == 2 && x2 == 0 || x2 == 2 && x1 == 0)
            {
                x3 = 1;
            }
            else
            {
                x3 = 0;
            }
            if (inputList.get(x3) == ModItems.SILVER_INGOT) {
                if (inputValList.get(x1) >= 8 && inputValList.get(x2) <= 1 && inputValList.get(x3) <= 1 && inputValList.get(x1) + inputValList.get(x2) + inputValList.get(x3) == 10) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new Pair<>(new ItemStack(ModItems.DURALUMIN_ALLOY, 8),ar);
                }
            }
        }
        int[] ar = new int[3];
        ar[0] = 1;
        ar[1] = 1;
        ar[2] = 1;
        return new Pair<>(ItemStack.EMPTY, ar);
    }

}
