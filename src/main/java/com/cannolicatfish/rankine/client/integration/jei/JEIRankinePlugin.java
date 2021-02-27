package com.cannolicatfish.rankine.client.integration.jei;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceScreen;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceScreen;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherScreen;
import com.cannolicatfish.rankine.client.integration.jei.categories.*;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.gui.screen.inventory.*;
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
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(AlloyFurnaceScreen.class, 98, 32, 24, 16, AlloyingRecipeCategory.UID);
        registration.addRecipeClickArea(InductionFurnaceScreen.class, 98, 32, 24, 16, AlloyingRecipeCategory.UID);
        registration.addRecipeClickArea(PistonCrusherScreen.class, 55, 58, 24, 16, AlloyingRecipeCategory.UID);
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registry) {
        RankineJEIRecipes rankineJEIRecipes = new RankineJEIRecipes();
        registry.addRecipes(RankineRecipes.getBeehiveOvenRecipes(), BeehiveOvenRecipeCategory.UID);
        registry.addRecipes(RankineRecipes.getSluicingRecipes(), SluicingRecipeCategory.UID);
        registry.addRecipes(RankineRecipes.getEvaporationRecipes(), EvaporationRecipeCategory.UID);
        registry.addRecipes(rankineJEIRecipes.getCrushingRecipes(), CrushingRecipeCategory.UID);
        registry.addRecipes(rankineJEIRecipes.getAlloyingRecipes(), AlloyingRecipeCategory.UID);
        registry.addIngredientInfo(new ItemStack(RankineItems.COKE.get()), VanillaTypes.ITEM, "Coke can be obtained by cooking Bituminous or Sub-bituminous Coal Blocks in a beehive oven.",
                "See Beehive Oven Pit for more details.");
        registry.addIngredientInfo(new ItemStack(RankineItems.QUICKLIME.get()), VanillaTypes.ITEM, "Quicklime can be obtained by cooking Limestone in a beehive oven.",
                "See Beehive Oven Pit for more details.");
        registry.addIngredientInfo(new ItemStack(RankineItems.MAGNESIA.get()), VanillaTypes.ITEM, "Magnesia can be obtained by cooking a Block of Magnesite in a beehive oven.",
                "See Beehive Oven Pit for more details.");
        registry.addIngredientInfo(new ItemStack(RankineBlocks.BEEHIVE_OVEN_PIT.get()), VanillaTypes.ITEM, "The beehive oven is at minimum a 3x3 structure with a beehive oven pit in the center of 8 refractory bricks. " +
                "The pit block must have access to the sky. Place blocks on the refractory bricks and light it with a flint and steel to cook them over time.");
        registry.addIngredientInfo(new ItemStack(RankineBlocks.EVAPORATION_TOWER.get()), VanillaTypes.ITEM, "The evaporation tower is a complex multiblock which requires sheetmetal, magma blocks, and the Evaporation Tower block." +
                " See the modpage or the Patchouli book for details.");
        registry.addIngredientInfo(new ItemStack(RankineItems.ELEMENT_INDEXER.get()), VanillaTypes.ITEM, "The Element Indexer is a device that can be used to analyze the properties of an element in an alloy. " +
                "Scroll up and down to change the percentage.");
        registry.addIngredientInfo(new ItemStack(RankineBlocks.TEMPLATE_TABLE.get()), VanillaTypes.ITEM, "The Alloy Template Table can make templates to automate the alloy furnace. " +
                "Paper and dye are required, and the template is determined by the arrangement of materials in the bottom row.");
        registry.addIngredientInfo(new ItemStack(RankineItems.ALLOY_TEMPLATE.get()), VanillaTypes.ITEM, "Created using the Alloy Template Table. " +
                "Paper and dye are required.");
        registry.addIngredientInfo(new ItemStack(RankineItems.FLINT_KNIFE.get()), VanillaTypes.ITEM, "Right-clicking on grass blocks allows you to obtain grass and convert the original block into dirt at an increased durability cost." +
                "The knife can also harvest grass and vines by left clicking.");
        registry.addIngredientInfo(new ItemStack(RankineItems.COMPOST.get()), VanillaTypes.ITEM, "Can be placed in the Composter.");

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new BeehiveOvenRecipeCategory(guiHelper));
        registry.addRecipeCategories(new CrushingRecipeCategory(guiHelper));
        registry.addRecipeCategories(new AlloyingRecipeCategory(guiHelper));
        registry.addRecipeCategories(new SluicingRecipeCategory(guiHelper));
        registry.addRecipeCategories(new EvaporationRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.ALLOY_FURNACE.get()), AlloyingRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.INDUCTION_FURNACE.get()), AlloyingRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.BEEHIVE_OVEN_PIT.get()), BeehiveOvenRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.HIGH_BEEHIVE_OVEN_PIT.get()), BeehiveOvenRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.ULTRA_HIGH_BEEHIVE_OVEN_PIT.get()), BeehiveOvenRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.PISTON_CRUSHER.get()), CrushingRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineItems.STEEL_GOLD_PAN.get()), SluicingRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.EVAPORATION_TOWER.get()), EvaporationRecipeCategory.UID);
    }

}
