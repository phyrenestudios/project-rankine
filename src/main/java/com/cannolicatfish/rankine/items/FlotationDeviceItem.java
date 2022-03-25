package com.cannolicatfish.rankine.items;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item.Properties;

public class FlotationDeviceItem extends Item {
    public FlotationDeviceItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 20;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        Vec3 vector3d = playerIn.getDeltaMovement();
        double d0;
        d0 = Math.min(3D, vector3d.y + 0.6D);

        playerIn.setDeltaMovement(vector3d.x, d0, vector3d.z);
        playerIn.fallDistance = 0.0F;
        if (!playerIn.isInWater()) {
            return new InteractionResultHolder<>(InteractionResult.PASS, ItemStack.EMPTY);
        }
        return super.use(worldIn, playerIn, handIn);
    }

}
