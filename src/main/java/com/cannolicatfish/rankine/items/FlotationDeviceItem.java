package com.cannolicatfish.rankine.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class FlotationDeviceItem extends Item {
    public FlotationDeviceItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 20;
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        Vector3d vector3d = playerIn.getDeltaMovement();
        double d0;
        d0 = Math.min(3D, vector3d.y + 0.6D);

        playerIn.setDeltaMovement(vector3d.x, d0, vector3d.z);
        playerIn.fallDistance = 0.0F;
        if (!playerIn.isInWater()) {
            return new ActionResult<>(ActionResultType.PASS, ItemStack.EMPTY);
        }
        return super.use(worldIn, playerIn, handIn);
    }

}
