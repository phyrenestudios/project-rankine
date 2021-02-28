package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.BeehiveOvenRecipe;
import com.cannolicatfish.rankine.recipe.CrushingRecipe;
import com.cannolicatfish.rankine.recipe.SluicingRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.world.World;

import java.util.Optional;

public class RankineRecipeTypes {
    public static final IRecipeType<AlloyingRecipe> ALLOYING = new IRecipeType<AlloyingRecipe>() {
        @Override
        public <C extends IInventory> Optional<AlloyingRecipe> matches(IRecipe<C> recipe, World worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((AlloyingRecipe) recipe) : Optional.empty();
        }
    };

    public static final IRecipeType<CrushingRecipe> CRUSHING = new IRecipeType<CrushingRecipe>() {
        @Override
        public <C extends IInventory> Optional<CrushingRecipe> matches(IRecipe<C> recipe, World worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((CrushingRecipe) recipe) : Optional.empty();
        }
    };

    public static final IRecipeType<BeehiveOvenRecipe> BEEHIVE = new IRecipeType<BeehiveOvenRecipe>() {
        @Override
        public <C extends IInventory> Optional<BeehiveOvenRecipe> matches(IRecipe<C> recipe, World worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((BeehiveOvenRecipe) recipe) : Optional.empty();
        }
    };

    public static final IRecipeType<SluicingRecipe> SLUICING = new IRecipeType<SluicingRecipe>() {
        @Override
        public <C extends IInventory> Optional<SluicingRecipe> matches(IRecipe<C> recipe, World worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((SluicingRecipe) recipe) : Optional.empty();
        }
    };
}
