package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.StructureManager;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NetherReplacerFeature extends Feature<StoneReplacerFeatureConfig> {


    public NetherReplacerFeature(Codec<StoneReplacerFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean func_230362_a_(ISeedReader worldIn, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random rand, BlockPos pos, StoneReplacerFeatureConfig config) {

        IChunk chunk = worldIn.getChunk(pos);
        int startX = chunk.getPos().getXStart();
        int startZ = chunk.getPos().getZStart();
        int endX = chunk.getPos().getXEnd();
        int endZ = chunk.getPos().getZEnd();


        for (int x = startX; x <= endX; ++x) {
            for (int z = startZ; z <= endZ; ++z) {
                if (worldIn.getBiome(pos) == Biomes.WARPED_FOREST) {
                    for (int y = 0; y <= 64; ++y) {
                        if (worldIn.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                            worldIn.setBlockState(new BlockPos(x, y, z), ModBlocks.RINGWOODITE.getDefaultState(), 2);
                        }
                        if (y == 64) {
                            if (rand.nextFloat() < 0.75 && worldIn.getBlockState(new BlockPos(x, y+1, z)).getBlock() == config.target.getBlock()) {
                                worldIn.setBlockState(new BlockPos(x, y + 1, z), ModBlocks.WADSLEYITE.getDefaultState(), 2);
                            }
                            if (rand.nextFloat() < 0.5 && worldIn.getBlockState(new BlockPos(x, y+2, z)).getBlock() == config.target.getBlock()) {
                                worldIn.setBlockState(new BlockPos(x, y + 2, z), ModBlocks.WADSLEYITE.getDefaultState(), 2);
                            }
                            if (rand.nextFloat() < 0.25 && worldIn.getBlockState(new BlockPos(x, y+3, z)).getBlock() == config.target.getBlock()) {
                                worldIn.setBlockState(new BlockPos(x, y + 3, z), ModBlocks.WADSLEYITE.getDefaultState(), 2);
                            }
                            if (rand.nextFloat() < 0.10 && worldIn.getBlockState(new BlockPos(x, y+4, z)).getBlock() == config.target.getBlock()) {
                                worldIn.setBlockState(new BlockPos(x, y + 4, z), ModBlocks.WADSLEYITE.getDefaultState(), 2);
                            }
                        }
                    }
                    for (int y = 64; y <= 128; ++y) {
                        if (worldIn.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                            worldIn.setBlockState(new BlockPos(x, y, z), ModBlocks.WADSLEYITE.getDefaultState(), 2);
                        }
                    }
                }
                if (worldIn.getBiome(pos) == Biomes.SOUL_SAND_VALLEY) {
                    for (int y = 0; y <= 64; ++y) {
                        if (worldIn.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                            worldIn.setBlockState(new BlockPos(x, y, z), ModBlocks.BRIDGMANITE.getDefaultState(), 2);
                        }
                        if (y == 64) {
                            if (rand.nextFloat() < 0.75 && worldIn.getBlockState(new BlockPos(x, y+1, z)).getBlock() == config.target.getBlock()) {
                                worldIn.setBlockState(new BlockPos(x, y + 1, z), ModBlocks.BRIDGMANITE.getDefaultState(), 2);
                            }
                            if (rand.nextFloat() < 0.5 && worldIn.getBlockState(new BlockPos(x, y+2, z)).getBlock() == config.target.getBlock()) {
                                worldIn.setBlockState(new BlockPos(x, y + 2, z), ModBlocks.BRIDGMANITE.getDefaultState(), 2);
                            }
                            if (rand.nextFloat() < 0.25 && worldIn.getBlockState(new BlockPos(x, y+3, z)).getBlock() == config.target.getBlock()) {
                                worldIn.setBlockState(new BlockPos(x, y + 3, z), ModBlocks.BRIDGMANITE.getDefaultState(), 2);
                            }
                            if (rand.nextFloat() < 0.10 && worldIn.getBlockState(new BlockPos(x, y+4, z)).getBlock() == config.target.getBlock()) {
                                worldIn.setBlockState(new BlockPos(x, y + 4, z), ModBlocks.BRIDGMANITE.getDefaultState(), 2);
                            }
                        }
                    }
                    for (int y = 64; y <= 128; ++y) {
                        if (worldIn.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                            worldIn.setBlockState(new BlockPos(x, y, z), ModBlocks.PEROVSKITE.getDefaultState(), 2);
                        }
                    }
                }
                if (worldIn.getBiome(pos) == Biomes.BASALT_DELTAS) {
                    for (int y = 0; y <= 128; ++y) {
                        if (worldIn.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                            worldIn.setBlockState(new BlockPos(x, y, z), ModBlocks.FERROPERICLASE.getDefaultState(), 2);
                        }
                    }
                }
                if (worldIn.getBiome(pos) == Biomes.CRIMSON_FOREST) {
                    for (int y = 0; y <= 128; ++y) {
                        if (worldIn.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                            worldIn.setBlockState(new BlockPos(x, y, z), ModBlocks.KOMATIITE.getDefaultState(), 2);
                        }
                    }
                }
            }
        }
        return true;
    }

}
