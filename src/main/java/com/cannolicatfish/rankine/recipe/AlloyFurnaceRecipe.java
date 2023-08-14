package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.element.AlloyBonusStats;
import com.cannolicatfish.rankine.init.RankineRecipeSerializers;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.AlloyEnchantmentData;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.Map;

public class AlloyFurnaceRecipe extends AbstractAlloyingRecipe{

    public AlloyFurnaceRecipe(ResourceLocation idIn, String nameIn, int colorIn, boolean forceNBTIn, boolean requiresTemplateIn, int materialToIngotIn, Map<ResourceLocation, Float> defaultCompositionIn, Map<ResourceLocation, AlloyIngredientData> requiredElementsIn, Map<ResourceLocation, AlloyIngredientData> optionalElementsIn, AlloyBonusStats alloyBonusStatsIn, AlloyEnchantmentData enchantmentRequirementsIn, ItemStack recipeOutputIn) {
        super(RankineRecipeTypes.ALLOY_FURNACE.get(), idIn, nameIn, colorIn, forceNBTIn, requiresTemplateIn, materialToIngotIn, defaultCompositionIn, requiredElementsIn, optionalElementsIn, alloyBonusStatsIn, enchantmentRequirementsIn, recipeOutputIn);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RankineRecipeSerializers.ALLOY_FURNACE_RECIPE_SERIALIZER.get();
    }
}
