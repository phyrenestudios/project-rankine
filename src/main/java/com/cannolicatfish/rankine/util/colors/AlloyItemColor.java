package com.cannolicatfish.rankine.util.colors;

import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;

import java.awt.*;

public class AlloyItemColor implements IItemColor {
    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        PeriodicTableUtils utils = new PeriodicTableUtils();
        INBT nbt = AlloyItem.getComposition(stack).getCompound(0).get("comp");
        if (nbt != null && nbt.getString().contains("-"))
        {
            String[] split = nbt.getString().split("-");
            String str = split[0].replaceAll("[^A-Za-z]+", "");
            String str2 = nbt.getString().split("-")[1].replaceAll("[^A-Za-z]+", "");
            boolean weighted = false;
            int w1 = 0;
            int w2 = 0;
            if (split[0].replaceAll("[A-Za-z]+", "").matches("-?\\d+") && split[1].replaceAll("[A-Za-z]+", "").matches("-?\\d+")){
                w1 = Integer.parseInt(split[0].replaceAll("[A-Za-z]+", ""));
                w2 = Integer.parseInt(split[1].replaceAll("[A-Za-z]+", ""));
                weighted = true;
            }


            PeriodicTableUtils.Element element = utils.getElementBySymbol(str);
            PeriodicTableUtils.Element element2 = utils.getElementBySymbol(str2);
            if (element != null) {
                int color;
                if (element2 != null) {

                    color = weighted ? returnBlendWeighted(element.getColor(),w1,element2.getColor(),w2) : returnBlend(element.getColor(),element2.getColor());
                } else {
                    color = element.getColor();
                }
                return color;
            }
        }
        return 16777215;
    }

    private int returnBlend(int colorint, int colorint2) {
        Color col = new Color(colorint);
        Color col2 = new Color(colorint2);

        int r = Math.round((col.getRed() + col2.getRed())/2f);
        int g = Math.round((col.getGreen() + col2.getGreen())/2f);
        int b = Math.round((col.getBlue() + col2.getBlue())/2f);
        int rgb = r;
        rgb = (rgb << 8) + g;
        rgb = (rgb << 8) + b;
        return rgb;
    }

    private int returnBlendWeighted(int colorint, int weight1, int colorint2, int weight2) {
        Color col = new Color(colorint);
        Color col2 = new Color(colorint2);

        int r = Math.round((col.getRed() * weight1/100f + col2.getRed() * weight2/100f));
        int g = Math.round((col.getGreen() * weight1/100f + col2.getGreen() * weight2/100f));
        int b = Math.round((col.getBlue() * weight1/100f + col2.getBlue() * weight2/100f));
        int rgb = r;
        rgb = (rgb << 8) + g;
        rgb = (rgb << 8) + b;
        return rgb;
    }
}
