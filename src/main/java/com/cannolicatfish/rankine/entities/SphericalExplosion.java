package com.cannolicatfish.rankine.entities;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SphericalExplosion extends Explosion {

    private final Level level;
    private final float radius;
    private final boolean fire;
    private final ParticleOptions particle;
    private final Explosion.BlockInteraction blockInteraction;
    //protected ImmutableSet<BlockPos> affectedBlockPositionsInternal;

    public SphericalExplosion(Level level, @Nullable Entity entity, @Nullable DamageSource damage, @Nullable ExplosionDamageCalculator context, double x, double y, double z, float radius, boolean causesFire, Explosion.BlockInteraction mode, ParticleOptions particle) {
        super(level, entity, damage, context, x, y, z, radius, causesFire, mode);
        this.level = level;
        this.radius = radius;
        this.fire = causesFire;
        this.particle = particle;
        this.blockInteraction = mode;
    }

    @Override
    public void explode() {
        float r = this.radius * this.radius;
        int i = (int) r + 1;

        for (int j = -i; j < i; ++j) {
            for (int k = -i; k < i; ++k) {
                for (int l = -i; l < i; ++l) {
                    int d = j * j + k * k + l * l;
                    if (d > r) continue;
                    BlockPos blockpos = new BlockPos(j, k, l).offset((int) getPosition().x, (int) getPosition().y, (int) getPosition().z);
                    if (this.level.isEmptyBlock(blockpos)) continue;

                    float f = this.radius * (1f - d / (r));
                    BlockState blockstate = this.level.getBlockState(blockpos);
                    FluidState ifluidstate = this.level.getFluidState(blockpos);
                    float f2 = Math.max(blockstate.getExplosionResistance(this.level, blockpos, this), ifluidstate.getExplosionResistance(this.level, blockpos, this));
                    if (getExploder() != null) {
                        f2 = getExploder().getBlockExplosionResistance(this, this.level, blockpos, blockstate, ifluidstate, f2);
                    }

                    f -= (f2 + 0.3F) * 0.3F;

                    if (f > 0.0F && (getExploder() == null || getExploder().shouldBlockExplode(this, this.level, blockpos, blockstate, f))) {
                        addAffectedBlock(blockpos);
                    }

                }
            }
        }

        float f2 = this.radius * 3.0F;
        int k1 = Mth.floor(getPosition().x - (double)f2 - 1.0D);
        int l1 = Mth.floor(getPosition().x + (double)f2 + 1.0D);
        int i2 = Mth.floor(getPosition().y - (double)f2 - 1.0D);
        int i1 = Mth.floor(getPosition().y + (double)f2 + 1.0D);
        int j2 = Mth.floor(getPosition().z - (double)f2 - 1.0D);
        int j1 = Mth.floor(getPosition().z + (double)f2 + 1.0D);
        List<Entity> list = this.level.getEntities(getExploder(), new AABB((double)k1, (double)i2, (double)j2, (double)l1, (double)i1, (double)j1));
        net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.level, this, list, f2);
        Vec3 vec3 = new Vec3(getPosition().x, getPosition().y, getPosition().z);

        for (Entity entity : list) {
            if (entity.ignoreExplosion()) return;
            double d12 = Math.sqrt(entity.distanceToSqr(vec3)) / (double) f2;
            if (d12 <= 1.0D) {
                double d5 = entity.getX() - getPosition().x;
                double d7 = (entity instanceof PrimedTnt ? entity.getY() : entity.getEyeY()) - getPosition().y;
                double d9 = entity.getZ() - getPosition().z;
                double d13 = Math.sqrt(d5 * d5 + d7 * d7 + d9 * d9);
                if (d13 != 0.0D) {
                    d5 /= d13;
                    d7 /= d13;
                    d9 /= d13;
                    double d14 = (double) getSeenPercent(vec3, entity);
                    double d10 = (1.0D - d12) * d14;
                    entity.hurt(this.getDamageSource(), (float) ((int) ((d10 * d10 + d10) / 2.0D * 7.0D * (double) f2 + 1.0D)));
                    double d11 = d10;
                    if (entity instanceof LivingEntity) {
                        d11 = ProtectionEnchantment.getExplosionKnockbackAfterDampener((LivingEntity) entity, d10);
                    }

                    entity.setDeltaMovement(entity.getDeltaMovement().add(d5 * d11, d7 * d11, d9 * d11));
                    if (entity instanceof Player) {
                        Player player = (Player) entity;
                        if (!player.isSpectator() && (!player.isCreative() || !player.getAbilities().flying)) {
                            getHitPlayers().put(player, new Vec3(d5 * d10, d7 * d10, d9 * d10));
                        }
                    }
                }
            }

        }
        //this.affectedBlockPositionsInternal = builder.build();
    }

    @Override
    public void finalizeExplosion(boolean spawnParticles) {
        if (this.level.isClientSide) {
            this.level.playLocalSound(getPosition().x, getPosition().y, getPosition().z, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4.0F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
        }

        if (this.blockInteraction == BlockInteraction.KEEP) return;

        ObjectArrayList<Pair<ItemStack, BlockPos>> objectarraylist = new ObjectArrayList<>();
        Collections.shuffle(getToBlow(), new Random());

        for(BlockPos blockpos : getToBlow()) {
            BlockState blockstate = this.level.getBlockState(blockpos);
            if (!blockstate.isAir()) {
                BlockPos blockpos1 = blockpos.immutable();
                this.level.getProfiler().push("explosion_blocks");
                if (blockstate.canDropFromExplosion(this.level, blockpos, this) && this.level instanceof ServerLevel) {
                    BlockEntity blockentity = blockstate.hasBlockEntity() ? this.level.getBlockEntity(blockpos) : null;
                    LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerLevel)this.level)).withRandom(this.level.random).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockpos)).withParameter(LootContextParams.TOOL, ItemStack.EMPTY).withOptionalParameter(LootContextParams.BLOCK_ENTITY, blockentity).withOptionalParameter(LootContextParams.THIS_ENTITY, getExploder());
                    if (this.blockInteraction == Explosion.BlockInteraction.DESTROY) {
                        lootcontext$builder.withParameter(LootContextParams.EXPLOSION_RADIUS, this.radius);
                    }
                    blockstate.getDrops(lootcontext$builder).forEach((p_46074_) -> {
                        addBlockDrops(objectarraylist, p_46074_, blockpos1);
                    });
                }
                blockstate.onBlockExploded(this.level, blockpos, this);
                this.level.getProfiler().pop();
                if (spawnParticles) this.level.addParticle(particle, blockpos1.getX(),  blockpos1.getY(),  blockpos1.getZ(), 1.0D, 0.0D, 0.0D);
            }
        }

        for(Pair<ItemStack, BlockPos> pair : objectarraylist) {
            Block.popResource(this.level, pair.getSecond(), pair.getFirst());
        }
        if (this.fire) {
            for(BlockPos blockpos2 : getToBlow()) {
                if (this.level.random.nextInt(3) == 0 && this.level.getBlockState(blockpos2).isAir() && this.level.getBlockState(blockpos2.below()).isSolidRender(this.level, blockpos2.below())) {
                    this.level.setBlockAndUpdate(blockpos2, BaseFireBlock.getState(this.level, blockpos2));
                }
            }
        }



    }

    public void addAffectedBlock(BlockPos blockPos) {
        getToBlow().add(blockPos);
    }

    private static void addBlockDrops(ObjectArrayList<com.mojang.datafixers.util.Pair<ItemStack, BlockPos>> p_46068_, ItemStack p_46069_, BlockPos p_46070_) {
        int i = p_46068_.size();

        for(int j = 0; j < i; ++j) {
            com.mojang.datafixers.util.Pair<ItemStack, BlockPos> pair = p_46068_.get(j);
            ItemStack itemstack = pair.getFirst();
            if (ItemEntity.areMergable(itemstack, p_46069_)) {
                ItemStack itemstack1 = ItemEntity.merge(itemstack, p_46069_, 16);
                p_46068_.set(j, com.mojang.datafixers.util.Pair.of(itemstack1, pair.getSecond()));
                if (p_46069_.isEmpty()) {
                    return;
                }
            }
        }

        p_46068_.add(com.mojang.datafixers.util.Pair.of(p_46069_, p_46070_));
    }
}

