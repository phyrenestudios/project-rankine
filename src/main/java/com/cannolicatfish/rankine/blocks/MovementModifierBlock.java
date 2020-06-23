package com.cannolicatfish.rankine.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MovementModifierBlock extends Block {

    private final Double modifier;
    public MovementModifierBlock(double modifier, Properties properties) {
        super(properties);
        this.modifier = modifier;
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        entityIn.setMotion(entityIn.getMotion().mul(modifier, 1.0D, modifier));
        super.onEntityWalk(worldIn, pos, entityIn);
    }
}
