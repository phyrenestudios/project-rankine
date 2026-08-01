package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.enchantment.RankineEnchantmentHelper;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

/**
 * 1.21.1: AnvilUpdateEvent gained getPlayer() (used here to reach the registry access needed to
 * resolve the datapack-driven Rankine enchantments) and setCost now takes a long.
 */
public class AnvilUpdateHandler {
    public static void specialEnchants(AnvilUpdateEvent event) {
        ItemStack input = event.getLeft();
        Level level = event.getPlayer().level();
        Item right = event.getRight().getItem();

        if (right == RankineItems.SANDALS.get() && isArmorForSlot(input, EquipmentSlot.FEET)) {
            applyEnchant(event, level, input, RankineEnchantments.DUNE_WALKER);
        } else if (right == RankineItems.SNOWSHOES.get() && isArmorForSlot(input, EquipmentSlot.FEET)) {
            applyEnchant(event, level, input, RankineEnchantments.SNOW_DRIFTER);
        } else if (right == RankineItems.ICE_SKATES.get() && isArmorForSlot(input, EquipmentSlot.FEET)) {
            applyEnchant(event, level, input, RankineEnchantments.SPEED_SKATER);
        } else if (right == RankineItems.GAS_MASK.get() && isArmorForSlot(input, EquipmentSlot.HEAD)) {
            applyEnchant(event, level, input, RankineEnchantments.GAS_PROTECTION);
        } else if (right == RankineItems.FINS.get() && isArmorForSlot(input, EquipmentSlot.FEET)) {
            applyEnchant(event, level, input, RankineEnchantments.SWIFT_SWIMMER);
        } else if (right == RankineItems.GOGGLES.get() && isArmorForSlot(input, EquipmentSlot.FEET)) {
            // NOTE: 1.18.2 checked the FEET slot for goggles too — preserved verbatim.
            applyEnchant(event, level, input, RankineEnchantments.AQUA_LENSE);
        }
    }

    private static boolean isArmorForSlot(ItemStack stack, EquipmentSlot slot) {
        return stack.getItem() instanceof ArmorItem armorItem && armorItem.getEquipmentSlot() == slot;
    }

    private static void applyEnchant(AnvilUpdateEvent event, Level level, ItemStack input, ResourceKey<Enchantment> key) {
        event.setOutput(input.copy());
        if (RankineEnchantmentHelper.getLevel(level, key, event.getOutput()) != 1) {
            Holder<Enchantment> holder = RankineEnchantmentHelper.holder(level, key);
            event.getOutput().enchant(holder, 1);
            event.setCost(20);
        }
    }
}
