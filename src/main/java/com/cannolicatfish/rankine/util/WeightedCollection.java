package com.cannolicatfish.rankine.util;



import java.util.*;

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

    public NavigableMap<Float, I> getMap()
    {
        return map;
    }

    public Collection<I> getEntries()
    {
        return map.values();
    }


    public Set<Float> getWeights()
    {
        return map.keySet();
    }

    public List<Float> returnConvertedWeights()
    {
        List<Float> weights = new ArrayList<>(getWeights());
        float sum = 0;
        for (float i: weights) {
            sum += i;
        }
        List<Float> ret = new ArrayList<>();
        for (float i: weights)
        {
            ret.add((float) (Math.round(i/sum) * 100));
        }
        return ret;
    }
}
