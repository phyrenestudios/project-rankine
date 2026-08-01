package com.cannolicatfish.rankine.init.packets;

import com.cannolicatfish.rankine.ProjectRankine;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

/**
 * 1.21.1 replacement for the old SimpleChannel in RankinePacketHandler.
 * Payload handlers are registered on the MOD bus; handlers run on the main thread
 * (PayloadRegistrar wraps them) — enqueueWork is therefore implicit.
 */
@EventBusSubscriber(modid = ProjectRankine.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class RankinePayloads {

    private RankinePayloads() {}

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToClient(FluidStackPacket.TYPE, FluidStackPacket.STREAM_CODEC,
                (payload, context) -> context.enqueueWork(() -> RankineClientPacketHandler.handlePacket(payload, context)));

        // 1.18.2 behavior: SelectAlloyPacket's handler was entirely commented out (no-op). Preserved.
        registrar.playToServer(SelectAlloyPacket.TYPE, SelectAlloyPacket.STREAM_CODEC,
                (payload, context) -> {});
    }
}
