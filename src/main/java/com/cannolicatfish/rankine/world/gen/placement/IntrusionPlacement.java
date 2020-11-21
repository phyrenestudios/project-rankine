package com.cannolicatfish.rankine.world.gen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.stream.Stream;

public class IntrusionPlacement extends Placement<ChanceConfig> {
    public IntrusionPlacement(Codec<ChanceConfig> codec) {
        super(codec);
    }

    public @NotNull Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, ChanceConfig config, BlockPos pos) {
        if (rand.nextInt(config.chance) == 0) {
            int i = rand.nextInt(16) + pos.getX();
            int j = rand.nextInt(16) + pos.getZ();
            int k = 1;
            return Stream.of(new BlockPos(i, k, j));
        }

        return Stream.empty();
    }
}
