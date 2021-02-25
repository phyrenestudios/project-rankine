package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.List;

public class MagnetItem extends Item {

    int type;
    public MagnetItem(int type, Properties properties) {
        super(properties);
        this.type = type;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (type != 3) {
            float radius = type == 1 ? Config.MACHINES.ALNICO_MAGNET_RANGE.get() : Config.MACHINES.RARE_MAGNET_RANGE.get();
            List<ItemEntity> items = worldIn.getEntitiesWithinAABB(ItemEntity.class, playerIn.getBoundingBox().grow(radius, radius, radius));
            for (ItemEntity i : items) {
                i.setPickupDelay(0);
                i.onCollideWithPlayer(playerIn);
            }
            if (!playerIn.isCreative())
            {
                playerIn.getHeldItem(handIn).damageItem(1, playerIn, (entity) -> {
                    entity.sendBreakAnimation(handIn);
                });
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof PlayerEntity && type == 3){
            PlayerEntity player = (PlayerEntity) entityIn;
            List<ItemEntity> items = worldIn.getEntitiesWithinAABB(ItemEntity.class, entityIn.getBoundingBox().grow(2, 2, 2));
            for (ItemEntity i : items) {
                i.onCollideWithPlayer(player);
            }
        }

        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }
}
