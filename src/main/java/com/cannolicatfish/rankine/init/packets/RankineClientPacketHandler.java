package com.cannolicatfish.rankine.init.packets;

import com.cannolicatfish.rankine.blocks.fusionfurnace.FusionFurnaceTile;
import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelTile;
import com.cannolicatfish.rankine.blocks.tap.TreeTapTile;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class RankineClientPacketHandler {

    public static void handlePacket(FluidStackPacket packet, IPayloadContext context) {
        Level worldIn = Minecraft.getInstance().level;
        if (worldIn != null && worldIn.hasChunkAt(packet.pos())) {
            BlockPos pos = packet.pos();
            FluidStack fluidStack = packet.fluidStack();
            if (worldIn.getBlockEntity(pos) instanceof FusionFurnaceTile tile) {
                if (packet.input()) {
                    tile.getInputTank().setFluid(fluidStack);
                } else {
                    tile.getOutputTank().setFluid(fluidStack);
                }
            } else if (worldIn.getBlockEntity(pos) instanceof MixingBarrelTile tile) {
                tile.getInputTank().setFluid(fluidStack);
            } else if (worldIn.getBlockEntity(pos) instanceof TreeTapTile tile) {
                tile.getOutputTank().setFluid(fluidStack);
            }
        }
    }
}
