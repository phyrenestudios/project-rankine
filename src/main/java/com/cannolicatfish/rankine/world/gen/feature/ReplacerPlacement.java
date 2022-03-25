package com.cannolicatfish.rankine.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.configurations.NoneDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.DecorationContext;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ReplacerPlacement extends FeatureDecorator<NoneDecoratorConfiguration> {
    public ReplacerPlacement(Codec<NoneDecoratorConfiguration> p_i232085_1_) {
        super(p_i232085_1_);
    }

    @Override
    public Stream<BlockPos> getPositions(DecorationContext p_70713_, Random random, NoneDecoratorConfiguration p_70715_, BlockPos pos) {
        int i = 1;
        return IntStream.range(0, i).mapToObj((p_215060_2_) -> {
            int j = random.nextInt(16);
            int k = random.nextInt(28) + 4;
            int l = random.nextInt(16);
            return pos.offset(j, k, l);
        });
    }

}