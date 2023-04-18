package com.cannolicatfish.rankine.client.integration.jei.recipes;

import com.cannolicatfish.rankine.blocks.cauldrons.AbstractRankineCauldronBlock;
import com.cannolicatfish.rankine.blocks.cauldrons.MapleSyrupCauldronBlock;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.BatteryItem;
import com.cannolicatfish.rankine.recipe.*;
import com.cannolicatfish.rankine.util.RockGeneratorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class RankineJEIRecipes {

    private final RecipeManager recipeManager;

    public RankineJEIRecipes() {
        ClientLevel world = Minecraft.getInstance().level;
        checkNotNull(world, "minecraft world");
        this.recipeManager = world.getRecipeManager();
    }

    public List<IRankineCauldronRecipe> getCauldronRecipes() {
        return ForgeRegistries.BLOCKS.getValues().stream().filter(block -> block instanceof AbstractRankineCauldronBlock && !(block instanceof MapleSyrupCauldronBlock)).map(block -> (AbstractRankineCauldronBlock) block)
                .<RankineCauldronDryingRecipe>mapMulti((block, consumer) -> {
                    if (block.getTransformFluid() == Fluids.EMPTY || block.getTransformFluid() == null) {
                        consumer.accept(new RankineCauldronDryingRecipe(!block.getFluid().equals(Fluids.EMPTY) ? new FluidStack(block.getFluid(), 1000) : FluidStack.EMPTY, block.getBottle() instanceof BucketItem ? Items.BUCKET.getDefaultInstance() : Items.GLASS_BOTTLE.getDefaultInstance(), block.getBottle().getDefaultInstance(), block.getOutput().getDefaultInstance()));
                    } else {
                        consumer.accept(new RankineCauldronDryingRecipe(!block.getFluid().equals(Fluids.EMPTY) ? new FluidStack(block.getFluid(), 1000) : FluidStack.EMPTY,block.getBottle() instanceof BucketItem ? Items.BUCKET.getDefaultInstance() : Items.GLASS_BOTTLE.getDefaultInstance(), block.getBottle().getDefaultInstance(), new FluidStack(block.getTransformFluid(),1000)));
                    }

        }).collect(Collectors.toList());

    }
    public List<IBatteryRecipe> getBatteryRecipes() {
        return ForgeRegistries.ITEMS.getValues().stream().filter(item -> item instanceof BatteryItem).<IBatteryRecipe>mapMulti((item, consumer) -> {
            int charge = item.getDefaultInstance().getMaxDamage();
            if (charge > 0) {
                consumer.accept(new BatteryRecipe(List.of(new ItemStack(item)), charge));
            }
        }).collect(Collectors.toList());

    }

    public List<CrushingRecipe> getCrushingRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.CRUSHING.get());
    }

    public List<ForagingRecipe> getForagingRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.FORAGING.get());
    }

    public List<AlloyingRecipe> getAlloyingRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ALLOYING.get());
    }

    public List<AlloyingRecipe> getAlloyFurnaceRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ALLOYING.get()).stream().filter(recipe -> (recipe.getTier() & 1) != 0 && recipe.getTier() != -1).collect(Collectors.toList());
    }

    public List<FusionFurnaceRecipe> getFusionFurnaceRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.FUSION_FURNACE.get());
    }

    public List<AlloyingRecipe> getInductionFurnaceRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ALLOYING.get()).stream().filter(recipe -> (recipe.getTier() & 2) != 0 && recipe.getTier() != -1).collect(Collectors.toList());
    }

    public List<RockGeneratorRecipe> getIntrusiveGeneratorRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR.get()).stream().filter(recipe -> (recipe.getGenType().equals(RockGeneratorUtils.RockGenType.INTRUSIVE_IGNEOUS))).collect(Collectors.toList());
    }

    public List<RockGeneratorRecipe> getExtrusiveGeneratorRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR.get()).stream().filter(recipe -> (recipe.getGenType().equals(RockGeneratorUtils.RockGenType.EXTRUSIVE_IGNEOUS))).collect(Collectors.toList());
    }

    public List<RockGeneratorRecipe> getSedimentaryGeneratorRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR.get()).stream().filter(recipe -> (recipe.getGenType().equals(RockGeneratorUtils.RockGenType.SEDIMENTARY))).collect(Collectors.toList());
    }

    public List<RockGeneratorRecipe> getMetamorphicGeneratorRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR.get()).stream().filter(recipe -> (recipe.getGenType().equals(RockGeneratorUtils.RockGenType.METAMORPHIC))).collect(Collectors.toList());
    }

    public List<RockGeneratorRecipe> getVolcanicGeneratorRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR.get()).stream().filter(recipe -> (recipe.getGenType().equals(RockGeneratorUtils.RockGenType.VOLCANIC))).collect(Collectors.toList());
    }

    public List<TreetappingRecipe> getTreetappingRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.TREETAPPING.get());
    }

    public List<StrippingRecipe> getStrippingRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.AXE_STRIPPING.get());
    }

    public List<AirDistillationRecipe> getAirDistillationRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.AIR_DISTILLATION.get());
    }

    public List<BeehiveOvenRecipe> getBeehiveRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.BEEHIVE.get());
    }

    public List<SluicingRecipe> getSluicingRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.SLUICING.get());
    }

    public List<MixingRecipe> getMixingRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.MIXING.get());
    }

    public List<ElementRecipe> getElementRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ELEMENT.get());
    }

    public List<CrucibleRecipe> getCrucibleRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.CRUCIBLE.get());
    }

    public List<EvaporationRecipe> getEvaporationRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.EVAPORATION.get());
    }

    public static <T> void checkNotNull(@Nullable T object, String name) {
        if (object == null) {
            throw new NullPointerException(name + " must not be null.");
        }
    }
}
