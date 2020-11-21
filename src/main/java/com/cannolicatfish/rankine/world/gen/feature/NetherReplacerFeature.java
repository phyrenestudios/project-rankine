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
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, StoneReplacerFeatureConfig config) {

        IChunk chunk = reader.getChunk(pos);
        int startX = chunk.getPos().getXStart();
        int startZ = chunk.getPos().getZStart();
        int endX = chunk.getPos().getXEnd();
        int endZ = chunk.getPos().getZEnd();


        for (int x = startX; x <= endX; ++x) {
            for (int z = startZ; z <= endZ; ++z) {

                if (reader.getBiome(new BlockPos(x,0,z)).getRegistryName() == Biomes.WARPED_FOREST.getRegistryName()) {
                    for (int y = 0; y <= 64; ++y) {
                        BlockPos TARGET = new BlockPos(x,y,z);
                        if (reader.getBlockState(TARGET).getBlock() == config.target.getBlock() && reader.getBlockState(TARGET.up(1)).isOpaqueCube(reader, TARGET.up(1)) && reader.getBlockState(TARGET.up(2)).isOpaqueCube(reader, TARGET.up(2)) && reader.getBlockState(TARGET.up(3)).isOpaqueCube(reader, TARGET.up(3))) {
                            reader.setBlockState(new BlockPos(x, y, z), ModBlocks.RINGWOODITE.getDefaultState(), 2);
                        }
                        if (y == 64) {
                            if (rand.nextFloat() < 0.75 && reader.getBlockState(new BlockPos(x, y+1, z)).getBlock() == config.target.getBlock()) {
                                reader.setBlockState(new BlockPos(x, y + 1, z), ModBlocks.RINGWOODITE.getDefaultState(), 2);
                            }
                            if (rand.nextFloat() < 0.5 && reader.getBlockState(new BlockPos(x, y+2, z)).getBlock() == config.target.getBlock()) {
                                reader.setBlockState(new BlockPos(x, y + 2, z), ModBlocks.RINGWOODITE.getDefaultState(), 2);
                            }
                            if (rand.nextFloat() < 0.25 && reader.getBlockState(new BlockPos(x, y+3, z)).getBlock() == config.target.getBlock()) {
                                reader.setBlockState(new BlockPos(x, y + 3, z), ModBlocks.RINGWOODITE.getDefaultState(), 2);
                            }
                            if (rand.nextFloat() < 0.10 && reader.getBlockState(new BlockPos(x, y+4, z)).getBlock() == config.target.getBlock()) {
                                reader.setBlockState(new BlockPos(x, y + 4, z), ModBlocks.RINGWOODITE.getDefaultState(), 2);
                            }
                        }
                    }
                    for (int y = 64; y <= 128; ++y) {
                        BlockPos TARGET = new BlockPos(x,y,z);
                        if (reader.getBlockState(TARGET).getBlock() == config.target.getBlock() && reader.getBlockState(TARGET.up(1)).isOpaqueCube(reader, TARGET.up(1)) && reader.getBlockState(TARGET.up(2)).isOpaqueCube(reader, TARGET.up(2)) && reader.getBlockState(TARGET.up(3)).isOpaqueCube(reader, TARGET.up(3))) {
                            reader.setBlockState(new BlockPos(x, y, z), ModBlocks.WADSLEYITE.getDefaultState(), 2);
                        }
                    }
                }
                if (reader.getBiome(new BlockPos(x,0,z)).getRegistryName() == Biomes.SOUL_SAND_VALLEY.getRegistryName()) {
                    for (int y = 0; y <= 64; ++y) {
                        BlockPos TARGET = new BlockPos(x,y,z);
                        if (reader.getBlockState(TARGET).getBlock() == config.target.getBlock()) {
                            reader.setBlockState(new BlockPos(x, y, z), ModBlocks.BRIDGMANITE.getDefaultState(), 2);
                        }
                        if (y == 64) {
                            if (rand.nextFloat() < 0.75 && reader.getBlockState(new BlockPos(x, y+1, z)).getBlock() == config.target.getBlock()) {
                                reader.setBlockState(new BlockPos(x, y + 1, z), ModBlocks.BRIDGMANITE.getDefaultState(), 2);
                            }
                            if (rand.nextFloat() < 0.5 && reader.getBlockState(new BlockPos(x, y+2, z)).getBlock() == config.target.getBlock()) {
                                reader.setBlockState(new BlockPos(x, y + 2, z), ModBlocks.BRIDGMANITE.getDefaultState(), 2);
                            }
                            if (rand.nextFloat() < 0.25 && reader.getBlockState(new BlockPos(x, y+3, z)).getBlock() == config.target.getBlock()) {
                                reader.setBlockState(new BlockPos(x, y + 3, z), ModBlocks.BRIDGMANITE.getDefaultState(), 2);
                            }
                            if (rand.nextFloat() < 0.10 && reader.getBlockState(new BlockPos(x, y+4, z)).getBlock() == config.target.getBlock()) {
                                reader.setBlockState(new BlockPos(x, y + 4, z), ModBlocks.BRIDGMANITE.getDefaultState(), 2);
                            }
                        }
                    }
                    for (int y = 64; y <= 128; ++y) {
                        BlockPos TARGET = new BlockPos(x,y,z);
                        if (reader.getBlockState(TARGET).getBlock() == config.target.getBlock()) {
                            reader.setBlockState(new BlockPos(x, y, z), ModBlocks.PEROVSKITE.getDefaultState(), 2);
                        }
                    }
                }
                if (reader.getBiome(new BlockPos(x,0,z)).getRegistryName() == Biomes.BASALT_DELTAS.getRegistryName()) {
                    for (int y = 0; y <= 128; ++y) {
                        BlockPos TARGET = new BlockPos(x,y,z);
                        if (reader.getBlockState(TARGET).getBlock() == config.target.getBlock()) {
                            reader.setBlockState(new BlockPos(x, y, z), ModBlocks.FERROPERICLASE.getDefaultState(), 2);
                        }
                    }
                }
                if (reader.getBiome(new BlockPos(x,0,z)).getRegistryName() == Biomes.CRIMSON_FOREST.getRegistryName()) {
                    for (int y = 0; y <= 128; ++y) {
                        BlockPos TARGET = new BlockPos(x,y,z);
                        if (reader.getBlockState(TARGET).getBlock() == config.target.getBlock() && reader.getBlockState(TARGET.up(1)).isOpaqueCube(reader, TARGET.up(1)) && reader.getBlockState(TARGET.up(2)).isOpaqueCube(reader, TARGET.up(2)) && reader.getBlockState(TARGET.up(3)).isOpaqueCube(reader, TARGET.up(3))) {
                            reader.setBlockState(new BlockPos(x, y, z), ModBlocks.KOMATIITE.getDefaultState(), 2);
                        }
                    }
                }
            }
        }
        return true;
    }

}
