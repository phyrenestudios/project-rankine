package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.recipe.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RankineRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, ProjectRankine.MODID);

    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AlloyCraftingRecipe>> ALLOY_CRAFTING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("alloy_crafting", AlloyCraftingRecipe.Serializer::new);
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CrushingRecipe>> CRUSHING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("crushing", CrushingRecipe.Serializer::new);
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AlloyingRecipe>> ALLOYING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("alloying", AlloyingRecipe.Serializer::new);
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BeehiveOvenRecipe>> BEEHIVE_OVEN_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("beehive_oven", BeehiveOvenRecipe.Serializer::new);
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SluicingRecipe>> SLUICING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("sluicing", SluicingRecipe.Serializer::new);
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ElementRecipe>> ELEMENT_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("element", ElementRecipe.Serializer::new);
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CrucibleRecipe>> CRUCIBLE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("crucible", CrucibleRecipe.Serializer::new);
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<EvaporationRecipe>> EVAPORATION_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("evaporation", EvaporationRecipe.Serializer::new);
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<FusionFurnaceRecipe>> FUSION_FURNACE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("fusion_furnace", FusionFurnaceRecipe.Serializer::new);
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<RockGeneratorRecipe>> ROCK_GENERATOR_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("rock_generator", RockGeneratorRecipe.Serializer::new);
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<TreetappingRecipe>> TREETAPPING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("treetapping", TreetappingRecipe.Serializer::new);
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<StrippingRecipe>> AXE_STRIPPING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("stripping", StrippingRecipe.Serializer::new);
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ForagingRecipe>> FORAGING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("foraging", ForagingRecipe.Serializer::new);
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AirDistillationRecipe>> AIR_DISTILLATION_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("air_distillation", AirDistillationRecipe.Serializer::new);
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MixingRecipe>> MIXING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("mixing", MixingRecipe.Serializer::new);
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AlloyModifierRecipe>> ALLOY_MODIFIER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("alloy_modifier", AlloyModifierRecipe.Serializer::new);
    public static DeferredHolder<RecipeSerializer<?>, SimpleCraftingRecipeSerializer<JamRecipe>> JAM_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_special_jam", () -> new SimpleCraftingRecipeSerializer<>(JamRecipe::new));

}
