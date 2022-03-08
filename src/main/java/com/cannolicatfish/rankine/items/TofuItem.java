package com.cannolicatfish.rankine.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TofuItem extends Item {
    public TofuItem(Properties properties) {
        super(properties);
    }

    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        if (!worldIn.isRemote()) entityLiving.clearActivePotions();

        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

}
