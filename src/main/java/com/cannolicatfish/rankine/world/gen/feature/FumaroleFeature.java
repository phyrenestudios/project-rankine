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
    public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {

        IChunk chunk = reader.getChunk(pos);
        int randX = chunk.getPos().getMinBlockX() + rand.nextInt(16) + 8;
        int randZ = chunk.getPos().getMinBlockZ() + rand.nextInt(16) + 8;
        int yHeight;
        Block FUMAROLE;
        if (reader.getBiome(new BlockPos(randX,0,randZ)).getBiomeCategory() == Biome.Category.NETHER) {
            yHeight = 30;
            for (int y = 80; y>=yHeight; --y) {
                if (reader.getBlockState(new BlockPos(randX, y, randZ)).is(RankineTags.Blocks.FUMAROLE_DEPOSIT) && reader.getBlockState(new BlockPos(randX, y + 1, randZ)).is(Blocks.AIR)) {
                    yHeight=y;
                    break;
                }
            }
            FUMAROLE = rand.nextFloat() < 0.5 ? RankineBlocks.HYDROGEN_SULFIDE_FUMAROLE.get() : RankineBlocks.HYDROGEN_CHLORIDE_FUMAROLE.get();
        } else {
            yHeight = 11;
            for (int y = 40; y>=yHeight; --y) {
                if (reader.getBlockState(new BlockPos(randX, y, randZ)).is(RankineTags.Blocks.FUMAROLE_DEPOSIT) && reader.getBlockState(new BlockPos(randX, y + 1, randZ)).is(Blocks.AIR)) {
                    yHeight=y;
                    break;
                }
            }
            FUMAROLE = rand.nextFloat() < 0.5 ? RankineBlocks.CARBON_DIOXIDE_FUMAROLE.get() : RankineBlocks.SULFUR_DIOXIDE_FUMAROLE.get();
        }

        BlockPos POS = new BlockPos(randX, yHeight, randZ);
        reader.setBlock(POS, FUMAROLE.defaultBlockState(), 3);
        reader.setBlock(POS.below(), Blocks.MAGMA_BLOCK.defaultBlockState(), 3);
        for (BlockPos blockpos : BlockPos.betweenClosed(POS.offset(-2,-2,-2),POS.offset(2,0,2))) {
            if (rand.nextFloat() < 0.5 && reader.getBlockState(blockpos).is(RankineTags.Blocks.FUMAROLE_DEPOSIT) && blockpos.distSqr(POS)<9) {
                reader.setBlock(blockpos, RankineBlocks.FUMAROLE_DEPOSIT.get().defaultBlockState(), 3);
            }
        }
        for (BlockPos blockpos : BlockPos.betweenClosed(POS.offset(-2,1,-2),POS.offset(2,3,2))) {
            if (reader.getBlockState(blockpos).is(RankineTags.Blocks.FUMAROLE_DEPOSIT) && blockpos.distSqr(POS)<9) {
                reader.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 3);
            }
        }

        return true;
    }


}
