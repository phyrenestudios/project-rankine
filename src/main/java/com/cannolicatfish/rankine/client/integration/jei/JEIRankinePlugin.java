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
        registry.addRecipes(ModRecipes.getFineryRecipes(), FineryForgeRecipeCategory.UID);
        registry.addIngredientInfo(new ItemStack(ModItems.BRONZE_ALLOY), VanillaTypes.ITEM, "Bronze Alloy can be made in the Alloy Furnace.", "80-90% Copper", "10-20% Tin", "Copper + Tin >= 90%",
                "Possible additions:", "Al, Mn, Ni, Zn");
        registry.addIngredientInfo(new ItemStack(ModItems.ALUMINUM_BRONZE_ALLOY), VanillaTypes.ITEM, "Aluminum Bronze Alloy can be made in the Alloy Furnace.", "74-93% Copper", "4-12% Aluminum", "Copper + Aluminum >= 85%",
                "Possible additions:", "Mn, Ni, Zn, As, Fe, Pb");
        registry.addIngredientInfo(new ItemStack(ModItems.BRASS_ALLOY), VanillaTypes.ITEM, "Brass Alloy can be made in the Alloy Furnace.", "30-70% Copper", "15-60% Zinc", "Copper + Zinc >= 90%",
                "Possible additions:", "Sn, Pb, Al, Ni, Fe");
        registry.addIngredientInfo(new ItemStack(ModItems.CUPRONICKEL_ALLOY), VanillaTypes.ITEM, "Cupronickel Alloy can be made in the Alloy Furnace.", "70-90% Copper", "10-30% Nickel", "Copper + Nickel >= 95%",
                "Possible additions:", "Fe, Mn");
        registry.addIngredientInfo(new ItemStack(ModItems.CAST_IRON_ALLOY), VanillaTypes.ITEM, "Cast Iron Alloy can be made in the Alloy Furnace.", "86-98% Pig/Sponge Iron", "2-4% Carbon (Coke/Graphite)", "Iron + Carbon >= 90%",
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
        registry.addIngredientInfo(new ItemStack(ModItems.NICKEL_SILVER_ALLOY), VanillaTypes.ITEM, "Nickel-Silver Alloy can be made in the Alloy Furnace.", "50-70% Copper", "15-25% Nickel", "15-25% Zinc");
        registry.addIngredientInfo(new ItemStack(ModItems.INVAR_ALLOY), VanillaTypes.ITEM, "Invar Alloy can be made in the Alloy Furnace.", "50-90% Iron", "10-50% Nickel");
        if (Config.AMALGAM_EXTRAS.get())
        {
            registry.addIngredientInfo(new ItemStack(ModItems.AMALGAM_ALLOY), VanillaTypes.ITEM, "Amalgam Alloy can be made in the Alloy Furnace.", "25-80% Mercury", "25-50% Gold",  "Mercury + Gold >= 50%",
                    "Amalgam can use any additions.");
        } else {
            registry.addIngredientInfo(new ItemStack(ModItems.AMALGAM_ALLOY), VanillaTypes.ITEM, "Amalgam Alloy can be made in the Alloy Furnace.", "25-80% Mercury", "25-50% Gold",  "Mercury + Gold >= 50%",
                    "Amalgam can use any additions EXCEPT Fe, Pt, W, Ta.");
        }
        registry.addIngredientInfo(new ItemStack(ModItems.NICKEL_SUPERALLOY), VanillaTypes.ITEM, "Nickel Superalloy can be made in the Alloy Furnace.", "50-75% Nickel", "14-20% Chromium",  "Nickel + Chromium >= 64%",
                "Possible additions:", "Si, Mn, Al, Ti, C, Fe, V");
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

        registry.addIngredientInfo(new ItemStack(ModItems.NICHROME_ALLOY), VanillaTypes.ITEM, "Currently not obtainable in Survival.");
        registry.addIngredientInfo(new ItemStack(ModItems.STAINLESS_STEEL_ALLOY), VanillaTypes.ITEM, "Currently not obtainable in Survival.");
        registry.addIngredientInfo(new ItemStack(ModItems.SOLDER_ALLOY), VanillaTypes.ITEM, "Currently not obtainable in Survival.");
        registry.addIngredientInfo(new ItemStack(ModItems.ALNICO_ALLOY), VanillaTypes.ITEM, "Currently not obtainable in Survival.");
        registry.addIngredientInfo(new ItemStack(ModItems.NITINOL_ALLOY), VanillaTypes.ITEM, "Currently not obtainable in Survival.");
        registry.addIngredientInfo(new ItemStack(ModItems.DURALUMIN_ALLOY), VanillaTypes.ITEM, "Currently not obtainable in Survival.");
        registry.addIngredientInfo(new ItemStack(ModItems.MAGNALIUM_ALLOY), VanillaTypes.ITEM, "Currently not obtainable in Survival.");
        registry.addIngredientInfo(new ItemStack(ModItems.ROSE_METAL_ALLOY), VanillaTypes.ITEM, "Currently not obtainable in Survival.");
        registry.addIngredientInfo(new ItemStack(ModItems.INVAR_ALLOY), VanillaTypes.ITEM, "Currently not obtainable in Survival.");

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new AlloyRecipeCategory(guiHelper));
        registry.addRecipeCategories(new BeehiveOvenRecipeCategory(guiHelper));
        registry.addRecipeCategories(new PistonCrusherRecipeCategory(guiHelper));
        registry.addRecipeCategories(new CoalForgeRecipeCategory(guiHelper));
        registry.addRecipeCategories(new FineryForgeRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.ALLOY_FURNACE), AlloyRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.BEEHIVE_OVEN_PIT), BeehiveOvenRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.MAGNESIUM_BEEHIVE_OVEN_PIT), BeehiveOvenRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.ZIRCON_BEEHIVE_OVEN_PIT), BeehiveOvenRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.PISTON_CRUSHER), PistonCrusherRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.COAL_FORGE), CoalForgeRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.FINERY_FORGE), FineryForgeRecipeCategory.UID);
    }

}
