package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PendantItem extends Item{

    public PendantItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityIn;
            if (player.getHeldItemOffhand().getItem() == ModItems.LUCK_PENDANT) {
                if (random.nextFloat() < 0.05) {
                    stack.damageItem(1, player, (livingEntity) -> livingEntity.sendBreakAnimation(EquipmentSlotType.OFFHAND));
                }
            }
            if (player.getHeldItemOffhand().getItem() == ModItems.SPEED_PENDANT) {
                entityIn.setMotion(entityIn.getMotion().mul(1.2D, 1.0D, 1.2D));
                if (random.nextFloat() < 0.05) {
                    stack.damageItem(1, player, (livingEntity) -> livingEntity.sendBreakAnimation(EquipmentSlotType.OFFHAND));
                }
            }
        }
    }


}
