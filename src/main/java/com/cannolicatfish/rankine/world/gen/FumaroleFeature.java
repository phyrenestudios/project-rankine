package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.ForgeRegistries;

public class FumaroleFeature extends Feature<NoneFeatureConfiguration> {
    public FumaroleFeature(Codec<NoneFeatureConfiguration> p_i49915_1_) {
        super(p_i49915_1_);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159749_) {
        WorldGenLevel reader = p_159749_.level();
        BlockPos pos = p_159749_.origin();
        RandomSource rand = reader.getRandom();

        ChunkAccess chunk = reader.getChunk(pos);
        int randX = chunk.getPos().getMinBlockX() + rand.nextInt(16) + 8;
        int randZ = chunk.getPos().getMinBlockZ() + rand.nextInt(16) + 8;
        int yHeight;

        if (ForgeRegistries.BIOMES.tags().getTag(BiomeTags.IS_NETHER).contains((reader.getBiome(new BlockPos(randX,0,randZ)).value()))) {
            yHeight = 10;
            for (int y = 120; y>=yHeight; --y) {
                BlockPos check = new BlockPos(randX, y, randZ);
                if (reader.getBlockState(check).is(RankineTags.Blocks.FUMAROLE_DEPOSIT) && spaceAbove(reader, check)) {
                    genFumarole(reader, check, rand.nextFloat() < 0.5 ? RankineBlocks.HYDROGEN_SULFIDE_FUMAROLE.get() : RankineBlocks.HYDROGEN_CHLORIDE_FUMAROLE.get());
                    return true;
                }
            }
        } else {
            yHeight = -50;
            for (int y = reader.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, randX, randZ) - 10; y>=yHeight; --y) {
                BlockPos check = new BlockPos(randX, y, randZ);
                if (reader.getBlockState(check).is(RankineTags.Blocks.FUMAROLE_DEPOSIT) && spaceAbove(reader, check)) {
                    genFumarole(reader, check, rand.nextFloat() < 0.5 ? RankineBlocks.CARBON_DIOXIDE_FUMAROLE.get() : RankineBlocks.SULFUR_DIOXIDE_FUMAROLE.get());
                    return true;
                }
            }
        }
        return false;
    }

    private static void genFumarole(WorldGenLevel levelIn, BlockPos pos, Block fumarole) {
        levelIn.setBlock(pos, fumarole.defaultBlockState(), 3);

        levelIn.setBlock(pos.below(), Blocks.MAGMA_BLOCK.defaultBlockState(), 3);
        for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-2,-2,-2),pos.offset(2,0,2))) {
            if (levelIn.getRandom().nextFloat() < 0.8 && levelIn.getBlockState(blockpos).is(RankineTags.Blocks.FUMAROLE_DEPOSIT) && blockpos.distSqr(pos)<9) {
                levelIn.setBlock(blockpos, RankineBlocks.FUMAROLE_DEPOSIT.get().defaultBlockState(), 3);
            }
        }
        for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-2,1,-2),pos.offset(2,3,2))) {
            if (levelIn.getBlockState(blockpos).is(RankineTags.Blocks.FUMAROLE_DEPOSIT) && blockpos.distSqr(pos)<9) {
                levelIn.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 3);
            }
        }
    }

    private static boolean spaceAbove(WorldGenLevel levelIn, BlockPos pos) {
        return levelIn.getBlockState(pos.above()).isAir() || !levelIn.getFluidState(pos.above()).isEmpty();
    }
}
