package com.cannolicatfish.rankine.init.packets;

import com.cannolicatfish.rankine.init.packets.FluidStackPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

public class RankinePacketHandler {

    public static int id = 0;

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("rankine", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        INSTANCE.registerMessage(id++, FluidStackPacket.class, FluidStackPacket::encode, FluidStackPacket::new, FluidStackPacket::handle);
        INSTANCE.registerMessage(id++, SelectAlloyPacket.class, SelectAlloyPacket::encode, SelectAlloyPacket::new, SelectAlloyPacket::handle);
    }
}
