package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.potion.RankineEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum GasUtilsEnum {
    HYDROGEN(0.1f,0.7f, Collections.singletonList(new MobEffectInstance(MobEffects.DIG_SLOWDOWN,25)),true,16767483),
    HELIUM(0.15f,0.5f,Collections.singletonList(new MobEffectInstance(MobEffects.JUMP,25)),true,16750204),
    NITROGEN(0.97f,0.19f,Collections.singletonList(new MobEffectInstance(MobEffects.CONFUSION,25)),true,14459799),
    OXYGEN(1.1f,0.18f,Collections.emptyList(),false,8585197),
    FLUORINE(1.6f,0.16f,Collections.singletonList(new MobEffectInstance(MobEffects.POISON,25)),true,15983239),
    NEON(0.9f,0.23f,Collections.singletonList(new MobEffectInstance(MobEffects.NIGHT_VISION,25)),true,16763457),
    CHLORINE(2.5f,0.12f,Collections.singletonList(new MobEffectInstance(MobEffects.BLINDNESS,25)),true,15925122),
    ARGON(1.4f,0.16f,Collections.singletonList(new MobEffectInstance(MobEffects.DIG_SPEED,25)),true,12354047),
    KRYPTON(2.8f,0.11f,Collections.singletonList(new MobEffectInstance(MobEffects.GLOWING,25)),true,12568788),
    XENON(4.5f,0.09f,Collections.singletonList(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,25)),true,8299263),
    RADON(7.5f,0.07f, Arrays.asList(new MobEffectInstance(RankineEffects.RADIATION_POISONING,25),new MobEffectInstance(MobEffects.POISON,25)),true,16743053),
    OGANESSON(10f,0.06f,Collections.singletonList(new MobEffectInstance(MobEffects.SLOW_FALLING,25)),true,10328228),
    AMMONIA(0.73f,0.24f,Collections.singletonList(new MobEffectInstance(MobEffects.CONFUSION,25)),true,14459799),
    CARBON_DIOXIDE(2f,0.15f, Collections.singletonList(new MobEffectInstance(MobEffects.DIG_SLOWDOWN,25)),true,3684408),
    HYDROGEN_CHLORIDE(1.5f,0.17f, Collections.emptyList(),true,10345635),
    HYDROGEN_FLUORIDE(1.15f,0.22f, Collections.emptyList(),true,10345635),
    HYDROGEN_SULFIDE(1.35f,0.17f, Collections.emptyList(),true,13676874),
    SULFUR_DIOXIDE(2.9f,0.13f, Collections.emptyList(),true,6118148),
    TUNGSTEN_HEXAFLUORIDE(2.4f,0.13f, Collections.emptyList(),true,11048836);


    private final float density;
    private final float dissipationRate;
    private final List<MobEffectInstance> effects;
    private final boolean suffocation;
    private final int color;
    GasUtilsEnum(float density, float dissipationRate, List<MobEffectInstance> effects, boolean suffocation, int color)
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

    public float getDissipationRate() {
        return dissipationRate;
    }

    public List<MobEffectInstance> getEffects() {
        return effects;
    }

    public int getColor() {
        return color;
    }

}
