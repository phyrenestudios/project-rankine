package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

import net.minecraft.block.AbstractBlock.Properties;

public class MineralColumnBlock extends StoneColumnBlock implements IWaterLoggable {
    //public static final IntegerProperty STABILITY = IntegerProperty.create("stability",0,24);

    public MineralColumnBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!worldIn.isClientSide && (worldIn.getBlockState(pos.above(1)).is(RankineTags.Blocks.STONES_LIMESTONE) || worldIn.getBlockState(pos.above(1)).is(RankineTags.Blocks.STONES_DOLOMITE)) && worldIn.getBlockState(pos.above(2)).is(Blocks.WATER)) {
            grow(worldIn,pos);
            //worldIn.setBlockState(pos,state.with(SIZE,Math.min(7,state.get(SIZE)+1)));
        }

        super.randomTick(state, worldIn, pos, random);
    }

    private void grow(World worldIn, BlockPos pos) {
        Random rand = worldIn.getRandom();
        int i = 0;
        while (worldIn.getBlockState(pos.below(i)).is(this)) {
            ++i;
        }
        if (rand.nextFloat() < 1.0f/(i+1) && worldIn.getBlockState(pos.below(i)).is(Blocks.AIR)) {
            worldIn.setBlockAndUpdate(pos.below(i),this.defaultBlockState());
        } else {
            int R = rand.nextInt(i);
            BlockState targetBS = worldIn.getBlockState(pos.below(R));
            if (rand.nextFloat() < 1.0f/(R+2)) {
                boolean flag1 = worldIn.getBlockState(pos.below(R).above()).is(this);
                boolean flag2 = worldIn.getBlockState(pos.below(R).below()).is(this);
                if (flag1 && flag2 && targetBS.getValue(SIZE)+1 - worldIn.getBlockState(pos.below(R).above()).getValue(SIZE) < 2 && targetBS.getValue(SIZE)+1 - worldIn.getBlockState(pos.below(R).below()).getValue(SIZE) < 2) {
                    worldIn.setBlockAndUpdate(pos.below(R),targetBS.setValue(SIZE,Math.min(7,targetBS.getValue(SIZE)+1)));
                } else if (flag1 && targetBS.getValue(SIZE)+1 - worldIn.getBlockState(pos.below(R).above()).getValue(SIZE) < 2) {
                    worldIn.setBlockAndUpdate(pos.below(R),targetBS.setValue(SIZE,Math.min(7,targetBS.getValue(SIZE)+1)));
                } else if (flag2 && targetBS.getValue(SIZE)+1 - worldIn.getBlockState(pos.below(R).below()).getValue(SIZE) < 2) {
                    worldIn.setBlockAndUpdate(pos.below(R),targetBS.setValue(SIZE,Math.min(7,targetBS.getValue(SIZE)+1)));
                }
            }
        }
    }

}
