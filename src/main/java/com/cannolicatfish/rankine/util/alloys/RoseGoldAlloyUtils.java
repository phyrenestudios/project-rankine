package com.cannolicatfish.rankine.util.alloys;

import com.cannolicatfish.rankine.init.ModEnchantments;
import com.cannolicatfish.rankine.items.tools.ItemHammer;
import com.cannolicatfish.rankine.items.tools.ItemSpear;
import com.cannolicatfish.rankine.items.tools.RankineToolMaterials;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.*;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.List;

public class RoseGoldAlloyUtils implements AlloyUtils {
    public RankineToolMaterials material = RankineToolMaterials.ROSE_GOLD;

    @Override
    public int getDurabilityBonus() {
        return 0;
    }

    @Override
    public float getMiningSpeedBonus() {
        return 0;
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
        return 0.05f;
    }

    @Override
    public float getHeatResistBonus() {
        return 0.35f;
    }

    @Override
    public float getToughnessBonus() {
        return 0.1f;
    }

    @Override
    public Enchantment getEnchantmentBonus(Item item) {
        if (item instanceof ToolItem)
        {
            return item instanceof ItemHammer ? ModEnchantments.SWING : Enchantments.EFFICIENCY;
        } else if (item instanceof SwordItem)
        {
            return Enchantments.SHARPNESS;
        } else if (item instanceof ItemSpear)
        {
            return ModEnchantments.PUNCTURE;
        }  else
        {
            return null;
        }

    }

    @Override
    public String getDefComposition() {
        return "75Au-22Cu-3Ni";
    }

    @Override
    public TextFormatting getAlloyGroupColor() {
        return TextFormatting.YELLOW;
    }
}
