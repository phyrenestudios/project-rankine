package com.cannolicatfish.rankine.dimension;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;

import javax.annotation.Nullable;

public class MantleDimension extends Dimension {

    public MantleDimension(World worldIn, DimensionType typeIn) {
        super(worldIn, typeIn, 0.0f);
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator() {
        return new MantleChunkGenerator(world, new MantleBiomeProvider());
    }


    @Nullable
    @Override
    public BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid) {
        return null;
    }

    @Nullable
    @Override
    public BlockPos findSpawn(int posX, int posZ, boolean checkValid) {
        return null;
    }

    @Override
    public int getActualHeight() {
        return 256;
    }

    @Override
    public SleepResult canSleepAt(PlayerEntity player, BlockPos pos) {
        return SleepResult.DENY;
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        return 0.5F;
    }

    protected void generateLightBrightnessTable() {
        float f = 0.1F;

        for(int i = 0; i <= 15; ++i) {
            float f1 = 1.0F - (float)i / 15.0F;
            this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * 0.9F + 0.1F;
        }

    }

    @Override
    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    public Vec3d getFogColor(float celestialAngle, float partialTicks) {
        return new Vec3d((double)0.03F, (double)0.2F, (double)0.03F);
    }


    @Override
    public boolean doesXZShowFog(int x, int z) {
        return true;
    }
}
