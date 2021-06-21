package com.cannolicatfish.rankine.recipe.helper;

import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;

import java.awt.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class AlloyColorHelper {

    public int getColor(ItemStack stack, int tintIndex) {
        PeriodicTableUtils utils = PeriodicTableUtils.getInstance();
        INBT nbt = AlloyItem.getComposition(stack).getCompound(0).get("comp");
        if (nbt != null)
        {
            boolean weighted = true;
            List<AbstractMap.SimpleEntry<PeriodicTableUtils.Element,Integer>> elements = new ArrayList<>();
            if (nbt.getString().contains("-")) {
                String[] split = nbt.getString().split("-");
                for (String s : split) {
                    if (s.replaceAll("[A-Za-z]+", "").matches("-?\\d+")) {
                        elements.add(new AbstractMap.SimpleEntry<>(utils.getElementBySymbol(s.replaceAll("[^A-Za-z]+", "")),Integer.parseInt(s.replaceAll("[A-Za-z]+", ""))));
                    } else {
                        elements.add(new AbstractMap.SimpleEntry<>(utils.getElementBySymbol(s.replaceAll("[^A-Za-z]+", "")),null));
                        weighted = false;
                    }
                }
            } else {
                String s = nbt.getString();
                if (s.replaceAll("[A-Za-z]+", "").matches("-?\\d+")) {
                    elements.add(new AbstractMap.SimpleEntry<>(utils.getElementBySymbol(s.replaceAll("[^A-Za-z]+", "")),Integer.parseInt(s.replaceAll("[A-Za-z]+", ""))));
                } else {
                    elements.add(new AbstractMap.SimpleEntry<>(utils.getElementBySymbol(s.replaceAll("[^A-Za-z]+", "")),null));
                    weighted = false;
                }
            }

            int color;
            if (elements.size() >= 2) {
                color = weighted ? returnBlendWeighted(elements) : returnBlend(elements);
            } else if (elements.size() == 1){
                color = elements.get(0).getKey().getColor();
            } else {
                color = 16777215;
            }
            return color;
        }
        return 16777215;
    }

    private int returnBlend(List<AbstractMap.SimpleEntry<PeriodicTableUtils.Element,Integer>> elements) {
        float r = 0;
        float g = 0;
        float b = 0;
        for (AbstractMap.SimpleEntry<PeriodicTableUtils.Element,Integer> e : elements) {
            Color col = new Color(e.getKey().getColor());
            r += (col.getRed());
            g += (col.getGreen());
            b += (col.getBlue());
        }
        int rgb = Math.round(r/elements.size());
        rgb = (rgb << 8) +  Math.round(g/elements.size());
        rgb = (rgb << 8) +  Math.round(b/elements.size());
        return rgb;
    }

    private int returnBlendWeighted(List<AbstractMap.SimpleEntry<PeriodicTableUtils.Element,Integer>> elements) {
        float r = 0;
        float g = 0;
        float b = 0;
        for (AbstractMap.SimpleEntry<PeriodicTableUtils.Element,Integer> e : elements) {
            Color col = new Color(e.getKey().getColor());
            r += (col.getRed() * e.getValue()/100f);
            g += (col.getGreen() * e.getValue()/100f);
            b += (col.getBlue() * e.getValue()/100f);
        }
        int rgb = Math.round(r);
        rgb = (rgb << 8) +  Math.round(g);
        rgb = (rgb << 8) +  Math.round(b);
        return rgb;
    }

}
