package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.advancements.ExactCompositionPredicate;
import com.cannolicatfish.rankine.advancements.HarvestLevelPredicate;
import com.cannolicatfish.rankine.advancements.IncludesCompositionPredicate;
import com.cannolicatfish.rankine.advancements.AlloyEnchantabilityPredicate;
import com.cannolicatfish.rankine.potion.RankinePotions;
import com.cannolicatfish.rankine.recipe.helper.AlloyRecipeHelper;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.cannolicatfish.rankine.util.alloys.AlloyUtils;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

import java.util.*;

public class RankineRecipes {

    public static void registerPotionRecipes() {
        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.fromItems(RankineItems.MERCURY_INGOT::get), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), RankinePotions.MERCURY_POISON));
        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD)),Ingredient.fromItems(RankineItems.SALT::get,RankineItems.PINK_SALT::get), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), RankinePotions.CONDUCTIVE_POTION));

    }

    public static void registerPredicates() {
        ItemPredicate.register(new ResourceLocation("rankine","harvest_level_check"), HarvestLevelPredicate::new);
        ItemPredicate.register(new ResourceLocation("rankine","enchant_check"), AlloyEnchantabilityPredicate::new);
        ItemPredicate.register(new ResourceLocation("rankine","exact_composition"), ExactCompositionPredicate::new);
        ItemPredicate.register(new ResourceLocation("rankine","includes_composition"), IncludesCompositionPredicate::new);
    }

    public static String generateAlloyString(IInventory inv) {
        List<PeriodicTableUtils.Element> currentElements = new ArrayList<>();
        List<Integer> currentMaterial = new ArrayList<>();
        PeriodicTableUtils utils = new PeriodicTableUtils();
        for (int i = 0; i < 6; i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                PeriodicTableUtils.Element element = utils.getElementFromItem(stack.getItem());
                if (!currentElements.contains(element)) {
                    currentElements.add(element);
                    currentMaterial.add(0);
                }
                int workingIndex = currentElements.indexOf(element);
                Item item = stack.getItem();
                ResourceLocation reg = item.getRegistryName();
                String registry = "";
                if (reg != null) {
                    registry = reg.getPath();
                }

                if (stack.getItem().getTags().contains(new ResourceLocation("forge:storage_blocks")) || stack.getItem() instanceof BlockItem || registry.contains("block")) {
                    currentMaterial.set(workingIndex, currentMaterial.get(workingIndex) + 81 * stack.getCount());
                } else if (stack.getItem().getTags().contains(new ResourceLocation("forge:ingots")) || registry.contains("ingot")) {
                    currentMaterial.set(workingIndex,currentMaterial.get(workingIndex) + 9 * stack.getCount());
                } else if (stack.getItem().getTags().contains(new ResourceLocation("forge:nuggets")) || registry.contains("nugget")) {
                    currentMaterial.set(workingIndex,currentMaterial.get(workingIndex) + stack.getCount());
                } else if (stack.getItem() == Items.NETHERITE_SCRAP || registry.contains("scrap")){
                    currentMaterial.set(workingIndex,currentMaterial.get(workingIndex) + 2 * stack.getCount());
                } else {
                    currentMaterial.set(workingIndex,currentMaterial.get(workingIndex) + 9 * stack.getCount());
                }
            }
        }

        int sum = currentMaterial.stream().mapToInt(Integer::intValue).sum();

        List<Integer> percents = new ArrayList<>();
        List<String> symbols = new ArrayList<>();
        for (int j = 0; j < currentElements.size(); j++) {
            PeriodicTableUtils.Element curEl = currentElements.get(j);
            int curPer = Math.round(currentMaterial.get(j) * 100f/sum);
            symbols.add(curEl.getSymbol());
            percents.add(curPer);
        }
        return AlloyRecipeHelper.getDirectComposition(percents,symbols);
    }

    public static List<PeriodicTableUtils.Element> getElements(String c)
    {
        //String c = getComposition(stack).getCompound(0).get("comp").getString();
        PeriodicTableUtils utils = new PeriodicTableUtils();
        String[] comp = c.split("-");
        List<PeriodicTableUtils.Element> list = new ArrayList<>();
        for (String e: comp)
        {
            String str = e.replaceAll("[^A-Za-z]+", "");
            list.add(utils.getElementBySymbol(str));
        }
        return list;
    }

    public static List<Integer> getPercents(String c)
    {
        String[] comp = c.split("-");
        List<Integer> list = new ArrayList<>();
        for (String e: comp)
        {
            String str = e.replaceAll("\\D+", "");
            list.add(Integer.parseInt(str));
        }
        return list;
    }

    public static List<Enchantment> getEnchantments(String c, AlloyUtils alloy, Item item)
    {
        PeriodicTableUtils utils = new PeriodicTableUtils();
        List<Enchantment> enchantments = new ArrayList<>();
        List<Enchantment> elementEn = utils.getEnchantments(getElements(c),getPercents(c));
        for (Enchantment e: elementEn)
        {
            if (e != null && !enchantments.contains(e))
            {
                enchantments.add(e);
            }
        }
        List<Enchantment> en = alloy.getEnchantmentBonus(item);
        for (Enchantment e: en)
        {
            if (e != null && !enchantments.contains(e))
            {
                enchantments.add(e);
            }
        }
        return enchantments;
    }
}
