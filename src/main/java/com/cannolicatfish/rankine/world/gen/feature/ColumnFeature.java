package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.blocks.StoneColumnBlock;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineLists;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class ColumnFeature extends Feature<NoFeatureConfig> {
    public ColumnFeature(Codec<NoFeatureConfig> p_i49915_1_) {
        super(p_i49915_1_);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        for (int X = 0; X < 16; ++ X) {
            for (int Z = 0; Z < 16; ++ Z) {
                if (rand.nextFloat() < Config.MISC_WORLDGEN.COLUMN_CHANCE.get()) {
                    IChunk chunk = reader.getChunk(pos);
                    int randX = chunk.getPos().getXStart() + X;
                    int randZ = chunk.getPos().getZStart() + Z;

                    BlockPos topPos = new BlockPos(randX, 0, randZ);
                    BlockPos bottomPos;
                    int endY = reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, topPos.getX(), topPos.getZ());
                    if (reader.getBiome(topPos).getCategory() != Biome.Category.NETHER && reader.getBiome(topPos).getCategory() != Biome.Category.THEEND) {
                        for (int y = endY; y > 1; --y) {
                            topPos = new BlockPos(randX, y, randZ);
                            if (RankineLists.STONES.contains(reader.getBlockState(topPos).getBlock()) && reader.getBlockState(topPos.down()).isAir(reader,topPos.down())) {
                                bottomPos = getBottom(reader, topPos);
                                if (bottomPos != null) {
                                    if (reader.getBlockState(bottomPos).isSolid()) {
                                        if (rand.nextFloat() < Config.MISC_WORLDGEN.COLUMN_FREQUENCY.get()) {
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

    public static void createStalactite(ISeedReader reader, BlockPos topPos, BlockPos bottomPos) {
        float length = reader.getRandom().nextInt(Math.round((topPos.getY() - bottomPos.getY())/2f)) + 1f;
        int maxSize = reader.getRandom().nextInt(2) + Math.round(length/2f);
        for (int i = 1; i <= length; ++i) {
            int SIZE = Math.max(Math.round(maxSize * (1 - (i / (length)))), 0);
            BlockState BS = reader.getBlockState(topPos.down(i));
            reader.setBlockState(topPos.down(i), SIZE < 1 ? BS : RankineLists.STONE_COLUMNS.get(RankineLists.STONES.indexOf(reader.getBlockState(topPos).getBlock())).getDefaultState().with(StoneColumnBlock.SIZE, Math.min(SIZE,7)).with(StoneColumnBlock.WATERLOGGED, BS.matchesBlock(Blocks.WATER)), 3);
        }
    }


    public static void createColumn(ISeedReader reader, BlockPos topPos, BlockPos bottomPos) {
        float length = topPos.getY() - bottomPos.getY();
        int maxSize = reader.getRandom().nextInt(2) + Math.round(length/2f);
        float usedLength = length - reader.getRandom().nextInt(Math.round(length/2f));
        for (int i = 1; i < length; ++i) {
            int SIZE = i < length/2f ? Math.max(Math.round(maxSize * (1 - (i / (usedLength)))), 0) : Math.max(Math.round(maxSize * ((i-(length-usedLength)) / (usedLength))), 0);
            BlockState BS = reader.getBlockState(topPos.down(i));
            reader.setBlockState(topPos.down(i), SIZE < 1 ? BS : RankineLists.STONE_COLUMNS.get(RankineLists.STONES.indexOf(reader.getBlockState(topPos).getBlock())).getDefaultState().with(StoneColumnBlock.SIZE, Math.min(SIZE,7)).with(StoneColumnBlock.WATERLOGGED, BS.matchesBlock(Blocks.WATER)), 3);
        }
    }

    public static BlockPos getBottom(ISeedReader worldIn, BlockPos topPos) {
        for (int i = 2; i < topPos.getY(); ++i) {
            if (!worldIn.getBlockState(topPos.down(i)).isAir(worldIn,topPos.down(i)) && !worldIn.getBlockState(topPos.down(i)).matchesBlock(Blocks.WATER)) {
                return topPos.down(i);
            }
        }
        return null;
    }

}
