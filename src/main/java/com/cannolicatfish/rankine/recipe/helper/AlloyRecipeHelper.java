package com.cannolicatfish.rankine.recipe.helper;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class AlloyRecipeHelper {

    public static int returnMaterialCountFromStack(ItemStack stack) {
        Item item = stack.getItem();
        ResourceLocation reg = item.getRegistryName();
        String registry = "";
        if (reg != null) {
            registry = reg.getPath();
        }

        if (stack.getItem().getTags().contains(new ResourceLocation("forge:storage_blocks")) || stack.getItem() instanceof BlockItem || registry.contains("block")) {
            return 81 * stack.getCount();
        } else if (stack.getItem().getTags().contains(new ResourceLocation("forge:ingots")) || registry.contains("ingot")) {
            return 9 * stack.getCount();
        } else if (stack.getItem().getTags().contains(new ResourceLocation("forge:nuggets")) || registry.contains("nugget")) {
            return stack.getCount();
        } else if (stack.getItem() == Items.NETHERITE_SCRAP || registry.contains("scrap")){
            return 2 * stack.getCount();
        } else {
            return 9 * stack.getCount();
        }
    }

    public static String getDirectComposition(List<Integer> percents, List<String> inputs) {
        StringBuilder ret = new StringBuilder();
        Map<String,Integer> map = new HashMap<>();

        for (int i = 0; i < inputs.size(); i++)
        {
            map.put(inputs.get(i),percents.get(i));
        }
        List<Integer> sPercents = new ArrayList<>();
        List<String> sInputs = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : mapStringsSort(map).entrySet())
        {
            sPercents.add(entry.getValue());
            sInputs.add(entry.getKey());
        }
        Collections.reverse(sPercents);
        Collections.reverse(sInputs);
        for (int i = 0; i < sPercents.size(); i++)
        {
            ret.append(sPercents.get(i)).append(sInputs.get(i));
            if (i != sPercents.size() - 1) {
                ret.append("-");
            }
        }
        return ret.toString();
    }

    public static Map<String, Integer> mapStringsSort(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort((o1, o2) -> {
            if (o1.getValue().equals(o2.getValue())) {
                return o2.getKey().compareToIgnoreCase(o1.getKey());
            }
            return o1.getValue() < o2.getValue() ? -1 : 1;
        });

        Map<String, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }


}
