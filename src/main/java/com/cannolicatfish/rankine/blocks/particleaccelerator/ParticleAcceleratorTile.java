package com.cannolicatfish.rankine.blocks.particleaccelerator;

import com.cannolicatfish.rankine.init.RankineTileEntities;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class ParticleAcceleratorTile extends TileEntity implements ITickableTileEntity {

    public ParticleAcceleratorTile() {
        super(RankineTileEntities.PARTICLE_ACCELERATOR.get());
    }

    public void tick() {
        if (!world.isAreaLoaded(pos, 1)) return;
        if (world.isBlockPowered(pos)) {

        }
    }
}
