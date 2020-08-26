package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.ModEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ReactiveItemEntity extends ItemEntity {

    private float radius;
    private boolean canBreakBlocks;

    public ReactiveItemEntity(EntityType<? extends ItemEntity> p_i50217_1_, World p_i50217_2_) {
        super(p_i50217_1_, p_i50217_2_);
        this.radius = 1f;
        this.canBreakBlocks = false;
    }

    public ReactiveItemEntity(World worldIn, double x, double y, double z) {
        this(ModEntityTypes.REACTIVE_ITEM, worldIn);
        this.setPosition(x, y, z);
        this.rotationYaw = this.rand.nextFloat() * 360.0F;
        this.radius = 1f;
        this.canBreakBlocks = false;
        this.setMotion(this.rand.nextDouble() * 0.2D - 0.1D, 0.2D, this.rand.nextDouble() * 0.2D - 0.1D);
    }

    public ReactiveItemEntity(World worldIn, double x, double y, double z, ItemStack stack) {
        super(ModEntityTypes.REACTIVE_ITEM,worldIn);
        this.setPosition(x, y, z);
        this.setItem(stack);
        this.radius = 1f;
        this.canBreakBlocks = false;
        this.lifespan = (stack.getItem() == null ? 6000 : stack.getEntityLifespan(worldIn));
    }

    public ReactiveItemEntity(World worldIn, double x, double y, double z, float radius, boolean canBreakBlocks, ItemStack stack) {
        super(ModEntityTypes.REACTIVE_ITEM,worldIn);
        this.setPosition(x, y, z);
        this.setItem(stack);
        this.radius = radius;
        this.canBreakBlocks = canBreakBlocks;
        this.lifespan = (stack.getItem() == null ? 6000 : stack.getEntityLifespan(worldIn));
    }

    @Override
    public void tick() {

        if (this.inWater)
        {
            BlockPos pos = this.getPosition();
            if (canBreakBlocks)
            {
                this.getEntityWorld().getWorld().createExplosion(null, pos.getX(), pos.getY() + 16 * .0625D, pos.getZ(), this.radius, Explosion.Mode.BREAK);
            } else {
                this.getEntityWorld().getWorld().createExplosion(null, pos.getX(), pos.getY() + 16 * .0625D, pos.getZ(), this.radius, Explosion.Mode.NONE);
            }
            this.remove();
        }
        super.tick();
    }

    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
