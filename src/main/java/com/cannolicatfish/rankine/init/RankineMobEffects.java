package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.potion.ConductiveEffect;
import com.cannolicatfish.rankine.potion.MercuryPoisonEffect;
import com.cannolicatfish.rankine.potion.RadiationPoisonEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RankineMobEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS.getRegistryName(), ProjectRankine.MODID);

    public static final RegistryObject<MobEffect> MERCURY_POISONING = MOB_EFFECTS.register("mercury_poisoning", () -> new MercuryPoisonEffect(MobEffectCategory.HARMFUL,14800345)
            .addAttributeModifier(Attributes.ATTACK_SPEED, "55FCED67-E92A-486E-9800-B47F202C4386", (double)-0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.0D, AttributeModifier.Operation.ADDITION));

    public static final RegistryObject<MobEffect> CONDUCTIVITY = MOB_EFFECTS.register("conductivity", () -> new ConductiveEffect(MobEffectCategory.HARMFUL,15001958));

    public static final RegistryObject<MobEffect> RADIATION_POISONING = MOB_EFFECTS.register("radiation_poisoning", () -> new RadiationPoisonEffect(MobEffectCategory.HARMFUL,5478720)
            .addAttributeModifier(Attributes.MAX_HEALTH, "3c4a1c57-ed5a-482e-946e-eb0b00fe9baa", -2D, AttributeModifier.Operation.ADDITION));

}
