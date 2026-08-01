package com.cannolicatfish.rankine.init.packets;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Client -> server notification of the selected alloy recipe in the template table screen.
 * NOTE: the 1.18.2 handler was a no-op (handling code was commented out); that behavior is preserved
 * in {@link RankinePayloads} where this payload is registered with an empty handler.
 */
public record SelectAlloyPacket(int index) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SelectAlloyPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "select_alloy"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SelectAlloyPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT, SelectAlloyPacket::index,
                    SelectAlloyPacket::new);

    @Override
    public CustomPacketPayload.Type<SelectAlloyPacket> type() {
        return TYPE;
    }
}
