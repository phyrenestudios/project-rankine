package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.recipe.AlloyRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.SmithingRecipe;
import net.minecraft.world.World;

import java.util.Optional;

public class ModRecipeTypes {
    public static final IRecipeType<AlloyRecipe> ALLOYING = new IRecipeType<AlloyRecipe>() {
        @Override
        public <C extends IInventory> Optional<AlloyRecipe> matches(IRecipe<C> recipe, World worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((AlloyRecipe) recipe) : Optional.empty();
        }
    };
}
