package com.cannolicatfish.rankine.element;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Collections;
import java.util.List;

public class SimpleElementEquation {
    private static final Codec<List<Integer>> INTEGER_LIST_CODEC = Codec.INT.listOf();
    private static final Codec<List<String>> STRING_LIST_CODEC = Codec.STRING.listOf();

    List<Integer> breaks;
    List<String> values;

    public static final Codec<SimpleElementEquation> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(INTEGER_LIST_CODEC.fieldOf("breaks").forGetter(l -> l.breaks),
                    STRING_LIST_CODEC.fieldOf("values").forGetter(l -> l.values)).apply(instance, SimpleElementEquation::new));

    public SimpleElementEquation(List<Integer> breaksIn, List<String> valuesIn) {
        this.breaks = breaksIn;
        this.values = valuesIn;
    }

    public SimpleElementEquation() {
        this(Collections.emptyList(),Collections.emptyList());
    }

    public String getValue(int x) {
        int index = 0;
        for (int br : breaks) {
            if (x <= br) {
                return values.get(index);
            }
            index += 1;
        }
        return "";
    }


}
