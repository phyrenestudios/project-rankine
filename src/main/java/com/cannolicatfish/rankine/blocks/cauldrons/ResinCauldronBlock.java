package com.cannolicatfish.rankine.blocks.cauldrons;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.item.Item;

public class ResinCauldronBlock extends AbstractRankineCauldronBlock {

    @Override
    public Item getBottle() {
        return RankineItems.RESIN_BUCKET.get();
    }
    @Override
    public Item getOutput() {
        return RankineItems.AMBER.get();
    }
}
