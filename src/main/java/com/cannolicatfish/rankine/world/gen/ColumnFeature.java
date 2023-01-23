package com.cannolicatfish.rankine.world.gen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class ColumnFeature extends Feature<NoneFeatureConfiguration> {
    public ColumnFeature(Codec<NoneFeatureConfiguration> p_i49915_1_) {
        super(p_i49915_1_);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159749_) {
        WorldGenLevel reader = p_159749_.level();
        BlockPos pos = p_159749_.origin();
        Random rand = reader.getRandom();
        for (int X = 0; X < 16; ++ X) {
            for (int Z = 0; Z < 16; ++ Z) {
                if (rand.nextFloat() < 1.0f) {
                    ChunkAccess chunk = reader.getChunk(pos);
                    int randX = chunk.getPos().getMinBlockX() + X;
                    int randZ = chunk.getPos().getMinBlockZ() + Z;

                    BlockPos topPos = new BlockPos(randX, 0, randZ);
                    BlockPos bottomPos;
                    int endY = reader.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, topPos.getX(), topPos.getZ());
                    if (Biome.getBiomeCategory(Holder.direct(reader.getBiome(topPos).value())) != Biome.BiomeCategory.NETHER && Biome.getBiomeCategory(Holder.direct(reader.getBiome(topPos).value())) != Biome.BiomeCategory.THEEND) {
                        for (int y = endY; y > 1; --y) {
                            topPos = new BlockPos(randX, y, randZ);
                            //if (RankineLists.STONES.contains(reader.getBlockState(topPos).getBlock()) && reader.getBlockState(topPos.below()).isAir()) {
                            if (reader.getBlockState(topPos.below()).isAir()) {
                                bottomPos = getBottom(reader, topPos);
                                if (bottomPos != null) {
                                    if (reader.getBlockState(bottomPos).canOcclude()) {
                                        if (rand.nextFloat() < 1.0f) {
                                            createColumn(reader, topPos, bottomPos);
                                        } else {
                                            createStalactite(reader, topPos, bottomPos);
                                        }
                                    } else {
                                        createStalactite(reader,topPos,bottomPos);
                                    }
                                    y = bottomPos.getY();
                                }


                            }
                        }
                    } else {
                        return false;
                    }
                }
            }
        }




        return true;
    }

    public static void createStalactite(WorldGenLevel reader, BlockPos topPos, BlockPos bottomPos) {
        float length = reader.getRandom().nextInt((int) Math.round(2*Math.sqrt(topPos.getY() - bottomPos.getY()))) + 1f;
        if (length > 12) return;
        int maxSize = reader.getRandom().nextInt(2) + Math.round(length/2f);
        for (int i = 1; i <= length; ++i) {
            int SIZE = Math.max(Math.round(maxSize * (1 - (i / (length)))), 0);
            BlockState BS = reader.getBlockState(topPos.below(i));
            //reader.setBlock(topPos.below(i), SIZE < 1 ? BS : RankineLists.STONE_COLUMNS.get(RankineLists.STONES.indexOf(reader.getBlockState(topPos).getBlock())).defaultBlockState().setValue(StoneColumnBlock.SIZE, Math.min(SIZE,7)).setValue(StoneColumnBlock.WATERLOGGED, BS.is(Blocks.WATER)), 3);
        }
    }


    public static void createColumn(WorldGenLevel reader, BlockPos topPos, BlockPos bottomPos) {
        float length = topPos.getY() - bottomPos.getY();
        if (length > 32) return;
        int maxSize = reader.getRandom().nextInt(2) + Math.round(length/2f);
        float usedLength = length - reader.getRandom().nextInt(Math.round(length/2f));
        for (int i = 1; i < length; ++i) {
            int SIZE = i < length/2f ? Math.max(Math.round(maxSize * (1 - (i / (usedLength)))), 0) : Math.max(Math.round(maxSize * ((i-(length-usedLength)) / (usedLength))), 0);
            BlockState BS = reader.getBlockState(topPos.below(i));
            //reader.setBlock(topPos.below(i), SIZE < 1 ? BS : RankineLists.STONE_COLUMNS.get(RankineLists.STONES.indexOf(reader.getBlockState(topPos).getBlock())).defaultBlockState().setValue(StoneColumnBlock.SIZE, Math.min(SIZE,7)).setValue(StoneColumnBlock.WATERLOGGED, BS.is(Blocks.WATER)), 3);
        }
    }

    public static BlockPos getBottom(WorldGenLevel worldIn, BlockPos topPos) {
        for (int i = 2; i < topPos.getY(); ++i) {
            if (!worldIn.getBlockState(topPos.below(i)).isAir() && !worldIn.getBlockState(topPos.below(i)).is(Blocks.WATER)) {
                return topPos.below(i);
            }
        }
        return null;
    }

}
