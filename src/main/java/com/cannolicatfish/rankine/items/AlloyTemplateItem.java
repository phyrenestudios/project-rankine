package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineDataComponents;
import com.cannolicatfish.rankine.init.RankineDataComponents.TemplateEntry;
import com.cannolicatfish.rankine.init.RankineDataComponents.TemplateInfo;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.init.RankineRecipes;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.AlloyingRecipe;
import com.cannolicatfish.rankine.recipe.ElementRecipe;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.*;

public class AlloyTemplateItem extends Item {
    public AlloyTemplateItem(Properties properties) {
        super(properties);
    }

    /**
     * Minimal {@link RecipeInput} over a fixed stack array — replaces the 1.18.2 SimpleContainer
     * that was handed straight to the recipe manager. Slot indices match the old container order.
     */
    private record StackRecipeInput(List<ItemStack> stacks) implements RecipeInput {
        @Override
        public ItemStack getItem(int index) {
            return index >= 0 && index < this.stacks.size() ? this.stacks.get(index) : ItemStack.EMPTY;
        }

        @Override
        public int size() {
            return this.stacks.size();
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        if (isTemplateInit(stack)) {
            int stackSize = getStackSize(stack);
            ResourceLocation outputId = getOutputAsResourceLocation(stack);
            Item output = outputId != null ? BuiltInRegistries.ITEM.get(outputId) : null;
            if (stackSize != 0 && output != null && output != Items.AIR) {
                String nameOverride = getAlloyName(stack);
                if (nameOverride.isEmpty()) {
                    if (getAlloyRecipe(stack).isEmpty()) {
                        // NOTE: 1.18.2 called new ResourceLocation("") here (always threw); tryParse
                        // keeps the same branch but degrades to the default lang key instead.
                        nameOverride = generateLangFromRecipe(ResourceLocation.tryParse(getAlloyRecipe(stack)));
                    } else if (output instanceof IAlloyItem) {
                        nameOverride = generateLangFromRecipe(((IAlloyItem) output).getDefaultRecipe());
                    }
                }
                String translate = Component.translatable(output.getDescriptionId(),Component.translatable(nameOverride).getString()).getString();
                String template = Component.translatable(this.getDescriptionId(stack)).getString();
                return Component.literal( stackSize + "x " + translate + " " + template);
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

            for (TemplateEntry entry : getStoredTemplate(template)) {
                ings.put(getIngredientFromString(entry.ingredient()), (short) entry.ingredientAmount());
            }
            return ings;
        }
        return Collections.emptyMap();
    }

    private static Ingredient getIngredientFromString(String s) {
        if (s.contains("T#")) {
            ResourceLocation rl = ResourceLocation.tryParse(s.split("T#")[1]);
            if (rl != null) {
                TagKey<Item> tag = TagKey.create(Registries.ITEM, rl);
                return Ingredient.of(tag);
            }
        } else if (s.contains("I#")) {
            ResourceLocation rl = ResourceLocation.tryParse(s.split("I#")[1]);
            Item item = rl != null ? BuiltInRegistries.ITEM.get(rl) : null;
            if (item != null) {
                return Ingredient.of(item);
            }
        }
        return Ingredient.EMPTY;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
        Level worldIn = context.level();
        if (isTemplateInit(stack)) {
            tooltip.add(Component.literal("Composition: " + getAlloyComp(stack)).withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Requires:").withStyle(ChatFormatting.DARK_GREEN));
            for (TemplateEntry entry : getStoredTemplate(stack)) {
                String ing = entry.ingredient();
                int ingAmount = entry.ingredientAmount();
                if (ing.contains("#")) {
                    ing = ing.split("#")[1];
                }
                tooltip.add(Component.literal(ingAmount + "x " + ing).withStyle(ChatFormatting.GRAY));
                if (Screen.hasShiftDown() && worldIn != null) {
                    ResourceLocation id = ResourceLocation.tryParse(entry.element());
                    int amount = entry.elementAmount();
                    RecipeHolder<?> recipe = id != null ? worldIn.getRecipeManager().byKey(id).orElse(null) : null;
                    if (recipe != null && recipe.value() instanceof ElementRecipe element) {
                        String elementName = element.getName();
                        if (elementName.length() > 1) {
                            elementName = elementName.substring(0,1).toUpperCase(Locale.ROOT) + elementName.substring(1);
                        }
                        String display = elementName + " (" + element.getSymbol() + ")";
                        tooltip.add(Component.literal("    " +amount + "x " + display).withStyle(ChatFormatting.GRAY));
                    }

                }
            }

            tooltip.add(Component.literal( ""));
            tooltip.add(Component.literal( "Made in:").withStyle(ChatFormatting.DARK_GREEN));
            int tier = getAlloyTier(stack);
            if ((tier & 1) != 0) {
                tooltip.add(Component.translatable(RankineBlocks.ALLOY_FURNACE.get().getDescriptionId()).withStyle(ChatFormatting.GRAY));
            }
            if ((tier & 2) != 0) {
                tooltip.add(Component.translatable(RankineBlocks.INDUCTION_FURNACE.get().getDescriptionId()).withStyle(ChatFormatting.GRAY));
            }
        }
    }

    public static void addTemplate(Level worldIn, ItemStack stack, AlloyingRecipe recipe, Container inv, DyeItem dye) {
        ItemStack output = recipe.generateResult(worldIn,inv,3);

        assembleTemplateData(stack,worldIn,inv,0,6);
        String nbt = IAlloyItem.getAlloyComposition(output);
        ResourceLocation alloyRecipe = IAlloyItem.getAlloyRecipe(output);
        ResourceLocation outputId = BuiltInRegistries.ITEM.getKey(output.getItem());
        // was NBT compound "StoredInfo"
        stack.set(RankineDataComponents.TEMPLATE_INFO.get(), new TemplateInfo(
                output.getCount(),
                outputId != null ? outputId.toString() : "",
                !nbt.isEmpty() ? nbt : RankineRecipes.generateAlloyString(inv,worldIn),
                IAlloyItem.getNameOverride(output),
                alloyRecipe != null ? alloyRecipe.toString() : "",
                recipe.getTier()));

        stack.set(RankineDataComponents.COLOR.get(), dye.getDyeColor().getMapColor().col);
    }

    public static void assembleTemplateData(ItemStack stack, Level worldIn, Container inv, int startIndex, int endIndex)
    {
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

            StackRecipeInput temp = new StackRecipeInput(List.of(invStackInSlot));
            ElementRecipe elem = worldIn.getRecipeManager().getRecipeFor(RankineRecipeTypes.ELEMENT.get(), temp, worldIn).map(RecipeHolder::value).orElse(null);
            if (elem != null) {
                ingredients.add(elem.getIngredientFromCount(elem.getMaterialCount(invStackInSlot.getItem())));
                amounts.add(invStackInSlot.getCount());
                elementRecipes.add(elem);
                elementAmounts.add(elem.getMaterialCount(invStackInSlot.getItem()) * invStackInSlot.getCount());
            }
        }
        List<TemplateEntry> elements = new ArrayList<>();
        for (int i = 0; i < ingredients.size(); i++) {
            elements.add(new TemplateEntry(ingredients.get(i), amounts.get(i).shortValue(),
                    String.valueOf(elementRecipes.get(i).getId()), elementAmounts.get(i).shortValue()));
        }
        // was NBT list "StoredTemplate"
        stack.set(RankineDataComponents.TEMPLATE_ENTRIES.get(), elements);
    }

    public static ItemStack getResult(Level worldIn, ItemStack stack)
    {
        if (isTemplateInit(stack)) {
            String rs = getOutput(stack);
            int amt = getStackSize(stack);
            ResourceLocation outputId = ResourceLocation.tryParse(rs);
            Item item = outputId != null ? BuiltInRegistries.ITEM.get(outputId) : null;
            if (item != null && item != Items.AIR) {
                ItemStack out = new ItemStack(item,amt);
                if (item instanceof IAlloyItem)
                {
                    Map<ElementRecipe,Integer> elementMap = new HashMap<>();
                    for (TemplateEntry entry : getStoredTemplate(stack)) {
                        ResourceLocation elem = ResourceLocation.tryParse(entry.element());
                        int amount = entry.elementAmount();
                        RecipeHolder<?> recipe = elem != null ? worldIn.getRecipeManager().byKey(elem).orElse(null) : null;
                        if (recipe != null && recipe.value() instanceof ElementRecipe elementRecipe) {
                            elementMap.put(elementRecipe,amount);
                        }
                    }
                    String recipe = getAlloyRecipe(stack);
                    String alloyName = !getAlloyName(stack).isEmpty() ? getAlloyName(stack) : null;
                    ResourceLocation alloyingRecipe = !recipe.isEmpty() ? ResourceLocation.parse(recipe) : null;
                    ((IAlloyItem) item).createAlloyNBT(out,worldIn,getAlloyComp(stack),alloyingRecipe,alloyName);
                }
                return out;
            }

        }
        return ItemStack.EMPTY;
    }

    /** was NBT list "StoredTemplate" */
    static List<TemplateEntry> getStoredTemplate(ItemStack stack) {
        return stack.getOrDefault(RankineDataComponents.TEMPLATE_ENTRIES.get(), Collections.emptyList());
    }

    /** was NBT compound "StoredInfo" */
    @Nullable
    static TemplateInfo getStoredInfo(ItemStack stack) {
        return stack.get(RankineDataComponents.TEMPLATE_INFO.get());
    }

    static int getStackSize(ItemStack stack)
    {
        TemplateInfo info = getStoredInfo(stack);
        return info != null ? info.stackSize() : 0;
    }

    @Nullable
    static ResourceLocation getOutputAsResourceLocation(ItemStack stack)
    {
        TemplateInfo info = getStoredInfo(stack);
        return info != null ? ResourceLocation.tryParse(info.output()) : null;
    }

    public static String getOutput(ItemStack stack)
    {
        TemplateInfo info = getStoredInfo(stack);
        return info != null ? info.output() : "";
    }

    public static String getAlloyComp(ItemStack stack)
    {
        TemplateInfo info = getStoredInfo(stack);
        return info != null ? info.alloyComp() : "";
    }

    public static String getAlloyName(ItemStack stack)
    {
        TemplateInfo info = getStoredInfo(stack);
        return info != null ? info.alloyName() : "";
    }

    public static String getAlloyRecipe(ItemStack stack)
    {
        TemplateInfo info = getStoredInfo(stack);
        return info != null ? info.alloyRecipe() : "";
    }

    public static short getAlloyTier(ItemStack stack)
    {
        TemplateInfo info = getStoredInfo(stack);
        return info != null ? (short) info.alloyTier() : 0;
    }

    public static Map<ElementRecipe,Integer> getElementList(Level worldIn, ItemStack stack) {
        if (!getStoredTemplate(stack).isEmpty()) {
            Map<ElementRecipe,Integer> outMap = new HashMap<>();
            for (TemplateEntry entry : getStoredTemplate(stack)) {
                ResourceLocation elem = ResourceLocation.tryParse(entry.element());
                int amount = entry.elementAmount();
                if (worldIn != null && elem != null) {
                    RecipeHolder<?> recipe = worldIn.getRecipeManager().byKey(elem).orElse(null);
                    if (recipe != null && recipe.value() instanceof ElementRecipe elementRecipe) {
                        outMap.put(elementRecipe, amount);
                    }
                }
            }
            return outMap;
        } else {
            return Collections.emptyMap();
        }
    }

    private static boolean isTemplateInit(ItemStack stack) {
        return !getStoredTemplate(stack).isEmpty() && getStoredInfo(stack) != null;
    }

    /**
     * NOTE: 1.18.2 wrote the dye color under "color" but read it back as "Color", so this always
     * returned 16777215. The component read below returns the value that was actually stored.
     */
    public static int getColor(ItemStack stack) {
        int color = stack.getOrDefault(RankineDataComponents.COLOR.get(), 0);
        return color == 0 ? 16777215 : color;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        // was: getTemplate(stack).size() == 0 (i.e. the stack carried no NBT at all)
        if (!stack.has(RankineDataComponents.TEMPLATE_INFO.get()) && !stack.has(RankineDataComponents.TEMPLATE_ENTRIES.get()) && !worldIn.isClientSide)
        {
            int random = worldIn.getRandom().nextInt(16);
            ItemStack[] inputs;
            AlloyingRecipe recipeIn;
            switch (random)
            {
                case 0:
                default:
                    inputs = new ItemStack[]{new ItemStack(Items.COPPER_INGOT,8),new ItemStack(RankineItems.TIN_INGOT.get(),2),ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
                    break;
                case 1:
                    inputs = new ItemStack[]{new ItemStack(RankineItems.TIN_INGOT.get(),9),new ItemStack(RankineItems.ANTIMONY.get(),1),ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
                    break;
                case 2:
                    inputs = new ItemStack[]{new ItemStack(Items.COPPER_INGOT,6),new ItemStack(RankineItems.ZINC_INGOT.get(),4),ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
                    break;
                case 3:
                    inputs = new ItemStack[]{new ItemStack(Items.GOLD_INGOT,5),new ItemStack(RankineItems.COPPER_NUGGET.get(),13), new ItemStack(RankineItems.SILVER_NUGGET.get(),2),ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
                    break;
                case 4:
                    inputs = new ItemStack[]{new ItemStack(Items.COPPER_INGOT,9),new ItemStack(RankineItems.ALUMINUM_INGOT.get(),1),ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
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
            StackRecipeInput sim = new StackRecipeInput(List.of(inputs));
            recipeIn = worldIn.getRecipeManager().getRecipeFor(RankineRecipeTypes.ALLOYING.get(), sim, worldIn).map(RecipeHolder::value).orElse(null);
            if (recipeIn != null) {
                AlloyTemplateItem.addTemplate(worldIn,stack, recipeIn, new SimpleContainer(inputs), (DyeItem) Items.WHITE_DYE);
            }

        }
    }
}
