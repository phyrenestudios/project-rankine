package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.init.Config;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

import java.util.List;

import net.minecraft.world.item.Item.Properties;

public class MagnetItem extends Item {

    int type;
    public MagnetItem(int type, Properties properties) {
        super(properties);
        this.type = type;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
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
    public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof Player && type == 4){
            Player player = (Player) entityIn;
            List<ItemEntity> items = worldIn.getEntitiesOfClass(ItemEntity.class, entityIn.getBoundingBox().inflate(2, 2, 2));
            for (ItemEntity i : items) {
                i.playerTouch(player);
            }
        }

        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }
}
