package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.items.alloys.AlloyData;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyRecipeHelper;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.AbstractMap;
import java.util.List;

public class TripleAlloyTemplateItem extends Item {
    PeriodicTableUtils utils = new PeriodicTableUtils();
    public TripleAlloyTemplateItem(Properties properties) {
        super(properties);
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        if (getTemplate(stack).size() != 0) {
            String comp = getTemplate(stack).get("NameAdd").getString();
            String p1;
            String p2;
            if (comp.contains("#"))
            {
                p1 = comp.split("#")[0];
                p2 = new TranslationTextComponent(comp.split("#")[1]).getString();
            } else
            {
                p1 = "";
                p2 = new TranslationTextComponent(comp).getString();
            }

            String p3 = new TranslationTextComponent(this.getTranslationKey(stack)).getString().split(" ")[2];
            return new StringTextComponent( p1 + " " + p2 + " " + p3);
        } else {
            return new TranslationTextComponent(this.getTranslationKey(stack));
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (getTemplate(stack).size() != 0) {
            tooltip.add(new StringTextComponent("Requires:").mergeStyle(TextFormatting.DARK_GREEN));
            String comp = getTemplate(stack).get("StoredTemplate").getString();
            int count = 0;
            for (String s : comp.split("-"))
            {
                String str = s.replaceAll("[^A-Za-z]+", "");
                String end = "";
                int num = Integer.parseInt(s.replaceAll("[A-Za-z]+",""));

                ListNBT nbt = getTemplate(stack).getList("Inputs",10);
                String t = nbt.getString(count).split("\"")[1];
                String namespace = t.split(":")[0];
                String path = t.split(":")[1];
                if (path.contains("ingot") || path.equals("silicon") || path.equals("phosphorus") || path.equals("sulfur") || path.equals("astatine"))
                {
                    num = num/9;
                    //end = t.contains("ingot") ? "Ingot" : "";
                } else if (path.contains("block"))
                {
                    num = num/81;
                    //end = "Block";
                } else if (path.equals("graphite")){
                    num = num/3;
                }
                count++;


                String display = new TranslationTextComponent(ForgeRegistries.ITEMS.getValue(new ResourceLocation(namespace,path)).getTranslationKey()).getString();
                tooltip.add(new StringTextComponent(num + "x " + display).mergeStyle(TextFormatting.GRAY));
                //tooltip.add(new StringTextComponent(num + "x " + utils.getElementBySymbol(str).toString().substring(0,1).toUpperCase() + utils.getElementBySymbol(str).toString().substring(1).toLowerCase() + " " + end).mergeStyle(TextFormatting.GRAY));
            }
        }
    }

    public static CompoundNBT getTemplate(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null ? compoundnbt : new CompoundNBT();
    }

    public static String assembleTemplateData(ItemStack... stacks)
    {
        PeriodicTableUtils utils = new PeriodicTableUtils();
        StringBuilder ret = new StringBuilder();
        for (ItemStack i : stacks)
        {
            if (i.isEmpty())
            {
                break;
            }
            if (!ret.toString().isEmpty())
            {
                ret.append("-");
            }
            AbstractMap.SimpleEntry<String,Integer> s = AlloyRecipeHelper.getInstance().returnItemMaterial(i);
            ret.append(s.getValue()).append(utils.getElementByMaterial(s.getKey()));
        }
        return ret.toString();
    }

    public static void addTemplate(ItemStack stack,ItemStack output, String alloy,  ItemStack ... inputs) {
        ListNBT listnbt = new ListNBT();
        stack.getOrCreateTag().putString("StoredTemplate",assembleTemplateData(inputs));
        stack.getOrCreateTag().putString("NameAdd",output.getCount() + "x#" + output.getTranslationKey());
        for (ItemStack i: inputs)
        {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putString(i.getDisplayName().getString().split(" ")[0], i.getItem().getRegistryName().toString());
            listnbt.add(nbt);
        }
        stack.getOrCreateTag().put("Inputs", listnbt);
        stack.getOrCreateTag().putString("Output",output.getItem().getRegistryName().toString());
        stack.getOrCreateTag().putString("AlloyData",alloy);
    }

    public static ItemStack getResult(ItemStack stack)
    {
        if (getTemplate(stack).size() != 0) {
            CompoundNBT compoundNBT = getTemplate(stack);
            String name = compoundNBT.get("NameAdd").getString();
            int num;
            try{
                num = Integer.parseInt(name.replaceAll("[^0-9]+",""));
            } catch (NumberFormatException e)
            {
                return ItemStack.EMPTY;
            }
            String namespace = compoundNBT.get("Output").getString().split(":")[0];
            String path = compoundNBT.get("Output").getString().split(":")[1];
            ItemStack item = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(namespace,path)),num);
            if (item.getItem() instanceof AlloyItem)
            {
                AlloyItem.addAlloy(item,new AlloyData(compoundNBT.get("AlloyData").getString()));
                return item;
            }
        }
        return ItemStack.EMPTY;
    }

    public static int[] getShrinkAmount(ItemStack stack) {
        int[] ret = new int[]{0,0,0,0,0};
        if (getTemplate(stack).size() != 0) {
            String comp = getTemplate(stack).get("StoredTemplate").getString();
            int count = 0;
            for (String s : comp.split("-"))
            {
                int num = Integer.parseInt(s.replaceAll("[A-Za-z]+",""));
                ListNBT nbt = getTemplate(stack).getList("Inputs",10);
                String t = nbt.getString(count).split("\"")[1].split(":")[1];
                if (t.contains("ingot") || t.equals("silicon") || t.equals("phosphorus") || t.equals("sulfur") || t.equals("astatine"))
                {
                    ret[count] = num/9;
                } else if (t.contains("block"))
                {
                    ret[count] = num/81;
                } else if (t.equals("graphite")){
                    ret[count] = num/3;
                } else {
                    ret[count] = num;
                }
                count++;
            }
        }
        return ret;
    }

    public static ItemStack[] getInputStacks(ItemStack stack)
    {
        ItemStack[] x = new ItemStack[]{ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
        int[] y = getShrinkAmount(stack);
        if (getTemplate(stack).size() != 0) {
            ListNBT nbt = getTemplate(stack).getList("Inputs",10);
            for (int i = 0; i < nbt.size(); i++)
            {
                String s = nbt.getString(i).split("\"")[1];
                //System.out.println("Current string: " + s);
                if (!s.equals("minecraft:air"))
                {
                    String namespace = s.split(":")[0];
                    String path = s.split(":")[1];
                    x[i] = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(namespace,path)),y[i]);
                    //System.out.println("Assigning to array variable: " + x[i]);
                }

            }
        }
        return x;
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (getTemplate(stack).size() == 0 && !worldIn.isRemote)
        {
            int random = worldIn.getRandom().nextInt(7);
            ItemStack[] inputs;
            ItemStack output;
            switch (random)
            {
                case 0:
                default:
                    inputs = new ItemStack[]{new ItemStack(RankineItems.TUNGSTEN_INGOT.get(),9),new ItemStack(RankineItems.NICKEL_NUGGET.get(),6),new ItemStack(Items.IRON_NUGGET,3),ItemStack.EMPTY,ItemStack.EMPTY};
                    output = new ItemStack(RankineItems.TUNGSTEN_HEAVY_ALLOY.get(), 9);
                case 1:
                    inputs = new ItemStack[]{new ItemStack(Items.IRON_INGOT,5),new ItemStack(RankineItems.CHROMIUM_NUGGET.get(),8),new ItemStack(RankineItems.CARBON_NUGGET.get(),1),
                            new ItemStack(RankineItems.NICKEL_NUGGET.get(),6),ItemStack.EMPTY};
                    output = new ItemStack(RankineItems.STAINLESS_STEEL_ALLOY.get(), 6);
                case 2:
                    inputs = new ItemStack[]{new ItemStack(RankineItems.BISMUTH_INGOT.get(),5),new ItemStack(RankineItems.LEAD_INGOT.get(),3),new ItemStack(RankineItems.TIN_INGOT.get(),2),
                            ItemStack.EMPTY,ItemStack.EMPTY};
                    output = new ItemStack(RankineItems.ROSE_METAL_ALLOY.get(), 9);
                case 3:
                    inputs = new ItemStack[]{new ItemStack(RankineItems.CERIUM_INGOT.get(),5),new ItemStack(RankineItems.LANTHANUM_NUGGET.get(),22),new ItemStack(RankineItems.NEODYMIUM_NUGGET.get(),18),
                            new ItemStack(Items.IRON_NUGGET,5),ItemStack.EMPTY};
                    output = new ItemStack(RankineItems.MISCHMETAL_ALLOY.get(), 9);
                case 4:
                    inputs = new ItemStack[]{new ItemStack(RankineItems.NICKEL_INGOT.get(),8),new ItemStack(RankineItems.TITANIUM_INGOT.get(),1),new ItemStack(RankineItems.ALUMINUM_INGOT.get(),1),
                            ItemStack.EMPTY,ItemStack.EMPTY};
                    output = new ItemStack(RankineItems.NICKEL_SUPERALLOY.get(), 9);
                case 5:
                    inputs = new ItemStack[]{new ItemStack(RankineItems.NICKEL_INGOT.get(),7),new ItemStack(RankineItems.CHROMIUM_INGOT.get(),2),new ItemStack(RankineItems.COBALT_INGOT.get(),1),
                            ItemStack.EMPTY,ItemStack.EMPTY};
                    output = new ItemStack(RankineItems.NICKEL_SUPERALLOY.get(), 9);
                case 6:
                    inputs = new ItemStack[]{new ItemStack(RankineItems.COBALT_INGOT.get(),7),new ItemStack(RankineItems.CHROMIUM_INGOT.get(),2),new ItemStack(RankineItems.NICKEL_INGOT.get(),1),
                            new ItemStack(RankineItems.TANTALUM_INGOT.get(),1),ItemStack.EMPTY};
                    output = new ItemStack(RankineItems.COBALT_SUPERALLOY.get(), 9);
            }
            addTemplate(stack, output, AlloyRecipeHelper.getInstance().getTripleComposition(inputs[0],inputs[1],inputs[2],inputs[3],inputs[4]),
                    inputs[0],inputs[1],inputs[2],inputs[3],inputs[4]);

        }
    }
}