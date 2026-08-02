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
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

/**
 * 1.21.1 rework of ItemAttributeModifierEvent:
 * - the event is no longer fired once per slot; {@code getSlotType()} is gone and every modifier is
 *   registered with an {@link EquipmentSlotGroup} instead. The old per-slot branches become groups
 *   (MAINHAND / OFFHAND / HAND / the armor item's own slot).
 * - AttributeModifier is identified by a ResourceLocation instead of a UUID, and the attribute is a
 *   {@code Holder<Attribute>}.
 * - The event carries no Level, so enchantment levels are read straight off the stack's
 *   ItemEnchantments component (holders compared by ResourceKey) instead of via
 *   RankineEnchantmentHelper, which needs a registry access.
 */
public class ItemAttributeModifierHandler {

    private static final ResourceLocation ALLOY_DAMAGE = rl("alloy_attack_damage");
    private static final ResourceLocation ALLOY_SPEED = rl("alloy_attack_speed");
    private static final ResourceLocation SWING_MODIFIER = rl("swing_modifier");
    private static final ResourceLocation ANTIQUATED_MODIFIER = rl("antiquated_modifier");
    private static final ResourceLocation TOTEM_LUCK_MODIFIER = rl("totem_of_promising_luck");

    private static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath("rankine", path);
    }

    private static ResourceLocation armorId(ArmorItem armorItem, String suffix) {
        return rl("alloy_armor/" + armorItem.getEquipmentSlot().getName() + "_" + suffix);
    }

    /** Registry-free enchantment level lookup — the stack stores resolved Holders. */
    private static int levelOf(ItemStack stack, ResourceKey<Enchantment> key) {
        for (Object2IntMap.Entry<Holder<Enchantment>> entry : EnchantmentHelper.getEnchantmentsForCrafting(stack).entrySet()) {
            if (entry.getKey().is(key)) {
                return entry.getIntValue();
            }
        }
        return 0;
    }

    public static void onItemAttributeModification(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();

        if (stack.getItem() instanceof IAlloyTool alloyTool && alloyTool.isAlloyInit(stack)) {
            event.addModifier(Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(ALLOY_DAMAGE, alloyTool.getAlloyAttackDamage(stack), AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.MAINHAND);
            event.addModifier(Attributes.ATTACK_SPEED,
                    new AttributeModifier(ALLOY_SPEED, alloyTool.getAlloyAttackSpeed(stack), AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.MAINHAND);
        }

        if (stack.getItem() instanceof AlloyArmorItem alloyArmor && alloyArmor.isAlloyInit(stack) && stack.getItem() instanceof ArmorItem armorItem) {
            EquipmentSlotGroup group = EquipmentSlotGroup.bySlot(armorItem.getEquipmentSlot());
            int tough = alloyArmor.getAlloyArmorToughness(stack);
            int def = alloyArmor.getAlloyDamageResistance(stack);
            float kr = alloyArmor.getKnockbackResistance(stack);
            event.addModifier(Attributes.ARMOR_TOUGHNESS,
                    new AttributeModifier(armorId(armorItem, "toughness"), tough, AttributeModifier.Operation.ADD_VALUE), group);
            event.addModifier(Attributes.ARMOR,
                    new AttributeModifier(armorId(armorItem, "armor"), def, AttributeModifier.Operation.ADD_VALUE), group);
            event.addModifier(Attributes.KNOCKBACK_RESISTANCE,
                    new AttributeModifier(armorId(armorItem, "knockback_resistance"), kr, AttributeModifier.Operation.ADD_VALUE), group);
        }

        if (stack.getItem() instanceof HammerItem) {
            int swing = levelOf(stack, RankineEnchantments.SWING);
            if (swing > 0) {
                event.addModifier(Attributes.ATTACK_SPEED,
                        new AttributeModifier(SWING_MODIFIER, 0.5D * swing, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND);
            }
        }

        int antiquated = levelOf(stack, RankineEnchantments.ANTIQUATED);
        if (antiquated > 0) {
            // old code: MAINHAND || OFFHAND -> EquipmentSlotGroup.HAND
            event.addModifier(Attributes.LUCK,
                    new AttributeModifier(ANTIQUATED_MODIFIER, antiquated, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.HAND);
        }

        if (stack.getItem() instanceof CrowbarItem) {
            int leverage = levelOf(stack, RankineEnchantments.LEVERAGE);
            event.addModifier(RankineAttributes.REACH_DISTANCE,
                    new AttributeModifier(RankineAttributes.REACH_MODIFIER, leverage * 0.5D + 1.0D, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.HAND);
        }
        if (stack.getItem() instanceof SpearItem) {
            event.addModifier(RankineAttributes.ATTACK_RANGE,
                    new AttributeModifier(RankineAttributes.SPEAR_RANGE_MODIFIER, 1, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.MAINHAND);
        }
        if (stack.getItem() instanceof KnifeItem) {
            event.addModifier(RankineAttributes.ATTACK_RANGE,
                    new AttributeModifier(RankineAttributes.KNIFE_RANGE_MODIFIER, -1, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.MAINHAND);
        }
        if (stack.getItem() == RankineItems.TOTEM_OF_PROMISING.get()) {
            event.addModifier(Attributes.LUCK,
                    new AttributeModifier(TOTEM_LUCK_MODIFIER, 2, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.OFFHAND);
        }
        /*
        1.18.2 had this commented out; kept commented for parity:
        if (levelOf(stack, RankineEnchantments.DUNE_WALKER) > 0 || stack.is(RankineItems.SANDALS.get()))
            event.addModifier(RankineAttributes.STEP_HEIGHT, RankineAttributes.STEP, EquipmentSlotGroup.FEET);
         */
    }
}
