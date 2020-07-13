package com.cannolicatfish.rankine.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.SimplePlacement;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ReplacerPlacement extends SimplePlacement<NoPlacementConfig> {
    public ReplacerPlacement(Codec<NoPlacementConfig> p_i232085_1_) {
        super(p_i232085_1_);
    }

    public Stream<BlockPos> getPositions(Random random, NoPlacementConfig p_212852_2_, BlockPos pos) {
        int i = 1;
        return IntStream.range(0, i).mapToObj((p_215060_2_) -> {
            int j = random.nextInt(16);
            int k = random.nextInt(28) + 4;
            int l = random.nextInt(16);
            return pos.add(j, k, l);
        });
    }
}