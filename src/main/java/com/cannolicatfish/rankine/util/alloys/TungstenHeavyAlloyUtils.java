package com.cannolicatfish.rankine.util.alloys;

public class TungstenHeavyAlloyUtils implements AlloyUtils {
    @Override
    public String getDefComposition() {
        return "90W-7Ni-3Fe";
    }

    @Override
    public int getMiningLevelBonus() {
        return 1;
    }

    @Override
    public int getDurabilityBonus() {
        return 1070;
    }

    @Override
    public float getMiningSpeedBonus() {
        return 3.5f;
    }

    @Override
    public int getEnchantabilityBonus() {
        return 3;
    }

    @Override
    public float getAttackDamageBonus() {
        return 4;
    }

    @Override
    public float getToughnessBonus() {
        return 0.15f;
    }
}
