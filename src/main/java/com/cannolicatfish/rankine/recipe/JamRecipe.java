package com.cannolicatfish.rankine.recipe;


import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeSerializers;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class JamRecipe extends CustomRecipe {
    public JamRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput inv, Level worldIn) {
        boolean flag = false;
        boolean flag2 = false;
        boolean flag3 = false;
        int berryCount = 0;
        int sugarCount = 0;
        for(int i = 0; i < inv.size(); ++i) {
            ItemStack itemstack = inv.getItem(i);
            if (!itemstack.isEmpty()) {
                if (itemstack.getItem() == Items.SUGAR && !flag) {
                    sugarCount++;
                    if (sugarCount == 2) {
                        flag = true;
                    }
                } else if (itemstack.is(RankineTags.Items.BERRIES) && !flag2) {
                    berryCount++;
                    if (berryCount == 6) {
                        flag2 = true;
                    }
                } else {
                    if (itemstack.getItem() != Items.GLASS_BOTTLE || flag3) {
                        return false;
                    }

                    flag3 = true;
                }
            }
        }

        return flag && flag2 && flag3;
    }

    @Override
    public ItemStack assemble(CraftingInput inv, HolderLookup.Provider provider) {
        return new ItemStack(RankineItems.FRUIT_JAM.get(), 1);
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RankineRecipeSerializers.JAM_RECIPE_SERIALIZER.get();
    }


}
