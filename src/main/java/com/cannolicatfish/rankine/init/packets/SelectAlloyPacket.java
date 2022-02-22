package com.cannolicatfish.rankine.init.packets;

import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.IOException;
import java.util.function.Supplier;

public class SelectAlloyPacket {
    public final int index;

    public SelectAlloyPacket(PacketBuffer buffer) {
        this.index = buffer.readVarInt();
    }

    @OnlyIn(Dist.CLIENT)
    public SelectAlloyPacket(int index) {
        this.index = index;
    }

    public static void encode(SelectAlloyPacket packet, PacketBuffer buffer) {
        buffer.writeVarInt(packet.index);
    }

    public static void handle(SelectAlloyPacket packet, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        /*ctx.enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> RankineClientPacketHandler.handlePacket(packet, context))
        );*/
        ctx.setPacketHandled(true);
    }
}
