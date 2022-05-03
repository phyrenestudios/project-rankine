package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class FumaroleFeature extends Feature<NoneFeatureConfiguration> {
    public FumaroleFeature(Codec<NoneFeatureConfiguration> p_i49915_1_) {
        super(p_i49915_1_);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159749_) {
        WorldGenLevel reader = p_159749_.level();
        BlockPos pos = p_159749_.origin();
        Random rand = reader.getRandom();

        ChunkAccess chunk = reader.getChunk(pos);
        int randX = chunk.getPos().getMinBlockX() + rand.nextInt(16) + 8;
        int randZ = chunk.getPos().getMinBlockZ() + rand.nextInt(16) + 8;
        int yHeight;
        Block FUMAROLE;
        if (Biome.getBiomeCategory(Holder.direct(reader.getBiome(new BlockPos(randX,0,randZ)).value())) == Biome.BiomeCategory.NETHER) {
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
