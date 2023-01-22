package com.cannolicatfish.rankine.blocks.cauldrons;

import com.cannolicatfish.rankine.init.RankineFluids;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class ResinCauldronBlock extends AbstractRankineCauldronBlock {

    @Override
    public Item getBottle() {
        return RankineItems.RESIN_BUCKET.get();
    }
    @Override
    public Item getOutput() {
        return RankineItems.AMBER.get();
    }

    @Override
    public Fluid getFluid() {
        return RankineFluids.RESIN;
    }

    @Override
    public Fluid getTransformFluid() {
        return Fluids.EMPTY;
    }
}
