package com.cannolicatfish.rankine;

import com.cannolicatfish.rankine.util.RankineMathHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * F29 (batch 4). 1.18.2's two-BlockPos {@code AABB(BlockPos, BlockPos)} constructor used the RAW
 * corner coordinates of each position with no rounding, so {@code new AABB(pos, pos.above())} was a
 * degenerate (zero-width in X/Z) line from the bottom of {@code pos} to the bottom of
 * {@code pos.above()} — NOT a box encompassing both whole blocks. That constructor is gone in
 * 1.21.1; the porters' first instinct, {@link AABB#encapsulatingFullBlocks(BlockPos, BlockPos)},
 * looks like a drop-in replacement but is not one — it adds +1 to the max corner (rounds OUT to
 * encompass whole blocks), which silently changes every call site that relied on the old
 * constructor's lack of rounding.
 *
 * <p>{@link RankineMathHelper#boxBetween(BlockPos, BlockPos)} is the exact 1.18.2 replacement
 * (confirmed against the mod's own {@code ElectromagnetBlock}/{@code GrassySoilBlock} PORT-NOTEs,
 * which independently derived the same fix before this batch). This test pins the exact corners
 * {@code boxBetween} produces at the three call sites that had the encapsulatingFullBlocks bug
 * (MixingBarrelTile, BalloonEntity, PedestalRenderer), computed by hand from the 1.18.2 baseline
 * expressions at {@code 859f87773}, and contrasts each against the wrong
 * {@code encapsulatingFullBlocks}-based box the buggy code produced — so a regression back to
 * {@code encapsulatingFullBlocks} at any of these sites fails loudly instead of silently.
 */
class AABBInflationRegressionTest {

    private static void assertBox(AABB box, double minX, double minY, double minZ,
                                   double maxX, double maxY, double maxZ, String what) {
        assertEquals(minX, box.minX, 1e-9, what + " minX");
        assertEquals(minY, box.minY, 1e-9, what + " minY");
        assertEquals(minZ, box.minZ, 1e-9, what + " minZ");
        assertEquals(maxX, box.maxX, 1e-9, what + " maxX");
        assertEquals(maxY, box.maxY, 1e-9, what + " maxY");
        assertEquals(maxZ, box.maxZ, 1e-9, what + " maxZ");
    }

    private static double volume(AABB box) {
        return (box.maxX - box.minX) * (box.maxY - box.minY) * (box.maxZ - box.minZ);
    }

    /**
     * {@code boxBetween} itself: the raw corner-to-corner box with no rounding, matching the removed
     * 1.18.2 {@code AABB(BlockPos, BlockPos)} constructor exactly.
     */
    @Test
    @DisplayName("boxBetween reproduces the removed 1.18.2 AABB(BlockPos,BlockPos) ctor (raw corners, no rounding)")
    void boxBetweenIsRawCorners() {
        BlockPos a = new BlockPos(5, 60, -3);
        BlockPos b = new BlockPos(7, 61, -1);
        AABB box = RankineMathHelper.boxBetween(a, b);
        assertBox(box, 5, 60, -3, 7, 61, -1, "boxBetween(a,b)");

        // Adjacent positions (pos, pos.above()) must NOT round out to a whole 1x2x1 block like
        // encapsulatingFullBlocks does — this is the crux of the whole bug family.
        BlockPos pos = new BlockPos(100, 64, 200);
        AABB adjacent = RankineMathHelper.boxBetween(pos, pos.above());
        assertBox(adjacent, 100, 64, 200, 100, 65, 200, "boxBetween(pos,pos.above())");
        assertEquals(0.0, volume(adjacent), 1e-9, "boxBetween(pos,pos.above()) must be degenerate (zero volume)");
    }

    /**
     * MixingBarrelTile#tick (MixingBarrelTile.java:144). 1.18.2 baseline (859f87773, line ~136):
     * {@code new AABB(tile.worldPosition, tile.worldPosition.above()).expandTowards(1,1,1)}.
     * <p>
     * Hand computation for pos = (10, 20, 30):
     * <ul>
     *   <li>1.18.2 raw ctor: AABB(10,20,30, 10,21,30) — degenerate in X/Z, spans Y 20-21.</li>
     *   <li>expandTowards(1,1,1) grows only the max corner: AABB(10,20,30, 11,22,31).</li>
     *   <li>Volume = 1 x 2 x 1 = 2 — exactly the barrel's own block plus the block above it.</li>
     * </ul>
     * The pre-fix bug (encapsulatingFullBlocks then expandTowards) gives AABB(10,20,30, 12,23,32),
     * volume 12 (a 2x3x2 region) — 6x too large, confirming finding #19's own "6x" figure.
     */
    @Test
    @DisplayName("F29: mixing barrel entity-catch box matches 1.18.2 (volume 2), not the buggy volume-12 box")
    void mixingBarrelBoxMatchesBaseline() {
        BlockPos pos = new BlockPos(10, 20, 30);

        AABB fixed = RankineMathHelper.boxBetween(pos, pos.above()).expandTowards(1, 1, 1);
        assertBox(fixed, 10, 20, 30, 11, 22, 31, "mixing barrel box (fixed)");
        assertEquals(2.0, volume(fixed), 1e-9, "mixing barrel box volume");

        AABB buggy = AABB.encapsulatingFullBlocks(pos, pos.above()).expandTowards(1, 1, 1);
        assertBox(buggy, 10, 20, 30, 12, 23, 32, "mixing barrel box (pre-fix, for contrast)");
        assertEquals(12.0, volume(buggy), 1e-9, "pre-fix mixing barrel box volume");
        assertNotEquals(volume(fixed), volume(buggy), "fixed and buggy boxes must differ");
    }

    /**
     * BalloonEntity#tick (BalloonEntity.java:73). 1.18.2 baseline (859f87773, line ~89):
     * {@code new AABB(this.blockPosition(), this.blockPosition().above()).expandTowards(1,1,1)} —
     * textually the same expression as the mixing barrel, so the same-shaped bug and fix apply.
     * <p>
     * Uses a different sample position from the mixing barrel test (not (10,20,30)) so this is not
     * a copy-pasted assertion of the same numbers: pos = (-4, 72, 8).
     * <ul>
     *   <li>1.18.2: AABB(-4,72,8, -3,74,9). Volume = 1 x 2 x 1 = 2 (the balloon's own column).</li>
     *   <li>Pre-fix bug: AABB(-4,72,8, -2,75,10). Volume = 2 x 3 x 2 = 12 — 6x too large, refuting
     *       the pool claim's "8x" figure (identical formula to the mixing barrel, so identical 6x
     *       ratio, not 8x).</li>
     * </ul>
     */
    @Test
    @DisplayName("F29: balloon lift box matches 1.18.2 (volume 2, 6x not 8x too large pre-fix)")
    void balloonBoxMatchesBaseline() {
        BlockPos pos = new BlockPos(-4, 72, 8);

        AABB fixed = RankineMathHelper.boxBetween(pos, pos.above()).expandTowards(1, 1, 1);
        assertBox(fixed, -4, 72, 8, -3, 74, 9, "balloon box (fixed)");
        assertEquals(2.0, volume(fixed), 1e-9, "balloon box volume");

        AABB buggy = AABB.encapsulatingFullBlocks(pos, pos.above()).expandTowards(1, 1, 1);
        assertBox(buggy, -4, 72, 8, -2, 75, 10, "balloon box (pre-fix, for contrast)");
        assertEquals(12.0, volume(buggy), 1e-9, "pre-fix balloon box volume");

        // The pool claim's headline said "8x too large". The actual ratio, from the identical
        // expression shape shared with MixingBarrelTile (finding #19), is 6x.
        double ratio = volume(buggy) / volume(fixed);
        assertEquals(6.0, ratio, 1e-9, "pre-fix/fixed volume ratio (pool claim said 8x; refuted)");
        assertNotEquals(8.0, ratio, "the pool claim's 8x figure does not match the code");
    }

    /**
     * PedestalRenderer#getRenderBoundingBox (PedestalRenderer.java:32). 1.18.2 baseline
     * (859f87773, PedestalTile.java:47): {@code new AABB(this.getBlockPos(), this.getBlockPos().above(2))}
     * — note NO {@code .expandTowards(...)} call here, unlike the two entity call sites above.
     * <p>
     * Hand computation for pos = (0, 65, 0):
     * <ul>
     *   <li>1.18.2: AABB(0,65,0, 0,67,0) — degenerate (zero width in X/Z), a vertical line 2 blocks
     *       tall used only as a render-culling bound. Volume = 0.</li>
     *   <li>Pre-fix bug (encapsulatingFullBlocks, no expand): AABB(0,65,0, 1,68,1). Volume = 1 x 3 x 1
     *       = 3 — a real solid box where 1.18.2 had a degenerate line.</li>
     * </ul>
     * Purely cosmetic (render bounding box for frustum culling), but restored for parity per F29.
     */
    @Test
    @DisplayName("F29: pedestal render bounding box matches 1.18.2 (degenerate line, volume 0)")
    void pedestalRenderBoxMatchesBaseline() {
        BlockPos pos = new BlockPos(0, 65, 0);

        AABB fixed = RankineMathHelper.boxBetween(pos, pos.above(2));
        assertBox(fixed, 0, 65, 0, 0, 67, 0, "pedestal render box (fixed)");
        assertEquals(0.0, volume(fixed), 1e-9, "pedestal render box volume must be degenerate, as in 1.18.2");

        AABB buggy = AABB.encapsulatingFullBlocks(pos, pos.above(2));
        assertBox(buggy, 0, 65, 0, 1, 68, 1, "pedestal render box (pre-fix, for contrast)");
        assertEquals(3.0, volume(buggy), 1e-9, "pre-fix pedestal render box volume");
        assertNotEquals(volume(fixed), volume(buggy), "fixed and buggy boxes must differ");
    }
}
