package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineRecipes;
import com.cannolicatfish.rankine.items.alloys.AlloyData;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
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
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
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
            int stackSize = getStackSize(stack);
            Item output = ForgeRegistries.ITEMS.getValue(getOutputAsResourceLocation(stack));
            if (stackSize != 0 && output != null && output != Items.AIR) {
                String nameOverride = getAlloyName(stack);
                if (nameOverride.isEmpty()) {
                    if (getAlloyRecipe(stack).isEmpty()) {
                        nameOverride = generateLangFromRecipe(new ResourceLocation(getAlloyRecipe(stack)));
                    } else if (output instanceof IAlloyItem) {
                        nameOverride = generateLangFromRecipe(((IAlloyItem) output).getDefaultRecipe());
                    }
                }
                String translate = new TranslationTextComponent(output.getTranslationKey(),new TranslationTextComponent(nameOverride).getString()).getString();
                String template = new TranslationTextComponent(this.getTranslationKey(stack)).getString();
                return new StringTextComponent( stackSize + "x " + translate + " " + template);
            } else {
                return super.getDisplayName(stack);
            }
        } else {
            return super.getDisplayName(stack);
        }
    }

    private String generateLangFromRecipe(ResourceLocation recipe) {
        if (recipe == null) {
            return "item.rankine.custom_alloy_default";
        } else {
            String[] s = recipe.getPath().split("/");
            return "item." + recipe.getNamespace() + "." + s[s.length-1];
        }
    }

    public static Map<Ingredient,Short> getInputStacks(ItemStack template) {
        Map<Ingredient,Short> ings = new HashMap<>();
        if (!getStoredTemplate(template).isEmpty()) {

            ListNBT temp = getStoredTemplate(template);
            for (int i = 0; i < temp.size(); i++) {
                CompoundNBT nbt = temp.getCompound(i);
                ings.put(getIngredientFromString(nbt.getString("ingredient")),nbt.getShort("ingredientAmount"));
            }
            return ings;
        }
        return Collections.emptyMap();
    }

    private static Ingredient getIngredientFromString(String s) {
        if (s.contains("T#")) {
            ITag<Item> tag = ItemTags.getCollection().get(new ResourceLocation(s.split("T#")[1]));

            if (tag != null){
                return Ingredient.fromTag(tag);
            }
        } else if (s.contains("I#")) {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(s.split("I#")[1]));
            if (item != null) {
                return Ingredient.fromItems(() -> item);
            }
        }
        return Ingredient.EMPTY;
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
                String ing = nbt.getString("ingredient");
                int ingAmount = nbt.getShort("ingredientAmount");
                if (ing.contains("#")) {
                    ing = ing.split("#")[1];
                }
                tooltip.add(new StringTextComponent(ingAmount + "x " + ing).mergeStyle(TextFormatting.GRAY));
                if (Screen.hasShiftDown() && worldIn != null) {
                    ResourceLocation id = new ResourceLocation(nbt.getString("element"));
                    int amount = nbt.getShort("elementAmount");
                    IRecipe<?> recipe = worldIn.getRecipeManager().getRecipe(id).orElse(null);
                    if (recipe instanceof ElementRecipe) {
                        ElementRecipe element = (ElementRecipe) recipe;
                        String elementName = element.getName();
                        if (elementName.length() > 1) {
                            elementName = elementName.substring(0,1).toUpperCase(Locale.ROOT) + elementName.substring(1);
                        }
                        String display = elementName + " (" + element.getSymbol() + ")";
                        tooltip.add(new StringTextComponent("    " +amount + "x " + display).mergeStyle(TextFormatting.GRAY));
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
                tooltip.add(new TranslationTextComponent(RankineBlocks.INDUCTION_FURNACE.get().getTranslationKey()).mergeStyle(TextFormatting.GRAY));
            }
        }
    }

    public static void addTemplate(World worldIn, ItemStack stack, AlloyingRecipe recipe, IInventory inv, DyeItem dye) {
        CompoundNBT listnbt = new CompoundNBT();
        ItemStack output = recipe.generateResult(worldIn,inv,3);

        assembleTemplateData(stack,worldIn,inv,0,6);
        listnbt.putShort("stackSize", (short) output.getCount());
        listnbt.putString("output",output.getItem().getRegistryName().toString());
        String nbt = IAlloyItem.getAlloyComposition(output);
        listnbt.putString("alloyComp",!nbt.isEmpty() ? nbt : RankineRecipes.generateAlloyString(inv,worldIn));
        listnbt.putString("alloyName",IAlloyItem.getNameOverride(output));
        ResourceLocation alloyRecipe = IAlloyItem.getAlloyRecipe(output);
        listnbt.putString("alloyRecipe",alloyRecipe != null ? alloyRecipe.toString() : "");
        listnbt.putShort("alloyTier", (short) recipe.getTier());
        stack.getOrCreateTag().put("StoredInfo", listnbt);

        stack.getOrCreateTag().putInt("color", dye.getDyeColor().getColorValue());
    }

    public static void assembleTemplateData(ItemStack stack, World worldIn, IInventory inv, int startIndex, int endIndex)
    {
        ListNBT elements = new ListNBT();
        List<ElementRecipe> elementRecipes = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();
        List<Integer> elementAmounts = new ArrayList<>();
        List<Integer> amounts = new ArrayList<>();
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
                ingredients.add(elem.getIngredientFromCount(elem.getMaterialCount(invStackInSlot.getItem())));
                amounts.add(invStackInSlot.getCount());
                elementRecipes.add(elem);
                elementAmounts.add(elem.getMaterialCount(invStackInSlot.getItem()) * invStackInSlot.getCount());
            }
        }
        for (int i = 0; i < ingredients.size(); i++) {
            CompoundNBT compoundnbt = new CompoundNBT();
            compoundnbt.putString("ingredient", ingredients.get(i));
            compoundnbt.putShort("ingredientAmount", amounts.get(i).shortValue());
            compoundnbt.putString("element", String.valueOf(elementRecipes.get(i).getId()));
            compoundnbt.putShort("elementAmount", elementAmounts.get(i).shortValue());
            elements.add(compoundnbt);
        }
        stack.getOrCreateTag().put("StoredTemplate",elements);
    }

    public static ItemStack getResult(World worldIn, ItemStack stack)
    {
        if (isTemplateInit(stack)) {
            String rs = getOutput(stack);
            int amt = getStackSize(stack);
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(rs));
            if (item != null && item != Items.AIR) {
                ItemStack out = new ItemStack(item,amt);
                if (item instanceof IAlloyItem)
                {
                    Map<ElementRecipe,Integer> elementMap = new HashMap<>();
                    ListNBT storedTemp = getStoredTemplate(stack);
                    for (int i = 0; i < storedTemp.size(); i++) {
                        CompoundNBT nbt = storedTemp.getCompound(i);
                        ResourceLocation elem = new ResourceLocation(nbt.getString("element"));
                        int amount = nbt.getShort("elementAmount");
                        IRecipe<?> recipe = worldIn.getRecipeManager().getRecipe(elem).orElse(null);
                        if (recipe instanceof ElementRecipe) {
                            elementMap.put((ElementRecipe) recipe,amount);
                        }
                    }
                    String recipe = getAlloyRecipe(stack);
                    String alloyName = !getAlloyName(stack).isEmpty() ? getAlloyName(stack) : null;
                    ResourceLocation alloyingRecipe = !recipe.isEmpty() ? new ResourceLocation(recipe) : null;
                    ((IAlloyItem) item).createAlloyNBT(out,worldIn,getAlloyComp(stack),alloyingRecipe,alloyName);
                }
                return out;
            }

        }
        return ItemStack.EMPTY;
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

    static int getStackSize(ItemStack stack)
    {
        if (!getStoredInfo(stack).isEmpty()) {
            return getStoredInfo(stack).getInt("stackSize");
        } else {
            return 0;
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

    public static String getOutput(ItemStack stack)
    {
        if (!getStoredInfo(stack).isEmpty()) {
            return getStoredInfo(stack).getString("output");
        } else {
            return "";
        }
    }

    public static String getAlloyComp(ItemStack stack)
    {
        if (!getStoredInfo(stack).isEmpty()) {
            return getStoredInfo(stack).getString("alloyComp");
        } else {
            return "";
        }
    }

    public static String getAlloyName(ItemStack stack)
    {
        if (!getStoredInfo(stack).isEmpty()) {
            return getStoredInfo(stack).getString("alloyName");
        } else {
            return "";
        }
    }

    public static String getAlloyRecipe(ItemStack stack)
    {
        if (!getStoredInfo(stack).isEmpty()) {
            return getStoredInfo(stack).getString("alloyRecipe");
        } else {
            return "";
        }
    }

    public static short getAlloyTier(ItemStack stack)
    {
        if (!getStoredInfo(stack).isEmpty()) {
            return getStoredInfo(stack).getShort("alloyTier");
        } else {
            return 0;
        }
    }

    public static Map<ElementRecipe,Integer> getElementList(World worldIn, ItemStack stack) {
        if (!getStoredTemplate(stack).isEmpty()) {
            Map<ElementRecipe,Integer> outMap = new HashMap<>();
            ListNBT temp = getStoredTemplate(stack);
            for (int i = 0; i < temp.size(); i++) {
                CompoundNBT nbt = temp.getCompound(i);
                ResourceLocation elem = new ResourceLocation(nbt.getString("element"));
                int amount = nbt.getShort("elementAmount");
                if (worldIn != null) {
                    IRecipe<?> recipe = worldIn.getRecipeManager().getRecipe(elem).orElse(null);
                    if (recipe instanceof ElementRecipe) {
                        outMap.put((ElementRecipe) recipe, amount);
                    }
                }
            }
            return outMap;
        } else {
            return Collections.emptyMap();
        }
    }

    private static boolean isTemplateInit(ItemStack stack) {
        return stack.getTag() != null && !getStoredTemplate(stack).isEmpty() && !getStoredInfo(stack).isEmpty();
    }

    public static int getColor(ItemStack stack) {
        return getTemplate(stack).getInt("Color") == 0 ? 16777215 : getTemplate(stack).getInt("Color");
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
                    break;
                case 10:
                    inputs = new ItemStack[]{new ItemStack(Items.IRON_INGOT,5),new ItemStack(RankineItems.CHROMIUM_NUGGET.get(),8),new ItemStack(RankineItems.CARBON_NUGGET.get(),1),
                            new ItemStack(RankineItems.NICKEL_NUGGET.get(),6),ItemStack.EMPTY,ItemStack.EMPTY};
                    break;
                case 11:
                    inputs = new ItemStack[]{new ItemStack(RankineItems.BISMUTH_INGOT.get(),5),new ItemStack(RankineItems.LEAD_INGOT.get(),3),new ItemStack(RankineItems.TIN_INGOT.get(),2),
                            ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
                    break;
                case 12:
                    inputs = new ItemStack[]{new ItemStack(RankineItems.CERIUM_INGOT.get(),5),new ItemStack(RankineItems.LANTHANUM_NUGGET.get(),22),new ItemStack(RankineItems.NEODYMIUM_NUGGET.get(),18),
                            new ItemStack(Items.IRON_NUGGET,5),ItemStack.EMPTY,ItemStack.EMPTY};
                    break;
                case 13:
                    inputs = new ItemStack[]{new ItemStack(RankineItems.NICKEL_INGOT.get(),8),new ItemStack(RankineItems.TITANIUM_INGOT.get(),1),new ItemStack(RankineItems.ALUMINUM_INGOT.get(),1),
                            ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
                    break;
                case 14:
                    inputs = new ItemStack[]{new ItemStack(RankineItems.NICKEL_INGOT.get(),7),new ItemStack(RankineItems.CHROMIUM_INGOT.get(),2),new ItemStack(RankineItems.COBALT_INGOT.get(),1),
                            ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
                    break;
                case 15:
                    inputs = new ItemStack[]{new ItemStack(RankineItems.COBALT_INGOT.get(),7),new ItemStack(RankineItems.CHROMIUM_INGOT.get(),2),new ItemStack(RankineItems.NICKEL_INGOT.get(),1),
                            new ItemStack(RankineItems.TANTALUM_INGOT.get(),1),ItemStack.EMPTY,ItemStack.EMPTY};
                    break;
            }
            sim = new Inventory(inputs);
            recipeIn = worldIn.getRecipeManager().getRecipe(RankineRecipeTypes.ALLOYING, sim, worldIn).orElse(null);
            if (recipeIn != null) {
                AlloyTemplateItem.addTemplate(worldIn,stack, recipeIn, new Inventory(inputs), (DyeItem) Items.WHITE_DYE);
            }

        }
    }
}
