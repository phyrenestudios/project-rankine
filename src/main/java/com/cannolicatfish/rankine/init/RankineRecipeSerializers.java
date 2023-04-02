package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.recipe.*;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RankineRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS.getRegistryName(), ProjectRankine.MODID);

    public static RegistryObject<RecipeSerializer<AlloyCraftingRecipe>> ALLOY_CRAFTING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("alloy_crafting", AlloyCraftingRecipe.Serializer::new);
    public static RegistryObject<RecipeSerializer<CrushingRecipe>> CRUSHING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("crushing", CrushingRecipe.Serializer::new);
    public static RegistryObject<RecipeSerializer<AlloyingRecipe>> ALLOYING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("alloying", AlloyingRecipe.Serializer::new);
    public static RegistryObject<RecipeSerializer<BeehiveOvenRecipe>> BEEHIVE_OVEN_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("beehive_oven", BeehiveOvenRecipe.Serializer::new);
    public static RegistryObject<RecipeSerializer<SluicingRecipe>> SLUICING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("sluicing", SluicingRecipe.Serializer::new);
    public static RegistryObject<RecipeSerializer<ElementRecipe>> ELEMENT_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("element", ElementRecipe.Serializer::new);
    public static RegistryObject<RecipeSerializer<CrucibleRecipe>> CRUCIBLE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("crucible", CrucibleRecipe.Serializer::new);
    public static RegistryObject<RecipeSerializer<EvaporationRecipe>> EVAPORATION_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("evaporation", EvaporationRecipe.Serializer::new);
    public static RegistryObject<RecipeSerializer<FusionFurnaceRecipe>> FUSION_FURNACE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("fusion_furnace", FusionFurnaceRecipe.Serializer::new);
    public static RegistryObject<RecipeSerializer<RockGeneratorRecipe>> ROCK_GENERATOR_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("rock_generator", RockGeneratorRecipe.Serializer::new);
    public static RegistryObject<RecipeSerializer<TreetappingRecipe>> TREETAPPING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("treetapping", TreetappingRecipe.Serializer::new);
    public static RegistryObject<RecipeSerializer<StrippingRecipe>> AXE_STRIPPING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("stripping", StrippingRecipe.Serializer::new);
    public static RegistryObject<RecipeSerializer<ForagingRecipe>> FORAGING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("foraging", ForagingRecipe.Serializer::new);
    public static RegistryObject<RecipeSerializer<AirDistillationRecipe>> AIR_DISTILLATION_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("air_distillation", AirDistillationRecipe.Serializer::new);
    public static RegistryObject<RecipeSerializer<MixingRecipe>> MIXING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("mixing", MixingRecipe.Serializer::new);
    public static RegistryObject<RecipeSerializer<AlloyModifierRecipe>> ALLOY_MODIFIER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("alloy_modifier", AlloyModifierRecipe.Serializer::new);
    public static RegistryObject<SimpleRecipeSerializer<JamRecipe>> JAM_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_special_jam", () -> new SimpleRecipeSerializer<>(JamRecipe::new));

}
