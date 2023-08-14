package com.cannolicatfish.rankine.util.math;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class ValueEquation {

    public static final Codec<ValueEquation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.SHORT.fieldOf("stopAt").forGetter(l -> l.endPercentage),
            Codec.STRING.fieldOf("value").forGetter(l -> l.value)
    ).apply(instance, ValueEquation::new));

    private final Short endPercentage;
    private final String value;
    public ValueEquation(Short endPercentageIn, String valueIn) {
        this.endPercentage = endPercentageIn;
        this.value = valueIn;
    }

    public Short getEndPercentage() {
        return endPercentage;
    }

    public String getValue() {
        return value;
    }
}
