package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.blocks.GrassySoilBlock;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.Tags;

import java.util.Random;

public class SnowyPeaksFeature extends Feature<NoFeatureConfig> {

    public SnowyPeaksFeature(Codec<NoFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        IChunk chunk = reader.getChunk(pos);
        int startX = chunk.getPos().getXStart();
        int startZ = chunk.getPos().getZStart();
        int endX = chunk.getPos().getXEnd();
        int endZ = chunk.getPos().getZEnd();

        for (int x = startX; x <= endX; ++x) {
            for (int z = startZ; z <= endZ; ++z) {
                int endY = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, x, z);
                for (int y = 110; y <= endY; ++y) {
                    BlockPos TARGET_POS = new BlockPos(x,y,z);
                    if (reader.getBlockState(TARGET_POS).isIn(Tags.Blocks.STONE) || reader.getBlockState(TARGET_POS).isIn(Tags.Blocks.DIRT)) {
                        if (reader.getBiome(pos).getTemperature()<0.15) {
                            reader.setBlockState(TARGET_POS, Blocks.SNOW_BLOCK.getDefaultState(), 19);
                        }
                    }

                }
            }
        }
        return true;
    }


}
