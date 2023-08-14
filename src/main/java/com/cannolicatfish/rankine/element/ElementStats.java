package com.cannolicatfish.rankine.element;

import com.cannolicatfish.rankine.util.math.MathEquation;
import com.cannolicatfish.rankine.util.math.ValueEquation;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.Tiers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ElementStats {

    private static final Codec<List<MathEquation>> MATH_EQUATION_LIST_CODEC = MathEquation.CODEC.listOf();
    private static final Codec<List<ValueEquation>> VALUE_EQUATION_LIST_CODEC = ValueEquation.CODEC.listOf();


    public static final Codec<ElementStats> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    MATH_EQUATION_LIST_CODEC.optionalFieldOf("durability").forGetter(l -> Optional.of(l.durability)),
                    MATH_EQUATION_LIST_CODEC.optionalFieldOf("miningSpeed").forGetter(l -> Optional.of(l.miningSpeed)),
                    VALUE_EQUATION_LIST_CODEC.optionalFieldOf("miningTier").forGetter(l -> Optional.of(l.miningTier)),
                    MATH_EQUATION_LIST_CODEC.optionalFieldOf("enchantability").forGetter(l -> Optional.of(l.enchantability)),
                    MATH_EQUATION_LIST_CODEC.optionalFieldOf("density").forGetter(l -> Optional.of(l.density)),
                    MATH_EQUATION_LIST_CODEC.optionalFieldOf("attackDamage").forGetter(l -> Optional.of(l.attackDamage))
            ).apply(instance, ElementStats::new));


    private final List<MathEquation> durability;

    private final List<MathEquation> miningSpeed;

    private final List<ValueEquation> miningTier;

    private final List<MathEquation> enchantability;

    private final List<MathEquation> attackDamage;

    private final List<MathEquation> density;

    public ElementStats(Optional<List<MathEquation>> durabilityIn, Optional<List<MathEquation>> miningSpeedIn, Optional<List<ValueEquation>> miningTierIn,
                   Optional<List<MathEquation>> enchantabilityIn, Optional<List<MathEquation>> densityIn, Optional<List<MathEquation>> attackDamageIn) {
        this.durability = durabilityIn.orElse(new ArrayList<>());
        this.miningSpeed = miningSpeedIn.orElse(new ArrayList<>());
        this.enchantability = enchantabilityIn.orElse(new ArrayList<>());
        this.density = durabilityIn.orElse(new ArrayList<>());
        this.attackDamage = attackDamageIn.orElse(new ArrayList<>());

        this.miningTier = miningTierIn.orElse(new ArrayList<>());
    }

    public ElementStats() {
        this.durability = new ArrayList<>();
        this.miningSpeed = new ArrayList<>();
        this.miningTier = new ArrayList<>();
        this.enchantability = new ArrayList<>();
        this.density = new ArrayList<>();
        this.attackDamage = new ArrayList<>();
    }

    public List<MathEquation> getDurabilityEquation() {
        return durability;
    }

    public int getDurability(short percent) {
        for (MathEquation equation : getDurabilityEquation()) {
            if (percent <= equation.getEndPercentage()) {
                return (int) equation.calculateValue(percent);
            }
        }
        return 0;
    }

    public List<MathEquation> getMiningSpeedEquation() {
        return miningSpeed;
    }

    public float getMiningSpeed(short percent) {
        for (MathEquation equation : getMiningSpeedEquation()) {
            if (percent <= equation.getEndPercentage()) {
                return equation.calculateValue(percent);
            }
        }
        return 0;
    }

    public List<ValueEquation> getMiningTierEquation() {
        return miningTier;
    }

    public String getMiningTier(short percent) {
        for (ValueEquation equation : getMiningTierEquation()) {
            if (percent <= equation.getEndPercentage()) {
                return equation.getValue();
            }
        }
        return Tiers.WOOD.toString();
    }



    public List<MathEquation> getEnchantabilityEquation() {
        return enchantability;
    }

    public int getEnchantability(short percent) {
        for (MathEquation equation : getEnchantabilityEquation()) {
            if (percent <= equation.getEndPercentage()) {
                return (int) equation.calculateValue(percent);
            }
        }
        return 0;
    }

    public List<MathEquation> getAttackDamageEquation() {
        return attackDamage;
    }

    public float getAttackDamage(short percent) {
        for (MathEquation equation : getAttackDamageEquation()) {
            if (percent <= equation.getEndPercentage()) {
                return equation.calculateValue(percent);
            }
        }
        return 0;
    }

    public List<MathEquation> getDensityEquation() {
        return density;
    }

    public float getDensity(short percent) {
        for (MathEquation equation : getDensityEquation()) {
            if (percent <= equation.getEndPercentage()) {
                return equation.calculateValue(percent);
            }
        }
        return 0;
    }
}
