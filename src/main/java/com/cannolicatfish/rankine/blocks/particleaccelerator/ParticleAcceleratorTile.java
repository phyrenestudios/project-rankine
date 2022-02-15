package com.cannolicatfish.rankine.blocks.particleaccelerator;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

import static com.cannolicatfish.rankine.init.RankineBlocks.PARTICLE_ACCELERATOR_TILE;

public class ParticleAcceleratorTile extends TileEntity implements ITickableTileEntity {

    public ParticleAcceleratorTile() {
        super(PARTICLE_ACCELERATOR_TILE);
    }

    public void tick() {
        if (!world.isAreaLoaded(pos, 1)) return;
        if (world.isBlockPowered(pos)) {

        }
    }
}
