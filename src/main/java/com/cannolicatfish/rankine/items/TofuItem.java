package com.cannolicatfish.rankine.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item.Properties;

public class TofuItem extends Item {
    public TofuItem(Properties properties) {
        super(properties);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        if (!worldIn.isClientSide()) entityLiving.removeAllEffects();

        return super.finishUsingItem(stack, worldIn, entityLiving);
    }

}
