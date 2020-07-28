package com.cannolicatfish.rankine.client.integration.jei;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.client.integration.jei.crusher.CoalForgeRecipeCategory;
import com.cannolicatfish.rankine.client.integration.jei.crusher.PistonCrusherRecipeCategory;
import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.init.ModItems;
import com.cannolicatfish.rankine.init.ModRecipes;
import com.cannolicatfish.rankine.recipe.IPistonCrusherRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JEIRankinePlugin implements IModPlugin {

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ProjectRankine.MODID, "jei_plugin");
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registry) {

        registry.addRecipes(ModRecipes.getCrushingRecipes(),PistonCrusherRecipeCategory.UID);
        registry.addRecipes(ModRecipes.getForgingRecipes(), CoalForgeRecipeCategory.UID);
        //registry.addRecipes(Minecraft.getInstance().world.getRecipeManager().getRecipes(IPistonCrusherRecipe.RECIPE_TYPE, IInventoryEmpty.INSTANCE, Minecraft.getInstance().world), PistonCrusherRecipeCategory.UID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();

        registry.addRecipeCategories(new PistonCrusherRecipeCategory(guiHelper));
        registry.addRecipeCategories(new CoalForgeRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.PISTON_CRUSHER), PistonCrusherRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.COAL_FORGE), CoalForgeRecipeCategory.UID);
    }
}
