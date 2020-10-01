package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AlloyRecipeHelper {
    private static final AlloyRecipeHelper INSTANCE = new AlloyRecipeHelper();
    private static final PeriodicTableUtils utils = new PeriodicTableUtils();

    public static AlloyRecipeHelper getInstance() {
        return INSTANCE;
    }

    private AlloyRecipeHelper() {

    }

    public AbstractMap.SimpleEntry<String,Integer> returnItemMaterial(ItemStack stack)
    {
        //System.out.println(RankineAlloyMaterial.getMaterial(stack.getItem()).toString());
        if (stack.isEmpty())
        {
            return new AbstractMap.SimpleEntry<>("none",0);
        }

        String reg = stack.getItem().getRegistryName().toString();
        String mat = "none";
        int amt = 0;
        if (!reg.contains(":"))
        {
            return new AbstractMap.SimpleEntry<>("nope",0);
        }
        reg = reg.split(":")[1];
        if (reg.equals("bloom_iron") || reg.equals("pig_iron_ingot"))
        {
            mat = "unref_iron";
            amt = 9 * stack.getCount();
        }
        if (reg.equals("pig_iron_nugget"))
        {
            mat = "unref_iron";
            amt = stack.getCount();
        }
        if (reg.equals("coke") || reg.equals("lignite") || reg.equals("graphite") || reg.contains("coal"))
        {

            switch (reg) {
                case "coke":
                    mat = "carbon";
                    amt = stack.getCount();
                    break;
                case "graphite":
                    mat = "carbon";
                    amt = 3 * stack.getCount();
                    break;
                default:
                    mat = "nope";
                    amt = stack.getCount();
                    break;
            }
            return new AbstractMap.SimpleEntry<>(mat,amt);
        }
        if (reg.equals("phosphorus") || reg.equals("astatine") || reg.equals("silicon") || reg.contains("sulfur"))
        {

            mat = reg;
            //System.out.println("Mat found!: " + mat);
            amt = 9*stack.getCount();
            return new AbstractMap.SimpleEntry<>(mat,amt);
        }
        if (!mat.equals("none"))
        {
            return new AbstractMap.SimpleEntry<>(mat,amt);
        }
        if (stack.getItem().getTags().contains(new ResourceLocation("forge:nuggets")))
        {
            for (ResourceLocation tag: stack.getItem().getTags())
            {
                if (tag.toString().contains("forge:nuggets/"))
                {
                    String temp = tag.getPath().split("/")[1];
                    if (utils.getImplementedElementNames().contains(temp))
                    {
                        mat = temp;
                        //System.out.println("Nugget mat found!: " + temp);
                    }
                }
            }
            amt = stack.getCount();
        }
        if (stack.getItem().getTags().contains(new ResourceLocation("forge:ingots")))
        {
            for (ResourceLocation tag: stack.getItem().getTags())
            {
                if (tag.toString().contains("forge:ingots/"))
                {
                    String temp = tag.getPath().split("/")[1];
                    if (utils.getImplementedElementNames().contains(temp))
                    {
                        mat = temp;
                        //System.out.println("Ingot mat found!: " + temp);
                    }
                }
            }
            amt = 9*stack.getCount();
        }
        if (stack.getItem().getTags().contains(new ResourceLocation("forge:storage_blocks")))
        {
            for (ResourceLocation tag: stack.getItem().getTags())
            {
                if (tag.toString().contains("forge:storage_blocks/"))
                {
                    String temp = tag.getPath().split("/")[1];
                    if (utils.getImplementedElementNames().contains(temp))
                    {
                        mat = temp;
                        //System.out.println("Storage block mat found!: " + temp);
                    }
                }
            }
            amt = 81*stack.getCount();
        }
        return new AbstractMap.SimpleEntry<>(mat,amt);
    }

    public int getPercent(ItemStack input1, ItemStack input2, ItemStack input3, int index)
    {
        List<Integer> amounts = Arrays.asList(returnItemMaterial(input1).getValue(),returnItemMaterial(input2).getValue(),returnItemMaterial(input3).getValue());
        float total = amounts.get(0) + amounts.get(1) + amounts.get(2);
        float percent = amounts.get(index)/total;
        return Math.round(percent * 100);
    }

    public String getComposition(ItemStack input1, ItemStack input2, ItemStack input3)
    {
        boolean flag = false;
        PeriodicTableUtils a = utils;
        List<Integer> percents = getPercents(input1,input2,input3).getKey();
        List<ItemStack> inputs = getPercents(input1,input2,input3).getValue();
        /*
        if (percents.get(2) != 0)
        {
            return getPercent(input1,input2,input3,0) + a.getElementbyMaterial(returnItemMaterial(input1).getKey()) + "-" + getPercent(input1,input2,input3,1) +
                    a.getElementbyMaterial(returnItemMaterial(input2).getKey()) + "-" + getPercent(input1,input2,input3,2) + a.getElementbyMaterial(returnItemMaterial(input3).getKey());
        } else {
            return getPercent(input1,input2,input3,0) + a.getElementbyMaterial(returnItemMaterial(input1).getKey()) + "-" + getPercent(input1,input2,input3,1) +
                    a.getElementbyMaterial(returnItemMaterial(input2).getKey());
        }*/
        if (percents.get(1).equals(percents.get(2)) && percents.get(2) != 0)
        {
            if (a.getElementByMaterial(returnItemMaterial(inputs.get(1)).getKey()).compareToIgnoreCase(a.getElementByMaterial(returnItemMaterial(inputs.get(2)).getKey())) > 0)
            {
                return percents.get(0) + a.getElementByMaterial(returnItemMaterial(inputs.get(0)).getKey()) + "-" + percents.get(2) +
                        a.getElementByMaterial(returnItemMaterial(inputs.get(2)).getKey()) + "-" + percents.get(1) + a.getElementByMaterial(returnItemMaterial(inputs.get(1)).getKey());
            }
        }
        if (percents.get(2) != 0)
        {
            return percents.get(0) + a.getElementByMaterial(returnItemMaterial(inputs.get(0)).getKey()) + "-" + percents.get(1) +
                    a.getElementByMaterial(returnItemMaterial(inputs.get(1)).getKey()) + "-" + percents.get(2) + a.getElementByMaterial(returnItemMaterial(inputs.get(2)).getKey());
        } else {
            return percents.get(0) + a.getElementByMaterial(returnItemMaterial(inputs.get(0)).getKey()) + "-" + percents.get(1) +
                    a.getElementByMaterial(returnItemMaterial(inputs.get(1)).getKey());
        }

    }

    public AbstractMap.SimpleEntry<List<Integer>,List<ItemStack>> getPercents(ItemStack input1, ItemStack input2, ItemStack input3)
    {
        int percent1 = getPercent(input1,input2,input3,0);
        int percent2 = getPercent(input1,input2,input3,1);
        int percent3 = getPercent(input1,input2,input3,2);
        if (percent1 + percent2 + percent3 == 101)
        {
            percent1 -= 1;
        }
        if (percent1 >= percent2 && percent1 >= percent3)
        {
            if (percent2 >= percent3)
            {
                return new AbstractMap.SimpleEntry<>(Arrays.asList(percent1,percent2,percent3),Arrays.asList(input1,input2,input3));
            } else {
                return new AbstractMap.SimpleEntry<>(Arrays.asList(percent1,percent3,percent2),Arrays.asList(input1,input3,input2));
            }
        }
        else if (percent2 >= percent1 && percent2 >= percent3)
        {
            if (percent1 >= percent3)
            {
                return new AbstractMap.SimpleEntry<>(Arrays.asList(percent2,percent1,percent3),Arrays.asList(input2,input1,input3));
            } else {
                return new AbstractMap.SimpleEntry<>(Arrays.asList(percent2,percent3,percent1),Arrays.asList(input2,input3,input1));
            }
        }
        else
        {
            if (percent1 >= percent2)
            {
                return new AbstractMap.SimpleEntry<>(Arrays.asList(percent3,percent1,percent2),Arrays.asList(input3,input1,input2));
            } else {
                return new AbstractMap.SimpleEntry<>(Arrays.asList(percent3,percent2,percent1),Arrays.asList(input3,input2,input2));
            }
        }
    }

    public String getCompositionAlloy(CompoundNBT nbt)
    {
        return nbt.getString();
    }

}
