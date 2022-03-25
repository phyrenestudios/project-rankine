package com.cannolicatfish.rankine.blocks.particleaccelerator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import static com.cannolicatfish.rankine.init.RankineBlocks.PARTICLE_ACCELERATOR_TILE;

public class ParticleAcceleratorTile extends BlockEntity {

    public ParticleAcceleratorTile(BlockPos posIn, BlockState stateIn) {
        super(PARTICLE_ACCELERATOR_TILE, posIn, stateIn);
    }

    public void tick() {
        if (!level.isAreaLoaded(worldPosition, 1)) return;
        if (level.hasNeighborSignal(worldPosition)) {

        }
    }
}
