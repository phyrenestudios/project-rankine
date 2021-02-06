package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.recipe.CrushingRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.world.World;

import java.util.Optional;

public class RankineRecipeTypes {
    /*public static final IRecipeType<AlloyingRecipe> ALLOYING = new IRecipeType<AlloyingRecipe>() {
        @Override
        public <C extends IInventory> Optional<AlloyingRecipe> matches(IRecipe<C> recipe, World worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((AlloyingRecipe) recipe) : Optional.empty();
        }
    };*/

    public static final IRecipeType<CrushingRecipe> CRUSHING = new IRecipeType<CrushingRecipe>() {
        @Override
        public <C extends IInventory> Optional<CrushingRecipe> matches(IRecipe<C> recipe, World worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((CrushingRecipe) recipe) : Optional.empty();
        }
    };
}
