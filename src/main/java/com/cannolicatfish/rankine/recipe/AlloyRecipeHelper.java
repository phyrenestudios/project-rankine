package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class AlloyRecipeHelper {
    private static final AlloyRecipeHelper INSTANCE = new AlloyRecipeHelper();
    private static final PeriodicTableUtils utils = new PeriodicTableUtils();

    public static AlloyRecipeHelper getInstance() {
        return INSTANCE;
    }

    private AlloyRecipeHelper() {

    }

    public static AbstractMap.SimpleEntry<String,Integer> returnItemMaterial(ItemStack stack)
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

        if (reg.equals("wrought_iron_ingot"))
        {
            mat = "ref_iron";
            amt = 9 * stack.getCount();
        }
        if (reg.equals("wrought_iron_nugget"))
        {
            mat = "ref_iron";
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

    public int getTriplePercent(ItemStack input1, ItemStack input2, ItemStack input3, ItemStack input4, ItemStack input5, int index)
    {
        List<Integer> amounts = Arrays.asList(returnItemMaterial(input1).getValue(),returnItemMaterial(input2).getValue(),returnItemMaterial(input3).getValue(),returnItemMaterial(input4).getValue(),returnItemMaterial(input5).getValue());
        float total = amounts.get(0) + amounts.get(1) + amounts.get(2) + amounts.get(3) + amounts.get(4);
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


    public String getTripleComposition(ItemStack input1, ItemStack input2, ItemStack input3, ItemStack input4, ItemStack input5)
    {
        List<Integer> percents = getTriplePercents(input1,input2,input3,input4,input5).getKey();
        List<ItemStack> inputs = getTriplePercents(input1,input2,input3,input4,input5).getValue();
        StringBuilder ret = new StringBuilder();
        Map<ItemStack,Integer> map = new HashMap<>();
        for (int i = 0; i < inputs.size(); i++)
        {
            map.put(inputs.get(i),percents.get(i));
        }

        List<Integer> sPercents = new ArrayList<>();
        List<ItemStack> sInputs = new ArrayList<>();
        for (Map.Entry<ItemStack, Integer> entry : mapInputsSort(map).entrySet())
        {
            sPercents.add(entry.getValue());
            sInputs.add(entry.getKey());
        }
        Collections.reverse(sPercents);
        Collections.reverse(sInputs);

        for (int i = 0; i < sPercents.size(); i++)
        {
            ret.append(sPercents.get(i)).append(utils.getElementByMaterial(returnItemMaterial(sInputs.get(i)).getKey()));
            if (i != sPercents.size() - 1) {
                ret.append("-");
            }
        }
        return ret.toString();
    }

    public static Map<ItemStack, Integer> mapInputsSort(Map<ItemStack, Integer> map) {
        List<Map.Entry<ItemStack, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort((o1, o2) -> {
            if (o1.getValue().equals(o2.getValue())) {
                return AlloyRecipeHelper.returnItemMaterial(o2.getKey()).getKey().compareToIgnoreCase(AlloyRecipeHelper.returnItemMaterial(o1.getKey()).getKey());
            }
            return o1.getValue() < o2.getValue() ? -1 : 1;
        });

        Map<ItemStack, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<ItemStack, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public AbstractMap.SimpleEntry<List<Integer>,List<ItemStack>> getTriplePercents(ItemStack input1, ItemStack input2, ItemStack input3, ItemStack input4, ItemStack input5)
    {

        int percent1 = getTriplePercent(input1,input2,input3,input4,input5,0);
        int percent2 = getTriplePercent(input1,input2,input3,input4,input5,1);
        int percent3 = getTriplePercent(input1,input2,input3,input4,input5,2);
        int percent4 = getTriplePercent(input1,input2,input3,input4,input5,3);
        int percent5 = getTriplePercent(input1,input2,input3,input4,input5,4);
        if (percent1 + percent2 + percent3 + percent4 + percent5 == 101)
        {
            percent1 -= 1;
        }
        List<Integer> perc;
        List<ItemStack> stacks;
        if (percent4 == 0)
        {
            stacks = Arrays.asList(input1, input2, input3);
            perc = Arrays.asList(percent1,percent2,percent3);
        } else if (percent5 == 0)
        {
            stacks = Arrays.asList(input1, input2, input3, input4);
            perc = Arrays.asList(percent1,percent2,percent3,percent4);
        } else {
            stacks = Arrays.asList(input1, input2, input3, input4, input5);
            perc = Arrays.asList(percent1,percent2,percent3,percent4,percent5);
        }

        return new AbstractMap.SimpleEntry<>(perc,stacks);
    }
    /*
    public AbstractMap.SimpleEntry<List<Integer>,List<ItemStack>> getTriplePercents(ItemStack input1, ItemStack input2, ItemStack input3, ItemStack input4, ItemStack input5)
    {
        Map<ItemStack,Integer> map = new HashMap<>();
        int percent1 = getTriplePercent(input1,input2,input3,input4,input5,0);
        int percent2 = getTriplePercent(input1,input2,input3,input4,input5,1);
        int percent3 = getTriplePercent(input1,input2,input3,input4,input5,2);
        int percent4 = getTriplePercent(input1,input2,input3,input4,input5,3);
        int percent5 = getTriplePercent(input1,input2,input3,input4,input5,4);
        if (percent1 + percent2 + percent3 + percent4 + percent5 == 101)
        {
            percent1 -= 1;
        }
        map.put(input1,percent1);
        map.put(input2,percent2);
        map.put(input3,percent3);
        if (!input4.isEmpty())
        {
            map.put(input4,percent4);
        }
        if (!input5.isEmpty())
        {
            map.put(input5,percent5);
        }
        List<Integer> perc = new ArrayList<>();
        List<ItemStack> stacks = new ArrayList<>();
        for (Map.Entry<ItemStack, Integer> entry : mapInputsSort(map).entrySet())
        {
            perc.add(entry.getValue());
            stacks.add(entry.getKey());
        }
        Collections.reverse(perc);
        Collections.reverse(stacks);
        return new AbstractMap.SimpleEntry<>(perc,stacks);
    }*/

}
