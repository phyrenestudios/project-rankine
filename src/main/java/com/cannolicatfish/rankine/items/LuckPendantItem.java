package com.cannolicatfish.rankine.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LuckPendantItem extends Item{

    public LuckPendantItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityIn;
            if (player.getHeldItemOffhand() == stack) {
                if (random.nextFloat() < 0.05) {
                    stack.damageItem(1, player, (livingEntity) -> livingEntity.sendBreakAnimation(EquipmentSlotType.OFFHAND));
                }
            }
        }
    }


}
