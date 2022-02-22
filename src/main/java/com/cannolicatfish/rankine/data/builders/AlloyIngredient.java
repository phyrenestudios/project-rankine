package com.cannolicatfish.rankine.data.builders;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class AlloyIngredient {
    private Ingredient ingredient;
    private String compositonReqs;
    private ResourceLocation alloyRecipe;
    private String langName;
    private int color;
    public AlloyIngredient(Ingredient ingredientIn, String compositionReqsIn, ResourceLocation alloyRecipeIn,String langNameIn,int colorIn) {
        this.ingredient = ingredientIn;
        this.compositonReqs = compositionReqsIn;
        this.alloyRecipe = alloyRecipeIn;
        this.langName = langNameIn;
        this.color = colorIn;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public String getCompositonReqs() {
        return compositonReqs;
    }

    public ResourceLocation getAlloyRecipe() {
        return alloyRecipe;
    }

    public String getLangName() {
        return langName;
    }

    public int getColor() {
        return color;
    }
}
