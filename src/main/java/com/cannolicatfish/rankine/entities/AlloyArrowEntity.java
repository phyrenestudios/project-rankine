package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.RankineEntityTypes;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

public class AlloyArrowEntity extends AbstractArrow {
    private double damage = 2.0D;
    private ItemStack arrowStack = new ItemStack(RankineItems.ALLOY_ARROW.get());

    public AlloyArrowEntity(EntityType<? extends AlloyArrowEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public AlloyArrowEntity(Level worldIn, ItemStack arrowStackIn, LivingEntity shooter, float damageIn) {
        super(RankineEntityTypes.ALLOY_ARROW.get(), shooter, worldIn);
        this.arrowStack = arrowStackIn.copy();
        this.damage = damageIn;
    }

    @OnlyIn(Dist.CLIENT)
    public AlloyArrowEntity(PlayMessages.SpawnEntity spawnEntity, Level world, EntityType<AlloyArrowEntity> e) {
        super(e, world);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected ItemStack getPickupItem() {
        return this.arrowStack.copy();
    }
}
