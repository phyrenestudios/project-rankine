package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.items.alloys.AlloySwordItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyArmor;
import com.cannolicatfish.rankine.items.alloys.IAlloyTool;
import com.cannolicatfish.rankine.items.tools.CrowbarItem;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import com.cannolicatfish.rankine.items.tools.KnifeItem;
import com.cannolicatfish.rankine.items.tools.SpearItem;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.HoeItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;

public class RankineEnchantmentTypes {
    public static EnchantmentType HAMMER = EnchantmentType.create("hammer", (itemIn) -> {
        return itemIn instanceof HammerItem; });

    public static EnchantmentType CROWBAR = EnchantmentType.create("crowbar", (itemIn) -> {
        return itemIn instanceof CrowbarItem; });

    public static EnchantmentType KNIFE = EnchantmentType.create("knife", (itemIn) -> {
        return itemIn instanceof KnifeItem; });

    public static EnchantmentType SWING = EnchantmentType.create("swing", (itemIn) -> {
        return itemIn instanceof HammerItem || itemIn instanceof CrowbarItem; });

    public static EnchantmentType SPEAR = EnchantmentType.create("spear", (itemIn) -> {
        return itemIn instanceof SpearItem; });

    public static EnchantmentType HOE = EnchantmentType.create("hoe", (itemIn) -> {
        return itemIn instanceof HoeItem; });

    public static EnchantmentType MINING = EnchantmentType.create("mining", (itemIn) -> {
        return itemIn instanceof ShovelItem || itemIn instanceof PickaxeItem; });

    public static EnchantmentType PEWTER = EnchantmentType.create("pewter", (itemIn) -> {
        return itemIn instanceof IAlloyTool; });

    public static EnchantmentType STAINLESS_STEEL_SWORD = EnchantmentType.create("stainless_steel_sword", (itemIn) -> {
        return itemIn instanceof AlloySwordItem; });

    public static EnchantmentType ALLOYTOOL = EnchantmentType.create("alloytool", (itemIn) -> {
        return itemIn instanceof IAlloyTool && itemIn.isDamageable(); });

    public static EnchantmentType ENDER_AMALGAM_SPEAR = EnchantmentType.create("ender_spear", (itemIn) -> {
        return itemIn == RankineItems.ENDER_AMALGAM_SPEAR.get(); });

    public static EnchantmentType ENDER_AMALGAM_ARMOR = EnchantmentType.create("ender_armor", (itemIn) -> {
        return itemIn instanceof IAlloyArmor; });
}
