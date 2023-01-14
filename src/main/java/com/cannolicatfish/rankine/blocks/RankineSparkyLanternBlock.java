package com.cannolicatfish.rankine.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class RankineSparkyLanternBlock extends RankineLanternBlock {

    public RankineSparkyLanternBlock(int color) {
        super(color);
    }

    @Override
    public void animateTick(BlockState p_49888_, Level p_49889_, BlockPos p_49890_, Random p_49891_) {
        double d0 = (double)p_49890_.getX() + 0.5D;
        double d1 = (double)p_49890_.getY() + 0.4D;
        double d2 = (double)p_49890_.getZ() + 0.5D;
        if (p_49891_.nextDouble() < 0.1D) {
            p_49889_.addParticle(ParticleTypes.ELECTRIC_SPARK, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
        super.animateTick(p_49888_, p_49889_, p_49890_, p_49891_);
    }
}
