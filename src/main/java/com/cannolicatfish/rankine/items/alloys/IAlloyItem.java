package com.cannolicatfish.rankine.items.alloys;

import com.cannolicatfish.rankine.init.RankineDataComponents;
import com.cannolicatfish.rankine.init.RankineDataComponents.AlloyElement;
import com.cannolicatfish.rankine.init.RankineDataComponents.AlloyModifierEntry;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import com.cannolicatfish.rankine.recipe.helper.AlloyRecipeHelper;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public interface IAlloyItem {

    default void createAlloyNBT(ItemStack stack, Level worldIn, Map<ElementRecipe,Integer> elementMap, @Nullable ResourceLocation alloyRecipe, @Nullable String nameOverride) {
        stack.remove(RankineDataComponents.REGENERATE_ALLOY.get());

        List<AlloyElement> elements = new ArrayList<>();
        for (Map.Entry<ElementRecipe, Integer> entry : elementMap.entrySet()) {
            elements.add(new AlloyElement(String.valueOf(getElementRecipeId(worldIn, entry.getKey())), entry.getValue()));
        }
        stack.set(RankineDataComponents.ALLOY_COMPOSITION.get(), AlloyRecipeHelper.getDirectComposition(elementMap));
        if (alloyRecipe != null) {
            stack.set(RankineDataComponents.ALLOY_RECIPE.get(), alloyRecipe);
        } else {
            stack.remove(RankineDataComponents.ALLOY_RECIPE.get());
        }
        stack.set(RankineDataComponents.ALLOY_ELEMENTS.get(), elements);

        if (nameOverride != null) {
            stack.set(RankineDataComponents.NAME_OVERRIDE.get(), nameOverride);
        }
    }

    /**
     * 1.21 recipes no longer carry their own id (it lives on the {@link net.minecraft.world.item.crafting.RecipeHolder}),
     * so the element id stored in the ALLOY_ELEMENTS component is resolved by looking the recipe
     * instance back up in the recipe manager. Replaces the old {@code ElementRecipe#getId()}.
     */
    @Nullable
    static ResourceLocation getElementRecipeId(@Nullable Level worldIn, ElementRecipe recipe) {
        if (worldIn == null) {
            return null;
        }
        for (net.minecraft.world.item.crafting.RecipeHolder<ElementRecipe> holder : worldIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ELEMENT.get())) {
            if (holder.value() == recipe) {
                return holder.id();
            }
        }
        return null;
    }

    default void createAlloyNBT(ItemStack stack, Level worldIn, String composition, @Nullable ResourceLocation alloyRecipe, @Nullable String nameOverride) {
        createAlloyNBT(stack,worldIn,getElementMap(composition,worldIn),alloyRecipe,nameOverride);
    }

    static void createDirectAlloyNBT(ItemStack stack, @Nullable String composition, @Nullable String alloyRecipe, @Nullable String nameOverride) {
        createDirectAlloyNBT(stack, composition, alloyRecipe, nameOverride,true);
    }

    static void createDirectAlloyNBT(ItemStack stack, @Nullable String composition, @Nullable String alloyRecipe, @Nullable String nameOverride,boolean regenerate) {
        if (regenerate) {
            stack.set(RankineDataComponents.REGENERATE_ALLOY.get(), true);
        }
        if (composition != null) {
            stack.set(RankineDataComponents.ALLOY_COMPOSITION.get(), composition);
        } else {
            stack.remove(RankineDataComponents.ALLOY_COMPOSITION.get());
        }
        if (alloyRecipe != null) {
            stack.set(RankineDataComponents.ALLOY_RECIPE.get(), ResourceLocation.parse(alloyRecipe));
        } else {
            stack.remove(RankineDataComponents.ALLOY_RECIPE.get());
        }

        if (nameOverride != null) {
            stack.set(RankineDataComponents.NAME_OVERRIDE.get(), nameOverride);
        }
    }

    static void addColorNBT(ItemStack stack, int color) {
        stack.set(RankineDataComponents.COLOR.get(), color);
    }

    static int getColorNBT(ItemStack stack) {
        return stack.getOrDefault(RankineDataComponents.COLOR.get(), 16777215);
    }

    default String getAlloyCompositionString(ItemStack stack) {
        return getAlloyComposition(stack);
    }

    default String getAlloyRecipeString(ItemStack stack) {
        ResourceLocation rs = getAlloyRecipe(stack);
        return rs != null ? rs.toString() : "";
    }

    /** Was NBT ListTag "Elements": {id, percent} entries stored by createAlloyNBT. */
    static List<AlloyElement> getElementNBT(ItemStack stack) {
        return stack.getOrDefault(RankineDataComponents.ALLOY_ELEMENTS.get(), Collections.emptyList());
    }

    default boolean isAlloyInit(ItemStack stack) {
        return stack.has(RankineDataComponents.ALLOY_COMPOSITION.get()) || stack.has(RankineDataComponents.ALLOY_RECIPE.get());
    }

    default boolean needsRefresh(ItemStack stack) {
        return this.isAlloyInit(stack) && stack.getOrDefault(RankineDataComponents.REGENERATE_ALLOY.get(), false);
    }

    default void setRefresh(ItemStack stack) {
        if (this.isAlloyInit(stack)) {
            stack.set(RankineDataComponents.REGENERATE_ALLOY.get(), true);
        }
    }

    default boolean checkCompositionRequirement(ItemStack stack, Level worldIn, ElementRecipe element, String operand, int percentage) {
        Map<ElementRecipe,Integer> elementMap = this.getElementMap(getAlloyComposition(stack),worldIn);
        if (elementMap.containsKey(element)) {
            if (percentage == -1) {
                switch (operand){
                    case "==":
                    case "=":
                        return true;
                    case "!=":
                        return false;
                }
            } else {
                switch (operand) {
                    case "==":
                    case "=":
                        return elementMap.get(element) == percentage;
                    case ">=":
                        return elementMap.get(element) >= percentage;
                    case "<=":
                        return elementMap.get(element) <= percentage;
                    case "!=":
                        return elementMap.get(element) != percentage;
                }
            }
        }
        return false;
    }

    static String getAlloyComposition(ItemStack stack)
    {
        return stack.getOrDefault(RankineDataComponents.ALLOY_COMPOSITION.get(), "");
    }

    static String getNameOverride(ItemStack stack)
    {
        return stack.getOrDefault(RankineDataComponents.NAME_OVERRIDE.get(), "");
    }

    @Nullable
    static ResourceLocation getAlloyRecipe(ItemStack stack)
    {
        return stack.get(RankineDataComponents.ALLOY_RECIPE.get());
    }

    /** Was NBT ListTag "AlloyModifiers": {modifierName, value} entries. */
    static List<AlloyModifierEntry> getAlloyModifiers(ItemStack stack)
    {
        return stack.getOrDefault(RankineDataComponents.ALLOY_MODIFIERS.get(), Collections.emptyList());
    }

    default List<ElementRecipe> getElementRecipes(String c, @Nullable Level worldIn) {
        if (worldIn != null) {
            if (c.contains("-")) {
                String[] comp = c.split("-");
                List<ElementRecipe> list = new ArrayList<>();
                for (String e: comp)
                {
                    String str = e.replaceAll("[^A-Za-z]+", "");
                    worldIn.getRecipeManager().getAllRecipesFor(RankineRecipeTypes.ELEMENT.get()).stream().map(net.minecraft.world.item.crafting.RecipeHolder::value).filter(elementRecipe -> elementRecipe.getSymbol().equals(str)).findFirst().ifPresent(list::add);
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

    default Map<ElementRecipe,Integer> getElementMap(String c, @Nullable Level worldIn) {
        List<ElementRecipe> elementRecipes = getElementRecipes(c,worldIn);
        List<Integer> percents = getPercents(c);
        Map<ElementRecipe,Integer> elementMap = new HashMap<>();
        for (int i = 0; i < elementRecipes.size(); i++) {
            if (i < percents.size()) {
                elementMap.put(elementRecipes.get(i),percents.get(i));
            }
        }
        return elementMap;
    }

    @Nullable
    default AlloyingRecipe getAlloyingRecipe(@Nullable ResourceLocation rs, Level worldIn) {
        if (rs != null) {
            Optional<? extends Recipe<?>> opt = worldIn.getRecipeManager().byKey(rs).map(net.minecraft.world.item.crafting.RecipeHolder::value);
            if (opt.isPresent() && opt.get() instanceof AlloyingRecipe) {
                return (AlloyingRecipe) opt.get();
            }
        }
        return null;
    }

    @Nonnull
    static String getSubtype(ItemStack stack) {
        return IAlloyItem.getNameOverride(stack).toLowerCase(Locale.ROOT).replace(" ","_");
    }

    default void addAlloyInformation(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (this.isAlloyInit(stack)) {
            if (IAlloyItem.getAlloyComposition(stack).isEmpty()) {
                tooltip.add((Component.literal("Any Composition").withStyle(ChatFormatting.GOLD)));
            } else {
                tooltip.add((Component.literal("Composition: " + IAlloyItem.getAlloyComposition(stack)).withStyle(ChatFormatting.GOLD)));
            }

            if (!IAlloyItem.getAlloyModifiers(stack).isEmpty()) {
                tooltip.add((Component.literal("Modifier: " + (IAlloyItem.getAlloyModifiers(stack).get(0).name())).withStyle(ChatFormatting.AQUA)));
            }
        }
    }

    default void addAdvancedAlloyInformation(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (IAlloyItem.getAlloyRecipe(stack) != null) {
            tooltip.add((Component.literal("Recipe: " + (IAlloyItem.getAlloyRecipe(stack))).withStyle(ChatFormatting.LIGHT_PURPLE)));
        } else {
            tooltip.add((Component.literal("No Recipe Defined").withStyle(ChatFormatting.LIGHT_PURPLE)));
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
