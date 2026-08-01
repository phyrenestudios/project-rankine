package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.RankineEntityTypes;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AlloyArrowEntity extends AbstractArrow {
    // 1.18.2 stored this but never applied it to the arrow's base damage; preserved 1:1.
    @SuppressWarnings("unused")
    private double damage = 2.0D;

    public AlloyArrowEntity(EntityType<? extends AlloyArrowEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public AlloyArrowEntity(Level worldIn, ItemStack arrowStackIn, LivingEntity shooter, float damageIn) {
        // 1.21.1: the pickup stack is passed to the AbstractArrow constructor (replaces the old arrowStack field)
        super(RankineEntityTypes.ALLOY_ARROW.get(), shooter, worldIn, arrowStackIn.copy(), null);
        this.damage = damageIn;
    }

    // 1.21.1: getAddEntityPacket/NetworkHooks override removed — vanilla handles projectile spawn packets.

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(RankineItems.ALLOY_ARROW.get());
    }
}
