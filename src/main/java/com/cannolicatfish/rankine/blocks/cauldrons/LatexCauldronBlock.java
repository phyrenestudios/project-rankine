package com.cannolicatfish.rankine.blocks.cauldrons;

import com.cannolicatfish.rankine.init.RankineFluids;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class LatexCauldronBlock extends AbstractRankineCauldronBlock {

    @Override
    public Item getBottle() {
        return RankineItems.LATEX_BUCKET.get();
    }
    @Override
    public Item getOutput() {
        return RankineItems.DRY_RUBBER.get();
    }

    @Override
    public Fluid getFluid() {
        return RankineFluids.LATEX;
    }

    @Override
    public Fluid getTransformFluid() {
        return Fluids.EMPTY;
    }
}
