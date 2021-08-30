package com.cannolicatfish.rankine.client.integration.jei;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceScreen;
import com.cannolicatfish.rankine.blocks.crucible.CrucibleScreen;
import com.cannolicatfish.rankine.blocks.evaporationtower.EvaporationTowerScreen;
import com.cannolicatfish.rankine.blocks.gyratorycrusher.GyratoryCrusherScreen;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceScreen;
import com.cannolicatfish.rankine.blocks.pistoncrusher.PistonCrusherScreen;
import com.cannolicatfish.rankine.client.integration.jei.categories.*;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineRecipes;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.CrushingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.gui.screen.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.List;

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
        registration.addRecipeClickArea(PistonCrusherScreen.class, 55, 58, 24, 16, CrushingRecipeCategory.UID);
        registration.addRecipeClickArea(GyratoryCrusherScreen.class, 55, 58, 24, 16, CrushingRecipeCategory.UID);
        registration.addRecipeClickArea(EvaporationTowerScreen.class, 76, 50, 24, 16, EvaporationRecipeCategory.UID);
        registration.addRecipeClickArea(CrucibleScreen.class, 109, 46, 7, 26, CrucibleRecipeCategory.UID);


    }

    private static List<ElementRecipe> getSortedElementRecipes() {
        RankineJEIRecipes rankineJEIRecipes = new RankineJEIRecipes();
        List<ElementRecipe> elements = rankineJEIRecipes.getElementRecipes();
        final Comparator<ElementRecipe> BY_ATOMIC_NUM = Comparator.comparing(ElementRecipe::getAtomicNumber);
        elements.sort(BY_ATOMIC_NUM);
        return elements;
    }

    private static <T extends IRecipe<?>> List<T> getSortedRecipes(List<T> recipes) {
        final Comparator<IRecipe<?>> BY_ID = Comparator.comparing(IRecipe::getId);
        recipes.sort(BY_ID);
        return recipes;
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registry) {
        RankineJEIRecipes rankineJEIRecipes = new RankineJEIRecipes();
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getBeehiveRecipes()), BeehiveOvenRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getSluicingRecipes()), SluicingRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getEvaporationRecipes()), EvaporationRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getCrushingRecipes()), CrushingRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getAlloyFurnaceRecipes()), AlloyingRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getInductionFurnaceRecipes()), InductionAlloyingRecipeCategory.UID);
        registry.addRecipes(getSortedElementRecipes(), ElementRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getCrucibleRecipes()), CrucibleRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getIntrusiveGeneratorRecipes()), IntrusiveGeneratorRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getExtrusiveGeneratorRecipes()), ExtrusiveGeneratorRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getSedimentaryGeneratorRecipes()), SedimentaryGeneratorRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getMetamorphicGeneratorRecipes()), MetamorphicGeneratorRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getVolcanicGeneratorRecipes()), VolcanicGeneratorRecipeCategory.UID);
        registry.addIngredientInfo(new ItemStack(RankineItems.COKE.get()), VanillaTypes.ITEM, "Coke can be obtained by cooking Bituminous Coal Blocks in a beehive oven.",
                "See Beehive Oven Pit for more details.");
        registry.addIngredientInfo(new ItemStack(RankineItems.QUICKLIME.get()), VanillaTypes.ITEM, "Quicklime can be obtained by cooking Limestone in a beehive oven.",
                "See Beehive Oven Pit for more details.");
        registry.addIngredientInfo(new ItemStack(RankineItems.MAGNESIA.get()), VanillaTypes.ITEM, "Magnesia can be obtained by cooking a Block of Magnesite in a beehive oven.",
                "See Beehive Oven Pit for more details.");
        registry.addIngredientInfo(new ItemStack(RankineBlocks.BEEHIVE_OVEN_PIT.get()), VanillaTypes.ITEM, "The beehive oven is at minimum a 3x3 structure with a beehive oven pit in the center of 8 refractory bricks. " +
                "The pit block must have access to the sky. Place blocks on the refractory bricks and light it to cook them over time.");
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
        registry.addIngredientInfo(new ItemStack(RankineItems.GAS_MASK.get()), VanillaTypes.ITEM, "The gas mask prevents negative effects from standing in gas blocks when worn. Combine with a helmet in an anvil to add the enchantment Gas Protection which has the same effect.");
        registry.addIngredientInfo(new ItemStack(RankineItems.SANDALS.get()), VanillaTypes.ITEM, "Sandals increase movement speed on sand blocks when worn. Combine with boots in an anvil to add the enchantment Dune Walker which has the same effect.");
        registry.addIngredientInfo(new ItemStack(RankineItems.SNOWSHOES.get()), VanillaTypes.ITEM, "Snowshoes increase movement speed on snow blocks when worn. Combine with boots in an anvil to add the enchantment Snow Drifter which has the same effect.");
        registry.addIngredientInfo(new ItemStack(RankineItems.ICE_SKATES.get()), VanillaTypes.ITEM, "Ice Skates increase movement speed on ice blocks when worn. Combine with boots in an anvil to add the enchantment Speed Skater which has the same effect.");
        //Tools
        registry.addIngredientInfo(new ItemStack(Items.COMPASS), VanillaTypes.ITEM, "While held, the compass will display the holder's coordinates and direction. Default is head position, sneak for position at feet.");
        registry.addIngredientInfo(new ItemStack(Items.CLOCK), VanillaTypes.ITEM, "While held, the clock will display the current game time on a 24 hour clock and in ticks.");
        registry.addIngredientInfo(new ItemStack(RankineItems.ALTIMETER.get()), VanillaTypes.ITEM, "While held, the altimeter will display the holder's Y value. Default is head position, sneak for position at feet.");
        registry.addIngredientInfo(new ItemStack(RankineItems.PHOTOMETER.get()), VanillaTypes.ITEM, "While held, the photometer will display the current light level. Default is head position, sneak for position at feet.");
        registry.addIngredientInfo(new ItemStack(RankineItems.THERMOMETER.get()), VanillaTypes.ITEM, "While held, the thermometer will display the current block temperature. Default is head position, sneak for position at feet.");
        registry.addIngredientInfo(new ItemStack(RankineBlocks.RANKINE_BOX.get()), VanillaTypes.ITEM, "The Rankine Box is a machine that allows you to transmute between different elements. " +
                "To transmute an element, you must provide a Power Cell and a Directional Element Transmuter (Up, Down, Left, and Right). The direction determines which element you will receive.");
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_DUST.get(), IAlloyItem::getSubtype);
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_GEAR.get(), IAlloyItem::getSubtype);
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_INGOT.get(), IAlloyItem::getSubtype);
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_PLATE.get(), IAlloyItem::getSubtype);
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_ROD.get(), IAlloyItem::getSubtype);
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_WIRE.get(), IAlloyItem::getSubtype);

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new BeehiveOvenRecipeCategory(guiHelper));
        registry.addRecipeCategories(new CrushingRecipeCategory(guiHelper));
        registry.addRecipeCategories(new AlloyingRecipeCategory(guiHelper));
        registry.addRecipeCategories(new InductionAlloyingRecipeCategory(guiHelper));
        registry.addRecipeCategories(new SluicingRecipeCategory(guiHelper));
        registry.addRecipeCategories(new ElementRecipeCategory(guiHelper));
        registry.addRecipeCategories(new EvaporationRecipeCategory(guiHelper));
        registry.addRecipeCategories(new CrucibleRecipeCategory(guiHelper));
        registry.addRecipeCategories(new IntrusiveGeneratorRecipeCategory(guiHelper));
        registry.addRecipeCategories(new ExtrusiveGeneratorRecipeCategory(guiHelper));
        registry.addRecipeCategories(new SedimentaryGeneratorRecipeCategory(guiHelper));
        registry.addRecipeCategories(new MetamorphicGeneratorRecipeCategory(guiHelper));
        registry.addRecipeCategories(new VolcanicGeneratorRecipeCategory(guiHelper));

    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.ALLOY_FURNACE.get()), AlloyingRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.INDUCTION_FURNACE.get()), InductionAlloyingRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.BEEHIVE_OVEN_PIT.get()), BeehiveOvenRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.HIGH_BEEHIVE_OVEN_PIT.get()), BeehiveOvenRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.ULTRA_HIGH_BEEHIVE_OVEN_PIT.get()), BeehiveOvenRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.PISTON_CRUSHER.get()), CrushingRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.GYRATORY_CRUSHER.get()), CrushingRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineItems.STEEL_GOLD_PAN.get()), SluicingRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineItems.ELEMENT_INDEXER.get()), ElementRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.EVAPORATION_TOWER.get()), EvaporationRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.CRUCIBLE_BLOCK.get()), CrucibleRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(Blocks.COBBLESTONE), IntrusiveGeneratorRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(Blocks.BLACKSTONE), ExtrusiveGeneratorRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.BRECCIA.get()), SedimentaryGeneratorRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.SKARN.get()), MetamorphicGeneratorRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(Blocks.OBSIDIAN), VolcanicGeneratorRecipeCategory.UID);
    }

}
