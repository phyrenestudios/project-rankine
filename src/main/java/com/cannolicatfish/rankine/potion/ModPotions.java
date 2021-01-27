package com.cannolicatfish.rankine.potion;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.potion.*;

public class ModPotions {
    public static final Potion MERCURY_POISON = new Potion(new EffectInstance(ModEffects.MERCURY_POISONING,800)).setRegistryName(ProjectRankine.MODID,"mercury_poison");

}
