package com.cannolicatfish.rankine.items.alloys;

import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class AlloySword extends SwordItem {
    private float wmodifier;
    private boolean heat_resistant;
    private boolean corr_resistant;
    private float toughness;
    private final float attackDamage;
    private final float attackSpeed;
    public AlloySword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.attackSpeed = attackSpeedIn;
        this.attackDamage = (float)attackDamageIn + tier.getAttackDamage();
        this.wmodifier = tier.getAttackDamage()/2;
        this.heat_resistant = false;
        this.corr_resistant = false;
        this.toughness = .05f;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Random rand = new Random();
        int i = rand.nextFloat() < toughness ? 1 : 0;
        if ((!heat_resistant && (attacker.isInLava() || attacker.getFireTimer() > 0)) || (!corr_resistant && attacker.isWet()))
        {
            stack.damageItem(2 + i, attacker, (p_220038_0_) -> {
                p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
            replaceModifier(getAttributeModifiers(EquipmentSlotType.MAINHAND), Attributes.ATTACK_DAMAGE,ATTACK_DAMAGE_MODIFIER,getWear(stack));
        } else {
            stack.damageItem(1 + i, attacker, (p_220038_0_) -> {
                p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
            replaceModifier(getAttributeModifiers(EquipmentSlotType.MAINHAND), Attributes.ATTACK_DAMAGE,ATTACK_DAMAGE_MODIFIER,getWear(stack));
        }
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        Random rand = new Random();
        int i = rand.nextFloat() < toughness ? 1 : 0;
        if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
            if ((!heat_resistant && (entityLiving.isInLava() || entityLiving.getFireTimer() > 0)) || (!corr_resistant && entityLiving.isWet()))
            {
                stack.damageItem(4 + i, entityLiving, (p_220038_0_) -> {
                    p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                });
                replaceModifier(getAttributeModifiers(EquipmentSlotType.MAINHAND), Attributes.ATTACK_DAMAGE,ATTACK_DAMAGE_MODIFIER,getWear(stack));
            } else {
                stack.damageItem(2 + i, entityLiving, (p_220038_0_) -> {
                    p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                });
                replaceModifier(getAttributeModifiers(EquipmentSlotType.MAINHAND),Attributes.ATTACK_DAMAGE,ATTACK_DAMAGE_MODIFIER,getWear(stack));
            }

        }
        return true;
    }

    private float getWear(ItemStack stack)
    {
        float current_dur = this.getDamage(stack);
        float max_dur = this.getTier().getMaxUses();
        float wear_modifier = wmodifier - wmodifier*((max_dur - current_dur)/max_dur);
        System.out.println(wear_modifier);
        return wear_modifier;
    }

    private void replaceModifier(Multimap<Attribute, AttributeModifier> modifierMultimap, Attribute attribute, UUID id, double multiplier)
    {
        // Get the modifiers for the specified attribute
        final Collection<AttributeModifier> modifiers = modifierMultimap.get(attribute);

        // Find the modifier with the specified ID, if any
        final Optional<AttributeModifier> modifierOptional = modifiers.stream().filter(attributeModifier -> attributeModifier.getID().equals(id)).findFirst();

        if (modifierOptional.isPresent())
        {
            final AttributeModifier modifier = modifierOptional.get();

            modifiers.remove(modifier); // Remove it
            modifiers.add(new AttributeModifier(modifier.getID(), modifier.getName(), this.attackDamage - multiplier, modifier.getOperation()));
            System.out.println("New Attack Damage:");
            System.out.println(this.attackDamage - multiplier);
        }
    }



}
