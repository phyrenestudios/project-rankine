package com.cannolicatfish.rankine.element;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Optional;

public class ElementStats {
    public static final Codec<ElementStats> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ElementEquation.CODEC.optionalFieldOf("durability").forGetter(l -> Optional.of(l.durability)),
                    ElementEquation.CODEC.optionalFieldOf("miningSpeed").forGetter(l -> Optional.of(l.miningSpeed)),
                    ElementEquation.CODEC.optionalFieldOf("miningTier").forGetter(l -> Optional.of(l.miningTier)),
                    ElementEquation.CODEC.optionalFieldOf("enchantability").forGetter(l -> Optional.of(l.enchantability)),
                    ElementEquation.CODEC.optionalFieldOf("attackSpeed").forGetter(l -> Optional.of(l.attackSpeed)),
                    ElementEquation.CODEC.optionalFieldOf("attackDamage").forGetter(l -> Optional.of(l.attackDamage)),
                    ElementEquation.CODEC.optionalFieldOf("heatResistance").forGetter(l -> Optional.of(l.heatResistance)),
                    ElementEquation.CODEC.optionalFieldOf("coldResistance").forGetter(l -> Optional.of(l.coldResistance)),
                    ElementEquation.CODEC.optionalFieldOf("knockbackResistance").forGetter(l -> Optional.of(l.knockbackResistance))
            ).apply(instance, ElementStats::new));


    private final ElementEquation durability;

    private final ElementEquation miningSpeed;

    private final ElementEquation miningTier;

    private final ElementEquation enchantability;

    private final ElementEquation attackDamage;

    private final ElementEquation attackSpeed;

    private final ElementEquation heatResistance;

    private final ElementEquation coldResistance;

    private final ElementEquation knockbackResistance;

    public ElementStats(Optional<ElementEquation> durabilityIn, Optional<ElementEquation> miningSpeedIn, Optional<ElementEquation> miningTierIn,
                   Optional<ElementEquation> enchantabilityIn, Optional<ElementEquation> attackSpeedIn, Optional<ElementEquation> attackDamageIn,
                   Optional<ElementEquation> heatResistanceIn, Optional<ElementEquation> coldResistanceIn, Optional<ElementEquation> knockbackResistanceIn) {
        this.durability = durabilityIn.orElse(new ElementEquation());
        this.miningSpeed = miningSpeedIn.orElse(new ElementEquation());
        this.miningTier = miningTierIn.orElse(new ElementEquation());
        this.enchantability = enchantabilityIn.orElse(new ElementEquation());
        this.attackSpeed = durabilityIn.orElse(new ElementEquation());
        this.attackDamage = attackDamageIn.orElse(new ElementEquation());
        this.heatResistance = heatResistanceIn.orElse(new ElementEquation());
        this.coldResistance = coldResistanceIn.orElse(new ElementEquation());
        this.knockbackResistance = knockbackResistanceIn.orElse(new ElementEquation());
    }

    public ElementStats() {
        this.durability = new ElementEquation();
        this.miningSpeed = new ElementEquation();
        this.miningTier = new ElementEquation();
        this.enchantability = new ElementEquation();
        this.attackSpeed = new ElementEquation();
        this.attackDamage = new ElementEquation();
        this.heatResistance = new ElementEquation();
        this.coldResistance = new ElementEquation();
        this.knockbackResistance = new ElementEquation();
    }

    public ElementEquation getDurabilityEquation() {
        return durability;
    }

    public ElementEquation getMiningSpeedEquation() {
        return miningSpeed;
    }

    public ElementEquation getMiningTierEquation() {
        return miningTier;
    }

    public ElementEquation getEnchantabilityEquation() {
        return enchantability;
    }

    public ElementEquation getAttackDamageEquation() {
        return attackDamage;
    }

    public ElementEquation getAttackSpeedEquation() {
        return attackSpeed;
    }

    public ElementEquation getHeatResistanceEquation() {
        return heatResistance;
    }

    public ElementEquation getColdResistanceEquation() {
        return coldResistance;
    }

    public ElementEquation getKnockbackResistanceEquation() {
        return knockbackResistance;
    }
}
