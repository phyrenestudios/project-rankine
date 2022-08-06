package com.cannolicatfish.rankine.blocks.cauldrons;

import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class SapCauldronBlock extends AbstractRankineCauldronBlock {

    @Override
    public Item getBottle() {
        return RankineItems.SAP_BUCKET.get();
    }
    @Override
    public Item getOutput() {
        return Items.SUGAR;
    }
}
