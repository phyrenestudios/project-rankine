package com.cannolicatfish.rankine.recipe.helper;

public class AlloyIngredientData {

    private final Float min;

    private final Float max;

    private final boolean isRequired;


    public AlloyIngredientData(Float minIn, Float maxIn, boolean isRequiredIn) {
        this.min = minIn;
        this.max = maxIn;
        this.isRequired = isRequiredIn;
    }

    public Float getMin() {
        return min;
    }

    public Float getMax() {
        return max;
    }

    public boolean isRequired() {
        return isRequired;
    }
}
