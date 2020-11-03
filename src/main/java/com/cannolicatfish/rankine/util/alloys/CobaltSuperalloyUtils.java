package com.cannolicatfish.rankine.util.alloys;

public class CobaltSuperalloyUtils implements AlloyUtils {
    @Override
    public String getDefComposition() {
        return "70Co-20Cr-10Ni";
    }

    @Override
    public int getMiningLevelBonus(){
        return 1;
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
        return 0.3f;
    }
}
