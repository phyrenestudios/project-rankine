package com.cannolicatfish.rankine.util.alloys;

public class NickelSuperalloyUtils implements AlloyUtils {
    @Override
    public String getDefComposition() {
        return "75Ni-15Cr-10Fe";
    }

    @Override
    public int getMiningLevelBonus(){
        return 1;
    }

    @Override
    public float getCorrResistBonus() {
        return 0.15f;
    }

    @Override
    public int getDurabilityBonus() {
        return 770;
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
