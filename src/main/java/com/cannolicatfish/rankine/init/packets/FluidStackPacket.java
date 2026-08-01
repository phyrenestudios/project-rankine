package com.cannolicatfish.rankine.init.packets;

import com.cannolicatfish.rankine.ProjectRankine;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidStack;

/**
 * Server -> client sync of a machine tank's contents (fusion furnace, mixing barrel, tree tap).
 * 1.21.1: CustomPacketPayload record; registered in {@link RankinePayloads}.
 */
public record FluidStackPacket(FluidStack fluidStack, BlockPos pos, boolean input) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<FluidStackPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(ProjectRankine.MODID, "fluid_stack"));

    public static final StreamCodec<RegistryFriendlyByteBuf, FluidStackPacket> STREAM_CODEC =
            StreamCodec.composite(
                    FluidStack.OPTIONAL_STREAM_CODEC, FluidStackPacket::fluidStack,
                    BlockPos.STREAM_CODEC, FluidStackPacket::pos,
                    ByteBufCodecs.BOOL, FluidStackPacket::input,
                    FluidStackPacket::new);

    @Override
    public CustomPacketPayload.Type<FluidStackPacket> type() {
        return TYPE;
    }
}
