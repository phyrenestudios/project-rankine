package com.cannolicatfish.rankine.client.integration.jei;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.client.integration.jei.categories.*;
import com.cannolicatfish.rankine.init.ModBlocks;
import com.cannolicatfish.rankine.init.ModItems;
import com.cannolicatfish.rankine.init.ModRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

@JeiPlugin
public class JEIRankinePlugin implements IModPlugin {

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ProjectRankine.MODID, "jei_plugin");
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registry) {
        registry.addRecipes(ModRecipes.getAlloyRecipes(),AlloyRecipeCategory.UID);
        registry.addRecipes(ModRecipes.getBeehiveOvenRecipes(), BeehiveOvenRecipeCategory.UID);
        registry.addRecipes(ModRecipes.getCrushingRecipes(),PistonCrusherRecipeCategory.UID);
        registry.addRecipes(ModRecipes.getForgingRecipes(), CoalForgeRecipeCategory.UID);
        registry.addRecipes(ModRecipes.getTripleAlloyRecipes(), TripleAlloyRecipeCategory.UID);
        if (Config.AMALGAM_EXTRAS.get())
        {
            registry.addIngredientInfo(new ItemStack(ModItems.AMALGAM_ALLOY), VanillaTypes.ITEM, "Amalgam Alloy can be made in the Alloy Furnace.", "25-80% Mercury", "25-50% Gold",  "Mercury + Gold >= 50%",
                    "Amalgam can use any additions.");
        } else {
            registry.addIngredientInfo(new ItemStack(ModItems.AMALGAM_ALLOY), VanillaTypes.ITEM, "Amalgam Alloy can be made in the Alloy Furnace.", "25-80% Mercury", "25-50% Gold",  "Mercury + Gold >= 50%",
                    "Amalgam can use any additions EXCEPT Fe, Pt, W, Ta.");
        }
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
        registry.addIngredientInfo(new ItemStack(ModBlocks.BEEHIVE_OVEN_PIT), VanillaTypes.ITEM, "The beehive oven is at minimum a 3x3 structure with a beehive oven pit in the center of 8 refractory bricks. " +
                "The pit block must have access to the sky. Place blocks on the refractory bricks and light it with a flint and steel to cook them over time.");
        registry.addIngredientInfo(new ItemStack(ModItems.ELEMENT_INDEXER), VanillaTypes.ITEM, "The Element Indexer is a device that can be used to analyze the properties of an element in an alloy. " +
                "Scroll up and down to change the percentage.");
        registry.addIngredientInfo(new ItemStack(ModItems.SOLDER_ALLOY), VanillaTypes.ITEM, "Currently not obtainable in Survival.");
        registry.addIngredientInfo(new ItemStack(ModItems.ALNICO_ALLOY), VanillaTypes.ITEM, "Currently not obtainable in Survival.");
        registry.addIngredientInfo(new ItemStack(ModItems.NITINOL_ALLOY), VanillaTypes.ITEM, "Currently not obtainable in Survival.");
        registry.addIngredientInfo(new ItemStack(ModItems.MAGNALIUM_ALLOY), VanillaTypes.ITEM, "Currently not obtainable in Survival.");

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new AlloyRecipeCategory(guiHelper));
        registry.addRecipeCategories(new BeehiveOvenRecipeCategory(guiHelper));
        registry.addRecipeCategories(new PistonCrusherRecipeCategory(guiHelper));
        registry.addRecipeCategories(new CoalForgeRecipeCategory(guiHelper));
        registry.addRecipeCategories(new TripleAlloyRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.ALLOY_FURNACE), AlloyRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.BEEHIVE_OVEN_PIT), BeehiveOvenRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.MAGNESIUM_BEEHIVE_OVEN_PIT), BeehiveOvenRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.ZIRCON_BEEHIVE_OVEN_PIT), BeehiveOvenRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.PISTON_CRUSHER), PistonCrusherRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.COAL_FORGE), CoalForgeRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.INDUCTION_FURNACE), TripleAlloyRecipeCategory.UID);
    }

}
