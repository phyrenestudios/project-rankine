package com.cannolicatfish.rankine.potion;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class RankineEffects {

    public static MobEffect MERCURY_POISONING = new MercuryPoisonEffect(MobEffectCategory.HARMFUL,14800345).addAttributeModifier(Attributes.ATTACK_SPEED, "55FCED67-E92A-486E-9800-B47F202C4386", (double)-0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.0D, AttributeModifier.Operation.ADDITION).setRegistryName(ProjectRankine.MODID,"mercury_poison");

    public static MobEffect CONDUCTIVE = new ConductiveEffect(MobEffectCategory.HARMFUL,15001958).setRegistryName(ProjectRankine.MODID,"conductive");

    public static MobEffect RADIATION_POISONING = new RadiationPoisonEffect(MobEffectCategory.HARMFUL,5478720).addAttributeModifier(Attributes.MAX_HEALTH, "3c4a1c57-ed5a-482e-946e-eb0b00fe9baa", -2D, AttributeModifier.Operation.ADDITION).setRegistryName(ProjectRankine.MODID,"radiation_poison");
}
