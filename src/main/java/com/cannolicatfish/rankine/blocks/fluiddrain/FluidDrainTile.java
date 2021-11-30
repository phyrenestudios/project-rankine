package com.cannolicatfish.rankine.blocks.fluiddrain;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

import static com.cannolicatfish.rankine.init.RankineBlocks.FLUID_DRAIN_TILE;

public class FluidDrainTile extends TileEntity implements ITickableTileEntity {

    public FluidDrainTile() {
        super(FLUID_DRAIN_TILE);
    }


    public void tick() {
        /*
        World worldIn = this.getWorld();
        BlockState state = worldIn.getBlockState(pos.up());
        if (worldIn.isAirBlock(pos.down()) && state.getBlock().matchesBlock(RankineBlocks.TAP_BARREL.get())) {
            int i = state.get(TapBarrelBlock.LEVEL);
            if (!worldIn.isRemote && i > 0) {
                TapBarrelFluids fluid = worldIn.getBlockState(pos.up()).get(TapBarrelBlock.FLUID);
                switch (fluid.toString()) {
                    case "water":
                    case "lava":
                        worldIn.setBlockState(pos.down(), ForgeRegistries.BLOCKS.getValue(new ResourceLocation("minecraft:"+fluid)).getDefaultState());
                        break;
                    default:
                        worldIn.setBlockState(pos.down(), ForgeRegistries.BLOCKS.getValue(new ResourceLocation("rankine:"+fluid)).getDefaultState());
                        break;
                }
                worldIn.setBlockState(pos.up(), RankineBlocks.TAP_BARREL.get().getDefaultState().with(TapBarrelBlock.FLUID, fluid).with(TapBarrelBlock.LEVEL, i-1), 2);
            }
        }

         */
    }

}
