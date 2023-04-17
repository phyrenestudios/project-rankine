package com.cannolicatfish.rankine.util;

import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;

import java.util.*;
import java.util.stream.Collectors;

public class WeightedRemovableCollection<I> {
    private final NavigableMap<Float, Tuple<I,Boolean>> map = new TreeMap<>();
    private final RandomSource random;
    private float total = 0;

    public WeightedRemovableCollection(RandomSource random) {
        this.random = random;
    }

    public WeightedRemovableCollection(RandomSource random, List<Float> weights, List<I> items, List<Integer> totals, List<Boolean> removes) {
        this(random);
        for (int i = 0; i < items.size(); i++) {
            for (int j = 0; j < totals.get(i); j++) {
                this.add(weights.get(i),items.get(i),removes.get(i));
            }
        }
    }

    public WeightedRemovableCollection<I> add(float weight, I result, boolean remove) {
        if (weight <= 0) {
            return this;
        }
        total += weight;
        map.put(total, new Tuple<>(result,remove));
        return this;
    }


    public I getRandomElement() {
        Tuple<I,Boolean> output;

        if (map.size() == 1) {
            output = map.firstEntry().getValue();
            if (output.getB()) {
                map.remove(map.firstEntry().getKey());
            }

            return output.getA();
        }

        float value = random.nextFloat() * total;
        if (map.higherEntry(value) != null) {
            output = map.higherEntry(value).getValue();
            if (output.getB()) {
                map.remove(map.higherEntry(value).getKey());
            }
        } else if (map.firstEntry() != null) {
            output = map.firstEntry().getValue();
        } else {
            output = null;
        }




        return output.getA();
    }

    public NavigableMap<Float, Tuple<I,Boolean>> getMap()
    {
        return map;
    }

    public Collection<I> getEntries()
    {
        return this.getMap().values().stream().map(Tuple::getA).collect(Collectors.toList());
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
