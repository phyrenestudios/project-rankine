package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.potion.ConductiveEffect;
import com.cannolicatfish.rankine.potion.MercuryPoisonEffect;
import com.cannolicatfish.rankine.potion.RadiationPoisonEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RankineMobEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, ProjectRankine.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> MERCURY_POISONING = MOB_EFFECTS.register("mercury_poisoning", () -> new MercuryPoisonEffect(MobEffectCategory.HARMFUL,14800345)
            .addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "effect.mercury_poisoning_attack_speed"), (double)-0.1F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "effect.mercury_poisoning_attack_damage"), 0.0D, AttributeModifier.Operation.ADD_VALUE));

    public static final DeferredHolder<MobEffect, MobEffect> CONDUCTIVITY = MOB_EFFECTS.register("conductivity", () -> new ConductiveEffect(MobEffectCategory.HARMFUL,15001958));

    public static final DeferredHolder<MobEffect, MobEffect> RADIATION_POISONING = MOB_EFFECTS.register("radiation_poisoning", () -> new RadiationPoisonEffect(MobEffectCategory.HARMFUL,5478720)
            .addAttributeModifier(Attributes.MAX_HEALTH, ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "effect.radiation_poisoning_max_health"), -2D, AttributeModifier.Operation.ADD_VALUE));

}
