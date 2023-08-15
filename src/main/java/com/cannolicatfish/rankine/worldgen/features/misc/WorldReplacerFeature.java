package com.cannolicatfish.rankine.worldgen.features.misc;

import com.cannolicatfish.rankine.util.worldgen.OverworldReplacer;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class WorldReplacerFeature extends Feature<NoneFeatureConfiguration> {

    public WorldReplacerFeature(Codec<NoneFeatureConfiguration> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel levelIn = context.level();
        ChunkAccess chunkIn = levelIn.getChunk(context.origin());
        return OverworldReplacer.replace(levelIn, chunkIn);
    }

}


