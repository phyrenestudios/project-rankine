package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.Config;
import com.cannolicatfish.rankine.blocks.RankineOre;
import com.cannolicatfish.rankine.init.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;

import java.util.Objects;
import java.util.Random;

public class NetherIntrusionFeature extends Feature<ReplacerFeatureConfig> {
    public NetherIntrusionFeature(Codec<ReplacerFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, ReplacerFeatureConfig config) {

        float STONE = rand.nextFloat();
        BlockState INTRUSION = null;

        int endY = 45 + rand.nextInt(40);
        int radius = 6-rand.nextInt(4);

        if (Objects.equals(reader.getBiome(pos).getRegistryName(), new ResourceLocation("minecraft:warped_forest"))) {
            if (STONE < 0.06) {
                INTRUSION = ModBlocks.WADSLEYITE.getDefaultState();
            } else if (STONE < 0.12) {
                INTRUSION = ModBlocks.RINGWOODITE.getDefaultState();
            }
        } else if (Objects.equals(reader.getBiome(pos).getRegistryName(), new ResourceLocation("minecraft:soul_sand_valley"))) {
            if (STONE < 0.06) {
                INTRUSION = ModBlocks.BRIDGMANITE.getDefaultState();
            } else if (STONE < 0.12) {
                INTRUSION = ModBlocks.PEROVSKITE.getDefaultState();
            }
        } else if (Objects.equals(reader.getBiome(pos).getRegistryName(), new ResourceLocation("minecraft:crimson_forest"))) {
            if (STONE < 0.12) {
                INTRUSION = ModBlocks.KOMATIITE.getDefaultState();
            }
        } else if (Objects.equals(reader.getBiome(pos).getRegistryName(), new ResourceLocation("minecraft:basalt_deltas"))) {
            if (STONE < 0.12) {
                INTRUSION = ModBlocks.FERROPERICLASE.getDefaultState();
            }
        } else if (Objects.equals(reader.getBiome(pos).getRegistryName(), new ResourceLocation("minecraft:nether_wastes"))) {
            if (STONE < 0.06) {
                INTRUSION = ModBlocks.SCORIA.getDefaultState();
            } else if (STONE < 0.12) {
                INTRUSION = ModBlocks.PUMICE.getDefaultState();
            }
        }

        if (INTRUSION!= null) {
            for (int y = 0; y <= endY; ++y) {
                for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-radius, y - 1, -radius), pos.add(radius, y - 1, radius))) {
                    if (blockpos.distanceSq(new BlockPos(pos.getX(), y, pos.getZ())) <= Math.pow(radius + 0.5, 2)) {
                        if (reader.getBlockState(blockpos) != Blocks.BEDROCK.getDefaultState()) {
                            if (INTRUSION == ModBlocks.KOMATIITE.getDefaultState()) {
                                if (rand.nextFloat() < Config.INTERSPINIFEX_CHANCE.get().floatValue()) {
                                    reader.setBlockState(blockpos, ModBlocks.INTERSPINIFEX_ORE.getDefaultState().with(RankineOre.TYPE, 29), 4);
                                } else {
                                    reader.setBlockState(blockpos, INTRUSION, 4);
                                }
                            } else {
                                reader.setBlockState(blockpos, INTRUSION, 4);
                            }
                        }
                    }
                }
                if (rand.nextFloat() < 0.1) {
                    radius -= 1;
                    if (radius <= 0) {
                        break;
                    }
                }
            }
            for (int y = endY; y <= 127; ++y) {
                for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-radius, y - 1, -radius), pos.add(radius, y - 1, radius))) {
                    if (blockpos.distanceSq(new BlockPos(pos.getX(), y, pos.getZ())) <= Math.pow(radius + 0.5, 2)) {
                        if (reader.getBlockState(blockpos) != Blocks.BEDROCK.getDefaultState()) {
                            if (INTRUSION == ModBlocks.KOMATIITE.getDefaultState()) {
                                if (rand.nextFloat() < Config.INTERSPINIFEX_CHANCE.get().floatValue()) {
                                    reader.setBlockState(blockpos, ModBlocks.INTERSPINIFEX_ORE.getDefaultState().with(RankineOre.TYPE, 29), 4);
                                } else {
                                    reader.setBlockState(blockpos, INTRUSION, 4);
                                }
                            } else {
                                reader.setBlockState(blockpos, INTRUSION, 4);
                            }
                        }
                    }
                }
                if (rand.nextFloat() < 0.08) {
                    radius += 1;
                }
            }
        }
        return true;
    }


}
