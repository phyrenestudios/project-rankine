package com.cannolicatfish.rankine.items.alloys;

import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlloyProperties {
    private HashMap<Item, Double> WEIGHT_MIN = new HashMap<>();
    private HashMap<Item, Double> WEIGHT_MAX = new HashMap<>();
    private HashMap<Item, Integer> FILLET_AMOUNT = new HashMap<>();

    public void addWeight(Item fish, double min, double max) {
        if (!WEIGHT_MAX.containsKey(fish)) {
            WEIGHT_MIN.put(fish, min);
            WEIGHT_MAX.put(fish, max);
        }
    }

    public double getMinWeight(Item fish) {
        return WEIGHT_MIN.get(fish);
    }

    public double getMaxWeight(Item fish) {
        return WEIGHT_MAX.get(fish);
    }

    public boolean hasWeight(Item fish) {
        return WEIGHT_MIN.containsKey(fish) || WEIGHT_MAX.containsKey(fish);
    }

    public List<Item> getFish() {
        return new ArrayList<>(WEIGHT_MIN.keySet());
    }

    public void add(Item fish, double min, double max) {
        this.add(fish, min, max, 1);
    }

    public void add(Item fish, double min, double max, int filletAmount) {
        this.addWeight(fish, min, max);
        if (filletAmount > 0) {
            this.addFilletAmount(fish, filletAmount);
        }
    }

    public void addFilletAmount(Item fish, int filletAmount) {
        if (!FILLET_AMOUNT.containsKey(fish)) {
            FILLET_AMOUNT.put(fish, filletAmount);
        }
    }

    public boolean hasFilletAmount(Item fish) {
        return FILLET_AMOUNT.containsKey(fish);
    }

    public int getFilletAmount(Item fish) {
        return FILLET_AMOUNT.get(fish);
    }
}