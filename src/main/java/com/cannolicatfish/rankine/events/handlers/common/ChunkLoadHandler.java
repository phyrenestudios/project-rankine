package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.capabilities.ChunkRetrogenProvider;
import com.cannolicatfish.rankine.capabilities.IChunkRetrogenHandler;
import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.util.ReplacementUtils;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.level.ChunkEvent;

public class ChunkLoadHandler {
    public static void retrogenChunk(ChunkEvent.Load event) {
        if (!event.getLevel().isClientSide() && Config.WORLDGEN.RETRO_GEN.get()) {
            ChunkAccess chunkAccess = event.getChunk();
            if (chunkAccess instanceof ICapabilityProvider && chunkAccess.getStatus().isOrAfter(ChunkStatus.FULL)) {
                LazyOptional<IChunkRetrogenHandler> capability = ((ICapabilityProvider) chunkAccess).getCapability(ChunkRetrogenProvider.CAPABILITY, null);
                if (capability.isPresent() && capability.resolve().isPresent() && !capability.resolve().get().getValue()) {
                    ReplacementUtils.performRetrogenReplacement(chunkAccess);
                    capability.ifPresent(iChunkRetrogenHandler -> iChunkRetrogenHandler.setValue(true));
                }
            }
        }

    }
}
