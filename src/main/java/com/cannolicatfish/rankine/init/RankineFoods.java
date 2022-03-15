package com.cannolicatfish.rankine.init;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class RankineFoods {
    public static final Food TRAIL_MIX = (new Food.Builder()).nutrition(5).saturationMod(1.0F).build();
    public static final Food TOFU = (new Food.Builder()).nutrition(4).saturationMod(0.4F).meat().build();
    public static final Food COOKED_TOFU = (new Food.Builder()).nutrition(6).saturationMod(0.6F).meat().build();
    public static final Food TOFU_CURRY = (new Food.Builder()).nutrition(10).saturationMod(0.6F).build();
    public static final Food TOAST = (new Food.Builder()).nutrition(6).saturationMod(0.7F).build();
    public static final Food CINNAMON_TOAST = (new Food.Builder()).nutrition(7).saturationMod(0.9F).build();
    public static final Food SIMPLE_FOOD = (new Food.Builder()).nutrition(1).saturationMod(0.1F).build();
    public static final Food SNOWBERRIES = (new Food.Builder()).nutrition(1).saturationMod(0.1F).effect(new EffectInstance(Effects.POISON, 3 * 20, 0),.25f).build();
    public static final Food POKEBERRIES = (new Food.Builder()).nutrition(1).saturationMod(0.1F).effect(new EffectInstance(Effects.POISON, 10 * 20, 0),1.0f).build();
    public static final Food JAM = (new Food.Builder()).nutrition(7).saturationMod(0.7F).build();
    public static final Food TOASTED_COCONUT = (new Food.Builder()).nutrition(2).saturationMod(0.2F).build();
    public static final Food ROASTED_ASPARAGUS = (new Food.Builder()).nutrition(2).saturationMod(0.2F).build();
    public static final Food POPCORN = (new Food.Builder()).nutrition(2).saturationMod(0.2F).build();
    public static final Food JUNIPER_BERRIES = (new Food.Builder()).nutrition(1).saturationMod(0.1F).effect(new EffectInstance(Effects.POISON, 10 * 20, 0),0.3f).build();
    public static final Food INNER_BARK = (new Food.Builder()).nutrition(1).saturationMod(0.1F).build();
    public static final Food BLACK_WALNUT = (new Food.Builder()).nutrition(1).saturationMod(0.0F).effect(new EffectInstance(Effects.POISON, 10 * 20, 0),1.0f).build();
    public static final Food ROASTED_WALNUT = (new Food.Builder()).nutrition(1).saturationMod(0.2F).build();
    public static final Food ALOE = (new Food.Builder()).nutrition(1).saturationMod(0.1F).effect(new EffectInstance(Effects.FIRE_RESISTANCE, 10 * 20, 0),1.0f).build();
    public static final Food PINEAPPLE = (new Food.Builder()).nutrition(2).saturationMod(0.1F).build();
    public static final Food PINEAPPLE_SLEEVES = (new Food.Builder()).nutrition(10).saturationMod(1F).build();
    public static final Food PINA_COLADA = (new Food.Builder()).nutrition(4).saturationMod(1.5F).effect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN,7 * 20,0),0.5f).build();
    public static final Food MAPLE_SYRUP = (new Food.Builder()).nutrition(1).saturationMod(0.5F).effect(new EffectInstance(Effects.MOVEMENT_SPEED,7 * 20,0),1.0f).build();
    public static final Food CHEESE = (new Food.Builder()).nutrition(2).saturationMod(0.2F).build();
    public static final Food PANCAKE_BATTER = (new Food.Builder()).nutrition(1).saturationMod(0.1F).effect(new EffectInstance(Effects.POISON, 5 * 20, 0),.1f).build();
    public static final Food PANCAKE = (new Food.Builder()).nutrition(3).saturationMod(0.3F).build();
    public static final Food PANCAKE_BREAKFAST = (new Food.Builder()).nutrition(12).saturationMod(1.2F).effect(new EffectInstance(Effects.SATURATION, 7, 0),1.0f).build();
    public static final Food TUNA = (new Food.Builder()).nutrition(3).saturationMod(0.3F).meat().build();
    public static final Food COOKED_TUNA = (new Food.Builder()).nutrition(8).saturationMod(8F).meat().build();
}
