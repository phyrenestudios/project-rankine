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

import net.minecraft.item.Item.Properties;

public class MagnetItem extends Item {

    int type;
    public MagnetItem(int type, Properties properties) {
        super(properties);
        this.type = type;
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (type != 4) {
            float radius = type * Config.MACHINES.MAGNET_RANGE.get();
            List<ItemEntity> items = worldIn.getEntitiesOfClass(ItemEntity.class, playerIn.getBoundingBox().inflate(radius, radius, radius));
            for (ItemEntity i : items) {
                i.setPickUpDelay(0);
                i.playerTouch(playerIn);
            }
            if (!playerIn.isCreative())
            {
                playerIn.getItemInHand(handIn).hurtAndBreak(1, playerIn, (entity) -> {
                    entity.broadcastBreakEvent(handIn);
                });
            }
        }

        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof PlayerEntity && type == 4){
            PlayerEntity player = (PlayerEntity) entityIn;
            List<ItemEntity> items = worldIn.getEntitiesOfClass(ItemEntity.class, entityIn.getBoundingBox().inflate(2, 2, 2));
            for (ItemEntity i : items) {
                i.playerTouch(player);
            }
        }

        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }
}
