package com.cannolicatfish.rankine.blocks.particleaccelerator;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ParticleAcceleratorBlock extends Block {
    public ParticleAcceleratorBlock(Properties properties) {
        super(properties);
    }


    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 10;
    }
}
