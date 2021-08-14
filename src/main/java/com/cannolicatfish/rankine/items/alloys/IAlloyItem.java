package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public interface IAlloyItem {

    default void createAlloyNBT(ItemStack stack, World worldIn, String composition, @Nullable ResourceLocation alloyRecipe, @Nullable String nameOverride) {
        if (stack.getTag() != null && stack.getTag().getBoolean("RegenerateAlloy")) {
            stack.getTag().remove("RegenerateAlloy");
        }
        ListNBT alloyData = getAlloyNBT(stack);

        CompoundNBT listnbt = new CompoundNBT();

        listnbt.putString("comp",composition);
        if (alloyRecipe != null) {
            listnbt.putString("recipe",alloyRecipe.toString());
        }
        alloyData.add(listnbt);
        stack.getOrCreateTag().put("StoredAlloy", listnbt);

        if (nameOverride != null && stack.getTag() != null) {
            stack.getTag().putString("nameOverride",nameOverride);
        }
    }

    static void createDirectAlloyNBT(ItemStack stack, @Nullable String composition, @Nullable String alloyRecipe, @Nullable String nameOverride) {
        stack.getOrCreateTag().putBoolean("RegenerateAlloy",true);
        CompoundNBT listnbt = new CompoundNBT();
        if (composition != null) {
            listnbt.putString("comp",composition);
        }
        if (alloyRecipe != null) {
            listnbt.putString("recipe",alloyRecipe);
        }
        getAlloyNBT(stack).add(listnbt);
        stack.getOrCreateTag().put("StoredAlloy",listnbt);

        if (nameOverride != null) {
            stack.getOrCreateTag().putString("nameOverride",nameOverride);
        }
    }

    static void addColorNBT(ItemStack stack, int color) {
        stack.getOrCreateTag().putInt("color",color);
    }

    static ListNBT getAlloyNBT(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null ? compoundnbt.getList("StoredAlloy", 10) : new ListNBT();
    }

    default boolean isAlloyInit(ItemStack stack) {

        return stack.getTag() != null && !stack.getTag().getCompound("StoredAlloy").isEmpty();
    }

    static boolean needsRefresh(ItemStack stack) {
        return stack.getTag() != null && !stack.getTag().getCompound("StoredAlloy").isEmpty() && stack.getTag().getBoolean("RegenerateAlloy");
    }

    static void setRefresh(ItemStack stack) {
        if (stack.getTag() != null && !stack.getTag().getCompound("StoredAlloy").isEmpty()) {
            stack.getTag().putBoolean("RegenerateAlloy",true);
        }
    }

    static String getAlloyComposition(ItemStack stack)
    {
        if (stack.getTag() != null) {
            return stack.getTag().getCompound("StoredAlloy").getString("comp");
        } else {
            return "";
        }
    }

    static String getNameOverride(ItemStack stack)
    {
        if (stack.getTag() != null) {
            return stack.getTag().getString("nameOverride");
        } else {
            return "";
        }
    }

    static ResourceLocation getAlloyRecipe(ItemStack stack)
    {
        if (stack.getTag() != null && !stack.getTag().getCompound("StoredAlloy").getString("recipe").isEmpty()) {
            return new ResourceLocation(stack.getTag().getCompound("StoredAlloy").getString("recipe"));
        } else {
            return null;
        }
    }

    default List<ElementRecipe> getElementRecipes(String c, @Nullable World worldIn) {
        if (worldIn != null) {
            if (c.contains("-")) {
                String[] comp = c.split("-");
                List<ElementRecipe> list = new ArrayList<>();
                for (String e: comp)
                {
                    String str = e.replaceAll("[^A-Za-z]+", "");
                    worldIn.getRecipeManager().getRecipesForType(RankineRecipeTypes.ELEMENT).stream().filter(elementRecipe -> elementRecipe.getSymbol().equals(str)).findFirst().ifPresent(list::add);
                }
                return list;
            }
            return Collections.emptyList();

        } else {
            return Collections.emptyList();
        }

    }

    default List<Integer> getPercents(String c)
    {
        if (c.contains("-")) {
            String[] comp = c.split("-");
            List<Integer> list = new ArrayList<>();
            for (String e: comp)
            {
                String str = e.replaceAll("\\D+", "");
                list.add(Integer.parseInt(str));
            }
            return list;
        }
        return Collections.emptyList();


    }

    default ItemStack createAlloyItemStack(Item item, World worldIn, String composition, @Nullable ResourceLocation alloyRecipe, @Nullable String nameOverride) {
        ItemStack itemstack = new ItemStack(item);
        this.createAlloyNBT(itemstack,worldIn,composition,alloyRecipe,nameOverride);
        return itemstack;
    }

    @Nonnull
    static String getSubtype(ItemStack stack) {
        return stack.hasTag() ? IAlloyItem.getNameOverride(stack).toLowerCase(Locale.ROOT).replace(" ","_") : "";
    }

    String getDefaultComposition();

    ResourceLocation getDefaultRecipe();
}
