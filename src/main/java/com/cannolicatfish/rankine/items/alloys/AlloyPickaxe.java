package com.cannolicatfish.rankine.items.alloys;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.Random;

public class AlloyPickaxe extends PickaxeItem {
    private float wmodifier;
    private boolean heat_resistant;
    private boolean corr_resistant;
    private float toughness;
    public AlloyPickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        this.wmodifier = tier.getEfficiency()/2;
        this.heat_resistant = false;
        this.corr_resistant = false;
        this.toughness = .05f;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        Material material = state.getMaterial();
        float current_dur = this.getDamage(stack);
        float max_dur = this.getTier().getMaxUses();
        float wear_modifier = wmodifier - wmodifier*((max_dur - current_dur)/max_dur);
        System.out.println(wear_modifier);
        return material != Material.IRON && material != Material.ANVIL && material != Material.ROCK ? super.getDestroySpeed(stack, state) : (this.efficiency - wear_modifier);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        Random rand = new Random();
        int i = rand.nextFloat() < toughness ? 1 : 0;
        if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
            if ((!heat_resistant && (worldIn.getDimension().getType() == DimensionType.THE_NETHER || entityLiving.isInLava() || entityLiving.getFireTimer() > 0)) || (!corr_resistant && entityLiving.isWet()))
            {
                stack.damageItem(2 + i, entityLiving, (p_220038_0_) -> {
                    p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                });
            } else {
                stack.damageItem(1 + i, entityLiving, (p_220038_0_) -> {
                    p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
                });
            }

        }
        return true;
    }
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Random rand = new Random();
        int i = rand.nextFloat() < toughness ? 1 : 0;
        if ((!heat_resistant && (attacker.getEntityWorld().getDimension().getType() == DimensionType.THE_NETHER || attacker.isInLava() || attacker.getFireTimer() > 0)) || (!corr_resistant && attacker.isWet()))
        {
            stack.damageItem(4 + i, attacker, (p_220038_0_) -> {
                p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        } else {
            stack.damageItem(2 + i, attacker, (p_220038_0_) -> {
                p_220038_0_.sendBreakAnimation(EquipmentSlotType.MAINHAND);
            });
        }
        return true;
    }


}
