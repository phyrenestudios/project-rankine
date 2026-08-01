// PORT-TODO(main): register RankineAttachments.ATTACHMENT_TYPES on the mod bus in ProjectRankine's constructor.
package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.ProjectRankine;
import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * NeoForge data attachments replacing the old Forge capabilities in
 * {@code com.cannolicatfish.rankine.capabilities} (ChunkRetrogenProvider et al.).
 *
 * <p>RETROGEN_DONE replaces the old {@code IChunkRetrogenHandler} chunk capability
 * ("rankine:retrogen_chunk", NBT key "retrogenComplete"). It is persisted with the chunk.
 * Note: data saved by the 1.18.2 capability lives under ForgeCaps and is NOT migrated —
 * chunks from old worlds will run retrogen replacement once more after updating.
 */
public class RankineAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, ProjectRankine.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> RETROGEN_DONE =
            ATTACHMENT_TYPES.register("retrogen_chunk", () -> AttachmentType.builder(() -> Boolean.FALSE)
                    .serialize(Codec.BOOL)
                    .build());
}
