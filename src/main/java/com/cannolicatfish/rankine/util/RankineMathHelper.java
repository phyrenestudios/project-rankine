package com.cannolicatfish.rankine.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

public class RankineMathHelper {

    public static float[] linspace(float min, float max, int steps) {
        float[] f = new float[steps];
        for (int i = 0; i < steps; i++){
            f[i] = min + i * (max - min) / (steps - 1);
        }
        return f;
    }

    /**
     * The exact box 1.18.2's {@code AABB(BlockPos, BlockPos)} constructor built: raw corner
     * coordinates, no rounding out to whole blocks. That constructor is gone in 1.21.1 —
     * {@link AABB#encapsulatingFullBlocks(BlockPos, BlockPos)} is NOT a drop-in replacement, since
     * it adds +1 to the max corner (AABB.java:60-69) to round the box out to encompass whole
     * blocks. Every 1.18.2 call site that fed this constructor two RAW block positions (rather than
     * a block and the position one-past-its-far-corner) relied on that lack of rounding, most
     * visibly wherever the two positions were adjacent (e.g. {@code pos, pos.above()}), which
     * produced a 1-block-tall degenerate slice, not a 2-block-tall box.
     * <p>
     * Originally duplicated privately in {@code ElectromagnetBlock} and {@code GrassySoilBlock};
     * lifted here (batch 4, F29) so every 1.18.2-parity call site shares one definition instead of
     * risking drift. See {@code MixingBarrelTile#tick}, {@code BalloonEntity#tick},
     * {@code PedestalRenderer#getRenderBoundingBox} for call sites that had the encapsulatingFullBlocks
     * bug before this fix.
     */
    public static AABB boxBetween(BlockPos a, BlockPos b) {
        return new AABB(a.getX(), a.getY(), a.getZ(), b.getX(), b.getY(), b.getZ());
    }

}
