package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;

public class AnvilUpdateHandler {
    public static void specialEnchants( AnvilUpdateEvent event) {
        ItemStack input = event.getLeft();
        if (event.getRight().getItem() == RankineItems.SANDALS.get() && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getEquipmentSlot() == EquipmentSlotType.FEET) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.DUNE_WALKER, event.getOutput()) != 1) {
                event.getOutput().addEnchantment(RankineEnchantments.DUNE_WALKER, 1);
                event.setCost(20);
            }
        } else if (event.getRight().getItem() == RankineItems.SNOWSHOES.get() && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getEquipmentSlot() == EquipmentSlotType.FEET) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.SNOW_DRIFTER,event.getOutput()) != 1) {
                event.getOutput().addEnchantment(RankineEnchantments.SNOW_DRIFTER, 1);
                event.setCost(20);
            }
        } else if (event.getRight().getItem() == RankineItems.ICE_SKATES.get() && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getEquipmentSlot() == EquipmentSlotType.FEET) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.SPEED_SKATER,event.getOutput()) != 1) {
                event.getOutput().addEnchantment(RankineEnchantments.SPEED_SKATER, 1);
                event.setCost(20);
            }
        } else if (event.getRight().getItem() == RankineItems.GAS_MASK.get() && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getEquipmentSlot() == EquipmentSlotType.HEAD) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getEnchantmentLevel(RankineEnchantments.GAS_PROTECTION,event.getOutput()) != 1) {
                event.getOutput().addEnchantment(RankineEnchantments.GAS_PROTECTION, 1);
                event.setCost(20);
            }
        }
    }
}
