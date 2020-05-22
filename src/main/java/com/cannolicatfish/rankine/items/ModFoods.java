package com.cannolicatfish.rankine.items;

import net.minecraft.item.Food;
import net.minecraft.item.Foods;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class ModFoods {
    public static final Food ELDERBERRIES = (new Food.Builder()).hunger(2).saturation(0.1F).build();
    public static final Food SNOWBERRIES = (new Food.Builder()).hunger(2).saturation(0.1F).effect(new EffectInstance(Effects.POISON, 5 * 20, 0),.25f).build();
    public static final Food YUCCA = (new Food.Builder()).hunger(2).saturation(0.1F).build();
    public static final Food COCONUT = (new Food.Builder()).hunger(2).saturation(0.1F).build();
    public static final Food PINEAPPLE = (new Food.Builder()).hunger(2).saturation(0.1F).build();
    public static final Food PINA_COLADA = (new Food.Builder()).hunger(4).saturation(0.5F).effect(new EffectInstance(Effects.SLOWNESS,5,0),0.5f).build();

}
