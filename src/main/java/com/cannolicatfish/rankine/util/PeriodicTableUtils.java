package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public final class PeriodicTableUtils {

    private static final PeriodicTableUtils INSTANCE = new PeriodicTableUtils();

    public static PeriodicTableUtils getInstance() {
        return INSTANCE;
    }


    public boolean hasElementRecipe(ItemStack stack, Level world) {
        return world != null && getElementRecipe(stack,world) != null;
    }

    public ElementRecipe getElementRecipe(ItemStack stack, Level world) {
        return world.getRecipeManager().getRecipeFor(RankineRecipeTypes.ELEMENT.get(), new SingleRecipeInput(stack), world).map(RecipeHolder::value).orElse(null);
    }

    /**
     * PORT-NOTE: 1.21.1 removed TierSortingRegistry and Tier#getLevel. Tiers are now identified by the
     * tag of blocks they cannot correctly mine, so the old numeric harvest levels are recovered from
     * {@link Tier#getIncorrectBlocksForDrops()}. Values match the 1.18.2 mapping exactly
     * (wood 0, stone 1, iron 2, diamond 3, netherite 4, anything else 0 — gold reported level 0 before too).
     */
    public int getCrushingAmountFromTier(Tier tier) {
        TagKey<Block> incorrect = tier.getIncorrectBlocksForDrops();
        if (incorrect != null) {
            if (incorrect.equals(BlockTags.INCORRECT_FOR_WOODEN_TOOL)) {
                return 0;
            } else if (incorrect.equals(BlockTags.INCORRECT_FOR_STONE_TOOL)) {
                return 1;
            } else if (incorrect.equals(BlockTags.INCORRECT_FOR_IRON_TOOL)) {
                return 2;
            } else if (incorrect.equals(BlockTags.INCORRECT_FOR_DIAMOND_TOOL)) {
                return 3;
            } else if (incorrect.equals(BlockTags.INCORRECT_FOR_NETHERITE_TOOL)) {
                return 4;
            }
        }
        return 0;
    }

    public AlloyingRecipe getAlloyRecipe(ItemStack stack, Level world) {
        ResourceLocation rs = IAlloyItem.getAlloyRecipe(stack);
        if (rs != null) {
            Recipe<?> recipe = world.getRecipeManager().byKey(rs).<Recipe<?>>map(RecipeHolder::value).orElse(null);
            if (recipe instanceof AlloyingRecipe) {
                return (AlloyingRecipe) recipe;
            }
        }
        return null;
    }
}
