package com.cannolicatfish.rankine.util.alloys;

public class StainlessSteelAlloyUtils implements AlloyUtils {
    @Override
    public String getDefComposition() {
        return "75Fe-13Cr-10Ni-2C";
    }

    @Override
    public int getDurabilityBonus() {
        return 660;
    }

    @Override
    public int getMiningLevelBonus(){
        return 1;
    }

    @Override
    public float getMiningSpeedBonus() {
        return 4;
    }

    @Override
    public float getAttackDamageBonus() {
        return 3;
    }

    @Override
    public float getToughnessBonus() {
        return 0.25f;
    }
}
