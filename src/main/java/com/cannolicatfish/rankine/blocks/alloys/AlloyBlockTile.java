package com.cannolicatfish.rankine.blocks.alloys;

import com.cannolicatfish.rankine.init.RankineBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

public class AlloyBlockTile extends BlockEntity {
    private CompoundTag alloyData = new CompoundTag();
    public AlloyBlockTile(BlockPos posIn, BlockState stateIn) {
        super(RankineBlockEntityTypes.ALLOY_BLOCK.get(), posIn, stateIn);
    }


    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        if (this.alloyData != null) compound.put("AlloyData", this.alloyData);
    }

    @Override
    public void load(CompoundTag nbt) {
        if (nbt.contains("AlloyData")) this.alloyData = nbt.getCompound("AlloyData");
        super.load(nbt);
    }

    public CompoundTag writeAlloyData(CompoundTag compoundNBT) {
        this.alloyData = compoundNBT;
        return compoundNBT;
    }

    public CompoundTag getAlloyData() {
        return this.alloyData;
    }
}
