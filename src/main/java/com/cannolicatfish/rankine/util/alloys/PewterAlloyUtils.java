package com.cannolicatfish.rankine.util.alloys;

import com.cannolicatfish.rankine.items.tools.RankineToolMaterials;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;

public class PewterAlloyUtils implements AlloyUtils {
    public RankineToolMaterials material = RankineToolMaterials.PEWTER;

    @Override
    public int getDurabilityBonus() {
        return 20;
    }

    @Override
    public float getMiningSpeedBonus() {
        return 4f;
    }

    @Override
    public int getMiningLevelBonus() {
        return 1;
    }

    @Override
    public int getEnchantabilityBonus() {
        return 5;
    }

    @Override
    public float getCorrResistBonus() {
        return 0.25f;
    }

    @Override
    public float getHeatResistBonus() {
        return 0;
    }

    @Override
    public float getToughnessBonus() {
        return -0.05f;
    }

    @Override
    public Enchantment getEnchantmentBonus(Item item) {
        return null;
    }

    @Override
    public String getDefComposition() {
        return "90Sn-10Sb";
    }

    @Override
    public TextFormatting getAlloyGroupColor() {
        return TextFormatting.WHITE;
    }
}