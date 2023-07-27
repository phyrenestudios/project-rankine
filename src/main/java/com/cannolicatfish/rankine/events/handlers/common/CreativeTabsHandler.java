package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.blocks.block_enums.BricksBlocks;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTabsHandler {
    public static CreativeModeTab BLOCKS;

    public static void registerTabs(CreativeModeTabEvent.Register event) {
        ProjectRankine.LOGGER.info("Registering Project Rankine Creative Tab");
        BLOCKS = event.registerCreativeModeTab(new ResourceLocation(ProjectRankine.MODID, "blocks_tab"), builder -> builder
                .icon(() -> new ItemStack(BricksBlocks.REFRACTORY_BRICKS.getBricksBlock()))
                .title(Component.translatable("itemGroup.rankine.blocks"))
        );
    }

    public static void addItemsToTabs(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == BLOCKS) {
            RankineBlocks.ITEMS.getEntries().stream().map(RegistryObject::get)
                .forEach(block -> event.accept(block.asItem()));
            RankineItems.ITEMS.getEntries().stream().map(RegistryObject::get)
                .forEach(block -> event.accept(block.asItem()));

            /*
            for (RankineStone Stone : RankineLists.RANKINE_STONES) {
                for (Block blk : Stone.getStoneBlocks()) {
                    event.accept(blk);
                }
            }

             */
        }
    }

}
