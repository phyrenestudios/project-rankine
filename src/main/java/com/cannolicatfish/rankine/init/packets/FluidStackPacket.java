package com.cannolicatfish.rankine.init.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.fml.DistExecutor;
import net.neoforged.neoforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FluidStackPacket {

    public final FluidStack fluidStack;
    public final BlockPos pos;
    public final boolean input;

    public FluidStackPacket(FriendlyByteBuf buffer) {
        this.fluidStack = buffer.readFluidStack();
        this.pos = buffer.readBlockPos();
        this.input = buffer.readBoolean();
    }

    public FluidStackPacket(FluidStack fluidStackIn, BlockPos pos, boolean input) {
        this.fluidStack = fluidStackIn;
        this.pos = pos;
        this.input = input;
    }

    public static void encode(FluidStackPacket packet, FriendlyByteBuf buffer) {
        buffer.writeFluidStack(packet.fluidStack);
        buffer.writeBlockPos(packet.pos);
        buffer.writeBoolean(packet.input);
    }

    public static void handle(FluidStackPacket packet, Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> RankineClientPacketHandler.handlePacket(packet, context))
        );
        ctx.setPacketHandled(true);
    }
}
