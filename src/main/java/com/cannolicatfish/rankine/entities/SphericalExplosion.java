package com.cannolicatfish.rankine.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.EntityBasedExplosionDamageCalculator;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import com.mojang.datafixers.util.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Spherical (instead of raycast) explosion.
 * 1.21.1 port notes:
 * - BlockInteraction values: old NONE/BREAK/DESTROY map to KEEP/DESTROY/DESTROY_WITH_DECAY.
 * - getPosition() -> center(), getExploder() -> getDirectSourceEntity().
 * - Block resistance/should-explode checks go through an ExplosionDamageCalculator (vanilla pattern).
 * - ProtectionEnchantment explosion knockback dampener -> Attributes.EXPLOSION_KNOCKBACK_RESISTANCE.
 * - Drops handled via BlockState#onExplosionHit like vanilla finalizeExplosion.
 */
public class SphericalExplosion extends Explosion {

    private final Level level;
    private final float radius;
    private final boolean fire;
    private final ParticleOptions particle;
    private final Explosion.BlockInteraction blockInteraction;
    private final DamageSource damageSource;
    private final ExplosionDamageCalculator damageCalc;

    public SphericalExplosion(Level level, @Nullable Entity entity, @Nullable DamageSource damage, @Nullable ExplosionDamageCalculator context, double x, double y, double z, float radius, boolean causesFire, Explosion.BlockInteraction mode, ParticleOptions particle) {
        super(level, entity, damage, context, x, y, z, radius, causesFire, mode, ParticleTypes.EXPLOSION, ParticleTypes.EXPLOSION_EMITTER, SoundEvents.GENERIC_EXPLODE);
        this.level = level;
        this.radius = radius;
        this.fire = causesFire;
        this.particle = particle;
        this.blockInteraction = mode;
        this.damageSource = damage == null ? Explosion.getDefaultDamageSource(level, entity) : damage;
        this.damageCalc = context != null ? context : (entity == null ? new ExplosionDamageCalculator() : new EntityBasedExplosionDamageCalculator(entity));
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
                    BlockPos blockpos = new BlockPos(j, k, l).offset((int) center().x, (int) center().y, (int) center().z);
                    if (this.level.isEmptyBlock(blockpos)) continue;

                    float f = this.radius * (1f - d / (r));
                    BlockState blockstate = this.level.getBlockState(blockpos);
                    FluidState ifluidstate = this.level.getFluidState(blockpos);
                    Optional<Float> resistance = this.damageCalc.getBlockExplosionResistance(this, this.level, blockpos, blockstate, ifluidstate);
                    if (resistance.isPresent()) {
                        f -= (resistance.get() + 0.3F) * 0.3F;
                    }

                    if (f > 0.0F && this.damageCalc.shouldBlockExplode(this, this.level, blockpos, blockstate, f)) {
                        addAffectedBlock(blockpos);
                    }

                }
            }
        }

        float f2 = this.radius * 3.0F;
        int k1 = Mth.floor(center().x - (double)f2 - 1.0D);
        int l1 = Mth.floor(center().x + (double)f2 + 1.0D);
        int i2 = Mth.floor(center().y - (double)f2 - 1.0D);
        int i1 = Mth.floor(center().y + (double)f2 + 1.0D);
        int j2 = Mth.floor(center().z - (double)f2 - 1.0D);
        int j1 = Mth.floor(center().z + (double)f2 + 1.0D);
        List<Entity> list = this.level.getEntities(this.getDirectSourceEntity(), new AABB((double)k1, (double)i2, (double)j2, (double)l1, (double)i1, (double)j1));
        net.neoforged.neoforge.event.EventHooks.onExplosionDetonate(this.level, this, list, f2);
        Vec3 vec3 = center();

        for (Entity entity : list) {
            if (entity.ignoreExplosion(this)) return;
            double d12 = Math.sqrt(entity.distanceToSqr(vec3)) / (double) f2;
            if (d12 <= 1.0D) {
                double d5 = entity.getX() - center().x;
                double d7 = (entity instanceof PrimedTnt ? entity.getY() : entity.getEyeY()) - center().y;
                double d9 = entity.getZ() - center().z;
                double d13 = Math.sqrt(d5 * d5 + d7 * d7 + d9 * d9);
                if (d13 != 0.0D) {
                    d5 /= d13;
                    d7 /= d13;
                    d9 /= d13;
                    double d14 = (double) getSeenPercent(vec3, entity);
                    double d10 = (1.0D - d12) * d14;
                    entity.hurt(this.damageSource, (float) ((int) ((d10 * d10 + d10) / 2.0D * 7.0D * (double) f2 + 1.0D)));
                    double d11 = d10;
                    if (entity instanceof LivingEntity livingentity) {
                        // 1.21.1: replaces ProtectionEnchantment.getExplosionKnockbackAfterDampener
                        d11 = d10 * (1.0D - livingentity.getAttributeValue(Attributes.EXPLOSION_KNOCKBACK_RESISTANCE));
                    }

                    entity.setDeltaMovement(entity.getDeltaMovement().add(d5 * d11, d7 * d11, d9 * d11));
                    if (entity instanceof Player player) {
                        if (!player.isSpectator() && (!player.isCreative() || !player.getAbilities().flying)) {
                            getHitPlayers().put(player, new Vec3(d5 * d10, d7 * d10, d9 * d10));
                        }
                    }
                }
            }

        }
    }

    @Override
    public void finalizeExplosion(boolean spawnParticles) {
        if (this.level.isClientSide) {
            this.level.playLocalSound(center().x, center().y, center().z, SoundEvents.GENERIC_EXPLODE.value(), net.minecraft.sounds.SoundSource.BLOCKS, 4.0F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
        }

        if (this.blockInteraction == Explosion.BlockInteraction.KEEP) return;

        List<BlockPos> positions = new ArrayList<>(getToBlow());
        Collections.shuffle(positions, new java.util.Random(this.level.random.nextLong()));

        List<Pair<ItemStack, BlockPos>> drops = new ArrayList<>();
        for(BlockPos blockpos : positions) {
            BlockState blockstate = this.level.getBlockState(blockpos);
            if (!blockstate.isAir()) {
                BlockPos blockpos1 = blockpos.immutable();
                this.level.getProfiler().push("explosion_blocks");
                // 1.21.1: onExplosionHit removes the block and gathers drops (respecting decay for DESTROY_WITH_DECAY)
                blockstate.onExplosionHit(this.level, blockpos, this, (stack, pos) -> addBlockDrops(drops, stack, pos));
                this.level.getProfiler().pop();
                if (spawnParticles) this.level.addParticle(particle, blockpos1.getX(),  blockpos1.getY(),  blockpos1.getZ(), 1.0D, 0.0D, 0.0D);
            }
        }

        for(Pair<ItemStack, BlockPos> pair : drops) {
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

    private static void addBlockDrops(List<Pair<ItemStack, BlockPos>> p_46068_, ItemStack p_46069_, BlockPos p_46070_) {
        int i = p_46068_.size();

        for(int j = 0; j < i; ++j) {
            Pair<ItemStack, BlockPos> pair = p_46068_.get(j);
            ItemStack itemstack = pair.getFirst();
            if (ItemEntity.areMergable(itemstack, p_46069_)) {
                ItemStack itemstack1 = ItemEntity.merge(itemstack, p_46069_, 16);
                p_46068_.set(j, Pair.of(itemstack1, pair.getSecond()));
                if (p_46069_.isEmpty()) {
                    return;
                }
            }
        }

        p_46068_.add(Pair.of(p_46069_, p_46070_));
    }
}
