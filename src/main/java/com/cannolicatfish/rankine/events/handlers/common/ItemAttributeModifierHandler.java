package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.RankineAttributes;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.items.alloys.AlloyArmorItem;
import com.cannolicatfish.rankine.items.alloys.IAlloyTool;
import com.cannolicatfish.rankine.items.tools.CrowbarItem;
import com.cannolicatfish.rankine.items.tools.HammerItem;
import com.cannolicatfish.rankine.items.tools.KnifeItem;
import com.cannolicatfish.rankine.items.tools.SpearItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.ItemAttributeModifierEvent;

import java.util.UUID;

public class ItemAttributeModifierHandler {
    public static void onItemAttributeModification(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof IAlloyTool && event.getSlotType() == EquipmentSlot.MAINHAND && ((IAlloyTool) stack.getItem()).isAlloyInit(stack)) {
            IAlloyTool alloyTool = (IAlloyTool) stack.getItem();

            event.addModifier(Attributes.ATTACK_DAMAGE,new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fc1"), "Rankine Damage modifier",
                    alloyTool.getAlloyAttackDamage(stack),
                    AttributeModifier.Operation.ADDITION));
            event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fc2"), "Rankine Attspeed modifier",
                    alloyTool.getAlloyAttackSpeed(stack),
                    AttributeModifier.Operation.ADDITION));
        }

        if (stack.getItem() instanceof AlloyArmorItem && ((AlloyArmorItem) stack.getItem()).isAlloyInit(stack) && stack.getItem() instanceof ArmorItem && event.getSlotType() == ((ArmorItem)stack.getItem()).getSlot()) {
            AlloyArmorItem alloyArmor = (AlloyArmorItem) stack.getItem();
            String character = "a";
            switch (event.getSlotType().getFilterFlag()) {
                case 0:
                    character = "a";
                    break;
                case 1:
                    character = "b";
                    break;
                case 2:
                    character = "c";
                    break;
                case 3:
                    character = "d";
                    break;
            }
            String slot1 = character + "0";
            String slot2 = character + "1";
            String slot3 = character + "2";
            int tough = alloyArmor.getAlloyArmorToughness(stack);
            int def = alloyArmor.getAlloyDamageResistance(stack);
            float kr = alloyArmor.getKnockbackResistance(stack);
            event.addModifier(Attributes.ARMOR_TOUGHNESS,new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe2a"+slot1), "Rankine Armor Toughness modifier",
                    tough,
                    AttributeModifier.Operation.ADDITION));
            event.addModifier(Attributes.ARMOR, new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe2a"+slot2), "Rankine Armor modifier",
                    def,
                    AttributeModifier.Operation.ADDITION));
            event.addModifier(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe2a"+slot3), "Rankine Knockback Resist modifier",
                    kr,
                    AttributeModifier.Operation.ADDITION));
        }
        if ((stack.getItem() instanceof HammerItem)) {
            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.SWING.get(),stack) > 0 && event.getSlotType() == EquipmentSlot.MAINHAND) {
                event.addModifier(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fc3"), "Rankine Swing modifier",
                        0.5D * EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.SWING.get(),stack),
                        AttributeModifier.Operation.ADDITION));
            }
        }

        if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ANTIQUATED.get(),stack) > 0 && (event.getSlotType() == EquipmentSlot.MAINHAND || event.getSlotType() == EquipmentSlot.OFFHAND)) {
            event.addModifier(Attributes.LUCK, new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fd1"), "Rankine Antiquated modifier",
                    EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ANTIQUATED.get(),stack),
                    AttributeModifier.Operation.ADDITION));
        }
        if ((stack.getItem() instanceof CrowbarItem || stack.getItem().equals(RankineItems.BUILDING_TOOL.get())) && (event.getSlotType() == EquipmentSlot.MAINHAND || event.getSlotType() == EquipmentSlot.OFFHAND)) {
            event.addModifier(RankineAttributes.REACH_DISTANCE, new AttributeModifier(RankineAttributes.REACH_MODIFIER,"Reach modifier", 1, AttributeModifier.Operation.ADDITION));
        }
        if (stack.getItem() instanceof SpearItem && event.getSlotType() == EquipmentSlot.MAINHAND) {
            event.addModifier(RankineAttributes.ATTACK_RANGE, new AttributeModifier(RankineAttributes.SPEAR_RANGE_MODIFIER,"Weapon modifier", 1, AttributeModifier.Operation.ADDITION));
        }
        if (stack.getItem() instanceof KnifeItem && event.getSlotType() == EquipmentSlot.MAINHAND) {
            event.addModifier(RankineAttributes.ATTACK_RANGE, new AttributeModifier(RankineAttributes.KNIFE_RANGE_MODIFIER,"Weapon modifier", -1, AttributeModifier.Operation.ADDITION));
        }
        if (stack.getItem() == RankineItems.TOTEM_OF_PROMISING.get() && event.getSlotType() == EquipmentSlot.OFFHAND) {
            event.addModifier(Attributes.LUCK, new AttributeModifier(UUID.fromString("3c4a1c57-ed5a-482e-946e-eb0b00fe5fd1"), "Rankine Totem modifier",
                    2,
                    AttributeModifier.Operation.ADDITION));
        }
        /*
        if ((EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.DUNE_WALKER.get(), stack) > 0 || stack.is(RankineItems.SANDALS.get())) && event.getSlotType() == EquipmentSlot.FEET) {
            event.addModifier(RankineAttributes.STEP_HEIGHT, RankineAttributes.STEP);
        }

         */
    }
}
