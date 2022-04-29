package com.cannolicatfish.rankine.blocks.alloys;

import com.cannolicatfish.rankine.init.RankineTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public class AlloyBlockTile extends TileEntity {
    private CompoundNBT alloyData = new CompoundNBT();
    public AlloyBlockTile() {
        super(RankineTileEntities.ALLOY_BLOCK.get());
    }


    @Override
    public CompoundNBT write(CompoundNBT compound) {
        if (this.alloyData != null) compound.put("AlloyData", this.alloyData);
        return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        if (nbt.contains("AlloyData")) this.alloyData = nbt.getCompound("AlloyData");
        super.read(state, nbt);
    }

    public CompoundNBT writeAlloyData(CompoundNBT compoundNBT) {
        this.alloyData = compoundNBT;
        return compoundNBT;
    }

    public CompoundNBT getAlloyData() {
        return this.alloyData;
    }
}
