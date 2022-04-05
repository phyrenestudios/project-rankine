package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.Config;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.world.SaplingGrowTreeEvent;
import net.minecraftforge.eventbus.api.Event;

public class SaplingGrowHandler {
    public static void onSaplingGrow( SaplingGrowTreeEvent event) {
        if (event.getRand().nextFloat() < 1- Config.GENERAL.SAPLING_GROW.get()) {
            event.setResult(Event.Result.DENY);
        }
        BlockPos pos = event.getPos();
        IWorld worldIn = event.getWorld();
        if (worldIn.getBlockState(pos).matchesBlock(Blocks.SPRUCE_SAPLING)) {

        }
    }
}
