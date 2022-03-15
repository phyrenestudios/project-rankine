package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.entities.CarcassEntity;
import com.cannolicatfish.rankine.entities.EnderballEntity;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import com.cannolicatfish.rankine.entities.CannonballEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Predicate;

import net.minecraft.item.Item.Properties;

public class BlunderbussItem extends ShootableItem implements IVanishable {
    public static final Predicate<ItemStack> CANNONBALLS = (p_220002_0_) -> {
        return RankineTags.Items.CANNONBALLS.contains(p_220002_0_.getItem());
    };
    public BlunderbussItem(Properties properties) {
        super(properties);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return CANNONBALLS;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity && timeLeft < 71975) {
            PlayerEntity playerentity = (PlayerEntity)entityLiving;
            boolean flag = playerentity.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
            ItemStack itemstack = playerentity.getProjectile(stack);

            int i = this.getUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(RankineItems.CANNONBALL.get());
                }

                float f = getBulletVelocity(i);
                if (!((double)f < 0.1D)) {
                    if (!worldIn.isClientSide) {
                        Vector3d vector3d = playerentity.getViewVector(1.0F);
                        playerentity.knockback(1,-vector3d.x,-vector3d.z);
                        Random random = worldIn.getRandom();
                        Vector3d inaccuracy = new Vector3d((random.nextInt(20)-10)/100f,(random.nextInt(20) - 10)/100f,(random.nextInt(20) - 10)/100f);
                        if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ACCURACY,stack) > 0) {
                            inaccuracy = inaccuracy.scale(1 - 0.33*EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ACCURACY,stack));
                        }
                        double d2 = playerentity.getX() - (playerentity.getX() + vector3d.x) + inaccuracy.x;
                        double d3 = playerentity.getY(0.5D) - (playerentity.getY(0.5D) + vector3d.y) + inaccuracy.y;
                        double d4 = playerentity.getZ() - (playerentity.getZ() + vector3d.z) + inaccuracy.z;

                        DamagingProjectileEntity cannonball;
                        if (itemstack.getItem() == RankineItems.CANNONBALL.get()) {
                            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDPLAY,stack) > 0) {
                                cannonball = new EnderballEntity(worldIn, playerentity,-d2*2, -d3*2, -d4*2);
                            } else {
                                cannonball = new CannonballEntity(worldIn, playerentity,-d2, -d3, -d4);
                            }
                        } else if (itemstack.getItem() == RankineItems.CARCASS.get()) {
                            cannonball = new CarcassEntity(worldIn, playerentity,-d2, -d3, -d4);
                        } else if (itemstack.getItem() == Items.FIRE_CHARGE) {
                            cannonball = new SmallFireballEntity(worldIn, playerentity,-d2, -d3, -d4);
                        } else if (itemstack.getItem() == RankineItems.ENDERBALL.get()) {
                            cannonball = new EnderballEntity(worldIn, playerentity,-d2*2, -d3*2, -d4*2);
                        }else {
                            cannonball = new CannonballEntity(worldIn, playerentity,-d2, -d3, -d4);
                        }
                        cannonball.setPos(playerentity.getX() + vector3d.x, playerentity.getY(0.5D) + 0.5D, cannonball.getZ() + vector3d.z);
                        cannonball = customProjectile(cannonball);

                        stack.hurtAndBreak(1, playerentity, (p_220009_1_) -> {
                            p_220009_1_.broadcastBreakEvent(playerentity.getUsedItemHand());
                        });

                        worldIn.addFreshEntity(cannonball);
                    }

                    worldIn.playSound((PlayerEntity)null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), SoundEvents.GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!playerentity.abilities.instabuild) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            playerentity.inventory.removeItem(itemstack);
                        }
                    }

                    playerentity.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    public static float getBulletVelocity(int charge) {
        float f = (float)charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 2.0F) {
            f = 2.0F;
        }

        return f;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.BOW;
    }

    public DamagingProjectileEntity customProjectile(DamagingProjectileEntity arrow) {
        return arrow;
    }

    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        boolean flag = !playerIn.getProjectile(itemstack).isEmpty();

        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
        if (ret != null) return ret;

        if (!playerIn.abilities.instabuild && !flag) {
            return flag ? new ActionResult<>(ActionResultType.PASS, itemstack) : new ActionResult<>(ActionResultType.FAIL, itemstack);
        } else {
            playerIn.startUsingItem(handIn);
            return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
        }
    }
}
