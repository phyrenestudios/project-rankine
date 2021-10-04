package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.blocks.GrassySoilBlock;
import com.cannolicatfish.rankine.blocks.SoilBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.VanillaIntegration;
import com.cannolicatfish.rankine.init.WGConfig;
import com.cannolicatfish.rankine.util.WorldgenUtils;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SoilReplacerFeature extends Feature<NoFeatureConfig> {

    public SoilReplacerFeature(Codec<NoFeatureConfig> configFactoryIn) {
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
                for (int y = 0; y <= endY; ++y) {
                    BlockPos TARGET_POS = new BlockPos(x,y,z);
                    Block TARGET = reader.getBlockState(TARGET_POS).getBlock();
                    ResourceLocation TARGET_BIOME = reader.getBiome(TARGET_POS).getRegistryName();

                    Block GRASS = WorldgenUtils.GEN_GRASSES.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME));
                    Block GRASS2 = WorldgenUtils.GEN_GRASSES2.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME));

                    if (TARGET.matchesBlock(Blocks.DIRT)) {
                        if (WorldgenUtils.GEN_BIOMES.contains(TARGET_BIOME)) {
                            soilPlacer(reader, TARGET_POS, WorldgenUtils.GEN_SOILS.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME)).getDefaultState(), WorldgenUtils.GEN_SOILS2.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME)).getDefaultState());
                        }
                    } else if (TARGET.matchesBlock(Blocks.GRASS_BLOCK)) {
                        if (WorldgenUtils.GEN_BIOMES.contains(TARGET_BIOME)) {
                            
                            if (GRASS instanceof GrassBlock && GRASS2 instanceof GrassBlock) {
                                boolean SNOWY = reader.getBlockState(TARGET_POS).get(BlockStateProperties.SNOWY);
                                soilPlacer(reader, TARGET_POS, GRASS.getDefaultState().with(BlockStateProperties.SNOWY, SNOWY), GRASS2.getDefaultState().with(BlockStateProperties.SNOWY, SNOWY));
                            } else {
                                soilPlacer(reader, TARGET_POS, GRASS.getDefaultState(), GRASS2.getDefaultState());
                            }
                        }
                    } else if (TARGET.matchesBlock(Blocks.GRASS_PATH)) {
                        if (WorldgenUtils.GEN_BIOMES.contains(TARGET_BIOME)) {
                            
                            if (VanillaIntegration.pathBlocks_map.get(GRASS) != null && VanillaIntegration.pathBlocks_map.get(GRASS2) != null) {
                                soilPlacer(reader, TARGET_POS, VanillaIntegration.pathBlocks_map.get(GRASS).getDefaultState(), VanillaIntegration.pathBlocks_map.get(GRASS2).getDefaultState());
                            }
                        }
                    }

                }
            }
        }
        return true;
    }


    private void soilPlacer(ISeedReader reader, BlockPos pos, BlockState soil, BlockState soil2) {
        double noise = Biome.INFO_NOISE.noiseAt((double)pos.getX() / 80, (double)pos.getZ() / 80, false);
        if (noise > 0.4) {
            reader.setBlockState(pos, soil2,19);
        } else {
            reader.setBlockState(pos, soil,19);
        }

    }

}
