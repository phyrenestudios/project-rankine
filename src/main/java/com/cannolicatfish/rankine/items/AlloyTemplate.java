package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.blocks.templatetable.TemplateTableContainer;
import com.cannolicatfish.rankine.init.ModItems;
import com.cannolicatfish.rankine.items.alloys.AlloyData;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipesComplex;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
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
import java.util.Arrays;
import java.util.List;

public class AlloyTemplate extends Item {
    PeriodicTableUtils utils = new PeriodicTableUtils();
    public AlloyTemplate(Properties properties) {
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

            String p3 = new TranslationTextComponent(this.getTranslationKey(stack)).getString().split(" ")[1];
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
            for (String s : comp.split("-"))
            {
                String str = s.replaceAll("[^A-Za-z]+", "");
                String end = "Nugget";
                int num = Integer.parseInt(s.replaceAll("[A-Za-z]+",""));
                if (num > 64 || num % 9 == 0)
                {
                    num = num/9;
                    end = "Ingot";
                }
                tooltip.add(new StringTextComponent(num + "x " + utils.getElementBySymbol(str).toString().substring(0,1).toUpperCase() + utils.getElementBySymbol(str).toString().substring(1).toLowerCase() + " " + end).mergeStyle(TextFormatting.GRAY));
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
            AbstractMap.SimpleEntry<String,Integer> s = AlloyingRecipesComplex.getInstance().returnItemMaterial(i);
            ret.append(s.getValue()).append(utils.getElementByMaterial(s.getKey()));
        }
        return ret.toString();
    }

    public static void addTemplate(ItemStack stack, String data, String name, ItemStack output, String alloy,  ItemStack ... inputs) {
        ListNBT listnbt = new ListNBT();
        stack.getOrCreateTag().putString("StoredTemplate",assembleTemplateData(inputs));
        stack.getOrCreateTag().putString("NameAdd",output.getCount() + "x#" + output.getTranslationKey());
        for (ItemStack i: inputs)
        {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putString(i.getItem().getRegistryName().toString(),i.getDisplayName().getString().split(" ")[0]);
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

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (getTemplate(stack).size() == 0 && !worldIn.isRemote)
        {
            int random = worldIn.getRandom().nextInt(5);
            ItemStack[] inputs;
            ItemStack output;
            switch (random)
            {
                case 0:
                    inputs = new ItemStack[]{new ItemStack(ModItems.COPPER_INGOT,8),new ItemStack(ModItems.TIN_INGOT,2),ItemStack.EMPTY};
                    output = new ItemStack(ModItems.BRONZE_ALLOY, 9);
                    addTemplate(stack, TemplateTableContainer.assembleTemplate(inputs),output.getCount() + "x#" + output.getDisplayName(), output, AlloyingRecipesComplex.getInstance().getComposition(inputs[0],inputs[1],inputs[2]),
                            inputs[0],inputs[1],inputs[2]);
                    break;
                case 1:
                    inputs = new ItemStack[]{new ItemStack(ModItems.TIN_INGOT,9),new ItemStack(ModItems.ANTIMONY_INGOT,1),ItemStack.EMPTY};
                    output = new ItemStack(ModItems.PEWTER_ALLOY, 9);
                    addTemplate(stack, TemplateTableContainer.assembleTemplate(inputs),output.getCount() + "x#" + output.getDisplayName(), output, AlloyingRecipesComplex.getInstance().getComposition(inputs[0],inputs[1],inputs[2]),
                            inputs[0],inputs[1],inputs[2]);
                    break;
                case 2:
                    inputs = new ItemStack[]{new ItemStack(ModItems.COPPER_INGOT,6),new ItemStack(ModItems.ZINC_INGOT,4),ItemStack.EMPTY};
                    output = new ItemStack(ModItems.BRASS_ALLOY, 9);
                    addTemplate(stack, TemplateTableContainer.assembleTemplate(inputs),output.getCount() + "x#" + output.getDisplayName(), output, AlloyingRecipesComplex.getInstance().getComposition(inputs[0],inputs[1],inputs[2]),
                            inputs[0],inputs[1],inputs[2]);
                    break;
                case 3:
                    inputs = new ItemStack[]{new ItemStack(Items.GOLD_INGOT,5),new ItemStack(ModItems.COPPER_NUGGET,13), new ItemStack(ModItems.SILVER_NUGGET,2)};
                    output = new ItemStack(ModItems.ROSE_GOLD_ALLOY, 6);
                    addTemplate(stack, TemplateTableContainer.assembleTemplate(inputs),output.getCount() + "x#" + output.getDisplayName(), output, AlloyingRecipesComplex.getInstance().getComposition(inputs[0],inputs[1],inputs[2]),
                            inputs[0],inputs[1],inputs[2]);
                    break;
                case 4:
                    inputs = new ItemStack[]{new ItemStack(ModItems.COPPER_INGOT,9),new ItemStack(ModItems.ALUMINUM_INGOT,1),ItemStack.EMPTY};
                    output = new ItemStack(ModItems.ALUMINUM_BRONZE_ALLOY, 9);
                    addTemplate(stack, TemplateTableContainer.assembleTemplate(inputs),output.getCount() + "x#" + output.getDisplayName(), output, AlloyingRecipesComplex.getInstance().getComposition(inputs[0],inputs[1],inputs[2]),
                            inputs[0],inputs[1],inputs[2]);
                    break;
                case 5:
                    inputs = new ItemStack[]{new ItemStack(Items.IRON_INGOT,6),new ItemStack(ModItems.NICKEL_INGOT,4),ItemStack.EMPTY};
                    output = new ItemStack(ModItems.INVAR_ALLOY, 9);
                    addTemplate(stack, TemplateTableContainer.assembleTemplate(inputs),output.getCount() + "x#" + output.getDisplayName(), output, AlloyingRecipesComplex.getInstance().getComposition(inputs[0],inputs[1],inputs[2]),
                            inputs[0],inputs[1],inputs[2]);
                    break;
                case 6:
                    inputs = new ItemStack[]{new ItemStack(Items.GOLD_INGOT,9),new ItemStack(ModItems.ZINC_INGOT,1),ItemStack.EMPTY};
                    output = new ItemStack(ModItems.WHITE_GOLD_ALLOY, 9);
                    addTemplate(stack, TemplateTableContainer.assembleTemplate(inputs),output.getCount() + "x#" + output.getDisplayName(), output, AlloyingRecipesComplex.getInstance().getComposition(inputs[0],inputs[1],inputs[2]),
                            inputs[0],inputs[1],inputs[2]);
                    break;
                case 7:
                    inputs = new ItemStack[]{new ItemStack(Items.GOLD_INGOT,5),new ItemStack(ModItems.SILVER_INGOT,5),ItemStack.EMPTY};
                    output = new ItemStack(ModItems.GREEN_GOLD_ALLOY, 9);
                    addTemplate(stack, TemplateTableContainer.assembleTemplate(inputs),output.getCount() + "x#" + output.getDisplayName(), output, AlloyingRecipesComplex.getInstance().getComposition(inputs[0],inputs[1],inputs[2]),
                            inputs[0],inputs[1],inputs[2]);
                    break;
                case 8:
                    inputs = new ItemStack[]{new ItemStack(Items.GOLD_INGOT,8),new ItemStack(ModItems.ALUMINUM_INGOT,2),ItemStack.EMPTY};
                    output = new ItemStack(ModItems.PURPLE_GOLD_ALLOY, 9);
                    addTemplate(stack, TemplateTableContainer.assembleTemplate(inputs),output.getCount() + "x#" + output.getDisplayName(), output, AlloyingRecipesComplex.getInstance().getComposition(inputs[0],inputs[1],inputs[2]),
                            inputs[0],inputs[1],inputs[2]);
                    break;
            }

        }
    }
}
