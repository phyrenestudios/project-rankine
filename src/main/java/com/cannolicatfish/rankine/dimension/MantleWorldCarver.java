package com.cannolicatfish.rankine.dimension;

import com.cannolicatfish.rankine.init.ModBlocks;
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
        return p_222726_1_.nextInt(p_222726_1_.nextInt(252) + 3);
    }

    @Override
    protected float generateCaveRadius(Random p_222722_1_) {
        float lvt_2_1_ = p_222722_1_.nextFloat() * 8.0F + p_222722_1_.nextFloat();
        if (p_222722_1_.nextInt(10) == 0) {
            lvt_2_1_ *= p_222722_1_.nextFloat() * p_222722_1_.nextFloat() * 3.0F + 1.0F;
        }

        return lvt_2_1_;
    }
}
