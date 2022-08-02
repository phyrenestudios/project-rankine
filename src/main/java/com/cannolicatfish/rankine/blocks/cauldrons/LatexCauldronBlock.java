package com.cannolicatfish.rankine.blocks.cauldrons;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.item.Item;

public class LatexCauldronBlock extends AbstractRankineCauldronBlock {

    @Override
    public Item getBottle() {
        return RankineItems.LATEX_BUCKET.get();
    }
    @Override
    public Item getOutput() {
        return RankineItems.DRY_RUBBER.get();
    }
}
