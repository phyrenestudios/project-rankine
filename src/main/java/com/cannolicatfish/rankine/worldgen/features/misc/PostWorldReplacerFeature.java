package com.cannolicatfish.rankine.worldgen.features.misc;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.util.ReplacementUtils;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class PostWorldReplacerFeature extends Feature<NoneFeatureConfiguration> {
    public PostWorldReplacerFeature(Codec<NoneFeatureConfiguration> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159749_) {
        if (!Config.WORLDGEN.RETRO_GEN.get()) {
            ReplacementUtils.performRetrogenReplacement(p_159749_.level().getChunk(p_159749_.origin()));
        }
        return true;
    }


}


