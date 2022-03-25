package com.cannolicatfish.rankine.init;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class RankineFoods {
    public static final FoodProperties TRAIL_MIX = (new FoodProperties.Builder()).nutrition(5).saturationMod(1.0F).build();
    public static final FoodProperties TOFU = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.4F).meat().build();
    public static final FoodProperties COOKED_TOFU = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.6F).meat().build();
    public static final FoodProperties TOFU_CURRY = (new FoodProperties.Builder()).nutrition(10).saturationMod(0.6F).build();
    public static final FoodProperties TOAST = (new FoodProperties.Builder()).nutrition(6).saturationMod(0.7F).build();
    public static final FoodProperties CINNAMON_TOAST = (new FoodProperties.Builder()).nutrition(7).saturationMod(0.9F).build();
    public static final FoodProperties SIMPLE_FOOD = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.1F).build();
    public static final FoodProperties SNOWBERRIES = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.1F).effect(new MobEffectInstance(MobEffects.POISON, 3 * 20, 0),.25f).build();
    public static final FoodProperties POKEBERRIES = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.1F).effect(new MobEffectInstance(MobEffects.POISON, 10 * 20, 0),1.0f).build();
    public static final FoodProperties JAM = (new FoodProperties.Builder()).nutrition(7).saturationMod(0.7F).build();
    public static final FoodProperties TOASTED_COCONUT = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.2F).build();
    public static final FoodProperties ROASTED_ASPARAGUS = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.2F).build();
    public static final FoodProperties POPCORN = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.2F).build();
    public static final FoodProperties JUNIPER_BERRIES = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.1F).effect(new MobEffectInstance(MobEffects.POISON, 10 * 20, 0),0.3f).build();
    public static final FoodProperties INNER_BARK = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.1F).build();
    public static final FoodProperties BLACK_WALNUT = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.0F).effect(new MobEffectInstance(MobEffects.POISON, 10 * 20, 0),1.0f).build();
    public static final FoodProperties ROASTED_WALNUT = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.2F).build();
    public static final FoodProperties ALOE = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.1F).effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 10 * 20, 0),1.0f).build();
    public static final FoodProperties PINEAPPLE = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.1F).build();
    public static final FoodProperties PINEAPPLE_SLEEVES = (new FoodProperties.Builder()).nutrition(10).saturationMod(1F).build();
    public static final FoodProperties PINA_COLADA = (new FoodProperties.Builder()).nutrition(4).saturationMod(1.5F).effect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,7 * 20,0),0.5f).build();
    public static final FoodProperties MAPLE_SYRUP = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.5F).effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,7 * 20,0),1.0f).build();
    public static final FoodProperties CHEESE = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.2F).build();
    public static final FoodProperties PANCAKE_BATTER = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.1F).effect(new MobEffectInstance(MobEffects.POISON, 5 * 20, 0),.1f).build();
    public static final FoodProperties PANCAKE = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.3F).build();
    public static final FoodProperties PANCAKE_BREAKFAST = (new FoodProperties.Builder()).nutrition(12).saturationMod(1.2F).effect(new MobEffectInstance(MobEffects.SATURATION, 7, 0),1.0f).build();
    public static final FoodProperties TUNA = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.3F).meat().build();
    public static final FoodProperties COOKED_TUNA = (new FoodProperties.Builder()).nutrition(8).saturationMod(8F).meat().build();
}
