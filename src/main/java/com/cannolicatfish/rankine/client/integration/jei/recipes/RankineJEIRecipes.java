package com.cannolicatfish.rankine.client.integration.jei.recipes;

import com.cannolicatfish.rankine.blocks.cauldrons.AbstractRankineCauldronBlock;
import com.cannolicatfish.rankine.blocks.cauldrons.MapleSyrupCauldronBlock;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.BatteryItem;
import com.cannolicatfish.rankine.recipe.*;
import com.cannolicatfish.rankine.util.RockGeneratorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;

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
        return BuiltInRegistries.BLOCK.stream().filter(block -> block instanceof AbstractRankineCauldronBlock && !(block instanceof MapleSyrupCauldronBlock)).map(block -> (AbstractRankineCauldronBlock) block)
                .<RankineCauldronDryingRecipe>mapMulti((block, consumer) -> {
                    if (block.getTransformFluid() == Fluids.EMPTY || block.getTransformFluid() == null) {
                        consumer.accept(new RankineCauldronDryingRecipe(!block.getFluid().equals(Fluids.EMPTY) ? new FluidStack(block.getFluid(), 1000) : FluidStack.EMPTY, block.getBottle() instanceof BucketItem ? Items.BUCKET.getDefaultInstance() : Items.GLASS_BOTTLE.getDefaultInstance(), block.getBottle().getDefaultInstance(), block.getOutput().getDefaultInstance()));
                    } else {
                        consumer.accept(new RankineCauldronDryingRecipe(!block.getFluid().equals(Fluids.EMPTY) ? new FluidStack(block.getFluid(), 1000) : FluidStack.EMPTY,block.getBottle() instanceof BucketItem ? Items.BUCKET.getDefaultInstance() : Items.GLASS_BOTTLE.getDefaultInstance(), block.getBottle().getDefaultInstance(), new FluidStack(block.getTransformFluid(),1000)));
                    }

        }).collect(Collectors.toList());

    }
    public List<IBatteryRecipe> getBatteryRecipes() {
        return BuiltInRegistries.ITEM.stream().filter(item -> item instanceof BatteryItem).<IBatteryRecipe>mapMulti((item, consumer) -> {
            int charge = item.getDefaultInstance().getMaxDamage();
            if (charge > 0) {
                consumer.accept(new BatteryRecipe(List.of(new ItemStack(item)), charge));
            }
        }).collect(Collectors.toList());

    }

    // PORT-TODO(jei): RecipeManager#getAllRecipesFor now returns List<RecipeHolder<T>> (1.20.5+),
    // and Recipe<?> no longer exposes getId(). These getters return the RecipeHolder-wrapped list so
    // JEIRankinePlugin#getSortedRecipes can still sort by recipe id (RecipeHolder::id) before unwrapping
    // to the plain recipe (RecipeHolder::value) that JEI's IRecipeCategory<T> actually renders.
    public List<RecipeHolder<CrushingRecipe>> getCrushingRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.CRUSHING.get());
    }

    public List<RecipeHolder<ForagingRecipe>> getForagingRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.FORAGING.get());
    }

    public List<RecipeHolder<AlloyingRecipe>> getAlloyingRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ALLOYING.get());
    }

    public List<RecipeHolder<AlloyingRecipe>> getAlloyFurnaceRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ALLOYING.get()).stream().filter(holder -> (holder.value().getTier() & 1) != 0 && holder.value().getTier() != -1).collect(Collectors.toList());
    }

    public List<RecipeHolder<FusionFurnaceRecipe>> getFusionFurnaceRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.FUSION_FURNACE.get());
    }

    public List<RecipeHolder<AlloyingRecipe>> getInductionFurnaceRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ALLOYING.get()).stream().filter(holder -> (holder.value().getTier() & 2) != 0 && holder.value().getTier() != -1).collect(Collectors.toList());
    }

    public List<RecipeHolder<RockGeneratorRecipe>> getIntrusiveGeneratorRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR.get()).stream().filter(holder -> (holder.value().getGenType().equals(RockGeneratorUtils.RockGenType.INTRUSIVE_IGNEOUS))).collect(Collectors.toList());
    }

    public List<RecipeHolder<RockGeneratorRecipe>> getExtrusiveGeneratorRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR.get()).stream().filter(holder -> (holder.value().getGenType().equals(RockGeneratorUtils.RockGenType.EXTRUSIVE_IGNEOUS))).collect(Collectors.toList());
    }

    public List<RecipeHolder<RockGeneratorRecipe>> getSedimentaryGeneratorRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR.get()).stream().filter(holder -> (holder.value().getGenType().equals(RockGeneratorUtils.RockGenType.SEDIMENTARY))).collect(Collectors.toList());
    }

    public List<RecipeHolder<RockGeneratorRecipe>> getMetamorphicGeneratorRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR.get()).stream().filter(holder -> (holder.value().getGenType().equals(RockGeneratorUtils.RockGenType.METAMORPHIC))).collect(Collectors.toList());
    }

    public List<RecipeHolder<RockGeneratorRecipe>> getVolcanicGeneratorRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ROCK_GENERATOR.get()).stream().filter(holder -> (holder.value().getGenType().equals(RockGeneratorUtils.RockGenType.VOLCANIC))).collect(Collectors.toList());
    }

    public List<RecipeHolder<TreetappingRecipe>> getTreetappingRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.TREETAPPING.get());
    }

    public List<RecipeHolder<StrippingRecipe>> getStrippingRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.STRIPPING.get());
    }

    public List<RecipeHolder<AirDistillationRecipe>> getAirDistillationRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.AIR_DISTILLATION.get());
    }

    public List<RecipeHolder<BeehiveOvenRecipe>> getBeehiveRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.BEEHIVE.get());
    }

    public List<RecipeHolder<SluicingRecipe>> getSluicingRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.SLUICING.get());
    }

    public List<RecipeHolder<MixingRecipe>> getMixingRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.MIXING.get());
    }

    public List<RecipeHolder<ElementRecipe>> getElementRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.ELEMENT.get());
    }

    public List<RecipeHolder<CrucibleRecipe>> getCrucibleRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.CRUCIBLE.get());
    }

    public List<RecipeHolder<EvaporationRecipe>> getEvaporationRecipes() {
        return recipeManager.getAllRecipesFor(RankineRecipeTypes.EVAPORATION.get());
    }

    public static <T> void checkNotNull(@Nullable T object, String name) {
        if (object == null) {
            throw new NullPointerException(name + " must not be null.");
        }
    }
}
