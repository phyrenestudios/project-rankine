package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.Config;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
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
        float radius = type == 1 ? Config.ALNICO_MAGNET_RANGE.get() : Config.RARE_MAGNET_RANGE.get();
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
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

}
