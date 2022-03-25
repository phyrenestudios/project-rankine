package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.recipe.*;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class RankineRecipeTypes {
    public static final RecipeType<AlloyingRecipe> ALLOYING = new RecipeType<AlloyingRecipe>() {
        @Override
        public <C extends Container> Optional<AlloyingRecipe> tryMatch(Recipe<C> recipe, Level worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((AlloyingRecipe) recipe) : Optional.empty();
        }
    };

    public static final RecipeType<CrushingRecipe> CRUSHING = new RecipeType<CrushingRecipe>() {
        @Override
        public <C extends Container> Optional<CrushingRecipe> tryMatch(Recipe<C> recipe, Level worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((CrushingRecipe) recipe) : Optional.empty();
        }
    };

    public static final RecipeType<BeehiveOvenRecipe> BEEHIVE = new RecipeType<BeehiveOvenRecipe>() {
        @Override
        public <C extends Container> Optional<BeehiveOvenRecipe> tryMatch(Recipe<C> recipe, Level worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((BeehiveOvenRecipe) recipe) : Optional.empty();
        }
    };

    public static final RecipeType<SluicingRecipe> SLUICING = new RecipeType<SluicingRecipe>() {
        @Override
        public <C extends Container> Optional<SluicingRecipe> tryMatch(Recipe<C> recipe, Level worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((SluicingRecipe) recipe) : Optional.empty();
        }
    };

    public static final RecipeType<ElementRecipe> ELEMENT = new RecipeType<ElementRecipe>() {
        @Override
        public <C extends Container> Optional<ElementRecipe> tryMatch(Recipe<C> recipe, Level worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((ElementRecipe) recipe) : Optional.empty();
        }
    };
            
    public static final RecipeType<EvaporationRecipe> EVAPORATION = new RecipeType<EvaporationRecipe>() {
        @Override
        public <C extends Container> Optional<EvaporationRecipe> tryMatch(Recipe<C> recipe, Level worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((EvaporationRecipe) recipe) : Optional.empty();
        }
    };

    public static final RecipeType<CrucibleRecipe> CRUCIBLE = new RecipeType<CrucibleRecipe>() {
        @Override
        public <C extends Container> Optional<CrucibleRecipe> tryMatch(Recipe<C> recipe, Level worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((CrucibleRecipe) recipe) : Optional.empty();
        }
    };

    public static final RecipeType<RockGeneratorRecipe> ROCK_GENERATOR = new RecipeType<RockGeneratorRecipe>() {
        @Override
        public <C extends Container> Optional<RockGeneratorRecipe> tryMatch(Recipe<C> recipe, Level worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((RockGeneratorRecipe) recipe) : Optional.empty();
        }
    };

    public static final RecipeType<FusionFurnaceRecipe> FUSION_FURNACE = new RecipeType<FusionFurnaceRecipe>() {
        @Override
        public <C extends Container> Optional<FusionFurnaceRecipe> tryMatch(Recipe<C> recipe, Level worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((FusionFurnaceRecipe) recipe) : Optional.empty();
        }
    };

    public static final RecipeType<TreetappingRecipe> TREETAPPING = new RecipeType<TreetappingRecipe>() {
        @Override
        public <C extends Container> Optional<TreetappingRecipe> tryMatch(Recipe<C> recipe, Level worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((TreetappingRecipe) recipe) : Optional.empty();
        }
    };

    public static final RecipeType<StrippingRecipe> STRIPPING = new RecipeType<StrippingRecipe>() {
        @Override
        public <C extends Container> Optional<StrippingRecipe> tryMatch(Recipe<C> recipe, Level worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((StrippingRecipe) recipe) : Optional.empty();
        }
    };

    public static final RecipeType<AirDistillationRecipe> AIR_DISTILLATION = new RecipeType<AirDistillationRecipe>() {
        @Override
        public <C extends Container> Optional<AirDistillationRecipe> tryMatch(Recipe<C> recipe, Level worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((AirDistillationRecipe) recipe) : Optional.empty();
        }
    };

    public static final RecipeType<MixingRecipe> MIXING = new RecipeType<MixingRecipe>() {
        @Override
        public <C extends Container> Optional<MixingRecipe> tryMatch(Recipe<C> recipe, Level worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((MixingRecipe) recipe) : Optional.empty();
        }
    };

    public static final RecipeType<AlloyModifierRecipe> ALLOY_MODIFIER = new RecipeType<AlloyModifierRecipe>() {
        @Override
        public <C extends Container> Optional<AlloyModifierRecipe> tryMatch(Recipe<C> recipe, Level worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((AlloyModifierRecipe) recipe) : Optional.empty();
        }
    };
}
