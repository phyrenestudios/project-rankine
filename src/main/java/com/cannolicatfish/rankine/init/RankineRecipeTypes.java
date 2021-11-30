package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.recipe.*;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.item.crafting.SuspiciousStewRecipe;
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

    public static final IRecipeType<ElementRecipe> ELEMENT = new IRecipeType<ElementRecipe>() {
        @Override
        public <C extends IInventory> Optional<ElementRecipe> matches(IRecipe<C> recipe, World worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((ElementRecipe) recipe) : Optional.empty();
        }
    };
            
    public static final IRecipeType<EvaporationRecipe> EVAPORATION = new IRecipeType<EvaporationRecipe>() {
        @Override
        public <C extends IInventory> Optional<EvaporationRecipe> matches(IRecipe<C> recipe, World worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((EvaporationRecipe) recipe) : Optional.empty();
        }
    };

    public static final IRecipeType<CrucibleRecipe> CRUCIBLE = new IRecipeType<CrucibleRecipe>() {
        @Override
        public <C extends IInventory> Optional<CrucibleRecipe> matches(IRecipe<C> recipe, World worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((CrucibleRecipe) recipe) : Optional.empty();
        }
    };

    public static final IRecipeType<RockGeneratorRecipe> ROCK_GENERATOR = new IRecipeType<RockGeneratorRecipe>() {
        @Override
        public <C extends IInventory> Optional<RockGeneratorRecipe> matches(IRecipe<C> recipe, World worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((RockGeneratorRecipe) recipe) : Optional.empty();
        }
    };

    public static final IRecipeType<FusionFurnaceRecipe> FUSION_FURNACE = new IRecipeType<FusionFurnaceRecipe>() {
        @Override
        public <C extends IInventory> Optional<FusionFurnaceRecipe> matches(IRecipe<C> recipe, World worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((FusionFurnaceRecipe) recipe) : Optional.empty();
        }
    };

    public static final IRecipeType<TreetappingRecipe> TREETAPPING = new IRecipeType<TreetappingRecipe>() {
        @Override
        public <C extends IInventory> Optional<TreetappingRecipe> matches(IRecipe<C> recipe, World worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((TreetappingRecipe) recipe) : Optional.empty();
        }
    };

    public static final IRecipeType<AirDistillationRecipe> AIR_DISTILLATION = new IRecipeType<AirDistillationRecipe>() {
        @Override
        public <C extends IInventory> Optional<AirDistillationRecipe> matches(IRecipe<C> recipe, World worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((AirDistillationRecipe) recipe) : Optional.empty();
        }
    };

    public static final IRecipeType<MixingRecipe> MIXING = new IRecipeType<MixingRecipe>() {
        @Override
        public <C extends IInventory> Optional<MixingRecipe> matches(IRecipe<C> recipe, World worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((MixingRecipe) recipe) : Optional.empty();
        }
    };
}
