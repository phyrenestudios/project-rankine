package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.OldAlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.Locale;

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
        return world.getRecipeManager().getRecipeFor(RankineRecipeTypes.ELEMENT.get(), temp, world).orElse(null);
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

    public OldAlloyingRecipe getAlloyRecipe(ItemStack stack, Level world) {
        ResourceLocation rs = IAlloyItem.getAlloyRecipe(stack);
        if (rs != null) {
            Recipe<?> recipe = world.getRecipeManager().byKey(rs).orElse(null);
            if (recipe instanceof OldAlloyingRecipe) {
                return (OldAlloyingRecipe) recipe;
            }
        }
        return null;
    }
}
