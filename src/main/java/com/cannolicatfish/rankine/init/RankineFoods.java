package com.cannolicatfish.rankine.init;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class RankineFoods {
    public static final FoodProperties TRAIL_MIX = (new FoodProperties.Builder()).nutrition(5).saturationModifier(1.0F).build();
    // PORT-TODO(init): FoodProperties.Builder#meat() was removed in 1.21.1 (no direct replacement on FoodProperties).
    // The "is meat" flag previously affected things like wolf taming/breeding interactions; that behavior now lives
    // on item tags (e.g. vanilla wolf food checks) rather than FoodProperties. Preserved nutrition/saturation only.
    public static final FoodProperties TOFU = (new FoodProperties.Builder()).nutrition(4).saturationModifier(0.4F).build();
    public static final FoodProperties COOKED_TOFU = (new FoodProperties.Builder()).nutrition(6).saturationModifier(0.6F).build();
    public static final FoodProperties TOFU_CURRY = (new FoodProperties.Builder()).nutrition(10).saturationModifier(0.6F).build();
    public static final FoodProperties TOAST = (new FoodProperties.Builder()).nutrition(6).saturationModifier(0.7F).build();
    public static final FoodProperties CINNAMON_TOAST = (new FoodProperties.Builder()).nutrition(7).saturationModifier(0.9F).build();
    public static final FoodProperties SIMPLE_FOOD = (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.1F).build();
    public static final FoodProperties SNOWBERRIES = (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.1F).effect(() -> new MobEffectInstance(MobEffects.POISON, 3 * 20, 0),.25f).build();
    public static final FoodProperties POKEBERRIES = (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.1F).effect(() -> new MobEffectInstance(MobEffects.POISON, 10 * 20, 0),1.0f).build();
    public static final FoodProperties JAM = (new FoodProperties.Builder()).nutrition(7).saturationModifier(0.7F).build();
    public static final FoodProperties TOASTED_COCONUT = (new FoodProperties.Builder()).nutrition(2).saturationModifier(0.2F).build();
    public static final FoodProperties ROASTED_ASPARAGUS = (new FoodProperties.Builder()).nutrition(2).saturationModifier(0.2F).build();
    public static final FoodProperties POPCORN = (new FoodProperties.Builder()).nutrition(2).saturationModifier(0.2F).build();
    public static final FoodProperties JUNIPER_BERRIES = (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.1F).effect(() -> new MobEffectInstance(MobEffects.POISON, 10 * 20, 0),0.3f).build();
    public static final FoodProperties INNER_BARK = (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.1F).build();
    public static final FoodProperties BLACK_WALNUT = (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.0F).effect(() -> new MobEffectInstance(MobEffects.POISON, 10 * 20, 0),1.0f).build();
    public static final FoodProperties ROASTED_WALNUT = (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.2F).build();
    public static final FoodProperties ALOE = (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.1F).effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 10 * 20, 0),1.0f).build();
    public static final FoodProperties PINEAPPLE = (new FoodProperties.Builder()).nutrition(2).saturationModifier(0.1F).build();
    public static final FoodProperties PINEAPPLE_SLEEVES = (new FoodProperties.Builder()).nutrition(10).saturationModifier(1F).build();
    public static final FoodProperties PINA_COLADA = (new FoodProperties.Builder()).nutrition(4).saturationModifier(1.5F).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,7 * 20,0),0.5f).build();
    public static final FoodProperties MAPLE_SYRUP = (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.5F).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED,7 * 20,0),1.0f).build();
    public static final FoodProperties CHEESE = (new FoodProperties.Builder()).nutrition(2).saturationModifier(0.2F).build();
    public static final FoodProperties PANCAKE_BATTER = (new FoodProperties.Builder()).nutrition(1).saturationModifier(0.1F).effect(() -> new MobEffectInstance(MobEffects.POISON, 5 * 20, 0),.1f).build();
    public static final FoodProperties PANCAKE = (new FoodProperties.Builder()).nutrition(3).saturationModifier(0.3F).build();
    public static final FoodProperties PANCAKE_BREAKFAST = (new FoodProperties.Builder()).nutrition(12).saturationModifier(1.2F).effect(() -> new MobEffectInstance(MobEffects.SATURATION, 7, 0),1.0f).build();
    public static final FoodProperties TUNA = (new FoodProperties.Builder()).nutrition(3).saturationModifier(0.3F).build();
    public static final FoodProperties COOKED_TUNA = (new FoodProperties.Builder()).nutrition(8).saturationModifier(8F).build();
}
