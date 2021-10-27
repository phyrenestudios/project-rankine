package com.cannolicatfish.rankine.init.packets;

import com.cannolicatfish.rankine.blocks.fusionfurnace.FusionFurnaceTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RankineClientPacketHandler {

    public static void handlePacket(FluidStackPacket packet, Supplier<NetworkEvent.Context> context) {
        World worldIn = Minecraft.getInstance().world;
        if (worldIn != null && worldIn.isBlockLoaded(packet.pos)) {
            BlockPos pos = packet.pos;
            FluidStack fluidStack = packet.fluidStack;
            FusionFurnaceTile tile = ((FusionFurnaceTile) worldIn.getTileEntity(pos));
            if (tile != null) {
                if (packet.input) {
                    tile.getInputTank().setFluid(fluidStack);
                } else {
                    tile.getOutputTank().setFluid(fluidStack);
                }
            }
        }
    }
}
