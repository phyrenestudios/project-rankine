package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.init.Config;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class FlatBedrockFeature extends Feature<NoneFeatureConfiguration> {
    public FlatBedrockFeature(Codec<NoneFeatureConfiguration> configFactoryIn) {
        super(configFactoryIn);
    }


    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159749_) {
        WorldGenLevel reader = p_159749_.level();
        BlockPos pos = p_159749_.origin();
        ChunkAccess chunk = reader.getChunk(pos);
        BlockState baseBlock;
        if (reader.getBiome(pos).is(BiomeTags.IS_NETHER)) {
            baseBlock = Blocks.NETHERRACK.defaultBlockState();
        } else {
            baseBlock = Blocks.STONE.defaultBlockState();
        }


        for (int x = chunk.getPos().getMinBlockX(); x <= chunk.getPos().getMaxBlockX(); ++x) {
            for (int z = chunk.getPos().getMinBlockZ(); z <= chunk.getPos().getMaxBlockZ(); ++z) {
                for (int y = reader.getMinBuildHeight(); y < reader.getMinBuildHeight() + Config.WORLDGEN.BEDROCK_LAYERS.get(); ++y) {
                    reader.setBlock(new BlockPos(x, y, z), Blocks.BEDROCK.defaultBlockState(), 2);
                }
                for (int y = reader.getMinBuildHeight() + Config.WORLDGEN.BEDROCK_LAYERS.get(); y <= reader.getMinBuildHeight() + 5; ++y) {
                    if (reader.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.BEDROCK) {
                        reader.setBlock(new BlockPos(x, y, z), baseBlock, 2);
                    }
                }
            }
        }

        return true;
    }
}
