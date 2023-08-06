package com.cannolicatfish.rankine.element;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class Element {
    //private static final Codec<List<ResourceLocation>> RESOURCE_LOCATION_LIST_CODEC = ResourceLocation.CODEC.listOf();
    private static final Codec<List<Integer>> INTEGER_LIST_CODEC = Codec.INT.listOf();
    private static final Codec<List<Float>> FLOAT_LIST_CODEC = Codec.FLOAT.listOf();
    private static final Codec<List<String>> STRING_LIST_CODEC = Codec.STRING.listOf();
    public static final Codec<Element> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.optionalFieldOf("name").forGetter(l -> Optional.of(l.name)),
                    Codec.STRING.optionalFieldOf("symbol").forGetter(l -> Optional.of(l.symbol)),
                    Codec.INT.optionalFieldOf("atomicNumber").forGetter(l -> Optional.of(l.atomicNumber)),
                    Codec.INT.optionalFieldOf("color").forGetter(l -> Optional.of(l.color)),
                    STRING_LIST_CODEC.fieldOf("items").forGetter(l -> l.items),
                    INTEGER_LIST_CODEC.fieldOf("materialValues").forGetter(l -> l.materialValues),
                    ElementStats.CODEC.optionalFieldOf("elementStats").forGetter(l -> Optional.of(l.elementStats)),
                    STRING_LIST_CODEC.optionalFieldOf("enchantments").forGetter(l -> Optional.ofNullable(l.enchantments)),
                    STRING_LIST_CODEC.optionalFieldOf("enchantmentTypes").forGetter(l -> Optional.ofNullable(l.enchantmentTypes)),
                    FLOAT_LIST_CODEC.optionalFieldOf("enchantmentFactors").forGetter(l -> Optional.ofNullable(l.enchantmentFactors))
            ).apply(instance, Element::new));

    private ResourceLocation location;

    private final String name;
    private final String symbol;
    private final int atomicNumber;
    private final int color;

    private final ElementStats elementStats;
    private final List<String> items;
    private final List<Integer> materialValues;

    private final List<String> enchantments;
    private final List<String> enchantmentTypes;
    private final List<Float> enchantmentFactors;

    public Element(Optional<String> nameIn, Optional<String> symbolIn, Optional<Integer> numIn, Optional<Integer> colorIn, List<String> items, List<Integer> values,
                         Optional<ElementStats> elementStatsIn,  Optional<List<String>> enchantmentsIn, Optional<List<String>> enchantmentTypesIn, Optional<List<Float>> enchantmentFactorsIn) {
        this.name = nameIn.orElse("element.unknown.name");
        this.symbol = nameIn.orElse("element.unknown.symbol");
        this.atomicNumber = numIn.orElse(-1);
        this.color = colorIn.orElse(16777215);
        this.items = items;
        this.materialValues = values;
        this.elementStats = elementStatsIn.orElse(new ElementStats());
        this.enchantments = enchantmentsIn.orElse(Collections.emptyList());
        this.enchantmentTypes = enchantmentTypesIn.orElse(Collections.emptyList());
        this.enchantmentFactors = enchantmentFactorsIn.orElse(Collections.emptyList());
    }

    public Element setRegistryName(ResourceLocation name) {
        this.location = name;
        return this;
    }

    @Nullable
    public ResourceLocation getRegistryName() {
        return location;
    }

    public int getAtomicNumber() {
        return atomicNumber;
    }

    public int getColor() {
        return color;
    }

    public List<String> getItems() {
        return items;
    }

    public List<Ingredient> getIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        for (String s : items) {
            if (s.contains("T#")) {
                TagKey<Item> tag = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(),new ResourceLocation(s.split("T#")[1]));
                ingredients.add(Ingredient.of(tag));
            } else if (s.contains("I#")) {
                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(s.split("I#")[1]));
                if (item != null) {
                    ingredients.add(Ingredient.of(() -> item));
                }
            }
        }
        return ingredients;
    }

    public List<Integer> getMaterialValues() {
        return materialValues;
    }

    /*public List<ElementEquation> getStats() {
        return stats;
    }*/

    public List<String> getEnchantments() {
        return enchantments;
    }

    public List<String> getEnchantmentTypes() {
        return enchantmentTypes;
    }

    public List<Float> getEnchantmentFactors() {
        return enchantmentFactors;
    }

    public String getLocalName() {
        return name;
    }

    public String getSymbol() {
       return symbol;
    }

    public ElementStats getElementStats() {
        return elementStats;
    }

    public boolean test(@Nullable ItemStack stack) {
        if (stack == null) {
            return false;
        } else {
            for(Ingredient i : this.getIngredients()) {
                if (i.test(stack)) {
                    return true;
                }
            }

            return false;
        }
    }

    public int getMaterialCount(ItemStack stack) {
        int index = 0;
        for(Ingredient i : this.getIngredients()) {
            if (i.test(stack)) {
                return getMaterialValues().get(index) * stack.getCount();
            }
            index++;
        }
        return 0;
    }
}
