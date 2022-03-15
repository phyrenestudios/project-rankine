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

public class RedAsphaltBlock extends BaseAsphaltBlock {

    public RedAsphaltBlock() {
        super();
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        Direction dir = player.getDirection();
        if (Tags.Items.DYES.contains(player.getItemInHand(handIn).getItem())) {
            if (state.getValue(FACING) != dir) {
                worldIn.setBlock(pos, state.setValue(FACING, dir), 3);
            } else {
                worldIn.setBlock(pos, RankineLists.RED_ASPHALT_BLOCKS.get((RankineLists.RED_ASPHALT_BLOCKS.indexOf(state.getBlock())+1)%RankineLists.RED_ASPHALT_BLOCKS.size()).defaultBlockState().setValue(SIZE,state.getValue(SIZE)).setValue(FACING, dir), 3);
            }
            return ActionResultType.SUCCESS;
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }
}
