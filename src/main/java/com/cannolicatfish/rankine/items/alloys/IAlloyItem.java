package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.recipe.helper.AlloyRecipeHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public interface IAlloyItem {

    default void createAlloyNBT(ItemStack stack, World worldIn, Map<ElementRecipe,Integer> elementMap, @Nullable ResourceLocation alloyRecipe, @Nullable String nameOverride) {
        if (stack.getTag() != null && stack.getTag().getBoolean("RegenerateAlloy")) {
            stack.getTag().remove("RegenerateAlloy");
        }

        CompoundNBT listnbt = new CompoundNBT();
        ListNBT elements = new ListNBT();
        for (Map.Entry<ElementRecipe, Integer> entry : elementMap.entrySet()) {
            int perc = entry.getValue();
            CompoundNBT compoundnbt = new CompoundNBT();
            compoundnbt.putString("id", String.valueOf(entry.getKey().getId()));
            compoundnbt.putShort("percent", (short)perc);
            elements.add(compoundnbt);
        }
        listnbt.putString("comp", AlloyRecipeHelper.getDirectComposition(elementMap));
        if (alloyRecipe != null) {
            listnbt.putString("recipe",alloyRecipe.toString());
        }
        stack.getOrCreateTag().put("StoredAlloy", listnbt);
        stack.getOrCreateTag().put("Elements",elements);

        if (nameOverride != null && stack.getTag() != null) {
            stack.getTag().putString("nameOverride",nameOverride);
        }
    }

    default void createAlloyNBT(ItemStack stack, World worldIn, String composition, @Nullable ResourceLocation alloyRecipe, @Nullable String nameOverride) {
        createAlloyNBT(stack,worldIn,getElementMap(composition,worldIn),alloyRecipe,nameOverride);
    }

    static void createDirectAlloyNBT(ItemStack stack, @Nullable String composition, @Nullable String alloyRecipe, @Nullable String nameOverride) {
        createDirectAlloyNBT(stack, composition, alloyRecipe, nameOverride,true);
    }

    static void createDirectAlloyNBT(ItemStack stack, @Nullable String composition, @Nullable String alloyRecipe, @Nullable String nameOverride,boolean regenerate) {
        if (regenerate) {
            stack.getOrCreateTag().putBoolean("RegenerateAlloy",true);
        }
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

    default String getAlloyCompositionString(ItemStack stack) {
        return stack.getTag() != null ? stack.getTag().getCompound("StoredAlloy").getString("comp") : "";
    }

    default String getAlloyRecipeString(ItemStack stack) {
        return stack.getTag() != null ? stack.getTag().getCompound("StoredAlloy").getString("recipe") : "";
    }

    static ListNBT getElementNBT(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null ? compoundnbt.getList("Elements", 10) : new ListNBT();
    }

    default boolean isAlloyInit(ItemStack stack) {
        return stack.getTag() != null && !stack.getTag().getCompound("StoredAlloy").isEmpty();
    }

    default boolean needsRefresh(ItemStack stack) {
        return stack.getTag() != null && !stack.getTag().getCompound("StoredAlloy").isEmpty() && stack.getTag().getBoolean("RegenerateAlloy");
    }

    default void setRefresh(ItemStack stack) {
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

    static ListNBT getAlloyModifiers(ItemStack stack)
    {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null ? compoundnbt.getList("AlloyModifiers", 10) : new ListNBT();
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

    default Map<ElementRecipe,Integer> getElementMap(String c, @Nullable World worldIn) {
        List<ElementRecipe> elementRecipes = getElementRecipes(c,worldIn);
        List<Integer> percents = getPercents(c);
        Map<ElementRecipe,Integer> elementMap = new HashMap<>();
        for (int i = 0; i < elementRecipes.size(); i++) {
            if (i < percents.size()) {
                elementMap.put(elementRecipes.get(i),percents.get(i));
            }
        }
        for (Map.Entry<ElementRecipe, Integer> entry : elementMap.entrySet()) {
            int perc = entry.getValue();
            CompoundNBT compoundnbt = new CompoundNBT();
            compoundnbt.putString("id", String.valueOf(entry.getKey().getId()));
            compoundnbt.putShort("percent", (short)perc);
        }
        return elementMap;
    }

    default AlloyingRecipe getAlloyingRecipe(ResourceLocation rs, World worldIn) {
        if (rs != null) {
            Optional<? extends IRecipe<?>> opt = worldIn.getRecipeManager().getRecipe(rs);
            if (opt.isPresent() && opt.get() instanceof AlloyingRecipe) {
                return (AlloyingRecipe) opt.get();
            }
        }
        return null;
    }

    @Nonnull
    static String getSubtype(ItemStack stack) {
        return stack.hasTag() ? IAlloyItem.getNameOverride(stack).toLowerCase(Locale.ROOT).replace(" ","_") : "";
    }

    default void addAlloyInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (this.isAlloyInit(stack)) {
            if (IAlloyItem.getAlloyComposition(stack).isEmpty()) {
                tooltip.add((new StringTextComponent("Any Composition").mergeStyle(TextFormatting.GOLD)));
            } else {
                tooltip.add((new StringTextComponent("Composition: " + IAlloyItem.getAlloyComposition(stack)).mergeStyle(TextFormatting.GOLD)));
            }

            if (!IAlloyItem.getAlloyModifiers(stack).isEmpty()) {
                tooltip.add((new StringTextComponent("Modifier: " + (IAlloyItem.getAlloyModifiers(stack).getCompound(0).getString("modifierName"))).mergeStyle(TextFormatting.AQUA)));
            } else {
                tooltip.add((new StringTextComponent("No Modifiers Present").mergeStyle(TextFormatting.AQUA)));
            }
        }
    }

    default void addAdvancedAlloyInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (IAlloyItem.getAlloyRecipe(stack) != null) {
            tooltip.add((new StringTextComponent("Recipe: " + (IAlloyItem.getAlloyRecipe(stack))).mergeStyle(TextFormatting.LIGHT_PURPLE)));
        } else {
            tooltip.add((new StringTextComponent("No Recipe Defined").mergeStyle(TextFormatting.LIGHT_PURPLE)));
        }
    }

    default String generateLangFromRecipe(ResourceLocation recipe) {
        if (recipe == null) {
            return "item.rankine.custom_alloy_default";
        } else {
            String[] s = recipe.getPath().split("/");
            return "item." + recipe.getNamespace() + "." + s[s.length-1];
        }
    }


    String getDefaultComposition();

    ResourceLocation getDefaultRecipe();
}
