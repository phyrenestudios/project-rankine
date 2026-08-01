package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RankinePotions {

    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, ProjectRankine.MODID);

    public static final DeferredHolder<Potion, Potion> MERCURY_POISONING = POTIONS.register("mercury_poisoning_potion", () -> new Potion(new MobEffectInstance(RankineMobEffects.MERCURY_POISONING,800)));
    public static final DeferredHolder<Potion, Potion> CONDUCTIVITY = POTIONS.register("conductivity_potion", () -> new Potion(new MobEffectInstance(RankineMobEffects.CONDUCTIVITY,500)));

}
