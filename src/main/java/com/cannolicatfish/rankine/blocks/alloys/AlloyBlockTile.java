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
    private CompoundNBT alloyData = new CompoundNBT();
    public AlloyBlockTile() {
        super(ALLOY_BLOCK_TILE);
    }


    @Override
    public CompoundNBT save(CompoundNBT compound) {
        if (this.alloyData != null) compound.put("AlloyData", this.alloyData);
        return super.save(compound);
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        if (nbt.contains("AlloyData")) this.alloyData = nbt.getCompound("AlloyData");
        super.load(state, nbt);
    }

    public CompoundNBT writeAlloyData(CompoundNBT compoundNBT) {
        this.alloyData = compoundNBT;
        return compoundNBT;
    }

    public CompoundNBT getAlloyData() {
        return this.alloyData;
    }
}
