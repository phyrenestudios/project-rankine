package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.WandererTradesEvent;

public class WandererTradesHandler {
    public static void addWandererTrades(WandererTradesEvent event) {
        if (Config.GENERAL.VILLAGER_TRADES.get()) {
            event.getGenericTrades().add(new BasicItemListing(1,new ItemStack(RankineItems.PINEAPPLE.get(), 1),4,1,0.5f));
            event.getGenericTrades().add(new BasicItemListing(1,new ItemStack(RankineBlocks.LIMESTONE.get(), 8),8,1,0.05f));
            event.getRareTrades().add(new BasicItemListing(3,new ItemStack(RankineItems.METEORIC_IRON.get()),6,1,0.5f));
        }
    }
}
