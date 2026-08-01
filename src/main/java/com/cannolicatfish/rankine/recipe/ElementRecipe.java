package com.cannolicatfish.rankine.recipe;

import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineRecipeSerializers;
import com.cannolicatfish.rankine.init.RankineRecipeTypes;
import com.cannolicatfish.rankine.recipe.helper.RankineMapCodec;
import com.cannolicatfish.rankine.util.ElementEquation;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ElementRecipe implements Recipe<SingleRecipeInput> {

    private final String name;
    private final String symbol;
    private final int num;
    private final int color;
    private final float potential;
    private final List<String> items;
    private final List<Integer> values;
    private final List<ElementEquation> stats;
    private final List<String> enchantments;
    private final List<String> enchantmentTypes;
    private final List<Float> enchantmentFactors;

    public ElementRecipe(String nameIn, String symbolIn, int numIn, int colorIn, float potentialIn, List<String> items, List<Integer> values, List<ElementEquation> statsIn,
                         List<String> enchantmentsIn, List<String> enchantmentTypesIn, List<Float> enchantmentFactorsIn) {
        this.num = numIn;
        this.color = colorIn;
        this.potential = potentialIn;
        this.items = items;
        this.values = values;
        this.name = nameIn;
        this.symbol = symbolIn;
        this.stats = statsIn;
        this.enchantments = enchantmentsIn;
        this.enchantmentTypes = enchantmentTypesIn;
        this.enchantmentFactors = enchantmentFactorsIn;
    }

    private static boolean itemIsInTag(Item item, ResourceLocation tagName) {
        TagKey<Item> tag = TagKey.create(Registries.ITEM, tagName);
        return item.builtInRegistryHolder().is(tag);
    }

    @Override
    public boolean matches(SingleRecipeInput inv, Level worldIn) {
        Item reg = inv.item().getItem();
        if (reg != Items.AIR) {
            for (String s : items) {
                if (s.contains("T#")) {
                    if (itemIsInTag(reg, ResourceLocation.parse(s.split("T#")[1]))){
                        return true;
                    }
                } else if (s.contains("I#")) {
                    Item item = BuiltInRegistries.ITEM.getOptional(ResourceLocation.parse(s.split("I#")[1])).orElse(Items.AIR);
                    if (item != Items.AIR && item == reg) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String getGroup() {
        return "";
    }

    @Override
    public ItemStack assemble(SingleRecipeInput inv, HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return new ItemStack(RankineItems.ELEMENT.get());
    }

    /** Convenience overload matching the pre-1.21 API. */
    public ItemStack getResultItem() {
        return new ItemStack(RankineItems.ELEMENT.get());
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.withSize(items.size(),Ingredient.EMPTY);
        int count = 0;
        for (String s : getItems()) {

            if (s.contains("T#")) {
                TagKey<Item> tag = TagKey.create(Registries.ITEM, ResourceLocation.parse(s.split("T#")[1]));
                list.set(count,Ingredient.of(tag));
            } else if (s.contains("I#")) {
                Item item = BuiltInRegistries.ITEM.getOptional(ResourceLocation.parse(s.split("I#")[1])).orElse(null);
                if (item != null) {
                    list.set(count,Ingredient.of(item));
                }
            }
            count++;
        }
        return list;
    }

    public String getIngredientFromCount(int count) {
        if (getValues().contains(count)) {
            return getItems().get(getValues().indexOf(count));
        }
        return "";
    }

    @Override
    public RecipeType<?> getType() {
        return RankineRecipeTypes.ELEMENT.get();
    }

    public String getName() {
        return this.name;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public int getAtomicNumber() {
        return this.num;
    }


    public int getColor() {
        return this.color;
    }

    public float getElectrodePotential() {
        return potential;
    }

    public List<ElementEquation> getStats() {
        return stats;
    }

    public ElementEquation getDurabilityFormula() {
        return this.getStats().get(0);
    }

    public int getDurability(int x) { return this.getDurabilityFormula().calculateRounded(x);}

    public ElementEquation getMiningSpeedFormula() {
        return this.getStats().get(1);
    }

    public float getMiningSpeed(int x) { return this.getMiningSpeedFormula().calculateFloat(x);}

    public ElementEquation getMiningLevelFormula() {
        return this.getStats().get(2);
    }

    public int getMiningLevel(int x) { return this.getMiningLevelFormula().calculateRounded(x);}

    public ElementEquation getEnchantabilityFormula() {
        return this.getStats().get(3);
    }

    public int getEnchantability(int x) { return this.getEnchantabilityFormula().calculateRounded(x);}

    public ElementEquation getDamageFormula() {
        return this.getStats().get(4);
    }

    public float getDamage(int x) { return this.getDamageFormula().calculateFloat(x);}

    public ElementEquation getAttackSpeedFormula() {
        return this.getStats().get(5);
    }

    public float getAttackSpeed(int x) { return this.getAttackSpeedFormula().calculateFloat(x);}

    public ElementEquation getCorrosionResistanceFormula() {
        return this.getStats().get(6);
    }

    public float getCorrosionResistance(int x) { return this.getCorrosionResistanceFormula().calculateFloat(x);}

    public ElementEquation getHeatResistanceFormula() {
        return this.getStats().get(7);
    }

    public float getHeatResistance(int x) { return this.getHeatResistanceFormula().calculateFloat(x);}

    public ElementEquation getKnockbackResistanceFormula() {
        return this.getStats().get(8);
    }

    public float getKnockbackResistance(int x) { return this.getKnockbackResistanceFormula().calculateFloat(x);}

    public ElementEquation getToughnessFormula() {
        return this.getStats().get(9);
    }

    public float getToughness(int x) { return this.getToughnessFormula().calculateFloat(x);}

    public ElementEquation getStatEquation(int stat){
        return this.getStats().get(stat);
    }

    public float getStat(int stat, int x) {
        switch (stat) {
            case 0:
                return this.getDurability(x);
            case 1:
                return this.getMiningSpeed(x);
            case 2:
                return this.getMiningLevel(x);
            case 3:
                return this.getEnchantability(x);
            case 4:
                return this.getDamage(x);
            case 5:
                return this.getAttackSpeed(x);
            case 6:
                return this.getCorrosionResistance(x);
            case 7:
                return this.getHeatResistance(x);
            case 8:
                return this.getKnockbackResistance(x);
            case 9:
                return this.getToughness(x);
        }
        return -1;
    }
    public List<String> getItems() {
        return items;
    }

    public List<Integer> getValues() {
        return values;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RankineRecipeSerializers.ELEMENT_RECIPE_SERIALIZER.get();
    }

    public List<String> getEnchantments() {
        return enchantments;
    }

    public List<String> getEnchantmentTypes() {
        return enchantmentTypes;
    }

    public List<Float> getEnchantmentFactors() {
        return enchantmentFactors;
    }

    public int getMaterialCount(Item reg) {
        for (int i = 0; i < getItems().size(); i++) {
            String s = getItems().get(i);
            if (s.contains("T#")) {
                if (itemIsInTag(reg, ResourceLocation.parse(s.split("T#")[1]))){
                    return getValues().get(i);
                }
            } else if (s.contains("I#")) {
                Item item = BuiltInRegistries.ITEM.getOptional(ResourceLocation.parse(s.split("I#")[1])).orElse(Items.AIR);
                if (item != Items.AIR && reg == item) {
                    return getValues().get(i);
                }
            }
        }
        return 0;
    }

    public static class Serializer implements RecipeSerializer<ElementRecipe> {

        private static final String[] STATS = new String[]{"durability","miningspeed","mininglevel","enchantability","damage","attackspeed",
                "corrosionresist","heatresist","knockbackresist","toughness"};

        public static final MapCodec<ElementRecipe> CODEC = RankineMapCodec.of(
                Serializer::fromJson, Serializer::toJson,
                RankineMapCodec.fixedKeys("name","symbol","atomic","color","potential","items","values",
                        "durability","miningspeed","mininglevel","enchantability","damage","attackspeed",
                        "corrosionresist","heatresist","knockbackresist","toughness",
                        "enchantments","enchantmentTypes","enchantmentFactors"));

        public static final StreamCodec<RegistryFriendlyByteBuf, ElementRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::toNetwork, Serializer::fromNetwork);

        @Override
        public MapCodec<ElementRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ElementRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static ElementRecipe fromJson(JsonObject json) {
            String n = json.get("name").getAsString().toLowerCase(Locale.ROOT);
            String s = json.get("symbol").getAsString();
            int t = json.get("atomic").getAsInt();
            int c;
            if (json.has("color")) {
                c = Math.max(0,json.get("color").getAsInt());
            } else {
                c = 16777215;
            }
            float p;
            if (json.has("potential")) {
                p = json.get("potential").getAsFloat();
            } else {
                p = 0;
            }
            JsonArray it = GsonHelper.getAsJsonArray(json,"items");
            JsonArray val = GsonHelper.getAsJsonArray(json,"values");
            List<String> itemList = new ArrayList<>();
            List<Integer> valueList = new ArrayList<>();
            for (int i = 0; i < it.size(); i++) {
                itemList.add(i,it.get(i).getAsString());
                valueList.add(i,val.get(i).getAsInt());
            }
            List<ElementEquation> equations = new ArrayList<>();
            int index = 0;
            for (String stat : STATS) {
                if (json.has(stat)) {
                    JsonObject object = GsonHelper.getAsJsonObject(json, stat);
                    JsonArray breaks = GsonHelper.getAsJsonArray(object,"breaks");
                    JsonArray formulas = GsonHelper.getAsJsonArray(object,"formulas");
                    JsonArray a = GsonHelper.getAsJsonArray(object,"a");
                    JsonArray b = GsonHelper.getAsJsonArray(object,"b");
                    JsonArray modifiers = GsonHelper.getAsJsonArray(object,"modifiers");
                    JsonArray limit = GsonHelper.getAsJsonArray(object,"limit");

                    int[] breaksIn = new int[breaks.size()];
                    ElementEquation.FormulaType[] formulasIn = new ElementEquation.FormulaType[breaks.size()];
                    float[] aIn = new float[breaks.size()];
                    float[] bIn = new float[breaks.size()];
                    ElementEquation.FormulaModifier[] modifiersIn = new ElementEquation.FormulaModifier[breaks.size()];
                    float[] limitIn = new float[breaks.size()];
                    for (int i = 0; i < breaks.size(); i++) {
                        breaksIn[i] = breaks.get(i).getAsInt();
                        formulasIn[i] = ElementEquation.FormulaType.valueOf(formulas.get(i).getAsString().toUpperCase(Locale.ROOT));
                        aIn[i] = a.get(i).getAsFloat();
                        bIn[i] = b.get(i).getAsFloat();
                        modifiersIn[i] = ElementEquation.FormulaModifier.valueOf(modifiers.get(i).getAsString().toUpperCase(Locale.ROOT));
                        limitIn[i] = limit.get(i).getAsFloat();
                    }
                    equations.add(index,new ElementEquation(breaksIn,formulasIn,aIn,bIn,modifiersIn,limitIn));
                } else {
                    equations.add(index,new ElementEquation());
                }
                index++;
            }
            List<String> enchantments = new ArrayList<>();
            List<String> enchantmentTypes = new ArrayList<>();
            List<Float> enchantmentFactors = new ArrayList<>();
            if (json.has("enchantments")) {
                JsonArray e = GsonHelper.getAsJsonArray(json,"enchantments");
                JsonArray eTypes = GsonHelper.getAsJsonArray(json,"enchantmentTypes");
                JsonArray eFactors = GsonHelper.getAsJsonArray(json,"enchantmentFactors");
                for (int i = 0; i < e.size(); i++) {
                    enchantments.add(e.get(i).getAsString().toLowerCase(Locale.ROOT));
                    enchantmentTypes.add(eTypes.get(i).getAsString().toUpperCase(Locale.ROOT));
                    enchantmentFactors.add(Math.min(1,Math.max(-1,eFactors.get(i).getAsFloat())));
                }
            }
            return new ElementRecipe(n,s,t,c,p,itemList,valueList,equations,enchantments,enchantmentTypes,enchantmentFactors);
        }

        public static void toJson(ElementRecipe recipe, JsonObject json) {
            json.addProperty("name", recipe.getName());
            json.addProperty("symbol", recipe.getSymbol());
            json.addProperty("atomic", recipe.getAtomicNumber());
            json.addProperty("color", recipe.getColor());
            json.addProperty("potential", recipe.getElectrodePotential());
            JsonArray it = new JsonArray();
            JsonArray val = new JsonArray();
            for (int i = 0; i < recipe.getItems().size(); i++) {
                it.add(recipe.getItems().get(i));
                val.add(recipe.getValues().get(i));
            }
            json.add("items", it);
            json.add("values", val);
            for (int j = 0; j < STATS.length; j++) {
                ElementEquation formula = recipe.getStats().get(j);
                if (formula.isEmpty()) {
                    continue;
                }
                JsonObject object = new JsonObject();
                JsonArray breaks = new JsonArray();
                JsonArray formulas = new JsonArray();
                JsonArray a = new JsonArray();
                JsonArray b = new JsonArray();
                JsonArray modifiers = new JsonArray();
                JsonArray limit = new JsonArray();
                for (int i = 0; i < formula.getBreaks().length; i++) {
                    breaks.add(formula.getBreaks()[i]);
                    formulas.add(formula.getFormulaTypes()[i].toString().toUpperCase(Locale.ROOT));
                    a.add(formula.getA()[i]);
                    b.add(formula.getB()[i]);
                    modifiers.add(formula.getFormulaModifiers()[i].toString().toUpperCase(Locale.ROOT));
                    limit.add(formula.getLimit()[i]);
                }
                object.add("breaks", breaks);
                object.add("formulas", formulas);
                object.add("a", a);
                object.add("b", b);
                object.add("modifiers", modifiers);
                object.add("limit", limit);
                json.add(STATS[j], object);
            }
            if (!recipe.getEnchantments().isEmpty()) {
                JsonArray e = new JsonArray();
                JsonArray eTypes = new JsonArray();
                JsonArray eFactors = new JsonArray();
                for (int i = 0; i < recipe.getEnchantments().size(); i++) {
                    e.add(recipe.getEnchantments().get(i));
                    eTypes.add(recipe.getEnchantmentTypes().get(i));
                    eFactors.add(recipe.getEnchantmentFactors().get(i));
                }
                json.add("enchantments", e);
                json.add("enchantmentTypes", eTypes);
                json.add("enchantmentFactors", eFactors);
            }
        }

        public static ElementRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            List<ElementEquation> equations = new ArrayList<>();
            List<String> itemList = new ArrayList<>();
            List<Integer> valueList = new ArrayList<>();

            String name = buffer.readUtf();
            String sym = buffer.readUtf();
            int atomic = buffer.readInt();
            int color = Math.max(0,buffer.readInt());
            float potential = buffer.readFloat();

            int itemSize = buffer.readInt();
            for (int i = 0; i < itemSize; i++) {
                itemList.add(i,buffer.readUtf());
                valueList.add(i,buffer.readInt());
            }

            for (int j = 0; j < 10; j++) {
                boolean stat = buffer.readBoolean();
                if (stat) {
                    int breaks_dur = buffer.readInt();
                    int[] breaksIn = new int[breaks_dur];
                    ElementEquation.FormulaType[] formulasIn = new ElementEquation.FormulaType[breaks_dur];
                    float[] aIn = new float[breaks_dur];
                    float[] bIn = new float[breaks_dur];
                    ElementEquation.FormulaModifier[] modifiersIn = new ElementEquation.FormulaModifier[breaks_dur];
                    float[] limitIn = new float[breaks_dur];
                    for (int i = 0; i < breaks_dur; i++) {
                        breaksIn[i] = buffer.readInt();
                        formulasIn[i] = ElementEquation.FormulaType.valueOf(buffer.readUtf().toUpperCase(Locale.ROOT));
                        aIn[i] = buffer.readFloat();
                        bIn[i] = buffer.readFloat();
                        modifiersIn[i] = ElementEquation.FormulaModifier.valueOf(buffer.readUtf().toUpperCase(Locale.ROOT));
                        limitIn[i] = buffer.readFloat();
                    }
                    equations.add(j,new ElementEquation(breaksIn,formulasIn,aIn,bIn,modifiersIn,limitIn));
                } else {
                    equations.add(j,new ElementEquation());
                }
            }

            int size = buffer.readInt();
            List<String> enchantments = new ArrayList<>();
            List<String> enchantmentTypes = new ArrayList<>();
            List<Float> enchantmentFactors = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                enchantments.add(buffer.readUtf().toLowerCase(Locale.ROOT));
                enchantmentTypes.add(buffer.readUtf().toUpperCase(Locale.ROOT));
                enchantmentFactors.add(buffer.readFloat());
            }

            return new ElementRecipe(name,sym,atomic,color,potential,itemList,valueList,equations,enchantments,enchantmentTypes,enchantmentFactors);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buffer, ElementRecipe element) {
            buffer.writeUtf(element.getName());
            buffer.writeUtf(element.getSymbol());
            buffer.writeInt(element.getAtomicNumber());
            buffer.writeInt(element.getColor());
            buffer.writeFloat(element.getElectrodePotential());

            buffer.writeInt(element.getItems().size());
            for (int i = 0; i < element.getItems().size(); i++) {
                buffer.writeUtf(element.getItems().get(i));
                buffer.writeInt(element.getValues().get(i));
            }

            for (int j = 0; j < 10; j++) {
                ElementEquation formula = element.getStats().get(j);
                buffer.writeBoolean(!formula.isEmpty());
                if (!formula.isEmpty()) {
                    buffer.writeInt(formula.getBreaks().length);
                    for (int i = 0; i < formula.getBreaks().length; i++) {
                        buffer.writeInt(formula.getBreaks()[i]);
                        buffer.writeUtf(formula.getFormulaTypes()[i].toString().toUpperCase(Locale.ROOT));
                        buffer.writeFloat(formula.getA()[i]);
                        buffer.writeFloat(formula.getB()[i]);
                        buffer.writeUtf(formula.getFormulaModifiers()[i].toString().toUpperCase(Locale.ROOT));
                        buffer.writeFloat(formula.getLimit()[i]);
                    }
                }
            }
            int size = element.getEnchantments().size();
            buffer.writeInt(size);
            for (int i = 0; i < size; i++) {
                buffer.writeUtf(element.getEnchantments().get(i));
                buffer.writeUtf(element.getEnchantmentTypes().get(i));
                buffer.writeFloat(element.getEnchantmentFactors().get(i));
            }
        }
    }
}
