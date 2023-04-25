package com.cannolicatfish.rankine.blocks.cauldrons;

import com.cannolicatfish.rankine.init.RankineFluids;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class JugloneCauldronBlock extends AbstractRankineCauldronBlock {

    @Override
    public Item getBottle() {
        return RankineItems.JUGLONE_BUCKET.get();
    }
    @Override
    public Item getOutput() {
        return Items.BROWN_DYE;
    }
    @Override
    public Fluid getFluid() {
        return RankineFluids.JUGLONE.get();
    }

    @Override
    public Fluid getTransformFluid() {
        return Fluids.EMPTY;
    }
}
