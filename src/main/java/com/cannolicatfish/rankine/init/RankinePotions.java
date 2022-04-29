package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.potion.*;

public class RankinePotions {
    public static final Potion MERCURY_POISON = new Potion(new EffectInstance(RankineEffects.MERCURY_POISONING,800)).setRegistryName(ProjectRankine.MODID,"mercury_poison");
    public static final Potion CONDUCTIVE_POTION = new Potion(new EffectInstance(RankineEffects.CONDUCTIVE,500)).setRegistryName(ProjectRankine.MODID,"conductive_potion");

    public static final Potion MINOR_REGENERATION_POTION = new Potion(new EffectInstance(Effects.REGENERATION,450)).setRegistryName(ProjectRankine.MODID,"minor_regeneration_potion");
}
