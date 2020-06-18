package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.items.tools.RankineToolMaterials;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public final class BronzeAlloyUtils implements AlloyUtils {
    public PeriodicTableUtils elem;
    public List<String> possible = Arrays.asList("Cu", "Sn", "Al", "Mn", "Ni", "Zn");
    public List<PeriodicTableUtils.Element> elementsList = Arrays.asList(PeriodicTableUtils.Element.COPPER, PeriodicTableUtils.Element.TIN, PeriodicTableUtils.Element.ALUMINUM,
            PeriodicTableUtils.Element.MANGANESE, PeriodicTableUtils.Element.NICKEL, PeriodicTableUtils.Element.ZINC);
    public List<Integer> emin = Arrays.asList(80,10,0,0,0,0);
    public List<Integer> emax = Arrays.asList(90,20,10,10,10,10);
    public RankineToolMaterials material = RankineToolMaterials.BRONZE;


    @Override
    public int getDurabilityBonus() {
        return 51;
    }

    @Override
    public float getMiningSpeedBonus() {
        return 0f;
    }

    @Override
    public int getEnchantabilityBonus() {
        return 0;
    }

    public int getDurability(String c)
    {
        List<String> comp = Arrays.asList(c.split("-"));
        int cuComp = Integer.parseInt(comp.get(0).substring(0,2));
        return Math.round(material.getMaxUses() * (1 + (cuComp - emin.get(0))*0.05f));
    }

    // Bronze Efficiency === 4 - ((MaxSN - AlloySn) / 5)

    public float getEfficiency(String c)
    {
        List<String> comp = Arrays.asList(c.split("-"));
        int snComp = Integer.parseInt(comp.get(1).substring(0,2));
        DecimalFormat df = new DecimalFormat("#.#");
        return Float.parseFloat(df.format(material.getEfficiency() - ((emax.get(1) - snComp)/5f)));
    }

    // Bronze AttackSpeedMod === 1 / (1 + (MaxSn - AlloySn)/0.1)

    public float getAttackSpeedMod(String c)
    {

        List<String> comp = Arrays.asList(c.split("-"));
        int snComp = Integer.parseInt(comp.get(1).substring(0,2));
        return 1/(1 + (emax.get(1) - snComp)/10f);
    }

    @Override
    public float getAttackDamageMod(String c) {
        return 0;
    }

    // "defaultEnchant" * (1 + ((ZnAlloy - MinZn) / 0.05))
    public int getEnchantability(String c)
    {
        List<String> comp = Arrays.asList(c.split("-"));
        int znComp = 0;
        if (comp.size() == 3)
        {
            if (comp.get(2).contains("Zn") && comp.get(2).length() == 3)
            {
                znComp = Integer.parseInt(comp.get(2).substring(0,1));
            } else if (comp.get(2).contains("Zn") && comp.get(2).length() == 4)
            {
                znComp = Integer.parseInt(comp.get(2).substring(0,2));
            }
        }
        return Math.round(material.getEnchantability() * (1 + ((znComp) * 0.05f)));
    }

    public float getCorrResistance(String c)
    {
        List<String> comp = Arrays.asList(c.split("-"));
        int alComp = 0;
        int znComp = 0;
        if (comp.size() == 3)
        {
            if (comp.get(2).contains("Al") && comp.get(2).length() == 3)
            {
                alComp = Integer.parseInt(comp.get(2).substring(0,1));
            } else if (comp.get(2).contains("Al") && comp.get(2).length() == 4)
            {
                alComp = Integer.parseInt(comp.get(2).substring(0,2));
            }

            if (comp.get(2).contains("Zn") && comp.get(2).length() == 3)
            {
                znComp = Integer.parseInt(comp.get(2).substring(0,1));
            } else if (comp.get(2).contains("Zn") && comp.get(2).length() == 4)
            {
                znComp = Integer.parseInt(comp.get(2).substring(0,2));
            }
        }
        DecimalFormat df = new DecimalFormat("#.#");
        return Float.parseFloat(df.format((alComp - emin.get(2)) * 0.05f + (znComp - emin.get(5)) * 0.02f));
    }

    public float getHeatResistance(String c)
    {
        return 0.25f;
    }

    @Override
    public String getDefComposition() {
        return "80Cu-20Sn";
    }

    /*

public float getCorrResistance(List<Pair<String, Float>> list)
(String = name, Float = weight)

variable resistance;

for i in list:
	find max/min (1: alMin, 2: znMin, 3: snMin)
	get weight
	get amount in composition (elementComp)
	ex. (elementComp - elementMin) * weight
	resistance += (elementComp - elementMin) * weight

return resistance;

     */
}
