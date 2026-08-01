package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.enchantment.RankineEnchantmentTags;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * Data-driven definitions of every Rankine enchantment (replaces the deleted 1.18 Enchantment
 * subclasses). Wired into datagen via {@code RegistrySetBuilder.add(Registries.ENCHANTMENT,
 * RankineEnchantmentBootstrap::bootstrap)} in the DatapackBuiltinEntriesProvider.
 *
 * Rarity translation follows vanilla's 1.20.5 migration:
 *   COMMON -> weight 10, anvilCost 1; UNCOMMON -> 5, 2; RARE -> 2, 4; VERY_RARE -> 1, 8.
 * Costs are 1:1 translations of the old getMinCost/getMaxCost formulas
 * (old vanilla default getMinCost(lvl) = 1 + lvl*10 = Cost(11, 10) where subclasses delegated
 * to super for their max cost).
 */
public class RankineEnchantmentBootstrap {

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Enchantment> enchantments = context.lookup(Registries.ENCHANTMENT);
        HolderGetter<Item> items = context.lookup(Registries.ITEM);

        // ---- Hammer / spear / mining tools ----
        register(context, RankineEnchantments.PUNCTURE, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.SPEAR_ENCHANTABLE),
                10, 3, Enchantment.dynamicCost(1, 10), Enchantment.dynamicCost(61, 10), 1, EquipmentSlotGroup.MAINHAND)));
        register(context, RankineEnchantments.SWING, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.SWING_ENCHANTABLE),
                10, 5, Enchantment.dynamicCost(1, 10), Enchantment.dynamicCost(61, 10), 1, EquipmentSlotGroup.MAINHAND)));
        register(context, RankineEnchantments.DAZE, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.HAMMER_ENCHANTABLE),
                10, 3, Enchantment.dynamicCost(1, 10), Enchantment.dynamicCost(61, 10), 1, EquipmentSlotGroup.MAINHAND)));
        register(context, RankineEnchantments.ATOMIZE, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.HAMMER_ENCHANTABLE),
                5, 1, Enchantment.constantCost(25), Enchantment.dynamicCost(61, 10), 2, EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(RankineEnchantmentTags.ATOMIZE_EXCLUSIVE)));
        register(context, RankineEnchantments.EXCAVATE, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.HAMMER_ENCHANTABLE),
                2, 3, Enchantment.dynamicCost(1, 30), Enchantment.dynamicCost(86, 10), 4, EquipmentSlotGroup.MAINHAND)));
        register(context, RankineEnchantments.IMPACT, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.SPEAR_ENCHANTABLE),
                5, 5, Enchantment.dynamicCost(1, 10), Enchantment.dynamicCost(61, 10), 2, EquipmentSlotGroup.MAINHAND)));
        register(context, RankineEnchantments.QUAKE, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.MINING_ENCHANTABLE),
                5, 3, Enchantment.dynamicCost(1, 10), Enchantment.dynamicCost(61, 10), 2, EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(RankineEnchantmentTags.QUAKE_EXCLUSIVE)));
        register(context, RankineEnchantments.FORAGING, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.ALLOY_HOE_ENCHANTABLE),
                2, 1, Enchantment.constantCost(10), Enchantment.dynamicCost(61, 10), 4, EquipmentSlotGroup.MAINHAND)));
        register(context, RankineEnchantments.LIGHTNING_ASPECT, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.HAMMER_ENCHANTABLE),
                1, 1, Enchantment.constantCost(15), Enchantment.dynamicCost(61, 10), 8, EquipmentSlotGroup.MAINHAND)));
        // PORT-TODO(enchantment): old AntiquatedEnchantment only allowed enchanting when the stack's alloy
        // recipe was rankine:alloying/pewter_alloying — recipe-conditional applicability is not expressible
        // in the data-driven definition. Supported items widened to all alloy tools; if pewter-only gating
        // is still wanted, enforce it in AnvilUpdateEvent / enchanting hooks.
        register(context, RankineEnchantments.ANTIQUATED, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.ALLOY_TOOL_ENCHANTABLE),
                1, 5, Enchantment.constantCost(15), Enchantment.dynamicCost(61, 10), 8, EquipmentSlotGroup.MAINHAND)));
        register(context, RankineEnchantments.CLEANSE, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.ALLOY_SWORD_ENCHANTABLE),
                1, 2, Enchantment.constantCost(15), Enchantment.dynamicCost(61, 10), 8, EquipmentSlotGroup.MAINHAND)));

        // ---- Armor (all treasure-only: old canApplyAtEnchantingTable() == false) ----
        register(context, RankineEnchantments.DUNE_WALKER, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                1, 1, Enchantment.dynamicCost(10, 10), Enchantment.dynamicCost(25, 10), 8, EquipmentSlotGroup.FEET)));
        register(context, RankineEnchantments.SNOW_DRIFTER, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                1, 1, Enchantment.dynamicCost(10, 10), Enchantment.dynamicCost(25, 10), 8, EquipmentSlotGroup.FEET)));
        register(context, RankineEnchantments.SPEED_SKATER, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                1, 1, Enchantment.dynamicCost(10, 10), Enchantment.dynamicCost(25, 10), 8, EquipmentSlotGroup.FEET)));
        register(context, RankineEnchantments.SWIFT_SWIMMER, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                1, 1, Enchantment.dynamicCost(10, 10), Enchantment.dynamicCost(25, 10), 8, EquipmentSlotGroup.FEET)));
        register(context, RankineEnchantments.GAS_PROTECTION, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                1, 1, Enchantment.dynamicCost(10, 10), Enchantment.dynamicCost(25, 10), 8, EquipmentSlotGroup.HEAD)));
        register(context, RankineEnchantments.AQUA_LENSE, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                1, 1, Enchantment.dynamicCost(10, 10), Enchantment.dynamicCost(25, 10), 8, EquipmentSlotGroup.HEAD)));

        // ---- Ender Amalgam set (all non-discoverable in 1.18: keep out of enchanting table via tags) ----
        register(context, RankineEnchantments.ENDOBIOTIC, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.ALLOY_ARMOR_ENCHANTABLE),
                1, 1, Enchantment.dynamicCost(10, 7), Enchantment.dynamicCost(61, 10), 8, EquipmentSlotGroup.HAND)));
        register(context, RankineEnchantments.ENDOTOXIN, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.ENDER_AMALGAM_CROWBAR_ENCHANTABLE),
                1, 3, Enchantment.dynamicCost(5, 8), Enchantment.dynamicCost(25, 8), 8, EquipmentSlotGroup.MAINHAND)));
        register(context, RankineEnchantments.ENDPOINT, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.ENDER_AMALGAM_SPEAR_ENCHANTABLE),
                1, 3, Enchantment.dynamicCost(10, 7), Enchantment.dynamicCost(61, 10), 8, EquipmentSlotGroup.MAINHAND))
                .exclusiveWith(enchantments.getOrThrow(RankineEnchantmentTags.ENDPOINT_EXCLUSIVE)));
        register(context, RankineEnchantments.ENDOSPORE, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.ENDER_AMALGAM_HOE_ENCHANTABLE),
                1, 1, Enchantment.dynamicCost(10, 7), Enchantment.dynamicCost(61, 10), 8, EquipmentSlotGroup.MAINHAND)));
        register(context, RankineEnchantments.ENDURE, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.ENDER_AMALGAM_SWORD_ENCHANTABLE),
                1, 1, Enchantment.dynamicCost(10, 7), Enchantment.dynamicCost(61, 10), 8, EquipmentSlotGroup.MAINHAND)));
        register(context, RankineEnchantments.ENDGAME, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.ENDER_AMALGAM_KNIFE_ENCHANTABLE),
                1, 1, Enchantment.dynamicCost(10, 7), Enchantment.dynamicCost(61, 10), 8, EquipmentSlotGroup.HAND)));
        register(context, RankineEnchantments.ENDOLITHIC, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.ENDER_AMALGAM_SHOVEL_ENCHANTABLE),
                1, 1, Enchantment.dynamicCost(10, 7), Enchantment.dynamicCost(61, 10), 8, EquipmentSlotGroup.MAINHAND)));
        register(context, RankineEnchantments.ENDLESS, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.ENDER_AMALGAM_PICKAXE_ENCHANTABLE),
                1, 1, Enchantment.dynamicCost(10, 7), Enchantment.dynamicCost(61, 10), 8, EquipmentSlotGroup.MAINHAND)));
        register(context, RankineEnchantments.ENDEAVOR, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.ENDER_AMALGAM_HAMMER_ENCHANTABLE),
                1, 3, Enchantment.dynamicCost(10, 7), Enchantment.dynamicCost(61, 10), 8, EquipmentSlotGroup.MAINHAND)));
        register(context, RankineEnchantments.ENDOTHERMIC, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.ENDER_AMALGAM_AXE_ENCHANTABLE),
                1, 1, Enchantment.dynamicCost(10, 7), Enchantment.dynamicCost(61, 10), 8, EquipmentSlotGroup.MAINHAND)));
        register(context, RankineEnchantments.ENDPLAY, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.ENDER_AMALGAM_BLUNDERBUSS_ENCHANTABLE),
                1, 1, Enchantment.dynamicCost(10, 7), Enchantment.dynamicCost(61, 10), 8, EquipmentSlotGroup.MAINHAND)));

        // ---- Special alloy enchantments ----
        register(context, RankineEnchantments.GHASTLY_REGENERATION, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.ALLOY_TOOL_ENCHANTABLE),
                1, 1, Enchantment.dynamicCost(10, 7), Enchantment.dynamicCost(61, 10), 8, EquipmentSlotGroup.MAINHAND)));
        // Old WitheringCurseEnchantment.canEnchant used EnchantmentCategory.BREAKABLE -> vanilla durability tag.
        register(context, RankineEnchantments.WITHERING_CURSE, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.DURABILITY_ENCHANTABLE),
                1, 1, Enchantment.dynamicCost(10, 7), Enchantment.dynamicCost(61, 10), 8, EquipmentSlotGroup.MAINHAND)));
        // PORT-TODO(enchantment): old ShapeMemoryEnchantment only allowed enchanting when the stack's alloy
        // recipe was rankine:alloying/nitinol_alloying — not expressible data-driven; widened to alloy tools.
        register(context, RankineEnchantments.SHAPE_MEMORY, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.ALLOY_TOOL_ENCHANTABLE),
                1, 1, Enchantment.dynamicCost(10, 7), Enchantment.dynamicCost(61, 10), 8, EquipmentSlotGroup.MAINHAND)));
        register(context, RankineEnchantments.GUARD, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                5, 1, Enchantment.dynamicCost(10, 10), Enchantment.dynamicCost(40, 10), 2, EquipmentSlotGroup.ARMOR)));

        // ---- Blunderbuss / knife ----
        register(context, RankineEnchantments.ACCURACY, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.BLUNDERBUSS_ENCHANTABLE),
                5, 3, Enchantment.dynamicCost(5, 9), Enchantment.dynamicCost(20, 9), 2, EquipmentSlotGroup.MAINHAND)));
        register(context, RankineEnchantments.PREPARATION, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.KNIFE_ENCHANTABLE),
                10, 3, Enchantment.dynamicCost(1, 10), Enchantment.dynamicCost(61, 10), 1, EquipmentSlotGroup.HAND)));
        register(context, RankineEnchantments.POISON_ASPECT, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.KNIFE_ENCHANTABLE),
                2, 2, Enchantment.dynamicCost(10, 20), Enchantment.dynamicCost(61, 10), 4, EquipmentSlotGroup.MAINHAND)));
        register(context, RankineEnchantments.BACKSTAB, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.KNIFE_ENCHANTABLE),
                2, 2, Enchantment.dynamicCost(5, 9), Enchantment.dynamicCost(20, 9), 4, EquipmentSlotGroup.MAINHAND)));
        register(context, RankineEnchantments.GRAFTING, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.KNIFE_ENCHANTABLE),
                2, 1, Enchantment.constantCost(10), Enchantment.dynamicCost(61, 10), 4, EquipmentSlotGroup.MAINHAND)));
        register(context, RankineEnchantments.RETALIATE, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.KNIFE_ENCHANTABLE),
                1, 1, Enchantment.constantCost(15), Enchantment.dynamicCost(61, 10), 8, EquipmentSlotGroup.HAND))
                .exclusiveWith(enchantments.getOrThrow(RankineEnchantmentTags.STANCE_EXCLUSIVE)));
        register(context, RankineEnchantments.RETREAT, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.KNIFE_ENCHANTABLE),
                1, 1, Enchantment.constantCost(15), Enchantment.dynamicCost(61, 10), 8, EquipmentSlotGroup.HAND))
                .exclusiveWith(enchantments.getOrThrow(RankineEnchantmentTags.STANCE_EXCLUSIVE)));

        // ---- Crowbar ----
        register(context, RankineEnchantments.LEVERAGE, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.CROWBAR_ENCHANTABLE),
                5, 5, Enchantment.dynamicCost(1, 10), Enchantment.dynamicCost(61, 10), 2, EquipmentSlotGroup.MAINHAND)));
        register(context, RankineEnchantments.TORQUE, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.CROWBAR_ENCHANTABLE),
                2, 5, Enchantment.dynamicCost(1, 10), Enchantment.dynamicCost(61, 10), 4, EquipmentSlotGroup.MAINHAND)));
        register(context, RankineEnchantments.PRYING, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.CROWBAR_ENCHANTABLE),
                2, 3, Enchantment.dynamicCost(1, 10), Enchantment.dynamicCost(61, 10), 4, EquipmentSlotGroup.MAINHAND)));
        register(context, RankineEnchantments.FULCRUM, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.CROWBAR_ENCHANTABLE),
                1, 3, Enchantment.dynamicCost(1, 10), Enchantment.dynamicCost(71, 10), 8, EquipmentSlotGroup.MAINHAND)));
        register(context, RankineEnchantments.RETRIEVAL, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(RankineEnchantmentTags.CROWBAR_ENCHANTABLE),
                1, 1, Enchantment.constantCost(15), Enchantment.dynamicCost(61, 10), 8, EquipmentSlotGroup.MAINHAND)));
    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.location()));
    }
}
