package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTags;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
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

public class FumaroleFeature extends Feature<NoFeatureConfig> {
    public FumaroleFeature(Codec<NoFeatureConfig> p_i49915_1_) {
        super(p_i49915_1_);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        IChunk chunk = reader.getChunk(pos);
        int randX = chunk.getPos().getXStart() + rand.nextInt(16);
        int randZ = chunk.getPos().getZStart() + rand.nextInt(16);

        if (reader.getBiome(new BlockPos(randX,0,randZ)).getCategory() == Biome.Category.NETHER) {
            int yHeight = 5;
            for (int y = yHeight; y<127; ++y) {
                BlockPos pos1 = new BlockPos(randX, y + 1, randZ);
                if (reader.getBlockState(pos1).matchesBlock(Blocks.AIR) || reader.getBlockState(pos1).matchesBlock(Blocks.WATER) || reader.getBlockState(pos1).matchesBlock(Blocks.LAVA)) {
                    yHeight=y;
                    break;
                }
            }
            BlockPos POS = new BlockPos(randX, yHeight - 1, randZ);
            Block FUMAROLE = rand.nextFloat() < 0.5 ? RankineBlocks.CARBON_DIOXIDE_FUMAROLE.get() : RankineBlocks.SULFUR_DIOXIDE_FUMAROLE.get();
            reader.setBlockState(POS, FUMAROLE.getDefaultState(), 2);
            reader.setBlockState(POS.down(), Blocks.MAGMA_BLOCK.getDefaultState(), 2);
            reader.setBlockState(POS.down().east(), Blocks.MAGMA_BLOCK.getDefaultState(), 2);
            reader.setBlockState(POS.down().north(), Blocks.MAGMA_BLOCK.getDefaultState(), 2);
            reader.setBlockState(POS.down().south(), Blocks.MAGMA_BLOCK.getDefaultState(), 2);
            reader.setBlockState(POS.down().west(), Blocks.MAGMA_BLOCK.getDefaultState(), 2);
            for (BlockPos blockpos : BlockPos.getAllInBoxMutable(POS.add(-4,-4,-4),POS.add(4,4,4))) {
                if (reader.getBlockState(blockpos).isIn(RankineTags.Blocks.FUMAROLE_DEPOSIT) && blockpos.distanceSq(POS)<12) {
                    reader.setBlockState(blockpos, RankineBlocks.FUMAROLE_DEPOSIT.get().getDefaultState(), 2);
                }
            }
        } else {
            int yHeight = 5;
            for (int y = yHeight; y<200; ++y) {
                BlockPos pos1 = new BlockPos(randX, y + 1, randZ);
                if (reader.getBlockState(pos1).matchesBlock(Blocks.AIR) || reader.getBlockState(pos1).matchesBlock(Blocks.WATER) || reader.getBlockState(pos1).matchesBlock(Blocks.LAVA)) {
                    yHeight=y;
                    break;
                }
            }
            BlockPos POS = new BlockPos(randX, yHeight, randZ);
            Block FUMAROLE = rand.nextFloat() < 0.5 ? RankineBlocks.CARBON_DIOXIDE_FUMAROLE.get() : RankineBlocks.SULFUR_DIOXIDE_FUMAROLE.get();
            reader.setBlockState(POS, FUMAROLE.getDefaultState(), 2);
            reader.setBlockState(POS.down(), Blocks.MAGMA_BLOCK.getDefaultState(), 2);
            reader.setBlockState(POS.down().east(), Blocks.MAGMA_BLOCK.getDefaultState(), 2);
            reader.setBlockState(POS.down().north(), Blocks.MAGMA_BLOCK.getDefaultState(), 2);
            reader.setBlockState(POS.down().south(), Blocks.MAGMA_BLOCK.getDefaultState(), 2);
            reader.setBlockState(POS.down().west(), Blocks.MAGMA_BLOCK.getDefaultState(), 2);
            for (BlockPos blockpos : BlockPos.getAllInBoxMutable(POS.add(-4,-4,-4),POS.add(4,4,4))) {
                if (reader.getBlockState(blockpos).isIn(RankineTags.Blocks.FUMAROLE_DEPOSIT) && blockpos.distanceSq(POS)<12) {
                    reader.setBlockState(blockpos, RankineBlocks.FUMAROLE_DEPOSIT.get().getDefaultState(), 2);
                }
            }
        }


        return true;
    }


}
