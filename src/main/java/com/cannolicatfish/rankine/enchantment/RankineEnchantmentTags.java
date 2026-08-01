package com.cannolicatfish.rankine.enchantment;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * Tags replacing the 1.18 EnchantmentCategory system (old RankineEnchantmentTypes).
 * Item tags define which items an enchantment supports; enchantment tags define exclusivity
 * (old checkCompatibility overrides). Tag CONTENTS are produced by datagen — see the porting
 * report / datagen agent notes for required members.
 */
public class RankineEnchantmentTags {

    // ---- Item tags (old EnchantmentCategory -> rankine:enchantable/*) ----
    /** old HAMMER category: any HammerItem (incl. alloy hammers) */
    public static final TagKey<Item> HAMMER_ENCHANTABLE = itemTag("enchantable/hammer");
    /** old CROWBAR category: any CrowbarItem (incl. alloy crowbars) */
    public static final TagKey<Item> CROWBAR_ENCHANTABLE = itemTag("enchantable/crowbar");
    /** old KNIFE category: any KnifeItem (incl. alloy knives) */
    public static final TagKey<Item> KNIFE_ENCHANTABLE = itemTag("enchantable/knife");
    /** old SWING category: HammerItem or CrowbarItem */
    public static final TagKey<Item> SWING_ENCHANTABLE = itemTag("enchantable/swing");
    /** old SPEAR category: any SpearItem (incl. alloy spears) */
    public static final TagKey<Item> SPEAR_ENCHANTABLE = itemTag("enchantable/spear");
    /** old MINING category: ShovelItem or PickaxeItem */
    public static final TagKey<Item> MINING_ENCHANTABLE = itemTag("enchantable/mining");
    /** old ALLOY_HOE category: AlloyHoeItem */
    public static final TagKey<Item> ALLOY_HOE_ENCHANTABLE = itemTag("enchantable/alloy_hoe");
    /** old PEWTER + ALLOYTOOL categories: any IAlloyTool item with durability */
    public static final TagKey<Item> ALLOY_TOOL_ENCHANTABLE = itemTag("enchantable/alloy_tool");
    /** old STAINLESS_STEEL_SWORD category: AlloySwordItem */
    public static final TagKey<Item> ALLOY_SWORD_ENCHANTABLE = itemTag("enchantable/alloy_sword");
    /** old ENDER_AMALGAM_ARMOR category: AlloyArmorItem */
    public static final TagKey<Item> ALLOY_ARMOR_ENCHANTABLE = itemTag("enchantable/alloy_armor");
    /** old BLUNDERBUSS category: BlunderbussItem (incl. alloy blunderbusses) */
    public static final TagKey<Item> BLUNDERBUSS_ENCHANTABLE = itemTag("enchantable/blunderbuss");

    // Ender Amalgam single-item categories (each tag contains exactly the one ender amalgam item)
    public static final TagKey<Item> ENDER_AMALGAM_HAMMER_ENCHANTABLE = itemTag("enchantable/ender_amalgam_hammer");
    public static final TagKey<Item> ENDER_AMALGAM_SWORD_ENCHANTABLE = itemTag("enchantable/ender_amalgam_sword");
    public static final TagKey<Item> ENDER_AMALGAM_SHOVEL_ENCHANTABLE = itemTag("enchantable/ender_amalgam_shovel");
    public static final TagKey<Item> ENDER_AMALGAM_KNIFE_ENCHANTABLE = itemTag("enchantable/ender_amalgam_knife");
    public static final TagKey<Item> ENDER_AMALGAM_CROWBAR_ENCHANTABLE = itemTag("enchantable/ender_amalgam_crowbar");
    public static final TagKey<Item> ENDER_AMALGAM_SPEAR_ENCHANTABLE = itemTag("enchantable/ender_amalgam_spear");
    public static final TagKey<Item> ENDER_AMALGAM_AXE_ENCHANTABLE = itemTag("enchantable/ender_amalgam_axe");
    public static final TagKey<Item> ENDER_AMALGAM_HOE_ENCHANTABLE = itemTag("enchantable/ender_amalgam_hoe");
    public static final TagKey<Item> ENDER_AMALGAM_PICKAXE_ENCHANTABLE = itemTag("enchantable/ender_amalgam_pickaxe");
    public static final TagKey<Item> ENDER_AMALGAM_BLUNDERBUSS_ENCHANTABLE = itemTag("enchantable/ender_amalgam_blunderbuss");

    // ---- Enchantment exclusivity tags (old checkCompatibility -> rankine:exclusive_set/*) ----
    /** atomize is incompatible with minecraft:fortune */
    public static final TagKey<Enchantment> ATOMIZE_EXCLUSIVE = enchantmentTag("exclusive_set/atomize");
    /** quake is incompatible with minecraft:efficiency */
    public static final TagKey<Enchantment> QUAKE_EXCLUSIVE = enchantmentTag("exclusive_set/quake");
    /** endpoint is incompatible with minecraft:loyalty */
    public static final TagKey<Enchantment> ENDPOINT_EXCLUSIVE = enchantmentTag("exclusive_set/endpoint");
    /** retaliate and retreat are mutually incompatible (tag contains both, vanilla-style) */
    public static final TagKey<Enchantment> STANCE_EXCLUSIVE = enchantmentTag("exclusive_set/stance");

    private static TagKey<Item> itemTag(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, path));
    }

    private static TagKey<Enchantment> enchantmentTag(String path) {
        return TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, path));
    }
}
