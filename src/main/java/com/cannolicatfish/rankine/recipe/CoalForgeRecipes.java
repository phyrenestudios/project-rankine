package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.ModItems;
import com.cannolicatfish.rankine.items.alloys.AlloyData;
import com.cannolicatfish.rankine.items.alloys.AlloyPickaxe;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.*;

public class CoalForgeRecipes {
    private static final CoalForgeRecipes INSTANCE = new CoalForgeRecipes();

    public static CoalForgeRecipes getInstance() {
        return INSTANCE;
    }

    public ItemStack getResult(ItemStack input1, ItemStack input2, ItemStack template) {
        ItemStack result;
        if (input1.getItem() == Items.STICK && input2.getItem() == ModItems.BRONZE_ALLOY) {
            if (template.getItem() == ModItems.PICKAXE_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 3)
            {
                result = new ItemStack(ModItems.BRONZE_PICKAXE);
                AlloyPickaxe.addAlloy(result,new AlloyData(input2.getTag().getList("StoredComposition", 10).getCompound(0).get("comp").getString()));
                return result;
            }

        }
        if (input1.getItem() == Items.STICK && input2.getItem() == ModItems.ROSE_GOLD_ALLOY)
        {
            if (template.getItem() == ModItems.PICKAXE_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 3)
            {
                result = new ItemStack(ModItems.ROSE_GOLD_PICKAXE);
                result.addEnchantment(Enchantments.EFFICIENCY, 2);
            }
            if (template.getItem() == ModItems.AXE_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 3)
            {
                result = new ItemStack(ModItems.ROSE_GOLD_AXE);
                result.addEnchantment(Enchantments.EFFICIENCY, 2);
            }
            if (template.getItem() == ModItems.SHOVEL_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 1)
            {
                result = new ItemStack(ModItems.ROSE_GOLD_SHOVEL);
                result.addEnchantment(Enchantments.EFFICIENCY, 2);
            }
            if (template.getItem() == ModItems.HOE_TEMPLATE && input1.getCount() >= 2 && input2.getCount() >= 2)
            {
                result = new ItemStack(ModItems.ROSE_GOLD_HOE);
                result.addEnchantment(Enchantments.EFFICIENCY, 2);
            }
            if (template.getItem() == ModItems.SWORD_TEMPLATE && input1.getCount() >= 1 && input2.getCount() >= 2)
            {
                result = new ItemStack(ModItems.ROSE_GOLD_SWORD);
                result.addEnchantment(Enchantments.EFFICIENCY, 2);
            }
        }

        return ItemStack.EMPTY;
    }
}
