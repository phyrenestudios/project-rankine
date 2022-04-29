package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.potion.ConductiveEffect;
import com.cannolicatfish.rankine.potion.MercuryPoisonEffect;
import com.cannolicatfish.rankine.potion.RadiationPoisonEffect;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class RankineEffects {

    public static Effect MERCURY_POISONING = new MercuryPoisonEffect(EffectType.HARMFUL,14800345).addAttributesModifier(Attributes.ATTACK_SPEED, "55FCED67-E92A-486E-9800-B47F202C4386", (double)-0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributesModifier(Attributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.0D, AttributeModifier.Operation.ADDITION).setRegistryName(ProjectRankine.MODID,"mercury_poison");

    public static Effect CONDUCTIVE = new ConductiveEffect(EffectType.HARMFUL,15001958).setRegistryName(ProjectRankine.MODID,"conductive");

    public static Effect RADIATION_POISONING = new RadiationPoisonEffect(EffectType.HARMFUL,5478720).addAttributesModifier(Attributes.MAX_HEALTH, "3c4a1c57-ed5a-482e-946e-eb0b00fe9baa", -2D, AttributeModifier.Operation.ADDITION).setRegistryName(ProjectRankine.MODID,"radiation_poison");
}
