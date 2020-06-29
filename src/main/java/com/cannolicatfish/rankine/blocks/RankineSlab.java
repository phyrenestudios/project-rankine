package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RankineSlab extends SlabBlock {
    private final Double modifier;
    public RankineSlab(Properties properties) {
        super(properties);
        this.modifier = 1.0D;
    }

    public RankineSlab(Double modifier, Properties properties) {
        super(properties);
        this.modifier = modifier;
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        entityIn.setMotion(entityIn.getMotion().mul(modifier, 1.0D, modifier));
        super.onEntityWalk(worldIn, pos, entityIn);
    }
}
