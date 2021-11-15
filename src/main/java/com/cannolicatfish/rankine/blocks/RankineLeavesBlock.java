package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.Config;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.extensions.IForgeBlock;

import java.util.Random;

public class RankineLeavesBlock extends LeavesBlock {
    public RankineLeavesBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (random.nextFloat() < Config.GENERAL.LEAF_LITTER_GEN.get()) {
            int i = 1;
            while (worldIn.getBlockState(pos.down(i)).matchesBlock(Blocks.AIR)) {
                ++i;
            }
            if (worldIn.getBlockState(pos.down(i+1)).isSolid()) {
                worldIn.setBlockState(pos.down(i), RankineLists.LEAF_LITTERS.get(RankineLists.LEAVES.indexOf(state.getBlock())).getDefaultState(),2);
            }
           // if (worldIn.getBlockState(pos.down(i+1)).isReplaceable(Fluids.FLOWING_WATER)) {
           //     worldIn.setBlockState(pos.down(i+1), RankineLists.LEAF_LITTERS.get(RankineLists.LEAVES.indexOf(state.getBlock())).getDefaultState(),2);
           // } else {
           //     worldIn.setBlockState(pos.down(i), RankineLists.LEAF_LITTERS.get(RankineLists.LEAVES.indexOf(state.getBlock())).getDefaultState(),2);
           // }
        }
        super.randomTick(state, worldIn, pos, random);
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 30;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return 60;
    }
}
