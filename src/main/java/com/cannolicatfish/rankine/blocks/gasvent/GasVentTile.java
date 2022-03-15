package com.cannolicatfish.rankine.blocks.gasvent;

import com.cannolicatfish.rankine.blocks.GasBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

import static com.cannolicatfish.rankine.init.RankineBlocks.GAS_VENT_TILE;

public class GasVentTile extends TileEntity implements ITickableTileEntity {

    public GasVentTile() {
        super(GAS_VENT_TILE);
    }

    public void tick() {
        if (!level.isAreaLoaded(worldPosition, 1)) return;
        BlockState BS = level.getBlockState(worldPosition);
        Block toMove = level.getBlockState(worldPosition.relative(BS.getValue(BlockStateProperties.FACING).getOpposite())).getBlock();
        if (toMove instanceof GasBlock && level.getBlockState(worldPosition.relative(BS.getValue(BlockStateProperties.FACING))).is(Blocks.AIR)) {
            level.removeBlock(worldPosition.relative(BS.getValue(BlockStateProperties.FACING).getOpposite()), false);
            level.setBlock(worldPosition.relative(BS.getValue(BlockStateProperties.FACING)),toMove.defaultBlockState(),3);
        }
    }

}
