package com.cannolicatfish.rankine.world.gen.feature;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTags;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class FumaroleFeature extends Feature<NoFeatureConfig> {
    public FumaroleFeature(Codec<NoFeatureConfig> p_i49915_1_) {
        super(p_i49915_1_);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        IChunk chunk = reader.getChunk(pos);
        int randX = chunk.getPos().getXStart() + rand.nextInt(16) + 8;
        int randZ = chunk.getPos().getZStart() + rand.nextInt(16) + 8;
        int yHeight;
        Block FUMAROLE;
        if (reader.getBiome(new BlockPos(randX,0,randZ)).getCategory() == Biome.Category.NETHER) {
            yHeight = 30;
            for (int y = yHeight; y<100; ++y) {
                if (reader.getBlockState(new BlockPos(randX, y + 1, randZ)).matchesBlock(Blocks.AIR)) {
                    yHeight=y;
                    break;
                }
            }
            FUMAROLE = rand.nextFloat() < 0.5 ? RankineBlocks.HYDROGEN_SULFIDE_FUMAROLE.get() : RankineBlocks.HYDROGEN_CHLORIDE_FUMAROLE.get();
        } else {
            yHeight = 11;
            for (int y = yHeight; y<40; ++y) {
                if (reader.getBlockState(new BlockPos(randX, y + 1, randZ)).matchesBlock(Blocks.AIR)) {
                    yHeight=y;
                    break;
                }
            }
            FUMAROLE = rand.nextFloat() < 0.5 ? RankineBlocks.CARBON_DIOXIDE_FUMAROLE.get() : RankineBlocks.SULFUR_DIOXIDE_FUMAROLE.get();
        }

        BlockPos POS = new BlockPos(randX, yHeight, randZ);
        reader.setBlockState(POS, FUMAROLE.getDefaultState(), 3);
        reader.setBlockState(POS.down(), Blocks.MAGMA_BLOCK.getDefaultState(), 3);
        for (BlockPos blockpos : BlockPos.getAllInBoxMutable(POS.add(-2,-2,-2),POS.add(2,0,2))) {
            if (rand.nextFloat() < 0.5 && reader.getBlockState(blockpos).isIn(RankineTags.Blocks.FUMAROLE_DEPOSIT) && blockpos.distanceSq(POS)<9) {
                reader.setBlockState(blockpos, RankineBlocks.FUMAROLE_DEPOSIT.get().getDefaultState(), 3);
            }
        }
        for (BlockPos blockpos : BlockPos.getAllInBoxMutable(POS.add(-2,1,-2),POS.add(2,3,2))) {
            if (reader.getBlockState(blockpos).isIn(RankineTags.Blocks.FUMAROLE_DEPOSIT) && blockpos.distanceSq(POS)<9) {
                reader.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 3);
            }
        }

        return true;
    }


}
