package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.recipe.ElementRecipe;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ElementUtils {

    private static final ElementUtils INSTANCE = new ElementUtils();

    public static ElementUtils getInstance() {
        return INSTANCE;
    }

    public int calcDurability(List<ElementRecipe> elements, List<Integer> percents)
    {
        int index = 0;
        int durability = 0;
        for (ElementRecipe e: elements)
        {
            durability += e.getDurability(percents.get(index));
            index++;
        }

        return durability;
    }

    public float calcMiningSpeed(List<ElementRecipe> elements, List<Integer> percents)
    {
        int index = 0;
        float miningSpeed = 0f;
        for (ElementRecipe e: elements)
        {
            miningSpeed += e.getMiningSpeed(percents.get(index));
            index++;
        }

        return miningSpeed;
    }

    public int calcMiningLevel(List<ElementRecipe> elements, List<Integer> percents)
    {
        int index = 0;
        List<Integer> levels = new ArrayList<>();
        for (ElementRecipe e: elements)
        {
            levels.add(e.getMiningLevel(percents.get(index)));
            index++;
        }
        if (levels.size() > 0) {
            int min = Math.min(Collections.min(levels),0);
            return Collections.max(levels) - min;
        } else {
            return 0;
        }
    }

    public float calcArrowDamage(List<ElementRecipe> elements, List<Integer> percents)
    {
        int hl = calcMiningLevel(elements,percents);
        float dmg = calcDamage(elements,percents);

        return 2f + 0.5f*hl + 0.5f*dmg;
    }

    public int calcDamageReduceAmount(List<ElementRecipe> elements, List<Integer> percents, EquipmentSlotType slotType)
    {
        int base = slotType == EquipmentSlotType.CHEST ? 3 : slotType == EquipmentSlotType.LEGS ? 2 : 1;
        int index = 0;
        List<Integer> levels = new ArrayList<>();
        for (ElementRecipe e: elements)
        {
            levels.add(e.getMiningLevel(percents.get(index)));
            index++;
        }
        int dr = Collections.max(levels);
        if (slotType == EquipmentSlotType.CHEST || slotType == EquipmentSlotType.LEGS) {
            return Math.min(base + dr,10);
        } else if (slotType == EquipmentSlotType.FEET) {
            if (dr >= 5) {
                return base + 2;
            } else if (dr >= 3) {
                return base + 1;
            } else {
                return base;
            }
        } else {
            if (dr >= 5) {
                return base + 3;
            } else if (dr >= 3) {
                return base + 2;
            } else if (dr >= 1) {
                return base + 1;
            } else {
                return base;
            }
        }
    }

    public float calcDamage(List<ElementRecipe> elements, List<Integer> percents) // takes max; 0 -> 4 (strongest)
    {
        int index = 0;
        List<Float> levels = new ArrayList<>();
        for (ElementRecipe e: elements) {
            levels.add(e.getDamage(percents.get(index)));
            index++;
        }
        if (levels.size() > 0) {
            float min = Math.min(Collections.min(levels),0);
            return Collections.max(levels) - min;
        } else {
            return 0;
        }

    }

    public float calcAttackSpeed(List<ElementRecipe> elements, List<Integer> percents) // takes max; 0 -> 3 adds to preexisting attack speed (-3 -> 0 maximum attack speed);
    {
        int index = 0;
        List<Float> levels = new ArrayList<>();
        for (ElementRecipe e: elements)
        {
            levels.add(e.getAttackSpeed(percents.get(index)));
            index++;
        }
        if (levels.size() > 0) {
            float min = Math.min(Collections.min(levels),0);
            return Collections.max(levels) - min;
        } else {
            return 0;
        }
    }

    public int calcEnchantability(List<ElementRecipe> elements, List<Integer> percents)
    {
        int index = 0;
        int enchant = 0;
        for (ElementRecipe e: elements)
        {
            enchant += e.getEnchantability(percents.get(index));
            index++;
        }

        return enchant;
    }

    public float calcCorrResist(List<ElementRecipe> elements, List<Integer> percents)
    {
        int index = 0;
        float corrResist = 0f;
        for (ElementRecipe e: elements)
        {
            corrResist += e.getCorrosionResistance(percents.get(index));
            index++;
        }

        return corrResist;
    }

    public float calcHeatResist(List<ElementRecipe> elements, List<Integer> percents)
    {
        int index = 0;
        float heatResist = 0f;
        for (ElementRecipe e: elements)
        {
            heatResist += e.getHeatResistance(percents.get(index));
            index++;
        }

        return heatResist;
    }

    public float calcToughness(List<ElementRecipe> elements, List<Integer> percents)
    {
        int index = 0;
        float tough = 0f;
        for (ElementRecipe e: elements)
        {
            tough += e.getToughness(percents.get(index));
            index++;
        }

        return tough;
    }


    public int calcArmorDurability(List<ElementRecipe> elements, List<Integer> percents, int extra, EquipmentSlotType slotType)
    {
        final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
        int durability = calcDurability(elements,percents) + extra;
        if (durability <= 100) {
            return Math.round(MAX_DAMAGE_ARRAY[slotType.getIndex()] * durability/10f);
        } else {
            return Math.round(MAX_DAMAGE_ARRAY[slotType.getIndex()] * (10 + (durability-100)/50f));
        }
    }

    public int calcArmorToughness(List<ElementRecipe> elements, List<Integer> percents)
    {
        int index = 0;
        float tough = 0f;
        for (ElementRecipe e: elements)
        {
            tough += e.getToughness(percents.get(index));
            index++;
        }
        if (tough >= 0.4) {
            return 4;
        } else if (tough >= 0.3) {
            return 3;
        } else if (tough >= 0.2){
            return 2;
        } else if (tough >= 0.1){
            return 1;
        } else {
            return 0;
        }
    }

    public List<Enchantment> getToolEnchantments(List<ElementRecipe> elements, List<Integer> percents)
    {
        int index = 0;
        List<Enchantment> enchantments = new ArrayList<>();
        for (ElementRecipe e: elements)
        {
            Enchantment en = e.getToolEnchantment(percents.get(index));
            if (en != null)
            {
                enchantments.add(en);
            }
            index++;
        }

        return enchantments;
    }

    public List<Enchantment> getArmorEnchantments(List<ElementRecipe> elements, List<Integer> percents)
    {
        int index = 0;
        List<Enchantment> enchantments = new ArrayList<>();
        for (ElementRecipe e: elements)
        {
            Enchantment en = e.getToolEnchantment(percents.get(index));
            if (en != null && !enchantments.contains(en))
            {
                enchantments.add(en);
            }
            index++;
        }

        return enchantments;
    }
}
