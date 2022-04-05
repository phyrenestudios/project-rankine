package com.cannolicatfish.rankine.blocks.gasvent;

import com.cannolicatfish.rankine.blocks.GasBlock;
import com.cannolicatfish.rankine.init.RankineTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class GasVentTile extends TileEntity implements ITickableTileEntity {

    public GasVentTile() {
        super(RankineTileEntities.GAS_VENT.get());
    }

    public void tick() {
        if (!world.isAreaLoaded(pos, 1)) return;
        BlockState BS = world.getBlockState(pos);
        Block toMove = world.getBlockState(pos.offset(BS.get(BlockStateProperties.FACING).getOpposite())).getBlock();
        if (toMove instanceof GasBlock && world.getBlockState(pos.offset(BS.get(BlockStateProperties.FACING))).matchesBlock(Blocks.AIR)) {
            world.removeBlock(pos.offset(BS.get(BlockStateProperties.FACING).getOpposite()), false);
            world.setBlockState(pos.offset(BS.get(BlockStateProperties.FACING)),toMove.getDefaultState(),3);
        }
    }

}
