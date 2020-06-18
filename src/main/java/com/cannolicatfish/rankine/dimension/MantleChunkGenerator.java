package com.cannolicatfish.rankine.dimension;

import com.cannolicatfish.rankine.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.WorldGenRegion;

public class MantleChunkGenerator extends ChunkGenerator<MantleChunkGenerator.Config> {
    public MantleChunkGenerator(IWorld world, BiomeProvider provider) {
        super(world, provider, Config.createDefault());
    }

    @Override
    public void generateSurface(WorldGenRegion region, IChunk chunk) {
        BlockState bedrock = Blocks.BEDROCK.getDefaultState();
        BlockState stone = ModBlocks.PERIDOTITE.getDefaultState();
        ChunkPos chunkpos = chunk.getPos();

        BlockPos.Mutable pos = new BlockPos.Mutable();

        int x;
        int z;

        for (x = 0; x < 16; x++) {
            for (z = 0; z < 16; z++) {
                chunk.setBlockState(pos.setPos(x, 0, z), bedrock, false);
                chunk.setBlockState(pos.setPos(x, 255, z), bedrock, false);
            }
        }

        for (x = 0; x < 16; x++) {
            for (z = 0; z < 16; z++) {
                int realx = chunkpos.x * 16 + x;
                int realz = chunkpos.z * 16 + z;
                int height = (int) (255);
                for (int y = 1 ; y < height ; y++) {
                    chunk.setBlockState(pos.setPos(x, y, z), stone, false);
                }
            }
        }

    }

    @Override
    public void makeBase(IWorld worldIn, IChunk chunkIn) {

    }

    @Override
    public int func_222529_a(int p_222529_1_, int p_222529_2_, Heightmap.Type heightmapType) {
        return 0;
    }

    @Override
    public int getGroundHeight() {
        return world.getSeaLevel()+1;
    }

    @Override
    public int getMaxHeight() {
        return 256;
    }

    @Override
    public int getSeaLevel() {
        return 16;
    }

    public static class Config extends GenerationSettings {

        public static Config createDefault() {
            Config config = new Config();
            config.setDefaultBlock(ModBlocks.PERIDOTITE.getDefaultState());
            config.setDefaultFluid(Blocks.LAVA.getDefaultState());
            return config;
        }

    }

}
