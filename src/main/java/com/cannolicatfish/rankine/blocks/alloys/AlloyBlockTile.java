package com.cannolicatfish.rankine.blocks.alloys;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.ForgeHooks;

import static com.cannolicatfish.rankine.init.RankineBlocks.ALLOY_BLOCK_TILE;

public class AlloyBlockTile extends BlockEntity {
    private CompoundTag alloyData = new CompoundTag();
    public AlloyBlockTile(BlockPos posIn, BlockState stateIn) {
        super(ALLOY_BLOCK_TILE, posIn, stateIn);
    }


    @Override
    public CompoundTag save(CompoundTag compound) {
        if (this.alloyData != null) compound.put("AlloyData", this.alloyData);
        return super.save(compound);
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
