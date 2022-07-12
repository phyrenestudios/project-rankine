package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.ChatFormatting;
import net.minecraft.util.Tuple;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public final class PeriodicTableUtils {

    private static final PeriodicTableUtils INSTANCE = new PeriodicTableUtils();

    public static PeriodicTableUtils getInstance() {
        return INSTANCE;
    }


    public boolean hasElementRecipe(ItemStack stack, Level world) {
        return world != null && getElementRecipe(stack,world) != null;
    }

    public ElementRecipe getElementRecipe(ItemStack stack, Level world) {
        SimpleContainer temp = new SimpleContainer(stack);
        return world.getRecipeManager().getRecipeFor(RankineRecipeTypes.ELEMENT, temp, world).orElse(null);
    }

    public int getCrushingAmountFromTier(Tier tier) {
        ResourceLocation tierLoc = TierSortingRegistry.getName(tier);
        if (tierLoc != null) {
            switch (tierLoc.getPath().toUpperCase(Locale.ROOT)) {
                case "WOOD":
                    return 0;
                case "STONE":
                    return 1;
                case "IRON":
                    return 2;
                case "DIAMOND":
                    return 3;
                case "NETHERITE":
                    return 4;
            }
        }
        return tier.getLevel();
    }

    public AlloyingRecipe getAlloyRecipe(ItemStack stack, Level world) {
        ResourceLocation rs = IAlloyItem.getAlloyRecipe(stack);
        if (rs != null) {
            Recipe<?> recipe = world.getRecipeManager().byKey(rs).orElse(null);
            if (recipe instanceof AlloyingRecipe) {
                return (AlloyingRecipe) recipe;
            }
        }
        return null;
    }
}
