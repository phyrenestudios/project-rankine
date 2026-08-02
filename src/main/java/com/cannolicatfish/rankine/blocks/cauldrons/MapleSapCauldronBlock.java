package com.cannolicatfish.rankine.blocks.cauldrons;

import com.cannolicatfish.rankine.init.RankineFluids;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class MapleSapCauldronBlock extends AbstractRankineCauldronBlock {

    @Override
    public Item getBottle() {
        return RankineItems.MAPLE_SAP_BUCKET.get();
    }
    @Override
    public Item getOutput() {
        return RankineItems.MAPLE_SYRUP.get();
    }

    @Override
    public Fluid getFluid() {
        return RankineFluids.MAPLE_SAP.get();
    }

    @Override
    public Fluid getTransformFluid() {
        return Fluids.EMPTY;
    }
}
