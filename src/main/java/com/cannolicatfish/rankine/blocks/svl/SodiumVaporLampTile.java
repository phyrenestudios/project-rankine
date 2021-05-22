package com.cannolicatfish.rankine.blocks.svl;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;

import static com.cannolicatfish.rankine.init.RankineBlocks.SODIUM_VAPOR_LAMP_TILE;

public class SodiumVaporLampTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    public SodiumVaporLampTile() {
        super(SODIUM_VAPOR_LAMP_TILE);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);

        return compound;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Override
    @Nullable
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new SodiumVaporLampContainer(i, world, pos, playerInventory, playerEntity);
    }

    @Override
    public void tick() {

    }
}
