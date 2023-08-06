package com.cannolicatfish.rankine.element;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Optional;

public class ElementStats {
    public static final Codec<ElementStats> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ElementEquation.CODEC.optionalFieldOf("durability").forGetter(l -> Optional.of(l.durability)),
                    ElementEquation.CODEC.optionalFieldOf("miningSpeed").forGetter(l -> Optional.of(l.miningSpeed)),
                    SimpleElementEquation.CODEC.optionalFieldOf("miningTier").forGetter(l -> Optional.of(l.miningTier)),
                    ElementEquation.CODEC.optionalFieldOf("enchantability").forGetter(l -> Optional.of(l.enchantability)),
                    ElementEquation.CODEC.optionalFieldOf("density").forGetter(l -> Optional.of(l.density)),
                    ElementEquation.CODEC.optionalFieldOf("attackDamage").forGetter(l -> Optional.of(l.attackDamage))
            ).apply(instance, ElementStats::new));


    private final ElementEquation durability;

    private final ElementEquation miningSpeed;

    private final SimpleElementEquation miningTier;

    private final ElementEquation enchantability;

    private final ElementEquation attackDamage;

    private final ElementEquation density;

    public ElementStats(Optional<ElementEquation> durabilityIn, Optional<ElementEquation> miningSpeedIn, Optional<SimpleElementEquation> miningTierIn,
                   Optional<ElementEquation> enchantabilityIn, Optional<ElementEquation> densityIn, Optional<ElementEquation> attackDamageIn) {
        this.durability = durabilityIn.orElse(new ElementEquation());
        this.miningSpeed = miningSpeedIn.orElse(new ElementEquation());
        this.enchantability = enchantabilityIn.orElse(new ElementEquation());
        this.density = durabilityIn.orElse(new ElementEquation());
        this.attackDamage = attackDamageIn.orElse(new ElementEquation());

        this.miningTier = miningTierIn.orElse(new SimpleElementEquation());
    }

    public ElementStats() {
        this.durability = new ElementEquation();
        this.miningSpeed = new ElementEquation();
        this.miningTier = new SimpleElementEquation();
        this.enchantability = new ElementEquation();
        this.density = new ElementEquation();
        this.attackDamage = new ElementEquation();
    }

    public ElementEquation getDurabilityEquation() {
        return durability;
    }

    public ElementEquation getMiningSpeedEquation() {
        return miningSpeed;
    }

    public SimpleElementEquation getMiningTierEquation() {
        return miningTier;
    }

    public ElementEquation getEnchantabilityEquation() {
        return enchantability;
    }

    public ElementEquation getAttackDamageEquation() {
        return attackDamage;
    }

    public ElementEquation getAttackSpeedEquation() {
        return density;
    }
}
