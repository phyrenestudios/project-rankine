package com.cannolicatfish.rankine.entities;

import com.cannolicatfish.rankine.init.RankineEntityTypes;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class MagnesiumArrowEntity extends AbstractArrowEntity {
    private boolean spawnedDistraction;
    private LivingEntity distraction;
    protected MagnesiumArrowEntity(EntityType<? extends ArrowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public MagnesiumArrowEntity(World worldIn, LivingEntity shooter) {
        super(RankineEntityTypes.MAGNESIUM_ARROW, shooter, worldIn);
    }

    @OnlyIn(Dist.CLIENT)
    public MagnesiumArrowEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world, EntityType<MagnesiumArrowEntity> e) {
        super(e, world);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        Entity entity = this.func_234616_v_();
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(RankineItems.MAGNESIUM_ARROW.get());
    }

    @Override
    public void tick() {
        super.tick();
        if (this.inGround) {
            if (this.timeInGround % 100 == 0) {
                this.spawnParticles(1);
                List<LivingEntity> mobEntities = this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(5, 5, 5));
                for (LivingEntity mob : mobEntities) {
                    mob.addPotionEffect(new EffectInstance(Effects.BLINDNESS,120));
                }
            }
        } else {
            if (this.ticksExisted % 100 == 0) {
                this.spawnParticles(1);
            }
        }
    }

    @Override
    protected void arrowHit(LivingEntity living) {
        super.arrowHit(living);
        if (living instanceof CreeperEntity)
        {
            ((CreeperEntity)living).ignite();
        } else {
            List<MobEntity> mobEntities = this.world.getEntitiesWithinAABB(MobEntity.class, this.getBoundingBox().grow(5, 5, 5));
            for (MobEntity mob : mobEntities) {
                if (living != mob && mob.getCreatureAttribute() == CreatureAttribute.UNDEAD) {
                    mob.setAttackTarget(living);
                }
            }
        }

    }

    private void spawnParticles(int particleCount) {
        int i = DyeColor.WHITE.getColorValue();
        if (i != -1 && particleCount > 0) {
            double d0 = (double)(i >> 16 & 255) / 255.0D;
            double d1 = (double)(i >> 8 & 255) / 255.0D;
            double d2 = (double)(i >> 0 & 255) / 255.0D;

            for(int j = 0; j < particleCount; ++j) {
                this.world.addParticle(ParticleTypes.FLASH, this.getPosXRandom(0.5D), this.getPosYRandom(), this.getPosZRandom(0.5D), d0, d1, d2);
            }

        }
    }
}
