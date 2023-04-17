package com.cannolicatfish.rankine.blocks.gases;

import com.cannolicatfish.rankine.util.GasUtilsEnum;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class HydrogenGasBlock extends GasBlock {
    public HydrogenGasBlock(Supplier<? extends Item> gasBottle, GasUtilsEnum gasUtilsEnum, Properties properties) {
        super(gasBottle,gasUtilsEnum, properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        BlockPos fire = BlockPos.findClosestMatch(pos.offset(random.nextInt(4) - 2,0,random.nextInt(4) - 2),2,2,B -> worldIn.getBlockState(B).getBlock() instanceof BaseFireBlock).orElse(null);
        boolean done = false;
        if (fire != null) {
            List<BlockPos> posCompleted = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                BlockPos close = BlockPos.findClosestMatch(pos.offset(random.nextInt(6) - 3,0,random.nextInt(6) - 3),2,2,B -> (worldIn.isEmptyBlock(B) && !worldIn.isEmptyBlock(B.below()) && !posCompleted.contains(B))).orElse(null);
                if (close != null) {
                    posCompleted.add(close);
                    worldIn.setBlockAndUpdate(close, BaseFireBlock.getState(worldIn, close));
                    done = true;
                } else {
                    break;
                }
            }
        }

        if (done) {
            worldIn.explode((Entity)null, pos.getX(), pos.getY(), pos.getZ(), (float)3, true, Level.ExplosionInteraction.NONE);
            worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(),3);
        } else {
            super.randomTick(state, worldIn, pos, random);
        }
    }
}
