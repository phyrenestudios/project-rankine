package com.cannolicatfish.rankine.util.alloys;

import com.cannolicatfish.rankine.items.tools.RankineToolMaterials;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public final class BronzeAlloyUtils implements AlloyUtils {
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
    public int getMiningLevelBonus() {
        return 0;
    }

    @Override
    public int getEnchantabilityBonus() {
        return 0;
    }

    @Override
    public float getCorrResistBonus() {
        return 0;
    }

    @Override
    public float getHeatResistBonus() {
        return 0.25f;
    }

    @Override
    public float getToughnessBonus() {
        return 0.05f;
    }

    @Override
    public Enchantment getEnchantmentBonus(Item item) {
        return null;
    }

    @Override
    public int getEnchantmentLevel(Enchantment en, int enchantability) {
        return 1;
    }


    @Override
    public String getDefComposition() {
        return "80Cu-20Sn";
    }

    @Override
    public TextFormatting getAlloyGroupColor() {
        return TextFormatting.GOLD;
    }
}
