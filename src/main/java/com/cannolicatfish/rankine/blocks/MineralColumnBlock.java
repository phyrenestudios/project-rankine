package com.cannolicatfish.rankine.blocks;

import com.cannolicatfish.rankine.init.RankineTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class MineralColumnBlock extends StoneColumnBlock implements IWaterLoggable {
    //public static final IntegerProperty STABILITY = IntegerProperty.create("stability",0,24);

    public MineralColumnBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!worldIn.isRemote && (worldIn.getBlockState(pos.up(1)).isIn(RankineTags.Blocks.STONES_LIMESTONE) || worldIn.getBlockState(pos.up(1)).isIn(RankineTags.Blocks.STONES_DOLOMITE)) && worldIn.getBlockState(pos.up(2)).matchesBlock(Blocks.WATER)) {
            grow(worldIn,pos);
            //worldIn.setBlockState(pos,state.with(SIZE,Math.min(7,state.get(SIZE)+1)));
        }

        super.randomTick(state, worldIn, pos, random);
    }

    private void grow(World worldIn, BlockPos pos) {
        Random rand = worldIn.getRandom();
        int i = 0;
        while (worldIn.getBlockState(pos.down(i)).matchesBlock(this)) {
            ++i;
        }
        if (rand.nextFloat() < 1.0f/(i+1) && worldIn.getBlockState(pos.down(i)).matchesBlock(Blocks.AIR)) {
            worldIn.setBlockState(pos.down(i),this.getDefaultState());
        } else {
            int R = rand.nextInt(i);
            BlockState targetBS = worldIn.getBlockState(pos.down(R));
            if (rand.nextFloat() < 1.0f/(R+2)) {
                boolean flag1 = worldIn.getBlockState(pos.down(R).up()).matchesBlock(this);
                boolean flag2 = worldIn.getBlockState(pos.down(R).down()).matchesBlock(this);
                if (flag1 && flag2 && targetBS.get(SIZE)+1 - worldIn.getBlockState(pos.down(R).up()).get(SIZE) < 2 && targetBS.get(SIZE)+1 - worldIn.getBlockState(pos.down(R).down()).get(SIZE) < 2) {
                    worldIn.setBlockState(pos.down(R),targetBS.with(SIZE,Math.min(7,targetBS.get(SIZE)+1)));
                } else if (flag1 && targetBS.get(SIZE)+1 - worldIn.getBlockState(pos.down(R).up()).get(SIZE) < 2) {
                    worldIn.setBlockState(pos.down(R),targetBS.with(SIZE,Math.min(7,targetBS.get(SIZE)+1)));
                } else if (flag2 && targetBS.get(SIZE)+1 - worldIn.getBlockState(pos.down(R).down()).get(SIZE) < 2) {
                    worldIn.setBlockState(pos.down(R),targetBS.with(SIZE,Math.min(7,targetBS.get(SIZE)+1)));
                }
            }
        }
    }

}
