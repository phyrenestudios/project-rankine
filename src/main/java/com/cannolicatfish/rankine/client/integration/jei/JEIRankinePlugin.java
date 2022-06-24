package com.cannolicatfish.rankine.client.integration.jei;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceScreen;
import com.cannolicatfish.rankine.blocks.crucible.CrucibleScreen;
import com.cannolicatfish.rankine.blocks.evaporationtower.EvaporationTowerScreen;
import com.cannolicatfish.rankine.blocks.fusionfurnace.FusionFurnaceScreen;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceScreen;
import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelScreen;
import com.cannolicatfish.rankine.client.integration.jei.categories.*;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.recipe.StrippingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.registration.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.Tags;

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
        registration.addRecipeClickArea(EvaporationTowerScreen.class, 76, 50, 24, 16, EvaporationRecipeCategory.UID);
        registration.addRecipeClickArea(CrucibleScreen.class, 109, 46, 7, 26, CrucibleRecipeCategory.UID);
        registration.addRecipeClickArea(MixingBarrelScreen.class, 109, 46, 7, 26, MixingRecipeCategory.UID);
        registration.addRecipeClickArea(FusionFurnaceScreen.class, 78, 34, 24, 16, FusionFurnaceRecipeCategory.UID);

    }

    private static List<ElementRecipe> getSortedElementRecipes() {
        RankineJEIRecipes rankineJEIRecipes = new RankineJEIRecipes();
        List<ElementRecipe> elements = rankineJEIRecipes.getElementRecipes();
        final Comparator<ElementRecipe> BY_ATOMIC_NUM = Comparator.comparing(ElementRecipe::getAtomicNumber);
        elements.sort(BY_ATOMIC_NUM);
        return elements;
    }

    private static <T extends Recipe<?>> List<T> getSortedRecipes(List<T> recipes) {
        final Comparator<Recipe<?>> BY_ID = Comparator.comparing(Recipe::getId);
        recipes.sort(BY_ID);
        return recipes;
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registry) {
        RankineJEIRecipes rankineJEIRecipes = new RankineJEIRecipes();
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getMixingRecipes()), MixingRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getBeehiveRecipes()), BeehiveOvenRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getSluicingRecipes()), SluicingRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getEvaporationRecipes()), EvaporationRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getAlloyFurnaceRecipes()), AlloyingRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getInductionFurnaceRecipes()), InductionAlloyingRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getFusionFurnaceRecipes()), FusionFurnaceRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getTreetappingRecipes()), TreetappingRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getStrippingRecipes()), StrippingRecipeCategory.UID);
        registry.addRecipes(getSortedElementRecipes(), ElementRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getCrucibleRecipes()), CrucibleRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getIntrusiveGeneratorRecipes()), IntrusiveGeneratorRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getExtrusiveGeneratorRecipes()), ExtrusiveGeneratorRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getSedimentaryGeneratorRecipes()), SedimentaryGeneratorRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getMetamorphicGeneratorRecipes()), MetamorphicGeneratorRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getVolcanicGeneratorRecipes()), VolcanicGeneratorRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getAirDistillationRecipes()), AirDistillationRecipeCategory.UID);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_DUST.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_GEAR.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_BLOCK.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_INGOT.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_NUGGET.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_PLATE.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_ROD.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_WIRE.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));

        registration.registerSubtypeInterpreter(RankineItems.ALLOY_PICKAXE.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_AXE.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_SHOVEL.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_SWORD.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_HOE.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_SPEAR.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_HAMMER.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_KNIFE.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_CROWBAR.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_BLUNDERBUSS.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_SURF_ROD.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));

        registration.registerSubtypeInterpreter(RankineItems.ALLOY_HELMET.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_CHESTPLATE.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_LEGGINGS.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_BOOTS.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(RankineItems.ALLOY_ARROW.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new MixingRecipeCategory(guiHelper));
        registry.addRecipeCategories(new BeehiveOvenRecipeCategory(guiHelper));
        registry.addRecipeCategories(new AlloyingRecipeCategory(guiHelper));
        registry.addRecipeCategories(new InductionAlloyingRecipeCategory(guiHelper));
        registry.addRecipeCategories(new FusionFurnaceRecipeCategory(guiHelper));
        registry.addRecipeCategories(new SluicingRecipeCategory(guiHelper));
        registry.addRecipeCategories(new ElementRecipeCategory(guiHelper));
        registry.addRecipeCategories(new EvaporationRecipeCategory(guiHelper));
        registry.addRecipeCategories(new CrucibleRecipeCategory(guiHelper));
        registry.addRecipeCategories(new IntrusiveGeneratorRecipeCategory(guiHelper));
        registry.addRecipeCategories(new ExtrusiveGeneratorRecipeCategory(guiHelper));
        registry.addRecipeCategories(new SedimentaryGeneratorRecipeCategory(guiHelper));
        registry.addRecipeCategories(new MetamorphicGeneratorRecipeCategory(guiHelper));
        registry.addRecipeCategories(new VolcanicGeneratorRecipeCategory(guiHelper));
        registry.addRecipeCategories(new AirDistillationRecipeCategory(guiHelper));
        registry.addRecipeCategories(new TreetappingRecipeCategory(guiHelper));
        registry.addRecipeCategories(new StrippingRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.MIXING_BARREL.get()), MixingRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.ALLOY_FURNACE.get()), AlloyingRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.INDUCTION_FURNACE.get()), InductionAlloyingRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.FUSION_FURNACE.get()), FusionFurnaceRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.BEEHIVE_OVEN_PIT.get()), BeehiveOvenRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineItems.STEEL_GOLD_PAN.get()), SluicingRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineItems.ELEMENT_INDEXER.get()), ElementRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.EVAPORATION_TOWER.get()), EvaporationRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.CRUCIBLE_BLOCK.get()), CrucibleRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(Blocks.COBBLESTONE), IntrusiveGeneratorRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(Blocks.BLACKSTONE), ExtrusiveGeneratorRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.BRECCIA.get()), SedimentaryGeneratorRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.SKARN.get()), MetamorphicGeneratorRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(Blocks.OBSIDIAN), VolcanicGeneratorRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.DISTILLATION_TOWER.get()), AirDistillationRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.TREE_TAP.get()), TreetappingRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(Items.IRON_AXE), StrippingRecipeCategory.UID);
    }

}
