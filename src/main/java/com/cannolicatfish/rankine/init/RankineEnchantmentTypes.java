package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.items.alloys.AlloySwordItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyTool;
import com.cannolicatfish.rankine.items.tools.CrowbarItem;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import com.cannolicatfish.rankine.items.tools.SpearItem;
import com.cannolicatfish.rankine.util.alloys.AlloyUtilsEnum;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.HoeItem;

public class RankineEnchantmentTypes {
    public static EnchantmentType HAMMER = EnchantmentType.create("hammer", (itemIn) -> {
        return itemIn instanceof HammerItem; });

    public static EnchantmentType CROWBAR = EnchantmentType.create("crowbar", (itemIn) -> {
        return itemIn instanceof CrowbarItem; });

    public static EnchantmentType SWING = EnchantmentType.create("swing", (itemIn) -> {
        return itemIn instanceof HammerItem || itemIn instanceof CrowbarItem; });

    public static EnchantmentType SPEAR = EnchantmentType.create("spear", (itemIn) -> {
        return itemIn instanceof SpearItem; });


    public static EnchantmentType HOE = EnchantmentType.create("hoe", (itemIn) -> {
        return itemIn instanceof HoeItem; });

    public static EnchantmentType PEWTER = EnchantmentType.create("pewter", (itemIn) -> {
        return itemIn instanceof IAlloyTool && ((IAlloyTool) itemIn).returnAlloyUtils().equals(AlloyUtilsEnum.PEWTER); });

    public static EnchantmentType STAINLESS_STEEL_SWORD = EnchantmentType.create("stainless_steel_sword", (itemIn) -> {
        return itemIn instanceof AlloySwordItem && ((IAlloyTool) itemIn).returnAlloyUtils().equals(AlloyUtilsEnum.STAINLESS); });

    public static EnchantmentType ALLOYTOOL = EnchantmentType.create("alloytool", (itemIn) -> {
        return itemIn instanceof IAlloyTool && itemIn.isDamageable(); });
}
