package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.blocks.RankineOre;
import com.cannolicatfish.rankine.init.ModBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.Random;
import java.util.function.Function;

public class IntrusionFeature extends Feature<ReplacerFeatureConfig> {
    public IntrusionFeature(Codec<ReplacerFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, ReplacerFeatureConfig config) {

        BlockState INTRUSION = null;
        float CHANCE = rand.nextFloat();
        int BOT = 0;
        int TOP = 0;

        if (CHANCE < 0.075f) {
            INTRUSION = ModBlocks.KIMBERLITE.getDefaultState();
            BOT = 0;
            TOP = 25;
        } else if (CHANCE < 0.12f) {
            INTRUSION = ModBlocks.RED_GRANITE.getDefaultState();
            BOT = 0;
            TOP = 256;
        } else if (CHANCE < 0.2f) {
            INTRUSION = ModBlocks.GRANODIORITE.getDefaultState();
            BOT = 0;
            TOP = 256;
        }

        IChunk chunk = reader.getChunk(pos);
        int startX = chunk.getPos().getXStart() - rand.nextInt(6);
        int startZ = chunk.getPos().getZStart() - rand.nextInt(6);
        int endX = chunk.getPos().getXEnd() - rand.nextInt(6);
        int endZ = chunk.getPos().getZEnd() - rand.nextInt(6);
        int startY;
        int endY;
        if (reader.getBiome(pos).getCategory() != Biome.Category.THEEND && reader.getBiome(pos).getCategory() != Biome.Category.NETHER) {
            startY = BOT;
            if (reader.getHeight() > TOP) {
                endY = TOP - rand.nextInt(10);
            } else {
                endY = reader.getHeight() - rand.nextInt(10);
            }
        } else {
            startY = BOT + rand.nextInt(60);
            endY = TOP;
        }

        if (INTRUSION != null) {
            for (int y = startY; y <= endY; ++y) {
                if (rand.nextFloat() < 0.05) {
                    startZ += 1;
                    endZ -= 1;
                    startX += 1;
                    endX -= 1;
                }
                for (int z = startZ; z <= endZ; ++z) {
                    for (int x = startX; x <= endX; ++x) {
                        if (y == startY && y - 1 > 0 && reader.getBlockState(new BlockPos(x, y - 1, z)).getBlock() == config.target.getBlock() && rand.nextFloat() < (1 / 2f)) {
                            reader.setBlockState(new BlockPos(x, y - 1, z), INTRUSION, 2);
                            if (y - 2 > 0 && reader.getBlockState(new BlockPos(x, y - 2, z)).getBlock() == config.target.getBlock() && rand.nextFloat() < (1 / 2f)) {
                                reader.setBlockState(new BlockPos(x, y - 2, z), INTRUSION, 2);
                            }
                        }
                        if (y == endY && y + 1 < 128 && reader.getBlockState(new BlockPos(x, y + 1, z)).getBlock() == config.target.getBlock() && rand.nextFloat() < (1 / 2f)) {
                            reader.setBlockState(new BlockPos(x, y + 1, z), INTRUSION, 2);
                            if (y + 2 < 128 && reader.getBlockState(new BlockPos(x, y + 2, z)).getBlock() == config.target.getBlock() && rand.nextFloat() < (1 / 2f)) {
                                reader.setBlockState(new BlockPos(x, y + 2, z), INTRUSION, 2);
                            }
                        }
                        if (INTRUSION.getBlock() == ModBlocks.KIMBERLITE) {
                            if (reader.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                                if (rand.nextFloat() < 0.03) {
                                    reader.setBlockState(new BlockPos(x, y, z), ModBlocks.DIAMOND_ORE.getDefaultState().with(RankineOre.TYPE,23), 2);
                                } else {
                                    reader.setBlockState(new BlockPos(x, y, z), INTRUSION, 2);
                                }
                            }
                        } else {
                            if (reader.getBlockState(new BlockPos(x, y, z)).getBlock() == config.target.getBlock()) {
                                if (rand.nextInt(4) != 1) {
                                    reader.setBlockState(new BlockPos(x, y, z), INTRUSION, 2);
                                }
                            }
                        }


                    }
                }
            }
        }
            return true;
    }
}
