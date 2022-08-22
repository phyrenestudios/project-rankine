package com.cannolicatfish.rankine.blocks.gases;

import com.cannolicatfish.rankine.blocks.GrassySoilBlock;
import com.cannolicatfish.rankine.util.GasUtilsEnum;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoulFireBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class OxygenGasBlock extends GasBlock {

    public OxygenGasBlock(Supplier<? extends Item> gasBottle, GasUtilsEnum gasUtilsEnum, Properties properties) {
        super(gasBottle,gasUtilsEnum, properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        BlockPos fire = BlockPos.findClosestMatch(pos.offset(random.nextInt(4) - 2,0,random.nextInt(4) - 2),2,2,B -> worldIn.getBlockState(B).getBlock() instanceof BaseFireBlock).orElse(null);
        boolean done = false;
        if (fire != null) {
            List<BlockPos> posCompleted = new ArrayList<>();

            for (int i = 0; i < 5; i++) {
                BlockPos close = BlockPos.findClosestMatch(pos.offset(random.nextInt(4) - 2,0,random.nextInt(4) - 2),2,2,B -> (worldIn.isEmptyBlock(B) && !worldIn.isEmptyBlock(B.below()) && !posCompleted.contains(B))).orElse(null);
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
            worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(),3);
        } else {
            super.randomTick(state, worldIn, pos, random);
        }
    }
}
