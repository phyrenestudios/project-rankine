package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineRecipes;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class AlloyTemplateItem extends Item {
    public AlloyTemplateItem(Properties properties) {
        super(properties);
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        if (isTemplateInit(stack)) {
            String nameAdd = getNameAdd(stack);
            Item output = ForgeRegistries.ITEMS.getValue(getOutputAsResourceLocation(stack));
            if (!nameAdd.isEmpty() && output != null && output != Items.AIR) {
                String translate = new TranslationTextComponent(output.getTranslationKey()).getString();
                String template = new TranslationTextComponent(this.getTranslationKey(stack)).getString();
                return new StringTextComponent( nameAdd + " " + translate + " " + template);
            } else {
                return super.getDisplayName(stack);
            }
        } else {
            return super.getDisplayName(stack);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (isTemplateInit(stack)) {
            tooltip.add(new StringTextComponent("Composition: " + getAlloyComp(stack)).mergeStyle(TextFormatting.GRAY));
            tooltip.add(new StringTextComponent("Requires:").mergeStyle(TextFormatting.DARK_GREEN));
            ListNBT inputs = getStoredTemplate(stack);
            for (int i = 0; i < inputs.size(); i++) {
                CompoundNBT nbt = inputs.getCompound(i);
                ResourceLocation id = new ResourceLocation(nbt.getString("id"));
                int amount = nbt.getShort("amount");

                if (worldIn != null) {
                    IRecipe<?> recipe = worldIn.getRecipeManager().getRecipe(id).orElse(null);
                    if (recipe instanceof ElementRecipe) {
                        ElementRecipe element = (ElementRecipe) recipe;
                        String elementName = element.getName();
                        if (elementName.length() > 1) {
                            elementName = elementName.substring(0,1).toUpperCase(Locale.ROOT) + elementName.substring(1);
                        }
                        String display = elementName + " (" + element.getSymbol() + ")";
                        tooltip.add(new StringTextComponent(amount + "x " + display).mergeStyle(TextFormatting.GRAY));
                        if (Screen.hasShiftDown()) {
                            List<Ingredient> ingredients = element.getIngredients();
                            int size = ingredients.size();
                            Ingredient selected = ingredients.get((Math.round(worldIn.getDayTime() / 30f) % size));
                            if (!selected.hasNoMatchingItems() && selected.getMatchingStacks().length > 0) {
                                DecimalFormat df = Util.make(new DecimalFormat("##.##"), (p_234699_0_) -> {
                                    p_234699_0_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
                                });
                                Item item = selected.getMatchingStacks()[0].getItem();
                                float am = amount * 1f/element.getMaterialCount(item);
                                String itemString = new TranslationTextComponent(item.getTranslationKey()).getString();
                                String res;
                                TextFormatting color = TextFormatting.LIGHT_PURPLE;
                                if (amount % element.getMaterialCount(item) != 0) {
                                    res = "     " + df.format(am) + "x " + itemString;
                                    color = TextFormatting.RED;
                                } else {
                                    res = "     " + Math.round(am) + "x " + itemString;
                                }


                                tooltip.add(new StringTextComponent(res).mergeStyle(color));
                            } else {
                                tooltip.add(new StringTextComponent("Index " + ingredients.indexOf(selected) + " of element recipe " + element.getId() + " has no matching stacks.").mergeStyle(TextFormatting.RED));
                            }


                        }
                    }
                }
            }

            tooltip.add(new StringTextComponent( ""));
            tooltip.add(new StringTextComponent( "Made in:").mergeStyle(TextFormatting.DARK_GREEN));
            int tier = getAlloyTier(stack);
            if ((tier & 1) != 0) {
                tooltip.add(new TranslationTextComponent(RankineBlocks.ALLOY_FURNACE.get().getTranslationKey()).mergeStyle(TextFormatting.GRAY));
            }
            if ((tier & 2) != 0) {
                tooltip.add(new StringTextComponent( RankineBlocks.INDUCTION_FURNACE.get().getTranslationKey()).mergeStyle(TextFormatting.GRAY));
            }
        }
    }

    public static void addTemplate(World worldIn, ItemStack stack, AlloyingRecipe recipe, IInventory inv, DyeItem dye) {
        CompoundNBT listnbt = new CompoundNBT();
        ItemStack output = recipe.generateResult(worldIn,inv,3);

        assembleTemplateData(stack,worldIn,inv,0,6);
        listnbt.putString("nameAdd",output.getCount() + "x");
        listnbt.putString("output",output.getItem().getRegistryName().toString());
        String nbt = IAlloyItem.getAlloyComposition(output);
        listnbt.putString("alloyComp",!nbt.isEmpty() ? nbt : RankineRecipes.generateAlloyString(inv));
        listnbt.putShort("alloyTier", (short) recipe.getTier());
        stack.getOrCreateTag().put("StoredInfo", listnbt);

        stack.getOrCreateTag().putInt("color", dye.getDyeColor().getColorValue());
    }

    public static void assembleTemplateData(ItemStack stack, World worldIn, IInventory inv, int startIndex, int endIndex)
    {
        ListNBT elements = new ListNBT();
        List<ElementRecipe> elementRecipes = new ArrayList<>();
        List<Integer> amount = new ArrayList<>();
        for (int i = startIndex; i < endIndex; i++)
        {
            ItemStack invStackInSlot = inv.getStackInSlot(i);
            if (stack.isEmpty())
            {
                continue;
            }

            Inventory temp = new Inventory(invStackInSlot);
            ElementRecipe elem = worldIn.getRecipeManager().getRecipe(RankineRecipeTypes.ELEMENT, temp, worldIn).orElse(null);
            if (elem != null) {
                if (elementRecipes.contains(elem)) {
                    int index = elementRecipes.indexOf(elem);
                    amount.set(index,amount.get(index) + elem.getMaterialCount(invStackInSlot.getItem()) * invStackInSlot.getCount());
                } else {
                    elementRecipes.add(elem);
                    amount.add(elem.getMaterialCount(invStackInSlot.getItem()) * invStackInSlot.getCount());
                }
            }
        }
        Map<ElementRecipe,Integer> elementMap = new HashMap<>();
        for (int i = 0; i < elementRecipes.size(); i++) {
            if (i < amount.size()) {
                elementMap.put(elementRecipes.get(i),amount.get(i));
            }
        }
        for (Map.Entry<ElementRecipe, Integer> entry : elementMap.entrySet()) {
            int perc = entry.getValue();
            CompoundNBT compoundnbt = new CompoundNBT();
            compoundnbt.putString("id", String.valueOf(entry.getKey().getId()));
            compoundnbt.putShort("amount", (short)perc);
            elements.add(compoundnbt);
        }
        stack.getOrCreateTag().put("StoredTemplate",elements);
    }

    public static CompoundNBT getTemplate(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null ? compoundnbt : new CompoundNBT();
    }

    static ListNBT getStoredTemplate(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null ? compoundnbt.getList("StoredTemplate", 10) : new ListNBT();
    }

    static CompoundNBT getStoredInfo(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null ? compoundnbt.getCompound("StoredInfo") : new CompoundNBT();
    }

    static String getNameAdd(ItemStack stack)
    {
        if (!getStoredInfo(stack).isEmpty()) {
            return getStoredInfo(stack).getString("nameAdd");
        } else {
            return "";
        }
    }

    static ResourceLocation getOutputAsResourceLocation(ItemStack stack)
    {
        if (!getStoredInfo(stack).isEmpty()) {
            return new ResourceLocation(getStoredInfo(stack).getString("output"));
        } else {
            return null;
        }
    }

    static String getOutput(ItemStack stack)
    {
        if (!getStoredInfo(stack).isEmpty()) {
            return getStoredInfo(stack).getString("output");
        } else {
            return "";
        }
    }

    static String getAlloyComp(ItemStack stack)
    {
        if (!getStoredInfo(stack).isEmpty()) {
            return getStoredInfo(stack).getString("alloyComp");
        } else {
            return "";
        }
    }

    static short getAlloyTier(ItemStack stack)
    {
        if (!getStoredInfo(stack).isEmpty()) {
            return getStoredInfo(stack).getShort("alloyTier");
        } else {
            return 0;
        }
    }

    private boolean isTemplateInit(ItemStack stack) {
        return stack.getTag() != null && !getStoredTemplate(stack).isEmpty() && !getStoredInfo(stack).isEmpty();
    }

    public static int getColor(ItemStack stack) {
        return getTemplate(stack).getInt("Color") == 0 ? 16777215 : getTemplate(stack).getInt("Color");
    }
}
