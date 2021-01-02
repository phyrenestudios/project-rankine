package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.compatibility.TerraForged;
import com.cannolicatfish.rankine.init.ModBlocks;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ObserverBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.feature.Feature;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StoneReplacerFeature extends Feature<StoneReplacerFeatureConfig> {


    public StoneReplacerFeature(Codec<StoneReplacerFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    public static final int NOISE_SCALE = Config.NOISE_SCALE.get();
    public static final int NOISE_OFFSET = Config.NOISE_OFFSET.get();

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, StoneReplacerFeatureConfig config) {

        IChunk chunk = reader.getChunk(pos);
        int startX = chunk.getPos().getXStart();
        int startZ = chunk.getPos().getZStart();
        int endX = chunk.getPos().getXEnd();
        int endZ = chunk.getPos().getZEnd();

        for (int x = startX; x <= endX; ++x) {
            for (int z = startZ; z <= endZ; ++z) {

                int endY = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, x, z);

                double d0 = Biome.INFO_NOISE.noiseAt((double) x / NOISE_SCALE, (double) z / NOISE_SCALE, false);
                double d1 = Biome.INFO_NOISE.noiseAt((double) x / NOISE_SCALE + NOISE_OFFSET, (double) z / NOISE_SCALE + NOISE_OFFSET, false);
                double d2 = Biome.INFO_NOISE.noiseAt((double) x / NOISE_SCALE + 2 * NOISE_OFFSET, (double) z / NOISE_SCALE + 2 * NOISE_OFFSET, false);
                double d3 = Biome.INFO_NOISE.noiseAt((double) x / NOISE_SCALE + 3 * NOISE_OFFSET, (double) z / NOISE_SCALE + 3 * NOISE_OFFSET, false);
                double d4 = Biome.INFO_NOISE.noiseAt((double) x / NOISE_SCALE + 4 * NOISE_OFFSET, (double) z / NOISE_SCALE + 4 * NOISE_OFFSET, false);
                double d5 = Biome.INFO_NOISE.noiseAt((double) x / NOISE_SCALE + 5 * NOISE_OFFSET, (double) z / NOISE_SCALE + 5 * NOISE_OFFSET, false);
                double d6 = Biome.INFO_NOISE.noiseAt((double) x / NOISE_SCALE + 6 * NOISE_OFFSET, (double) z / NOISE_SCALE + 6 * NOISE_OFFSET, false);
                double d7 = Biome.INFO_NOISE.noiseAt((double) x / NOISE_SCALE + 7 * NOISE_OFFSET, (double) z / NOISE_SCALE + 7 * NOISE_OFFSET, false);


                if (TerraForged.isInstalled()) {
                    replaceAll(reader, config, x, z, 0, geteHeights(d0, 5), ModBlocks.PERIDOTITE.getDefaultState());
                    replaceStone(reader, config, x, z, geteHeights(d0, 5), geteHeights(d1, 50), ModBlocks.SLATE.getDefaultState());
                    replaceStone(reader, config, x, z, geteHeights(d1, 50), geteHeights(d2, 100), ModBlocks.ANORTHOSITE.getDefaultState());
                    replaceStone(reader, config, x, z, geteHeights(d2, 100), endY, ModBlocks.HORNBLENDE_ANDESITE.getDefaultState());
                } else {

                    //Universal Stones
                    int peridotiteHeights = 7;
                    replaceStone(reader, config, x, z, 0, geteHeights(d0, peridotiteHeights), ModBlocks.PERIDOTITE.getDefaultState());

                    if (reader.getBiome(new BlockPos(x, 0, z)).getCategory() == Biome.Category.OCEAN || reader.getBiome(new BlockPos(x, 0, z)).getCategory() == Biome.Category.MUSHROOM) {
                        int oGabbroHeights = 23;
                        int oBasaltHeights = 38;
                        int BasaltTuffHeight = 39;
                        int oShaleHeights = 43;
                        int oLimestoneHeights = 0;
                        replaceStone(reader, config, x, z, geteHeights(d0, peridotiteHeights), geteHeights(d1, oGabbroHeights), ModBlocks.GABBRO.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d1, oGabbroHeights), geteHeights(d2, oBasaltHeights), ModBlocks.THOLEIITIC_BASALT.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d2, oBasaltHeights), geteHeights(d3, BasaltTuffHeight), ModBlocks.THOLEIITIC_BASALTIC_TUFF.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d3, BasaltTuffHeight), geteHeights(d4, oShaleHeights), ModBlocks.SHALE.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d4, oShaleHeights), endY, ModBlocks.LIMESTONE.getDefaultState());
                    } else if (reader.getBiome(new BlockPos(x, 0, z)).getCategory() == Biome.Category.DESERT) {
                        int sMarbleHeights = 18;
                        int sGraniteHeights = 29;
                        int sRhyoliteHeights = 37;
                        int RhyoliteTuffHeight = 38;
                        int sAnorthositeHeights = 48;
                        int sLimestoneHeights = 60;
                        int sBrecciaHeights = 61;
                        int sSandstoneHeights = 0;
                        replaceStone(reader, config, x, z, geteHeights(d0, peridotiteHeights), geteHeights(d1, sMarbleHeights), ModBlocks.MARBLE.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d1, sMarbleHeights), geteHeights(d2, sGraniteHeights), ModBlocks.RED_GRANITE.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d2, sGraniteHeights), geteHeights(d3, sRhyoliteHeights), ModBlocks.RHYOLITE.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d3, sRhyoliteHeights), geteHeights(d7, RhyoliteTuffHeight), ModBlocks.RHYOLITIC_TUFF.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d7, RhyoliteTuffHeight), geteHeights(d4, sAnorthositeHeights), ModBlocks.ANORTHOSITE.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d4, sAnorthositeHeights), geteHeights(d5, sLimestoneHeights), ModBlocks.LIMESTONE.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d5, sLimestoneHeights), geteHeights(d6, sBrecciaHeights), ModBlocks.BRECCIA.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d6, sBrecciaHeights), endY, Blocks.SANDSTONE.getDefaultState());
                    } else if (reader.getBiome(new BlockPos(x, 0, z)).getCategory() == Biome.Category.MESA) {
                        int sMarbleHeights = 18;
                        int sGraniteHeights = 29;
                        int sRhyoliteHeights = 37;
                        int RhyoliteTuffHeight = 38;
                        int sAnorthositeHeights = 48;
                        int sLimestoneHeights = 60;
                        int sBrecciaHeights = 61;
                        int sSandstoneHeights = 0;
                        replaceStone(reader, config, x, z, geteHeights(d0, peridotiteHeights), geteHeights(d1, sMarbleHeights), ModBlocks.MARBLE.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d1, sMarbleHeights), geteHeights(d2, sGraniteHeights), ModBlocks.RED_GRANITE.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d2, sGraniteHeights), geteHeights(d3, sRhyoliteHeights), ModBlocks.RHYOLITE.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d3, sRhyoliteHeights), geteHeights(d7, RhyoliteTuffHeight), ModBlocks.RHYOLITIC_TUFF.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d7, RhyoliteTuffHeight), geteHeights(d4, sAnorthositeHeights), ModBlocks.ANORTHOSITE.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d4, sAnorthositeHeights), geteHeights(d5, sLimestoneHeights), ModBlocks.LIMESTONE.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d5, sLimestoneHeights), geteHeights(d6, sBrecciaHeights), ModBlocks.BRECCIA.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d6, sBrecciaHeights), endY, Blocks.RED_SANDSTONE.getDefaultState());
                    } else {
                        int cGneissHeights = 16;
                        int cSchistHeights = 23;
                        int cGraniteHeights = 34;
                        int cSlateHeights = 39;
                        int cRhyoliteHeights = 48;
                        int RhyoliteTuffHeight = 49;
                        int AnorthositeHeight = 60;
                        replaceStone(reader, config, x, z, geteHeights(d0, peridotiteHeights), geteHeights(d1, cGneissHeights), ModBlocks.GNEISS.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d1, cGneissHeights), geteHeights(d2, cSchistHeights), ModBlocks.SCHIST.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d2, cSchistHeights), geteHeights(d3, cGraniteHeights), ModBlocks.RED_GRANITE.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d3, cGraniteHeights), geteHeights(d4, cSlateHeights), ModBlocks.SLATE.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d4, cSlateHeights), geteHeights(d5, cRhyoliteHeights), ModBlocks.RHYOLITE.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d5, cRhyoliteHeights), geteHeights(d6, RhyoliteTuffHeight), ModBlocks.RHYOLITIC_TUFF.getDefaultState());
                        replaceStone(reader, config, x, z, geteHeights(d6, RhyoliteTuffHeight), geteHeights(d7, AnorthositeHeight), ModBlocks.ANORTHOSITE.getDefaultState());
                        if (endY > 60 && endY < 68) {
                            replaceStone(reader, config, x, z, geteHeights(d7, AnorthositeHeight), endY, ModBlocks.SHALE.getDefaultState());
                        } else if (endY > 85) {
                            replaceStone(reader, config, x, z, geteHeights(d7, AnorthositeHeight), endY, ModBlocks.HORNBLENDE_ANDESITE.getDefaultState());
                        } else {
                            replaceStone(reader, config, x, z, geteHeights(d5, cRhyoliteHeights), endY, ModBlocks.ANORTHOSITE.getDefaultState());
                        }
                    }
                }
            }
        }
        return true;
    }

    private static int geteHeights(double noise, int height) {
        return height + (int) Math.round((noise/0.2D));
    }

    private static void replaceStone(ISeedReader reader, StoneReplacerFeatureConfig config, int x, int z, int StartY, int EndY, BlockState Block) {
        for (int y = StartY; y <= EndY; ++y) {
            if (reader.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                reader.setBlockState(new BlockPos(x, y, z), Block, 2);
            }
        }
    }
    private static void replaceAll(ISeedReader reader, StoneReplacerFeatureConfig config, int x, int z, int StartY, int EndY, BlockState Block) {
        for (int y = StartY; y <= EndY; ++y) {
            if (reader.getBlockState(new BlockPos(x, y, z)).getBlock().getTags().contains(new ResourceLocation("forge:stone"))) {
                reader.setBlockState(new BlockPos(x, y, z), Block, 2);
            }
        }
    }
}
