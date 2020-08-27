package com.cannolicatfish.rankine.util.alloys;

public class MeteoricIronAlloyUtils implements AlloyUtils {
    @Override
    public String getDefComposition() {
        return "90Fe-10Ni";
    }

    @Override
    public float getMiningSpeedBonus() {
        return 1;
    }

    @Override
    public float getAttackDamageBonus() {
        return 2;
    }
}
