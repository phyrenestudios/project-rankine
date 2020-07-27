package com.cannolicatfish.rankine.util.alloys;

import com.cannolicatfish.rankine.init.ModEnchantments;
import com.cannolicatfish.rankine.items.tools.ItemHammer;
import com.cannolicatfish.rankine.items.tools.ItemSpear;
import com.cannolicatfish.rankine.items.tools.RankineToolMaterials;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import com.cannolicatfish.rankine.util.alloys.AlloyUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.List;

public class WhiteGoldAlloyUtils implements AlloyUtils {
    public RankineToolMaterials material = RankineToolMaterials.WHITE_GOLD;

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
        return 0.1f;
    }

    @Override
    public float getHeatResistBonus() {
        return 0.3f;
    }

    @Override
    public float getToughnessBonus() {
        return 0.2f;
    }

    @Override
    public Enchantment getEnchantmentBonus(Item item) {
        if (item instanceof ToolItem)
        {
            return item instanceof ItemHammer ? ModEnchantments.BLAST : Enchantments.FORTUNE;
        } else if (item instanceof SwordItem)
        {
            return Enchantments.LOOTING;
        } else if (item instanceof ItemSpear)
        {
            return Enchantments.IMPALING;
        }  else
        {
            return null;
        }

    }

    @Override
    public int getEnchantmentLevel(Enchantment en, int enchantability) {
        if (enchantability >= 25 && en.getMaxLevel() >= 2)
        {
            return 2;
        }
        return 1;
    }

    @Override
    public String getDefComposition() {
        return "90Au-10Ni";
    }

    @Override
    public TextFormatting getAlloyGroupColor() {
        return TextFormatting.YELLOW;
    }
}
