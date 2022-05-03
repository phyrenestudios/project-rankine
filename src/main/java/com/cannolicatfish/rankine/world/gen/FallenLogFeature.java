package com.cannolicatfish.rankine.world.gen;

import com.cannolicatfish.rankine.init.Config;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class FallenLogFeature extends Feature<NoneFeatureConfiguration> {
    public FallenLogFeature(Codec<NoneFeatureConfiguration> p_i49915_1_) {
        super(p_i49915_1_);
    }
    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159749_) {
        WorldGenLevel reader = p_159749_.level();
        BlockPos pos = p_159749_.origin();
        Random rand = reader.getRandom();

        if (rand.nextFloat() < Config.MISC_WORLDGEN.END_METEORITE_CHANCE.get()) {

        }
        return true;
    }

}
