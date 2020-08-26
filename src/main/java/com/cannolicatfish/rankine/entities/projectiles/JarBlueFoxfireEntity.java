package com.cannolicatfish.rankine.entities.projectiles;

import com.cannolicatfish.rankine.init.ModEntityTypes;
import com.cannolicatfish.rankine.init.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class JarBlueFoxfireEntity extends ProjectileItemEntity {
    public JarBlueFoxfireEntity(EntityType<? extends ProjectileItemEntity> p_i50217_1_, World p_i50217_2_) {
        super(p_i50217_1_, p_i50217_2_);
    }

    public JarBlueFoxfireEntity(World worldIn, LivingEntity throwerIn) {
        super(ModEntityTypes.JAR_BLUE_FOXFIRE, throwerIn, worldIn);
    }

    public JarBlueFoxfireEntity(World worldIn, double x, double y, double z) {
        super(ModEntityTypes.JAR_BLUE_FOXFIRE, x, y, z, worldIn);
    }

    protected Item getDefaultItem() {
        return ModItems.JAR_BLUE_FOXFIRE;
    }

    @OnlyIn(Dist.CLIENT)
    private IParticleData makeParticle() {
        ItemStack itemstack = this.func_213882_k();
        return (IParticleData)(itemstack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleData(ParticleTypes.ITEM, itemstack));
    }


    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            IParticleData iparticledata = this.makeParticle();

            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(iparticledata, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }


    protected void onImpact(RayTraceResult result) {
        BlockPos pos = this.getPosition();
        if (!this.world.isRemote) {
            this.getEntityWorld().getWorld().createExplosion(null, pos.getX(), pos.getY() + 16 * .0625D, pos.getZ(), 2.0F, Explosion.Mode.BREAK);
            this.remove();
        }

    }
}