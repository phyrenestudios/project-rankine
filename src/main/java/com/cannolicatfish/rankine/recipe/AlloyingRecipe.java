package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceTile;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceTile;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.alloys.AlloyData;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.recipe.helper.AlloyRecipeHelper;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
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
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

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

    public List<PeriodicTableUtils.Element> getElementList(boolean required) {
        List<PeriodicTableUtils.Element> ret = new ArrayList<>();
        for (int i = 0; i < this.recipeItems.size(); i++) {
            if ((this.mins.get(i) > 0 && required) || (this.mins.get(i) == 0 && !required)) {
                ret.add(this.elements.get(i));
            }
        }
        return ret;
    }

    public ItemStack generateResult(IInventory inv, int type) {
         if ((getTier() & type) != Math.min(getTier(),type) && getTier() != 0 && type != 0) {
            return ItemStack.EMPTY;
        }

        List<PeriodicTableUtils.Element> currentElements = new ArrayList<>();
        List<Integer> currentMaterial = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            int workingIndex = 0;
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                boolean flag = false;
                for (Ingredient s : getIngredients()) {
                    if (s.test(stack)) {
                        PeriodicTableUtils.Element element = getElements().get(getIngredients().indexOf(s));
                        if (!currentElements.contains(element)) {
                            currentElements.add(element);
                            currentMaterial.add(0);
                        }
                        workingIndex = currentElements.indexOf(element);
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
                        currentMaterial.set(workingIndex, currentMaterial.get(workingIndex) + 81 * stack.getCount());
                    } else if (stack.getItem().getTags().contains(new ResourceLocation("forge:ingots")) || registry.contains("ingot")) {
                        currentMaterial.set(workingIndex,currentMaterial.get(workingIndex) + 9 * stack.getCount());
                    } else if (stack.getItem().getTags().contains(new ResourceLocation("forge:nuggets")) || registry.contains("nugget")) {
                        currentMaterial.set(workingIndex,currentMaterial.get(workingIndex) + stack.getCount());
                    } else if (stack.getItem() == Items.NETHERITE_SCRAP || registry.contains("scrap")){
                        currentMaterial.set(workingIndex,currentMaterial.get(workingIndex) + 2 * stack.getCount());
                    } else {
                        currentMaterial.set(workingIndex,currentMaterial.get(workingIndex) + 9 * stack.getCount());
                    }
                } else {
                    return ItemStack.EMPTY;
                }
            }
        }

        int sum = currentMaterial.stream().mapToInt(Integer::intValue).sum();

        if ((Math.round(sum/10f) > 64 || Math.round(sum/10f) < 1) && currentElements.size() >= this.required){
            //System.out.println("Required total " + this.required + " not present or material total not between 1 and 64!");
            return ItemStack.EMPTY;
        }
        for (PeriodicTableUtils.Element e : getElementList(true))
        {
            if (!currentElements.contains(e)) {
                //System.out.println("Required element " + e + " not present!");
                return ItemStack.EMPTY;
            }
        }

        List<Integer> percents = new ArrayList<>();
        List<String> symbols = new ArrayList<>();
        for (int j = 0; j < currentElements.size(); j++) {
            PeriodicTableUtils.Element curEl = currentElements.get(j);
            int curPer = Math.round(currentMaterial.get(j) * 100f/sum);
            int windex = getElements().indexOf(curEl);
            if (Math.round(getMins().get(windex) * 100) > curPer || Math.round(getMaxes().get(windex) * 100) < curPer) {
                //System.out.println("Element " + curEl + " does not fall between max or min!");
                //System.out.println("Min: " + Math.round(getMins().get(windex) * 100) + "%");
                //System.out.println("Max: " + Math.round(getMaxes().get(windex) * 100) + "%");
                //System.out.println("Element %: " + curPer + "%");
                return ItemStack.EMPTY;
            }
            symbols.add(curEl.getSymbol());
            percents.add(curPer);
        }
        if (percents.stream().mapToInt(Integer::intValue).sum() != 100 || percents.contains(0)) {
            return ItemStack.EMPTY;
        }
        ItemStack out = new ItemStack(this.recipeOutput.copy().getItem(),Math.round(sum/10f));
        AlloyItem.addAlloy(out,new AlloyData(AlloyRecipeHelper.getInstance().getDirectComposition(percents,symbols)));
        return out;
    }

    public ItemStack generateRandomResult(World worldIn) {
        List<Integer> percents = new ArrayList<>();
        List<String> symbols = new ArrayList<>();
        List<PeriodicTableUtils.Element> req = getElementList(true);
        List<PeriodicTableUtils.Element> nonreq = getElementList(false);
        int r = worldIn.getRandom().nextInt(6 - required) + required;
        int total = 0;
        for (int j = 0; j < r; j++) {
            PeriodicTableUtils.Element curEl;
            if (j < req.size()) {
                curEl = req.get(j);
            } else {
                curEl = nonreq.get(worldIn.getRandom().nextInt(nonreq.size()));
            }

            if (symbols.contains(curEl.getSymbol()) || total >= 100) {
                break;
            }
            int windex = getElements().indexOf(curEl);
            int min = Math.round(getMins().get(windex) * 100);
            int max = Math.round(getMaxes().get(windex) * 100);

            int curPer = Math.min(worldIn.getRandom().nextInt(max - min) + min, 100 - total);
            total += curPer;
            symbols.add(curEl.getSymbol());
            percents.add(curPer);
        }
        ItemStack out = new ItemStack(this.recipeOutput.copy().getItem(),1);
        AlloyItem.addAlloy(out,new AlloyData(AlloyRecipeHelper.getInstance().getDirectComposition(percents,symbols)));
        return out;
    }

    public NonNullList<Float> getMins() {
        return mins;
    }

    public NonNullList<Float> getMaxes() {
        return maxes;
    }



    @Override
    public boolean matches(IInventory inv, World worldIn) {
        if (inv instanceof AlloyFurnaceTile) {
            return !generateResult(inv,1).isEmpty();
        } else if (inv instanceof InductionFurnaceTile) {
            return !generateResult(inv,2).isEmpty();
        } else if (getTier() != 0){
            return !generateResult(inv,3).isEmpty();
        } else {
            return false;
        }
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
                        nonnulllist.set(i, AlloyIngredientHelper.deserialize(object,null));
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
                count++;
            }
            while (count < recipe.total) {
                Ingredient.EMPTY.write(buffer);
                count++;
            }

            count = 0;
            for(PeriodicTableUtils.Element element : recipe.elements) {
                buffer.writeString(element.name());
                count++;
            }
            while (count < recipe.total) {
                buffer.writeString(PeriodicTableUtils.Element.MERCURY.name());
                count++;
            }

            buffer.writeItemStack(recipe.recipeOutput);

            count = 0;
            for (float chance : recipe.mins) {
                buffer.writeFloat(chance);
                count++;
            }
            while (count < recipe.total) {
                buffer.writeFloat(0f);
                count++;
            }

            count = 0;
            for (float add : recipe.maxes) {
                buffer.writeFloat(add);
                count++;
            }
            while (count < recipe.total) {
                buffer.writeFloat(0f);
                count++;
            }

        }
    }

}
