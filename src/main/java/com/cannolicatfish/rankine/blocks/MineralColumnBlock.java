package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class MineralColumnBlock extends StoneColumnBlock implements SimpleWaterloggedBlock {
    //public static final IntegerProperty STABILITY = IntegerProperty.create("stability",0,24);

    public MineralColumnBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        if (!worldIn.isClientSide && (worldIn.getBlockState(pos.above(1)).is(RankineTags.Blocks.STONES_LIMESTONE) || worldIn.getBlockState(pos.above(1)).is(RankineTags.Blocks.STONES_DOLOMITE)) && worldIn.getBlockState(pos.above(2)).is(Blocks.WATER)) {
            grow(worldIn,pos);
            //worldIn.setBlockState(pos,state.with(SIZE,Math.min(7,state.get(SIZE)+1)));
        }

        super.randomTick(state, worldIn, pos, random);
    }

    private void grow(Level worldIn, BlockPos pos) {
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
