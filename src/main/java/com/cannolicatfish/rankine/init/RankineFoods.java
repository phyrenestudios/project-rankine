package com.cannolicatfish.rankine.init;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class RankineFoods {
    public static final Food TRAIL_MIX = (new Food.Builder()).hunger(5).saturation(1.0F).build();
    public static final Food TOAST = (new Food.Builder()).hunger(6).saturation(0.7F).build();
    public static final Food CINNAMON_TOAST = (new Food.Builder()).hunger(7).saturation(0.9F).build();
    public static final Food SIMPLE_FOOD = (new Food.Builder()).hunger(1).saturation(0.1F).build();
    public static final Food SNOWBERRIES = (new Food.Builder()).hunger(1).saturation(0.1F).effect(new EffectInstance(Effects.POISON, 3 * 20, 0),.25f).build();
    public static final Food POKEBERRIES = (new Food.Builder()).hunger(1).saturation(0.1F).effect(new EffectInstance(Effects.POISON, 10 * 20, 0),1.0f).build();
    public static final Food JAM = (new Food.Builder()).hunger(7).saturation(0.7F).build();
    public static final Food TOASTED_COCONUT = (new Food.Builder()).hunger(2).saturation(0.2F).build();
    public static final Food ROASTED_ASPARAGUS = (new Food.Builder()).hunger(2).saturation(0.2F).build();
    public static final Food POPCORN = (new Food.Builder()).hunger(2).saturation(0.2F).build();
    public static final Food JUNIPER_BERRIES = (new Food.Builder()).hunger(1).saturation(0.1F).effect(new EffectInstance(Effects.POISON, 10 * 20, 0),0.3f).build();
    public static final Food BLACK_WALNUT = (new Food.Builder()).hunger(1).saturation(0.0F).effect(new EffectInstance(Effects.POISON, 10 * 20, 0),1.0f).build();
    public static final Food ROASTED_WALNUT = (new Food.Builder()).hunger(1).saturation(0.2F).build();
    public static final Food ALOE = (new Food.Builder()).hunger(1).saturation(0.1F).effect(new EffectInstance(Effects.FIRE_RESISTANCE, 10 * 20, 0),1.0f).build();
    public static final Food PINEAPPLE = (new Food.Builder()).hunger(2).saturation(0.1F).build();
    public static final Food PINEAPPLE_SLEEVES = (new Food.Builder()).hunger(10).saturation(1F).build();
    public static final Food PINA_COLADA = (new Food.Builder()).hunger(4).saturation(1.5F).effect(new EffectInstance(Effects.SLOWNESS,7 * 20,0),0.5f).build();
    public static final Food MAPLE_SYRUP = (new Food.Builder()).hunger(1).saturation(0.5F).effect(new EffectInstance(Effects.SPEED,7 * 20,0),1.0f).build();
    public static final Food CHEESE = (new Food.Builder()).hunger(2).saturation(0.2F).build();
    public static final Food PANCAKE_BATTER = (new Food.Builder()).hunger(1).saturation(0.1F).effect(new EffectInstance(Effects.POISON, 5 * 20, 0),.1f).build();
    public static final Food PANCAKE = (new Food.Builder()).hunger(3).saturation(0.3F).build();
    public static final Food PANCAKE_BREAKFAST = (new Food.Builder()).hunger(12).saturation(1.2F).effect(new EffectInstance(Effects.SATURATION, 7, 0),1.0f).build();
    public static final Food TUNA = (new Food.Builder()).hunger(3).saturation(0.3F).build();
    public static final Food COOKED_TUNA = (new Food.Builder()).hunger(8).saturation(8F).build();
}
