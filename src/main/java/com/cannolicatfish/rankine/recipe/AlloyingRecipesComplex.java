package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.init.ModItems;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AlloyingRecipesComplex {
    private static final AlloyingRecipesComplex INSTANCE = new AlloyingRecipesComplex();
    private final Table<ItemStack, ItemStack, ItemStack> smeltingList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
    private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

    public static AlloyingRecipesComplex getInstance() {
        return INSTANCE;
    }

    private AlloyingRecipesComplex() {

    }

    public ItemStack getFurnaceResult(ItemStack input1)
    {
        return ItemStack.EMPTY;
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
                    if (new PeriodicTableUtils().getImplementedElementNames().contains(temp))
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
                    if (new PeriodicTableUtils().getImplementedElementNames().contains(temp))
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
                    if (new PeriodicTableUtils().getImplementedElementNames().contains(temp))
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

    public AbstractMap.SimpleEntry<ItemStack,int[]> getAlloyResult(ItemStack input1, ItemStack input2, ItemStack input3) {
        List<Item> inputList = Arrays.asList(input1.getItem(),input2.getItem(),input3.getItem());
        if (inputList.stream().distinct().count() < 3)
        {
            int[] ar = new int[3];
            ar[0] = 1;
            ar[1] = 1;
            ar[2] = 1;
            return new AbstractMap.SimpleEntry<>(ItemStack.EMPTY, ar);
        }
        List<Integer> inputValList = Arrays.asList(input1.getCount(),input2.getCount(),input3.getCount());
        AbstractMap.SimpleEntry<String,Integer> material1 = returnItemMaterial(input1);
        AbstractMap.SimpleEntry<String,Integer> material2 = returnItemMaterial(input2);
        AbstractMap.SimpleEntry<String,Integer> material3 = returnItemMaterial(input3);
        List<String> materials = Arrays.asList(material1.getKey(),material2.getKey(),material3.getKey());
        List<Integer> amounts = Arrays.asList(material1.getValue(),material2.getValue(),material3.getValue());
        float total = material1.getValue() + material2.getValue() + material3.getValue();
        if (materials.contains("copper") && materials.contains("tin") && total >= 10) // Bronze
        {
            int x1 = materials.indexOf("copper");
            int x2 = materials.indexOf("tin");
            int x3;
            if (x1 == 0 && x2 == 1 || x2 == 0 && x1 == 1)
            {
                x3 = 2;
            }
            else if (x1 == 2 && x2 == 0 || x2 == 2 && x1 == 0)
            {
                x3 = 1;
            }
            else
            {
                x3 = 0;
            }
            if (materials.get(x3).equals("aluminum") || materials.get(x3).equals("manganese") || materials.get(x3).equals("nickel") || materials.get(x3).equals("zinc") || materials.get(x3).equals("none")) {

                float propx1 = amounts.get(x1)/total;
                float propx2 = amounts.get(x2)/total;
                float propx3 = amounts.get(x3)/total;
                /*
                System.out.println(propx1);
                System.out.println(propx2);
                System.out.println(propx3);
                System.out.println(Math.round(total/10));
                 */
                if (propx1 <= .9f && propx1 >= .8f && propx2 >= .1f && propx2 <= .2f && Math.round(total/10) <= 64) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.BRONZE_ALLOY, Math.round(total/10)),ar);
                }
            }
        }
        if (materials.contains("copper") && materials.contains("aluminum") && total >= 10) // Aluminum Bronze
        {
            int x1 = materials.indexOf("copper");
            int x2 = materials.indexOf("aluminum");
            int x3;
            if (x1 == 0 && x2 == 1 || x2 == 0 && x1 == 1)
            {
                x3 = 2;
            }
            else if (x1 == 2 && x2 == 0 || x2 == 2 && x1 == 0)
            {
                x3 = 1;
            }
            else
            {
                x3 = 0;
            }
            if (materials.get(x3).equals("iron") || materials.get(x3).equals("manganese") || materials.get(x3).equals("nickel") || materials.get(x3).equals("zinc") || materials.get(x3).equals("arsenic") || materials.get(x3).equals("lead")
                    || materials.get(x3).equals("none")) {

                float propx1 = amounts.get(x1)/total;
                float propx2 = amounts.get(x2)/total;
                float propx3 = amounts.get(x3)/total;
                /*
                System.out.println(propx1);
                System.out.println(propx2);
                System.out.println(propx3);
                System.out.println(Math.round(total/10));
                 */
                if (propx1 <= .93f && propx1 >= .74f && propx2 >= .04f && propx2 <= .15f && propx1 + propx2 >= .85f && Math.round(total/10) <= 64) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.ALUMINUM_BRONZE_ALLOY, Math.round(total/10)),ar);
                }
            }
        }
        if (materials.contains("copper") && materials.contains("zinc") && total >= 10) // Brass
        {
            int x1 = materials.indexOf("copper");
            int x2 = materials.indexOf("zinc");
            int x3;
            if (x1 == 0 && x2 == 1 || x2 == 0 && x1 == 1)
            {
                x3 = 2;
            }
            else if (x1 == 2 && x2 == 0 || x2 == 2 && x1 == 0)
            {
                x3 = 1;
            }
            else
            {
                x3 = 0;
            }
            if (materials.get(x3).equals("tin") || materials.get(x3).equals("lead") || materials.get(x3).equals("aluminum") || materials.get(x3).equals("nickel") || materials.get(x3).equals("iron")
                    || materials.get(x3).equals("none")) {

                float propx1 = amounts.get(x1)/total;
                float propx2 = amounts.get(x2)/total;
                float propx3 = amounts.get(x3)/total;
                /*
                System.out.println(propx1);
                System.out.println(propx2);
                System.out.println(propx3);
                System.out.println(Math.round(total/10));
                 */
                if (propx1 >= .30f && propx1 <= .70f && propx2 >= .15f && propx2 <= .60f && propx1 + propx2 >= .90f && Math.round(total/10) <= 64) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.BRASS_ALLOY, Math.round(total/10)),ar);
                }
            }
        }
        if (materials.contains("unref_iron") && materials.contains("carbon") && total >= 10) // Cast Iron
        {
            int x1 = materials.indexOf("unref_iron");
            int x2 = materials.indexOf("carbon");
            int x3;
            if (x1 == 0 && x2 == 1 || x2 == 0 && x1 == 1)
            {
                x3 = 2;
            }
            else if (x1 == 2 && x2 == 0 || x2 == 2 && x1 == 0)
            {
                x3 = 1;
            }
            else
            {
                x3 = 0;
            }
            if ((materials.get(x3).equals("silicon") || materials.get(x3).equals("manganese") || materials.get(x3).equals("nickel") || materials.get(x3).equals("chromium") ||
                    materials.get(x3).equals("molybdenum") || materials.get(x3).equals("titanium") || materials.get(x3).equals("vanadium")
                    && new PeriodicTableUtils().getImplementedElementNames().contains(materials.get(x3))) || materials.get(x3).equals("none")) {

                float propx1 = amounts.get(x1)/total;
                float propx2 = amounts.get(x2)/total;
                float propx3 = amounts.get(x3)/total;

                //System.out.println(propx1);
                //System.out.println(propx2);
                //System.out.println(propx3);
                //System.out.println(Math.round(total/10));
                if (propx1 >= .86f && propx1 <= .98f && propx2 >= .02f && propx2 <= .04f && Math.round(total/10) <= 64) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.CAST_IRON_ALLOY, Math.round(total/10)),ar);
                }
            }
        }
        if (materials.contains("gold") && materials.contains("zinc") && total >= 10) // White Gold
        {
            int x1 = materials.indexOf("gold");
            int x2 = materials.indexOf("zinc");
            int x3;
            if (x1 == 0 && x2 == 1 || x2 == 0 && x1 == 1)
            {
                x3 = 2;
            }
            else if (x1 == 2 && x2 == 0 || x2 == 2 && x1 == 0)
            {
                x3 = 1;
            }
            else
            {
                x3 = 0;
            }
            if (((materials.get(x3).equals("nickel") || materials.get(x3).equals("palladium") || materials.get(x3).equals("silver") || materials.get(x3).equals("platinum"))
                && new PeriodicTableUtils().getImplementedElementNames().contains(materials.get(x3)))
                    || materials.get(x3).equals("none")) {

                float propx1 = amounts.get(x1)/total;
                float propx2 = amounts.get(x2)/total;
                float propx3 = amounts.get(x3)/total;
                /*
                System.out.println(propx1);
                System.out.println(propx2);
                System.out.println(propx3);
                System.out.println(Math.round(total/10));
                 */
                if (propx1 >= .74f && propx1 <= .76f && propx2 >= .05f && propx2 <= .10f && propx3 >= .05f && Math.round(total/10) <= 64) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.WHITE_GOLD_ALLOY, Math.round(total/10)),ar);
                }
            }
        }
        if (materials.contains("gold") && materials.contains("copper") && total >= 10) // Rose Gold
        {
            int x1 = materials.indexOf("gold");
            int x2 = materials.indexOf("copper");
            int x3;
            if (x1 == 0 && x2 == 1 || x2 == 0 && x1 == 1)
            {
                x3 = 2;
            }
            else if (x1 == 2 && x2 == 0 || x2 == 2 && x1 == 0)
            {
                x3 = 1;
            }
            else
            {
                x3 = 0;
            }
            if (materials.get(x3).equals("silver") || materials.get(x3).equals("zinc")
                    || materials.get(x3).equals("none")) {

                float propx1 = amounts.get(x1)/total;
                float propx2 = amounts.get(x2)/total;
                float propx3 = amounts.get(x3)/total;
                /*
                System.out.println(propx1);
                System.out.println(propx2);
                System.out.println(propx3);
                System.out.println(Math.round(total/10));
                 */
                if (propx1 >= .74f && propx1 <= .76f && propx2 >= .20f && propx2 <= .25f && Math.round(total/10) <= 64) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.ROSE_GOLD_ALLOY, Math.round(total/10)),ar);
                }
            }
        }
        if (materials.contains("gold") && materials.contains("silver") && total >= 10) // Green Gold
        {
            int x1 = materials.indexOf("gold");
            int x2 = materials.indexOf("silver");
            int x3;
            if (x1 == 0 && x2 == 1 || x2 == 0 && x1 == 1)
            {
                x3 = 2;
            }
            else if (x1 == 2 && x2 == 0 || x2 == 2 && x1 == 0)
            {
                x3 = 1;
            }
            else
            {
                x3 = 0;
            }
            if (((materials.get(x3).equals("copper") || materials.get(x3).equals("cadmium") || materials.get(x3).equals("platinum"))
                && new PeriodicTableUtils().getImplementedElementNames().contains(materials.get(x3)))
                    || materials.get(x3).equals("none")) {

                float propx1 = amounts.get(x1)/total;
                float propx2 = amounts.get(x2)/total;
                float propx3 = amounts.get(x3)/total;
                /*
                System.out.println(propx1);
                System.out.println(propx2);
                System.out.println(propx3);
                System.out.println(Math.round(total/10));
                 */
                if (propx1 >= .3f && propx1 <= .7f && propx2 >= .3f && propx2 <= .7f  && propx1 + propx2 >= .90f && Math.round(total/10) <= 64) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.GREEN_GOLD_ALLOY, Math.round(total/10)),ar);
                }
            }
        }
        if (materials.contains("gold") && materials.contains("aluminum") && total >= 10) // Purple Gold
        {
            int x1 = materials.indexOf("gold");
            int x2 = materials.indexOf("aluminum");
            int x3;
            if (x1 == 0 && x2 == 1 || x2 == 0 && x1 == 1)
            {
                x3 = 2;
            }
            else if (x1 == 2 && x2 == 0 || x2 == 2 && x1 == 0)
            {
                x3 = 1;
            }
            else
            {
                x3 = 0;
            }
            if (materials.get(x3).equals("none")) {

                float propx1 = amounts.get(x1)/total;
                float propx2 = amounts.get(x2)/total;
                float propx3 = amounts.get(x3)/total;
                /*
                System.out.println(propx1);
                System.out.println(propx2);
                System.out.println(propx3);
                System.out.println(Math.round(total/10));
                 */
                if (propx1 >= .79f && propx1 <= .81f && propx2 >= .19f && propx2 <= .21f && Math.round(total/10) <= 64) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.PURPLE_GOLD_ALLOY, Math.round(total/10)),ar);
                }
            }
        }
        if (materials.contains("gold") && materials.contains("aluminum") && total >= 10) // Purple Gold
        {
            int x1 = materials.indexOf("gold");
            int x2 = materials.indexOf("iron");
            int x3;
            if (x1 == 0 && x2 == 1 || x2 == 0 && x1 == 1)
            {
                x3 = 2;
            }
            else if (x1 == 2 && x2 == 0 || x2 == 2 && x1 == 0)
            {
                x3 = 1;
            }
            else
            {
                x3 = 0;
            }
            if (((materials.get(x3).equals("nickel") || materials.get(x3).equals("ruthenium") || materials.get(x3).equals("rhodium"))
                    && new PeriodicTableUtils().getImplementedElementNames().contains(materials.get(x3))) || materials.get(x3).equals("none")) {

                float propx1 = amounts.get(x1)/total;
                float propx2 = amounts.get(x2)/total;
                float propx3 = amounts.get(x3)/total;
                /*
                System.out.println(propx1);
                System.out.println(propx2);
                System.out.println(propx3);
                System.out.println(Math.round(total/10));
                 */
                if (propx1 >= .74f && propx1 <= .76f && propx2 >= .19f && propx2 <= .21f && Math.round(total/10) <= 64) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.BLUE_GOLD_ALLOY, Math.round(total/10)),ar);
                }
            }
        }
        if (materials.contains("copper") && materials.contains("nickel") && total >= 10) // Cupronickel
        {
            int x1 = materials.indexOf("copper");
            int x2 = materials.indexOf("nickel");
            int x3;
            if (x1 == 0 && x2 == 1 || x2 == 0 && x1 == 1)
            {
                x3 = 2;
            }
            else if (x1 == 2 && x2 == 0 || x2 == 2 && x1 == 0)
            {
                x3 = 1;
            }
            else
            {
                x3 = 0;
            }
            if (materials.get(x3).equals("iron") || materials.get(x3).equals("manganese")
                    || materials.get(x3).equals("none")) {

                float propx1 = amounts.get(x1)/total;
                float propx2 = amounts.get(x2)/total;
                float propx3 = amounts.get(x3)/total;
                /*
                System.out.println(propx1);
                System.out.println(propx2);
                System.out.println(propx3);
                System.out.println(Math.round(total/10));
                 */
                if (propx1 >= .70f && propx1 <= .90f && propx2 >= .10f && propx2 <= .30f && propx1 + propx2 >= .95f && Math.round(total/10) <= 64) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.CUPRONICKEL_ALLOY, Math.round(total/10)),ar);
                }
            }
        }
        if (materials.contains("copper") && materials.contains("nickel") && materials.contains("zinc")  && total >= 10) // Nickel Silver
        {
            int x1 = materials.indexOf("copper");
            int x2 = materials.indexOf("nickel");
            int x3;
            if (x1 == 0 && x2 == 1 || x2 == 0 && x1 == 1)
            {
                x3 = 2;
            }
            else if (x1 == 2 && x2 == 0 || x2 == 2 && x1 == 0)
            {
                x3 = 1;
            }
            else
            {
                x3 = 0;
            }
            if (materials.get(x3).equals("zinc") && new PeriodicTableUtils().getImplementedElementNames().contains(materials.get(x3))){

                float propx1 = amounts.get(x1)/total;
                float propx2 = amounts.get(x2)/total;
                float propx3 = amounts.get(x3)/total;
                /*
                System.out.println(propx1);
                System.out.println(propx2);
                System.out.println(propx3);
                System.out.println(Math.round(total/10));
                 */
                if (propx1 >= .50f && propx1 <= .70f && propx2 >= .15f && propx2 <= .25f && propx3 >= .15f && propx3 <= .25f && Math.round(total/10) <= 64) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.NICKEL_SILVER_ALLOY, Math.round(total/10)),ar);
                }
            }
        }
        if (materials.contains("aluminum") && materials.contains("magnesium") && total >= 10) // Magnalium
        {
            int x1 = materials.indexOf("aluminum");
            int x2 = materials.indexOf("magnesium");
            int x3;
            if (x1 == 0 && x2 == 1 || x2 == 0 && x1 == 1)
            {
                x3 = 2;
            }
            else if (x1 == 2 && x2 == 0 || x2 == 2 && x1 == 0)
            {
                x3 = 1;
            }
            else
            {
                x3 = 0;
            }
            if (materials.get(x3).equals("none")) {

                float propx1 = amounts.get(x1)/total;
                float propx2 = amounts.get(x2)/total;
                float propx3 = amounts.get(x3)/total;
                /*
                System.out.println(propx1);
                System.out.println(propx2);
                System.out.println(propx3);
                System.out.println(Math.round(total/10));
                 */
                if (propx1 >= .94f && propx1 <= .96f && propx2 >= .04f && propx2 <= .06f && Math.round(total/10) <= 64) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.MAGNALIUM_ALLOY, Math.round(total/10)),ar);
                }
            }
        }
        if (materials.contains("iron") && materials.contains("nickel") && total >= 10) // invar
        {
            int x1 = materials.indexOf("iron");
            int x2 = materials.indexOf("nickel");
            int x3;
            if (x1 == 0 && x2 == 1 || x2 == 0 && x1 == 1)
            {
                x3 = 2;
            }
            else if (x1 == 2 && x2 == 0 || x2 == 2 && x1 == 0)
            {
                x3 = 1;
            }
            else
            {
                x3 = 0;
            }
            if (materials.get(x3).equals("none")) {

                float propx1 = amounts.get(x1)/total;
                float propx2 = amounts.get(x2)/total;
                float propx3 = amounts.get(x3)/total;
                /*
                System.out.println(propx1);
                System.out.println(propx2);
                System.out.println(propx3);
                System.out.println(Math.round(total/10));
                 */
                if (propx1 >= .50f && propx1 <= .90f && propx2 >= .10f && propx2 <= .50f && Math.round(total/10) <= 64) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.INVAR_ALLOY, Math.round(total/10)),ar);
                }
            }
        }
        if (materials.contains("mercury") && materials.contains("gold") && total >= 10) // Amalgam
        {
            int x1 = materials.indexOf("mercury");
            int x2 = materials.indexOf("gold");
            int x3;
            if (x1 == 0 && x2 == 1 || x2 == 0 && x1 == 1)
            {
                x3 = 2;
            }
            else if (x1 == 2 && x2 == 0 || x2 == 2 && x1 == 0)
            {
                x3 = 1;
            }
            else
            {
                x3 = 0;
            }
            if (Config.AMALGAM_EXTRAS.get() && (new PeriodicTableUtils().getImplementedElementNames().contains(materials.get(x3)) || materials.get(x3).equals("none")))
            {
                float propx1 = amounts.get(x1)/total;
                float propx2 = amounts.get(x2)/total;
                float propx3 = amounts.get(x3)/total;
                /*
                System.out.println(propx1);
                System.out.println(propx2);
                System.out.println(propx3);
                System.out.println(Math.round(total/10));
                 */
                if (propx1 >= .25f && propx1 <= .8f && propx2 >= .25f && propx2 <= .5f && Math.round(total/10) <= 64) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.AMALGAM_ALLOY, Math.round(total/10)),ar);
                }
            }
            if ((!materials.get(x3).equals("iron") && !materials.get(x3).equals("platinum") && !materials.get(x3).equals("tungsten") && !materials.get(x3).equals("tantalum")
                    && !materials.get(x3).equals("mercury") && !materials.get(x3).equals("gold") && new PeriodicTableUtils().getImplementedElementNames().contains(materials.get(x3))) || materials.get(x3).equals("none")) {

                float propx1 = amounts.get(x1)/total;
                float propx2 = amounts.get(x2)/total;
                float propx3 = amounts.get(x3)/total;
                /*
                System.out.println(propx1);
                System.out.println(propx2);
                System.out.println(propx3);
                System.out.println(Math.round(total/10));
                 */
                if (propx1 >= .25f && propx1 <= .8f && propx2 >= .25f && propx2 <= .5f && Math.round(total/10) <= 64) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.AMALGAM_ALLOY, Math.round(total/10)),ar);
                }
            }
        }
        if (materials.contains("nickel") && materials.contains("chromium") && total >= 10) // Nickel Superalloy
        {
            int x1 = materials.indexOf("nickel");
            int x2 = materials.indexOf("chromium");
            int x3;
            if (x1 == 0 && x2 == 1 || x2 == 0 && x1 == 1)
            {
                x3 = 2;
            }
            else if (x1 == 2 && x2 == 0 || x2 == 2 && x1 == 0)
            {
                x3 = 1;
            }
            else
            {
                x3 = 0;
            }
            if (((materials.get(x3).equals("molybdenum") || materials.get(x3).equals("niobium") || materials.get(x3).equals("tantalum") || materials.get(x3).equals("cobalt") ||
                    materials.get(x3).equals("manganese") || materials.get(x3).equals("aluminum") || materials.get(x3).equals("titanium") || materials.get(x3).equals("silicon") ||
                    materials.get(x3).equals("carbon") || materials.get(x3).equals("phosphorus") || materials.get(x3).equals("boron") || materials.get(x3).equals("iron") ||
                    materials.get(x3).equals("vanadium") || materials.get(x3).equals("zirconium") || materials.get(x3).equals("hafnium") || materials.get(x3).equals("rhenium") ||
                    materials.get(x3).equals("tungsten")) && new PeriodicTableUtils().getImplementedElementNames().contains(materials.get(x3))) || materials.get(x3).equals("none")) {

                float propx1 = amounts.get(x1)/total;
                float propx2 = amounts.get(x2)/total;
                float propx3 = amounts.get(x3)/total;
                /*
                System.out.println(propx1);
                System.out.println(propx2);
                System.out.println(propx3);
                System.out.println(Math.round(total/10));
                 */
                if (propx1 >= .50f && propx1 <= .75f && propx2 >= .14f && propx2 <= .3f && Math.round(total/10) <= 64) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new AbstractMap.SimpleEntry<>(new ItemStack(ModItems.NICKEL_SUPERALLOY, Math.round(total/10)),ar);
                }
            }
        }
        int[] ar = new int[3];
        ar[0] = 1;
        ar[1] = 1;
        ar[2] = 1;
        return new AbstractMap.SimpleEntry<>(ItemStack.EMPTY, ar);
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
        PeriodicTableUtils a = new PeriodicTableUtils();
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
