package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineRecipes;
import com.cannolicatfish.rankine.items.alloys.AlloyData;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.recipe.helper.AlloyRecipeHelper;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
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
import java.util.ArrayList;
import java.util.List;

public class AlloyTemplateItem extends Item {
    public AlloyTemplateItem(Properties properties) {
        super(properties);
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        if (getTemplate(stack).size() != 0) {
            String comp = getTemplate(stack).get("NameAdd").getString();
            String p1;
            String p2;
            String p3;
            if (comp.contains("#"))
            {
                p1 = comp.split("#")[0];
                p2 = new TranslationTextComponent(comp.split("#")[1]).getString();
            } else
            {
                p1 = "";
                p2 = new TranslationTextComponent(comp).getString();
            }

            ITextComponent text = new TranslationTextComponent(this.getTranslationKey(stack));
            if (text.getString().split(" ").length <= 1) {
                // local is Chinese or Japanese
                p3 = text.getString();
            } else {
                p3 = text.getString().split(" ")[1];
            }

            return new StringTextComponent( p1 + " " + p2 + " " + p3);
        } else {
            return new TranslationTextComponent(this.getTranslationKey(stack));
        }

    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (getTemplate(stack).size() != 0) {
            tooltip.add(new StringTextComponent("Composition: " + getOutputAlloyData(stack)).mergeStyle(TextFormatting.GRAY));
            tooltip.add(new StringTextComponent("Requires:").mergeStyle(TextFormatting.DARK_GREEN));
            String comp = getTemplate(stack).get("StoredTemplate").getString();
            int count = 0;
            for (String s : comp.split("-"))
            {
                String str = s.replaceAll("[^A-Za-z]+", "");
                String end = "";
                int num = Integer.parseInt(s.replaceAll("[A-Za-z]+",""));

                String namespace, path;
                ListNBT nbt = getTemplate(stack).getList("Inputs",10);
                String nbtstring = nbt.getString(count);
                String t = nbtstring.split("\"")[3];
                namespace = t.split(":")[0];
                path = t.split(":")[1];

                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(namespace,path));
                if (item != null) {
                    int reduce = AlloyRecipeHelper.returnMaterialCountFromStack(new ItemStack(item,1));
                    num = num/reduce;
                    count++;


                    String display = new TranslationTextComponent(item.getTranslationKey()).getString();
                    tooltip.add(new StringTextComponent(num + "x " + display).mergeStyle(TextFormatting.GRAY));
                }

                //tooltip.add(new StringTextComponent(num + "x " + utils.getElementBySymbol(str).toString().substring(0,1).toUpperCase() + utils.getElementBySymbol(str).toString().substring(1).toLowerCase() + " " + end).mergeStyle(TextFormatting.GRAY));
            }
            tooltip.add(new StringTextComponent( ""));
            tooltip.add(new StringTextComponent( "Made in:").mergeStyle(TextFormatting.DARK_GREEN));
            int tier = getTier(stack);
            if ((tier & 1) != 0) {
                tooltip.add(new StringTextComponent( "Alloy Furnace").mergeStyle(TextFormatting.GRAY));
            }
            if ((tier & 2) != 0) {
                tooltip.add(new StringTextComponent( "Induction Furnace").mergeStyle(TextFormatting.GRAY));
            }
        }
    }

    public static void addTemplate(World worldIn, ItemStack stack, AlloyingRecipe recipe, IInventory inv, DyeItem dye) {
        ListNBT listnbt = new ListNBT();
        ItemStack output = recipe.generateResult(worldIn,inv,3);

        stack.getOrCreateTag().putString("StoredTemplate",assembleTemplateData(inv,0,6));
        stack.getOrCreateTag().putString("NameAdd",output.getCount() + "x#" + output.getTranslationKey());
        for (int i = 0; i < 6; i++)
        {
            if (!inv.getStackInSlot(i).isEmpty()) {
                CompoundNBT cnbt = new CompoundNBT();
                cnbt.putString(inv.getStackInSlot(i).getDisplayName().getString().split(" ")[0], inv.getStackInSlot(i).getItem().getRegistryName().toString());
                listnbt.add(cnbt);
            }
        }
        stack.getOrCreateTag().put("Inputs", listnbt);
        stack.getOrCreateTag().putString("Output",output.getItem().getRegistryName().toString());
        INBT nbt = AlloyItem.getComposition(output).getCompound(0).get("comp");
        stack.getOrCreateTag().putString("AlloyData",nbt != null ? nbt.getString() : RankineRecipes.generateAlloyString(inv));
        stack.getOrCreateTag().putInt("Tier",recipe.getTier());
        stack.getOrCreateTag().putInt("Color", dye.getDyeColor().getColorValue());
    }

    public static String getOutputAlloyData(ItemStack stack) {
        return getTemplate(stack).getString("AlloyData");
    }

    public static int getTier(ItemStack stack) {
        return getTemplate(stack).getInt("Tier");
    }

    public static String assembleTemplateData(IInventory inv, int startIndex, int endIndex)
    {
        PeriodicTableUtils utils = PeriodicTableUtils.getInstance();
        StringBuilder ret = new StringBuilder();
        List<Integer> x = new ArrayList<>();
        List<String> s = new ArrayList<>();
        for (int i = startIndex; i < endIndex; i++)
        {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack.isEmpty())
            {
                continue;
            }
            String el = utils.getElementFromItem(stack.getItem()).getSymbol();
            int mat = AlloyRecipeHelper.returnMaterialCountFromStack(stack);
            if (!s.contains(el)) {
                s.add(el);
                x.add(mat);
            } else {
                x.set(s.indexOf(el),x.get(s.indexOf(el)) + mat);
            }
        }
        for (int j = 0; j < s.size(); j++) {
            if (!ret.toString().isEmpty())
            {
                ret.append("-");
            }
            ret.append(s.get(j)).append(x.get(j));
        }

        return ret.toString();
    }

    public static CompoundNBT getTemplate(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null ? compoundnbt : new CompoundNBT();
    }

    public static int getColor(ItemStack stack) {
        return getTemplate(stack).getInt("Color") == 0 ? 16777215 : getTemplate(stack).getInt("Color");
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


    public static ItemStack[] getInputStacks(ItemStack stack)
    {
        if (getTemplate(stack).size() != 0) {
            INBT temp = getTemplate(stack).get("StoredTemplate");
            boolean flag = temp != null;
            ListNBT nbt = getTemplate(stack).getList("Inputs",10);
            ItemStack[] x = new ItemStack[nbt.size()];
            for (int i = 0; i < nbt.size(); i++)
            {
                String s = nbt.getString(i).split("\"")[1];
                if (!s.equals("minecraft:air"))
                {
                    String namespace = s.split(":")[0];
                    String path = s.split(":")[1];
                    Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(namespace,path));
                    int amount = 1;
                    if (flag) {
                        if (temp.getString().split("-").length > i) {
                            String t = temp.getString().split("-")[i];
                            amount = Integer.parseInt(t.replaceAll("[A-Za-z]+",""))/AlloyRecipeHelper.returnMaterialCountFromStack(new ItemStack(item));
                        }
                    }
                    x[i] = new ItemStack(item,amount);
                }

            }
            return x;
        }
        return new ItemStack[0];
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (getTemplate(stack).size() == 0 && !worldIn.isRemote)
        {
            int random = worldIn.getRandom().nextInt(16);
            ItemStack[] inputs;
            AlloyingRecipe recipeIn;
            Inventory sim;
            switch (random)
            {
                case 0:
                default:
                    inputs = new ItemStack[]{new ItemStack(RankineItems.COPPER_INGOT.get(),8),new ItemStack(RankineItems.TIN_INGOT.get(),2),ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
                    break;
                case 1:
                    inputs = new ItemStack[]{new ItemStack(RankineItems.TIN_INGOT.get(),9),new ItemStack(RankineItems.ANTIMONY.get(),1),ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
                    break;
                case 2:
                    inputs = new ItemStack[]{new ItemStack(RankineItems.COPPER_INGOT.get(),6),new ItemStack(RankineItems.ZINC_INGOT.get(),4),ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
                    break;
                case 3:
                    inputs = new ItemStack[]{new ItemStack(Items.GOLD_INGOT,5),new ItemStack(RankineItems.COPPER_NUGGET.get(),13), new ItemStack(RankineItems.SILVER_NUGGET.get(),2),ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
                    break;
                case 4:
                    inputs = new ItemStack[]{new ItemStack(RankineItems.COPPER_INGOT.get(),9),new ItemStack(RankineItems.ALUMINUM_INGOT.get(),1),ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
                    break;
                case 5:
                    inputs = new ItemStack[]{new ItemStack(Items.IRON_INGOT,6),new ItemStack(RankineItems.NICKEL_INGOT.get(),4),ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
                    break;
                case 6:
                    inputs = new ItemStack[]{new ItemStack(Items.GOLD_INGOT,9),new ItemStack(RankineItems.ZINC_INGOT.get(),1),ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
                    break;
                case 7:
                    inputs = new ItemStack[]{new ItemStack(Items.GOLD_INGOT,5),new ItemStack(RankineItems.SILVER_INGOT.get(),5),ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
                    break;
                case 8:
                    inputs = new ItemStack[]{new ItemStack(Items.GOLD_INGOT,8),new ItemStack(RankineItems.ALUMINUM_INGOT.get(),2),ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
                    break;
                case 9:
                    inputs = new ItemStack[]{new ItemStack(RankineItems.TUNGSTEN_INGOT.get(),9),new ItemStack(RankineItems.NICKEL_NUGGET.get(),6),new ItemStack(Items.IRON_NUGGET,3),ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
                case 10:
                    inputs = new ItemStack[]{new ItemStack(Items.IRON_INGOT,5),new ItemStack(RankineItems.CHROMIUM_NUGGET.get(),8),new ItemStack(RankineItems.CARBON_NUGGET.get(),1),
                            new ItemStack(RankineItems.NICKEL_NUGGET.get(),6),ItemStack.EMPTY,ItemStack.EMPTY};
                case 11:
                    inputs = new ItemStack[]{new ItemStack(RankineItems.BISMUTH_INGOT.get(),5),new ItemStack(RankineItems.LEAD_INGOT.get(),3),new ItemStack(RankineItems.TIN_INGOT.get(),2),
                            ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
                case 12:
                    inputs = new ItemStack[]{new ItemStack(RankineItems.CERIUM_INGOT.get(),5),new ItemStack(RankineItems.LANTHANUM_NUGGET.get(),22),new ItemStack(RankineItems.NEODYMIUM_NUGGET.get(),18),
                            new ItemStack(Items.IRON_NUGGET,5),ItemStack.EMPTY,ItemStack.EMPTY};
                case 13:
                    inputs = new ItemStack[]{new ItemStack(RankineItems.NICKEL_INGOT.get(),8),new ItemStack(RankineItems.TITANIUM_INGOT.get(),1),new ItemStack(RankineItems.ALUMINUM_INGOT.get(),1),
                            ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
                case 14:
                    inputs = new ItemStack[]{new ItemStack(RankineItems.NICKEL_INGOT.get(),7),new ItemStack(RankineItems.CHROMIUM_INGOT.get(),2),new ItemStack(RankineItems.COBALT_INGOT.get(),1),
                            ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
                case 15:
                    inputs = new ItemStack[]{new ItemStack(RankineItems.COBALT_INGOT.get(),7),new ItemStack(RankineItems.CHROMIUM_INGOT.get(),2),new ItemStack(RankineItems.NICKEL_INGOT.get(),1),
                            new ItemStack(RankineItems.TANTALUM_INGOT.get(),1),ItemStack.EMPTY,ItemStack.EMPTY};
            }
            sim = new Inventory(inputs);
            recipeIn = worldIn.getRecipeManager().getRecipe(RankineRecipeTypes.ALLOYING, sim, worldIn).orElse(null);
            if (recipeIn != null) {
                AlloyTemplateItem.addTemplate(worldIn,stack, recipeIn, new Inventory(inputs), (DyeItem) Items.WHITE_DYE);
            }

        }
    }
}
