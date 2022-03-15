package com.cannolicatfish.rankine.blocks.ptc;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import org.jetbrains.annotations.Nullable;

public class PeriodicTableChestContainer extends Container {


    public PeriodicTableChestContainer(int windowId, PlayerInventory playerInventory, PeriodicTableChestTile periodicTableChestTile) {
        super(ContainerType.GENERIC_9x1,windowId);
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return false;
    }


    @Override
    public void removed(PlayerEntity playerIn) {
        super.removed(playerIn);
        //this.inventory.closeInventory(playerIn);
    }
}
