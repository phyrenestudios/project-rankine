package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

public class RankinePotions {

    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS.getRegistryName(), ProjectRankine.MODID);

    public static final RegistryObject<Potion> MERCURY_POISONING = POTIONS.register("mercury_poisoning_potion", () -> new Potion(new MobEffectInstance(RankineMobEffects.MERCURY_POISONING.get(),800)));
    public static final RegistryObject<Potion> CONDUCTIVITY = POTIONS.register("conductivity_potion", () -> new Potion(new MobEffectInstance(RankineMobEffects.CONDUCTIVITY.get(),500)));

}
