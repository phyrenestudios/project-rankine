package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.items.BlunderbussItem;
import com.cannolicatfish.rankine.items.alloys.*;
import com.cannolicatfish.rankine.items.tools.CrowbarItem;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import com.cannolicatfish.rankine.items.tools.KnifeItem;
import com.cannolicatfish.rankine.items.tools.SpearItem;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;

public class RankineEnchantmentTypes {
    public static EnchantmentCategory HAMMER = EnchantmentCategory.create("hammer", (itemIn) -> {
        return itemIn instanceof HammerItem; });

    public static EnchantmentCategory CROWBAR = EnchantmentCategory.create("crowbar", (itemIn) -> {
        return itemIn instanceof CrowbarItem; });

    public static EnchantmentCategory KNIFE = EnchantmentCategory.create("knife", (itemIn) -> {
        return itemIn instanceof KnifeItem; });

    public static EnchantmentCategory SWING = EnchantmentCategory.create("swing", (itemIn) -> {
        return itemIn instanceof HammerItem || itemIn instanceof CrowbarItem; });

    public static EnchantmentCategory SPEAR = EnchantmentCategory.create("spear", (itemIn) -> {
        return itemIn instanceof SpearItem; });

    public static EnchantmentCategory HOE = EnchantmentCategory.create("hoe", (itemIn) -> {
        return itemIn instanceof HoeItem; });

    public static EnchantmentCategory ALLOY_SHOVEL = EnchantmentCategory.create("alloy_shovel", (itemIn) -> {
        return itemIn instanceof AlloyShovelItem; });

    public static EnchantmentCategory MINING = EnchantmentCategory.create("mining", (itemIn) -> {
        return itemIn instanceof ShovelItem || itemIn instanceof PickaxeItem; });

    public static EnchantmentCategory PEWTER = EnchantmentCategory.create("pewter", (itemIn) -> {
        return itemIn instanceof IAlloyTool; });

    public static EnchantmentCategory BLUNDERBUSS = EnchantmentCategory.create("blunderbuss", (itemIn) -> {
        return itemIn instanceof BlunderbussItem; });

    public static EnchantmentCategory STAINLESS_STEEL_SWORD = EnchantmentCategory.create("stainless_steel_sword", (itemIn) -> {
        return itemIn instanceof AlloySwordItem; });

    public static EnchantmentCategory ALLOYTOOL = EnchantmentCategory.create("alloytool", (itemIn) -> {
        return itemIn instanceof IAlloyTool && itemIn.canBeDepleted(); });

    public static EnchantmentCategory ENDER_AMALGAM_HAMMER = EnchantmentCategory.create("ender_hammer", (itemIn) -> {
        return itemIn == RankineItems.ENDER_AMALGAM_HAMMER.get(); });

    public static EnchantmentCategory ENDER_AMALGAM_SWORD = EnchantmentCategory.create("ender_sword", (itemIn) -> {
        return itemIn == RankineItems.ENDER_AMALGAM_SWORD.get(); });

    public static EnchantmentCategory ENDER_AMALGAM_SHOVEL = EnchantmentCategory.create("ender_shovel", (itemIn) -> {
        return itemIn == RankineItems.ENDER_AMALGAM_SPEAR.get(); });

    public static EnchantmentCategory ENDER_AMALGAM_KNIFE = EnchantmentCategory.create("ender_knife", (itemIn) -> {
        return itemIn == RankineItems.ENDER_AMALGAM_KNIFE.get(); });

    public static EnchantmentCategory ENDER_AMALGAM_CROWBAR = EnchantmentCategory.create("ender_crowbar", (itemIn) -> {
        return itemIn == RankineItems.ENDER_AMALGAM_CROWBAR.get(); });

    public static EnchantmentCategory ENDER_AMALGAM_SPEAR = EnchantmentCategory.create("ender_spear", (itemIn) -> {
        return itemIn == RankineItems.ENDER_AMALGAM_SPEAR.get(); });

    public static EnchantmentCategory ENDER_AMALGAM_AXE = EnchantmentCategory.create("ender_axe", (itemIn) -> {
        return itemIn == RankineItems.ENDER_AMALGAM_AXE.get(); });

    public static EnchantmentCategory ENDER_AMALGAM_HOE = EnchantmentCategory.create("ender_hoe", (itemIn) -> {
        return itemIn == RankineItems.ENDER_AMALGAM_HOE.get(); });

    public static EnchantmentCategory ENDER_AMALGAM_PICKAXE = EnchantmentCategory.create("ender_pickaxe", (itemIn) -> {
        return itemIn == RankineItems.ENDER_AMALGAM_PICKAXE.get(); });

    public static EnchantmentCategory ENDER_AMALGAM_BLUNDERBUSS = EnchantmentCategory.create("ender_blunderbuss", (itemIn) -> {
        return itemIn == RankineItems.ENDER_AMALGAM_BLUNDERBUSS.get(); });

    public static EnchantmentCategory ENDER_AMALGAM_ARMOR = EnchantmentCategory.create("ender_armor", (itemIn) -> {
        return itemIn instanceof AlloyArmorItem; });
}
