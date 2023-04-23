package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.block_groups.RankineStone;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.CreativeModeTabEvent;

public class CreativeTabsHandler {
    public static CreativeModeTab BLOCKS;
    public static void registerTabs(CreativeModeTabEvent.Register event) {
        BLOCKS = event.registerCreativeModeTab(new ResourceLocation(ProjectRankine.MODID, "blocks_tab"), builder -> builder
                .icon(() -> new ItemStack(RankineBlocks.REFRACTORY_BRICKS.getBricksBlock()))
                .title(Component.translatable("itemGroup.rankine.blocks")));
    }

    public static void addItemsToTabs(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == BLOCKS) {
            for (RankineStone Stone : RankineLists.RANKINE_STONES) {
                for (Block blk : Stone.getStoneBlocks()) {
                    event.accept(blk);
                }
            }
        }
    }

}
