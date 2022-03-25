package com.cannolicatfish.rankine.init.packets;

import com.cannolicatfish.rankine.blocks.fusionfurnace.FusionFurnaceTile;
import com.cannolicatfish.rankine.blocks.mixingbarrel.MixingBarrelTile;
import com.cannolicatfish.rankine.blocks.tap.TreeTapTile;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class RankineClientPacketHandler {

    public static void handlePacket(FluidStackPacket packet, Supplier<NetworkEvent.Context> context) {
        Level worldIn = Minecraft.getInstance().level;
        if (worldIn != null && worldIn.hasChunkAt(packet.pos)) {
            BlockPos pos = packet.pos;
            FluidStack fluidStack = packet.fluidStack;
            if (worldIn.getBlockEntity(pos) instanceof FusionFurnaceTile) {
                FusionFurnaceTile tile = ((FusionFurnaceTile) worldIn.getBlockEntity(pos));
                if (tile != null) {
                    if (packet.input) {
                        tile.getInputTank().setFluid(fluidStack);
                    } else {
                        tile.getOutputTank().setFluid(fluidStack);
                    }
                }
            } else if (worldIn.getBlockEntity(pos) instanceof MixingBarrelTile) {
                MixingBarrelTile tile = ((MixingBarrelTile) worldIn.getBlockEntity(pos));
                if (tile != null) {
                    tile.getInputTank().setFluid(fluidStack);
                }
            } else if (worldIn.getBlockEntity(pos) instanceof TreeTapTile) {
                TreeTapTile tile = ((TreeTapTile) worldIn.getBlockEntity(pos));
                if (tile != null) {
                    tile.getOutputTank().setFluid(fluidStack);
                }
            }

        }
    }
}
