package com.cannolicatfish.rankine.util;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;

public class ReplacementUtils {

    public static void performRetrogenReplacement(ChunkAccess chunk) {
        for (int x = chunk.getPos().getMinBlockX(); x <= chunk.getPos().getMaxBlockX(); ++x) {
            for (int z = chunk.getPos().getMinBlockZ(); z <= chunk.getPos().getMaxBlockZ(); ++z) {
                int endY = chunk.getHeight();
                for (int y = -64; y <= endY; ++y) {
                   /* BlockState blockState = chunk.getBlockState(new BlockPos(x, y, z));
                    if (blockState.getBlock().equals(RankineBlocks.PLATINUM_BLOCK.get()) || RankineLists.STONES.contains(blockState.getBlock())){
                        chunk.setBlockState(new BlockPos(x,y,z), RankineBlocks.COPERNICIUM_BLOCK.get().defaultBlockState(),false);
                    }*/
                }
            }
        }
        chunk.setUnsaved(true);
    }
}
