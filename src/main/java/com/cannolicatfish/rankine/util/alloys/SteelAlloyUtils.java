package com.cannolicatfish.rankine.util.alloys;

public class SteelAlloyUtils implements AlloyUtils{
    @Override
    public String getDefComposition() {
        return "98Fe-2C";
    }

    @Override
    public int getDurabilityBonus() {
        return 500;
    }

    @Override
    public int getMiningLevelBonus(){
        return 1;
    }

    @Override
    public float getMiningSpeedBonus() {
        return 4;
    }
}
