package com.cannolicatfish.rankine.events.handlers.common;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineAttachments;
import com.cannolicatfish.rankine.util.ReplacementUtils;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.neoforged.neoforge.event.level.ChunkEvent;

public class ChunkLoadHandler {
    // 1.21.1: the retrogen chunk capability became the RankineAttachments.RETROGEN_DONE attachment.
    // ChunkAccess implements IAttachmentHolder, so getData/setData replace LazyOptional plumbing.
    // getStatus() -> getPersistedStatus(); ChunkStatus moved to ...chunk.status.
    public static void retrogenChunk(ChunkEvent.Load event) {
        if (!event.getLevel().isClientSide() && Config.WORLDGEN.RETRO_GEN.get()) {
            ChunkAccess chunkAccess = event.getChunk();
            if (chunkAccess.getPersistedStatus().isOrAfter(ChunkStatus.FULL)) {
                if (!chunkAccess.getData(RankineAttachments.RETROGEN_DONE.get())) {
                    ReplacementUtils.performRetrogenReplacement(chunkAccess);
                    chunkAccess.setData(RankineAttachments.RETROGEN_DONE.get(), true);
                }
            }
        }

    }
}
