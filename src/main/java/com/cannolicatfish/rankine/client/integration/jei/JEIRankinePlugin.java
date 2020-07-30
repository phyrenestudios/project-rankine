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
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredientType;
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
        registry.addIngredientInfo(new ItemStack(ModItems.BRONZE_ALLOY), VanillaTypes.ITEM, "Bronze Alloy can be made in the Alloy Furnace.", "80-90% Copper", "10-20% Tin", "Copper + Tin >= 90%",
                "Possible additions:", "Al, Mn, Ni, Zn");
        registry.addIngredientInfo(new ItemStack(ModItems.ALUMINUM_BRONZE_ALLOY), VanillaTypes.ITEM, "Aluminum Bronze Alloy can be made in the Alloy Furnace.", "74-93% Copper", "4-12% Zinc", "Copper + Aluminum >= 85%",
                "Possible additions:", "Mn, Ni, Zn, As, Fe, Pb");
        registry.addIngredientInfo(new ItemStack(ModItems.BRASS_ALLOY), VanillaTypes.ITEM, "Brass Alloy can be made in the Alloy Furnace.", "30-70% Copper", "15-60% Zinc", "Copper + Zinc >= 90%",
                "Possible additions:", "Sn, Pb, Al, Ni, Fe");
        registry.addIngredientInfo(new ItemStack(ModItems.CAST_IRON_INGOT), VanillaTypes.ITEM, "Cast Iron Alloy can be made in the Alloy Furnace.", "86-98% Pig/Sponge Iron", "2-4% Carbon (Coke/Graphite)", "Iron + Carbon >= 90%",
                "Possible additions:", "Si, Mn, Ni, Cr, Mo, Ti, Vn");
        registry.addIngredientInfo(new ItemStack(ModItems.WHITE_GOLD_ALLOY), VanillaTypes.ITEM, "White Gold Alloy can be made in the Alloy Furnace.", "74-90% Gold", "5-10% Zinc", "Gold + Zinc >= 90%",
                "Possible additions:", "Ni, Pd, Ag, Pt");
        registry.addIngredientInfo(new ItemStack(ModItems.ROSE_GOLD_ALLOY), VanillaTypes.ITEM, "Rose Gold Alloy can be made in the Alloy Furnace.", "74-76% Gold", "20-25% Copper",  "Gold + Copper >= 94%",
                "Possible additions:", "Ag, Zn");
        registry.addIngredientInfo(new ItemStack(ModItems.GREEN_GOLD_ALLOY), VanillaTypes.ITEM, "Green Gold Alloy can be made in the Alloy Furnace.", "30-70% Gold", "30-70% Silver", "Gold + Silver >= 90%",
                "Possible additions:", "Cu, Cd, Pt");
        registry.addIngredientInfo(new ItemStack(ModItems.PURPLE_GOLD_ALLOY), VanillaTypes.ITEM, "Purple Gold Alloy can be made in the Alloy Furnace.", "79-81% Gold", "19-21% Aluminum");
        registry.addIngredientInfo(new ItemStack(ModItems.BLUE_GOLD_ALLOY), VanillaTypes.ITEM, "Blue Gold Alloy can be made in the Alloy Furnace.", "74-76% Gold", "20-25% Iron",  "Gold + Iron >= 90%",
                "Possible additions:", "Ni, Ru, Rh");
        registry.addIngredientInfo(new ItemStack(ModItems.AMALGAM_ALLOY), VanillaTypes.ITEM, "Amalgam Alloy can be made in the Alloy Furnace.", "25-80% Mercury", "25-50% Gold",  "Mercury + Gold >= 50%",
                "Amalgam can use any additions EXCEPT Fe, Pt, W, Ta");
        registry.addIngredientInfo(new ItemStack(ModItems.STEEL_ALLOY), VanillaTypes.ITEM, "To make crucible steel, right-click on an empty crucible on top of a heat source with pig iron ingots.",
                "Then, right-click the filled crucible with wrought iron ingots.");
        registry.addIngredientInfo(new ItemStack(ModItems.METEORIC_IRON), VanillaTypes.ITEM, "Meteoric Iron can be mined from meteorites.", "Possible compositions:", "Kamacite - 90% Iron, 10% Nickel",
                "Antitaenite - 80% Iron, 20% Nickel", "Taenite - 60% Iron, 40% Nickel", "Tetrataenite - 50% Iron, 50% Nickel");
        registry.addIngredientInfo(new ItemStack(ModItems.COKE), VanillaTypes.ITEM, "Coke can be obtained by cooking Bituminous or Sub-bituminous Coal Blocks in a beehive oven.",
                "See Beehive Oven Pit for more details.");
        registry.addIngredientInfo(new ItemStack(ModBlocks.COKE_BLOCK), VanillaTypes.ITEM, "Coke can be obtained by cooking Bituminous or Sub-bituminous Coal Blocks in a beehive oven.",
                "See Beehive Oven Pit for more details.");
        registry.addIngredientInfo(new ItemStack(ModItems.QUICKLIME), VanillaTypes.ITEM, "Quicklime can be obtained by cooking Limestone in a beehive oven.",
                "See Beehive Oven Pit for more details.");
        registry.addIngredientInfo(new ItemStack(ModBlocks.QUICKLIME_BLOCK), VanillaTypes.ITEM, "Quicklime can be obtained by cooking Limestone in a beehive oven.",
                "See Beehive Oven Pit for more details.");
        registry.addIngredientInfo(new ItemStack(ModItems.MAGNESIA), VanillaTypes.ITEM, "Magnesia can be obtained by cooking a Block of Magnesite in a beehive oven.",
                "See Beehive Oven Pit for more details.");
        registry.addIngredientInfo(new ItemStack(ModBlocks.MAGNESIA_BLOCK), VanillaTypes.ITEM, "Magnesia can be obtained by cooking a Block of Magnesite in a beehive oven.",
                "See Beehive Oven Pit for more details.");
        registry.addIngredientInfo(new ItemStack(ModBlocks.BEEHIVE_OVEN_PIT), VanillaTypes.ITEM, "The minimum requirements for a beehive oven is a 3x3 structure with a beehive oven pit in the center and lighting the pit with flint and steel.",
                "Only the pit block must have access to the sky. Place blocks around the pit to cook them over time.");

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
