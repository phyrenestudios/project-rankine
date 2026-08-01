package com.cannolicatfish.rankine.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class RubberBlock extends Block {
    public static final MapCodec<RubberBlock> CODEC = simpleCodec(RubberBlock::new);

    @Override
    public MapCodec<? extends RubberBlock> codec() {
        return CODEC;
    }

    public RubberBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void fallOn(Level worldIn, BlockState bs, BlockPos pos, Entity entityIn, float fallDistance) {
        if (entityIn.isSuppressingBounce()) {
            super.fallOn(worldIn, bs, pos, entityIn, fallDistance);
        } else {
            entityIn.causeFallDamage(fallDistance, 0.2F, entityIn.damageSources().fall());
        }

    }

    /**
     * Called when an Entity lands on this Block. This method *must* update motionY because the entity will not do that
     * on its own
     */
    @Override
    public void updateEntityAfterFallOn(BlockGetter worldIn, Entity entityIn) {
        if (entityIn.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(worldIn, entityIn);
        } else {
            this.bounceEntity(entityIn);
        }

    }

    private void bounceEntity(Entity entity) {
        Vec3 vector3d = entity.getDeltaMovement();
        if (vector3d.y < 0.0D) {
            double d0 = entity instanceof LivingEntity ? 1.0D : 0.8D;
            entity.setDeltaMovement(vector3d.x, -vector3d.y * d0, vector3d.z);
        }

    }

    /**
     * Called when the given entity walks on this Block
     */
    @Override
    public void stepOn(Level worldIn, BlockPos pos, BlockState state, Entity entityIn) {
        double d0 = Math.abs(entityIn.getDeltaMovement().y);
        if (d0 < 0.1D && !entityIn.isSteppingCarefully()) {
            double d1 = 0.4D + d0 * 0.2D;
            entityIn.setDeltaMovement(entityIn.getDeltaMovement().multiply(d1, 1.0D, d1));
        }

        super.stepOn(worldIn, pos, state, entityIn);
    }
}
