package com.cannolicatfish.rankine.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class SparkingBlock extends Block {
    public SparkingBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onProjectileHit(Level levelIn, BlockState p_60454_, BlockHitResult p_60455_, Projectile p_60456_) {
        BlockPos posIn = p_60455_.getBlockPos();
        double d0 = 3.0D;
        double d1 = 1.0D;
        for(int i = 0; i < 30; ++i) {
            double d2 = levelIn.getRandom().nextGaussian() * 0.02D;
            double d3 = levelIn.getRandom().nextGaussian() * 0.02D;
            double d4 = levelIn.getRandom().nextGaussian() * 0.02D;
            double d5 = 0.5D - d0;
            double d6 = (double)posIn.getX() + d5 + levelIn.getRandom().nextDouble() * d0 * 2.0D;
            double d7 = (double)posIn.getY() + d5 + levelIn.getRandom().nextDouble() * d0 * 2.0D;
            double d8 = (double)posIn.getZ() + d5 + levelIn.getRandom().nextDouble() * d0 * 2.0D;
            if (!levelIn.getBlockState((new BlockPos(d6, d7, d8)).below()).isAir()) {
                levelIn.addParticle( levelIn.getRandom().nextFloat() < 0.5 ? ParticleTypes.CRIT : ParticleTypes.ELECTRIC_SPARK, d6, d7, d8, d2, d3, d4);
            }
        }
        super.onProjectileHit(levelIn, p_60454_, p_60455_, p_60456_);
    }
}
