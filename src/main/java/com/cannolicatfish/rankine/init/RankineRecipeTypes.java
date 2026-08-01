package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.recipe.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RankineRecipeTypes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, ProjectRankine.MODID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<AlloyingRecipe>> ALLOYING = RECIPE_TYPES.register("alloying", () -> RecipeType.simple(rl("alloying")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<CrushingRecipe>> CRUSHING = RECIPE_TYPES.register("crushing", () -> RecipeType.simple(rl("crushing")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<BeehiveOvenRecipe>> BEEHIVE = RECIPE_TYPES.register("beehive_oven", () -> RecipeType.simple(rl("beehive_oven")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<SluicingRecipe>> SLUICING = RECIPE_TYPES.register("sluicing", () -> RecipeType.simple(rl("sluicing")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<ElementRecipe>> ELEMENT = RECIPE_TYPES.register("element", () -> RecipeType.simple(rl("element")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<EvaporationRecipe>> EVAPORATION = RECIPE_TYPES.register("evaporation", () -> RecipeType.simple(rl("evaporation")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<CrucibleRecipe>> CRUCIBLE = RECIPE_TYPES.register("crucible", () -> RecipeType.simple(rl("crucible")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<RockGeneratorRecipe>> ROCK_GENERATOR = RECIPE_TYPES.register("rock_generator", () -> RecipeType.simple(rl("rock_generator")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<FusionFurnaceRecipe>> FUSION_FURNACE = RECIPE_TYPES.register("fusion_furnace", () -> RecipeType.simple(rl("fusion_furnace")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<TreetappingRecipe>> TREETAPPING = RECIPE_TYPES.register("treetapping", () -> RecipeType.simple(rl("treetapping")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<StrippingRecipe>> STRIPPING = RECIPE_TYPES.register("stripping", () -> RecipeType.simple(rl("stripping")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<ForagingRecipe>> FORAGING = RECIPE_TYPES.register("foraging", () -> RecipeType.simple(rl("foraging")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<AirDistillationRecipe>> AIR_DISTILLATION = RECIPE_TYPES.register("air_distillation", () -> RecipeType.simple(rl("air_distillation")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<MixingRecipe>> MIXING = RECIPE_TYPES.register("mixing", () -> RecipeType.simple(rl("mixing")));
    public static final DeferredHolder<RecipeType<?>, RecipeType<AlloyModifierRecipe>> ALLOY_MODIFIER = RECIPE_TYPES.register("alloy_modifier", () -> RecipeType.simple(rl("alloy_modifier")));

    private static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, path);
    }
}
