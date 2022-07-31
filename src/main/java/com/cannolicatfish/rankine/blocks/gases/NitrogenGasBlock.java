package com.cannolicatfish.rankine.blocks.gases;

import com.cannolicatfish.rankine.blocks.GasBlock;
import com.cannolicatfish.rankine.blocks.GrassySoilBlock;
import com.cannolicatfish.rankine.util.GasUtilsEnum;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NitrogenGasBlock extends AbstractGasBlock {
    public NitrogenGasBlock(float densityIn, float dissipationChanceIn, List<MobEffectInstance> effectInstancesIn, boolean suffocatingIn, int colorIn, Properties properties) {
        super(densityIn, dissipationChanceIn, effectInstancesIn, suffocatingIn, colorIn, properties);
    }

    public NitrogenGasBlock(GasUtilsEnum gasUtilsEnum, Properties properties) {
        this(gasUtilsEnum.getDensity(),gasUtilsEnum.getDissipationRate(),gasUtilsEnum.getEffects(),gasUtilsEnum.isSuffocating(),gasUtilsEnum.getColor(),properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
        List<BlockPos> posCompleted = new ArrayList<>();
        boolean done = false;
        for (int i = 0; i < 5; i++) {
            BlockPos close = BlockPos.findClosestMatch(pos.offset(random.nextInt(4) - 2,0,random.nextInt(4) - 2),2,2,B -> (worldIn.getBlockState(B).getBlock() instanceof GrassySoilBlock && !posCompleted.contains(B))).orElse(null);
            if (close != null) {
                posCompleted.add(close);
                ((GrassySoilBlock) worldIn.getBlockState(close).getBlock()).growGrassBlock(state,worldIn,close,random);
                done = true;
            } else {
                break;
            }
        }
        if (done) {
            worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(),3);
        } else {
            super.randomTick(state, worldIn, pos, random);
        }
    }
}
