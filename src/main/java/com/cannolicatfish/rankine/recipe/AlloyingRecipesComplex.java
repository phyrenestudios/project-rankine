package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.ModItems;
import com.cannolicatfish.rankine.util.ElementUtils;
import com.cannolicatfish.rankine.util.RankineAlloyMaterial;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import javafx.util.Pair;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

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

    public Pair<String,Integer> returnItemMaterial(ItemStack stack)
    {
        System.out.println(RankineAlloyMaterial.getMaterial(stack.getItem()).toString());
        if (stack.isEmpty())
        {
            return new Pair<>("none",0);
        }

        String reg = stack.getItem().getRegistryName().toString();
        String mat = "none";
        int amt = 0;
        if (!reg.contains(":"))
        {
            return new Pair<>("nope",0);
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
                    mat = "pure_carbon";
                    amt = stack.getCount();
                    break;
                case "graphite":
                    mat = "pure_carbon";
                    amt = 3 * stack.getCount();
                    break;
                case "anthracite_coal":
                    mat = "carbon";
                    amt = 4 * stack.getCount();
                    break;
                case "bituminous_coal":
                    mat = "carbon";
                    amt = 3 * stack.getCount();
                    break;
                case "subbituminous_coal":
                    mat = "carbon";
                    amt = 2 * stack.getCount();
                    break;
                default:
                    amt = stack.getCount();
                    break;
            }
        }
        if (!mat.equals("none"))
        {
            return new Pair<>(mat,amt);
        }
        if (!reg.contains("_") && mat.equals("none"))
        {
            return new Pair<>("nope",0);
        }
        if (reg.contains("block"))
        {
            mat = reg.split("_")[0];
            amt = 81 * stack.getCount();
        }
        else if (reg.contains("ingot"))
        {
            mat = reg.split("_")[0];
            amt = 9 * stack.getCount();
        }
        else if (reg.contains("nugget"))
        {
            mat = reg.split("_")[0];
            amt = stack.getCount();
        }

        return new Pair<>(mat,amt);
    }

    public Pair<ItemStack,int[]> getAlloyResult(ItemStack input1, ItemStack input2, ItemStack input3) {
        List<Item> inputList = Arrays.asList(input1.getItem(),input2.getItem(),input3.getItem());
        if (inputList.stream().distinct().count() < 3)
        {
            int[] ar = new int[3];
            ar[0] = 1;
            ar[1] = 1;
            ar[2] = 1;
            return new Pair<>(ItemStack.EMPTY, ar);
        }
        List<Integer> inputValList = Arrays.asList(input1.getCount(),input2.getCount(),input3.getCount());
        List<String> materials = Arrays.asList(returnItemMaterial(input1).getKey(),returnItemMaterial(input2).getKey(),returnItemMaterial(input3).getKey());
        List<Integer> amounts = Arrays.asList(returnItemMaterial(input1).getValue(),returnItemMaterial(input2).getValue(),returnItemMaterial(input3).getValue());
        float total = returnItemMaterial(input1).getValue() + returnItemMaterial(input2).getValue() + returnItemMaterial(input3).getValue();
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
                    return new Pair<>(new ItemStack(ModItems.BRONZE_ALLOY, Math.round(total/10)),ar);
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
            if (materials.get(x3).equals("iron") || materials.get(x3).equals("manganese") || materials.get(x3).equals("nickel") || materials.get(x3).equals("zinc") || materials.get(x3).equals("arsenic")
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
                if (propx1 <= .93f && propx1 >= .74f && propx2 >= .04f && propx2 <= .12f && propx1 + propx2 >= .86f && Math.round(total/10) <= 64) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new Pair<>(new ItemStack(ModItems.ALUMINUM_BRONZE_ALLOY, Math.round(total/10)),ar);
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
                    return new Pair<>(new ItemStack(ModItems.BRASS_ALLOY, Math.round(total/10)),ar);
                }
            }
        }
        if (materials.contains("unref_iron") && materials.contains("pure_carbon") && total >= 10) // Cast Iron
        {
            int x1 = materials.indexOf("unref_iron");
            int x2 = materials.indexOf("pure_carbon");
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
            if (materials.get(x3).equals("tin") || materials.get(x3).equals("manganese") || materials.get(x3).equals("nickel") || materials.get(x3).equals("chromium") ||
                    materials.get(x3).equals("molybdenum") || materials.get(x3).equals("titanium") || materials.get(x3).equals("vanadium") || materials.get(x3).equals("none")) {

                float propx1 = amounts.get(x1)/total;
                float propx2 = amounts.get(x2)/total;
                float propx3 = amounts.get(x3)/total;

                System.out.println(propx1);
                System.out.println(propx2);
                System.out.println(propx3);
                System.out.println(Math.round(total/10));
                if (propx1 >= .86f && propx1 <= .98f && propx2 >= .02f && propx2 <= .04f && Math.round(total/10) <= 64) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new Pair<>(new ItemStack(ModItems.CAST_IRON_INGOT, Math.round(total/10)),ar);
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
            if (materials.get(x3).equals("nickel") || materials.get(x3).equals("palladium") || materials.get(x3).equals("silver") || materials.get(x3).equals("platinum")) {

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
                    return new Pair<>(new ItemStack(ModItems.WHITE_GOLD_ALLOY, Math.round(total/10)),ar);
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
                    return new Pair<>(new ItemStack(ModItems.ROSE_GOLD_ALLOY, Math.round(total/10)),ar);
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
            if (materials.get(x3).equals("copper") || materials.get(x3).equals("cadmium") || materials.get(x3).equals("platinum")
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
                    return new Pair<>(new ItemStack(ModItems.GREEN_GOLD_ALLOY, Math.round(total/10)),ar);
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
                    return new Pair<>(new ItemStack(ModItems.PURPLE_GOLD_ALLOY, Math.round(total/10)),ar);
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
            if (materials.get(x3).equals("nickel") || materials.get(x3).equals("ruthenium") || materials.get(x3).equals("rhodium")
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
                if (propx1 >= .74f && propx1 <= .76f && propx2 >= .19f && propx2 <= .21f && Math.round(total/10) <= 64) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new Pair<>(new ItemStack(ModItems.BLUE_GOLD_ALLOY, Math.round(total/10)),ar);
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
                if (propx1 >= .71f && propx1 <= .90f && propx2 >= .10f && propx2 <= .360f && propx1 + propx2 >= .95f && Math.round(total/10) <= 64) {
                    int[] ar = new int[3];
                    ar[0] = input1.getCount();
                    ar[1] = input2.getCount();
                    ar[2] = input3.getCount();
                    return new Pair<>(new ItemStack(ModItems.CUPRONICKEL_ALLOY, Math.round(total/10)),ar);
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
            if (materials.get(x3).equals("zinc")){

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
                    return new Pair<>(new ItemStack(ModItems.NICKEL_SILVER_ALLOY, Math.round(total/10)),ar);
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
                    return new Pair<>(new ItemStack(ModItems.MAGNALIUM_ALLOY, Math.round(total/10)),ar);
                }
            }
        }
        int[] ar = new int[3];
        ar[0] = 1;
        ar[1] = 1;
        ar[2] = 1;
        return new Pair<>(ItemStack.EMPTY, ar);
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
        ElementUtils a = new ElementUtils();
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
        if (percents.get(2) != 0)
        {
            return percents.get(0) + a.getElementbyMaterial(returnItemMaterial(inputs.get(0)).getKey()) + "-" + percents.get(1) +
                    a.getElementbyMaterial(returnItemMaterial(inputs.get(1)).getKey()) + "-" + percents.get(2) + a.getElementbyMaterial(returnItemMaterial(inputs.get(2)).getKey());
        } else {
            return percents.get(0) + a.getElementbyMaterial(returnItemMaterial(inputs.get(0)).getKey()) + "-" + percents.get(1) +
                    a.getElementbyMaterial(returnItemMaterial(inputs.get(1)).getKey());
        }

    }

    public Pair<List<Integer>,List<ItemStack>> getPercents(ItemStack input1, ItemStack input2, ItemStack input3)
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
                return new Pair<>(Arrays.asList(percent1,percent2,percent3),Arrays.asList(input1,input2,input3));
            } else {
                return new Pair<>(Arrays.asList(percent1,percent3,percent2),Arrays.asList(input1,input3,input2));
            }
        }
        else if (percent2 >= percent1 && percent2 >= percent3)
        {
            if (percent1 >= percent3)
            {
                return new Pair<>(Arrays.asList(percent2,percent1,percent3),Arrays.asList(input2,input1,input3));
            } else {
                return new Pair<>(Arrays.asList(percent2,percent3,percent1),Arrays.asList(input2,input3,input1));
            }
        }
        else
        {
            if (percent1 >= percent2)
            {
                return new Pair<>(Arrays.asList(percent3,percent1,percent2),Arrays.asList(input3,input1,input2));
            } else {
                return new Pair<>(Arrays.asList(percent3,percent2,percent1),Arrays.asList(input3,input2,input2));
            }
        }
    }

    public String getCompositionAlloy(CompoundNBT nbt)
    {
        return nbt.getString();
    }

}
