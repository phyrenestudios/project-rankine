package com.cannolicatfish.rankine.util.alloys;

import com.cannolicatfish.rankine.init.ModEnchantments;
import com.cannolicatfish.rankine.items.tools.ItemHammer;
import com.cannolicatfish.rankine.items.tools.ItemSpear;
import com.cannolicatfish.rankine.items.tools.RankineToolMaterials;
import com.cannolicatfish.rankine.util.PeriodicTableUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.List;

public class PurpleGoldAlloyUtils implements AlloyUtils {
    public RankineToolMaterials material = RankineToolMaterials.PURPLE_GOLD;

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
        return 0.25f;
    }

    @Override
    public float getHeatResistBonus() {
        return 0.25f;
    }

    @Override
    public float getToughnessBonus() {
        return 0.1f;
    }

    @Override
    public Enchantment getEnchantmentBonus(Item item) {
        if (item instanceof ToolItem)
        {
            return item instanceof ItemHammer ? ModEnchantments.ATOMIZE : Enchantments.SILK_TOUCH;
        } else if (item instanceof SwordItem)
        {
            return Enchantments.KNOCKBACK;
        } else if (item instanceof ItemSpear)
        {
            return Enchantments.LOYALTY;
        } else
        {
            return null;
        }

    }

    @Override
    public String getDefComposition() {
        return "80Au-20Al";
    }

    @Override
    public TextFormatting getAlloyGroupColor() {
        return TextFormatting.YELLOW;
    }
}