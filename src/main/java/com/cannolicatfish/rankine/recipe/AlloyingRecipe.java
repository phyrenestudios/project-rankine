package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AlloyingRecipe implements IRecipe<IInventory> {

    private final int total;
    private final int required;
    private final int tier;
    private final NonNullList<Ingredient> recipeItems;
    private final NonNullList<PeriodicTableUtils.Element> elements;
    private final ItemStack recipeOutput;
    private final ResourceLocation id;
    private final NonNullList<Float> mins;
    private final NonNullList<Float> maxes;

    public static final AlloyingRecipe.Serializer SERIALIZER = new AlloyingRecipe.Serializer();

    public AlloyingRecipe(ResourceLocation idIn, int totalIn, int requiredIn, int tierIn, NonNullList<Ingredient> recipeItemsIn, NonNullList<PeriodicTableUtils.Element> elementsIn,
                          ItemStack outputIn, NonNullList<Float> minsIn, NonNullList<Float> maxesIn) {
        this.id = idIn;
        this.total = totalIn;
        this.required = requiredIn;
        this.tier = tierIn; // Binary: 1 = Alloy Furnace, 2 = Induction Furnace, 3 = Alloy Furnace && Induction Furnace
        this.recipeItems = recipeItemsIn;
        this.elements = elementsIn;
        this.recipeOutput = outputIn;
        this.mins = minsIn;
        this.maxes = maxesIn;
    }

    public String getGroup() {
        return "";
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }

    public int getTier() {
        return this.tier;
    }

    public List<Ingredient> getIngredientsList(boolean required) {
        List<Ingredient> ret = new ArrayList<>();
        for (int i = 0; i < this.recipeItems.size(); i++) {
            if ((this.mins.get(i) > 0 && required) || (this.mins.get(i) == 0 && !required)) {
                ret.add(this.recipeItems.get(i));
            }
        }
        return ret;
    }

    public List<Integer> getIndexList(boolean required) {
        List<Integer> ret = new ArrayList<>();
        for (int i = 0; i < this.recipeItems.size(); i++) {
            if ((this.mins.get(i) > 0 && required) || (this.mins.get(i) == 0 && !required)) {
                ret.add(i);
            }
        }
        return ret;
    }

    public ItemStack generateRandomResult() {
        return ItemStack.EMPTY;
    }

    public NonNullList<Float> getMins() {
        return mins;
    }

    public NonNullList<Float> getMaxes() {
        return maxes;
    }



    @Override
    public boolean matches(IInventory inv, World worldIn) {
        int material = 0;
        List<PeriodicTableUtils.Element> currentElements = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                boolean flag = false;
                for (Ingredient s : getIngredients()) {
                    if (s.test(stack)) {
                        PeriodicTableUtils.Element element = getElements().get(getIngredients().indexOf(s));
                        if (!currentElements.contains(element)) {
                            currentElements.add(element);
                        }
                        flag = true;
                    }
                }
                if (flag) {
                    Item item = stack.getItem();
                    ResourceLocation reg = item.getRegistryName();
                    String registry = "";
                    if (reg != null) {
                        registry = reg.getPath();
                    }

                    if (stack.getItem().getTags().contains(new ResourceLocation("forge:storage_blocks")) || stack.getItem() instanceof BlockItem || registry.contains("block")) {
                        material += 81 * stack.getCount();
                    } else if (stack.getItem().getTags().contains(new ResourceLocation("forge:ingots")) || registry.contains("ingot")) {
                        material += 9 * stack.getCount();
                    } else if (stack.getItem().getTags().contains(new ResourceLocation("forge:nuggets")) || registry.contains("nugget")) {
                        material += stack.getCount();
                    } else if (stack.getItem() == Items.NETHERITE_SCRAP || registry.contains("scrap")){
                        material += 2 * stack.getCount();
                    } else {
                        material += 9 * stack.getCount();
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput.copy();
    }

    public int getTotal() {
        return this.total;
    }

    public NonNullList<PeriodicTableUtils.Element> getElements() {
        return this.elements;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static ItemStack deserializeItem(JsonObject object) {
        String s = JSONUtils.getString(object, "item");
        Item item = Registry.ITEM.getOptional(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + s + "'");
        });

        if (object.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            int i = JSONUtils.getInt(object, "count", 1);
            return AlloyIngredientHelper.getItemStack(object, true, false);
        }
    }

    @Override
    public IRecipeType<?> getType() {
        return RankineRecipeTypes.ALLOYING;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>>  implements IRecipeSerializer<AlloyingRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation("rankine", "alloying");
        public AlloyingRecipe read(ResourceLocation recipeId, JsonObject json) {
            int reqcount = 0;
            int t = json.get("total").getAsInt();
            int y;
            if (json.has("tier")) {
                y = json.get("tier").getAsInt();
            } else {
                y = 3;
            }

            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(t,Ingredient.EMPTY);

            String s1 = JSONUtils.getString(json, "result");
            ResourceLocation resourcelocation = new ResourceLocation(s1);
            ItemStack stack = new ItemStack(Registry.ITEM.getOptional(resourcelocation).orElseThrow(() -> new IllegalStateException("Item: " + s1 + " does not exist")));

            NonNullList<PeriodicTableUtils.Element> elements = NonNullList.withSize(t, PeriodicTableUtils.Element.MERCURY);
            NonNullList<Float> mins = NonNullList.withSize(t, 0f);
            NonNullList<Float> maxes = NonNullList.withSize(t, 0f);
            for (int i = 0; i < t; i++) {
                String input = "input" + (i+1);
                if (json.has(input)) {
                    JsonObject object = JSONUtils.getJsonObject(json, input);
                    String elementTag = "";
                    if (object.has("element")){
                        elements.set(i, PeriodicTableUtils.Element.valueOfCaseIgnored(object.get("element").getAsString()));
                        elementTag = object.get("element").getAsString().toLowerCase();
                    } else {
                        elements.set(i,PeriodicTableUtils.Element.MERCURY);
                        elementTag = "mercury";
                    }

                    boolean e = !json.has("auto") || (json.has("auto") && JSONUtils.getBoolean(json, "auto"));
                    if (e) {
                        String rs = "rankine:elements/" + elementTag;
                        JsonObject d = new JsonObject();
                        d.addProperty("tag",rs);
                        Ingredient out = AlloyIngredientHelper.deserialize(d,null);
                        if (out != Ingredient.EMPTY) {
                            nonnulllist.set(i, AlloyIngredientHelper.deserialize(d,null));
                        } else {
                            nonnulllist.set(i, Ingredient.fromStacks(new ItemStack(RankineItems.ELEMENT.get())));
                        }
                    } else {
                        nonnulllist.set(i,AlloyIngredientHelper.deserialize(object,null));
                    }


                    if (object.has("min")){
                        mins.set(i,Math.min(Math.max(object.get("min").getAsFloat(),0f),1f));
                        if (object.get("min").getAsFloat() > 0) {
                            reqcount += 1;
                        }
                    } else {
                        mins.set(i,0f);
                    }

                    if (object.has("max")){
                        maxes.set(i,Math.min(Math.max(object.get("max").getAsFloat(),0f),1f));
                    } else {
                        maxes.set(i,0f);
                    }

                }
            }

            int r;
            if (json.has("required") && reqcount < json.get("required").getAsInt()) {
                r = json.get("required").getAsInt();
            } else {
                r = reqcount;
            }
            if (r > 5 || r <= 1) {
                throw new JsonParseException("Unsupported number of alloy ingredient requirements (" + r + ") in " + json);
            }

            return new AlloyingRecipe(recipeId, t,r, y, nonnulllist, elements, stack, mins, maxes);
        }

        public AlloyingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            int t = buffer.readInt();
            int r = buffer.readInt();
            int y = buffer.readInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(t, Ingredient.EMPTY);

            for(int k = 0; k < nonnulllist.size(); ++k) {
                nonnulllist.set(k, Ingredient.read(buffer));
            }

            NonNullList<PeriodicTableUtils.Element> elements = NonNullList.withSize(t, PeriodicTableUtils.Element.MERCURY);

            for(int k = 0; k < elements.size(); ++k) {
                elements.set(k, PeriodicTableUtils.Element.valueOfCaseIgnored(buffer.readString()));
            }

            ItemStack stack = buffer.readItemStack();

            NonNullList<Float> mins = NonNullList.withSize(t, 0f);
            for(int k = 0; k < mins.size(); ++k) {
                mins.set(k, buffer.readFloat());
            }


            NonNullList<Float> maxes = NonNullList.withSize(t,0f);
            for(int k = 0; k < maxes.size(); ++k) {
                maxes.set(k, buffer.readFloat());
            }


            return new AlloyingRecipe(recipeId,t,r,y,nonnulllist, elements, stack, mins, maxes);
        }

        public void write(PacketBuffer buffer, AlloyingRecipe recipe) {
            buffer.writeInt(recipe.total);
            buffer.writeInt(recipe.required);
            buffer.writeInt(recipe.tier);
            int count = 0;
            for(Ingredient ingredient : recipe.recipeItems) {
                ingredient.write(buffer);
            }
            while (count < recipe.total) {
                Ingredient.EMPTY.write(buffer);
                count++;
            }

            count = 0;
            for(PeriodicTableUtils.Element element : recipe.elements) {
                buffer.writeString(element.name());
            }
            while (count < recipe.total) {
                buffer.writeString(PeriodicTableUtils.Element.MERCURY.name());
                count++;
            }

            buffer.writeItemStack(recipe.recipeOutput);

            count = 0;
            for (float chance : recipe.mins) {
                buffer.writeFloat(chance);
            }
            while (count < recipe.total) {
                buffer.writeFloat(0f);
                count++;
            }

            count = 0;
            for (float add : recipe.maxes) {
                buffer.writeFloat(add);
            }
            while (count < recipe.total) {
                buffer.writeFloat(0f);
                count++;
            }

        }
    }

}
