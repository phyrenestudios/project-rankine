package com.cannolicatfish.rankine.blocks.cauldrons;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class MapleSyrupCauldronBlock extends AbstractRankineCauldronBlock {

    @Override
    public Item getBottle() {
        return RankineItems.MAPLE_SYRUP.get();
    }
    @Override
    public Item getOutput() {
        return null;
    }

    @Override
    public Fluid getFluid() {
        return null;
    }

    @Override
    public Fluid getTransformFluid() {
        return Fluids.EMPTY;
    }
}
