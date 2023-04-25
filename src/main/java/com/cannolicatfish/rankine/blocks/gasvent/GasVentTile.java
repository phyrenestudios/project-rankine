package com.cannolicatfish.rankine.blocks.gasvent;

import com.cannolicatfish.rankine.blocks.gases.GasBlock;
import com.cannolicatfish.rankine.init.RankineBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.entity.BlockEntity;

public class GasVentTile extends BlockEntity {

    public GasVentTile(BlockPos posIn, BlockState stateIn) {
        super(RankineBlockEntityTypes.GAS_VENT.get(), posIn, stateIn);
    }

    public static void tick(Level level, BlockPos pos, BlockState bs, GasVentTile tile) {
        if (!level.isAreaLoaded(tile.worldPosition, 1)) return;
        BlockState BS = level.getBlockState(tile.worldPosition);
        Block toMove = level.getBlockState(tile.worldPosition.relative(BS.getValue(BlockStateProperties.FACING).getOpposite())).getBlock();
        if (toMove instanceof GasBlock && level.getBlockState(tile.worldPosition.relative(BS.getValue(BlockStateProperties.FACING))).is(Blocks.AIR)) {
            level.removeBlock(tile.worldPosition.relative(BS.getValue(BlockStateProperties.FACING).getOpposite()), false);
            level.setBlock(tile.worldPosition.relative(BS.getValue(BlockStateProperties.FACING)),toMove.defaultBlockState(),3);
        }
    }

}
