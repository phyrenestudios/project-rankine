package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.AnvilUpdateEvent;

public class AnvilUpdateHandler {
    public static void specialEnchants(AnvilUpdateEvent event) {
        ItemStack input = event.getLeft();
        if (event.getRight().getItem() == RankineItems.SANDALS.get() && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getSlot() == EquipmentSlot.FEET) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.DUNE_WALKER.get(),event.getOutput()) != 1) {
                event.getOutput().enchant(RankineEnchantments.DUNE_WALKER.get(), 1);
                event.setCost(20);
            }
        } else if (event.getRight().getItem() == RankineItems.SNOWSHOES.get() && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getSlot() == EquipmentSlot.FEET) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.SNOW_DRIFTER.get(),event.getOutput()) != 1) {
                event.getOutput().enchant(RankineEnchantments.SNOW_DRIFTER.get(), 1);
                event.setCost(20);
            }
        } else if (event.getRight().getItem() == RankineItems.ICE_SKATES.get() && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getSlot() == EquipmentSlot.FEET) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.SPEED_SKATER.get(),event.getOutput()) != 1) {
                event.getOutput().enchant(RankineEnchantments.SPEED_SKATER.get(), 1);
                event.setCost(20);
            }
        } else if (event.getRight().getItem() == RankineItems.GAS_MASK.get() && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getSlot() == EquipmentSlot.HEAD) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.GAS_PROTECTION.get(),event.getOutput()) != 1) {
                event.getOutput().enchant(RankineEnchantments.GAS_PROTECTION.get(), 1);
                event.setCost(20);
            }
        } else if (event.getRight().getItem() == RankineItems.FINS.get() && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getSlot() == EquipmentSlot.FEET) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.SWIFT_SWIMMER.get(),event.getOutput()) != 1) {
                event.getOutput().enchant(RankineEnchantments.SWIFT_SWIMMER.get(), 1);
                event.setCost(20);
            }
        } else if (event.getRight().getItem() == RankineItems.GOGGLES.get() && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getSlot() == EquipmentSlot.FEET) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.AQUA_LENSE.get(),event.getOutput()) != 1) {
                event.getOutput().enchant(RankineEnchantments.AQUA_LENSE.get(), 1);
                event.setCost(20);
            }
        }
    }
}
