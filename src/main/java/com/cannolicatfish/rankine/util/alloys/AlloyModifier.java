package com.cannolicatfish.rankine.util.alloys;

public class AlloyModifier {
    private String name;
    private ModifierType type;
    private float value;
    private boolean isAdditive;

    public AlloyModifier(String name, ModifierType type, float value, boolean additive) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.isAdditive = additive;
    }

    public String getName() {
        return this.name;
    }

    public ModifierType getType() {
        return this.type;
    }

    public float getValue() {
        return this.value;
    }

    public float returnModification(float original) {
        if (this.isAdditive) {
            return original + this.getValue();
        } else {
            return original * this.getValue();
        }
    }

    public enum ModifierType {
        DURABILITY,
        ENCHANTABILITY,
        MINING_SPEED,
        HARVEST_LEVEL,
        ATTACK_DAMAGE,
        ATTACK_SPEED,
        CORROSION_RESISTANCE,
        HEAT_RESISTANCE,
        KNOCKBACK_RESISTANCE,
        TOUGHNESS
    }
}
