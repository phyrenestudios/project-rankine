package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.potion.RankineEffects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum GasUtilsEnum {
    HYDROGEN(0.1f,0, Collections.singletonList(new EffectInstance(Effects.MINING_FATIGUE,25)),true,16767483),
    HELIUM(0.15f,0,Collections.singletonList(new EffectInstance(Effects.JUMP_BOOST,25)),true,16750204),
    NITROGEN(0.97f,79,Collections.singletonList(new EffectInstance(Effects.NAUSEA,25)),true,14459799),
    OXYGEN(1.1f,0,Collections.emptyList(),false,8585197),
    FLUORINE(1.6f,0,Collections.singletonList(new EffectInstance(Effects.POISON,25)),true,15983239),
    NEON(0.9f,0,Collections.singletonList(new EffectInstance(Effects.NIGHT_VISION,25)),true,16763457),
    CHLORINE(2.5f,0,Collections.singletonList(new EffectInstance(Effects.BLINDNESS,25)),true,15925122),
    ARGON(1.4f,0,Collections.singletonList(new EffectInstance(Effects.HASTE,25)),true,12354047),
    KRYPTON(2.8f,0,Collections.singletonList(new EffectInstance(Effects.GLOWING,25)),true,12568788),
    XENON(4.5f,0,Collections.singletonList(new EffectInstance(Effects.RESISTANCE,25)),true,8299263),
    RADON(7.5f,0, Arrays.asList(new EffectInstance(RankineEffects.RADIATION_POISONING,25),new EffectInstance(Effects.POISON,25)),true,16743053),
    OGANESSON(10f,0,Collections.singletonList(new EffectInstance(Effects.SLOW_FALLING,25)),true,10328228),
    AMMONIA(0.73f,0,Collections.singletonList(new EffectInstance(Effects.NAUSEA,25)),true,14459799),
    CARBON_DIOXIDE(2f,1, Collections.singletonList(new EffectInstance(Effects.MINING_FATIGUE,25)),true,3684408),
    HYDROGEN_CHLORIDE(1.5f,1, Collections.emptyList(),true,10345635),
    HYDROGEN_FLUORIDE(1.15f,1, Collections.emptyList(),true,10345635),
    HYDROGEN_SULFIDE(1.35f,0, Collections.emptyList(),true,13676874),
    SULFUR_DIOXIDE(2.9f,0, Collections.emptyList(),true,6118148);

    private final float density;
    private final int dissipationRate;
    private final List<EffectInstance> effects;
    private final boolean suffocation;
    private final int color;
    GasUtilsEnum(float density, int dissipationRate, List<EffectInstance> effects, boolean suffocation, int color)
    {
        this.density = density;
        this.dissipationRate = dissipationRate;
        this.effects = effects;
        this.suffocation = suffocation;
        this.color = color;
    }

    public boolean isSuffocating() {
        return this.suffocation;
    }

    public float getDensity() {
        return density;
    }

    public int getDissipationRate() {
        return dissipationRate;
    }

    public List<EffectInstance> getEffects() {
        return effects;
    }

    public int getColor() {
        return color;
    }

}
