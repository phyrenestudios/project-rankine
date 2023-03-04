package com.cannolicatfish.rankine.client.integration.jei;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceScreen;
import com.cannolicatfish.rankine.blocks.crucible.CrucibleScreen;
import com.cannolicatfish.rankine.blocks.evaporationtower.EvaporationTowerScreen;
import com.cannolicatfish.rankine.blocks.fusionfurnace.FusionFurnaceScreen;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceScreen;
import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelScreen;
import com.cannolicatfish.rankine.client.integration.jei.categories.*;
import com.cannolicatfish.rankine.client.integration.jei.recipes.IBatteryRecipe;
import com.cannolicatfish.rankine.client.integration.jei.recipes.IRankineCauldronRecipe;
import com.cannolicatfish.rankine.client.integration.jei.recipes.RankineJEIRecipes;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import com.cannolicatfish.rankine.init.RankineTags;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.*;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.List;

@JeiPlugin
public class JEIRankinePlugin implements IModPlugin {

    public static final RecipeType<AirDistillationRecipe> AIR_DISTILLATION = RecipeType.create("rankine","air_distillation", AirDistillationRecipe.class);
    public static final RecipeType<AlloyingRecipe> ALLOYING = RecipeType.create("rankine","alloying", AlloyingRecipe.class);
    public static final RecipeType<IBatteryRecipe> BATTERY = RecipeType.create("rankine","battery", IBatteryRecipe.class);
    public static final RecipeType<BeehiveOvenRecipe> BEEHIVE_OVEN = RecipeType.create("rankine","beehive_oven", BeehiveOvenRecipe.class);
    public static final RecipeType<IRankineCauldronRecipe> CAULDRON_DRYING = RecipeType.create("rankine","cauldron_drying", IRankineCauldronRecipe.class);
    public static final RecipeType<CrucibleRecipe> CRUCIBLE = RecipeType.create("rankine","crucible", CrucibleRecipe.class);
    public static final RecipeType<CrushingRecipe> CRUSHING = RecipeType.create("rankine","crushing", CrushingRecipe.class);
    public static final RecipeType<ElementRecipe> ELEMENT = RecipeType.create("rankine","element", ElementRecipe.class);
    public static final RecipeType<EvaporationRecipe> EVAPORATION = RecipeType.create("rankine","evaporation", EvaporationRecipe.class);
    public static final RecipeType<RockGeneratorRecipe> EXTRUSIVE_IGNEOUS = RecipeType.create("rankine","extrusive_igneous", RockGeneratorRecipe.class);
    public static final RecipeType<ForagingRecipe> FORAGING = RecipeType.create("rankine","foraging", ForagingRecipe.class);
    public static final RecipeType<FusionFurnaceRecipe> FUSION_FURNACE = RecipeType.create("rankine","fusion_furnace", FusionFurnaceRecipe.class);
    public static final RecipeType<AlloyingRecipe> INDUCTION_ALLOYING = RecipeType.create("rankine","induction_alloying", AlloyingRecipe.class);
    public static final RecipeType<RockGeneratorRecipe> INTRUSIVE_IGNEOUS = RecipeType.create("rankine","intrusive_igneous", RockGeneratorRecipe.class);
    public static final RecipeType<RockGeneratorRecipe> METAMORPHIC = RecipeType.create("rankine","metamorphic", RockGeneratorRecipe.class);
    public static final RecipeType<MixingRecipe> MIXING = RecipeType.create("rankine","mixing", MixingRecipe.class);
    public static final RecipeType<RockGeneratorRecipe> SEDIMENTARY = RecipeType.create("rankine","sedimentary", RockGeneratorRecipe.class);
    public static final RecipeType<SluicingRecipe> SLUICING = RecipeType.create("rankine","sluicing", SluicingRecipe.class);
    public static final RecipeType<StrippingRecipe> STRIPPING = RecipeType.create("rankine","stripping", StrippingRecipe.class);
    public static final RecipeType<TreetappingRecipe> TREETAPPING = RecipeType.create("rankine","treetapping", TreetappingRecipe.class);
    public static final RecipeType<RockGeneratorRecipe> VOLCANIC = RecipeType.create("rankine","volcanic", RockGeneratorRecipe.class);

    @Nonnull
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ProjectRankine.MODID, "jei_plugin");
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(AlloyFurnaceScreen.class, 98, 32, 24, 16, ALLOYING);
        registration.addRecipeClickArea(InductionFurnaceScreen.class, 98, 32, 24, 16, INDUCTION_ALLOYING);
        registration.addRecipeClickArea(EvaporationTowerScreen.class, 76, 50, 24, 16, EVAPORATION);
        registration.addRecipeClickArea(CrucibleScreen.class, 109, 46, 7, 26, CRUCIBLE);
        registration.addRecipeClickArea(MixingBarrelScreen.class, 109, 46, 7, 26, MIXING);
        registration.addRecipeClickArea(FusionFurnaceScreen.class, 78, 34, 24, 16, FUSION_FURNACE);

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

    private static List<IBatteryRecipe> getSortedBatteryRecipes(List<IBatteryRecipe> recipes) {
        final Comparator<IBatteryRecipe> BY_ID = Comparator.comparing(IBatteryRecipe::getBatteryCharge);
        recipes.sort(BY_ID);
        return recipes;
    }

    @Override
    public void registerRecipes(@Nonnull IRecipeRegistration registry) {
        RankineJEIRecipes rankineJEIRecipes = new RankineJEIRecipes();
        registry.addRecipes(AIR_DISTILLATION, getSortedRecipes(rankineJEIRecipes.getAirDistillationRecipes()));
        registry.addRecipes(ALLOYING, getSortedRecipes(rankineJEIRecipes.getAlloyFurnaceRecipes()));
        registry.addRecipes(BEEHIVE_OVEN, getSortedRecipes(rankineJEIRecipes.getBeehiveRecipes()));
        registry.addRecipes(CRUCIBLE, getSortedRecipes(rankineJEIRecipes.getCrucibleRecipes()));
        registry.addRecipes(CRUSHING, getSortedRecipes(rankineJEIRecipes.getCrushingRecipes()));
        registry.addRecipes(ELEMENT, getSortedElementRecipes());
        registry.addRecipes(EVAPORATION, getSortedRecipes(rankineJEIRecipes.getEvaporationRecipes()));
        registry.addRecipes(EXTRUSIVE_IGNEOUS, getSortedRecipes(rankineJEIRecipes.getExtrusiveGeneratorRecipes()));
        registry.addRecipes(FORAGING, getSortedRecipes(rankineJEIRecipes.getForagingRecipes()));
        registry.addRecipes(FUSION_FURNACE, getSortedRecipes(rankineJEIRecipes.getFusionFurnaceRecipes()));
        registry.addRecipes(INDUCTION_ALLOYING, getSortedRecipes(rankineJEIRecipes.getInductionFurnaceRecipes()));
        registry.addRecipes(INTRUSIVE_IGNEOUS, getSortedRecipes(rankineJEIRecipes.getIntrusiveGeneratorRecipes()));
        registry.addRecipes(METAMORPHIC, getSortedRecipes(rankineJEIRecipes.getMetamorphicGeneratorRecipes()));
        registry.addRecipes(MIXING, getSortedRecipes(rankineJEIRecipes.getMixingRecipes()));
        registry.addRecipes(SEDIMENTARY, getSortedRecipes(rankineJEIRecipes.getSedimentaryGeneratorRecipes()));
        registry.addRecipes(SLUICING, getSortedRecipes(rankineJEIRecipes.getSluicingRecipes()));
        registry.addRecipes(STRIPPING, getSortedRecipes(rankineJEIRecipes.getStrippingRecipes()));
        registry.addRecipes(TREETAPPING, getSortedRecipes(rankineJEIRecipes.getTreetappingRecipes()));
        registry.addRecipes(VOLCANIC, getSortedRecipes(rankineJEIRecipes.getVolcanicGeneratorRecipes()));
        registry.addRecipes(BATTERY,getSortedBatteryRecipes(rankineJEIRecipes.getBatteryRecipes()));
        registry.addRecipes(CAULDRON_DRYING,rankineJEIRecipes.getCauldronRecipes());
        /*registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getAlloyFurnaceRecipes()), AlloyingRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getInductionFurnaceRecipes()), InductionAlloyingRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getIntrusiveGeneratorRecipes()), IntrusiveGeneratorRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getExtrusiveGeneratorRecipes()), ExtrusiveGeneratorRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getSedimentaryGeneratorRecipes()), SedimentaryGeneratorRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getMetamorphicGeneratorRecipes()), MetamorphicGeneratorRecipeCategory.UID);
        registry.addRecipes(getSortedRecipes(rankineJEIRecipes.getVolcanicGeneratorRecipes()), VolcanicGeneratorRecipeCategory.UID);*/

        /*registry.addIngredientInfo(new ItemStack(RankineItems.DRY_RUBBER.get()), VanillaTypes.ITEM_STACK,new TranslatableComponent("rankine.jei.info_dry_rubber"));
        registry.addIngredientInfo(new ItemStack(RankineItems.AMBER.get()), VanillaTypes.ITEM_STACK,new TranslatableComponent("rankine.jei.info_amber"));

        registry.addIngredientInfo(new FluidStack(RankineFluids.SAP,1000), ForgeTypes.FLUID_STACK,new TranslatableComponent("rankine.jei.info_sap"));
        registry.addIngredientInfo(new FluidStack(RankineFluids.MAPLE_SAP,1000), ForgeTypes.FLUID_STACK,new TranslatableComponent("rankine.jei.info_maple_sap"));
        registry.addIngredientInfo(new FluidStack(RankineFluids.LATEX,1000), ForgeTypes.FLUID_STACK,new TranslatableComponent("rankine.jei.info_latex"));
        registry.addIngredientInfo(new FluidStack(RankineFluids.RESIN,1000), ForgeTypes.FLUID_STACK,new TranslatableComponent("rankine.jei.info_resin"));
        registry.addIngredientInfo(new FluidStack(RankineFluids.JUGLONE,1000), ForgeTypes.FLUID_STACK,new TranslatableComponent("rankine.jei.info_juglone"));*/
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK,RankineItems.ALLOY_DUST.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK,RankineItems.ALLOY_GEAR.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK,RankineItems.ALLOY_BLOCK.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK,RankineItems.ALLOY_INGOT.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK,RankineItems.ALLOY_NUGGET.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK,RankineItems.ALLOY_PLATE.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK,RankineItems.ALLOY_ROD.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK,RankineItems.ALLOY_WIRE.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));

        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK,RankineItems.ALLOY_PICKAXE.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK,RankineItems.ALLOY_AXE.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK,RankineItems.ALLOY_SHOVEL.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK,RankineItems.ALLOY_SWORD.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK,RankineItems.ALLOY_HOE.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK,RankineItems.ALLOY_SPEAR.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK,RankineItems.ALLOY_HAMMER.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK,RankineItems.ALLOY_KNIFE.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK,RankineItems.ALLOY_CROWBAR.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK,RankineItems.ALLOY_BLUNDERBUSS.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));

        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK,RankineItems.ALLOY_HELMET.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK,RankineItems.ALLOY_CHESTPLATE.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK,RankineItems.ALLOY_LEGGINGS.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK,RankineItems.ALLOY_BOOTS.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK,RankineItems.ALLOY_ARROW.get(), (ingredient, context) -> IAlloyItem.getSubtype(ingredient));
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new MixingRecipeCategory(guiHelper));
        registry.addRecipeCategories(new BeehiveOvenRecipeCategory(guiHelper));
        registry.addRecipeCategories(new AlloyingRecipeCategory(guiHelper));
        registry.addRecipeCategories(new CrushingRecipeCategory(guiHelper));
        registry.addRecipeCategories(new ForagingRecipeCategory(guiHelper));
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
        registry.addRecipeCategories(new BatteryRecipeCategory(guiHelper));
        registry.addRecipeCategories(new CauldronDryingRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        for (Item item: RankineLists.HAMMERS) {
            registry.addRecipeCatalyst(new ItemStack(item), CRUSHING);
        }
        for (ItemStack stack : Ingredient.of(RankineTags.Items.AXES).getItems()) {
            registry.addRecipeCatalyst(stack, STRIPPING);
        }
        for (Block blk: RankineLists.CRUSHING_HEADS) {
            registry.addRecipeCatalyst(new ItemStack(blk), CRUSHING);
        }
        for (Item item : RankineLists.FORAGING_TOOLS) {
            registry.addRecipeCatalyst(new ItemStack(item), FORAGING);
        }
        for (ItemStack stack : Ingredient.of(RankineTags.Items.GOLD_PANS).getItems()) {
            registry.addRecipeCatalyst(stack, SLUICING);
        }
        registry.addRecipeCatalyst(new ItemStack(RankineItems.FLINT_SHOVEL.get()), FORAGING);

        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.BEEHIVE_OVEN_PIT.get()), BEEHIVE_OVEN);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.REFRACTORY_BRICKS.getBricksBlock()),BEEHIVE_OVEN);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.HIGH_REFRACTORY_BRICKS.getBricksBlock()),BEEHIVE_OVEN);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.ULTRA_HIGH_REFRACTORY_BRICKS.getBricksBlock()),BEEHIVE_OVEN);

        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.MIXING_BARREL.get()), MIXING);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.ALLOY_FURNACE.get()), ALLOYING);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.INDUCTION_FURNACE.get()), INDUCTION_ALLOYING);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.FUSION_FURNACE.get()), FUSION_FURNACE);

        registry.addRecipeCatalyst(new ItemStack(Items.CAULDRON), CAULDRON_DRYING);

        registry.addRecipeCatalyst(new ItemStack(RankineItems.ELEMENT_INDEXER.get()), ELEMENT);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.EVAPORATION_TOWER.get()), EVAPORATION);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.HEATING_ELEMENT_1.get()), EVAPORATION);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.CRUCIBLE_BLOCK.get()), CRUCIBLE);
        registry.addRecipeCatalyst(new ItemStack(Blocks.COBBLESTONE), INTRUSIVE_IGNEOUS);
        registry.addRecipeCatalyst(new ItemStack(Blocks.BLACKSTONE), EXTRUSIVE_IGNEOUS);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.BRECCIA.get()), SEDIMENTARY);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.SEDIMENT_FAN.get()), SEDIMENTARY);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.SKARN.get()), METAMORPHIC);
        registry.addRecipeCatalyst(new ItemStack(Blocks.OBSIDIAN), VOLCANIC);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.DISTILLATION_TOWER.get()), AIR_DISTILLATION);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.AIR_DISTILLATION_PACKING.get()), AIR_DISTILLATION);
        registry.addRecipeCatalyst(new ItemStack(RankineBlocks.TREE_TAP.get()), TREETAPPING);
    }

}
