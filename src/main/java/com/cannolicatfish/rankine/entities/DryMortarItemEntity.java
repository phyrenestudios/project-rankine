package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class DryMortarItemEntity extends ItemEntity {

    public DryMortarItemEntity(EntityType<? extends ItemEntity> p_i50217_1_, Level p_i50217_2_) {
        super(p_i50217_1_, p_i50217_2_);
    }

    public DryMortarItemEntity(Level worldIn, double x, double y, double z) {
        this(EntityType.ITEM, worldIn);
        this.setPos(x, y, z);
        this.setYRot(this.random.nextFloat() * 360.0F);
        this.setDeltaMovement(this.random.nextDouble() * 0.2D - 0.1D, 0.2D, this.random.nextDouble() * 0.2D - 0.1D);
    }

    public DryMortarItemEntity(Level worldIn, double x, double y, double z, ItemStack stack) {
        super(EntityType.ITEM,worldIn);
        this.setPos(x, y, z);
        this.setItem(stack);
        this.lifespan = (stack.getItem() == null ? 6000 : stack.getEntityLifespan(worldIn));
    }

    public DryMortarItemEntity(Level worldIn, double x, double y, double z, float radius, boolean canBreakBlocks, ItemStack stack) {
        super(EntityType.ITEM,worldIn);
        this.setPos(x, y, z);
        this.setItem(stack);
        this.lifespan = (stack.getItem() == null ? 6000 : stack.getEntityLifespan(worldIn));
    }

    @Override
    public void tick() {

        if (this.wasTouchingWater) {
            BlockPos pos = this.blockPosition();
            Level worldIn = this.getCommandSenderWorld();
            if (!worldIn.isClientSide && !worldIn.restoringBlockSnapshots) {
                double d0 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
                double d1 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
                double d2 = (double) (worldIn.random.nextFloat() * 0.5F) + 0.25D;
                ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(RankineItems.MORTAR.get(),this.getItem().getCount()));
                itementity.setDefaultPickUpDelay();
                worldIn.addFreshEntity(itementity);
            }
            this.remove(RemovalReason.DISCARDED);
        }
        super.tick();
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
