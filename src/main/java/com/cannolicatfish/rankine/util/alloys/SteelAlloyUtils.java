package com.cannolicatfish.rankine.util.alloys;

public class SteelAlloyUtils implements AlloyUtils{
    @Override
    public String getDefComposition() {
        return "99Fe-1C";
    }

    @Override
    public int getDurabilityBonus() {
        return 460;
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
}
