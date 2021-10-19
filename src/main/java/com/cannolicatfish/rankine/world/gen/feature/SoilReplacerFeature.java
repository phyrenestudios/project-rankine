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
import net.minecraftforge.common.Tags;
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
                for (int y = endY; y > 0; --y) {
                    BlockPos TARGET_POS = new BlockPos(x, y, z);
                    Block TARGET = reader.getBlockState(TARGET_POS).getBlock();
                    ResourceLocation TARGET_BIOME = reader.getBiome(TARGET_POS).getRegistryName();

                    Block O1 = WorldgenUtils.O1.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME));
                    Block A1 = WorldgenUtils.A1.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME));
                    Block B1 = WorldgenUtils.B1.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME));
                    Block O2 = WorldgenUtils.O2.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME));
                    Block A2 = WorldgenUtils.A2.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME));
                    Block B2 = WorldgenUtils.B2.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME));
                    double noise = Biome.INFO_NOISE.noiseAt((double) x / 70, (double) z / 70, false);

                    if (WorldgenUtils.GEN_BIOMES.contains(TARGET_BIOME)) {
                        if (TARGET.matchesBlock(Blocks.GRASS_BLOCK)) {
                            if (O1 instanceof GrassBlock && O2 instanceof GrassBlock) {
                                boolean SNOWY = reader.getBlockState(TARGET_POS).get(BlockStateProperties.SNOWY);
                                if (noise > 0.3) {
                                    reader.setBlockState(TARGET_POS, O2.getDefaultState().with(BlockStateProperties.SNOWY, SNOWY), 2);
                                } else {
                                    reader.setBlockState(TARGET_POS, O1.getDefaultState().with(BlockStateProperties.SNOWY, SNOWY), 2);
                                }
                            } else {
                                if (noise > 0.3) {
                                    reader.setBlockState(TARGET_POS, O2.getDefaultState(), 2);
                                } else {
                                    reader.setBlockState(TARGET_POS, O1.getDefaultState(), 2);
                                }
                            }
                        } else if (TARGET.matchesBlock(Blocks.DIRT)) {
                            if (reader.getBlockState(TARGET_POS.down()).isIn(Tags.Blocks.STONE)) {
                                if (noise > 0.3) {
                                    reader.setBlockState(TARGET_POS.down(), B2.getDefaultState(), 2);
                                } else {
                                    reader.setBlockState(TARGET_POS.down(), B1.getDefaultState(), 2);
                                }
                            }
                            if (reader.getBlockState(TARGET_POS.down(2)).isIn(Tags.Blocks.STONE)) {
                                if (noise > 0.3) {
                                    reader.setBlockState(TARGET_POS.down(2), B2.getDefaultState(), 2);
                                } else {
                                    reader.setBlockState(TARGET_POS.down(2), B1.getDefaultState(), 2);
                                }
                            }
                            if (noise > 0.3) {
                                reader.setBlockState(TARGET_POS, A2.getDefaultState(), 2);
                            } else {
                                reader.setBlockState(TARGET_POS, A1.getDefaultState(), 2);
                            }
                        } else if (TARGET.matchesBlock(Blocks.GRASS_PATH)) {
                            if (noise > 0.3) {
                                if (VanillaIntegration.pathBlocks_map.get(O2) != null) {
                                    reader.setBlockState(TARGET_POS, O2.getDefaultState(), 2);
                                }
                            } else {
                                if (VanillaIntegration.pathBlocks_map.get(O1) != null) {
                                    reader.setBlockState(TARGET_POS, O1.getDefaultState(), 2);
                                }
                            }
                        } else if (TARGET.matchesBlock(Blocks.GRAVEL)) {
                            if (WorldgenUtils.GRAVELS.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME)) != Blocks.AIR) {
                                reader.setBlockState(TARGET_POS, WorldgenUtils.GRAVELS.get(WorldgenUtils.GEN_BIOMES.indexOf(TARGET_BIOME)).getDefaultState(), 2);
                            }
                        }
                        if (TARGET.isIn(Tags.Blocks.STONE) && reader.getBlockState(TARGET_POS.up()).isIn(Tags.Blocks.ORES_COAL)) {
                            reader.setBlockState(TARGET_POS, RankineBlocks.FIRE_CLAY.get().getDefaultState(), 2);
                            if (rand.nextFloat()<0.5 && reader.getBlockState(TARGET_POS.down()).isIn(Tags.Blocks.STONE)) {
                                reader.setBlockState(TARGET_POS.down(), RankineBlocks.FIRE_CLAY.get().getDefaultState(), 2);
                            }
                        }
                    }


                }
            }
        }
        return true;
    }

}
