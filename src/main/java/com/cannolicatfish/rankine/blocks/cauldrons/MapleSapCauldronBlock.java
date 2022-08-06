package com.cannolicatfish.rankine.blocks.cauldrons;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.item.Item;

public class MapleSapCauldronBlock extends AbstractRankineCauldronBlock {

    @Override
    public Item getBottle() {
        return RankineItems.MAPLE_SAP_BUCKET.get();
    }
    @Override
    public Item getOutput() {
        return RankineItems.MAPLE_SYRUP.get();
    }
}
