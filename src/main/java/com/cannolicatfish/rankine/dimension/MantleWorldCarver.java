package com.cannolicatfish.rankine.dimension;

import com.cannolicatfish.rankine.blocks.ModBlocks;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.Random;
import java.util.function.Function;

public class MantleWorldCarver extends CaveWorldCarver {
    public MantleWorldCarver(Function<Dynamic<?>, ? extends ProbabilityConfig> p_i49929_1_, int p_i49929_2_) {
        super(p_i49929_1_,255);
        this.carvableBlocks = ImmutableSet.of(Blocks.STONE, ModBlocks.PERIDOTITE, ModBlocks.BRIDGMANITE, ModBlocks.KIMBERLITE, ModBlocks.PEROVSKITE, ModBlocks.WADSLEYITE, ModBlocks.RINGWOODITE);
        this.carvableFluids = ImmutableSet.of(Fluids.LAVA, Fluids.WATER);
    }

    @Override
    protected int generateCaveStartY(Random p_222726_1_) {
        return p_222726_1_.nextInt(p_222726_1_.nextInt(248) + 8);
    }
}
