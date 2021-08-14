package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.blocks.alloyfurnace.AlloyFurnaceTile;
import com.cannolicatfish.rankine.blocks.inductionfurnace.InductionFurnaceTile;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.items.alloys.AlloyData;
import com.cannolicatfish.rankine.items.alloys.AlloyItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyItem;
import com.cannolicatfish.rankine.recipe.helper.AlloyIngredientHelper;
import com.cannolicatfish.rankine.recipe.helper.AlloyRecipeHelper;
import com.cannolicatfish.rankine.util.ElementEquation;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
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

import java.util.*;

public class AlloyingRecipe implements IRecipe<IInventory> {

    private final int total;

    private final int tier;
    private final NonNullList<ResourceLocation> elements;
    private final ItemStack recipeOutput;
    private final ResourceLocation id;
    private final NonNullList<Float> mins;
    private final NonNullList<Float> maxes;
    private final NonNullList<Boolean> required;
    private final List<Float> bonusValues;
    private final int color;
    private final List<String> enchantments;
    private final List<String> enchantmentTypes;
    private final int minEnchantability;
    private final int enchantInterval;
    private final int maxEnchantLevelIn;
    private final String localName;

    public static final AlloyingRecipe.Serializer SERIALIZER = new AlloyingRecipe.Serializer();

    public AlloyingRecipe(ResourceLocation idIn, int totalIn, int tierIn, NonNullList<ResourceLocation> elementsIn, NonNullList<Boolean> requiredIn, NonNullList<Float> minsIn, NonNullList<Float> maxesIn,
                          ItemStack outputIn, List<Float> bonusValuesIn, List<String> enchantmentsIn, List<String> enchantmentTypesIn, int minEnchantabilityIn, int enchantIntervalIn, int maxEnchantLevelIn,
                          String nameIn, int colorIn) {
        this.id = idIn;
        this.total = totalIn;
        this.required = requiredIn;
        this.tier = tierIn; // Binary: 1 = Alloy Furnace, 2 = Induction Furnace, 3 = Alloy Furnace && Induction Furnace
        this.elements = elementsIn;
        this.recipeOutput = outputIn;
        this.mins = minsIn;
        this.maxes = maxesIn;
        this.bonusValues = bonusValuesIn;
        this.enchantments = enchantmentsIn;
        this.enchantmentTypes = enchantmentTypesIn;
        this.minEnchantability = minEnchantabilityIn;
        this.enchantInterval = enchantIntervalIn;
        this.maxEnchantLevelIn = maxEnchantLevelIn;
        this.color = colorIn;
        this.localName = nameIn;
    }

    public String getGroup() {
        return "";
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.withSize(1,Ingredient.EMPTY);
    }

    public int getTier() {
        return this.tier;
    }

    public List<Ingredient> getIngredientsList(World worldIn) {
        List<Ingredient> ret = new ArrayList<>();
        for (ResourceLocation rs : this.elements) {
            if (worldIn.getRecipeManager().getRecipe(rs).isPresent()) {
                ElementRecipe recipe = (ElementRecipe) worldIn.getRecipeManager().getRecipe(rs).get();
                ret.add(Ingredient.merge(recipe.getIngredients()));
            }
        }
        return ret;
    }

    public List<Ingredient> getIngredientsList(World worldIn, boolean required) {
        List<Ingredient> ret = new ArrayList<>();
        for (int i = 0; i < this.elements.size(); i++) {
            ResourceLocation rs = this.elements.get(i);
            if (worldIn.getRecipeManager().getRecipe(rs).isPresent() && this.required.get(i).equals(required)) {
                ElementRecipe recipe = (ElementRecipe) worldIn.getRecipeManager().getRecipe(rs).get();
                ret.add(Ingredient.merge(recipe.getIngredients()));
            }
        }
        return ret;
    }

    public List<ElementRecipe> getElementList(World worldIn, boolean required) {
        List<ElementRecipe> ret = new ArrayList<>();
        for (int i = 0; i < this.elements.size(); i++) {
            ResourceLocation rs = this.elements.get(i);
            if (worldIn.getRecipeManager().getRecipe(rs).isPresent() && this.required.get(i).equals(required)) {
                ElementRecipe recipe = (ElementRecipe) worldIn.getRecipeManager().getRecipe(rs).get();
                ret.add(recipe);
            }
        }
        return ret;
    }

    public List<ElementRecipe> getElementList(World worldIn) {
        List<ElementRecipe> ret = new ArrayList<>();
        for (ResourceLocation rs : this.elements) {
            if (worldIn.getRecipeManager().getRecipe(rs).isPresent()) {
                ElementRecipe recipe = (ElementRecipe) worldIn.getRecipeManager().getRecipe(rs).get();
                ret.add(recipe);
            }
        }
        return ret;
    }

    public int getTotalRequired() {
        return Collections.frequency(this.required,true);
    }

    public List<Integer> getIndexList(World worldIn, boolean required) {
        List<Integer> ret = new ArrayList<>();
        for (int i = 0; i < this.elements.size(); i++) {
            ResourceLocation rs = this.elements.get(i);
            if (worldIn.getRecipeManager().getRecipe(rs).isPresent() && this.required.get(i).equals(required)) {
                ret.add(i);
            }
        }
        return ret;
    }

    public ItemStack generateResult(World worldIn, IInventory inv, int type) {
        if ((getTier() & type) != Math.min(getTier(),type) && getTier() != 0 && type != 0) {
            return ItemStack.EMPTY;
        }

        List<ElementRecipe> currentElements = new ArrayList<>();
        List<Integer> currentMaterial = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty()) {
                boolean flag = false;
                ElementRecipe element = worldIn.getRecipeManager().getRecipe(RankineRecipeTypes.ELEMENT, new Inventory(stack), worldIn).orElse(null);
                if (element != null && getElements().contains(element.getId())) {
                    if (!currentElements.contains(element)) {
                        currentElements.add(element);
                        currentMaterial.add(element.getMaterialCount(stack.getItem()) * stack.getCount());
                    } else {
                        currentMaterial.set(currentElements.indexOf(element),currentMaterial.get(currentElements.indexOf(element)) + element.getMaterialCount(stack.getItem()) * stack.getCount());
                    }
                    flag = true;
                }
                if (!flag) {
                    return ItemStack.EMPTY;
                }
            }
        }

        int sum = currentMaterial.stream().mapToInt(Integer::intValue).sum();

        if (currentElements.size() > 1 && (Math.round(sum/10f) > 64 || Math.round(sum/10f) < 1) && currentElements.size() >= getTotalRequired()){
            //System.out.println("Required total " + this.required + " not present or material total not between 1 and 64!");
            return ItemStack.EMPTY;
        }

        List<Integer> percents = new ArrayList<>();
        List<String> symbols = new ArrayList<>();
        for (int j = 0; j < currentElements.size(); j++) {
            ElementRecipe curEl = currentElements.get(j);
            int curPer = Math.round(currentMaterial.get(j) * 100f/sum);
            int windex = getElements().indexOf(curEl.getId());
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
        if (out.getItem() instanceof IAlloyItem) {
            ((IAlloyItem) out.getItem()).createAlloyNBT(out, worldIn, AlloyRecipeHelper.getDirectComposition(percents,symbols), this.id, !this.getLocalName().isEmpty() ? this.getLocalName() : null);
            if (this.getColor() != 16777215) {
                out.getOrCreateTag().putInt("color",this.getColor());
            }
        }

        return out;
    }

    /*public ItemStack generateRandomResult(World worldIn) {
        List<Integer> percents = new ArrayList<>();
        List<String> symbols = new ArrayList<>();
        List<ResourceLocation> req = getElementList(true);
        List<ResourceLocation> nonreq = getElementList(false);
        int r = worldIn.getRandom().nextInt(6 - required) + required;
        int total = 0;
        for (int j = 0; j < r; j++) {
            ResourceLocation curEl;
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
        AlloyItem.addAlloy(out,new AlloyData(AlloyRecipeHelper.getDirectComposition(percents,symbols)));
        return out;
    }*/

    public List<String> getEnchantments() {
        return enchantments;
    }

    public List<String> getEnchantmentTypes() {
        return enchantmentTypes;
    }

    public NonNullList<Float> getMins() {
        return mins;
    }

    public NonNullList<Float> getMaxes() {
        return maxes;
    }

    public List<Float> getBonusValues() {
        return bonusValues;
    }

    public NonNullList<Boolean> getRequired() {
        return required;
    }

    public String getLocalName() {
        return localName;
    }

    public int getColor() {
        return color;
    }

    public int getBonusDurability() { return Math.round(this.getBonusValues().get(0));}

    public float getBonusMiningSpeed() { return this.getBonusValues().get(1);}

    public int getBonusMiningLevel() { return Math.round(this.getBonusValues().get(2));}

    public int getBonusEnchantability() { return Math.round(this.getBonusValues().get(3));}

    public float getBonusDamage() { return this.getBonusValues().get(4);}

    public float getBonusAttackSpeed() { return this.getBonusValues().get(5);}

    public float getBonusCorrosionResistance() { return this.getBonusValues().get(6);}

    public float getBonusHeatResistance() { return this.getBonusValues().get(7);}

    public float getBonusKnockbackResistance() { return this.getBonusValues().get(8);}

    public float getBonusToughness() { return this.getBonusValues().get(9);}

    public int getMinEnchantability() {
        return minEnchantability;
    }

    public int getEnchantInterval() {
        return enchantInterval;
    }

    public int getMaxEnchantLevelIn() {
        return maxEnchantLevelIn;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        if (inv instanceof AlloyFurnaceTile) {
            return !generateResult(worldIn,inv,1).isEmpty();
        } else if (inv instanceof InductionFurnaceTile) {
            return !generateResult(worldIn, inv,2).isEmpty();
        } else if (getTier() != 0){
            return !generateResult(worldIn, inv,3).isEmpty();
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
        ItemStack out = this.recipeOutput.copy();
        if (this.getColor() != 16777215) {
            out.getOrCreateTag().putInt("color",this.getColor());
        }

        if (!this.getLocalName().isEmpty()) {
            out.getOrCreateTag().putString("nameOverride",this.getLocalName());
        }
        return out;
    }

    public int getTotal() {
        return this.total;
    }

    public NonNullList<ResourceLocation> getElements() {
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
            int c;
            if (json.has("color")) {
                c = Math.max(0,json.get("color").getAsInt());
            } else {
                c = 16777215;
            }
            String n;
            if (json.has("name")) {
                n = json.get("name").getAsString();
            } else {
                n = "";
            }

            String s1 = JSONUtils.getString(json, "result");
            ResourceLocation resourcelocation = new ResourceLocation(s1);
            ItemStack stack = new ItemStack(Registry.ITEM.getOptional(resourcelocation).orElseThrow(() -> new IllegalStateException("Item: " + s1 + " does not exist")));

            NonNullList<ResourceLocation> elements = NonNullList.withSize(t, new ResourceLocation(""));
            NonNullList<Float> mins = NonNullList.withSize(t, 0f);
            NonNullList<Float> maxes = NonNullList.withSize(t, 0f);
            NonNullList<Boolean> reqs = NonNullList.withSize(t, false);

            for (int i = 0; i < t; i++) {
                String input = "input" + (i+1);
                if (json.has(input)) {
                    JsonObject object = JSONUtils.getJsonObject(json, input);
                    if (object.has("element")){
                        elements.set(i, new ResourceLocation(object.get("element").getAsString()));
                    } else {
                        throw new JsonParseException("Object 'element' for " + input + " does not exist!");
                    }


                    if (object.has("min")){
                        mins.set(i,Math.min(Math.max(object.get("min").getAsFloat(),0f),1f));
                    }

                    if (object.has("max")){
                        maxes.set(i,Math.min(Math.max(object.get("max").getAsFloat(),0f),1f));
                    }

                    if (object.has("required")){
                        reqs.set(i,object.get("required").getAsBoolean());
                    }

                }
            }

            int r = Collections.frequency(reqs,true);
            if (r > 6) {
                throw new JsonParseException("Unsupported number of alloy ingredient requirements (" + r + ") in " + json);
            }

            String[] stats = new String[]{"durability","miningspeed","mininglevel","enchantability","damage","attackspeed",
                    "corrosionresist","heatresist","knockbackresist","toughness"};
            List<Float> bonusStats = new ArrayList<>();
            for (String stat : stats) {
                if (json.has(stat)) {
                    bonusStats.add(JSONUtils.getFloat(json, stat));
                } else {
                    bonusStats.add(0f);
                }
            }

            List<String> enchantments = new ArrayList<>();
            List<String> enchantmentTypes = new ArrayList<>();
            if (json.has("enchantments")) {
                JsonArray e = JSONUtils.getJsonArray(json,"enchantments");
                JsonArray eTypes = JSONUtils.getJsonArray(json,"enchantmentTypes");
                for (int i = 0; i < e.size(); i++) {
                    enchantments.add(e.get(i).getAsString().toLowerCase(Locale.ROOT));
                    enchantmentTypes.add(eTypes.get(i).getAsString().toUpperCase(Locale.ROOT));
                }
            }

            int startEnchant = json.has("minEnchantability") ? json.get("minEnchantability").getAsInt() : 10;
            int interval = json.has("enchantInterval") ? json.get("enchantInterval").getAsInt() : 5;
            int maxLvl = json.has("maxEnchantLevel") ? json.get("maxEnchantLevel").getAsInt() : 5;
            return new AlloyingRecipe(recipeId, t, y, elements, reqs, mins, maxes, stack, bonusStats,enchantments,enchantmentTypes,startEnchant,interval,maxLvl,n,c);
        }

        public AlloyingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            int t = buffer.readInt();
            int y = buffer.readInt();
            List<Float> bonusStats = new ArrayList<>();

            NonNullList<ResourceLocation> elements = NonNullList.withSize(t, new ResourceLocation(""));

            for(int k = 0; k < elements.size(); ++k) {
                elements.set(k, new ResourceLocation(buffer.readString()));
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

            NonNullList<Boolean> reqs = NonNullList.withSize(t,false);
            for(int k = 0; k < reqs.size(); ++k) {
                reqs.set(k, buffer.readBoolean());
            }

            for (int k = 0; k < 10; k++) {
                bonusStats.add(buffer.readFloat());
            }

            String n = buffer.readString();
            int c = buffer.readInt();

            int size = buffer.readInt();
            List<String> enchantments = new ArrayList<>();
            List<String> enchantmentTypes = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                enchantments.add(buffer.readString().toLowerCase(Locale.ROOT));
                enchantmentTypes.add(buffer.readString().toUpperCase(Locale.ROOT));
            }

            int startEnchant = buffer.readInt();
            int interval = buffer.readInt();
            int maxLvl = buffer.readInt();
            return new AlloyingRecipe(recipeId,t,y, elements, reqs, mins, maxes, stack,bonusStats,enchantments,enchantmentTypes,startEnchant,interval,maxLvl,n,c);
        }

        public void write(PacketBuffer buffer, AlloyingRecipe recipe) {
            buffer.writeInt(recipe.total);
            buffer.writeInt(recipe.tier);

            int count = 0;
            for(ResourceLocation element : recipe.elements) {
                buffer.writeString(element.toString());
                count++;
            }
            while (count < recipe.total) {
                buffer.writeString("rankine:elements/mercury");
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

            count = 0;
            for (boolean add : recipe.required) {
                buffer.writeBoolean(add);
                count++;
            }
            while (count < recipe.total) {
                buffer.writeBoolean(false);
                count++;
            }

            for (int k = 0; k < 10; k++) {
                buffer.writeFloat(recipe.getBonusValues().get(k));
            }

            buffer.writeString(recipe.getLocalName());
            buffer.writeInt(recipe.getColor());

            int size = recipe.getEnchantments().size();
            buffer.writeInt(size);
            for (int i = 0; i < size; i++) {
                buffer.writeString(recipe.getEnchantments().get(i));
                buffer.writeString(recipe.getEnchantmentTypes().get(i));
            }

            buffer.writeInt(recipe.minEnchantability);
            buffer.writeInt(recipe.enchantInterval);
            buffer.writeInt(recipe.maxEnchantLevelIn);
        }
    }

}
