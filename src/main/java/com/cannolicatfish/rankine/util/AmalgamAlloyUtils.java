package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.items.tools.RankineToolMaterials;

import java.util.Arrays;
import java.util.List;

public class AmalgamAlloyUtils implements AlloyUtils {
    public PeriodicTableUtils elem;
    public List<String> restrict = Arrays.asList("Fe", "Pt", "W", "Ta");
    public List<Integer> emin = Arrays.asList(50,0,0);
    public List<Integer> emax = Arrays.asList(80,50,50);
    public RankineToolMaterials material = RankineToolMaterials.AMALGAM;

    @Override
    public int getDurability(String c) {
        return 0;
    }

    @Override
    public float getEfficiency(String c) {
        return 0;
    }

    @Override
    public float getAttackSpeedMod(String c) {
        return 0;
    }

    @Override
    public float getAttackDamageMod(String c) {
        return 0;
    }

    @Override
    public int getEnchantability(String c) {
        return 0;
    }

    @Override
    public float getCorrResistance(String c) {
        return 0;
    }

    @Override
    public float getHeatResistance(String c) {
        return 0;
    }

    @Override
    public String getDefComposition() {
        return "80Hg-20Au";
    }
}
