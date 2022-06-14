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
            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.DUNE_WALKER,event.getOutput()) != 1) {
                event.getOutput().enchant(RankineEnchantments.DUNE_WALKER, 1);
                event.setCost(20);
            }
        } else if (event.getRight().getItem() == RankineItems.SNOWSHOES.get() && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getSlot() == EquipmentSlot.FEET) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.SNOW_DRIFTER,event.getOutput()) != 1) {
                event.getOutput().enchant(RankineEnchantments.SNOW_DRIFTER, 1);
                event.setCost(20);
            }
        } else if (event.getRight().getItem() == RankineItems.ICE_SKATES.get() && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getSlot() == EquipmentSlot.FEET) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.SPEED_SKATER,event.getOutput()) != 1) {
                event.getOutput().enchant(RankineEnchantments.SPEED_SKATER, 1);
                event.setCost(20);
            }
        } else if (event.getRight().getItem() == RankineItems.GAS_MASK.get() && input.getItem() instanceof ArmorItem && ((ArmorItem)input.getItem()).getSlot() == EquipmentSlot.HEAD) {
            event.setOutput(input.copy());
            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.GAS_PROTECTION,event.getOutput()) != 1) {
                event.getOutput().enchant(RankineEnchantments.GAS_PROTECTION, 1);
                event.setCost(20);
            }
        }
    }
}
