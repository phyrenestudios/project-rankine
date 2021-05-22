package com.cannolicatfish.rankine.blocks.svl;


import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import static com.cannolicatfish.rankine.init.RankineBlocks.SODIUM_VAPOR_LAMP_CONTAINER;

public class SodiumVaporLampContainer extends Container {
    private TileEntity tileEntity;
    private PlayerEntity playerEntity;
    private IItemHandler playerInventory;
    private World world;

    public SodiumVaporLampContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(SODIUM_VAPOR_LAMP_CONTAINER, windowId);
        tileEntity = world.getTileEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.world = playerEntity.world;

        layoutPlayerInventorySlots(10, 86);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, RankineBlocks.SODIUM_VAPOR_LAMP.get());
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }



}