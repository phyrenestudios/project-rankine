package com.cannolicatfish.rankine.element;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public final class Element {
    //private static final Codec<List<ResourceLocation>> RESOURCE_LOCATION_LIST_CODEC = ResourceLocation.CODEC.listOf();
    private static final Codec<List<Integer>> INTEGER_LIST_CODEC = Codec.INT.listOf();
    private static final Codec<List<Float>> FLOAT_LIST_CODEC = Codec.FLOAT.listOf();
    private static final Codec<List<String>> STRING_LIST_CODEC = Codec.STRING.listOf();

    //public static final Codec<List<ElementEquation>> ELEMENT_EQUATION_LIST_CODEC = ElementEquation.CODEC.listOf();
    public static final Codec<Element> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.optionalFieldOf("atomicNumber").forGetter(l -> Optional.of(l.atomicNumber)),
                    Codec.INT.optionalFieldOf("color").forGetter(l -> Optional.of(l.color)),
                    STRING_LIST_CODEC.fieldOf("items").forGetter(l -> l.items),
                    INTEGER_LIST_CODEC.fieldOf("materialValues").forGetter(l -> l.materialValues),
                    //ELEMENT_EQUATION_LIST_CODEC.optionalFieldOf("stats").forGetter(l -> Optional.of(l.stats)),
                    STRING_LIST_CODEC.fieldOf("enchantments").forGetter(l -> l.enchantments),
                    STRING_LIST_CODEC.fieldOf("enchantmentTypes").forGetter(l -> l.enchantmentTypes),
                    FLOAT_LIST_CODEC.fieldOf("enchantmentFactors").forGetter(l -> l.enchantmentFactors)
            ).apply(instance, Element::new));

    private ResourceLocation name;
    private final int atomicNumber;
    private final int color;
    private final List<String> items;
    private final List<Integer> materialValues;
    //private final List<ElementEquation> stats;
    private final List<String> enchantments;
    private final List<String> enchantmentTypes;
    private final List<Float> enchantmentFactors;

    public Element(Optional<Integer> numIn, Optional<Integer> colorIn, List<String> items, List<Integer> values,
                         List<String> enchantmentsIn, List<String> enchantmentTypesIn, List<Float> enchantmentFactorsIn) {
        this.atomicNumber = numIn.orElse(-1);
        this.color = colorIn.orElse(16777215);
        this.items = items;
        this.materialValues = values;
        //this.stats = statsIn.orElse(Collections.emptyList());
        this.enchantments = enchantmentsIn;
        this.enchantmentTypes = enchantmentTypesIn;
        this.enchantmentFactors = enchantmentFactorsIn;
    }

    public Element setRegistryName(ResourceLocation name) {
        this.name = name;
        return this;
    }

    @Nullable
    public ResourceLocation getRegistryName() {
        return name;
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
        return I18n.get("element."+getRegistryName().getNamespace().toLowerCase()+"."+getRegistryName().getPath().toLowerCase()+".name");
    }

    public String getSymbol() {
        return I18n.get("element."+getRegistryName().getNamespace().toLowerCase()+"."+getRegistryName().getPath().toLowerCase()+".symbol");
    }
}
