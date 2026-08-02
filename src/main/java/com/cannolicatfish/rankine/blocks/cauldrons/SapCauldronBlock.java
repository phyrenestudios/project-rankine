package com.cannolicatfish.rankine.blocks.cauldrons;

import com.cannolicatfish.rankine.init.RankineFluids;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class SapCauldronBlock extends AbstractRankineCauldronBlock {

    @Override
    public Item getBottle() {
        return RankineItems.SAP_BUCKET.get();
    }
    @Override
    public Item getOutput() {
        return Items.SUGAR;
    }

    @Override
    public Fluid getFluid() {
        return RankineFluids.SAP.get();
    }

    @Override
    public Fluid getTransformFluid() {
        return Fluids.EMPTY;
    }
}
