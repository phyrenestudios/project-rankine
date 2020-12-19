package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.entities.ThoriumArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ThoriumArrowItem extends ArrowItem {
    public ThoriumArrowItem(Properties builder) {
        super(builder);
    }

    @Override
    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        ThoriumArrowEntity arrowentity = new ThoriumArrowEntity(worldIn, shooter);
        return arrowentity;
    }
}
