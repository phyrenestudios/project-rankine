package com.cannolicatfish.rankine.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class RankineSparkyLanternBlock extends RankineLanternBlock {
    ParticleOptions particle;

    public RankineSparkyLanternBlock(int color, ParticleOptions particle) {
        super(color);
        this.particle = particle;
    }

    @Override
    public void animateTick(BlockState p_49888_, Level p_49889_, BlockPos p_49890_, Random rand) {
        double d0 = (double)p_49890_.getX() + rand.nextDouble();
        double d1 = (double)p_49890_.getY() + (rand.nextDouble()*0.8D);
        double d2 = (double)p_49890_.getZ() + rand.nextDouble();
        if (rand.nextDouble() < 0.2D) {
            p_49889_.addParticle(particle, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
        super.animateTick(p_49888_, p_49889_, p_49890_, rand);
    }
}
