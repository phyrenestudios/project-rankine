package com.cannolicatfish.rankine.blocks.particleaccelerator;
import com.cannolicatfish.rankine.init.RankineBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ParticleAcceleratorTile extends BlockEntity {

    public ParticleAcceleratorTile(BlockPos posIn, BlockState stateIn) {
        super(RankineBlockEntityTypes.PARTICLE_ACCELERATOR.get(), posIn, stateIn);
    }

    public void tick() {
        if (!level.isAreaLoaded(worldPosition, 1)) return;
        if (level.hasNeighborSignal(worldPosition)) {

        }
    }
}
