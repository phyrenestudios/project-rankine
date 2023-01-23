package com.cannolicatfish.rankine.items;

import com.cannolicatfish.rankine.entities.CannonballEntity;
import com.cannolicatfish.rankine.entities.CarcassEntity;
import com.cannolicatfish.rankine.entities.EnderballEntity;
import com.cannolicatfish.rankine.init.RankineEnchantments;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;
import java.util.function.Predicate;

public class BlunderbussItem extends ProjectileWeaponItem implements Vanishable {
    public static final Predicate<ItemStack> CANNONBALLS = (p_220002_0_) -> {
        return p_220002_0_.is(RankineTags.Items.CANNONBALLS);
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

    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player && timeLeft < 71975) {
            Player playerentity = (Player)entityLiving;
            boolean flag = playerentity.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
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
                        Vec3 vector3d = playerentity.getViewVector(1.0F);
                        playerentity.knockback(1,-vector3d.x,-vector3d.z);
                        Random random = worldIn.getRandom();
                        Vec3 inaccuracy = new Vec3((random.nextInt(20)-10)/100f,(random.nextInt(20) - 10)/100f,(random.nextInt(20) - 10)/100f);
                        if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ACCURACY.get(),stack) > 0) {
                            inaccuracy = inaccuracy.scale(1 - 0.33*EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ACCURACY.get(),stack));
                        }
                        double d2 = playerentity.getX() - (playerentity.getX() + vector3d.x) + inaccuracy.x;
                        double d3 = playerentity.getY(0.5D) - (playerentity.getY(0.5D) + vector3d.y) + inaccuracy.y;
                        double d4 = playerentity.getZ() - (playerentity.getZ() + vector3d.z) + inaccuracy.z;

                        AbstractHurtingProjectile cannonball;
                        if (itemstack.getItem() == RankineItems.CANNONBALL.get()) {
                            if (EnchantmentHelper.getItemEnchantmentLevel(RankineEnchantments.ENDPLAY.get(),stack) > 0) {
                                cannonball = new EnderballEntity(worldIn, playerentity,-d2*2, -d3*2, -d4*2);
                            } else {
                                cannonball = new CannonballEntity(worldIn, playerentity,-d2, -d3, -d4);
                            }
                        } else if (itemstack.getItem() == RankineItems.CARCASS.get()) {
                            cannonball = new CarcassEntity(worldIn, playerentity,-d2, -d3, -d4);
                        } else if (itemstack.getItem() == Items.FIRE_CHARGE) {
                            cannonball = new SmallFireball(worldIn, playerentity,-d2, -d3, -d4);
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

                    worldIn.playSound((Player)null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0F, 1.0F / (worldIn.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!playerentity.getAbilities().instabuild) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            playerentity.getInventory().removeItem(itemstack);
                        }
                    }
                    if (playerentity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) < 1 && !playerentity.isCreative()) {
                        playerentity.knockback(1 - playerentity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE),playerentity.getLookAngle().x(),playerentity.getLookAngle().z());
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
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    public AbstractHurtingProjectile customProjectile(AbstractHurtingProjectile arrow) {
        return arrow;
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        boolean flag = !playerIn.getProjectile(itemstack).isEmpty();

        InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
        if (ret != null) return ret;

        if (!playerIn.getAbilities().instabuild && !flag) {
            return flag ? new InteractionResultHolder<>(InteractionResult.PASS, itemstack) : new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
        } else {
            playerIn.startUsingItem(handIn);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
        }
    }
}
