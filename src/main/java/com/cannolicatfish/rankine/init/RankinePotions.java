package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RankinePotions {

    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, ProjectRankine.MODID);

    //public static final RegistryObject<Potion> MERCURY_POISONING = POTIONS.register("mercury_poisoning", () -> new Potion(new MobEffectInstance(RankineEffects.MERCURY_POISONING.get(),800)));
    //public static final RegistryObject<Potion> CONDUCTIVITY = POTIONS.register("conductivity", () -> new Potion(new MobEffectInstance(RankineEffects.CONDUCTIVITY.get(),500)));

    public static final Potion MERCURY_POISON = new Potion(new MobEffectInstance(RankineEffects.MERCURY_POISONING,800)).setRegistryName(ProjectRankine.MODID,"mercury_poison");
    public static final Potion CONDUCTIVE_POTION = new Potion(new MobEffectInstance(RankineEffects.CONDUCTIVE,500)).setRegistryName(ProjectRankine.MODID,"conductive_potion");

    //public static final Potion INSTANT_HEALTH_S = new Potion(new MobEffectInstance(MobEffects.HEAL,1)).setRegistryName(ProjectRankine.MODID,"healing_s");
}
