package com.cannolicatfish.rankine.dimension;

import com.cannolicatfish.rankine.blocks.ModBlocks;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;
import java.util.function.Function;

public class MantleSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {

        private static final BlockState CAVE_AIR = ModBlocks.PERIDOTITE.getDefaultState();
        private static final BlockState PERIDOTITE = ModBlocks.PERIDOTITE.getDefaultState();
        private static final BlockState STONE = Blocks.STONE.getDefaultState();
        private static final BlockState MAGMA = Blocks.MAGMA_BLOCK.getDefaultState();
        protected long field_205552_a;
        protected OctavesNoiseGenerator field_205553_b;

        public MantleSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> p_i51308_1_) {
            super(p_i51308_1_);
        }

        public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
            int i = seaLevel + 1;
            int j = x & 15;
            int k = z & 15;
            double d0 = 0.03125D;
            boolean flag = this.field_205553_b.func_205563_a((double)x * 0.03125D, (double)z * 0.03125D, 0.0D) + random.nextDouble() * 0.2D > 0.0D;
            boolean flag1 = this.field_205553_b.func_205563_a((double)x * 0.03125D, 109.0D, (double)z * 0.03125D) + random.nextDouble() * 0.2D > 0.0D;
            int l = (int)(noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
            BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();
            int i1 = -1;
            BlockState blockstate = PERIDOTITE;
            BlockState blockstate1 = PERIDOTITE;

            for(int j1 = 255; j1 >= 0; --j1) {
                blockpos$mutableblockpos.setPos(j, j1, k);
                BlockState blockstate2 = chunkIn.getBlockState(blockpos$mutableblockpos);
                if (blockstate2.getBlock() != null && !blockstate2.isAir()) {
                    if (blockstate2.getBlock() == defaultBlock.getBlock()) {
                        if (i1 == -1) {
                            if (l <= 0) {
                                blockstate = CAVE_AIR;
                                blockstate1 = PERIDOTITE;
                            } else if (j1 >= i - 4 && j1 <= i + 1) {
                                blockstate = PERIDOTITE;
                                blockstate1 = PERIDOTITE;
                                if (flag1) {
                                    blockstate = STONE;
                                    blockstate1 = PERIDOTITE;
                                }

                                if (flag) {
                                    blockstate = MAGMA;
                                    blockstate1 = MAGMA;
                                }
                            }

                            if (j1 < i && (blockstate == null || blockstate.isAir())) {
                                blockstate = defaultFluid;
                            }

                            i1 = l;
                            if (j1 >= i - 1) {
                                chunkIn.setBlockState(blockpos$mutableblockpos, blockstate, false);
                            } else {
                                chunkIn.setBlockState(blockpos$mutableblockpos, blockstate1, false);
                            }
                        } else if (i1 > 0) {
                            --i1;
                            chunkIn.setBlockState(blockpos$mutableblockpos, blockstate1, false);
                        }
                    }
                } else {
                    i1 = -1;
                }
            }

        }

        public void setSeed(long seed) {
            if (this.field_205552_a != seed || this.field_205553_b == null) {
                this.field_205553_b = new OctavesNoiseGenerator(new SharedSeedRandom(seed), 3, 0);
            }

            this.field_205552_a = seed;
        }
}
