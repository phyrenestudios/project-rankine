package com.cannolicatfish.rankine.blocks.alloys;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ForgeHooks;

import static com.cannolicatfish.rankine.init.RankineBlocks.ALLOY_BLOCK_TILE;

public class AlloyBlockTile extends TileEntity {
    public AlloyBlockTile() {
        super(ALLOY_BLOCK_TILE);
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
}
