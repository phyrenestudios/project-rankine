package com.cannolicatfish.rankine.client.integration.jei.recipes;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.recipe.*;
import mezz.jei.api.recipe.RecipeType;

public class RankineJEIRecipeTypes {
    public static final RecipeType<AlloyingRecipe> ALLOYING_RECIPE_TYPE =
            RecipeType.create(ProjectRankine.MODID, "alloying", AlloyingRecipe.class);

    public static final RecipeType<AlloyingRecipe> INDUCTION_ALLOYING_RECIPE_TYPE =
            RecipeType.create(ProjectRankine.MODID, "induction_alloying", AlloyingRecipe.class);

    public static final RecipeType<CrushingRecipe> CRUSHING_RECIPE_TYPE =
            RecipeType.create(ProjectRankine.MODID, "crushing", CrushingRecipe.class);

    public static final RecipeType<BeehiveOvenRecipe> BEEHIVE_OVEN_RECIPE_TYPE =
            RecipeType.create(ProjectRankine.MODID, "beehive_oven", BeehiveOvenRecipe.class);

    public static final RecipeType<SluicingRecipe> SLUICING_RECIPE_TYPE =
            RecipeType.create(ProjectRankine.MODID, "sluicing", SluicingRecipe.class);

    public static final RecipeType<ElementRecipe> ELEMENT_RECIPE_TYPE =
            RecipeType.create(ProjectRankine.MODID, "element", ElementRecipe.class);

    public static final RecipeType<EvaporationRecipe> EVAPORATION_RECIPE_TYPE =
            RecipeType.create(ProjectRankine.MODID, "evaporation", EvaporationRecipe.class);

    public static final RecipeType<CrucibleRecipe> CRUCIBLE_RECIPE_TYPE =
            RecipeType.create(ProjectRankine.MODID, "crucible", CrucibleRecipe.class);

    public static final RecipeType<RockGeneratorRecipe> ROCK_GENERATOR_RECIPE_TYPE =
            RecipeType.create(ProjectRankine.MODID, "rock_generator", RockGeneratorRecipe.class);

    public static final RecipeType<FusionFurnaceRecipe> FUSION_FURNACE_RECIPE_TYPE =
            RecipeType.create(ProjectRankine.MODID, "fusion_furnace", FusionFurnaceRecipe.class);

    public static final RecipeType<TreetappingRecipe> TREETAPPING_RECIPE_TYPE =
            RecipeType.create(ProjectRankine.MODID, "treetapping", TreetappingRecipe.class);

    public static final RecipeType<StrippingRecipe> AXE_STRIPPING_RECIPE_TYPE =
            RecipeType.create(ProjectRankine.MODID, "axe_stripping", StrippingRecipe.class);

    public static final RecipeType<ForagingRecipe> FORAGING_RECIPE_TYPE =
            RecipeType.create(ProjectRankine.MODID, "foraging", ForagingRecipe.class);

    public static final RecipeType<AirDistillationRecipe> AIR_DISTILLATION_RECIPE_TYPE =
            RecipeType.create(ProjectRankine.MODID, "air_distillation", AirDistillationRecipe.class);

    public static final RecipeType<MixingRecipe> MIXING_RECIPE_TYPE =
            RecipeType.create(ProjectRankine.MODID, "mixing", MixingRecipe.class);

    public static final RecipeType<AlloyModifierRecipe> ALLOY_MODIFIER_RECIPE_TYPE =
            RecipeType.create(ProjectRankine.MODID, "alloy_modifier", AlloyModifierRecipe.class);

    public static final RecipeType<BatteryRecipe> BATTERY_RECIPE_TYPE =
            RecipeType.create(ProjectRankine.MODID, "battery", BatteryRecipe.class);
    public static final RecipeType<RankineCauldronDryingRecipe> CAULDRON_DRYING_RECIPE_TYPE =
            RecipeType.create(ProjectRankine.MODID, "cauldron_drying", RankineCauldronDryingRecipe.class);
}
