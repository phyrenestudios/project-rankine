package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineRecipes;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.*;

public class AlloyTemplateItem extends Item {
    public AlloyTemplateItem(Properties properties) {
        super(properties);
    }

    @Override
    public Component getName(ItemStack stack) {
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
                String translate = new TranslatableComponent(output.getDescriptionId(),new TranslatableComponent(nameOverride).getString()).getString();
                String template = new TranslatableComponent(this.getDescriptionId(stack)).getString();
                return new TextComponent( stackSize + "x " + translate + " " + template);
            } else {
                return super.getName(stack);
            }
        } else {
            return super.getName(stack);
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

            ListTag temp = getStoredTemplate(template);
            for (int i = 0; i < temp.size(); i++) {
                CompoundTag nbt = temp.getCompound(i);
                ings.put(getIngredientFromString(nbt.getString("ingredient")),nbt.getShort("ingredientAmount"));
            }
            return ings;
        }
        return Collections.emptyMap();
    }

    private static Ingredient getIngredientFromString(String s) {
        if (s.contains("T#")) {
            TagKey<Item> tag = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(),new ResourceLocation(s.split("T#")[1]));
            return Ingredient.of(tag);
        } else if (s.contains("I#")) {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(s.split("I#")[1]));
            if (item != null) {
                return Ingredient.of(() -> item);
            }
        }
        return Ingredient.EMPTY;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (isTemplateInit(stack)) {
            tooltip.add(new TextComponent("Composition: " + getAlloyComp(stack)).withStyle(ChatFormatting.GRAY));
            tooltip.add(new TextComponent("Requires:").withStyle(ChatFormatting.DARK_GREEN));
            ListTag inputs = getStoredTemplate(stack);
            for (int i = 0; i < inputs.size(); i++) {
                CompoundTag nbt = inputs.getCompound(i);
                String ing = nbt.getString("ingredient");
                int ingAmount = nbt.getShort("ingredientAmount");
                if (ing.contains("#")) {
                    ing = ing.split("#")[1];
                }
                tooltip.add(new TextComponent(ingAmount + "x " + ing).withStyle(ChatFormatting.GRAY));
                if (Screen.hasShiftDown() && worldIn != null) {
                    ResourceLocation id = new ResourceLocation(nbt.getString("element"));
                    int amount = nbt.getShort("elementAmount");
                    Recipe<?> recipe = worldIn.getRecipeManager().byKey(id).orElse(null);
                    if (recipe instanceof ElementRecipe) {
                        ElementRecipe element = (ElementRecipe) recipe;
                        String elementName = element.getName();
                        if (elementName.length() > 1) {
                            elementName = elementName.substring(0,1).toUpperCase(Locale.ROOT) + elementName.substring(1);
                        }
                        String display = elementName + " (" + element.getSymbol() + ")";
                        tooltip.add(new TextComponent("    " +amount + "x " + display).withStyle(ChatFormatting.GRAY));
                    }

                }
            }

            tooltip.add(new TextComponent( ""));
            tooltip.add(new TextComponent( "Made in:").withStyle(ChatFormatting.DARK_GREEN));
            int tier = getAlloyTier(stack);
            if ((tier & 1) != 0) {
                tooltip.add(new TranslatableComponent(RankineBlocks.ALLOY_FURNACE.get().getDescriptionId()).withStyle(ChatFormatting.GRAY));
            }
            if ((tier & 2) != 0) {
                tooltip.add(new TranslatableComponent(RankineBlocks.INDUCTION_FURNACE.get().getDescriptionId()).withStyle(ChatFormatting.GRAY));
            }
        }
    }

    public static void addTemplate(Level worldIn, ItemStack stack, AlloyingRecipe recipe, Container inv, DyeItem dye) {
        CompoundTag listnbt = new CompoundTag();
        ItemStack output = recipe.generateResult(worldIn,inv,3);

        assembleTemplateData(stack,worldIn,inv,0,6);
        listnbt.putShort("stackSize", (short) output.getCount());
        listnbt.putString("output",output.getItem().getRegistryName().toString());
        String nbt = IAlloyItem.getAlloyComposition(output);
        listnbt.putString("alloyComp",!nbt.isEmpty() ? nbt : RankineRecipes.generateAlloyString(inv));
        listnbt.putString("alloyName",IAlloyItem.getNameOverride(output));
        ResourceLocation alloyRecipe = IAlloyItem.getAlloyRecipe(output);
        listnbt.putString("alloyRecipe",alloyRecipe != null ? alloyRecipe.toString() : "");
        listnbt.putShort("alloyTier", (short) recipe.getTier());
        stack.getOrCreateTag().put("StoredInfo", listnbt);

        stack.getOrCreateTag().putInt("color", dye.getDyeColor().getMaterialColor().col);
    }

    public static void assembleTemplateData(ItemStack stack, Level worldIn, Container inv, int startIndex, int endIndex)
    {
        ListTag elements = new ListTag();
        List<ElementRecipe> elementRecipes = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();
        List<Integer> elementAmounts = new ArrayList<>();
        List<Integer> amounts = new ArrayList<>();
        for (int i = startIndex; i < endIndex; i++)
        {
            ItemStack invStackInSlot = inv.getItem(i);
            if (stack.isEmpty())
            {
                continue;
            }

            SimpleContainer temp = new SimpleContainer(invStackInSlot);
            ElementRecipe elem = worldIn.getRecipeManager().getRecipeFor(RankineRecipeTypes.ELEMENT, temp, worldIn).orElse(null);
            if (elem != null) {
                ingredients.add(elem.getIngredientFromCount(elem.getMaterialCount(invStackInSlot.getItem())));
                amounts.add(invStackInSlot.getCount());
                elementRecipes.add(elem);
                elementAmounts.add(elem.getMaterialCount(invStackInSlot.getItem()) * invStackInSlot.getCount());
            }
        }
        for (int i = 0; i < ingredients.size(); i++) {
            CompoundTag compoundnbt = new CompoundTag();
            compoundnbt.putString("ingredient", ingredients.get(i));
            compoundnbt.putShort("ingredientAmount", amounts.get(i).shortValue());
            compoundnbt.putString("element", String.valueOf(elementRecipes.get(i).getId()));
            compoundnbt.putShort("elementAmount", elementAmounts.get(i).shortValue());
            elements.add(compoundnbt);
        }
        stack.getOrCreateTag().put("StoredTemplate",elements);
    }

    public static ItemStack getResult(Level worldIn, ItemStack stack)
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
                    ListTag storedTemp = getStoredTemplate(stack);
                    for (int i = 0; i < storedTemp.size(); i++) {
                        CompoundTag nbt = storedTemp.getCompound(i);
                        ResourceLocation elem = new ResourceLocation(nbt.getString("element"));
                        int amount = nbt.getShort("elementAmount");
                        Recipe<?> recipe = worldIn.getRecipeManager().byKey(elem).orElse(null);
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

    public static CompoundTag getTemplate(ItemStack stack) {
        CompoundTag compoundnbt = stack.getTag();
        return compoundnbt != null ? compoundnbt : new CompoundTag();
    }

    static ListTag getStoredTemplate(ItemStack stack) {
        CompoundTag compoundnbt = stack.getTag();
        return compoundnbt != null ? compoundnbt.getList("StoredTemplate", 10) : new ListTag();
    }

    static CompoundTag getStoredInfo(ItemStack stack) {
        CompoundTag compoundnbt = stack.getTag();
        return compoundnbt != null ? compoundnbt.getCompound("StoredInfo") : new CompoundTag();
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

    public static Map<ElementRecipe,Integer> getElementList(Level worldIn, ItemStack stack) {
        if (!getStoredTemplate(stack).isEmpty()) {
            Map<ElementRecipe,Integer> outMap = new HashMap<>();
            ListTag temp = getStoredTemplate(stack);
            for (int i = 0; i < temp.size(); i++) {
                CompoundTag nbt = temp.getCompound(i);
                ResourceLocation elem = new ResourceLocation(nbt.getString("element"));
                int amount = nbt.getShort("elementAmount");
                if (worldIn != null) {
                    Recipe<?> recipe = worldIn.getRecipeManager().byKey(elem).orElse(null);
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
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (getTemplate(stack).size() == 0 && !worldIn.isClientSide)
        {
            int random = worldIn.getRandom().nextInt(16);
            ItemStack[] inputs;
            AlloyingRecipe recipeIn;
            SimpleContainer sim;
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
            sim = new SimpleContainer(inputs);
            recipeIn = worldIn.getRecipeManager().getRecipeFor(RankineRecipeTypes.ALLOYING, sim, worldIn).orElse(null);
            if (recipeIn != null) {
                AlloyTemplateItem.addTemplate(worldIn,stack, recipeIn, new SimpleContainer(inputs), (DyeItem) Items.WHITE_DYE);
            }

        }
    }
}
