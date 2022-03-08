package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public final class PeriodicTableUtils {

    private static final PeriodicTableUtils INSTANCE = new PeriodicTableUtils();

    public static PeriodicTableUtils getInstance() {
        return INSTANCE;
    }


    public boolean hasElementRecipe(ItemStack stack, World world) {
        return world != null && getElementRecipe(stack,world) != null;
    }

    public ElementRecipe getElementRecipe(ItemStack stack, World world) {
        Inventory temp = new Inventory(stack);
        return world.getRecipeManager().getRecipe(RankineRecipeTypes.ELEMENT, temp, world).orElse(null);
    }

    public AlloyingRecipe getAlloyRecipe(ItemStack stack, World world) {
        ResourceLocation rs = IAlloyItem.getAlloyRecipe(stack);
        if (rs != null) {
            IRecipe<?> recipe = world.getRecipeManager().getRecipe(rs).orElse(null);
            if (recipe instanceof AlloyingRecipe) {
                return (AlloyingRecipe) recipe;
            }
        }
        return null;
    }
}
