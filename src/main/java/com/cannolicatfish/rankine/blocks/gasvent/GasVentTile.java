package com.cannolicatfish.rankine.blocks.gasvent;

import com.cannolicatfish.rankine.blocks.GasBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.entity.BlockEntity;

import static com.cannolicatfish.rankine.init.RankineBlocks.GAS_VENT_TILE;

public class GasVentTile extends BlockEntity {

    public GasVentTile(BlockPos posIn, BlockState stateIn) {
        super(GAS_VENT_TILE, posIn, stateIn);
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
