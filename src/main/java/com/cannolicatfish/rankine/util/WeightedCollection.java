package com.cannolicatfish.rankine.util;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class WeightedCollection<I> {
    private final NavigableMap<Float, I> map = new TreeMap<>();
    private final Random random;
    private float total = 0;

    public WeightedCollection() {
        this(new Random());
    }

    public WeightedCollection(Random random) {
        this.random = random;
    }

    public WeightedCollection<I> add(float weight, I result) {
        if (weight <= 0) {
            return this;
        }
        total += weight;
        map.put(total, result);
        return this;
    }

    public I getRandomElement() {
        float value = random.nextFloat() * total;
        return map.higherEntry(value).getValue();
    }
}
