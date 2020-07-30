package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.StructureManager;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StoneReplacerFeature extends Feature<StoneReplacerFeatureConfig> {


    public StoneReplacerFeature(Codec<StoneReplacerFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    private static List<BlockState> STONES;

    @Override
    public boolean func_230362_a_(ISeedReader worldIn, StructureManager p_230362_2_, ChunkGenerator p_230362_3_, Random rand, BlockPos pos, StoneReplacerFeatureConfig config) {

        IChunk chunk = worldIn.getChunk(pos);
        int startX = chunk.getPos().getXStart();
        int startZ = chunk.getPos().getZStart();
        int endX = chunk.getPos().getXEnd();
        int endZ = chunk.getPos().getZEnd();

        for (int x = startX; x <= endX; ++x) {
            for (int z = startZ; z <= endZ; ++z) {
                int endY = worldIn.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, x, z);
                for (int y = 0; y <= endY; ++y) {
                    if (worldIn.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                        worldIn.setBlockState(new BlockPos(x, y, z), getStone(config.biomeType, y, endY), 2);
                    }
                }
            }
        }

        return true;
    }


    @Nonnull
    private static BlockState getStone(int biome_type, int y, int endY) {
        if (biome_type == 1) {
            STONES = Arrays.asList(ModBlocks.SHALE.getDefaultState(), ModBlocks.LIMESTONE.getDefaultState(), ModBlocks.BASALT.getDefaultState(), ModBlocks.GNEISS.getDefaultState(), ModBlocks.MARBLE.getDefaultState(), ModBlocks.PERIDOTITE.getDefaultState());
            int THICKNESS = endY / STONES.size();
            if (y >= 0 && y < endY - THICKNESS * 5.5) {
                return STONES.get(5);
            }
            if (y >= endY - THICKNESS * 5.5 && y < endY - THICKNESS * 4.5) {
                return STONES.get(4);
            }
            if (y >= endY - THICKNESS * 4.5 && y < endY - THICKNESS * 3.75) {
                return STONES.get(3);
            }
            if (y >= endY - THICKNESS * 3.75 && y < endY - THICKNESS * 2.75) {
                return STONES.get(2);
            }
            if (y >= endY - THICKNESS * 2.75 && y < endY - THICKNESS * 1.5) {
                return STONES.get(1);
            }
            if (y >= endY - THICKNESS * 1.5 && y < endY) {
                return STONES.get(0);
            }
        } else if (biome_type == 2) {
            STONES = Arrays.asList(ModBlocks.ANORTHOSITE.getDefaultState(), ModBlocks.SHALE.getDefaultState(), ModBlocks.LIMESTONE.getDefaultState(), ModBlocks.GRANITE.getDefaultState(), ModBlocks.BASALT.getDefaultState(), ModBlocks.GNEISS.getDefaultState(), ModBlocks.MARBLE.getDefaultState(), ModBlocks.PERIDOTITE.getDefaultState());
            int THICKNESS = endY / STONES.size();
            if (y >= 0 && y < endY - THICKNESS * 7.5) {
                return STONES.get(7);
            }
            if (y >= endY - THICKNESS *7.5 && y < endY - THICKNESS * 7.0) {
                return STONES.get(6);
            }
            if (y >= endY - THICKNESS * 7.0 && y < endY - THICKNESS * 6.0) {
                return STONES.get(5);
            }
            if (y >= endY - THICKNESS * 6.0 && y < endY - THICKNESS * 5.0) {
                return STONES.get(4);
            }
            if (y >= endY - THICKNESS * 5.0 && y < endY - THICKNESS * 4.0) {
                return STONES.get(3);
            }
            if (y >= endY - THICKNESS * 4.0 && y < endY - THICKNESS * 2.5) {
                return STONES.get(2);
            }
            if (y >= endY - THICKNESS * 2.5 && y < endY - THICKNESS * 1.0) {
                return STONES.get(1);
            }
            if (y >= endY - THICKNESS * 1.0 && y < endY) {
                return STONES.get(0);
            }
        } else if (biome_type == 3) {
            STONES = Arrays.asList(ModBlocks.BASALT.getDefaultState(), ModBlocks.ANORTHOSITE.getDefaultState(), ModBlocks.GRANITE.getDefaultState(), ModBlocks.RHYOLITE.getDefaultState(), ModBlocks.GNEISS.getDefaultState(), ModBlocks.PERIDOTITE.getDefaultState());
            int THICKNESS = endY / STONES.size();
            if (y >= 0 && y < endY - THICKNESS * 5.5) {
                return STONES.get(5);
            }
            if (y >= endY - THICKNESS * 5.5 && y < endY - THICKNESS * 5.0) {
                return STONES.get(4);
            }
            if (y >= endY - THICKNESS * 5.0 && y < endY - THICKNESS * 4.0) {
                return STONES.get(3);
            }
            if (y >= endY - THICKNESS * 4.0 && y < endY - THICKNESS * 3.5) {
                return STONES.get(2);
            }
            if (y >= endY - THICKNESS * 3.5 && y < endY - THICKNESS * 2.5) {
                return STONES.get(1);
            }
            if (y >= endY - THICKNESS * 2.5 && y < endY) {
                return STONES.get(0);
            }
        } else {
            STONES = Arrays.asList(ModBlocks.ANORTHOSITE.getDefaultState(), ModBlocks.ANDESITE.getDefaultState(), ModBlocks.SHALE.getDefaultState(), ModBlocks.LIMESTONE.getDefaultState(), ModBlocks.GRANITE.getDefaultState(), ModBlocks.RHYOLITE.getDefaultState(), ModBlocks.GNEISS.getDefaultState(), ModBlocks.MARBLE.getDefaultState(), ModBlocks.PERIDOTITE.getDefaultState());
            int THICKNESS = endY / STONES.size();
            if (y >= 0 && y < endY - THICKNESS * 8.5) {
                return STONES.get(8);
            }
            if (y >= endY - THICKNESS * 8.5 && y < endY - THICKNESS * 7.5) {
                return STONES.get(7);
            }
            if (y >= endY - THICKNESS * 7.5 && y < endY - THICKNESS * 7.0) {
                return STONES.get(6);
            }
            if (y >= endY - THICKNESS * 7.0 && y < endY - THICKNESS * 6.0) {
                return STONES.get(5);
            }
            if (y >= endY - THICKNESS * 6.0 && y < endY - THICKNESS * 4.5) {
                return STONES.get(4);
            }
            if (y >= endY - THICKNESS * 4.5 && y < endY - THICKNESS * 3.5) {
                return STONES.get(3);
            }
            if (y >= endY - THICKNESS * 3.5 && y < endY - THICKNESS * 3.0) {
                return STONES.get(2);
            }
            if (y >= endY - THICKNESS * 3.0 && y < endY - THICKNESS * 1.75) {
                return STONES.get(1);
            }
            if (y >= endY - THICKNESS * 1.75 && y < endY) {
                return STONES.get(0);
            }
        }
        return ModBlocks.ANORTHOSITE.getDefaultState();
    }


}
