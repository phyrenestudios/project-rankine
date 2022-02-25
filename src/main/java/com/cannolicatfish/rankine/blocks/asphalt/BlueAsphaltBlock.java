package com.cannolicatfish.rankine.blocks.asphalt;

import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

public class BlueAsphaltBlock extends BaseAsphaltBlock {

    public BlueAsphaltBlock() {
        super();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        Direction dir = player.getHorizontalFacing();
        if (Tags.Items.DYES.contains(player.getHeldItem(handIn).getItem())) {
            if (state.get(HORIZONTAL_FACING) != dir) {
                worldIn.setBlockState(pos, state.with(HORIZONTAL_FACING, dir), 3);
            } else {
                worldIn.setBlockState(pos, RankineLists.BLUE_ASPHALT_BLOCKS.get((RankineLists.BLUE_ASPHALT_BLOCKS.indexOf(state.getBlock())+1)%RankineLists.BLUE_ASPHALT_BLOCKS.size()).getDefaultState().with(SIZE,state.get(SIZE)).with(HORIZONTAL_FACING, dir), 3);
            }
            return ActionResultType.SUCCESS;
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
