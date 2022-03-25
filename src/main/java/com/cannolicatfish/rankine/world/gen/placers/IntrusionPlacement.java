package com.cannolicatfish.rankine.world.gen.placers;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.placement.DecorationContext;
import net.minecraft.world.level.levelgen.placement.ChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.stream.Stream;

public class IntrusionPlacement extends FeatureDecorator<ChanceDecoratorConfiguration> {
    public IntrusionPlacement(Codec<ChanceDecoratorConfiguration> codec) {
        super(codec);
    }

    public @NotNull Stream<BlockPos> getPositions(DecorationContext helper, Random rand, ChanceDecoratorConfiguration config, BlockPos pos) {
        if (rand.nextInt(config.chance) == 0) {
            int i = rand.nextInt(16) + pos.getX();
            int j = rand.nextInt(16) + pos.getZ();
            int k = 1;
            return Stream.of(new BlockPos(i, k, j));
        }

        return Stream.empty();
    }
}
