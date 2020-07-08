package com.cannolicatfish.rankine.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class LuckPendantItem extends Item{

    public LuckPendantItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.LUCK, 5 * 20, 3));
    }





}
