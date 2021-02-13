package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SedimentFanBlock extends Block {
    public SedimentFanBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        List<Fluid> adjPos = Arrays.asList(worldIn.getFluidState(pos.south().up()).getFluid(),worldIn.getFluidState(pos.north().up()).getFluid(),
                worldIn.getFluidState(pos.west().up()).getFluid(),worldIn.getFluidState(pos.east().up()).getFluid());
        if (worldIn.getFluidState(pos.up()).isSource()) {
            Direction dir = null;
            if (adjPos.contains(Fluids.FLOWING_WATER))
            switch (adjPos.indexOf(Fluids.FLOWING_WATER)) {
                case 0:
                    dir = Direction.SOUTH;
                    break;
                case 1:
                    dir = Direction.NORTH;
                    break;
                case 2:
                    dir = Direction.WEST;
                    break;
                case 3:
                    dir = Direction.EAST;
                    break;
            }

            if (dir != null)
            {
                List<Fluid> waterChecks = Arrays.asList(worldIn.getFluidState(pos.up().offset(dir,2)).getFluid(),worldIn.getFluidState(pos.up().offset(dir,3)).getFluid());
                if (waterChecks.stream().allMatch((e) -> e == Fluids.FLOWING_WATER)) {
                    Block sediment = worldIn.getBlockState(pos.offset(dir)).getBlock();
                    Block sediment2 = worldIn.getBlockState(pos.offset(dir,2)).getBlock();
                    BlockPos end = pos.up().offset(dir,3);

                    if ((sediment == Blocks.RED_SAND && sediment2 == Blocks.QUARTZ_BLOCK) || (sediment2 == Blocks.RED_SAND && sediment == Blocks.QUARTZ_BLOCK)) {
                        worldIn.setBlockState(end, RankineBlocks.ARKOSE_SANDSTONE.get().getDefaultState(),2);
                        return;
                    } else if ((sediment == Blocks.SAND && sediment2 == Blocks.QUARTZ_BLOCK) || (sediment2 == Blocks.SAND && sediment == Blocks.QUARTZ_BLOCK)) {
                        worldIn.setBlockState(end, RankineBlocks.QUARTZ_SANDSTONE.get().getDefaultState(),2);
                        return;
                    }else if ((sediment == Blocks.SAND && sediment2 == Blocks.CLAY) || (sediment == Blocks.CLAY && sediment2 == Blocks.SAND)) {
                        worldIn.setBlockState(end, RankineBlocks.SILTSTONE.get().getDefaultState(), 2);
                        return;
                    }  else if ((sediment == RankineBlocks.MUD.get()) && (sediment2 == RankineBlocks.MUD.get())) {
                        worldIn.setBlockState(end, RankineBlocks.MUDSTONE.get().getDefaultState(), 2);
                        return;
                    } else if ((sediment == Blocks.RED_SAND) && (sediment2 == Blocks.RED_SAND)) {
                        worldIn.setBlockState(end, Blocks.RED_SANDSTONE.getDefaultState(),2);
                        return;
                    } else if ((sediment == Blocks.SAND) && (sediment2 == Blocks.SAND)) {
                        worldIn.setBlockState(end, Blocks.SANDSTONE.getDefaultState(),2);
                        return;
                    } else if ((sediment == Blocks.CLAY) && (sediment2 == Blocks.CLAY)) {
                        worldIn.setBlockState(end, RankineBlocks.CARBONACEOUS_SHALE.get().getDefaultState(),2);
                        return;
                    }  else if ((sediment == RankineBlocks.DOLOMITE_BLOCK.get()) && (sediment2 == RankineBlocks.DOLOMITE_BLOCK.get())) {
                        worldIn.setBlockState(end, RankineBlocks.DOLOSTONE.get().getDefaultState(), 2);
                        return;
                    } else if ((sediment == RankineBlocks.CALCITE_BLOCK.get()) && (sediment2 == RankineBlocks.CALCITE_BLOCK.get())) {
                        worldIn.setBlockState(end, RankineBlocks.TUFA_LIMESTONE.get().getDefaultState(), 2);
                        return;
                    } else if ((sediment == RankineBlocks.CALCITE_BLOCK.get()) && (sediment2 == RankineBlocks.CALCITE_BLOCK.get())) {
                        worldIn.setBlockState(end, RankineBlocks.TUFA_LIMESTONE.get().getDefaultState(), 2);
                        return;
                    }
                    switch (worldIn.getRandom().nextInt(11))
                    {
                        case 0:
                            worldIn.setBlockState(end,Blocks.SANDSTONE.getDefaultState(),2);
                            break;
                        case 1:
                            worldIn.setBlockState(end,Blocks.RED_SANDSTONE.getDefaultState(),2);
                            break;
                        case 2:
                            worldIn.setBlockState(end,RankineBlocks.QUARTZ_SANDSTONE.get().getDefaultState(),2);
                            break;
                        case 3:
                            worldIn.setBlockState(end,RankineBlocks.ARKOSE_SANDSTONE.get().getDefaultState(),2);
                            break;
                        case 4:
                            worldIn.setBlockState(end,RankineBlocks.TUFA_LIMESTONE.get().getDefaultState(),2);
                            break;
                        case 5:
                            worldIn.setBlockState(end,RankineBlocks.DOLOSTONE.get().getDefaultState(),2);
                            break;
                        case 6:
                            worldIn.setBlockState(end,RankineBlocks.BRECCIA.get().getDefaultState(),2);
                            break;
                        case 7:
                            worldIn.setBlockState(end,RankineBlocks.MUDSTONE.get().getDefaultState(),2);
                            break;
                        case 8:
                            worldIn.setBlockState(end,RankineBlocks.CARBONACEOUS_SHALE.get().getDefaultState(),2);
                            break;
                        case 9:
                            worldIn.setBlockState(end,RankineBlocks.SILTSTONE.get().getDefaultState(),2);
                            break;
                        case 10:
                            worldIn.setBlockState(end,RankineBlocks.CHALK.get().getDefaultState(),2);
                            break;
                    }
                }

            }



        }

    }
}
