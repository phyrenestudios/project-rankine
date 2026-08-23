package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.RankineEntityTypes;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class AlloyArrowEntity extends AbstractArrow {
    // 1.18.2 stored this but never applied it to the arrow's base damage; preserved 1:1.
    @SuppressWarnings("unused")
    private double damage = 2.0D;

    public AlloyArrowEntity(EntityType<? extends AlloyArrowEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    /**
     * @param firedFromWeapon the bow or crossbow this arrow was drawn from, or null when it was not
     *                        fired from one. Every bow/crossbow enchantment in 1.21.1 is gated on
     *                        this field: {@code AbstractArrow}'s constructor only reads Piercing and
     *                        only runs {@code EnchantmentHelper#onProjectileSpawned} (Flame's
     *                        ignition, Power's base-damage bump) when it is non-null,
     *                        {@code onHitEntity} only calls {@code EnchantmentHelper#modifyDamage}
     *                        when {@code getWeaponItem() != null}, and {@code doKnockback} returns a
     *                        flat 0 for Punch otherwise. 1.18.2 had no such field — vanilla
     *                        {@code BowItem#releaseUsing} applied all four to whatever
     *                        {@code ArrowItem#createArrow} returned, so alloy arrows behaved exactly
     *                        like vanilla ones. Hard-coding null here silently stripped Power,
     *                        Punch, Flame (bows) and Piercing (crossbows) from every alloy arrow.
     *                        Must be null rather than empty: {@code AbstractArrow} throws on an
     *                        empty weapon stack.
     */
    public AlloyArrowEntity(Level worldIn, ItemStack arrowStackIn, LivingEntity shooter, float damageIn,
                            @Nullable ItemStack firedFromWeapon) {
        // 1.21.1: the pickup stack is passed to the AbstractArrow constructor (replaces the old arrowStack field)
        super(RankineEntityTypes.ALLOY_ARROW.get(), shooter, worldIn, arrowStackIn.copy(), firedFromWeapon);
        this.damage = damageIn;
    }

    // 1.21.1: getAddEntityPacket/NetworkHooks override removed — vanilla handles projectile spawn packets.

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(RankineItems.ALLOY_ARROW.get());
    }
}
